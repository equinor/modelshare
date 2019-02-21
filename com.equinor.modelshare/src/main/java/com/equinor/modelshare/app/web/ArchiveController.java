package com.equinor.modelshare.app.web;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.file.AccessDeniedException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.text.MessageFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.mail.MessagingException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.equinor.modelshare.Folder;
import com.equinor.modelshare.Model;
import com.equinor.modelshare.ModelshareFactory;
import com.equinor.modelshare.User;
import com.equinor.modelshare.app.RepositoryConfiguration;
import com.equinor.modelshare.app.service.AssetProxy;
import com.equinor.modelshare.controller.ModelRepository;

@Controller
public class ArchiveController extends AbstractController {

	static Logger log = LoggerFactory.getLogger(AbstractController.class);

	@Autowired ModelRepository modelrepository;

	@Autowired
	private RepositoryConfiguration repositoryConfig;
	
	private static Logger downloadLog = LoggerFactory.getLogger("downloadLogger");

	/** Encryption key for the download URL's */
	private static final String KEY = "L+QeFnncyzU+aIfJJLOgfw==";
		
	/**
	 * Use to copy the model to a local directory. 
	 */
	@RequestMapping(value = "/copy", method = RequestMethod.GET)
	public String copyModel(ModelMap map, @RequestParam(value = "asset", required = true) String asset,
			HttpServletResponse response, Principal principal) {
		
		try {
			User user = addCommonItems(map, principal);
	
			map.addAttribute("downloadTerms", repositoryConfig.getDownloadTerms());
	
			map.addAttribute("client", user);
			map.addAttribute("viewOnly",hasViewOnlyAccess(asset, user));
			map.addAttribute("writeAccess",hasWriteAccess(user, Paths.get(asset)));
			map.addAttribute("activeMenuItem", asset);
			map.addAttribute("title", "Model archive");

			// common
			AssetProxy n = getAssetAtPath(user, asset);
			map.addAttribute("node", n);
			map.addAttribute("currentModel", n.getAsset());
			map.addAttribute("crumbs", getBreadCrumb(n));
	
			// do the actual copy
			Path rootPath = Paths.get(modelrepository.getRoot(user).getPath());
			Path path = Paths.get(asset);
			Path resolvedPath = rootPath.resolve(path);
			String localCopy = modelrepository.localCopy(user, resolvedPath);			
			map.addAttribute("success", localCopy);
		} catch (AccessDeniedException ioe) {
			String msg = "You don't have access to copying a model!";
			log.error(msg, ioe);
			map.addAttribute("error", msg);
			return "folder";		
		} catch (IOException ioe) {
			String msg = "File system error!";
			log.error(msg, ioe);
			map.addAttribute("error", msg);
			return "folder";
		}		
		return "content";
	}
	
	public static String encrypt(String plainText, SecretKey secretKey)
			throws Exception {
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		byte[] plainTextByte = plainText.getBytes();
		cipher.init(Cipher.ENCRYPT_MODE, secretKey,new IvParameterSpec(new byte[16]));
		byte[] encryptedByte = cipher.doFinal(plainTextByte);
		Base64.Encoder encoder = Base64.getEncoder();
		String encryptedText = encoder.encodeToString(encryptedByte);
		return encryptedText;
	}

	public static String decrypt(String encryptedText, SecretKey secretKey)
			throws Exception {
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		Base64.Decoder decoder = Base64.getDecoder();
		byte[] encryptedTextByte = decoder.decode(encryptedText);
		cipher.init(Cipher.DECRYPT_MODE, secretKey,new IvParameterSpec(new byte[16]));
		byte[] decryptedByte = cipher.doFinal(encryptedTextByte);
		String decryptedText = new String(decryptedByte);
		return decryptedText;
	}

	@RequestMapping(value = "/share", method = RequestMethod.GET)
	public String showRequestForm(ModelMap map, 
			@RequestParam(value = "item", required = true) String asset,
			Principal principal) {
		try {
			User user = modelrepository.getUser(principal.getName());
			map.put("from", user.getName());
			map.put("mailfrom", user.getEmail());
			map.put("asset", asset);
		} catch (Exception e) {
			String msg = "Error getting model from repository";
			log.error(msg, e);
			map.addAttribute("error", msg);
			return "share";
		}
		return "share";
	}

