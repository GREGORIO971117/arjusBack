package com.arjusven.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;

@Entity
@Table(name = "Adicionales")
public class Adicional {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idAdicionales")
    private Long idAdicionales;

    // Atributos mapeados de la tabla SQL
    @Column(name = "Ciudad")
    private String ciudad;
    
    @Column(name = "Cerro en punto clave")
    private String cerroEnPuntoClave;
    
    @Column(name = "Tarjeta")
    private String tarjeta;
    
    @Column(name = "Marca Entra")
    private String marcaEntra;
    
    @Column(name = "SIM")
    private String sim;
    
    @Column(name = "Modelo sale")
    private String modeloSale;
    
    @Column(name = "Serie fisica sale")
    private String serieFisicaSale;
    
    @Column(name = "Eliminador sale")
    private String eliminadorSale;
    
    @Column(name = "Tipo de comunicacion")
    private String tipoDeComunicacion;
    
    @Column(name = "Orden de servicio")
    private String ordenDeServicio;
    
    @Column(name = "Modelo de stock")
    private String modeloDeStock;
    
    @Column(name = "Plaza")
    private String plaza;
    
    @Column(name = "Atencion en punto")
    private String atencionEnPunto;
    
    @Column(name = "Cantidad TPV")
    private String cantidadTpv;
    
    // NOTA: Tu SQL tiene 2 veces "Serie logica entra" y "Eliminador entra". Mapeamos la primera ocurrencia.
    @Column(name = "Serie logica entra")
    private String serieLogicaEntra; 
    
    @Column(name = "PTID entra")
    private String ptidEntra;
    
    @Column(name = "Marca Sale")
    private String marcaSale;
    
    @Column(name = "SIM sale")
    private String simSale;
    
    @Column(name = "Version de browser")
    private String versionDeBrowser;
    
    @Column(name = "Tipo de comunicacion sale")
    private String tipoDeComunicacionSale;
    
    @Column(name = "Serie que queda de stock")
    private String serieQueQuedaDeStock;
    
    @Column(name = "Tecnico")
    private String tecnico; // Nota: Si el técnico es un usuario, esta columna de texto es redundante.
    
    @Column(name = "Firma en estacion")
    private String firmaEnEstacion;
    
    @Column(name = "Modelo entra")
    private String modeloEntra;
    
    @Column(name = "Serie fisica entra")
    private String serieFisicaEntra;
    
    @Column(name = "Eliminador entra")
    private String eliminadorEntra;
    
    @Column(name = "Serie logica sale")
    private String serieLogicaSale;
    
    @Column(name = "PTID sale")
    private String ptidSale;
    
    @Column(name = "Estado")
    private String estado;
    
    @Column(name = "SIM que queda de stock")
    private String simQueQuedaDeStock;

    // CLAVE FORÁNEA (Foreign Key)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Tickets_idTickets_FK", referencedColumnName = "idTickets") 
    @JsonIgnore
    private Tickets ticket;

	public Adicional() {
	
	}

	public Long getId() {
		return idAdicionales;
	}

	public void setId(Long id) {
		this.idAdicionales = id;
	}

