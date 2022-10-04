package com.siman.credisiman.visa.service;

import org.apache.xmlbeans.XmlCursor;
import org.apache.xmlbeans.XmlObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.namespace.QName;

public class SolicitudReposicionTarjeta {
    private static Logger log = LoggerFactory.getLogger(SolicitudReposicionTarjeta.class);

    public static XmlObject SolicitudReposicionTarjeta(String pais, String numeroTarjeta, String nombreEmbozar,String razonReposicion, String remoteJndi) {
        String namespace = "http://siman.com/SolicitudReposicionTarjeta";
        String operationResponse = "ObtenerSolicitudReposicionTarjetaResponse";
        //OBTENER DATOS

        XmlObject result = XmlObject.Factory.newInstance();
        XmlCursor cursor = result.newCursor();
        QName responseQName = new QName(namespace, operationResponse);
        cursor.toNextToken();
        cursor.beginElement(responseQName);
        cursor.insertElementWithText(new QName(namespace, "statusCode"), "00");
        cursor.insertElementWithText(new QName(namespace, "status"), "SUCCESS");
        cursor.insertElementWithText(new QName(namespace, "statusMessage"), "Proceso exitoso");
        cursor.insertElementWithText(new QName(namespace, "nuevoNumeroTarjeta"), "4000123456780001");
        cursor.toParent();

        log.info("obtenerSolicitudReposicionTarjeta response = [" + result.toString() + "]");
        return result;
    }
}