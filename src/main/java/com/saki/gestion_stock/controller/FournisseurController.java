// Déclaration du package pour les contrôleurs
package com.saki.gestion_stock.controller;

// Importation des classes nécessaires
import java.util.List;

// Importations de l'interface API et des DTOs
import com.saki.gestion_stock.controller.api.FournisseurApi;
import com.saki.gestion_stock.dto.FournisseurDto;
// Importation du service Fournisseur
import com.saki.gestion_stock.services.FournisseurService;
// Importations des classes Spring
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

// Configuration CORS pour autoriser les requêtes depuis le frontend Angular
@CrossOrigin(origins = "http://localhost:4200")
// Annotation indiquant que cette classe est un contrôleur REST
@RestController
public class FournisseurController implements FournisseurApi {

    // Déclaration du service Fournisseur
    private FournisseurService fournisseurService;

    // Injection de dépendance via le constructeur
    @Autowired
    public FournisseurController(FournisseurService fournisseurService) {
        this.fournisseurService = fournisseurService;
    }

    // Implémentation de la méthode de sauvegarde de fournisseur
    @Override
    public FournisseurDto save(FournisseurDto dto) {
        // Délégation au service pour sauvegarder le fournisseur (create ou update)
        return fournisseurService.save(dto);
    }

    // Implémentation de la méthode de recherche de fournisseur par ID
    @Override
    public FournisseurDto findById(Integer id) {
        // Délégation au service pour trouver un fournisseur par son ID
        return fournisseurService.findById(id);
    }

    // Implémentation de la méthode pour récupérer tous les fournisseurs
    @Override
    public List<FournisseurDto> findAll() {
        // Délégation au service pour récupérer tous les fournisseurs
        return fournisseurService.findAll();
    }

    // Implémentation de la méthode de suppression de fournisseur
    @Override
    public void delete(Integer id) {
        // Délégation au service pour supprimer un fournisseur
        fournisseurService.delete(id);
    }
}