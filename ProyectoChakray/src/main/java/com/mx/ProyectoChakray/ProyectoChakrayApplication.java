package com.mx.ProyectoChakray;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class ProyectoChakrayApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProyectoChakrayApplication.class, args);
	}

}
