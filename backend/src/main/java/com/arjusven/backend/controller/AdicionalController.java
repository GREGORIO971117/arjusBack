// UsuariosController.java
package com.arjusven.backend.controller;

import com.arjusven.backend.model.Adicional;
import com.arjusven.backend.service.AdicionalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/adicional") // Base URL for all endpoints in this controller
public class AdicionalController {

    @Autowired
    private AdicionalService adicionalService;

    @PostMapping
    public ResponseEntity<Adicional> createAdicional(@RequestBody Adicional adicional) {
    	Adicional savedAdicional = adicionalService.saveAdicional(adicional);
        return new ResponseEntity<>(savedAdicional, HttpStatus.CREATED);
    }

    // ENDPOINT: GET /api/usuarios/{id}
    // Purpose: Retrieve a user by ID (reads data from the database)
    @GetMapping("/{id}")
    public ResponseEntity<Adicional> getUsuario(@PathVariable("id") Long id) {
    	Adicional adicional = adicionalService.getAdicionalById(id);
        if (adicional != null) {
            return new ResponseEntity<>(adicional, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}