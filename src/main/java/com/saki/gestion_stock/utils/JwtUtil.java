package com.saki.gestion_stock.utils;

import com.saki.gestion_stock.model.auth.ExtendedUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class JwtUtil {

    private final String SECRET_KEY = "secret"; // À externaliser en prod

    // Durée de validité du token : 1 heure
    private final long EXPIRATION_TIME = 1000 * 60 * 60 * 24; // 1 heure en millisecondes

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public String extractIdEntreprise(String token) {
        return extractClaim(token, claims -> claims.get("idEntreprise", String.class));
    }

    public java.util.List<String> extractRoles(String token) {
        return extractClaim(token, claims -> claims.get("roles", java.util.List.class));
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            System.err.println("JWT expiré: " + e.getMessage());
            throw e; // Propager l'exception
        } catch (MalformedJwtException | SignatureException | UnsupportedJwtException | IllegalArgumentException e) {
            System.err.println("JWT invalide: " + e.getMessage());
            throw new RuntimeException("JWT invalide", e);
        }
    }

    private Boolean isTokenExpired(String token) {
        try {
            return extractExpiration(token).before(new Date());
        } catch (Exception e) {
            return true; // Si erreur, considérer comme expiré
        }
    }

    public String generateToken(ExtendedUser userDetails) {
        Map<String, Object> claims = new HashMap<>();

        claims.put("roles", userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList()));

        return createToken(claims, userDetails);
    }

    private String createToken(Map<String, Object> claims, ExtendedUser userDetails) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + EXPIRATION_TIME);

        // Debug
        System.out.println("=== JWT GENERATION ===");
        System.out.println("Utilisateur: " + userDetails.getUsername());
        System.out.println("Généré à: " + now);
        System.out.println("Expire à: " + expiryDate);
        System.out.println("Durée: " + (EXPIRATION_TIME/1000/60) + " minutes");
        System.out.println("======================");

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .claim("idEntreprise", userDetails.getIdEntreprise().toString())
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        try {
            final String username = extractUsername(token);
            boolean isValid = (username.equals(userDetails.getUsername()) && !isTokenExpired(token));

            if (!isValid) {
                System.err.println("Token invalide pour: " + username);
            }

            return isValid;
        } catch (Exception e) {
            System.err.println("Erreur validation token: " + e.getMessage());
            return false;
        }
    }

    // Méthode utilitaire pour debug
    public void debugToken(String token) {
        try {
            Claims claims = extractAllClaims(token);
            System.out.println("=== JWT DEBUG ===");
            System.out.println("Sujet: " + claims.getSubject());
            System.out.println("Émis à: " + claims.getIssuedAt());
            System.out.println("Expire à: " + claims.getExpiration());
            System.out.println("IdEntreprise: " + claims.get("idEntreprise"));
            System.out.println("Roles: " + claims.get("roles"));
            System.out.println("Temps restant: " +
                    (claims.getExpiration().getTime() - System.currentTimeMillis()) + "ms");
            System.out.println("=================");
        } catch (Exception e) {
            System.err.println("Erreur debug token: " + e.getMessage());
        }
    }
}