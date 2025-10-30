package com.arjusven.backend.config;

import com.arjusven.backend.model.Usuarios;
import com.arjusven.backend.repository.UsuariosRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collections;
import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UsuariosRepository usuariosRepository;

    public CustomUserDetailsService(UsuariosRepository usuariosRepository) {
        this.usuariosRepository = usuariosRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        
        Optional<Usuarios> usuario = usuariosRepository.findByCorreo(email);
        
        if (!usuario.isPresent()) {
            throw new UsernameNotFoundException("Usuario no encontrado con correo: " + email);
        }
        
        Usuarios user = usuario.get();
        
        // El rol se debe prefijar con "ROLE_" para que Spring Security lo reconozca
        return new User(
            user.getCorreo(), 
            user.getContraseña(), 
            Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + user.getRol()))
        );
    }
}