package com.atelier.exception;

import com.atelier.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ReglesMetierException.class)
    public ResponseEntity<ErrorResponse> handleReglesMetier(ReglesMetierException ex) {
        return construireReponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(TransitionInvalideException.class)
    public ResponseEntity<ErrorResponse> handleTransitionInvalide(TransitionInvalideException ex) {
        return construireReponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(AccesRefuseException.class)
    public ResponseEntity<ErrorResponse> handleAccesRefuse(AccesRefuseException ex) {
        return construireReponse(HttpStatus.FORBIDDEN, ex.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgument(IllegalArgumentException ex) {
        return construireReponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenerique(Exception ex) {
        return construireReponse(HttpStatus.INTERNAL_SERVER_ERROR, "Erreur interne : " + ex.getMessage());
    }

    private ResponseEntity<ErrorResponse> construireReponse(HttpStatus statut, String message) {
        ErrorResponse erreur = new ErrorResponse(
                LocalDateTime.now(),
                statut.value(),
                statut.getReasonPhrase(),
                message
        );
        return ResponseEntity.status(statut).body(erreur);
    }
}