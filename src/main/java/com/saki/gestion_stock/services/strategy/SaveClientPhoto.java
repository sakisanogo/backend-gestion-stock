package com.saki.gestion_stock.services.strategy;

import com.flickr4java.flickr.FlickrException;
import java.io.InputStream;

import com.saki.gestion_stock.dto.ClientDto;
import com.saki.gestion_stock.exception.ErrorCodes;
import com.saki.gestion_stock.exception.InvalidOperationException;
import com.saki.gestion_stock.services.ClientService;
import com.saki.gestion_stock.services.FlickrService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

// Implémentation de la stratégie pour sauvegarder les photos de clients
// Réutilise le pattern Strategy pour une gestion cohérente des photos across différentes entités
@Service("clientStrategy") // Nom qualifié "clientStrategy" pour différencier cette implémentation
@Slf4j
public class SaveClientPhoto implements Strategy<ClientDto> {

    private FlickrService flickrService;  // Service d'intégration avec Flickr pour le stockage photos
    private ClientService clientService;  // Service métier pour la gestion des clients

    @Autowired
    public SaveClientPhoto(FlickrService flickrService, ClientService clientService) {
        this.flickrService = flickrService;
        this.clientService = clientService;
    }

    // Implémentation de la stratégie pour sauvegarder une photo de client
    @Override
    public ClientDto savePhoto(Integer id, InputStream photo, String titre) throws FlickrException {
        // 1. Récupération du client existant par son ID
        ClientDto client = clientService.findById(id);

        // 2. Upload de la photo sur Flickr et récupération de l'URL générée
        String urlPhoto = flickrService.savePhoto(photo, titre);

        // 3. Validation que Flickr a bien retourné une URL valide
        if (!StringUtils.hasLength(urlPhoto)) {
            throw new InvalidOperationException("Erreur lors de l'enregistrement de photo du client", ErrorCodes.UPDATE_PHOTO_EXCEPTION);
        }

        // 4. Mise à jour du client avec la nouvelle URL de photo de profil
        client.setPhoto(urlPhoto);

        // 5. Sauvegarde du client mis à jour en base de données
        return clientService.save(client);
    }
}