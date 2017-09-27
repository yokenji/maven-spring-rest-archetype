package ${package}.email;

import javax.activation.DataHandler;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import ${package}.email.impl.MimeType;

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
   * Send email.
   * 
   * @param String to
   * @param String subject
   * @param String content
   * @param DataHandler attachment
   * @param String fileName
   * @throws AddressException, MessagingException
   */
  public void sendEmail(String to, String subject, String content, DataHandler attachment, String fileName) throws AddressException, MessagingException;

  /**
   * Return a DataHandler for the given byteArray.
   * 
   * @param byte[] byteArray
   * @param MimeType type
   * @return DataHandler
   */
  public DataHandler getDataHandler(byte[] byteArray, MimeType type);

}
