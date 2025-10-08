package com.arjusven.backend.model;

import jakarta.persistence.*;


@Entity
@Table(name="tickets_has_inventario")

public class PivoteInventario {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Cantidad_Usada")
	private Long cantidad;
	
	// CLAVE FORÁNEA (Foreign Key)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Tickets_idTickets_FK", nullable = false)
    private Tickets ticket;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Inventario_idInventario_FK", nullable = false)
    private Inventario inventario;

	public PivoteInventario() {
	}

	public Long getCantidad() {
		return cantidad;
	}

	public void setCantidad(Long cantidad) {
		this.cantidad = cantidad;
	}

	public Tickets getTicket() {
		return ticket;
	}

	public void setTicket(Tickets ticket) {
		this.ticket = ticket;
	}

	public Inventario getInventario() {
		return inventario;
	}

	public void setInventario(Inventario inventario) {
		this.inventario = inventario;
	}

	@Override
	public String toString() {
		return "PivoteInventario [cantidad=" + cantidad + ", ticket=" + ticket + ", inventario=" + inventario + "]";
	}
}
