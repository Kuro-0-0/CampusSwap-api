package com.salesianostriana.dam.campusswap.errores;


import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.java.Log;
import org.jspecify.annotations.Nullable;
import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.net.URI;
import java.util.NoSuchElementException;

@Log
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(NoSuchElementException.class)
    public ProblemDetail handleNoSuchElementException(NoSuchElementException ex, HttpServletRequest request) {
        ProblemDetail pd = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());

        log.warning(ex.getMessage());

        pd.setTitle("Recurso no encontrado");
        pd.setInstance(URI.create(request.getRequestURI()));

        return pd;
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ProblemDetail handleIllegalArgumentException(IllegalArgumentException ex, HttpServletRequest request) {
        ProblemDetail pd = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());

        log.warning(ex.getMessage());

        pd.setTitle("Argumento no válido");
        pd.setInstance(URI.create(request.getRequestURI()));

        return pd;
    }

    @ExceptionHandler(IllegalStateException.class)
    public ProblemDetail handleIllegalStateException(IllegalStateException ex, HttpServletRequest request) {
        ProblemDetail pd = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());

        log.warning(ex.getMessage());

        pd.setTitle("Estado no válido");
        pd.setInstance(URI.create(request.getRequestURI()));

        return pd;
    }


    @Override
    protected @Nullable ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        ProblemDetail pb = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "Hay errores de validación en la petición");
        pb.setType(URI.create("about:blank"));
        pb.setTitle("Error de validación");

        pb.setProperty("invalid-params", ex.getAllErrors().stream()
                .map(ApiValidationSubError::from)
                .toList());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(pb);
    }

}
