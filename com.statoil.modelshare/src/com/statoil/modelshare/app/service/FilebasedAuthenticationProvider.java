package com.statoil.modelshare.app.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.statoil.modelshare.Client;
import com.statoil.modelshare.app.config.RepositoryConfig;
import com.statoil.modelshare.controller.ModelRepository;

/**
 * 
 * @author Torkild U. Resheim, Itema AS
 */
@Service
public class FilebasedAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

	private ModelRepository repository;

	@Override
	protected void additionalAuthenticationChecks(UserDetails userDetails,
			UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
		// TODO Auto-generated method stub
	}

	@Override
	protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication)
			throws AuthenticationException {
		if (repository == null) {
			ApplicationContext ctx = new AnnotationConfigApplicationContext(RepositoryConfig.class);
			repository = ctx.getBean(ModelRepository.class);
			((ConfigurableApplicationContext) ctx).close();
		}
		Client client = repository.getUser(username);
		
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String credentials = (String)authentication.getCredentials();
		if (client.getPassword().isEmpty() || passwordEncoder.matches(credentials, client.getPassword())){
			List<GrantedAuthority> authorities = new ArrayList<>();
			authorities.add(new SimpleGrantedAuthority("USER"));
			return new User(client.getIdentifier(), client.getPassword(), authorities);
		} throw new BadCredentialsException("Bad credentials");
	}

}
