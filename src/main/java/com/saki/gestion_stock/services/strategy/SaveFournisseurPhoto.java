package com.saki.gestion_stock.services.strategy;

import com.flickr4java.flickr.FlickrException;
import java.io.InputStream;

import com.saki.gestion_stock.dto.FournisseurDto;
import com.saki.gestion_stock.exception.ErrorCodes;
import com.saki.gestion_stock.exception.InvalidOperationException;
import com.saki.gestion_stock.services.FlickrService;
import com.saki.gestion_stock.services.FournisseurService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

// Implémentation de la stratégie pour sauvegarder les photos de fournisseurs
// Quatrième implémentation du pattern Strategy, complétant la couverture de toutes les entités métier
@Service("fournisseurStrategy") // Nom qualifié "fournisseurStrategy" pour l'injection ciblée
@Slf4j
public class SaveFournisseurPhoto implements Strategy<FournisseurDto> {

    private FlickrService flickrService;        // Service externe de stockage d'images
    private FournisseurService fournisseurService; // Service métier pour la gestion des fournisseurs

    @Autowired
    public SaveFournisseurPhoto(FlickrService flickrService, FournisseurService fournisseurService) {
        this.flickrService = flickrService;
        this.fournisseurService = fournisseurService;
    }

    // Implémentation de la stratégie pour sauvegarder une photo de fournisseur
    @Override
    public FournisseurDto savePhoto(Integer id, InputStream photo, String titre) throws FlickrException {
        // 1. Récupération du fournisseur existant par son ID
        FournisseurDto fournisseur = fournisseurService.findById(id);

        // 2. Upload de la photo sur Flickr et récupération de l'URL générée
        String urlPhoto = flickrService.savePhoto(photo, titre);

        // 3. Validation de la réussite de l'upload et de la génération d'URL
        if (!StringUtils.hasLength(urlPhoto)) {
            throw new InvalidOperationException("Erreur lors de l'enregistrement de photo du fournisseur", ErrorCodes.UPDATE_PHOTO_EXCEPTION);
        }

        // 4. Mise à jour du fournisseur avec la nouvelle URL de photo
        fournisseur.setPhoto(urlPhoto);

        // 5. Sauvegarde du fournisseur mis à jour en base de données
        return fournisseurService.save(fournisseur);
    }
}