package ${package}.util.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * @author Delsael Kenji <kenji@delsael.com>, Original Author
 */
public class URLHelper {

  private static final String ENCODER = "UTF-8";

  /**
   * Encode the String.
   * 
   * @param String v
   * @return String
   */
  public static String urlEncode(String v) {
    try {
      return URLEncoder.encode(v, ENCODER);
    } catch (UnsupportedEncodingException ex) {
        throw new IllegalStateException(ex);
    }
  }

}
