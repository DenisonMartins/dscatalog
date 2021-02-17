package com.michaelmartins.dscatalog.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class DsCatalogExceptionHandle {

    @ExceptionHandler(ResourceEntityNotFoundException.class)
    public ResponseEntity<StandardError> handleEntityNotFound(ResourceEntityNotFoundException exception,
                                                              HttpServletRequest request) {
        HttpStatus notFound = HttpStatus.NOT_FOUND;
        StandardError error = new StandardError(notFound.value(),
                                          "Resource not found",
                                                exception.getMessage(),
                                                request.getServletPath());
        return ResponseEntity.status(notFound).body(error);
    }

    @ExceptionHandler(DatabaseException.class)
    public ResponseEntity<StandardError> database(DatabaseException exception, HttpServletRequest request) {
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        StandardError error = new StandardError(badRequest.value(),
                                          "Database Integrity",
                                                exception.getMessage(),
                                                request.getServletPath());
        return ResponseEntity.status(badRequest).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationError> validation(MethodArgumentNotValidException exception, HttpServletRequest request) {
        HttpStatus notFound = HttpStatus.UNPROCESSABLE_ENTITY;

        ValidationError error = new ValidationError();
        error.setStatus(notFound.value());
        error.setError("Validation Exception");
        error.setMessage(exception.getMessage());
        error.setPath(request.getServletPath());

        exception.getBindingResult().getFieldErrors()
                .forEach(fieldError -> error.addError(fieldError.getField(), fieldError.getDefaultMessage()));

        return ResponseEntity.status(notFound).body(error);
    }
}
