package com.salesianostriana.dam.campusswap;

import com.salesianostriana.dam.campusswap.entidades.Usuario;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CampusswapApplication {

	public static void main(String[] args) {
		SpringApplication.run(CampusswapApplication.class, args);
	}

}
