// UsuariosController.java
package com.arjusven.backend.controller;

import com.arjusven.backend.model.Usuarios;
import com.arjusven.backend.service.UsuariosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/usuarios") // Base URL for all endpoints in this controller
public class UsuariosController {

    @Autowired
    private UsuariosService usuariosService;

    // ENDPOINT: POST /api/usuarios
    // Purpose: Create a new user (saves data to the database)
    @PostMapping
    public ResponseEntity<Usuarios> createUsuario(@RequestBody Usuarios usuario) {
        Usuarios savedUsuario = usuariosService.saveUsuario(usuario);
        return new ResponseEntity<>(savedUsuario, HttpStatus.CREATED);
    }

    // ENDPOINT: GET /api/usuarios/{id}
    // Purpose: Retrieve a user by ID (reads data from the database)
    @GetMapping("/{id}")
    public ResponseEntity<Usuarios> getUsuario(@PathVariable("id") Long id) {
        Usuarios usuario = usuariosService.getUsuarioById(id);
        if (usuario != null) {
            return new ResponseEntity<>(usuario, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}