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
    private String cliente; 
    private String guiaDhl;
    private String direccion;
    private String tipoServicio;
    private String observacionImportante;
    private String equipoReportado;
    private String equipoEnviado;

    public PatchPlaneacionDTO() {}
    
    public String getCliente() {return cliente;}
	public void setCliente(String cliente) {this.cliente = cliente;}

	public String getGuiaDhl() {return guiaDhl;}
	public void setGuiaDhl(String guiaDhl) {this.guiaDhl = guiaDhl;}

	public String getDireccion() {return direccion;}
	public void setDireccion(String direccion) {this.direccion = direccion;}

	public String getTipoServicio() {return tipoServicio;}
	public void setTipoServicio(String tipoServicio) {this.tipoServicio = tipoServicio;}

	public String getObservacionImportante() {return observacionImportante;}
	public void setObservacionImportante(String observacionImportante) {this.observacionImportante = observacionImportante;}

	public String getEquipoReportado() {return equipoReportado;}
	public void setEquipoReportado(String equipoReportado) {this.equipoReportado = equipoReportado;}

	public String getEquipoEnviado() {return equipoEnviado;}
	public void setEquipoEnviado(String equipoEnviado) {this.equipoEnviado = equipoEnviado;}

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
