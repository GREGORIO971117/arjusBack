package com.arjusven.backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.arjusven.backend.model.Usuarios;

@Repository
public interface PersonalRepository extends JpaRepository<Usuarios, Long> {
    // Método personalizado para buscar un usuario por su correo
    // Esto es muy útil para el login o validaciones.
    Optional<Usuarios> findByCorreo(String correo);
}
