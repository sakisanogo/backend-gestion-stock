package com.saki.gestion_stock.handlers;

import com.saki.gestion_stock.exception.EntityNotFoundException;
import com.saki.gestion_stock.exception.ErrorCodes;
import com.saki.gestion_stock.exception.InvalidEntityException;
import com.saki.gestion_stock.exception.InvalidOperationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Collections;
import org.springframework.security.authentication.BadCredentialsException;

// Classe globale de gestion des exceptions pour l'application Spring Boot
// @RestControllerAdvice intercepte les exceptions levées dans tous les contrôleurs
@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    // Gestion des exceptions lorsque une entité n'est pas trouvée en base de données
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorDto> handleException(EntityNotFoundException exception, WebRequest webRequest) {

        final HttpStatus notFound = HttpStatus.NOT_FOUND;
        // Construction de l'objet ErrorDto avec les détails de l'erreur
        final ErrorDto errorDto = ErrorDto.builder()
                .code(exception.getErrorCode())  // Code d'erreur métier spécifique
                .httpCode(notFound.value())      // Code HTTP 404
                .message(exception.getMessage()) // Message d'erreur descriptif
                .build();

        // Retourne la réponse HTTP avec le DTO d'erreur et le statut 404
        return new ResponseEntity<>(errorDto, notFound);
    }

    // Gestion des exceptions pour les opérations non valides (règles métier)
    @ExceptionHandler(InvalidOperationException.class)
    public ResponseEntity<ErrorDto> handleException(InvalidOperationException exception, WebRequest webRequest) {

        final HttpStatus notFound = HttpStatus.BAD_REQUEST;
        final ErrorDto errorDto = ErrorDto.builder()
                .code(exception.getErrorCode())  // Code d'erreur métier
                .httpCode(notFound.value())      // Code HTTP 400
                .message(exception.getMessage()) // Message d'erreur
                .build();

        return new ResponseEntity<>(errorDto, notFound);
    }

    // Gestion des exceptions pour les entités non valides (erreurs de validation)
    @ExceptionHandler(InvalidEntityException.class)
    public ResponseEntity<ErrorDto> handleException(InvalidEntityException exception, WebRequest webRequest) {
        final HttpStatus badRequest = HttpStatus.BAD_REQUEST;

        final ErrorDto errorDto = ErrorDto.builder()
                .code(exception.getErrorCode())  // Code d'erreur métier
                .httpCode(badRequest.value())    // Code HTTP 400
                .message(exception.getMessage()) // Message d'erreur général
                .errors(exception.getErrors())   // Liste détaillée des erreurs de validation
                .build();

        return new ResponseEntity<>(errorDto, badRequest);
    }

    // Gestion des exceptions d'authentification (identifiants incorrects)
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorDto> handleException(BadCredentialsException exception, WebRequest webRequest) {
        final HttpStatus badRequest = HttpStatus.BAD_REQUEST;

        final ErrorDto errorDto = ErrorDto.builder()
                .code(ErrorCodes.BAD_CREDENTIALS)        // Code d'erreur pour mauvais identifiants
                .httpCode(badRequest.value())            // Code HTTP 400
                .message(exception.getMessage())         // Message d'erreur Spring Security
                .errors(Collections.singletonList("Login et / ou mot de passe incorrecte")) // Message user-friendly
                .build();

        return new ResponseEntity<>(errorDto, badRequest);
    }

}