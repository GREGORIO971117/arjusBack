package com.arjusven.backend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.arjusven.backend.model.Estaciones;
import com.arjusven.backend.repository.EstacionesRepository;

@Service
public class EstacionesService {
	
	private EstacionesRepository estacionesRepository;
	
	@Autowired
	public EstacionesService(EstacionesRepository estacionesRepository) {
		this.estacionesRepository = estacionesRepository;
	}
	
	public List<Estaciones> getAllEstaciones(){
		return estacionesRepository.findAll();
	}
	
	public Optional<Estaciones> findById(Long idMerchant) {
	    return estacionesRepository.findById(idMerchant);
	}
	
	public Estaciones saveEstaciones(Estaciones estaciones) {
		return estacionesRepository.save(estaciones);
	}
	public Estaciones deleteEstaciones(Long idMerchant) {
		
		Estaciones estaciones=null;
		
    	if(estacionesRepository.existsById(idMerchant)) {
    		estaciones=estacionesRepository.findById(idMerchant).get();
    		estacionesRepository.deleteById(idMerchant);
    	}
    	return estaciones;
    }
	
}
