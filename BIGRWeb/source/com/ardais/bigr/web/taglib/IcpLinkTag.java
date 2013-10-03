package com.ardais.bigr.web.taglib;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.security.SecurityInfo;
import com.ardais.bigr.util.IcpUtils;
import com.ardais.bigr.util.WebUtils;

/**
 * Render the tag contents as either plain text or as an ICP link, depending
 * on whether the user has the ICP privilege and whether the tag content is
 * the id for one of the object types that has an ICP page.
 */
public class IcpLinkTag extends org.apache.struts.taglib.html.BaseHandlerTag {
  private String _linkText;
  private String _popup;

  /**
   * Create an IcpLinkTag object.
   */
  public IcpLinkTag() {
    super();
  }

  public int doStartTag() throws JspTagException {
    return EVAL_BODY_BUFFERED;
  }

  public int doEndTag() throws JspException {

    SecurityInfo securityInfo =
      WebUtils.getSecurityInfo((HttpServletRequest) pageContext.getRequest());

    String trimmedContent = bodyContent.getString().trim();
    String linkText = _linkText;
    if (linkText == null) {
      linkText = trimmedContent;
    }
 
    boolean usePopup = (_popup != null && _popup.equals("true"));
    String icpLink = IcpUtils.prepareLink(trimmedContent, linkText, securityInfo, usePopup);

    try {
      // Render this element to our writer
      JspWriter writer = pageContext.getOut();

      writer.print(icpLink);
    }
    catch (IOException e) {
      ApiFunctions.throwAsRuntimeException(e);
    }

    return EVAL_PAGE;
  }

  public void release() {
    super.release();
    _linkText = null;
    _popup = null;
  }

  /**
   * Set to the text that will be displayed for the ICP link.  If this isn't set, the link
   * text will display the id (the body content).
   * 
   * @param linkText The link text.
   */
  public void setLinkText(String linkText) {
    _linkText = linkText;
  }

  /**
   * Set to "true" to make the ICP link open ICP in a popup window rather
   * than replacing the current page with the ICP page.
   * 
   * @param popup the "true"/"false" value
   */
  public void setPopup(String popup) {
    _popup = popup;
  }

}