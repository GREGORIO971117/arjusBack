package com.arjusven.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.arjusven.backend.model.*;

import java.util.Optional;

@Repository
public interface AdicionalRepository extends JpaRepository<Adicional, Long> {
	
}