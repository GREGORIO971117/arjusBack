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
        
        // 1. Obtener el Ticket principal
        Tickets ticket = ticketService.getTicketsById(id);
        
        if (ticket == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        try {
            // 2. Llamar al servicio para generar los bytes del documento (Esta parte se mantiene igual)
            byte[] documentBytes = documentGenerationService.generateTicketDocument(ticket);

            // =========================================================================
            // 3. CONFIGURACIÓN DEL NOMBRE Y ENCABEZADO (LA CORRECCIÓN ESTÁ AQUÍ)
            // =========================================================================
            
            String incidencia = ticket.getServicios().getIncidencia();
            String nombreEss = ticket.getServicios().getNombreDeEss();

            // 3.1. LIMPIEZA DEL NOMBRE: Eliminar o reemplazar caracteres no seguros para nombres de archivo
            // Reemplazamos los espacios con guiones bajos y eliminamos cualquier otro carácter especial.
            String nombreLimpio = incidencia + "_" + nombreEss;
            nombreLimpio = nombreLimpio.replaceAll("[^a-zA-Z0-9\\s_-]", "").replaceAll("\\s+", "_");
            
            // El nombre final que se intenta enviar.
            final String filename = nombreLimpio + ".docx"; 

            // 3.2. CODIFICACIÓN: Se usa para el formato filename* (RFC 5987)
            String encodedFilename = java.net.URLEncoder.encode(filename, "UTF-8").replaceAll("\\+", "%20");


            HttpHeaders headers = new HttpHeaders();
            // Tipo de contenido para .docx
            headers.setContentType(MediaType.valueOf("application/vnd.openxmlformats-officedocument.wordprocessingml.document"));
            
            headers.add(HttpHeaders.CONTENT_DISPOSITION, 
                        "attachment; filename=\"" + filename + "\"; filename*=UTF-8''" + encodedFilename); 
            
            headers.setContentLength(documentBytes.length);

            // 4. Devolver los bytes al navegador
            return new ResponseEntity<>(documentBytes, headers, HttpStatus.OK);

        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR); 
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