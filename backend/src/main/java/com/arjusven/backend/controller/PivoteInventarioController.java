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
    	this.pivoteInventarioService = pivoteInventarioService;
    }

    @PostMapping
    public ResponseEntity<PivoteInventario> createPivote(@RequestBody PivoteInventario pivote) {

    	PivoteInventario savedPivote = pivoteInventarioService.save(pivote);
        return new ResponseEntity<>(savedPivote, HttpStatus.CREATED);
    }
    
    @GetMapping("/{idInventario}")
    public ResponseEntity<List<PivoteInventario>> getHistorialInventario(@PathVariable("idInventario") Long idInventario) {
        List<PivoteInventario> historial = pivoteInventarioService.getHistorialByInventarioId(idInventario);
        return ResponseEntity.ok(historial);
    }

    @GetMapping
    public List<PivoteInventario> getAllPivotes() {
        return pivoteInventarioService.findAll();
    }
}
