package com.siman.credisiman.visa.service;

import org.apache.xmlbeans.XmlCursor;
import org.apache.xmlbeans.XmlObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.namespace.QName;

public class ConsultaMovimientos {
    private static Logger log = LoggerFactory.getLogger(ConsultaMovimientos.class);

    public static XmlObject obtenerConsultaMovimientos(String pais, String numeroTarjeta,
                                                       String fechaInicial, String fechaFinal,
                                                       String remoteJndi) {
        String namespace = "http://siman.com/ConsultaMovimientos";
        String operationResponse = "ObtenerConsultaMovimientosResponse";
        String[] numeroAutorizacion = {"123456701", "123456702", "123456703"};

        //OBTENER DATOS
        XmlObject result = XmlObject.Factory.newInstance();
        XmlCursor cursor = result.newCursor();
        QName responseQName = new QName(namespace, operationResponse);
        cursor.toNextToken();
        cursor.beginElement(responseQName);
        cursor.insertElementWithText(new QName(namespace, "statusCode"), "00");
        cursor.insertElementWithText(new QName(namespace, "status"), "SUCCESS");
        cursor.insertElementWithText(new QName(namespace, "statusMessage"), "Proceso exitoso");

        for (String movimientos : numeroAutorizacion) {
            cursor.beginElement(new QName(namespace, "movimientos"));
            cursor.insertElementWithText(new QName(namespace, "tipoMovimiento"), "C");
            cursor.insertElementWithText(new QName(namespace, "fechaMovimiento"), "20220718");
            cursor.insertElementWithText(new QName(namespace, "fechaAplicacion"), "20220718");
            cursor.insertElementWithText(new QName(namespace, "moneda"), "DO");
            cursor.insertElementWithText(new QName(namespace, "monto"), "000000310000");
            cursor.insertElementWithText(new QName(namespace, "numeroAutorizacion"), movimientos);
            cursor.insertElementWithText(new QName(namespace, "comercio"), "SIMAN GALERIAS");
            cursor.toParent();
        }
        cursor.toParent();

        log.info("obtenerConsultaMovimientos response = [" + result.toString() + "]");
        return result;
    }
}
