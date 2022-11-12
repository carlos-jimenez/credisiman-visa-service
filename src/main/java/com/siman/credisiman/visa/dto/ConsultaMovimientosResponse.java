package com.siman.credisiman.visa.dto;

import java.util.List;

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
public class ConsultaMovimientosResponse {

	@JsonProperty("statusCode") @JsonAlias("statusCode")
    public String statusCode;

    @JsonProperty("status") @JsonAlias("status")
    public String status;

    @JsonProperty("statusMessage") @JsonAlias("statusMessage")
    public String statusMessage;
    
    @JsonProperty("movimientos") @JsonAlias("movimientos")
    public List<MovimientosResponse> movimientos;

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

	public List<MovimientosResponse> getMovimientos() {
		return movimientos;
	}

	public void setMovimientos(List<MovimientosResponse> movimientos) {
		this.movimientos = movimientos;
	}
}
