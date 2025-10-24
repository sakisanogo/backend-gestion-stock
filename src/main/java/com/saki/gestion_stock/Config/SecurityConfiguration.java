// Déclaration du package pour la configuration de sécurité
package com.saki.gestion_stock.Config;

// Importations des classes nécessaires pour la sécurité Spring
import com.saki.gestion_stock.services.auth.ApplicationUserDetailsService;
import com.saki.gestion_stock.Config.ApplicationRequestFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;
import java.util.Collections;

// Activation de la sécurité Web Spring
@EnableWebSecurity
// Déclaration de cette classe comme configuration Spring
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    // Injection du service de détails utilisateur personnalisé
    @Autowired
    private ApplicationUserDetailsService applicationUserDetailsService;

    // Injection du filtre JWT personnalisé
    @Autowired
    private ApplicationRequestFilter applicationRequestFilter;

    // Configuration de l'authentification
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // Configuration du service utilisateur personnalisé et de l'encodeur de mots de passe
        auth.userDetailsService(applicationUserDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    // Configuration de la sécurité HTTP
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                // Activation de la configuration CORS
                .cors()
                .and()
                // Désactivation de la protection CSRF (car API stateless + JWT)
                .csrf().disable()
                // Configuration des autorisations des requêtes
                .authorizeRequests()
                // Endpoints publics - accessibles sans authentification
                .antMatchers(HttpMethod.POST, "/gestiondestock/v1/auth/authenticate").permitAll()
                .antMatchers(HttpMethod.POST, "/gestiondestock/v1/entreprises/create").permitAll()
                .antMatchers(HttpMethod.POST, "/gestiondestock/v1/articles/create").permitAll()
                .antMatchers(HttpMethod.POST,"/gestiondestock/v1/commandesclients/**").permitAll()
                .antMatchers(HttpMethod.POST,"/gestiondestock/v1/commandesfournisseurs/**").permitAll()

                // Configuration des endpoints Swagger/OpenAPI - accessibles sans authentification
                .antMatchers(
                        "/v2/api-docs",
                        "/swagger-resources",
                        "/swagger-resources/**",
                        "/configuration/ui",
                        "/configuration/security",
                        "/swagger-ui.html",
                        "/webjars/**",
                        "/v3/api-docs/**",
                        "/swagger-ui/**"
                ).permitAll()
                // Toutes les autres requêtes nécessitent une authentification
                .anyRequest().authenticated()
                .and()
                // Configuration de la gestion des sessions en mode stateless (sans session)
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // Ajout du filtre JWT personnalisé avant le filtre d'authentification standard
        http.addFilterBefore(applicationRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }

    // Configuration CORS pour autoriser les requêtes cross-origin
    @Bean
    public CorsFilter corsFilter() {
        // Source de configuration basée sur les URLs
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        // Configuration CORS
        CorsConfiguration config = new CorsConfiguration();
        // Autorisation des credentials (cookies, auth headers)
        config.setAllowCredentials(true);
        // Origines autorisées (Angular dev server)
        config.setAllowedOrigins(Arrays.asList("http://localhost:4200", "http://127.0.0.1:4200"));
        // Headers autorisés
        config.setAllowedHeaders(Arrays.asList("Origin", "Content-Type", "Accept", "Authorization"));
        // Méthodes HTTP autorisées
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
        // Application de la configuration à tous les endpoints
        source.registerCorsConfiguration("/**", config);
        // Retour du filtre CORS configuré
        return new CorsFilter(source);
    }

    // Exposition du AuthenticationManager en tant que bean pour injection
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    // Configuration de l'encodeur de mots de passe avec BCrypt
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}