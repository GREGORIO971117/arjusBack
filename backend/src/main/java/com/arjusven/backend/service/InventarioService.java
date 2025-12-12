package com.arjusven.backend.service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

// 1. Nuevos Imports para Paginación
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.arjusven.backend.model.Adicional;
import com.arjusven.backend.model.Inventario;
import com.arjusven.backend.model.PivoteInventario;
import com.arjusven.backend.model.Servicio;
import com.arjusven.backend.model.Usuarios;
import com.arjusven.backend.model.Tickets;
import com.arjusven.backend.repository.InventarioRepository;
import com.arjusven.backend.repository.PivoteInventarioRepository;
import com.arjusven.backend.repository.TicketRepository;
import com.arjusven.backend.repository.UsuariosRepository;
import com.arjusven.backend.repository.ServicioRepository;
import com.arjusven.backend.dto.TicketUploadResponse;

@Service
public class InventarioService {
    
    private final InventarioRepository inventarioRepository;
    private final PivoteInventarioRepository pivoteInventarioRepository;
    private final UsuariosRepository usuariosRepository;
    private final TicketRepository ticketRepository;
    private final TicketService ticketService;
    
    @Autowired
    public InventarioService(InventarioRepository inventarioRepository, 
                             PivoteInventarioRepository pivoteInventarioRepository,
                             UsuariosRepository usuariosRepository,
                             TicketRepository ticketRepository,
                             ServicioRepository servicioRepository,
                             TicketService ticketService){
        this.inventarioRepository = inventarioRepository;
        this.pivoteInventarioRepository = pivoteInventarioRepository;
        this.usuariosRepository = usuariosRepository;
        this.ticketRepository = ticketRepository;
        this.ticketService = ticketService;
    }

    // ----------------------------------------------------------------
    // SECCIÓN DE PAGINACIÓN (NUEVOS MÉTODOS)
    // ----------------------------------------------------------------

    // 1. Obtener todos paginado (Sustituye a findAll en la vista principal)
    public Page<Inventario> getAllInventarioPaged(int page, int size) {
        // Ordenamos descendente por ID para ver los registros más nuevos primero
        Pageable pageable = PageRequest.of(page, size, Sort.by("idInventario").descending());
        return inventarioRepository.findAll(pageable);
    }

    // 2. Filtro paginado
    public Page<Inventario> filterInventarioPaged(String estado, String plaza, String equipo, LocalDate fechaInicio, LocalDate fechaFin, int page, int size){
        // Ordenamos por fecha de actualización
        Pageable pageable = PageRequest.of(page, size, Sort.by("ultimaActualizacion").descending());
        return inventarioRepository.buscarPorFiltro(estado, plaza, equipo, fechaInicio, fechaFin, pageable);
    }
    
    // 3. Búsqueda paginada
    public Page<Inventario> searchInventarioPaged(String query, int page, int size){
         if (query == null || query.trim().isEmpty()) {
             return getAllInventarioPaged(page, size);
         }
         
         String textoBusqueda = query.trim();
         Pageable pageable = PageRequest.of(page, size, Sort.by("idInventario").descending());
         
         // Usamos un solo método 'buscarGlobal' en el repositorio que cubra exacto y parcial
         return inventarioRepository.buscarGlobal(textoBusqueda, pageable);
    }

    // ----------------------------------------------------------------
    // FIN SECCIÓN PAGINACIÓN
    // ----------------------------------------------------------------

    public void deleteAllInventario() {
        inventarioRepository.deleteAll();
    }
    
    // Método antiguo de búsqueda sin paginación (Mantenlo solo si lo usas en reportes, si no, puedes borrarlo)
    public List<Inventario> searchInventarioLegacy(String query){
         if (query == null || query.trim().isEmpty()) {
             return inventarioRepository.findAll();
         }
         String textoBusqueda = query.trim();
         List<Inventario> resultados = inventarioRepository.buscarExacto(textoBusqueda);
         if (resultados.isEmpty()) {
             resultados = inventarioRepository.buscarParcial(textoBusqueda);
         }
         return resultados;
    }
    
