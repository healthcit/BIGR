package com.ardais.bigr.iltds.assistants;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Represents an ordered set of legal values.  A <b>legal value</b>
 * is a set of two possible values - one is known as the <b>value</b>,
 * (aka the code or key) and is generally what is stored, and the 
 * other is known as the <b>display value</b> (aka the name or description),
 * which is generally what is displayed.  The display value is optional,
 * and if not supplied the value is returned as the display value.
 * <p>
 * In addition, each legal value can have another <code>LegalValueSet</code>
 * associated with it, creating a hierarchy of <code>LegalValueSet</code>s.
 * This is useful when, for example, the selection of a value in
 * a <code>LegalValueSet</code> is used to determine the possible selections
 * from another <code>LegalValueSet</code>.
 * <p>
 * <code>LegalValueSet</code> does not guard against duplicates,
 * and the behavior will be such that all duplicate values map to
 * the same display value.
 */
public class LegalValueSet implements Serializable {

  private String _name;
  private List _values = new ArrayList();
  private Map _displayValues = new HashMap();

  private Map _subLegalValueSets = new HashMap();

  private class LegalValueSetIterator implements Iterator {
    Iterator _valuesIt = _values.iterator();

    public boolean hasNext() {
      return _valuesIt.hasNext();
    }

    public Object next() {
      String value = (String) _valuesIt.next();
      return new LegalValue(value, (String) _displayValues.get(value));
    }

    public void remove() {
      _valuesIt.remove();
    }
  }
  /**
   * Creates a new <code>LegalValueSet</code>.
   */
  public LegalValueSet() {
  }
  /**
   * Creates a new <code>LegalValueSet</code> as a copy of the specified
   * <code>LegalValueSet</code>.
   *
   * @param  lvs  the <code>LegalValueSet</code> to copy
   */
  public LegalValueSet(LegalValueSet lvs) {
    int numValues = lvs.getCount();
    for (int i = 0; i < numValues; i++) {
      LegalValueSet subLvs = lvs.getSubLegalValueSet(i);
      if (subLvs != null) {
        addLegalValue(lvs.getValue(i), lvs.getDisplayValue(i), new LegalValueSet(subLvs));
      }
      else {
        addLegalValue(lvs.getValue(i), lvs.getDisplayValue(i));
      }
    }
  }

  /**
   * Creates a new <code>LegalValueSet</code> from the specified list.  The list is assumed to be
   * a list of strings.  A legal value will be created for each list entry, and value and display 
   * value of the legal value will both be set to the text of the entry.  
   * 
   * @param  list  the list
   */
  public LegalValueSet(List list) {
    if (list != null) {
      Iterator i = list.iterator();
      while (i.hasNext()) {
        String item = (String) i.next();
        addLegalValue(item, item);
      }
    }
  }

  public String getName() {
    return _name;
  }

  public void setName(String name) {
    _name = name;
  }
  
