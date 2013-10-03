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
 * Tag handler for the <code>isInRole</code> tag.
 */
public class IsInRoleTag extends BodyTagSupport {

	private String _role1;
	private String _role2;

	/**
	 * Creates a new <code>IsInRoleTag</code>.
	 */
	public IsInRoleTag() {
		super();
	}

  /**
   * Sets the first role to be checked.
   * 
   * @param  roleName  the name of the role
   */
  public void setRole1(String roleName) {
    _role1 = roleName;
  }
	
  /**
   * Sets the second role to be checked.
   * 
   * @param  roleName  the name of the role
   */
  public void setRole2(String roleName) {
    _role2 = roleName;
  }

  /**
   */
  public int doStartTag() throws JspException {
		SecurityInfo securityInfo = 
			WebUtils.getSecurityInfo((HttpServletRequest)pageContext.getRequest());
		if (securityInfo.isInRole(_role1)
				|| ((_role2 != null) && (securityInfo.isInRole(_role2)))) {
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
        throw new JspException("IOException in IsInRoleTag.doAfterBody");
      }
    }
    return SKIP_BODY;
  }

  /**
   * Release all allocated resources.
   */
  public void release() {
    super.release();
	  _role1 = null;
	  _role2 = null;
  }

}
