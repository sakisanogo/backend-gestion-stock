// Déclaration du package pour les contrôleurs
package com.saki.gestion_stock.controller;

// Importation des classes nécessaires
import java.util.List;

// Importations de l'interface API et des DTOs
import com.saki.gestion_stock.controller.api.ClientApi;
import com.saki.gestion_stock.dto.ClientDto;
// Importation du service Client
import com.saki.gestion_stock.services.ClientService;
// Importations des classes Spring
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

// Configuration CORS pour autoriser les requêtes depuis le frontend Angular
@CrossOrigin(origins = "http://localhost:4200")
// Annotation indiquant que cette classe est un contrôleur REST
@RestController
public class ClientController implements ClientApi {

    // Déclaration du service Client
    private ClientService clientService;

    // Injection de dépendance via le constructeur
    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    // Implémentation de la méthode de sauvegarde de client
    @Override
    public ClientDto save(ClientDto dto) {
        // Délégation au service pour sauvegarder le client (create ou update)
        return clientService.save(dto);
    }

    // Implémentation de la méthode de recherche de client par ID
    @Override
    public ClientDto findById(Integer id) {
        // Délégation au service pour trouver le client par son ID
        return clientService.findById(id);
    }

    // Implémentation de la méthode pour récupérer tous les clients
    @Override
    public List<ClientDto> findAll() {
        // Délégation au service pour récupérer tous les clients
        return clientService.findAll();
    }

    // Implémentation de la méthode de suppression de client
    @Override
    public void delete(Integer id) {
        // Délégation au service pour supprimer le client
        clientService.delete(id);
    }
}