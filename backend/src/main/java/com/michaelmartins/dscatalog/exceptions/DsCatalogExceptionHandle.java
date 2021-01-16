package com.michaelmartins.dscatalog.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class DsCatalogExceptionHandle {

    @ExceptionHandler(ResourceEntityNotFoundException.class)
    public ResponseEntity<StandardError> handleEntityNotFound(ResourceEntityNotFoundException exception,
                                                              HttpServletRequest request) {
        HttpStatus notFound = HttpStatus.NOT_FOUND;
        StandardError error = StandardError.Builder.newBuilder()
                .status(notFound.value())
                .error("Resource not found")
                .message(exception.getMessage())
                .path(request.getServletPath())
                .build();
        return ResponseEntity.status(notFound).body(error);
    }

    @ExceptionHandler(DatabaseException.class)
    public ResponseEntity<StandardError> handleEntityNotFound(DatabaseException exception, HttpServletRequest request) {
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        StandardError error = StandardError.Builder.newBuilder()
                .status(badRequest.value())
                .error("Database Integrity")
                .message(exception.getMessage())
                .path(request.getServletPath())
                .build();
        return ResponseEntity.status(badRequest).body(error);
    }
}
