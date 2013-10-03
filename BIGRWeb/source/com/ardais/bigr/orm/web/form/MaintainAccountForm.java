package com.ardais.bigr.orm.web.form;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BasicDynaClass;
import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.DynaClass;
import org.apache.commons.beanutils.DynaProperty;
import org.apache.struts.action.ActionErrors;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.configuration.SystemConfiguration;
import com.ardais.bigr.iltds.assistants.LegalValue;
import com.ardais.bigr.iltds.assistants.LegalValueSet;
import com.ardais.bigr.iltds.btx.BTXDetails;
import com.ardais.bigr.iltds.helpers.FormLogic;
import com.ardais.bigr.javabeans.AccountDto;
import com.ardais.bigr.javabeans.AccountDtoForUi;
import com.ardais.bigr.orm.btx.BtxDetailsFindAccounts;
import com.ardais.bigr.orm.helpers.BigrGbossData;
import com.ardais.bigr.util.ArtsConstants;
import com.ardais.bigr.util.Constants;
import com.ardais.bigr.web.action.BigrActionMapping;
import com.ardais.bigr.web.form.BigrActionForm;
import com.ardais.bigr.web.servlet.BigrRequestProcessor;

public class MaintainAccountForm extends BigrActionForm {

  //holds search criteria when looking for accounts, or account data when creating/modifying accounts
  private AccountDto _accountData;
  
  //a LegalValueSet of account types
  private LegalValueSet _accountTypeChoices;
  
  //a LegalValueSet of account statuses
  private LegalValueSet _accountStatusChoices;
  
  //a LegalValueSet of choices for limiting the account to viewing linked cases only 
  private LegalValueSet _linkedCasesOnlyChoices;
  
  //a LegalValueSet of choices for requiring samples in the account to have unique alias values 
  private LegalValueSet _requireUniqueSampleAliasesChoices;
  
  //a LegalValueSet of choices for requiring samples in the account to have alias values 
  private LegalValueSet _requireSampleAliasesChoices;
  
  //a LegalValueSet of password policies 
  private LegalValueSet _passwordPolicyChoices;
  
  //a LegalValueSet of storage unit type choices 
  private LegalValueSet _storageUnitTypeChoices;
  
  //a LegalValueSet of slot per drawer choices 
  private LegalValueSet _slotPerDrawerChoices;
  
  //a List of AccountDtos populated when an account search is performed
  private List _matchingAccounts;
  
  //a LegalValueSet of donor registration form choices
  private LegalValueSet _donorRegistrationFormChoices;
  
  //method to populate the request from a btxDetails object
  //used in this class to handle the case of a non-system owner account clicking on the
  //"Account Management" menu item.  When this happens, the performFindAccountsStart()
  //method in BtxPerformerOrmOperations is invoked and recognizes this situation.  It 
  //creates an AccountDto object, sets its id, and returns that in the BtxDetails class.
  //When this situation occurs we need to grab that AccountDto object and save it to the
  //request so our form is populated correctly.  Otherwise that information is lost after
  //struts calls reset on the form.
  public void populateRequestFromBtxDetails(BTXDetails btxDetails, 
                                            BigrActionMapping mapping,
                                            HttpServletRequest request) {

    if (btxDetails instanceof BtxDetailsFindAccounts &&
        "accountDetermined".equalsIgnoreCase(btxDetails.getActionForward().getTargetName())) {
      try {
        DynaProperty[] properties = new DynaProperty[] {new DynaProperty("accountData", AccountDto.class)};
        DynaClass myClass = new BasicDynaClass("", null, properties);
        DynaBean populatorSource = myClass.newInstance();
        populatorSource.set("accountData", ((BtxDetailsFindAccounts)btxDetails).getAccountData());
        request.setAttribute(BigrRequestProcessor.ACTION_FORM_POPULATOR_SOURCE_KEY, populatorSource);
      }
      catch (Exception e) {
        ApiFunctions.throwAsRuntimeException(e);
      }
    }
                                       
  }

