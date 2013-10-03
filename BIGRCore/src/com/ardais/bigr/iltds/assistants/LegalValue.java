package com.ardais.bigr.iltds.assistants;

import java.io.Serializable;

/**
 */
public class LegalValue implements Serializable {
	private String _value;
	private String _displayValue;

  /**
   * Create a <code>LegalValue</code>.
   */
  public LegalValue() {
    super();
  }

/**
 * Create a <code>LegalValue</code> with the specified value.  The
 * value also serves as the display value.
 *
 * @param  value  the value
 */
public LegalValue(String value) {
	this();
	_value = value;
	_displayValue = value;
}
/**
 * Create a <code>LegalValue</code> with the specified value and
 * display value.
 *
 * @param  value  the value
 * @param  value  the display value
 */
public LegalValue(String value, String displayValue) {
	this();
	_value = value;
	_displayValue = displayValue;
}
public String getDisplayValue() { 
	return _displayValue;
}

public void setDisplayValue(String displayValue) { 
  _displayValue = displayValue;
}

public String getValue() { 
	return _value;
}

public void setValue(String value) { 
  _value = value;
}
}
