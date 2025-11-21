package com.arjusven.backend.service;

import com.arjusven.backend.model.PivoteInventario;
import com.arjusven.backend.repository.PivoteInventarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class PivoteService {

    private final PivoteInventarioRepository pivoteInventarioRepository;

    @Autowired
    public PivoteService(PivoteInventarioRepository pivoteInventarioRepository) {
        this.pivoteInventarioRepository = pivoteInventarioRepository;
    }

    @Transactional
    public PivoteInventario save(PivoteInventario pivote) {
        return pivoteInventarioRepository.save(pivote);
    }

    public List<PivoteInventario> findAll() {
        return pivoteInventarioRepository.findAll();
    }

    // CORRECCIÓN: Cambiado ID a Long para coincidir con la entidad
    public Optional<PivoteInventario> findById(Long id) {
        return pivoteInventarioRepository.findById(id);
    }
}