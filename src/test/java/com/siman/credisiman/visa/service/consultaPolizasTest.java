package com.siman.credisiman.visa.service;

import org.apache.xmlbeans.SimpleValue;
import org.apache.xmlbeans.XmlObject;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class consultaPolizasTest {
    static private final String NS = "http://siman.com/ConsultaPolizas";

    @Test
    public void obtenerConsultaPolizasPrivadaOk() {
        XmlObject result = ConsultaPolizas.obtenerConsultaPolizas("SV", "4573840094950811",
                "jdbc/SUNTST", "jdbc/ORIONREPOSV", "http://soauat.siman.com:7003/v1/orion",
                "usuario", "600831, 600831, 600831", "P");
//        int i = 0;

        //Status
        assertEquals("00", ((SimpleValue) result.selectPath( "declare namespace ns='" + NS + "' " + ".//ns:statusCode")[0]).getStringValue());
        assertEquals("SUCCESS", ((SimpleValue) result.selectPath( "declare namespace ns='" + NS + "' " + ".//ns:status")[0]).getStringValue());

        //Data
//        XmlObject[] polizas = result.selectPath( "declare namespace ns='" + NS + "' " + ".//ns:polizas");
//        while (i < polizas.length) {
//            String nombrePoliza = ((SimpleValue) polizas[i].selectPath( "declare namespace ns='" + NS + "' " + ".//ns:nombrePoliza")[0]).getStringValue();
//            assertEquals(listadoPolizas[i], nombrePoliza);
//            i++;
//        }
    }
}
