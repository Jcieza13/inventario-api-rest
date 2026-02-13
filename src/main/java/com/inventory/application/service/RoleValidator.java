package com.inventory.application.service;

import com.inventory.infrastucture.exception.CustomException;
import org.springframework.stereotype.Service;

@Service
public class RoleValidator {
    public String validateRole(String role) {
        if (role == null || role.isEmpty()){
            return "ROLE_USER"; // Rol predeterminado
        }

        if (!role.equals("ROLE_ADMIN") && !role.equals("ROLE_USER")) {
            throw new CustomException("Rol no válido. Los roles permitidos son ROLE_ADMIN y ROLE_USER.", 400);
        }
        return role;
    }
}
