package com.ardais.bigr.iltds.databeans;

import java.io.Serializable;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.api.Escaper;
import com.ardais.bigr.util.BigrXmlUtils;
import com.gulfstreambio.gboss.GbossFactory;

public class StorageType implements Serializable {
  static final long serialVersionUID = -7752469571153567683L;
  
  private String _cui;
  private String _cuiDescription;
  private boolean _immutable = false;

  public StorageType() {
    super();
  }

  /**
   * Convert the attribute object into XML.
   * 
   * @return The XML representation of the storage type object.
   */
  public String toXml() {
    StringBuffer xml = new StringBuffer();
    
    xml.append("<storageType");
    
    BigrXmlUtils.writeAttribute(xml, "cui", getCui());
    BigrXmlUtils.writeAttribute(xml, "cuiDescription", getCuiDescription());
    
    xml.append("/>\n");
   
    return xml.toString();
  }
  
  /**
   * 
   * @return
   */
  public String toHtml() {
    StringBuffer html = new StringBuffer();
    html.append(Escaper.htmlEscapeAndPreserveWhitespace(getCuiDescription()));
    return html.toString();
  }

  /**
   * @return
   */
  public String getCui() {
    return _cui;
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
      throw new IllegalStateException("Attempted to modify an immutable StorageType: " + this);
    }
  }

}
