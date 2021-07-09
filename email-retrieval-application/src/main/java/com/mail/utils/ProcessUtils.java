package com.mail.utils;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mail.process.EmailReceivingApplication;

@Service
public class ProcessUtils {
	
	@Autowired
	EmailReceivingApplication eml;
	
	public void startProcess() throws MessagingException {
		eml.process();
	}
}
