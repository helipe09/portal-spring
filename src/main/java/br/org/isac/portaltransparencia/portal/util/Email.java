package br.org.isac.portaltransparencia.portal.util;

import java.util.Properties;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Email {
	
	private static Properties parametrosConexao() {
		Properties props = new Properties();
		/** Parâmetros de conexão com servidor Gmail */
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class",
				"javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");

		return props;
	}

	private static Session getSession(Properties props) {
		Session session = Session.getDefaultInstance(props,new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication(){
				return new PasswordAuthentication(USER_CONNECTION,PSW_CONNECTION);
			}
		});

		return session;
	}


	public static void enviar(String titulo, String mensagem, String email) {

		Properties props = parametrosConexao(); 

		Session session = getSession(props);

		/** Ativa Debug para sessão */
		//session.setDebug(true);

		try {

			Message message = new MimeMessage(session);
			//Remetente
			message.setFrom(new InternetAddress(USER_CONNECTION));

			//Destinatário(s)
			Address[] toUser = InternetAddress.parse(email);

			message.setRecipients(Message.RecipientType.TO, toUser);
			message.setSubject(titulo);//Assunto
			//message.setText(mensagem);
			
			message.setContent(mensagem,"text/html");
			
			/**Método para enviar a mensagem criada*/
			Transport.send(message);

			//System.out.println("Feito!!!");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
	
	private static final String USER_CONNECTION = "contato@isac.org.br";
	private static final String PSW_CONNECTION = "@contatoisac!";

}
