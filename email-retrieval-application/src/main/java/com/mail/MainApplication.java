package com.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.mail.constants.ApplicationConstants;
import com.mail.utils.ProcessUtils;

/**
 * This is the mail retrieval application main class.
 *
 */
@SpringBootApplication
@EnableConfigurationProperties
public class MainApplication implements CommandLineRunner
{
	@Autowired
	ProcessUtils procUtils;
	
    public static void main( String[] args )
    {
		System.setProperty(ApplicationConstants.JASYPT_ENCRYPTION_KEY_NAME, ApplicationConstants.JASYPT_ENCRYPTION_PASSWORD);
		SpringApplication.run(MainApplication.class, args);
    }
 
    @Override
    public void run(String...args) throws Exception
    {
    	procUtils.startProcess();
    }
}
