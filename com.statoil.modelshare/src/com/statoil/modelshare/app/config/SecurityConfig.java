package com.statoil.modelshare.app.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
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

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
            .inMemoryAuthentication()
                .withUser("user").password("password").roles("USER").and()
        		.withUser("admin").password("password").roles("USER", "ADMIN");
    }
}
