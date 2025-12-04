package com.arjusven.backend.repository;

import com.arjusven.backend.model.Tickets; // o Tickets, según el nombre de tu clase
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;


@Repository
public interface TicketRepository extends JpaRepository<Tickets, Long> {
    
	List<Tickets> findByServicios_Incidencia(String incidencia);
	
	@Query("SELECT t FROM Tickets t " +
	           "JOIN t.servicios s " +
	           "JOIN t.adicionales u " +
	           "WHERE LOWER(s.nombreDeEss) = LOWER(:texto) " +
	           "OR LOWER(s.incidencia) = LOWER(:texto) " +
	           "OR LOWER(s.situacionActual) = LOWER(:texto) " +
	           "OR LOWER(u.serieLogicaEntra) = LOWER(:texto) " +
	           "OR LOWER(u.serieLogicaSale) = LOWER(:texto) " +
	           "OR (:idMerchant IS NOT NULL AND s.idMerchant = :idMerchant)")
	    List<Tickets> buscarExacto(@Param("texto") String texto, 
	                               @Param("idMerchant") Long idMerchant);
	
	
	 @Query("SELECT t FROM Tickets t " +
	           "JOIN t.servicios s " +
	           "JOIN t.adicionales u " +
	           "WHERE LOWER(s.nombreDeEss) LIKE LOWER(CONCAT('%', :texto, '%')) " +
	           "OR LOWER(s.incidencia) LIKE LOWER(CONCAT('%', :texto, '%')) " +
	           "OR LOWER(u.serieLogicaEntra) LIKE LOWER(CONCAT('%', :texto, '%')) " +
	           "OR LOWER(u.serieLogicaSale) LIKE LOWER(CONCAT('%', :texto, '%')) " +
	           "OR LOWER(s.situacionActual) LIKE LOWER(CONCAT('%', :texto, '%'))")
	    List<Tickets> buscarParcial(@Param("texto") String texto);
	
	 @Query("SELECT t FROM Tickets t " +
		       "JOIN t.servicios s " +
		       "JOIN t.adicionales u " +
		       "WHERE " +
		       "(:situacion IS NULL OR s.situacionActual = :situacion) " +
		       "AND (:sla IS NULL OR s.sla = :sla) " +
		       "AND (:tipoDeServicio IS NULL OR s.tipoDeServicio = :tipoDeServicio) " + 
		       "AND (:supervisor IS NULL OR s.supervisor = :supervisor) " +
		       "AND (:plaza IS NULL OR u.plaza = :plaza) " +
		       "AND (:fechaInicio IS NULL OR s.fechaDeAsignacion >= :fechaInicio) " +
	           "AND (:fechaFin IS NULL OR s.fechaDeAsignacion <= :fechaFin)")
		List<Tickets> buscarPorFiltros(
		        @Param("situacion") String situacion,
		        @Param("sla") String sla,
		        @Param("tipoDeServicio") String tipoDeServicio,
		        @Param("supervisor") String supervisor,
		        @Param("plaza") String plaza,
		        @Param("fechaInicio") LocalDate fechaInicio,
	            @Param("fechaFin") LocalDate fechaFin
		);
}