package com.arjusven.backend.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.arjusven.backend.model.Inventario;

@Repository
public interface InventarioRepository extends JpaRepository<Inventario, Long> {

    // Búsquedas básicas por campos específicos
    Optional<Inventario> findByNumeroDeSerie(String numeroDeSerie);
    Optional<Inventario> findByNumeroDeSerieIgnoreCase(String numeroDeSerie);	

    // =========================================================================
    // 🆕 MÉTODOS OPTIMIZADOS PARA PAGINACIÓN (SERVER-SIDE PAGINATION)
    // =========================================================================

    /**
     * 1. Búsqueda Global Paginada.
     * Busca coincidencias parciales en Título, Serie o Incidencia.
     * Devuelve una 'Page' en lugar de una 'List'.
     */
    @Query("SELECT s FROM Inventario s " + 
           "WHERE LOWER(s.titulo) LIKE LOWER(CONCAT('%', :texto, '%')) " +
           "OR LOWER(s.numeroDeSerie) LIKE LOWER(CONCAT('%', :texto, '%')) " +
           "OR LOWER(s.numeroDeIncidencia) LIKE LOWER(CONCAT('%', :texto, '%'))")
    Page<Inventario> buscarGlobal(@Param("texto") String texto, Pageable pageable);

    /**
     * 2. Filtrado Paginado.
     * Aplica filtros dinámicos (si el parámetro es null, lo ignora).
     * Devuelve una 'Page'.
     */
    @Query("SELECT s FROM Inventario s " +
           "WHERE " +
           "(:estado IS NULL OR s.estado = :estado) " +
           "AND (:plaza IS NULL OR s.plaza = :plaza) " +
           "AND (:equipo IS NULL OR s.equipo = :equipo) " +
           "AND (:fechaInicio IS NULL OR s.ultimaActualizacion >= :fechaInicio) " +
           "AND (:fechaFin IS NULL OR s.ultimaActualizacion <= :fechaFin)")
    Page<Inventario> buscarPorFiltro(
            @Param("estado") String estado,
            @Param("plaza") String plaza,
            @Param("equipo") String equipo,
            @Param("fechaInicio") LocalDate fechaInicio,
            @Param("fechaFin") LocalDate fechaFin,
            Pageable pageable // 👈 Este parámetro habilita la paginación mágica de Spring
    );

    // =========================================================================
    // ⚠️ MÉTODOS LEGACY (Soporte para código antiguo / Reportes sin paginar)
    // =========================================================================

    @Query("SELECT s FROM Inventario s " + 
           "WHERE LOWER(s.titulo) = LOWER(:texto) " +
           "OR LOWER(s.numeroDeSerie) = LOWER(:texto) " +
           "OR LOWER(s.numeroDeIncidencia) = LOWER(:texto)")
    List<Inventario> buscarExacto(@Param("texto") String texto);
    
    @Query("SELECT s FROM Inventario s " + 
           "WHERE LOWER(s.titulo) LIKE LOWER(CONCAT('%', :texto, '%')) " +
           "OR LOWER(s.numeroDeSerie) LIKE LOWER(CONCAT('%', :texto, '%')) " +
           "OR LOWER(s.numeroDeIncidencia) LIKE LOWER(CONCAT('%', :texto, '%'))")
    List<Inventario> buscarParcial(@Param("texto") String texto);
    
    // Versión antigua del filtro que devuelve TODA la lista (Cuidado con el rendimiento)
    @Query("SELECT s FROM Inventario s " +
           "WHERE " +
           "(:estado IS NULL OR s.estado = :estado) " +
           "AND (:plaza IS NULL OR s.plaza = :plaza) " +
           "AND (:equipo IS NULL OR s.equipo = :equipo) " +
           "AND (:fechaInicio IS NULL OR s.ultimaActualizacion >= :fechaInicio) " +
           "AND (:fechaFin IS NULL OR s.ultimaActualizacion <= :fechaFin)")
    List<Inventario> buscarPorFiltroLegacy(
            @Param("estado") String estado,
            @Param("plaza") String plaza,
            @Param("equipo") String equipo,
            @Param("fechaInicio") LocalDate fechaInicio,
            @Param("fechaFin") LocalDate fechaFin
    );
}
