package com.siman.credisiman.visa.service;

import com.siman.credisiman.visa.dto.email.EmailTo;
import org.apache.xmlbeans.SimpleValue;
import org.apache.xmlbeans.XmlObject;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class EnvioCorreoElectronicoTest {
    static private final String NS = "http://siman.com/EnvioCorreoElectronico";

    @Test
    public void obtenerEnvioCorreoOk() {
        List<EmailTo> emails = new ArrayList<>();
        emails.add(new EmailTo("carlos","carlos_penate@siman.com"));

        XmlObject result = EnvioCorreoElectronico.send("no-reply@simaninternet.net", "CREDISIMAN EL SALVADOR","prueba de corrreo",false,"<h1>Automatic JUnit Test </h1><p style='color:green;'>process complete</p>",
                 emails);

        assertEquals("00", ((SimpleValue) result.selectPath( "declare namespace ns='" +NS + "' " + ".//ns:statusCode")[0]).getStringValue());
        assertEquals("SUCCESS", ((SimpleValue) result.selectPath( "declare namespace ns='" + NS + "' " + ".//ns:status")[0]).getStringValue());

    }
}
