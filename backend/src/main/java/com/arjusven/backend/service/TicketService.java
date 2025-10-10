package com.arjusven.backend.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.arjusven.backend.model.Tickets;
import com.arjusven.backend.repository.TicketRepository;

import java.util.List;
import java.util.Optional;

@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketsRepository;

    // Method to save a new user
    public Tickets saveTickets(Tickets tickets) {
        return ticketsRepository.save(tickets);
    }

    // Method to find a user by ID
    public Optional<Tickets> getTicketsById(Long id) { // <-- Change return type to Optional<Tickets>
        return ticketsRepository.findById(id); // <-- Simply return the result of findById
    }

    // Method to get all users (optional but good for testing)
    public List<Tickets> getAllTickets() {
        return ticketsRepository.findAll();
    }
}