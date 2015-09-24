package com.statoil.modelshare.app.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:repository.properties")
public class MailConfig {

	@Autowired
	@Value("${smtp.host}")
	private final String smtpHost = null;
	
	@Autowired
	@Value("${smtp.port}")
	private final String smtpPort = null;
	
	@Autowired
	private SMTPConfiguration smtpConfiguration = new SMTPConfiguration();

	
	@Bean
	public SMTPConfiguration getSMTPConfiguration() {
		smtpConfiguration.setHost(smtpHost);
		smtpConfiguration.setPort(smtpPort);
		return smtpConfiguration;
	}
	
	public class SMTPConfiguration {
		private String host;
		private String port;
		
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
	}
	
}
