package com.arjusven.backend.service;

import com.arjusven.backend.model.Servicio;
import com.arjusven.backend.model.Estaciones;
import com.arjusven.backend.repository.ServicioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; 

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class ServicioService {

    private final ServicioRepository servicioRepository;
    private final EstacionesService estacionesService; 

    @Autowired
    public ServicioService(ServicioRepository servicioRepository, EstacionesService estacionesService) {
        this.servicioRepository = servicioRepository;
        this.estacionesService = estacionesService;
    }

    /**
     * Lógica central de asignación de detalles de Estación al Servicio,
     * SOLO si el campo de Servicio está nulo o vacío.
     */
    private void assignEstacionesDetails(Servicio servicio) {
        Long merchantId = servicio.getIdMerchant(); 

        if (merchantId != null) {
            Optional<Estaciones> estacionesOpt = estacionesService.findById(merchantId);

            if (estacionesOpt.isPresent()) {
                Estaciones estacion = estacionesOpt.get();
                
                // --- INICIO DE ASIGNACIONES CONDICIONALES ---
                
                // 1. Técnico Asignado
                if (servicio.getTecnico() == null || servicio.getTecnico().trim().isEmpty()) {
                    servicio.setTecnico(estacion.getTecnicoAsignado()); 
                }
                
                // 2. Supervisor Arjus -> Supervisor (¡Añadido!)
                // ASUMIMOS que Estaciones tiene getSupervisorArjus()
                if (servicio.getSupervisor() == null || servicio.getSupervisor().trim().isEmpty()) {
                    servicio.setSupervisor(estacion.getSupervisorArjus()); 
                }

                // 3. Nombre Comercial (Estaciones) -> Nombre de ESS (Servicio)
                if (servicio.getNombreDeEss() == null || servicio.getNombreDeEss().trim().isEmpty()) {
                    servicio.setNombreDeEss(estacion.getNombreComercial()); 
                }
                
                // 4. Prioridad (Estaciones) -> Motivo de Servicio (Servicio)
                if (servicio.getMotivoDeServicio() == null || servicio.getMotivoDeServicio().trim().isEmpty()) {
                    servicio.setMotivoDeServicio(estacion.getPrioridad()); 
                }
                
                // 5. Plaza de Atención (Estaciones) -> Tipo de Servicio (Servicio)
                if (servicio.getTipoDeServicio() == null || servicio.getTipoDeServicio().trim().isEmpty()) {
                    servicio.setTipoDeServicio(estacion.getPlazaDeAtencion()); 
                }
                
                // 6. Asignar la relación completa de JPA (si fuera necesario para manejo futuro)
                // servicio.setEstaciones(estacion); 
                
                // --- FIN DE ASIGNACIONES CONDICIONALES ---
                
            } else {
                // Mensaje de advertencia si no se encuentra la estación
                System.out.println("Advertencia: ID Merchant [" + merchantId + "] no encontrado en Estaciones. No se asignaron datos preliminares.");
            }
        }
    }

    // Método para guardar un nuevo servicio (Creación)
    @Transactional
    public Servicio saveServicio(Servicio servicio) {
        // La asignación se realiza ANTES de guardar
        assignEstacionesDetails(servicio); 
        
        return servicioRepository.save(servicio);
    }

    // Método para encontrar un servicio por ID
    public Servicio getServicioById(Long id) {
        return servicioRepository.findById(id)
            .orElseThrow(() -> new NoSuchElementException("Servicio con ID " + id + " no encontrado."));
    }

    // Método para obtener todos los servicios
    public List<Servicio> getAllServicio() {
        return servicioRepository.findAll();
    }
    
    /**
     * Actualiza parcialmente un servicio. Incluye corrección para forzar la reasignación
     * de detalles de Estaciones si un campo queda nulo en el patch.
     */
    @Transactional
    public Servicio patchServicio(Long id, Servicio servicioDetails) {
        Servicio servicioExistente = servicioRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("El servicio con el ID [" + id + "] no fue encontrado para actualizar."));
        
        // --- LÓGICA CORREGIDA PARA FORZAR LA ASIGNACIÓN CONDICIONAL ---
        
        // 1. Si el usuario intenta cambiar el ID Merchant
        if (servicioDetails.getIdMerchant() != null) {
            servicioExistente.setIdMerchant(servicioDetails.getIdMerchant());
        }
        
        // 2. Ejecutar la asignación de detalles. 
        // Esto usa el ID_Merchant ACTUALIZADO o el ID EXISTENTE 
        // para rellenar campos que no vienen en servicioDetails y que son nulos.
        assignEstacionesDetails(servicioExistente); 
        
        // --- APLICACIÓN DE DETALLES DEL USUARIO (SOBRESCRIBEN ASIGNACIÓN) ---
        
        // Si el usuario envió un valor (incluso un NULL explícito, pero aquí solo chequeamos != NULL)
        // se sobrescribe lo que pudo haber asignado assignEstacionesDetails.
        
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
        
        if (servicioDetails.getSupervisor() != null) { // Asegúrate de tener este campo en servicioDetails
            servicioExistente.setSupervisor(servicioDetails.getSupervisor());
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
        
        // Retorna el servicio ya actualizado
        return servicioRepository.save(servicioExistente);
    }
}