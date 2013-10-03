package com.ardais.bigr.web.taglib;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * <code>ChildPropertyTag</code> is the tag handler for the <code>childProperty</code> tag.
 */
public class ChildPropertyTag extends TagSupport {
  private String _property;
  /**
   * Creates a new <code>ChildPropertyTag</code>.
   */
  public ChildPropertyTag() {
    super();
  }
  /**
   */
  public int doEndTag() throws JspException {
    Tag parent = TagSupport.findAncestorWithClass(this, ChildPropertyParent.class);
    if (parent == null) {
      throw new JspException("The childProperty tag is not enclosed in a supported parent tag");
    }
    ChildPropertyParent childPropertyParent = (ChildPropertyParent) parent;
    childPropertyParent.addChildProperty(_property);

    return EVAL_PAGE;
  }
  public void release() {
    _property = null;
    super.release();
  }
  /**
   * Sets the child property.
   *
   * @param  property  the child property
   */
  public void setProperty(String property) {
    _property = property;
  }
}
