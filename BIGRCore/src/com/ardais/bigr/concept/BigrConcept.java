package com.ardais.bigr.concept;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.builder.StandardToStringStyle;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.ardais.bigr.api.ApiFunctions;

/**
 * This represents a BIGR concept, for example an ARTS concept.
 */
public final class BigrConcept implements Serializable {
  private static final long serialVersionUID = -9159520430852297604L;

  private static final ToStringStyle TO_STRING_STYLE;

  // Constants that specify the valid concept types.  If you add a new concept type here, add
  // it to the validTypes set below. 
  public static final String TYPE_ADE = "ADE";
  public static final String TYPE_ADE_ELEMENT = "ADEElement";
  public static final String TYPE_CATEGORY = "Category";
  public static final String TYPE_DIAGNOSIS_GROUP = "DiagnosisGroup";
  public static final String TYPE_ELEMENT = "Element";
  public static final String TYPE_ELVAL_GRAPH_CONTEXT = "ElValGraphContext";
  public static final String TYPE_GRAPH_CONTEXT = "GraphContext";
  public static final String TYPE_TOPICS_GRAPH_CONTEXT = "TopicsGraphContext";
  public static final String TYPE_UNIT = "Unit";
  public static final String TYPE_VALUE = "Value";
  public static final String TYPE_VALUE_SET = "ValueSet";
  public static final String TYPE_VALUE_SET_GRAPH_CONTEXT = "ValueSetGraphContext";
  public static final String TYPE_VALUE_SET_BIGR_GBOSS = "BIGR_GBOSS_Value_Set";
  
  // The set of valid concept types, to be used for validating input.
  private static Set _validTypes = new HashSet();
  static {
    _validTypes.add(TYPE_ADE);
    _validTypes.add(TYPE_ADE_ELEMENT);
    _validTypes.add(TYPE_CATEGORY);
    _validTypes.add(TYPE_DIAGNOSIS_GROUP);
    _validTypes.add(TYPE_ELEMENT);
    _validTypes.add(TYPE_ELVAL_GRAPH_CONTEXT);
    _validTypes.add(TYPE_GRAPH_CONTEXT);
    _validTypes.add(TYPE_TOPICS_GRAPH_CONTEXT);
    _validTypes.add(TYPE_UNIT);
    _validTypes.add(TYPE_VALUE);
    _validTypes.add(TYPE_VALUE_SET);
    _validTypes.add(TYPE_VALUE_SET_GRAPH_CONTEXT);
    _validTypes.add(TYPE_VALUE_SET_BIGR_GBOSS);
  }

  // Constants that specify the valid datatypes.  If you add a new concept type here, add
  // it to the validDataTypes set below, and possible the valueRangeDataTypes set. 
  public static final String DATATYPE_CV = "cv";
  public static final String DATATYPE_INT = "int";
  public static final String DATATYPE_FLOAT = "float";
  public static final String DATATYPE_DATE = "date";
  public static final String DATATYPE_PARTIAL_DATE = "vpd";
  public static final String DATATYPE_REPORT = "report";
  public static final String DATATYPE_TEXT = "text";

  // The set of valid datatypes, to be used for validating input.
  private static Set _validDataTypes = new HashSet();
  static {
    _validDataTypes.add(DATATYPE_CV);
    _validDataTypes.add(DATATYPE_INT);
    _validDataTypes.add(DATATYPE_FLOAT);
    _validDataTypes.add(DATATYPE_DATE);
    _validDataTypes.add(DATATYPE_PARTIAL_DATE);
    _validDataTypes.add(DATATYPE_REPORT);
    _validDataTypes.add(DATATYPE_TEXT);
  }

  // The set of valid datatypes for concepts that have a range, i.e. min and/or max value.
  private static Set _validRangeDataTypes = new HashSet();
  static {
    _validRangeDataTypes.add(DATATYPE_INT);
    _validRangeDataTypes.add(DATATYPE_FLOAT);
    _validRangeDataTypes.add(DATATYPE_DATE);
    _validRangeDataTypes.add(DATATYPE_PARTIAL_DATE);
  }

