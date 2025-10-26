package com.saki.gestion_stock.Config;

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

@Component
public class ApplicationRequestFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private ApplicationUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        // Autoriser les requêtes OPTIONS sans authentification
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            chain.doFilter(request, response);
            return;
        }

        // Vérification des routes publiques
        String path = request.getServletPath();
        if (isPublicPath(path)) {
            chain.doFilter(request, response);
            return;
        }

        // Récupération du header Authorization
        final String authHeader = request.getHeader("Authorization");
        String userEmail = null;
        String jwt = null;
        String idEntreprise = null;

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            jwt = authHeader.substring(7);
            try {
                userEmail = jwtUtil.extractUsername(jwt);
                idEntreprise = jwtUtil.extractIdEntreprise(jwt);
            } catch (Exception e) {
                logger.warn("JWT invalide: " + e.getMessage());
            }
        }

        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
            if (jwtUtil.validateToken(jwt, userDetails)) {
                List<SimpleGrantedAuthority> authorities = jwtUtil.extractRoles(jwt).stream()
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, authorities);
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        MDC.put("idEntreprise", idEntreprise);
        chain.doFilter(request, response);
    }

    private boolean isPublicPath(String path) {
        return path.equals("/gestiondestock/v1/auth/authenticate") ||
                path.equals("/gestiondestock/v1/entreprises/create") ||
                path.startsWith("/v2/api-docs") ||
                path.startsWith("/v3/api-docs") ||
                path.startsWith("/swagger-resources") ||
                path.startsWith("/swagger-ui") ||
                path.startsWith("/webjars") ||
                path.startsWith("/configuration/ui") ||
                path.startsWith("/configuration/security");
    }
}