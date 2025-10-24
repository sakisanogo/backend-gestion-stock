package com.saki.gestion_stock.services.strategy;

import com.flickr4java.flickr.FlickrException;
import java.io.InputStream;

import com.saki.gestion_stock.dto.ArticleDto;
import com.saki.gestion_stock.exception.ErrorCodes;
import com.saki.gestion_stock.exception.InvalidOperationException;
import com.saki.gestion_stock.services.ArticleService;
import com.saki.gestion_stock.services.FlickrService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

// Implémentation de la stratégie pour sauvegarder les photos d'articles
// Utilise le pattern Strategy pour gérer différents types de sauvegarde de photos
@Service("articleStrategy") // Nom qualifié pour l'injection par type
@Slf4j
public class SaveArticlePhoto implements Strategy<ArticleDto> {

    private FlickrService flickrService;  // Service d'intégration avec Flickr
    private ArticleService articleService; // Service métier des articles

    @Autowired
    public SaveArticlePhoto(FlickrService flickrService, ArticleService articleService) {
        this.flickrService = flickrService;
        this.articleService = articleService;
    }

    // Implémentation de la stratégie pour sauvegarder une photo d'article
    @Override
    public ArticleDto savePhoto(Integer id, InputStream photo, String titre) throws FlickrException {
        // 1. Récupération de l'article existant
        ArticleDto article = articleService.findById(id);

        // 2. Sauvegarde de la photo sur Flickr et récupération de l'URL
        String urlPhoto = flickrService.savePhoto(photo, titre);

        // 3. Vérification que l'URL de la photo a bien été générée
        if (!StringUtils.hasLength(urlPhoto)) {
            throw new InvalidOperationException("Erreur lors de l'enregistrement de photo de l'article", ErrorCodes.UPDATE_PHOTO_EXCEPTION);
        }

        // 4. Mise à jour de l'article avec la nouvelle URL de photo
        article.setPhoto(urlPhoto);

        // 5. Sauvegarde de l'article mis à jour
        return articleService.save(article);
    }
}