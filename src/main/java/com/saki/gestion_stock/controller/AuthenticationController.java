// Déclaration du package pour les contrôleurs
package com.saki.gestion_stock.controller;

// Importations de l'interface API et des classes d'authentification
import com.saki.gestion_stock.controller.api.AuthenticationApi;
import com.saki.gestion_stock.dto.auth.AuthenticationRequest;
import com.saki.gestion_stock.dto.auth.AuthenticationResponse;
import com.saki.gestion_stock.model.auth.ExtendedUser;
import com.saki.gestion_stock.services.auth.ApplicationUserDetailsService;
import com.saki.gestion_stock.utils.JwtUtil;
// Importations des classes Spring
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

// Configuration CORS pour autoriser les requêtes depuis le frontend Angular
@CrossOrigin(origins = "http://localhost:4200")
// Annotation indiquant que cette classe est un contrôleur REST
@RestController
public class AuthenticationController implements AuthenticationApi {

    // Injection du gestionnaire d'authentification Spring Security
    @Autowired
    private AuthenticationManager authenticationManager;

    // Injection du service de détails utilisateur personnalisé
    @Autowired
    private ApplicationUserDetailsService userDetailsService;

    // Injection de l'utilitaire JWT pour la génération de tokens
    @Autowired
    private JwtUtil jwtUtil;

    // Implémentation de la méthode d'authentification
    @Override
    public ResponseEntity<AuthenticationResponse> authenticate(AuthenticationRequest request) {
        // Authentification des credentials (login/mot de passe) via Spring Security
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getLogin(),    // Récupération du login depuis la requête
                        request.getPassword()  // Récupération du mot de passe depuis la requête
                )
        );
        // Chargement des détails de l'utilisateur après authentification réussie
        final UserDetails userDetails = userDetailsService.loadUserByUsername(request.getLogin());

        // Génération du token JWT avec les informations de l'utilisateur étendu
        final String jwt = jwtUtil.generateToken((ExtendedUser) userDetails);

        // Retour de la réponse contenant le token JWT
        return ResponseEntity.ok(AuthenticationResponse.builder().accessToken(jwt).build());
    }

}