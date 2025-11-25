package com.arjusven.backend.repository;

import com.arjusven.backend.model.Tickets; // o Tickets, según el nombre de tu clase
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Tickets, Long> {
    
	List<Tickets> findByServicios_Incidencia(String incidencia);
	
	
	@Query("SELECT t FROM Tickets t " +
	           "JOIN t.servicios s " +
	           "WHERE LOWER(s.nombreDeEss) = LOWER(:texto) " +
	           "OR LOWER(s.incidencia) = LOWER(:texto) " +
	           "OR LOWER(s.situacionActual) = LOWER(:texto) " +
	           "OR (:idMerchant IS NOT NULL AND s.idMerchant = :idMerchant)")
	    List<Tickets> buscarExacto(@Param("texto") String texto, 
	                               @Param("idMerchant") Long idMerchant);
	
	
	 @Query("SELECT t FROM Tickets t " +
	           "JOIN t.servicios s " +
	           "WHERE LOWER(s.nombreDeEss) LIKE LOWER(CONCAT('%', :texto, '%')) " +
	           "OR LOWER(s.incidencia) LIKE LOWER(CONCAT('%', :texto, '%')) " +
	           "OR LOWER(s.situacionActual) LIKE LOWER(CONCAT('%', :texto, '%'))")
	    List<Tickets> buscarParcial(@Param("texto") String texto);
	
	 
	 @Query("SELECT t FROM Tickets t " +
	           "JOIN t.servicios s " +
	           "WHERE " +
	           // 1. Filtro por Situación Actual (situacionActual en Servicios)
	           "(:situacion IS NULL OR s.situacionActual = :situacion)")
	    List<Tickets> buscarPorFiltros(
	            @Param("situacion") String situacion
	    );
							
}