// Déclaration du package pour les contrôleurs
package com.saki.gestion_stock.controller;

// Importation des classes nécessaires
import java.util.List;

// Importations de l'interface API et des DTOs
import com.saki.gestion_stock.controller.api.VentesApi;
import com.saki.gestion_stock.dto.VentesDto;
// Importation du service Ventes
import com.saki.gestion_stock.services.VentesService;
// Importations des classes Spring
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

// Configuration CORS pour autoriser les requêtes depuis le frontend Angular
@CrossOrigin(origins = "http://localhost:4200")
// Annotation indiquant que cette classe est un contrôleur REST
@RestController
public class VentesController implements VentesApi {

    // Déclaration du service Ventes
    private VentesService ventesService;

    // Injection de dépendance via le constructeur
    @Autowired
    public VentesController(VentesService ventesService) {
        this.ventesService = ventesService;
    }

    // Implémentation de la méthode de sauvegarde de vente
    @Override
    public VentesDto save(VentesDto dto) {
        // Délégation au service pour sauvegarder la vente (create ou update)
        return ventesService.save(dto);
    }

    // Implémentation de la méthode de recherche de vente par ID
    @Override
    public VentesDto findById(Integer id) {
        // Délégation au service pour trouver une vente par son ID
        return ventesService.findById(id);
    }

    // Implémentation de la méthode de recherche de vente par code
    @Override
    public VentesDto findByCode(String code) {
        // Délégation au service pour trouver une vente par son code
        return ventesService.findByCode(code);
    }

    // Implémentation de la méthode pour récupérer toutes les ventes
    @Override
    public List<VentesDto> findAll() {
        // Délégation au service pour récupérer toutes les ventes
        return ventesService.findAll();
    }

    // Implémentation de la méthode de suppression de vente
    @Override
    public void delete(Integer id) {
        // Délégation au service pour supprimer une vente
        ventesService.delete(id);
    }

}