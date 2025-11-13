package com.giustizia.mediazionecivile.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import jakarta.servlet.http.HttpServletResponse;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<String> handleUsernameNotFoundException(UsernameNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNAUTHORIZED);
    }
    
    // CATTURA ERRORE DI MANCATI REQUISITI PER ACCEDERE ALL'API
    @ExceptionHandler(InsufficientAuthenticationException.class)
    public ResponseEntity<String> InsufficientAuthenticationException(InsufficientAuthenticationException ex) {
    	return ResponseEntity.status(HttpStatus.FORBIDDEN).body("L'utente non ha l'autorizzazione alla visualizzazione della pagina");
    }
    
    // CATTURA GENERALE DI QUALSIASI ERRORE LANCIATO DAL CONTROLLER
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGenericException(Exception ex) {
		String message = (ex.getMessage() != null && ex.getMessage().contains("-ErrorInfo"))
				? ex.getMessage().replace("-ErrorInfo", "")
				: "Si è verificato un errore non previsto";
		
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);    	
    }
    
}
