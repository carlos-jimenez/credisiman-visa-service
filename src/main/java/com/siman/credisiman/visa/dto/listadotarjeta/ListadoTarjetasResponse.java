package com.siman.credisiman.visa.dto.listadotarjeta;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.siman.credisiman.visa.dto.TarjetasResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ListadoTarjetasResponse {
    @JsonProperty("statusCode") @JsonAlias("statusCode")
    public String statusCode;

    @JsonProperty("status") @JsonAlias("status")
    public String status;

    @JsonProperty("statusMessage") @JsonAlias("statusMessage")
    public String statusMessage;
    
    @JsonProperty("tarjetas") @JsonAlias("tarjetas")
    public List<TarjetasResponse> tarjetas;

}
