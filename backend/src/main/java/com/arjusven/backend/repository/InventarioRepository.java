package com.arjusven.backend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.arjusven.backend.model.Inventario;

@Repository
public interface InventarioRepository extends JpaRepository<Inventario, Long> {
	
	Optional<Inventario> findByNumeroDeSerieIgnoreCase(String numeroDeSerie);	
		@Query("SELECT s FROM Inventario s " + 
				"WHERE LOWER(s.titulo) = LOWER(:texto) " +
				"OR LOWER(s.numeroDeSerie) = LOWER(:texto) " +
				"OR LOWER(s.numeroDeIncidencia) = LOWER(:texto)")
		List<Inventario> buscarExacto(@Param("texto") String texto);
		
		@Query("SELECT s FROM Inventario s " + 
				"WHERE LOWER(s.titulo) LIKE LOWER(CONCAT('%', :texto, '%')) " +
				"OR LOWER(s.numeroDeSerie) LIKE LOWER(CONCAT('%', :texto, '%')) " +
				"OR LOWER(s.numeroDeIncidencia) LIKE LOWER(CONCAT('%', :texto, '%'))")
		List<Inventario> buscarParcial(@Param("texto") String texto);
	
	
}
