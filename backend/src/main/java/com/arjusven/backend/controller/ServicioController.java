// UsuariosController.java
package com.arjusven.backend.controller;

import com.arjusven.backend.model.Servicio;
import com.arjusven.backend.service.ServicioService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/servicio") // Base URL for all endpoints in this controller
public class ServicioController {

    @Autowired
    private ServicioService servicioService;

    // ENDPOINT: POST /api/usuarios
    // Purpose: Create a new user (saves data to the database)
    
    
    @PostMapping
    public ResponseEntity<Servicio> createServicio(@RequestBody Servicio servicio) {
    	Servicio savedServicio = servicioService.saveServicio(servicio);
        return new ResponseEntity<>(savedServicio, HttpStatus.CREATED);
    }

    // ENDPOINT: GET /api/usuarios/{id}
    // Purpose: Retrieve a user by ID (reads data from the database)
    
    
    @GetMapping
    public ResponseEntity<List<Servicio>> getAllServicio() {
        List<Servicio> adicional = servicioService.getAllServicio();
        if (adicional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // Código 204
        }
        return new ResponseEntity<>(adicional, HttpStatus.OK); // Código 200
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Servicio> getServicio(@PathVariable("id") Long id) {
    	Servicio servicio = servicioService.getServicioById(id);
        if (servicio != null) {
            return new ResponseEntity<>(servicio, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @PatchMapping(path="{idServicios}")
    public Servicio patchServicios(
        @PathVariable("idServicios") Long id,
        @RequestBody Servicio servicioDetails) {	
        return servicioService.patchServicio(id, servicioDetails);
    }
}
