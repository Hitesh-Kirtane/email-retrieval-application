package com.mail.process;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.MimeBodyPart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mail.config.ApplicationConfig;
import com.mail.constants.ApplicationConstants;

@Component
public class EmailReceivingApplication implements EmailApplicationModule {

	Folder folder = null;
	Store store = null;

	@Autowired
	ApplicationConfig config;

	public void process() throws MessagingException {
		try {
			Properties props = System.getProperties();
			props.setProperty("mail.store.protocol", ApplicationConstants.EMAIL_PROTOCOL_IMAP);
			Session session = Session.getDefaultInstance(props, null);
			store = session.getStore(ApplicationConstants.EMAIL_PROTOCOL_IMAP);
			store.connect(config.getImapServerURL(), config.getEmailUsername(), config.getEmailPassword());
			folder = store.getFolder("Inbox");

			folder.open(Folder.READ_WRITE);
			Message messages[] = folder.getMessages();
			System.out.println("No of Messages : " + folder.getMessageCount());
			System.out.println("No of Unread Messages : " + folder.getUnreadMessageCount());
			for (int i=0; i < messages.length; ++i) {
				System.out.println("MESSAGE #" + (i + 1) + ":");
				Message msg = messages[i];

				//if we don''t want to fetch messages already processed
				if (!msg.isSet(Flags.Flag.SEEN)) {

				}

				String from = "unknown";
				if (msg.getReplyTo().length >= 1) {
					from = msg.getReplyTo()[0].toString();
				}
				else if (msg.getFrom().length >= 1) {
					from = msg.getFrom()[0].toString();
				}
				String subject = msg.getSubject();
				System.out.println("Saving ... " + subject +" " + from);
				// you may want to replace the spaces with "_"
				// the TEMP directory is used to store the files
				String filename = "D:\\Attachments\\" +  subject;
				saveParts(msg.getContent(), filename);
				msg.setFlag(Flags.Flag.SEEN,true);
			}
		} catch (Exception se) {
			se.printStackTrace();
		} finally {
			if (folder != null) { folder.close(true); }
			if (store != null) { store.close(); }	
		}
	}
	
	  public static void saveParts(Object content, String filename)
			  throws IOException, MessagingException
			  {
			    OutputStream out = null;
			    InputStream in = null;
			    try {
			      if (content instanceof Multipart) {
			        Multipart multi = ((Multipart)content);
			        int parts = multi.getCount();
			        for (int j=0; j < parts; ++j) {
			          MimeBodyPart part = (MimeBodyPart)multi.getBodyPart(j);
			          if (part.getContent() instanceof Multipart) {
			            // part-within-a-part, do some recursion...
			            saveParts(part.getContent(), filename);
			          }
			          else {
			            String extension = "";
			            if (part.isMimeType("text/html")) {
			              extension = "html";
			            }
			            else {
			              if (part.isMimeType("text/plain")) {
			                extension = "txt";
			              }
			              else {
			                //  Try to get the name of the attachment
			                extension = part.getDataHandler().getName();
			              }
			              filename = filename + "." + extension;
			              System.out.println("... " + filename);
			              out = new FileOutputStream(new File(filename));
			              in = part.getInputStream();
			              int k;
			              while ((k = in.read()) != -1) {
			                out.write(k);
			              }
			            }
			          }
			        }
			      }
			    }
			    finally {
			      if (in != null) { in.close(); }
			      if (out != null) { out.flush(); out.close(); }
			    }
			  }
	
}


