package com.salesianostriana.dam.campusswap.entidades;

import com.salesianostriana.dam.campusswap.entidades.extras.RolUsuario;
import jakarta.persistence.*;
import lombok.*;
import org.jspecify.annotations.Nullable;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@EntityListeners(AuditingEntityListener.class)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue
    @EqualsAndHashCode.Include()
    private UUID id;

    private String nombre;
    private String email;
    private String fotoPerfil;
    private String descripcion;
    private double reputacionMedia;

    @CreatedDate
    private LocalDateTime fechaRegistro;

    private String username;
    private String contrasena;

    @ElementCollection(fetch = FetchType.EAGER)
    @Builder.Default
    private Set<RolUsuario> roles = new HashSet<>();

    @Builder.Default
    private boolean enabled = true;
    @Builder.Default
    private boolean accountNonExpired = true;
    @Builder.Default
    private boolean accountNonLocked = true;
    @Builder.Default
    private boolean credentialsNonExpired = true;



    @Builder.Default
    @OneToMany(mappedBy = "usuario", orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Anuncio> anuncios = new ArrayList<>();

    public void agregarAnuncio(Anuncio anuncio) {
        anuncios.add(anuncio);
        anuncio.setUsuario(this);
    }

    public void borrarAnuncio(Anuncio anuncio){
        anuncios.remove(anuncio);
        anuncio.setUsuario(null);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(
                        rol -> new SimpleGrantedAuthority("ROLE_" + rol.name())
                )
                .collect(Collectors.toSet());
    }

    @Override
    public @Nullable String getPassword() {
        return contrasena;
    }
}
