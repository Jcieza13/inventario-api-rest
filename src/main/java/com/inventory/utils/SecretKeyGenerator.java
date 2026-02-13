package com.inventory.utils;

import io.jsonwebtoken.security.Keys;

import java.util.Base64;

public class SecretKeyGenerator {
    public static void main(String[] args) {
        // Generar una clave segura para HS512
        var secretKey = Keys.secretKeyFor(io.jsonwebtoken.SignatureAlgorithm.HS512);
        System.out.println("Generated Secret Key: " + Base64.getEncoder().encodeToString(secretKey.getEncoded()));
    }
}
