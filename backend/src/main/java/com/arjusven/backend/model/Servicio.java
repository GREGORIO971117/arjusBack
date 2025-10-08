package com.arjusven.backend.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "servicios")
public class Servicio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idServicios")
    private Long id;
    
    // Atributos mapeados de la tabla SQL
    @Column(name = "Fecha_de_asignacion")
    private LocalDate fechaDeAsignacion;

    @Column(name = "Resolucion")
    private String resolucion;

    @Column(name = "Situacion_Actual")
    private String situacionActual;
    
    @Column(name = "Nombre_de_ESS")
    private String nombreDeEss;
    
    @Column(name = "Incidencia")
    private String incidencia;
    
    @Column(name = "Codigo_de_afiliado")
    private String codigoDeAfiliado;
    
    @Column(name = "Supervidor")
    private String supervidor;
    
    @Column(name = "ID_merchant")
    private String idMerchant;
    
    @Column(name = "Tipo_de_servicio")
    private String tipoDeServicio;
    
    @Column(name = "Motivo_de_servicio")
    private String motivoDeServicio;
    
    @Column(name = "Motivo_real")
    private String motivoReal;
    
    @Column(name = "Observaciones")
    private String observaciones;
    
    @Column(name = "Guia_de_encomienda")
    private String guiaDeEncomienda;
    
    @Column(name = "Fecha_de_envio")
    private LocalDate fechaDeEnvio;
    
    @Column(name = "Direccion")
    private String direccion;
    
    @Column(name = "Tecnico")
    private String tecnico; // Nota: Si el técnico es un usuario, esta columna de texto es redundante.
    
    @Column(name = "SLA")
    private Integer sla;
    
    
 // CLAVE FORÁNEA (Foreign Key)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Tickets_idTickets_FK", nullable = false)
    private Tickets ticket;


public Servicio() {
	
}


public Long getId() {
	return id;
}


public void setId(Long id) {
	this.id = id;
}


public LocalDate getFechaDeAsignacion() {
	return fechaDeAsignacion;
}


public void setFechaDeAsignacion(LocalDate fechaDeAsignacion) {
	this.fechaDeAsignacion = fechaDeAsignacion;
}


public String getResolucion() {
	return resolucion;
}


public void setResolucion(String resolucion) {
	this.resolucion = resolucion;
}


public String getSituacionActual() {
	return situacionActual;
}


public void setSituacionActual(String situacionActual) {
	this.situacionActual = situacionActual;
}


public String getNombreDeEss() {
	return nombreDeEss;
}


public void setNombreDeEss(String nombreDeEss) {
	this.nombreDeEss = nombreDeEss;
}


public String getIncidencia() {
	return incidencia;
}


public void setIncidencia(String incidencia) {
	this.incidencia = incidencia;
}


public String getCodigoDeAfiliado() {
	return codigoDeAfiliado;
}


public void setCodigoDeAfiliado(String codigoDeAfiliado) {
	this.codigoDeAfiliado = codigoDeAfiliado;
}


public String getSupervidor() {
	return supervidor;
}


public void setSupervidor(String supervidor) {
	this.supervidor = supervidor;
}


public String getIdMerchant() {
	return idMerchant;
}


public void setIdMerchant(String idMerchant) {
	this.idMerchant = idMerchant;
}


public String getTipoDeServicio() {
	return tipoDeServicio;
}


public void setTipoDeServicio(String tipoDeServicio) {
	this.tipoDeServicio = tipoDeServicio;
}


public String getMotivoDeServicio() {
	return motivoDeServicio;
}


public void setMotivoDeServicio(String motivoDeServicio) {
	this.motivoDeServicio = motivoDeServicio;
}


public String getMotivoReal() {
	return motivoReal;
}


public void setMotivoReal(String motivoReal) {
	this.motivoReal = motivoReal;
}


public String getObservaciones() {
	return observaciones;
}


public void setObservaciones(String observaciones) {
	this.observaciones = observaciones;
}


public String getGuiaDeEncomienda() {
	return guiaDeEncomienda;
}


public void setGuiaDeEncomienda(String guiaDeEncomienda) {
	this.guiaDeEncomienda = guiaDeEncomienda;
}


public LocalDate getFechaDeEnvio() {
	return fechaDeEnvio;
}


public void setFechaDeEnvio(LocalDate fechaDeEnvio) {
	this.fechaDeEnvio = fechaDeEnvio;
}


public String getDireccion() {
	return direccion;
}


public void setDireccion(String direccion) {
	this.direccion = direccion;
}


public String getTecnico() {
	return tecnico;
}


public void setTecnico(String tecnico) {
	this.tecnico = tecnico;
}


public Integer getSla() {
	return sla;
}


public void setSla(Integer sla) {
	this.sla = sla;
}


public Tickets getTicket() {
	return ticket;
}


public void setTicket(Tickets ticket) {
	this.ticket = ticket;
}


@Override
public String toString() {
	return "Servicio [id=" + id + ", fechaDeAsignacion=" + fechaDeAsignacion + ", resolucion=" + resolucion
			+ ", situacionActual=" + situacionActual + ", nombreDeEss=" + nombreDeEss + ", incidencia=" + incidencia
			+ ", codigoDeAfiliado=" + codigoDeAfiliado + ", supervidor=" + supervidor + ", idMerchant=" + idMerchant
			+ ", tipoDeServicio=" + tipoDeServicio + ", motivoDeServicio=" + motivoDeServicio + ", motivoReal="
			+ motivoReal + ", observaciones=" + observaciones + ", guiaDeEncomienda=" + guiaDeEncomienda
			+ ", fechaDeEnvio=" + fechaDeEnvio + ", direccion=" + direccion + ", tecnico=" + tecnico + ", sla=" + sla
			+ ", ticket=" + ticket + "]";
	}
}