package com.salesianostriana.dam.campusswap.configuraciones;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "admin.default")
@Getter
@Setter
public class AdminConfig {

    private String username,password,email;


}