    public List<PivoteInventario> obtenerHistorialPorInventario(Long idInventario) {
        if(!inventarioRepository.existsById(idInventario)){
             throw new NoSuchElementException("Inventario no encontrado");
        }
        return pivoteInventarioRepository.findByInventario_IdInventario(idInventario);
    }
 
    @Transactional 
    public Inventario saveInventario(Inventario inventario) {
        return inventarioRepository.save(inventario);
    }
    
    public Inventario getInventarioById(Long id) {
        return inventarioRepository.findById(id).orElse(null);
    }

    // Método legacy (sin paginación)
    public List<Inventario> getAllInventario() {
        return inventarioRepository.findAll();
    }
    
    public Inventario deleteInventario(Long id) {
        Inventario inventario = null;
        if(inventarioRepository.existsById(id)) {
            inventario = inventarioRepository.findById(id).get();
            inventarioRepository.deleteById(id);
        }
        return inventario;
    }
    
    @Transactional
    public Inventario patchInventario(Long id, Inventario inventarioDetails) {
        Inventario inventarioExistente = inventarioRepository.findById(id)
            .orElseThrow(() -> new NoSuchElementException("El item de Inventario con el ID [" + id + "] no fue encontrado para actualizar."));        

        if (inventarioDetails.getTitulo() != null) inventarioExistente.setTitulo(inventarioDetails.getTitulo());
        if (inventarioDetails.getNumeroDeSerie() != null) inventarioExistente.setNumeroDeSerie(inventarioDetails.getNumeroDeSerie());
        if (inventarioDetails.getEquipo() != null) inventarioExistente.setEquipo(inventarioDetails.getEquipo());
        if (inventarioDetails.getEstado() != null) inventarioExistente.setEstado(inventarioDetails.getEstado());
        if (inventarioDetails.getResponsable() != null) inventarioExistente.setResponsable(inventarioDetails.getResponsable());
        if (inventarioDetails.getCliente() != null) inventarioExistente.setCliente(inventarioDetails.getCliente());
        if (inventarioDetails.getPlaza() != null) inventarioExistente.setPlaza(inventarioDetails.getPlaza());
        if (inventarioDetails.getTecnico() != null) inventarioExistente.setTecnico(inventarioDetails.getTecnico());
        if (inventarioDetails.getNumeroDeIncidencia() != null) inventarioExistente.setNumeroDeIncidencia(inventarioDetails.getNumeroDeIncidencia());
        if (inventarioDetails.getCodigoEmail() != null) inventarioExistente.setCodigoEmail(inventarioDetails.getCodigoEmail());
        if (inventarioDetails.getGuias() != null) inventarioExistente.setGuias(inventarioDetails.getGuias());
        if (inventarioDetails.getFechaDeInicioPrevista() != null) inventarioExistente.setFechaDeInicioPrevista(inventarioDetails.getFechaDeInicioPrevista());
        if (inventarioDetails.getFechaDeFinPrevista() != null) inventarioExistente.setFechaDeFinPrevista(inventarioDetails.getFechaDeFinPrevista());
        if (inventarioDetails.getFechaDeFin() != null) inventarioExistente.setFechaDeFin(inventarioDetails.getFechaDeFin());
        if (inventarioDetails.getUltimaActualizacion() != null) inventarioExistente.setUltimaActualizacion(inventarioDetails.getUltimaActualizacion());
        if (inventarioDetails.getDescripcion() != null) inventarioExistente.setDescripcion(inventarioDetails.getDescripcion());
        
        return inventarioRepository.save(inventarioExistente);     
    }
    
