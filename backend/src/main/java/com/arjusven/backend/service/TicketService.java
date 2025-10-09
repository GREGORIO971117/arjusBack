package com.arjusven.backend.service;

import com.arjusven.backend.model.Tickets;
import com.arjusven.backend.repository.TicketRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class TicketService {

    private final TicketRepository ticketRepository;
    
    // Solo inyectamos el repositorio de Tickets (eliminamos UsuariosRepository)
    @Autowired
    public TicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    // ====================================================================
    // 1. LÓGICA DE CREACIÓN (POST) - MÍNIMA
    // ====================================================================

    /**
     * Guarda el Ticket directamente. La validación de IDs/Roles la hará la DB.
     */
    @Transactional
    public Tickets crearNuevoTicket(Tickets nuevoTicket) {
        
        // ¡Única línea de lógica! Dejar que Jackson haga el mapeo y JPA la inserción.
        return ticketRepository.save(nuevoTicket);
    }
    
    // ====================================================================
    // 2. LÓGICA DE CONSULTA (GET)
    // ====================================================================

    /**
     * @return Todos los tickets.
     */
    public List<Tickets> findAll() {
        return ticketRepository.findAll();
    }
    
    /**
     * @param id ID del ticket a buscar.
     * @return Un ticket específico.
     */
    public Optional<Tickets> findById(Integer id) {
        // Asumiendo que tu repositorio usa Integer para findById, como lo tenías.
        return ticketRepository.findById(id); 
    }
}