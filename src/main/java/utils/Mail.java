package utils;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;

public class Mail {

	// Atributos privados
	private final static String USERNAME = System.getenv("SERVER_MAIL_USER");
	private final static String PASSWORD = System.getenv("SERVER_MAIL_PASS");

	/**
	 * Envía un mensaje de confirmación de registro del usuario al usuario con
	 * el correo @param emailUser Devuelve true si tuvo éxito. False en caso
	 * contrario.
	 * 
	 * @version 1.0
	 */
	public static boolean sendRegistrationMail(String emailUser) {
		// Construye el asunto
		String subject = "Fooding App registration validation";

		// Construye el mensaje
		String text = "";
		// [FALTA POR HACER] Cuerpo del mensaje
		// [FALTA POR HACER] Link único de confirmación

		// Envía el mensaje
		return sendMail(emailUser,subject,text);
	}

	/**
	 * Crea un mail con el mensaje @param messageStr, el asunto @param subject
	 * para enviarselo al destinatario @param to Devuelve true si tuvo éxito.
	 * False en el caso contrario.
	 * 
	 * @version 1.0
	 */
	public static boolean sendMail(String to, String subject, String messageStr) {

		// Configura las properties para mandar el mensaje
		Properties props = new Properties();
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(USERNAME, PASSWORD);
			}
		});

		try {
			// Construye el mensaje
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(USERNAME));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
			message.setSubject(subject);
			message.setText(messageStr);

			// Envia el mensaje
			Transport.send(message);
			return true;

		} catch (MessagingException e) {
			return false;
		} catch(Exception e){
			return false;
		}
	}
}