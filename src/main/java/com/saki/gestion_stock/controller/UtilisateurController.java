// Déclaration du package pour les contrôleurs
package com.saki.gestion_stock.controller;

// Importation des classes nécessaires
import java.util.List;

// Importations de l'interface API et des DTOs
import com.saki.gestion_stock.controller.api.UtilisateurApi;
import com.saki.gestion_stock.dto.ChangerMotDePasseUtilisateurDto;
import com.saki.gestion_stock.dto.UtilisateurDto;
// Importation du service Utilisateur
import com.saki.gestion_stock.services.UtilisateurService;
// Importations des classes Spring
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

// Configuration CORS pour autoriser les requêtes depuis le frontend Angular
@CrossOrigin(origins = "http://localhost:4200")
// Annotation indiquant que cette classe est un contrôleur REST
@RestController
public class UtilisateurController implements UtilisateurApi {

    // Déclaration du service Utilisateur
    private UtilisateurService utilisateurService;

    // Injection de dépendance via le constructeur
    @Autowired
    public UtilisateurController(UtilisateurService utilisateurService) {
        this.utilisateurService = utilisateurService;
    }

    // Implémentation de la méthode de sauvegarde d'utilisateur
    @Override
    public UtilisateurDto save(UtilisateurDto dto) {
        // Délégation au service pour sauvegarder l'utilisateur (create ou update)
        return utilisateurService.save(dto);
    }

    // Implémentation de la méthode spécifique pour changer le mot de passe
    @Override
    public UtilisateurDto changerMotDePasse(ChangerMotDePasseUtilisateurDto dto) {
        // Délégation au service pour modifier le mot de passe de l'utilisateur
        return utilisateurService.changerMotDePasse(dto);
    }

    // Implémentation de la méthode de recherche d'utilisateur par ID
    @Override
    public UtilisateurDto findById(Integer id) {
        // Délégation au service pour trouver un utilisateur par son ID
        return utilisateurService.findById(id);
    }

    // Implémentation de la méthode de recherche d'utilisateur par email
    @Override
    public UtilisateurDto findByEmail(String email) {
        // Délégation au service pour trouver un utilisateur par son email
        return utilisateurService.findByEmail(email);
    }

    // Implémentation de la méthode pour récupérer tous les utilisateurs
    @Override
    public List<UtilisateurDto> findAll() {
        // Délégation au service pour récupérer tous les utilisateurs
        return utilisateurService.findAll();
    }

    // Implémentation de la méthode de suppression d'utilisateur
    @Override
    public void delete(Integer id) {
        // Délégation au service pour supprimer un utilisateur
        utilisateurService.delete(id);
    }
}