  private String _code = null;
  private String _description = null;
  private String _systemName = null;
  private boolean _multivalued = false;
  private String _rawMultiplicity;
  private boolean _oceEnabled = false;
  private String _rawOceYn;
  private String _datatype = null;
  private boolean _otherCui = false;
  private String _rawOtherCuiYn;
  private String _conceptType = null;
  private boolean _noValue = false;
  private String _rawNoValueYn;
  private boolean _immutable = false;
  private String _unitCui = null;
  private String _maxValue;
  private String _minValue;
  private String _maxInclusive = null;
  private String _minInclusive = null;
  static {
    StandardToStringStyle toStringStyle = new StandardToStringStyle();
    toStringStyle.setUseClassName(false);
    toStringStyle.setUseIdentityHashCode(false);
    TO_STRING_STYLE = toStringStyle;
  }

  /**
   * Create a new BigrConcept with no code or description.  Such a concept
   * isn't valid, since all concepts must have a non-empty code, but we
   * include this constructor so that this class obeys the JavaBean contract.
   * This constructor should never be used in our code, it is only here to support
   * serialization.
   */
  public BigrConcept() {
    // This can't call the multi-argument constructor like the other constructors do
    // because that constructor validates its arguments (e.g. code != null) and this constructor
    // doesn't create a valid concept object.  It is here to support serialization.

    super();
  }

  /**
   * Create a new BigrConcept with the specified code, description and system name.
   * The newly-created instance is immutable.
   * @param code the code that uniquely identifies this concept.  The
   *     code must be non-empty.
   * @param description the concept description.  The description is
   *     the string that users see when displaying
   *     a concept.
   * @param systemName the system name that uniquely identifies this concept.  The
   *     system name also uniquely identifies this concept, and is more suitable for
   *     use than the code in some situations.  The system name must be non-empty.
   * @param multiplicity the multiplicity of this concept.  The multiplicity is not required and
   *     only applies to concepts that represent data elements.  Expected values are either 1 or N.
   *     Any value other than N, including null, is interpreted as 1.
   * @param oce whether this concept represents an OCE-enabled data element.  oce is not 
   *     required and only applies to concepts that represent data elements.  Expected values are
   *     Y or N.  Any value other than Y, including null, is interpreted as N.
   * @param datatype the datatype of this concept.  The datatype is not required and only applies 
   *     to concepts that represent data elements.
   * @param conceptType the type of this concept.  The conceptType is not required and must be one
   *     of the TYPE constants specified in this class.
   * @param otherCui whether this concept holds the code for an "other" value.  Expected values
   *     are "Y" and "N".  Any value other than Y, including null, is interpreted as N.
   */
  public BigrConcept(
    String code,
    String description,
    String systemName,
    String multiplicity,
    String oce,
    String datatype,
    String conceptType,
    String otherCui,
    String unitCui,
    String minValue,
    String maxValue,
    String minInclusive,
    String maxInclusive,
    String noValue) {
      
      //Note - if the value for immutable below is changed from true to false, then
      //make sure to update the SystemConfigurationData code so that every concept
      //in every BigrConceptList is made immutable when an instance of that class is
      //marked immutable.
    this(code, description, systemName, multiplicity, oce, datatype, conceptType, otherCui, unitCui, 
      minValue, maxValue, minInclusive, maxInclusive, noValue, true);
  }

