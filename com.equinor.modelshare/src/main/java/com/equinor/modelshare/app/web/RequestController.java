package com.equinor.modelshare.app.web;

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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.equinor.modelshare.User;
import com.equinor.modelshare.app.MailConfiguration.SMTPConfiguration;
import com.equinor.modelshare.repository.ModelRepository;
import com.equinor.modelshare.Model;

/**
 * @author Torkild U. Resheim, Itema AS
 */
@Controller
public class RequestController extends AbstractController {

	static Logger log = LoggerFactory.getLogger(RequestController.class);

	@Autowired
	private ModelRepository modelrepository;

	@Autowired
	private SMTPConfiguration smtpConfig;

	/**
	 * Show form for requesting access to a model.
	 */
	@RequestMapping(value = "/request", method = RequestMethod.GET)
	public String showRequestForm(ModelMap map, 
			@RequestParam(value = "asset", required = true) String asset,
			Principal principal) {
		try {
			User user = modelrepository.getUser(principal.getName());
			Model model = modelrepository.getModel(user,Paths.get(URLDecoder.decode(asset, "UTF-8")));
			map.put("from", user.getName());
			map.put("mailfrom", user.getEmail());
			map.put("to", model.getMail());
			map.put("asset", asset);
		} catch (Exception e) {
			String msg = "Error getting model from repository";
			log.error(msg, e);
			map.addAttribute("error", msg);
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
				User user = modelrepository.getUser(principal.getName());
				String url = makeUrl(request, asset, user);
				sendEmail(message, mailTo, user, asset, url);
			} catch (MessagingException e) {
				String msg = "Error sending mail. Contact system administrator.";
				log.error(msg, e);
				modelMap.addAttribute("error", msg);
				return "request";
			} catch (UnsupportedEncodingException ue) {
				String msg = "Error creating new URL when sending mail. Contact system administrator.";
				log.error(msg, ue);
				modelMap.addAttribute("error", msg);
				return "request";
			}
			
		} else {
			String msg = "Missing well formed e-mail address";
			log.error(msg);
			modelMap.addAttribute("error", msg);
			return "request";
		}
		Model model = null;
		try {
			User user = modelrepository.getUser(principal.getName());
			model = modelrepository.getModel(user,Paths.get(URLDecoder.decode(asset, "UTF-8")));
		} catch (Exception e) {
			String msg = "Error getting model information..";
			log.error(msg, e);
			modelMap.addAttribute("request", msg);
			return "error";
		}
		return "redirect:view?item=" + model.getPath() + "&leaf=true";
	}
	
	private String makeUrl(HttpServletRequest request, String asset, User user) {
	    String url = request.getRequestURL().toString();
	    url = url.substring(0, url.lastIndexOf("/")) + "/grantaccess";
		return url + "?asset=" + asset + "&user=" + user.getEmail() + "&grant=true";
	}

	private void sendEmail(String message, String mailTo, User user, String asset, String url) throws MessagingException, UnsupportedEncodingException {
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
