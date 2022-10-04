package com.siman.credisiman.visa.service;

import org.apache.xmlbeans.SimpleValue;
import org.apache.xmlbeans.XmlCursor;
import org.apache.xmlbeans.XmlObject;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.namespace.QName;

import static org.junit.Assert.assertEquals;

public class ConsultaSaldoMonederoTest {
    static private String NS = "http://siman.com/ConsultaSaldoMonedero";

    @Test
    public void obtenerDatosClienteOk() {
        XmlObject result = ConsultaSaldoMonedero.ConsultaSaldoMonedero("SLV", "4000123456780000", "12345","20220718","jndi/ArcaSV");
        int i = 0;

        //Status
        assertEquals("00", ((SimpleValue) result.selectPath("declare namespace ns='" + NS + "' " + ".//ns:statusCode")[0]).getStringValue());
        assertEquals("SUCCESS", ((SimpleValue) result.selectPath("declare namespace ns='" + NS + "' " + ".//ns:status")[0]).getStringValue());
        assertEquals("000000050000", ((SimpleValue) result.selectPath("declare namespace ns='" + NS + "' " + ".//ns:saldoInicial")[0]).getStringValue());
        assertEquals("000000002000", ((SimpleValue) result.selectPath("declare namespace ns='" + NS + "' " + ".//ns:puntosGanados")[0]).getStringValue());
        assertEquals("000000010000", ((SimpleValue) result.selectPath("declare namespace ns='" + NS + "' " + ".//ns:puntosCanjeados")[0]).getStringValue());
        assertEquals("000000051000", ((SimpleValue) result.selectPath("declare namespace ns='" + NS + "' " + ".//ns:saldoFinal")[0]).getStringValue());

    }
}
