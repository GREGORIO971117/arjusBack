// UsuariosService.java
package com.arjusven.backend.service;

import com.arjusven.backend.model.Servicio;
import com.arjusven.backend.repository.ServicioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServicioService {

    @Autowired
    private ServicioRepository servicioRepository;

    // Method to save a new user
    public Servicio saveServicio(Servicio servicio) {
        return servicioRepository.save(servicio);
    }

    // Method to find a user by ID
    public Servicio getServicioById(Long id) {
        // Use orElse(null) or a custom exception handling for better practice
        return servicioRepository.findById(id).orElse(null);
    }

    // Method to get all users (optional but good for testing)
    public List<Servicio> getAllServicio() {
        return servicioRepository.findAll();
    }
}