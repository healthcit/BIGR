package com.gulfstreambio.gboss;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.api.Escaper;

public abstract class GbossElement extends GbossConcept {

  private String _longDescription = null;
  private String _comment = null;
  private String _systemName = null;
  
  public String toHtml() {
    StringBuffer buff = new StringBuffer(100);
    buff.append(super.toHtml());
    if (!ApiFunctions.isEmpty(getLongDescription())) {
      buff.append(", ");
      buff.append("long description = ");
      buff.append(Escaper.htmlEscapeAndPreserveWhitespace(getLongDescription()));
    }
    if (!ApiFunctions.isEmpty(getComment())) {
      buff.append(", ");
      buff.append("comment = ");
      buff.append(Escaper.htmlEscapeAndPreserveWhitespace(getComment()));
    }
    if (!ApiFunctions.isEmpty(getSystemName())) {
      buff.append(", ");
      buff.append("system name = ");
      buff.append(Escaper.htmlEscapeAndPreserveWhitespace(getSystemName()));
    }
    return buff.toString();
  }
  
  /**
   * @return Returns the comment.
   */
  public String getComment() {
    return _comment;
  }
  
  /**
   * @param comment The comment to set.
   */
  public void setComment(String comment) {
    checkImmutable();
    _comment = comment;
  }
  
  /**
   * @return Returns the longDescription.
   */
  public String getLongDescription() {
    return _longDescription;
  }
  
  /**
   * @param longDescription The longDescription to set.
   */
  public void setLongDescription(String longDescription) {
    checkImmutable();
    _longDescription = longDescription;
  }
  
  /**
   * @return Returns the systemName.
   */
  public String getSystemName() {
    return _systemName;
  }
  
  /**
   * @param systemName The systemName to set.
   */
  public void setSystemName(String systemName) {
    checkImmutable();
    _systemName = systemName;
  }
}
