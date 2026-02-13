package com.inventory.application.mapper;

import com.inventory.application.dto.RegisterDTO;
import com.inventory.application.dto.UserResponseDTO;
import com.inventory.domain.model.User;



public interface UserMapper {

    public static User toEntity(RegisterDTO registerDTO) {
        if (registerDTO == null) {
            return null;
        }
        User user = new User();
        // Ignorar el ID porque se genera automáticamente
        user.setUsername(registerDTO.getUsername());
        user.setPassword(registerDTO.getPassword()); // Asegúrate de cifrar la contraseña si es necesario
        user.setRole(registerDTO.getRole()); // Asignar el rol directamente desde el DTO
        return user;
    }

    public static UserResponseDTO toDTO(User user) {
        if (user == null) {
            return null;
        }
        return new UserResponseDTO(
                user.getId(),
                user.getUsername(),
                user.getRole()
        );
    }
}