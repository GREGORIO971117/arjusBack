package com.arjusven.backend.service;

import com.arjusven.backend.model.PivoteInventario;
import com.arjusven.backend.model.PivoteInventarioId;
import com.arjusven.backend.repository.PivoteInventarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PivoteService {

    private PivoteInventarioRepository pivoteInventarioRepository;

    @Autowired
    public void PivoteInventarioService(PivoteInventarioRepository pivoteInventarioRepository) {
        this.pivoteInventarioRepository = pivoteInventarioRepository;
    }

    @Transactional
    public PivoteInventario save(PivoteInventario pivote) {
        // Since you removed the complex validation, we save it directly. 
        // Note: If the Ticket or Inventario IDs are invalid, the database will throw a foreign key error here.
        return pivoteInventarioRepository.save(pivote);
    }

    
    public List<PivoteInventario> findAll() {
        return pivoteInventarioRepository.findAll();
    }

    public Optional<PivoteInventario> findById(PivoteInventarioId id) {
        return pivoteInventarioRepository.findById(id);
    }
}