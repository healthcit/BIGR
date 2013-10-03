package com.ardais.bigr.iltds.databeans;

import java.io.Serializable;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.api.Escaper;
import com.gulfstreambio.gboss.GbossFactory;

public class Attribute implements Serializable {
  static final long serialVersionUID = -887580661934213048L;
  
  private String _cui;
  private String _cuiDescription;
  private String _relevance;
  private boolean _immutable = false;

  public Attribute() {
    super();
  }
  
  /**
   * 
   * @return
   */
  public String toHtml() {
    StringBuffer html = new StringBuffer();
    html.append(Escaper.htmlEscapeAndPreserveWhitespace(getCuiDescription()));
    html.append(": ");
    html.append(Escaper.htmlEscapeAndPreserveWhitespace(getRelevance()));
    return html.toString();
  }

  /**
   * @return
   */
  public String getCui() {
    return _cui;
  }

  /**
   * @return
   */
  public String getRelevance() {
    return _relevance;
  }

  /**
   * @param string
   */
  public void setCui(String string) {
    checkImmutable();
    _cui = string;
    String cuiDescription = determineCuiDescription();
    if (!ApiFunctions.isEmpty(cuiDescription)) {
      setCuiDescription(cuiDescription);
    }
  }
  
  private String determineCuiDescription() {
    String description = null;
    try {
      description = GbossFactory.getInstance().getDescription(getCui());
    }
    catch (Exception e) {
      //couldn't determine the description, so nothing to do
    }
    return description;
  }

  /**
   * @param string
   */
  public void setRelevance(String string) {
    checkImmutable();
    _relevance = string;
  }

  /**
   * @return
   */
  public String getCuiDescription() {
    return _cuiDescription;
  }

  /**
   * @param string
   */
  public void setCuiDescription(String string) {
    checkImmutable();
    _cuiDescription = string;
  }

  /**
   * Set this instance to be immutable.  Any attempts to modify immutable
   * instances will throw an exception.
   */
  public void setImmutable() {
    _immutable = true;
  }

  /**
   * Is this instance immutable.
   */
  public boolean isImmutable() {
    return _immutable;
  }

  /**
   * Throw an exception if this instance is immutable.
   */
  private void checkImmutable() {
    if (_immutable) {
      throw new IllegalStateException("Attempted to modify an immutable Attribute: " + this);
    }
  }

}
