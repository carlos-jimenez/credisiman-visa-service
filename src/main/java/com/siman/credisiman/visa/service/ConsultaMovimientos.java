package com.siman.credisiman.visa.service;

import org.apache.xmlbeans.XmlCursor;
import org.apache.xmlbeans.XmlObject;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.siman.credisiman.visa.dto.ConsultaMovimientosResponse;
import com.siman.credisiman.visa.dto.ListadoTarjetasResponse;
import com.siman.credisiman.visa.utils.ConnectionHandler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.xml.namespace.QName;

public class ConsultaMovimientos {
    private static Logger log = LoggerFactory.getLogger(ConsultaMovimientos.class);

	public static XmlObject obtenerConsultaMovimientos(String pais, String numeroTarjeta, String fechaInicial,
			String fechaFinal, String remoteJndiSunnel, String remoteJndiOrion, String siscardUrl, String siscardUser,
			String binCredisiman, String tipoTarjeta) {
        String namespace = "http://siman.com/ConsultaMovimientos";
        String operationResponse = "ObtenerConsultaMovimientosResponse";
      

        ConsultaMovimientosResponse response1 = new ConsultaMovimientosResponse();
		//OBTENER DATOS
		try {
		JSONObject jsonSend = new JSONObject(); //json a enviar
		jsonSend.put("pais", pais)
		.put("processIdentifier", "ConsultaMovimientos")
		.put("tipoMensaje", 4300)
		.put("numeroTarjeta", numeroTarjeta)
		.put("fechaInicial", fechaInicial)
		.put("fechaFinal", fechaFinal)
		.put("usuarioSiscard", siscardUser);
		
		HttpResponse<String> jsonResponse //realizar petición demiante unirest
		= Unirest.post(siscardUrl)
		.header("Content-Type", "application/json")
		.body(jsonSend.toString())
		.asString();
		
		//capturar respuesta
		JSONObject response = new JSONObject(jsonResponse
		.getBody()
		.replaceAll("\u200B", ""));
		response1 = new ObjectMapper()
		.readValue(response.toString(), ConsultaMovimientosResponse.class);
		
		log.info(new ObjectMapper().writeValueAsString(response1));
		} catch (Exception e) {
		e.printStackTrace();
		log.info(e.getMessage());
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


		for(int i=0;i<response1.getMovimientos().size();i++){
			cursor.beginElement(new QName(namespace, "movimientos"));
			cursor.insertElementWithText(new QName(namespace, "tipoMovimiento"), response1.getMovimientos().get(i).getTipoMovimiento());
			cursor.insertElementWithText(new QName(namespace, "fechaMovimiento"), response1.getMovimientos().get(i).getFechaMovimiento());
			cursor.insertElementWithText(new QName(namespace, "fechaAplicacion"), response1.getMovimientos().get(i).getFechaAplicacion());
			cursor.insertElementWithText(new QName(namespace, "moneda"), response1.getMovimientos().get(i).getMoneda());
			cursor.insertElementWithText(new QName(namespace, "monto"), response1.getMovimientos().get(i).getMonto());
			cursor.insertElementWithText(new QName(namespace, "numeroAutorizacion"), response1.getMovimientos().get(i).getNumeroAutorizacion());
			cursor.insertElementWithText(new QName(namespace, "comercio"), response1.getMovimientos().get(i).getComercio());
			cursor.toParent();
		}

        cursor.toParent();
        
        //si no esta en siscard, buscar en arca
        String query ="";//TODO obtener query ARCA
        int counter=1;
        try {
        	ConnectionHandler connectionHandler=new ConnectionHandler();
        	Connection conexion=connectionHandler.getConnection("jdbc/SUNTST");
        	PreparedStatement sentencia=conexion.prepareStatement(query);
        	sentencia.setString(1, "parametro"); //TODO agregar parametros
        	ResultSet rs=sentencia.executeQuery();
        	
        	while(rs.next()) {
        		if(counter==1) {//llenar encabezado
        			
        		}
        		//TODO agregar a xml
        		log.info(rs.getString("columna"));
        	}
        } catch (SQLException e) {
        	throw new RuntimeException(e);
        }


        log.info("obtenerConsultaMovimientos response = [" + result.toString() + "]");
        return result;
    }
}