	public String getCiudad() {
		return ciudad;
	}

	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}

	public String getCerroEnPuntoClave() {
		return cerroEnPuntoClave;
	}

	public void setCerroEnPuntoClave(String cerroEnPuntoClave) {
		this.cerroEnPuntoClave = cerroEnPuntoClave;
	}

	public String getTarjeta() {
		return tarjeta;
	}

	public void setTarjeta(String tarjeta) {
		this.tarjeta = tarjeta;
	}

	public String getMarcaEntra() {
		return marcaEntra;
	}

	public void setMarcaEntra(String marcaEntra) {
		this.marcaEntra = marcaEntra;
	}

	public String getSim() {
		return sim;
	}

	public void setSim(String sim) {
		this.sim = sim;
	}

	public String getModeloSale() {
		return modeloSale;
	}

	public void setModeloSale(String modeloSale) {
		this.modeloSale = modeloSale;
	}

	public String getSerieFisicaSale() {
		return serieFisicaSale;
	}

	public void setSerieFisicaSale(String serieFisicaSale) {
		this.serieFisicaSale = serieFisicaSale;
	}

	public String getEliminadorSale() {
		return eliminadorSale;
	}

	public void setEliminadorSale(String eliminadorSale) {
		this.eliminadorSale = eliminadorSale;
	}

	public String getTipoDeComunicacion() {
		return tipoDeComunicacion;
	}

	public void setTipoDeComunicacion(String tipoDeComunicacion) {
		this.tipoDeComunicacion = tipoDeComunicacion;
	}

	public String getOrdenDeServicio() {
		return ordenDeServicio;
	}

	public void setOrdenDeServicio(String ordenDeServicio) {
		this.ordenDeServicio = ordenDeServicio;
	}

	public String getModeloDeStock() {
		return modeloDeStock;
	}

	public void setModeloDeStock(String modeloDeStock) {
		this.modeloDeStock = modeloDeStock;
	}

	public String getPlaza() {
		return plaza;
	}

	public void setPlaza(String plaza) {
		this.plaza = plaza;
	}

	public String getAtencionEnPunto() {
		return atencionEnPunto;
	}

	public void setAtencionEnPunto(String atencionEnPunto) {
		this.atencionEnPunto = atencionEnPunto;
	}

	public String getCantidadTpv() {
		return cantidadTpv;
	}

	public void setCantidadTpv(String cantidadTpv) {
		this.cantidadTpv = cantidadTpv;
	}

	public String getSerieLogicaEntra() {
		return serieLogicaEntra;
	}

	public void setSerieLogicaEntra(String serieLogicaEntra) {
		this.serieLogicaEntra = serieLogicaEntra;
	}

	public String getPtidEntra() {
		return ptidEntra;
	}

	public void setPtidEntra(String ptidEntra) {
		this.ptidEntra = ptidEntra;
	}

	public String getMarcaSale() {
		return marcaSale;
	}

	public void setMarcaSale(String marcaSale) {
		this.marcaSale = marcaSale;
	}

	public String getSimSale() {
		return simSale;
	}

	public void setSimSale(String simSale) {
		this.simSale = simSale;
	}

	public String getVersionDeBrowser() {
		return versionDeBrowser;
	}

	public void setVersionDeBrowser(String versionDeBrowser) {
		this.versionDeBrowser = versionDeBrowser;
	}

	public String getTipoDeComunicacionSale() {
		return tipoDeComunicacionSale;
	}

	public void setTipoDeComunicacionSale(String tipoDeComunicacionSale) {
		this.tipoDeComunicacionSale = tipoDeComunicacionSale;
	}

	public String getSerieQueQuedaDeStock() {
		return serieQueQuedaDeStock;
	}

	public void setSerieQueQuedaDeStock(String serieQueQuedaDeStock) {
		this.serieQueQuedaDeStock = serieQueQuedaDeStock;
	}

	public String getTecnico() {
		return tecnico;
	}

	public void setTecnico(String tecnico) {
		this.tecnico = tecnico;
	}

	public String getFirmaEnEstacion() {
		return firmaEnEstacion;
	}

	public void setFirmaEnEstacion(String firmaEnEstacion) {
		this.firmaEnEstacion = firmaEnEstacion;
	}

	public String getModeloEntra() {
		return modeloEntra;
	}

	public void setModeloEntra(String modeloEntra) {
		this.modeloEntra = modeloEntra;
	}

	public String getSerieFisicaEntra() {
		return serieFisicaEntra;
	}

	public void setSerieFisicaEntra(String serieFisicaEntra) {
		this.serieFisicaEntra = serieFisicaEntra;
	}

	public String getEliminadorEntra() {
		return eliminadorEntra;
	}

	public void setEliminadorEntra(String eliminadorEntra) {
		this.eliminadorEntra = eliminadorEntra;
	}

	public String getSerieLogicaSale() {
		return serieLogicaSale;
	}

	public void setSerieLogicaSale(String serieLogicaSale) {
		this.serieLogicaSale = serieLogicaSale;
	}

	public String getPtidSale() {
		return ptidSale;
	}

	public void setPtidSale(String ptidSale) {
		this.ptidSale = ptidSale;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getSimQueQuedaDeStock() {
		return simQueQuedaDeStock;
	}

	public void setSimQueQuedaDeStock(String simQueQuedaDeStock) {
		this.simQueQuedaDeStock = simQueQuedaDeStock;
	}

	public Tickets getTicket() {
		return ticket;
	}

	public void setTicket(Tickets ticket) {
		this.ticket = ticket;
	}

	@Override
	public String toString() {
		return "Adicional [id=" + idAdicionales + ", ciudad=" + ciudad + ", cerroEnPuntoClave=" + cerroEnPuntoClave + ", tarjeta="
				+ tarjeta + ", marcaEntra=" + marcaEntra + ", sim=" + sim + ", modeloSale=" + modeloSale
				+ ", serieFisicaSale=" + serieFisicaSale + ", eliminadorSale=" + eliminadorSale
				+ ", tipoDeComunicacion=" + tipoDeComunicacion + ", ordenDeServicio=" + ordenDeServicio
				+ ", modeloDeStock=" + modeloDeStock + ", plaza=" + plaza + ", atencionEnPunto=" + atencionEnPunto
				+ ", cantidadTpv=" + cantidadTpv + ", serieLogicaEntra=" + serieLogicaEntra + ", ptidEntra=" + ptidEntra
				+ ", marcaSale=" + marcaSale + ", simSale=" + simSale + ", versionDeBrowser=" + versionDeBrowser
				+ ", tipoDeComunicacionSale=" + tipoDeComunicacionSale + ", serieQueQuedaDeStock="
				+ serieQueQuedaDeStock + ", tecnico=" + tecnico + ", firmaEnEstacion=" + firmaEnEstacion
				+ ", modeloEntra=" + modeloEntra + ", serieFisicaEntra=" + serieFisicaEntra + ", eliminadorEntra="
				+ eliminadorEntra + ", serieLogicaSale=" + serieLogicaSale + ", ptidSale=" + ptidSale + ", estado="
				+ estado + ", simQueQuedaDeStock=" + simQueQuedaDeStock + ", ticket=" + ticket + "]";
	}

   
}
