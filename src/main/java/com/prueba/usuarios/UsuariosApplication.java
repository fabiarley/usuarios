package com.prueba.usuarios;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@SpringBootApplication
public class UsuariosApplication {

    public UsuariosApplication() throws SQLException {
    }
    public static void main(String[] args) {
		SpringApplication.run(UsuariosApplication.class, args);
	}

}
