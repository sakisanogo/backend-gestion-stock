// Déclaration du package pour les contrôleurs
package com.saki.gestion_stock.controller;

// Importation des classes nécessaires
import java.util.List;

// Importations de l'interface API et des DTOs
import com.saki.gestion_stock.controller.api.CategoryApi;
import com.saki.gestion_stock.dto.CategoryDto;
// Importation du service Category
import com.saki.gestion_stock.services.CategoryService;
// Importations des classes Spring
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

// Configuration CORS pour autoriser les requêtes depuis le frontend Angular
@CrossOrigin(origins = "http://localhost:4200")
// Annotation indiquant que cette classe est un contrôleur REST
@RestController
public class CategoryController implements CategoryApi {

    // Déclaration du service Category
    private CategoryService categoryService;

    // Injection de dépendance via le constructeur
    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    // Implémentation de la méthode de sauvegarde de catégorie
    @Override
    public CategoryDto save(CategoryDto dto) {
        // Délégation au service pour sauvegarder la catégorie
        return categoryService.save(dto);
    }

    // Implémentation de la méthode de recherche de catégorie par ID
    @Override
    public CategoryDto findById(Integer idCategory) {
        // Délégation au service pour trouver la catégorie par son ID
        return categoryService.findById(idCategory);
    }

    // Implémentation de la méthode de recherche de catégorie par code
    @Override
    public CategoryDto findByCode(String codeCategory) {
        // Délégation au service pour trouver la catégorie par son code
        return categoryService.findByCode(codeCategory);
    }

    // Implémentation de la méthode pour récupérer toutes les catégories
    @Override
    public List<CategoryDto> findAll() {
        // Délégation au service pour récupérer toutes les catégories
        return categoryService.findAll();
    }

    // Implémentation de la méthode de suppression de catégorie
    @Override
    public void delete(Integer id) {
        // Délégation au service pour supprimer la catégorie
        categoryService.delete(id);
    }
}