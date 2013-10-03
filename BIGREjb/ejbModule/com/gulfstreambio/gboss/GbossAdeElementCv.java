package com.gulfstreambio.gboss;

import java.util.Iterator;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.api.Escaper;
import com.gulfstreambio.kc.det.DetElementDatatypes;

public class GbossAdeElementCv extends GbossAdeElement {

  private String _multiplicity;
  private String _broadValueSetRef;
  private GbossValueSet _broadValueSet;
  private String _otherValueCui;
  private String _noValueCui;
  
  /**
   * Create a new GbossAdeElementCv object.
   */
  public GbossAdeElementCv() {
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
    return buff.toString();
  }
  
  /**
   * @return Returns the multiplicity.
   */
  public String getMultiplicity() {
    return _multiplicity;
  }
  
  /**
   * @return Returns multiplicity as boolean.
   */
  public boolean isMultiValued() {
   if (getMultiplicity() != null ) {
       if (!(getMultiplicity().equalsIgnoreCase("1"))) 
        return true;
       else
        return false;
       }
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
   * @param multiplicity The multiplicity to set.
   */
  public void setMultiplicity(String multiplicity) {
    checkImmutable();
    _multiplicity = multiplicity;
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
   * Set this instance to be immutable.  Any attempts to modify immutable
   * instances will throw an exception.
   */
  public void setImmutable() {
    super.setImmutable();
    if (_broadValueSet != null) {
      _broadValueSet.setImmutable();
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
