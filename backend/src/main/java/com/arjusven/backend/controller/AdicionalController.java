// UsuariosController.java
package com.arjusven.backend.controller;

import com.arjusven.backend.model.Adicional;
import com.arjusven.backend.service.AdicionalService;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/adicional") // Base URL for all endpoints in this controller
public class AdicionalController {

    @Autowired
    private AdicionalService adicionalService;
    
    
    @GetMapping
    public ResponseEntity<List<Adicional>> getAllAdicional() {
        List<Adicional> adicional = adicionalService.getAllAdicional();
        if (adicional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // Código 204
        }
        return new ResponseEntity<>(adicional, HttpStatus.OK); // Código 200
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
    
    @PatchMapping(path="{idAdicionales}")
    public ResponseEntity<?> patchAdicionales(@PathVariable("idAdicionales") Long id, @RequestBody Adicional adicionalDetails) {
        try {
            // Llamamos al servicio que TIENE la validación
            Adicional actualizado = adicionalService.patchAdicionales(id, adicionalDetails);
            return ResponseEntity.ok(actualizado);

        } catch (IllegalArgumentException e) {
            // IMPORTANTE: Devolver el error 400 con el mensaje exacto para que el Front lo pinte en rojo
            return ResponseEntity
                    .badRequest()
                    .body(Collections.singletonMap("error", e.getMessage()));
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("error", "Error interno: " + e.getMessage()));
        }
    }
}