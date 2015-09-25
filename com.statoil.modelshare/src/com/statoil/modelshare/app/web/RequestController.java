package com.statoil.modelshare.app.web;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

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
public class RequestController {

	static Logger log = Logger.getLogger(RequestController.class.getName());

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
			Model downloadModel = getModelFromAssets(asset);
			model.put("from", user.getName());
			model.put("mailfrom", user.getEmail());
			model.put("to", downloadModel.getMail());
			model.put("asset", asset);
		} catch (Exception e) {
			return "error";
		}
		return "request";
	}

	public Model getModelFromAssets(String encodedPath) throws UnsupportedEncodingException {
		if (encodedPath != null) {
			Model metaInformation = modelrepository
					.getMetaInformation(Paths.get(URLDecoder.decode(encodedPath, "UTF-8")));
			return metaInformation;
		}
		return null;
	}

	@RequestMapping(value = "/request", method = RequestMethod.POST)
	public String actOnRequestForm(ModelMap modelMap, 
			@RequestParam(value = "asset", required = true) String asset,
			@RequestParam(value = "message", required = true) String message, 
			Principal principal,
			@RequestParam(value = "to", required = true) String mailTo, 
			@RequestParam(value = "mailfrom", required = true) String mailFrom) {

		if (isEmailWellformed(mailTo)) {
			try {
				Client user = modelrepository.getUser(principal.getName());
				sendEmail(message, mailTo, user, asset);
			} catch (MessagingException e) {
				String msg = "Error sending mail. Contact system responsible.";
				log.log(Level.SEVERE, msg, e);
				modelMap.addAttribute("error", msg);
				return "errorpage";
			}
			
		} else {
			String msg = "Missing well formed e-mail address";
			log.log(Level.SEVERE, msg);
			modelMap.addAttribute("error", msg);
			return "errorpage";
		}
		Model model = null;
		try {
			model = getModelFromAssets(asset);
		} catch (Exception e) {
			String msg = "Error getting model information..";
			log.log(Level.SEVERE, msg, e);
			modelMap.addAttribute("error", msg);
			return "errorpage";
		}
		return "redirect:showModel?item=" + model.getPath() + "&leaf=true";
	}

	private void sendEmail(String message, String mailTo, Client user, String asset) throws MessagingException {
		Properties properties = System.getProperties();
		properties.setProperty("mail.smtp.host", smtpConfig.getHost());
		properties.setProperty("mail.smtp.port", String.valueOf(smtpConfig.getPort()));
		Session session = Session.getDefaultInstance(properties);

		MimeMessage mimeMessage = new MimeMessage(session);
		mimeMessage.setFrom(new InternetAddress(user.getEmail()));
		mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(mailTo));
		mimeMessage.setSubject("Modelshare: Request for download of model "+ asset);
		mimeMessage.setText(message);
		Transport.send(mimeMessage);
	}

	private boolean isEmailWellformed(String mailTo) {
		if (mailTo.isEmpty() || !mailTo.contains("@")) {
			return false;
		}
		return true;
	}
}
