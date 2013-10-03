package com.ardais.bigr.iltds.validation;

import java.util.ArrayList;
import java.util.List;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.iltds.bizlogic.SampleFinder;
import com.ardais.bigr.iltds.btx.BtxActionErrors;
import com.ardais.bigr.iltds.helpers.FormLogic;
import com.ardais.bigr.iltds.helpers.IltdsUtils;
import com.ardais.bigr.iltds.helpers.SampleSelect;
import com.ardais.bigr.javabeans.DerivationDto;
import com.ardais.bigr.javabeans.SampleData;
import com.ardais.bigr.security.SecurityInfo;
import com.ardais.bigr.validation.AbstractValidator;
import com.ardais.bigr.validation.Validator;
import com.ardais.bigr.validation.ValidatorErrorListener;
import com.ardais.bigr.validation.ValidatorRequired;
import com.ardais.bigr.validation.ValidatorUserCanAccessInventoryGroups;

/**
 * Validates that the child samples of a single derivative operation can properly be assigned
 * to one or more inventory groups.
 * <p>
 * This validator may return one of two errors as follows, with insertion strings listed below the 
 * error:
 * <ul>
 * <li>{@link #ERROR_KEY1} - It was specified that inventory groups should be inherited from the
 *     parent samples, but the parent samples do not have an identical, non-empty set of inventory
 *     groups.
 *   <ol>
 *   <li>The name of the property containing the value.  This must be supplied by the caller
 *       by calling {@link #setPropertyDisplayName(java.lang.String) setPropertyDisplayName}. 
 *   </li>
 *   </ol>
 * </li>
 * <li>{@link #ERROR_KEY2} - Inventory groups were specified, but the user does not have access to
 *     one or more of them.
 *   <ol>
 *   <li>The name of the property containing the value.  This must be supplied by the caller
 *       by calling {@link #setPropertyDisplayName(java.lang.String) setPropertyDisplayName}. 
 *   </li>
 *   </ol>
 * </li>
 * </ul> 
 */
public class ValidatorDerivativeOperationInventoryGroupSpecifications extends AbstractValidator  {
  
  /**
   * The key of the error that is returned if the parent samples do not have an identical, 
   * non-empty set of inventory groups. 
   */
  public final static String ERROR_KEY1 = "iltds.error.genealogy.cannotInheritInventoryGroups";
  
  /**
   * The key of the error that is returned if the user does not have access to one or more
   * specified inventory groups. 
   */
  public final static String ERROR_KEY2 = "error.noValuesSpecified";

  private DerivationDto _dto;

  // The default error listener.
  class DefaultErrorListener implements ValidatorErrorListener {
    public boolean validatorError(Validator v, String errorKey) {
      ValidatorDerivativeOperationInventoryGroupSpecifications v1 =
        (ValidatorDerivativeOperationInventoryGroupSpecifications) v;
      v1.addErrorMessage(errorKey, v1.getPropertyDisplayName());
      return true;
    }
  }

  /**
   * Creates a new <code>ValidatorDerivativeOperationInventoryGroupSpecifications</code> validator.
   */
  public ValidatorDerivativeOperationInventoryGroupSpecifications() {
    super();
    addErrorKey(ERROR_KEY1);
    addErrorKey(ERROR_KEY2);
    ValidatorErrorListener listener = new DefaultErrorListener();
    addValidatorErrorListener(listener, ERROR_KEY1);
    addValidatorErrorListener(listener, ERROR_KEY2);
  }

  /* (non-Javadoc)
   * @see com.ardais.bigr.validation.AbstractValidator#validate()
   */
  public BtxActionErrors validate() {
    // Note: Currently only Ardais users are allowed to specify inventory group information
    //for the children of derivative operations.  If the user is not an Ardais user just return
    if (getSecurityInfo().isInRoleSystemOwner()) {
      doValidate();
    }
    return getActionErrors();
  }

  /*
   * Perform the validations.
   */  
  private void doValidate() {
    DerivationDto dto = getDto();
    String inheritInventoryGroups = dto.getInheritInventoryGroupsYN();
    
    //first make sure the user has specified either to inherit inventory groups
    //from the parent or has specified they want to explictly choose inventory
    //groups
    ValidatorRequired v = new ValidatorRequired();
    v.setValue(inheritInventoryGroups);
    v.setPropertyDisplayName(getPropertyDisplayName());
    getActionErrors().addErrors(v.validate());
    
    //if the user has specified to inherit inventory group membership from the parents, 
    //make sure that:
    //1) all parents share an identical non-empty set of inventory groups accessible by the user
    if (FormLogic.DB_YES.equalsIgnoreCase(inheritInventoryGroups)) {
      SecurityInfo securityInfo = getSecurityInfo();
      // Iterate over all parents in the derivation operation and retrieve their persisted version.
      List parents = dto.getParents();
      List myParents = new ArrayList();
      int parentCount = parents.size();
      for (int j = 0; j < parentCount; j++) {
        SampleData parent = (SampleData) parents.get(j);
        SampleData persistedParent =
          SampleFinder.find(securityInfo, SampleSelect.BASIC_IMPORTED_CONFIG_INVENTORYGROUPS, parent.getSampleId());
        if (persistedParent != null) {
          myParents.add(persistedParent);
        }
      }
      if (!IltdsUtils.isSamplesInIdenticalInventoryGroups(securityInfo, myParents)) {
        notifyValidatorErrorListener(ERROR_KEY1);
      }
    }
    //if the user has specified the inventory groups to use, make sure that:
    //1) they have selected at least one group, and 
    //2) that every group they have specified is accessible to them
    if (FormLogic.DB_NO.equalsIgnoreCase(inheritInventoryGroups)) {
      List groupIds = ApiFunctions.safeToList(dto.getInventoryGroups());
      if (ApiFunctions.isEmpty(groupIds)) {
        notifyValidatorErrorListener(ERROR_KEY2);
      }
      else {
        ValidatorUserCanAccessInventoryGroups x = new ValidatorUserCanAccessInventoryGroups();
        x.setSecurityInfo(getSecurityInfo());
        x.setInventoryGroupIds(groupIds);
        getActionErrors().addErrors(x.validate());
      }
    }
  }

  public DerivationDto getDto() {
    return _dto;
  }

  /**
   * Sets the DerivationDto that contains the details of the derivation operation that holds the
   * child samples to validate.
   * 
   * @param dto  the DerivationDto
   */
  public void setDto(DerivationDto dto) {
    _dto = dto;
  }

}
