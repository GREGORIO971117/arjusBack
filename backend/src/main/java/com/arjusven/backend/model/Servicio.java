package com.arjusven.backend.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "Servicios")
@JsonIgnoreProperties(ignoreUnknown = true) 
public class Servicio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idServicios")
    private Long idServicios;
    
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
    
    @Column(name = "Supervisor")
    private String supervisor;
    
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
    private String tecnico;
    
    @Column(name = "SLA")
    private Integer sla;
    
    
 // CLAVE FORÁNEA (Foreign Key)
    @OneToOne
    @JoinColumn(name = "Tickets_idTickets", referencedColumnName = "idTickets")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler","adicionales"}) 
    private Tickets ticket;
    
    public Servicio() {
	} 

	public Long getIdServicios() {
		return idServicios;
	}


	public void setIdServicios(Long idServicios) {
		this.idServicios = idServicios;
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


	public String getSupervisor() {
		return supervisor;
	}


	public void setSupervidor(String supervisor) {
		this.supervisor = supervisor;
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


	

}