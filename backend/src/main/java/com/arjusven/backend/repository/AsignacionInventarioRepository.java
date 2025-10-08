package com.arjusven.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.arjusven.backend.model.AsignacionInventario;

@Repository
public interface AsignacionInventarioRepository extends JpaRepository<AsignacionInventario, Long> {
    // Puedes buscar asignaciones por el ID del ticket
    // List<AsignacionInventario> findByTicketId(Long ticketId);
}

