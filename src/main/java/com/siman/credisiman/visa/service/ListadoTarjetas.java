package com.siman.credisiman.visa.service;

import javax.xml.namespace.QName;

import org.apache.xmlbeans.XmlCursor;
import org.apache.xmlbeans.XmlObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ListadoTarjetas {
	private static Logger log = LoggerFactory.getLogger(ListadoTarjetas.class);

	public static XmlObject obtenerListadoTarjetas(String pais, String identificacion, String remoteJndi) {
		String namespace = "http://siman.com/ListadoTarjetas";
		String operationResponse = "ObtenerListadoTarjetasResponse";
		String[] listadoTarjetas = {"4000123456780000", "4000123456780001", "4000123456780002"};
		//OBTENER DATOS
		
		XmlObject result = XmlObject.Factory.newInstance();
		XmlCursor cursor = result.newCursor();
		QName responseQName = new QName(namespace, operationResponse);
		cursor.toNextToken();
		cursor.beginElement(responseQName);
		cursor.insertElementWithText(new QName(namespace, "statusCode"), "00");
		cursor.insertElementWithText(new QName(namespace, "status"), "SUCCESS");
		cursor.insertElementWithText(new QName(namespace, "statusMessage"), "Proceso exitoso");
		for (String numeroTarjeta : listadoTarjetas) {
			cursor.beginElement(new QName(namespace, "tarjetas"));
			cursor.insertElementWithText(new QName(namespace, "numeroTarjeta"), numeroTarjeta);
			cursor.insertElementWithText(new QName(namespace, "cuenta"), "12345");
			cursor.insertElementWithText(new QName(namespace, "tipoTarjeta"), "Titular");
			cursor.insertElementWithText(new QName(namespace, "nombreTH"), "JUAN PEREZ");
			cursor.insertElementWithText(new QName(namespace, "estado"), "Activa");
			cursor.insertElementWithText(new QName(namespace, "limiteCreditoLocal"), "000000310000");
			cursor.insertElementWithText(new QName(namespace, "limiteCreditoDolares"), "000000310000");
			cursor.insertElementWithText(new QName(namespace, "saldoLocal"), "000000150000");
			cursor.insertElementWithText(new QName(namespace, "saldoDolares"), "000000150000");
			cursor.insertElementWithText(new QName(namespace, "disponibleLocal"), "000000150000");
			cursor.insertElementWithText(new QName(namespace, "disponibleDolares"), "000000150000");
			cursor.insertElementWithText(new QName(namespace, "pagoMinimoLocal"), "000000003455");
			cursor.insertElementWithText(new QName(namespace, "pagoMinimoDolares"), "000000003455");
			cursor.insertElementWithText(new QName(namespace, "pagoMinimoVencidoLocal"), "000000000000");
			cursor.insertElementWithText(new QName(namespace, "pagoMinimoVencidoDolares"), "000000000000");
			cursor.insertElementWithText(new QName(namespace, "pagoContadoLocal"), "000000150000");
			cursor.insertElementWithText(new QName(namespace, "pagoContadoDolares"), "000000150000");
			cursor.insertElementWithText(new QName(namespace, "fechaPago"), "20220812");
			cursor.insertElementWithText(new QName(namespace, "fechaUltimoCorte"), "20220718");
			cursor.insertElementWithText(new QName(namespace, "saldoMonedero"), "0");
			cursor.insertElementWithText(new QName(namespace, "rombosAcumulados"), "0");
			cursor.insertElementWithText(new QName(namespace, "rombosDinero"), "0");
			cursor.insertElementWithText(new QName(namespace, "fondosReservados"), "0");
			cursor.toParent();
		}
		cursor.toParent();

		log.info("obtenerListadoTarjetas response = [" + result.toString() + "]");
		return result;
	}
}
