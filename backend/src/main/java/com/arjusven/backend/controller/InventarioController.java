package com.arjusven.backend.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.arjusven.backend.model.Inventario;
import com.arjusven.backend.service.InventarioService;

@RestController
@RequestMapping("/api/inventario")
public class InventarioController {

	@Autowired
	private InventarioService inventarioService;
	
	@GetMapping("/filter")
	public ResponseEntity<List<Inventario>> filterInventario(
	        @RequestParam(value = "estado", required = false) String estado,
	        @RequestParam(value = "plaza", required = false) String plaza,
	        @RequestParam(value = "equipo", required = false) String equipo,
	        @RequestParam(value = "fechaInicio", required = false) LocalDate fechaInicio, 
            @RequestParam(value = "fechaFin", required = false) LocalDate fechaFin	        
	        ){
		
		
	    String estadoFilter = null;
	    String equipoFilter = null;
	    
	    
	    if(estado != null && !"todos".equalsIgnoreCase(estado)) {
	        
	        String normalizedTipo = estado.trim();
	        estadoFilter = normalizedTipo; 
	    }else {
	        estadoFilter = null; 
	    }
	    
	    if(plaza != null && !"todos".equalsIgnoreCase(plaza)) {
	    	
	    	String normalizedTipo = plaza.trim();
	    	plaza = normalizedTipo;
	    }else {
	    	plaza = null;
	    }
	    
	    if(equipo != null && !"todos".equalsIgnoreCase(equipo)) {
	    	
	    	String normalizedTipo = equipo.trim();
	    	equipoFilter = normalizedTipo;
	    }else {
	    	equipoFilter = null;
	    }
	    
	    // 4. Aplicar los filtros
	    List<Inventario> filteredInventario = inventarioService.filterInventario(estadoFilter,plaza,equipoFilter,fechaInicio, fechaFin );
	    if (filteredInventario == null || filteredInventario.isEmpty()) {
	        return ResponseEntity.noContent().build();
	    }
	    return ResponseEntity.ok(filteredInventario);
	}
	
	
	
	@GetMapping("/search")
	public ResponseEntity<List<Inventario>> searchInventario(@RequestParam("query") String query){
		if(query == null || query.trim().isEmpty()) {
			return getAllInventario();
		}
		
		List<Inventario> resultados = inventarioService.searchInventario(query);
		
		if (resultados.isEmpty()) {
			return new ResponseEntity<>(resultados,HttpStatus.OK);	
			}
		return new ResponseEntity<>(resultados,HttpStatus.OK);
	}	
	
	@PostMapping
	public ResponseEntity<Inventario> createInventario(@RequestBody Inventario inventario){
		
		inventario.setEstado("Para instalar");
		Inventario savedInventario = inventarioService.saveInventario(inventario);
	
		return new ResponseEntity<>(savedInventario, HttpStatus.CREATED);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Inventario> getUsuario(@PathVariable("id") Long id){
		Inventario inventario = inventarioService.getInventarioById(id);
		if (inventario != null) {
            return new ResponseEntity<>(inventario, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
	}
	
	@GetMapping
    public ResponseEntity<List<Inventario>> getAllInventario() {
        List<Inventario> inventario = inventarioService.getAllInventario();
        if (inventario.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // Código 204
        }
        return new ResponseEntity<>(inventario, HttpStatus.OK); // Código 200
    }	
	
	 @DeleteMapping(path="{idInventario}")
		public Inventario delInventario(@PathVariable ("idInventario") Long id) {
			return inventarioService.deleteInventario(id);
		}

	@PatchMapping(path="{idInventario}")
    public Inventario patchUsuario(
        @PathVariable("idInventario") Long id,
        @RequestBody Inventario inventarioDetails) {	
        return inventarioService.patchInventario(id, inventarioDetails);
    }
	
	
}
