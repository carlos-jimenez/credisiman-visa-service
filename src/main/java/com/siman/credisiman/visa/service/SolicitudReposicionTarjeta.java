package com.siman.credisiman.visa.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.siman.credisiman.visa.dto.datoscliente.ConsultaDatosClienteResponse;
import com.siman.credisiman.visa.dto.solicitudreposiciontarjeta.SolicitudReposicionTarjetaResponse;
import org.apache.xmlbeans.XmlCursor;
import org.apache.xmlbeans.XmlObject;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.namespace.QName;

public class SolicitudReposicionTarjeta {
    private static Logger log = LoggerFactory.getLogger(SolicitudReposicionTarjeta.class);

	public static XmlObject crearSolicitudReposicionTarjeta(String pais, String numeroTarjeta, String nombreEmbozar,
			String razonReposicion, String remoteJndiSunnel, String remoteJndiOrion, String siscardUrl,
			String siscardUser, String binCredisiman) {
        String namespace = "http://siman.com/SolicitudReposicionTarjeta";
        String operationResponse = "ObtenerSolicitudReposicionTarjetaResponse";
        SolicitudReposicionTarjetaResponse response1 = new SolicitudReposicionTarjetaResponse();
        XmlObject result = XmlObject.Factory.newInstance();
        XmlCursor cursor = result.newCursor();
        QName responseQName = new QName(namespace, operationResponse);

        //OBTENER DATOS

        try {
            JSONObject jsonSend = new JSONObject(); //json a enviar
            jsonSend.put("country", pais)
                    .put("processIdentifier", "solicitudTarjetaReposicion")
                    .put("numeroTarjeta", numeroTarjeta)
                    .put("nombreEmbozar", nombreEmbozar)
                    .put("razonReposicion", razonReposicion)
                    .put("localidadTramite", "01")
                    .put("localidadRetiro", "01")
                    .put("indicadorCobroxReposicion", "S");

            HttpResponse<String> jsonResponse //realizar petición demiante unirest
                    = Unirest
                    .post(siscardUrl.concat("/solicitudTarjetaReposicion"))
                    .header("Content-Type", "application/json")
                    .body(jsonSend.toString())
                    .asString();

            //capturar respuesta
            JSONObject response = new JSONObject(jsonResponse
                    .getBody()
                    .replaceAll("\u200B", ""));
            response1 = new ObjectMapper()
                    .readValue(response.toString(), SolicitudReposicionTarjetaResponse.class);
        } catch (Exception e) {
            cursor.toNextToken();
            cursor.beginElement(responseQName);
            cursor.insertElementWithText(new QName(namespace, "status"),"ERROR");
            cursor.insertElementWithText(new QName(namespace, "statusCode"), "600");
            cursor.insertElementWithText(new QName(namespace, "statusMessage"), "Error general contacte al administrador del sistema...");
            cursor.toParent();

            log.info("SolicitudReposicionTarjeta response = [" + result + "]");
            log.info(e.getMessage());

            return result;
        }



        cursor.toNextToken();
        cursor.beginElement(responseQName);
        cursor.insertElementWithText(new QName(namespace, "statusCode"), response1.getStatusCode());
        cursor.insertElementWithText(new QName(namespace, "status"), response1.getStatus());
        cursor.insertElementWithText(new QName(namespace, "statusMessage"), response1.getStatusMessage());
        cursor.insertElementWithText(new QName(namespace, "nuevoNumeroTarjeta"), response1.getNuevoNumeroTarjeta());
        cursor.toParent();

        log.info("obtenerSolicitudReposicionTarjeta response = [" + result.toString() + "]");
        return result;
    }
}