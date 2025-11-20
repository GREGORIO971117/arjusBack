package com.arjusven.backend.service;


import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.arjusven.backend.dto.TicketUploadResponse;
import com.arjusven.backend.model.Servicio;
import com.arjusven.backend.model.Tickets;
import com.arjusven.backend.model.Usuarios;
import com.arjusven.backend.repository.EstacionesRepository;
import com.arjusven.backend.repository.TicketRepository;
import com.arjusven.backend.repository.UsuariosRepository;

import jakarta.transaction.Transactional;

import java.io.IOException;
import java.util.*;

@Service
public class TicketService {
    
    private TicketRepository ticketsRepository;
    private ServicioService servicioService;
    private EstacionesRepository estacionesRepository;
    
    @Autowired
    public TicketService(TicketRepository ticketsRepository, ServicioService servicioService,EstacionesRepository estacionesRepository) {
		this.ticketsRepository = ticketsRepository;
		this.servicioService = servicioService;
		this.estacionesRepository = estacionesRepository;
	}
    
    
    @Autowired
    private UsuariosRepository usuariosRepository; 
    
    public TicketUploadResponse uploadTicketsFromExcel(MultipartFile file, Long idAdministrador) {
        TicketUploadResponse response = new TicketUploadResponse();

        // 1. Validar Admin
        Usuarios administrador = usuariosRepository.findById(idAdministrador).orElse(null);
        if (administrador == null) {
            response.agregarError("No se encontró el usuario administrador con ID: " + idAdministrador);
            return response;
        }

        //Se añade linea de comentario
        
        
        try (Workbook workbook = WorkbookFactory.create(file.getInputStream())) {
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();

            Map<String, Integer> headerMap = new HashMap<>();
            boolean headersFound = false;
            
            // Nombres de columnas esperados (en minúsculas para facilitar comparación)
            String COL_INCIDENCIA = "incidencia";
            String COL_ID_MERCHANT = "id merchant"; // Busca "id merchant" con espacio, ajusta si es "idmerchant"
            String COL_DETALLE = "detalle";
            String COL_OBSERVACIONES = "observaciones";

            int rowIndex = 0; // Contador real de filas del Excel

            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                rowIndex++; // Incrementamos fila actual (Excel empieza en 1 visualmente)

                // --- FASE 1: BUSCANDO ENCABEZADOS ---
                if (!headersFound) {
                    // Recorremos las celdas de esta fila para ver si es la fila de encabezados
                    for (Cell cell : row) {
                        String cellValue = getCellValueAsString(cell).toLowerCase().trim();
                        
                        if (cellValue.contains(COL_INCIDENCIA)) headerMap.put(COL_INCIDENCIA, cell.getColumnIndex());
                        else if (cellValue.contains(COL_ID_MERCHANT)) headerMap.put(COL_ID_MERCHANT, cell.getColumnIndex());
                        else if (cellValue.contains(COL_DETALLE)) headerMap.put(COL_DETALLE, cell.getColumnIndex());
                        else if (cellValue.contains(COL_OBSERVACIONES)) headerMap.put(COL_OBSERVACIONES, cell.getColumnIndex());
                    }

                    // Si encontramos AL MENOS Incidencia y ID Merchant en esta fila, bingo!
                    if (headerMap.containsKey(COL_INCIDENCIA) && headerMap.containsKey(COL_ID_MERCHANT)) {
                        headersFound = true;
                        // Ya tenemos el mapa, pasamos a la siguiente iteración que ya será DATA
                        continue; 
                    }
                    
                    // Si no encontramos las columnas clave, asumimos que es una fila de logo/título y la ignoramos.
                    // Limpiamos el mapa parcial para no mezclar basura de filas anteriores
                    headerMap.clear();
                    continue;
                }

                // --- FASE 2: PROCESANDO DATOS (Sólo llegamos aquí si headersFound es true) ---
                
                if (isRowEmpty(row)) continue; // Ignorar filas vacías entre datos

                try {
                    // Extraer datos usando el mapa dinámico que creamos arriba
                    String incidencia = getCellValueAsString(row.getCell(headerMap.get(COL_INCIDENCIA)));
                    
                    // ID Merchant a veces viene como texto o numérico, extraemos string seguro
                    Integer idxMerchant = headerMap.get(COL_ID_MERCHANT);
                    String idMerchantStr = (idxMerchant != null) ? getCellValueAsString(row.getCell(idxMerchant)) : "";
                    
                    String motivoServicio = headerMap.containsKey(COL_DETALLE) ? getCellValueAsString(row.getCell(headerMap.get(COL_DETALLE))) : "";
                    String observaciones = headerMap.containsKey(COL_OBSERVACIONES) ? getCellValueAsString(row.getCell(headerMap.get(COL_OBSERVACIONES))) : "";

                    // Validación básica de campos vacíos
                    if (incidencia.isEmpty() || idMerchantStr.isEmpty()) {
                        // Puede ser un pie de página o total, lo ignoramos o marcamos error leve
                        continue; 
                    }

                    // Parsear ID Merchant y Validar
                    Long idMerchant = null;
                    try {
                        // Limpieza agresiva: quitar ".0", comas, espacios
                        String limpio = idMerchantStr.replace(".0", "").replaceAll("[^0-9]", "").trim();
                        if(limpio.isEmpty()) throw new NumberFormatException();
                        idMerchant = Long.parseLong(limpio);
                    } catch (NumberFormatException e) {
                        response.agregarError("Fila " + rowIndex + ": ID Merchant inválido ('" + idMerchantStr + "').");
                        continue;
                    }

                    // VALIDACIÓN EXISTENCIA ESTACIÓN (Foreign Key check)
                    if (!estacionesRepository.existsById(idMerchant)) {
                        response.agregarError("Fila " + rowIndex + ": La Estación con ID " + idMerchant + " no existe. Ticket omitido.");
                        continue;
                    }

                    // ADVERTENCIA DUPLICADOS
                    if (!findTicketsByIncidencia(incidencia).isEmpty()) {
                        response.agregarAdvertencia("Fila " + rowIndex + ": La incidencia " + incidencia + " ya existe (Duplicado creado).");
                    }

                    // CREAR Y GUARDAR TICKET
                    Tickets nuevoTicket = new Tickets();
                    nuevoTicket.setAdministrador(administrador);

                    Servicio nuevoServicio = new Servicio();
                    nuevoServicio.setIncidencia(incidencia);
                    nuevoServicio.setIdMerchant(idMerchant);
                    nuevoServicio.setMotivoDeServicio(motivoServicio);
                    nuevoServicio.setObservaciones(observaciones);

                    nuevoTicket.setServicios(nuevoServicio);

                    saveTickets(nuevoTicket);
                    response.incrementarExito();

                } catch (Exception e) {
                    response.agregarError("Fila " + rowIndex + ": Error inesperado (" + e.getMessage() + ")");
                    e.printStackTrace();
                }
            }
            
            // Validación final: ¿Encontramos alguna vez los encabezados?
            if (!headersFound) {
                response.agregarError("No se encontró la fila de encabezados. Asegúrate que existan columnas llamadas 'Incidencia' e 'ID Merchant'.");
            } else {
                response.setTotalProcesados(rowIndex); // Informativo
            }

        } catch (IOException e) {
            response.agregarError("Error crítico al leer archivo: " + e.getMessage());
        }

