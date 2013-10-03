package com.gulfstreambio.kc.form;

import java.io.Serializable;

import com.ardais.bigr.api.ApiException;

/**
 * Holds a single value of a data element. 
 */
public class DataElementValue extends ElementValueBase implements Serializable {

  /**
   * The ADE associated with this data element value, if any.
   */
  private Ade _ade;  

  /**
   * Creates a new <code>DataElementValue</code>.
   */
  DataElementValue() {
    super();
  }

  DataElementValue(DataElementValue value) {
    super(value);
    Ade ade = value.getAde();
    if (ade != null) {
      createAde(ade);      
    }
  }

  /**
   * Returns the ADE associated with this data element value.
   * 
   * @return  The ADE, or null if this data element does not have an ADE.
   */
  public Ade getAde() {
    return _ade;
  }

  /**
   * Creates the ADE associated with this data element value.  If an attempt is made to call this
   * more than once on an instance, an exception is thrown, since each value can only have a
   * single ADE.
   * 
   * @return  The newly created ADE.
   * @throws ApiException if an attempt is made to call this more than once on this instance, 
   * since each value can only have a single ADE.
   */
  public Ade createAde() {
    Ade ade = new Ade();
    setAde(ade);
    return ade;
  }

  private Ade createAde(Ade ade) {
    Ade newAde = new Ade(ade);
    setAde(newAde);
    return newAde;
  }
  
  private void setAde(Ade ade) {
    if (_ade != null) {
      throw new ApiException("Attempt to create more than one ADE for a data element value.");
    }
    _ade = ade;
  }
}
