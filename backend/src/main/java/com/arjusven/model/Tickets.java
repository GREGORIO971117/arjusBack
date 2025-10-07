package com.arjusven.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "Tickets")
public class Tickets {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idTickets")
    private Long id;

    // --- RELACIONES N:1 (Muchos-a-Uno) a Personal ---
    // Mapean a las FKs: Usuarios_Tecnico_ID y Usuarios_Supervisor_ID en la DB
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Usuarios_Tecnico_ID") 
    private Usuarios tecnico; 

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Usuarios_Supervisor_ID") 
    private Usuarios supervisor; 

    // --- RELACIONES 1:N (Uno-a-Muchos) a Hijos ---
    @OneToMany(mappedBy = "ticket", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Servicio> servicios;

    @OneToMany(mappedBy = "ticket", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Adicional> adicionales;
    
    // Relación a la tabla pivote para Inventario (N:M)
    @OneToMany(mappedBy = "ticket", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AsignacionInventario> asignaciones;

    // Constructor por defecto
    public Tickets() {}

    // Getters
    public Long getId() { return id; }
    public Usuarios getTecnico() { return tecnico; }
    public Usuarios getSupervisor() { return supervisor; }
    public List<Servicio> getServicios() { return servicios; }
    public List<Adicional> getAdicionales() { return adicionales; }
    public List<AsignacionInventario> getAsignaciones() { return asignaciones; }

    // Setters
    public void setId(Long id) { this.id = id; }
    public void setTecnico(Usuarios tecnico) { this.tecnico = tecnico; }
    public void setSupervisor(Usuarios supervisor) { this.supervisor = supervisor; }
    public void setServicios(List<Servicio> servicios) { this.servicios = servicios; }
    public void setAdicionales(List<Adicional> adicionales) { this.adicionales = adicionales; }
    public void setAsignaciones(List<AsignacionInventario> asignaciones) { this.asignaciones = asignaciones; }
}