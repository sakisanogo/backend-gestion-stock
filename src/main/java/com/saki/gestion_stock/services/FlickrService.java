package com.saki.gestion_stock.services;

import com.flickr4java.flickr.FlickrException;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ExecutionException;

// Interface de service pour l'intégration avec l'API Flickr
// Définit le contrat pour le stockage externe des photos/images
public interface FlickrService {

    /**
     * Sauvegarde une photo sur le service Flickr et retourne son URL d'accès
     *
     * @param photo Flux d'entrée contenant les données binaires de l'image
     * @param title Titre ou description de la photo pour l'identification
     * @return String URL publique de la photo sauvegardée sur Flickr
     * @throws FlickrException Si une erreur survient lors de l'API Flickr
     * @throws IOException Si une erreur I/O survient lors de la lecture du flux
     * @throws ExecutionException Si une erreur survient dans l'exécution asynchrone
     * @throws InterruptedException Si le thread est interrompu pendant l'opération
     */
    String savePhoto(InputStream photo, String title);

}