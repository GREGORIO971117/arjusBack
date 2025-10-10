package com.arjusven.backend.model;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;

@Entity
@Table(name = "usuarios")
public class Usuarios {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idUsuarios")
    private Long idUsuarios;

    @Column(name = "Nombre", nullable = false)
    private String nombre;
    
    @Column(name = "Correo")
    private String correo;
    
    @Column(name = "Estado_de_residencia")
    private String estadoDeResidencia;
    
    @Column(name = "Edad")
    private Integer edad;
    
    @Column(name = "Rol", nullable = false)
    private String rol;
    
    @Column(name ="Contraseña" , nullable =false)
    private String contraseña;
    
    @JsonIgnore
    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Tickets> ticketsCliente;
    
    // 2. Tickets donde este Usuario aparece como TÉCNICO (TecnicoID_FK)
    @JsonIgnore
    @OneToMany(mappedBy = "tecnico", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Tickets> ticketsTecnico;

    // 3. Tickets donde este Usuario aparece como SUPERVISOR (SupervisorID_FK)
    @JsonIgnore
    @OneToMany(mappedBy = "supervisor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Tickets> ticketsSupervisor;

    // 4. Tickets donde este Usuario aparece como ADMINISTRADOR (AdminID_FK)
    @JsonIgnore
    @OneToMany(mappedBy = "administrador", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Tickets> ticketsAdministrador;

	public Usuarios() {
		super();
	}

	public Long getIdUsuarios() {
		return idUsuarios;
	}

	public void setIdUsuarios(Long idUsuarios) {
		this.idUsuarios = idUsuarios;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
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

	public String getContraseña() {
		return contraseña;
	}

	public void setContraseña(String contraseña) {
		this.contraseña = contraseña;
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
	
}