package com.arjusven.backend.service;

import com.arjusven.backend.dto.PatchPlaneacionDTO;
import com.arjusven.backend.dto.PlaneacionDTO;
import com.arjusven.backend.model.Adicional;
import com.arjusven.backend.model.Estaciones;
import com.arjusven.backend.model.Servicio;
import com.arjusven.backend.model.Tickets;
import com.arjusven.backend.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlaneacionService {

    private final TicketRepository ticketsRepository;
    private final TicketService ticketService;

    @Autowired
    public PlaneacionService(TicketRepository ticketsRepository, TicketService ticketService) {
        this.ticketsRepository = ticketsRepository;
        this.ticketService = ticketService;
    }

    // ... (Mantén tus métodos GET existentes aquí) ...
    public List<PlaneacionDTO> getPlaneacionOperativa() {
        return ticketsRepository.findAll().stream().map(this::mapTicketToDTO).collect(Collectors.toList());
    }

    @Transactional
    public PlaneacionDTO patchPlaneacion(String incidencia, PatchPlaneacionDTO dto) {

        List<Tickets> tickets = ticketService.findTicketsByIncidencia(incidencia);

        if (tickets.isEmpty()) {
            throw new IllegalArgumentException("No existe ticket con incidencia: " + incidencia);
        }

        Tickets ticket = tickets.get(0);
        Servicio servicio = ticket.getServicios();

        if (servicio == null) {
            throw new IllegalArgumentException("El ticket no tiene servicio asociado");
        }

        // ==========================================
        // 1. ACTUALIZACIÓN DE DATOS DEL SERVICIO
        // ==========================================
        
        // Campos existentes
        if (dto.getFechaAsignacion() != null) servicio.setFechaDeAsignacion(dto.getFechaAsignacion());
       // if (dto.getCliente() != null) servicio.setNombreDeEss(dto.getCliente());
        if (dto.getEstadoGuia() != null) servicio.setSituacionActual(dto.getEstadoGuia());
        if (dto.getFechaDeEnvio() != null) servicio.setFechaDeEnvio(dto.getFechaDeEnvio());
        if (dto.getFechaLlegada() != null) servicio.setFechaLlegada(dto.getFechaLlegada());
        if (dto.getGuiaDhl() != null) servicio.setGuiaDeEncomienda(dto.getGuiaDhl());
        //if (dto.getDireccion() != null) servicio.setDireccion(dto.getDireccion());
        //if (dto.getTipoServicio() != null) servicio.setTipoDeServicio(dto.getTipoServicio());
       // if (dto.getDescripcion() != null) servicio.setMotivoDeServicio(dto.getDescripcion());
        if (dto.getObservacionArjus() != null) servicio.setObservacionesEspeciales(dto.getObservacionArjus());
        //if (dto.getNombreTecnico() != null) servicio.setTecnico(dto.getNombreTecnico());
        //if (dto.getFechaAsignacionReporte() != null) servicio.setFechaReporte(dto.getFechaAsignacionReporte());
        if (dto.getFechaCierre() != null) servicio.setFechaCierre(dto.getFechaCierre());
        if (dto.getObservacionImportante() != null) servicio.setObservaciones(dto.getObservacionImportante());
        if (dto.getSupervisor() != null) servicio.setSupervisor(dto.getSupervisor());

        // NUEVOS CAMPOS AGREGADOS EN TU DTO

        // ==========================================
        // 2. ACTUALIZACIÓN DE DATOS ADICIONALES (EQUIPOS)
        // ==========================================
        
        // Verificamos si hay datos de equipos para actualizar
        if (dto.getEquipoReportado() != null || dto.getEquipoEnviado() != null) {
            Adicional adicional = ticket.getAdicionales();

            // CRÍTICO: Si no existe la entidad Adicional, la creamos
            if (adicional == null) {
                adicional = new Adicional();
                // Aquí deberías setear cualquier relación inversa si es necesaria, o guardar 'adicional' primero si no hay cascade.
                // Asumiendo que Tickets tiene CascadeType.ALL sobre Adicionales:
                ticket.setAdicionales(adicional); 
            }

            if (dto.getEquipoReportado() != null) adicional.setSerieFisicaSale(dto.getEquipoReportado());
            if (dto.getEquipoEnviado() != null) adicional.setSerieFisicaEntra(dto.getEquipoEnviado());
        }

        // Guardamos el ticket (y por cascada el servicio y adicionales)
        ticketsRepository.save(ticket);

        return mapTicketToDTO(ticket);
    }

    // ... (Tu método mapTicketToDTO sigue igual) ...
      private PlaneacionDTO mapTicketToDTO(Tickets ticket) {

        PlaneacionDTO dto = new PlaneacionDTO();
        Servicio servicio = ticket.getServicios();
        Adicional adicional = ticket.getAdicionales();

        if (servicio != null) {

            dto.setFechaAsignacion(servicio.getFechaDeAsignacion());
            dto.setIncidencia(servicio.getIncidencia());
            dto.setCliente(servicio.getNombreDeEss());
            dto.setEstadoGuia(servicio.getSituacionActual());
            dto.setFechaDeEnvio(servicio.getFechaDeEnvio());
            dto.setMerchantId(servicio.getIdMerchant());
            dto.setGuiaDhl(servicio.getGuiaDeEncomienda());
            dto.setDireccion(servicio.getDireccion());
            dto.setTipoServicio(servicio.getTipoDeServicio());
            dto.setDescripcion(servicio.getMotivoDeServicio());
            dto.setNombreTecnico(servicio.getTecnico());
            dto.setObservacionImportante(servicio.getObservaciones());
            dto.setSupervisor(servicio.getSupervisor());
            dto.setFechaLlegada(servicio.getFechaLlegada());
            dto.setFechaCierre(servicio.getFechaCierre());
            dto.setFechaAsignacionReporte(servicio.getFechaReporte());
            dto.setObservacionArjus(servicio.getObservacionesEspeciales());

            if (adicional != null) {
                dto.setEquipoEnviado(adicional.getSerieFisicaEntra());
                dto.setEquipoReportado(adicional.getSerieFisicaSale());
            }

            Estaciones estacion = servicio.getEstaciones();
            if (estacion != null) {
                dto.setColonia(estacion.getColoniaAsentamiento());
                dto.setCiudad(estacion.getMunicipio());
                dto.setEstadoMx(estacion.getEstado());

                if (estacion.getTransporte() != null) {
                    dto.setTransporteEstimado(estacion.getTransporte().toString());
                }
            }
        }

        return dto;
    }
}