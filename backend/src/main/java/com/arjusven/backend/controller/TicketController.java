package com.arjusven.backend.controller;

import com.arjusven.backend.model.Adicional;
import com.arjusven.backend.model.Servicio;
import com.arjusven.backend.model.Tickets;
import com.arjusven.backend.service.DocumentGenerationService;
import com.arjusven.backend.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    private final TicketService ticketService;
    private final DocumentGenerationService documentGenerationService;

    @Autowired
    public TicketController(TicketService ticketService, DocumentGenerationService documentGenerationService) {
        this.ticketService = ticketService;
        this.documentGenerationService =documentGenerationService;
    }
    
    @GetMapping("/download/{id}")
    public ResponseEntity<byte[]> downloadTicketDocx(@PathVariable("id") Long id) {
        
        // 1. Obtener el Ticket principal que contiene Servicio y Adicional
        Tickets ticket = ticketService.getTicketsById(id);
        
        if (ticket == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 404 si el ticket no existe
        }
        
        // CRÍTICO: Obtener las entidades relacionadas desde el ticket
        // Estos Getters deben existir en la clase Tickets (getServicio, getAdicionales)
        Servicio servicio = ticket.getServicios();
        Adicional adicional = ticket.getAdicionales(); 
        
        if (servicio == null || adicional == null) {
             // 404 o 400 si faltan datos clave para generar la plantilla
             return new ResponseEntity<>(HttpStatus.BAD_REQUEST); 
        }

        try {
            // 2. Llamar al servicio para generar los bytes del documento
            byte[] documentBytes = documentGenerationService.generateTicketDocument(servicio, adicional);

            // 3. Configurar la respuesta HTTP para la descarga
            String filename = "Ticket_Servicio_" + servicio.getIncidencia() + ".docx";

            HttpHeaders headers = new HttpHeaders();
            // Tipo de contenido para .docx
            headers.setContentType(MediaType.valueOf("application/vnd.openxmlformats-officedocument.wordprocessingml.document"));
            // Indicar al navegador que es una descarga
            headers.setContentDispositionFormData("attachment", filename); 
            headers.setContentLength(documentBytes.length);

            // 4. Devolver los bytes al navegador
            return new ResponseEntity<>(documentBytes, headers, HttpStatus.OK);

        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR); // 500 en caso de error de generación
        }
    }
    
    
    

    @GetMapping
    public ResponseEntity<List<Tickets>> getAllTickets() {
        List<Tickets> tickets = ticketService.getAllTickets();
        if (tickets.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // Código 204
        }
        return new ResponseEntity<>(tickets, HttpStatus.OK); // Código 200
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tickets> getTicketById(@PathVariable("id") Long id) {
        Tickets ticket = ticketService.getTicketsById(id);
        
        if (ticket!=null) {
            return new ResponseEntity<>(ticket, HttpStatus.OK); // Código 200
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Código 404
        }
    }
    
    // ====================================================================
    // MÉTODO POST
    // ====================================================================

    @PostMapping
    public ResponseEntity<Tickets> createTicket(@RequestBody Tickets nuevoTicket) {
        try {
            // Llama al servicio, que maneja la validación de los 4 IDs de Usuario.
            Tickets ticketGuardado = ticketService.saveTickets(nuevoTicket);
            
            // Retorna el ticket creado con el ID generado por la DB.
            return new ResponseEntity<>(ticketGuardado, HttpStatus.CREATED); // Código 201

        } catch (IllegalArgumentException e) {
            // Error de lógica, como rol incorrecto o ID nulo
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // Código 400
        } catch (RuntimeException e) {
            // Error de DB, como usuario no encontrado
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Código 404
        } catch (Exception e) {
            // Error inesperado
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); // Código 500
        }
    }
    
    
    @DeleteMapping(path="{idTickets}")
	public Tickets deleteUsuario(@PathVariable ("idTickets") Long id) {
		return ticketService.deleteTickets(id);
	}
}