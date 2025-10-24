// Déclaration du package pour les exceptions personnalisées
package com.saki.gestion_stock.exception;

// Importation de l'annotation Lombok pour générer le getter
import lombok.Getter;

// Classe d'exception personnalisée pour les entités non trouvées
// Étend RuntimeException pour être une exception unchecked
public class EntityNotFoundException extends RuntimeException {

    // Code d'erreur personnalisé pour identifier le type d'erreur
    @Getter
    private ErrorCodes errorCode;

    // Constructeur avec uniquement le message d'erreur
    public EntityNotFoundException(String message) {
        super(message);
    }

    // Constructeur avec message et cause (exception d'origine)
    public EntityNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    // Constructeur avec message et code d'erreur personnalisé
    public EntityNotFoundException(String message, ErrorCodes errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    // Constructeur complet avec message, code d'erreur et cause
    public EntityNotFoundException(String message, ErrorCodes errorCode, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }
}