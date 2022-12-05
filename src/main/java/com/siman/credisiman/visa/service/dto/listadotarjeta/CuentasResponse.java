package com.siman.credisiman.visa.service.dto.listadotarjeta;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CuentasResponse {

    @JsonProperty("emisor")
    @JsonAlias("emisor")
    private String emisor;

    @JsonProperty("cuenta")
    @JsonAlias("cuenta")
    private String cuenta;

    @JsonProperty("nombreTH")
    @JsonAlias("nombreTH")
    private String nombreTH;

    @JsonProperty("cedula")
    @JsonAlias("cedula")
    private String cedula;

    @JsonProperty("estadoCuenta")
    @JsonAlias("estadoCuenta")
    private String estadoCuenta;

    @JsonProperty("diaCorte")
    @JsonAlias("diaCorte")
    private String diaCorte;

    @JsonProperty("importeVencidoInter")
    @JsonAlias("importeVencidoInter")
    private String importeVencidoInter;

    @JsonProperty("importeVencidoLocal")
    @JsonAlias("importeVencidoLocal")
    private String importeVencidoLocal;

    @JsonProperty("fechaVencimientoCuenta")
    @JsonAlias("fechaVencimientoCuenta")
    private String fechaVencimientoCuenta;

    @JsonProperty("fechaVencimientoPago")
    @JsonAlias("fechaVencimientoPago")
    private String fechaVencimientoPago;

    @JsonProperty("montoImporteInter")
    @JsonAlias("montoImporteInter")
    private String montoImporteInter;

    @JsonProperty("montoImporteLocal")
    @JsonAlias("montoImporteLocal")
    private String montoImporteLocal;

    @JsonProperty("pagoContInt")
    @JsonAlias("pagoContInt")
    private String pagoContInt;

    @JsonProperty("pagoContadoLocal")
    @JsonAlias("pagoContadoLocal")
    private String pagoContadoLocal;

    @JsonProperty("pagoMinimoInt")
    @JsonAlias("pagoMinimoInt")
    private String pagoMinimoInt;

    @JsonProperty("pagoMinimoLocal")
    @JsonAlias("pagoMinimoLocal")
    private String pagoMinimoLocal;

    @JsonProperty("saldoInter")
    @JsonAlias("saldoInter")
    private String saldoInter;

    @JsonProperty("saldoLocal")
    @JsonAlias("saldoLocal")
    private String saldoLocal;

    @JsonProperty("saldoPremiacion")
    @JsonAlias("saldoPremiacion")
    private String saldoPremiacion;

    @JsonProperty("marca")
    @JsonAlias("marca")
    private String marca;

    @JsonProperty("salTotCorteLocal")
    @JsonAlias("salTotCorteLocal")
    private String salTotCorteLocal;

    @JsonProperty("salTotCorteInter")
    @JsonAlias("salTotCorteInter")
    private String salTotCorteInter;

    @JsonProperty("tasaInteresMoratoriaLocal")
    @JsonAlias("tasaInteresMoratoriaLocal")
    private String tasaInteresMoratoriaLocal;

    @JsonProperty("tasaInteresMoratoriaInter")
    @JsonAlias("tasaInteresMoratoriaInter")
    private String tasaInteresMoratoriaInter;

    @JsonProperty("tasaInteresMensualLocal")
    @JsonAlias("tasaInteresMensualLocal")
    private String tasaInteresMensualLocal;

    @JsonProperty("tasaInteresMensualInter")
    @JsonAlias("tasaInteresMensualInter")
    private String tasaInteresMensualInter;

    @JsonProperty("limiteCreditoLocal")
    @JsonAlias("limiteCreditoLocal")
    private String limiteCreditoLocal;

    @JsonProperty("limiteCreditoInter")
    @JsonAlias("limiteCreditoInter")
    private String limiteCreditoInter;

    @JsonProperty("tarjetas")
    @JsonAlias("tarjetas")
    private List<TarjetasResponse> tarjetas;

}
