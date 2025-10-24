package com.saki.gestion_stock.services.Impl;

import com.flickr4java.flickr.Flickr;
import com.flickr4java.flickr.FlickrException;
import com.flickr4java.flickr.REST;
import com.flickr4java.flickr.RequestContext;
import com.flickr4java.flickr.auth.Auth;
import com.flickr4java.flickr.auth.Permission;
import com.flickr4java.flickr.uploader.UploadMetaData;
import com.saki.gestion_stock.services.FlickrService;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ExecutionException;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

// Service d'implémentation pour l'intégration avec l'API Flickr
// Permet de sauvegarder des photos sur Flickr et de récupérer leurs URLs
@Service
@Slf4j
public class FlickrServiceImpl implements FlickrService {

    // Injection des clés d'API Flickr depuis le fichier de configuration application.properties
    @Value("${flickr.apiKey}")
    private String apiKey;          // Clé API publique de l'application Flickr

    @Value("${flickr.apiSecret}")
    private String apiSecret;       // Secret API privé de l'application Flickr

    @Value("${flickr.appKey}")
    private String appKey;          // Token d'accès de l'utilisateur (OAuth)

    @Value("${flickr.appSecret}")
    private String appSecret;       // Secret du token d'accès utilisateur

    private Flickr flickr;          // Client principal de l'API Flickr

    // Sauvegarde d'une photo sur Flickr et retourne l'URL de la photo
    // @SneakyThrows permet de ne pas déclarer les exceptions checked dans la signature
    @Override
    @SneakyThrows
    public String savePhoto(InputStream photo, String title) {
        // Établir la connexion avec l'API Flickr
        connect();

        // Configuration des métadonnées de l'upload
        UploadMetaData uploadMetaData = new UploadMetaData();
        uploadMetaData.setTitle(title);  // Définit le titre de la photo

        // Upload de la photo sur Flickr et récupération de l'ID de la photo
        String photoId = flickr.getUploader().upload(photo, uploadMetaData);

        // Récupération de l'URL de la photo en taille moyenne (640px)
        return flickr.getPhotosInterface().getPhoto(photoId).getMedium640Url();
    }

    // Établit la connexion avec l'API Flickr en utilisant l'authentification OAuth
    private void connect() throws InterruptedException, ExecutionException, IOException, FlickrException {
        // Initialisation du client Flickr avec les clés API
        flickr = new Flickr(apiKey, apiSecret, new REST());

        // Configuration de l'authentification OAuth
        Auth auth = new Auth();
        auth.setPermission(Permission.READ);        // Permission en lecture seule
        auth.setToken(appKey);                      // Token d'accès OAuth
        auth.setTokenSecret(appSecret);             // Secret du token OAuth

        // Configuration du contexte de requête avec l'authentification
        RequestContext requestContext = RequestContext.getRequestContext();
        requestContext.setAuth(auth);
        flickr.setAuth(auth);

        log.debug("Connexion à Flickr établie avec succès");
    }
}