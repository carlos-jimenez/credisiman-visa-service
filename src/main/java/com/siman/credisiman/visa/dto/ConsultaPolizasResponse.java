package com.siman.credisiman.visa.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ConsultaPolizasResponse {
	@JsonProperty("statusCode") @JsonAlias("statusCode")
    public String statusCode;

    @JsonProperty("status") @JsonAlias("status")
    public String status;

    @JsonProperty("statusMessage") @JsonAlias("statusMessage")
    public String statusMessage;
    
    @JsonProperty("listaDePolizas") @JsonAlias("listaDePolizas")
    public List<ListaDePolizasResponse> listaDePolizas;

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatusMessage() {
		return statusMessage;
	}

	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
	}

	public List<ListaDePolizasResponse> getListaDePolizas() {
		return listaDePolizas;
	}

	public void setListaDePolizas(List<ListaDePolizasResponse> listaDePolizas) {
		this.listaDePolizas = listaDePolizas;
	}
}
