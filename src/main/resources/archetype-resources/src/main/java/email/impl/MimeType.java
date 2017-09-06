package ${package}.email.impl;

/**
 * @author Delsael Kenji <kenji@romacfuels.com>, Original Author
 */
public enum MimeType {

  PDF("application/pdf"),
  CSV("text/csv"),
  XLS("application/vnd.ms-excel");

  private String contentType;

  private MimeType(String contentType) {
    this.contentType = contentType;
  }

  public String getContentType() {
    return this.contentType;
  }

}
