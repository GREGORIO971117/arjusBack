package com.arjusven.backend.dto;

// Clase para encapsular la respuesta de autenticación exitosa
public class JwtAuthResponse {
    
    private String accessToken;
    private String tokenType = "Bearer"; 
    
    // 🔑 CAMPOS NUEVOS: ID y Nombre del usuario
    private Long userId;        
    private String userName;  
    private String rol; 

    // 1. Constructor ACTUALIZADO para incluir los nuevos datos
    public JwtAuthResponse(String accessToken, Long userId, String userName, String rol) {
        this.accessToken = accessToken;
        this.userId = userId;
        this.userName = userName;
        this.rol = rol;
    }
    
    // 2. GETTERS Y SETTERS ACTUALIZADOS

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    // 🔑 Nuevos Getters y Setters
    
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}
    
    
}