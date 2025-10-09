package com.arjusven.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.arjusven.backend.model.Inventario;
import com.arjusven.backend.service.InventarioService;

@RestController
@RequestMapping("/api/inventario")
public class InventarioController {

	
	@Autowired
	private InventarioService inventarioService;
	
	@PostMapping
	public ResponseEntity<Inventario> createInventario(@RequestBody Inventario inventario){
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
}
