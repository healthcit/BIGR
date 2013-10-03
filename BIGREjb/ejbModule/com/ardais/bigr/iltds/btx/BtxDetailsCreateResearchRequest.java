package com.ardais.bigr.iltds.btx;

import java.io.Serializable;

import com.ardais.bigr.iltds.helpers.RequestType;

/**
 * Class to represent a create research request business transaction.
 * <p>
 * The transaction-specific fields are described below.  See also
 * {@link BTXDetails BTXDetails} and {@link BtxDetailsRequestOperation BtxDetailsRequestOperation} for fields 
 * that are shared by all business transactions.
 *
 * <h4>Required input fields</h4>
 * <ul>
 * <li>{@link #setInputRequestDto(RequestDto) inputRequestDto}: The RequestDto containing all 
 *      necessary input data.</li>
 * </ul>
 *
 * <h4>Optional input fields</h4>
 * <ul>
 * <li>None.</li>
 * </ul>
 *
 * <h4>Output fields</h4>
 * <ul>
 * <li>{@link #setOutputRequestDto(RequestDto) outputRequestDto}: The RequestDto containing all 
 *      necessary output data.</li>
 * </ul>
 */
public class BtxDetailsCreateResearchRequest extends BtxDetailsCreateRequest implements Serializable {
  private static final long serialVersionUID = -7273150918391848739L;
  
  public BtxDetailsCreateResearchRequest() {
    super();
  }
  
  public String getBTXType() {
    return BTXDetails.BTX_TYPE_CREATE_RESEARCH_REQUEST;
  }
  
  public RequestType getRequestType() {
    return RequestType.RESEARCH;
  }

}
