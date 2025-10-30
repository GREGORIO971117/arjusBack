package com.arjusven.backend.dto;

// Clase para encapsular la respuesta de autenticación exitosa
// Contiene el token JWT y, opcionalmente, el tipo de token (Bearer)
public class JwtAuthResponse {
    
    private String accessToken;
    private String tokenType = "Bearer"; // Estandar para tokens JWT

    // 1. Constructor para facilitar la creación después de generar el token
    public JwtAuthResponse(String accessToken) {
        this.accessToken = accessToken;
    }

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
}
