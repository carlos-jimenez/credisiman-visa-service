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
public class ListaDePolizasResponse {
	
	@JsonProperty("tipoPoliza") @JsonAlias("tipoPoliza")
    public String tipoPoliza;
	
	@JsonProperty("nombrePoliza") @JsonAlias("nombrePoliza")
	    public String nombrePoliza;
	
	@JsonProperty("estadoPoliza") @JsonAlias("estadoPoliza")
	    public String estadoPoliza;

	public String getTipoPoliza() {
		return tipoPoliza;
	}

	public void setTipoPoliza(String tipoPoliza) {
		this.tipoPoliza = tipoPoliza;
	}

	public String getNombrePoliza() {
		return nombrePoliza;
	}

	public void setNombrePoliza(String nombrePoliza) {
		this.nombrePoliza = nombrePoliza;
	}

	public String getEstadoPoliza() {
		return estadoPoliza;
	}

	public void setEstadoPoliza(String estadoPoliza) {
		this.estadoPoliza = estadoPoliza;
	}


}
