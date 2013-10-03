package com.ardais.bigr.web.taglib;

import javax.servlet.jsp.JspException;

/**
 * Tag handler for the notHasPrivilege tag.
 */
public class NotHasPrivilegeTag extends HasPrivilegeTag {

	/**
	 * Creates a new <code>NotHasPrivilegeTag</code>.
	 */
	public NotHasPrivilegeTag() {
		super();
	}

  /**
   */
  public int doStartTag() throws JspException {
  	int returnValue = super.doStartTag();
  	return (returnValue == SKIP_BODY) ? EVAL_BODY_BUFFERED : SKIP_BODY;
  }

}
