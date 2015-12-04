package com.statoil.modelshare.app.web;

import java.io.IOException;
import java.net.URLDecoder;
import java.nio.file.AccessDeniedException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.statoil.modelshare.Access;
import com.statoil.modelshare.Account;
import com.statoil.modelshare.Group;
import com.statoil.modelshare.User;
import com.statoil.modelshare.app.config.MailConfig.SMTPConfiguration;
import com.statoil.modelshare.controller.ModelRepository;

@Controller
public class GrantAccessController extends AbstractController {
	
	static Logger log = Logger.getLogger(GrantAccessController.class.getName());
	
	@Autowired
	private ModelRepository modelrepository;
	
	@Autowired
	private SMTPConfiguration smtpConfig;
	
	@RequestMapping(value = "/grantaccess", method = RequestMethod.GET)
	public String prepareAccesPage(ModelMap model,
			@RequestParam("asset") String asset,
			@RequestParam("user") String userId) {
		model.addAttribute("asset", asset);
		model.addAttribute("user", userId);
		return "grantaccess";
	}
	
	@RequestMapping(value = "/grantaccess", method = RequestMethod.POST)
	public String setAccess(ModelMap map,	Principal principal,
			@RequestParam("asset") String asset,
			@RequestParam("user") String userId) {
		
		map.addAttribute("asset", asset);
		map.addAttribute("user", userId);

		User owner = modelrepository.getUser(principal.getName());
		User user = modelrepository.getUser(userId);
		try {
			Path path = Paths.get(URLDecoder.decode(asset, "UTF-8"));
			if (!modelrepository.hasDownloadAccess(user, path)) {
				modelrepository.setDownloadRights(owner, user, path);
				
			} else {
				String msg = "User "+ userId + " already has access to download model named " + asset;
				log.log(Level.INFO, msg);
				map.addAttribute("error", user.getName()+" already has download access to the model");
				return "grantaccess";
			}
			
			// Send mail to requesting user that download now can be done
			User requestUser = modelrepository.getUser(principal.getName());
			if (modelrepository.isValidEmailAddress(requestUser.getEmail())) {
				try {
					sendEmail("You are now granted access to download model " + asset, requestUser.getEmail(), requestUser);
				} catch (MessagingException e) {
					String msg = "Error sending mail. Contact system responsible.";
					log.log(Level.SEVERE, msg, e);
					map.addAttribute("error", msg);
					return "grantaccess";
				}
				
			} else {
				String msg = "Missing well formed e-mail address";
				log.log(Level.SEVERE, msg);
				map.addAttribute("error", msg);
				return "grantaccess";
			}
			
		} catch (AccessDeniedException ioe) {
			String msg = "You don't have access to this model!";
			log.log(Level.SEVERE, msg, ioe);
			map.addAttribute("error", msg);
			return "grantaccess";		
		} catch (IOException ioe) {
			String msg = "File system error!";
			log.log(Level.SEVERE, msg, ioe);
			map.addAttribute("error", msg);
			return "grantaccess";
		}		
		map.addAttribute("success", user.getName()+ " now has access to download the model.");		
		return "grantaccess";
	}
	
	@RequestMapping(value = "/manageaccess", method = RequestMethod.GET)
	public String manageAccessView(ModelMap model,
			@RequestParam("path") String path) {
		
		model.addAttribute("path", path);
		
		return "manageaccess";
	}
	@RequestMapping(value = "/saveAccess", method = RequestMethod.POST)
	public String saveAccess(ModelMap model,
			@RequestParam("path") String path,
			@RequestParam(value = "access", required = false) String[] access) {
		
		//model.addAttribute("path", path);
		
		return "redirect:/manageaccess?path=" + path;
	}
	
	private void sendEmail(String message, String mailTo, User user) throws MessagingException {
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
		
	/**
	 * Returns a map of all groups with explicit access to the asset at the given path. 
	 */
	private Map<Account, EnumSet<Access>> getGroupsWithAccess(User user, Path path) throws AccessDeniedException, IOException{
		return modelrepository
				.getRights(user, path).entrySet()
				.stream()
				.filter(entry -> entry.getKey() instanceof Group)
				.collect(Collectors.toMap(entry -> entry.getKey(), entry -> entry.getValue()));
	}

	/**
	 * Returns a map of all users with explicit access to the asset at the given path. 
	 */
	private Map<Account, EnumSet<Access>> getUsersWithAccess(User user, Path path) throws AccessDeniedException, IOException{
		return modelrepository
				.getRights(user, path).entrySet()
				.stream()
				.filter(entry -> entry.getKey() instanceof User)
				.collect(Collectors.toMap(entry -> entry.getKey(), entry -> entry.getValue()));
	}
 	
	/**
	 * Returns a list of all accounts with no explicit access to the asset at the given path. 
	 */
	private List<Account> getAccountsWithoutAccess(User user, Path path) throws AccessDeniedException, IOException{
		Set<Account> explicitAccounts = modelrepository.getRights(user, path).keySet();
		return modelrepository.getAccounts()
				.stream()
				.filter(u -> !explicitAccounts.contains(u))
				.collect(Collectors.toList());		
	}
}
