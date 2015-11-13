package com.statoil.modelshare.app.web;

import java.io.IOException;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.Date;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.statoil.modelshare.Client;
import com.statoil.modelshare.app.config.MailConfig.SMTPConfiguration;
import com.statoil.modelshare.controller.ModelRepository;

@Controller
public class GrantAccessController {
	
	static Logger log = Logger.getLogger(GrantAccessController.class.getName());
	
	@Autowired
	private ModelRepository modelrepository;
	
	@Autowired
	private SMTPConfiguration smtpConfig;
	
	@RequestMapping(value = "/grantaccess", method = RequestMethod.GET)
	public String prepareAccesPage(ModelMap model, HttpServletRequest request) {
		String queryString = request.getQueryString();
		String filename = queryString.substring(queryString.lastIndexOf("/")+1, queryString.indexOf("&"));
		String user = queryString.substring(queryString.indexOf("user=")+5, queryString.lastIndexOf("&"));
		model.addAttribute("querystring", queryString);
		model.addAttribute("filename", filename);
		model.addAttribute("user", user);
		return "grantaccess";
	}
	
	@RequestMapping(value = "/grantaccess", method = RequestMethod.POST)
	public String setAccess(ModelMap model,
			@RequestParam("filename") String filename,
			@RequestParam("user") String user,
			@RequestParam("querystring") String query,
			Principal principal) {
		
		Client client = modelrepository.getUser(user);
		String item = query.substring(query.indexOf("repository/") + 11, query.indexOf("&"));
		try {
			if (!modelrepository.hasDownloadAccess(client, Paths.get(item))) {
				modelrepository.setDownloadRights(client, Paths.get(item));
				
			} else {
				String msg = "User "+ user + " already has access to download model named " + filename;
				log.log(Level.INFO, msg);
				model.addAttribute("error", msg);
				return "errorpage";
			}
			
			// Send mail to requesting user that download now can be done
			Client requestUser = modelrepository.getUser(principal.getName());
			if (modelrepository.isValidEmailAddress(requestUser.getEmail())) {
				try {
					sendEmail("You are now granted access to download model " + filename, requestUser.getEmail(), requestUser);
				} catch (MessagingException e) {
					String msg = "Error sending mail. Contact system responsible.";
					log.log(Level.SEVERE, msg, e);
					model.addAttribute("error", msg);
					return "errorpage";
				}
				
			} else {
				String msg = "Missing well formed e-mail address";
				log.log(Level.SEVERE, msg);
				model.addAttribute("error", msg);
				return "errorpage";
			}
			
		} catch (IOException e) {
			String msg = "Error found when checking or setting access rights";
			log.log(Level.SEVERE, msg, e);
			model.addAttribute("error", msg);
			return "errorpage";
		}
		
		return "redirect:archive?item";
	}
	
	private void sendEmail(String message, String mailTo, Client user) throws MessagingException {
		Properties properties = System.getProperties();
		properties.setProperty("mail.smtp.host", smtpConfig.getHost());
		properties.setProperty("mail.smtp.port", String.valueOf(smtpConfig.getPort()));
		Session session = Session.getDefaultInstance(properties);

		MimeMessage mimeMessage = new MimeMessage(session);
		mimeMessage.setFrom(new InternetAddress(user.getEmail()));
		mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(mailTo));
		mimeMessage.setSubject("Access granted");
		mimeMessage.setSentDate(new Date());
		
		Multipart multipart = new MimeMultipart();
		
		MimeBodyPart htmlPart = new MimeBodyPart();
		String htmlContent = "<html><body><h3>"+message+"</h3></body></html>";
		htmlPart.setContent(htmlContent, "text/html; charset=UTF-8");
		multipart.addBodyPart(htmlPart);
		
		mimeMessage.setContent(multipart);
		
		Transport.send(mimeMessage);
	}
 	
}
