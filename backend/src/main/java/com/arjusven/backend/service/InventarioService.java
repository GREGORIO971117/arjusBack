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
        TicketUploadResponse response = new TicketUploadResponse(); // Reutilizando tu DTO de respuesta

        // 1. Validar Admin
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
            
            // Nombres de columnas esperados (basado en tu imagen y requerimiento)
            String COL_MATERIAL = "material entregado";
            String COL_SERIE = "serie";
            String COL_GUIA = "guia";

            int rowIndex = 0; 

            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                rowIndex++;

                // --- FASE 1: BUSCANDO ENCABEZADOS ---
                if (!headersFound) {
                    for (Cell cell : row) {
                        String cellValue = ticketService.getCellValueAsString(cell).toLowerCase().trim();
                        
                        // Mapeo dinámico de columnas
                        if (cellValue.contains(COL_MATERIAL)) headerMap.put(COL_MATERIAL, cell.getColumnIndex());
                        else if (cellValue.equals(COL_SERIE)) headerMap.put(COL_SERIE, cell.getColumnIndex()); // equals para ser más exacto con "serie"
                        else if (cellValue.contains(COL_GUIA)) headerMap.put(COL_GUIA, cell.getColumnIndex());
                    }

                    // Verificamos si encontramos al menos Material y Serie (Guía podría ser opcional, depende de tu regla)
                    if (headerMap.containsKey(COL_MATERIAL) && headerMap.containsKey(COL_SERIE)) {
                        headersFound = true;
                        continue; 
                    }
                    
                    headerMap.clear();
                    continue;
                }

                // --- FASE 2: PROCESANDO DATOS ---
                
                if (ticketService.isRowEmpty(row)) continue;

                try {
                    // Extraer datos usando el mapa
                    String material = headerMap.containsKey(COL_MATERIAL) ? ticketService.getCellValueAsString(row.getCell(headerMap.get(COL_MATERIAL))) : "";
                    String serie = headerMap.containsKey(COL_SERIE) ? ticketService.getCellValueAsString(row.getCell(headerMap.get(COL_SERIE))) : "";
                    String guia = headerMap.containsKey(COL_GUIA) ? ticketService.getCellValueAsString(row.getCell(headerMap.get(COL_GUIA))) : "";

                    // Validación básica
                    if (material.isEmpty() && serie.isEmpty()) {
                        continue; 
                    }

                    // Limpieza de Serie (La imagen muestra notación científica '8.95E+19', dataFormatter ayuda pero a veces hay que limpiar)
                    // Si serie viene vacía, decidimos si saltar o guardar. Asumiré que Serie es requerida.
                    if (serie.isEmpty()) {
                         response.agregarError("Fila " + rowIndex + ": El número de serie está vacío.");
                         continue;
                    }

                    // CREAR Y GUARDAR INVENTARIO
                    Inventario nuevoInventario = new Inventario();
                    
                    // Mapeo de datos solicitados
                    nuevoInventario.setDescripcion(material);      // material entregado -> Inventario.descripcion
                    nuevoInventario.setNumeroDeSerie(serie);       // serie -> Inventario.numeroDeSerie
                    nuevoInventario.setGuias(guia);                // guia -> Inventario.guias (o ticket.guia según lógica de negocio)
                    
                    // Datos de auditoría o por defecto
                    // nuevoInventario.setResponsable(administrador.getNombre()); // Opcional: asignar al admin como responsable inicial
                    nuevoInventario.setUltimaActualizacion(LocalDate.now());

                    saveInventario(nuevoInventario);
                    response.incrementarExito();

                } catch (Exception e) {
                    response.agregarError("Fila " + rowIndex + ": Error al guardar inventario (" + e.getMessage() + ")");
                    e.printStackTrace();
                }
            }
            
            if (!headersFound) {
                response.agregarError("No se encontró la fila de encabezados. Se requieren: 'Material Entregado' y 'Serie'.");
            } else {
                response.setTotalProcesados(rowIndex);
            }

        } catch (IOException e) {
            response.agregarError("Error crítico al leer archivo: " + e.getMessage());
        }

        return response;
    }
    
    
}