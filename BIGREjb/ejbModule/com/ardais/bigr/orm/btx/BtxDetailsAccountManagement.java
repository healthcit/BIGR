package com.ardais.bigr.orm.btx;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.api.Escaper;
import com.ardais.bigr.beanutils.BigrBeanUtilsBean;
import com.ardais.bigr.btx.framework.BtxHistoryAttributes;
import com.ardais.bigr.iltds.assistants.LogicalRepository;
import com.ardais.bigr.iltds.btx.BTXDetails;
import com.ardais.bigr.iltds.btx.BTXHistoryRecord;
import com.ardais.bigr.iltds.helpers.FormLogic;
import com.ardais.bigr.javabeans.AccountDto;
import com.ardais.bigr.javabeans.AddressDto;
import com.ardais.bigr.javabeans.ContactDto;
import com.ardais.bigr.javabeans.LocationData;
import com.ardais.bigr.javabeans.PrivilegeDto;
import com.ardais.bigr.javabeans.StorageUnitDto;
import com.ardais.bigr.javabeans.StorageUnitSummaryDto;
import com.ardais.bigr.orm.helpers.AccountData;
import com.ardais.bigr.security.SecurityInfo;
import com.ardais.bigr.util.Constants;
import com.ardais.bigr.util.IcpUtils;
import com.gulfstreambio.gboss.GbossFactory;

public abstract class BtxDetailsAccountManagement extends BTXDetails implements Serializable {
  private static final long serialVersionUID = 8152706773244833043L;
  private static final String ACCOUNT_OPEN_DATE_STRING = "accountData.openDateAsString";
  private static final String ACCOUNT_ACTIVE_DATE_STRING = "accountData.activeDateAsString";
  private static final String ACCOUNT_CLOSE_DATE_STRING = "accountData.closeDateAsString";
  private static final String ACCOUNT_DEACTIVATE_DATE_STRING = "accountData.deactivateDateAsString";
  private static final String ACCOUNT_REACTIVATE_DATE_STRING = "accountData.reactivateDateAsString";
  private static final String ACCOUNT_LOCATIONS = "locations";
  private static final String ACCOUNT_LOCATION_EXISTING_STORAGE_UNITS = "existingStorageUnits";
  private static final String ACCOUNT_LOCATION_NEW_STORAGE_UNITS = "newStorageUnits";
  
  private AccountDto _accountData;
  private BtxHistoryAttributes _attributes;

  /**
   * 
   */
  public BtxDetailsAccountManagement() {
    super();
  }

  /**
   * Fill a business transaction history record object with information
   * from this transaction details object.  This method will set <b>all</b>
   * fields on the history record, even ones not used by the this type of
   * transaction.  Fields that aren't used by this transaction type will be
   * set to their initial default values.
   * <p>
   * This method is only meant to be used internally by the business
   * transaction framework implementation.  Please don't use it anywhere else.
   *
   * @param history the history record object that will have its fields set to
   *    the transaction information.
   */
  public void describeIntoHistoryRecord(BTXHistoryRecord history) {
    super.describeIntoHistoryRecord(history);
    history.setHistoryObject(describeAsHistoryObject());
  }

  /**
   * Populate the fields of this object with information contained in a
   * business transaction history record object.  This method must set <b>all</b>
   * fields on this object, as if it had been newly created immediately before
   * this method was called.  A runtime exception is thrown if the transaction type
   * represented by the history record doesn't match the transaction type represented
   * by this object.
   * <p>
   * This method is only meant to be used internally by the business
   * transaction framework implementation.  Please don't use it anywhere else.
   *
   * @param history the history record object that will be used as the
   *    information source.  A runtime exception is thrown if this is null.
   */
  public void populateFromHistoryRecord(BTXHistoryRecord history) {
    super.populateFromHistoryRecord(history);
    AccountDto account = new AccountDto();
    setAccountData(account);
    _attributes = (BtxHistoryAttributes)history.getHistoryObject();
    if (_attributes != null) {
      Map attributeMap = _attributes.asMap();
      //set the simple attributes
      BigrBeanUtilsBean.SINGLETON.copyProperties(this,attributeMap);
      //now set the complex attributes.  First take care of the various date attributes
      //on the account.
      String openDate = (String)_attributes.getAttribute(ACCOUNT_OPEN_DATE_STRING);
      if (!ApiFunctions.isEmpty(openDate)) {
        account.setOpenDate(new Date((new Long(openDate)).longValue()));
      }
      String activeDate = (String)_attributes.getAttribute(ACCOUNT_ACTIVE_DATE_STRING);
      if (!ApiFunctions.isEmpty(activeDate)) {
        account.setActiveDate(new Date((new Long(activeDate)).longValue()));
      }
      String closeDate = (String)_attributes.getAttribute(ACCOUNT_CLOSE_DATE_STRING);
      if (!ApiFunctions.isEmpty(closeDate)) {
        account.setCloseDate(new Date((new Long(closeDate)).longValue()));
      }
      String deactivateDate = (String)_attributes.getAttribute(ACCOUNT_DEACTIVATE_DATE_STRING);
      if (!ApiFunctions.isEmpty(deactivateDate)) {
        account.setDeactivateDate(new Date((new Long(deactivateDate)).longValue()));
      }
      String reactivateDate = (String)_attributes.getAttribute(ACCOUNT_REACTIVATE_DATE_STRING);
      if (!ApiFunctions.isEmpty(reactivateDate)) {
        account.setReactivateDate(new Date((new Long(reactivateDate)).longValue()));
      }
      //now take care of the list of locations
      Iterator locationIterator = ApiFunctions.safeList((List)_attributes.getAttribute(ACCOUNT_LOCATIONS)).iterator();
      while (locationIterator.hasNext()) {
        LocationData location = new LocationData();
        BtxHistoryAttributes locationAttributes = (BtxHistoryAttributes) locationIterator.next();
        BigrBeanUtilsBean.SINGLETON.copyProperties(location, locationAttributes.asMap());
        account.setLocation(location);
        //populate the list of existing storage units
        Iterator existingUnitIterator = ApiFunctions.safeList((List)locationAttributes.getAttribute(ACCOUNT_LOCATION_EXISTING_STORAGE_UNITS)).iterator();
        while (existingUnitIterator.hasNext()) {
          StorageUnitSummaryDto existingUnit = new StorageUnitSummaryDto();
          BigrBeanUtilsBean.SINGLETON.copyProperties(existingUnit, ((BtxHistoryAttributes)existingUnitIterator.next()).asMap());
          location.addExistingStorageUnit(existingUnit);
        }
        //populate the list of new storage units
        Iterator newUnitIterator = ApiFunctions.safeList((List)locationAttributes.getAttribute(ACCOUNT_LOCATION_NEW_STORAGE_UNITS)).iterator();
        while (newUnitIterator.hasNext()) {
          StorageUnitDto newUnit = new StorageUnitDto();
          BigrBeanUtilsBean.SINGLETON.copyProperties(newUnit, ((BtxHistoryAttributes)newUnitIterator.next()).asMap());
          location.addNewStorageUnit(newUnit);
        }
      }
    }
  }

