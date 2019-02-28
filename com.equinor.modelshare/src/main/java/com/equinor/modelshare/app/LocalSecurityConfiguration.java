package com.equinor.modelshare.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.equinor.modelshare.security.LocalAuthenticationProvider;

/**
 * Security configuration to use when using the built it mechanism. This 
 * utilizes the <i>.passwd</i> file residing in the root folder of the model
 * repository.
 * 
 * @author Torkild U. Resheim, Itema AS
 */
@Configuration
@EnableWebSecurity
@Profile(value = { "!Azure" })
public class LocalSecurityConfiguration extends WebSecurityConfigurerAdapter {

	static Logger log = LoggerFactory.getLogger(LocalSecurityConfiguration.class.getName());
	
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
	        	.permitAll();
    }
    
    @Bean
	public PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}
    
    @Bean
    public LocalAuthenticationProvider authenticationProvider() {
    	return new LocalAuthenticationProvider();
    }
    
    @Autowired
    private LocalAuthenticationProvider authenticationProvider;
    
    // Called due to @EnableGlobalAuthentication which is enabled by
    // @EnableWebSecurity
	@Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authenticationProvider);
	}
	
}
