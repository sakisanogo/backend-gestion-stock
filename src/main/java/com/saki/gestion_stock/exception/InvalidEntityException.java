// Déclaration du package pour les exceptions personnalisées
package com.saki.gestion_stock.exception;

// Importations des classes nécessaires
import java.util.List;
// Importation de l'annotation Lombok pour générer les getters
import lombok.Getter;

// Classe d'exception personnalisée pour les entités invalides
// Étend RuntimeException pour être une exception unchecked
public class InvalidEntityException extends RuntimeException {

    // Code d'erreur personnalisé pour identifier le type d'erreur
    @Getter
    private ErrorCodes errorCode;

    // Liste des erreurs de validation détaillées
    @Getter
    private List<String> errors;

    // Constructeur avec uniquement le message d'erreur
    public InvalidEntityException(String message) {
        super(message);
    }

    // Constructeur avec message et cause (exception d'origine)
    public InvalidEntityException(String message, Throwable cause) {
        super(message, cause);
    }

    // Constructeur avec message et code d'erreur personnalisé
    public InvalidEntityException(String message, ErrorCodes errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    // Constructeur avec message, code d'erreur et liste d'erreurs détaillées
    public InvalidEntityException(String message, ErrorCodes errorCode, List<String> errors) {
        super(message);
        this.errorCode = errorCode;
        this.errors = errors;
    }

    // Constructeur avec message, code d'erreur et cause
    public InvalidEntityException(String message, ErrorCodes errorCode, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }
}