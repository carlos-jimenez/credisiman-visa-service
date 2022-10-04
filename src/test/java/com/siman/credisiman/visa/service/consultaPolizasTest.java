package com.siman.credisiman.visa.service;

import org.apache.xmlbeans.SimpleValue;
import org.apache.xmlbeans.XmlObject;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class consultaPolizasTest {
    static private String NS = "http://siman.com/ConsultaPolizas";
    static String[] listadoPolizas = {"0011223346", "0011223345", "0011223344"};

    @Test
    public void obtenerConsultaSubProductosOk() {
        XmlObject result = ConsultaPolizas.obtenerConsultaPolizas("SLV", "0398765432", "jndi/ArcaSV");
        int i = 0;

        //Status
        assertEquals("00", ((SimpleValue) result.selectPath( "declare namespace ns='" + NS + "' " + ".//ns:statusCode")[0]).getStringValue());
        assertEquals("SUCCESS", ((SimpleValue) result.selectPath( "declare namespace ns='" + NS + "' " + ".//ns:status")[0]).getStringValue());

        //Data
        XmlObject[] polizas = result.selectPath( "declare namespace ns='" + NS + "' " + ".//ns:polizas");
        while (i < polizas.length) {
            String nombrePoliza = ((SimpleValue) polizas[i].selectPath( "declare namespace ns='" + NS + "' " + ".//ns:nombrePoliza")[0]).getStringValue();
            assertEquals(listadoPolizas[i], nombrePoliza);
            i++;
        }
    }
}