        return response;
    }

    // Métodos auxiliares para leer celdas de Excel de forma segura
    private String getCellValueAsString(Cell cell) {
        if (cell == null) return "";
        DataFormatter formatter = new DataFormatter();
        return formatter.formatCellValue(cell).trim();
    }

    private boolean isRowEmpty(Row row) {
        if (row == null) return true;
        for (int c = row.getFirstCellNum(); c < row.getLastCellNum(); c++) {
            Cell cell = row.getCell(c);
            if (cell != null && cell.getCellType() != CellType.BLANK && !getCellValueAsString(cell).isEmpty())
                return false;
        }
        return true;
    }
    

    @Transactional
    public Tickets saveTickets(Tickets tickets) {
    	if(tickets.getServicios() != null) {
    		servicioService.assignEstacionesDetails(tickets.getServicios());
    	}
    	
        return ticketsRepository.save(tickets);
    }

    // Method to find a user by ID
    public Tickets getTicketsById(Long id) { // <-- Change return type to Optional<Tickets>
        return ticketsRepository.findById(id).orElseThrow(
        		()->new IllegalArgumentException("El ticket con el id" + id+ "no existe")
        		); 
    }

    public List<Tickets> getAllTickets() {
        return ticketsRepository.findAll();
    }
    
    public List<Tickets> findTicketsByIncidencia(String incidencia) {
        return ticketsRepository.findByServicios_Incidencia(incidencia);
    }
    
    public Tickets saveTicketsOnly(Tickets tickets) {
        return ticketsRepository.save(tickets);
    }
    
    public Tickets deleteTickets(Long id) {
    	Tickets ticket=null;
    	if(ticketsRepository.existsById(id)) {
    		ticket=ticketsRepository.findById(id).get();
    		ticketsRepository.deleteById(id);
    	}
    	return ticket;
    }
    
    
}