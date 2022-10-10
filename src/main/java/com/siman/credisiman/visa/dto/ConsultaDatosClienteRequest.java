package com.siman.credisiman.visa.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ConsultaDatosClienteRequest {
        public String country;
        public String processIdentifier;
        public String tipoMensaje;
        public String identificacion;
        public String usuarioSiscard;
}