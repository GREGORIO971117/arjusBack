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
        return pivoteInventarioRepository.save(pivote);
    }

    @Transactional(readOnly = true) 
    public List<PivoteInventario> getHistorialByInventarioId(Long idInventario) {
        return pivoteInventarioRepository.findByInventario_IdInventarioOrderByFechaAsignacionDesc(idInventario);
    }
    
    public List<PivoteInventario> findAll() {
        return pivoteInventarioRepository.findAll();
    }

    public Optional<PivoteInventario> findById(PivoteInventarioId id) {
        return pivoteInventarioRepository.findById(id);
    }
}