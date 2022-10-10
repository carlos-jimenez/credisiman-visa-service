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
        ConsultaDatosClienteResponse response1 = new ConsultaDatosClienteResponse();
        //OBTENER DATOS
        try {
            JSONObject jsonSend = new JSONObject(); //json a enviar
            jsonSend.put("country", pais)
                    .put("processIdentifier", "ConsultaDatosEnte")
                    .put("tipoMensaje", 4300)
                    .put("identificacion", identificacion)
                    .put("usuarioSiscard", siscardUser);

            HttpResponse<String> jsonResponse //realizar petición demiante unirest
                    = Unirest.post(siscardUrl)
                    .header("Content-Type", "application/json")
                    .body(jsonSend.toString())
                    .asString();

            //capturar respuesta
            JSONObject response = new JSONObject(jsonResponse.getBody().replaceAll("\u200B", ""));
            response1 = new ObjectMapper().readValue(response.toString(), ConsultaDatosClienteResponse.class);

            log.info(new ObjectMapper().writeValueAsString(response1));
        } catch (Exception e) {
            e.printStackTrace();
            log.info(e.getMessage());
        }


        XmlObject result = XmlObject.Factory.newInstance();
        XmlCursor cursor = result.newCursor();
        QName responseQName = new QName(namespace, operationResponse);
        cursor.toNextToken();
        cursor.beginElement(responseQName);
        cursor.insertElementWithText(new QName(namespace, "statusCode"), response1.getStatusCode());
        cursor.insertElementWithText(new QName(namespace, "status"), response1.getStatus());
        cursor.insertElementWithText(new QName(namespace, "statusMessage"), response1.getStatusMessage());
        cursor.insertElementWithText(new QName(namespace, "primerNombre"), response1.getNombre());
        cursor.insertElementWithText(new QName(namespace, "segundoNombre"), response1.getSegundoNombre());
        cursor.insertElementWithText(new QName(namespace, "primerApellido"), response1.getPrimerApellido());
        cursor.insertElementWithText(new QName(namespace, "segundoApellido"), response1.getSegundoApellido());
        cursor.insertElementWithText(new QName(namespace, "apellidoCasada"), response1.getApellidoCasada());
        cursor.insertElementWithText(new QName(namespace, "fechaNacimiento"), response1.getNacimiento()); //no esta
        cursor.insertElementWithText(new QName(namespace, "tipoIdentificacion"), response1.getTipoIdentificacion());
        cursor.insertElementWithText(new QName(namespace, "identificacion"), response1.getIdentificacion());
        cursor.insertElementWithText(new QName(namespace, "correo"), response1.getCorreoElectronico());
        cursor.insertElementWithText(new QName(namespace, "celular"), response1.getCelular());
        cursor.insertElementWithText(new QName(namespace, "lugarTrabajo"), "Almacenes Simán S.A. de C.V."); //no esta
        cursor.insertElementWithText(new QName(namespace, "direccion"), response1.getDireccion());
        cursor.insertElementWithText(new QName(namespace, "direccionPatrono"), response1.getDireccionPatrono());
        cursor.toParent();

        log.info("obtenerDatosCliente response = [" + result.toString() + "]");
        return result;
    }
}
