package com.siman.credisiman.visa.service;

import com.credisiman.visa.soa.utils.Utils;
import com.siman.credisiman.visa.utils.Message;
import org.apache.xmlbeans.XmlCursor;
import org.apache.xmlbeans.XmlObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.namespace.QName;

public class ReposicionTarjeta {
    private static Logger log = LoggerFactory.getLogger(ReposicionTarjeta.class);

    public static XmlObject obtenerReposicionTarjeta(String pais, String numeroTarjeta, String nombreEmbozador, String razonReposicion,
                                                     String localidadTramite, String localidadRetiro, String remoteJndiSunnel,
                                                     String remoteJndiOrion, String siscardUrl, String siscardUser, String binCredisiman) {
        String namespace = "http://siman.com/ReposicionTarjeta";
        String operationResponse = "ObtenerReposicionTarjetaResponse";
        //VALIDAR DATOS

        //validar campos requeridos
        Utils utils = new Utils();
        Message message = new Message();

        if (utils.validateNotNull(pais)) {
            log.info("pais required");
            return message.genericMessage("ERROR", "025", "El campo pais es obligatorio", namespace, operationResponse);
        }
        if (utils.validateNotNull(numeroTarjeta)) {
            log.info("numero tarjeta required");
            return message.genericMessage("ERROR", "025", "El campo número tarjeta es obligatorio", namespace, operationResponse);
        }
        if (utils.validateNotNull(nombreEmbozador)) {
            log.info("nombre embozador required");
            return message.genericMessage("ERROR", "025", "El campo nombre embozador es obligatorio", namespace, operationResponse);
        }
        if (utils.validateNotNull(razonReposicion)) {
            log.info("razon reposicion required");
            return message.genericMessage("ERROR", "025", "El campo razon reposicion es obligatorio", namespace, operationResponse);
        }
        if (utils.validateNotNull(localidadRetiro)) {
            log.info("localidad retiro required");
            return message.genericMessage("ERROR", "025", "El campo localidad retiro es obligatorio", namespace, operationResponse);
        }
        if (utils.validateNotNull(localidadTramite)) {
            log.info("localidad tramite required");
            return message.genericMessage("ERROR", "025", "El campo localidad tramite es obligatorio", namespace, operationResponse);
        }


        //validar longitudes
        if (!utils.validateLongitude(pais,3)) {
            log.info("longitud pais");
            return message.genericMessage("ERROR", "025", "La longitud del campo pais debe ser menor o igual a 3", namespace, operationResponse);
        }
        if (!utils.validateLongitude(numeroTarjeta  ,16)) {
            log.info("longitud numero de tarjeta");
            return message.genericMessage("ERROR", "025", "La longitud del campo número tarjeta debe ser menor o igual a 16", namespace, operationResponse);
        }
        if (!utils.validateLongitude(nombreEmbozador  ,29)) {
            log.info("longitud nombre embozar");
            return message.genericMessage("ERROR", "025", "La longitud del campo nombre embozar debe ser menor o igual a 29", namespace, operationResponse);
        }
        if (!utils.validateLongitude(razonReposicion  ,1)) {
            log.info("longitud razon reposicion");
            return message.genericMessage("ERROR", "025", "La longitud del campo razón reposición debe ser  1", namespace, operationResponse);
        }
        if (!utils.validateLongitude(localidadTramite  ,2)) {
            log.info("longitud localidad tramite");
            return message.genericMessage("ERROR", "025", "La longitud del campo localidad tramite debe ser menor o igual a 2", namespace, operationResponse);
        }
        if (!utils.validateLongitude(localidadRetiro  ,2)) {
            log.info("longitud localidad retiro");
            return message.genericMessage("ERROR", "025", "La longitud del campo localidad retiro debe ser  menor o igual a 2", namespace, operationResponse);
        }

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

        log.info("ObtenerReposicionTarjeta response = [" + result.toString() + "]");
        return result;
    }
}
