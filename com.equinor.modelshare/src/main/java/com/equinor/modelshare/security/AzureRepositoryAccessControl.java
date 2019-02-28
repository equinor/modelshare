package com.equinor.modelshare.security;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.oauth2.client.authentication.OAuth2LoginAuthenticationToken;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.stereotype.Component;

import com.equinor.modelshare.Account;
import com.equinor.modelshare.Group;
import com.equinor.modelshare.ModelshareFactory;
import com.equinor.modelshare.User;
import com.equinor.modelshare.repository.ModelRepository;

import net.minidev.json.JSONArray;

/**
 * This type handles both the 
 * @author Torkild U. Resheim, Itema AS
 */
@Component
@Profile(value = { "Azure" })
public class AzureRepositoryAccessControl extends RepositoryAccessControl {
	
	static Logger log = LoggerFactory.getLogger(AzureRepositoryAccessControl.class.getName());
	
	private List<Account> accounts = new ArrayList<>();

	@Autowired
	private ModelRepository modelrepository;
	
	/**
	 * Handles that a user has logged in. When this happens we register the
	 * user locally and maps access to one of the available user groups.
	 */
	
	@Component
	public class LoginSuccessService implements ApplicationListener<AuthenticationSuccessEvent>{

	    @Override
	    public void onApplicationEvent(AuthenticationSuccessEvent event) {
	        log.debug("You have been logged in successfully.");
	        
	        OAuth2LoginAuthenticationToken authentication = (OAuth2LoginAuthenticationToken) event.getAuthentication();
	        DefaultOidcUser p = (DefaultOidcUser) authentication.getPrincipal();
	        
	        String fullName = p.getFullName();
	        
	        // get the e-mail address
	        String id = (String) p.getAttributes().get("upn");
	        // make sure we have the user registered locally
			if (modelrepository.getUser(id) == null) {
				User user = createUser(id, fullName);
				// get the group memberships of the user
				JSONArray groups = (JSONArray) p.getAttributes().get("groups");
				groups.forEach(g -> {
					Optional<Group> f = modelrepository.getGroups()
							.stream()
							.filter(mg -> mg.getExternalIdentifier().equals((String)g)).findFirst();
					if (f.isPresent()) {
						user.setGroup(f.get());
						log.info(String.format("User \"%1$s\" joins group \"%2$s\"", user.getName(), f.get().getName()));
					}
				});
			}
			
			
	    }
	}
	
	@Autowired
	public AzureRepositoryAccessControl(
			@Value("${repository.root}")Path root,
			@Value("${modelshare.azure.activedirectory.groups}")String groups) {
		super(root);
		// Create group instances and map these to the Azure AD
		String[] group = groups.split(",");		
		log.info("Azure AD group mappings:");
		for (String string : group) {
			String[] split = string.split(":");
			Group g = ModelshareFactory.eINSTANCE.createGroup();
			g.setIdentifier(split[0]);
			g.setName(split[1]);
			g.setExternalIdentifier(split[2]);
			getAuthorizedAccounts().add(g);
			log.info(String.format("Created group %1$s", g.getIdentifier()));
		}
	}
		
	public List<Account> getAuthorizedAccounts() {
		return accounts;
	}
	
	/**
	 * 
	 * @param identifier
	 * @param name
	 * @return
	 */
	public User createUser(String identifier, String name){
		User newUser = ModelshareFactory.eINSTANCE.createUser();
		newUser.setIdentifier(identifier);
		newUser.setName(name);
		newUser.setEmail(identifier);
		newUser.setOrganisation("Equinor ASA");
		getAuthorizedAccounts().add(newUser);
		return newUser;
	}
	
	public void setPassword(String id, String hash) {
	}
	
	/**
	 * Writes the accounts file.
	 */
	public void writeAccounts(){
		// don't need to write anything here
	}

	public void deleteUser(String identifier) {
	}

	@Override
	public User createUser(String identifier, String name, String identifier2, String organisation, String group,
			String password) {
		return null;
	}
}
