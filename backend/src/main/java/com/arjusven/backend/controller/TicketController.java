package com.arjusven.backend.controller;

import com.arjusven.backend.model.Tickets;
import com.arjusven.backend.service.DocumentGenerationService;
import com.arjusven.backend.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import com.arjusven.backend.dto.TicketUploadResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.time.LocalDate;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    private final TicketService ticketService;
    private final DocumentGenerationService documentGenerationService;

    @Autowired
    public TicketController(TicketService ticketService, DocumentGenerationService documentGenerationService) {
        this.ticketService = ticketService;
        this.documentGenerationService = documentGenerationService;
    }

// --------------------------------------------------------------------------------
// 🚀 ENDPOINT DE CONSULTA Y FILTRADO (CONSOLIDADO) 🚀
// --------------------------------------------------------------------------------

    /**
     * Único método para GET /api/tickets. Maneja el caso sin filtros y con todos los filtros.
     * Esto resuelve el error de mapeo ambiguo.
     */
    @GetMapping
    public ResponseEntity<List<Tickets>> getAllTickets(
            @RequestParam(value = "situacion", required = false) String situacion, 
            @RequestParam(value = "sla", required = false) String sla,          
            @RequestParam(value = "tipoDeServicio", required = false) String tipoDeServicio,
            @RequestParam(value = "supervisor", required = false) String supervisor,
            @RequestParam(value = "plaza", required = false) String plaza,
            
            @RequestParam(value = "startDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate, 
            @RequestParam(value = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
    ) {
        
        // La lógica de normalización de filtros de string se mantiene.
        String situacionFilter = null;
        String slaFilter = null; 
        String tipoDeServicioFilter = null;
        String supervisorFilter = null;
        String plazaFilter = null; 

        // 1. Normalización de la Situación
        if ("todos".equalsIgnoreCase(situacion) || situacion == null) {
            situacionFilter = null;
        } else {
            if ("abierto".equalsIgnoreCase(situacion)) {
                situacionFilter = "Abierta";
            } else if ("cerrado".equalsIgnoreCase(situacion)) {
                situacionFilter = "Cerrado"; 
            } else {
                situacionFilter = situacion;
            }
        }

        // 2. Normalización del SLA
        if (sla != null && !"todos".equalsIgnoreCase(sla)) {
            String normalizedSla = sla.trim();

            if ("local".equalsIgnoreCase(normalizedSla)) {
                slaFilter = "Local";
            } else if ("foraneo".equalsIgnoreCase(normalizedSla) || "foráneo".equalsIgnoreCase(normalizedSla)) {
                slaFilter = "Foráneo"; 
            } else {
                slaFilter = sla; 
            }
        } else {
            slaFilter = null; 
        }
        
        // Normalización de Tipo de Servicio
        if (tipoDeServicio != null && !"todos".equalsIgnoreCase(tipoDeServicio)) {
            String normalizedTipo = tipoDeServicio.trim();
            tipoDeServicioFilter = normalizedTipo;
        } else {
            tipoDeServicioFilter = null;
        }
        
        // Normalización de Supervisor
        if(supervisor != null && !"todos".equalsIgnoreCase(supervisor)) {
            String normalizedTipo = supervisor.trim();
            supervisorFilter = normalizedTipo;
        }else {
            supervisorFilter = null;
        }
        
        // Normalización de Plaza
        if(plaza != null && !"todos".equalsIgnoreCase(plaza)) {
            String normalizedTipo = plaza.trim();
            plazaFilter = normalizedTipo;
        }else {
            plazaFilter = null;
        }
        
        // 4. Aplicar los filtros
        // Asumiendo que filterTickets en el servicio ya maneja los filtros de fecha (startDate, endDate)
        List<Tickets> filteredTickets = ticketService.filterTickets(
            situacionFilter, 
            slaFilter, 
            tipoDeServicioFilter,
            supervisorFilter, 
            plazaFilter, 
            startDate, 
            endDate    
        );
        
        if (filteredTickets == null || filteredTickets.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        
        return ResponseEntity.ok(filteredTickets);
    }

// --------------------------------------------------------------------------------
// 🔍 ENDPOINT DE BÚSQUEDA 🔍
// --------------------------------------------------------------------------------

    @GetMapping("/search")
    public ResponseEntity<List<Tickets>> searchTickets(@RequestParam("query") String query) {
        if (query == null || query.trim().isEmpty()) {
             // Si la query está vacía, devuelve todos los tickets sin filtro
             // ⚠️ Se reemplaza la llamada al método ambiguo por una llamada directa al servicio
             List<Tickets> allTickets = ticketService.getAllTickets(); 
             return new ResponseEntity<>(allTickets, HttpStatus.OK);
        }

        List<Tickets> resultados = ticketService.searchTicketsSmart(query);

        if (resultados.isEmpty()) {
            return new ResponseEntity<>(resultados, HttpStatus.OK);
        }
        return new ResponseEntity<>(resultados, HttpStatus.OK);
    }

// --------------------------------------------------------------------------------
// 📤 ENDPOINT DE SUBIDA DE ARCHIVOS 📤
// --------------------------------------------------------------------------------
    
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<TicketUploadResponse> uploadTickets(
            @RequestParam("file") MultipartFile file,
            @RequestParam("idAdministrador") Long idAdministrador) {

        // Validaciones básicas
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

        // Llamar al servicio
        TicketUploadResponse response = ticketService.uploadTicketsFromExcel(file, idAdministrador);

        // Lógica de respuesta HTTP
        if (response.getTotalExitosos() == 0 && !response.getErrores().isEmpty()) {
            // Si todo falló
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } else if (!response.getErrores().isEmpty()) {
            // Éxito parcial
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        // Éxito total
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

// --------------------------------------------------------------------------------
// ⬇️ ENDPOINT DE DESCARGA ⬇️
// --------------------------------------------------------------------------------
    
    @GetMapping("/download/{id}")
    public ResponseEntity<byte[]> downloadTicketDocx(
            @PathVariable("id") Long id,
            @RequestParam(name = "type", defaultValue = "intercambio") String type) { 
        
        Tickets ticket = ticketService.getTicketsById(id);
        
        if (ticket == null || ticket.getServicios() == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        byte[] documentBytes;
        String templateName;

        try {
            // 2. SELECCIÓN DE PLANTILLA Y GENERACIÓN DE BYTES
            switch (type.toLowerCase()) {
                case "mantenimiento":
                    documentBytes = documentGenerationService.generateMantenimientoTicket(ticket);
                    templateName = "Mantenimiento";
                    break;
                case "retiro":
                    documentBytes = documentGenerationService.generateRetiroTicket(ticket);
                    templateName = "Retiro";
                    break;
                case "ticket":
                case "intercambio":
                default:
                    documentBytes = documentGenerationService.generateDefaultTicket(ticket);
                    templateName = "Intercambio";
                    break;
            }

            // 3. CONFIGURACIÓN DEL NOMBRE Y ENCABEZADO
            
            String incidencia = ticket.getServicios().getIncidencia();
            String nombreEss = ticket.getServicios().getNombreDeEss();
            
            String baseName = incidencia + "_" + nombreEss + "_" + templateName; 
            String nombreLimpio = baseName.replaceAll("[^a-zA-Z0-9\\s_-]", "").replaceAll("\\s+", "_");
            
            final String filename = nombreLimpio + ".docx"; 

            String encodedFilename = java.net.URLEncoder.encode(filename, "UTF-8").replaceAll("\\+", "%20");


            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.valueOf("application/vnd.openxmlformats-officedocument.wordprocessingml.document"));
            
            headers.add(HttpHeaders.CONTENT_DISPOSITION, 
                        "attachment; filename=\"" + filename + "\"; filename*=UTF-8''" + encodedFilename); 
            
            headers.setContentLength(documentBytes.length);

            return new ResponseEntity<>(documentBytes, headers, HttpStatus.OK);

        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR); 
        }
    }

// --------------------------------------------------------------------------------
// ℹ️ ENDPOINT DE DETALLE POR ID ℹ️
// --------------------------------------------------------------------------------

    @GetMapping("/{id}")
    public ResponseEntity<Tickets> getTicketById(@PathVariable("id") Long id) {
        Tickets ticket = ticketService.getTicketsById(id);
        
        if (ticket!=null) {
            return new ResponseEntity<>(ticket, HttpStatus.OK); 
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); 
        }
    }
    
// --------------------------------------------------------------------------------
// ➕ ENDPOINT DE CREACIÓN (POST) ➕
// --------------------------------------------------------------------------------

    @PostMapping
    public ResponseEntity<Tickets> createTicket(@RequestBody Tickets nuevoTicket) {
        try {
            
            nuevoTicket.getServicios().setFechaDeAsignacion(LocalDate.now());
            nuevoTicket.getServicios().setSituacionActual("Abierta");
            
            Tickets ticketGuardado = ticketService.saveTickets(nuevoTicket);
            
            return new ResponseEntity<>(ticketGuardado, HttpStatus.CREATED); 

        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST); 
        } catch (RuntimeException e) {
            // Error de DB, como usuario no encontrado
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); 
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); 
        }
    }
    
// --------------------------------------------------------------------------------
// ❌ ENDPOINTS DE ELIMINACIÓN ❌
// --------------------------------------------------------------------------------
    
    @DeleteMapping(path="{idTickets}")
	public Tickets deleteUsuario(@PathVariable ("idTickets") Long id) {
		return ticketService.deleteTickets(id);
	}
    
    @DeleteMapping("/deleteAll")
    public ResponseEntity<?> deleteAllTickets() {
        ticketService.deleteAllTickets();
        return ResponseEntity.noContent().build();
    }
}