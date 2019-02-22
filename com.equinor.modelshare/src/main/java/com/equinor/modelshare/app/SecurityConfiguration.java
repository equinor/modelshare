package com.equinor.modelshare.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.equinor.modelshare.app.service.FilebasedAuthenticationProvider;

/**
 * @author Torkild U. Resheim, Itema AS
 */
@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	static Logger log = LoggerFactory.getLogger(SecurityConfiguration.class.getName());
	
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
    			.anyRequest()
	            .authenticated()
	            .and()
	        .formLogin()
	            .loginPage("/signin")
	            .permitAll()
	            .and()
	        .logout()
	        	.logoutSuccessUrl("/signin?logout")
	        	.permitAll()
	        .and()
	        	.csrf().disable();
    }
    
    @Bean
	public PasswordEncoder passwordEncoder(){
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder;
	}    
    
    @Autowired
    private FilebasedAuthenticationProvider authenticationProvider;
    
	@Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authenticationProvider);
	}
}
