package com.arjusven.backend.config;

//Asegúrate de que esta ruta sea correcta para tu proveedor de tokens

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

 private final JwtTokenProvider jwtTokenProvider;
 
 private final CustomUserDetailsService customUserDetailsService; // Se necesitará para cargar el usuario

 // Inyección por constructor
 public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider, CustomUserDetailsService customUserDetailsService) {
     this.jwtTokenProvider = jwtTokenProvider;
     this.customUserDetailsService = customUserDetailsService;
 }

 @Override
 protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
         throws ServletException, IOException {


     // 1. Extraer el token JWT del encabezado 'Authorization'
     String token = getJwtFromRequest(request);
     
     
     
     

     if (token != null && jwtTokenProvider.validateToken(token)) {
    	 
    	  
    	 
         
         // 3. Obtener el correo (username) del token
         String username = jwtTokenProvider.getUsernameFromToken(token);
         
         try {
        	 
   	      // Si llegas aquí, el usuario existe y tienes éxito.
   	      System.out.println("✅ Usuario " + username + " cargado con éxito."); 
   	      // ... luego se establece la autenticación
   	  } catch (Exception e) {
   	      // Si la carga falla por cualquier motivo (ej: usuario no encontrado), añade un log aquí.
   	      System.err.println("❌ ERROR: No se pudo cargar el usuario " + username + ": " + e.getMessage());
   	  }

         // 4. Cargar el usuario y la autenticación
         // Spring Security necesita cargar los UserDetails para establecer la sesión.
         UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
         
         // 5. Crear el objeto de autenticación
         UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                 userDetails, null, userDetails.getAuthorities());
         
         authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
         
         // 6. 🛑 Establecer la autenticación en el contexto de seguridad de Spring
         SecurityContextHolder.getContext().setAuthentication(authentication);
     }

     filterChain.doFilter(request, response);
 }

 /**
  * Extrae el JWT de la cabecera HTTP "Authorization".
  * Espera el formato: "Bearer <token>"
  */
 private String getJwtFromRequest(HttpServletRequest request) {
     String bearerToken = request.getHeader("Authorization");
     if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
         return bearerToken.substring(7); // Quitar "Bearer "
     }
     return null;
 }
}