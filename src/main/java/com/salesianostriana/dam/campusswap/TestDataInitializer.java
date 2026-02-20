package com.salesianostriana.dam.campusswap;

import com.salesianostriana.dam.campusswap.entidades.*;
import com.salesianostriana.dam.campusswap.entidades.extras.Condicion;
import com.salesianostriana.dam.campusswap.entidades.extras.Estado;
import com.salesianostriana.dam.campusswap.entidades.extras.RolUsuario;
import com.salesianostriana.dam.campusswap.entidades.extras.TipoOperacion;
import com.salesianostriana.dam.campusswap.repositorios.*;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
@Log
@Profile("dev")
class TestDataInitializer {

    private final RepositorioAnuncio repoAnuncio;
    private final RepositorioUsuario repoUsuario;
    private final RepositorioCategoria repoCategoria;
    private final RepositorioMensaje repoMensaje;
    private final RepositorioFavorito repoFavorito;
    private final RepositorioValoracion repoValoracion;
    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    @Transactional
    public void init() {
        log.info("INICIANDO CARGA DE DATOS DE PRUEBA...");

        repoMensaje.deleteAll();
        repoFavorito.deleteAll();
        repoValoracion.deleteAll();
        repoAnuncio.deleteAll();
        repoCategoria.deleteAll();
        repoUsuario.deleteAll();

        Categoria catLibros = crearCategoria("Libros y Apuntes", "Libros de texto, apuntes de ciclos y material didáctico.");
        Categoria catElectronica = crearCategoria("Electrónica e Informática", "Portátiles, tablets, calculadoras y periféricos.");
        Categoria catRopa = crearCategoria("Ropa y Merch", "Sudaderas del centro, ropa deportiva y accesorios.");
        Categoria catTransporte = crearCategoria("Transporte", "Bicicletas, patinetes y accesorios de movilidad.");
        Categoria catMuebles = crearCategoria("Mobiliario", "Sillas de escritorio, flexos y estanterías.");

        repoCategoria.saveAll(List.of(catLibros, catElectronica, catRopa, catTransporte, catMuebles));

        Usuario uAdmin = crearUsuario("Admin User", "admin", "admin@salesianos.edu", "1234", null, "Administrador del sistema y moderador.", 5.0, RolUsuario.ADMIN);
        Usuario uVendedor = crearUsuario("Carlos Vendedor", "carlos_v", "carlos@salesianos.edu", "1234", null, "Vendo todo lo que ya no uso del ciclo de DAM. ¡Precios negociables!", 4.8, RolUsuario.USUARIO);
        Usuario uComprador = crearUsuario("Laura Compradora", "laura_buyer", "laura@salesianos.edu", "1234", null, "Busco material para 1º de DAM.", 0.0, RolUsuario.USUARIO);
        Usuario uNuevo = crearUsuario("Pepe Novato", "pepito", "pepe@salesianos.edu", "1234", null, "Nuevo en el campus.", 0.0, RolUsuario.ADMIN);

        List<Usuario> usuarios = repoUsuario.saveAll(List.of(uAdmin, uVendedor, uComprador, uNuevo));
        uAdmin = usuarios.get(0);
        uVendedor = usuarios.get(1);
        uComprador = usuarios.get(2);
        uNuevo = usuarios.get(3);

        Anuncio aPortatil = Anuncio.builder()
                .titulo("Portátil HP Victus 16GB RAM")
                .descripcion("Lo vendo porque me he comprado un Mac. Ideal para programar en IntelliJ.")
                .precio(650.00) // Venta lleva precio
                .tipoOperacion(TipoOperacion.VENTA)
                .estado(Estado.ACTIVO)
                .condicion(Condicion.COMO_NUEVO)
                .imagen("hp_victus.jpg")
                .usuario(uVendedor)
                .categoria(catElectronica)
                .fechaPublicacion(LocalDateTime.now().minusDays(2))
                .build();

        Anuncio aBici = Anuncio.builder()
                .titulo("Bicicleta de montaña Rockrider")
                .descripcion("Cambio por un patinete eléctrico Xiaomi en buen estado.")
                .precio(null)
                .tipoOperacion(TipoOperacion.INTERCAMBIO)
                .estado(Estado.ACTIVO)
                .condicion(Condicion.USADO)
                .imagen("bici.jpg")
                .usuario(uVendedor)
                .categoria(catTransporte)
                .fechaPublicacion(LocalDateTime.now().minusDays(5))
                .build();

        Anuncio aApuntes = Anuncio.builder()
                .titulo("Apuntes de Base de Datos 1º DAM")
                .descripcion("Regalo mis apuntes en limpio del año pasado. A quien venga a buscarlos.")
                .precio(null)
                .tipoOperacion(TipoOperacion.CESION)
                .estado(Estado.ACTIVO)
                .condicion(Condicion.USADO)
                .imagen("apuntes_sql.jpg")
                .usuario(uComprador)
                .categoria(catLibros)
                .fechaPublicacion(LocalDateTime.now().minusHours(4))
                .build();

        Anuncio aLibroJava = Anuncio.builder()
                .titulo("Clean Code - Robert C. Martin")
                .descripcion("Libro imprescindible. Ya lo he leído.")
                .precio(20.00)
                .tipoOperacion(TipoOperacion.VENTA)
                .estado(Estado.CERRADO)
                .condicion(Condicion.NUEVO)
                .imagen("cleancode.jpg")
                .usuario(uVendedor)
                .categoria(catLibros)
                .fechaPublicacion(LocalDateTime.now().minusWeeks(2))
                .build();

        repoAnuncio.saveAll(List.of(aPortatil, aBici, aApuntes, aLibroJava));

        Mensaje m1 = Mensaje.builder()
                .contenido("Hola Carlos, ¿sigue disponible el portátil? ¿El precio es negociable?")
                .fechaEnvio(LocalDateTime.now().minusDays(1))
                .anuncio(aPortatil)
                .emisor(uComprador)
                .receptor(uVendedor)
                .build();

        Mensaje m2 = Mensaje.builder()
                .contenido("Hola Laura, sí, sigue disponible. Podría bajarlo a 630€ si vienes hoy.")
                .fechaEnvio(LocalDateTime.now().minusDays(1).plusHours(1))
                .emisor(uVendedor)
                .receptor(uComprador)
                .anuncio(aPortatil)
                .build();

        repoMensaje.saveAll(List.of(m1, m2));

        Favorito f1 = Favorito.builder()
                .usuario(uComprador)
                .anuncio(aBici)
                .fecha(LocalDateTime.now())
                .build();

        Favorito f2 = Favorito.builder()
                .usuario(uComprador)
                .anuncio(aPortatil)
                .fecha(LocalDateTime.now())
                .build();

        repoFavorito.saveAll(List.of(f1, f2));

        Valoracion val1 = Valoracion.builder()
                .puntuacion(5.0)
                .comentario("El libro estaba impecable, tal como decía el anuncio. Carlos es muy amable.")
                .evaluador(uComprador)
                .evaluado(uVendedor)
                .anuncio(aLibroJava)
                .fecha(LocalDateTime.now().minusDays(3))
                .build();

        repoValoracion.save(val1);

        log.info("CARGA DE DATOS COMPLETADA: Usuarios, Anuncios, Favoritos, Mensajes y Valoraciones listos.");
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
                .fechaRegistro(LocalDateTime.now().minusMonths(1))
                .build();
    }

    private Categoria crearCategoria(String nombre, String descripcion) {
        return Categoria.builder()
                .nombre(nombre)
                .descripcion(descripcion)
                .build();
    }
}