  /* 
   * method to get details of the account
   */
  protected String getAccountDetailsAsHTML() {
    // NOTE: This method must not make use of any fields that aren't
    // set by the populateFromHistoryRecord method.
    SecurityInfo securityInfo = getLoggedInUserSecurityInfo();
    StringBuffer sb = new StringBuffer(300);
    AccountDto accountData = getAccountData();
    sb.append("The account had the following information specified:");
    sb.append("<ul>");
    if (!ApiFunctions.isEmpty(accountData.getId())) {
      sb.append("<li>");
      sb.append(" an id of ");
      sb.append(IcpUtils.prepareLink(accountData.getId(), securityInfo));
      sb.append("</li>");
    }
    if (!ApiFunctions.isEmpty(accountData.getType())) {
      sb.append("<li>");
      sb.append(" a type of ");
      Escaper.htmlEscape(accountData.getType(), sb);
      sb.append("</li>");
    }
    if (!ApiFunctions.isEmpty(accountData.getStatus())) {
      sb.append("<li>");
      sb.append(" a status of ");
      Escaper.htmlEscape(accountData.getStatus(), sb);
      sb.append("</li>");
    }
    if (!ApiFunctions.isEmpty(accountData.getName())) {
      sb.append("<li>");
      sb.append(" a name of ");
      Escaper.htmlEscape(accountData.getName(), sb);
      sb.append("</li>");
    }
    //no need to do parent name, as we've already shown the full name of the type and this is
    //the same thing
    if (accountData.getOpenDate() != null) {
      sb.append("<li>");
      sb.append(" an open date of ");
      Escaper.htmlEscape(ApiFunctions.sqlDateToString(accountData.getOpenDate()), sb);
      sb.append("</li>");
    }
    if (accountData.getActiveDate() != null) {
      sb.append("<li>");
      sb.append(" an activate date of ");
      Escaper.htmlEscape(ApiFunctions.sqlDateToString(accountData.getActiveDate()), sb);
      sb.append("</li>");
    }
    if (accountData.getCloseDate() != null) {
      sb.append("<li>");
      sb.append(" a close date of ");
      Escaper.htmlEscape(ApiFunctions.sqlDateToString(accountData.getCloseDate()), sb);
      sb.append("</li>");
    }
    if (accountData.getDeactivateDate() != null) {
      sb.append("<li>");
      sb.append(" a deactivate date of ");
      Escaper.htmlEscape(ApiFunctions.sqlDateToString(accountData.getDeactivateDate()), sb);
      sb.append("</li>");
    }
    if (!ApiFunctions.isEmpty(accountData.getDeactivateReason())) {
      sb.append("<li>");
      sb.append(" a deactivate reason of ");
      Escaper.htmlEscape(accountData.getDeactivateReason(), sb);
      sb.append("</li>");
    }
    if (accountData.getReactivateDate() != null) {
      sb.append("<li>");
      sb.append(" a reactivate date of ");
      Escaper.htmlEscape(ApiFunctions.sqlDateToString(accountData.getReactivateDate()), sb);
      sb.append("</li>");
    }
    //don't include primary location id or brand id.  Location information is
    //handled below, and brand id isn't something that is visible via the ui
    if (!ApiFunctions.isEmpty(accountData.getRequestManagerEmail())) {
      sb.append("<li>");
      sb.append(" a request manager email address of ");
      Escaper.htmlEscape(accountData.getRequestManagerEmail(), sb);
      sb.append("</li>");
    }
    if (!ApiFunctions.isEmpty(accountData.getViewLinkedCasesOnly())) {
      sb.append("<li>");
      if (FormLogic.DB_NO.equalsIgnoreCase(accountData.getViewLinkedCasesOnly())) {
        sb.append(" no restriction on cases that may be viewed");
      }
      else if (FormLogic.DB_YES.equalsIgnoreCase(accountData.getViewLinkedCasesOnly())) {
        sb.append(" a restriction to viewing linked cases only");
      }
      sb.append("</li>");
    }
    if (!ApiFunctions.isEmpty(accountData.getPasswordPolicy())) {
      sb.append("<li>");
      sb.append(" a password policy of ");
      Escaper.htmlEscape(accountData.getPasswordPolicy(), sb);
      sb.append("</li>");
    }
    if (!ApiFunctions.isEmpty(accountData.getPasswordLifeSpan())) {
      sb.append("<li>");
      sb.append(" a password life span of ");
      Escaper.htmlEscape(accountData.getPasswordLifeSpan(), sb);
      sb.append("</li>");
    }
    if (!ApiFunctions.isEmpty(accountData.getDonorRegistrationFormId())) {
      sb.append("<li>");
      String formName = accountData.getDonorRegistrationFormName();
      if (ApiFunctions.isEmpty(formName)) {
        formName = accountData.getDonorRegistrationFormId();
      }
      sb.append(" a donor registration form of ");
      sb.append(IcpUtils.prepareLink(accountData.getDonorRegistrationFormId(), formName, securityInfo));
      sb.append("</li>");
    }
    if (!ApiFunctions.isEmpty(accountData.getDefaultBoxLayoutId())) {
      sb.append("<li>");
      sb.append(" a default box layout id of ");
      sb.append(IcpUtils.prepareLink(accountData.getDefaultBoxLayoutId(), securityInfo));
      sb.append("</li>");
    }
    if (!ApiFunctions.isEmpty(accountData.getRequireUniqueSampleAliases())) {
      sb.append("<li>");
      if (FormLogic.DB_NO.equalsIgnoreCase(accountData.getRequireUniqueSampleAliases())) {
        sb.append(" no requirement that sample alias values be unique");
      }
      else if (FormLogic.DB_YES.equalsIgnoreCase(accountData.getRequireUniqueSampleAliases())) {
        sb.append(" a requirement that sample alias values be unique");
      }
      sb.append("</li>");
    }
    if (!ApiFunctions.isEmpty(accountData.getRequireSampleAliases())) {
      sb.append("<li>");
      if (FormLogic.DB_NO.equalsIgnoreCase(accountData.getRequireSampleAliases())) {
        sb.append(" no requirement that sample alias values be provided");
      }
      else if (FormLogic.DB_YES.equalsIgnoreCase(accountData.getRequireSampleAliases())) {
        sb.append(" a requirement that sample alias values be provided");
      }
      sb.append("</li>");
    }
    //contact information is included below, so don't include text about the contact
    //address id
    sb.append("</ul>");
    
    ContactDto contact = accountData.getContact();
    if (contact != null) {
      sb.append("The contact information for the account had the following information specified: ");
      sb.append("<ul>");
      AddressDto address = contact.getAddress();
      if (address != null) {
        //don't include text for address id, account id, or type
        if (!ApiFunctions.isEmpty(address.getFirstName())) {
          sb.append("<li>");
          sb.append(" a first name of ");
          Escaper.htmlEscape(address.getFirstName(), sb);
          sb.append("</li>");
        }
        if (!ApiFunctions.isEmpty(address.getMiddleName())) {
          sb.append("<li>");
          sb.append(" a middle name of ");
          Escaper.htmlEscape(address.getMiddleName(), sb);
          sb.append("</li>");
        }
        if (!ApiFunctions.isEmpty(address.getLastName())) {
          sb.append("<li>");
          sb.append(" a last name of ");
          Escaper.htmlEscape(address.getLastName(), sb);
          sb.append("</li>");
        }
        if (!ApiFunctions.isEmpty(address.getAddress1())) {
          sb.append("<li>");
          sb.append(" an address 1 of ");
          Escaper.htmlEscape(address.getAddress1(), sb);
          sb.append("</li>");
        }
        if (!ApiFunctions.isEmpty(address.getAddress2())) {
          sb.append("<li>");
          sb.append(" an address 2 of ");
          Escaper.htmlEscape(address.getAddress2(), sb);
          sb.append("</li>");
        }
        if (!ApiFunctions.isEmpty(address.getCity())) {
          sb.append("<li>");
          sb.append(" a city of ");
          Escaper.htmlEscape(address.getCity(), sb);
          sb.append("</li>");
        }
        if (!ApiFunctions.isEmpty(address.getState())) {
          sb.append("<li>");
          sb.append(" a state of ");
          Escaper.htmlEscape(address.getState(), sb);
          sb.append("</li>");
        }
        if (!ApiFunctions.isEmpty(address.getZipCode())) {
          sb.append("<li>");
          sb.append(" a postal code of ");
          Escaper.htmlEscape(address.getZipCode(), sb);
          sb.append("</li>");
        }
        if (!ApiFunctions.isEmpty(address.getCountry())) {
          sb.append("<li>");
          sb.append(" a country of ");
          Escaper.htmlEscape(address.getCountry(), sb);
          sb.append("</li>");
        }
      }
      if (!ApiFunctions.isEmpty(contact.getPhoneNumber())) {
        sb.append("<li>");
        sb.append(" a phone number of ");
        Escaper.htmlEscape(contact.getPhoneNumber(), sb);
        sb.append("</li>");
      }
      if (!ApiFunctions.isEmpty(contact.getExtension())) {
        sb.append("<li>");
        sb.append(" an extension of ");
        Escaper.htmlEscape(contact.getExtension(), sb);
        sb.append("</li>");
      }
      if (!ApiFunctions.isEmpty(contact.getFaxNumber())) {
        sb.append("<li>");
        sb.append(" a fax number of ");
        Escaper.htmlEscape(contact.getFaxNumber(), sb);
        sb.append("</li>");
      }
      if (!ApiFunctions.isEmpty(contact.getMobileNumber())) {
        sb.append("<li>");
        sb.append(" a mobile number of ");
        Escaper.htmlEscape(contact.getMobileNumber(), sb);
        sb.append("</li>");
      }
      if (!ApiFunctions.isEmpty(contact.getPagerNumber())) {
        sb.append("<li>");
        sb.append(" a pager number of ");
        Escaper.htmlEscape(contact.getPagerNumber(), sb);
        sb.append("</li>");
      }
      if (!ApiFunctions.isEmpty(contact.getEmail())) {
        sb.append("<li>");
        sb.append(" an email address of ");
        Escaper.htmlEscape(contact.getEmail(), sb);
        sb.append("</li>");
      }
      sb.append("</ul>");
    }
    
    //include text about assignable inventory groups and privs if it exists
    if (!ApiFunctions.isEmpty(accountData.getAccountData())) {
      AccountData parser = new AccountData();
      parser.setAccountData(accountData.getAccountData());
      List inventoryGroups = parser.getAssignableInventoryGroups();
      List privileges = parser.getAssignablePrivileges();
      if (!ApiFunctions.isEmpty(inventoryGroups)) {
        sb.append("<br>The account can self-manage the following inventory groups: ");
        boolean includeComma = false;
        Iterator iterator = inventoryGroups.iterator();
        while (iterator.hasNext()) {
          if (includeComma) {
            sb.append(", ");
          }
          includeComma = true;
          LogicalRepository lr = (LogicalRepository)iterator.next();
          sb.append(lr.getFullName());
        }
        sb.append(".");
      }
      if (!ApiFunctions.isEmpty(inventoryGroups)) {
        sb.append("<br>The account can self-manage the following privileges: ");
        boolean includeComma = false;
        Iterator iterator = privileges.iterator();
        while (iterator.hasNext()) {
          if (includeComma) {
            sb.append(", ");
          }
          includeComma = true;
          PrivilegeDto privilege = (PrivilegeDto)iterator.next();
          sb.append(privilege.getDescription());
        }
        sb.append(".");
      }
    }
    return sb.toString();
  }
  
