package com.gulfstreambio.gboss;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.api.Escaper;

public abstract class GbossConcept implements Comparable {

  private String _cui = null;
  private String _vIntro = null;
  private String _vRevised = null;
  private String _description = null;
  private boolean _immutable = false;
  
  
  public String toHtml() {
    StringBuffer buff = new StringBuffer(50);
    buff.append(ApiFunctions.shortClassName(this.getClass().getName()));
    buff.append(": ");
    if (!ApiFunctions.isEmpty(getCui())) {
      buff.append("cui = ");
      buff.append(Escaper.htmlEscapeAndPreserveWhitespace(getCui()));
    }
    if (!ApiFunctions.isEmpty(getDescription())) {
      buff.append(", ");
      buff.append("description = ");
      buff.append(Escaper.htmlEscapeAndPreserveWhitespace(getDescription()));
    }
    if (!ApiFunctions.isEmpty(getVIntro())) {
      buff.append(", ");
      buff.append("vIntro = ");
      buff.append(Escaper.htmlEscapeAndPreserveWhitespace(getVIntro()));
    }
    if (!ApiFunctions.isEmpty(getVRevised())) {
      buff.append(", ");
      buff.append("vRevised = ");
      buff.append(Escaper.htmlEscapeAndPreserveWhitespace(getVRevised()));
    }
    return buff.toString();
  }
  
  /**
   * @return Returns the cui.
   */
  public String getCui() {
    return _cui;
  }
  
  /**
   * @param cui The cui to set.
   */
  public void setCui(String cui) {
    checkImmutable();
    _cui = cui;
  }
  
  /**
   * @return Returns the description.
   */
  public String getDescription() {
    return _description;
  }
  
  /**
   * @param description The description to set.
   */
  public void setDescription(String description) {
    checkImmutable();
    _description = description;
  }
  
  /**
   * @return Returns the vIntro.
   */
  public String getVIntro() {
    return _vIntro;
  }
  
  /**
   * @param intro The vIntro to set.
   */
  public void setVIntro(String intro) {
    checkImmutable();
    _vIntro = intro;
  }
  
  /**
   * @return Returns the vRevised.
   */
  public String getVRevised() {
    return _vRevised;
  }
  
  /**
   * @param revised The vRevised to set.
   */
  public void setVRevised(String revised) {
    checkImmutable();
    _vRevised = revised;
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
  void checkImmutable() {
    if (_immutable) {
      throw new IllegalStateException("Attempted to modify an immutable " + this.getClass().getName() + ": " + this);
    }
  }

  /* (non-Javadoc)
   * @see java.lang.Comparable#compareTo(java.lang.Object)
   * @return  a negative integer, zero, or a positive integer as this object
   *    is less than, equal to, or greater than the specified object.
   * 
   * @throws ClassCastException if the specified object's type prevents it
   *         from being compared to this Object.
   */
  public int compareTo(Object o) {
    GbossValue otherValue = (GbossValue)o;
    return getDescription().compareTo(otherValue.getDescription());
  }
}
