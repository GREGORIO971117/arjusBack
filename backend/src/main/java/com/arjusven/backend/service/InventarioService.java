package com.arjusven.backend.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.arjusven.backend.model.Inventario;
import com.arjusven.backend.repository.InventarioRepository;

@Service
public class InventarioService {
	
    private InventarioRepository inventarioRepository;
	
	
	@Autowired
    public InventarioService(InventarioRepository inventarioRepository) {
		this.inventarioRepository = inventarioRepository;
	}

	// Method to save a new user
    public Inventario saveInventario(Inventario inventario) {
        return inventarioRepository.save(inventario);
    }

    // Method to find a user by ID
    public Inventario getInventarioById(Long id) {
        // Use orElse(null) or a custom exception handling for better practice
        return inventarioRepository.findById(id).orElse(null);
    }

    // Method to get all users (optional but good for testing)
    public List<Inventario> getAllInventario() {
        return inventarioRepository.findAll();
    }
    
    
    //==========================================================================================
	public Inventario deleteInventario(Long id) {
			
		Inventario inventario = null;
			if(inventarioRepository.existsById(id)) {
				inventario = inventarioRepository.findById(id).get();
				inventarioRepository.deleteById(id);
			}//if exists
			return inventario;
		}//deleteUsuarios
    
	//=======================================================================================
	
    public Inventario patchInventario(Long id, Inventario inventarioDetails) {
        // 1. Buscar la entidad existente o lanzar una excepción.
        Inventario inventarioExistente = inventarioRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("El item de Inventario con el ID [" + id + "] no fue encontrado para actualizar."));

       

        if (inventarioDetails.getTitulo() != null) {
            inventarioExistente.setTitulo(inventarioDetails.getTitulo());
        }
        
     
        if (inventarioDetails.getNumeroDeSerie() != null) {
            inventarioExistente.setNumeroDeSerie(inventarioDetails.getNumeroDeSerie());
        }

        if (inventarioDetails.getEquipo() != null) {
            inventarioExistente.setEquipo(inventarioDetails.getEquipo());
        }

        if (inventarioDetails.getEstado() != null) {
            inventarioExistente.setEstado(inventarioDetails.getEstado());
        }

        if (inventarioDetails.getResponsable() != null) {
            inventarioExistente.setResponsable(inventarioDetails.getResponsable());
        }

        if (inventarioDetails.getCliente() != null) {
            inventarioExistente.setCliente(inventarioDetails.getCliente());
        }

        if (inventarioDetails.getPlaza() != null) {
            inventarioExistente.setPlaza(inventarioDetails.getPlaza());
        }

        if (inventarioDetails.getTecnico() != null) {
            inventarioExistente.setTecnico(inventarioDetails.getTecnico());
        }

        if (inventarioDetails.getNumeroDeIncidencia() != null) {
            inventarioExistente.setNumeroDeIncidencia(inventarioDetails.getNumeroDeIncidencia());
        }

        if (inventarioDetails.getCodigoEmail() != null) {
            inventarioExistente.setCodigoEmail(inventarioDetails.getCodigoEmail());
        }

        if (inventarioDetails.getGuias() != null) {
            inventarioExistente.setGuias(inventarioDetails.getGuias());
        }

        if (inventarioDetails.getFechaDeInicioPrevista() != null) {
            inventarioExistente.setFechaDeInicioPrevista(inventarioDetails.getFechaDeInicioPrevista());
        }

        if (inventarioDetails.getFechaDeFinPrevista() != null) {
            inventarioExistente.setFechaDeFinPrevista(inventarioDetails.getFechaDeFinPrevista());
        }

        if (inventarioDetails.getFechaDeFin() != null) {
            inventarioExistente.setFechaDeFin(inventarioDetails.getFechaDeFin());
        }

        if (inventarioDetails.getUltimaActualizacion() != null) {
            inventarioExistente.setUltimaActualizacion(inventarioDetails.getUltimaActualizacion());
        }

        if (inventarioDetails.getDescripcion() != null) {
            inventarioExistente.setDescripcion(inventarioDetails.getDescripcion());
        }
        
        // Nota: La lista 'pivoteInventario' (OneToMany) NO se actualiza directamente aquí. 
        // Los elementos de colecciones se manejan típicamente en endpoints/lógica separada.
        
        // 3. Guardar y devolver la entidad actualizada.
        return inventarioRepository.save(inventarioExistente);
    }
    
    

}
