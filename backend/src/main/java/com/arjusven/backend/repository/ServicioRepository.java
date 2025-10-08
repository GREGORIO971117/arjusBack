package com.arjusven.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.arjusven.backend.model.Servicio;

@Repository
public interface ServicioRepository extends JpaRepository<Servicio, Long> {
	
}
