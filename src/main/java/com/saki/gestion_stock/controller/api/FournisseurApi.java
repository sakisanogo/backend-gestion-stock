// Déclaration du package pour les interfaces API
package com.saki.gestion_stock.controller.api;

// Importation du DTO Fournisseur
import com.saki.gestion_stock.dto.FournisseurDto;
// Importation de l'annotation Swagger
import io.swagger.annotations.Api;
import java.util.List;
// Importations des annotations Spring Web
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

// Importation de la constante pour l'endpoint fournisseur
import static com.saki.gestion_stock.utils.Constants.FOURNISSEUR_ENDPOINT;

// Annotation Swagger pour documenter cette interface comme API "fournisseur"
@Api("fournisseur")
public interface FournisseurApi {

    // Endpoint pour créer ou modifier un fournisseur
    @PostMapping(FOURNISSEUR_ENDPOINT + "/create")
    // Méthode pour sauvegarder un fournisseur (create ou update)
    FournisseurDto save(@RequestBody FournisseurDto dto);

    // Endpoint pour rechercher un fournisseur par son ID
    @GetMapping(FOURNISSEUR_ENDPOINT + "/{idFournisseur}")
    // Méthode pour trouver un fournisseur par son identifiant
    FournisseurDto findById(@PathVariable("idFournisseur") Integer id);

    // Endpoint pour récupérer tous les fournisseurs
    @GetMapping(FOURNISSEUR_ENDPOINT + "/all")
    // Méthode pour récupérer la liste de tous les fournisseurs
    List<FournisseurDto> findAll();

    // Endpoint pour supprimer un fournisseur
    @DeleteMapping(FOURNISSEUR_ENDPOINT + "/delete/{idFournisseur}")
    // Méthode pour supprimer un fournisseur par son identifiant
    void delete(@PathVariable("idFournisseur") Integer id);

}