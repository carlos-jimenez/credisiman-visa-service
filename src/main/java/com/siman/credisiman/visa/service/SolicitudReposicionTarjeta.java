package com.siman.credisiman.visa.service;

import org.apache.xmlbeans.XmlCursor;
import org.apache.xmlbeans.XmlObject;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.siman.credisiman.visa.dto.ListadoTarjetasResponse;
import com.siman.credisiman.visa.dto.SolicitudReposicionTarjetaResponse;

import javax.xml.namespace.QName;

public class SolicitudReposicionTarjeta {
    private static Logger log = LoggerFactory.getLogger(SolicitudReposicionTarjeta.class);

	public static XmlObject crearSolicitudReposicionTarjeta(String pais, String numeroTarjeta, String nombreEmbozar,
			String razonReposicion, String remoteJndiSunnel, String remoteJndiOrion, String siscardUrl,
			String siscardUser, String binCredisiman, String tipoTarjeta) {
        String namespace = "http://siman.com/SolicitudReposicionTarjeta";
        String operationResponse = "ObtenerSolicitudReposicionTarjetaResponse";
        SolicitudReposicionTarjetaResponse response1 = new SolicitudReposicionTarjetaResponse();
        //OBTENER DATOS
        try {
    		JSONObject jsonSend = new JSONObject(); //json a enviar
    		jsonSend.put("pais", pais)
    		.put("processIdentifier", "SolicitudReposicionTarjeta")
    		.put("tipoMensaje", 4300)
    		.put("numeroTarjeta", numeroTarjeta)
    		.put("nombreEmbozar", nombreEmbozar)
    		.put("razonReposicion", razonReposicion)
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
    		.readValue(response.toString(), SolicitudReposicionTarjetaResponse.class);
    		
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
    		cursor.insertElementWithText(new QName(namespace, "nuevoNumeroTarjeta"), response1.getNuevoNumeroTarjeta());
    		cursor.toParent();


        log.info("obtenerSolicitudReposicionTarjeta response = [" + result.toString() + "]");
        return result;
    }
}