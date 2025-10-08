package com.arjusven.backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.arjusven.backend.model.Inventario;

@Repository
public interface InventarioRepository extends JpaRepository<Inventario, Long> {
    // Útil para buscar un artículo antes de asignarlo
    Optional<Inventario> findByNumeroDeSerie(String numeroDeSerie);
}
