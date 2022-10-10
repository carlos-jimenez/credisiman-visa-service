package com.siman.credisiman.visa.service;

import org.apache.xmlbeans.XmlCursor;
import org.apache.xmlbeans.XmlObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.namespace.QName;

public class ConsultaPolizas {
    private static Logger log = LoggerFactory.getLogger(ConsultaPolizas.class);

	public static XmlObject obtenerConsultaPolizas(String pais, String numeroTarjeta, String remoteJndiSunnel,
			String remoteJndiOrion, String siscardUrl, String siscardUser, String binCredisiman) {
        String namespace = "http://siman.com/ConsultaPolizas";
        String operationResponse = "ObtenerConsultaPolizasResponse";
        String[] nombrePolizas = {"0011223344", "0011223345", "0011223346"};
        //OBTENER DATOS

        XmlObject result = XmlObject.Factory.newInstance();
        XmlCursor cursor = result.newCursor();
        QName responseQName = new QName(namespace, operationResponse);
        cursor.toNextToken();
        cursor.beginElement(responseQName);
        cursor.insertElementWithText(new QName(namespace, "statusCode"), "00");
        cursor.insertElementWithText(new QName(namespace, "status"), "SUCCESS");
        cursor.insertElementWithText(new QName(namespace, "statusMessage"), "Proceso exitoso");
        for (String nombrePoliza : nombrePolizas) {
            cursor.beginElement(new QName(namespace, "polizas"));
            cursor.insertElementWithText(new QName(namespace, "tipoPoliza"), "FRA");
            cursor.insertElementWithText(new QName(namespace, "nombrePoliza"), nombrePoliza);
            cursor.insertElementWithText(new QName(namespace, "estadoPoliza"), "A");
            cursor.toParent();
        }
        cursor.toParent();

        log.info("obtenerConsultaPolizas response = [" + result.toString() + "]");
        return result;
    }
}
