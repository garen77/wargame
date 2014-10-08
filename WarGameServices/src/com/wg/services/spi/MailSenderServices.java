package com.wg.services.spi;

import java.util.Properties;
import java.util.ResourceBundle;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.stereotype.Service;

import com.wg.criteria.MailSenderCriteria;
import com.wg.result.MailSenderResult;
import com.wg.services.api.IMailSenderServices;

@Service(value=IMailSenderServices.SERVICE_NAME)
public class MailSenderServices extends GenericService implements IMailSenderServices {

	public static final String SMTP_HOST = "mail.smtp.host";
	
	@Override
	public MailSenderResult sendMail(MailSenderCriteria criteria) {
		
		MailSenderResult result = new MailSenderResult();
		
		Properties props = null;
		ResourceBundle rb = getResourceBundle(GENERIC_RESOURCES_BUNDLE);
		if(criteria == null || criteria.getToAddr() == null || criteria.getToAddr().trim().equals(""))
		{
			
			StringBuilder keyMess = new StringBuilder(IMailSenderServices.SERVICE_NAME).append(".err.")
					.append("invalidMailCriteria");
			result.getMessages().add(rb.getString(keyMess.toString()));
		}
		else
		{
			final String userName = "crescenzo.garofalo@gmail.com";
			final String pwd = "iuytrewq";
			props = System.getProperties();
	        props.put("mail.smtp.auth", "true");
	        props.put("mail.smtp.starttls.enable", "true");	        	       
			props.setProperty(SMTP_HOST, "smtp.gmail.com");
			props.put("mail.smtp.port", "587");
			Session session = Session.getInstance(props,new Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication()
					{
						return new PasswordAuthentication(userName,pwd);
					}
				});
			try
			{
				MimeMessage message = new MimeMessage(session);
				message.setFrom(new InternetAddress(criteria.getFromAddr()));
				message.addRecipient(Message.RecipientType.TO, new InternetAddress(criteria.getToAddr()));
				StringBuilder keyMess = new StringBuilder(IMailSenderServices.SERVICE_NAME).append(".")
					.append("registrationSubject");
				
				message.setSubject(rb.getString(keyMess.toString()));
			
				keyMess = new StringBuilder(IMailSenderServices.SERVICE_NAME).append(".")
						.append("registrationText");
				
				message.setText(rb.getString(keyMess.toString()));
				
				Transport.send(message);
			}
			catch(MessagingException me)
			{
				StringBuilder keyMess = new StringBuilder(IMailSenderServices.SERVICE_NAME).append(".err.")
						.append("errorSending");
				result.getMessages().add(rb.getString(keyMess.toString()));
			}
		}
		
			
		return result;
	}

}
