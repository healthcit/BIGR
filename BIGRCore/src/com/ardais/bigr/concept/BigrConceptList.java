package com.ardais.bigr.concept;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.iltds.assistants.LegalValueSet;

/**
 * This class represents a list of BIGR concepts (e.g. ARTS concepts).
 * 
 * <p>To create a BigrConceptList create an instance of this class, call the 
 * {@link #setName(String) setName} method, and then call the
 * {@link #addConcept(BigrConcept) addConcept} method to add each
 * concept in the list.  When finished adding BigrConcepts, call 
 * {@link #setCompleted() setCompleted} to indicate the list is complete.
 * Note that once the instance is marked complete the name cannot be changed and
 * the list becomes immutable.
 * 
 * <p>Elements in the list will be returned in the order in which they were
 * added to the list.
 */
public class BigrConceptList implements Serializable {
  private static final long serialVersionUID = 8838852043125856168L;
  private List _conceptList = new ArrayList();
  private String _name;
  private boolean _completed = false;
  private boolean _valid = false;
  private String _validError = null;

  /**
   * Create a new BigrConceptList with no name.  Such a concept list
   * isn't valid but we include this constructor so that this class obeys the 
   * JavaBean contract.
   * This constructor should never be used in our code, it is only here to support
   * XML digestion.
   */
  public BigrConceptList() {
    super();
  }

  /**
   * Add a new BigConcept to the list
   * @param concept  the BigrConcept to add
   */
  public void addConcept(BigrConcept bigrConcept) {    
    checkNotCompleted();
    _conceptList.add(bigrConcept);    
  }

  /**
   * Get an iterator over the BigrConcepts in the list
   */
  public Iterator iterator() { 
    return _conceptList.iterator();    
  }

  /**
   * Get the number of BigrConcepts in the list
   */
  public int getSize() {    
    return _conceptList.size();    
  }

  /**
   * @return
   */
  public String getName() {
    return _name;
  }

  /**
   * @param string
   */
  public void setName(String string) {
    checkNotCompleted();
    _name = string;
  }
  
  /**
   * Returns true if the list has been marked as complete by calling the
   * {@link #setCompleted() method}.
   * @return the completed flag
   */
  public boolean isCompleted() {
    return _completed;
  }

  /**
   * Sets the completed flag to true.  Once a list has been marked 
   * as complete, it cannot be set back to incomplete.
   */
  public void setCompleted() {
    _completed = true;
    determineValidity();
    if (!isValid()) {
      throw new RuntimeException("The list is not valid: " + _validError);
    }
    else {
      _conceptList = Collections.unmodifiableList(_conceptList);
    }
  }

  /**
   * Return true if the list is valid.  This will return false both for
   * invalid graphs and graphs whose validity has not yet been determined
   * (by {@link determineValidity()}).
   */
  private boolean isValid() {
    return _valid;
  }

  /**
   * Throw an exception if either the list is incomplete
   * ({@link #setCompleted()} has not yet been called) or is complete
   * but invalid (for example, it has no name or concepts).
   */
  private void checkCompleted() {
    if (!isCompleted()) {
      throw new IllegalStateException("Method cannot be called prior to calling setCompleted().");
    }

    if (!isValid()) {
      throw new RuntimeException("The list is not valid: " + _validError);
    }
  }

  /**
   * Throw an exception if the list is complete
   * ({@link #setCompleted()} has already been called).
   */
  private void checkNotCompleted() {
    if (isCompleted()) {
      throw new IllegalStateException("Method can only be called prior to calling setCompleted().");
    }
  }
  
  /**
   * Determine whether the list is valid.  setCompleted calls this,
   * and it results in setting the _valid and _validError variables
   * to appropriate values.
   */
  private void determineValidity() {
    _valid = true;
    _validError = null;

    //make sure the name has been set
    if (ApiFunctions.isEmpty(getName())) {
      _valid = false;
      _validError = "The BigrConceptList has no name.";
    }
    
    //make sure there is at least one BigrConcept is the list
    if (getSize() <= 0) {
      _valid = false;
      _validError = "The " + getName() + " BigrConceptList contains no BigrConcepts.";
    }
    //make sure each BigrConcept is valid
    else {
      Iterator i = iterator();
      while (i.hasNext()) {
        BigrConcept concept = (BigrConcept)i.next();
        if (ApiFunctions.isEmpty(concept.getCode())) {
          _valid = false;
          _validError = "The " + getName() + " BigrConceptList contains one or more BigrConcepts with an empty getCode() value.";
        }
      }
    }
  }

  /**
   * Return true if the concept list contains a concept matching the specified cui.
   */
  public boolean containsConcept(String cui) {
    String localCui = ApiFunctions.safeString(ApiFunctions.safeTrim(cui));
    if (ApiFunctions.isEmpty(localCui)) {
      return false;
    }
    else {
      Iterator iterator = iterator();
      while (iterator.hasNext()) {
        if (((BigrConcept)iterator.next()).getCode().equalsIgnoreCase(localCui)) {
          return true;
        }
      }
      return false;
    }
  }

  /**
   * Return a LegalValueSet representation of this concept list.
   */
  public LegalValueSet toLegalValueSet() {
    LegalValueSet lvs = new LegalValueSet();
    Iterator iterator = iterator();
    while (iterator.hasNext()) {
      BigrConcept concept = (BigrConcept)iterator.next();
      lvs.addLegalValue(concept.getCode(), concept.getDescription());
    }
    return lvs;
  }

}
