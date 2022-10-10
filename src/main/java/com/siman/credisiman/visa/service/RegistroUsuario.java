/**
 * 
 */
package com.siman.credisiman.visa.service;

import javax.xml.namespace.QName;

import org.apache.xmlbeans.XmlCursor;
import org.apache.xmlbeans.XmlObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RegistroUsuario {
	private static Logger log = LoggerFactory.getLogger(RegistroUsuario.class);
	
	public static XmlObject registroUsuario(String pais, String primerNombre, String segundoNombre,
			String primerApellido, String segundoApellido, String apellidoCasada, String paisResidencia,
			String fechaNacimiento, String tipoDocumento, String numeroDocumento, String correo, String celular,
			String correoNotificacion, String celularNotificacion, String remoteJndiSunnel, String remoteJndiOrion,
			String siscardUrl, String siscardUser, String binCredisiman) {

		String namespace = "http://siman.com/RegistroUsuario";
		String operationResponse = "RegistroUsuarioResponse";

		//INSERT / UPDATE / SELECT A BASE DE DATOS
		
		XmlObject result = XmlObject.Factory.newInstance();
		XmlCursor cursor = result.newCursor();
		QName responseQName = new QName(namespace, operationResponse);
		cursor.toNextToken();
		cursor.beginElement(responseQName);
		cursor.insertElementWithText(new QName(namespace, "statusCode"), "00");
		cursor.insertElementWithText(new QName(namespace, "status"), "SUCCESS");
		cursor.insertElementWithText(new QName(namespace, "statusMessage"), "Registro exitoso");
		cursor.toParent();

		log.info("registroUsuario response = [" + result.toString() + "]");
		return result;
	}
	
	public static XmlObject actualizarUsuario(String pais, String primerNombre, String segundoNombre,
			String primerApellido, String segundoApellido, String apellidoCasada, String paisResidencia,
			String fechaNacimiento, String tipoDocumento, String numeroDocumento, String correo, String celular,
			String correoNotificacion, String celularNotificacion, String remoteJndiSunnel, String remoteJndiOrion,
			String siscardUrl, String siscardUser, String binCredisiman) {

		String namespace = "http://siman.com/RegistroUsuario";
		String operationResponse = "ActualizarUsuarioResponse";

		XmlObject result = XmlObject.Factory.newInstance();
		XmlCursor cursor = result.newCursor();
		QName responseQName = new QName(namespace, operationResponse);
		cursor.toNextToken();
		cursor.beginElement(responseQName);
		cursor.insertElementWithText(new QName(namespace, "statusCode"), "00");
		cursor.insertElementWithText(new QName(namespace, "status"), "SUCCESS");
		cursor.insertElementWithText(new QName(namespace, "statusMessage"), "Registro exitoso");
		cursor.toParent();

		log.info("actualizarUsuario response = [" + result.toString() + "]");
		return result;
	}
	
	public static String registroUsuarioString(String pais, String primerNombre, String segundoNombre,
			String primerApellido, String segundoApellido, String apellidoCasada, String paisResidencia,
			String fechaNacimiento, String tipoDocumento, String numeroDocumento, String correo, String celular,
			String correoNotificacion, String celularNotificacion, String remoteJndiSunnel, String remoteJndiOrion,
			String siscardUrl, String siscardUser, String binCredisiman) {

		return RegistroUsuario.registroUsuario(pais, primerNombre, segundoNombre, primerApellido, segundoApellido, apellidoCasada, paisResidencia, fechaNacimiento, tipoDocumento, numeroDocumento, correo, celular, correoNotificacion, celularNotificacion, remoteJndiSunnel, remoteJndiOrion, siscardUrl, siscardUser, binCredisiman).toString();
	}
}
