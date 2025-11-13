package com.arjusven.backend.service;

import com.arjusven.backend.model.Servicio;
import com.arjusven.backend.model.Adicional;
import org.apache.poi.xwpf.usermodel.*;
import org.springframework.stereotype.Service;
import java.io.*;
import java.util.*;

@Service
public class DocumentGenerationService {

    public byte[] generateTicketDocument(Servicio servicio, Adicional adicional) throws IOException {
        try (InputStream is = getClass().getResourceAsStream("/templates/TicketPlantilla.docx");
             XWPFDocument document = new XWPFDocument(is);
             ByteArrayOutputStream baos = new ByteArrayOutputStream()) {

            // Creamos un mapa de reemplazo unificado a partir de las dos entidades
            Map<String, String> replacements = createReplacementMap(servicio, adicional);
            
            // Lógica de reemplazo de Apache POI
            replacePlaceholdersInDocument(document, replacements);

            document.write(baos);
            return baos.toByteArray();
        } catch (IOException e) {
            // Manejar la excepción: registra el error.
            throw new IOException("Error al cargar o generar el documento Word. Verifique la ubicación de la plantilla.", e);
        }
    }

    // --- LÓGICA CLAVE: Unir y Mapear las dos Entidades a la Plantilla ---
    private Map<String, String> createReplacementMap(Servicio servicio, Adicional adicional) {
        Map<String, String> map = new HashMap<>();
        
        // 1. Mapeo de campos de la entidad SERVICIO
        map.put("${resolucion}", servicio.getResolucion());
        map.put("${incidencia}", servicio.getIncidencia()); // Coincide con tu Incidencia
        map.put("${nombreDeEss}", servicio.getNombreDeEss());
        map.put("${motivoDeServicio}", servicio.getMotivoDeServicio());
        map.put("${motivoReal}", servicio.getMotivoReal());
        map.put("${observaciones}", servicio.getObservaciones());
        // El campo 'tecnico' se usa en la sección de cierre
        
        // 2. Mapeo de campos de la entidad ADICIONAL (Donde están la mayoría de los equipos)
        // EQUIPO QUE SALE
        map.put("${modeloSale}", adicional.getModeloSale());
        map.put("${versionDeBrowserSale}", adicional.getVersionDeBrowserSale());
        map.put("${serieLogicaSale}", adicional.getSerieLogicaSale());
        map.put("${serieFisicaSale}", adicional.getSerieFisicaSale());
        map.put("${ptidSale}", adicional.getPtidSale());
        map.put("${tipoDeComunicacionSale}", adicional.getTipoDeComunicacionSale());
        map.put("${simSale}", adicional.getSimSale());
        map.put("${eliminadorSale}", adicional.getEliminadorSale());

        // EQUIPO QUE ENTRA
        map.put("${modeloEntra}", adicional.getModeloEntra());
        map.put("${versionDeBrowserEntra}", adicional.getVersionDeBrowserEntra());
        map.put("${serieLogicaEntra}", adicional.getSerieLogicaEntra());
        map.put("${serieFisicaEntra}", adicional.getSerieFisicaEntra());
        map.put("${ptidEntra}", adicional.getPtidEntra());
        map.put("${tipoDeComunicacion}", adicional.getTipoDeComunicacion()); // Tipo de comunicación Entra (de Adicional)
        map.put("${simQueQuedaDeStock}", adicional.getSimQueQuedaDeStock());
        map.put("${eliminadorEntra}", adicional.getEliminadorEntra());

        // Cierre (Usamos los campos de Adicional que coinciden con la plantilla)
        map.put("${atencionEnPunto}", adicional.getAtencionEnPunto());
        map.put("${tecnico}", adicional.getTecnico());
        map.put("${firmaEnEstacion}", adicional.getFirmaEnEstacion());
        
        // Aseguramos que los valores nulos se reemplacen por cadena vacía
        map.replaceAll((k, v) -> v != null ? v.toString() : ""); 
        
        return map;
    }
    
    // --- LÓGICA DE REEMPLAZO DE POI (Debe ser copiada en esta clase) ---
    private void replacePlaceholdersInDocument(XWPFDocument document, Map<String, String> replacements) {
        // ... (código de reemplazo para párrafos y tablas) ...
        for (XWPFParagraph p : document.getParagraphs()) {
            replacePlaceholdersInParagraph(p, replacements);
        }
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
        String fullText = p.getText();
        boolean found = false;
        
        for (String key : replacements.keySet()) {
            if (fullText.contains(key)) {
                found = true;
                fullText = fullText.replace(key, replacements.get(key));
            }
        }
        
        if (found) {
            for (int i = p.getRuns().size() - 1; i >= 0; i--) {
                p.removeRun(i);
            }
            XWPFRun run = p.createRun();
            run.setText(fullText, 0);
        }
    }
}