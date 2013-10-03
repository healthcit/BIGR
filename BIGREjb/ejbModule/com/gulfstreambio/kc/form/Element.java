package com.gulfstreambio.kc.form;

import java.util.Iterator;

public interface Element {
  public String getCuiOrSystemName();
  public ElementValue createElementValue();
  public boolean isElementValueExists(int index);  
  public ElementValue getElementValue(int index);
  public ElementValue[] getElementValues();
}
