package com.arjusven.backend.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.arjusven.backend.model.PivoteInventario;

@Repository
public interface PivoteInventarioRepository extends JpaRepository<PivoteInventario, Long> {
    List<PivoteInventario> findByInventario_IdInventario(Long idInventario);
}