    @Transactional
    public TicketUploadResponse uploadInventarioFromExcel(MultipartFile file, Long idAdministrador) {
        TicketUploadResponse response = new TicketUploadResponse();

        Usuarios administrador = usuariosRepository.findById(idAdministrador).orElse(null);
        if (administrador == null) {
            response.agregarError("No se encontró el usuario administrador con ID: " + idAdministrador);
            return response;
        }

        try (Workbook workbook = WorkbookFactory.create(file.getInputStream())) {
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();

            Map<String, Integer> headerMap = new HashMap<>();
            boolean headersFound = false;
            
            String COL_MATERIAL = "material entregado";
            String COL_SERIE = "serie";
            String COL_GUIA = "guia";
            String COL_INCIDENCIA = "incidencias";
            String COL_FECHA_ENVIO = "fecha de envio";
           
            int rowIndex = 0; 

            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                rowIndex++;
                
                if (!headersFound) {
                    for (Cell cell : row) {
                        String cellValue = ticketService.getCellValueAsString(cell).toLowerCase().trim();
                        if (cellValue.contains(COL_MATERIAL)) headerMap.put(COL_MATERIAL, cell.getColumnIndex());
                        else if (cellValue.equals(COL_SERIE)) headerMap.put(COL_SERIE, cell.getColumnIndex());
                        else if (cellValue.contains(COL_GUIA)) headerMap.put(COL_GUIA, cell.getColumnIndex());
                        else if (cellValue.contains(COL_INCIDENCIA)) headerMap.put(COL_INCIDENCIA, cell.getColumnIndex());
                        else if (cellValue.contains(COL_FECHA_ENVIO)) headerMap.put(COL_FECHA_ENVIO, cell.getColumnIndex());
                    }
                    if (headerMap.containsKey(COL_MATERIAL) && headerMap.containsKey(COL_SERIE) && headerMap.containsKey(COL_INCIDENCIA)) {
                        headersFound = true;
                        continue; 
                    }
                    if (rowIndex > 10 && !headersFound) {
                        response.agregarError("No se encontraron los encabezados 'serie', 'material entregado' e 'incidencias'.");
                        break;
                    }
                    continue;
                }

                if (ticketService.isRowEmpty(row)) continue;

                try {
                    String material = headerMap.containsKey(COL_MATERIAL) ? ticketService.getCellValueAsString(row.getCell(headerMap.get(COL_MATERIAL))) : "";
                    String serie = headerMap.containsKey(COL_SERIE) ? ticketService.getCellValueAsString(row.getCell(headerMap.get(COL_SERIE))) : "";
                    String guia = headerMap.containsKey(COL_GUIA) ? ticketService.getCellValueAsString(row.getCell(headerMap.get(COL_GUIA))) : "";
                    LocalDate fechaEnvio = headerMap.containsKey(COL_FECHA_ENVIO) ? ticketService.getCellValueAsLocalDate(row.getCell(headerMap.get(COL_FECHA_ENVIO))) : null;
                    
                    String incidenciaStr = headerMap.containsKey(COL_INCIDENCIA) ? ticketService.getCellValueAsString(row.getCell(headerMap.get(COL_INCIDENCIA))) : "";
                    String incidenciaLimpia = incidenciaStr.replace(".0", "").replaceAll("[^a-zA-Z0-9-]", "").trim();
                    
                    Inventario inventarioExistente = inventarioRepository.findByNumeroDeSerie(serie).orElse(null);
                    Inventario inventario = null;
                    
                    if (material.isEmpty() && serie.isEmpty() && incidenciaLimpia.isEmpty()) continue; 
                    if (serie.isEmpty()) {
                          response.agregarError("Fila " + rowIndex + ": El número de serie está vacío.");
                          continue;
                    }

                    if (inventarioExistente != null) {
                        inventario = inventarioExistente;
                    } else {
                        inventario = new Inventario();
                        inventario.setNumeroDeSerie(serie);
                    }

                    String tipoEquipo = determinarTipoEquipo(material);
                    
                    inventario.setDescripcion(material);       
                    inventario.setGuias(guia);
                    inventario.setEquipo(tipoEquipo); 
                    inventario.setUltimaActualizacion(LocalDate.now());
                    inventario.setEstado("Para instalar");
                    
                    if (!incidenciaLimpia.isEmpty()) {
                        inventario.setNumeroDeIncidencia(incidenciaLimpia); 
                    }

                    inventarioRepository.save(inventario);
                    response.incrementarExito();

                    if (!incidenciaLimpia.isEmpty()) {
                        List<Tickets> ticketsEncontrados = ticketRepository.findByServicios_Incidencia(incidenciaLimpia);
                        
                        if (!ticketsEncontrados.isEmpty()) {
                            Tickets ticket = ticketsEncontrados.get(0);
                            
                            Servicio servicio = ticket.getServicios();
                            if (servicio != null) {
                                servicio.setFechaDeEnvio(fechaEnvio); 
                                servicio.setGuiaDeEncomienda(guia);
                            }

                             Adicional adicionales = ticket.getAdicionales();
                             if (adicionales != null) {
                                 if(inventario.getEquipo().equals("SIM ATT") || inventario.getEquipo().equals("SIM TELCEL")){
                                     adicionales.setSim(inventario.getNumeroDeSerie());
                                 } else{
                                     adicionales.setSerieFisicaEntra(inventario.getNumeroDeSerie());
                                 }
                             }
                            ticketRepository.save(ticket); 
                        } else {
                            response.agregarAdvertencia("Fila " + rowIndex + ": La Incidencia '" + incidenciaLimpia + "' no fue encontrada. No se actualizó el Ticket ni sus entidades.");
                        }
                    }

                } catch (Exception e) {
                    response.agregarError("Fila " + rowIndex + ": Error al procesar datos (" + e.getMessage() + ")");
                    e.printStackTrace();
                }
            }
            
            if (!headersFound) {
                response.agregarError("No se encontró la fila de encabezados requerida.");
            } else {
                response.setTotalProcesados(rowIndex);
            }

        } catch (IOException e) {
            response.agregarError("Error crítico al leer archivo: " + e.getMessage());
        }

