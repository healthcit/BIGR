package com.ardais.bigr.javabeans;

import java.io.Serializable;
import java.util.List;

/**
 * Holds all data for a list of storage units at a location, lazily creating 
 * storage units as requested.
 */
public class LocationDataForUi extends LocationData implements Serializable {

  public LocationDataForUi() {
    super();
  }

  public StorageUnitDto getNewStorageUnit(int index) {
    List storageUnitDtos = super.getNewStorageUnits();
    for (int i = storageUnitDtos.size(); i <= index; i++) {
      super.addNewStorageUnit(new StorageUnitDto()); 
    }
    return (StorageUnitDto) storageUnitDtos.get(index);
  }

}
