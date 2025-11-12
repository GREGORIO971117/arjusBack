package com.arjusven.backend.model;

import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name = "Estaciones")
public class Estaciones{

    @Id
    @Column(name = "ID_Merchant")
    private Long idMerchant; 
    
    @Column(name = "Afiliado_AS_400")
    private String afiliadoAS400;

    @Column(name = "Afiliado_ATPV")
    private String afiliadoATPV;

    @Column(name = "Controlador_Volumetrico")
    private String controladorVolumetrico;

    @Column(name = "Ranking_Edenred")
    private String rankingEdenred;

    @Column(name = "Modelo")
    private String modelo;

    @Column(name = "Tipo_de_conexion")
    private String tipoDeConexion;

    @Column(name = "Tipo_SIM")
    private String tipoSIM;

    @Column(name = "Carrier")
    private String carrier;

    @Column(name = "Cant_POS_Activas")
    private Integer cantPOSActivas; 
    
    @Column(name = "Nombre_Comercial")
    private String nombreComercial;

    @Column(name = "Codigo_PEMEX")
    private String codigoPEMEX;

    @Column(name = "Tipo_PEMEX")
    private String tipoPEMEX;

    @Column(name = "Direccion")
    private String direccion;

    @Column(name = "Colonia_Asentamiento")
    private String coloniaAsentamiento;

    @Column(name = "CP") 
    private String cp;

    @Column(name = "Municipio")
    private String municipio;

    @Column(name = "Estado")
    private String estado;

    @Column(name = "Telefono_1")
    private String telefono1;

    @Column(name = "Telefono_2")
    private String telefono2;

    @Column(name = "Soporte_Noviembre_2022")
    private String soporteNoviembre2022;

    @Column(name = "KM")
    private Double km; 

    @Column(name = "Cobertura")
    private String cobertura;

    @Column(name = "Plaza_de_atencion")
    private String plazaDeAtencion;

    @Column(name = "AS_400")
    private String as400; 

    @Column(name = "BO")
    private String bo;

    @Column(name = "Grupo")
    private String grupo;

    @Column(name = "Prioridad")
    private String prioridad;

    @Column(name = "Referencias")
    private String referencias;

    @Column(name = "SUPERVISOR_ARJUS")
    private String supervisorArjus;

    @Column(name = "Rollos")
    private Integer rollos;

    @Column(name = "Trasporte")
    private Long transporte;

    @Column(name = "Tecnico_asignado")
    private String tecnicoAsignado;

    @OneToMany(mappedBy = "estaciones", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Servicio> servicios;
   
    public Estaciones() {
    }

	public Long getIdMerchant() {
		return idMerchant;
	}

	public void setIdMerchant(Long idMerchant) {
		this.idMerchant = idMerchant;
	}

	public String getAfiliadoAS400() {
		return afiliadoAS400;
	}

	public void setAfiliadoAS400(String afiliadoAS400) {
		this.afiliadoAS400 = afiliadoAS400;
	}

	public String getAfiliadoATPV() {
		return afiliadoATPV;
	}

	public void setAfiliadoATPV(String afiliadoATPV) {
		this.afiliadoATPV = afiliadoATPV;
	}

	public String getControladorVolumetrico() {
		return controladorVolumetrico;
	}

	public void setControladorVolumetrico(String controladorVolumetrico) {
		this.controladorVolumetrico = controladorVolumetrico;
	}

	public String getRankingEdenred() {
		return rankingEdenred;
	}

	public void setRankingEdenred(String rankingEdenred) {
		this.rankingEdenred = rankingEdenred;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public String getTipoDeConexion() {
		return tipoDeConexion;
	}

	public void setTipoDeConexion(String tipoDeConexion) {
		this.tipoDeConexion = tipoDeConexion;
	}

	public String getTipoSIM() {
		return tipoSIM;
	}

	public void setTipoSIM(String tipoSIM) {
		this.tipoSIM = tipoSIM;
	}

	public String getCarrier() {
		return carrier;
	}

	public void setCarrier(String carrier) {
		this.carrier = carrier;
	}

	public Integer getCantPOSActivas() {
		return cantPOSActivas;
	}

	public void setCantPOSActivas(Integer cantPOSActivas) {
		this.cantPOSActivas = cantPOSActivas;
	}

	public String getNombreComercial() {
		return nombreComercial;
	}

	public void setNombreComercial(String nombreComercial) {
		this.nombreComercial = nombreComercial;
	}

	public String getCodigoPEMEX() {
		return codigoPEMEX;
	}

	public void setCodigoPEMEX(String codigoPEMEX) {
		this.codigoPEMEX = codigoPEMEX;
	}

	public String getTipoPEMEX() {
		return tipoPEMEX;
	}

	public void setTipoPEMEX(String tipoPEMEX) {
		this.tipoPEMEX = tipoPEMEX;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getColoniaAsentamiento() {
		return coloniaAsentamiento;
	}

	public void setColoniaAsentamiento(String coloniaAsentamiento) {
		this.coloniaAsentamiento = coloniaAsentamiento;
	}

	public String getCp() {
		return cp;
	}

	public void setCp(String cp) {
		this.cp = cp;
	}

	public String getMunicipio() {
		return municipio;
	}

	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getTelefono1() {
		return telefono1;
	}

	public void setTelefono1(String telefono1) {
		this.telefono1 = telefono1;
	}

	public String getTelefono2() {
		return telefono2;
	}

	public void setTelefono2(String telefono2) {
		this.telefono2 = telefono2;
	}

	public String getSoporteNoviembre2022() {
		return soporteNoviembre2022;
	}

	public void setSoporteNoviembre2022(String soporteNoviembre2022) {
		this.soporteNoviembre2022 = soporteNoviembre2022;
	}

	public Double getKm() {
		return km;
	}

	public void setKm(Double km) {
		this.km = km;
	}

	public String getCobertura() {
		return cobertura;
	}

	public void setCobertura(String cobertura) {
		this.cobertura = cobertura;
	}

	public String getPlazaDeAtencion() {
		return plazaDeAtencion;
	}

	public void setPlazaDeAtencion(String plazaDeAtencion) {
		this.plazaDeAtencion = plazaDeAtencion;
	}

	public String getAs400() {
		return as400;
	}

	public void setAs400(String as400) {
		this.as400 = as400;
	}

	public String getBo() {
		return bo;
	}

	public void setBo(String bo) {
		this.bo = bo;
	}

	public String getGrupo() {
		return grupo;
	}

	public void setGrupo(String grupo) {
		this.grupo = grupo;
	}

	public String getPrioridad() {
		return prioridad;
	}

	public void setPrioridad(String prioridad) {
		this.prioridad = prioridad;
	}

	public String getReferencias() {
		return referencias;
	}

	public void setReferencias(String referencias) {
		this.referencias = referencias;
	}

	public String getSupervisorArjus() {
		return supervisorArjus;
	}

	public void setSupervisorArjus(String supervisorArjus) {
		this.supervisorArjus = supervisorArjus;
	}

	public Integer getRollos() {
		return rollos;
	}

	public void setRollos(Integer rollos) {
		this.rollos = rollos;
	}

	public Long getTransporte() {
		return transporte;
	}

	public void setTransporte(Long transporte) {
		this.transporte = transporte;
	}

	public String getTecnicoAsignado() {
		return tecnicoAsignado;
	}

	public void setTecnicoAsignado(String tecnicoAsignado) {
		this.tecnicoAsignado = tecnicoAsignado;
	}
    
}