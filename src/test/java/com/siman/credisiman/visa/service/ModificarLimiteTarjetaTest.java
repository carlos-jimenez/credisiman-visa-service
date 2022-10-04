package com.siman.credisiman.visa.service;

import org.apache.xmlbeans.SimpleValue;
import org.apache.xmlbeans.XmlObject;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ModificarLimiteTarjetaTest {
    static private final String NS = "http://siman.com/ModificarLimiteTarjeta";

    @Test
    public void obtenerConsultaSubProductosOk() {
        XmlObject result = ModificarLimiteTarjeta.modificarLimiteTarjeta("SLV", "0398765432","000000500000", "jndi/ArcaSV");

        //Status
        assertEquals("00", ((SimpleValue) result.selectPath( "declare namespace ns='" + NS + "' " + ".//ns:statusCode")[0]).getStringValue());
        assertEquals("SUCCESS", ((SimpleValue) result.selectPath( "declare namespace ns='" + NS + "' " + ".//ns:status")[0]).getStringValue());

    }
}
