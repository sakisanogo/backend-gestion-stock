// Déclaration du package pour les contrôleurs
package com.saki.gestion_stock.controller;

// Importation des exceptions Flickr
import com.flickr4java.flickr.FlickrException;
// Importation des classes d'entrée/sortie
import java.io.IOException;
import java.io.InputStream;

// Importations de l'interface API et des services
import com.saki.gestion_stock.controller.api.PhotoApi;
import com.saki.gestion_stock.services.strategy.StrategyPhotoContext;
// Importations des DTOs et exceptions (non utilisés dans ce contrôleur mais importés)
import com.saki.gestion_stock.dto.ArticleDto;
import com.saki.gestion_stock.exception.ErrorCodes;
import com.saki.gestion_stock.exception.InvalidOperationException;
import com.saki.gestion_stock.services.ArticleService;
import com.saki.gestion_stock.services.FlickrService;
import com.saki.gestion_stock.services.strategy.Strategy;
// Importations des classes Spring
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

// Configuration CORS pour autoriser les requêtes depuis le frontend Angular
@CrossOrigin(origins = "http://localhost:4200")
// Annotation indiquant que cette classe est un contrôleur REST
@RestController
public class PhotoController implements PhotoApi {

    // Déclaration du contexte de stratégie pour la gestion des photos
    private StrategyPhotoContext strategyPhotoContext;

    // Injection de dépendance via le constructeur
    @Autowired
    public PhotoController(StrategyPhotoContext strategyPhotoContext) {
        this.strategyPhotoContext = strategyPhotoContext;
    }

    // Implémentation de la méthode de sauvegarde de photo
    @Override
    public Object savePhoto(String context, Integer id, MultipartFile photo, String title) throws IOException, FlickrException {
        // Délégation au contexte de stratégie pour sauvegarder la photo
        // Conversion du MultipartFile en InputStream pour le traitement
        return strategyPhotoContext.savePhoto(context, id, photo.getInputStream(), title);
    }
}