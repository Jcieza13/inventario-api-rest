package com.inventory.infrastucture.initializer;

import com.inventory.domain.model.User;
import com.inventory.infrastucture.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {
    @Bean
    public CommandLineRunner initializeData(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            // Verificar si ya existe un usuario administrador
            if (!userRepository.existsByUsername("admin")) {
                User adminUser = new User();
                adminUser.setUsername("admin");
                adminUser.setPassword(passwordEncoder.encode("admin123")); // Contraseña segura para el administrador
                adminUser.setRole("ROLE_ADMIN");

                // Guardar el usuario administrador en la base de datos
                userRepository.save(adminUser);
                System.out.println("Usuario administrador creado: admin");
            }
        };
    }
}
