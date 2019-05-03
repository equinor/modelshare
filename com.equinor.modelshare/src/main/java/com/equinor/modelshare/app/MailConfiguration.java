package com.equinor.modelshare.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@Configuration
@PropertySources( value = { 
		@PropertySource (value = "classpath:modelshare.properties"),
		@PropertySource (value = "file:///${user.home}/modelshare/modelshare.properties", ignoreResourceNotFound=true),
		@PropertySource (value = "file:///D:/modelshare/modelshare.properties", ignoreResourceNotFound=true)
		})
public class MailConfiguration {

	@Autowired
	@Value("${smtp.host}")
	private final String smtpHost = null;
	
	@Autowired
	@Value("${smtp.port}")
	private final String smtpPort = null;
	
	@Autowired
	@Value("${access.request.mail.template}")
	private final String accessRequestMailTemplate = null;
	
	private SMTPConfiguration smtpConfiguration = new SMTPConfiguration();
	
	@Bean
	public SMTPConfiguration getSMTPConfiguration() {
		smtpConfiguration.setHost(smtpHost);
		smtpConfiguration.setPort(smtpPort);
		smtpConfiguration.setAccessRequestMailTemplate(accessRequestMailTemplate);
		return smtpConfiguration;
	}
	
	public class SMTPConfiguration {
		private String host;
		private String port;
		private String accessRequestMailTemplate;
		
		public String getHost() {
			return host;
		}
		private void setHost(final String host) {
			this.host = host;
		}
		public String getPort() {
			return port;
		}
		private void setPort(final String port) {
			this.port = port;
		}
		public String getAccessRequestMailTemplate() {
			return accessRequestMailTemplate;
		}
		private void setAccessRequestMailTemplate(final String template) {
			this.accessRequestMailTemplate = template;
		}

	}
}