  protected String getAccountLocationDetailsAsHTML() {
    // NOTE: This method must not make use of any fields that aren't
    // set by the populateFromHistoryRecord method.
    SecurityInfo securityInfo = getLoggedInUserSecurityInfo();
    StringBuffer sb = new StringBuffer(300);
    LocationData location = getAccountData().getLocation();
    if (location != null) {
      sb.append("This location had the following information specified: ");
      sb.append("<ul>");
      if (!ApiFunctions.isEmpty(location.getAddressId())) {
        sb.append("<li>");
        sb.append(" an id of ");
        Escaper.htmlEscape(location.getAddressId(), sb);
        sb.append("</li>");
      }
      if (!ApiFunctions.isEmpty(location.getName())) {
        sb.append("<li>");
        sb.append(" a name of ");
        Escaper.htmlEscape(location.getName(), sb);
        sb.append("</li>");
      }
      if (!ApiFunctions.isEmpty(location.getAddress1())) {
        sb.append("<li>");
        sb.append(" an address 1 of ");
        Escaper.htmlEscape(location.getAddress1(), sb);
        sb.append("</li>");
      }
      if (!ApiFunctions.isEmpty(location.getAddress2())) {
        sb.append("<li>");
        sb.append(" an address 2 of ");
        Escaper.htmlEscape(location.getAddress2(), sb);
        sb.append("</li>");
      }
      if (!ApiFunctions.isEmpty(location.getCity())) {
        sb.append("<li>");
        sb.append(" a city of ");
        Escaper.htmlEscape(location.getCity(), sb);
        sb.append("</li>");
      }
      if (!ApiFunctions.isEmpty(location.getState())) {
        sb.append("<li>");
        sb.append(" a state of ");
        Escaper.htmlEscape(location.getState(), sb);
        sb.append("</li>");
      }
      if (!ApiFunctions.isEmpty(location.getZipCode())) {
        sb.append("<li>");
        sb.append(" a postal code of ");
        Escaper.htmlEscape(location.getZipCode(), sb);
        sb.append("</li>");
      }
      if (!ApiFunctions.isEmpty(location.getCountry())) {
        sb.append("<li>");
        sb.append(" a country of ");
        Escaper.htmlEscape(location.getCountry(), sb);
        sb.append("</li>");
      }
      if (!ApiFunctions.isEmpty(location.getPhoneNumber())) {
        sb.append("<li>");
        sb.append(" a phone number of ");
        Escaper.htmlEscape(location.getPhoneNumber(), sb);
        sb.append("</li>");
      }
      sb.append("</ul>");
      AddressDto shipToAddress = location.getShipToAddress();
      if (shipToAddress != null) {
        sb.append("This location had the following shipping information specified: ");
        sb.append("<ul>");
        //don't include text for address id, account id, or type
        if (!ApiFunctions.isEmpty(shipToAddress.getFirstName())) {
          sb.append("<li>");
          sb.append(" a first name of ");
          Escaper.htmlEscape(shipToAddress.getFirstName(), sb);
          sb.append("</li>");
        }
        if (!ApiFunctions.isEmpty(shipToAddress.getMiddleName())) {
          sb.append("<li>");
          sb.append(" a middle name of ");
          Escaper.htmlEscape(shipToAddress.getMiddleName(), sb);
          sb.append("</li>");
        }
        if (!ApiFunctions.isEmpty(shipToAddress.getLastName())) {
          sb.append("<li>");
          sb.append(" a last name of ");
          Escaper.htmlEscape(shipToAddress.getLastName(), sb);
          sb.append("</li>");
        }
        if (!ApiFunctions.isEmpty(shipToAddress.getAddress1())) {
          sb.append("<li>");
          sb.append(" an address 1 of ");
          Escaper.htmlEscape(shipToAddress.getAddress1(), sb);
          sb.append("</li>");
        }
        if (!ApiFunctions.isEmpty(shipToAddress.getAddress2())) {
          sb.append("<li>");
          sb.append(" an address 2 of ");
          Escaper.htmlEscape(shipToAddress.getAddress2(), sb);
          sb.append("</li>");
        }
        if (!ApiFunctions.isEmpty(shipToAddress.getCity())) {
          sb.append("<li>");
          sb.append(" a city of ");
          Escaper.htmlEscape(shipToAddress.getCity(), sb);
          sb.append("</li>");
        }
        if (!ApiFunctions.isEmpty(shipToAddress.getState())) {
          sb.append("<li>");
          sb.append(" a state of ");
          Escaper.htmlEscape(shipToAddress.getState(), sb);
          sb.append("</li>");
        }
        if (!ApiFunctions.isEmpty(shipToAddress.getZipCode())) {
          sb.append("<li>");
          sb.append(" a postal code of ");
          Escaper.htmlEscape(shipToAddress.getZipCode(), sb);
          sb.append("</li>");
        }
        if (!ApiFunctions.isEmpty(shipToAddress.getCountry())) {
          sb.append("<li>");
          sb.append(" a country of ");
          Escaper.htmlEscape(shipToAddress.getCountry(), sb);
          sb.append("</li>");
        }
        sb.append("</ul>");
      }
      List existingStorageUnits = location.getExistingStorageUnits();
      if (!ApiFunctions.isEmpty(existingStorageUnits)) {
        sb.append("This location had the following existing storage units:");
        sb.append("<ul>");
        for (int i=0; i<existingStorageUnits.size(); i++) {
          boolean includeComma = false;
          StorageUnitSummaryDto existingStorageUnit = (StorageUnitSummaryDto) existingStorageUnits.get(i);
          sb.append("<li>");
          if (!ApiFunctions.isEmpty(existingStorageUnit.getStorageTypeCui())) {
            if (includeComma) {
              sb.append(", ");
            }
            includeComma = true;
            sb.append("Storage Type: ");
            Escaper.htmlEscape(existingStorageUnit.getStorageTypeCui(), sb);
          }
          if (!ApiFunctions.isEmpty(existingStorageUnit.getRoomId())) {
            if (includeComma) {
              sb.append(", ");
            }
            includeComma = true;
            sb.append("Room: ");
            Escaper.htmlEscape(existingStorageUnit.getRoomId(), sb);
          }
          if (!ApiFunctions.isEmpty(existingStorageUnit.getUnitName())) {
            if (includeComma) {
              sb.append(", ");
            }
            includeComma = true;
            sb.append("Storage Unit: ");
            Escaper.htmlEscape(existingStorageUnit.getUnitName(), sb);
          }
          if (!ApiFunctions.isEmpty(existingStorageUnit.getNumberOfDrawers())) {
            if (includeComma) {
              sb.append(", ");
            }
            includeComma = true;
            sb.append("Number of Drawers: ");
            Escaper.htmlEscape(existingStorageUnit.getNumberOfDrawers(), sb);
          }
          if (!ApiFunctions.isEmpty(existingStorageUnit.getMinimumSlotId()) &&
              !ApiFunctions.isEmpty(existingStorageUnit.getMaximumSlotId())) {
            if (includeComma) {
              sb.append(", ");
            }
            includeComma = true;
            sb.append("Slots per Drawer: ");
            Escaper.htmlEscape(existingStorageUnit.getMinimumSlotId(), sb);
            sb.append("-");
            Escaper.htmlEscape(existingStorageUnit.getMaximumSlotId(), sb);
          }
          sb.append("</li>");
        }
        sb.append("</ul>");
      }
      List newStorageUnits = location.getNewStorageUnits();
      if (!ApiFunctions.isEmpty(newStorageUnits)) {
        sb.append("The following storage units were added to this location:");
        sb.append("<ul>");
        for (int i=0; i<newStorageUnits.size(); i++) {
          boolean includeComma = false;
          StorageUnitDto newStorageUnit = (StorageUnitDto) newStorageUnits.get(i);
          sb.append("<li>");
          if (!ApiFunctions.isEmpty(newStorageUnit.getStorageTypeCui())) {
            if (includeComma) {
              sb.append(", ");
            }
            includeComma = true;
            sb.append("Storage Type: ");
            Escaper.htmlEscape(newStorageUnit.getStorageTypeCui(), sb);
          }
          if (!ApiFunctions.isEmpty(newStorageUnit.getRoomId())) {
            if (includeComma) {
              sb.append(", ");
            }
            includeComma = true;
            sb.append("Room: ");
            Escaper.htmlEscape(newStorageUnit.getRoomId(), sb);
          }
          if (!ApiFunctions.isEmpty(newStorageUnit.getUnitName())) {
            if (includeComma) {
              sb.append(", ");
            }
            includeComma = true;
            sb.append("Storage Unit: ");
            Escaper.htmlEscape(newStorageUnit.getUnitName(), sb);
          }
          if (!ApiFunctions.isEmpty(newStorageUnit.getNumberOfDrawers())) {
            if (includeComma) {
              sb.append(", ");
            }
            includeComma = true;
            sb.append("Number of Drawers: ");
            Escaper.htmlEscape(newStorageUnit.getNumberOfDrawers(), sb);
          }
          if (!ApiFunctions.isEmpty(newStorageUnit.getSlotsPerDrawer())) {
            if (includeComma) {
              sb.append(", ");
            }
            includeComma = true;
            sb.append("Slots per Drawer: ");
            Escaper.htmlEscape(newStorageUnit.getSlotsPerDrawer(), sb);
          }
          sb.append("</li>");
        }
        sb.append("</ul>");
      }
    }
    return sb.toString();
  }

