package ${package}.email.impl;

import java.util.Properties;

import javax.mail.Address;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.Message.RecipientType;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import ${package}.email.EmailService;

/**
 * @author Delsael Kenji <kenji@delsael.com>, Original Author
 */
@Component
public class EmailServiceImpl implements EmailService {

  private final String MAIL_TYPE = "text/html; charset=utf-8";

  private static Logger logger = LogManager.getLogger(EmailServiceImpl.class);

  private Properties props;
  private Environment env;

  @Autowired
  public EmailServiceImpl(Properties props, Environment env) {
    this.props = props;
    this.env = env;
  }

  /**
   * Convert email addresses.
   * 
   * @param String emailClient
   * @return Address[]
   * @throws AddressException
   */
  private Address[] toAddress(String email) throws AddressException {
    return InternetAddress.parse(email, false);
  }

  /**
   * Convert email addresses.
   * 
   * @param String email
   * @return InternetAddress[]
   * @throws AddressException
   */
  private InternetAddress[] toInternetAddress(String email) throws AddressException {
    return InternetAddress.parse(email, false);
  }

  /**
   * Return the mail properties.
   * 
   * @return Properties
   */
  private Properties getMailProperties() {
    props.setProperty("mail.smtp.host", env.getRequiredProperty("mail.smtp.host"));
    props.setProperty("mail.smtp.port", env.getRequiredProperty("mail.smtp.port"));
    props.setProperty("mail.from", env.getProperty("mail.from"));

    return props;
  }

  /* (non-Javadoc)
   * @see ${package}.EmailService\#sendEmail(java.lang.String, java.lang.String, java.lang.String)
   */
  @Override
  public void sendEmail(String to, String subject, String content) throws AddressException, MessagingException {
    logger.info(String.format("Send email to %s with subject %s", to, subject));

    Session sess = Session.getInstance(getMailProperties());
    sess.setDebug(true);

    MimeMessage msg = new MimeMessage(sess);
    msg.setFrom();
    msg.setSubject(subject);
    msg.setRecipients(RecipientType.TO, toAddress(to));
    msg.setContent(content, MAIL_TYPE);

    Transport.send(msg);
  }

  /*
   * (non-Javadoc)
   * @see ${package}.email.EmailService\#sendEmailWithAttachment(java.lang.String, java.lang.String, java.lang.String, byte[])
   */
  @Override
  public void sendEmailWithAttachment(String to, String subject, String content, byte[] attachment) throws AddressException, MessagingException {
    logger.info("Send email to " + to + " with subject " + subject);
    Session sess = Session.getInstance(getMailProperties());

    MimeMessage mimeMessage = new MimeMessage(sess);
    mimeMessage.setFrom();

    MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
    message.setSubject(subject);
    message.setTo(toInternetAddress(to));
    message.setText(content, true);

    Transport.send(mimeMessage);
  }
}
