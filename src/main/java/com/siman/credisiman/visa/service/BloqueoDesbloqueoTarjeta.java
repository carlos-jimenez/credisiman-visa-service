package com.siman.credisiman.visa.service;

import org.apache.xmlbeans.XmlCursor;
import org.apache.xmlbeans.XmlObject;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.siman.credisiman.visa.dto.BloqueoDesbloqueoTarjetaResponse;
import com.siman.credisiman.visa.dto.SolicitudReposicionTarjetaResponse;

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
        try {
    		JSONObject jsonSend = new JSONObject(); //json a enviar
    		jsonSend.put("pais", pais)
    		.put("processIdentifier", "SolicitudReposicionTarjeta")
    		.put("tipoMensaje", 4300)
    		.put("numeroTarjeta", numeroTarjeta)
    		.put("estadoDeseado", estadoDeseado)
    		.put("motivo", motivo)
    		.put("usuarioSiscard", siscardUser);
    		
    		HttpResponse<String> jsonResponse //realizar petición mediante unirest
    		= Unirest.post(siscardUrl)
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
    		
    		cursor.insertElementWithText(new QName(namespace, "statusCode"),  response1.statusCode);
    		cursor.insertElementWithText(new QName(namespace, "status"), response1.getStatus());
    		cursor.insertElementWithText(new QName(namespace, "statusMessage"), response1.getStatusMessage());

    		cursor.toParent();
        

        log.info("obtenerBloqueoDesbloqueoTarjeta response = [" + result.toString() + "]");
        return result;
    }
}