  /**
   * Create a new BigrConcept with the specified code, description and system name.
   * @param code the code that uniquely identifies this concept.  The
   *     code must be non-empty.
   * @param description the concept description.  The description is
   *     the string that users see when displaying
   *     a concept.
   * @param systemName the system name that uniquely identifies this concept.  The
   *     system name also uniquely identifies this concept, and is more suitable for
   *     use than the code in some situations.  The system name must be non-empty.
   * @param multiplicity the multiplicity of this concept.  The multiplicity is not required and
   *     only applies to concepts that represent data elements.  Expected values are either 1 or N.
   *     Any value other than N, including null, is interpreted as 1.
   * @param oce whether this concept represents an OCE-enabled data element.  oce is not 
   *     required and only applies to concepts that represent data elements.  Expected values are
   *     Y or N.  Any value other than Y, including null, is interpreted as N.
   * @param datatype the datatype of this concept.  The datatype is not required and only applies 
   *     to concepts that represent data elements.
   * @param conceptType the type of this concept.  The conceptType is not required and must be one
   *     of the TYPE constants specified in this class.
   * @param otherCui whether this concept holds the code for an "other" value.  Expected values
   *     are "Y" and "N".  Any value other than Y, including null, is interpreted as N.
   * @param immutable if this is true, make the instance immutable.  Any
   *     attempts to modify immutable instances will throw an exception.
   */
  private BigrConcept(
    String code,
    String description,
    String systemName,
    String multiplicity,
    String oce,
    String datatype,
    String conceptType,
    String otherCui,
    String unitCui,
    String minValue,
    String maxValue,
    String minInclusive,
    String maxInclusive,
    String noValue,
    boolean immutable) {
      
    // This is private, external callers can currently only create immutable instances.
    
    super();

    setCode(code);
    setDescription(description);
    setSystemName(systemName);
    setRawMultiplicity(multiplicity);
    setRawOceYn(oce);
    setDatatype(datatype);
    setConceptType(conceptType);
    setRawOtherCuiYn(otherCui);
    setUnitCui(unitCui);
    setMinValue(minValue);
    setMaxValue(maxValue);
    setMinInclusive(minInclusive);
    setMaxInclusive(maxInclusive);
    setRawNoValueYn(noValue);

    if (immutable) {
      setImmutable();
    }
  }

  /**
   * Set this instance to be immutable.  Any attempts to modify immutable
   * instances will throw an exception.
   */
  private void setImmutable() {
    _immutable = true;
  }

  /**
   * Throw an exception if this instance is immutable.
   */
  private void checkImmutable() {
    if (_immutable) {
      throw new IllegalStateException("Attempted to modify an immutable BigrConcept: " + this);
    }
  }

