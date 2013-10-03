package com.ardais.bigr.configuration;

import java.io.Serializable;

public class LabelTemplate implements Serializable {
  private String _fullyQualifiedName;
  private String _labelTemplateDefinitionName;
  private LabelPrinter _parent;
  private boolean _immutable = false;
  
  /**
   * @return Returns the fullyQualifiedName.
   */
  public String getFullyQualifiedName() {
    return _fullyQualifiedName;
  }

  /**
   * @return Returns the labelTemplateDefinitionName.
   */
  public String getLabelTemplateDefinitionName() {
    return _labelTemplateDefinitionName;
  }
  
  LabelPrinter getParent() {
    return _parent;
  }
  
  /**
   * @param fullyQualifiedName The fullyQualifiedName to set.
   */
  public void setFullyQualifiedName(String fullyQualifiedName) {
    checkImmutable();
    _fullyQualifiedName = fullyQualifiedName;
  }

  /**
   * @param labelTemplateDefinitionName The labelTemplateDefinitionName to set.
   */
  public void setLabelTemplateDefinitionName(String labelTemplateDefinitionName) {
    checkImmutable();
    _labelTemplateDefinitionName = labelTemplateDefinitionName;
  }
  
  void setParent(LabelPrinter parent) {
    checkImmutable();
    _parent = parent;
  }
  
  /**
   * Throw an exception if this instance is immutable.
   */
  private void checkImmutable() {
    if (_immutable) {
      throw new IllegalStateException("Attempted to modify an immutable LabelTemplate: " + this);
    }
  }
  
  /**
   * Mark this instance as immutable.
   */
  public void setImmutable() {
    _immutable = true;
  }
  
}
