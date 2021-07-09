package com.mail.process;

import javax.mail.MessagingException;

public interface EmailApplicationModule {

	public void process() throws MessagingException;
}
