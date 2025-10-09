package com.arjusven.backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.arjusven.backend.model.Adicional;
import com.arjusven.backend.repository.AdicionalRepository;

@Service
public class AdicionalService {

	@Autowired
	private AdicionalRepository adicionalRepository;
	
	public Adicional saveAdicional(Adicional adicionales) {
		return adicionalRepository.save(adicionales);
	}
	
	public Adicional getAdicionalById(Long id) {
		return adicionalRepository.findById(id).orElse(null);
	}
	
	public List<Adicional> getAllAdicional(){
		return adicionalRepository.findAll();
	}
}
