package com.salesianostriana.dam.campusswap.seguridad.jwt;


import com.salesianostriana.dam.campusswap.entidades.Usuario;
import com.salesianostriana.dam.campusswap.repositorios.RepositorioUsuario;

import com.salesianostriana.dam.campusswap.seguridad.error.JwtAccessDeniedHandler;
import com.salesianostriana.dam.campusswap.seguridad.error.JwtAuthenticationEntryPoint;
import io.jsonwebtoken.JwtException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;


import lombok.extern.java.Log;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Log
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtAccessTokenService jwtService;
    private final RepositorioUsuario repositorioUsuario;
    private final JwtAuthenticationEntryPoint authenticationEntryPoint;
    private final JwtAccessDeniedHandler accessDeniedHandler;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String token = getJwtAccessTokenFromRequest(request);

        if (StringUtils.hasText(token)) {
            try {
                jwtService.validateAccessToken(token);
                String userId = jwtService.getUserIdFromAccessToken(token);

                Usuario user = repositorioUsuario.findById(UUID.fromString(userId))
                        .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + userId));

                if (!user.isAccountNonLocked()) {
                    throw new LockedException("Tu cuenta ha sido bloqueada por un administrador.");
                }

                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        user, null, user.getAuthorities()
                );
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);

            } catch (LockedException | AccessDeniedException e) {
                // 403 → delega en tu AccessDeniedHandler
                SecurityContextHolder.clearContext();
                accessDeniedHandler.handle(request, response,
                        new AccessDeniedException(e.getMessage()));
                return;

            } catch (JwtException | AuthenticationException e) {
                SecurityContextHolder.clearContext();
                authenticationEntryPoint.commence(request, response,
                        new BadCredentialsException(e.getMessage()));
                return;
            }
        }

        filterChain.doFilter(request, response);
    }

    private String getJwtAccessTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader(JwtAccessTokenService.TOKEN_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(JwtAccessTokenService.TOKEN_PREFIX))
            return bearerToken.substring(JwtAccessTokenService.TOKEN_PREFIX.length());
        return null;
    }

}
