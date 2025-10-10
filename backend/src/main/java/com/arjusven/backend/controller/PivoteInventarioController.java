package com.arjusven.backend.controller;

import com.arjusven.backend.model.PivoteInventario;
import com.arjusven.backend.service.PivoteService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pivote")
public class PivoteInventarioController {

    private final PivoteService pivoteInventarioService;

    @Autowired
    public PivoteInventarioController(PivoteService pivoteInventarioService) {
        // Asignamos la instancia inyectada a la variable final.
        this.pivoteInventarioService = pivoteInventarioService;
    }

    @PostMapping
    public ResponseEntity<PivoteInventario> createPivote(@RequestBody PivoteInventario pivote) {
        // Usamos el servicio para guardar la entidad
        PivoteInventario savedPivote = pivoteInventarioService.save(pivote);
        return new ResponseEntity<>(savedPivote, HttpStatus.CREATED);
    }

    @GetMapping
    public List<PivoteInventario> getAllPivotes() {
        return pivoteInventarioService.findAll();
    }
}
