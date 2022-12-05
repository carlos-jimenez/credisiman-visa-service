package com.siman.credisiman.visa.service.dto.bloqueodesbloqueotarjeta;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BloqueoDesbloqueoTarjetaRequest {
    public String pais;
    public String numeroTarjeta;
    public String estadoDeseado;
    public String motivo;

}
