package com.arjusven.backend.service;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.arjusven.backend.model.Adicional;
import com.arjusven.backend.model.Estaciones;
import com.arjusven.backend.model.Inventario;
import com.arjusven.backend.model.PivoteInventario;
import com.arjusven.backend.model.Tickets;
import com.arjusven.backend.repository.AdicionalRepository;
import com.arjusven.backend.repository.InventarioRepository;
import com.arjusven.backend.repository.PivoteInventarioRepository;

@Service
public class AdicionalService {

	
	private AdicionalRepository adicionalRepository;
	private InventarioRepository inventarioRepository;
	private PivoteInventarioRepository pivoteInventarioRepository;
	private EstacionesService estacionesService;
	
	@Autowired
	public AdicionalService(
			AdicionalRepository adicionalRepository,
			InventarioRepository inventarioRepository,
			PivoteInventarioRepository pivoteInventarioRepository,
			EstacionesService estacionesService
			) {
		this.adicionalRepository = adicionalRepository;
		this.inventarioRepository = inventarioRepository;
		this.pivoteInventarioRepository = pivoteInventarioRepository;
		this.estacionesService = estacionesService;
	}
	
	public List<Adicional> getAllAdicional(){
		return adicionalRepository.findAll();
	}
	
	
	public Adicional getAdicionalById(Long id) {
		return adicionalRepository.findById(id).orElse(null);
	}
	
