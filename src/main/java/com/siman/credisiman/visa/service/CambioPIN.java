package com.siman.credisiman.visa.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.siman.credisiman.visa.dto.cambiopin.CambioPinResponse;
import com.siman.credisiman.visa.utils.Message;
import com.siman.credisiman.visa.utils.Utils;
import org.apache.xmlbeans.XmlCursor;
import org.apache.xmlbeans.XmlObject;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.namespace.QName;

public class CambioPIN {
    private static Logger log = LoggerFactory.getLogger(CambioPIN.class);

    public static XmlObject cambioPIN(String pais, String numeroTarjeta, String nip, String remoteJndiSunnel,
                                      String remoteJndiOrion, String siscardUrl, String siscardUser, String binCredisiman) {
        String namespace = "http://siman.com/CambioPIN";
        String operationResponse = "CambioPINResponse";
        //OBTENER DATOS
        CambioPinResponse response1 = new CambioPinResponse();
        Utils utils = new Utils();
        Message message = new Message();


        //validar campos requeridos
        if (utils.validateNotNull(pais) || utils.validateNotEmpty(pais)) {
            return message.genericMessage("ERROR", "025", "El campo pais es obligatorio", namespace, operationResponse);
        }
        if (utils.validateNotNull(numeroTarjeta) || utils.validateNotEmpty(numeroTarjeta)) {
            return message.genericMessage("ERROR", "025", "El campo número tarjeta es obligatorio", namespace, operationResponse);
        }
        if (utils.validateNotNull(nip) || utils.validateNotEmpty(nip)) {
            return message.genericMessage("ERROR", "025", "El campo NIP monto es obligatorio", namespace, operationResponse);
        }

        try {
            JSONObject jsonSend = new JSONObject(); //json a enviar
            jsonSend.put("country", pais)
                    .put("processIdentifier", "ConsultaTransaccionesNotificaciones")
                    .put("numeroTarjeta", numeroTarjeta)
                    .put("nip", nip);

            HttpResponse<String> jsonResponse //realizar petición mediante unirest
                    = Unirest.post(siscardUrl.concat("/cambioPIN"))
                    .header("Content-Type", "application/json")
                    .body(jsonSend.toString())
                    .asString();

            //capturar respuesta
            JSONObject response = new JSONObject(jsonResponse
                    .getBody());
            response1 = new ObjectMapper()
                    .readValue(response.toString(), CambioPinResponse.class);

            log.info(new ObjectMapper().writeValueAsString(response1));

            XmlObject result = XmlObject.Factory.newInstance();
            XmlCursor cursor = result.newCursor();
            QName responseQName = new QName(namespace, operationResponse);

            cursor.toNextToken();
            cursor.beginElement(responseQName);

            String mensaje = null;
            String estado = null;
            String codigo = null;
            if(response1.getRespuestas()!= null){
                mensaje =  response1.getRespuestas().get(0).getStatusMessage();
                estado = response1.getRespuestas().get(0).getStatus();
                codigo = response1.getRespuestas().get(0).getStatusCode();
            }
            if(mensaje != null){
                cursor.insertElementWithText(new QName(namespace, "statusCode"),codigo);
                cursor.insertElementWithText(new QName(namespace, "status"), estado);
                cursor.insertElementWithText(new QName(namespace, "statusMessage"),mensaje);
            }else{
                cursor.insertElementWithText(new QName(namespace, "statusCode"),response1.getStatusCode());
                cursor.insertElementWithText(new QName(namespace, "status"), response1.getStatus());
                cursor.insertElementWithText(new QName(namespace, "statusMessage"), response1.getStatusMessage());
            }
            cursor.toParent();
            log.info("obtenerCambioPIN response = [" + result.toString() + "]");
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return message.genericMessage("ERROR", "600", "Error general contacte al administrador del sistema...", namespace, operationResponse);
        }
    }
}
