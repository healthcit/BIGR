package com.gulfstreambio.bigr.error;

import java.io.Serializable;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * Holds a single BIGR validation error.  The key indicates which error occurred, and also serves
 * as a key into the BigrServiceResources.properties file that contains the error message associated
 * with the key.  Error messages may have replacement values, and in such a case a caller that 
 * creates a <code>BigrValidationError</code> should call the 
 * {@link #BigrValidationError(String, String[])} constructor with an array of values to insert 
 * into the message.    
 */
public class BigrValidationError implements Serializable {
  
  /**
   * The key of this error.
   */
  private String _key = null;

  /**
   * The replacement values for this error mesasge.
   */
  private String _replacementValues[];
  
  /**
   * Constructs an error with no replacement values.
   *
   * @param key message key for this error
   */
  public BigrValidationError(String key) {
    this(key, new String[0]);
  }

  /**
   * Constructs an error with the specified replacement values.
   *
   * @param key message key for this error
   * @param replacementValues an array of replacement values
   */
  public BigrValidationError(String key, String[] replacementValues) {
    _key = key;
    _replacementValues = replacementValues;
  }

  /**
   * Returns the key for this error.
   * 
   * @return the error key
   */
  public String getKey() {
    return _key;
  }

  /**
   * Returns the message for this error.  If there are any replacement values, they will have
   * been inserted into the message before it is returned.
   * 
   * @return the error message
   */
  public String getMessage() {
    BigrValidationErrorProperties props = BigrValidationErrorProperties.getInstance();
    String msg = props.getProperty(getKey());
    for (int i = 0; i < _replacementValues.length; i++) {
      String insertionString = "{" + i + "}";
      int insertionPoint = msg.indexOf(insertionString);
      //if we cannot find the insertion point for the next argument, return
      //a string to indicate that
      if (insertionPoint < 0) {
        return ("???" + getKey() + "???");
      }
      else {
        msg = msg.substring(0, insertionPoint) + _replacementValues[i]
            + msg.substring(insertionPoint + insertionString.length());
      }
    }
    return msg;
  }

  /**
   * Override of <code>Object.equals()</code>.  Two <code>BigrValidationError</code> objects are equal if 
   * their keys and all of their replacement values are equal.
   * 
   * @param obj the object to compare to
   * @return true if this object is equal to the specified object
   */
  public boolean equals(Object obj) {
      if (obj == this) {
          return true;
      }
      else if (!(obj instanceof BigrValidationError)) {
          return false;
      }
      else {
        BigrValidationError o2 = (BigrValidationError) obj;

          // Are the keys non-equals?
          if (_key == null) {
              if (o2._key != null) {
                  return false;
              }
          }
          else if (!_key.equals(o2._key)) {
              return false;
          }

          // Are the replacement values non-equals?
          if (_replacementValues.length != o2._replacementValues.length) {
              return false;
          }
          for (int i = 0; i < _replacementValues.length; i++) {
              String v1 = _replacementValues[i];
              String v2 = o2._replacementValues[i];
              if (v1 == null) {
                  if (v2 != null) {
                      return false;
                  }
              }
              else if (!v1.equals(v2)) {
                  return false;
              }
          }
          return true;
      }
  }

  /**
   * Return a hash code that is consistent with the
   * {@link #equals(Object)} method in that it depends only on the
   * same set of features that <code>equals</code> depends on.
   * 
   * @return the hash code
   */
  public int hashCode() {
      // HashCodeBuilder is a class in
      // org.apache.commons.lang.builder that generates good hash codes
      // that comply with the guidelines Joshua Bloch lays out in
      // "Effective Java: Programming Language Guide".
      //
      // If you're copying code from here to other hashCode implementations,
      // please read the Javadoc for HashCodeBuilder first, and ideally
      // Bloch's book as well.  There are a lot of guidelines that need
      // to be followed to create a hashCode function that obeys the
      // general contract it must observe and still yields good hash
      // distributions.  One thing to note is that the multiplier and
      // initialValue constants must both be odd, and ideally the
      // multiplier should be a prime number.

      return new HashCodeBuilder(103, 77)
          .append(_key)
          .append(_replacementValues)
          .toHashCode();
  }

  public String toString() {
    StringBuffer buff = new StringBuffer(50);
    buff.append(_key);
    buff.append(": ");
    boolean appendComma = false;
    for (int i = 0; i < _replacementValues.length; i++) {
      if (appendComma) {
        buff.append(", ");
      }
      buff.append(_replacementValues[i]);
      appendComma = true;
    }
    return buff.toString();
  }

}
