package com.arjusven.backend.controller;

import com.arjusven.backend.model.Estaciones;
import com.arjusven.backend.service.EstacionesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/estaciones")
public class EstacionesController {

    private final EstacionesService estacionesService;

    @Autowired
    public EstacionesController(EstacionesService estacionesService) {
        this.estacionesService = estacionesService;
    }

    @GetMapping
    public ResponseEntity<List<Estaciones>> getAllEstaciones() {
        List<Estaciones> estaciones = estacionesService.getAllEstaciones();
        return ResponseEntity.ok(estaciones);
    }

    
    @GetMapping("/{idMerchant}")
    public ResponseEntity<Estaciones> getEstacionesById(@PathVariable Long idMerchant) {
        Optional<Estaciones> estacion = estacionesService.findById(idMerchant);
        
        if (estacion.isPresent()) { 
            return ResponseEntity.ok(estacion.get()); 
        } else {
            return ResponseEntity.notFound().build();
        }
    }

 
    @PostMapping
    public ResponseEntity<Estaciones> createEstaciones(@RequestBody Estaciones estacion) {
        Estaciones nuevaEstacion = estacionesService.saveEstaciones(estacion);
        return new ResponseEntity<>(nuevaEstacion, HttpStatus.CREATED);
    }

 
    @PutMapping("/{idMerchant}")
    public ResponseEntity<Estaciones> updateEstaciones(@PathVariable Long idMerchant, @RequestBody Estaciones estacionDetails) {
        Optional<Estaciones> estacionOptional = estacionesService.findById(idMerchant);
        
        if (estacionOptional.isPresent()) {            
            estacionDetails.setIdMerchant(idMerchant); 
            
            Estaciones estacionActualizada = estacionesService.saveEstaciones(estacionDetails);
            
            return ResponseEntity.ok(estacionActualizada);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    
    @DeleteMapping("/{idMerchant}")
    public ResponseEntity<Void> deleteEstaciones(@PathVariable Long idMerchant) {
        Optional<Estaciones> estacionOptional = estacionesService.findById(idMerchant);

        if (estacionOptional.isPresent()) {
            try {
                // FALTA LLAMAR AL MÉTODO DE BORRADO DEL SERVICIO
                return ResponseEntity.noContent().build();
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }

  
}