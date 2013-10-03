package com.ardais.bigr.web.taglib;

import javax.servlet.jsp.JspException;

/**
 */
public class DebugDisabledTag extends DebugEnabledTag {

	/**
	 * Creates a new <code>DebugDisabledTag</code>.
	 */
	public DebugDisabledTag() {
		super();
	}

  /**
   */
  public int doStartTag() throws JspException {
  	int returnValue = super.doStartTag();
  	return (returnValue == SKIP_BODY) ? EVAL_BODY_BUFFERED : SKIP_BODY;
  }

}
