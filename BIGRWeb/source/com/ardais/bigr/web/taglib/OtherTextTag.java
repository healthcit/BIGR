package com.ardais.bigr.web.taglib;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.struts.taglib.html.Constants;
import org.apache.struts.util.RequestUtils;
import org.apache.struts.util.ResponseUtils;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.api.ApiFunctions;

/**
 * Implements the BIGR <code>otherText</code> tag.
 */
public class OtherTextTag extends TagSupport {
  private String _maxlength;
  private String _name;
  private String _property;
  private String _size;
  private String _value;
  private String _onkeydown;
  private String _onblur;
  private String _style;
  private String _parentProperty;
  private String _otherCode;

  /**
   * Creates a new <code>OtherTextTag</code>.
   */
  public OtherTextTag() {
    super();
  }
  /**
   * Writes the text INPUT element to the HTTP response.
   */
  public int doStartTag() throws JspException {
    // Make sure name, parent property and other CUI are all specified.
    String name = getName();
    if (ApiFunctions.isEmpty(name)) {
      throw new ApiException("In OtherTextTag: name not specified");
    }

    String parentProperty = getParentProperty();
    if (ApiFunctions.isEmpty(parentProperty)) {
      throw new ApiException("In OtherTextTag: parentProperty not specified");
    }

    String otherCode = getOtherCode();
    if (ApiFunctions.isEmpty(otherCode)) {
      throw new ApiException("In OtherTextTag: otherCode not specified");
    }

    //disable this control unless the parent control has the "Other" choice selected
    boolean disable = true;
    String parentValue = null; 
    Object parentValueAsObject = RequestUtils.lookup(pageContext, name, parentProperty, null);
    if (parentValueAsObject != null) { 
      if (parentValueAsObject.getClass().isArray()) {
        parentValue = ((String[]) parentValueAsObject)[0];
      }
      else {
        parentValue = (String) parentValueAsObject;
      }
      if (otherCode.equals(parentValue)) {
        disable = false; 
      }
    }

    // Get the value of the other text INPUT input, if any.  If the control is disabled, the 
    // value should be N/A.
    String value = _value;
    if (disable) {
      value = "N/A";
    }
    else if (value == null) {
      value = (String) RequestUtils.lookup(pageContext, name, _property, null);
    }
    
    ResponseUtils.write(pageContext, "<input type=\"text\"");
    BigrTagUtils.writeAttribute(pageContext, "name", _property);
    BigrTagUtils.writeAttribute(pageContext, "value", value);
    BigrTagUtils.writeAttribute(pageContext, "size", _size);
    BigrTagUtils.writeAttribute(pageContext, "maxlength", _maxlength);
    BigrTagUtils.writeAttribute(pageContext, "onkeydown", _onkeydown);
    BigrTagUtils.writeAttribute(pageContext, "onblur", _onblur);
    BigrTagUtils.writeAttribute(pageContext, "style", _style);

    if (disable) {
      ResponseUtils.write(pageContext, " disabled");
    }
    
    ResponseUtils.write(pageContext, ">");

    return SKIP_BODY;
  }
  /**
   * Release all allocated resources.
   */
  public void release() {
    super.release();
    _maxlength = null;
    _name = null;
    _property = null;
    _size = null;
    _value = null;
    _onkeydown = null;
    _onblur = null;
    _style = null;
    _parentProperty = null;
    _otherCode = null;
  }
  /**
   * Sets the maximum length of the other text INPUT.  This is the value assigned to the
   * INPUT element's maxlength attribute.
   *
   * @param  length  the text INPUT maxlength
   */
  public void setMaxlength(String length) {
    _maxlength = length;
  }
  /**
   * Returns the name of the bean that contains the value that should be displayed
   * in the other text input.  If the name is not explictly specified on this tag, and this tag 
   * is nested in a bigr:cirDataElement or Struts html:form tag, the name will be obtained from
   * the ancestor tag.
   *
   * @param  name  the bean name
   */
  private String getName() {
    // Note that we want to pass back a a key into the page context, and not the actual
    // form/bean name.
    String name = _name;
    if (name == null) {
      if (pageContext.findAttribute(Constants.BEAN_KEY) != null) {  // from Struts html:form
        name = Constants.BEAN_KEY;
      }
    }
    return name;
    
  }
  /**
   * Sets the name of the bean that contains the value that should be displayed
   * in the other text input.  This is optional, and is overriden by the value
   * attribute.
   *
   * @param  name  the bean name
   */
  public void setName(String name) {
    _name = name;
  }
  /**
   * Sets the name of the other text INPUT.  This is the value assigned to the
   * INPUT element's name attribute, which is therefore also the name of
   * the request parameter.  In addition, if the name attribute of this
   * tag is also specified, then the property attribute is used to look 
   * up the value that should be set for the text input, from the 
   * bean specified by name.
   *
   * @param  property  the text INPUT name and bean property for determining the value 
   */
  public void setProperty(String property) {
    _property = property;
  }
  /**
   * Sets the size of the other text INPUT.  This is the value assigned to the
   * INPUT element's size attribute.
   *
   * @param  size  the text INPUT size 
   */
  public void setSize(String size) {
    _size = size;
  }
  /**
   * Sets the value that should be displayed in the other text input.  This is 
   * optional, and can instead be specified by a combination of the name and
   * property attributes.
   *
   * @param  value  the value
   */
  public void setValue(String value) {
    _value = value;
  }
  /**
   * Sets the onkeypress event that should be fired.  This is 
   * optional.
   *
   * @param  onkeypress the onkeypress
   */
  public void setOnkeydown(String onkeydown) {
    _onkeydown = onkeydown;
  }

  /**
   * Sets the onblur event that should be fired.  This is 
   * optional.
   *
   * @param  onblur the onblur
   */
  public void setOnblur(String onblur) {
    _onblur = onblur;
  }

  /**
   *
   * @param  style the style
   */
  public void setStyle(String style) {
    _style = style;
  }

  private String getOtherCode() {
    return _otherCode;    
  }

  /**
   * @param string
   */
  public void setOtherCode(String string) {
    _otherCode = string;
  }

  private String getParentProperty() {
    return _parentProperty;    
  }

  /**
   * @param string
   */
  public void setParentProperty(String string) {
    _parentProperty = string;
  }

}
