package com.arjusven.backend.service;

import com.arjusven.backend.model.Servicio;
import com.arjusven.backend.model.Estaciones;
import com.arjusven.backend.repository.ServicioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // Necesario para transacciones

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class ServicioService {

    private final ServicioRepository servicioRepository;
    private final EstacionesService estacionesService; // INYECCIÓN

    // Constructor para inyección de dependencias
    @Autowired
    public ServicioService(ServicioRepository servicioRepository, EstacionesService estacionesService) {
        this.servicioRepository = servicioRepository;
        this.estacionesService = estacionesService;
    }

    private void assignEstacionesDetails(Servicio servicio) {
        // Asumimos que ID_merchant es Long
        Long merchantId = servicio.getIdMerchant(); 

        if (merchantId != null) {
            Optional<Estaciones> estacionesOpt = estacionesService.findById(merchantId);

            if (estacionesOpt.isPresent()) {
                Estaciones estacion = estacionesOpt.get();
                
                // 1. Técnico Asignado
                if (servicio.getTecnico() == null || servicio.getTecnico().trim().isEmpty()) {
                    servicio.setTecnico(estacion.getTecnicoAsignado()); 
                }
                
                // 3. Prioridad (Mapeado a MotivoDeServicio)
                if (servicio.getMotivoDeServicio() == null || servicio.getMotivoDeServicio().trim().isEmpty()) {
                    servicio.setMotivoDeServicio(estacion.getPrioridad()); 
                }
                
                // 4. Plaza de Atención (Mapeado a TipoDeServicio)
                if (servicio.getTipoDeServicio() == null || servicio.getTipoDeServicio().trim().isEmpty()) {
                    servicio.setTipoDeServicio(estacion.getPlazaDeAtencion()); 
                }
                
                // NOTA: Se pueden añadir más campos aquí (Dirección, Nombre_de_ESS, SLA, etc.)
                
            } else {
                System.out.println("Advertencia: ID Merchant [" + merchantId + "] no encontrado en Estaciones. No se asignaron datos preliminares.");
            }
        }
    }

    // Método para guardar un nuevo servicio (Creación)
    @Transactional
    public Servicio saveServicio(Servicio servicio) {
        // Ejecuta la asignación condicional antes de guardar (Crea la base preliminar)
        assignEstacionesDetails(servicio); 
        
        return servicioRepository.save(servicio);
    }

    // Método para encontrar un servicio por ID
    public Servicio getServicioById(Long id) {
        return servicioRepository.findById(id).orElse(null);
    }

    // Método para obtener todos los servicios
    public List<Servicio> getAllServicio() {
        return servicioRepository.findAll();
    }
    
    // Método para actualizar parcialmente un servicio
    @Transactional
    public Servicio patchServicio(Long id, Servicio servicioDetails) {
        Servicio servicioExistente = servicioRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("El servicio con el ID [" + id + "] no fue encontrado para actualizar."));

        // -----------------------------------------------------------
        // LÓGICA DE ACTUALIZACIÓN DE DATOS PRELIMINARES
        // -----------------------------------------------------------
        if (servicioDetails.getIdMerchant() != null && !servicioDetails.getIdMerchant().equals(servicioExistente.getIdMerchant())) {
            
            // 1. Si el ID_Merchant es modificado, se actualiza el ID
            servicioExistente.setIdMerchant(servicioDetails.getIdMerchant());
            
            // 2. Se re-asignan los datos preliminares de la NUEVA Estación.
            // Esto solo actualiza los campos que el usuario NO modificó en la solicitud actual.
            assignEstacionesDetails(servicioExistente); 
        }
        
        // -----------------------------------------------------------
        // TU LÓGICA ORIGINAL DE PATCH (sobrescribe cualquier valor)
        // -----------------------------------------------------------
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