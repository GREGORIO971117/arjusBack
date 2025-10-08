package com.arjusven.backend.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "tickets")
public class Tickets {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idTickets")
    private Long id;
    
    @Column(name = "Fecha_Creacion")
    private LocalDateTime fechaCreacion;
    
    @Column(name = "Estado")
    private String estado;

    // --- RELACIONES N:1 (Muchos-a-Uno) a Personal ---
    // Mapean a las FKs: Usuarios_Tecnico_ID y Usuarios_Supervisor_ID en la DB
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ClienteID_FK") 
    private Usuarios cliente; 

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TecnicoID_FK") 
    private Usuarios tecnico;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SupervisorID_FK")
    private Usuarios supervisor;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "AdminID_FK")
    private Usuarios admin;

    // --- RELACIONES 1:N (Uno-a-Muchos) a Hijos ---
    @OneToMany(mappedBy = "ticket", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Servicio> servicios;

    @OneToMany(mappedBy = "ticket", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Adicional> adicionales;
    
    @OneToMany(mappedBy = "ticket", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PivoteInventario> pivoteInventario;

	public Tickets() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDateTime getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(LocalDateTime fechaCreacion) {
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

	public Usuarios getAdmin() {
		return admin;
	}

	public void setAdmin(Usuarios admin) {
		this.admin = admin;
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
		return "Tickets [id=" + id + ", fechaCreacion=" + fechaCreacion + ", estado=" + estado + ", cliente=" + cliente
				+ ", tecnico=" + tecnico + ", supervisor=" + supervisor + ", admin=" + admin + ", servicios="
				+ servicios + ", adicionales=" + adicionales + ", pivoteInventario=" + pivoteInventario + "]";
	}    
}