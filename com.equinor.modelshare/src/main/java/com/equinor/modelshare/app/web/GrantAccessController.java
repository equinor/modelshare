package com.equinor.modelshare.app.web;

import java.io.IOException;
import java.net.URLDecoder;
import java.nio.file.AccessDeniedException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.equinor.modelshare.Access;
import com.equinor.modelshare.Account;
import com.equinor.modelshare.Group;
import com.equinor.modelshare.User;
import com.equinor.modelshare.repository.ModelRepository;

@Controller
public class GrantAccessController extends AbstractController {
	
	static Logger log = Logger.getLogger(GrantAccessController.class.getName());
	
	@Autowired
	private ModelRepository modelrepository;
		
	@RequestMapping(value = "/grantaccess", method = RequestMethod.GET)
	public String prepareAccesPage(ModelMap map, Principal principal,
			@RequestParam("asset") String asset,
			@RequestParam("user") String userId) {

		addCommonItems(map, principal);
		map.addAttribute("asset", asset);
		map.addAttribute("user", userId);
		return "grantaccess";
	}
	
	@RequestMapping(value = "/grantaccess", method = RequestMethod.POST)
	public String setAccess(ModelMap map,	Principal principal,
			@RequestParam("asset") String asset,
			@RequestParam("user") String userId) {
		
		User owner = addCommonItems(map, principal);
		map.addAttribute("asset", asset);
		map.addAttribute("user", userId);

		User user = getUser(principal);

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
					sendEmail("Access granted", "You are now granted access to download model " + asset, requestUser.getEmail(), requestUser.getEmail());
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
			User owner = addCommonItems(map, principal);
						
			Map<Account, Rights> accounts = getAccountsWithoutAccess((User)owner, path);
			Map<Account, Rights> groups = getGroupsWithExplicitAccess((User)owner, path);
			Map<Account, Rights> users = getUsersWithExplicitAccess((User)owner, path);
					
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
	
	@RequestMapping(value = "/saveaccess", method = RequestMethod.POST)
	public String saveaccess(ModelMap map,
			Principal principal,
			@RequestParam("path") String p,
			@RequestParam(value = "access", required = false) String[] access,
			@RequestParam(value = "accounts", required = false) String[] accounts) {
			
		try {
			Path path = Paths.get(URLDecoder.decode(p, "UTF-8"));
			User user = addCommonItems(map, principal);
			
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
		return modelrepository.getAuthorizedAccounts()
				.stream()
				.filter(u -> !explicitAccounts.contains(u))
				.collect(Collectors.toMap(u -> u, u -> new Rights(EnumSet.of(Access.VIEW))));		
	}
	
	// -----------------------------------------------------------------------
	// User control
	// Manage user identifiers, organizations, local user etc.
	// -----------------------------------------------------------------------
	
	@RequestMapping(value = "/manage-users", method = RequestMethod.GET)
	public String manageUsers(ModelMap map, Principal principal,
			@RequestParam(value = "delete", required = false) String identifier){
						
		User user = addCommonItems(map, principal);
		if (identifier != null) {
			try {
				modelrepository.deleteUser(user, identifier);
			} catch (AccessDeniedException e) {
				String msg = "You don't have access to this view!";
				log.log(Level.SEVERE, msg,e);
				map.addAttribute("error", msg);
				return "manage-users";
			}
		}
		map.addAttribute("users", modelrepository.getAuthorizedUsers());
		
		return "manage-users";
	}
	
	@RequestMapping(value = "/add-user", method = RequestMethod.GET)
	public String addUserDialog(ModelMap map, Principal principal){
						
		User user = addCommonItems(map, principal);
		map.addAttribute("groups",modelrepository.getGroups());
		if (!user.getGroup().getIdentifier().equals("supervisor")){
			String msg = "You don't have access to this view!";
			log.log(Level.SEVERE, msg);
			map.addAttribute("error", msg);
			return "add-user";
		}		
		return "add-user";
	}
	
	@RequestMapping(value = "/add-user", method = RequestMethod.POST)
	public String addUser(ModelMap map, Principal principal, HttpServletRequest request,
		@RequestParam(value = "identifier", required = true) String identifier,
		@RequestParam(value = "name", required = true) String name,
		@RequestParam(value = "group", required = true) String group,
		@RequestParam(value = "password", required = false) String password,
		@RequestParam(value = "organisation", required = true) String organisation) {
						
		User user = addCommonItems(map, principal);
		
		if (modelrepository.getUser(identifier)!=null){
			map.addAttribute("error", String.format("A user account with the specified identifier (%1$s) already exists.",identifier));
			return "add-user";
		}
		// create the new user and display the result
		try {
			if (password != null) {
				BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
				String passwordHash = passwordEncoder.encode(password);
				modelrepository.createUser(user, identifier, name, organisation, group, passwordHash);
			} else {
				User newUser = modelrepository.createUser(user, identifier, name, organisation, group, null);
				String key = newUser.getResettoken().getKey();
				String url = request.getRequestURL().toString();
				url = url.substring(0, url.lastIndexOf("/")) + "/change-password?token=" + key + "&email=" + identifier;
				try {
					String msg = String.format("<p>A Modelshare account has been created for you. Please follow this "
							+ "<a href=\"%1$s\">link</a> within 24 hours in order to set a password.</p>", url);
					sendEmail("Modelshare account created", msg, identifier, identifier);
					map.addAttribute("info",
							String.format(
									"Account created. An e-mail has been sent to <a href=\"mailto:%1$s\">%1$s</a> with instructions on how to set a password. This must be done within 24 hours.",
									identifier));
				} catch (MessagingException e) {
					map.addAttribute("error",
							"An e-mail with reset instructions could not be sent. Please contact the system administrator.");
					return "add-user";
				}
			}
		} catch (AccessDeniedException e) {
			String msg = "You don't have access to this view!";
			log.log(Level.SEVERE, msg,e);
			map.addAttribute("error", msg);
			return "add-user";
		}
		map.addAttribute("users", modelrepository.getAuthorizedUsers());
		return "manage-users";
	}

	@RequestMapping(value = "/manage-users", method = RequestMethod.POST, produces = "application/json")
	public String editUser(ModelMap map, Principal principal,
			@RequestParam(value = "pk", required = true) String pk,
			@RequestParam(value = "name", required = true) String name,
			@RequestParam(value = "value", required = true) String value) {
		
		User user = getUser(principal);

		String[] split = name.split("_");
		String field = split[1];
		User editedUser = modelrepository.getUser(pk);
		// If the user group is not supervisor we fail silently
		if (!user.getGroup().getIdentifier().equals("supervisor")){
			return "manage-users";
		}
		switch (field) {
		case "identifier":
			editedUser.setEmail(value);
			editedUser.setIdentifier(value);
			break;
		case "name":
			editedUser.setName(value);
			break;
		case "group":
			Group group = modelrepository.getGroup(value);
			editedUser.setGroup(group);
			break;
		case "organisation":
			editedUser.setOrganisation(value);
			break;
		case "localuser":
			editedUser.setLocalUser(value);
			break;
		default:
			break;
		}
		modelrepository.updateAccountsOnFile();
		return "manage-users";
	}
	
}
