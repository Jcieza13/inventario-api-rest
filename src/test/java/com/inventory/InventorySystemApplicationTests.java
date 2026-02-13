package com.inventory;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.core.env.Environment;

@SpringBootTest
@ActiveProfiles("test")
 public class InventorySystemApplicationTests {
	@Autowired
	private Environment environment;

	@Test
	void contextLoads() {
		System.out.println("Contexto cargado correctamente.");
		System.out.println("Perfil activo: " + String.join(", ", environment.getActiveProfiles()));
	}
	}


