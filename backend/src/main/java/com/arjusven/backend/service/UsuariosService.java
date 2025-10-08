// UsuariosService.java
package com.arjusven.backend.service;

import com.arjusven.backend.model.Usuarios;
import com.arjusven.backend.repository.UsuariosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuariosService {

    @Autowired
    private UsuariosRepository usuariosRepository;

    // Method to save a new user
    public Usuarios saveUsuario(Usuarios usuario) {
        return usuariosRepository.save(usuario);
    }

    // Method to find a user by ID
    public Usuarios getUsuarioById(Long id) {
        // Use orElse(null) or a custom exception handling for better practice
        return usuariosRepository.findById(id).orElse(null);
    }

    // Method to get all users (optional but good for testing)
    public List<Usuarios> getAllUsuarios() {
        return usuariosRepository.findAll();
    }
}