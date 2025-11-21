package com.arjusven.backend.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.arjusven.backend.model.Inventario;
import com.arjusven.backend.model.PivoteInventario;
import com.arjusven.backend.repository.InventarioRepository;
import com.arjusven.backend.repository.PivoteInventarioRepository; // Importante
import com.arjusven.backend.repository.TicketRepository;

@Service
public class InventarioService {
    
    private final InventarioRepository inventarioRepository;
    private final TicketRepository ticketRepository;
    private final PivoteInventarioRepository pivoteInventarioRepository; // Nuevo repositorio
    
    // Nota: He quitado TicketService de aquí para evitar "Dependencia Circular" 
    // (TicketService llama a Inventario y Inventario llama a TicketService = Error al iniciar).
    
    @Autowired
    public InventarioService(InventarioRepository inventarioRepository, 
                             TicketRepository ticketRepository,
                             PivoteInventarioRepository pivoteInventarioRepository) {
        this.inventarioRepository = inventarioRepository;
        this.ticketRepository = ticketRepository;
        this.pivoteInventarioRepository = pivoteInventarioRepository;
    }

    // --- NUEVA FUNCIONALIDAD: HISTORIAL ---
    
    public List<PivoteInventario> obtenerHistorialPorInventario(Long idInventario) {
        // Verificamos que exista el inventario (opcional pero recomendado)
        if(!inventarioRepository.existsById(idInventario)){
             throw new NoSuchElementException("Inventario no encontrado");
        }
        
        // Simplemente devolvemos lo que encuentra el repositorio
        return pivoteInventarioRepository.findByInventario_IdInventario(idInventario);
    }
 
    @Transactional 
    public Inventario saveInventario(Inventario inventario) {
        return inventarioRepository.save(inventario);
    }
    
    public Inventario getInventarioById(Long id) {
        return inventarioRepository.findById(id).orElse(null);
    }

    public List<Inventario> getAllInventario() {
        return inventarioRepository.findAll();
    }
    
    public Inventario deleteInventario(Long id) {
        Inventario inventario = null;
        if(inventarioRepository.existsById(id)) {
            inventario = inventarioRepository.findById(id).get();
            inventarioRepository.deleteById(id);
        }
        return inventario;
    }
    
    @Transactional
    public Inventario patchInventario(Long id, Inventario inventarioDetails) {
        Inventario inventarioExistente = inventarioRepository.findById(id)
            .orElseThrow(() -> new NoSuchElementException("El item de Inventario con el ID [" + id + "] no fue encontrado para actualizar."));        

        if (inventarioDetails.getTitulo() != null) inventarioExistente.setTitulo(inventarioDetails.getTitulo());
        if (inventarioDetails.getNumeroDeSerie() != null) inventarioExistente.setNumeroDeSerie(inventarioDetails.getNumeroDeSerie());
        if (inventarioDetails.getEquipo() != null) inventarioExistente.setEquipo(inventarioDetails.getEquipo());
        if (inventarioDetails.getEstado() != null) inventarioExistente.setEstado(inventarioDetails.getEstado());
        if (inventarioDetails.getResponsable() != null) inventarioExistente.setResponsable(inventarioDetails.getResponsable());
        if (inventarioDetails.getCliente() != null) inventarioExistente.setCliente(inventarioDetails.getCliente());
        if (inventarioDetails.getPlaza() != null) inventarioExistente.setPlaza(inventarioDetails.getPlaza());
        if (inventarioDetails.getTecnico() != null) inventarioExistente.setTecnico(inventarioDetails.getTecnico());
        if (inventarioDetails.getNumeroDeIncidencia() != null) inventarioExistente.setNumeroDeIncidencia(inventarioDetails.getNumeroDeIncidencia());
        if (inventarioDetails.getCodigoEmail() != null) inventarioExistente.setCodigoEmail(inventarioDetails.getCodigoEmail());
        if (inventarioDetails.getGuias() != null) inventarioExistente.setGuias(inventarioDetails.getGuias());
        if (inventarioDetails.getFechaDeInicioPrevista() != null) inventarioExistente.setFechaDeInicioPrevista(inventarioDetails.getFechaDeInicioPrevista());
        if (inventarioDetails.getFechaDeFinPrevista() != null) inventarioExistente.setFechaDeFinPrevista(inventarioDetails.getFechaDeFinPrevista());
        if (inventarioDetails.getFechaDeFin() != null) inventarioExistente.setFechaDeFin(inventarioDetails.getFechaDeFin());
        if (inventarioDetails.getUltimaActualizacion() != null) inventarioExistente.setUltimaActualizacion(inventarioDetails.getUltimaActualizacion());
        if (inventarioDetails.getDescripcion() != null) inventarioExistente.setDescripcion(inventarioDetails.getDescripcion());
        
        return inventarioRepository.save(inventarioExistente);     
    }
}