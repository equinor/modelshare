package com.statoil.modelshare.app.config;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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

import com.statoil.modelshare.app.service.FilebasedAuthenticationProvider;

/**
 * @author Torkild U. Resheim, Itema AS
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	static Log log = LogFactory.getLog(SecurityConfig.class.getName());
	
	@Override
    public void configure(WebSecurity web) throws Exception {
        web
            .ignoring().antMatchers("/css/**").and()
            .ignoring().antMatchers("/images/**");
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
