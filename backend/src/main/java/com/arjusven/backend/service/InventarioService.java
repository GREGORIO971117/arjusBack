package com.arjusven.backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.arjusven.backend.model.Inventario;
import com.arjusven.backend.repository.InventarioRepository;

@Service
public class InventarioService {
	
	@Autowired
    private InventarioRepository inventarioRepository;

    // Method to save a new user
    public Inventario saveInventario(Inventario inventario) {
        return inventarioRepository.save(inventario);
    }

    // Method to find a user by ID
    public Inventario getInventarioById(Long id) {
        // Use orElse(null) or a custom exception handling for better practice
        return inventarioRepository.findById(id).orElse(null);
    }

    // Method to get all users (optional but good for testing)
    public List<Inventario> getAllInventario() {
        return inventarioRepository.findAll();
    }

}
