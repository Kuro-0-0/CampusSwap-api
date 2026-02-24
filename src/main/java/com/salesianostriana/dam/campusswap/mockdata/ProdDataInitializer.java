package com.salesianostriana.dam.campusswap.mockdata;
import com.salesianostriana.dam.campusswap.configuraciones.AdminConfig;
import com.salesianostriana.dam.campusswap.entidades.*;
import com.salesianostriana.dam.campusswap.entidades.extras.RolUsuario;
import com.salesianostriana.dam.campusswap.repositorios.*;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Set;

@Component
@RequiredArgsConstructor
@Log
@Profile("prod")
class ProdDataInitializer {


    private final RepositorioUsuario repoUsuario;
    private final PasswordEncoder passwordEncoder;
    private final AdminConfig adminConfig;

    @PostConstruct
    @Transactional
    public void init() {
        log.info("INICIANDO CARGA DE DATOS DE PRUEBA...");


        repoUsuario.deleteAll();




        Usuario uAdmin = crearUsuario("admin", adminConfig.getUsername(), adminConfig.getEmail(), adminConfig.getPassword(), null, "Administrador del sistema y moderador.", 5.0, RolUsuario.ADMIN);


        Usuario usuarios = repoUsuario.save(uAdmin);



    }

    private Usuario crearUsuario(String nombre, String username, String email, String pass, String foto, String desc, double reputacion, RolUsuario rol) {
        return Usuario.builder()
                .nombre(nombre)
                .username(username) // Nuevo campo
                .email(email)
                .contrasena(passwordEncoder.encode(pass))
                .fotoPerfil(foto)
                .descripcion(desc)
                .reputacionMedia(reputacion)
                .roles(Set.of(rol)) // Asignación del rol
                .enabled(true)
                .build();
    }

}