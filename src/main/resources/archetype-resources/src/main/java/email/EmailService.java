package ${package}.email;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

/**
 * @author Delsael Kenji <kenji@delsael.com>, Original Author
 */
public interface EmailService {

  /**
   * Send email.
   * 
   * @param String to - multiple recipients must be separated with a comma.
   * @param String subject
   * @param String content
   * @throws AddressException, MessagingException
   */
  public void sendEmail(String to, String subject, String content) throws AddressException, MessagingException;

  /**
   * Send email with attachment.
   * 
   * @param String to - multiple recipients must be separated with a comma.
   * @param String subject
   * @param String content
   * @param String attachment
   * @throws MessagingException
   */
  public void sendEmailWithAttachment(String to, String subject, String content, byte[] attachment) throws MessagingException;
}
