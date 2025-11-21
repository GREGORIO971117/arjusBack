package com.arjusven.backend.controller;

import com.arjusven.backend.model.PivoteInventario;
import com.arjusven.backend.service.InventarioService;
import com.arjusven.backend.service.PivoteService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pivote")
public class PivoteInventarioController {

    private PivoteService pivoteInventarioService;
    private InventarioService inventarioService;

    @Autowired
    public PivoteInventarioController(
    		PivoteService pivoteInventarioService,
    		InventarioService inventarioService 
    		) {
    	this.pivoteInventarioService = pivoteInventarioService;
    	this.inventarioService = inventarioService;
    }

    @PostMapping
    public ResponseEntity<PivoteInventario> createPivote(@RequestBody PivoteInventario pivote) {
    	try {
    		PivoteInventario savedPivote = pivoteInventarioService.save(pivote);
            return new ResponseEntity<>(savedPivote, HttpStatus.CREATED);
    		
    	}catch (IllegalArgumentException e) {
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
    
    @GetMapping("/historial/{id}")
    public ResponseEntity<List<PivoteInventario>> getHistorialInventario(@PathVariable("id") Long id) {
        try {
        	
        	
            List<PivoteInventario> historial = inventarioService.obtenerHistorialPorInventario(id);
            
            if (historial.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            
            return ResponseEntity.ok(historial);
            
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping
    public List<PivoteInventario> getAllPivotes() {
        return pivoteInventarioService.findAll();
    }
}
