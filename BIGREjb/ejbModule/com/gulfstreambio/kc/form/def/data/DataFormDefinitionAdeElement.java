package com.gulfstreambio.kc.form.def.data;

import java.io.Serializable;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.util.BigrXmlUtils;
import com.gulfstreambio.kc.form.def.FormDefinition;

/**
 * Represents a single ADE element definition in a KnowledgeCapture {@link DataFormDefinition}.
 */
public class DataFormDefinitionAdeElement implements Serializable {

  private DataFormDefinition _form;
  private String _cui;
  private String _displayName;
  private int _xmlIndentLevel = 0;
  
  /**
   * Creates a new <code>DataFormDefinitionAdeElement</code>.
   */
  public DataFormDefinitionAdeElement() {
    super();
  }

  public DataFormDefinitionAdeElement(DataFormDefinitionAdeElement adeElement) {
    this();
    setCui(adeElement.getCui());
    setDisplayName(adeElement.getDisplayName());
  }

  /**
   * Returns the CUI of this ADE element.
   * 
   * @return  The CUI.
   */
  public String getCui() {
    return _cui;
  }

  /**
   * Sets the CUI of this ADE element.
   * 
   * @param  cui  the CUI
   */
  public void setCui(String cui) {
    _cui = cui;
  }

  /**
   * Returns the display name of this ADE element.
   * 
   * @return  The display name.
   */
  public String getDisplayName() {
      return _displayName;      
  }

  /**
   * Sets the display name of this ADE element.
   * 
   * @param  displayName  the display name
   */
  public void setDisplayName(String displayName) {
    _displayName = displayName;
  }

  public FormDefinition getForm() {
    return getDataForm();
  }

  public DataFormDefinition getDataForm() {
    return _form;
  }

  void setDataForm(DataFormDefinition form) {
    _form = form;
  }

  public String toXml() {
    StringBuffer buf = new StringBuffer(1024);
    toXml(buf);
    return buf.toString();
  }
 
  public void toXml(StringBuffer buf) {
    int indent = getXmlIndentLevel();
    BigrXmlUtils.writeElementStartTag(buf, "adeElementDefinition", indent);
    BigrXmlUtils.writeAttribute(buf, "cui", getCui());
    String displayName = getDisplayName();
    if (!ApiFunctions.isEmpty(displayName)) {
      BigrXmlUtils.writeAttribute(buf, "displayName", displayName);
    }
    buf.append("/>");
  }

  private int getXmlIndentLevel() {
    return _xmlIndentLevel;
  }

  void setXmlIndentLevel(int indentLevel) {
    _xmlIndentLevel = indentLevel;
  }  
}
