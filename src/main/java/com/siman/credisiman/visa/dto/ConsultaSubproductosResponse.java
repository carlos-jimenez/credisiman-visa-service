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
public class ConsultaSubproductosResponse {
    @JsonProperty("code") @JsonAlias("code")
    private String code;

    @JsonProperty("message") @JsonAlias("message")
    private String message;

    @JsonProperty("status") @JsonAlias("status")
    private String status;

    @JsonProperty("statusCode") @JsonAlias("statusCode")
    private String statusCode;

    @JsonProperty("statusMessage") @JsonAlias("statusMessage")
    private String statusMessage;

    @JsonProperty("tipoMensaje") @JsonAlias("tipoMensaje")
    private String tipoMensaje;

    @JsonProperty("codigoEmisor") @JsonAlias("codigoEmisor")
    private String codigoEmisor;

    @JsonProperty("numeroCuenta") @JsonAlias("numeroCuenta")
    private String numeroCuenta;

    @JsonProperty("numeroTarjeta") @JsonAlias("numeroTarjeta")
    private String numeroTarjeta;

    @JsonProperty("usuarioSiscard") @JsonAlias("usuarioSiscard")
    private String usuarioSiscard;

    @JsonProperty("saldoDeudorExtrafinanciamientoLocal") @JsonAlias("saldoDeudorExtrafinanciamientoLocal")
    private String saldoDeudorExtrafinanciamientoLocal;

    @JsonProperty("saldoDeudorExtrafinanciamientoInter") @JsonAlias("saldoDeudorExtrafinanciamientoInter")
    private String saldoDeudorExtrafinanciamientoInter;

    @JsonProperty("saldoDeudorIntrafinanciamientoLocal") @JsonAlias("saldoDeudorIntrafinanciamientoLocal")
    private String saldoDeudorIntrafinanciamientoLocal;

    @JsonProperty("saldoDeudorIntrafinanciamientoInter") @JsonAlias("saldoDeudorIntrafinanciamientoInter")
    private String saldoDeudorIntrafinanciamientoInter;

    @JsonProperty("saldoDisponible") @JsonAlias("saldoDisponible")
    private String saldoDisponible;
}
