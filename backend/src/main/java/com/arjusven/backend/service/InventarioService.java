package com.arjusven.backend.service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.arjusven.backend.model.Inventario;
import com.arjusven.backend.model.PivoteInventario;
import com.arjusven.backend.model.Usuarios; // Asegúrate de tener este import
import com.arjusven.backend.repository.InventarioRepository;
import com.arjusven.backend.repository.PivoteInventarioRepository;
import com.arjusven.backend.repository.UsuariosRepository;
// Asumo que reutilizas la clase de respuesta, si tienes una específica para inventario cámbiala aquí
import com.arjusven.backend.dto.TicketUploadResponse;


@Service
public class InventarioService {
    
    private InventarioRepository inventarioRepository;
    private PivoteInventarioRepository pivoteInventarioRepository;
    private UsuariosRepository usuariosRepository;
    private TicketService ticketService;// Necesario para validar admin
    
    @Autowired
    public InventarioService(InventarioRepository inventarioRepository, 
                             PivoteInventarioRepository pivoteInventarioRepository,
                             UsuariosRepository usuariosRepository,
                             TicketService ticketService){
        this.inventarioRepository = inventarioRepository;
        this.pivoteInventarioRepository = pivoteInventarioRepository;
        this.usuariosRepository = usuariosRepository;
        this.ticketService = ticketService;
    }

    //Filtro del inventario
    
    public List<Inventario> filterInventario(String estado,String plaza,String equipo ,LocalDate fechaInicio, LocalDate fechaFin){
    	
    	return inventarioRepository.buscarPorFiltro(estado, plaza, equipo, fechaInicio, fechaFin);
    }
    
    

	public void deleteAllEstaciones() {
	    inventarioRepository.deleteAll();
	}
    
   
    public List<Inventario>searchInventario(String query){
    	 if (query == null || query.trim().isEmpty()) {
             return inventarioRepository.findAll();
         }
    	 
    	 String textoBusqueda = query.trim();
    	 
    	 List<Inventario> resultados = inventarioRepository.buscarExacto(textoBusqueda);
         // 3. INTENTO 2: Si la exacta no trajo nada, intentamos Búsqueda Parcial (LIKE)
         if (resultados.isEmpty()) {
             resultados = inventarioRepository.buscarParcial(textoBusqueda);
         }
         return resultados;

    }
    
    public List<PivoteInventario> obtenerHistorialPorInventario(Long idInventario) {
        // Verificamos que exista el inventario (opcional pero recomendado)
        if(!inventarioRepository.existsById(idInventario)){
             throw new NoSuchElementException("Inventario no encontrado");
        }
        
        // Simplemente devolvemos lo que encuentra el repositorio
        return pivoteInventarioRepository.findByInventario_IdInventario(idInventario);
    }
 
    @Transactional 
    public Inventario saveInventario(Inventario inventario) {
        return inventarioRepository.save(inventario);
    }
    
    public Inventario getInventarioById(Long id) {
        return inventarioRepository.findById(id).orElse(null);
    }

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
                    }
                    if (headerMap.containsKey(COL_MATERIAL) && headerMap.containsKey(COL_SERIE)) {
                        headersFound = true;
                        continue; 
                    }
                    headerMap.clear();
                    continue;
                }
                
                if (ticketService.isRowEmpty(row)) continue;

                try {
                    String material = headerMap.containsKey(COL_MATERIAL) ? ticketService.getCellValueAsString(row.getCell(headerMap.get(COL_MATERIAL))) : "";
                    String serie = headerMap.containsKey(COL_SERIE) ? ticketService.getCellValueAsString(row.getCell(headerMap.get(COL_SERIE))) : "";
                    String guia = headerMap.containsKey(COL_GUIA) ? ticketService.getCellValueAsString(row.getCell(headerMap.get(COL_GUIA))) : "";

                    if (material.isEmpty() && serie.isEmpty()) continue; 
                    if (serie.isEmpty()) {
                         response.agregarError("Fila " + rowIndex + ": El número de serie está vacío.");
                         continue;
                    }

                    // --- CLASIFICACIÓN DEL EQUIPO ---
                    // Aquí llamamos a la función que determina el tipo basándose en la columna 'material'
                    String tipoEquipo = determinarTipoEquipo(material);

                    Inventario nuevoInventario = new Inventario();
                    nuevoInventario.setDescripcion(material);      
                    nuevoInventario.setNumeroDeSerie(serie);       
                    nuevoInventario.setGuias(guia);
                    
                    // Asignamos el equipo clasificado automáticamente
                    nuevoInventario.setEquipo(tipoEquipo); 

                    nuevoInventario.setUltimaActualizacion(LocalDate.now());

                    saveInventario(nuevoInventario);
                    response.incrementarExito();

                } catch (Exception e) {
                    response.agregarError("Fila " + rowIndex + ": Error al guardar inventario (" + e.getMessage() + ")");
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

    // --- NUEVO MÉTODO DE CLASIFICACIÓN ---
    private String determinarTipoEquipo(String descripcionMaterial) {
        if (descripcionMaterial == null || descripcionMaterial.isEmpty()) {
            return "OTROS";
        }
        
        // Convertimos a mayúsculas para comparar fácilmente
        String desc = descripcionMaterial.toUpperCase().trim();

        // 1. ACCESORIOS (Prioridad Alta: para evitar que "Eliminador N910" se clasifique como "N910")
        if (desc.contains("ELIMINADOR")) return "ELIMINADOR";
        if (desc.contains("BATERIA") || desc.contains("BATERÍA")) return "BATERIA";
        if (desc.contains("BASE DE CARGA")) return "BASE DE CARGA";

        // 2. SIMs
        // Telcel
        if (desc.contains("SIM TELCEL")) return "SIM TELCEL";
        // AT&T (cubre "SIM AT&T", "SIM ATT", "SIMAT&T" si vienen pegados)
        if (desc.contains("SIM AT&T") || desc.contains("SIM ATT") || desc.contains("SIMAT&T")) return "SIM ATT";

        // 3. TERMINALES Y PINPADS
        // Verifone VX520 (Validar primero CTLS por ser más específico)
        if (desc.contains("VX520 TRÍO CTLS") || desc.contains("VX520 TRIO CTLS") || desc.contains("CTLS")) return "VX520 CTLS";
        if (desc.contains("VX520 C") || desc.contains("VX520 TRIO C")) return "VX520 C";
        
        // Otras Verifone
        if (desc.contains("VX690")) return "VX690";
        if (desc.contains("V240M")) return "V240M";

        // Newland
        if (desc.contains("N910")) return "N910";

        // Ingenico
        if (desc.contains("LANE 3000")) return "Ingenico Lane 3000";
        if (desc.contains("LINK 2500")) return "Ingenico Link 2500";

        // Si no coincide con nada, devolvemos un valor por defecto o la misma descripción
        return "OTROS"; 
    }
    
    
}