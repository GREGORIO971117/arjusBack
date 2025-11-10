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

        String numeroIncidencia = inventarioGuardado.getNumeroDeIncidencia();

        if (numeroIncidencia != null && !numeroIncidencia.isEmpty()) {
            vincularTicketsExistentes(inventarioGuardado, numeroIncidencia);
        }
        
        return inventarioGuardado;
    }
    
 // Dentro de InventarioService.java

 // Método privado para manejar la lógica de asignación
 private void vincularTicketsExistentes(Inventario inventarioActualizado, String numeroIncidencia) {
     
     if (numeroIncidencia == null || numeroIncidencia.isEmpty()) {
         return;
     }
     
     // 1. BUSCAR TICKETS por la incidencia
     List<Tickets> ticketsEncontrados = ticketService.findTicketsByIncidencia(numeroIncidencia);
     
     // 2. Iterar y Crear la Relación (PivoteInventario)
     for (Tickets ticket : ticketsEncontrados) {
         
         // **IMPORTANTE: Evitar duplicados**
         // Si ya hay un PivoteInventario para este ticket y este inventario, saltar.
         boolean alreadyLinked = ticket.getPivoteInventario().stream()
             .anyMatch(p -> p.getInventario() != null && 
                            p.getInventario().getIdInventario().equals(inventarioActualizado.getIdInventario()));
         
         if (alreadyLinked) {
             continue; // Ya están vinculados, pasa al siguiente ticket
         }
         
         // Crea la entidad pivote
         PivoteInventario pivote = new PivoteInventario();
         pivote.setInventario(inventarioActualizado); 
         pivote.setTicket(ticket);
         pivote.setCantidad(1); 
         pivote.setFechaAsignacion(LocalDate.now());

         // 3. Añadir el pivote a la colección del Ticket
         ticket.getPivoteInventario().add(pivote);

         // 4. PERSISTIR el Ticket actualizado (Usando TicketRepository para evitar el ciclo)
         ticketRepository.save(ticket);
     }
 }

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
	    String oldIncidencia = inventarioExistente.getNumeroDeIncidencia();
	    
	    boolean incidenciaChanged = false;
	    
	    if (newIncidencia != null && !newIncidencia.equals(oldIncidencia)) {
	        inventarioExistente.setNumeroDeIncidencia(newIncidencia);
	        incidenciaChanged = true;
	    }

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
        
        // 3. LÓGICA DE VINCULACIÓN: Solo si el campo de incidencia fue cambiado en esta operación.
        if (incidenciaChanged) {
            vincularTicketsExistentes(inventarioActualizado, inventarioActualizado.getNumeroDeIncidencia());
        }
        
        return inventarioActualizado;
    }
    
    

}