        return response;
    }

    private String determinarTipoEquipo(String descripcionMaterial) {
        if (descripcionMaterial == null || descripcionMaterial.isEmpty()) {
            return "OTROS";
        }
        String desc = descripcionMaterial.toUpperCase().trim();

        if (desc.contains("ELIMINADOR")) return "ELIMINADOR";
        if (desc.contains("BATERIA") || desc.contains("BATERÍA")) return "BATERIA";
        if (desc.contains("BASE DE CARGA")) return "BASE DE CARGA";
        if (desc.contains("SIM TELCEL")) return "SIM TELCEL";
        if (desc.contains("SIM AT&T") || desc.contains("SIM ATT") || desc.contains("SIMAT&T")) return "SIM ATT";
        if (desc.contains("VX520 TRÍO CTLS") || desc.contains("VX520 TRIO CTLS") || desc.contains("CTLS")) return "VX520 CTLS";
        if (desc.contains("VX520 C") || desc.contains("VX520 TRIO C")) return "VX520 C";
        if (desc.contains("VX690")) return "VX690";
        if (desc.contains("V240M")) return "V240M";
        if (desc.contains("N910")) return "N910";
        if (desc.contains("LANE 3000")) return "Ingenico Lane 3000";
        if (desc.contains("LINK 2500")) return "Ingenico Link 2500";

        return "OTROS"; 
    }

    public Map<String, Object> saveAllWithReport(List<Inventario> estaciones) {
        List<Inventario> guardadas = new ArrayList<>();
        List<Map<String, Object>> errores = new ArrayList<>();

        for (int i = 0; i < estaciones.size(); i++) {
            try {
                guardadas.add(inventarioRepository.save(estaciones.get(i)));
            } catch (Exception e) {
                errores.add(Map.of("index", i, "error", e.getMessage()));
            }
        }
        return Map.of("guardadas", guardadas, "errores", errores);
    }
}