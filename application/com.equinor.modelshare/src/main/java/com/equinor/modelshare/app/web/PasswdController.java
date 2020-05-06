/*******************************************************************************
 * Copyright © 2020 Equinor ASA
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package com.equinor.modelshare.app.web;

import java.math.BigInteger;
import java.security.Principal;
import java.security.SecureRandom;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.equinor.modelshare.Account;
import com.equinor.modelshare.ModelshareFactory;
import com.equinor.modelshare.Token;
import com.equinor.modelshare.User;
import com.equinor.modelshare.repository.ModelRepository;

/**
 * Controller for requesting a password reset along with changing the password. 
 * 
 * @author Torkild U. Resheim, Itema AS
 */
@Controller
@Profile(value = { "!Azure" })
public class PasswdController extends AbstractController {
	
    @Autowired
    private ModelRepository repository;

	@RequestMapping(value = "/change-password", method = RequestMethod.POST)
	public String changePassword(ModelMap map, Principal principal, 
			@RequestParam(value = "old-password", required = false) String password,
			@RequestParam(value = "new-password", required = true) String newPassword,
			@RequestParam(value = "confirm-password", required = true) String confirmPassword,
			@RequestParam(value = "email", required = false) String email,
			@RequestParam(value = "token", required = false) String key) {
		
		// make sure password are the same
		if (!newPassword.equals(confirmPassword)) {
			map.addAttribute("error", "'New password' and 'Confirm password' must match.");
			return "passwd";
		}		
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String newHashedPassword = passwordEncoder.encode(newPassword);
		
		User user = null;
		// normal change of password with signed in user
		if (principal != null) {
			user = repository.getUser(principal.getName());
			String userPassword = repository.getUser(principal.getName()).getPassword();
			if (!userPassword.isEmpty() && !passwordEncoder.matches(password, userPassword)) {
				map.addAttribute("error", "'Old password' is not correct.");
				return "passwd";
			}
		// change password trough reset
		} else {
			user = getUser(email);
			if (user==null){
				map.addAttribute("error", "Cannot reset password. Unknown user.");
				return "resetpasswd";				
			}
			Token token = user.getResettoken();
			if (token==null) {
				map.addAttribute("error", "Cannot reset password. Please fill in a new request.");
				return "resetpasswd";								
			}
			// make sure the key is correct
			String key2 = token.getKey();
			if (!key2.equals(key)){
				map.addAttribute("error", "Cannot reset password. The reset token is invalid.");
				return "resetpasswd";												
			}
			// make sure the key has not timed out
			ZonedDateTime now = ZonedDateTime.now();
			Instant epoch = Instant.ofEpochSecond(token.getTimeout());			
			ZonedDateTime t = epoch.atZone(ZoneId.systemDefault());
			if (now.isAfter(t)){
				map.addAttribute("error", "Cannot reset password. The reset token has expired.");
				return "resetpasswd";												
			}
		}
		// change the password
		user.setResettoken(null);
		repository.setPassword(user.getIdentifier(), newHashedPassword);
		// reset security context and force user to log in again
		if (principal!=null){
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();		
	        auth.setAuthenticated(false);
			SecurityContextHolder.clearContext();
		}
		map.addAttribute("message", "Your password has been changed, please sign in using the new credentials.");
		return "signin";
	}

	private User getUser(String email) {
		User user = null;
		List<Account> accounts = repository.getAuthorizedAccounts();
		for (Account account : accounts) {
			if (account instanceof User){
				if (((User) account).getEmail().equals(email)){
					user = (User) account;
					break;
				}
			}
		}
		return user;
	}
	
	@RequestMapping(value = "/reset-password", method = RequestMethod.GET)
	public String resetPasswordView(ModelMap map) {
		return "resetpasswd";
	}
	
	@RequestMapping(value = "/reset-password", method = RequestMethod.POST)
	public String resetPassword(ModelMap map, HttpServletRequest request,
			@RequestParam(value = "email", required = true) String email) {

		SecureRandom random = new SecureRandom();
		String key = new BigInteger(130, random).toString(32);
		
		Token token = ModelshareFactory.eINSTANCE.createToken();
		token.setKey(key);
		LocalDateTime plusHours = LocalDateTime.now().plusHours(1);
		token.setTimeout(plusHours.atZone(ZoneId.systemDefault()).toEpochSecond());
		
		User user = getUser(email);
		if (user!=null){
			user.setResettoken(token);
			String url = request.getRequestURL().toString();
		    url = url.substring(0, url.lastIndexOf("/")) + "/change-password?token="+key+"&email="+email;
			
			try {
					String msg = "<p>Someone recently requested a password change for your Modelshare account. " +
							"If this was you, you can set a new password <a href=\""+url+"\">here</a> within one hour.</p>" +						
							"<p>If you don't want to change your password or didn't request this, just ignore and delete this message.</p>";					
				sendEmail("Modelshare password reset", msg, email, email);
				repository.updateAccountsOnFile();
			} catch (MessagingException e) {
				map.addAttribute("error", "An e-mail with reset instructions could not be sent. Please contact the system administrator.");
				return "resetpasswd";
			}
		}
		map.addAttribute("info", "If your address ("+email+") is in our records you will soon receive an e-mail with instructions to reset your password.");
		return "resetpasswd";
	}
	

	@RequestMapping(value = "/change-password", method = RequestMethod.GET)
	public String showPasswordView(ModelMap map, Principal principal,
			@RequestParam(value = "token", required = false) String token,
			@RequestParam(value = "email", required = false) String email) {
		// this view may be accessed without being logged in
		if (principal!=null){
			addCommonItems(map, principal);
		}
		map.addAttribute("token", token);
		map.addAttribute("email", email);
		return "passwd";
	}
}