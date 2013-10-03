package com.ardais.bigr.util.digester;

import org.apache.commons.beanutils.MethodUtils;
import org.apache.commons.digester.SetNextRule;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>
 * Rule implementation that calls a method on the (top-1) (parent) object, passing the top object
 * (child) as an argument. It is commonly used to establish parent-child relationships.
 * </p>
 * 
 * <p>
 * This rule extends {@link org.apache.commons.digester.SetNextRule}by taking an additional
 * parameter that specifies the expected class of the parent. The specified method will only be
 * called if the parent is not null and is an instance of the specified expected parent class.
 * </p>
 */
public class SetNextIfRule extends SetNextRule {
  
  // This class was added to provide a solution for MR 8680, in which using SetNextRule would
  // throw exceptions in invalid XML documents.  What was happening is that even though the
  // document was invalid according to the DTD, and a validating Digester was used to parse
  // it, it was still trying to run the parsing rules (like SetNextRule) before realizing the
  // document was invalid.  If the document was invalid in certain ways (such as having a tag
  // nested inside the wrong kind of parent tag), Digester would throw exceptions about
  // the method it was trying to call on the parent object in a SetNextRule not existing, since
  // the parent class on the stack wasn't what it was expected to be in a valid document.
  //
  // By using SetNextIfRule instead, the inability to call the method on the incorrect parent
  // class will be quietly ignored in an invalid document, and the parser get to continue
  // so that it can ultimately report a validation error against the DTD it was using.

  /**
   * The expected parent object type.
   */
  protected String _expectedParentType = null;

  /**
   * Construct a "set next if" rule with the specified expected parent type and method name. The
   * method's argument type is assumed to be the class of the child object.
   * 
   * @param expectedParentType Expected type of the parent object. The method will only be called if
   *          the actual parent object is not null and is an instance of the specified type.
   * @param methodName Method name of the parent method to call
   */
  public SetNextIfRule(String expectedParentType, String methodName) {
    super(methodName);
    validateExpectedParentType(expectedParentType);
    _expectedParentType = expectedParentType;
  }

  /**
   * Construct a "set next if" rule with the specified expected parent type, method name and
   * parameter type.
   * 
   * @param expectedParentType Expected type of the parent object. The method will only be called if
   *          the actual parent object is not null and is an instance of the specified type.
   * @param methodName Method name of the parent method to call
   * @param paramType Java class of the parent method's argument (if you wish to use a primitive
   *          type, specify the corresonding Java wrapper class instead, such as
   *          <code>java.lang.Boolean</code> for a <code>boolean</code> parameter)
   */
  public SetNextIfRule(String expectedParentType, String methodName, String paramType) {
    super(methodName, paramType);
    validateExpectedParentType(expectedParentType);
    _expectedParentType = expectedParentType;
  }

  /**
   * Throw a runtime exception if the specified type is not a valid value for the expectedParentType
   * parameter to this class.
   * 
   * @param expectedParentType The expected parent type to validate.
   */
  protected void validateExpectedParentType(String expectedParentType) {
    if (expectedParentType == null) {
      throw new IllegalArgumentException("The expectedParentType must not be null.");
    }
  }

  /**
   * Process the end of this element. If the parent object is null or is not an instance of the
   * specified expected parent type, quietly do nothing. Otherwise do the same thing as
   * {@link org.apache.commons.digester.SetNextRule}would do.
   */
  public void end() throws Exception {

    // Identify the objects to be used
    Object parent = digester.peek(1);

    if (parent == null) {
      return;
    }

    Class expectedClass = digester.getClassLoader().loadClass(_expectedParentType);

    if (!(expectedClass.isInstance(parent))) {
      return;
    }

    super.end();
  }

  /**
   * Render a printable version of this Rule.
   */
  public String toString() {
    StringBuffer sb = new StringBuffer("SetNextIfRule[");
    sb.append("expectedParentType=");
    sb.append(_expectedParentType);
    sb.append(", methodName=");
    sb.append(methodName);
    sb.append(", paramType=");
    sb.append(paramType);
    sb.append("]");
    return (sb.toString());
  }

}
