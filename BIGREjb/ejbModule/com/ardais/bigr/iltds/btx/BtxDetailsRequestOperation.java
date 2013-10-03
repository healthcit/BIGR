package com.ardais.bigr.iltds.btx;

import java.io.Serializable;

import com.ardais.bigr.javabeans.RequestDto;

/**
 * Abstract class to represent a request operation business transaction.
 * <p>
 * The transaction-specific fields are described below.  See also
 * {@link BTXDetails BTXDetails} for fields that are shared by all
 * business transactions.
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
public abstract class BtxDetailsRequestOperation extends BTXDetails implements Serializable {
  private static final long serialVersionUID = 827720121500719306L;

  private RequestDto _inputRequestDto = null;
  private RequestDto _outputRequestDto = null;

  public BtxDetailsRequestOperation() {
      super();
  }
  /**
   * @return
   */
  public RequestDto getInputRequestDto() {
    return _inputRequestDto;
  }

  /**
   * @return
   */
  public RequestDto getOutputRequestDto() {
    return _outputRequestDto;
  }

  /**
   * @param dto
   */
  public void setInputRequestDto(RequestDto dto) {
    _inputRequestDto = dto;
  }

  /**
   * @param dto
   */
  public void setOutputRequestDto(RequestDto dto) {
    _outputRequestDto = dto;
  }

}