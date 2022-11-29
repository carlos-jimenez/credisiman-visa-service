package com.siman.credisiman.visa.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.siman.credisiman.visa.dto.bloqueodesbloqueotarjeta.BloqueoDesbloqueoTarjetaResponse;
import com.siman.credisiman.visa.utils.Message;
import com.siman.credisiman.visa.utils.Utils;
import org.apache.xmlbeans.XmlCursor;
import org.apache.xmlbeans.XmlObject;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.namespace.QName;

public class BloqueoDesbloqueoTarjeta {
    private static final Logger log = LoggerFactory.getLogger(BloqueoDesbloqueoTarjeta.class);

    public static XmlObject obtenerBloqueoDesbloqueoTarjeta(String pais, String numeroTarjeta, String estadoDeseado,
                                                            String motivo, String remoteJndiSunnel, String remoteJndiOrion, String siscardUrl, String siscardUser,
                                                            String binCredisiman, String tipoTarjeta) {
        String namespace = "http://siman.com/BloqueoDesbloqueoTarjeta";
        String operationResponse = "ObtenerBloqueoDesbloqueoTarjetaResponse";

        BloqueoDesbloqueoTarjetaResponse response1 = new BloqueoDesbloqueoTarjetaResponse();
        //OBTENER DATOS

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
        if (utils.validateNotNull(motivo) || utils.validateNotEmpty(motivo)) {
            log.info("motivo required");
            return message.genericMessage("ERROR", "025", "El campo motivo es obligatorio", namespace, operationResponse);
        }
        if (utils.validateNotNull(estadoDeseado) || utils.validateNotEmpty(estadoDeseado)) {
            log.info("estado deseado required");
            return message.genericMessage("ERROR", "025", "El campo estado deseado es obligatorio", namespace, operationResponse);
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



        try {
            JSONObject jsonSend = new JSONObject(); //json a enviar
            jsonSend.put("country", pais)
                    .put("processIdentifier", "BloqueoDesbloqueoTarjetas")
                    .put("tipoMensaje", 4300)
                    .put("numeroTarjeta", numeroTarjeta)
                    .put("estadodeseado", estadoDeseado)
                    .put("motivoCancelacion", motivo);

            HttpResponse<String> jsonResponse //realizar petición mediante unirest
                    = Unirest.post(siscardUrl.concat("/bloqueoDesbloqueoTarjetas"))
                    .header("Content-Type", "application/json")
                    .body(jsonSend.toString())
                    .asString();

            //capturar respuesta
            JSONObject response = new JSONObject(jsonResponse
                    .getBody()
                    .replaceAll("\u200B", ""));
            response1 = new ObjectMapper()
                    .readValue(response.toString(), BloqueoDesbloqueoTarjetaResponse.class);

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

        cursor.toParent();


        log.info("obtenerBloqueoDesbloqueoTarjeta response = [" + result.toString() + "]");
        return result;
    }
}
