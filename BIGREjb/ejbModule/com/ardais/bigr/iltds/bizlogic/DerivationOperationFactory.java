package com.ardais.bigr.iltds.bizlogic;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.configuration.SystemConfiguration;
import com.ardais.bigr.javabeans.DerivationDto;

/**
 * A factory for creating {@link DerivationOperation} instances.  These instances are created
 * based on information in the {@link com.ardais.bigr.javabeans.DerivationDto} specified in
 * the {@link #createDerivationOperation(com.ardais.bigr.javabeans.DerivationDto)} method.
 */
public class DerivationOperationFactory {

  // The singleton instance for this class.
  public static DerivationOperationFactory SINGLETON = new DerivationOperationFactory();

  /**
   * Creates and returns the necessary DerivationOperation instance based on the information
   * in the specified DTO.  
   *  
   * @param dto  the DerivationDto that contains the details of the derivation operation
   * @return  The DerivationOperation instance.
   */
  public DerivationOperation createDerivationOperation(DerivationDto dto) {
    String operationCui = dto.getOperationCui();

    // Check that operation is valid first.
    if (!SystemConfiguration
      .getConceptList(SystemConfiguration.CONCEPT_LIST_DERIVATION_OPERATIONS)
      .containsConcept(operationCui)) {
      throw new ApiException(
        "Unknown derivative operation ('" + operationCui + "') encountered.");
    }
    
    // For now, always return BasicDerivationOperationService since it handles many things in 
    // a generic fashion.
    return new BasicDerivationOperationService(dto);
  }

  // Private since this class is a singleton which is accessed by its static SINGLETON field. 
  private DerivationOperationFactory() {
    super();
  }

}
