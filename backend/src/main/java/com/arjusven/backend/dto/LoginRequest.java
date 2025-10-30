package com.arjusven.backend.dto;

// Clase simple para transferir las credenciales de login
// No necesita anotaciones de JPA/Hibernate, solo getters y setters.
public class LoginRequest {
    
    // Correo (el campo que el front-end enviará como username)
    private String correo;
    
    // Contraseña
    private String contraseña;

    // Constructor vacío (necesario para la deserialización de JSON por Spring)
    public LoginRequest() {
    }

    // Constructor con campos (opcional, pero útil)
    public LoginRequest(String correo, String contraseña) {
        this.correo = correo;
        this.contraseña = contraseña;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }
}