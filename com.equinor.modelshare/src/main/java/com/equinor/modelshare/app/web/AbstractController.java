package com.equinor.modelshare.app.web;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
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
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.equinor.modelshare.Folder;
import com.equinor.modelshare.User;
import com.equinor.modelshare.app.MailConfiguration.SMTPConfiguration;
import com.equinor.modelshare.app.service.AssetProxy;
import com.equinor.modelshare.repository.ModelRepository;

/**
 * @author Torkild U. Resheim, Itema AS
 */
public abstract class AbstractController {

	@Autowired
	private SMTPConfiguration smtpConfig;
	
	@ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleResourceNotFoundException() {
        return "404";
    }

	@Autowired
	private ModelRepository modelrepository;

	protected List<AssetProxy> getRootItems(User user) {
		AssetProxy root = new AssetProxy(null, modelrepository.getRoot(user));
		return root.getChildren();
	}

	protected List<AssetProxy> getBreadCrumb(AssetProxy asset) {
		ArrayList<AssetProxy> crumbs = new ArrayList<AssetProxy>();
		AssetProxy parent = asset;
		while (parent != null && !parent.getRelativePath().equals("/")) {
			if (!parent.isLeaf()){
				crumbs.add(0, parent);
			}
			parent = parent.getParent();
		}
		return crumbs;
	}
	/**
	 * Returns an asset for the given path and user. If the user does not have
	 * access to the asset or the asset does not exist; and
	 * {@link ResourceNotFoundException} will be thrown.
	 * 
	 * @param user
	 *            the user to get the asset for
	 * @param path
	 *            the relative path to the asset
	 * @return the asset proxy
	 */
	protected AssetProxy getAssetAtPath(User user, String path) {
		Path p = Paths.get(path);
		if (p.isAbsolute()){
			throw new IllegalArgumentException("The path is not relative: "+path);
		}
		Folder root = modelrepository.getRoot(user);
		AssetProxy rootProxy = new AssetProxy(null, root);
		List<AssetProxy> list = rootProxy
				.stream()
				.filter(m -> path.equals(m.getRelativePath()))
				.collect(Collectors.toList());
		if (list.isEmpty()) {
			throw new ResourceNotFoundException("The asset \""+path+"\" is not available.");
		} else {
			return list.get(0);
		}
	}

	protected void sendEmail(String subject, String message, String mailTo, String mailFrom) throws MessagingException {
		Properties properties = System.getProperties();
		properties.setProperty("mail.smtp.host", smtpConfig.getHost());
		properties.setProperty("mail.smtp.port", String.valueOf(smtpConfig.getPort()));
		Session session = Session.getDefaultInstance(properties);
	
		MimeMessage mimeMessage = new MimeMessage(session);
		mimeMessage.setFrom(new InternetAddress(mailFrom));
		mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(mailTo));
		mimeMessage.setSubject(subject);
		mimeMessage.setSentDate(new Date());
		
		Multipart multipart = new MimeMultipart();
		
		MimeBodyPart htmlPart = new MimeBodyPart();
		String htmlContent = "<html><body>"+message+"</body></html>";
		htmlPart.setContent(htmlContent, "text/html; charset=UTF-8");
		multipart.addBodyPart(htmlPart);
		
		mimeMessage.setContent(multipart);
		
		Transport.send(mimeMessage);
	}

	protected User addCommonItems(ModelMap map, Principal principal) {
		User user = getUser(principal);
		map.addAttribute("authenticated", user);
		map.addAttribute("topLevel", getRootItems(user));
		return user;
	}

	/**
	 * Obtains the user instance using information from the {@link Principal}
	 * instance. This method takes into account that the user may be logged in using
	 * OAuth2.
	 * 
	 * @param principal the logged in principal
	 * @return the user instance or <code>null</code>
	 */
	protected User getUser(Principal principal) {
		String id = principal.getName(); 
		// principal.getName() returns the full name of the user when using 
		// Azure AD, so we need to get the unique name from somewhere else
		if (principal instanceof OAuth2AuthenticationToken) {
			DefaultOidcUser u = (DefaultOidcUser) ((OAuth2AuthenticationToken)principal).getPrincipal();
			id = (String) u.getAttributes().get("unique_name");
		}
		return modelrepository.getUser(id);		
	}

	
}
