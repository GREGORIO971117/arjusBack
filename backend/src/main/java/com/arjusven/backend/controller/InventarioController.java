package com.arjusven.backend.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.arjusven.backend.dto.TicketUploadResponse;
import com.arjusven.backend.model.Inventario;
import com.arjusven.backend.service.InventarioService;

@RestController
@RequestMapping("/api/inventario")
public class InventarioController {

    @Autowired
    private InventarioService inventarioService;

    // =========================================================================
    // 🆕 ENDPOINTS PAGINADOS
    // =========================================================================


    @GetMapping
    public ResponseEntity<Page<Inventario>> getAllInventario(
    		@RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size
    ) {
        Page<Inventario> inventarioPage = inventarioService.getAllInventarioPaged(page, size);
        
        return ResponseEntity.ok(inventarioPage); 
    }

    /**
     * 2. Buscador Global (Paginado)
     * Ejemplo: GET /api/inventario/search?query=Batería&page=1&size=5
     */
    @GetMapping("/search")
    public ResponseEntity<Page<Inventario>> searchInventario(
    		@RequestParam(name = "query") String query, // Agrega name="query"
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size
    ) {
        // Si no hay query, devolvemos todo paginado normal
        if (query == null || query.trim().isEmpty()) {
            return getAllInventario(page, size);
        }

        Page<Inventario> resultados = inventarioService.searchInventarioPaged(query, page, size);
        return ResponseEntity.ok(resultados);
    }

    /**
     * 3. Filtro Avanzado (Paginado)
     */
    @GetMapping("/filter")
    public ResponseEntity<Page<Inventario>> filterInventario(
    		@RequestParam(name = "estado", required = false) String estado,
            @RequestParam(name = "plaza", required = false) String plaza,
            @RequestParam(name = "equipo", required = false) String equipo,
            @RequestParam(name = "fechaInicio", required = false) LocalDate fechaInicio,
            @RequestParam(name = "fechaFin", required = false) LocalDate fechaFin,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size
    ) {

        // Limpieza de inputs (Misma lógica que tenías)
        String estadoFilter = (estado != null && !"todos".equalsIgnoreCase(estado)) ? estado.trim() : null;
        String plazaFilter = (plaza != null && !"todos".equalsIgnoreCase(plaza)) ? plaza.trim() : null;
        String equipoFilter = (equipo != null && !"todos".equalsIgnoreCase(equipo)) ? equipo.trim() : null;

        // Llamada al servicio paginado
        Page<Inventario> filteredInventario = inventarioService.filterInventarioPaged(
            estadoFilter, plazaFilter, equipoFilter, fechaInicio, fechaFin, page, size
        );

        return ResponseEntity.ok(filteredInventario);
    }

    // =========================================================================
    // ENDPOINTS CRUD Y UTILIDADES (Se mantienen igual o con ajustes menores)
    // =========================================================================

    @GetMapping("/{id}")
    public ResponseEntity<Inventario> getInventarioById(@PathVariable("id") Long id) {
        Inventario inventario = inventarioService.getInventarioById(id);
        if (inventario != null) {
            return new ResponseEntity<>(inventario, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Inventario> createInventario(@RequestBody Inventario inventario) {
        inventario.setEstado("Para instalar");
        Inventario savedInventario = inventarioService.saveInventario(inventario);
        return new ResponseEntity<>(savedInventario, HttpStatus.CREATED);
    }

    @PostMapping("/bulk")
    public ResponseEntity<?> createManyInventario(@RequestBody List<Inventario> estaciones) {
        try {
            Map<String, Object> nuevas = inventarioService.saveAllWithReport(estaciones);
            return new ResponseEntity<>(nuevas, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al guardar: " + e.getMessage());
        }
    }

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<TicketUploadResponse> uploadInventario(
            @RequestParam("file") MultipartFile file,
            @RequestParam("idAdministrador") Long idAdministrador) {

        if (file.isEmpty()) {
            TicketUploadResponse resp = new TicketUploadResponse();
            resp.agregarError("El archivo está vacío.");
            return new ResponseEntity<>(resp, HttpStatus.BAD_REQUEST);
        }

        if (idAdministrador == null) {
            TicketUploadResponse resp = new TicketUploadResponse();
            resp.agregarError("El ID del administrador es obligatorio.");
            return new ResponseEntity<>(resp, HttpStatus.BAD_REQUEST);
        }

        TicketUploadResponse response = inventarioService.uploadInventarioFromExcel(file, idAdministrador);

        if (response.getTotalExitosos() == 0 && !response.getErrores().isEmpty()) {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping(path = "{idInventario}")
    public Inventario delInventario(@PathVariable("idInventario") Long id) {
        return inventarioService.deleteInventario(id);
    }

    @PatchMapping(path = "{idInventario}")
    public Inventario patchInventario(
            @PathVariable("idInventario") Long id,
            @RequestBody Inventario inventarioDetails) {
        return inventarioService.patchInventario(id, inventarioDetails);
    }

    @DeleteMapping("/deleteAll")
    public ResponseEntity<?> deleteAllEstaciones() {
        inventarioService.deleteAllInventario();
        return ResponseEntity.noContent().build();
    }
}