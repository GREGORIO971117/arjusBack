package com.arjusven.backend.controller;

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
    public ResponseEntity<byte[]> downloadTicketDocx(
            @PathVariable("id") Long id,
            @RequestParam(name = "type", defaultValue = "intercambio") String type) { // Acepta el parámetro 'type'
        
        // 1. Obtener el Ticket principal
        Tickets ticket = ticketService.getTicketsById(id);
        
        if (ticket == null || ticket.getServicios() == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        byte[] documentBytes;
        String templateName;

        try {
            // 2. SELECCIÓN DE PLANTILLA Y GENERACIÓN DE BYTES
            switch (type.toLowerCase()) {
                case "mantenimiento":
                    documentBytes = documentGenerationService.generateMantenimientoTicket(ticket);
                    templateName = "Mantenimiento";
                    break;
                case "retiro":
                    documentBytes = documentGenerationService.generateRetiroTicket(ticket);
                    templateName = "Retiro";
                    break;
                case "ticket":
                case "intercambio":
                default:
                    documentBytes = documentGenerationService.generateDefaultTicket(ticket);
                    templateName = "Intercambio";
                    break;
            }

            // 3. CONFIGURACIÓN DEL NOMBRE Y ENCABEZADO
            
            String incidencia = ticket.getServicios().getIncidencia();
            String nombreEss = ticket.getServicios().getNombreDeEss();
            
            // 3.1. Limpieza y Creación del nombre de archivo
            // Incluimos el tipo de plantilla en el nombre
            String baseName = incidencia + "_" + nombreEss + "_" + templateName; 
            
            // Eliminación y reemplazo de caracteres
            String nombreLimpio = baseName.replaceAll("[^a-zA-Z0-9\\s_-]", "").replaceAll("\\s+", "_");
            
            final String filename = nombreLimpio + ".docx"; 

            // 3.2. Codificación para el Content-Disposition
            String encodedFilename = java.net.URLEncoder.encode(filename, "UTF-8").replaceAll("\\+", "%20");


            HttpHeaders headers = new HttpHeaders();
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