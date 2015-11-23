/*
 * Security.java v1.0 14/11/2015
 */
package utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Security {

	/**
	 * Clase para encriptar passwords
	 * 
	 * @version 1.0 - stub method
	 * @date 14/11/2015
	 */

	/**
	 * @param pw:
	 *            password a encriptar
	 * @return una cadena de caracteres que representa la password (encriptada)
	 * @throws NoSuchAlgorithmException si el algoritmo empleado para el encriptado no existe
	 */
	public static String encrypt_password(String pw) throws NoSuchAlgorithmException {

		MessageDigest md = MessageDigest.getInstance("SHA-256");
		md.update(pw.getBytes());

		byte byteData[] = md.digest();

		// Convertir los bytes a hexadecimal
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < byteData.length; i++) {
			sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
		}

		return sb.toString();
	}
}
