package com.siman.credisiman.visa.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TarjetasResponse {

	@JsonProperty("numeroTarjeta") @JsonAlias("numeroTarjeta")
	    public String numeroTarjeta;
	
	@JsonProperty("cuenta") @JsonAlias("cuenta")
	    public String cuenta;
	
	@JsonProperty("tipoTarjeta") @JsonAlias("tipoTarjeta")
	    public String tipoTarjeta;
	
	@JsonProperty("nombreTH") @JsonAlias("nombreTH")
	    public String nombreTH;
	
	@JsonProperty("estado") @JsonAlias("estado")
	    public String estado;
	
	@JsonProperty("limiteCreditoLocal") @JsonAlias("limiteCreditoLocal")
	    public String limiteCreditoLocal;
	
	@JsonProperty("limiteCreditoDolares") @JsonAlias("limiteCreditoDolares")
	    public String limiteCreditoDolares;
	
	@JsonProperty("saldoLocal") @JsonAlias("saldoLocal")
	    public String saldoLocal;
	
	@JsonProperty("saldoDolares") @JsonAlias("saldoDolares")
	    public String saldoDolares;
	
	@JsonProperty("disponibleLocal") @JsonAlias("disponibleLocal")
	    public String disponibleLocal;
	
	@JsonProperty("disponibleDolares") @JsonAlias("disponibleDolares")
	    public String disponibleDolares;
	
	@JsonProperty("pagoMinimoLocal") @JsonAlias("pagoMinimoLocal")
	    public String pagoMinimoLocal;
	
	@JsonProperty("pagoMinimoDolares") @JsonAlias("pagoMinimoDolares")
	    public String pagoMinimoDolares;
	
	@JsonProperty("pagoMinimoVencidoLocal") @JsonAlias("pagoMinimoVencidoLocal")
	    public String pagoMinimoVencidoLocal;
	
	@JsonProperty("pagoMinimoVencidoDolares") @JsonAlias("pagoMinimoVencidoDolares")
	    public String pagoMinimoVencidoDolares;
	
	@JsonProperty("pagoContadoLocal") @JsonAlias("pagoContadoLocal")
	    public String pagoContadoLocal;
	
	@JsonProperty("pagoContadoDolares") @JsonAlias("pagoContadoDolares")
	    public String pagoContadoDolares;
	
	@JsonProperty("fechaPago") @JsonAlias("fechaPago")
	    public String fechaPago;
	
	@JsonProperty("fechaUltimoCorte") @JsonAlias("fechaUltimoCorte")
	    public String fechaUltimoCorte;
	
	@JsonProperty("saldoMonedero") @JsonAlias("saldoMonedero")
	    public String saldoMonedero;
	
	@JsonProperty("rombosAcumulados") @JsonAlias("rombosAcumulados")
	    public String rombosAcumulados;
	
	@JsonProperty("rombosDinero") @JsonAlias("rombosDinero")
	    public String rombosDinero;
	
	@JsonProperty("fondosReservados") @JsonAlias("fondosReservados")
	    public String fondosReservados;

	public String getNumeroTarjeta() {
		return numeroTarjeta;
	}

	public void setNumeroTarjeta(String numeroTarjeta) {
		this.numeroTarjeta = numeroTarjeta;
	}

	public String getCuenta() {
		return cuenta;
	}

	public void setCuenta(String cuenta) {
		this.cuenta = cuenta;
	}

	public String getTipoTarjeta() {
		return tipoTarjeta;
	}

	public void setTipoTarjeta(String tipoTarjeta) {
		this.tipoTarjeta = tipoTarjeta;
	}

	public String getNombreTH() {
		return nombreTH;
	}

	public void setNombreTH(String nombreTH) {
		this.nombreTH = nombreTH;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getLimiteCreditoLocal() {
		return limiteCreditoLocal;
	}

	public void setLimiteCreditoLocal(String limiteCreditoLocal) {
		this.limiteCreditoLocal = limiteCreditoLocal;
	}

	public String getLimiteCreditoDolares() {
		return limiteCreditoDolares;
	}

	public void setLimiteCreditoDolares(String limiteCreditoDolares) {
		this.limiteCreditoDolares = limiteCreditoDolares;
	}

	public String getSaldoLocal() {
		return saldoLocal;
	}

	public void setSaldoLocal(String saldoLocal) {
		this.saldoLocal = saldoLocal;
	}

	public String getSaldoDolares() {
		return saldoDolares;
	}

	public void setSaldoDolares(String saldoDolares) {
		this.saldoDolares = saldoDolares;
	}

	public String getDisponibleLocal() {
		return disponibleLocal;
	}

	public void setDisponibleLocal(String disponibleLocal) {
		this.disponibleLocal = disponibleLocal;
	}

	public String getDisponibleDolares() {
		return disponibleDolares;
	}

	public void setDisponibleDolares(String disponibleDolares) {
		this.disponibleDolares = disponibleDolares;
	}

	public String getPagoMinimoLocal() {
		return pagoMinimoLocal;
	}

	public void setPagoMinimoLocal(String pagoMinimoLocal) {
		this.pagoMinimoLocal = pagoMinimoLocal;
	}

	public String getPagoMinimoDolares() {
		return pagoMinimoDolares;
	}

	public void setPagoMinimoDolares(String pagoMinimoDolares) {
		this.pagoMinimoDolares = pagoMinimoDolares;
	}

	public String getPagoMinimoVencidoLocal() {
		return pagoMinimoVencidoLocal;
	}

	public void setPagoMinimoVencidoLocal(String pagoMinimoVencidoLocal) {
		this.pagoMinimoVencidoLocal = pagoMinimoVencidoLocal;
	}

	public String getPagoMinimoVencidoDolares() {
		return pagoMinimoVencidoDolares;
	}

	public void setPagoMinimoVencidoDolares(String pagoMinimoVencidoDolares) {
		this.pagoMinimoVencidoDolares = pagoMinimoVencidoDolares;
	}

	public String getPagoContadoLocal() {
		return pagoContadoLocal;
	}

	public void setPagoContadoLocal(String pagoContadoLocal) {
		this.pagoContadoLocal = pagoContadoLocal;
	}

	public String getPagoContadoDolares() {
		return pagoContadoDolares;
	}

	public void setPagoContadoDolares(String pagoContadoDolares) {
		this.pagoContadoDolares = pagoContadoDolares;
	}

	public String getFechaPago() {
		return fechaPago;
	}

	public void setFechaPago(String fechaPago) {
		this.fechaPago = fechaPago;
	}

	public String getFechaUltimoCorte() {
		return fechaUltimoCorte;
	}

	public void setFechaUltimoCorte(String fechaUltimoCorte) {
		this.fechaUltimoCorte = fechaUltimoCorte;
	}

	public String getSaldoMonedero() {
		return saldoMonedero;
	}

	public void setSaldoMonedero(String saldoMonedero) {
		this.saldoMonedero = saldoMonedero;
	}

	public String getRombosAcumulados() {
		return rombosAcumulados;
	}

	public void setRombosAcumulados(String rombosAcumulados) {
		this.rombosAcumulados = rombosAcumulados;
	}

	public String getRombosDinero() {
		return rombosDinero;
	}

	public void setRombosDinero(String rombosDinero) {
		this.rombosDinero = rombosDinero;
	}

	public String getFondosReservados() {
		return fondosReservados;
	}

	public void setFondosReservados(String fondosReservados) {
		this.fondosReservados = fondosReservados;
	}
	

}
