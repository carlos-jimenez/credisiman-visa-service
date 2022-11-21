package com.siman.credisiman.visa.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.siman.credisiman.visa.dto.consultamovimientos.ConsultaMovimientosResponse;
import com.siman.credisiman.visa.dto.listadotarjeta.CuentasResponse;
import com.siman.credisiman.visa.dto.listadotarjeta.ListadoTarjetasResponse;
import com.siman.credisiman.visa.dto.listadotarjeta.Tarjetas;
import com.siman.credisiman.visa.dto.listadotarjeta.TarjetasResponse;
import com.siman.credisiman.visa.utils.ConnectionHandler;
import com.siman.credisiman.visa.utils.Message;
import com.siman.credisiman.visa.utils.Utils;
import org.apache.xmlbeans.XmlCursor;
import org.apache.xmlbeans.XmlObject;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.namespace.QName;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

public class ListadoTarjetas {
    private static final Logger log = LoggerFactory.getLogger(ListadoTarjetas.class);
    private static final String namespace = "http://siman.com/ConsultaListadoTarjetas";
    private static final String operationResponse = "ObtenerListadoTarjetasResponse";


    public static XmlObject obtenerListadoTarjetas(String pais, String identificacion, String remoteJndiSunnel,
                                                   String remoteJndiOrion, String siscardUrl, String siscardUser, String binCredisiman) {
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

        //validar longitudes
        if (!utils.validateLongitude(pais, 3)) {
            log.info("pais, size overload");
            return message.genericMessage("ERROR", "025", "La longitud del campo pais debe ser menor o igual a 3", namespace, operationResponse);
        }
        if (!utils.validateLongitude(identificacion, 19)) {
            log.info("identificacion, size overload");
            return message.genericMessage("ERROR", "025", "La longitud del campo identificacion debe ser menor o igual a 19", namespace, operationResponse);
        }

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
                cursor.insertElementWithText(new QName(namespace, "pagoContadoDolares"), cuentas.getPagoContInt());
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

        return result;
    }

