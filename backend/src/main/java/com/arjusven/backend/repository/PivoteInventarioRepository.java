package com.arjusven.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.arjusven.backend.model.PivoteInventario;
import com.arjusven.backend.model.PivoteInventarioId;

@Repository
public interface PivoteInventarioRepository extends JpaRepository<PivoteInventario, PivoteInventarioId> {
	List<PivoteInventario> findByInventario_IdInventarioOrderByFechaAsignacionDesc(Long idInventario);
}