	/**
	 * Allot a model share to a user that does not have an account.
	 */
	@RequestMapping(value = { "/allot" }, method = RequestMethod.POST)
	public String shareModel(ModelMap map, Principal principal, HttpServletRequest request,
			@RequestParam(value = "asset", required=true) String asset,
			@RequestParam(value = "to", required=true) String email,
			@RequestParam(value = "message", required=true) String message) {

		try {
			User user = addCommonItems(map, principal);
			
			map.addAttribute("client", user);
			map.addAttribute("viewOnly",hasViewOnlyAccess(asset, user));
			map.addAttribute("writeAccess",hasWriteAccess(user, Paths.get(asset)));
			map.addAttribute("title", "Model archive");

			// common
			AssetProxy n = getAssetAtPath(user, asset);
			map.addAttribute("node", n);
			map.addAttribute("currentModel", n.getAsset());
			map.addAttribute("currentFolder",n.getRelativePath());
			map.addAttribute("crumbs", getBreadCrumb(n));
			
			StringBuilder sb = new StringBuilder();
			// from
			sb.append(user.getEmail());
			sb.append(',');
			// to
			sb.append(email);
			sb.append(',');
			// asset
			sb.append(asset);
			sb.append(',');
			// expiration
			sb.append(LocalDateTime.now().plusHours(24).atZone(ZoneId.systemDefault()).toEpochSecond());

			Base64.Decoder encoder = Base64.getDecoder();
			byte[] decodedKey = encoder.decode(KEY);
			SecretKey key = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES"); 
			String encrypt = encrypt(sb.toString(), key);
			
		    String url = request.getRequestURL().toString();
		    url = url.substring(0, url.lastIndexOf("/")) + "/allotment";
			url = url + "?id=" +URLEncoder.encode(encrypt,"UTF-8");
			
			try {
			sendEmail("Access granted to Modelshare asset", 
					"<p>You have been granted temporary access to download <a href=\""+url+"\">"+n.getName()+"</a>. The download link will be automatically invalidated after 24 hours.</p><p>"+message+"</p>", email, user.getIdentifier());
			} catch (MessagingException e){
				map.addAttribute("error","Could not send e-mail. "+e.getMessage());
				return "share";
			}
			map.addAttribute("info", "E-mail sent to <a href=\"mailto:"+email+"\">"+email+"</a> with download instructions.");

		} catch (Exception e){}
		return "content";
	}

	/**
	 * View a model as a logged in user.
	 */
	@RequestMapping(value = { "/view" }, method = RequestMethod.GET)
	public String viewModel(ModelMap map, Principal principal,
			@RequestParam(value = "item", required=true) String asset) {

		try {
			User user = addCommonItems(map, principal);
				
			map.addAttribute("client", user);
			map.addAttribute("viewOnly",hasViewOnlyAccess(asset, user));
			map.addAttribute("writeAccess",hasWriteAccess(user, Paths.get(asset)));
			map.addAttribute("title", "Model archive");

			// common
			map.addAttribute("downloadTerms", repositoryConfig.getDownloadTerms());
			AssetProxy n = getAssetAtPath(user, asset);
			map.addAttribute("node", n);
			map.addAttribute("currentModel", n.getAsset());
			map.addAttribute("currentFolder",n.getRelativePath());
			map.addAttribute("crumbs", getBreadCrumb(n));				
		} catch (Exception ioe) {
			String msg = "Cannot show item "+asset;
			log.error(msg, ioe);
			map.addAttribute("error", msg);
			return "content";
		}		
		return "content";
	}
	
