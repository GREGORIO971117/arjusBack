package com.arjusven.backend.repository;

import com.arjusven.backend.model.Tickets; // o Tickets, según el nombre de tu clase
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Tickets, Integer> {
    
   

}