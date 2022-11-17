package com.siman.credisiman.visa.service;

import com.credisiman.visa.soa.utils.Utils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.siman.credisiman.visa.dto.listadotarjeta.CuentasResponse;
import com.siman.credisiman.visa.dto.listadotarjeta.ListadoTarjetasResponse;
import com.siman.credisiman.visa.dto.listadotarjeta.TarjetasResponse;
import com.siman.credisiman.visa.utils.Message;
import org.apache.xmlbeans.XmlCursor;
import org.apache.xmlbeans.XmlObject;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.namespace.QName;

public class ListadoTarjetas {
    private static final Logger log = LoggerFactory.getLogger(ListadoTarjetas.class);

    public static XmlObject obtenerListadoTarjetas(String pais, String identificacion, String remoteJndiSunnel,
                                                   String remoteJndiOrion, String siscardUrl, String siscardUser, String binCredisiman) {
        String namespace = "http://siman.com/ConsultaListadoTarjetas";
        String operationResponse = "ObtenerListadoTarjetasResponse";
        ListadoTarjetasResponse response1 = new ListadoTarjetasResponse();
        //OBTENER DATOS

        Utils utils = new Utils();
        Message message = new Message();

        //validar campos requeridos
        if (utils.validateNotNull(pais) || utils.validateNotEmpty(pais)) {
            return message.genericMessage("ERROR", "025", "El campo pais es obligatorio", namespace, operationResponse);
        }
        if (utils.validateNotNull(identificacion) || utils.validateNotEmpty(identificacion)) {
            return message.genericMessage("ERROR", "025", "El campo identificacion es obligatorio", namespace, operationResponse);
        }

        try {
            JSONObject jsonSend = new JSONObject(); //json a enviar
            jsonSend.put("country", pais)
                    .put("processIdentifier", "ListadoTarjetas")
                    .put("cedula", identificacion)
                    .put("typeService", "");

            HttpResponse<String> jsonResponse //realizar petición demiante unirest
                    = Unirest.post(siscardUrl.concat("/consultaCuenta"))
                    .header("Content-Type", "application/json")
                    .body(jsonSend.toString())
                    .asString();

            //capturar respuesta
            JSONObject response = new JSONObject(jsonResponse
                    .getBody()
                    .replaceAll("u200B", ""));
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

        cursor.insertElementWithText(new QName(namespace, "statusCode"), response1.getStatusCode());
        cursor.insertElementWithText(new QName(namespace, "status"), response1.getStatus());
        cursor.insertElementWithText(new QName(namespace, "statusMessage"), response1.getStatusMessage());

        //Listado tarjetas


        for (int i = 0; i < response1.getCuentas().size(); i++) {
            CuentasResponse cuentas = response1.getCuentas().get(i);
            for (int j = 0; j < cuentas.getTarjetas().size(); j++) {
                TarjetasResponse tarjetas = cuentas.getTarjetas().get(j);
                cursor.beginElement(new QName(namespace, "tarjetas"));
                cursor.insertElementWithText(new QName(namespace, "numeroTarjeta"), tarjetas.getNumeroTarjeta());
                cursor.insertElementWithText(new QName(namespace, "cuenta"), cuentas.getCuenta());
                cursor.insertElementWithText(new QName(namespace, "tipoTarjeta"), tarjetas.getTipoTarjeta());
                cursor.insertElementWithText(new QName(namespace, "nombreTH"), tarjetas.getNombreTH());
                cursor.insertElementWithText(new QName(namespace, "estado"), tarjetas.getEstadoTarjeta());
                cursor.insertElementWithText(new QName(namespace, "limiteCreditoLocal"), tarjetas.getLimiteCreditoLocal());
                cursor.insertElementWithText(new QName(namespace, "limiteCreditoDolares"), tarjetas.getLimiteCreditoInter());
                cursor.insertElementWithText(new QName(namespace, "saldoLocal"), cuentas.getSaldoLocal());
                cursor.insertElementWithText(new QName(namespace, "saldoDolares"), cuentas.getSaldoInter());
                cursor.insertElementWithText(new QName(namespace, "disponibleLocal"), tarjetas.getDispLocalTarjeta());
                cursor.insertElementWithText(new QName(namespace, "disponibleDolares"), tarjetas.getDispIntTarjeta());
                cursor.insertElementWithText(new QName(namespace, "pagoMinimoLocal"), cuentas.getPagoMinimoLocal());
                cursor.insertElementWithText(new QName(namespace, "pagoMinimoDolares"), cuentas.getPagoMinimoInt());
                cursor.insertElementWithText(new QName(namespace, "pagoMinimoVencidoLocal"), "");
                cursor.insertElementWithText(new QName(namespace, "pagoMinimoVencidoDolares"), "");
                cursor.insertElementWithText(new QName(namespace, "pagoContadoLocal"), cuentas.getPagoContadoLocal());
                cursor.insertElementWithText(new QName(namespace, "pagoContadoDolares"),cuentas.getPagoContInt());
                cursor.insertElementWithText(new QName(namespace, "fechaPago"), cuentas.getFechaVencimientoPago());
                cursor.insertElementWithText(new QName(namespace, "fechaUltimoCorte"), "");
                cursor.insertElementWithText(new QName(namespace, "saldoMonedero"), "");
                cursor.insertElementWithText(new QName(namespace, "rombosAcumulados"), "");
                cursor.insertElementWithText(new QName(namespace, "rombosDinero"), cuentas.getSaldoPremiacion());
                cursor.insertElementWithText(new QName(namespace, "fondosReservados"), "");
                cursor.toParent();
                }
        }

        cursor.toParent();

        //si no esta en siscard, buscar en arca
       /*

        String query = "";//TODO obtener query arca
        int counter = 1;
        try {
            ConnectionHandler connectionHandler = new ConnectionHandler();
            Connection conexion = connectionHandler.getConnection("jdbc/SUNTST");
            PreparedStatement sentencia = conexion.prepareStatement(query);
            sentencia.setString(1, "parametro"); //TODO agregar parametros
            ResultSet rs = sentencia.executeQuery();

            while (rs.next()) {
                if (counter == 1) {//llenar encabezado

                }
                //TODO agregar a xml
                log.info(rs.getString("columna"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        */

        log.info("obtenerDatosCliente response = [" + result + "]");
        return result;
    }
}
