package com.arjusven.backend.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.arjusven.backend.model.Tickets;
import com.arjusven.backend.repository.TicketRepository;

import jakarta.transaction.Transactional;

import java.util.List;

@Service
public class TicketService {
    
    private TicketRepository ticketsRepository;
    private ServicioService servicioService;
    
    @Autowired
    public TicketService(TicketRepository ticketsRepository, ServicioService servicioService) {
		this.ticketsRepository = ticketsRepository;
		this.servicioService = servicioService;
	}

    @Transactional
    public Tickets saveTickets(Tickets tickets) {
    	if(tickets.getServicios() != null) {
    		servicioService.assignEstacionesDetails(tickets.getServicios());
    	}
    	
        return ticketsRepository.save(tickets);
    }

    // Method to find a user by ID
    public Tickets getTicketsById(Long id) { // <-- Change return type to Optional<Tickets>
        return ticketsRepository.findById(id).orElseThrow(
        		()->new IllegalArgumentException("El ticket con el id" + id+ "no existe")
        		); 
    }

    public List<Tickets> getAllTickets() {
        return ticketsRepository.findAll();
    }
    
    public List<Tickets> findTicketsByIncidencia(String incidencia) {
        return ticketsRepository.findByServicios_Incidencia(incidencia);
    }
    
    public Tickets saveTicketsOnly(Tickets tickets) {
        return ticketsRepository.save(tickets);
    }
    
    public Tickets deleteTickets(Long id) {
    	Tickets ticket=null;
    	if(ticketsRepository.existsById(id)) {
    		ticket=ticketsRepository.findById(id).get();
    		ticketsRepository.deleteById(id);
    	}
    	return ticket;
    }
    
    
}