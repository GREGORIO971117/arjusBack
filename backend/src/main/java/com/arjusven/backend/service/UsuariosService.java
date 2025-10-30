// UsuariosService.java
package com.arjusven.backend.service;

import com.arjusven.backend.model.Usuarios;
import com.arjusven.backend.repository.UsuariosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuariosService {
	
	@Autowired
	private PasswordEncoder encoder;

    
    private UsuariosRepository usuariosRepository;

    @Autowired
    public UsuariosService(UsuariosRepository usuariosRepository) {
		this.usuariosRepository = usuariosRepository;
	}
    
 // Method to get all users (optional but good for testing)
    public List<Usuarios> getAllUsuarios() {
        return usuariosRepository.findAll();
    }
    
    public Usuarios getUsuarioById(Long id) {
        // Use orElse(null) or a custom exception handling for better practice
        return usuariosRepository.findById(id).orElseThrow(
        		()-> new IllegalArgumentException("El usuario con el id[ " + id
        				+ " ] no existe.")
        					);
        }
	// Method to save a new user
    public Usuarios saveUsuario(Usuarios usuario) {
    	Optional<Usuarios> user = usuariosRepository.findByCorreo(usuario.getCorreo());
		if(user.isEmpty()) {
			usuario.setContraseña(encoder.encode(usuario.getContraseña()));
			usuariosRepository.save(usuario);
		} else {
			usuario = null;
		}
		return usuario;    
		}
    
    public Usuarios deleteUsuario(Long id) {
		
		Usuarios user = null;
		if(usuariosRepository.existsById(id)) {
			user = usuariosRepository.findById(id).get();
			usuariosRepository.deleteById(id);
		}//if exists
		return user;
	}//deleteUsuarios
    
    
    public Usuarios patchUsuario(Long id, Usuarios usuarioDetails) {
        // 1. Buscar el usuario existente por ID.
        Usuarios usuarioExistente = usuariosRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("El usuario con el id[ " + id + " ] no existe para actualizar."));

        // 2. Aplicar lógica de actualización parcial (PATCH)
        
        if (usuarioDetails.getNombre() != null) {
            usuarioExistente.setNombre(usuarioDetails.getNombre());
        }
        
        if (usuarioDetails.getCorreo() != null) {
            usuarioExistente.setCorreo(usuarioDetails.getCorreo());
        }
        
        if (usuarioDetails.getEstadoDeResidencia() != null) {
            usuarioExistente.setEstadoDeResidencia(usuarioDetails.getEstadoDeResidencia());
        }
        
        if (usuarioDetails.getEdad() != null) {
            usuarioExistente.setEdad(usuarioDetails.getEdad());
        }
        
        if (usuarioDetails.getRol() != null) {
            usuarioExistente.setRol(usuarioDetails.getRol());
        }
        
        // NOTA: Para la contraseña, normalmente se usa un endpoint de cambio de contraseña dedicado, 
        // pero si se permite la actualización parcial aquí:
        if (usuarioDetails.getContraseña() != null) {
            // En un caso real, aquí deberías HASHEAR la contraseña antes de guardarla.
            usuarioExistente.setContraseña(usuarioDetails.getContraseña());
        }
        

        // 3. Guardar y devolver la entidad actualizada.
        return usuariosRepository.save(usuarioExistente);
    }
    
    public boolean validateUser(Usuarios usuarios) {
		Optional<Usuarios> user = usuariosRepository.findByCorreo(usuarios.getCorreo());
		if(user.isPresent()) {
			Usuarios tmp = user.get();
			if(encoder.matches(usuarios.getContraseña(), tmp.getContraseña())) {
				return true;
			}//matches
		} //if isPresent
		return false;
	}//validateUser
    
    
    
  
    // Method to find a user by ID
    

    
}