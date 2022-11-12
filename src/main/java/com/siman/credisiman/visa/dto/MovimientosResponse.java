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
public class MovimientosResponse {

	@JsonProperty("tipoMovimiento") @JsonAlias("tipoMovimiento")
    public String tipoMovimiento;
	
	@JsonProperty("fechaMovimiento") @JsonAlias("fechaMovimiento")
	    public String fechaMovimiento;
	
	@JsonProperty("fechaAplicacion") @JsonAlias("fechaAplicacion")
	    public String fechaAplicacion;
	
	@JsonProperty("moneda") @JsonAlias("moneda")
	    public String moneda;
	
	@JsonProperty("monto") @JsonAlias("monto")
	    public String monto;
	
	@JsonProperty("numeroAutorizacion") @JsonAlias("numeroAutorizacion")
	    public String numeroAutorizacion;
	
	@JsonProperty("comercio") @JsonAlias("comercio")
	    public String comercio;

	public String getTipoMovimiento() {
		return tipoMovimiento;
	}

	public void setTipoMovimiento(String tipoMovimiento) {
		this.tipoMovimiento = tipoMovimiento;
	}

	public String getFechaMovimiento() {
		return fechaMovimiento;
	}

	public void setFechaMovimiento(String fechaMovimiento) {
		this.fechaMovimiento = fechaMovimiento;
	}

	public String getFechaAplicacion() {
		return fechaAplicacion;
	}

	public void setFechaAplicacion(String fechaAplicacion) {
		this.fechaAplicacion = fechaAplicacion;
	}

	public String getMoneda() {
		return moneda;
	}

	public void setMoneda(String moneda) {
		this.moneda = moneda;
	}

	public String getMonto() {
		return monto;
	}

	public void setMonto(String monto) {
		this.monto = monto;
	}

	public String getNumeroAutorizacion() {
		return numeroAutorizacion;
	}

	public void setNumeroAutorizacion(String numeroAutorizacion) {
		this.numeroAutorizacion = numeroAutorizacion;
	}

	public String getComercio() {
		return comercio;
	}

	public void setComercio(String comercio) {
		this.comercio = comercio;
	}


}