  private Object describeAsHistoryObject() {
    BtxHistoryAttributes attributes = new BtxHistoryAttributes();
    AccountDto accountData = getAccountData();
    if (accountData != null) {
      if (!ApiFunctions.isEmpty(accountData.getId())) {
        attributes.setAttribute("accountData.id", accountData.getId());
      }
      if (!ApiFunctions.isEmpty(accountData.getType())) {
        attributes.setAttribute("accountData.type", (String)Constants.ACCOUNT_TYPE_MAP.get(accountData.getType()));
      }
      if (!ApiFunctions.isEmpty(accountData.getStatus())) {
        attributes.setAttribute("accountData.status", (String)Constants.ACCOUNT_STATUS_MAP.get(accountData.getStatus()));
      }
      if (!ApiFunctions.isEmpty(accountData.getName())) {
        attributes.setAttribute("accountData.name", accountData.getName());
      }
      if (!ApiFunctions.isEmpty(accountData.getParentName())) {
        attributes.setAttribute("accountData.parentName", accountData.getParentName());
      }
      if (accountData.getOpenDate() != null) {
        attributes.setAttribute(ACCOUNT_OPEN_DATE_STRING, accountData.getOpenDate().getTime() + "");
      }
      if (accountData.getActiveDate() != null) {
        attributes.setAttribute(ACCOUNT_ACTIVE_DATE_STRING, accountData.getActiveDate().getTime() + "");
      }
      if (accountData.getCloseDate() != null) {
        attributes.setAttribute(ACCOUNT_CLOSE_DATE_STRING, accountData.getCloseDate().getTime() + "");
      }
      if (accountData.getDeactivateDate() != null) {
        attributes.setAttribute(ACCOUNT_DEACTIVATE_DATE_STRING, accountData.getDeactivateDate().getTime() + "");
      }
      if (!ApiFunctions.isEmpty(accountData.getDeactivateReason())) {
        attributes.setAttribute("accountData.deactivateReason", accountData.getDeactivateReason());
      }
      if (accountData.getReactivateDate() != null) {
        attributes.setAttribute(ACCOUNT_REACTIVATE_DATE_STRING, accountData.getReactivateDate().getTime() + "");
      }
      if (!ApiFunctions.isEmpty(accountData.getPrimaryLocation())) {
        attributes.setAttribute("accountData.primaryLocation", accountData.getPrimaryLocation());
      }
      if (!ApiFunctions.isEmpty(accountData.getBrandId())) {
        attributes.setAttribute("accountData.brandId", accountData.getBrandId());
      }
      if (!ApiFunctions.isEmpty(accountData.getRequestManagerEmail())) {
        attributes.setAttribute("accountData.requestManagerEmail", accountData.getRequestManagerEmail());
      }
      if (!ApiFunctions.isEmpty(accountData.getViewLinkedCasesOnly())) {
        attributes.setAttribute("accountData.viewLinkedCasesOnly", accountData.getViewLinkedCasesOnly());
      }
      if (!ApiFunctions.isEmpty(accountData.getPasswordPolicy())) {
        attributes.setAttribute("accountData.passwordPolicy", GbossFactory.getInstance().getDescription(accountData.getPasswordPolicy()));
      }
      if (!ApiFunctions.isEmpty(accountData.getPasswordLifeSpan())) {
        attributes.setAttribute("accountData.passwordLifeSpan", accountData.getPasswordLifeSpan());
      }
      if (!ApiFunctions.isEmpty(accountData.getDonorRegistrationFormId())) {
        attributes.setAttribute("accountData.donorRegistrationFormId", accountData.getDonorRegistrationFormId());
      }
      if (!ApiFunctions.isEmpty(accountData.getDonorRegistrationFormName())) {
        attributes.setAttribute("accountData.donorRegistrationFormName", accountData.getDonorRegistrationFormName());
      }
      if (!ApiFunctions.isEmpty(accountData.getDefaultBoxLayoutId())) {
        attributes.setAttribute("accountData.defaultBoxLayoutId", accountData.getDefaultBoxLayoutId());
      }
      if (!ApiFunctions.isEmpty(accountData.getAccountData())) {
        attributes.setAttribute("accountData.accountData", accountData.getAccountData());
      }
      if (!ApiFunctions.isEmpty(accountData.getAccountDataEncoding())) {
        attributes.setAttribute("accountData.accountDataEncoding", accountData.getAccountDataEncoding());
      }
      if (!ApiFunctions.isEmpty(accountData.getContactAddressId())) {
        attributes.setAttribute("accountData.contactAddressId", accountData.getContactAddressId());
      }
      if (!ApiFunctions.isEmpty(accountData.getRequireUniqueSampleAliases())) {
        attributes.setAttribute("accountData.requireUniqueSampleAliases", accountData.getRequireUniqueSampleAliases());
      }
      if (!ApiFunctions.isEmpty(accountData.getRequireSampleAliases())) {
        attributes.setAttribute("accountData.requireSampleAliases", accountData.getRequireSampleAliases());
      }
      
      ContactDto contact = accountData.getContact();
      if (contact != null) {
        if (!ApiFunctions.isEmpty(contact.getPhoneNumber())) {
          attributes.setAttribute("accountData.contact.phoneNumber", contact.getPhoneNumber());
        }
        if (!ApiFunctions.isEmpty(contact.getExtension())) {
          attributes.setAttribute("accountData.contact.extension", contact.getExtension());
        }
        if (!ApiFunctions.isEmpty(contact.getFaxNumber())) {
          attributes.setAttribute("accountData.contact.faxNumber", contact.getFaxNumber());
        }
        if (!ApiFunctions.isEmpty(contact.getMobileNumber())) {
          attributes.setAttribute("accountData.contact.mobileNumber", contact.getMobileNumber());
        }
        if (!ApiFunctions.isEmpty(contact.getPagerNumber())) {
          attributes.setAttribute("accountData.contact.pagerNumber", contact.getPagerNumber());
        }
        if (!ApiFunctions.isEmpty(contact.getEmail())) {
          attributes.setAttribute("accountData.contact.email", contact.getEmail());
        }
        AddressDto address = contact.getAddress();
        if (address != null) {
          if (!ApiFunctions.isEmpty(address.getAddressId())) {
            attributes.setAttribute("accountData.contact.address.addressId", address.getAddressId());
          }
          if (!ApiFunctions.isEmpty(address.getAccountId())) {
            attributes.setAttribute("accountData.contact.address.accountId", address.getAccountId());
          }
          if (!ApiFunctions.isEmpty(address.getType())) {
            attributes.setAttribute("accountData.contact.address.type", address.getType());
          }
          if (!ApiFunctions.isEmpty(address.getAddress1())) {
            attributes.setAttribute("accountData.contact.address.address1", address.getAddress1());
          }
          if (!ApiFunctions.isEmpty(address.getAddress2())) {
            attributes.setAttribute("accountData.contact.address.address2", address.getAddress2());
          }
          if (!ApiFunctions.isEmpty(address.getCity())) {
            attributes.setAttribute("accountData.contact.address.city", address.getCity());
          }
          if (!ApiFunctions.isEmpty(address.getState())) {
            attributes.setAttribute("accountData.contact.address.state", address.getState());
          }
          if (!ApiFunctions.isEmpty(address.getZipCode())) {
            attributes.setAttribute("accountData.contact.address.zipCode", address.getZipCode());
          }
          if (!ApiFunctions.isEmpty(address.getCountry())) {
            attributes.setAttribute("accountData.contact.address.country", address.getCountry());
          }
          if (!ApiFunctions.isEmpty(address.getFirstName())) {
            attributes.setAttribute("accountData.contact.address.firstName", address.getFirstName());
          }
          if (!ApiFunctions.isEmpty(address.getLastName())) {
            attributes.setAttribute("accountData.contact.address.lastName", address.getLastName());
          }
          if (!ApiFunctions.isEmpty(address.getMiddleName())) {
            attributes.setAttribute("accountData.contact.address.middleName", address.getMiddleName());
          }
        }
      }
      
      //to accomodate location granularity, treat the (currently) single location as if it was a list
      List locations = new ArrayList();
      attributes.setAttribute(ACCOUNT_LOCATIONS, locations);
      LocationData location = accountData.getLocation();
      if (location != null) {
        BtxHistoryAttributes locationAttributes = new BtxHistoryAttributes();
        locations.add(locationAttributes);
        if (!ApiFunctions.isEmpty(location.getAddressId())) {
          locationAttributes.setAttribute("addressId", location.getAddressId());
        }
        if (!ApiFunctions.isEmpty(location.getName())) {
          locationAttributes.setAttribute("name", location.getName());
        }
        if (!ApiFunctions.isEmpty(location.getAddress1())) {
          locationAttributes.setAttribute("address1", location.getAddress1());
        }
        if (!ApiFunctions.isEmpty(location.getAddress2())) {
          locationAttributes.setAttribute("address2", location.getAddress2());
        }
        if (!ApiFunctions.isEmpty(location.getCity())) {
          locationAttributes.setAttribute("city", location.getCity());
        }
        if (!ApiFunctions.isEmpty(location.getState())) {
          locationAttributes.setAttribute("state", location.getState());
        }
        if (!ApiFunctions.isEmpty(location.getZipCode())) {
          locationAttributes.setAttribute("zipCode", location.getZipCode());
        }
        if (!ApiFunctions.isEmpty(location.getCountry())) {
          locationAttributes.setAttribute("country", location.getCountry());
        }
        if (!ApiFunctions.isEmpty(location.getPhoneNumber())) {
          locationAttributes.setAttribute("phoneNumber", location.getPhoneNumber());
        }
        AddressDto shipToAddress = location.getShipToAddress();
        if (shipToAddress != null) {
          if (!ApiFunctions.isEmpty(shipToAddress.getAddressId())) {
            locationAttributes.setAttribute("shipToAddress.addressId", shipToAddress.getAddressId());
          }
          if (!ApiFunctions.isEmpty(shipToAddress.getAccountId())) {
            locationAttributes.setAttribute("shipToAddress.accountId", shipToAddress.getAccountId());
          }
          if (!ApiFunctions.isEmpty(shipToAddress.getType())) {
            locationAttributes.setAttribute("shipToAddress.type", shipToAddress.getType());
          }
          if (!ApiFunctions.isEmpty(shipToAddress.getAddress1())) {
            locationAttributes.setAttribute("shipToAddress.address1", shipToAddress.getAddress1());
          }
          if (!ApiFunctions.isEmpty(shipToAddress.getAddress2())) {
            locationAttributes.setAttribute("shipToAddress.address2", shipToAddress.getAddress2());
          }
          if (!ApiFunctions.isEmpty(shipToAddress.getCity())) {
            locationAttributes.setAttribute("shipToAddress.city", shipToAddress.getCity());
          }
          if (!ApiFunctions.isEmpty(shipToAddress.getState())) {
            locationAttributes.setAttribute("shipToAddress.state", shipToAddress.getState());
          }
          if (!ApiFunctions.isEmpty(shipToAddress.getZipCode())) {
            locationAttributes.setAttribute("shipToAddress.zipCode", shipToAddress.getZipCode());
          }
          if (!ApiFunctions.isEmpty(shipToAddress.getCountry())) {
            locationAttributes.setAttribute("shipToAddress.country", shipToAddress.getCountry());
          }
          if (!ApiFunctions.isEmpty(shipToAddress.getFirstName())) {
            locationAttributes.setAttribute("shipToAddress.firstName", shipToAddress.getFirstName());
          }
          if (!ApiFunctions.isEmpty(shipToAddress.getLastName())) {
            locationAttributes.setAttribute("shipToAddress.lastName", shipToAddress.getLastName());
          }
          if (!ApiFunctions.isEmpty(shipToAddress.getMiddleName())) {
            locationAttributes.setAttribute("shipToAddress.middleName", shipToAddress.getMiddleName());
          }
        }
        List existingStorageUnitAttributes = new ArrayList();
        locationAttributes.setAttribute(ACCOUNT_LOCATION_EXISTING_STORAGE_UNITS, existingStorageUnitAttributes);
        List existingStorageUnits = location.getExistingStorageUnits();
        if (!ApiFunctions.isEmpty(existingStorageUnits)) {
          for (int i=0; i<existingStorageUnits.size(); i++) {
            StorageUnitSummaryDto existingStorageUnit = (StorageUnitSummaryDto) existingStorageUnits.get(i);
            BtxHistoryAttributes unitAttributes = new BtxHistoryAttributes();
            existingStorageUnitAttributes.add(unitAttributes);
            if (!ApiFunctions.isEmpty(existingStorageUnit.getStorageTypeCui())) {
              unitAttributes.setAttribute("storageTypeCui", GbossFactory.getInstance().getDescription(existingStorageUnit.getStorageTypeCui()));
            }
            if (!ApiFunctions.isEmpty(existingStorageUnit.getRoomId())) {
              unitAttributes.setAttribute("roomId", existingStorageUnit.getRoomId());
            }
            if (!ApiFunctions.isEmpty(existingStorageUnit.getUnitName())) {
              unitAttributes.setAttribute("unitName", existingStorageUnit.getUnitName());
            }
            if (!ApiFunctions.isEmpty(existingStorageUnit.getNumberOfDrawers())) {
              unitAttributes.setAttribute("numberOfDrawers", existingStorageUnit.getNumberOfDrawers());
            }
            if (!ApiFunctions.isEmpty(existingStorageUnit.getMinimumSlotId())) {
              unitAttributes.setAttribute("minimumSlotId", existingStorageUnit.getMinimumSlotId());
            }
            if (!ApiFunctions.isEmpty(existingStorageUnit.getMaximumSlotId())) {
              unitAttributes.setAttribute("maximumSlotId", existingStorageUnit.getMaximumSlotId());
            }
          }
        }
        List newStorageUnitAttributes = new ArrayList();
        locationAttributes.setAttribute(ACCOUNT_LOCATION_NEW_STORAGE_UNITS, newStorageUnitAttributes);
        List newStorageUnits = location.getNewStorageUnits();
        if (!ApiFunctions.isEmpty(newStorageUnits)) {
          for (int i=0; i<newStorageUnits.size(); i++) {
            StorageUnitDto newStorageUnit = (StorageUnitDto) newStorageUnits.get(i);
            BtxHistoryAttributes unitAttributes = new BtxHistoryAttributes();
            newStorageUnitAttributes.add(unitAttributes);
            if (!ApiFunctions.isEmpty(newStorageUnit.getStorageTypeCui())) {
              unitAttributes.setAttribute("storageTypeCui", GbossFactory.getInstance().getDescription(newStorageUnit.getStorageTypeCui()));
            }
            if (!ApiFunctions.isEmpty(newStorageUnit.getRoomId())) {
              unitAttributes.setAttribute("roomId", newStorageUnit.getRoomId());
            }
            if (!ApiFunctions.isEmpty(newStorageUnit.getUnitName())) {
              unitAttributes.setAttribute("unitName", newStorageUnit.getUnitName());
            }
            if (!ApiFunctions.isEmpty(newStorageUnit.getNumberOfDrawers())) {
              unitAttributes.setAttribute("numberOfDrawers", newStorageUnit.getNumberOfDrawers());
            }
            if (!ApiFunctions.isEmpty(newStorageUnit.getSlotsPerDrawer())) {
              int spd = (new Integer(newStorageUnit.getSlotsPerDrawer())).intValue();
              unitAttributes.setAttribute("slotsPerDrawer", com.ardais.bigr.orm.helpers.FormLogic.translateSlotIdToLetter(spd) + " (" + newStorageUnit.getSlotsPerDrawer() + ")");
            }
          }
        }
      }
    }
    return attributes;
  }

  /**
   * @return
   */
  public BtxHistoryAttributes getAttributes() {
    return _attributes;
  }

  /**
   * @return
   */
  public AccountDto getAccountData() {
    if (_accountData == null) {
      _accountData = new AccountDto();
    }
    return _accountData;
  }

  /**
   * @param attributes
   */
  public void setAttributes(BtxHistoryAttributes attributes) {
    _attributes = attributes;
  }

  /**
   * @param dto
   */
  public void setAccountData(AccountDto dto) {
    _accountData = dto;
  }

}
