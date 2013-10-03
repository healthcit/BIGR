package com.ardais.bigr.kc.form;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.api.Escaper;
import com.ardais.bigr.btx.framework.BtxHistoryAttributes;
import com.ardais.bigr.iltds.btx.BTXDetails;
import com.ardais.bigr.iltds.btx.BTXHistoryRecord;
import com.ardais.bigr.kc.form.def.BigrFormDefinition;
import com.ardais.bigr.security.SecurityInfo;
import com.ardais.bigr.util.IcpUtils;
import com.gulfstreambio.kc.det.DataElementTaxonomy;
import com.gulfstreambio.kc.det.DetAdeElement;
import com.gulfstreambio.kc.det.DetDataElement;
import com.gulfstreambio.kc.det.DetService;
import com.gulfstreambio.kc.form.DataElement;
import com.gulfstreambio.kc.form.DataElementValue;
import com.gulfstreambio.kc.form.ElementValue;
import com.gulfstreambio.kc.form.FormInstance;
import com.gulfstreambio.kc.form.def.FormDefinitionDataElement;
import com.gulfstreambio.kc.form.def.data.DataFormDefinition;

public abstract class BtxDetailsKcFormInstance extends BTXDetails {

  private BigrFormInstance _formInstance;
  private BigrFormInstance[] _formInstances;
  private BigrFormDefinition _formDef;

  private BtxHistoryAttributes _historyObject;

  /**
   * Creates a new <code>BtxDetailsKcFormInstance</code>.
   */
  public BtxDetailsKcFormInstance() {
    super();
  }

  /* (non-Javadoc)
   * @see com.ardais.bigr.iltds.btx.BTXDetails#getDirectlyInvolvedObjects()
   */
  public Set getDirectlyInvolvedObjects() {
    return null;
  }

  public BigrFormInstance getFormInstance() {
    return _formInstance;
  }

  public void setFormInstance(BigrFormInstance formInstance) {
    _formInstance = formInstance;
  }

  public BigrFormInstance[] getFormInstances() {
    return _formInstances;
  }

  public void setFormInstances(BigrFormInstance[] formInstances) {
    _formInstances = formInstances;
  }

  public BigrFormDefinition getFormDefinition() {
    return _formDef;
  }

  public void setFormDefinition(BigrFormDefinition formDef) {
    _formDef = formDef;
  }

  /**
   * Fill a business transaction history record object with information
   * from this transaction details object.  This method will set <b>all</b>
   * fields on the history record, even ones not used by the this type of
   * transaction.  Fields that aren't used by this transaction type will be
   * set to their initial default values.
   * <p>
   * This method is only meant to be used internally by the business
   * transaction framework implementation.  Please don't use it anywhere else.
   *
   * @param history the history record object that will have its fields set to
   *    the transaction information.
   */
  public void describeIntoHistoryRecord(BTXHistoryRecord history) {
    super.describeIntoHistoryRecord(history);
    if (!this.isHasException()) {
      FormInstanceHistoryObject historyObject = new FormInstanceHistoryObject();
      historyObject.setFormInstance(getFormInstance().getKcFormInstance());
      historyObject.setFormDefinition(getFormInstance().getFormDefinition().getKcFormDefinition());
      history.setHistoryObject(historyObject.describeAsHistoryObject());
    }
  }
  
  /**
   * Populate the fields of this object with information contained in a
   * business transaction history record object.  This method must set <b>all</b>
   * fields on this object, as if it had been newly created immediately before
   * this method was called.  A runtime exception is thrown if the transaction type
   * represented by the history record doesn't match the transaction type represented
   * by this object.
   * <p>
   * This method is only meant to be used internally by the business
   * transaction framework implementation.  Please don't use it anywhere else.
   *
   * @param history the history record object that will be used as the
   *    information source.  A runtime exception is thrown if this is null.
   */
  public void populateFromHistoryRecord(BTXHistoryRecord history) {
    super.populateFromHistoryRecord(history);
    _historyObject = (BtxHistoryAttributes)history.getHistoryObject();
  }
  
  /* (non-Javadoc)
   * @see com.ardais.bigr.iltds.btx.BTXDetails#doGetDetailsAsHTML()
   */
  protected String doGetDetailsAsHTML() {
    // NOTE: This method must not make use of any fields that aren't
    // set by the populateFromHistoryRecord method.
    FormInstanceHistoryObject historyObject = new FormInstanceHistoryObject();
    return historyObject.doGetDetailsAsHTMLFull(
        _historyObject, getLoggedInUserSecurityInfo(), getBTXType());
  }
}
