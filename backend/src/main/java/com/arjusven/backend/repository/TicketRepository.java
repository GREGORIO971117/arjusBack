package com.arjusven.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.arjusven.backend.model.Tickets;

import java.util.List;
import java.time.LocalDate; // Necesario para los filtros de fecha

@Repository
public interface TicketRepository extends JpaRepository<Tickets, Long> {

    // 1. FILTRO GENERAL POR ESTADO (Campo simple en Ticket)
    List<Tickets> findByEstado(String estado); 

    // 2. FILTRO POR TÉCNICO (Busca en la relación ManyToOne a Personal)
    List<Tickets> findByTecnicoNombreCompleto(String nombreCompleto);

    // 3. FILTRO POR CIUDAD / ESTADO DE LA REPÚBLICA (Busca en la relación OneToMany a Adicionales)
    @Query("SELECT DISTINCT t FROM Ticket t JOIN t.adicionales a WHERE a.ciudad = :ciudad")
    List<Tickets> findByAdicionalesCiudad(@Param("ciudad") String ciudad);
    
    // 4. FILTRO POR SLA (Busca en la relación OneToMany a Servicios)
    @Query("SELECT DISTINCT t FROM Ticket t JOIN t.servicios s WHERE s.sla = :sla")
    List<Tickets> findByServiciosSla(@Param("sla") Integer sla);

    // 5. FILTRO POR FECHA DE ASIGNACIÓN (Busca en la relación OneToMany a Servicios)
    @Query("SELECT DISTINCT t FROM Ticket t JOIN t.servicios s WHERE s.fechaDeAsignacion = :fechaAsignacion")
    List<Tickets> findByServiciosFechaAsignacion(@Param("fechaAsignacion") LocalDate fechaAsignacion);

    // 6. FILTRO POR FECHA DE ENVÍO (Busca en la relación OneToMany a Servicios)
    @Query("SELECT DISTINCT t FROM Ticket t JOIN t.servicios s WHERE s.fechaDeEnvio = :fechaEnvio")
    List<Tickets> findByServiciosFechaEnvio(@Param("fechaEnvio") LocalDate fechaEnvio);
    
    // 7. FILTRO POR SITUACIÓN ACTUAL (Busca en la relación OneToMany a Servicios)
    @Query("SELECT DISTINCT t FROM Ticket t JOIN t.servicios s WHERE s.situacionActual = :situacionActual")
    List<Tickets> findByServiciosSituacionActual(@Param("situacionActual") String situacionActual);
    
    @Query("SELECT DISTINCT t FROM Ticket t JOIN t.servicios s WHERE s.tipoDeServicio = :tipoServicio")
    List<Tickets> findByServiciosTipoDeServicio(@Param("tipoServicio") String tipoServicio);

}