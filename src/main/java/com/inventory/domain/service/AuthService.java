package com.inventory.domain.service;

import com.inventory.application.dto.LoginDTO;
import com.inventory.application.dto.RegisterDTO;

public interface AuthService {
    /**
     * Registra un nuevo usuario en el sistema.
     * '@param' registerDTO DTO con los datos del usuario a registrar.
     */
    void register(RegisterDTO registerDTO);

    /**
     * 'Autentica' a un usuario y devuelve un token JWT.
     * '@param' loginDTO DTO con las credenciales del usuario.
     * '@return' Un token JWT.
     */
    String authenticate(LoginDTO loginDTO);
}
