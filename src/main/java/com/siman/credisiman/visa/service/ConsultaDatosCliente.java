package com.siman.credisiman.visa.service;

import javax.xml.namespace.QName;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.siman.credisiman.visa.dto.datoscliente.ConsultaDatosClienteResponse;
import com.siman.credisiman.visa.utils.ConnectionHandler;
import org.apache.xmlbeans.XmlCursor;
import org.apache.xmlbeans.XmlObject;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

public class ConsultaDatosCliente {
    private static final Logger log = LoggerFactory.getLogger(ConsultaDatosCliente.class);

    public static XmlObject obtenerDatosCliente(String pais, String identificacion, String remoteJndiSunnel,
                                                String remoteJndiOrion, String siscardUrl, String siscardUser, String binCredisiman) {
        String namespace = "http://siman.com/ConsultaDatosCliente";
        String operationResponse = "ObtenerDatosClienteResponse";
        ConsultaDatosClienteResponse response1 = new ConsultaDatosClienteResponse();
        //OBTENER DATOS TARJETA CREDISIMAN
        try {
            JSONObject jsonSend = new JSONObject(); //json a enviar
            jsonSend.put("country", pais)
                    .put("processIdentifier", "ConsultaDatosEnte")
                    .put("tipoMensaje", 4300)
                    .put("identificacion", identificacion)
                    .put("usuarioSiscard", siscardUser);

            HttpResponse<String> jsonResponse //realizar petición demiante unirest
                    = Unirest
                    .post(siscardUrl.concat("/consultaDatosEnte"))
                    .header("Content-Type", "application/json")
                    .body(jsonSend.toString())
                    .asString();

            //capturar respuesta
            JSONObject response = new JSONObject(jsonResponse
                    .getBody()
                    .replaceAll("\u200B", ""));
            response1 = new ObjectMapper()
                    .readValue(response.toString(), ConsultaDatosClienteResponse.class);
        } catch (Exception e) {
            e.printStackTrace();
            log.info(e.getMessage());
        }

        //datos tarjeta privada
        ConsultaDatosClienteResponse response2 = tarjetaPrivada("022504664", remoteJndiSunnel);
        log.info(response2.getNombre());

        XmlObject result = XmlObject.Factory.newInstance();
        XmlCursor cursor = result.newCursor();
        QName responseQName = new QName(namespace, operationResponse);
        cursor.toNextToken();
        cursor.beginElement(responseQName);
        cursor.insertElementWithText(new QName(namespace, "statusCode"), response1.getStatusCode());
        cursor.insertElementWithText(new QName(namespace, "status"), response1.getStatus());
        cursor.insertElementWithText(new QName(namespace, "statusMessage"), response1.getStatusMessage());
        cursor.insertElementWithText(new QName(namespace, "primerNombre"), response1.getNombre());
        cursor.insertElementWithText(new QName(namespace, "segundoNombre"), response1.getSegundoNombre());
        cursor.insertElementWithText(new QName(namespace, "primerApellido"), response1.getPrimerApellido());
        cursor.insertElementWithText(new QName(namespace, "segundoApellido"), response1.getSegundoApellido());
        cursor.insertElementWithText(new QName(namespace, "apellidoCasada"), response1.getApellidoCasada());
        cursor.insertElementWithText(new QName(namespace, "fechaNacimiento"), response1.getNacimiento());
        cursor.insertElementWithText(new QName(namespace, "tipoIdentificacion"), response1.getTipoIdentificacion());
        cursor.insertElementWithText(new QName(namespace, "identificacion"), response1.getIdentificacion());
        cursor.insertElementWithText(new QName(namespace, "correo"), response1.getCorreoElectronico());
        cursor.insertElementWithText(new QName(namespace, "celular"), response1.getTelefonoCelular());
        cursor.insertElementWithText(new QName(namespace, "lugarTrabajo"), response1.getNombrePatrono());
        cursor.insertElementWithText(new QName(namespace, "direccion"), response1.getDireccion());
        cursor.insertElementWithText(new QName(namespace, "direccionPatrono"), response1.getDireccionPatrono());
        cursor.toParent();

        log.info("obtenerDatosCliente response = [" + result + "]");
        return result;
    }

    //OBTENER DATOS TARJETA PRIVADA
    public static ConsultaDatosClienteResponse tarjetaPrivada(String identifier, String remoteJndiSunnel) {
        String QUERY =
                "SELECT c.customerid AS niu," + "substr(pc.firstname, 1, instr(pc.firstname, " + "' '" + ") - 1) as primerNombre,"
                        + "substr(pc.firstname, instr(pc.firstname, " + "' '" + ") + 1)    as segundoNombre, " +
                        " pc.lastname1 AS primerApellido, pc.lastname2 AS segundoApellido, pc.marriedname AS apellidoCasada,  " +
                        "to_char(pc.birthdate, 'ddmmyyyy') AS fechaNacimiento, c.identificationtypeid AS tipoIdentificacion, c.identificationnumber AS numeroIdentificacion,  " +
                        "(SELECT DISTINCT ea.email FROM sunnel.t_gemailaddress ea WHERE ea.customerid = c.customerid AND ea.addressid = (SELECT MAX(at.addressid) " +
                        "FROM sunnel.t_gemailaddress at WHERE at.customerid = ea.customerid)) AS correo, " +
                        "(SELECT DISTINCT p.phonenumber FROM sunnel.t_gphone p WHERE p.customerid =  c.customerid AND p.phoneid = (SELECT MAX(pt.phoneid) FROM sunnel.t_gphone pt WHERE pt.customerid = p.customerid)) AS celular, " +
                        "' ' AS lugarTrabajo, " +
                        "' ' AS direccion, " +
                        "' ' AS direccionTrabajo " +
                        "FROM sunnel.t_gcustomer c " +
                        "INNER JOIN sunnel.t_gpersoncustomer pc ON pc.customerid = c.customerid " +
                        "WHERE c.identificationnumber = ? ";
        ConsultaDatosClienteResponse response1 = new ConsultaDatosClienteResponse();
        try {//instancia de conexion
            Connection conexion = new ConnectionHandler().getConnection(remoteJndiSunnel);
            PreparedStatement sentencia = conexion.prepareStatement(QUERY);
            sentencia.setString(1, identifier);
            ResultSet rs = sentencia.executeQuery();

            while (rs.next()) {
                //Display values
                response1.setNombre(rs.getString("primerNombre"));
                response1.setSegundoNombre(rs.getString("segundoNombre"));
                response1.setPrimerApellido(rs.getString("primerApellido"));
                response1.setSegundoApellido(rs.getString("segundoApellido"));
                response1.setApellidoCasada(rs.getString("apellidoCasada"));
                response1.setNacimiento(rs.getString("fechaNacimiento"));
                response1.setTipoIdentificacion(rs.getString("tipoIdentificacion"));
                response1.setIdentificacion(rs.getString("numeroIdentificacion"));
                response1.setCorreoElectronico(rs.getString("correo"));
                response1.setCelular(rs.getString("celular"));
                response1.setDireccion(rs.getString("direccion"));
                response1.setNombrePatrono(rs.getString("direccionTrabajo"));
                response1.setDireccionPatrono(rs.getString("lugarTrabajo"));
            }
            return response1;
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
