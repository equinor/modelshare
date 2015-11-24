package com.statoil.modelshare.app;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.Ordered;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.statoil.modelshare.app.config.RootConfig;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.statoil.modelshare")
@Import({ WebInitializer.class, RootConfig.class })
public class AppConfiguration extends WebMvcConfigurerAdapter{
	
	@Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/signin").setViewName("signin");
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
    }	

	@Bean
	public CommonsMultipartResolver multipartResolver(){
	    CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
	    multipartResolver.setDefaultEncoding("UTF-8");
	    multipartResolver.setMaxUploadSize(1_073_741_824); // 1GB
	    return multipartResolver;
	}	
}

