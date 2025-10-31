package com.arjusven.backend.controller;

import com.arjusven.backend.model.Usuarios;
import com.arjusven.backend.service.UsuariosService;

import com.arjusven.backend.dto.LoginRequest; 
import com.arjusven.backend.dto.JwtAuthResponse; 
import com.arjusven.backend.config.JwtTokenProvider; 

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/usuarios") 
public class UsuariosController {

    // 💡 Usa 'final' para los servicios inyectados por constructor
    private final UsuariosService usuariosService;
    private final JwtTokenProvider jwtTokenProvider; 
    
    // 🛑 CONSTRUCTOR PARA INYECCIÓN DE DEPENDENCIAS 🛑
    // Se recomienda usar inyección por constructor en lugar de @Autowired en el campo
    @Autowired
    public UsuariosController(UsuariosService usuariosService, JwtTokenProvider jwtTokenProvider) {
    	this.usuariosService = usuariosService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    // Mapeado a POST /api/usuarios/login
    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        
        // 1. Crear el objeto temporal
        Usuarios tempUser = new Usuarios();
        tempUser.setCorreo(loginRequest.getCorreo());
        tempUser.setContraseña(loginRequest.getContraseña());
        
        // 2. Ejecutar la autenticación y OBTENER el objeto del usuario
        Usuarios authenticatedUser = usuariosService.validateUser(tempUser); // <-- Se sigue llamando validateUser
        
        if (authenticatedUser != null) { // <-- La condición ahora revisa si el objeto es nulo o no
            
            // 3. Generar el Token JWT
            String token = jwtTokenProvider.generateToken(authenticatedUser.getCorreo());
            
            // 4. Devolver la respuesta con el token, ID y Nombre
            // *Asegúrate de que JwtAuthResponse tenga el constructor de 3 parámetros*
            JwtAuthResponse response = new JwtAuthResponse(
                token,
                authenticatedUser.getIdUsuarios(), // Obtenido del objeto
                authenticatedUser.getNombre()      // Obtenido del objeto
            );
            
            return new ResponseEntity<>(response, HttpStatus.OK);

        } else {
            // Fallo: El método devolvió null
            return new ResponseEntity<>("Credenciales inválidas", HttpStatus.UNAUTHORIZED);
        }
    }
    // ------------------------------------------------------------------

    // ENDPOINT: POST /api/usuarios (Se mantiene para CREAR/REGISTRAR)
    // Purpose: Create a new user (saves data to the database)
    @PostMapping
    public ResponseEntity<Usuarios> createUsuario(@RequestBody Usuarios usuario) {
        // Asegúrate de que usuariosService.saveUsuario encripte la contraseña antes de guardarla!
        Usuarios savedUsuario = usuariosService.saveUsuario(usuario);
        return new ResponseEntity<>(savedUsuario, HttpStatus.CREATED);
    }

    // ENDPOINT: GET /api/usuarios/{id}
    // Purpose: Retrieve a user by ID (reads data from the database)
    @GetMapping("/{id}")
    public ResponseEntity<Usuarios> getUsuario(@PathVariable("id") Long id) {
        Usuarios usuario = usuariosService.getUsuarioById(id);
        if (usuario != null) {
            return new ResponseEntity<>(usuario, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<List<Usuarios>> getAllUsuario() {
        List<Usuarios> adicional = usuariosService.getAllUsuarios();
        if (adicional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // Código 204
        }
        return new ResponseEntity<>(adicional, HttpStatus.OK); // Código 200
    }
    
    
    @DeleteMapping(path="{idUsuarios}")
	public Usuarios deleteUsuario(@PathVariable ("idUsuarios") Long id) {
		return usuariosService.deleteUsuario(id);
	}

    @PatchMapping(path="{idUsuarios}")
    public Usuarios patchUsuario(
        @PathVariable("idUsuarios") Long id,
        @RequestBody Usuarios usuarioDetails) {	
        return usuariosService.patchUsuario(id, usuarioDetails);
    }
}