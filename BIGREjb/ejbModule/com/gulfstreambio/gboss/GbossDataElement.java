package com.gulfstreambio.gboss;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.api.Escaper;

public abstract class GbossDataElement extends GbossElement {

  private String _adeRef = null;
  private GbossAde _ade = null;
  private GbossCategory _parentCategory;
  
  // must be implemented in concrete classes
  public abstract String getDatatype();
  
  // return default values; override in concrete class if appropriate
  public boolean isMaximumInclusive() {
   return false; 
  }
  
  public boolean isMinimumInclusive() {
    return false; 
   }
  
  public boolean isMlvsEnabled() {
    return false; 
   }
  
  public boolean isMultiValued() {
    return false; 
   }
  
  public String getMaxValue() {
   return null; 
  }
  
  public String getMinValue() {
    return null; 
   }
  
  public String getNoValueCui() {
    return null; 
   }
  
  public String getOtherValueCui() {
    return null; 
   }
  
  public String getUnitCui() {
    return null; 
   }
  
  public GbossValueSet getBroadValueSet() {
    return (new GbossValueSet());
  }
  
  public GbossValueSet getNonAtvValueSet() {
    return (new GbossValueSet());
  }
  
  public String toHtml() {
    StringBuffer buff = new StringBuffer(100);
    buff.append(super.toHtml());
    if (getAde() != null) {
      buff.append(", ");
      buff.append("ade = ");
      buff.append(Escaper.htmlEscapeAndPreserveWhitespace(getAde().getDescription()));
      buff.append(" (");
      buff.append(getAde().getCui());
      buff.append(")");
    }
    return buff.toString();
  }
  
  /**
   * @return Returns the parentCategory.
   */
  public GbossCategory getParentCategory() {
    return _parentCategory;
  }
  
  /**
   * @param parentCategory The parentCategory to set.
   */
  public void setParentCategory(GbossCategory parentCategory) {
    checkImmutable();
    _parentCategory = parentCategory;
  }

  /**
   * @return Returns the ancestor category that is the database category.
   */
  public GbossCategory getDatabaseCategory() {
    GbossCategory parent = _parentCategory;
    while (parent != null) {
      if (!ApiFunctions.isEmpty(parent.getDatabaseType())) {
        return parent;
      }
      parent = parent.getParentCategory();
    }
    
    // We should never get here since validation ensures that each data element has exactly
    // one database category ancestor, so if we do get here throw an exception.
    throw new ApiException("Could not determine database category for data element " + getCui());
  }
  
  /**
   * @return Returns the ade.
   */
  public GbossAde getAde() {
    return _ade;
  }
  
  /**
   * @param ade The ade to set.
   */
  public void setAde(GbossAde ade) {
    checkImmutable();
    _ade = ade;
  }
  
  /**
   * @return Returns the adeRef.
   */
  public String getAdeRef() {
    return _adeRef;
  }
  
  /**
   * @param adeRef The adeRef to set.
   */
  public void setAdeRef(String adeRef) {
    checkImmutable();
    _adeRef = adeRef;
  }
  
  /**
   * Set this instance to be immutable.  Any attempts to modify immutable
   * instances will throw an exception.
   */
  public void setImmutable() {
    super.setImmutable();
    if (_ade != null) {
      _ade.setImmutable();
    }
  }
}
