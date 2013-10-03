package com.ardais.bigr.iltds.databeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.list.UnmodifiableList;
import org.apache.commons.collections.map.UnmodifiableMap;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.concept.BigrConcept;
import com.ardais.bigr.concept.BigrConceptList;
import com.ardais.bigr.configuration.SystemConfiguration;

public class DerivativeOperationConfiguration implements Serializable {
  public static final long serialVersionUID = 9035772793722905088L;

  private Map _derivativeOperationMap = new HashMap();  //used for quick reference to a derivative operation
  private List _derivativeOperationList = new ArrayList();  //used for ordered reference to derivative operations
  private boolean _immutable = false;

  public DerivativeOperationConfiguration() {
    super();
  }

  /**
   * Add a derivative operation object to the derivative operation map and list. This method is 
   * called from the digester when a derivative operation element is encountered while 
   * parsing the XML string.
   * 
   * @param derivativeOperation The derivative operation object to add to the derivative operation 
   * map and list.
   */
  public void addDerivativeOperation(DerivativeOperation derivativeOperation) {
    checkImmutable();
    if (derivativeOperation != null) {
      _derivativeOperationMap.put(derivativeOperation.getCui(), derivativeOperation);
      _derivativeOperationList.add(derivativeOperation);
    }
  }

  /**
   * Convert the derivative operation configuration object into XML. This method in turn will call 
   * the toXml method of each derivative operation object contained within this object. The order 
   * of the derivative operation is derived from the derivative operation concept graph.
   * 
   * @return The XML representation of the derivative operation configuration object.
   */
  public String toXml() {
    StringBuffer xml = new StringBuffer();

    xml.append("<derivativeOperationConfiguration>\n");

    // Get the list of all derivative operations. Iterate through that list. The order of the 
    // list is defined by the concept graph. Fetch each derivative operation object from the hash 
    // map.
    BigrConceptList derivativeOperationList = 
      SystemConfiguration.getConceptList(SystemConfiguration.CONCEPT_LIST_DERIVATION_OPERATIONS);
    Iterator iterator = derivativeOperationList.iterator();

    // Call the toXml method for each derivative operation stored within the 
    // derivative operation map.
    while (iterator.hasNext()) {
      String derivativeOperationCui = ((BigrConcept)iterator.next()).getCode();
      DerivativeOperation dop = (DerivativeOperation) _derivativeOperationMap.get(derivativeOperationCui);
      if (dop != null) {
        xml.append(dop.toXml());
      }
    }
    xml.append("</derivativeOperationConfiguration>\n");

    return xml.toString();
  }

  /**
   * 
   * @return
   */
  public String toHtml() {
    StringBuffer html = new StringBuffer();

    html.append("Derivative Operations Configuration: <ul>");

    // Get the list of all derivative operations. Iterate through that list. The order of the 
    // list is defined by the concept graph. Fetch each derivative operation object from the hash 
    // map.
    BigrConceptList derivativeOperationList = 
      SystemConfiguration.getConceptList(SystemConfiguration.CONCEPT_LIST_DERIVATION_OPERATIONS);
    Iterator iterator = derivativeOperationList.iterator();

    while (iterator.hasNext()) {
      String derivativeOperationCui = ((BigrConcept)iterator.next()).getCode();
      DerivativeOperation dop = (DerivativeOperation) _derivativeOperationMap.get(derivativeOperationCui);
      if (dop != null) {
        html.append("<li>");
        html.append(dop.toHtml());
        html.append("</li>");
      }
    }
    html.append("</ul>");

    return html.toString();
  }

  /**
   * Return the derivative operation map.
   * 
   * @return A read-only version of this object's derivative operation map.
   */
  public Map getDerivativeOperationMap() {
    if (_derivativeOperationMap instanceof UnmodifiableMap) {
      return _derivativeOperationMap;
    }
    else {
      return Collections.unmodifiableMap(_derivativeOperationMap);
    }
  }

  /**
   * Return the derivative operation list.
   * 
   * @return A read-only version of this object's derivative operation list.
   */
  public List getDerivativeOperationList() {
    if (_derivativeOperationList instanceof UnmodifiableList) {
      return _derivativeOperationList;
    }
    else {
      return Collections.unmodifiableList(_derivativeOperationList);
    }
  }

  /**
   * This method validates the derivative operation cui. The derivative operation cui 
   * cannot be null or invalid, otherwise an exception is thrown.
   * 
   * @param derivativeOperationCui The derivative operation to validate against.
   */
  private void validateDerivativeOperationCui(String derivativeOperationCui) {
    // Validate the derivative operation.
    if (ApiFunctions.isEmpty(derivativeOperationCui)) {
      throw new ApiException("Derivative operation CUI is null.");
    }
    else {
      BigrConceptList knownDerivativeOperations = 
         SystemConfiguration.getConceptList(SystemConfiguration.CONCEPT_LIST_DERIVATION_OPERATIONS);
      if (!knownDerivativeOperations.containsConcept(derivativeOperationCui)) {
        throw new ApiException("Invalid derivative operation CUI: " + derivativeOperationCui + ".");
      }
    }
  }

  /**
   * Set this instance to be immutable.  Any attempts to modify immutable
   * instances will throw an exception.
   */
  public void setImmutable() {
    _immutable = true;
    Iterator derivativeOperationIterator = _derivativeOperationList.iterator();
    while (derivativeOperationIterator.hasNext()) {
      ((DerivativeOperation)derivativeOperationIterator.next()).setImmutable();
    }
    _derivativeOperationMap = Collections.unmodifiableMap(_derivativeOperationMap);
    _derivativeOperationList = Collections.unmodifiableList(_derivativeOperationList);
  }

  /**
   * Is this instance immutable.
   */
  public boolean isImmutable() {
    return _immutable;
  }

  /**
   * Throw an exception if this instance is immutable.
   */
  private void checkImmutable() {
    if (_immutable) {
      throw new IllegalStateException("Attempted to modify an immutable DerivativeOperationConfiguration: " + this);
    }
  }

  /**
  * Get the derivative operation matching the specified cui
  * @param derivativeOperationCui
  * @return  the DerivativeOperation (if any) matching the specified cui
  */  
  public DerivativeOperation getDerivativeOperation(String derivativeOperationCui) {
    validateDerivativeOperationCui(derivativeOperationCui);
    return (DerivativeOperation)getDerivativeOperationMap().get(derivativeOperationCui);
  }
}
