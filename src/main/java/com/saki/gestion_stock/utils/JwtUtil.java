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

// Service utilitaire pour la gestion des JSON Web Tokens (JWT)
// Gère la génération, validation et extraction des informations des tokens JWT
@Service
public class JwtUtil {

    // Clé secrète pour signer les tokens - À EXTERNALISER en production
    // En environnement réel, utiliser une clé forte et la stocker dans les variables d'environnement
    private final String SECRET_KEY = "secret"; // À externaliser en prod

    // Durée de validité du token : 24 heures (corrigé du commentaire original)
    // 1000ms * 60s * 60min * 24h = 24 heures en millisecondes
    private final long EXPIRATION_TIME = 1000 * 60 * 60 * 24; // 24 heures en millisecondes

    // Extrait le nom d'utilisateur (subject) du token JWT
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // Extrait la date d'expiration du token JWT
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    // Extrait l'ID de l'entreprise depuis les claims personnalisés du token
    public String extractIdEntreprise(String token) {
        return extractClaim(token, claims -> claims.get("idEntreprise", String.class));
    }

    // Extrait la liste des rôles depuis les claims personnalisés du token
    public java.util.List<String> extractRoles(String token) {
        return extractClaim(token, claims -> claims.get("roles", java.util.List.class));
    }

    // Méthode générique pour extraire n'importe quel claim du token
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // Extrait tous les claims (revendications) du token JWT
    private Claims extractAllClaims(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(SECRET_KEY)  // Utilise la clé secrète pour vérifier la signature
                    .parseClaimsJws(token)      // Parse le token JWS (JSON Web Signature)
                    .getBody();                 // Retourne le corps du token (les claims)
        } catch (ExpiredJwtException e) {
            System.err.println("JWT expiré: " + e.getMessage());
            throw e; // Propager l'exception pour traitement spécifique
        } catch (MalformedJwtException | SignatureException | UnsupportedJwtException | IllegalArgumentException e) {
            System.err.println("JWT invalide: " + e.getMessage());
            throw new RuntimeException("JWT invalide", e);
        }
    }

    // Vérifie si le token est expiré
    private Boolean isTokenExpired(String token) {
        try {
            return extractExpiration(token).before(new Date());
        } catch (Exception e) {
            return true; // En cas d'erreur, considérer le token comme expiré par sécurité
        }
    }

    // Génère un nouveau token JWT pour un utilisateur étendu
    public String generateToken(ExtendedUser userDetails) {
        Map<String, Object> claims = new HashMap<>();

        // Ajoute les rôles de l'utilisateur dans les claims
        claims.put("roles", userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)  // Convertit les GrantedAuthority en String
                .collect(Collectors.toList()));

        return createToken(claims, userDetails);
    }

    // Crée le token JWT avec les claims spécifiés
    private String createToken(Map<String, Object> claims, ExtendedUser userDetails) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + EXPIRATION_TIME);

        // Debug - Affichage des informations de génération
        System.out.println("=== JWT GENERATION ===");
        System.out.println("Utilisateur: " + userDetails.getUsername());
        System.out.println("Généré à: " + now);
        System.out.println("Expire à: " + expiryDate);
        System.out.println("Durée: " + (EXPIRATION_TIME/1000/60/60) + " heures"); // Correction du calcul
        System.out.println("======================");

        // Construction du token JWT
        return Jwts.builder()
                .setClaims(claims)                                      // Claims personnalisés (rôles)
                .setSubject(userDetails.getUsername())                  // Sujet (nom d'utilisateur)
                .setIssuedAt(now)                                       // Date d'émission
                .setExpiration(expiryDate)                              // Date d'expiration
                .claim("idEntreprise", userDetails.getIdEntreprise().toString()) // Claim personnalisé multi-locataire
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)         // Signature avec algorithme HS256
                .compact();                                             // Génération finale
    }

    // Valide le token par rapport aux détails de l'utilisateur
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
            return false; // En cas d'erreur, le token est invalide
        }
    }

    // Méthode utilitaire pour debugger le contenu d'un token
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