  /**
   * Determine if this object is equal to another object.
   * In a valid BigrConcept, the code property is non-empty and uniquely
   * identifies a concept.  This equals function assumes that the concept
   * object is valid and this object is considered to be equal to another
   * object if and only if they are both BigrConcept objects and their
   * code properties are equal.
   * 
   * @param obj the object to compare to
   * @return true if this object is equal to the specified object
   */
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    else if (!(obj instanceof BigrConcept)) {
      return false;
    }
    else {
      BigrConcept o2 = (BigrConcept) obj;

      if (_code == null) {
        return (o2.getCode() == null);
      }
      else {
        return _code.equals(o2.getCode());
      }
    }
  }

  /**
   * Return a hash code that is consistent with the
   * {@link #equals(Object) equals} method in that it depends only on
   * the value of the <code>code</code> property.
   * 
   * @return the hash code
   */
  public int hashCode() {
    // This returns the sample value as we'd get from
    //   return new HashCodeBuilder(initialValue, multiplier).append(_code).toHashCode();
    // but without the extra overhead.  HashCodeBuilder is a class in
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

    final int multiplier = 11;
    final int initialValue = 53;

    int total = initialValue;
    int c;

    if (_code == null) {
      c = 0;
    }
    else {
      c = _code.hashCode();
    }

    total = total * multiplier + c;

    return total;
  }

  /**
   * Return a string representation of this object.
   * 
   * @return the string representation
   */
  public String toString() {
    return new ToStringBuilder(this, TO_STRING_STYLE)
      .append("code", _code)
      .append("desc", _description)
      .append("sysname", _systemName)
      .toString();
  }

  /**
   * Returns the concept's code.  The code is the string
   * code that uniquely identifies this concept.
   * 
   * @return the concept code
   */
  public String getCode() {
    return _code;
  }

  /**
   * Returns the concept's description.  The code must be non-empty and
   * uniquely identify a concept (two different concepts must not have
   * the same code).
   * 
   * @return the concept description
   */
  public String getDescription() {
    return _description;
  }

  /**
   * Returns the concept's system name.  The system name uniquely identifies this concept.
   * 
   * @return the concept system name
   */
  public String getSystemName() {
    return _systemName;
  }

  /**
   * Returns an indication of whether the concept is multivalued.  This only applies to concepts 
   * that are data elements, not data values.
   * 
   * @return <code>true</code> if the concept is a multivalued data element;
   *          <code>false</code> otherwise.
   */
  public boolean isMultivalued() {
    return _multivalued;
  }

  /**
   * Returns an indication of whether the concept is OCE-enabled.  This only applies to concepts 
   * that are data elements, not data values, and indicates that the value set of the data element 
   * has a value that represents an "other" choice.
   * 
   * @return  <code>true</code> if the concept is an OCE-enabled data element;
   *           <code>false</code> otherwise
   */
  public boolean isOceEnabled() {
    return _oceEnabled;
  }

  /**
   * Returns the datatype of this concept.  This only applies to concepts that are data 
   * elements, not data values.
   * 
   * @return  The datatype.
   */
  public String getDatatype() {
    return _datatype;
  }

  /**
   * Returns the type of this concept.  The returned string will either be null of one of the
   * public TYPE constants defined in this class. 
   * 
   * @return  The concept type.
   */
  public String getConceptType() {
    return _conceptType;
  }

  /**
   * Returns an indication of whether this concept represents an "other" value.
   * 
   * @return  <code>true</code> if this concept represents an "other" value;
   *           <code>false</code> otherwise
   */
  public boolean isOtherCui() {
    return _otherCui;
  }

  /**
   * Returns an indication of whether this concept represents a "noValueCui" value.
   * 
   * @return  <code>true</code> if this concept represents a "noValueCui" value;
   *           <code>false</code> otherwise
   */
  public boolean isNoValue() {
    return _noValue;
  }

  /**
   * Sets the concept's code.  The code must be non-empty and
   * uniquely identify a concept (two different concepts must not have
   * the same code).
   * 
   * @param code The code to set
   */
  private void setCode(String code) {
    checkImmutable();

    if (ApiFunctions.isEmpty(code)) {
      throw new IllegalArgumentException("A concept code must not be empty or null.");
    }

    _code = code;
  }

  /**
   * Sets the concept's description.  The description is the string
   * that is the string that users see when displaying a concept.
   * 
   * @param description The description to set
   */
  private void setDescription(String description) {
    checkImmutable();

    _description = description;
  }

  /**
   * Sets the concept's system name.  The system name, if non-empty, must
   * uniquely identify a concept (two different concepts must not have
   * the same system name).
   * 
   * @param systemName The system name to set
   */
  private void setSystemName(String systemName) {
    checkImmutable();

    _systemName = systemName;
  }

  /**
   * Sets the datatype of this concept.  This only applies to concepts that are data 
   * elements, not data values.
   * 
   * @param datatype  the datatype
   */
  private void setDatatype(String datatype) {
    checkImmutable();

    _datatype = datatype;
  }

  /**
   * Sets the type of this concept. 
   * 
   * @param  type  the concept type.  This must be one of the public TYPE constants defined in this 
   *               class, or null. 
   */
  private void setConceptType(String type) {
    checkImmutable();
    //TODO: do not allow null type
    if ((type == null) || (_validTypes.contains(type))) {
      _conceptType = type;
    }
    else {
      throw new IllegalArgumentException("Concept type '" + type + "' is not recognized as a valid type.");      
    }
  }


  /**
   * Returns the concept's unit code.  
   * 
   * @return the concept's unit code
   */
  public String getUnitCui() {
    return _unitCui;
  }

  /**
   * Sets the concept's unit code.  
   * 
   * @param unitCui The unit code to set
   */
  private void setUnitCui(String unitCui) {
    checkImmutable();
    _unitCui = unitCui;
  }

  /**
   * Returns Y or N to indicate whether the MaxValue will be inclusive.
   */
  public String getMaxInclusive() {
    return _maxInclusive;
  }

  /**
   * Returns the value to be used as the MaxValue for the data element
   */
  public String getMaxValue() {
    return _maxValue;
  }

  /**
   * Returns Y or N to indicate whether the MinValue will be inclusive.
   */
  public String getMinInclusive() {
    return _minInclusive;
  }

  /**
   * Returns the value to be used as the MinValue for the data element
   */
  public String getMinValue() {
    return _minValue;
  }

  /**
   * Sets to Y or N to indicate whether MaxValue will be 
   * inclusive or not.
   */
  private void setMaxInclusive(String maxInclusive) {
    checkImmutable();
    _maxInclusive = maxInclusive;
  }

  /**
   * @param string The MaxValue to set.
   */
  private void setMaxValue(String string) {
    checkImmutable();
    _maxValue = string;
  }

  /**
   * Sets to Y or N to indicate whether MinValue will be 
   * inclusive or not.
   */
  private void setMinInclusive(String minInclusive) {
    checkImmutable();
    _minInclusive = minInclusive;
  }

  /**
   * @param string The MinValue to set.
   */
  private void setMinValue(String string) {
    checkImmutable();
    _minValue = string;
  }

  /**
   * Returns the raw multiplicity value.  This is only intended to be used when validating this 
   * concept.  All other callers should use {@link #isMultivalued()}.
   * 
   * @return  The multiplicity.
   */
  public String getRawMultiplicity() {
    return _rawMultiplicity;
  }

  /**
   * Returns the raw value of no value y/n.  This is only intended to be used when validating this 
   * concept.  All other callers should use {@link #isNoValue()}.
   * 
   * @return  The no value y/n value.
   */
  public String getRawNoValueYn() {
    return _rawNoValueYn;
  }

  /**
   * Returns the raw value of OCE y/n.  This is only intended to be used when validating this 
   * concept.  All other callers should use {@link #isOceEnabled()}.
   * 
   * @return  The raw OCE y/n value.
   */
  public String getRawOceYn() {
    return _rawOceYn;
  }

  /**
   * Returns the raw value of other cui y/n.  This is only intended to be used when validating this 
   * concept.  All other callers should use {@link #isOtherCui()}.
   * 
   * @return  The raw other CUI y/n value.
   */
  public String getRawOtherCuiYn() {
    return _rawOtherCuiYn;
  }

  /**
   * Sets the raw multiplicity value.
   * 
   * @param   multiplicity  the multiplicity
   */
  private void setRawMultiplicity(String multiplicity) {
    checkImmutable();
    _rawMultiplicity = multiplicity;
    _multivalued = "N".equalsIgnoreCase(multiplicity);
  }

  /**
   * Sets the raw value of no value y/n.
   * 
   * @param   noValueYn  the no value y/n value
   */
  private void setRawNoValueYn(String noValueYn) {
    checkImmutable();
    _rawNoValueYn = noValueYn;
    _noValue = "Y".equalsIgnoreCase(noValueYn);
  }

  /**
   * Sets the raw value of OCE y/n.
   * 
   * @param   oceYn  the raw OCE y/n value
   */
  private void setRawOceYn(String oceYn) {
    checkImmutable();
    _rawOceYn = oceYn;
    _oceEnabled = "Y".equalsIgnoreCase(oceYn);
  }

  /**
   * Sets the raw value of other cui y/n.
   * 
   * @param   otherCuiYn  the raw other CUI y/n value
   */
  private void setRawOtherCuiYn(String otherCuiYn) {
    checkImmutable();
    _rawOtherCuiYn = otherCuiYn;
    _otherCui = "Y".equalsIgnoreCase(otherCuiYn);
  }

  /**
   * Returns the set of valid datatypes.  This is the set of values that can be used as a parameter
   * to the {@link #setDatatype(java.lang.String)} method.  Each value in this set is described
   * by one of the DATATYPE_* constants in this class.
   * 
   * @return  the Set of valid datatypes.
   */
  public static Set getValidDatatypes() {
    return Collections.unmodifiableSet(_validDataTypes);
  }

  /**
   * Returns the set of valid datatypes for concepts that have a range, that is, a minimum and/or
   * maximum value.  Each value in this set is described by one of the DATATYPE_* constants in 
   * this class.  This set is a subset of that returned by {@link #getValidDatatypes()} 
   * 
   * @return  the Set of valid datatypes.
   */
  public static Set getValidRangeDatatypes() {
    return Collections.unmodifiableSet(_validRangeDataTypes);
  }

}
