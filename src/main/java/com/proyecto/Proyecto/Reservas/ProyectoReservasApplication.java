package com.proyecto.Proyecto.Reservas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class ProyectoReservasApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProyectoReservasApplication.class, args);
	}

}
