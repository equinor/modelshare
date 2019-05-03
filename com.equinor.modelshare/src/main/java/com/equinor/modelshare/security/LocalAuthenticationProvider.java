package com.equinor.modelshare.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
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

import com.equinor.modelshare.repository.ModelRepository;

/**
 * This authentication provider uses information from the <i>.passwd</i> file
 * to authenticate users.
 * 
 * @author Torkild U. Resheim, Itema AS
 */
@Service
@Profile(value = { "!Azure" })
public class LocalAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

	@Autowired
	private ModelRepository repository;

	@Override
	protected void additionalAuthenticationChecks(UserDetails userDetails,
			UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {		
//		if (userDetails.getPassword().isEmpty()) throw new CredentialsExpiredException("Password must be changed");		
	}

	@Override
	protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication)
			throws AuthenticationException {

		com.equinor.modelshare.User client = repository.getUser(username);
		if (client == null) {
			throw new BadCredentialsException("Bad credentials");
		}
		
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String credentials = (String)authentication.getCredentials();
		if (client.getPassword().isEmpty() || passwordEncoder.matches(credentials, client.getPassword())){
			List<GrantedAuthority> authorities = new ArrayList<>();
			authorities.add(new SimpleGrantedAuthority("USER"));
			return new User(client.getIdentifier(), client.getPassword(), authorities);
		} throw new BadCredentialsException("Bad credentials");
	}

}
