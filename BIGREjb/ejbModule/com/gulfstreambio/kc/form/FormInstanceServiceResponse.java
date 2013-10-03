package com.gulfstreambio.kc.form;

import java.util.ArrayList;
import java.util.List;

import com.ardais.bigr.iltds.btx.BtxActionErrors;

/**
 * The response object that is returned by all {@link FormInstanceService} methods.  
 * One of the following methods will return the value that comprises the actual response
 * from the <code>FormInstanceService</code> that is called.
 * <ul>
 * <li>{@link #getErrors() getErrors}: If there was one or more validation errors they will be 
 * returned by a call to this method.  Clients should call this method first to discover if
 * the method call produced any errors before attempting to call any other method.
 * </li> 
 * <li>{@link #getFormInstance() getFormInstance}: If the <code>FormInstanceService</code>
 * method that was called returns a single {@link FormInstance}, then this method returns it. 
 * </li> 
 * <li>{@link #getFormInstances() getFormInstances}: If the <code>FormInstanceService</code>
 * method that was called returns any array of <code>FormInstance</code>s, then this method 
 * returns them. 
 * </li> 
 * </ul>
 */
public class FormInstanceServiceResponse {
  
  private List _forms;
  private BtxActionErrors _errors;

  public FormInstanceServiceResponse() {
    super();
  }

  /**
   * Returns the errors that are part of this response. 
   * 
   * @return  The <code>BtxActionErrors</code> instance that contains the errors.  If there are
   *          no errors, then an empty <code>BtxActionErrors</code> is returned.
   */
  public BtxActionErrors getErrors() {
    return (_errors == null) ? new BtxActionErrors() : _errors;
  }

  /**
   * Returns the form instance that is part of this response. 
   * 
   * @return  The <code>FormInstance</code> instance.
   */
  public FormInstance getFormInstance() {
    return (_forms == null) ? null : (FormInstance) _forms.get(0);      
  }

  /**
   * Returns the array of form instances that are part of this response. 
   * 
   * @return  The array of <code>FormInstance</code> instances.
   */
  public FormInstance[] getFormInstances() {
    return (_forms == null) 
      ? new FormInstance[0] : (FormInstance[]) _forms.toArray(new FormInstance[0]);
  }

  void setErrors(BtxActionErrors errors) {
    _errors = errors;
  }

  void addFormInstance(FormInstance formInstance) {
    if (_forms == null) {
      _forms = new ArrayList();
    }
    _forms.add(formInstance);
  }
}
