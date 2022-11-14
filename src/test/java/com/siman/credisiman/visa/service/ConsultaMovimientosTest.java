package com.siman.credisiman.visa.service;

import org.apache.xmlbeans.SimpleValue;
import org.apache.xmlbeans.XmlObject;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ConsultaMovimientosTest {
    static private final String NS = "http://siman.com/ConsultaMovimientos";

    @Test
    public void obtenerConsultaMovimientosPrivadaOk() {
        XmlObject result = ConsultaMovimientos.obtenerConsultaMovimientos("SLV",
                "6008314502274917", "20180101", "20221231",
                "jdbc/SUNTST", "jdbc/ORIONREPOSV", "http://soauat.siman.com:7003/v1/orion",
                "usuario", "600831, 600831, 600831", "P");
//        int i = 0;
        //Status
        assertEquals("00", ((SimpleValue) result.selectPath("declare namespace ns='" + NS + "' " + ".//ns:statusCode")[0]).getStringValue());
        assertEquals("SUCCESS", ((SimpleValue) result.selectPath("declare namespace ns='" + NS + "' " + ".//ns:status")[0]).getStringValue());

        //Data
//        XmlObject[] movimientos = result.selectPath("declare namespace ns='" + NS + "' " + ".//ns:movimientos");
//        while (i < movimientos.length) {
//            String numeroAutorizacion = ((SimpleValue) movimientos[i].selectPath("declare namespace ns='" + NS + "' " + ".//ns:numeroAutorizacion")[0]).getStringValue();
//            assertEquals(ListadoNumeroAutorizacion[i], numeroAutorizacion);
//            i++;
//        }
    }

    @Test
    public void obtenerConsultaMovimientosVisaOk() {
        XmlObject result = ConsultaMovimientos.obtenerConsultaMovimientos("SV",
                "4573840028591061", "20190916", "20211030",
                "jdbc/SUNTST", "jdbc/ORIONREPOSV", "http://soauat.siman.com:7003/v1/orion",
                "usuario", "600831, 600831, 600831", "V");
//        int i = 0;
        //Status
        assertEquals("00", ((SimpleValue) result.selectPath("declare namespace ns='" + NS + "' " + ".//ns:statusCode")[0]).getStringValue());
        assertEquals("SUCCESS", ((SimpleValue) result.selectPath("declare namespace ns='" + NS + "' " + ".//ns:status")[0]).getStringValue());

        //Data
//        XmlObject[] movimientos = result.selectPath("declare namespace ns='" + NS + "' " + ".//ns:movimientos");
//        while (i < movimientos.length) {
//            String numeroAutorizacion = ((SimpleValue) movimientos[i].selectPath("declare namespace ns='" + NS + "' " + ".//ns:numeroAutorizacion")[0]).getStringValue();
//            assertEquals(ListadoNumeroAutorizacion[i], numeroAutorizacion);
//            i++;
//        }
    }
}