	/**
	 * View a model as a user with a download link and no account.
	 */
	@RequestMapping(value = { "/allotment" }, method = RequestMethod.GET)
	public String viewAllotment(ModelMap map, Principal principal, HttpServletResponse response,
			@RequestParam(value = "id", required=true) String id,
			@RequestParam(value = "download", required=false) boolean download) {

		try {
			Base64.Decoder encoder = Base64.getDecoder();
			byte[] decodedKey = encoder.decode(KEY);
			SecretKey key = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");
			String[] decrypted = decrypt(id, key).split(",");
			
			String from = decrypted[0];
			String to = decrypted[1];
			String asset = decrypted[2];
			String expiration = decrypted[3];
						
			// make sure the key has not timed out
			ZonedDateTime now = ZonedDateTime.now();
			Instant epoch = Instant.ofEpochSecond(Long.parseLong(expiration));		
			System.out.println(epoch);
			ZonedDateTime t = epoch.atZone(ZoneId.systemDefault());
			if (now.isAfter(t)){
				map.addAttribute("error", "Cannot show allotment. The download token has expired.");
				return "content";												
			}

			if (!download) {
				map.addAttribute("viewOnly", false);
				map.addAttribute("writeAccess", false);
				map.addAttribute("link",id);
				map.addAttribute("title", "Model archive");
				User user = modelrepository.getUser(from);
				map.addAttribute("downloadTerms", repositoryConfig.getDownloadTerms());
				AssetProxy n = getAssetAtPath(user, asset);
				map.addAttribute("node", n);
				map.addAttribute("currentModel", n.getAsset());
				map.addAttribute("currentFolder", n.getRelativePath());
				map.addAttribute("crumbs", getBreadCrumb(n));
			} else {
				User user = modelrepository.getUser(from);
				Path path = Paths.get(asset);
				String name = path.getFileName().toString();
				// use the sharing user's credentials
				try (ServletOutputStream outputStream = response.getOutputStream();
						InputStream is = modelrepository.downloadModel(user, path)) {
					response.setContentType("application/octet-stream");
					response.setHeader("Content-Disposition", "attachment; filename=\"" + name + "\"");
					org.apache.commons.io.IOUtils.copy(is, outputStream);
					response.flushBuffer();
					Object[] messageArgs = { asset, to, from };
					downloadLog.info(MessageFormat.format("Model {0} was downloaded by {1} as shared by {2} ", messageArgs));
					return null;
				} catch (AccessDeniedException e) {
					log.error(MessageFormat.format("You do not have access to this file. Filename was '{0}'", asset),
							e);
					return "content";
				}

			}
		} catch (Exception ioe) {
			String msg = "Cannot show item.";
			log.error(msg, ioe);
			map.addAttribute("error", msg);
			return "content";
		}		
		return "content";
	}	
	
	@RequestMapping(value = { "/archive" }, method = RequestMethod.GET)
	public String viewFolder(ModelMap map, Principal principal,
			@RequestParam(value = "item", required = false) String asset) {

		try {
			User user = addCommonItems(map, principal);

			map.addAttribute("client", user);
			map.addAttribute("viewOnly",hasViewOnlyAccess(asset, user));
			map.addAttribute("writeAccess",hasWriteAccess(user, Paths.get(asset)));
			
			// common
			AssetProxy n = getAssetAtPath(user, asset);
			map.addAttribute("node", n);
			map.addAttribute("currentFolder", asset);
			map.addAttribute("crumbs", getBreadCrumb(n));
		} catch (Exception e) {
			String msg = "Could not load asset: "+e.getMessage();
			log.error(msg, e);
			map.addAttribute("error", msg);
			return "archive";
		}
		return "archive";
	}
	
	/**
	 * Shows the "create folder" form.
	 * 
	 * @param map
	 *            attributes for the page
	 * @param asset
	 *            the current folder
	 * @param principal
	 *            the logged in user
	 * @return the template to render
	 */
	@RequestMapping(value = "/folder", method = RequestMethod.GET)
	public String createFolderForm(ModelMap map, Principal principal,
			@RequestParam(value = "item", required = false) String asset) {		

		User user = addCommonItems(map, principal);
		map.addAttribute("currentFolder", asset);

		// common
		AssetProxy n = getAssetAtPath(user, asset);
		map.addAttribute("node", n);
		map.addAttribute("crumbs", getBreadCrumb(n));
		return "folder";
	}

