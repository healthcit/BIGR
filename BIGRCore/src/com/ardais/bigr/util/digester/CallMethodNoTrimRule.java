package com.ardais.bigr.util.digester;

import org.apache.commons.digester.CallMethodRule;

/**
 * The {@link CallMethodRule} class that ships with the Apache Digester automatically trims
 * the tag body text when it is used as a method parameter.  This is an extension of CallMethodRule
 * that differs only in that it does not trim the tag body text.
 */
public class CallMethodNoTrimRule extends CallMethodRule {

  /**
   * @see CallMethodRule#CallMethodRule(String, int)
   */
  public CallMethodNoTrimRule(String methodName, int paramCount) {
    super(methodName, paramCount);
  }

  /**
   * @see CallMethodRule#CallMethodRule(int, String, int)
   */
  public CallMethodNoTrimRule(int targetOffset, String methodName, int paramCount) {
    super(targetOffset, methodName, paramCount);
  }

  /**
   * @see CallMethodRule#CallMethodRule(String)
   */
  public CallMethodNoTrimRule(String methodName) {
    super(methodName);
  }

  /**
   * @see CallMethodRule#CallMethodRule(int, String)
   */
  public CallMethodNoTrimRule(int targetOffset, String methodName) {
    super(targetOffset, methodName);
  }

  /**
   * @see CallMethodRule#CallMethodRule(String, int, String[])
   */
  public CallMethodNoTrimRule(String methodName, int paramCount, String[] paramTypes) {
    super(methodName, paramCount, paramTypes);
  }

  /**
   * @see CallMethodRule#CallMethodRule(int, String, int, String[])
   */
  public CallMethodNoTrimRule(
    int targetOffset,
    String methodName,
    int paramCount,
    String[] paramTypes) {
    super(targetOffset, methodName, paramCount, paramTypes);
  }

  /**
   * @see CallMethodRule#CallMethodRule(String, int, Class[])
   */
  public CallMethodNoTrimRule(String methodName, int paramCount, Class[] paramTypes) {
    super(methodName, paramCount, paramTypes);
  }

  /**
   * @see CallMethodRule#CallMethodRule(int, String, int, Class[])
   */
  public CallMethodNoTrimRule(
    int targetOffset,
    String methodName,
    int paramCount,
    Class[] paramTypes) {
    super(targetOffset, methodName, paramCount, paramTypes);
  }

  /**
   * Process the body text of this element.  This overrides the implementation on
   * {@link CallMethodRule} and differs only in that it does not trim the tag body text
   * when it is being used as the method parameter.
   *
   * @param bodyText The body text of this element
   */
  public void body(String bodyText) throws Exception {
    if (paramCount == 0) {
      this.bodyText = bodyText;
    }
  }

}
