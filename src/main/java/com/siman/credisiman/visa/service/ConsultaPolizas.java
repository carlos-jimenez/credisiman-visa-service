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
import com.siman.credisiman.visa.dto.ConsultaPolizasResponse;
import com.siman.credisiman.visa.utils.ConnectionHandler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.xml.namespace.QName;

public class ConsultaPolizas {
    private static Logger log = LoggerFactory.getLogger(ConsultaPolizas.class);

	public static XmlObject obtenerConsultaPolizas(String pais, String numeroTarjeta, String remoteJndiSunnel,
			String remoteJndiOrion, String siscardUrl, String siscardUser, String binCredisiman) {
        String namespace = "http://siman.com/ConsultaPolizas";
        String operationResponse = "ObtenerConsultaPolizasResponse";
        

        ConsultaPolizasResponse response1 = new ConsultaPolizasResponse();
		//OBTENER DATOS
		try {
		JSONObject jsonSend = new JSONObject(); //json a enviar
		jsonSend.put("pais", pais)
		.put("processIdentifier", "ConsultaPolizas")
		.put("tipoMensaje", 4300)
		.put("numeroTarjeta", numeroTarjeta)
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
		.readValue(response.toString(), ConsultaPolizasResponse.class);
		
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


		for(int i=0;i<response1.getListaDePolizas().size();i++){
			cursor.beginElement(new QName(namespace, "listaDePolizas"));
			cursor.insertElementWithText(new QName(namespace, "tipoPoliza"), response1.getListaDePolizas().get(i).getTipoPoliza());
			cursor.insertElementWithText(new QName(namespace, "nombrePoliza"), response1.getListaDePolizas().get(i).getNombrePoliza());
			cursor.insertElementWithText(new QName(namespace, "estadoPoliza"), response1.getListaDePolizas().get(i).getEstadoPoliza());
			cursor.toParent();
		}

        cursor.toParent();
     
        //si no esta en siscard, buscar en arca
        String query ="SELECT A.CARDID,\n"
        		+ "       AT.AUTOMATEDCHARGETYPEID tipoPoliza,\n"
        		+ "       AT.DESCRIPTION nombrePoliza,\n"
        		+ "       A.STATUS estadoPoliza \n"
        		+ "  FROM SUNNEL.T_GAUTOMATEDCHARGE a, SUNNEL.T_GAUTOMATEDCHARGETYPE at\n"
        		+ " WHERE A.AUTOMATEDCHARGETYPEID = AT.AUTOMATEDCHARGETYPEID\n"
        		+ "AND A.CARDID=?";
        
        int counter=1;
        try {
        	ConnectionHandler connectionHandler=new ConnectionHandler();
        	Connection conexion=connectionHandler.getConnection("jdbc/SUNTST");
        	PreparedStatement sentencia=conexion.prepareStatement(query);
        	//parametros
        	sentencia.setString(1, numeroTarjeta);
        	ResultSet rs=sentencia.executeQuery();
        	
        	while(rs.next()) {

        		cursor.beginElement(new QName(namespace, "listaDePolizas"));
    			cursor.insertElementWithText(new QName(namespace, "tipoPoliza"), rs.getString("tipoPoliza"));
    			cursor.insertElementWithText(new QName(namespace, "nombrePoliza"),  rs.getString("nombrePoliza"));
    			cursor.insertElementWithText(new QName(namespace, "estadoPoliza"),  rs.getString("estadoPoliza"));
    			cursor.toParent();
        		//log.info(rs.getString("columna"));
        	}
        	cursor.toParent();
        } catch (SQLException e) {
        	throw new RuntimeException(e);
        }

        log.info("obtenerConsultaPolizas response = [" + result.toString() + "]");
        return result;
    }
}
