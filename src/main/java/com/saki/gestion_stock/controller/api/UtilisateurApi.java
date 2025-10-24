// Déclaration du package pour les interfaces API
package com.saki.gestion_stock.controller.api;

// Importations des DTOs utilisateur
import com.saki.gestion_stock.dto.ChangerMotDePasseUtilisateurDto;
import com.saki.gestion_stock.dto.UtilisateurDto;
// Importation de l'annotation Swagger
import io.swagger.annotations.Api;
import java.util.List;
// Importations des annotations Spring Web
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

// Importation de la constante pour l'endpoint utilisateur
import static com.saki.gestion_stock.utils.Constants.UTILISATEUR_ENDPOINT;

// Annotation Swagger pour documenter cette interface comme API "utilisateurs"
@Api("utilisateurs")
public interface UtilisateurApi {

    // Endpoint pour créer ou modifier un utilisateur
    @PostMapping(UTILISATEUR_ENDPOINT + "/create")
    // Méthode pour sauvegarder un utilisateur (create ou update)
    UtilisateurDto save(@RequestBody UtilisateurDto dto);

    // Endpoint pour changer le mot de passe d'un utilisateur
    @PostMapping(UTILISATEUR_ENDPOINT + "/update/password")
    // Méthode spécifique pour modifier le mot de passe d'un utilisateur
    UtilisateurDto changerMotDePasse(@RequestBody ChangerMotDePasseUtilisateurDto dto);

    // Endpoint pour rechercher un utilisateur par son ID
    @GetMapping(UTILISATEUR_ENDPOINT + "/{idUtilisateur}")
    // Méthode pour trouver un utilisateur par son identifiant
    UtilisateurDto findById(@PathVariable("idUtilisateur") Integer id);

    // Endpoint pour rechercher un utilisateur par son email
    @GetMapping(UTILISATEUR_ENDPOINT + "/find/{email}")
    // Méthode pour trouver un utilisateur par son adresse email
    UtilisateurDto findByEmail(@PathVariable("email") String email);

    // Endpoint pour récupérer tous les utilisateurs
    @GetMapping(UTILISATEUR_ENDPOINT + "/all")
    // Méthode pour récupérer la liste de tous les utilisateurs
    List<UtilisateurDto> findAll();

    // Endpoint pour supprimer un utilisateur
    @DeleteMapping(UTILISATEUR_ENDPOINT + "/delete/{idUtilisateur}")
    // Méthode pour supprimer un utilisateur par son identifiant
    void delete(@PathVariable("idUtilisateur") Integer id);

}