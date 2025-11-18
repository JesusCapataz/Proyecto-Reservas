package com.proyecto.Proyecto.Reservas;

import org.springframework.boot.SpringApplication;

public class TestProyectoReservasApplication {

	public static void main(String[] args) {
		SpringApplication.from(ProyectoReservasApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