	public void assignEstacionDetails(Adicional adicionales, Long merchantId) { // Modificación aquí
	    
	    if (merchantId != null) {
	        // Usamos el EstacionesService (inyectado previamente)
	        Optional<Estaciones> estacionesOpt = estacionesService.findById(merchantId);
	        
	        if (estacionesOpt.isPresent()) {
	            Estaciones estacion = estacionesOpt.get();
	            
	            // 1. Asignar PLAZA (Si está nulo o vacío)
	            String plazaActual = adicionales.getPlaza();
	            if (plazaActual == null || plazaActual.trim().isEmpty()) {
	                adicionales.setPlaza(estacion.getPlazaDeAtencion()); 
	            }
	            
	            // 2. Asignar CIUDAD/ESTADO (Si está nulo, vacío o es el placeholder "—")
	            String ciudadActual = adicionales.getCiudad();
	            if (ciudadActual == null || ciudadActual.trim().isEmpty() || ciudadActual.equals("—")) {
	                adicionales.setCiudad(estacion.getEstado()); 
	            }
	        } else {
	            System.out.println("Advertencia: ID Merchant [" + merchantId + "] no encontrado en Estaciones para Adicionales.");
	        }
	    }
	}

//	public Adicional saveAdicional(Adicional adicionales) {
	//	return adicionalRepository.save(adicionales);
	//}
	
	
	public Adicional patchAdicionales(Long id, Adicional adicionalesDetails) {
	    // 1. Buscar la entidad existente o lanzar una excepción.
	    Adicional adicionalesExistentes = adicionalRepository.findById(id)
	            .orElseThrow(() -> new NoSuchElementException("La entidad Adicionales con el ID [" + id + "] no fue encontrada para actualizar."));

	    // 2. Aplicar lógica de actualización parcial (PATCH): solo actualizar si el valor no es nulo.

	    if (adicionalesDetails.getCiudad() != null) {
	        adicionalesExistentes.setCiudad(adicionalesDetails.getCiudad());
	    }
	    
	    if (adicionalesDetails.getCerroEnPuntoClave() != null) {
	        adicionalesExistentes.setCerroEnPuntoClave(adicionalesDetails.getCerroEnPuntoClave());
	    }
	    
	    if (adicionalesDetails.getTarjeta() != null) {
	        adicionalesExistentes.setTarjeta(adicionalesDetails.getTarjeta());
	    }
	    
	    if (adicionalesDetails.getMarcaEntra() != null) {
	        adicionalesExistentes.setMarcaEntra(adicionalesDetails.getMarcaEntra());
	    }
	    
	    if (adicionalesDetails.getSim() != null) {
	        adicionalesExistentes.setSim(adicionalesDetails.getSim());
	    }
	    
	    if (adicionalesDetails.getModeloSale() != null) {
	        adicionalesExistentes.setModeloSale(adicionalesDetails.getModeloSale());
	    }
	    
	    if (adicionalesDetails.getSerieFisicaSale() != null) {
	        adicionalesExistentes.setSerieFisicaSale(adicionalesDetails.getSerieFisicaSale());
	    }
	    
	    if (adicionalesDetails.getEliminadorSale() != null) {
	        adicionalesExistentes.setEliminadorSale(adicionalesDetails.getEliminadorSale());
	    }
	    
	    if (adicionalesDetails.getTipoDeComunicacion() != null) {
	        adicionalesExistentes.setTipoDeComunicacion(adicionalesDetails.getTipoDeComunicacion());
	    }
	    
	    if (adicionalesDetails.getOrdenDeServicio() != null) {
	        adicionalesExistentes.setOrdenDeServicio(adicionalesDetails.getOrdenDeServicio());
	    }
	    
	    if (adicionalesDetails.getModeloDeStock() != null) {
	        adicionalesExistentes.setModeloDeStock(adicionalesDetails.getModeloDeStock());
	    }
	    
	    if (adicionalesDetails.getPlaza() != null) {
	        adicionalesExistentes.setPlaza(adicionalesDetails.getPlaza());
	    }
	    
	    if (adicionalesDetails.getAtencionEnPunto() != null) {
	        adicionalesExistentes.setAtencionEnPunto(adicionalesDetails.getAtencionEnPunto());
	    }
	    
	    if (adicionalesDetails.getCantidadTpv() != null) {
	        adicionalesExistentes.setCantidadTpv(adicionalesDetails.getCantidadTpv());
	    }
	    
	    if (adicionalesDetails.getSerieLogicaEntra() != null) {
	        adicionalesExistentes.setSerieLogicaEntra(adicionalesDetails.getSerieLogicaEntra());
	    } 
	    
	    if (adicionalesDetails.getPtidEntra() != null) {
	        adicionalesExistentes.setPtidEntra(adicionalesDetails.getPtidEntra());
	    }
	    
	    if (adicionalesDetails.getMarcaSale() != null) {
	        adicionalesExistentes.setMarcaSale(adicionalesDetails.getMarcaSale());
	    }
	    
	    if (adicionalesDetails.getSimSale() != null) {
	        adicionalesExistentes.setSimSale(adicionalesDetails.getSimSale());
	    }
	    
	    if (adicionalesDetails.getVersionDeBrowserSale() != null) {
	        adicionalesExistentes.setVersionDeBrowserSale(adicionalesDetails.getVersionDeBrowserSale());
	    }
	    
	    if (adicionalesDetails.getVersionDeBrowserEntra() != null) {
	        adicionalesExistentes.setVersionDeBrowserEntra(adicionalesDetails.getVersionDeBrowserEntra());
	    }
	    
	    if (adicionalesDetails.getTipoDeComunicacionSale() != null) {
	        adicionalesExistentes.setTipoDeComunicacionSale(adicionalesDetails.getTipoDeComunicacionSale());
	    }
	    
	    if (adicionalesDetails.getSerieQueQuedaDeStock() != null) {
	        adicionalesExistentes.setSerieQueQuedaDeStock(adicionalesDetails.getSerieQueQuedaDeStock());
	    }
	    
	    if (adicionalesDetails.getTecnico() != null) {
	        adicionalesExistentes.setTecnico(adicionalesDetails.getTecnico());
	    } 
	    
	    if (adicionalesDetails.getFirmaEnEstacion() != null) {
	        adicionalesExistentes.setFirmaEnEstacion(adicionalesDetails.getFirmaEnEstacion());
	    }
	    
	    if (adicionalesDetails.getModeloEntra() != null) {
	        adicionalesExistentes.setModeloEntra(adicionalesDetails.getModeloEntra());
	    }
	    
	    if (adicionalesDetails.getSerieFisicaEntra() != null) {
	        adicionalesExistentes.setSerieFisicaEntra(adicionalesDetails.getSerieFisicaEntra());
	    }
	    
	    if (adicionalesDetails.getEliminadorEntra() != null) {
	        adicionalesExistentes.setEliminadorEntra(adicionalesDetails.getEliminadorEntra());
	    }
	    
	    if (adicionalesDetails.getSerieLogicaSale() != null) {
	        adicionalesExistentes.setSerieLogicaSale(adicionalesDetails.getSerieLogicaSale());
	    }
	    
	    if (adicionalesDetails.getPtidSale() != null) {
	        adicionalesExistentes.setPtidSale(adicionalesDetails.getPtidSale());
	    }
	    
	    if (adicionalesDetails.getEstado() != null) {
	        adicionalesExistentes.setEstado(adicionalesDetails.getEstado());
	    }
	    
	    if (adicionalesDetails.getSimQueQuedaDeStock() != null) {
	        adicionalesExistentes.setSimQueQuedaDeStock(adicionalesDetails.getSimQueQuedaDeStock());
	    }
	    
	    procesarVinculacionInventario(adicionalesExistentes);
	    
	    return adicionalRepository.save(adicionalesExistentes);
	}
	
