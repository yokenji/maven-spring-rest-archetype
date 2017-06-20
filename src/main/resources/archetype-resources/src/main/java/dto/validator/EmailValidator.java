package ${package}.dto.validator;

import java.util.Hashtable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The email validator will validate the given email structure.
 * If the option to lookup MX record has been set
 * a check will be made on DNS name.
 *  
 * @author Bossuyt Fabrice <fabrice.bossuyt@gmail.com>, Original Author
 */
public class EmailValidator implements ConstraintValidator<ValidEmail, String> {

  private static final Logger logger = LogManager.getLogger(EmailValidator.class);

  private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-+]+(.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(.[A-Za-z0-9]+)*(.[A-Za-z]{2,})$";
  private Pattern pattern;
  private Matcher matcher;
  

  private boolean isRequired;
  private boolean lookUpMxRecord;

  @Override
  public void initialize(ValidEmail validEmail) {
    this.isRequired = validEmail.required();
    this.lookUpMxRecord = validEmail.mxLookup();
  }

  @Override
  public boolean isValid(String email, ConstraintValidatorContext context) {
    return validateEmail(email);
  }

  private boolean validateEmail(String email) {
    boolean retval = false;

    if (isRequired == false && (email == null || email.isEmpty()))
      return true;

    pattern = Pattern.compile(EMAIL_PATTERN);
    matcher = pattern.matcher(email);
    retval = matcher.matches();

    if (lookUpMxRecord == true)
      retval = doLookup(email.substring(email.indexOf("@") +1));

    return retval;
  }

  /**
   * Check if the dns exists.
   * 
   * @param String hostName
   * @return boolean
   */
  private boolean doLookup(String hostName) {
    boolean retval = true;
    Hashtable<String, String> env = new Hashtable<>();
    env.put("java.naming.factory.initial", "com.sun.jndi.dns.DnsContextFactory");

    try {
      DirContext ictx = new InitialDirContext( env );
      Attributes attrs = 
         ictx.getAttributes( hostName, new String[] { "MX" });
      Attribute attr = attrs.get( "MX" );

      if (attr == null)
        retval = false;

    } catch (NamingException ex) {
        logger.error(hostName + " : " + ex.getMessage());
        retval = false;
    }

    return retval;
  }

}
