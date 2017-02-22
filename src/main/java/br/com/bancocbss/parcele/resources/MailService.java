package br.com.bancocbss.parcele.resources;

import javax.ejb.Asynchronous;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;
import javax.enterprise.event.Observes;
import javax.enterprise.event.TransactionPhase;
import javax.inject.Inject;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;

@Singleton
public class MailService {

	/*
	 * Note that this resource has to be configured in standalone.xml 
	 * (or domain.xml if you're in domain mode)
	 */
	/* Used by MailService */
	//@Resource(mappedName = "java:jboss/mail/ParceleMail")
	@Inject
	private Session mailSession;
	
	@Inject
	private Logger logger;

	@Asynchronous
	@Lock(LockType.READ)
	public void sendMail(@Observes(during = TransactionPhase.AFTER_SUCCESS) MailEvent event) {

	    /* adicionar isso no standalone.xml ou mapear esses valores aqui
<subsystem xmlns="urn:jboss:domain:mail:2.0">
    <mail-session name="default" jndi-name="java:jboss/mail/Default">
        <smtp-server outbound-socket-binding-ref="mail-smtp"/>
    </mail-session>
+>    <mail-session name="parcelemail" jndi-name="java:jboss/mail/ParceleMail" from="your.account@gmail.com">
+>        <smtp-server outbound-socket-binding-ref="mail-parcele" ssl="true" username="your.account@gmail.com" password="your-password"/>
+>    </mail-session>
</subsystem>

<socket-binding-group name="standard-sockets" default-interface="public" port-offset="${jboss.socket.binding.port-offset:0}">
 
    <!-- a bunch of stuff here -->
 
    <outbound-socket-binding name="mail-smtp">
        <remote-destination host="localhost" port="25"/>
    </outbound-socket-binding>
 
+>    <!-- "mail-parcele" is the same name we used in the mail-session config -->
+>    <outbound-socket-binding name="mail-parcele">
+>        <remote-destination host="smtp.gmail.com" port="465"/>
+>    </outbound-socket-binding>
         
</socket-binding-group>

	    Properties props = new Properties();
	    props.put("mail.smtp.host", "my-mail-server");
	    Session session = Session.getInstance(props, null);
	    */
	    logger.debug("Enviando e-mail: "+event.toString());
	    try {

			MimeMessage m = new MimeMessage(mailSession);
			Address[] to = new InternetAddress[] { new InternetAddress(event.getTo()) };

			m.setRecipients(Message.RecipientType.TO, to);
			m.setSubject(event.getSubject());
			m.setSentDate(new java.util.Date());
			m.setContent(event.getMessage(), "text/plain");

			Transport.send(m);
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
}