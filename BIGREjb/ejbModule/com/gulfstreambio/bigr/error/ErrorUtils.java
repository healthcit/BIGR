package com.gulfstreambio.bigr.error;

/**
 */
public class ErrorUtils {

  /**
   * Converts an arbitrary exception to an exception that contains the same information but is a
   * {@link BigrException} instead, and then throws the resulting exception. If the exception is
   * already a {@link BigrException}, this simply throws it. Otherwise this throws a
   * <code>BigrException</code> that wraps <code>e</code>. We wrap the original exception with an
   * <code>BigrException</code> even if it is already a <code>RuntimeException</code> because 
   * <code>BigrException</code> preserves stack trace information about the wrapped exception when 
   * an exception is passed from the server side of an EJB call to the client side. Other 
   * exceptions normally lose their server-side stack trace information in this situation, and that 
   * information can be very helpful in diagnosing problems.
   * 
   * @param e the exception to convert
   */
  public static void throwAsBigrException(Exception e) {
    throw convertToRuntimeException(e);
  }
  
  private static RuntimeException convertToRuntimeException(Exception e) {
    if (e instanceof BigrException) {
      return (RuntimeException) e;
    }
    else {
      return new BigrException(e);
    }
  }

  private ErrorUtils() {
    super();
  }

}
