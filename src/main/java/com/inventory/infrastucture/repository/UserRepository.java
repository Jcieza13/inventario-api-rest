package com.inventory.infrastucture.repository;

import com.inventory.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * Busca un usuario por su nombre de usuario.
     *
     * @param username El nombre de usuario.
     * @return Un Optional que contiene el usuario si existe, o vacío si no existe.
     */
    Optional<User> findByUsername(String username);

    /**
     * Verifica si existe un usuario con el nombre de usuario dado.
     *
     * @param username El nombre de usuario.
     * @return true si existe, false si no existe.
     */
    boolean existsByUsername(String username);
}
