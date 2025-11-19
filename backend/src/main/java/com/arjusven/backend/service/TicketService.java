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
        
        // 1. Validar existencia del Administrador
        Usuarios administrador = usuariosRepository.findById(idAdministrador)
                .orElse(null);

        if (administrador == null) {
            response.agregarError("No se encontró el usuario administrador con ID: " + idAdministrador + ". Proceso abortado.");
            return response;
        }

        try (Workbook workbook = WorkbookFactory.create(file.getInputStream())) {
            Sheet sheet = workbook.getSheetAt(0); // Leemos la primera hoja
            Iterator<Row> rowIterator = sheet.iterator();

            // Mapas para guardar la posición de las columnas dinámicamente
            Map<String, Integer> headerMap = new HashMap<>();
            
            // Definimos los nombres esperados (normalizados a minúsculas para comparar mejor)
            String COL_INCIDENCIA = "incidencia";
            String COL_ID_MERCHANT = "id merchant";
            String COL_DETALLE = "detalle";
            String COL_OBSERVACIONES = "observaciones";

            // 2. Leer Encabezados (Fila 0 o la primera que encuentre)
            if (rowIterator.hasNext()) {
                Row headerRow = rowIterator.next();
                for (Cell cell : headerRow) {
                    String headerValue = getCellValueAsString(cell).toLowerCase().trim();
                    
                    // Mapeo flexible
                    if (headerValue.contains(COL_INCIDENCIA)) headerMap.put(COL_INCIDENCIA, cell.getColumnIndex());
                    else if (headerValue.contains(COL_ID_MERCHANT)) headerMap.put(COL_ID_MERCHANT, cell.getColumnIndex());
                    else if (headerValue.contains(COL_DETALLE)) headerMap.put(COL_DETALLE, cell.getColumnIndex());
                    else if (headerValue.contains(COL_OBSERVACIONES)) headerMap.put(COL_OBSERVACIONES, cell.getColumnIndex());
                }
            }

            // Validar que encontramos todas las columnas necesarias
            if (!headerMap.containsKey(COL_INCIDENCIA) || !headerMap.containsKey(COL_ID_MERCHANT)) {
                response.agregarError("El archivo no contiene los encabezados obligatorios: Incidencia o ID Merchant.");
                return response;
            }

            // 3. Iterar sobre los datos
            int rowIndex = 1; // Solo para referencia en logs
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                rowIndex++;
                
                // Verificar si la fila está vacía
                if (isRowEmpty(row)) continue; 

                try {
                    // Extracción de datos usando el mapa de índices
                    String incidencia = getCellValueAsString(row.getCell(headerMap.get(COL_INCIDENCIA)));
                    String idMerchantStr = getCellValueAsString(row.getCell(headerMap.get(COL_ID_MERCHANT)));
                    String motivoServicio = headerMap.containsKey(COL_DETALLE) ? getCellValueAsString(row.getCell(headerMap.get(COL_DETALLE))) : "";
                    String observaciones = headerMap.containsKey(COL_OBSERVACIONES) ? getCellValueAsString(row.getCell(headerMap.get(COL_OBSERVACIONES))) : "";

                    if (incidencia.isEmpty() || idMerchantStr.isEmpty()) {
                        response.agregarError("Fila " + rowIndex + ": Incidencia o ID Merchant vacíos.");
                        continue;
                    }

                    // Parsear ID Merchant
                    Long idMerchant = null;
                    try {
                        idMerchant = Long.parseLong(idMerchantStr.replace(".0", "").trim());
                    } catch (NumberFormatException e) {
                        response.agregarError("Fila " + rowIndex + ": El ID Merchant '" + idMerchantStr + "' no es un número válido.");
                        continue;
                    }

                    // 4. Verificar Duplicados (Generar Advertencia)
                    List<Tickets> existentes = findTicketsByIncidencia(incidencia);
                    if (!existentes.isEmpty()) {
                        response.agregarAdvertencia("Fila " + rowIndex + ": La incidencia " + incidencia + " ya existía previamente. Se ha creado un duplicado.");
                    }

                    // 5. Crear Entidades
                    Tickets nuevoTicket = new Tickets();
                    nuevoTicket.setAdministrador(administrador); // Asignamos al usuario que sube el archivo

                    Servicio nuevoServicio = new Servicio();
                    nuevoServicio.setIncidencia(incidencia);
                    nuevoServicio.setIdMerchant(idMerchant);
                    nuevoServicio.setMotivoDeServicio(motivoServicio); // Viene de "Detalle"
                    nuevoServicio.setObservaciones(observaciones);

                    // Vinculación bidireccional
                    nuevoTicket.setServicios(nuevoServicio);
                    
                    if (!estacionesRepository.existsById(idMerchant)) {
                        response.agregarError("Fila " + rowIndex + ": El ID Merchant [" + idMerchant + "] no existe en la base de datos de Estaciones. No se puede crear el ticket.");
                        continue; // Saltamos a la siguiente fila del Excel sin guardar esta
                    }
                    // 6. Guardar (Esto llama a tu lógica assignEstacionesDetails automáticamente)
                    saveTickets(nuevoTicket);
                    
                    response.incrementarExito();

                } catch (Exception e) {
                    response.agregarError("Fila " + rowIndex + ": Error inesperado - " + e.getMessage());
                    e.printStackTrace();
                }
            }
            
            response.setTotalProcesados(rowIndex - 1); // Descontando encabezado

        } catch (IOException e) {
            response.agregarError("Error al leer el archivo: " + e.getMessage());
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