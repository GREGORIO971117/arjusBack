package com.arjusven.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.arjusven.backend.model.Estaciones;

@Repository
public interface EstacionesRepository extends JpaRepository<Estaciones, Long>{
	

	@Query("SELECT t FROM Estaciones t " +
			"WHERE LOWER(t.nombreComercial) = LOWER(:texto) " +
			"OR LOWER(t.direccion) = LOWER(:texto) " +
			"OR LOWER(t.plazaDeAtencion) = LOWER(:texto) " +
	        "OR (:idMerchant IS NOT NULL AND t.idMerchant = :idMerchant)")
	List<Estaciones> buscarExacto(@Param("texto") String texto,
								  @Param("idMerchant") Long idMerchant);

	@Query("SELECT t FROM Estaciones t " +
			"WHERE LOWER(t.nombreComercial) LIKE LOWER(CONCAT('%', :texto, '%')) " +
			"OR LOWER(t.direccion) LIKE LOWER(CONCAT('%', :texto, '%')) " +
			"OR LOWER(t.plazaDeAtencion) LIKE LOWER(CONCAT('%', :texto, '%'))")
	List<Estaciones> buscarParcial(@Param("texto") String texto);
			
}