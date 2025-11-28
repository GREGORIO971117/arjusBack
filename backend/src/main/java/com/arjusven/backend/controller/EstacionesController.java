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
    
    @GetMapping("/filter")
    public ResponseEntity<List<Estaciones>> filterEstaciones(
    		
        // ✅ CORRECCIÓN: Añadir required = false
    	@RequestParam(value = "supervisorArjus", required = false) String supervisorArjus, 
    	@RequestParam(value = "estado", required = false) String estado,
    	@RequestParam(value = "cobertura", required = false) String cobertura,
    	@RequestParam(value = "plazaDeAtencion", required = false) String plazaDeAtencion){ 

    	
    		String supervisorArjusFilter = null;
    		String estadoFilter = null;
    		String coberturaFilter = null;
    		String plazaDeAtencionFilter = null;
    		
    		if(supervisorArjus != null && !"todos".equalsIgnoreCase(supervisorArjus)) {
    	    	
    	    	String normalizedTipo = supervisorArjus.trim();
    	    	supervisorArjusFilter = normalizedTipo;
    	    }else {
    	    	supervisorArjusFilter = null;
    	    }
    		
    		
    		if(estado != null && !"todos".equalsIgnoreCase(estado)) {
    	    	
    	    	String normalizedTipo = estado.trim();
    	    	estadoFilter = normalizedTipo;
    	    }else {
    	    	estadoFilter = null;
    	    }
    		
    		if(cobertura != null && !"todos".equalsIgnoreCase(cobertura)) {
    	    	
    	    	String normalizedTipo = cobertura.trim();
    	    	coberturaFilter = normalizedTipo;
    	    }else {
    	    	coberturaFilter = null;
    	    }
    		
    		if(plazaDeAtencion != null && !"todos".equalsIgnoreCase(plazaDeAtencion)) {
    	    	
    	    	String normalizedTipo = plazaDeAtencion.trim();
    	    	plazaDeAtencionFilter = normalizedTipo;
    	    }else {
    	    	plazaDeAtencionFilter = null;
    	    }
    		
    		List<Estaciones> filteredEstaciones = estacionesService.filterEstaciones(supervisorArjusFilter, estadoFilter, coberturaFilter, plazaDeAtencionFilter);
    	    
    	    if (filteredEstaciones == null || filteredEstaciones.isEmpty()) {
    	        return ResponseEntity.noContent().build();
    	    }
    	    
    	    return ResponseEntity.ok(filteredEstaciones);
    	}

    @GetMapping
    public ResponseEntity<List<Estaciones>> getAllEstaciones() {
        List<Estaciones> estaciones = estacionesService.getAllEstaciones();
        return ResponseEntity.ok(estaciones);
    }
    
    @GetMapping("/search")
    public ResponseEntity<List<Estaciones>> searchEstaciones(@RequestParam("query") String query) {
        if (query == null || query.trim().isEmpty()) {
             return getAllEstaciones();
        }
        List<Estaciones> resultados = estacionesService.searchEstacionesSmart(query);

        if (resultados.isEmpty()) {
            // Opción A: Devolver 204 No Content (la lista en front se vacía)
            // Opción B: Devolver 200 con lista vacía (más fácil para React)
            return new ResponseEntity<>(resultados, HttpStatus.OK);
        }
        return new ResponseEntity<>(resultados, HttpStatus.OK);
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
  
    
    @PatchMapping("/{idMerchant}")
    public ResponseEntity<Estaciones> updateEstacionesPatch(
    		@PathVariable("idMerchant") Long idMerchant, 
            @RequestBody Estaciones updatedEstacion) {
        
        Estaciones result = estacionesService.updateEstacionesPatch(idMerchant, updatedEstacion);
        
        if (result != null) {
            return ResponseEntity.ok(result); 
        } else {
            return ResponseEntity.notFound().build(); 
        }
    }
    
    @DeleteMapping("/{idMerchant}")
    public ResponseEntity<Void> deleteEstaciones(@PathVariable("idMerchant") Long idMerchant) {
        Optional<Estaciones> estacionOptional = estacionesService.findById(idMerchant);

        if (estacionOptional.isPresent()) {
            try {
                // Se realiza la llamada al método de borrado del servicio
                estacionesService.deleteEstaciones(idMerchant); 
                
                // Retorna 204 No Content si el borrado fue exitoso
                return ResponseEntity.noContent().build();
            } catch (Exception e) {
                // Logear la excepción para debug (recomendado)
                System.err.println("Error al intentar borrar la estación con ID: " + idMerchant + ". Causa: " + e.getMessage());
                
                // Retorna 500 Internal Server Error si falla el borrado
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        } else {
            // Retorna 404 Not Found si la estación no existe
            return ResponseEntity.notFound().build();
        }
    }
  
}