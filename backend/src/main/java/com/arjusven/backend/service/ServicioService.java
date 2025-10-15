// UsuariosService.java
package com.arjusven.backend.service;

import com.arjusven.backend.model.Servicio;
import com.arjusven.backend.repository.ServicioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ServicioService {

    @Autowired
    private ServicioRepository servicioRepository;

    // Method to save a new user
    public Servicio saveServicio(Servicio servicio) {
        return servicioRepository.save(servicio);
    }

    // Method to find a user by ID
    public Servicio getServicioById(Long id) {
        // Use orElse(null) or a custom exception handling for better practice
        return servicioRepository.findById(id).orElse(null);
    }

    // Method to get all users (optional but good for testing)
    public List<Servicio> getAllServicio() {
        return servicioRepository.findAll();
    }
    
    public Servicio patchServicio(Long id, Servicio servicioDetails) {
        // 1. Buscar el servicio existente o lanzar una excepción si no existe.
        Servicio servicioExistente = servicioRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("El servicio con el ID [" + id + "] no fue encontrado para actualizar."));


        if (servicioDetails.getFechaDeAsignacion() != null) {
            servicioExistente.setFechaDeAsignacion(servicioDetails.getFechaDeAsignacion());
        }
        
        if (servicioDetails.getResolucion() != null) {
            servicioExistente.setResolucion(servicioDetails.getResolucion());
        }
        
        if (servicioDetails.getSituacionActual() != null) {
            servicioExistente.setSituacionActual(servicioDetails.getSituacionActual());
        }
        
        if (servicioDetails.getNombreDeEss() != null) {
            servicioExistente.setNombreDeEss(servicioDetails.getNombreDeEss());
        }
        
        if (servicioDetails.getIncidencia() != null) {
            servicioExistente.setIncidencia(servicioDetails.getIncidencia());
        }
        
        if (servicioDetails.getCodigoDeAfiliado() != null) {
            servicioExistente.setCodigoDeAfiliado(servicioDetails.getCodigoDeAfiliado());
        }
        
        if (servicioDetails.getSupervidor() != null) {
            servicioExistente.setSupervidor(servicioDetails.getSupervidor());
        }
        
        if (servicioDetails.getIdMerchant() != null) {
            servicioExistente.setIdMerchant(servicioDetails.getIdMerchant());
        }
        
        if (servicioDetails.getTipoDeServicio() != null) {
            servicioExistente.setTipoDeServicio(servicioDetails.getTipoDeServicio());
        }
        
        if (servicioDetails.getMotivoDeServicio() != null) {
            servicioExistente.setMotivoDeServicio(servicioDetails.getMotivoDeServicio());
        }
        
        if (servicioDetails.getMotivoReal() != null) {
            servicioExistente.setMotivoReal(servicioDetails.getMotivoReal());
        }
        
        if (servicioDetails.getObservaciones() != null) {
            servicioExistente.setObservaciones(servicioDetails.getObservaciones());
        }
        
        if (servicioDetails.getGuiaDeEncomienda() != null) {
            servicioExistente.setGuiaDeEncomienda(servicioDetails.getGuiaDeEncomienda());
        }
        
        if (servicioDetails.getFechaDeEnvio() != null) {
            servicioExistente.setFechaDeEnvio(servicioDetails.getFechaDeEnvio());
        }
        
        if (servicioDetails.getDireccion() != null) {
            servicioExistente.setDireccion(servicioDetails.getDireccion());
        }
        
        if (servicioDetails.getTecnico() != null) {
            servicioExistente.setTecnico(servicioDetails.getTecnico());
        }
        
        if (servicioDetails.getSla() != null) {
            servicioExistente.setSla(servicioDetails.getSla());
        }
        
        return servicioRepository.save(servicioExistente);
    }
}