	/**
	 * Shows the "upload" form.
	 * 
	 * 
	 * @param map
	 *            attributes for the page
	 * @param asset
	 *            the current folder
	 * @param principal
	 *            the logged in user
	 * @return the template to render
	 */
	@RequestMapping(value = "/upload", method = RequestMethod.GET)
	public String uploadModelForm(ModelMap map,Principal principal,
			@RequestParam(value = "item", required = false) String asset) {		

		User user = addCommonItems(map, principal);

		// common
		AssetProxy n = getAssetAtPath(user, asset);
		if (n.isLeaf()){
			Model model = (Model)n.getAsset();
			map.addAttribute("currentModel", model);
			map.addAttribute("modelName",model.getName());
			map.addAttribute("modelDescription",model.getDescription());			
			map.addAttribute("currentFolder",n.getParent().getRelativePath());			
		} else {
			map.addAttribute("currentFolder",n.getRelativePath());			
		}
		map.addAttribute("crumbs", getBreadCrumb(n));
		return "upload";
	}
	/**
	 * Receives the "upload" form.
	 * 
	 * @param map
	 * @param principal
	 * @param modelFile the model file being uploaded
	 * @param pictureFile the model picture being uploaded
	 * @param asset the path to the destination folder/path to existing model
	 * @param usage a description of the folder
	 * @param name
	 * @return
	 */
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public String uploadModel(ModelMap map, Principal principal,
			@RequestParam(value = "modelFile", required = true) MultipartFile file,
			@RequestParam(value = "pictureFile") MultipartFile picture,
			@RequestParam(value = "asset", required = true) String asset, 
			@RequestParam(value = "usage") String usage,
			@RequestParam(value = "name", required = true) String name) {
		
		try ( 	BufferedInputStream ms = new BufferedInputStream(file.getInputStream());
				BufferedInputStream ps = picture.isEmpty() ? null: new BufferedInputStream(picture.getInputStream())) {
			User user = addCommonItems(map, principal);

			String path;
			// common
			AssetProxy n = getAssetAtPath(user, asset);
			Model replacedModel = null;
			if (n.isLeaf()){
				replacedModel = (Model)n.getAsset();
				map.addAttribute("currentModel", replacedModel);
				map.addAttribute("modelName",replacedModel.getName());
				map.addAttribute("modelDescription",replacedModel.getDescription());
				path = n.getParent().getRelativePath();
				map.addAttribute("currentFolder",n.getParent().getRelativePath());			
			} else {
				path = n.getRelativePath();
				map.addAttribute("currentFolder",n.getRelativePath());			
			}
			map.addAttribute("crumbs", getBreadCrumb(n));

			if (file.isEmpty()){
				map.addAttribute("error", "A model file must be specified!");
				return "upload";	
			}
			// Create a new model and keep the old one?
			Model model = ModelshareFactory.eINSTANCE.createModel();
			model.setRelativePath(Paths.get(path, file.getOriginalFilename()).toString());
			model.setName(name.isEmpty() ? file.getOriginalFilename(): name);
			model.setOwner(user.getName());
			model.setMail(user.getEmail());
			// organization cannot be blank
			model.setOrganisation(user.getOrganisation() == null ? "" : user.getOrganisation());
			model.setUsage(usage == null ? "" : usage);
			modelrepository.uploadModel(user, ms, ps, model, replacedModel);
			return "redirect:view?item=" + model.getRelativePath().replace('\\', '/');
		} catch (AccessDeniedException ioe) {
			String msg = "You don't have access to upload a new model!";
			log.error(msg, ioe);
			map.addAttribute("error", msg);
			return "upload";		
		} catch (Exception ioe) {
			String msg = "Could not upload model: "+ioe.getMessage();
			log.error(msg, ioe);
			map.addAttribute("error", msg);
			return "upload";		
		}
	}

	@RequestMapping(value = "/createFolder", method = RequestMethod.POST)
	public String uploadFolder(ModelMap map, Principal principal,
			@RequestParam("path") String path, 
			@RequestParam("name") String name, 
			@RequestParam("picture") MultipartFile picture)
			throws UnsupportedEncodingException {

		User user = addCommonItems(map, principal);

		map.addAttribute("currentFolder", path);
		
		if (name.isEmpty()){
			map.addAttribute("error", "A folder name must be specified");
			return "folder";	
		}

		// ensure we have the correct encoding
		name = URLDecoder.decode(name.toString(), "UTF-8");
		path = URLDecoder.decode(path.toString(), "UTF-8");
		
		try (BufferedInputStream ps = picture.isEmpty() ? null: new BufferedInputStream(picture.getInputStream())){

			Path parentPath = Paths.get(modelrepository.getRoot(user).getPath(), path);
			Folder parentFolder = ModelshareFactory.eINSTANCE.createFolder();
			parentFolder.setPath(parentPath.toString());
			modelrepository.createFolder(user, parentFolder, ps, name);
			// upon success
			return "redirect:archive.html?item=" + URLEncoder.encode(path + '/' + name, "UTF-8");
		} catch (AccessDeniedException ioe) {
			String msg = "You don't have access to creating a new folder!";
			log.error(msg, ioe);
			map.addAttribute("error", msg);
			return "folder";		
		} catch (Exception ioe) {
			String msg = "Could not create folder: "+ioe.getMessage();
			log.error(msg, ioe);
			map.addAttribute("error", msg);
			return "folder";
		}
	}
	
	public boolean hasWriteAccess(User user, Path path) throws IOException {
		return modelrepository.hasWriteAccess(user, path);
	}

	private boolean hasViewOnlyAccess(String item, User client) {
		boolean hasReadAccess;
		try {
			hasReadAccess = modelrepository.hasDownloadAccess(client, Paths.get(item));
			boolean hasDisplayAccess = modelrepository.hasViewAccess(client, Paths.get(item));
			return (!hasReadAccess) && (hasDisplayAccess);
		} catch (IOException e) {
			log.error("Could not determine access rights", e);
			e.printStackTrace();
			return true;
		}
	}

}
