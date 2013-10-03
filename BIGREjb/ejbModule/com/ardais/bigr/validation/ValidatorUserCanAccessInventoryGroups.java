package com.ardais.bigr.validation;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.iltds.assistants.LogicalRepository;
import com.ardais.bigr.iltds.btx.BtxActionError;
import com.ardais.bigr.iltds.btx.BtxActionErrors;
import com.ardais.bigr.security.SecurityInfo;
import com.ardais.bigr.util.LogicalRepositoryUtils;

/**
 * Validates that a user has access to all of the inventory groups in a list.
 * <p>
 * This validator may return one error as follows, with insertion strings listed below the error:
 * <ul>
 * <li>{@link #ERROR_KEY1} - The user does not have access to one or more inventory groups.
 *   <ol>
 *   <li>The list of invalid inventory group names, as returned by 
 *       {@link #getInvalidGroupNames() getInvalidGroupNames}. 
 *   </li>
 *   </ol>
 * </li>
 * </ul> 
 */
public class ValidatorUserCanAccessInventoryGroups extends AbstractValidator {
  
  /**
   * The key of the error that may be returned from this validator. 
   */
  public final static String ERROR_KEY1 = "error.noAccessToInventoryGroups";
  
  private List _inventoryGroupIds;
  private List _invalidGroupNames;

  // The default error listener.
  class DefaultErrorListener implements ValidatorErrorListener {
    public boolean validatorError(Validator v, String errorKey) {
      ValidatorUserCanAccessInventoryGroups v1 = (ValidatorUserCanAccessInventoryGroups) v;
      String invalidGroups =
        ApiFunctions.toCommaSeparatedList(ApiFunctions.toStringArray(v1.getInvalidGroupNames()));
      v1.addErrorMessage(ValidatorUserCanAccessInventoryGroups.ERROR_KEY1, invalidGroups);
      return true;
    }
  }

  /**
   * Creates a new <code>ValidatorUserCanAccessInventoryGroups</code> validator.
   */
  public ValidatorUserCanAccessInventoryGroups() {
    super();
    addErrorKey(ERROR_KEY1);
    addValidatorErrorListener(new DefaultErrorListener(), ERROR_KEY1);
  }

  /* (non-Javadoc)
   * @see com.ardais.bigr.javabeans.AbstractValidator#validate()
   */
  public BtxActionErrors validate() {
    if (!ApiFunctions.isEmpty(getInventoryGroupIds())) {
      List invalidGroupIds = new ArrayList();
      Iterator iterator = getInventoryGroupIds().iterator();
      SecurityInfo securityInfo = getSecurityInfo();
      while (iterator.hasNext()) {
        String inventoryGroupId = (String)iterator.next();
        if (!securityInfo.isLogicalRepositoryAccessible(inventoryGroupId)) {
          invalidGroupIds.add(inventoryGroupId);
        }
      }

      // Add an error if there are any invalid inventory group ids.
      if (invalidGroupIds.size() > 0) {
        List invalidGroups = LogicalRepositoryUtils.getLogicalRepositoriesById(invalidGroupIds);
        List invalidGroupNames = new ArrayList();
        Iterator groupIterator = invalidGroups.iterator();
        while (groupIterator.hasNext()) {
          LogicalRepository inventoryGroup = (LogicalRepository)groupIterator.next();
          invalidGroupNames.add(inventoryGroup.getFullName());
        }
        setInvalidGroupNames(invalidGroupNames);
        notifyValidatorErrorListener(ERROR_KEY1);
      }
    }
    
    return getActionErrors();
  }

  public List getInventoryGroupIds() {
    return _inventoryGroupIds;
  }

  public void setInventoryGroupIds(List list) {
    _inventoryGroupIds = list;
  }

  /**
   * Returns the list of invalid inventory group names, if any, as a list of strings.
   * 
   * @return  The list of invalid inventory group names.
   */
  public List getInvalidGroupNames() {
    return _invalidGroupNames;
  }

  private void setInvalidGroupNames(List list) {
    _invalidGroupNames = list;
  }

}
