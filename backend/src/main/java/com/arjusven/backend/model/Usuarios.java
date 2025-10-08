package com.arjusven.backend.model;
import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name = "usuarios")
public class Usuarios {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idUsuarios")
    private Long id;

    @Column(name = "Nombre", nullable = false)
    private String nombreCompleto;
    
    @Column(name = "Correo")
    private String correo;
    
    @Column(name = "Estado_de_residencia")
    private String estadoDeResidencia;
    
    @Column(name = "Edad")
    private Integer edad;
    
    @Column(name = "Rol", nullable = false)
    private String rol;
    
    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Tickets> ticketsCliente;
    
    // 2. Tickets donde este Usuario aparece como TÉCNICO (TecnicoID_FK)
    @OneToMany(mappedBy = "tecnico", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Tickets> ticketsTecnico;

    // 3. Tickets donde este Usuario aparece como SUPERVISOR (SupervisorID_FK)
    @OneToMany(mappedBy = "supervisor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Tickets> ticketsSupervisor;

    // 4. Tickets donde este Usuario aparece como ADMINISTRADOR (AdminID_FK)
    @OneToMany(mappedBy = "administrador", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Tickets> ticketsAdministrador;

	public Usuarios() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombreCompleto() {
		return nombreCompleto;
	}

	public void setNombreCompleto(String nombreCompleto) {
		this.nombreCompleto = nombreCompleto;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getEstadoDeResidencia() {
		return estadoDeResidencia;
	}

	public void setEstadoDeResidencia(String estadoDeResidencia) {
		this.estadoDeResidencia = estadoDeResidencia;
	}

	public Integer getEdad() {
		return edad;
	}

	public void setEdad(Integer edad) {
		this.edad = edad;
	}

	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}

	public List<Tickets> getTicketsCliente() {
		return ticketsCliente;
	}

	public void setTicketsCliente(List<Tickets> ticketsCliente) {
		this.ticketsCliente = ticketsCliente;
	}

	public List<Tickets> getTicketsTecnico() {
		return ticketsTecnico;
	}

	public void setTicketsTecnico(List<Tickets> ticketsTecnico) {
		this.ticketsTecnico = ticketsTecnico;
	}

	public List<Tickets> getTicketsSupervisor() {
		return ticketsSupervisor;
	}

	public void setTicketsSupervisor(List<Tickets> ticketsSupervisor) {
		this.ticketsSupervisor = ticketsSupervisor;
	}

	public List<Tickets> getTicketsAdministrador() {
		return ticketsAdministrador;
	}

	public void setTicketsAdministrador(List<Tickets> ticketsAdministrador) {
		this.ticketsAdministrador = ticketsAdministrador;
	}

	@Override
	public String toString() {
		return "Usuarios [id=" + id + ", nombreCompleto=" + nombreCompleto + ", correo=" + correo
				+ ", estadoDeResidencia=" + estadoDeResidencia + ", edad=" + edad + ", rol=" + rol + ", ticketsCliente="
				+ ticketsCliente + ", ticketsTecnico=" + ticketsTecnico + ", ticketsSupervisor=" + ticketsSupervisor
				+ ", ticketsAdministrador=" + ticketsAdministrador + "]";
	}  
}