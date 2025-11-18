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
	
public Estaciones updateEstacionesPatch(Long idMerchant, Estaciones updatedEstacion) {
        
        Optional<Estaciones> existingEstacionOptional = estacionesRepository.findById(idMerchant);

        if (existingEstacionOptional.isPresent()) {
            Estaciones existingEstacion = existingEstacionOptional.get();

            if (updatedEstacion.getAfiliadoAS400() != null) existingEstacion.setAfiliadoAS400(updatedEstacion.getAfiliadoAS400());
            if (updatedEstacion.getAfiliadoATPV() != null) existingEstacion.setAfiliadoATPV(updatedEstacion.getAfiliadoATPV());
            if (updatedEstacion.getControladorVolumetrico() != null) existingEstacion.setControladorVolumetrico(updatedEstacion.getControladorVolumetrico());
            if (updatedEstacion.getRankingEdenred() != null) existingEstacion.setRankingEdenred(updatedEstacion.getRankingEdenred());
            if (updatedEstacion.getModelo() != null) existingEstacion.setModelo(updatedEstacion.getModelo());
            if (updatedEstacion.getTipoDeConexion() != null) existingEstacion.setTipoDeConexion(updatedEstacion.getTipoDeConexion());
            if (updatedEstacion.getTipoSIM() != null) existingEstacion.setTipoSIM(updatedEstacion.getTipoSIM());
            if (updatedEstacion.getCarrier() != null) existingEstacion.setCarrier(updatedEstacion.getCarrier());
            if (updatedEstacion.getNombreComercial() != null) existingEstacion.setNombreComercial(updatedEstacion.getNombreComercial());
            if (updatedEstacion.getCodigoPEMEX() != null) existingEstacion.setCodigoPEMEX(updatedEstacion.getCodigoPEMEX());
            if (updatedEstacion.getTipoPEMEX() != null) existingEstacion.setTipoPEMEX(updatedEstacion.getTipoPEMEX());
            if (updatedEstacion.getDireccion() != null) existingEstacion.setDireccion(updatedEstacion.getDireccion());
            if (updatedEstacion.getColoniaAsentamiento() != null) existingEstacion.setColoniaAsentamiento(updatedEstacion.getColoniaAsentamiento());
            if (updatedEstacion.getCp() != null) existingEstacion.setCp(updatedEstacion.getCp());
            if (updatedEstacion.getMunicipio() != null) existingEstacion.setMunicipio(updatedEstacion.getMunicipio());
            if (updatedEstacion.getEstado() != null) existingEstacion.setEstado(updatedEstacion.getEstado());
            if (updatedEstacion.getTelefono1() != null) existingEstacion.setTelefono1(updatedEstacion.getTelefono1());
            if (updatedEstacion.getTelefono2() != null) existingEstacion.setTelefono2(updatedEstacion.getTelefono2());
            if (updatedEstacion.getSoporteNoviembre2022() != null) existingEstacion.setSoporteNoviembre2022(updatedEstacion.getSoporteNoviembre2022());
            if (updatedEstacion.getCobertura() != null) existingEstacion.setCobertura(updatedEstacion.getCobertura());
            if (updatedEstacion.getPlazaDeAtencion() != null) existingEstacion.setPlazaDeAtencion(updatedEstacion.getPlazaDeAtencion());
            if (updatedEstacion.getAs400() != null) existingEstacion.setAs400(updatedEstacion.getAs400());
            if (updatedEstacion.getBo() != null) existingEstacion.setBo(updatedEstacion.getBo());
            if (updatedEstacion.getGrupo() != null) existingEstacion.setGrupo(updatedEstacion.getGrupo());
            if (updatedEstacion.getPrioridad() != null) existingEstacion.setPrioridad(updatedEstacion.getPrioridad());
            if (updatedEstacion.getReferencias() != null) existingEstacion.setReferencias(updatedEstacion.getReferencias());
            if (updatedEstacion.getSupervisorArjus() != null) existingEstacion.setSupervisorArjus(updatedEstacion.getSupervisorArjus());
            if (updatedEstacion.getTecnicoAsignado() != null) existingEstacion.setTecnicoAsignado(updatedEstacion.getTecnicoAsignado());
            if (updatedEstacion.getCantPOSActivas() != null) existingEstacion.setCantPOSActivas(updatedEstacion.getCantPOSActivas());
            if (updatedEstacion.getKm() != null) existingEstacion.setKm(updatedEstacion.getKm());
            if (updatedEstacion.getRollos() != null) existingEstacion.setRollos(updatedEstacion.getRollos());
            if (updatedEstacion.getTransporte() != null) existingEstacion.setTransporte(updatedEstacion.getTransporte());
            
            return estacionesRepository.save(existingEstacion);
        } else {
            return null;
        }
    }
	
}
