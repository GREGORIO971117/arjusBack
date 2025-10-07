package com.arjusven.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "Asignacion") // Mapea a tu tabla 'Asignacion' en la DB
public class AsignacionInventario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relación MUCHOS a UNO con Ticket
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Tickets_idTickets", nullable = false) 
    private Tickets ticket; 

    // Relación MUCHOS a UNO con Inventario
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Inventario_idInventario", nullable = false) 
    private Inventario inventario; 

    // Atributos de la asignación
    private LocalDate fechaDeAsignacion;
    private String estadoEnAsignacion; 

    // Constructor por defecto
    public AsignacionInventario() {}

    // Getters
    public Long getId() { return id; }
    public Tickets getTicket() { return ticket; }
    public Inventario getInventario() { return inventario; }
    public LocalDate getFechaDeAsignacion() { return fechaDeAsignacion; }
    public String getEstadoEnAsignacion() { return estadoEnAsignacion; }

    // Setters
    public void setId(Long id) { this.id = id; }
    public void setTicket(Tickets ticket) { this.ticket = ticket; }
    public void setInventario(Inventario inventario) { this.inventario = inventario; }
    public void setFechaDeAsignacion(LocalDate fechaDeAsignacion) { this.fechaDeAsignacion = fechaDeAsignacion; }
    public void setEstadoEnAsignacion(String estadoEnAsignacion) { this.estadoEnAsignacion = estadoEnAsignacion; }
}