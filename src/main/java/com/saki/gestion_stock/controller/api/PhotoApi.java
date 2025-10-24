// Déclaration du package pour les interfaces API
package com.saki.gestion_stock.controller.api;

// Importation des exceptions Flickr
import com.flickr4java.flickr.FlickrException;
// Importation de l'annotation Swagger
import io.swagger.annotations.Api;
import java.io.IOException;
// Importations des annotations Spring Web
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

// Importation de la constante pour la racine de l'application
import static com.saki.gestion_stock.utils.Constants.APP_ROOT;

// Annotation Swagger pour documenter cette interface comme API "photos"
@Api("photos")
public interface PhotoApi {

    // Endpoint pour sauvegarder une photo
    @PostMapping(APP_ROOT + "/save/{id}/{title}/{context}")
    // Méthode pour sauvegarder une photo avec ses métadonnées
    // context: le contexte d'utilisation (ex: article, utilisateur, etc.)
    // id: l'identifiant de l'entité associée à la photo
    // photo: le fichier image uploadé
    // title: le titre de la photo
    Object savePhoto(@PathVariable("context") String context,
                     @PathVariable("id") Integer id,
                     @RequestPart("file") MultipartFile photo,
                     @PathVariable("title") String title)
            throws IOException, FlickrException;  // Gestion des exceptions liées à l'upload et à Flickr

}