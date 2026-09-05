package com.atelier;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class GestionAtelierApplication {

	public static void main(String[] args) {

		// Hash
		System.out.println("HASH de '500227' : " + new BCryptPasswordEncoder().encode("500227"));
		SpringApplication.run(GestionAtelierApplication.class, args);
	}

}