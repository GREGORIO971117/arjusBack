package com.arjusven.backend.config;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter; // Para insertar el filtro JWT
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
    // 1. INYECCIÓN: Se inyecta el filtro JWT que creaste
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    
    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }
    
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
    
    	.cors(cors -> cors.configurationSource(corsConfigurationSource()))
        .csrf(csrf -> csrf.disable())
        .authorizeHttpRequests(auth -> auth
            // Rutas públicas
            .requestMatchers("/api/usuarios/login").permitAll()
            .requestMatchers("/api/usuarios").permitAll() // Si el registro es público

            // 🔒 RUTAS RESTRINGIDAS 🔒
            // Solo ADMIN puede ver planeación o subir archivos
            // Nota: .hasRole("ADMINISTRADOR") busca automáticamente "ROLE_ADMINISTRADOR"
            .requestMatchers("/api/planeacion/**").hasRole("ADMINISTRADOR") 
            .requestMatchers("/api/archivos/subir/**").hasRole("ADMINISTRADOR")

            // Cualquier otra ruta requiere estar autenticado (cualquier rol)
            .anyRequest().authenticated()
        )
        // Agregas tu filtro JWT
        .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

    return http.build();
	}
	
	@Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        
        // A. Orígenes permitidos (Tu Frontend)
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:5173")); 
        
        // B. Métodos permitidos (Incluye OPTIONS es vital para el preflight)
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
        
        // C. Cabeceras permitidas (Authorization es vital para el JWT)
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type", "X-Requested-With", "Accept"));
        
        // D. Permitir credenciales (si fuera necesario, aunque con JWT en header no es estricto, ayuda a veces)
        configuration.setAllowCredentials(true); 

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); // Aplica a todas las rutas
        return source;
    }
	
    // Bean del AuthenticationManager (necesario para el login)
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
    
	@Bean
	public PasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}//encoder
}//class SecurityConfig