  /**
   * @see com.ardais.bigr.web.form.BigrActionForm#doReset(BigrActionMapping, HttpServletRequest)
   */
  protected void doReset(BigrActionMapping mapping, HttpServletRequest request) {
    _accountData = null;
    resetAccountTypeChoices();
    resetAccountStatusChoices();
    resetLinkedCasesOnlyChoices();
    resetRequireUniqueSampleAliasesChoices();
    resetRequireSampleAliasesChoices();
    resetPasswordPolicyChoices();
    resetStorageUnitTypeChoices();
    resetSlotPerDrawerChoices();
    _matchingAccounts = null;
    _donorRegistrationFormChoices = null;
  }
  
  private void resetAccountTypeChoices() {
    _accountTypeChoices = new LegalValueSet();
    _accountTypeChoices.addLegalValue("", "Select");
    Iterator iterator = Constants.ACCOUNT_TYPE_MAP.keySet().iterator();
    while (iterator.hasNext()) {
      String key = (String)iterator.next();
      _accountTypeChoices.addLegalValue(key, (String)Constants.ACCOUNT_TYPE_MAP.get(key));
    }
  }
  
  private void resetAccountStatusChoices() {
    _accountStatusChoices = new LegalValueSet();
    _accountStatusChoices.addLegalValue("", "Select");
    Iterator iterator = Constants.ACCOUNT_STATUS_MAP.keySet().iterator();
    while (iterator.hasNext()) {
      String key = (String)iterator.next();
      _accountStatusChoices.addLegalValue(key, (String)Constants.ACCOUNT_STATUS_MAP.get(key));
    }
  }
  
  private void resetLinkedCasesOnlyChoices() {
    _linkedCasesOnlyChoices = new LegalValueSet();
    _linkedCasesOnlyChoices.addLegalValue("", "Select");
    _linkedCasesOnlyChoices.addLegalValue(FormLogic.DB_YES, FormLogic.DB_YES_TEXT);
    _linkedCasesOnlyChoices.addLegalValue(FormLogic.DB_NO, FormLogic.DB_NO_TEXT);
  }
  
  private void resetRequireUniqueSampleAliasesChoices() {
    _requireUniqueSampleAliasesChoices = new LegalValueSet();
    _requireUniqueSampleAliasesChoices.addLegalValue("", "Select");
    _requireUniqueSampleAliasesChoices.addLegalValue(FormLogic.DB_YES, FormLogic.DB_YES_TEXT);
    _requireUniqueSampleAliasesChoices.addLegalValue(FormLogic.DB_NO, FormLogic.DB_NO_TEXT);
  }
  
  private void resetRequireSampleAliasesChoices() {
    _requireSampleAliasesChoices = new LegalValueSet();
    _requireSampleAliasesChoices.addLegalValue("", "Select");
    _requireSampleAliasesChoices.addLegalValue(FormLogic.DB_YES, FormLogic.DB_YES_TEXT);
    _requireSampleAliasesChoices.addLegalValue(FormLogic.DB_NO, FormLogic.DB_NO_TEXT);
  }
  
  private void resetPasswordPolicyChoices() {
    _passwordPolicyChoices = new LegalValueSet();
    _passwordPolicyChoices.addLegalValue("", "Select");
    Iterator iterator = BigrGbossData.getValueSetAsLegalValueSet(ArtsConstants.VALUE_SET_ACCOUNT_PASSWORD_POLICY).getIterator();
    while (iterator.hasNext()) {
      LegalValue lv = (LegalValue)iterator.next();
      _passwordPolicyChoices.addLegalValue(lv.getValue(), lv.getDisplayValue());
    }
  }
  
  private void resetStorageUnitTypeChoices() {
    _storageUnitTypeChoices = new LegalValueSet();
    _storageUnitTypeChoices.addLegalValue("", "Select");
    Iterator iterator = SystemConfiguration.getConceptList(SystemConfiguration.CONCEPT_LIST_STORAGE_TYPES).toLegalValueSet().getIterator();
    while (iterator.hasNext()) {
      LegalValue lv = (LegalValue)iterator.next();
      _storageUnitTypeChoices.addLegalValue(lv.getValue(), lv.getDisplayValue());
    }
  }
  
