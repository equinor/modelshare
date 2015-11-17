package com.statoil.modelshare.app.web;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.statoil.modelshare.app.service.ArchiveService;
import com.statoil.modelshare.controller.ModelRepository;

/**
 * @author Torkild U. Resheim, Itema AS
 */
@Controller
public class PasswdController {
	
	private ArchiveService service = new ArchiveService();
	
    @Autowired
    private ModelRepository repository;

	@RequestMapping(value = "/passwd", method = RequestMethod.POST)
	public String changePassword(ModelMap model, @RequestParam(value = "old-password", required = true) String password,
			@RequestParam(value = "new-password", required = true) String newPassword,
			@RequestParam(value = "confirm-password", required = true) String confirmPassword, Principal principal) {
		
		
		
		// make sure password are the same
		if (!newPassword.equals(confirmPassword)) {
			model.addAttribute("error", "'New password' and 'Confirm password' must match.");
			return "passwd";
		}
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String newHashedPassword = passwordEncoder.encode(newPassword);
		
		String userPassword = repository.getUser(principal.getName()).getPassword();
		if (!userPassword.isEmpty() && !passwordEncoder.matches(password, userPassword)){
			model.addAttribute("error", "'Old password' is not correct.");
			return "passwd";			
		}

		// change the password
		String name = principal.getName();
		repository.setPassword(name, newHashedPassword); // XXX: Check for correct user
		// reset security context and force user to log in again
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();		
        auth.setAuthenticated(false);
		SecurityContextHolder.clearContext();
		model.addAttribute("message", "Your password has been changed, please sign in using the new credentials.");
		return "signin";
	}
	

	@RequestMapping(value = "/passwd", method = RequestMethod.GET)
	public String showPasswordView(ModelMap model, Principal principal) {
		model.addAttribute("topLevel", service.getTopLevel(principal));
		return "passwd";
	}
}