package com.siman.credisiman.visa.service;

import com.siman.credisiman.visa.utils.ConnectionHandler;
import com.siman.credisiman.visa.utils.Message;
import com.siman.credisiman.visa.utils.Utils;
import org.apache.xmlbeans.XmlCursor;
import org.apache.xmlbeans.XmlObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.namespace.QName;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ListadoEstadosCuentas {
    private static final Logger log = LoggerFactory.getLogger(ListadoEstadosCuentas.class);
    private static final String namespace = "http://siman.com/ListadoEstadosCuenta";
    private static final String operationResponse = "ObtenerListadoEstadosCuentaResponse";

	public static XmlObject obtenerListadoEstadosCuenta(String pais, String numeroTarjeta, String remoteJndiSunnel,
			String remoteJndiOrion, String siscardUrl, String siscardUser, String binCredisiman, String tipoTarjeta) {



        //validar campos requeridos
        Utils utils = new Utils();
        Message message = new Message();
        if (utils.validateNotNull(pais) || utils.validateNotEmpty(pais)) {
            log.info("pais required");
            return message.genericMessage("ERROR", "025", "El campo pais es obligatorio", namespace, operationResponse);
        }
        if (utils.validateNotNull(numeroTarjeta) || utils.validateNotEmpty(numeroTarjeta)) {
            log.info("numero tarjeta required");
            return message.genericMessage("ERROR", "025", "El campo número tarjeta es obligatorio", namespace, operationResponse);
        }

        //validar longitudes
        if (!utils.validateLongitude(pais, 3)) {
            log.info("pais, size overload");
            return message.genericMessage("ERROR", "025", "La longitud del campo pais debe ser menor o igual a 3", namespace, operationResponse);
        }
        if (!utils.validateLongitude(numeroTarjeta, 16)) {
            log.info("identificacion, size overload");
            return message.genericMessage("ERROR", "025",
                    "La longitud del campo número tarjeta debe ser menor o igual a 16", namespace, operationResponse);
        }



        //OBTENER DATOS
        try {
            switch (tipoTarjeta) {
                case "P":
                    //datos tarjeta privada
                    ArrayList<String> response1;
                    response1 = obtenerDatosArca(numeroTarjeta, remoteJndiSunnel);
                    if (response1.size()>0) {
                        return estructura(response1);
                    } else {
                        log.info("obtenerConsultaMovimientos response = [" + message.genericMessage("ERROR", "400", "La consulta no devolvio resultados", namespace, operationResponse) + "]");
                        return message.genericMessage("ERROR", "400", "La consulta no devolvio resultados", namespace, operationResponse);
                    }
                case "V":
                    //datos tarjeta visa
                    //ArrayList<String> response2 = obtenerDatosSiscard(pais, numeroTarjeta,  siscardUrl);
                    ArrayList<String> response2 = obtenerDatosArca(numeroTarjeta, remoteJndiSunnel);
                    if (response2.size()>0) {
                        return estructura(response2);
                    } else {
                        log.info("obtenerConsultaMovimientos response = [" + message.genericMessage("ERROR", "400", "La consulta no devolvio resultados", namespace, operationResponse) + "]");
                        return message.genericMessage("ERROR", "400", "La consulta no devolvio resultados", namespace, operationResponse);
                    }
            }
        } catch (SQLException e) {
            log.error("SQL ERROR, " + e.getMessage());
            log.info("obtenerConsultaMovimientos response = [" + message.genericMessage("ERROR", "600", "Error general contacte al administrador del sistema...", namespace, operationResponse) + "]");
            return message.genericMessage("ERROR", "600", "Error general contacte al administrador del sistema...", namespace, operationResponse);
        } catch (Exception ex) {
            log.error("SERVICE ERROR, " + ex.getMessage());
            log.info("obtenerConsultaMovimientos response = [" + message.genericMessage("ERROR", "600", "Error general contacte al administrador del sistema...", namespace, operationResponse) + "]");
            return message.genericMessage("ERROR", "600", "Error general contacte al administrador del sistema...", namespace, operationResponse);
        }

        log.info("obtenerConsultaMovimientos response = [" + message.genericMessage("ERROR", "400", "La consulta no devolvio resultados", namespace, operationResponse) + "]");
        return message.genericMessage("ERROR", "400", "La consulta no devolvio resultados", namespace, operationResponse);
    }

    public static ArrayList<String> obtenerDatosArca(String numeroTarjeta,  String remoteJndiSunnel) throws Exception {
        ArrayList<String> response = new ArrayList<String>();

        String query = "SELECT * " +
                "    FROM (SELECT " +
                "TO_CHAR(s.billingdate,'YYYYMMDD') AS fechaCorte " +
                "FROM SUNNELP3.t_gstatement s " +
                "INNER JOIN SUNNELP3.t_gcreditline cl ON cl.creditlineid = s.creditlineid " +
                "INNER JOIN SUNNELP3.t_gaccount a ON cl.creditlineid = a.accountid " +
                "INNER JOIN SUNNELP3.t_gcard c ON a.cardid = c.cardid " +
                "WHERE c.cardid = ? AND s.billingdate IS NOT NULL " +
                "ORDER BY s.billingyear DESC, s.billingperiodid DESC" +
                ")";


        ConnectionHandler connectionHandler = new ConnectionHandler();
        Connection conexion = connectionHandler.getConnection(remoteJndiSunnel);
        PreparedStatement sentencia = conexion.prepareStatement(query);

        sentencia.setString(1, numeroTarjeta); // agregar parametros

        ResultSet rs = sentencia.executeQuery();

        while (rs.next()) {
            response.add(rs.getString("fechaCorte"));
        }
        return response;
    }

    public static XmlObject estructura(ArrayList<String> response1) {

        XmlObject result = XmlObject.Factory.newInstance();
        XmlCursor cursor = result.newCursor();
        QName responseQName = new QName(namespace, operationResponse);
        cursor.toNextToken();
        cursor.beginElement(responseQName);
        cursor.insertElementWithText(new QName(namespace, "statusCode"), "00");
        cursor.insertElementWithText(new QName(namespace, "status"), "SUCCESS");
        cursor.insertElementWithText(new QName(namespace, "statusMessage"), "Proceso exitoso");
        for (String fechaCorte : response1) {
            cursor.beginElement(new QName(namespace, "fechasCorte"));
            cursor.insertElementWithText(new QName(namespace, "fechaCorte"), fechaCorte);
            cursor.toParent();
        }
        cursor.toParent();

        log.info("ObtenerListadoEstadosCuenta response = [" + result.toString() + "]");
        return result;
    }
}
