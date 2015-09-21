package com.statoil.modelshare.app.config;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.statoil.modelshare.User;
import com.statoil.modelshare.controller.ModelRepository;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	static Logger log = Logger.getLogger(SecurityConfig.class.getName());
	
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
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		System.out.println("Loading user database");
		log.info("Loading user database");
		// We're using the repository configuration to get a handle on the user database
		ApplicationContext ctx = new AnnotationConfigApplicationContext(RepositoryConfig.class);
		ModelRepository repo = ctx.getBean(ModelRepository.class);
		((ConfigurableApplicationContext)ctx).close();		
		List<User> users = repo.getUsers();
		for (User user : users) {
			if (user.isForceChangePassword()){
				auth.inMemoryAuthentication().withUser(user.getIdentifier()).password(user.getPassword()).roles("USER");
				log.warning("- User "+user.getIdentifier()+" must change password");
			} else {
				auth.inMemoryAuthentication().passwordEncoder(passwordEncoder()).withUser(user.getIdentifier()).password(user.getPassword()).roles("USER");
			}
		}
	}
}
