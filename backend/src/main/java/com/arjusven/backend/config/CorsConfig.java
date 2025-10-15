package com.arjusven.backend.config; // Asegúrate de usar el paquete correcto

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // Mapea la configuración CORS a todos tus endpoints (/api/**)
        registry.addMapping("/api/**") 
                // Permite solicitudes desde la URL de tu frontend (Vite/React)
                .allowedOrigins("http://localhost:5173") 
                
                // Permite los métodos HTTP que usas
                .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH")
                
                // Permite todos los encabezados (headers)
                .allowedHeaders("*");
                
                // Si planeas usar cookies o sesiones, descomenta esta línea:
                // .allowCredentials(true); 
    }
}