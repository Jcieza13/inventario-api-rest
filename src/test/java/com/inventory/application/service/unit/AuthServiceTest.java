package com.inventory.application.service.unit;

import com.inventory.application.dto.RegisterDTO;
import com.inventory.application.service.AuthServiceImpl;
import com.inventory.application.service.JwtService;
import com.inventory.application.service.RoleValidator;
import com.inventory.domain.model.User;
import com.inventory.infrastucture.exception.CustomException;
import com.inventory.infrastucture.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class AuthServiceTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @Mock
    private RoleValidator roleValidator;

    @InjectMocks
    private AuthServiceImpl authService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Convierte un DTO RegisterDTO en una entidad User.
     */
    private User toEntity(RegisterDTO registerDTO) {
        if (registerDTO == null) {
            return null;
        }
        User user = new User();
        user.setUsername(registerDTO.getUsername());
        user.setPassword(registerDTO.getPassword()); // La contraseña aún no está hasheada
        user.setRole(registerDTO.getRole()); // El rol será validado después
        return user;
    }

    @Test
    void testRegister_Success() {
        // Arrange
        RegisterDTO registerDTO = new RegisterDTO("newuser", "password123", "ROLE_USER");
        String validatedRole = "ROLE_USER";
        User user = toEntity(registerDTO); // Usamos el método manual
        user.setPassword("encodedPassword"); // Simulamos la contraseña hasheada

        when(userRepository.existsByUsername("newuser")).thenReturn(false);
        when(roleValidator.validateRole("ROLE_USER")).thenReturn(validatedRole);
        when(passwordEncoder.encode("password123")).thenReturn("encodedPassword");

        // Act
        authService.register(registerDTO);

        // Assert
        verify(userRepository, times(1)).existsByUsername("newuser");
        verify(roleValidator, times(1)).validateRole("ROLE_USER");
        verify(passwordEncoder, times(1)).encode("password123");
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testRegister_UsernameAlreadyExists() {
        // Arrange
        RegisterDTO registerDTO = new RegisterDTO("existinguser", "password123", "ROLE_USER");
        when(userRepository.existsByUsername("existinguser")).thenReturn(true);

        // Act & Assert
        CustomException exception = assertThrows(CustomException.class, () -> {
            authService.register(registerDTO);
        });

        assertEquals("El nombre de usuario ya existe", exception.getMessage());
        assertEquals(409, exception.getStatusCode());
        verify(userRepository, times(1)).existsByUsername("existinguser");
        verifyNoInteractions(roleValidator, passwordEncoder);
    }

    @Test
    void testRegister_InvalidRole() {
        // Arrange
        RegisterDTO registerDTO = new RegisterDTO("newuser", "password123", "INVALID_ROLE");
        when(userRepository.existsByUsername("newuser")).thenReturn(false);
        when(roleValidator.validateRole("INVALID_ROLE")).thenThrow(new CustomException("Rol no válido", 400));

        // Act & Assert
        CustomException exception = assertThrows(CustomException.class, () -> {
            authService.register(registerDTO);
        });

        assertEquals("Rol no válido", exception.getMessage());
        assertEquals(400, exception.getStatusCode());
        verify(userRepository, times(1)).existsByUsername("newuser");
        verify(roleValidator, times(1)).validateRole("INVALID_ROLE");
        verifyNoInteractions(passwordEncoder);
    }
}