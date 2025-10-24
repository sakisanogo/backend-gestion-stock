// Déclaration du package pour les contrôleurs
package com.saki.gestion_stock.controller;

// Importation des classes nécessaires
import java.util.List;

// Importations de l'interface API et des DTOs
import com.saki.gestion_stock.controller.api.EntrepriseApi;
import com.saki.gestion_stock.dto.EntrepriseDto;
// Importation du service Entreprise
import com.saki.gestion_stock.services.EntrepriseService;
// Importations des annotations Lombok pour le logging
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
// Importations des classes Spring
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

// Configuration CORS pour autoriser les requêtes depuis le frontend Angular
@CrossOrigin(origins = "http://localhost:4200")
// Annotation Lombok pour générer automatiquement un logger SLF4J
@Slf4j
// Annotation indiquant que cette classe est un contrôleur REST
@RestController
public class EntrepriseController implements EntrepriseApi {

    // Déclaration du service Entreprise
    private EntrepriseService entrepriseService;

    // Injection de dépendance via le constructeur
    @Autowired
    public EntrepriseController(EntrepriseService entrepriseService) {
        this.entrepriseService = entrepriseService;
    }

    // Implémentation de la méthode de sauvegarde d'entreprise
    @Override
    public EntrepriseDto save(EntrepriseDto dto) {
        // Log de débogage simple pour tracer l'entrée dans la méthode
        System.out.print("Enter =========================");

        // Délégation au service pour sauvegarder l'entreprise
        return entrepriseService.save(dto);
    }

    // Implémentation de la méthode de recherche d'entreprise par ID
    @Override
    public EntrepriseDto findById(Integer id) {
        // Délégation au service pour trouver une entreprise par son ID
        return entrepriseService.findById(id);
    }

    // Implémentation de la méthode pour récupérer toutes les entreprises
    @Override
    public List<EntrepriseDto> findAll() {
        // Délégation au service pour récupérer toutes les entreprises
        return entrepriseService.findAll();
    }

    // Implémentation de la méthode de suppression d'entreprise
    @Override
    public void delete(Integer id) {
        // Délégation au service pour supprimer une entreprise
        entrepriseService.delete(id);
    }
}