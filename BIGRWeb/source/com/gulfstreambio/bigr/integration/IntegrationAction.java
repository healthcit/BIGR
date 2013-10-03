package com.gulfstreambio.bigr.integration;

import javax.servlet.http.HttpSession;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.web.action.BigrAction;

/**
 * Provides common functionality for actions that involve integration with other systems.
 */
public abstract class IntegrationAction extends BigrAction {

  protected final static String SESSION_ATTRIBUTE_PREFIX = "com.gulfstreambio.integration"; 
  
  /**
   * Returns a string that uniquely identifies the integration partner.  This should be a simple 
   * string such as "xyzcorp".  The string returned by this method will be prefixed with 
   * {@link #SESSION_ATTRIBUTE_PREFIX} to generate the full namespace under which session 
   * attributes will be saved.  
   *     
   * @return  The partner name. 
   */
  protected abstract String getPartnerName();
  
  /**
   * Sets the specified object as a session attribute under the specified name in the integration 
   * namespace. 
   * 
   * @param session the HTTP session
   * @param name the attribute name
   * @param value the attribute value
   */
  public void setSessionAttribute(HttpSession session, String name, Object value) {
    session.setAttribute(getSessionAttributeQualifiedName(name), value);
  }

  /**
   * Returns the value of the specified session attribute from the integration namespace.
   * 
   * @param session the HTTP session
   * @param name the attribute name
   * @return The attribute value.
   */
  public Object getSessionAttribute(HttpSession session, String name) {
    return session.getAttribute(getSessionAttributeQualifiedName(name));    
  }
  
  private String getSessionAttributeQualifiedName(String name) {
    String partnerName = getPartnerName();
    return (ApiFunctions.isEmpty(partnerName)) 
        ? SESSION_ATTRIBUTE_PREFIX + name
        : SESSION_ATTRIBUTE_PREFIX + "." + partnerName + "." + name;    
  }
}
