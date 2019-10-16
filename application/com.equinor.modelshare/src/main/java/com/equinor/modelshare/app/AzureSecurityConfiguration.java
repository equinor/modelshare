package com.equinor.modelshare.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;

/**
 * Security configuration to use when Azure AD authentication is enabled.
 * 
 * @author Torkild U. Resheim, Itema AS
 */
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
@Order(SecurityProperties.BASIC_AUTH_ORDER)
@Profile(value = { "Azure" })
public class AzureSecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	static Logger log = LoggerFactory.getLogger(AzureSecurityConfiguration.class.getName());
	
	@Autowired
    private OAuth2UserService<OidcUserRequest, OidcUser> workingOidcUserService;
	
	/**
	 * Disregard certain resources for authentication purposes as these should
	 * be available for all users.
	 */
	@Override
    public void configure(WebSecurity web) throws Exception {
        web
        	.ignoring().antMatchers("/webjars/**").and()
            .ignoring().antMatchers("/css/**").and()
            .ignoring().antMatchers("/js/**").and()
            .ignoring().antMatchers("/img/**").and()
            .ignoring().antMatchers("/fonts/**").and()
            .ignoring().antMatchers("/change-password").and()
            .ignoring().antMatchers("/reset-password").and()
            // special case for models shared with unauthenticated users 
            .ignoring().antMatchers("/pictures").and()
            .ignoring().antMatchers("/allotment");
    }
	
    @Override
    protected void configure(HttpSecurity http) throws Exception {                
    	http
    		.authorizeRequests()	        
    			.anyRequest().authenticated()
    		// XXX: Azure AD won't really let you log out as it is
    		.and()
    			.logout()
    				.logoutSuccessUrl("/")
    				.deleteCookies("JSESSIONID")
    				.clearAuthentication(true)
    				.invalidateHttpSession(true)
    				.permitAll()
			.and()
				.oauth2Login()
				.tokenEndpoint()
			.and()
				.userInfoEndpoint()
				.oidcUserService(workingOidcUserService);				
    }    
    
}
