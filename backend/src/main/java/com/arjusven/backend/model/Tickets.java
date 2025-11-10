package com.arjusven.backend.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "tickets")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Tickets {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idTickets")
    private Long idTickets;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Usuarios_idAdministrador", referencedColumnName = "idUsuarios", nullable = false) 
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Usuarios administrador;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Usuarios_idSupervisor", referencedColumnName = "idUsuarios") 
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Usuarios supervisor;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Usuarios_idTecnico", referencedColumnName = "idUsuarios")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Usuarios tecnico;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Usuarios_idCliente", referencedColumnName = "idUsuarios")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Usuarios cliente;

    // --- RELACIONES 1:N (Uno-a-Muchos) a Hijos ---
    @OneToOne(mappedBy = "ticket", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("ticket") 
    private Servicio servicios;
    
    @OneToOne(mappedBy = "ticket", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("ticket") 
    private Adicional adicionales;
    
    @OneToMany(mappedBy = "ticket", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler","ticket"}) 
    private List<PivoteInventario> pivoteInventario = new ArrayList<>();

	public Tickets() {
	}

	public Long getIdTickets() {
		return idTickets;
	}

	public void setIdTickets(Long idTickets) {
		this.idTickets = idTickets;
	}

	public Usuarios getAdministrador() {
		return administrador;
	}

	public void setAdministrador(Usuarios administrador) {
		this.administrador = administrador;
	}

	public Usuarios getSupervisor() {
		return supervisor;
	}

	public void setSupervisor(Usuarios supervisor) {
		this.supervisor = supervisor;
	}

	public Usuarios getTecnico() {
		return tecnico;
	}

	public void setTecnico(Usuarios tecnico) {
		this.tecnico = tecnico;
	}

	public Usuarios getCliente() {
		return cliente;
	}

	public void setCliente(Usuarios cliente) {
		this.cliente = cliente;
	}

	public Servicio getServicios() {
		return servicios;
	}

	public void setServicios(Servicio servicios) {
	    if (servicios != null) {
	        servicios.setTicket(this); 
	    }
	    this.servicios = servicios;
	}

	public Adicional getAdicionales() {
		return adicionales;
	}

	public void setAdicionales(Adicional adicionales) {

	    if (adicionales != null) {
	        adicionales.setTicket(this); 
	    }
	    this.adicionales = adicionales;
	}

	public List<PivoteInventario> getPivoteInventario() {
		return pivoteInventario;
	}

	public void setPivoteInventario(List<PivoteInventario> pivoteInventario) {
		this.pivoteInventario = pivoteInventario;
	}
}