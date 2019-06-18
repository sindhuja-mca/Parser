/**
 * 
 */
package com.hsi.parsing.exception;

/**
 * @author VKuma09
 *
 */
public class CVParsingException extends RuntimeException {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  public CVParsingException() {
    super();

  }

  public CVParsingException(String message, Throwable cause, boolean enableSuppression,
      boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);

  }

  public CVParsingException(String message, Throwable cause) {
    super(message, cause);

  }

  public CVParsingException(String message) {
    super(message);

  }

  public CVParsingException(Throwable cause) {
    super(cause);

  }

}
