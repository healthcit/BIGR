package com.gulfstreambio.gboss;


public abstract class GbossAdeElement extends GbossElement {

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
    
}