  /**
   * Adds a legal value with just a value to this
   * <code>LegalValueSet</code> at the specified position.
   *
   * @param  index  the zero-based position
   * @param  value  the legal value's value
   */
  public void addLegalValue(int index, String value) {
    _displayValues.put(value, value);
    _values.add(index, value);
  }
  /**
   * Adds a legal value with just a value and a sub-<code>LegalValueSet</code>
   * to this <code>LegalValueSet</code> at the specified position.
   *
   * @param  index  the zero-based position
   * @param  value  the legal value's value
   * @param  lvs  the sub-<code>LegalValueSet</code>
   */
  public void addLegalValue(int index, String value, LegalValueSet lvs) {
    addLegalValue(index, value);
    _subLegalValueSets.put(value, lvs);
  }
  /**
   * Adds a legal value with a value and display value to
   * this <code>LegalValueSet</code> at the specified position.
   *
   * @param  index  the zero-based position
   * @param  value  the legal value's value
   * @param  display  the legal value's display value
   */
  public void addLegalValue(int index, String value, String display) {
    _displayValues.put(value, display);
    _values.add(index, value);
  }
  /**
   * Adds the specified LegalValue to this <code>LegalValueSet</code> at the specified position.
   *
   * @param  index  the zero-based position
   * @param  value  the LegalValue
   */
  public void addLegalValue(int index, LegalValue value) {
    _displayValues.put(value.getValue(), value.getDisplayValue());
    _values.add(index, value.getValue());
  }
  /**
   * Adds a legal value with a value, display value and a 
   * sub-<code>LegalValueSet</code> to this <code>LegalValueSet</code>
   * at the specified position.
   *
   * @param  index  the zero-based position
   * @param  value  the legal value's value
   * @param  value  the legal value's display value
   * @param  lvs  the sub-<code>LegalValueSet</code>
   */
  public void addLegalValue(int index, String value, String display, LegalValueSet lvs) {
    addLegalValue(index, value, display);
    _subLegalValueSets.put(value, lvs);
  }
  /**
   * Adds a legal value with just a value to the end of
   * this <code>LegalValueSet</code>.
   *
   * @param  value  the legal value's value
   */
  public void addLegalValue(String value) {
    _displayValues.put(value, value);
    _values.add(value);
  }
  /**
   * Adds a legal value with just a value and a sub-<code>LegalValueSet</code>
   * to the end of this <code>LegalValueSet</code>.
   *
   * @param  value  the legal value's value
   * @param  lvs  the sub-<code>LegalValueSet</code>
   */
  public void addLegalValue(String value, LegalValueSet lvs) {
    addLegalValue(value);
    _subLegalValueSets.put(value, lvs);
  }
  /**
   * Adds a legal value with a value and display value to the
   * end of this <code>LegalValueSet</code>.
   *
   * @param  value  the legal value's value
   * @param  display  the legal value's display value
   */
  public void addLegalValue(String value, String display) {
    _displayValues.put(value, display);
    _values.add(value);
  }
  /**
   * Adds a legal value with a value, display value and a 
   * sub-<code>LegalValueSet</code> to the end of this
   * <code>LegalValueSet</code>
   *
   * @param  value  the legal value's value
   * @param  display  the legal value's display value
   * @param  lvs  the sub-<code>LegalValueSet</code>
   */
  public void addLegalValue(String value, String display, LegalValueSet lvs) {
    addLegalValue(value, display);
    _subLegalValueSets.put(value, lvs);
  }
  /**
   * Adds the specified LegalValue to the end of this <code>LegalValueSet</code>.
   *
   * @param  value  the LegalValue
   */
  public void addLegalValue(LegalValue value) {
    _displayValues.put(value.getValue(), value.getDisplayValue());
    _values.add(value.getValue());
  }
  /**
   * Returns the number of legal values in this <code>LegalValueSet</code>.
   *
   * @return  The count.
   */
  public int getCount() {
    return _values.size();
  }
  /**
   * Returns the display value at the specified index.
   *
   * @param  index  the zero-based index
   * @return  The display value.
   */
  public String getDisplayValue(int index) {
    return (String) _displayValues.get(getValue(index));
  }
  /**
   * Returns the display value corresponding to the
   * specified value.
   *
   * @param  value  the value
   * @return  The display value.
   */
  public String getDisplayValue(String value) {
    return (String) _displayValues.get(value);
  }
  /**
   * Returns an <code>Iterator</code> over the legal values in this
   * <code>LegalValueSet</code>.  The <code>Iterator</code> returns 
   * {@link LegalValue} objects.
   *
   * @return  The <code>Iterator<code>.
   */
  public Iterator getIterator() {
    return new LegalValueSetIterator();
  }
  /**
   * Returns the sub-<code>LegalValueSet</code> of the legal
   * value at the specified index.
   *
   * @param  index  the zero-based index
   * @return  The sub-<code>LegalValueSet</code>.
   */
  public LegalValueSet getSubLegalValueSet(int index) {
    return (LegalValueSet) _subLegalValueSets.get(getValue(index));
  }
  /**
   * Returns the sub-<code>LegalValueSet</code> of the legal
   * value with the specified value.
   *
   * @param  value  the value
   * @return  The sub-<code>LegalValueSet</code>.
   */
  public LegalValueSet getSubLegalValueSet(String value) {
    return (LegalValueSet) _subLegalValueSets.get(value);
  }
  /**
   * Returns the value at the specified index.
   *
   * @param  index  the zero-based index
   * @return  The value.
   */
  public String getValue(int index) {
    return (String) _values.get(index);
  }
  /**
   * Removes the legal value at the specified index.
   *
   * @param  index  the zero-based index
   */
  public void removeLegalValue(int index) {
    String value = (String) _values.get(index);
    _values.remove(index);
    _displayValues.remove(value);
  }
  /**
   * Removes the specified legal value if exists.
   *
   * @param  value the value
   */
  public void removeLegalValue(String value) {
    if ((value != null) && (_values.contains(value))) {
      int index = _values.indexOf(value);
      removeLegalValue(index);
    }
  }

  /**
   * Searches for the first occurence of the given argument, testing for equality
   * using the equals method. 
   * 
   * @param value The value to search for.
   * 
   * @return The index of the first occurrence of the arguement in this list;
   * returns -1 if the object is not found.
   */
  public int indexOf(String value) {
    return _values.indexOf(value);
  }

  /**
   * Returns true if this LegalValueSet contains the specified value.
   * 
   * @param value The value to search for.
   * @return true if the specified value is present; false otherwise.
   */
  public boolean contains(String value) {
    return _values.contains(value);
  }

  /**
   * Returns true if this LegalValueSet is equal to the specified object.  This object is considered 
   * to be equal to another object if and only if they are both LegalValueSet objects and they have
   * all of the same values and display values in the same order, and the same sub legal value sets.
   * 
   * @param obj the object to compare to
   * @return true if this object is equal to the specified object
   */
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    else if (!(obj instanceof LegalValueSet)) {
      return false;
    }
    else {
      LegalValueSet o2 = (LegalValueSet) obj;

      // Check that the value lists are the same, and return false if they aren't.
      if (_values == null) {
        if (o2._values != null) {
          return false;
        }
      }
      else {
        if (o2._values == null) {
          return false;
        }
        else {
          if (!_values.equals(o2._values)) {
            return false;
          }
        }
      }

      // Check that the display values are the same, and return false if they aren't.
      if (_displayValues == null) {
        if (o2._displayValues != null) {
          return false;
        }
      }
      else {
        if (o2._displayValues == null) {
          return false;
        }
        else {
          if (!_displayValues.equals(o2._displayValues)) {
            return false;
          }
        }
      }

      // Check that the sub legal value sets are the same, and return false if they aren't.
      if (_subLegalValueSets == null) {
        if (o2._subLegalValueSets != null) {
          return false;
        }
      }
      else {
        if (o2._subLegalValueSets == null) {
          return false;
        }
        else {
          if (!_subLegalValueSets.equals(o2._subLegalValueSets)) {
            return false;
          }
        }
      }
      return true;
    }
  }

  /**
   * Returns a hash code that is the sum of the hash codes of all values.  This obeys the contract
   * that two LegalValueSets that are equal must produce the same hash code, since equal 
   * LegalValueSets must have the same list of values.
   * 
   * @return the hash code
   */
  public int hashCode() {
    int hash = 0;
    Iterator i = _values.iterator();
    while (i.hasNext()) {
      String value = (String) i.next();
      hash += value.hashCode();
    }
    return 53 * 11 + hash;
  }
}