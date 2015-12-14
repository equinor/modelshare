package com.statoil.modelshare.app.web;

import java.io.IOException;
import java.net.URLDecoder;
import java.nio.file.AccessDeniedException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.Arrays;
import java.util.Date;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
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
import com.statoil.modelshare.app.service.AssetProxy;
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
	public String manageAccessView(ModelMap map,
			Principal principal,
			@RequestParam("path") String p){
		try {
		
			map.addAttribute("path", p);
						
			Path path = Paths.get(URLDecoder.decode(p, "UTF-8"));
			Account owner = modelrepository.getUser(principal.getName());
						
			AssetProxy n = getAssetAtPath((User)owner, p);
			Map<Account, Rights> accounts = getAccountsWithoutAccess((User)owner, path);
			Map<Account, Rights> groups = getGroupsWithExplicitAccess((User)owner, path);
			Map<Account, Rights> users = getUsersWithExplicitAccess((User)owner, path);
					
			map.addAttribute("topLevel", getRootNodes(n));
			map.addAttribute("accounts", accounts);
			map.addAttribute("groups", groups);
			map.addAttribute("users", users);
			
		} catch (AccessDeniedException ioe) {
			String msg = "You don't have access to this model!";
			log.log(Level.SEVERE, msg, ioe);
			map.addAttribute("error", msg);
			return "manageaccess";	
			
		}catch(IOException ioe){
			String msg = "File system error!";
			log.log(Level.SEVERE, msg, ioe);
			map.addAttribute("error", msg);
			return "manageaccess";
		}
		return "manageaccess";
	}
	@RequestMapping(value = "/saveAccess", method = RequestMethod.POST)
	public String saveAccess(ModelMap map,
			@RequestParam("path") String p,
			@RequestParam(value = "access", required = false) String[] access,
			@RequestParam(value = "accounts", required = false) String[] accounts,
			Principal principal) {
			
		try {
			Path path = Paths.get(URLDecoder.decode(p, "UTF-8"));
			User user =  modelrepository.getUser(principal.getName());
			
			// get all users with access
			Map<Account, EnumSet<Access>> existingRights = modelrepository.getRights(user, path);
			Map<Account, EnumSet<Access>> postedRights = new HashMap<>();
									
			// determine what has changed
			if (access != null) {
				int a = 0;
				for (int i = 0; i<access.length; i+=3){
				    Account account = getAccount(accounts[a++]);
					EnumSet<Access> rights = Rights.getRights(Arrays.copyOfRange(access, i, i+3));
					postedRights.put(account, rights);
				}
			}
			
			// determine deleted rights
			List<Account> deleted = existingRights.keySet()
					.stream()
					.filter(a -> !postedRights.keySet().contains(a))
					.collect(Collectors.toList());

			// when an account is deleted from the rights list it basically 
			// means inherit everything
			for (Account account : deleted) {
				postedRights.put(account,EnumSet.of(Access.INHERIT_VIEW, Access.INHERIT_READ, Access.INHERIT_WRITE));
			}

			// modify rights
			for(Account key: postedRights.keySet()){
				modelrepository.modifyRights(user, key, path, postedRights.get(key));
			}				
		} catch (AccessDeniedException ioe) {
			String msg = "You don't have access to this model!";
			log.log(Level.SEVERE, msg, ioe);
			map.addAttribute("error", msg);
			return "manageaccess";	
			
		}catch(IOException ioe){
			String msg = "File system error!";
			log.log(Level.SEVERE, msg, ioe);
			map.addAttribute("error", msg);
			return "manageaccess";
		}				
		
		return "redirect:/manageaccess?path=" + p;
	}
	
	private Account getAccount(String id){
		Account account = modelrepository.getUser(id);
		if(account==null)
			account = modelrepository.getGroup(id);
		return account;
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
	 * It has proved difficult to get the checkbox control to handle +r/-r/?r
	 * properly, so this type is used to convert between the Modelshare 
	 * internal format and the Bootstrap Checkbox X control format.
	 */
	public static class Rights {
		
		public String view = "";
		public String read = "";
		public String write = "";
		
		public static EnumSet<Access> getRights(String[] in ){
			EnumSet<Access> rights = EnumSet.noneOf(Access.class);
			for (int i=0;i<3;i++){
				String v = in[i];
				switch (v) {
				case "1":
					if (i==0) rights.add(Access.VIEW);
					if (i==1) rights.add(Access.READ);
					if (i==2) rights.add(Access.WRITE);
					break;
				case "0":
					if (i==0) rights.add(Access.NO_VIEW);
					if (i==1) rights.add(Access.NO_READ);
					if (i==2) rights.add(Access.NO_WRITE);
					break;
				case "":
					if (i==0) rights.add(Access.INHERIT_VIEW);
					if (i==1) rights.add(Access.INHERIT_READ);
					if (i==2) rights.add(Access.INHERIT_WRITE);
					break;
				default:
					break;
				}
			}
			return rights;
		}
		
		public Rights(EnumSet<Access> set){
			for (Access access : set) {
				switch (access) {
				case VIEW:
					view = "1";
					break;
				case NO_VIEW:
					view = "0";
					break;
				case INHERIT_VIEW:
					view = "";
					break;
				case READ:
					read = "1";
					break;
				case NO_READ:
					read = "0";
					break;
				case INHERIT_READ:
					read = null;
					break;
				case WRITE:
					write = "1";
					break;
				case NO_WRITE:
					write = "0";
					break;
				case INHERIT_WRITE:
					write = "";
					break;

				default:
					break;
				}				
			}
		}
		
	}
		
	/**
	 * Returns a map of all groups with explicit access to the asset at the given path. 
	 */
	private Map<Account, Rights> getGroupsWithExplicitAccess(User user, Path path) throws AccessDeniedException, IOException{
		return modelrepository
				.getRights(user, path).entrySet()
				.stream()
				.filter(entry -> entry.getKey() instanceof Group)
				.collect(Collectors.toMap(entry -> entry.getKey(), entry -> new Rights(entry.getValue())));
	}

	/**
	 * Returns a map of all users with explicit access to the asset at the given path. 
	 */
	private Map<Account, Rights> getUsersWithExplicitAccess(User user, Path path) throws AccessDeniedException, IOException{
		return modelrepository
				.getRights(user, path).entrySet()
				.stream()
				.filter(entry -> entry.getKey() instanceof User)
				.collect(Collectors.toMap(entry -> entry.getKey(), entry -> new Rights(entry.getValue())));
	}
 	
	/**
	 * Returns a list of all accounts with no explicit access to the asset at the given path. 
	 */
	private Map<Account, Rights> getAccountsWithoutAccess(User user, Path path) throws AccessDeniedException, IOException{
		Set<Account> explicitAccounts = modelrepository.getRights(user, path).keySet();
		return modelrepository.getAccounts()
				.stream()
				.filter(u -> !explicitAccounts.contains(u))
				.collect(Collectors.toMap(u -> u, u -> new Rights(EnumSet.of(Access.VIEW))));		
	}
}
