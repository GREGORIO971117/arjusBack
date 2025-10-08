package com.arjusven.backend.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "inventario")
public class Inventario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idInventario")
    private Long idInventario;
 
    @Column(name = "Titulo")
    private String titulo;

    @Column(name = "Numero_de_serie", unique = true, nullable = false)
    private String numeroDeSerie;

    @Column(name = "Equipo")
    private String equipo;

    @Column(name = "Estado")
    private String estado; // Ejemplo: 'ALMACEN', 'ASIGNADO', 'REPARACION'

    @Column(name = "Responsable")
    private String responsable;

    @Column(name = "Cliente")
    private String cliente;

    @Column(name = "Plaza")
    private String plaza;

    @Column(name = "Tecnico")
    private String tecnico; // Nota: Si este Técnico es un usuario, esta columna de texto es redundante.

    @Column(name = "Numero_de_incidencia")
    private String numeroDeIncidencia;

    @Column(name = "Codigo_email")
    private String codigoEmail;

    @Column(name = "Guias")
    private String guias;

    @Column(name = "Fecha_de_inicio_prevista")
    private String fechaDeInicioPrevista;

    @Column(name = "Fecha_de_fin_prevista")
    private String fechaDeFinPrevista;

    @Column(name = "Fecha_de_fin")
    private String fechaDeFin;

    @Column(name = "Ultima_actualizacion")
    private String ultimaActualizacion;

    @Column(name = "Descripcion")
    private String descripcion;

    @OneToMany(mappedBy = "inventario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PivoteInventario> pivoteInventario;

	public Inventario() {
		super();
	}

	public Long getId() {
		return idInventario;
	}

	public void setId(Long id) {
		this.idInventario = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getNumeroDeSerie() {
		return numeroDeSerie;
	}

	public void setNumeroDeSerie(String numeroDeSerie) {
		this.numeroDeSerie = numeroDeSerie;
	}

	public String getEquipo() {
		return equipo;
	}

	public void setEquipo(String equipo) {
		this.equipo = equipo;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getResponsable() {
		return responsable;
	}

	public void setResponsable(String responsable) {
		this.responsable = responsable;
	}

	public String getCliente() {
		return cliente;
	}

	public void setCliente(String cliente) {
		this.cliente = cliente;
	}

	public String getPlaza() {
		return plaza;
	}

	public void setPlaza(String plaza) {
		this.plaza = plaza;
	}

	public String getTecnico() {
		return tecnico;
	}

	public void setTecnico(String tecnico) {
		this.tecnico = tecnico;
	}

	public String getNumeroDeIncidencia() {
		return numeroDeIncidencia;
	}

	public void setNumeroDeIncidencia(String numeroDeIncidencia) {
		this.numeroDeIncidencia = numeroDeIncidencia;
	}

	public String getCodigoEmail() {
		return codigoEmail;
	}

	public void setCodigoEmail(String codigoEmail) {
		this.codigoEmail = codigoEmail;
	}

	public String getGuias() {
		return guias;
	}

	public void setGuias(String guias) {
		this.guias = guias;
	}

	public String getFechaDeInicioPrevista() {
		return fechaDeInicioPrevista;
	}

	public void setFechaDeInicioPrevista(String fechaDeInicioPrevista) {
		this.fechaDeInicioPrevista = fechaDeInicioPrevista;
	}

	public String getFechaDeFinPrevista() {
		return fechaDeFinPrevista;
	}

	public void setFechaDeFinPrevista(String fechaDeFinPrevista) {
		this.fechaDeFinPrevista = fechaDeFinPrevista;
	}

	public String getFechaDeFin() {
		return fechaDeFin;
	}

	public void setFechaDeFin(String fechaDeFin) {
		this.fechaDeFin = fechaDeFin;
	}

	public String getUltimaActualizacion() {
		return ultimaActualizacion;
	}

	public void setUltimaActualizacion(String ultimaActualizacion) {
		this.ultimaActualizacion = ultimaActualizacion;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public List<PivoteInventario> getPivoteInventario() {
		return pivoteInventario;
	}

	public void setPivoteInventario(List<PivoteInventario> pivoteInventario) {
		this.pivoteInventario = pivoteInventario;
	}

	@Override
	public String toString() {
		return "Inventario [id=" + idInventario + ", titulo=" + titulo + ", numeroDeSerie=" + numeroDeSerie + ", equipo=" + equipo
				+ ", estado=" + estado + ", responsable=" + responsable + ", cliente=" + cliente + ", plaza=" + plaza
				+ ", tecnico=" + tecnico + ", numeroDeIncidencia=" + numeroDeIncidencia + ", codigoEmail=" + codigoEmail
				+ ", guias=" + guias + ", fechaDeInicioPrevista=" + fechaDeInicioPrevista + ", fechaDeFinPrevista="
				+ fechaDeFinPrevista + ", fechaDeFin=" + fechaDeFin + ", ultimaActualizacion=" + ultimaActualizacion
				+ ", descripcion=" + descripcion + ", pivoteInventario=" + pivoteInventario + "]";
	}

	
    
}