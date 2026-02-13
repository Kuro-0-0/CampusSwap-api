package com.salesianostriana.dam.campusswap;

import com.salesianostriana.dam.campusswap.entidades.Anuncio;
import com.salesianostriana.dam.campusswap.entidades.Usuario;
import com.salesianostriana.dam.campusswap.entidades.extras.Condicion;
import com.salesianostriana.dam.campusswap.entidades.extras.Estado;
import com.salesianostriana.dam.campusswap.entidades.extras.TipoOperacion;
import com.salesianostriana.dam.campusswap.repositorios.RepositorioAnuncio;
import com.salesianostriana.dam.campusswap.repositorios.RepositorioUsuario;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Log
public class mainMentira {

    private final RepositorioAnuncio repositorioAnuncio;
    private final RepositorioUsuario repositorioUsuario;

    @PostConstruct
    public void init() {
        log.info("Creando usuarios de prueba...");

        // Primer usuario (u)
        Usuario u = repositorioUsuario.save(Usuario.builder()
                .nombre("Usuario de Prueba")
                .email("test@salesianos.edu")
                .contrasena("1234")
                .fotoPerfil("avatar.png")
                .descripcion("Descripción de prueba")
                .reputacionMedia(5.0)
                .activo(true)
                .build());

        // 1. Usuario extra (u2 != u)
        Usuario u2 = repositorioUsuario.save(Usuario.builder()
                .nombre("Segundo Usuario")
                .email("segundo@salesianos.edu")
                .contrasena("abcd")
                .fotoPerfil("perfil2.png")
                .descripcion("Soy el segundo usuario para pruebas")
                .reputacionMedia(4.0)
                .activo(true)
                .build());

        log.info("Usuarios creados: " + u.getId() + " y " + u2.getId());

        log.info("Creando anuncios de prueba...");

        // Anuncio original de u
        Anuncio a = repositorioAnuncio.save(Anuncio.builder()
                .titulo("Producto de Prueba")
                .descripcion("Una descripción detallada del producto")
                .precio(25.50)
                .tipoOperacion(TipoOperacion.VENTA)
                .estado(Estado.ACTIVO)
                .condicion(Condicion.NUEVO)
                .imagen("producto.jpg")
                .usuario(u)
                .build());

        // 2. Anuncio de usuario != u (pertenece a u2)
        Anuncio a2 = repositorioAnuncio.save(Anuncio.builder()
                .titulo("Bicicleta de Montaña")
                .descripcion("Bicicleta en muy buen estado")
                .precio(120.0)
                .tipoOperacion(TipoOperacion.VENTA)
                .estado(Estado.ACTIVO)
                .condicion(Condicion.USADO)
                .imagen("bici.jpg")
                .usuario(u2)
                .build());

        // 3. Anuncio con estado.CERRADO
        Anuncio a3 = repositorioAnuncio.save(Anuncio.builder()
                .titulo("Libro de Programación Java")
                .descripcion("Vendido recientemente")
                .precio(15.0)
                .tipoOperacion(TipoOperacion.VENTA)
                .estado(Estado.CERRADO)
                .condicion(Condicion.NUEVO)
                .imagen("libro.jpg")
                .usuario(u)
                .build());

        log.info("Datos de prueba inicializados correctamente.");
    }
}