    public static XmlObject estructura(List<Tarjetas> response) {
        XmlObject result = XmlObject.Factory.newInstance();
        XmlCursor cursor = result.newCursor();
        QName responseQName = new QName(namespace, operationResponse);

        cursor.toNextToken();
        cursor.beginElement(responseQName);

        cursor.insertElementWithText(new QName(namespace, "statusCode"), "00");
        cursor.insertElementWithText(new QName(namespace, "status"), "SUCCESS");
        cursor.insertElementWithText(new QName(namespace, "statusMessage"), "Proceso exitoso");
        //Listado tarjetas

        for (int i = 0; i < response.size(); i++) {
            cursor.beginElement(new QName(namespace, "tarjetas"));
            cursor.insertElementWithText(new QName(namespace, "numeroTarjeta"), response.get(i).getNumeroTarjeta());
            cursor.insertElementWithText(new QName(namespace, "cuenta"), response.get(i).getCuenta());
            cursor.insertElementWithText(new QName(namespace, "tipoTarjeta"), response.get(i).getTipoTarjeta());
            cursor.insertElementWithText(new QName(namespace, "nombreTH"), response.get(i).getNombreTH());
            cursor.insertElementWithText(new QName(namespace, "estado"),response.get(i).getEstado());
            cursor.insertElementWithText(new QName(namespace, "limiteCreditoLocal"), response.get(i).getLimiteCreditoLocal());
            cursor.insertElementWithText(new QName(namespace, "limiteCreditoDolares"), response.get(i).getLimiteCreditoDolares());
            cursor.insertElementWithText(new QName(namespace, "saldoLocal"), response.get(i).getSaldoLocal());
            cursor.insertElementWithText(new QName(namespace, "saldoDolares"), response.get(i).getSaldoDolares());
            cursor.insertElementWithText(new QName(namespace, "disponibleLocal"), response.get(i).getDisponibleLocal());
            cursor.insertElementWithText(new QName(namespace, "disponibleDolares"),response.get(i).getDisponibleDolares());
            cursor.insertElementWithText(new QName(namespace, "pagoMinimoLocal"), response.get(i).getPagoMinimoLocal());
            cursor.insertElementWithText(new QName(namespace, "pagoMinimoDolares"),response.get(i).getPagoMinimoDolares());
            cursor.insertElementWithText(new QName(namespace, "pagoMinimoVencidoLocal"), response.get(i).getPagoMinimoVencidoLocal());
            cursor.insertElementWithText(new QName(namespace, "pagoMinimoVencidoDolares"), "");
            cursor.insertElementWithText(new QName(namespace, "pagoContadoLocal"), response.get(i).getPagoContadoLocal());
            cursor.insertElementWithText(new QName(namespace, "pagoContadoDolares"), response.get(i).getPagoContadoDolares());
            cursor.insertElementWithText(new QName(namespace, "fechaPago"), response.get(i).getFechaPago());
            cursor.insertElementWithText(new QName(namespace, "fechaUltimoCorte"), response.get(i).getFechaUltimoCorte());
            cursor.insertElementWithText(new QName(namespace, "saldoMonedero"), response.get(i).getSaldoMonedero());
            cursor.insertElementWithText(new QName(namespace, "rombosAcumulados"), response.get(i).getRombosAcumulados());
            cursor.insertElementWithText(new QName(namespace, "rombosDinero"), response.get(i).getRombosDinero());
            cursor.insertElementWithText(new QName(namespace, "fondosReservados"), response.get(i).getFondosReservados());
            cursor.toParent();
        }
        cursor.toParent();
        log.info("obtenerDatosCliente response = [" + result + "]");
        return result;
    }
    public static ListadoTarjetasResponse obtenerDatosArca(String identificacion) throws Exception{
        //si no esta en siscard, buscar en arca
        String query = "SELECT c.cardid AS numeroTarjeta, " +
                "       cl.creditlineid AS cuenta, " +
                "       DECODE (c.CARDTYPE, " +
                "               'M', 'TITULAR', " +
                "               'P', 'TITULAR', " +
                "               'A', 'ADICIONAL') " +
                "          AS tipoTarjeta, " +
                "       cu.aliasname AS nombreTH, " +
                "       CASE WHEN cl.blockedind = 'T' THEN 'Bloqueada' ELSE 'Activa' END " +
                "          AS estado, " +
                "       CASE WHEN cl.currencycreditlimit <> 840 THEN cl.creditlimit ELSE 0 END " +
                "          AS limiteCreditoLocal, " +
                "       CASE WHEN cl.currencycreditlimit = 840 THEN cl.creditlimit ELSE 0 END " +
                "          AS limiteCreditoDolares, " +
                "       CASE " +
                "          WHEN fb.currencyid <> 840 " +
                "          THEN " +
                "             CASE " +
                "                WHEN NVL (fb.saldo, 0) = 0 AND NVL (cb.saldoAFavor, 0) > 0 " +
                "                THEN " +
                "                   (cb.saldoAFavor * -1) " +
                "                ELSE " +
                "                   NVL (fb.saldo, 0) " +
                "             END " +
                "          ELSE " +
                "             0 " +
                "       END " +
                "          AS saldoLocal, " +
                "       CASE " +
                "          WHEN fb.currencyid = 840 " +
                "          THEN " +
                "             CASE " +
                "                WHEN NVL (fb.saldo, 0) = 0 AND NVL (cb.saldoAFavor, 0) > 0 " +
                "                THEN " +
                "                   (cb.saldoAFavor * -1) " +
                "                ELSE " +
                "                   NVL (fb.saldo, 0) " +
                "             END " +
                "          ELSE " +
                "             0 " +
                "       END " +
                "          AS saldoDolares, " +
                "       CASE " +
                "          WHEN clp.currencycreditlimit <> 840 THEN clp.availablebalance " +
                "          ELSE 0 " +
                "       END " +
                "          AS disponibleLocal, " +
                "       CASE " +
                "          WHEN clp.currencycreditlimit = 840 THEN clp.availablebalance " +
                "          ELSE 0 " +
                "       END " +
                "          AS disponibleDolares, " +
                "       CASE WHEN fb.currencyid <> 840 THEN fb.pagoMinimo ELSE 0 END " +
                "          AS pagoMinimoLocal, " +
                "       CASE WHEN fb.currencyid = 840 THEN fb.pagoMinimo ELSE 0 END " +
                "          AS pagoMinimoDolares, " +
                "       CASE WHEN fb.currencyid <> 840 THEN fb.capitalVencido ELSE 0 END " +
                "          AS pagoMinimoVencidoLocal, " +
                "       CASE WHEN fb.currencyid = 840 THEN fb.capitalVencido ELSE 0 END " +
                "          AS pagoMinimoVencidoDolares, " +
                "       CASE WHEN fb.currencyid <> 840 THEN fb.pagoContado ELSE 0 END " +
                "          AS pagoContadoLocal, " +
                "       CASE WHEN fb.currencyid = 840 THEN fb.pagoContado ELSE 0 END " +
                "          AS pagoContadoDolares, " +
                "       bp.fechaPago AS fechaPago, " +
                "       cl.lastinterestaccruingdate AS fechaUltimoCorte, " +
                "       ' ' AS saldoMonedero, " +
                "       ' ' AS rombosAcumulados, " +
                "       ' ' AS rombosDinero, " +
                "       ' ' AS fondosReservados " +
                "  FROM SUNNELP3.t_gcard c " +
                "       INNER JOIN SUNNELP3.t_gcustomer cu " +
                "          ON cu.customerid = c.customerid " +
                "       INNER JOIN SUNNELP3.t_gaccount a " +
                "          ON a.cardid = c.cardid " +
                "       INNER JOIN SUNNELP3.t_gcreditline cl " +
                "          ON cl.creditlineid = a.accountid " +
                "       INNER JOIN SUNNELP3.t_gcreditlinepartition clp " +
                "          ON cl.creditlineid = clp.creditlineid " +
                "       LEFT OUTER JOIN (  SELECT clt.creditlineid, " +
                "                                 MAX (bpt.paymentdate) AS fechaPago " +
                "                            FROM    SUNNELP3.t_gbillingperiod bpt " +
                "                                 INNER JOIN " +
                "                                    SUNNELP3.t_gcreditline clt " +
                "                                 ON     clt.billingcycleid = bpt.billingcycleid " +
                "                                    AND clt.lastinterestaccruingdate = " +
                "                                           bpt.billingdate " +
                "                        GROUP BY clt.creditlineid) bp " +
                "          ON bp.creditlineid = cl.creditlineid " +
                "       LEFT OUTER JOIN (  SELECT fbt.creditlineid, " +
                "                                 fbt.currencyid, " +
                "                                 SUM ( " +
                "                                      fbt.regularbalance " +
                "                                    + fbt.periodamountdue " +
                "                                    + fbt.regularinterest " +
                "                                    + fbt.regularinteresttax " +
                "                                    + fbt.overduebalance " +
                "                                    + fbt.overdueinterest " +
                "                                    + fbt.overdueinteresttax " +
                "                                    + fbt.contingentinterest) " +
                "                                    AS saldo, " +
                "                                 SUM ( " +
                "                                      fbt.periodamountdue " +
                "                                    + fbt.regularinterest " +
                "                                    + fbt.regularinteresttax " +
                "                                    + fbt.overduebalance " +
                "                                    + fbt.overdueinterest " +
                "                                    + fbt.overdueinteresttax " +
                "                                    + fbt.contingentinterest) " +
                "                                    AS pagoMinimo, " +
                "                                 SUM ( " +
                "                                      fbt.periodamountdue " +
                "                                    + fbt.regularinterest " +
                "                                    + fbt.regularinteresttax " +
                "                                    + fbt.overduebalance " +
                "                                    + fbt.overdueinterest " +
                "                                    + fbt.overdueinteresttax " +
                "                                    + fbt.contingentinterest) " +
                "                                    AS pagoContado, " +
                "                                 SUM (fbt.regularbalance) AS capitalNoExigible, " +
                "                                 SUM (fbt.periodamountdue) AS capitalExigible, " +
                "                                 SUM (fbt.overduebalance) AS capitalVencido, " +
                "                                 SUM (regularinterest + fbt.regularinteresttax) " +
                "                                    AS interesCorriente, " +
                "                                 SUM ( " +
                "                                      fbt.overdueinterest " +
                "                                    + fbt.overdueinteresttax) " +
                "                                    AS interesMoratorio, " +
                "                                 SUM (fbt.contingentinterest) " +
                "                                    AS interesContigente " +
                "                            FROM t_gfinancingbalance fbt " +
                "                        GROUP BY fbt.creditlineid, fbt.currencyid) fb " +
                "          ON     fb.creditlineid = cl.creditlineid " +
                "             AND fb.currencyid = cl.currencycreditlimit " +
                "       LEFT OUTER JOIN (  SELECT cbt.creditlineid, " +
                "                                 cbt.currencyid, " +
                "                                 SUM (cbt.amountinexcess) AS saldoAFavor " +
                "                            FROM t_gcreditbalance cbt " +
                "                        GROUP BY cbt.creditlineid, cbt.currencyid) cb " +
                "          ON     cb.creditlineid = cl.creditlineid " +
                "             AND cb.currencyid = cl.currencycreditlimit " +
                " WHERE c.closedind = 'F' AND cu.identificationnumber = ? ";//TODO obtener query arca

        try {
            ConnectionHandler connectionHandler = new ConnectionHandler();
            Connection conexion = connectionHandler.getConnection("jdbc/SUNTST");
            PreparedStatement sentencia = conexion.prepareStatement(query);
            sentencia.setString(1, identificacion); //TODO agregar parametros
            ResultSet rs = sentencia.executeQuery();

            while (rs.next()) {
                //TODO agregar a xml
                log.info(rs.getString("columna"));
            }
        }

    }
    public static ListadoTarjetasResponse obtenerDatosSiscard(String pais, String identificacion, String siscardUrl) throws Exception {
        ListadoTarjetasResponse response1 = new ListadoTarjetasResponse();
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
        return response1;
    }
}
