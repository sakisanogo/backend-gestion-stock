// Déclaration du package pour les handlers (gestionnaires)
package com.saki.gestion_stock.handlers;

// Importations des classes nécessaires
import java.util.ArrayList;
import java.util.List;

// Importation des codes d'erreur personnalisés
import com.saki.gestion_stock.exception.ErrorCodes;
// Importations des annotations Lombok
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// Annotation Lombok pour générer les getters
@Getter
// Annotation Lombok pour générer les setters
@Setter
// Annotation Lombok pour générer un constructeur avec tous les arguments
@AllArgsConstructor
// Annotation Lombok pour générer un constructeur sans arguments
@NoArgsConstructor
// Annotation Lombok pour générer un builder pattern pour la classe
@Builder
public class ErrorDto {

    // Code HTTP de la réponse (ex: 400, 404, 500)
    private Integer httpCode;

    // Code d'erreur métier personnalisé de l'application
    private ErrorCodes code;

    // Message d'erreur général et lisible
    private String message;

    // Liste des erreurs de validation détaillées (pour les validations multiples)
    private List<String> errors = new ArrayList<>();

}