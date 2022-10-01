package com.siman.credisiman.visa.service;

import javax.xml.namespace.QName;

import org.apache.xmlbeans.XmlCursor;
import org.apache.xmlbeans.XmlObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConsultaDatosCliente {
	private static Logger log = LoggerFactory.getLogger(ConsultaDatosCliente.class);
	
	public static XmlObject obtenerDatosCliente(String pais, String identificacion, String remoteJndi) {
		String namespace = "http://siman.com/ConsultaDatosCliente";
		String operationResponse = "ObtenerDatosClienteResponse";

		//OBTENER DATOS
		
		XmlObject result = XmlObject.Factory.newInstance();
		XmlCursor cursor = result.newCursor();
		QName responseQName = new QName(namespace, operationResponse);
		cursor.toNextToken();
		cursor.beginElement(responseQName);
		cursor.insertElementWithText(new QName(namespace, "statusCode"), "00");
		cursor.insertElementWithText(new QName(namespace, "status"), "SUCCESS");
		cursor.insertElementWithText(new QName(namespace, "statusMessage"), "Proceso exitoso");
		cursor.insertElementWithText(new QName(namespace, "primerNombre"), "Juan");
		cursor.insertElementWithText(new QName(namespace, "segundoNombre"), "José");
		cursor.insertElementWithText(new QName(namespace, "primerApellido"), "Pérez");
		cursor.insertElementWithText(new QName(namespace, "segundoApellido"), "Cañas");
		cursor.insertElementWithText(new QName(namespace, "apellidoCasada"), "Gómez");
		cursor.insertElementWithText(new QName(namespace, "fechaNacimiento"), "19810423");
		cursor.insertElementWithText(new QName(namespace, "tipoIdentificacion"), "012345678");
		cursor.insertElementWithText(new QName(namespace, "identificacion"), "012345678");
		cursor.insertElementWithText(new QName(namespace, "correo"), "juan.perez@correo.com");
		cursor.insertElementWithText(new QName(namespace, "celular"), "77770000");
		cursor.insertElementWithText(new QName(namespace, "lugarTrabajo"), "Almacenes Simán S.A. de C.V.");
		cursor.insertElementWithText(new QName(namespace, "direccion"), "Direccion particular");
		cursor.insertElementWithText(new QName(namespace, "direccionPatrono"), "Direccion patrono");
		cursor.toParent();

		log.info("obtenerDatosCliente response = [" + result.toString() + "]");
		return result;
	}
}
