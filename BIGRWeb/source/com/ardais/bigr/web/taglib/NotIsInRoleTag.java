package com.ardais.bigr.web.taglib;

import javax.servlet.jsp.JspException;

/**
 * Tag handler for the <code>notIsInRole</code> tag.
 */
public class NotIsInRoleTag extends IsInRoleTag {

	/**
	 * Creates a new <code>NotIsInRoleTag</code>.
	 */
	public NotIsInRoleTag() {
		super();
	}

  /**
   */
  public int doStartTag() throws JspException {
  	int returnValue = super.doStartTag();
  	return (returnValue == SKIP_BODY) ? EVAL_BODY_BUFFERED : SKIP_BODY;
  }

}
