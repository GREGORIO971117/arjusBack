package com.arjusven.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "Servicios")
public class Servicio {

    // La clave primaria compuesta en la DB (idServicios, Tickets_idTickets) 
    // se maneja en JPA usando una clase @IdClass o @EmbeddedId, 
    // pero para simplicidad, usamos @Id en id y la relación en la FK.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idServicios")
    private Long id;

    // CLAVE FORÁNEA (Foreign Key)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Tickets_idTickets", nullable = false)
    private Tickets ticket;
    
    // Atributos mapeados de la tabla SQL
    @Column(name = "Fecha de asignacion")
    private LocalDate fechaDeAsignacion;

    @Column(name = "Resolucion")
    private String resolucion;

    @Column(name = "Situacion Actual")
    private String situacionActual;
    
    @Column(name = "Nombre de ESS")
    private String nombreDeEss;
    
    @Column(name = "Incidencia")
    private String incidencia;
    
    @Column(name = "Codigo de afiliado")
    private String codigoDeAfiliado;
    
    @Column(name = "Supervidor")
    private String supervidor;
    
    @Column(name = "ID merchant")
    private String idMerchant;
    
    @Column(name = "Tipo de servicio")
    private String tipoDeServicio;
    
    @Column(name = "Motivo de servicio")
    private String motivoDeServicio;
    
    @Column(name = "Motivo real")
    private String motivoReal;
    
    @Column(name = "Observaciones")
    private String observaciones;
    
    @Column(name = "Guia de encomienda")
    private String guiaDeEncomienda;
    
    @Column(name = "Fecha de envio")
    private LocalDate fechaDeEnvio;
    
    @Column(name = "Direccion")
    private String direccion;
    
    @Column(name = "Tecnico")
    private String tecnico; // Nota: Si el técnico es un usuario, esta columna de texto es redundante.
    
    @Column(name = "SLA")
    private Integer sla;
    

    // Constructor por defecto
    public Servicio() {}

    // Getters
    public Long getId() { return id; }
    public LocalDate getFechaDeAsignacion() { return fechaDeAsignacion; }
    public String getResolucion() { return resolucion; }
    public String getSituacionActual() { return situacionActual; }
    public String getNombreDeEss() { return nombreDeEss; }
    public String getIncidencia() { return incidencia; }
    public String getCodigoDeAfiliado() { return codigoDeAfiliado; }
    public String getSupervidor() { return supervidor; }
    public String getIdMerchant() { return idMerchant; }
    public String getTipoDeServicio() { return tipoDeServicio; }
    public String getMotivoDeServicio() { return motivoDeServicio; }
    public String getMotivoReal() { return motivoReal; }
    public String getObservaciones() { return observaciones; }
    public String getGuiaDeEncomienda() { return guiaDeEncomienda; }
    public LocalDate getFechaDeEnvio() { return fechaDeEnvio; }
    public String getDireccion() { return direccion; }
    public String getTecnico() { return tecnico; }
    public Integer getSla() { return sla; }
    public Tickets getTicket() { return ticket; }

    // Setters
    public void setId(Long id) { this.id = id; }
    public void setFechaDeAsignacion(LocalDate fechaDeAsignacion) { this.fechaDeAsignacion = fechaDeAsignacion; }
    public void setResolucion(String resolucion) { this.resolucion = resolucion; }
    public void setSituacionActual(String situacionActual) { this.situacionActual = situacionActual; }
    public void setNombreDeEss(String nombreDeEss) { this.nombreDeEss = nombreDeEss; }
    public void setIncidencia(String incidencia) { this.incidencia = incidencia; }
    public void setCodigoDeAfiliado(String codigoDeAfiliado) { this.codigoDeAfiliado = codigoDeAfiliado; }
    public void setSupervidor(String supervidor) { this.supervidor = supervidor; }
    public void setIdMerchant(String idMerchant) { this.idMerchant = idMerchant; }
    public void setTipoDeServicio(String tipoDeServicio) { this.tipoDeServicio = tipoDeServicio; }
    public void setMotivoDeServicio(String motivoDeServicio) { this.motivoDeServicio = motivoDeServicio; }
    public void setMotivoReal(String motivoReal) { this.motivoReal = motivoReal; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }
    public void setGuiaDeEncomienda(String guiaDeEncomienda) { this.guiaDeEncomienda = guiaDeEncomienda; }
    public void setFechaDeEnvio(LocalDate fechaDeEnvio) { this.fechaDeEnvio = fechaDeEnvio; }
    public void setDireccion(String direccion) { this.direccion = direccion; }
    public void setTecnico(String tecnico) { this.tecnico = tecnico; }
    public void setSla(Integer sla) { this.sla = sla; }
    public void setTicket(Tickets ticket) { this.ticket = ticket; }

	@Override
	public String toString() {
		return "Servicio [id=" + id + ", ticket=" + ticket + ", fechaDeAsignacion=" + fechaDeAsignacion
				+ ", resolucion=" + resolucion + ", situacionActual=" + situacionActual + ", nombreDeEss=" + nombreDeEss
				+ ", incidencia=" + incidencia + ", codigoDeAfiliado=" + codigoDeAfiliado + ", supervidor=" + supervidor
				+ ", idMerchant=" + idMerchant + ", tipoDeServicio=" + tipoDeServicio + ", motivoDeServicio="
				+ motivoDeServicio + ", motivoReal=" + motivoReal + ", observaciones=" + observaciones
				+ ", guiaDeEncomienda=" + guiaDeEncomienda + ", fechaDeEnvio=" + fechaDeEnvio + ", direccion="
				+ direccion + ", tecnico=" + tecnico + ", sla=" + sla + "]";
	}
}