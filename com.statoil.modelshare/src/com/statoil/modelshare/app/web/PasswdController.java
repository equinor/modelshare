package com.statoil.modelshare.app.web;

import java.security.Principal;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.statoil.modelshare.app.config.RepositoryConfig;
import com.statoil.modelshare.controller.ModelRepository;

/**
 * @author Torkild U. Resheim, Itema AS
 */
@Controller
public class PasswdController {

	private ModelRepository repo;

	@RequestMapping(value = "/passwd", method = RequestMethod.POST)
	public String changePassword(ModelMap model, @RequestParam(value = "old-password", required = true) String password,
			@RequestParam(value = "new-password", required = true) String newPassword,
			@RequestParam(value = "confirm-password", required = true) String confirmPassword, Principal principal) {
		if (!newPassword.equals(confirmPassword)) {
			model.addAttribute("error", "'New password' and 'Confirm password' must match.");
			return "passwd";
		}
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String newHashedPassword = passwordEncoder.encode(newPassword);

		// We're using the repository configuration to get a handle on the user
		// database
		if (repo == null) {
			ApplicationContext ctx = new AnnotationConfigApplicationContext(RepositoryConfig.class);
			repo = ctx.getBean(ModelRepository.class);
			((ConfigurableApplicationContext) ctx).close();
		}
		
		// Change the password
		String name = principal.getName();
		repo.setPassword(name, newHashedPassword);
		// Reset securitycontext and force user to log in again
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();		
        auth.setAuthenticated(false);
		SecurityContextHolder.clearContext();
		model.addAttribute("info", "Your password has been changed, please log in using the new credentials.");
		return "signin";
	}
	

	@RequestMapping(value = "/passwd", method = RequestMethod.GET)
	public String showPasswordView(ModelMap model) {
		return "passwd";
	}
}