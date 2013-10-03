package com.ardais.bigr.web.taglib;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTagSupport;

import com.ardais.bigr.security.SecurityInfo;
import com.ardais.bigr.util.WebUtils;

/**
 * Tag handler for the hasPrivilege tag.
 */
public class HasPrivilegeTag extends BodyTagSupport {

	private String _priv;
	
	/**
	 * Creates a new <code>HasPrivilegeTag</code>.
	 */
	public HasPrivilegeTag() {
		super();
	}

  /**
   * Sets the privilege to be checked.
   * 
   * @param  priv  the privilege
   */
  public void setPriv(String priv) {
    _priv = priv;
  }
	
  /**
   */
  public int doStartTag() throws JspException {
		SecurityInfo securityInfo = 
			WebUtils.getSecurityInfo((HttpServletRequest)pageContext.getRequest());
		if (securityInfo.isHasPrivilege(_priv)) {
	    return EVAL_BODY_BUFFERED;
		}
		else {
	    return SKIP_BODY;
		}
  }

  /**
   */
  public int doAfterBody() throws JspException {
    BodyContent body = getBodyContent();
    if (body != null) {
      JspWriter out = body.getEnclosingWriter();
      try {
         out.write(body.getString());
      } catch (IOException e) {
        throw new JspException("IOException in HasPrivilegeTag.doAfterBody");
      }
    }
    return SKIP_BODY;
  }

  /**
   * Release all allocated resources.
   */
  public void release() {
    super.release();
	  _priv = null;
  }

}
