package com.prueba.usuarios;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.SQLException;

@SpringBootApplication
public class UsuariosApplication {

    public UsuariosApplication() throws SQLException {
    }
    public static void main(String[] args) {
		SpringApplication.run(UsuariosApplication.class, args);
	}
}
