package com.gulfstreambio.kc.form.def.data;

import java.io.Serializable;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.util.BigrXmlUtils;
import com.gulfstreambio.kc.form.def.FormDefinition;
import com.gulfstreambio.kc.form.def.FormDefinitionHostElementBase;
import com.gulfstreambio.kc.form.def.Tag;

public class DataFormDefinitionHostElement 
  extends FormDefinitionHostElementBase implements Serializable {

  private boolean _required = false;
  private DataFormDefinition _form;
  private int _xmlIndentLevel = 0;

  public DataFormDefinitionHostElement() {
    super();
  }

  public DataFormDefinitionHostElement(DataFormDefinitionHostElement hostElement) {
    super(hostElement);
    setRequired(hostElement.isRequired());
  }

  /* (non-Javadoc)
   * @see com.gulfstreambio.kc.form.def.FormDefinitionHostElement#getForm()
   */
  public FormDefinition getForm() {
    return getDataForm();
  }

  public DataFormDefinition getDataForm() {
    return _form;
  }

  void setDataForm(DataFormDefinition form) {
    _form = form;
  }

  /**
   * Returns whether a value is required by the host for this host element. 
   * 
   * @return  true if a value is required; false otherwise.
   */
  public boolean isRequired() {
    return _required;
  }

  /**
   * Sets whether a value is required by the host for this host element.
   *  
   * @param  required  true if a value is required; false otherwise.
   */
  public void setRequired(boolean required) {
    _required = required;
  }

  public String toXml() {
    StringBuffer buf = new StringBuffer(1024);
    toXml(buf);
    return buf.toString();
  }
 
  public void toXml(StringBuffer buf) {
    int indent = getXmlIndentLevel();
    BigrXmlUtils.writeElementStartTag(buf, "hostElement", indent);
    BigrXmlUtils.writeAttribute(buf, "hostId", getHostId());
    BigrXmlUtils.writeAttribute(buf, "required", (isRequired() ? "true" : "false"));
    String displayName = getDisplayName();
    if (!ApiFunctions.isEmpty(displayName)) {
      BigrXmlUtils.writeAttribute(buf, "displayName", displayName);
    }
    buf.append(">");
    
    int childIndent = indent + 1;
    Tag[] tags = getTags();
    for (int i = 0; i < tags.length; i++) {
      Tag tag = tags[i];
      tag.setXmlIndentLevel(childIndent);
      tag.toXml(buf);
    }
    BigrXmlUtils.writeElementEndTag(buf, "hostElement", indent);
  }

  private int getXmlIndentLevel() {
    return _xmlIndentLevel;
  }

  void setXmlIndentLevel(int indentLevel) {
    _xmlIndentLevel = indentLevel;
  }
    
  /**
   * Implements {@link java.lang.Object#equals(java.lang.Object) equals}.
   */
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof DataFormDefinitionHostElement)) {
      return false;
    }
    
    DataFormDefinitionHostElement de = (DataFormDefinitionHostElement) obj;
        
    boolean result = true;
    String hostId = getHostId();
    String deHostId = de.getHostId();
    String displayName = getDisplayName();
    String deDisplayName = de.getDisplayName();
    result = result && ((hostId == null) ? deHostId == null : hostId.equals(deHostId));  
    result =
      result && ((displayName == null) ? deDisplayName == null : displayName.equals(deDisplayName));
    result = result && (_required == de._required);
    result = result && (getTags().equals(de.getTags()));  
    return result;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  public int hashCode() {
    int result = 17;
    
    String hostId = getHostId();
    String displayName = getDisplayName();
    result = 37*result + (_required ? 0 : 1);    
    result = 37*result + ((hostId == null) ? 0 : hostId.hashCode());
    result = 37*result + ((displayName == null) ? 0 : displayName.hashCode());
    result = 37*result + getTags().hashCode();
    
    return result;
  }
}
