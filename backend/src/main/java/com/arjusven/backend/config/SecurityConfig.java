package com.arjusven.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod; // Para especificar métodos HTTP
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy; // Para configurar STATELESS
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter; // Para insertar el filtro JWT

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
    // 1. INYECCIÓN: Se inyecta el filtro JWT que creaste
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    
    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }
    
	@Bean
	public SecurityFilterChain configure(HttpSecurity http) 
													throws Exception {
		return http.csrf(csrf->csrf.disable())
            
            // 🛑 HACER STATELESS (Sin sesiones HTTP), necesario para JWT 🛑
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            
			.authorizeHttpRequests(auth->auth
                
                // 2. RUTAS PÚBLICAS: Permitir el login y el registro
                .requestMatchers(HttpMethod.POST, "/api/usuarios/login").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/usuarios").permitAll() 
                
                // 3. RUTAS PROTEGIDAS: Cualquier otra petición requiere autenticación (token)
                .anyRequest().authenticated() 
					)
            
            // 4. INSERTAR EL FILTRO JWT: Ejecutar nuestro filtro antes del filtro de autenticación por defecto
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
            
			.build();
	} //configure
	
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