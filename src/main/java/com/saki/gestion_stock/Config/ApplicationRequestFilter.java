// Déclaration du package pour la configuration
package com.saki.gestion_stock.Config;

// Importations des classes nécessaires
import com.saki.gestion_stock.services.auth.ApplicationUserDetailsService;
import com.saki.gestion_stock.utils.JwtUtil;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

// Déclaration de la classe comme composant Spring et filtre exécuté une fois par requête
@Component
public class ApplicationRequestFilter extends OncePerRequestFilter {

    // Injection du service utilitaire JWT
    @Autowired
    private JwtUtil jwtUtil;

    // Injection du service de détails utilisateur
    @Autowired
    private ApplicationUserDetailsService userDetailsService;

    // Implémentation de la méthode principale du filtre
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        // Vérification des routes publiques qui ne nécessitent pas d'authentification
        String path = request.getServletPath();
        if (path.equals("/gestiondestock/v1/auth/authenticate") || path.equals("/gestiondestock/v1/entreprises/create")) {
            // Pour les routes publiques, passer directement au filtre suivant
            chain.doFilter(request, response);
            return;
        }

        // Récupération du header Authorization de la requête
        final String authHeader = request.getHeader("Authorization");
        String userEmail = null;
        String jwt = null;
        String idEntreprise = null;

        // Vérification de la présence et du format du token JWT
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            // Extraction du token JWT (enlevant "Bearer ")
            jwt = authHeader.substring(7);
            try {
                // Extraction de l'email utilisateur depuis le JWT
                userEmail = jwtUtil.extractUsername(jwt);
                // Extraction de l'ID de l'entreprise depuis le JWT
                idEntreprise = jwtUtil.extractIdEntreprise(jwt);
            } catch (Exception e) {
                // Log en cas de JWT invalide ou expiré
                logger.warn("JWT invalide: " + e.getMessage());
            }
        }

        // Vérification si l'utilisateur est trouvé et non déjà authentifié
        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // Chargement des détails de l'utilisateur depuis la base de données
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
            // Validation du token JWT avec les détails de l'utilisateur
            if (jwtUtil.validateToken(jwt, userDetails)) {
                // Extraction des rôles depuis le JWT et conversion en autorités Spring Security
                List<SimpleGrantedAuthority> authorities = jwtUtil.extractRoles(jwt).stream()
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

                // Création du token d'authentification Spring Security
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, authorities);
                // Ajout des détails de la requête au token
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                // Définition de l'authentification dans le contexte de sécurité
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        // Ajout de l'ID entreprise dans le contexte MDC pour les logs
        MDC.put("idEntreprise", idEntreprise);
        // Passage au filtre suivant dans la chaîne
        chain.doFilter(request, response);
    }
}