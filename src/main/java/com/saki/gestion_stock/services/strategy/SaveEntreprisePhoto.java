package com.saki.gestion_stock.services.strategy;

import com.flickr4java.flickr.FlickrException;
import java.io.InputStream;

import com.saki.gestion_stock.dto.EntrepriseDto;
import com.saki.gestion_stock.exception.ErrorCodes;
import com.saki.gestion_stock.exception.InvalidOperationException;
import com.saki.gestion_stock.services.EntrepriseService;
import com.saki.gestion_stock.services.FlickrService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

// Implémentation de la stratégie pour sauvegarder les logos/photos d'entreprises
// Troisième implémentation du pattern Strategy pour la gestion cohérente des photos
@Service("entrepriseStrategy") // Nom qualifié "entrepriseStrategy" pour l'injection spécifique
@Slf4j
public class SaveEntreprisePhoto implements Strategy<EntrepriseDto> {

    private FlickrService flickrService;      // Service de stockage externe des photos
    private EntrepriseService entrepriseService; // Service métier pour la gestion des entreprises

    @Autowired
    public SaveEntreprisePhoto(FlickrService flickrService, EntrepriseService entrepriseService) {
        this.flickrService = flickrService;
        this.entrepriseService = entrepriseService;
    }

    // Implémentation de la stratégie pour sauvegarder un logo/photo d'entreprise
    @Override
    public EntrepriseDto savePhoto(Integer id, InputStream photo, String titre) throws FlickrException {
        // 1. Récupération de l'entreprise existante par son ID
        EntrepriseDto entreprise = entrepriseService.findById(id);

        // 2. Upload du logo/photo sur Flickr et récupération de l'URL
        String urlPhoto = flickrService.savePhoto(photo, titre);

        // 3. Vérification que l'upload a réussi et qu'une URL a été générée
        if (!StringUtils.hasLength(urlPhoto)) {
            throw new InvalidOperationException("Erreur lors de l'enregistrement de photo de l'entreprise", ErrorCodes.UPDATE_PHOTO_EXCEPTION);
        }

        // 4. Mise à jour de l'entreprise avec la nouvelle URL du logo
        entreprise.setPhoto(urlPhoto);

        // 5. Sauvegarde de l'entreprise mise à jour en base de données
        return entrepriseService.save(entreprise);
    }
}