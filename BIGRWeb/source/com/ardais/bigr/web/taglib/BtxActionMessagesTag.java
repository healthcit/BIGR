package com.ardais.bigr.web.taglib;

import java.util.Iterator;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

import org.apache.struts.taglib.html.ErrorsTag;
import org.apache.struts.util.RequestUtils;
import org.apache.struts.util.ResponseUtils;

import com.ardais.bigr.iltds.btx.BtxActionMessage;
import com.ardais.bigr.iltds.btx.BtxActionMessages;
import com.ardais.bigr.web.action.BigrAction;

/**
 * @author jesielionis
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class BtxActionMessagesTag extends ErrorsTag {
  
  /**
   * The request attribute key for our error messages (if any).
   */
  protected String name = BigrAction.BTX_MESSAGES_KEY;

  /**
   * Constructor for BtxActionMessagesTag.
   */
  public BtxActionMessagesTag() {
    super();
  }
  
  /**
   * Render the specified error messages if there are any.
   *
   * @exception JspException if a JSP exception has occurred
   */
  public int doStartTag() throws JspException {

    // Were any messages specified?
    BtxActionMessages btxMessages = new BtxActionMessages();
    try {
      Object value = pageContext.getAttribute(name, PageContext.REQUEST_SCOPE);
      if (value == null) {
        ;
      }
      else if (value instanceof String) {
        btxMessages.add(new BtxActionMessage((String) value));
      }
      else if (value instanceof BtxActionMessages) {
        btxMessages = (BtxActionMessages) value;
      }
      else {
        JspException e =
          new JspException(messages.getMessage("errorsTag.errors", value.getClass().getName()));
        RequestUtils.saveException(pageContext, e);
        throw e;
      }
    }
    catch (Exception e) {
      ;
    }
    if (btxMessages.empty())
      return (EVAL_BODY_INCLUDE);

    // Check for presence of header and footer message keys
    boolean headerPresent = RequestUtils.present(pageContext, bundle, locale, "btx.messages.header");
    boolean footerPresent = RequestUtils.present(pageContext, bundle, locale, "btx.messages.footer");

    // Render the error messages appropriately
    StringBuffer results = new StringBuffer();
    String message = null;
    if (headerPresent)
      message = RequestUtils.message(pageContext, bundle, locale, "btx.messages.header");
    Iterator reports = null;
    reports = btxMessages.get();
    // Render header iff there is a message
    boolean msgPresent = reports.hasNext();
    if ((message != null) || msgPresent) {
      results.append(message);
      results.append("\r\n");
    }

    while (reports.hasNext()) {
      BtxActionMessage report = (BtxActionMessage) reports.next();
      message =
        RequestUtils.message(pageContext, bundle, locale, report.getKey(), report.getValues());
      if (message != null) {
        results.append(message);
        results.append("\r\n");
      }
    }
    message = null;
    if (footerPresent)
      message = RequestUtils.message(pageContext, bundle, locale, "btx.messages.footer");

    if ((message != null) || msgPresent) {
      results.append(message);
      results.append("\r\n");
    }

    // Print the results to our output writer
    ResponseUtils.write(pageContext, results.toString());

    // Continue processing this page
    return (EVAL_BODY_INCLUDE);

  }

}
