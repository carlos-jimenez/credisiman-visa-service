package com.siman.credisiman.visa.service;

import javax.xml.namespace.QName;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.siman.credisiman.visa.dto.ConsultaDatosClienteResponse;
import org.apache.xmlbeans.XmlCursor;
import org.apache.xmlbeans.XmlObject;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConsultaDatosCliente {
    private static final Logger log = LoggerFactory.getLogger(ConsultaDatosCliente.class);

    public static XmlObject obtenerDatosCliente(String pais, String identificacion, String remoteJndiSunnel,
                                                String remoteJndiOrion, String siscardUrl, String siscardUser, String binCredisiman) {
        String namespace = "http://siman.com/ConsultaDatosCliente";
        String operationResponse = "ObtenerDatosClienteResponse";

        //OBTENER DATOS
        try {
            HttpResponse<String> jsonResponse
                    = Unirest.post(siscardUrl)
                    .header("Content-Type", "application/json")
                    .body("{\"country\":\""+pais+"\", \"processIdentifier\":\"ConsultaDatosEnte\",\"tipoMensaje\":\"4300\", \"identificacion\":\"" + identificacion + "\", \"usuarioSiscard\":\"" + siscardUser + "\"}")
                    .asString();
            JSONObject response = new JSONObject(jsonResponse.getBody().replaceAll("\u200B",  ""));
            ConsultaDatosClienteResponse consultaDatosClienteResponse = new ObjectMapper()
                    .readValue(response.toString(), ConsultaDatosClienteResponse.class);
            log.info(new ObjectMapper().writeValueAsString(consultaDatosClienteResponse));
        } catch (Exception e) {
            e.printStackTrace();
            log.info(e.getMessage());
        }


        XmlObject result = XmlObject.Factory.newInstance();
        XmlCursor cursor = result.newCursor();
        QName responseQName = new QName(namespace, operationResponse);
        cursor.toNextToken();
        cursor.beginElement(responseQName);
        cursor.insertElementWithText(new QName(namespace, "statusCode"), "00");
        cursor.insertElementWithText(new QName(namespace, "status"), "SUCCESS");
        cursor.insertElementWithText(new QName(namespace, "statusMessage"), "Proceso exitoso");
        cursor.insertElementWithText(new QName(namespace, "primerNombre"), "Juan");
        cursor.insertElementWithText(new QName(namespace, "segundoNombre"), "José");
        cursor.insertElementWithText(new QName(namespace, "primerApellido"), "Pérez");
        cursor.insertElementWithText(new QName(namespace, "segundoApellido"), "Cañas");
        cursor.insertElementWithText(new QName(namespace, "apellidoCasada"), "Gómez");
        cursor.insertElementWithText(new QName(namespace, "fechaNacimiento"), "19810423"); //no esta
        cursor.insertElementWithText(new QName(namespace, "tipoIdentificacion"), "012345678");
        cursor.insertElementWithText(new QName(namespace, "identificacion"), "012345678");
        cursor.insertElementWithText(new QName(namespace, "correo"), "juan.perez@correo.com");
        cursor.insertElementWithText(new QName(namespace, "celular"), "77770000");
        cursor.insertElementWithText(new QName(namespace, "lugarTrabajo"), "Almacenes Simán S.A. de C.V."); //no esta
        cursor.insertElementWithText(new QName(namespace, "direccion"), "Direccion particular");
        cursor.insertElementWithText(new QName(namespace, "direccionPatrono"), "Direccion patrono");
        cursor.toParent();

        log.info("obtenerDatosCliente response = [" + result.toString() + "]");
        return result;
    }
}
