package com.arjusven.backend.dto;

import com.arjusven.backend.model.Tickets;
import java.util.List;

public class DashboardResponseDTO {
    // La lista completa (por si la necesitas para tablas o detalles)
    private List<Tickets> tickets;

    // Los contadores ya calculados
    private long totalTickets;
    private long ticketsAbiertos;
    private long ticketsCerrados;
    private long ticketsCancelados;

    // Constructor, Getters y Setters
    public DashboardResponseDTO(List<Tickets> tickets, long total, long abiertos, long cerrados, long cancelados) {
        this.tickets = tickets;
        this.totalTickets = total;
        this.ticketsAbiertos = abiertos;
        this.ticketsCerrados = cerrados;
        this.ticketsCancelados = cancelados;
    }

    public List<Tickets> getTickets() { return tickets; }
    public void setTickets(List<Tickets> tickets) { this.tickets = tickets; }
    
    public long getTotalTickets() { return totalTickets; }
    public void setTotalTickets(long totalTickets) { this.totalTickets = totalTickets; }

    public long getTicketsAbiertos() { return ticketsAbiertos; }
    public void setTicketsAbiertos(long ticketsAbiertos) { this.ticketsAbiertos = ticketsAbiertos; }

    public long getTicketsCerrados() { return ticketsCerrados; }
    public void setTicketsCerrados(long ticketsCerrados) { this.ticketsCerrados = ticketsCerrados; }

    public long getTicketsCancelados() { return ticketsCancelados; }
    public void setTicketsCancelados(long ticketsCancelados) { this.ticketsCancelados = ticketsCancelados; }
}