package com.arjusven.backend.service;

import com.arjusven.backend.model.Adicional;
import com.arjusven.backend.model.Servicio;
import com.arjusven.backend.model.Tickets;
import org.apache.poi.xwpf.usermodel.*;
import org.springframework.stereotype.Service;
import java.io.*;
import java.util.*;

@Service
public class DocumentGenerationService {

  
    public byte[] generateDefaultTicket(Tickets tickets) throws IOException {
        return generateDocument(tickets, "/templates/TicketPlantilla.docx"); 
    }
    public byte[] generateMantenimientoTicket(Tickets tickets) throws IOException {
        return generateDocument(tickets, "/templates/TicketPlantillaMantenimiento.docx");
    }
    public byte[] generateRetiroTicket(Tickets tickets) throws IOException {
        return generateDocument(tickets, "/templates/TicketPlantillaRetiro.docx");
    }


    private byte[] generateDocument(Tickets tickets, String templatePath) throws IOException {
        try (InputStream is = getClass().getResourceAsStream(templatePath);
             XWPFDocument document = new XWPFDocument(is);
             ByteArrayOutputStream baos = new ByteArrayOutputStream()) {

            if (is == null) {
                 throw new FileNotFoundException("Plantilla no encontrada en la ruta: " + templatePath);
            }

            // Creamos el mapa de reemplazo (la lógica es la misma para todas las plantillas)
            Map<String, String> replacements = createReplacementMap(tickets);
            
            // Lógica de reemplazo de Apache POI
            replacePlaceholdersInDocument(document, replacements);

            document.write(baos);
            return baos.toByteArray();
        } catch (IOException e) {
            // Manejar la excepción: registra el error.
            throw new IOException("Error al cargar o generar el documento Word. Verifique la ubicación de la plantilla. Path: " + templatePath, e);
        }
    }


    // --- LÓGICA CLAVE: Unir y Mapear las dos Entidades a la Plantilla ---
    private Map<String, String> createReplacementMap(Tickets tickets) {
        
        Map<String, String> map = new HashMap<>();
        
        // --- OBTENCIÓN SEGURA DE ENTIDADES ---
        Adicional adicionales = tickets.getAdicionales();
        Servicio servicios = tickets.getServicios();

        // 2. Mapeo de campos de la entidad ADICIONAL (Protegido contra NullPointerException)
        if (adicionales != null) {
            map.put("${modeloSale}", adicionales.getModeloSale());
            map.put("${versionDeBrowserSale}", adicionales.getVersionDeBrowserSale());
            map.put("${serieLogicaSale}", adicionales.getSerieLogicaSale());
            map.put("${serieFisicaSale}", adicionales.getSerieFisicaSale());
            map.put("${ptidSale}", adicionales.getPtidSale());
            map.put("${tipoDeComunicacionSale}", adicionales.getTipoDeComunicacionSale());
            map.put("${simSale}", adicionales.getSimSale());
            map.put("${eliminadorSale}", adicionales.getEliminadorSale());

            map.put("${modeloEntra}", adicionales.getModeloEntra());
            map.put("${versionDeBrowserEntra}", adicionales.getVersionDeBrowserEntra());
            map.put("${serieLogicaEntra}", adicionales.getSerieLogicaEntra());
            map.put("${serieFisicaEntra}", adicionales.getSerieFisicaEntra());
            map.put("${ptidEntra}", adicionales.getPtidEntra());
            map.put("${tipoDeComunicacion}", adicionales.getTipoDeComunicacion());
            map.put("${simQueQuedaDeStock}", adicionales.getSimQueQuedaDeStock());
            map.put("${eliminadorEntra}", adicionales.getEliminadorEntra());

            // Cierre
            map.put("${atencionEnPunto}", adicionales.getAtencionEnPunto());
            map.put("${firmaEnEstacion}", adicionales.getFirmaEnEstacion());
        }
        
        if (servicios != null) {
            map.put("${resolucion}", servicios.getResolucion());
            map.put("${tecnico}", servicios.getTecnico());
            map.put("${incidencia}", servicios.getIncidencia());
            map.put("${nombreDeEss}", servicios.getNombreDeEss());
            map.put("${motivoDeServicio}", servicios.getMotivoDeServicio());
            map.put("${motivoReal}", servicios.getMotivoReal());
            map.put("${observaciones}", servicios.getObservaciones());
        }
        
        // Aseguramos que los valores nulos se reemplacen por cadena vacía
        map.replaceAll((k, v) -> v != null ? v.toString() : ""); 
        
        return map;
    }

    // --- LÓGICA DE REEMPLAZO DE POI (Implementación Completa) ---

    private void replacePlaceholdersInDocument(XWPFDocument document, Map<String, String> replacements) {
        // Reemplazo en párrafos principales
        for (XWPFParagraph p : document.getParagraphs()) {
            replacePlaceholdersInParagraph(p, replacements);
        }
        // Reemplazo en tablas (celdas)
        for (XWPFTable table : document.getTables()) {
            for (XWPFTableRow row : table.getRows()) {
                for (XWPFTableCell cell : row.getTableCells()) {
                    for (XWPFParagraph p : cell.getParagraphs()) {
                        replacePlaceholdersInParagraph(p, replacements);
                    }
                }
            }
        }
    }
    
    private void replacePlaceholdersInParagraph(XWPFParagraph p, Map<String, String> replacements) {
        // Esto maneja placeholders que pueden estar divididos en múltiples Runs (común en Word)
        // Ejemplo: ${variable} puede ser ${var}iable
        String fullText = p.getText();
        boolean found = false;
        
        for (String key : replacements.keySet()) {
            if (fullText.contains(key)) {
                found = true;
                fullText = fullText.replace(key, replacements.get(key));
            }
        }
        
        if (found) {
            // Si se encontró un placeholder, eliminamos todos los runs existentes
            for (int i = p.getRuns().size() - 1; i >= 0; i--) {
                p.removeRun(i);
            }
            // Y creamos un nuevo run con el texto ya reemplazado
            XWPFRun run = p.createRun();
            run.setText(fullText, 0);
            
            // Opcional: Copiar formatos del primer run al nuevo run si fuera necesario,
            // pero para una solución simple de reemplazo, solo la línea anterior es suficiente.
        }
    }
}