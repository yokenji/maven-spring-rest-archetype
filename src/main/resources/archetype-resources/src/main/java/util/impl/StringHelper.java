package ${package}.util.impl;

import java.io.UnsupportedEncodingException;

import org.apache.commons.codec.binary.Base64;

/**
 * @author Delsael Kenji <kenji@delsael.com>, Original Author
 */
public final class StringHelper {

  private static final String ENCODING = "UTF-8";

  /**
   * Convert a byte array to a base64 string.
   * 
   * @param byte[] bytes
   * @return String
   */
  public static String byteArrayToBase64String(byte[] bytes) {
    String base64String = "";
    if (bytes != null) {
      base64String = new String(Base64.encodeBase64String(bytes));
    }
    return base64String;
  }

  /**
   * Convert a base64String to a byte array.
   * 
   * @param String base64String
   * @return byte[]
   */
  public static byte[] base64StringToByteArray(String base64String) {
    byte[] retval = null;

    try {
      retval = Base64.decodeBase64(
          replaceRemoveImageFormat(base64String).getBytes(ENCODING));
    } catch (UnsupportedEncodingException e) {
        e.printStackTrace();
    }
    return retval;
  }

  /**
   * Replace the Supported image formats.
   * 
   * @param String base64String
   * @return String
   */
  private static String replaceRemoveImageFormat(String base64String) {
    String regex = "data:image/(jpeg|png|jpg);base64,";
    if (base64String.matches(regex + ".*"))
      base64String = base64String.replaceAll(regex, "");

    return base64String;
  }

}
