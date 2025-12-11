package com.arjusven.backend.dto;

import java.time.LocalDate;

public class PlaneacionDTO {

    // 🔹 Identificador principal para realizar PATCH
    private Long idTicket;

    // Datos directos de Ticket/Servicio
    private LocalDate fechaAsignacion;
    private String incidencia;
    private String statusPaqueteria;
    private String cliente; // nombreDeEss
    private String estadoGuia; // situacionActual
    private LocalDate fechaDeEnvio;
    private Long merchantId;
    private String guiaDhl; // guiaDeEncomienda
    private String direccion;
    private String tipoServicio;
    private String descripcion; // motivoDeServicio
    private String nombreTecnico;
    private String observacionImportante; // observaciones
    private String supervisor;
    private String colonia;
    private String ciudad;
    private String estadoMx;
    private String transporteEstimado;
    // Datos de Inventario
    private LocalDate fechaLlegada;
    private String equipoReportado;
    private String equipoEnviado;
    private LocalDate fechaAsignacionReporte;
    private LocalDate fechaCierre;

    // Datos vacíos o calculados
    private String observacionArjus;

    // Constructor vacío
    public PlaneacionDTO() {}
    
    public String getStatusPaqueteria() {return statusPaqueteria;}
	public void setStatusPaqueteria(String statusPaqueteria) {this.statusPaqueteria = statusPaqueteria;}

	public Long getIdTicket() { return idTicket; }
    public void setIdTicket(Long idTicket) { this.idTicket = idTicket; }

    public String getEstadoMx() { return estadoMx; }
    public void setEstadoMx(String estadoMx) { this.estadoMx = estadoMx; }

    public LocalDate getFechaCierre() { return fechaCierre; }
    public void setFechaCierre(LocalDate fechaCierre) { this.fechaCierre = fechaCierre; }

    public LocalDate getFechaAsignacion() { return fechaAsignacion; }
    public void setFechaAsignacion(LocalDate fechaAsignacion) { this.fechaAsignacion = fechaAsignacion; }

    public String getIncidencia() { return incidencia; }
    public void setIncidencia(String incidencia) { this.incidencia = incidencia; }

    public String getCliente() { return cliente; }
    public void setCliente(String cliente) { this.cliente = cliente; }

    public String getEstadoGuia() { return estadoGuia; }
    public void setEstadoGuia(String estadoGuia) { this.estadoGuia = estadoGuia; }

    public LocalDate getFechaDeEnvio() { return fechaDeEnvio; }
    public void setFechaDeEnvio(LocalDate fechaDeEnvio) { this.fechaDeEnvio = fechaDeEnvio; }

    public Long getMerchantId() { return merchantId; }
    public void setMerchantId(Long merchantId) { this.merchantId = merchantId; }

    public String getGuiaDhl() { return guiaDhl; }
    public void setGuiaDhl(String guiaDhl) { this.guiaDhl = guiaDhl; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    public String getTipoServicio() { return tipoServicio; }
    public void setTipoServicio(String tipoServicio) { this.tipoServicio = tipoServicio; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getNombreTecnico() { return nombreTecnico; }
    public void setNombreTecnico(String nombreTecnico) { this.nombreTecnico = nombreTecnico; }

    public String getObservacionImportante() { return observacionImportante; }
    public void setObservacionImportante(String observacionImportante) { this.observacionImportante = observacionImportante; }

    public String getSupervisor() { return supervisor; }
    public void setSupervisor(String supervisor) { this.supervisor = supervisor; }

    public String getColonia() { return colonia; }
    public void setColonia(String colonia) { this.colonia = colonia; }

    public String getCiudad() { return ciudad; }
    public void setCiudad(String ciudad) { this.ciudad = ciudad; }

    public String getTransporteEstimado() { return transporteEstimado; }
    public void setTransporteEstimado(String transporteEstimado) { this.transporteEstimado = transporteEstimado; }

    public LocalDate getFechaLlegada() { return fechaLlegada; }
    public void setFechaLlegada(LocalDate fechaLlegada) { this.fechaLlegada = fechaLlegada; }

    public String getEquipoReportado() { return equipoReportado; }
    public void setEquipoReportado(String equipoReportado) { this.equipoReportado = equipoReportado; }

    public String getEquipoEnviado() { return equipoEnviado; }
    public void setEquipoEnviado(String equipoEnviado) { this.equipoEnviado = equipoEnviado; }

    public LocalDate getFechaAsignacionReporte() { return fechaAsignacionReporte; }
    public void setFechaAsignacionReporte(LocalDate fechaAsignacionReporte) { this.fechaAsignacionReporte = fechaAsignacionReporte; }

    public String getObservacionArjus() { return observacionArjus; }
    public void setObservacionArjus(String observacionArjus) { this.observacionArjus = observacionArjus; }
}
