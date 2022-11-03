package com.siman.credisiman.visa.service;

import org.apache.xmlbeans.XmlCursor;
import org.apache.xmlbeans.XmlObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.namespace.QName;

public class CambioFechaCorte {
    private static Logger log = LoggerFactory.getLogger(CambioFechaCorte.class);

    public static XmlObject obtenerCambioFechaCorte(String pais, String numeroTarjeta, String cuenta, String diaCorte,
                                                     String remoteJndiSunnel, String remoteJndiOrion, String siscardUrl, String siscardUser, String binCredisiman) {
        String namespace = "http://siman.com/CambioFechaCorte";
        String operationResponse = "ObtenerCambioFechaCorteResponse";
        //VALIDAR DATOS


        //OBTENER DATOS

        XmlObject result = XmlObject.Factory.newInstance();
        XmlCursor cursor = result.newCursor();
        QName responseQName = new QName(namespace, operationResponse);
        cursor.toNextToken();
        cursor.beginElement(responseQName);
        cursor.insertElementWithText(new QName(namespace, "statusCode"), "00");
        cursor.insertElementWithText(new QName(namespace, "status"), "SUCCESS");
        cursor.insertElementWithText(new QName(namespace, "statusMessage"), "Proceso exitoso");
        cursor.toParent();

        log.info("ObtenerCambioFechaCorte response = [" + result.toString() + "]");
        return result;
    }
}
