package com.arjusven.backend.model;
import java.time.LocalDate;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "inventario")
public class Inventario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idinventario") 
    private Long idInventario;
 
    @Column(name = "titulo")
    private String titulo;

    @Column(name = "numero_de_serie", unique = true, nullable = false) 
    private String numeroDeSerie;

    @Column(name = "equipo") 
    private String equipo;

    @Column(name = "estado") 
    private String estado;

    @Column(name = "responsable") 
    private String responsable;

    @Column(name = "cliente") 
    private String cliente;

    @Column(name = "plaza") 
    private String plaza;

    @Column(name = "tecnico") 
    private String tecnico;

    @Column(name = "numero_de_incidencia") 
    private String numeroDeIncidencia;

    @Column(name = "codigo_email") 
    private String codigoEmail;

    @Column(name = "guias") 
    private String guias;

    @Column(name = "fecha_de_inicio_prevista") 
    private LocalDate fechaDeInicioPrevista;

    @Column(name = "fecha_de_fin_prevista") 
    private LocalDate fechaDeFinPrevista;

    @Column(name = "fecha_de_fin") 
    private LocalDate fechaDeFin;

    @Column(name = "ultima_actualizacion") 
    private LocalDate ultimaActualizacion;

    @Column(name = "descripcion") 
    private String descripcion;

    @OneToMany(mappedBy = "inventario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PivoteInventario> pivoteInventario;

	public Inventario() {
		
	}
	
	public Long getIdInventario() { return idInventario; }
	public void setIdInventario(Long idInventario) { this.idInventario = idInventario; }

	public String getTitulo() { return titulo; }
	public void setTitulo(String titulo) { this.titulo = titulo; }

	public String getNumeroDeSerie() { return numeroDeSerie; }
	public void setNumeroDeSerie(String numeroDeSerie) { this.numeroDeSerie = numeroDeSerie; }

	public String getEquipo() { return equipo; }
	public void setEquipo(String equipo) { this.equipo = equipo; }

	public String getEstado() { return estado; }
	public void setEstado(String estado) { this.estado = estado; }

	public String getResponsable() { return responsable; }
	public void setResponsable(String responsable) { this.responsable = responsable; }

	public String getCliente() { return cliente; }
	public void setCliente(String cliente) { this.cliente = cliente; }

	public String getPlaza() { return plaza; }
	public void setPlaza(String plaza) { this.plaza = plaza; }

	public String getTecnico() { return tecnico; }
	public void setTecnico(String tecnico) { this.tecnico = tecnico; }

	public String getNumeroDeIncidencia() { return numeroDeIncidencia; }
	public void setNumeroDeIncidencia(String numeroDeIncidencia) { this.numeroDeIncidencia = numeroDeIncidencia; }

	public String getCodigoEmail() { return codigoEmail; }
	public void setCodigoEmail(String codigoEmail) { this.codigoEmail = codigoEmail; }

	public String getGuias() { return guias; }
	public void setGuias(String guias) { this.guias = guias; }

	public LocalDate getFechaDeInicioPrevista() {
		return fechaDeInicioPrevista;
	}

	public void setFechaDeInicioPrevista(LocalDate fechaDeInicioPrevista) {
		this.fechaDeInicioPrevista = fechaDeInicioPrevista;
	}

	public LocalDate getFechaDeFinPrevista() {
		return fechaDeFinPrevista;
	}

	public void setFechaDeFinPrevista(LocalDate fechaDeFinPrevista) {
		this.fechaDeFinPrevista = fechaDeFinPrevista;
	}

	public LocalDate getFechaDeFin() {
		return fechaDeFin;
	}

	public void setFechaDeFin(LocalDate fechaDeFin) {
		this.fechaDeFin = fechaDeFin;
	}

	public LocalDate getUltimaActualizacion() {
		return ultimaActualizacion;
	}

	public void setUltimaActualizacion(LocalDate ultimaActualizacion) {
		this.ultimaActualizacion = ultimaActualizacion;
	}

	public String getDescripcion() { return descripcion; }
	public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

	public List<PivoteInventario> getPivoteInventario() { return pivoteInventario; }
	public void setPivoteInventario(List<PivoteInventario> pivoteInventario) { this.pivoteInventario = pivoteInventario; }
}