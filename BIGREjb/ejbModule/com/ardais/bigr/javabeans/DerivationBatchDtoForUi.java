package com.ardais.bigr.javabeans;

import java.io.Serializable;
import java.util.List;

/**
 * Holds all data for a batch of derivation operations, lazily creating sub-DTOs as requested.
 */
public class DerivationBatchDtoForUi extends DerivationBatchDto implements Serializable {

  public DerivationBatchDtoForUi() {
    super();
  }

  public DerivationDto getDerivation(int index) {
    List derivationDtos = super.getDerivations();
    for (int i = derivationDtos.size(); i <= index; i++) {
      super.addDerivation(new DerivationDtoForUi()); 
    }
    return (DerivationDto) derivationDtos.get(index);
  }

}
