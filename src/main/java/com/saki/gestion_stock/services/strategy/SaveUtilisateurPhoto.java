package com.saki.gestion_stock.services.strategy;

import com.flickr4java.flickr.FlickrException;
import java.io.InputStream;

import com.saki.gestion_stock.dto.UtilisateurDto;
import com.saki.gestion_stock.exception.ErrorCodes;
import com.saki.gestion_stock.exception.InvalidOperationException;
import com.saki.gestion_stock.services.FlickrService;
import com.saki.gestion_stock.services.UtilisateurService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

// Implémentation de la stratégie pour sauvegarder les photos de profil des utilisateurs
// Cinquième implémentation du pattern Strategy, étendant la couverture aux utilisateurs du système
@Service("utilisateurStrategy") // Nom qualifié "utilisateurStrategy" pour l'injection spécifique
@Slf4j
public class SaveUtilisateurPhoto implements Strategy<UtilisateurDto> {

    private FlickrService flickrService;        // Service de stockage externe des images
    private UtilisateurService utilisateurService; // Service métier pour la gestion des utilisateurs

    @Autowired
    public SaveUtilisateurPhoto(FlickrService flickrService, UtilisateurService utilisateurService) {
        this.flickrService = flickrService;
        this.utilisateurService = utilisateurService;
    }

    // Implémentation de la stratégie pour sauvegarder une photo de profil utilisateur
    @Override
    public UtilisateurDto savePhoto(Integer id, InputStream photo, String titre) throws FlickrException {
        // 1. Récupération de l'utilisateur existant par son ID
        UtilisateurDto utilisateur = utilisateurService.findById(id);

        // 2. Upload de la photo de profil sur Flickr et récupération de l'URL
        String urlPhoto = flickrService.savePhoto(photo, titre);

        // 3. Vérification que l'upload a réussi et qu'une URL valide a été générée
        if (!StringUtils.hasLength(urlPhoto)) {
            throw new InvalidOperationException("Erreur lors de l'enregistrement de photo de l'utilisateur", ErrorCodes.UPDATE_PHOTO_EXCEPTION);
        }

        // 4. Mise à jour de l'utilisateur avec la nouvelle URL de photo de profil
        utilisateur.setPhoto(urlPhoto);

        // 5. Sauvegarde de l'utilisateur mis à jour en base de données
        return utilisateurService.save(utilisateur);
    }
}