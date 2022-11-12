package com.siman.credisiman.visa.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor

public class ConsultaPolizasRequest {
    public String pais;
    public String numeroTarjeta;

}
