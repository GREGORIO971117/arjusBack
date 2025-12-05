package com.arjusven.backend.dto;

import java.time.LocalDate;

public class PatchPlaneacionDTO {

    private String descripcion;
    private String estadoGuia;
    private LocalDate fechaDeEnvio;
    private LocalDate fechaAsignacion;
    private LocalDate fechaLlegada;
    private LocalDate fechaCierre;
    private LocalDate fechaAsignacionReporte;
    private String nombreTecnico;
    private String supervisor;
    private String observacionArjus;

    public PatchPlaneacionDTO() {}

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getEstadoGuia() { return estadoGuia; }
    public void setEstadoGuia(String estadoGuia) { this.estadoGuia = estadoGuia; }

    public LocalDate getFechaDeEnvio() { return fechaDeEnvio; }
    public void setFechaDeEnvio(LocalDate fechaDeEnvio) { this.fechaDeEnvio = fechaDeEnvio; }

    public LocalDate getFechaAsignacion() { return fechaAsignacion; }
    public void setFechaAsignacion(LocalDate fechaAsignacion) { this.fechaAsignacion = fechaAsignacion; }

    public LocalDate getFechaLlegada() { return fechaLlegada; }
    public void setFechaLlegada(LocalDate fechaLlegada) { this.fechaLlegada = fechaLlegada; }

    public LocalDate getFechaCierre() { return fechaCierre; }
    public void setFechaCierre(LocalDate fechaCierre) { this.fechaCierre = fechaCierre; }

    public LocalDate getFechaAsignacionReporte() { return fechaAsignacionReporte; }
    public void setFechaAsignacionReporte(LocalDate fechaAsignacionReporte) { this.fechaAsignacionReporte = fechaAsignacionReporte; }

    public String getNombreTecnico() { return nombreTecnico; }
    public void setNombreTecnico(String nombreTecnico) { this.nombreTecnico = nombreTecnico; }

    public String getSupervisor() { return supervisor; }
    public void setSupervisor(String supervisor) { this.supervisor = supervisor; }

    public String getObservacionArjus() { return observacionArjus; }
    public void setObservacionArjus(String observacionArjus) { this.observacionArjus = observacionArjus; }
}
