package com.siman.credisiman.visa.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.xml.namespace.QName;

import org.apache.xmlbeans.XmlCursor;
import org.apache.xmlbeans.XmlObject;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.siman.credisiman.visa.dto.ListadoTarjetasResponse;
import com.siman.credisiman.visa.utils.ConnectionHandler;

public class ListadoTarjetas {
	private static Logger log = LoggerFactory.getLogger(ListadoTarjetas.class);

	public static XmlObject obtenerListadoTarjetas(String pais, String identificacion, String remoteJndiSunnel,
		            String remoteJndiOrion, String siscardUrl, String siscardUser, String binCredisiman) {
		String namespace = "http://siman.com/ConsultaListadoTarjetas";
		String operationResponse = "ObtenerListadoTarjetasResponse";
		ListadoTarjetasResponse response1 = new ListadoTarjetasResponse();
		//OBTENER DATOS
		try {
		JSONObject jsonSend = new JSONObject(); //json a enviar
		jsonSend.put("pais", pais)
		.put("processIdentifier", "ListadoTarjetas")
		.put("tipoMensaje", 4300)
		.put("identificacion", identificacion)
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
		.readValue(response.toString(), ListadoTarjetasResponse.class);
		
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

		//Listado tarjetas
		
		for(int i=0;i<response1.getTarjetas().size();i++){
			cursor.beginElement(new QName(namespace, "tarjetas"));
			cursor.insertElementWithText(new QName(namespace, "numeroTarjeta"), response1.getTarjetas().get(i).getNumeroTarjeta());
			cursor.insertElementWithText(new QName(namespace, "cuenta"), response1.getTarjetas().get(i).getCuenta());
			cursor.insertElementWithText(new QName(namespace, "tipoTarjeta"), response1.getTarjetas().get(i).getTipoTarjeta());
			cursor.insertElementWithText(new QName(namespace, "nombreTH"), response1.getTarjetas().get(i).getNombreTH());
			cursor.insertElementWithText(new QName(namespace, "estado"), response1.getTarjetas().get(i).getEstado());
			cursor.insertElementWithText(new QName(namespace, "limiteCreditoLocal"), response1.getTarjetas().get(i).getLimiteCreditoLocal());
			cursor.insertElementWithText(new QName(namespace, "limiteCreditoDolares"), response1.getTarjetas().get(i).getLimiteCreditoDolares());
			cursor.insertElementWithText(new QName(namespace, "saldoLocal"), response1.getTarjetas().get(i).getSaldoLocal());
			cursor.insertElementWithText(new QName(namespace, "saldoDolares"), response1.getTarjetas().get(i).getSaldoDolares());
			cursor.insertElementWithText(new QName(namespace, "disponibleLocal"), response1.getTarjetas().get(i).getDisponibleLocal());
			cursor.insertElementWithText(new QName(namespace, "disponibleDolares"), response1.getTarjetas().get(i).getDisponibleDolares());
			cursor.insertElementWithText(new QName(namespace, "pagoMinimoLocal"), response1.getTarjetas().get(i).getPagoMinimoLocal());
			cursor.insertElementWithText(new QName(namespace, "pagoMinimoDolares"), response1.getTarjetas().get(i).getPagoMinimoDolares());
			cursor.insertElementWithText(new QName(namespace, "pagoMinimoVencidoLocal"), response1.getTarjetas().get(i).getPagoMinimoVencidoLocal());
			cursor.insertElementWithText(new QName(namespace, "pagoMinimoVencidoDolares"), response1.getTarjetas().get(i).getPagoMinimoVencidoDolares());
			cursor.insertElementWithText(new QName(namespace, "pagoContadoLocal"), response1.getTarjetas().get(i).getPagoContadoLocal());
			cursor.insertElementWithText(new QName(namespace, "pagoContadoDolares"), response1.getTarjetas().get(i).getPagoContadoDolares());
			cursor.insertElementWithText(new QName(namespace, "fechaPago"), response1.getTarjetas().get(i).getFechaPago());
			cursor.insertElementWithText(new QName(namespace, "fechaUltimoCorte"), response1.getTarjetas().get(i).getFechaUltimoCorte());
			cursor.insertElementWithText(new QName(namespace, "saldoMonedero"), response1.getTarjetas().get(i).getSaldoMonedero());
			cursor.insertElementWithText(new QName(namespace, "rombosAcumulados"), response1.getTarjetas().get(i).getRombosAcumulados());
			cursor.insertElementWithText(new QName(namespace, "rombosDinero"), response1.getTarjetas().get(i).getRombosDinero());
			cursor.insertElementWithText(new QName(namespace, "fondosReservados"), response1.getTarjetas().get(i).getFondosReservados());
			cursor.insertElementWithText(new QName(namespace, "cuenta"), response1.getTarjetas().get(i).getCuenta());
			cursor.insertElementWithText(new QName(namespace, "tipoTarjeta"), response1.getTarjetas().get(i).getTipoTarjeta());
			cursor.insertElementWithText(new QName(namespace, "nombreTH"), response1.getTarjetas().get(i).getNombreTH());
			cursor.insertElementWithText(new QName(namespace, "estado"), response1.getTarjetas().get(i).getEstado());
			cursor.insertElementWithText(new QName(namespace, "limiteCreditoLocal"), response1.getTarjetas().get(i).getLimiteCreditoLocal());
			cursor.insertElementWithText(new QName(namespace, "limiteCreditoDolares"), response1.getTarjetas().get(i).getLimiteCreditoDolares());
			cursor.insertElementWithText(new QName(namespace, "saldoLocal"), response1.getTarjetas().get(i).getSaldoLocal());
			cursor.insertElementWithText(new QName(namespace, "saldoDolares"), response1.getTarjetas().get(i).getSaldoDolares());
			cursor.insertElementWithText(new QName(namespace, "disponibleLocal"), response1.getTarjetas().get(i).getDisponibleLocal());
			cursor.insertElementWithText(new QName(namespace, "disponibleDolares"), response1.getTarjetas().get(i).getDisponibleDolares());
			cursor.insertElementWithText(new QName(namespace, "pagoMinimoLocal"), response1.getTarjetas().get(i).getPagoMinimoLocal());
			cursor.insertElementWithText(new QName(namespace, "pagoMinimoDolares"), response1.getTarjetas().get(i).getPagoMinimoDolares());
			cursor.insertElementWithText(new QName(namespace, "pagoMinimoVencidoLocal"), response1.getTarjetas().get(i).getPagoMinimoVencidoLocal());
			cursor.insertElementWithText(new QName(namespace, "pagoMinimoVencidoDolares"), response1.getTarjetas().get(i).getPagoMinimoVencidoDolares());
			cursor.insertElementWithText(new QName(namespace, "pagoContadoLocal"), response1.getTarjetas().get(i).getPagoContadoLocal());
			cursor.insertElementWithText(new QName(namespace, "pagoContadoDolares"), response1.getTarjetas().get(i).getPagoContadoDolares());
			cursor.insertElementWithText(new QName(namespace, "fechaPago"), response1.getTarjetas().get(i).getFechaPago());
			cursor.insertElementWithText(new QName(namespace, "fechaUltimoCorte"), response1.getTarjetas().get(i).getFechaUltimoCorte());
			cursor.insertElementWithText(new QName(namespace, "saldoMonedero"), response1.getTarjetas().get(i).getSaldoMonedero());
			cursor.insertElementWithText(new QName(namespace, "rombosAcumulados"), response1.getTarjetas().get(i).getRombosAcumulados());
			cursor.insertElementWithText(new QName(namespace, "rombosDinero"), response1.getTarjetas().get(i).getRombosDinero());
			cursor.insertElementWithText(new QName(namespace, "fondosReservados"), response1.getTarjetas().get(i).getFondosReservados());
			cursor.toParent();
		}

		cursor.toParent();
		
        //si no esta en siscard, buscar en arca
		  String query ="";//TODO obtener query arca
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

		log.info("obtenerDatosCliente response = [" + result.toString() + "]");
		return result;
		}
}
