package com.statoil.modelshare.app.web;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.file.Paths;
import java.security.Principal;
import java.text.MessageFormat;
import java.util.Date;
import java.util.Properties;

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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.statoil.modelshare.Client;
import com.statoil.modelshare.Model;
import com.statoil.modelshare.app.config.MailConfig.SMTPConfiguration;
import com.statoil.modelshare.controller.ModelRepository;

/**
 * @author Torkild U. Resheim, Itema AS
 */
@Controller
public class RequestController extends AbstractController {

	static Log log = LogFactory.getLog(RequestController.class);

	@Autowired
	private ModelRepository modelrepository;

	@Autowired
	private SMTPConfiguration smtpConfig;

	@RequestMapping(value = "/request", method = RequestMethod.GET)
	public String showRequestForm(ModelMap model, 
			@RequestParam(value = "asset", required = true) String asset,
			Principal principal) {
		try {
			Client user = modelrepository.getUser(principal.getName());
			Model currentModel = modelrepository.getModel(user,Paths.get(URLDecoder.decode(asset, "UTF-8")));
			model.put("from", user.getName());
			model.put("mailfrom", user.getEmail());
			model.put("to", currentModel.getMail());
			model.put("asset", asset);
		} catch (Exception e) {
			String msg = "Error getting model from repository";
			log.fatal(msg, e);
			model.addAttribute("error", msg);
			return "request";
		}
		return "request";
	}

	@RequestMapping(value = "/request", method = RequestMethod.POST)
	public String actOnRequestForm(ModelMap modelMap, 
			@RequestParam(value = "asset", required = true) String asset,
			@RequestParam(value = "message", required = true) String message, 
			Principal principal,
			@RequestParam(value = "to", required = true) String mailTo, 
			@RequestParam(value = "mailfrom", required = true) String mailFrom, HttpServletRequest request) {

		if (modelrepository.isValidEmailAddress(mailTo)) {
			try {
				Client user = modelrepository.getUser(principal.getName());
				String url = makeUrl(request, asset, user);
				sendEmail(message, mailTo, user, asset, url);
			} catch (MessagingException e) {
				String msg = "Error sending mail. Contact system responsible.";
				log.fatal(msg, e);
				modelMap.addAttribute("error", msg);
				return "request";
			} catch (UnsupportedEncodingException ue) {
				String msg = "Error creating new URL when sending mail. Contact system responsible.";
				log.fatal(msg, ue);
				modelMap.addAttribute("error", msg);
				return "request";
			}
			
		} else {
			String msg = "Missing well formed e-mail address";
			log.fatal(msg);
			modelMap.addAttribute("error", msg);
			return "request";
		}
		Model model = null;
		try {
			Client user = modelrepository.getUser(principal.getName());
			model = modelrepository.getModel(user,Paths.get(URLDecoder.decode(asset, "UTF-8")));
		} catch (Exception e) {
			String msg = "Error getting model information..";
			log.fatal(msg, e);
			modelMap.addAttribute("request", msg);
			return "error";
		}
		return "redirect:showModel?item=" + model.getPath() + "&leaf=true";
	}
	
	private String makeUrl(HttpServletRequest request, String asset, Client user) {
	    String url = request.getRequestURL().toString();
	    url = url.substring(0, url.lastIndexOf("/")) + "/grantaccess";
		return url + "?asset=" + asset + "&user=" + user.getEmail() + "&grant=true";
	}

	private void sendEmail(String message, String mailTo, Client user, String asset, String url) throws MessagingException, UnsupportedEncodingException {
		Properties properties = System.getProperties();
		properties.setProperty("mail.smtp.host", smtpConfig.getHost());
		properties.setProperty("mail.smtp.port", String.valueOf(smtpConfig.getPort()));
		log.info("Sending e-mail using server at "+smtpConfig.getHost()+":"+smtpConfig.getPort());
		Session session = Session.getDefaultInstance(properties);

		MimeMessage mimeMessage = new MimeMessage(session);
		mimeMessage.setFrom(new InternetAddress(user.getEmail()));
		mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(mailTo));
		mimeMessage.setSubject("Modelshare: Request for download of model");
		mimeMessage.setSentDate(new Date());
		
		Multipart multipart = new MimeMultipart();
		
		MimeBodyPart htmlPart = new MimeBodyPart();
		String htmlContent = MessageFormat.format(smtpConfig.getAccessRequestMailTemplate(), user.getName(), asset, message, url);
		
		htmlPart.setContent(htmlContent, "text/html; charset=UTF-8");
		multipart.addBodyPart(htmlPart);
		
		mimeMessage.setContent(multipart);
		
		Transport.send(mimeMessage);
	}

}
