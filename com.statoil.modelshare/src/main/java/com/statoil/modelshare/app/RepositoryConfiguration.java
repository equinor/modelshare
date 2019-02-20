package com.statoil.modelshare.app;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.lang3.SystemUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import com.statoil.modelshare.controller.ModelRepository;
import com.statoil.modelshare.controller.ModelRepositoryImpl;
/**
 * @author Torkild U. Resheim, Itema AS
 */
@Configuration
@PropertySources( value = { 
		@PropertySource (value = "classpath:modelshare.properties"),
		@PropertySource (value = "file:///${user.home}/modelshare/modelshare.properties", ignoreResourceNotFound=true),
		@PropertySource (value = "file:///D:/modelshare/modelshare.properties", ignoreResourceNotFound=true)
		})
public class RepositoryConfiguration {

	@Autowired
	@Value("${repository.root}")
	private final String repositoryRoot = null;
	
	@Autowired
	@Value("${repository.terms}")
	private final String repositoryTerms = null;
	
	@Autowired
	@Value("${help.text}")
	private final String helpText = null;

	@Bean
	public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
		return new PropertySourcesPlaceholderConfigurer();
	}

	/**
	 * Returns the model repository. The physical location of this is determined
	 * by the property <i>repository.root</i>. See <a href=
	 * "http://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-external-config.html">
	 * the Spring documentation</a> for information on how to set up
	 * configuration.
	 * 
	 * @return the model repository
	 */
	@Bean
	public ModelRepository getModelRepository() {
		String path = null;
		if (SystemUtils.IS_OS_WINDOWS){
			path = "C:/Users/";
		} else if (SystemUtils.IS_OS_MAC){
			path = "/Users/";
		} else if (SystemUtils.IS_OS_LINUX){
			path = "/home/";
		}
		return new ModelRepositoryImpl(Paths.get(repositoryRoot), Paths.get(path));
	}
	
	@Bean
	public String getDownloadTerms() {
		return repositoryTerms;
	}
	
	public Path getRootFolder() {
		return Paths.get(repositoryRoot);
	}

}
