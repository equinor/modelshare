package com.statoil.modelshare.app.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import com.statoil.modelshare.controller.ModelRepository;
import com.statoil.modelshare.controller.ModelRepositoryImpl;

@Configuration
@PropertySource("classpath:repository.properties")
public class RepositoryConfig {
	
	@Autowired
	@Value("${repository.root}")
	private final String repositoryRoot = null;
	
	@Bean
	public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
		return new PropertySourcesPlaceholderConfigurer();
	}
	
	@Bean
	public ModelRepository getModelRepository() {
		return new ModelRepositoryImpl(repositoryRoot);
	}

}