  private void resetSlotPerDrawerChoices() {
    //NOTE - if you modify this set of values or alter the mappings between
    //letters and values MAKE SURE to also modify the translateSlotIdToLetter(int) 
    //method in com.ardais.bigr.orm.helpers.FormLogic
    _slotPerDrawerChoices = new LegalValueSet();
    _slotPerDrawerChoices.addLegalValue("", "Select");
    _slotPerDrawerChoices.addLegalValue("1", "A");
    _slotPerDrawerChoices.addLegalValue("2", "B");
    _slotPerDrawerChoices.addLegalValue("3", "C");
    _slotPerDrawerChoices.addLegalValue("4", "D");
    _slotPerDrawerChoices.addLegalValue("5", "E");
    _slotPerDrawerChoices.addLegalValue("6", "F");
    _slotPerDrawerChoices.addLegalValue("7", "G");
    _slotPerDrawerChoices.addLegalValue("8", "H");
    _slotPerDrawerChoices.addLegalValue("9", "I");
    _slotPerDrawerChoices.addLegalValue("10", "J");
    _slotPerDrawerChoices.addLegalValue("11", "K");
    _slotPerDrawerChoices.addLegalValue("12", "L");
    _slotPerDrawerChoices.addLegalValue("13", "M");
    _slotPerDrawerChoices.addLegalValue("14", "N");
    _slotPerDrawerChoices.addLegalValue("15", "O");
    _slotPerDrawerChoices.addLegalValue("16", "P");
    _slotPerDrawerChoices.addLegalValue("17", "Q");
    _slotPerDrawerChoices.addLegalValue("18", "R");
    _slotPerDrawerChoices.addLegalValue("19", "S");
    _slotPerDrawerChoices.addLegalValue("20", "T");
    _slotPerDrawerChoices.addLegalValue("21", "U");
    _slotPerDrawerChoices.addLegalValue("22", "V");
    _slotPerDrawerChoices.addLegalValue("23", "W");
    _slotPerDrawerChoices.addLegalValue("24", "X");
    _slotPerDrawerChoices.addLegalValue("25", "Y");
    _slotPerDrawerChoices.addLegalValue("26", "Z");
}

  /**
   * @see com.ardais.bigr.web.form.BigrActionForm#doValidate(BigrActionMapping, HttpServletRequest)
   */
  protected ActionErrors doValidate(BigrActionMapping mapping, HttpServletRequest request) {
     return null; 
  }
  
  /**
   * @return
   */
  public LegalValueSet getAccountTypeChoices() {
    return _accountTypeChoices;
  }
  
  /**
   * @return
   */
  public LegalValueSet getAccountStatusChoices() {
    return _accountStatusChoices;
  }
  
  /**
   * @return
   */
  public LegalValueSet getLinkedCasesOnlyChoices() {
    return _linkedCasesOnlyChoices;
  }
  
  /**
   * @return
   */
  public LegalValueSet getRequireUniqueSampleAliasesChoices() {
    return _requireUniqueSampleAliasesChoices;
  }
  
  /**
   * @return
   */
  public LegalValueSet getRequireSampleAliasesChoices() {
    return _requireSampleAliasesChoices;
  }
  
  /**
   * @return
   */
  public LegalValueSet getPasswordPolicyChoices() {
    return _passwordPolicyChoices;
  }

  /**
   * @return
   */
  public AccountDto getAccountData() {
    if (_accountData == null) {
      _accountData = new AccountDtoForUi();
    }
    return _accountData;
  }
  
  /**
   * @return
   */
  public List getMatchingAccounts() {
    return _matchingAccounts;
  }

  /**
   * @param dto
   */
  public void setAccountData(AccountDto dto) {
    _accountData = dto;
  }

  /**
   * @param list
   */
  public void setMatchingAccounts(List list) {
    _matchingAccounts = list;
  }

  /**
   * @return Returns the slotPerDrawerChoices.
   */
  public LegalValueSet getSlotPerDrawerChoices() {
    return _slotPerDrawerChoices;
  }
  
  /**
   * @return Returns the storageUnitTypeChoices.
   */
  public LegalValueSet getStorageUnitTypeChoices() {
    return _storageUnitTypeChoices;
  }
  
  public LegalValueSet getDonorRegistrationFormChoices() {
    return _donorRegistrationFormChoices;
  }
  
  public void setDonorRegistrationFormChoices(LegalValueSet donorRegistrationFormChoices) {
    _donorRegistrationFormChoices = donorRegistrationFormChoices;
  }
}
