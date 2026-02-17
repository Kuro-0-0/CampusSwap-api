package com.salesianostriana.dam.campusswap.seguridad.jwt;


import com.salesianostriana.dam.campusswap.repositorios.RepositorioUsuario;

import io.jsonwebtoken.JwtException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;



import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtAccessTokenService jwtService;
    private final RepositorioUsuario repositorioUsuario;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String token = getJwtAccessTokenFromRequest(request);
        try {

            if (StringUtils.hasText(token) && jwtService.validateAccessToken(token)) {

                String userId = jwtService.getUserIdFromAccessToken(token);

                RepositorioUsuario.findById(UUID.fromString(userId)).ifPresentOrElse(user -> {
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                            user,
                            null,
                            user.getAuthorities()
                    );

                    authenticationToken.setDetails(
                            new WebAuthenticationDetails(request)
                    );

                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);


                }, () -> {
                    throw new EntityNotFoundException("User not found with id: " + userId);
                });



            }

        } catch (JwtException e) {
            throw new RuntimeException(e);
        }


    }

    private String getJwtAccessTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader(JwtAccessTokenService.TOKEN_HEADER);

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(JwtAccessTokenService.TOKEN_PREFIX))
            return bearerToken.substring(JwtAccessTokenService.TOKEN_PREFIX.length());

        return null;


    }

}
