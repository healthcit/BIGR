package com.ardais.bigr.web.taglib;

import javax.servlet.jsp.JspException;

/**
 * Tag class for the <code>selectListWithOther</code> custom JSP tag.
 */
public class SelectListWithOtherTag extends SelectListTag {
  private String _otherCode;
  private String _otherProperty;
  /**
   * Creates a new <code>PdcLookupWithOtherTag</code>.
   */
  public SelectListWithOtherTag() {
    super();
  }
  /**
   * Writes the SELECT element to the HTTP response.
   */
  public int doStartTag() throws JspException {
    StringBuffer showOther = new StringBuffer(64);
    showOther.append("showOtherForList(this.value,'");
    showOther.append(_otherProperty);
    showOther.append("','");
    showOther.append(_otherCode);
    showOther.append("');");

    String onchange = getOnchange();
    if (onchange != null)
      showOther.append(onchange);
    setOnchange(showOther.toString());

    return super.doStartTag();
  }
  /**
   * Release all allocated resources.
   */
  public void release() {
    super.release();
    _otherCode = null;
    _otherProperty = null;
  }
  /**
   * Sets the "other" code.
   *
   * @param  code  the "other" code
   */
  public void setOtherCode(String code) {
    _otherCode = code;
  }
  /**
   * Sets the property that returns the "other" value.
   *
   * @param  other  the other property
   */
  public void setOtherProperty(String other) {
    _otherProperty = other;
  }
}