	private void procesarVinculacionInventario(Adicional adicional) {
        String serieSale = (adicional.getSerieLogicaSale() != null) ? adicional.getSerieLogicaSale().trim() : "";
        String serieEntra = (adicional.getSerieLogicaEntra() != null) ? adicional.getSerieLogicaEntra().trim() : "";

        boolean haySalida = !serieSale.isEmpty();
        boolean hayEntrada = !serieEntra.isEmpty();

        // Validación de igualdad
        if (haySalida && hayEntrada && serieSale.equalsIgnoreCase(serieEntra)) {
            throw new IllegalArgumentException("Los números de serie de Salida y Entrada no pueden ser iguales.");
        }

        // Necesitamos el ticket padre para el historial
        Tickets ticketPadre = adicional.getTicket(); 

        if (haySalida) {
            System.out.println("Validando Salida: " + serieSale); // Log para debug
            actualizarEstadoInventario(serieSale, "Stock", ticketPadre);
        }

        if (hayEntrada) {
             System.out.println("Validando Entrada: " + serieEntra); // Log para debug
            actualizarEstadoInventario(serieEntra, "Instalado", ticketPadre);
        }
    }
	
	private void actualizarEstadoInventario(String numeroSerie, String nuevoEstado, Tickets ticket) {
        // Usamos IgnoreCase para ser más flexibles
        Inventario inventario = inventarioRepository.findByNumeroDeSerieIgnoreCase(numeroSerie)
                .orElseThrow(() -> new IllegalArgumentException(
                        "El número de serie '" + numeroSerie + "' no existe en el inventario."
                ));
        
        

        // Actualizar Inventario
        inventario.setEstado(nuevoEstado);
        inventario.setUltimaActualizacion(LocalDate.now());
        inventario.setPlaza(ticket.getAdicionales().getPlaza());
        inventario.setTecnico(ticket.getServicios().getTecnico());
        
        ticket.getServicios().setFechaDeAsignacion(LocalDate.now());
        

        if (ticket != null && ticket.getServicios() != null) {
            inventario.setNumeroDeIncidencia(ticket.getServicios().getIncidencia());
        }
        inventarioRepository.save(inventario);

        // Crear Historial si tenemos el ticket padre
        if (ticket != null) {
            PivoteInventario pivote = new PivoteInventario();
            pivote.setEstadoAsignado(nuevoEstado);
            pivote.setFechaAsignacion(LocalDate.now());
            pivote.setTicket(ticket);
            pivote.setInventario(inventario);
            pivoteInventarioRepository.save(pivote);
        }
    }
}
