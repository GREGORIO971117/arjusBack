package com.arjusven.backend.dto; // Ajusta el paquete si es necesario

import java.util.ArrayList;
import java.util.List;

public class TicketUploadResponse {
    private int totalProcesados;
    private int totalExitosos;
    private int totalFallidos;
    private List<String> errores;
    private List<String> advertencias;

    public TicketUploadResponse() {
        this.errores = new ArrayList<>();
        this.advertencias = new ArrayList<>();
    }

    public void agregarError(String error) {
        this.errores.add(error);
        this.totalFallidos++;
    }

    public void agregarAdvertencia(String advertencia) {
        this.advertencias.add(advertencia);
    }

    public void incrementarExito() {
        this.totalExitosos++;
    }

    // Getters y Setters
    public int getTotalProcesados() { return totalProcesados; }
    public void setTotalProcesados(int totalProcesados) { this.totalProcesados = totalProcesados; }
    public int getTotalExitosos() { return totalExitosos; }
    public int getTotalFallidos() { return totalFallidos; }
    public List<String> getErrores() { return errores; }
    public List<String> getAdvertencias() { return advertencias; }
}