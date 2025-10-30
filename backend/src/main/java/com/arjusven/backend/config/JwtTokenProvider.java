package com.arjusven.backend.config;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtTokenProvider {

    // 💡 La clave secreta debe ser leída desde application.properties (o .yml)
    // Asumiendo que has añadido: jwt.secret=Gregorioeslameraverdura
    @Value("${jwt.secret}")
    private String jwtSecret;

    // 💡 El tiempo de expiración (e.g., 86400000 milisegundos = 24 horas)
    @Value("${jwt.expiration-ms}")
    private long jwtExpirationMs;
    
    // Método de utilidad para obtener la clave de firma
    private SecretKey getSigningKey() {
        // Decodificamos la clave secreta (asumimos que está codificada en Base64 en el archivo de propiedades)
        byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * Genera un token JWT para un usuario.
     * @param userEmail El correo electrónico del usuario que será el "Subject" del token.
     * @return El token JWT generado como String.
     */
    public String generateToken(String userEmail) {
        
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationMs); 

        return Jwts.builder()
                // Claim principal: Identifica al usuario
                .setSubject(userEmail)
                .setIssuedAt(now) 
                .setExpiration(expiryDate) 
                // Firma el token usando la clave y el algoritmo
                .signWith(getSigningKey(), SignatureAlgorithm.HS512) 
                .compact();
    }

    /**
     * Obtiene el correo electrónico del usuario desde el token.
     * @param token El JWT.
     * @return El correo electrónico del usuario (Subject).
     */
    public String getUsernameFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody().getSubject();
    }

    /**
     * Valida la integridad y la expiración del token.
     * @param authToken El token a validar.
     * @return true si el token es válido, false en caso contrario.
     */
    public boolean validateToken(String authToken) {
        try {
            Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(authToken);
            return true;
        } catch (Exception ex) {
            System.err.println("JWT Validation Error: " + ex.getMessage());
        }
        return false;
    }
}