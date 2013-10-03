package com.ardais.bigr.iltds.btx;

import java.io.Serializable;

import com.ardais.bigr.javabeans.ManifestDto;

/**
 * Abstract base class of all shipping operations BTX details classes.
 */
public abstract class BtxDetailsShippingOperation extends BTXDetails implements Serializable {
  static final long serialVersionUID = 8939035052518296100L;
  
  private ManifestDto _manifestDto;

  public BtxDetailsShippingOperation() {
    super();
  }

  public ManifestDto getManifestDto() {
    return _manifestDto;
  }

  public void setManifestDto(ManifestDto manifestDto) {
    _manifestDto = manifestDto;
  }

}
