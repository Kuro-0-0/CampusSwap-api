package com.salesianostriana.dam.campusswap.seguridad.jwt;

import com.salesianostriana.dam.campusswap.entidades.Usuario;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Date;

@Service
public class JwtAccessTokenService {

    public static final String TOKEN_TYPE = "JWT";
    public static final String TOKEN_HEADER = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.duration}")
    private long jwtLifetime;

    private JwtParser jwtParser;
    private SecretKey secretKey;

    @PostConstruct
    public void init() {
        secretKey = Keys.hmacShaKeyFor(secret.getBytes());

        jwtParser = Jwts.parser()
                .verifyWith(secretKey)
                .build();
    }

    public String generateAccessToken(Usuario usuario) {
        return generateAccessToken(usuario.getId().toString());
    }

    public String generateAccessToken(String uuid) {
        return Jwts.builder()
                .header().setType(TOKEN_TYPE).and()
                .subject(uuid)
                .issuedAt(new Date())
                .expiration(
                        Date.from(Instant.now().plusSeconds(jwtLifetime))
                )
                .signWith(secretKey)
                .compact();
    }

    public boolean validateAccessToken(String token) throws JwtException {
        jwtParser.parseSignedClaims(token);
        return true;
    }

    public String getUserIdFromAccessToken(String token) {
        return jwtParser.parseSignedClaims(token).getBody().getSubject();
    }


}
