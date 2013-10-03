/*
 * Created on Nov 5, 2003
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.ardais.bigr.orm.btx;

import java.util.Set;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.iltds.btx.BTXDetails;
import com.ardais.bigr.iltds.btx.BTXHistoryRecord;
import com.ardais.bigr.javabeans.UserDto;

/**
 * Represent the details of a concept-graph-display transaction.
 * 
 * <p>The transaction-specific fields are described below.  See also
 * {@link BTXDetails BTXDetails} for fields that are shared by all
 * business transactions.
 *
 * <h4>Required input fields</h4>
 * <ul>
 * <li>{@link #setGraphContext(String) Graph context}: The graph context of the concept graph
 *     to be displayed.</li>
 * </ul>
 *
 * <h4>Optional input fields</h4>
 * <ul>
 * <li>None.</li>
 * </ul>
 *
 * <h4>Output fields</h4>
 * <ul>
 * <li>{@link #setConceptGraph(BigrConceptGraph) Concept graph}: The concept graph to display.</li>
 * </ul>
 * 
 * @author sthomashow
 *
 *
 * 
 */
public class BtxDetailsGetDisplayBanner extends BTXDetails {
  private String _imageLogo = null;
  private UserDto _userDto = null;

  /** 
   * Default constructor.
   */
  public BtxDetailsGetDisplayBanner() {
    super();
  }

  /**
   * @see com.ardais.bigr.iltds.btx.BTXDetails#getBTXType()
   */
  public String getBTXType() {
    return BTXDetails.BTX_TYPE_DISPLAY_BANNER;
  }

  /**
   * @see com.ardais.bigr.iltds.btx.BTXDetails#getDirectlyInvolvedObjects()
   */
  public Set getDirectlyInvolvedObjects() {
    return null;
  }

  /**
   * @see com.ardais.bigr.iltds.btx.BTXDetails#doGetDetailsAsHTML()
   */
  protected String doGetDetailsAsHTML() {
    throw new ApiException("doGetDetailsAsHTML should not have been called, this class does not support transaction logging.");
  }

  /**
   * @see com.ardais.bigr.iltds.btx.BTXDetails#describeIntoHistoryRecord(com.ardais.bigr.iltds.btx.BTXHistoryRecord)
   */
  public void describeIntoHistoryRecord(BTXHistoryRecord history) {
    throw new ApiException("describeIntoHistoryRecord should not have been called, this class does not support transaction logging.");

    //super.describeIntoHistoryRecord(history);
  }

  /**
   * @see com.ardais.bigr.iltds.btx.BTXDetails#populateFromHistoryRecord(com.ardais.bigr.iltds.btx.BTXHistoryRecord)
   */
  public void populateFromHistoryRecord(BTXHistoryRecord history) {
    throw new ApiException("populateFromHistoryRecord should not have been called, this class does not support transaction logging.");

    //super.populateFromHistoryRecord(history);
  }

  /**
   * @return the image logo. This is an output parameter
   */
  public String getImageLogo() {
    return _imageLogo;
  }

  /**
   * Set the image logo.
   * 
   * @param imageLogo the image logo to display.  This is an output parameter.
   */
  public void setImageLogo(String imageLogo) {
    _imageLogo = imageLogo;
  }

  /**
   * @return
   */
  public UserDto getUserDto() {
    return _userDto;
  }

  /**
   * @param dto
   */
  public void setUserDto(UserDto dto) {
    _userDto = dto;
  }
}
