package com.arjusven.backend.service;

import com.arjusven.backend.model.Tickets;
import com.arjusven.backend.model.Usuarios;
import com.arjusven.backend.repository.TicketRepository;
import com.arjusven.backend.repository.UsuariosRepository;
import com.arjusven.backend.repository.PivoteInventarioRepository; // Nombre corregido
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class TicketService {

    // Inyectamos solo los repositorios esenciales para esta prueba
    private final TicketRepository ticketRepository;
    private final UsuariosRepository usuarioRepository; 
    // Mantenemos el repositorio de pivote inyectado por si acaso
    private final PivoteInventarioRepository pivoteInventarioRepository; 
    
    @Autowired
    public TicketService(
        TicketRepository ticketRepository,
        UsuariosRepository usuarioRepository,
        PivoteInventarioRepository pivoteInventarioRepository) // Nombre corregido
    {
        this.ticketRepository = ticketRepository;
        this.usuarioRepository = usuarioRepository;
        this.pivoteInventarioRepository = pivoteInventarioRepository; 
    }

    // ====================================================================
    // 1. LÓGICA DE CREACIÓN (POST)
    // ====================================================================

    /**
     * Crea un nuevo Ticket con validación de sus 4 entidades Usuario.
     */
    @Transactional
    public Tickets crearNuevoTicket(Tickets nuevoTicket) {
        
        // 1. EXTRAER IDS (Asumimos que el DTO/Controller solo envió el ID dentro del objeto Usuario)
        Long tecnicoId = nuevoTicket.getTecnico() != null ? nuevoTicket.getTecnico().getId() : null;
        Long supervisorId = nuevoTicket.getSupervisor() != null ? nuevoTicket.getSupervisor().getId() : null;
        Long adminId = nuevoTicket.getAdministrador() != null ? nuevoTicket.getAdministrador().getId() : null;
        
        // 2. VALIDAR Y RE-ADJUNTAR LAS ENTIDADES
        nuevoTicket.setTecnico(validarYObtenerUsuario(tecnicoId, "TECNICO"));
        nuevoTicket.setSupervisor(validarYObtenerUsuario(supervisorId, "SUPERVISOR"));
        nuevoTicket.setAdmininistrador(validarYObtenerUsuario(adminId, "ADMINISTRADOR"));
        
        // 3. ESTABLECER RELACIONES BIDIRECCIONALES (1:N)
        // Necesario si usas CascadeType.ALL, para que Hibernate sepa qué FK escribir.
        if (nuevoTicket.getServicios() != null) {
            nuevoTicket.getServicios().forEach(servicio -> servicio.setTicket(nuevoTicket));
        }

        if (nuevoTicket.getAdicionales() != null) {
            nuevoTicket.getAdicionales().forEach(adicional -> adicional.setTicket(nuevoTicket));
        }

        // 4. GUARDAR
        return ticketRepository.save(nuevoTicket);
    }
    
    /**
     * Helper: Busca un usuario por ID y valida que tenga el rol esperado.
     */
    private Usuarios validarYObtenerUsuario(Long tecnicoId, String rolEsperado) {
        if (tecnicoId == null) {
             throw new IllegalArgumentException(rolEsperado + " ID no puede ser nulo.");
        }
        
        Optional<Usuarios> usuarioOpt = usuarioRepository.findById(tecnicoId);
        
        if (usuarioOpt.isEmpty()) {
            throw new RuntimeException(rolEsperado + " no encontrado con ID: " + tecnicoId);
        }
        
        Usuarios usuario = usuarioOpt.get();
        
        // La validación del rol 'CLIENTE' puede ser omitida o suavizada, ya que su 'rol' puede ser genérico.
        // Si no es cliente, valida el rol.
        if (!"CLIENTE".equalsIgnoreCase(rolEsperado) && !rolEsperado.equalsIgnoreCase(usuario.getRol())) {
            throw new IllegalArgumentException("El usuario con ID " + tecnicoId + " no tiene el rol de " + rolEsperado + ".");
        }
        return usuario;
    }

    // ====================================================================
    // 2. LÓGICA DE CONSULTA (GET)
    // ====================================================================

    /**
     * @return Todos los tickets.
     */
    public List<Tickets> findAll() {
        return ticketRepository.findAll();
    }
    
    /**
     * @param id ID del ticket a buscar.
     * @return Un ticket específico.
     */
    public Optional<Tickets> findById(Integer id) {
        return ticketRepository.findById(id);
    }
}