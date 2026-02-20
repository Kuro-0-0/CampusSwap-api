package com.salesianostriana.dam.campusswap.errores;

import com.salesianostriana.dam.campusswap.errores.custom.NonExistentImageException;
import com.salesianostriana.dam.campusswap.errores.custom.NotOwnedException;
import io.jsonwebtoken.JwtException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.java.Log;
import org.springframework.security.access.AccessDeniedException;
import org.apache.coyote.BadRequestException;
import org.springframework.http.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.ErrorResponse;
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

    @ExceptionHandler(NotOwnedException.class)
    public ProblemDetail handleNotOwnedException(NotOwnedException ex, HttpServletRequest request) {
        ProblemDetail pd = ProblemDetail.forStatusAndDetail(HttpStatus.FORBIDDEN, ex.getMessage());

        log.warning(ex.getMessage());

        pd.setTitle("Recurso no perteneciente al usuario");
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

    @ExceptionHandler(AccessDeniedException.class)
    public ProblemDetail handleAccessDeniedException(AccessDeniedException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.FORBIDDEN,
                ex.getMessage());

        return problemDetail;
    }
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        ProblemDetail pb = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "Fallo de validacion en uno o mas argumentos");
        pb.setType(URI.create("about:blank"));
        pb.setTitle("Error de validación");

        pb.setProperty("invalid-params", ex.getAllErrors().stream()
                .map(ValidacionDeApiSubError::from)
                .toList());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(pb);
    }

    @ExceptionHandler(IllegalStateException.class)
    public ProblemDetail handleIllegalStateException(IllegalStateException ex, HttpServletRequest request) {
        ProblemDetail pd = ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, ex.getMessage());

        log.warning(ex.getMessage());

        pd.setTitle("Estado no válido");
        pd.setInstance(URI.create(request.getRequestURI()));

        return pd;
    }

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleGenericException(Exception ex, HttpServletRequest request) {
        ProblemDetail pd = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, "Ha ocurrido un error inesperado");
        log.warning("Error class: " + ex.getClass() + ", Error message: " + ex.getMessage());
        pd.setTitle("Error inesperado.");
        pd.setInstance(URI.create(request.getRequestURI()));
        return pd;
    }

    @ExceptionHandler(BadRequestException.class)
    public ProblemDetail handleBadRequestException(BadRequestException ex, HttpServletRequest request) {
        ProblemDetail pd = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());
        log.warning("Error class: " + ex.getClass() + ", Error message: " + ex.getMessage());
        pd.setTitle("Error de solicitud.");
        pd.setInstance(URI.create(request.getRequestURI()));
        return pd;
    }

    @ExceptionHandler(JwtException.class)
    public ProblemDetail handleJwtException(JwtException ex, HttpServletRequest request) {
        ProblemDetail pd = ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED, ex.getMessage());
        log.warning("Error class: " + ex.getClass() + ", Error message: " + ex.getMessage());
        pd.setTitle("Error de autenticación.");
        pd.setInstance(URI.create(request.getRequestURI()));
        return pd;
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ProblemDetail handleEntityNotFoundException(EntityNotFoundException ex, HttpServletRequest request) {
        ProblemDetail pd = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
        log.warning("Error class: " + ex.getClass() + ", Error message: " + ex.getMessage());
        pd.setTitle("Entidad no encontrada.");
        pd.setInstance(URI.create(request.getRequestURI()));
        return pd;
    }

    @ExceptionHandler(AuthenticationException.class)
    public ErrorResponse handleAuthenticationException(AuthenticationException ex) {

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED,
                ex.getMessage());


        ErrorResponse response = ErrorResponse.builder(ex, problemDetail)
                .header("WWW-Authenticate", "Bearer")
                .build();


        return response;
    }

    @ExceptionHandler(NonExistentImageException.class)
    public ProblemDetail handleInexsistantImageException(NonExistentImageException ex, HttpServletRequest request) {
        ProblemDetail pd = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
        log.warning("Error class: " + ex.getClass() + ", Error message: " + ex.getMessage());
        pd.setTitle("Imagen no encontrada.");
        pd.setInstance(URI.create(request.getRequestURI()));
        return pd;
    }

}
