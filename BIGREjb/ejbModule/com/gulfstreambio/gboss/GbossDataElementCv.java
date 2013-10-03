package com.gulfstreambio.gboss;

import java.util.Iterator;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.api.Escaper;
import com.gulfstreambio.kc.det.DetElementDatatypes;

public class GbossDataElementCv extends GbossDataElement {

  private String _multiplicity;
  private String _broadValueSetRef;
  private GbossValueSet _broadValueSet;
  private String _nonAtvValueSetRef;
  private GbossValueSet _nonAtvValueSet;
  private String _otherValueCui;
  private String _noValueCui;
  
  /**
   * Create a new GbossDataElementCv object.
   */
  public GbossDataElementCv() {
    super();
  }
  
  public String getDatatype() {
   return DetElementDatatypes.CV; 
  }
  
  public String toHtml() {
    StringBuffer buff = new StringBuffer(100);
    buff.append(super.toHtml());
    if (!ApiFunctions.isEmpty(getMultiplicity())) {
      buff.append(", ");
      buff.append("multiplicity = ");
      buff.append(Escaper.htmlEscapeAndPreserveWhitespace(getMultiplicity()));
    }
    if (getBroadValueSet() != null) {
      buff.append(", ");
      buff.append("broad value set = ");
      buff.append(Escaper.htmlEscapeAndPreserveWhitespace(getBroadValueSet().getDescription()));
      buff.append(" (");
      buff.append(getBroadValueSet().getCui());
      buff.append(")");
    }
    if (getNonAtvValueSet() != null) {
      buff.append(", ");
      buff.append("nonATV value set = ");
      buff.append(Escaper.htmlEscapeAndPreserveWhitespace(getNonAtvValueSet().getDescription()));
      buff.append(" (");
      buff.append(getNonAtvValueSet().getCui());
      buff.append(")");
    }
    return buff.toString();
  }
  
  /**
   * @return Returns the multiplicity.
   */
  public String getMultiplicity() {
    return _multiplicity;
  }

  /**
   * @param multiplicity The multiplicity to set.
   */
  public void setMultiplicity(String multiplicity) {
    checkImmutable();
    _multiplicity = multiplicity;
  }
  
  /**
   * @return Returns multiplicity as boolean.
   */
  public boolean isMultiValued() {
   if (!(getMultiplicity().equalsIgnoreCase("1"))) 
   	return true;
   else
    return false;
   }
  
  public String getOtherValueCui() {
    return _otherValueCui;
  }
  
  public String getNoValueCui() {
    return _noValueCui;
  }

  /**
   * @return Returns the broadValueSet.
   */
  public GbossValueSet getBroadValueSet() {
    return _broadValueSet;
  }
  
  /**
   * @param broadValueSet The broadValueSet to set.
   */
  public void setBroadValueSet(GbossValueSet broadValueSet) {
    checkImmutable();
    _broadValueSet = broadValueSet;
    
    // Get the other and no value CUIs from the broad value set. 
    Iterator values = broadValueSet.depthFirstIterator();
    while (values.hasNext()) {
      GbossValue value = (GbossValue) values.next();
      if (value.isOtherValue()) {
        _otherValueCui = value.getCui();
      }
      if (value.isNoValue()) {
        _noValueCui = value.getCui();
      }
    }
  }
  
  /**
   * @return Returns the broadValueSetRef.
   */
  public String getBroadValueSetRef() {
    return _broadValueSetRef;
  }
  
  /**
   * @param broadValueSetRef The broadValueSetRef to set.
   */
  public void setBroadValueSetRef(String broadValueSetRef) {
    checkImmutable();
    _broadValueSetRef = broadValueSetRef;
  }
  
  /**
   * @return Returns the nonAtvValueSet.
   */
  public GbossValueSet getNonAtvValueSet() {
    return _nonAtvValueSet;
  }
  
  /**
   * @param nonAtvValueSet The nonAtvValueSet to set.
   */
  public void setNonAtvValueSet(GbossValueSet nonAtvValueSet) {
    checkImmutable();
    _nonAtvValueSet = nonAtvValueSet;
  }
  
  /**
   * @return Returns the nonAtvValueSetRef.
   */
  public String getNonAtvValueSetRef() {
    return _nonAtvValueSetRef;
  }
  
  /**
   * @param nonAtvValueSetRef The nonAtvValueSetRef to set.
   */
  public void setNonAtvValueSetRef(String nonAtvValueSetRef) {
    checkImmutable();
    _nonAtvValueSetRef = nonAtvValueSetRef;
  }
  
  /**
   * Set this instance to be immutable.  Any attempts to modify immutable
   * instances will throw an exception.
   */
  public void setImmutable() {
    super.setImmutable();
    if (_broadValueSet != null) {
      _broadValueSet.setImmutable();
    }
    if (_nonAtvValueSet != null) {
      _nonAtvValueSet.setImmutable();
    }
  }
  
  public boolean isMlvsEnabled() {
    GbossValueSet vs = getBroadValueSet();
    Iterator i = vs.getValues().iterator();
    while (i.hasNext()) {
      GbossValue v = (GbossValue) i.next();
      if (v.getValues().size() > 0) {
        return true;
      }
    }
    return false;
  }
}
