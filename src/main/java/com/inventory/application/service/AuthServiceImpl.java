package com.inventory.application.service;

import com.inventory.application.dto.LoginDTO;
import com.inventory.application.dto.RegisterDTO;
import com.inventory.application.mapper.UserMapper;
import com.inventory.domain.model.User;
import com.inventory.domain.service.AuthService;
import com.inventory.infrastucture.exception.CustomException;
import com.inventory.infrastucture.exception.ResourceNotFoundException;
import com.inventory.infrastucture.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService; // Supongamos que tienes un servicio para generar tokens JWT
    private final RoleValidator roleValidator;

    @Autowired
    public AuthServiceImpl(UserRepository userRepository,
                           PasswordEncoder passwordEncoder,
                           JwtService jwtService,
                           RoleValidator roleValidator) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.roleValidator = roleValidator;
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

    @Override
    public void register(RegisterDTO registerDTO) {
        if (userRepository.existsByUsername(registerDTO.getUsername())) {
            throw new CustomException("El nombre de usuario ya existe", 409);
        }
        // Validar el rol antes de mapear
        String validatedRole = roleValidator.validateRole(registerDTO.getRole());

        // Convertir el DTO a una entidad manualmente
        User newUser = toEntity(registerDTO);

        // Asignar el rol validado
        newUser.setRole(validatedRole);

        // Hashear la contraseña
        newUser.setPassword(passwordEncoder.encode(registerDTO.getPassword()));

        // Guardar el usuario en la base de datos
        userRepository.save(newUser);
    }

    @Override
    public String authenticate(LoginDTO loginDTO) {
        User user = userRepository.findByUsername(loginDTO.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));
        if (!passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())) {
            throw new CustomException("Credenciales inválidas", 401); // Código HTTP 401: Unauthorized
        }
        return jwtService.generateToken(user);
    }
}