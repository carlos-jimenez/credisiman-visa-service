package com.siman.credisiman.visa.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.siman.credisiman.visa.dto.subproductos.ConsultaSubproductosResponse;
import com.siman.credisiman.visa.dto.subproductos.SubProducto;
import org.apache.xmlbeans.XmlCursor;
import org.apache.xmlbeans.XmlObject;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.namespace.QName;

public class ConsultaSubproductos {
    private static Logger log = LoggerFactory.getLogger(ConsultaSubproductos.class);

    public static XmlObject obtenerConsultaSubproductos(String pais, String numeroTarjeta, String remoteJndiSunnel,
                                                        String remoteJndiOrion, String siscardUrl, String siscardUser, String binCredisiman) {
        String namespace = "http://siman.com/ConsultaSubproductos";
        String operationResponse = "ObtenerConsultaSubproductosResponse";
        String[] fechasCompra = {"20220716", "20220717", "20220718"};
        ConsultaSubproductosResponse response1 = new ConsultaSubproductosResponse();XmlObject result = XmlObject.Factory.newInstance();
        XmlCursor cursor = result.newCursor();
        QName responseQName = new QName(namespace, operationResponse);

        //OBTENER DATOS TARJETA CREDISIMAN
        try {
            JSONObject jsonSend = new JSONObject(); //json a enviar
            jsonSend.put("country", pais)
                    .put("processIdentifier", "ConsultaSubProductos")
                    .put("tipoMensaje", 3000)
                    .put("codigoEmisor", "")
                    .put("numeroCuenta", "")
                    .put("numeroTarjeta", numeroTarjeta);

            HttpResponse<String> jsonResponse //realizar petición demiante unirest
                    = Unirest.post(siscardUrl.concat("/ConsultaSubProductosIntraExtra"))
                    .header("Content-Type", "application/json")
                    .body(jsonSend.toString())
                    .asString();

            //capturar respuesta
            JSONObject response = new JSONObject(jsonResponse
                    .getBody()
                    .replaceAll("\u200B", ""));
            response1 = new ObjectMapper()
                    .readValue(response.toString(), ConsultaSubproductosResponse.class);

        } catch (Exception e) {
            cursor.toNextToken();
            cursor.beginElement(responseQName);
            cursor.insertElementWithText(new QName(namespace, "status"),"ERROR");
            cursor.insertElementWithText(new QName(namespace, "statusCode"), "600");
            cursor.insertElementWithText(new QName(namespace, "statusMessage"), "Error general contacte al administrador del sistema...");
            cursor.toParent();

            log.info("ConsultaSubproductos response = [" + result + "]");
            log.info(e.getMessage());
        }


        cursor.toNextToken();
        cursor.beginElement(responseQName);
        cursor.insertElementWithText(new QName(namespace, "statusCode"),response1.getStatusCode() );
        cursor.insertElementWithText(new QName(namespace, "status"), response1.getStatus());
        cursor.insertElementWithText(new QName(namespace, "statusMessage"), response1.getStatusMessage());

        for (int i = 0; i < response1.subproductos.size(); i++) {
            SubProducto subproductos = response1.subproductos.get(i);

            cursor.beginElement(new QName(namespace, "subproductos"));
            cursor.insertElementWithText(new QName(namespace, "tipoSubproducto"), subproductos.getTipoSubProducto());
            cursor.insertElementWithText(new QName(namespace, "fechaCompra"), subproductos.getFechaCreacion());
            cursor.insertElementWithText(new QName(namespace, "moneda"), subproductos.getCodigoMoneda());
            cursor.insertElementWithText(new QName(namespace, "montoCompra"), subproductos.getMontoInicial());
            cursor.insertElementWithText(new QName(namespace, "montoCuota"), subproductos.getMontoCuotaActual());
            cursor.insertElementWithText(new QName(namespace, "fechaPago"), subproductos.getFechaFinalizacion());
            cursor.insertElementWithText(new QName(namespace, "saldoActual"), subproductos.getSaldoActual());
            cursor.toParent();
        }

        cursor.toParent();

        log.info("obtenerConsultaSubproductos response = [" + result.toString() + "]");
        return result;
    }
}