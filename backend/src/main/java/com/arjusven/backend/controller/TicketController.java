package com.arjusven.backend.controller;

import com.arjusven.backend.model.Tickets;
import com.arjusven.backend.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    private final TicketService ticketService;

    @Autowired
    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    // ====================================================================
    // MÉTODOS GET
    // ====================================================================

    @GetMapping
    public ResponseEntity<List<Tickets>> getAllTickets() {
        List<Tickets> tickets = ticketService.findAll();
        if (tickets.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // Código 204
        }
        return new ResponseEntity<>(tickets, HttpStatus.OK); // Código 200
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tickets> getTicketById(@PathVariable("id") Integer id) {
        Optional<Tickets> ticket = ticketService.findById(id);
        
        if (ticket.isPresent()) {
            return new ResponseEntity<>(ticket.get(), HttpStatus.OK); // Código 200
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
            Tickets ticketGuardado = ticketService.crearNuevoTicket(nuevoTicket);
            
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
}