package com.arjusven.backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.arjusven.backend.model.Servicio;

@Repository
public interface ServicioRepository extends JpaRepository<Servicio, Long> {
	
	Optional<Servicio> findByIncidencia(String incidencia);
}
