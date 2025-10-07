package com.arjusven.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "Inventario")
public class Inventario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idInventario")
    private Long id;

    // --- Atributos mapeados de la tabla SQL ---
    
    @Column(name = "Titulo")
    private String titulo;

    @Column(name = "Numero de serie", unique = true, nullable = false)
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

    @Column(name = "Numero de incidencia")
    private String numeroDeIncidencia;

    @Column(name = "Codigo email")
    private String codigoEmail;

    @Column(name = "Guias")
    private String guias;

    @Column(name = "Fecha de inicio prevista")
    private String fechaDeInicioPrevista;

    @Column(name = "Fecha de fin prevista")
    private String fechaDeFinPrevista;

    @Column(name = "Fecha de fin")
    private String fechaDeFin;

    @Column(name = "Ultima actualizacion")
    private String ultimaActualizacion;

    @Column(name = "Descripcion")
    private String descripcion;

    @Column(name = "Inventariocol")
    private String inventarioCol; // Mapeado a 'Inventariocol'

    // --- RELACIÓN 1:N (Uno-a-Muchos) a AsignacionInventario (Historial N:M) ---
    // Esto permite ver en qué tickets ha estado este artículo.
    @OneToMany(mappedBy = "inventario", cascade = CascadeType.ALL) 
    private List<AsignacionInventario> historialDeAsignaciones;


    // Constructor por defecto
    public Inventario() {}

    // Getters
    public Long getId() { return id; }
    public String getTitulo() { return titulo; }
    public String getNumeroDeSerie() { return numeroDeSerie; }
    public String getEquipo() { return equipo; }
    public String getEstado() { return estado; }
    public String getResponsable() { return responsable; }
    public String getCliente() { return cliente; }
    public String getPlaza() { return plaza; }
    public String getTecnico() { return tecnico; }
    public String getNumeroDeIncidencia() { return numeroDeIncidencia; }
    public String getCodigoEmail() { return codigoEmail; }
    public String getGuias() { return guias; }
    public String getFechaDeInicioPrevista() { return fechaDeInicioPrevista; }
    public String getFechaDeFinPrevista() { return fechaDeFinPrevista; }
    public String getFechaDeFin() { return fechaDeFin; }
    public String getUltimaActualizacion() { return ultimaActualizacion; }
    public String getDescripcion() { return descripcion; }
    public String getInventarioCol() { return inventarioCol; }
    public List<AsignacionInventario> getHistorialDeAsignaciones() { return historialDeAsignaciones; }

    // Setters
    public void setId(Long id) { this.id = id; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public void setNumeroDeSerie(String numeroDeSerie) { this.numeroDeSerie = numeroDeSerie; }
    public void setEquipo(String equipo) { this.equipo = equipo; }
    public void setEstado(String estado) { this.estado = estado; }
    public void setResponsable(String responsable) { this.responsable = responsable; }
    public void setCliente(String cliente) { this.cliente = cliente; }
    public void setPlaza(String plaza) { this.plaza = plaza; }
    public void setTecnico(String tecnico) { this.tecnico = tecnico; }
    public void setNumeroDeIncidencia(String numeroDeIncidencia) { this.numeroDeIncidencia = numeroDeIncidencia; }
    public void setCodigoEmail(String codigoEmail) { this.codigoEmail = codigoEmail; }
    public void setGuias(String guias) { this.guias = guias; }
    public void setFechaDeInicioPrevista(String fechaDeInicioPrevista) { this.fechaDeInicioPrevista = fechaDeInicioPrevista; }
    public void setFechaDeFinPrevista(String fechaDeFinPrevista) { this.fechaDeFinPrevista = fechaDeFinPrevista; }
    public void setFechaDeFin(String fechaDeFin) { this.fechaDeFin = fechaDeFin; }
    public void setUltimaActualizacion(String ultimaActualizacion) { this.ultimaActualizacion = ultimaActualizacion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public void setInventarioCol(String inventarioCol) { this.inventarioCol = inventarioCol; }
    public void setHistorialDeAsignaciones(List<AsignacionInventario> historialDeAsignaciones) { this.historialDeAsignaciones = historialDeAsignaciones; }

	@Override
	public String toString() {
		return "Inventario [id=" + id + ", titulo=" + titulo + ", numeroDeSerie=" + numeroDeSerie + ", equipo=" + equipo
				+ ", estado=" + estado + ", responsable=" + responsable + ", cliente=" + cliente + ", plaza=" + plaza
				+ ", tecnico=" + tecnico + ", numeroDeIncidencia=" + numeroDeIncidencia + ", codigoEmail=" + codigoEmail
				+ ", guias=" + guias + ", fechaDeInicioPrevista=" + fechaDeInicioPrevista + ", fechaDeFinPrevista="
				+ fechaDeFinPrevista + ", fechaDeFin=" + fechaDeFin + ", ultimaActualizacion=" + ultimaActualizacion
				+ ", descripcion=" + descripcion + ", inventarioCol=" + inventarioCol + ", historialDeAsignaciones="
				+ historialDeAsignaciones + "]";
	}
}