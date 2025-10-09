package com.arjusven.backend.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "tickets")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Tickets {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idTickets")
    private Long idTickets;
    
    
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "Fecha_Creacion")
    private LocalDate fechaCreacion=LocalDate.now();;
    
    @Column(name = "Estado")
    private String estado;

    // --- RELACIONES N:1 (Muchos-a-Uno) a Personal ---
    // Mapean a las FKs: Usuarios_Tecnico_ID y Usuarios_Supervisor_ID en la DB
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ClienteID_FK") 
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Usuarios cliente; 

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TecnicoID_FK") 
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Usuarios tecnico;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SupervisorID_FK")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Usuarios supervisor;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "AdminID_FK")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Usuarios administrador;

    // --- RELACIONES 1:N (Uno-a-Muchos) a Hijos ---
    @OneToMany(mappedBy = "ticket", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Servicio> servicios;

    @OneToMany(mappedBy = "ticket", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Adicional> adicionales;
    
    @OneToMany(mappedBy = "ticket", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PivoteInventario> pivoteInventario;

	public Tickets() {
	}

	public Long getIdTickets() {
		return idTickets;
	}

	public void setIdTickets(Long id) {
		this.idTickets = id;
	}

	public LocalDate getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(LocalDate fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Usuarios getCliente() {
		return cliente;
	}

	public void setCliente(Usuarios cliente) {
		this.cliente = cliente;
	}

	public Usuarios getTecnico() {
		return tecnico;
	}

	public void setTecnico(Usuarios tecnico) {
		this.tecnico = tecnico;
	}

	public Usuarios getSupervisor() {
		return supervisor;
	}

	public void setSupervisor(Usuarios supervisor) {
		this.supervisor = supervisor;
	}

	public Usuarios getAdministrador() {
		return administrador;
	}

	public void setAdministrador(Usuarios admin) {
		this.administrador = admin;
	}

	public List<Servicio> getServicios() {
		return servicios;
	}

	public void setServicios(List<Servicio> servicios) {
		this.servicios = servicios;
	}

	public List<Adicional> getAdicionales() {
		return adicionales;
	}

	public void setAdicionales(List<Adicional> adicionales) {
		this.adicionales = adicionales;
	}

	public List<PivoteInventario> getPivoteInventario() {
		return pivoteInventario;
	}

	public void setPivoteInventario(List<PivoteInventario> pivoteInventario) {
		this.pivoteInventario = pivoteInventario;
	}

	@Override
	public String toString() {
		return "Tickets [id=" + idTickets + ", fechaCreacion=" + fechaCreacion + ", estado=" + estado + ", cliente=" + cliente
				+ ", tecnico=" + tecnico + ", supervisor=" + supervisor + ", admin=" + administrador + ", servicios="
				+ servicios + ", adicionales=" + adicionales + ", pivoteInventario=" + pivoteInventario + "]";
	}    
}