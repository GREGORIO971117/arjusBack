package com.arjusven.backend.model;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;


@Embeddable
public class PivoteInventarioId implements Serializable {

    // FK de Tickets
    @Column(name = "Tickets_idTickets")
    private Long idTickets;

    // FK de Inventario
    @Column(name = "Inventario_idInventario")
    private Long idInventario;

    // --- Constructores ---
    
    public PivoteInventarioId() {}

    public PivoteInventarioId(Long idTickets, Long idInventario) {
        this.idTickets = idTickets;
        this.idInventario = idInventario;
    }

    // --- Getters y Setters ---

    public Long getIdTickets() {
        return idTickets;
    }

    public void setIdTickets(Long idTickets) {
        this.idTickets = idTickets;
    }

    public Long getIdInventario() {
        return idInventario;
    }

    public void setIdInventario(Long idInventario) {
        this.idInventario = idInventario;
    }

    // --- Métodos hashCode y equals (OBLIGATORIOS para @EmbeddedId) ---
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PivoteInventarioId that = (PivoteInventarioId) o;
        return Objects.equals(idTickets, that.idTickets) &&
               Objects.equals(idInventario, that.idInventario);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idTickets, idInventario);
    }
}