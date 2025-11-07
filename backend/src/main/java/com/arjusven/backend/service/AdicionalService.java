package com.arjusven.backend.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.arjusven.backend.model.Adicional;
import com.arjusven.backend.repository.AdicionalRepository;

@Service
public class AdicionalService {

	
	private AdicionalRepository adicionalRepository;
	
	@Autowired
	public AdicionalService(AdicionalRepository adicionalRepository) {
		this.adicionalRepository = adicionalRepository;
	}
	
	public List<Adicional> getAllAdicional(){
		return adicionalRepository.findAll();
	}
	public Adicional getAdicionalById(Long id) {
		return adicionalRepository.findById(id).orElse(null);
	}

	public Adicional saveAdicional(Adicional adicionales) {
		return adicionalRepository.save(adicionales);
	}
	
	
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
	    return adicionalRepository.save(adicionalesExistentes);
	}
	
	
	
	
}
