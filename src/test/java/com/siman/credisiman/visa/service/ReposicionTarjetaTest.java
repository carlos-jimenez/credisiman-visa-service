package com.siman.credisiman.visa.service;

import org.apache.xmlbeans.SimpleValue;
import org.apache.xmlbeans.XmlObject;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ReposicionTarjetaTest {
    static private String NS = "http://siman.com/ReposicionTarjeta";

    @Test
    public void registroUsuario() {
        XmlObject result = ReposicionTarjeta.obtenerReposicionTarjeta("SV","4000123456780000","JUAN PEREZ", "1","1","1","jndi/SimacSV", "jdbc/ORIONREPOSV", "http://soauat.siman.com:7003/v1/orion", "usuario", "600831, 600831, 600831");
        assertEquals("00", ((SimpleValue) result.selectPath( "declare namespace ns='" + NS + "' " + ".//ns:statusCode")[0]).getStringValue());
        assertEquals("SUCCESS", ((SimpleValue) result.selectPath( "declare namespace ns='" + NS + "' " + ".//ns:status")[0]).getStringValue());
    }
}
