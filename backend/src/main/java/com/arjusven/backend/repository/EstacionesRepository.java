package com.arjusven.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.arjusven.backend.model.Estaciones;

@Repository
public interface EstacionesRepository extends JpaRepository<Estaciones, Long>{
	
}
