package com.ardais.bigr.query.generator;

import java.util.HashMap;
import java.util.Map;

import com.ardais.bigr.query.filters.FilterConstants;
import com.ardais.bigr.query.filters.FilterStringEquals;
import com.ardais.bigr.query.filters.InitializableFromFilter;
import com.ardais.bigr.util.Constants;

/**
 * @author sislam
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class FilterHoldSoldStatus extends FilterStringEquals {

    static Map displayCodes = new HashMap();
    static {
        displayCodes.put(Constants.HOLDSOLD_ALL_SAMPLES, "Show All samples");
        displayCodes.put(Constants.HOLDSOLD_USER_CASE, "Show samples from my Hold/Sold cases");
        displayCodes.put(Constants.HOLDSOLD_USER_CASE_NOT, "Do not show samples from my Hold/Sold cases");
        displayCodes.put(Constants.HOLDSOLD_ACCOUNT_CASE, "Show samples from all Hold/Sold cases");
        displayCodes.put(Constants.HOLDSOLD_ACCOUNT_CASE_NOT, "Do not show samples from any Hold/Sold cases");
    }

   private String _userId;  // the user
   private String _accountKey;  // the account
   private boolean _userPrivilege;
   private boolean _accountPrivilege;

  /**
   * Constructor for FilterHoldSoldStatus.
   * @param key
   * @param filterValue
   */
  public FilterHoldSoldStatus(String filterValue) {
    super(FilterConstants.KEY_HOLD_SOLD_STATUS, filterValue);
  }

  /**
   * @see com.ardais.bigr.query.filters.QueryBuilderFilter#addToQueryBuilder(InitializableFromFilter)
   */
  public void addToQueryBuilder(InitializableFromFilter initFromFilt) {
    ProductSummaryQueryBuilder qb = (ProductSummaryQueryBuilder) initFromFilt;
    
    String operation = null;
    if (Constants.HOLDSOLD_ACCOUNT_CASE.equals(getFilterValue()) ||
         Constants.HOLDSOLD_USER_CASE.equals(getFilterValue()))
      operation = "intersect";
    else if (Constants.HOLDSOLD_ACCOUNT_CASE_NOT.equals(getFilterValue()) ||
         Constants.HOLDSOLD_USER_CASE_NOT.equals(getFilterValue()))
      operation = "minus";
    else
      return; // no restriction
        
    qb.setSecondaryPsqb(operation);
    ProductSummaryQueryBuilder soldSummary = qb.getSecondaryPsqb();
    
    if (Constants.HOLDSOLD_USER_CASE.equals(getFilterValue()) ||
        Constants.HOLDSOLD_USER_CASE_NOT.equals(getFilterValue())) {
          soldSummary.addFilterUserSoldCases(_userId);
          operation = "union";
          soldSummary.setSecondaryPsqb(operation);
          ProductSummaryQueryBuilder holdSummary = soldSummary.getSecondaryPsqb();
          holdSummary.addFilterUserHoldCases(_userId);
    }
        
    if (Constants.HOLDSOLD_ACCOUNT_CASE.equals(getFilterValue()) ||
        Constants.HOLDSOLD_ACCOUNT_CASE_NOT.equals(getFilterValue())) {
          soldSummary.addFilterAccountSoldCases(_accountKey);
          operation = "union";
          soldSummary.setSecondaryPsqb(operation);
          ProductSummaryQueryBuilder holdSummary = soldSummary.getSecondaryPsqb();
          holdSummary.addFilterAccountHoldCases(_accountKey);
    }
  }
  
  protected String displayName() {
      return "Hold/Sold Status";
  }

  protected Map codeDisplayMap() {
      return displayCodes;
  }

  public boolean hasUserId() {
      return _userId != null;
  }

  public boolean hasAccountKey() {
      return _accountKey != null;
  }
  /**
   * Sets the userId.
   * @param userId The userId to set
   */
  public void setUserId(String userId) {
    _userId = userId;
  }

  /**
   * Sets the accountKey.
   * @param accountKey The accountKey to set
   */
  public void setAccountKey(String accountKey) {
    _accountKey = accountKey;
  }

  /**
   * Returns the accountPrivilege.
   * @return boolean
   */
  public boolean isAccountPrivilege() {
    return _accountPrivilege;
  }

  /**
   * Returns the userPrivilege.
   * @return boolean
   */
  public boolean isUserPrivilege() {
    return _userPrivilege;
  }

  /**
   * Sets the accountPrivilege.
   * @param accountPrivilege The accountPrivilege to set
   */
  public void setAccountPrivilege(boolean accountPrivilege) {
    _accountPrivilege = accountPrivilege;
  }

  /**
   * Sets the userPrivilege.
   * @param userPrivilege The userPrivilege to set
   */
  public void setUserPrivilege(boolean userPrivilege) {
    _userPrivilege = userPrivilege;
  }

  /**
   * Returns the accountKey.
   * @return String
   */
  public String getAccountKey() {
    return _accountKey;
  }

  /**
   * Returns the userId.
   * @return String
   */
  public String getUserId() {
    return _userId;
  }

}
