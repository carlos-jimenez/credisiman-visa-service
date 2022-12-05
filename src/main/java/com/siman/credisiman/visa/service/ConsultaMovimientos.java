package com.siman.credisiman.visa.service;

import org.apache.xmlbeans.XmlCursor;
import org.apache.xmlbeans.XmlObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.siman.credisiman.visa.utils.Message;
import com.siman.credisiman.visa.utils.Utils;

import javax.xml.namespace.QName;

public class ConsultaMovimientos {
    private static final Logger log = LoggerFactory.getLogger(ConsultaMovimientos.class);
    private static final String namespace = "http://siman.com/ConsultaMovimientos";
    private static final String operationResponse = "ObtenerConsultaMovimientosResponse";
	public static XmlObject obtenerConsultaMovimientos(String pais, String numeroTarjeta, String fechaInicial,
			String fechaFinal, String remoteJndiSunnel, String remoteJndiOrion, String siscardUrl, String siscardUser,
			String binCredisiman, String tipoTarjeta) {

        //validar campos requeridos
        Utils utils = new Utils();
        Message message = new Message();

        if (utils.validateNotNull(pais) || utils.validateNotEmpty(pais)) {
            log.info("pais required");
            return message.genericMessage("ERROR", "025", "El campo pais es obligatorio", namespace, operationResponse);
        }
        if (utils.validateNotNull(numeroTarjeta) || utils.validateNotEmpty(numeroTarjeta)) {
            log.info("numero tarjeta required");
            return message.genericMessage("ERROR", "025", "El campo número tarjeta es obligatorio", namespace, operationResponse);
        }

        //validar longitudes
        if (!utils.validateLongitude(pais, 3)) {
            log.info("pais, size overload");
            return message.genericMessage("ERROR", "025", "La longitud del campo pais debe ser menor o igual a 3", namespace, operationResponse);
        }
        if (!utils.validateLongitude(numeroTarjeta, 16)) {
            log.info("identificacion, size overload");
            return message.genericMessage("ERROR", "025",
                    "La longitud del campo número tarjeta debe ser menor o igual a 16", namespace, operationResponse);
        }

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
