package com.siman.credisiman.visa.service;

import org.apache.xmlbeans.SimpleValue;
import org.apache.xmlbeans.XmlObject;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ListadoEstadosCuentasTest {
    static private final String NS = "http://siman.com/ListadoEstadosCuenta";
//    static String[] listadoFechaCorte = {  "20220713","20220712","20220711"};

    @Test
    public void obtenerListadoEstadosCuentasOk() {
        XmlObject result = ListadoEstadosCuentas.obtenerListadoEstadosCuenta("SV", "6008318000609201", "jdbc/SUNTST", "jdbc/ORIONREPOSV", "http://soauat.siman.com:7003/v1/orion", "usuario", "600831, 600831, 600831","V");

        //Status
        assertEquals("00", ((SimpleValue) result.selectPath( "declare namespace ns='" +NS + "' " + ".//ns:statusCode")[0]).getStringValue());
        assertEquals("SUCCESS", ((SimpleValue) result.selectPath( "declare namespace ns='" + NS + "' " + ".//ns:status")[0]).getStringValue());
    }

}