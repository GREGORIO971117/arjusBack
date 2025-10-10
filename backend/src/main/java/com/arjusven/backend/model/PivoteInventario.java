package com.arjusven.backend.model;


import java.io.Serializable;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;

@Entity
@Table(name = "Tickets_has_Inventario")
public class PivoteInventario implements Serializable {

    // 1. Clave compuesta
    @EmbeddedId
    private PivoteInventarioId id;

    // 2. Mapeo a la entidad Tickets
    @ManyToOne
    @MapsId("idTickets") // Mapea este ManyToOne al campo 'idTickets' del @EmbeddedId
    @JoinColumn(name = "Tickets_idTickets", referencedColumnName = "idTickets")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "pivoteInventario"})    
    private Tickets ticket;

    // 3. Mapeo a la entidad Inventario
    @ManyToOne
    @MapsId("idInventario") // Mapea este ManyToOne al campo 'idInventario' del @EmbeddedId
    @JoinColumn(name = "Inventario_idInventario", referencedColumnName = "idInventario")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "pivoteInventario"})
    private Inventario inventario;
    
    // 4. Atributos propios de la relación (del pivote)
    
    @Column(name = "cantidad")
    private Integer cantidad; // Usamos Integer, Long o el tipo que definiste en SQL
    
    // El nombre de columna coincide con el snake_case en SQL
    @Column(name = "fecha_asignacion")
    private LocalDate fechaAsignacion; 


    public PivoteInventario() {
        this.id = new PivoteInventarioId();
    }
    
    public PivoteInventario(Tickets ticket, Inventario inventario, Integer cantidad, LocalDate fechaAsignacion) {
        this.ticket = ticket;
        this.inventario = inventario;
        this.cantidad = cantidad;
        this.fechaAsignacion = fechaAsignacion;
        this.id = new PivoteInventarioId(ticket.getIdTickets(), inventario.getIdInventario());
    }


    public PivoteInventarioId getId() {
        return id;
    }

    public void setId(PivoteInventarioId id) {
        this.id = id;
    }

    public Tickets getTicket() {
        return ticket;
    }

    public void setTicket(Tickets ticket) {
        this.ticket = ticket;
        this.id.setIdTickets(ticket.getIdTickets());
    }

    public Inventario getInventario() {
        return inventario;
    }

    public void setInventario(Inventario inventario) {
        this.inventario = inventario;
        this.id.setIdInventario(inventario.getIdInventario());
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public LocalDate getFechaAsignacion() {
        return fechaAsignacion;
    }

    public void setFechaAsignacion(LocalDate fechaAsignacion) {
        this.fechaAsignacion = fechaAsignacion;
    }
}
