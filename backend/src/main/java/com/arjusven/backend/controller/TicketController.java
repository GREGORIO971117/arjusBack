package com.arjusven.backend.controller;

import com.arjusven.backend.model.Tickets;
import com.arjusven.backend.service.TicketService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/tickets") // URI base para todos los endpoints de tickets
@CrossOrigin(origins = "*") // Permite peticiones desde cualquier origen (ajustar en producción)
public class TicketController {

    private final TicketService ticketService;

    @Autowired
    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    // ====================================================================
    // 1. ENDPOINT DE CREACIÓN DE TICKETS (POST)
    // ====================================================================

    /**
     * Crea un nuevo ticket y realiza las validaciones de roles de Técnico y Supervisor 
     * definidas en la capa de Service.
     * * Ejemplo de uso: 
     * POST /api/tickets?tecnicoId=1&supervisorId=5
     * con el cuerpo JSON del Ticket.
     */
    @PostMapping
    public ResponseEntity<Tickets> crearTicket(
            // Obtenemos los IDs desde los parámetros de la URL
            @RequestParam Long tecnicoId,
            @RequestParam Long supervisorId,
            // Obtenemos los datos del ticket desde el cuerpo de la petición (JSON)
            @RequestBody Tickets nuevoTicket) 
    {
        try {
            Tickets ticketCreado = ticketService.crearNuevoTicket(tecnicoId, supervisorId, nuevoTicket);
            // Devuelve el ticket creado con el estado 201 CREATED
            return new ResponseEntity<>(ticketCreado, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            // Manejo de errores de negocio (ej. rol incorrecto)
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (RuntimeException e) {
            // Manejo de errores (ej. técnico/supervisor no encontrado)
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }


    // ====================================================================
    // 2. ENDPOINT DE BÚSQUEDA Y FILTRADO DE TICKETS (GET)
    // ====================================================================

    /**
     * Busca tickets aplicando múltiples criterios de filtrado. 
     * Todos los parámetros son opcionales y se pasan vía query parameters.
     * * Ejemplo de uso: 
     * GET /api/tickets/buscar?estadoGeneral=CERRADO&nombreTecnico=Juan Perez&sla=24
     */
    @GetMapping("/buscar")
    public ResponseEntity<List<Tickets>> buscarTickets(
            @RequestParam(required = false) String estadoGeneral, 
            @RequestParam(required = false) String nombreTecnico, 
            @RequestParam(required = false) String ciudad, 
            @RequestParam(required = false) String tipoServicio,
            @RequestParam(required = false) Integer sla,
            @RequestParam(required = false) LocalDate fechaAsignacion,
            @RequestParam(required = false) LocalDate fechaEnvio,
            @RequestParam(required = false) String situacionActual) 
    {
        
        List<Tickets> ticketsEncontrados = ticketService.buscarTicketsPorCriterios(
            estadoGeneral, 
            nombreTecnico, 
            ciudad, 
            tipoServicio,
            sla,
            fechaAsignacion,
            fechaEnvio,
            situacionActual
        );
        
        // Devuelve la lista de tickets encontrados con el estado 200 OK
        return new ResponseEntity<>(ticketsEncontrados, HttpStatus.OK);
    }
    
    // Si quieres un endpoint para ver TODOS los tickets sin filtros
    @GetMapping
    public ResponseEntity<List<Tickets>> obtenerTodos() {
        return new ResponseEntity<>(ticketService.findAll(), HttpStatus.OK);
    }
}
