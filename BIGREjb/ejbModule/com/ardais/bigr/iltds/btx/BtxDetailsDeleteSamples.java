package com.ardais.bigr.iltds.btx;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Represent the details of deleting one or more samples.
 * <p>
 * The transaction-specific fields are described below.  See also
 * {@link BTXDetails BTXDetails} for fields that are shared by all
 * business transactions.
 *
 * <h4>Required input fields</h4>
 * <ul>
 * <li>{@link #setSampleDatas(List) Sample datas}: A list of SampleData beans representing the samples
 *  to be deleted.</li>
 * </ul>
 *
 * <h4>Optional input fields</h4>
 * <ul>
 * <li>{@link #setDeleteReason(String) reason}: The reason for deleting the samples.</li>
 * </ul>
 *
 * <h4>Output fields</h4>
 * <ul>
 * <li>None.</li>
 * </ul>
 */
public class BtxDetailsDeleteSamples extends BTXDetails implements java.io.Serializable {
  private static final long serialVersionUID = 4231989504721284734L;

  private List _sampleDatas = null;
  private String _deleteReason = null;

  public BtxDetailsDeleteSamples() {
    super();
  }

  public String getBTXType() {
    return BTXDetails.BTX_TYPE_DELETE_SAMPLES;
  }

  /**
   * This method is called from the BTXDetails base class when its getDetailsAsHTML() 
   * method has been called from the BTXHistoryReaderBean.getHistoryDisplayLines 
   * method.  Since this transaction is not logged, there won't be any history so this 
   * method should never be called.  If for some reason this method is called it 
   * returns a message to the effect that it was called in error.
   *
   * @return an HTML string that defines how the transaction details are presented
   * in a transaction-history web page
   */
  protected String doGetDetailsAsHTML() {
    String msg = "BtxDetailsDeleteSamples.doGetDetailsAsHTML() method called in error!";
    return msg;
  }

  public Set getDirectlyInvolvedObjects() {
    Set set = new HashSet();
    return set;
  }

  /**
   * @return
   */
  public List getSampleDatas() {
    return _sampleDatas;
  }

  /**
   * @param list
   */
  public void setSampleDatas(List list) {
    _sampleDatas = list;
  }

  /**
   * @return
   */
  public String getDeleteReason() {
    return _deleteReason;
  }

  /**
   * @param string
   */
  public void setDeleteReason(String string) {
    _deleteReason = string;
  }

}