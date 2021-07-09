package com.mail.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;

@Configuration
@EnableEncryptableProperties
@ConfigurationProperties
public class ApplicationConfig {

	private String emailUsername;
	
	private String emailPassword;
	
	private String imapServerURL;
	
	private int imapServerPort;
	
	

	public String getEmailUsername() {
		return emailUsername;
	}

	public void setEmailUsername(String emailUsername) {
		this.emailUsername = emailUsername;
	}

	public String getEmailPassword() {
		return emailPassword;
	}

	public void setEmailPassword(String emailPassword) {
		this.emailPassword = emailPassword;
	}

	public String getImapServerURL() {
		return imapServerURL;
	}

	public void setImapServerURL(String imapServerURL) {
		this.imapServerURL = imapServerURL;
	}

	public int getImapServerPort() {
		return imapServerPort;
	}

	public void setImapServerPort(int imapServerPort) {
		this.imapServerPort = imapServerPort;
	}
	
	
	
	
}
