package com.ardais.bigr.javabeans;

import java.util.List;

/**
 * Holds all data for a single derivation operation, lazily creating sub-DTOs as requested.
 */
public class DerivationDtoForUi extends DerivationDto {

  public DerivationDtoForUi() {
    super();
  }

  /* (non-Javadoc)
   * @see com.ardais.bigr.javabeans.DerivationDto#getChild(int)
   */
  public SampleData getChild(int index) {
    List children = super.getChildren();
    for (int i = children.size(); i <= index; i++) {
      super.addChild(new SampleDataForUI()); 
    }
    children = super.getChildren();
    return (SampleData) children.get(index);
  }

  /* (non-Javadoc)
   * @see com.ardais.bigr.javabeans.DerivationDto#getParent(int)
   */
  public SampleData getParent(int index) {
    List parents = super.getParents();
    for (int i = parents.size(); i <= index; i++) {
      super.addParent(new SampleDataForUI()); 
    }
    parents = super.getParents();
    return (SampleData) parents.get(index);
  }

}
