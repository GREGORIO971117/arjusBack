package com.arjusven.backend.service;

import com.arjusven.backend.model.*;
import com.arjusven.backend.repository.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

// NOTA: Para un proyecto real, se deberían crear clases de excepción personalizadas 
// (ej. ResourceNotFoundException, InvalidRoleException) en lugar de RuntimeException.

@Service
public class TicketService {

    // Inyección de dependencias (Repositorios)
    public final TicketRepository ticketRepository;
    private final PersonalRepository personalRepository;
    // Puedes inyectar otros repositorios si los necesitas en futuras lógicas
    // private final InventarioRepository inventarioRepository;
    // private final AsignacionInventarioRepository asignacionInventarioRepository;
    
    @Autowired
    public TicketService(
        TicketRepository ticketRepository,
        PersonalRepository personalRepository) 
    {
        this.ticketRepository = ticketRepository;
        this.personalRepository = personalRepository;
    }

    // ====================================================================
    // 1. LÓGICA DE CREACIÓN DE TICKET CON VALIDACIONES
    // ====================================================================

    /**
     * Crea un nuevo Ticket, validando la existencia y rol del Técnico y Supervisor.
     * @param tecnicoId ID del usuario que será el Técnico.
     * @param supervisorId ID del usuario que será el Supervisor.
     * @param nuevoTicket Objeto Ticket con los demás datos.
     * @return El Ticket guardado.
     */
    public Tickets crearNuevoTicket(Long tecnicoId, Long supervisorId, Tickets nuevoTicket) {
        
        // --- 1. Validar y obtener el Técnico ---
        Usuarios tecnico = personalRepository.findById(tecnicoId)
            .orElseThrow(() -> new RuntimeException("Técnico no encontrado con ID: " + tecnicoId));
        
        if (!"TECNICO".equalsIgnoreCase(tecnico.getRol())) {
            throw new IllegalArgumentException("El usuario con ID " + tecnicoId + " no tiene el rol de Técnico.");
        }
        
        // --- 2. Validar y obtener el Supervisor ---
        Usuarios supervisor = personalRepository.findById(supervisorId)
            .orElseThrow(() -> new RuntimeException("Supervisor no encontrado con ID: " + supervisorId));
        
        if (!"SUPERVISOR".equalsIgnoreCase(supervisor.getRol())) {
            throw new IllegalArgumentException("El usuario con ID " + supervisorId + " no tiene el rol de Supervisor.");
        }

        // --- 3. Asignar las entidades al Ticket ---
        nuevoTicket.setTecnico(tecnico);
        nuevoTicket.setSupervisor(supervisor);
        
        // --- 4. Guardar ---
        return ticketRepository.save(nuevoTicket);
    }
    
    // Método de ejemplo para obtener un ticket por ID
    public Tickets obtenerTicketPorId(Long id) {
        return ticketRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Ticket no encontrado con ID: " + id));
    }


    // ====================================================================
    // 2. LÓGICA MAESTRA DE FILTRADO POR CRITERIOS
    // ====================================================================

    /**
     * Implementa la lógica de filtrado de tickets según el criterio proporcionado.
     * IMPORTANTE: Solo se ejecuta el primer filtro que NO sea nulo o vacío, 
     * priorizando los más específicos.
     */
    public List<Tickets> buscarTicketsPorCriterios(
        String estadoGeneral, 
        String nombreTecnico, 
        String ciudad, 
        String tipoServicio,
        Integer sla,
        LocalDate fechaAsignacion,
        LocalDate fechaEnvio,
        String situacionActual) 
    {
        
        // 1. Tipo de Servicio (Máxima prioridad de los Servicios)
        if (tipoServicio != null && !tipoServicio.isEmpty()) {
            return ticketRepository.findByServiciosTipoDeServicio(tipoServicio);
        }
        
        // 2. Situación Actual
        if (situacionActual != null && !situacionActual.isEmpty()) {
            return ticketRepository.findByServiciosSituacionActual(situacionActual);
        }
        
        // 3. Fecha de Envío
        if (fechaEnvio != null) {
            return ticketRepository.findByServiciosFechaEnvio(fechaEnvio);
        }
        
        // 4. Fecha de Asignación
        if (fechaAsignacion != null) {
            return ticketRepository.findByServiciosFechaAsignacion(fechaAsignacion);
        }
        
        // 5. SLA
        if (sla != null) {
            return ticketRepository.findByServiciosSla(sla);
        }
        
        // 6. Ciudad (Estado de la República)
        if (ciudad != null && !ciudad.isEmpty()) {
            return ticketRepository.findByAdicionalesCiudad(ciudad);
        }

        // 7. Nombre del Técnico
        if (nombreTecnico != null && !nombreTecnico.isEmpty()) {
            return ticketRepository.findByTecnicoNombreCompleto(nombreTecnico);
        }
        
        // 8. Estado General
        if (estadoGeneral != null && !estadoGeneral.isEmpty()) {
            return ticketRepository.findByEstado(estadoGeneral);
        }
        
        // Si no se proporciona ningún criterio, se devuelve todo
        return ticketRepository.findAll();
    }
    
    public List<Tickets> findAll() {
        return ticketRepository.findAll();
    }
}