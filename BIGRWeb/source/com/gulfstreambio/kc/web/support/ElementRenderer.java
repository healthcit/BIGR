package com.gulfstreambio.kc.web.support;

import javax.servlet.jsp.PageContext;

/**
 * An interface that defines methods to be called to render data elements.  KC UI services calls 
 * these methods to render elements within a KC form definition at the appropriate times.
 */
public interface ElementRenderer {

  /**
   * Renders the specified element for editing its value(s).  This method is responsible for 
   * writing its output to the response stream.
   * 
   * @param elementId  The identifier of the element.  For a KC data element this is its CUI;
   * for a host element this is its host id. 
   * @param formContext  The {@link FormContext}, which contains the element to be rendered along
   * with other relevant KC context. 
   * @param pageContext  The JSP <code>PageContext</code> in which the page containing the
   * specified element is being rendered.  The <code>PageContext</code> provides access to a
   * number of facilities, including the <code>JspWriter</code> for output, as well as the request, 
   * response and session.  
   */
  void renderForEdit(String elementId, FormContext formContext, PageContext pageContext);

  /**
   * Renders the specified element for providing a summary of its value(s).  This method is 
   * responsible for writing its output to the response stream.
   * 
   * @param elementId  The identifier of the element.  For a KC data element this is its CUI;
   * for a host element this is its host id. 
   * @param formContext  The {@link FormContext}, which contains the element to be rendered along
   * with other relevant KC context. 
   * @param pageContext  The JSP <code>PageContext</code> in which the page containing the
   * specified element is being rendered.  The <code>PageContext</code> provides access to a
   * number of facilities, including the <code>JspWriter</code> for output, as well as the request, 
   * response and session.  
   */
  void renderForSummary(String elementId, FormContext formContext, PageContext pageContext);
}
