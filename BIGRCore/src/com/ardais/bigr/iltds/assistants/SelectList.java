package com.ardais.bigr.iltds.assistants;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Set;

import com.ardais.bigr.api.Escaper;

/**
 * <code>SelectList</code> generates HTML for a SELECT element
 * with OPTION elements.  The OPTIONs to put in the SELECT are
 * created from the <code>LegalValueSet</code> that must be specified.
 *
 */
public class SelectList {

	private LegalValueSet _legalValueSet;
	private String _id;
	private String _selectedValue;
  private String[] _selectedValues;
	private int _selectedIndex = -1;
	private String _events;
	private String _styles;
	private String _firstValue;
	private String _firstDisplayValue;
  private Set _excludedValues;
  private String _multiple;
  private String _size;
/**
 * SelectList constructor comment.
 */
public SelectList() {
	super();
}
public SelectList(String id) {
	this();
	_id = id;
}
/**
 * Insert the method's description here.
 * Creation date: (10/01/2001 12:58:01 PM)
 * @param id java.lang.String
 * @param lvs com.ardais.bigr.iltds.assistants.LegalValueSet
 */
public SelectList(String id, LegalValueSet legalValueSet) {
	this();
	_id = id;
	_legalValueSet = legalValueSet;
}
public String generate() {
	StringBuffer buf = new StringBuffer(256);
	generate(buf);
    return buf.toString();
}
public void generate(StringBuffer buf) {

	// Create the SELECT element.
	buf.append("<select");
	if (_id != null) {
		buf.append(" name=\"");
		buf.append(_id);
		buf.append("\"");
	}
	if (_events != null) {
		buf.append(_events);
		
	}
	if (_styles != null) {
		buf.append(_styles);
	}
  if (_multiple != null) {
      buf.append(" multiple=\"multiple\"");
  }
  if (_size != null) {
      buf.append(" size=\"");
      buf.append(_size);
      buf.append("\"");
  }
	
	buf.append(">");
  
  //if _selectedValues is not null, convert it to an ArrayList for easier processing
  ArrayList selectedValuesArray = new ArrayList();
  if (_selectedValues != null) {
    for (int i=0; i<_selectedValues.length; i++) {
      selectedValuesArray.add(_selectedValues[i]);
    }
  }

	// If there is a specific first OPTION specified, then create it.
	int index = 0;
	if (_firstValue != null) {
       	buf.append("<option value=\"");
       	buf.append(_firstValue);
       	buf.append('"');
       	if (((_selectedValue != null) && (_selectedValue.equals(_firstValue))) ||
          (selectedValuesArray.contains(_firstValue)) ||
	       	(_selectedIndex == index)) {
	       	buf.append(" selected");
		}
		buf.append('>');
		if (_firstDisplayValue != null)
	       	buf.append(_firstDisplayValue);
	    else
	       	buf.append(_firstValue);
       	buf.append("</option>");
       	index++;
	}

	// Create an OPTION for each value in the legal value set, excluding the excluded values, if any.
	for (int i = 0; i < _legalValueSet.getCount(); i++, index++) {
    String value = _legalValueSet.getValue(i);
    if (_excludedValues == null || !_excludedValues.contains(value)) {
      buf.append("<option value=\"");
      buf.append(value);
      buf.append('"');
      if (((_selectedValue != null) && (_selectedValue.equals(value))) ||
        (selectedValuesArray.contains(value)) ||
        (_selectedIndex == index)) {
          buf.append(" selected");
        }
      buf.append('>');
      Escaper.htmlEscape(_legalValueSet.getDisplayValue(i), buf);
      buf.append("</option>");      
    }
	}

	buf.append("</select>");
}
public void setFirstDisplayValue(String value) {
	_firstDisplayValue = value;
}
public void setFirstValue(String value) {
	_firstValue = value;
}
public void setId(String id) {
	_id = id;
}
public void setLegalValueSet(LegalValueSet legalValueSet) {
	_legalValueSet = legalValueSet;
}

public void setSelectedIndex(int index) {
	_selectedIndex = index;
}
public void setSelectedValue(String value) {
	_selectedValue = value;
}

	/**
	 * Sets the events.
	 * @param events The events to set
	 */
	public void setEvents(String events) {
		_events = events;
	}

	/**
	 * Sets the styles.
	 * @param styles The styles to set
	 */
	public void setStyles(String styles) {
		_styles = styles;
	}

  /**
   * @param string
   */
  public void setMultiple(String string) {
    _multiple = string;
  }

  /**
   * @param string
   */
  public void setSize(String string) {
    _size = string;
  }

  /**
   * @param strings
   */
  public void setSelectedValues(String[] strings) {
    _selectedValues = strings;
  }

  public void setExcludedValues(Set excludedValues) {
    _excludedValues = excludedValues;
  }
}
