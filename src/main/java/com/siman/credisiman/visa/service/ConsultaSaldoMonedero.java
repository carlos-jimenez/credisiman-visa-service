package com.siman.credisiman.visa.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.siman.credisiman.visa.dto.consultamonedero.ConsultaSaldoMonederoResponse;
import org.apache.xmlbeans.XmlCursor;
import org.apache.xmlbeans.XmlObject;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.namespace.QName;

public class ConsultaSaldoMonedero {
    private static final Logger log = LoggerFactory.getLogger(ConsultaSaldoMonedero.class);

	public static XmlObject obtenerConsultaSaldoMonedero(String pais, String numeroTarjeta, String cuenta,
			String fechaCorte, String remoteJndiSunnel, String remoteJndiOrion, String siscardUrl, String siscardUser,
			String binCredisiman) {
        String namespace = "http://siman.com/ConsultaSaldoMonedero";
        String operationResponse = "ObtenerConsultaSaldoMonederoResponse";
        //OBTENER DATOS
        XmlObject result = XmlObject.Factory.newInstance();
        XmlCursor cursor = result.newCursor();
        QName responseQName = new QName(namespace, operationResponse);
        ConsultaSaldoMonederoResponse response1 = new ConsultaSaldoMonederoResponse();
        //OBTENER DATOS TARJETA CREDISIMAN
        try {
            JSONObject jsonSend = new JSONObject(); //json a enviar
            jsonSend.put("country", pais)
                    .put("processIdentifier", "ConsultaDatosEnte")
                    .put("fechaCorte", fechaCorte)
                    .put("numeroCuenta", cuenta)
                    .put("codigoEmisor", "45738400");

            HttpResponse<String> jsonResponse //realizar petición demiante unirest
                    = Unirest
                    .post(siscardUrl.concat("/consultaPremiacion"))
                    .header("Content-Type", "application/json")
                    .body(jsonSend.toString())
                    .asString();
            //capturar respuesta
            JSONObject response = new JSONObject(jsonResponse
                    .getBody()
                    .replaceAll("\u200B", ""));
            response1 = new ObjectMapper()
                    .readValue(response.toString(), ConsultaSaldoMonederoResponse.class);
        } catch (Exception e) {
            cursor.toNextToken();
            cursor.beginElement(responseQName);
            cursor.insertElementWithText(new QName(namespace, "status"),"ERROR");
            cursor.insertElementWithText(new QName(namespace, "statusCode"), "600");
            cursor.insertElementWithText(new QName(namespace, "statusMessage"), "Error general contacte al administrador del sistema...");
            cursor.toParent();

            log.info("ConsultaSaldoMonedero response = [" + result + "]");
            log.info(e.getMessage());

            return result;
        }

        cursor.toNextToken();
        cursor.beginElement(responseQName);
        cursor.insertElementWithText(new QName(namespace, "statusCode"), "00");
        cursor.insertElementWithText(new QName(namespace, "status"), "SUCCESS");
        cursor.insertElementWithText(new QName(namespace, "statusMessage"), "Proceso exitoso");
        cursor.insertElementWithText(new QName(namespace, "saldoInicial"),response1.getSaldoInicial());
        cursor.insertElementWithText(new QName(namespace, "puntosGanados"),response1.getPuntosGanados());
        cursor.insertElementWithText(new QName(namespace, "puntosCanjeados"),response1.getPuntosCanjeados());
        cursor.insertElementWithText(new QName(namespace, "saldoFinal"),response1.getSaldoFinal());
        cursor.toParent();

        log.info("obtenerConsultaSaldoMonedero response = [" + result.toString() + "]");
        return result;
    }
}