package com.saki.gestion_stock.services.strategy;

import com.flickr4java.flickr.FlickrException;
import java.io.InputStream;

// Interface Strategy définissant le contrat pour la sauvegarde de photos
// Pattern Strategy permettant différentes implémentations selon le type d'entité
// <T> Type générique représentant le DTO de l'entité (ArticleDto, ClientDto, etc.)
public interface Strategy<T> {

    /**
     * Contrat pour sauvegarder une photo associée à une entité
     *
     * @param id Identifiant de l'entité à laquelle associer la photo
     * @param photo Flux d'entrée contenant les données binaires de la photo
     * @param titre Titre ou description de la photo pour le stockage
     * @return T DTO de l'entité mise à jour avec l'URL de la photo
     * @throws FlickrException Si une erreur survient lors de l'upload vers Flickr
     */
    T savePhoto(Integer id, InputStream photo, String titre) throws FlickrException;

}