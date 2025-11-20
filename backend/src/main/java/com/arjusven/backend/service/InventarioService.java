package com.arjusven.backend.service;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.arjusven.backend.model.Inventario;
import com.arjusven.backend.model.PivoteInventario;
import com.arjusven.backend.model.Tickets;
import com.arjusven.backend.repository.InventarioRepository;
import com.arjusven.backend.repository.TicketRepository;

@Service
public class InventarioService {
	
    private InventarioRepository inventarioRepository;
    private TicketService ticketService; 
    private TicketRepository ticketRepository;
	
	
    @Autowired
    public InventarioService(InventarioRepository inventarioRepository, 
                             TicketService ticketService, 
                             TicketRepository ticketRepository) {
        this.inventarioRepository = inventarioRepository;
        this.ticketService = ticketService;
        this.ticketRepository = ticketRepository;
    }

    @Transactional 
    public Inventario saveInventario(Inventario inventario) {
        Inventario inventarioGuardado = inventarioRepository.save(inventario);
        return inventarioGuardado;
    }
    
 // Dentro de InventarioService.java


    // Method to find a user by ID
    public Inventario getInventarioById(Long id) {
        // Use orElse(null) or a custom exception handling for better practice
        return inventarioRepository.findById(id).orElse(null);
    }

    // Method to get all users (optional but good for testing)
    public List<Inventario> getAllInventario() {
        return inventarioRepository.findAll();
    }
    
    
	public Inventario deleteInventario(Long id) {
			
		Inventario inventario = null;
			if(inventarioRepository.existsById(id)) {
				inventario = inventarioRepository.findById(id).get();
				inventarioRepository.deleteById(id);
			}//if exists
			return inventario;
		}//deleteUsuarios
    
	
	@Transactional
	public Inventario patchInventario(Long id, Inventario inventarioDetails) {

		Inventario inventarioExistente = inventarioRepository.findById(id)
	            .orElseThrow(() -> new NoSuchElementException("El item de Inventario con el ID [" + id + "] no fue encontrado para actualizar."));

	    String newIncidencia = inventarioDetails.getNumeroDeIncidencia();
	    

        if (inventarioDetails.getTitulo() != null) {
            inventarioExistente.setTitulo(inventarioDetails.getTitulo());
        }
        
     
        if (inventarioDetails.getNumeroDeSerie() != null) {
            inventarioExistente.setNumeroDeSerie(inventarioDetails.getNumeroDeSerie());
        }

        if (inventarioDetails.getEquipo() != null) {
            inventarioExistente.setEquipo(inventarioDetails.getEquipo());
        }

        if (inventarioDetails.getEstado() != null) {
            inventarioExistente.setEstado(inventarioDetails.getEstado());
        }

        if (inventarioDetails.getResponsable() != null) {
            inventarioExistente.setResponsable(inventarioDetails.getResponsable());
        }

        if (inventarioDetails.getCliente() != null) {
            inventarioExistente.setCliente(inventarioDetails.getCliente());
        }

        if (inventarioDetails.getPlaza() != null) {
            inventarioExistente.setPlaza(inventarioDetails.getPlaza());
        }

        if (inventarioDetails.getTecnico() != null) {
            inventarioExistente.setTecnico(inventarioDetails.getTecnico());
        }

        if (inventarioDetails.getNumeroDeIncidencia() != null) {
            inventarioExistente.setNumeroDeIncidencia(inventarioDetails.getNumeroDeIncidencia());
        }

        if (inventarioDetails.getCodigoEmail() != null) {
            inventarioExistente.setCodigoEmail(inventarioDetails.getCodigoEmail());
        }

        if (inventarioDetails.getGuias() != null) {
            inventarioExistente.setGuias(inventarioDetails.getGuias());
        }

        if (inventarioDetails.getFechaDeInicioPrevista() != null) {
            inventarioExistente.setFechaDeInicioPrevista(inventarioDetails.getFechaDeInicioPrevista());
        }

        if (inventarioDetails.getFechaDeFinPrevista() != null) {
            inventarioExistente.setFechaDeFinPrevista(inventarioDetails.getFechaDeFinPrevista());
        }

        if (inventarioDetails.getFechaDeFin() != null) {
            inventarioExistente.setFechaDeFin(inventarioDetails.getFechaDeFin());
        }

        if (inventarioDetails.getUltimaActualizacion() != null) {
            inventarioExistente.setUltimaActualizacion(inventarioDetails.getUltimaActualizacion());
        }

        if (inventarioDetails.getDescripcion() != null) {
            inventarioExistente.setDescripcion(inventarioDetails.getDescripcion());
        }
        
        Inventario inventarioActualizado = inventarioRepository.save(inventarioExistente);     
      
        return inventarioActualizado;
    }
    
    

}
