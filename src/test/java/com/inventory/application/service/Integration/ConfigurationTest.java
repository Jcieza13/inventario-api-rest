package com.inventory.application.service.Integration;

import org.springframework.core.env.Environment;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest // Carga el contexto completo de Spring Boot
@ActiveProfiles("test") // Activa el perfil "test" para cargar application-test.properties
class ConfigurationTest {

    @Autowired
    private Environment environment;

    @Test
    void testEnvironmentProperties() {
        String datasourceUrl = environment.getProperty("spring.datasource.url");
        assertNotNull(datasourceUrl, "La propiedad spring.datasource.url no debe ser nula");
        System.out.println("Datasource URL: " + datasourceUrl);
    }
}