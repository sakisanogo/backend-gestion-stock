// Déclaration du package pour les interfaces API
package com.saki.gestion_stock.controller.api;

// Importation du DTO Client
import com.saki.gestion_stock.dto.ClientDto;
// Importation de l'annotation Swagger
import io.swagger.annotations.Api;
import java.util.List;
// Importations des classes Spring
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

// Importation de la constante pour la racine de l'application
import static com.saki.gestion_stock.utils.Constants.APP_ROOT;

// Annotation Swagger pour documenter cette interface comme API "clients"
@Api("clients")
public interface ClientApi {

    // Endpoint pour créer ou modifier un client
    @PostMapping(value = APP_ROOT + "/clients/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    // Méthode pour sauvegarder un client (create ou update)
    ClientDto save(@RequestBody ClientDto dto);

    // Endpoint pour rechercher un client par son ID
    @GetMapping(value = APP_ROOT + "/clients/{idClient}", produces = MediaType.APPLICATION_JSON_VALUE)
    // Méthode pour trouver un client par son identifiant
    ClientDto findById(@PathVariable("idClient") Integer id);

    // Endpoint pour récupérer tous les clients
    @GetMapping(value = APP_ROOT + "/clients/all", produces = MediaType.APPLICATION_JSON_VALUE)
    // Méthode pour récupérer la liste de tous les clients
    List<ClientDto> findAll();

    // Endpoint pour supprimer un client
    @DeleteMapping(value = APP_ROOT + "/clients/delete/{idClient}")
    // Méthode pour supprimer un client par son identifiant
    void delete(@PathVariable("idClient") Integer id);

}