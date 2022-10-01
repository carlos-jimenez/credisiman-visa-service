package com.siman.credisiman.visa.service;

import static org.junit.Assert.assertEquals;

import org.apache.xmlbeans.SimpleValue;
import org.apache.xmlbeans.XmlObject;
import org.junit.Test;

public class RegistroUsuarioTest {
	static private String NS = "http://siman.com/RegistroUsuario";

    @Test
    public void registroUsuario() {
        XmlObject result = RegistroUsuario.registroUsuario(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
        assertEquals("00", ((SimpleValue) result.selectPath( "declare namespace ns='" + NS + "' " + ".//ns:statusCode")[0]).getStringValue());
        assertEquals("SUCCESS", ((SimpleValue) result.selectPath( "declare namespace ns='" + NS + "' " + ".//ns:status")[0]).getStringValue());
    }

    @Test
    public void actualizarUsuario() {
    	XmlObject result = RegistroUsuario.actualizarUsuario(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
        assertEquals("00", ((SimpleValue) result.selectPath( "declare namespace ns='" + NS + "' " + ".//ns:statusCode")[0]).getStringValue());
        assertEquals("SUCCESS", ((SimpleValue) result.selectPath( "declare namespace ns='" + NS + "' " + ".//ns:status")[0]).getStringValue());
    }
}
