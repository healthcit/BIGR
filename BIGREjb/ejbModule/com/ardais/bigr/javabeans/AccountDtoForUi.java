package com.ardais.bigr.javabeans;

import java.io.Serializable;
import java.util.List;

public class AccountDtoForUi extends AccountDto implements Serializable {
  
  private LocationDataForUi _location;

  public AccountDtoForUi() {
    super();
  }
  
  /**
   * @return Returns the location.
   */
  public LocationData getLocation() {
    if (_location == null) {
      _location = new LocationDataForUi();
    }
    return _location;
  }

}
