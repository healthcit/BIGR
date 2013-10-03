package com.ardais.bigr.web.taglib;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.struts.util.RequestUtils;
import org.apache.struts.util.ResponseUtils;

/**
 * Implements the BIGR <code>conditionalWrite</code> tag.
 */
public class ConditionalWriteTag extends TagSupport {
  String _condition;
  String _name;
  String _property;
  String _value;

  String _nullValue;
  String _falseValue;
  String _trueValue;
  /**
   * Creates a new <code>ConditionalWriteTag</code>.
   */
  public ConditionalWriteTag() {
    super();
  }
  /**
   * Writes either the <code>true</code> or <code>false</code> value to the HTTP response.
   */
  public int doStartTag() throws JspException {

    Boolean condition = null;

    // Get the condition.
    if (_condition == null) {
      if ((_name == null) || (_property == null))
        throw new JspException("Error in tag conditionalWrite: condition not specified");
      else {
        Object cond = RequestUtils.lookup(pageContext, _name, _property, null);
        if (cond != null) {
          if (_value != null)
            condition = new Boolean(cond.toString().equals(_value));
          else
            condition = new Boolean(cond.toString());
        }
      }
    } else
      condition = new Boolean(_condition);

    // If a boolean condition was specified, then write either the true 
    // or false value based on the condition.
    if (condition != null) {
      String trueValue = (_trueValue != null) ? _trueValue : "";
      String falseValue = (_falseValue != null) ? _falseValue : "";
      if (condition.equals(Boolean.TRUE))
        ResponseUtils.write(pageContext, trueValue);
      else
        ResponseUtils.write(pageContext, falseValue);
    }

    // Otherwise if the condition is null, then write the null value if 
    // the user specified a null value.
    else if (_nullValue != null)
      ResponseUtils.write(pageContext, _nullValue);

    // Otherwise the tag was not properly specified, so  throw an exception.
    else
      throw new JspException("Error in tag conditionalWrite: specified name/property does not return a value and a null value was not specified");

    return (SKIP_BODY);
  }
  /**
   * Release all allocated resources.
   */
  public void release() {
    super.release();
    _condition = null;
    _name = null;
    _property = null;
    _value = null;
    _nullValue = null;
    _falseValue = null;
    _trueValue = null;
  }
  /**
   * Sets the condition that is to be evaluated to determine whether to write the
   * true or false value.  This can also be specified by a combination of
   * the name and property attributes.
   *
   * @param  condition  the condition
   */
  public void setCondition(String condition) {
    _condition = condition;
  }
  /**
   * Sets the value to write if the condition evaluates to <code>false</code>.
   *
   * @param  value  the <code>false</code> value
   */
  public void setFalseValue(String value) {
    _falseValue = value;
  }
  /**
   * Sets the name of the bean that contains a property that evaluates a
   * condition and returns a boolean.  The condition is used to determine
   * whether to write the true or false value.  This can also be specified 
   * by the condition attribute.
   *
   * @param  name  the bean name
   */
  public void setName(String name) {
    _name = name;
  }
  /**
   * Sets the value to write if the condition evaluates to <code>null</code>.
   *
   * @param  value  the <code>null</code> value
   */
  public void setNullValue(String value) {
    _nullValue = value;
  }
  /**
   * Sets the property of the bean specified by name that evaluates a
   * condition and returns a boolean.  The condition is used to determine
   * whether to write the true or false value.  This can also be specified 
   * by the condition attribute.
   *
   * @param  property  the bean property
   */
  public void setProperty(String property) {
    _property = property;
  }
  /**
   * Sets the value to write if the condition evaluates to <code>true</code>.
   *
   * @param  value  the <code>true</code> value
   */
  public void setTrueValue(String value) {
    _trueValue = value;
  }
  /**
   * Sets the value to which the bean property should be compared.
   *
   * @param  value  the value
   */
  public void setValue(String value) {
    _value = value;
  }
}
