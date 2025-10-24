// Déclaration du package pour les interfaces API
package com.saki.gestion_stock.controller.api;

// Importation de la constante pour le endpoint d'authentification
import static com.saki.gestion_stock.utils.Constants.AUTHENTICATION_ENDPOINT;

// Importation des DTOs pour l'authentification
import com.saki.gestion_stock.dto.auth.AuthenticationRequest;
import com.saki.gestion_stock.dto.auth.AuthenticationResponse;
// Importation de l'annotation Swagger
import io.swagger.annotations.Api;
// Importation des classes Spring
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

// Annotation Swagger pour documenter cette interface comme API "authentication"
@Api("authentication")
public interface AuthenticationApi {

    // Endpoint pour l'authentification des utilisateurs
    @PostMapping(AUTHENTICATION_ENDPOINT + "/authenticate")
    // Méthode pour authentifier un utilisateur et générer un token JWT
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request);

}