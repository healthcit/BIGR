package com.ardais.bigr.orm.performers;

/**
*
*<!-- LICENSE_TEXT_START -->
*
*The NCICB Common Security Module (CSM) Software License, Version 3.0 Copyright
*2004-2005 Ekagra Software Technologies Limited ('Ekagra')
*
*Copyright Notice.  The software subject to this notice and license includes both
*human readable source code form and machine readable, binary, object code form
*(the 'CSM Software').  The CSM Software was developed in conjunction with the
*National Cancer Institute ('NCI') by NCI employees and employees of Ekagra.  To
*the extent government employees are authors, any rights in such works shall be
*subject to Title 17 of the United States Code, section 105.    
*
*This CSM Software License (the 'License') is between NCI and You.  'You (or
*'Your') shall mean a person or an entity, and all other entities that control,
*are controlled by, or are under common control with the entity.  'Control' for
*purposes of this definition means (i) the direct or indirect power to cause the
*direction or management of such entity, whether by contract or otherwise, or
*(ii) ownership of fifty percent (50%) or more of the outstanding shares, or
*(iii) beneficial ownership of such entity.  
*
*This License is granted provided that You agree to the conditions described
*below.  NCI grants You a non-exclusive, worldwide, perpetual, fully-paid-up,
*no-charge, irrevocable, transferable and royalty-free right and license in its
*rights in the CSM Software to (i) use, install, access, operate, execute, copy,
*modify, translate, market, publicly display, publicly perform, and prepare
*derivative works of the CSM Software; (ii) distribute and have distributed to
*and by third parties the CSM Software and any modifications and derivative works
*thereof; and (iii) sublicense the foregoing rights set out in (i) and (ii) to
*third parties, including the right to license such rights to further third
*parties.  For sake of clarity, and not by way of limitation, NCI shall have no
*right of accounting or right of payment from You or Your sublicensees for the
*rights granted under this License.  This License is granted at no charge to You.
*
*1. Your redistributions of the source code for the Software must retain the
*above copyright notice, this list of conditions and the disclaimer and
*limitation of liability of Article 6 below.  Your redistributions in object code
*form must reproduce the above copyright notice, this list of conditions and the
*disclaimer of Article 6 in the documentation and/or other materials provided
*with the distribution, if any.
*2. Your end-user documentation included with the redistribution, if any, must
*include the following acknowledgment: 'This product includes software developed
*by Ekagra and the National Cancer Institute.'  If You do not include such
*end-user documentation, You shall include this acknowledgment in the Software
*itself, wherever such third-party acknowledgments normally appear.
*
*3. You may not use the names 'The National Cancer Institute', 'NCI' 'Ekagra
*Software Technologies Limited' and 'Ekagra' to endorse or promote products
*derived from this Software.  This License does not authorize You to use any
*trademarks, service marks, trade names, logos or product names of either NCI or
*Ekagra, except as required to comply with the terms of this License.
*
*4. For sake of clarity, and not by way of limitation, You may incorporate this
*Software into Your proprietary programs and into any third party proprietary
*programs.  However, if You incorporate the Software into third party proprietary
*programs, You agree that You are solely responsible for obtaining any permission
*from such third parties required to incorporate the Software into such third
*party proprietary programs and for informing Your sublicensees, including
*without limitation Your end-users, of their obligation to secure any required
*permissions from such third parties before incorporating the Software into such
*third party proprietary software programs.  In the event that You fail to obtain
*such permissions, You agree to indemnify NCI for any claims against NCI by such
*third parties, except to the extent prohibited by law, resulting from Your
*failure to obtain such permissions.
*
*5. For sake of clarity, and not by way of limitation, You may add Your own
*copyright statement to Your modifications and to the derivative works, and You
*may provide additional or different license terms and conditions in Your
*sublicenses of modifications of the Software, or any derivative works of the
*Software as a whole, provided Your use, reproduction, and distribution of the
*Work otherwise complies with the conditions stated in this License.
*
*6. THIS SOFTWARE IS PROVIDED 'AS IS,' AND ANY EXPRESSED OR IMPLIED WARRANTIES,
*(INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY,
*NON-INFRINGEMENT AND FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED.  IN NO
*EVENT SHALL THE NATIONAL CANCER INSTITUTE, EKAGRA, OR THEIR AFFILIATES BE LIABLE
*FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
*DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
*SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
*CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR
*TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
*THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*
*<!-- LICENSE_TEXT_END -->
*
*/

import gov.nih.nci.security.AuthenticationManager;
import gov.nih.nci.security.SecurityServiceProvider;
import gov.nih.nci.security.exceptions.CSLoginException;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import javax.ejb.ObjectNotFoundException;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.api.ApiProperties;
import com.ardais.bigr.api.ApiResources;
import com.ardais.bigr.api.Escaper;
import com.ardais.bigr.api.TemporaryClob;
import com.ardais.bigr.beanutils.BigrBeanUtilsBean;
import com.ardais.bigr.btx.framework.Btx;
import com.ardais.bigr.btx.framework.BtxTransactionPerformerBase;
import com.ardais.bigr.concept.BigrConceptList;
import com.ardais.bigr.configuration.SystemConfiguration;
import com.ardais.bigr.es.beans.ArdaisaccountAccessBean;
import com.ardais.bigr.es.beans.ArdaisaccountKey;
import com.ardais.bigr.es.beans.ArdaisaddressAccessBean;
import com.ardais.bigr.es.beans.ArdaisaddressKey;
import com.ardais.bigr.es.beans.ArdaisuserAccessBean;
import com.ardais.bigr.es.beans.ArdaisuserKey;
import com.ardais.bigr.iltds.assistants.LegalValue;
import com.ardais.bigr.iltds.assistants.LegalValueSet;
import com.ardais.bigr.iltds.assistants.LogicalRepository;
import com.ardais.bigr.iltds.beans.ListGenerator;
import com.ardais.bigr.iltds.beans.ListGeneratorHome;
import com.ardais.bigr.iltds.btx.BTXDetails;
import com.ardais.bigr.iltds.btx.BtxActionError;
import com.ardais.bigr.iltds.btx.BtxActionMessage;
import com.ardais.bigr.iltds.databeans.PolicyData;
import com.ardais.bigr.iltds.databeans.PolicyExtendedData;
import com.ardais.bigr.iltds.databeans.SampleType;
import com.ardais.bigr.iltds.databeans.SampleTypeConfiguration;
import com.ardais.bigr.iltds.helpers.FormLogic;
import com.ardais.bigr.iltds.helpers.IltdsUtils;
import com.ardais.bigr.iltds.helpers.ProductType;
import com.ardais.bigr.javabeans.AccountBoxLayoutDto;
import com.ardais.bigr.javabeans.AccountDto;
import com.ardais.bigr.javabeans.AddressDto;
import com.ardais.bigr.javabeans.BoxLayoutDto;
import com.ardais.bigr.javabeans.ContactDto;
import com.ardais.bigr.javabeans.LocationData;
import com.ardais.bigr.javabeans.PrivilegeDto;
import com.ardais.bigr.javabeans.RoleDto;
import com.ardais.bigr.javabeans.SampleData;
import com.ardais.bigr.javabeans.ShippingPartnerDto;
import com.ardais.bigr.javabeans.StorageUnitDto;
import com.ardais.bigr.javabeans.StorageUnitSummaryDto;
import com.ardais.bigr.javabeans.UrlDto;
import com.ardais.bigr.javabeans.UserDto;
import com.ardais.bigr.kc.form.def.BigrFormDefinition;
import com.ardais.bigr.kc.form.def.TagTypes;
import com.ardais.bigr.kc.form.helpers.FormUtils;
import com.ardais.bigr.orm.beans.AccountSrchSB;
import com.ardais.bigr.orm.beans.AccountSrchSBHome;
import com.ardais.bigr.orm.beans.OrmUserManagement;
import com.ardais.bigr.orm.beans.OrmUserManagementHome;
import com.ardais.bigr.orm.btx.BTXDetailsDisableLogin;
import com.ardais.bigr.orm.btx.BTXDetailsLogin;
import com.ardais.bigr.orm.btx.BtxDetailsAccountManagement;
import com.ardais.bigr.orm.btx.BtxDetailsChangePassword;
import com.ardais.bigr.orm.btx.BtxDetailsChangeProfile;
import com.ardais.bigr.orm.btx.BtxDetailsChangeProfileStart;
import com.ardais.bigr.orm.btx.BtxDetailsCreateAccount;
import com.ardais.bigr.orm.btx.BtxDetailsCreateAccountLocation;
import com.ardais.bigr.orm.btx.BtxDetailsCreateAccountLocationStart;
import com.ardais.bigr.orm.btx.BtxDetailsCreateAccountStart;
import com.ardais.bigr.orm.btx.BtxDetailsCreateUser;
import com.ardais.bigr.orm.btx.BtxDetailsCreateUserStart;
import com.ardais.bigr.orm.btx.BtxDetailsFailedLogin;
import com.ardais.bigr.orm.btx.BtxDetailsFindAccounts;
import com.ardais.bigr.orm.btx.BtxDetailsFindUsers;
import com.ardais.bigr.orm.btx.BtxDetailsGetDisplayBanner;
import com.ardais.bigr.orm.btx.BtxDetailsMaintainAccountUrl;
import com.ardais.bigr.orm.btx.BtxDetailsMaintainAccountUrlStart;
import com.ardais.bigr.orm.btx.BtxDetailsMaintainBoxLayout;
import com.ardais.bigr.orm.btx.BtxDetailsMaintainBoxLayoutStart;
import com.ardais.bigr.orm.btx.BtxDetailsMaintainLogicalRepository;
import com.ardais.bigr.orm.btx.BtxDetailsMaintainLogicalRepositoryStart;
import com.ardais.bigr.orm.btx.BtxDetailsMaintainPolicy;
import com.ardais.bigr.orm.btx.BtxDetailsMaintainPolicyStart;
import com.ardais.bigr.orm.btx.BtxDetailsMaintainRole;
import com.ardais.bigr.orm.btx.BtxDetailsMaintainRoleStart;
import com.ardais.bigr.orm.btx.BtxDetailsManageAccountBoxLayout;
import com.ardais.bigr.orm.btx.BtxDetailsManageAccountBoxLayoutStart;
import com.ardais.bigr.orm.btx.BtxDetailsManageAccountLocations;
import com.ardais.bigr.orm.btx.BtxDetailsManageInventoryGroups;
import com.ardais.bigr.orm.btx.BtxDetailsManageInventoryGroupsStart;
import com.ardais.bigr.orm.btx.BtxDetailsManagePrivileges;
import com.ardais.bigr.orm.btx.BtxDetailsManagePrivilegesStart;
import com.ardais.bigr.orm.btx.BtxDetailsManageRoles;
import com.ardais.bigr.orm.btx.BtxDetailsManageRolesStart;
import com.ardais.bigr.orm.btx.BtxDetailsManageShippingPartners;
import com.ardais.bigr.orm.btx.BtxDetailsModifyAccount;
import com.ardais.bigr.orm.btx.BtxDetailsModifyAccountLocation;
import com.ardais.bigr.orm.btx.BtxDetailsModifyAccountLocationStart;
import com.ardais.bigr.orm.btx.BtxDetailsModifyAccountStart;
import com.ardais.bigr.orm.btx.BtxDetailsModifyUser;
import com.ardais.bigr.orm.btx.BtxDetailsModifyUserStart;
import com.ardais.bigr.orm.btx.BtxDetailsMoveByInventoryGroup;
import com.ardais.bigr.orm.btx.BtxDetailsMoveByInventoryGroupStart;
import com.ardais.bigr.orm.btx.BtxDetailsMoveSample;
import com.ardais.bigr.orm.btx.BtxDetailsUserManagement;
import com.ardais.bigr.orm.helpers.BigrGbossData;
import com.ardais.bigr.security.SecurityInfo;
import com.ardais.bigr.userprofile.UserProf;
import com.ardais.bigr.userprofile.UserProfHome;
import com.ardais.bigr.util.ArtsConstants;
import com.ardais.bigr.util.BigrCallableStatement;
import com.ardais.bigr.util.BoxLayoutUtils;
import com.ardais.bigr.util.Constants;
import com.ardais.bigr.util.DbUtils;
import com.ardais.bigr.util.EjbHomes;
import com.ardais.bigr.util.IcpUtils;
import com.ardais.bigr.util.LogicalRepositoryUtils;
import com.ardais.bigr.util.PolicyUtils;
import com.ardais.bigr.util.RoleUtils;
import com.ardais.bigr.util.UrlUtils;
import com.ardais.bigr.util.ValidatePasswords;
import com.ardais.bigr.util.gen.DbAliases;
import com.ardais.bigr.util.gen.DbConstants;
import com.gulfstreambio.gboss.GbossFactory;
import com.gulfstreambio.gboss.GbossValueSet;
import com.gulfstreambio.kc.form.def.FormDefinition;
import com.gulfstreambio.kc.form.def.FormDefinitionService;
import com.gulfstreambio.kc.form.def.FormDefinitionServiceResponse;
import com.gulfstreambio.kc.form.def.Tag;
import com.gulfstreambio.kc.form.def.data.DataFormDefinition;

/**
 * This performs ORM BTX business transactions that didn't belong in any of the more specific ORM
 * performer classes.
 */
public class BtxPerformerOrmOperations extends BtxTransactionPerformerBase {

  private static int MAX_POLICY_NAME_LENGTH = 50;
  
  private static int MAX_ROLE_NAME_LENGTH = 50;

  private static int MAX_BOX_LAYOUT_NAME_LENGTH = 100;

  //since the columns can be labeled A-Z and then AA-ZZ, the maximum value that we can allow here
  //is 26 + 26*26 = 702.  Making 50 for now since that's the maximum a customer has requested
  private static int MAX_NUMBER_OF_COLUMNS = 50;

  //since the rows can be labeled A-Z and then AA-ZZ, the maximum value that we can allow here
  //is 26 + 26*26 = 702.  Making 50 for now since that's the maximum a customer has requested
  private static int MAX_NUMBER_OF_ROWS = 50;

  private static int MIN_NUMBER_OF_COLUMNS = 1;

  private static int MIN_NUMBER_OF_ROWS = 1;

  // various user-based attribute maximum lengths
  private static final int MAXIMUM_TITLE_LENGTH = 15;

  private static final int MAXIMUM_FUNCTIONAL_TITLE_LENGTH = 44;

  private static final int MAXIMUM_AFFILIATION_LENGTH = 44;

  private static final int MAXIMUM_USER_NAME_LENGTH = 25;

  private static final int MAXIMUM_PHONE_NUMBER_LENGTH = 20;

  private static final int MAXIMUM_EXTENSION_LENGTH = 10;

  private static final int MAXIMUM_EMAIL_LENGTH = 100;

  private static final int MAXIMUM_ADDRESS_LENGTH = 60;

  private static final int MAXIMUM_CITY_LENGTH = 25;

  private static final int MAXIMUM_STATE_LENGTH = 25;

  private static final int MAXIMUM_ZIP_CODE_LENGTH = 15;

  private static final int MAXIMUM_COUNTRY_LENGTH = 35;

  private static final int MAXIMUM_PASSWORD_QUESTION_LENGTH = 100;

  private static final int MAXIMUM_PASSWORD_ANSWER_LENGTH = 100;

  private static final int MAXIMUM_DEPARTMENT_LENGTH = 200;

  // various account-based attribute maximum lengths
  private static final int MAXIMUM_ACCOUNT_ID_LENGTH = 10;

  private static final int MAXIMUM_ACCOUNT_NAME_LENGTH = 40;

  private static final int MAXIMUM_ACCOUNT_CONTACT_FIRST_NAME_LENGTH = 35;

  private static final int MAXIMUM_ACCOUNT_CONTACT_LAST_NAME_LENGTH = 30;

  private static final int MAXIMUM_ACCOUNT_CONTACT_PHONE_NUMBER_LENGTH = 20;

  private static final int MAXIMUM_ACCOUNT_CONTACT_EXTENSION_LENGTH = 10;

  private static final int MAXIMUM_ACCOUNT_CONTACT_FAX_NUMBER_LENGTH = 20;

  private static final int MAXIMUM_ACCOUNT_CONTACT_CELL_NUMBER_LENGTH = 20;

  private static final int MAXIMUM_ACCOUNT_CONTACT_PAGER_NUMBER_LENGTH = 20;

  private static final int MAXIMUM_ACCOUNT_CONTACT_EMAIL_LENGTH = 100;

  // various location-based attribute maximum lengths
  private static final int MAXIMUM_LOCATION_ID_LENGTH = 12;

  private static final int MAXIMUM_LOCATION_NAME_LENGTH = 50;

  private static final int MAXIMUM_LOCATION_ADDRESS_LENGTH = 60;

  private static final int MAXIMUM_LOCATION_CITY_LENGTH = 25;

  private static final int MAXIMUM_LOCATION_STATE_LENGTH = 25;

  private static final int MAXIMUM_LOCATION_ZIPCODE_LENGTH = 15;

  private static final int MAXIMUM_LOCATION_COUNTRY_LENGTH = 35;

  private static final int MAXIMUM_LOCATION_PHONE_NUMBER_LENGTH = 12;

  // various storage unit based attribute maximum lengths
  private static final int MAXIMUM_STORAGE_UNIT_ROOM_LENGTH = 22;

  private static final int MAXIMUM_STORAGE_UNIT_UNIT_LENGTH = 30;

  private static final int MAXIMUM_STORAGE_UNIT_NUMBER_OF_DRAWERS_LENGTH = 3;

  private static final int MAXIMUM_STORAGE_UNIT_NUMBER_OF_DRAWERS = 500;

  private static final int MINIMUM_STORAGE_UNIT_NUMBER_OF_DRAWERS = 1;

  private static final int MAXIMUM_STORAGE_UNIT_SLOTS_PER_DRAWER = 26;

  private static final int MINIMUM_STORAGE_UNIT_SLOTS_PER_DRAWER = 1;

  /**
   * Invoke the specified method on this class. This is only meant to be called from
   * BtxTransactionPerformerBase, please don't call it from anywhere else as a mechanism to gain
   * access to private methods in this class. Every object that the BTX framework dispatches to must
   * contain this method definition, and its implementation should be exactly the same in each
   * class. Please don't alter this method or its implementation in any way.
   */
  protected BTXDetails invokeBtxEntryPoint(java.lang.reflect.Method method, BTXDetails btxDetails)
      throws Exception {

    // **** DO NOT EDIT THIS METHOD, see the Javadoc comment above.
    return (BTXDetails) method.invoke(this, new Object[] { btxDetails });
  }

  private BTXDetails validatePerformDisplayBanner(BtxDetailsGetDisplayBanner btxDetails) {
    // No inputs to validate.
    return btxDetails;
  }

  /**
   * Perform a BTX transaction: get the display banner
   */
  private BtxDetailsGetDisplayBanner performDisplayBanner(BtxDetailsGetDisplayBanner btxDetails)
      throws Exception {
    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    String imageLogo = null;

    // need the account to make the database call...the decision has been
    // made to drive the branding on account...for now...
    SecurityInfo securityInfo = btxDetails.getLoggedInUserSecurityInfo();

    // set up and perform the database call....
    try {
      String query = " select brand_logo " + " from es_ardais_account acct, ard_branding br"
          + " where br.brand_id = acct.brand_id " + " and acct.ardais_acct_key =  ? ";

      con = ApiFunctions.getDbConnection();

      pstmt = con.prepareStatement(query);
      pstmt.setString(1, securityInfo.getAccount());
      rs = ApiFunctions.queryDb(pstmt, con);

      if (rs.next()) {
        // only branding the logo for now...
        imageLogo = rs.getString("brand_logo");
      }
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(con, pstmt, rs);

      String user = securityInfo.getUsername();
      String account = securityInfo.getAccount();

      ArdaisuserAccessBean ardaisUserAB = new ArdaisuserAccessBean(new ArdaisuserKey(user,
          new ArdaisaccountKey(account)));

      UserDto userDto = new UserDto();
      userDto.populate(ardaisUserAB);

      // return the user account info...
      btxDetails.setUserDto(userDto);

      // return the image logo...
      btxDetails.setImageLogo(imageLogo);

      btxDetails.setActionForwardTXCompleted("success");
    }
    return btxDetails;
  }

  /**
   * Do BTX transaction validation for the performMaintainLogicalRepositoryStart performer method.
   */
  private BTXDetails validatePerformMaintainLogicalRepositoryStart(
                                                                   BtxDetailsMaintainLogicalRepositoryStart btxDetails)
      throws Exception {

    // We don't do much validation here, since this is the action that we go to to report
    // validation errors. Because of that, we can expect that there will sometimes be
    // odd data in the fields we get here. We do need some things to be correct, though.
    // For example, the operation field needs to have a valid operation name in it, and for
    // update operations we need a valid repository id so that we can look up its data.

    boolean testProhibitRepositoryId = false;
    boolean testInvalidRepositoryId = false;

    String operation = btxDetails.getOperation();

    if (ApiFunctions.isEmpty(operation)) {
      btxDetails.addActionError(new BtxActionError("orm.error.logicalrepos.requiredOperation"));
    }
    else if (!(Constants.OPERATION_CREATE.equals(operation) || Constants.OPERATION_UPDATE
        .equals(operation))) {
      btxDetails
          .addActionError(new BtxActionError("orm.error.logicalrepos.operationCreateOrUpdate"));
    }

    // Decide which tests to do based on the operation.
    //
    if (Constants.OPERATION_CREATE.equals(operation)) {
      testProhibitRepositoryId = true;
    } // end operation == Create
    else if (Constants.OPERATION_UPDATE.equals(operation)) {
      testInvalidRepositoryId = true;
    } // end operation == Update

    // Now perform the tests...

    if (testProhibitRepositoryId && !ApiFunctions.isEmpty(btxDetails.getRepositoryId())) {
      btxDetails.addActionError(new BtxActionError("orm.error.logicalrepos.prohibitRepositoryId"));
    }

    if (testInvalidRepositoryId) {
      if (ApiFunctions.isEmpty(btxDetails.getRepositoryId())) {
        btxDetails
            .addActionError(new BtxActionError("orm.error.logicalrepos.requiredRepositoryId"));
      }
      else if (!IltdsUtils.isExistingLogicalRepository(btxDetails.getRepositoryId())) {
        btxDetails.addActionError(new BtxActionError("orm.error.logicalrepos.invalidRepositoryId"));
      }
    }

    return btxDetails;
  }

  /**
   * This is the main BTX entry point for performing the transaction that starts the process of
   * creating, editing, or deleting a logical repository.
   */
  private BTXDetails performMaintainLogicalRepositoryStart(
                                                           BtxDetailsMaintainLogicalRepositoryStart btxDetails)
      throws Exception {

    ListGeneratorHome home = (ListGeneratorHome) EjbHomes.getHome(ListGeneratorHome.class);
    ListGenerator listGen = home.create();

    btxDetails.setLogicalRepositories(listGen.getLogicalRepositories());

    String operation = btxDetails.getOperation();

    if (Constants.OPERATION_UPDATE.equals(operation)) {
      if (btxDetails.isPopulateRepositoryOutputFields()) {
        LogicalRepository repos = IltdsUtils.getLogicalRepositoryData(btxDetails.getRepositoryId());
        btxDetails.setRepositoryFullName(repos.getFullName());
        btxDetails.setRepositoryShortName(repos.getShortName());
        btxDetails.setBms("Y".equals(repos.getBmsYN()) ? Boolean.TRUE : Boolean.FALSE);
      }
    }

    btxDetails.setActionForwardTXCompleted("success");
    return btxDetails;
  }

  /**
   * Do BTX transaction validation for the performMaintainLogicalRepositorySave performer method.
   */
  private BTXDetails validatePerformMaintainLogicalRepositorySave(
                                                                  BtxDetailsMaintainLogicalRepository btxDetails)
      throws Exception {

    boolean testRequiredRepositoryFullName = false;
    boolean testRequiredRepositoryShortName = false;
    boolean testRequiredBmsYN = false;
    boolean testProhibitRepositoryId = false;
    boolean testInvalidRepositoryId = false;
    boolean testUnusedOnDelete = false;

    String operation = btxDetails.getOperation();

    if (ApiFunctions.isEmpty(operation)) {
      btxDetails.addActionError(new BtxActionError("orm.error.logicalrepos.requiredOperation"));
    }
    else if (!(Constants.OPERATION_CREATE.equals(operation)
        || Constants.OPERATION_UPDATE.equals(operation) || Constants.OPERATION_DELETE
        .equals(operation))) {
      btxDetails.addActionError(new BtxActionError(
          "orm.error.logicalrepos.operationCreateOrUpdateOrDelete"));
    }

    // Decide which tests to do based on the operation.
    //
    if (Constants.OPERATION_CREATE.equals(operation)) {
      testProhibitRepositoryId = true;
      testRequiredRepositoryFullName = true;
      testRequiredRepositoryShortName = true;
      testRequiredBmsYN = true;
    } // end operation == Create
    else if (Constants.OPERATION_UPDATE.equals(operation)) {
      testInvalidRepositoryId = true;
      testRequiredRepositoryFullName = true;
      testRequiredRepositoryShortName = true;
      testRequiredBmsYN = true;
    } // end operation == Update
    else if (Constants.OPERATION_DELETE.equals(operation)) {
      testInvalidRepositoryId = true;
      testUnusedOnDelete = true;
    } // end operation == Delete

    // Now perform the tests...

    boolean idOk = true;
    String reposId = btxDetails.getRepositoryId();

    if (testProhibitRepositoryId && !ApiFunctions.isEmpty(reposId)) {
      idOk = false;
      btxDetails.addActionError(new BtxActionError("orm.error.logicalrepos.prohibitRepositoryId"));
    }

    if (idOk && (testInvalidRepositoryId || testUnusedOnDelete)) {
      if (ApiFunctions.isEmpty(reposId)) {
        idOk = false;
        btxDetails
            .addActionError(new BtxActionError("orm.error.logicalrepos.requiredRepositoryId"));
      }
      else if (!IltdsUtils.isExistingLogicalRepository(reposId)) {
        idOk = false;
        btxDetails.addActionError(new BtxActionError("orm.error.logicalrepos.invalidRepositoryId"));
      }

      if (idOk && testUnusedOnDelete) {
        if (!isEmptyLogicalRepository(reposId)) {
          btxDetails.addActionError(new BtxActionError("orm.error.logicalrepos.nonEmptyOnDelete"));
        }
        List relatedPolicies = PolicyUtils.getPoliciesRelatedToLogicalRepository(reposId);
        if (!ApiFunctions.isEmpty(relatedPolicies)) {
          btxDetails.addActionError(new BtxActionError(
              "orm.error.logicalrepos.usedByPolicyOnDelete", Escaper.htmlEscape(IltdsUtils
                  .getPolicyNames(relatedPolicies))));
        }
      }
    }

    if (testRequiredRepositoryFullName) {
      String name = btxDetails.getRepositoryFullName();
      if (ApiFunctions.isEmpty(name)) {
        btxDetails.addActionError(new BtxActionError(
            "orm.error.logicalrepos.requiredRepositoryFullName"));
      }
      else if (idOk && isExistingLogicalRepositoryWithName(name, true, reposId)) {
        btxDetails.addActionError(new BtxActionError("orm.error.logicalrepos.duplicateFullName"));
      }
    }

    if (testRequiredRepositoryShortName) {
      String name = btxDetails.getRepositoryShortName();
      if (ApiFunctions.isEmpty(name)) {
        btxDetails.addActionError(new BtxActionError(
            "orm.error.logicalrepos.requiredRepositoryShortName"));
      }
      else if (idOk && isExistingLogicalRepositoryWithName(name, false, reposId)) {
        btxDetails.addActionError(new BtxActionError("orm.error.logicalrepos.duplicateShortName"));
      }
    }

    if (testRequiredBmsYN && null == btxDetails.getBms()) {
      btxDetails
          .addActionError(new BtxActionError("orm.error.logicalrepos.requiredRepositoryBmsYN"));
    }

    return btxDetails;
  }

  /**
   * This is the main BTX entry point for performing the transaction that creates, edits, or deletes
   * a logical repository.
   */
  private BTXDetails performMaintainLogicalRepositorySave(
                                                          BtxDetailsMaintainLogicalRepository btxDetails)
      throws Exception {

    String operation = btxDetails.getOperation();

    // The action we take here depends on the operation.
    //
    if (Constants.OPERATION_CREATE.equals(operation)) {
      LogicalRepository repos = new LogicalRepository();
      repos.setFullName(btxDetails.getRepositoryFullName());
      repos.setShortName(btxDetails.getRepositoryShortName());
      repos.setBmsYN(btxDetails.getBms().booleanValue() ? "Y" : "N");

      // Create the repository, setting the id field on the repos parameter to the
      // id assigned to the new repository.
      //
      logicalRepositoryCreate(repos);

      btxDetails.setRepositoryId(repos.getId());

      btxDetails.addActionMessage(new BtxActionMessage(
          "orm.error.logicalrepos.confirmCreateLogicalRepos", Escaper.htmlEscape(btxDetails
              .getRepositoryFullName())));
    } // end operation == Create
    else if (Constants.OPERATION_UPDATE.equals(operation)) {
      LogicalRepository repos = new LogicalRepository();
      repos.setId(btxDetails.getRepositoryId());
      repos.setFullName(btxDetails.getRepositoryFullName());
      repos.setShortName(btxDetails.getRepositoryShortName());
      repos.setBmsYN(btxDetails.getBms().booleanValue() ? "Y" : "N");

      logicalRepositoryUpdate(repos);

      btxDetails.addActionMessage(new BtxActionMessage(
          "orm.error.logicalrepos.confirmUpdateLogicalRepos", Escaper.htmlEscape(btxDetails
              .getRepositoryFullName())));
    } // end operation == Update
    else if (Constants.OPERATION_DELETE.equals(operation)) {
      // Look up the repository we're going to delete so that we can set output parameters.
      LogicalRepository repos = IltdsUtils.getLogicalRepositoryData(btxDetails.getRepositoryId());
      btxDetails.setRepositoryFullName(repos.getFullName());
      btxDetails.setRepositoryShortName(repos.getShortName());
      btxDetails.setBms("Y".equals(repos.getBmsYN()) ? Boolean.TRUE : Boolean.FALSE);

      // Now delete the repository.
      logicalRepositoryDelete(btxDetails.getRepositoryId());

      btxDetails.addActionMessage(new BtxActionMessage(
          "orm.error.logicalrepos.confirmDeleteLogicalRepos", Escaper.htmlEscape(btxDetails
              .getRepositoryFullName())));
    } // end operation == Delete

    btxDetails.setActionForwardTXCompleted("success");
    return btxDetails;
  }

  /**
   * Return true if there is a logical repository with the specified name. If the testFullName
   * parameter is true, it will match against the repository full names, otherwise it will match
   * against short names. The matching is case-insensitive. If the repositoryId parameter is
   * non-empty, then it won't consider that repository id when matching.
   * 
   * @param name The name to search for.
   * @param testFullName True to test against full names, False to test against short names.
   * @param repositoryId if non-empty, this repository id will be excluded from the match (this will
   *          return false if the only repository that would have matched is the specified one).
   * @return true if a matching logical repository exists.
   */
  private boolean isExistingLogicalRepositoryWithName(String name, boolean testFullName,
                                                      String repositoryId) {

    boolean testRepositoryId = (!ApiFunctions.isEmpty(repositoryId));

    StringBuffer sb = new StringBuffer(128);
    sb.append("select 1 from ard_logical_repos where lower(");
    sb.append(testFullName ? "full_name" : "short_name");
    sb.append(") = ?");
    if (testRepositoryId) {
      sb.append(" and id <> ?");
    }
    String queryString = sb.toString();

    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
      con = ApiFunctions.getDbConnection();
      pstmt = con.prepareStatement(queryString);
      pstmt.setString(1, ApiFunctions.safeString(name).toLowerCase());
      if (testRepositoryId) {
        pstmt.setString(2, repositoryId);
      }
      rs = ApiFunctions.queryDb(pstmt, con);

      return rs.next();
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
      return false; // unreached, keep compiler happy
    }
    finally {
      ApiFunctions.close(con, pstmt, rs);
    }
  }

  /**
   * Return true if the specified logical repository contains no items. This method assumes that the
   * repositoryId parameter is the valid id of an existing repository.
   * 
   * @param repositoryId The logical repository id.
   * @return true if the logical repository is empty.
   */
  private boolean isEmptyLogicalRepository(String repositoryId) {
    String queryString = "select 1 from ard_logical_repos_item where repository_id = ?";

    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
      con = ApiFunctions.getDbConnection();
      pstmt = con.prepareStatement(queryString);
      pstmt.setBigDecimal(1, new BigDecimal(repositoryId));
      rs = ApiFunctions.queryDb(pstmt, con);

      return !rs.next();
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
      return false; // unreached, keep compiler happy
    }
    finally {
      ApiFunctions.close(con, pstmt, rs);
    }
  }

  /**
   * Create a new logical repository using the fields specified in the <code>repos</code>
   * parameter. This assumes that the id field is null and that all other fields are valid, but it
   * doesn't check any of this. As a side effect, it sets the id field on the <code>repos</code>
   * parameter to the id assigned to the new repository.
   * 
   * @param repos The description of the repository to create.
   */
  private void logicalRepositoryCreate(LogicalRepository repos) {
    String queryString = "{ call insert into ard_logical_repos (id, full_name, short_name, bms_yn) "
        + "values (ard_logical_repos_seq.nextval, ?, ?, ?) returning id into ? }";

    Connection con = null;
    CallableStatement cstmt = null;
    try {
      con = ApiFunctions.getDbConnection();
      cstmt = con.prepareCall(queryString);

      cstmt.setString(1, repos.getFullName());
      cstmt.setString(2, repos.getShortName());
      cstmt.setString(3, repos.getBmsYN());
      cstmt.registerOutParameter(4, Types.VARCHAR);

      cstmt.execute();

      repos.setId(cstmt.getString(4));
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(cstmt);
      ApiFunctions.close(con);
    }
  }

  /**
   * Update an existing logical repository to match the fields specified in the <code>repos</code>
   * parameter. This assumes that the id field is the id of an existing repository and that all
   * other fields are valid, but it doesn't check any of this.
   * 
   * @param repos The description of the repository to update.
   */
  private void logicalRepositoryUpdate(LogicalRepository repos) {
    String queryString = "update ard_logical_repos set full_name = ?, short_name = ?, bms_yn = ? where id = ?";

    Connection con = null;
    PreparedStatement pstmt = null;
    try {
      con = ApiFunctions.getDbConnection();
      pstmt = con.prepareStatement(queryString);

      pstmt.setString(1, repos.getFullName());
      pstmt.setString(2, repos.getShortName());
      pstmt.setString(3, repos.getBmsYN());
      pstmt.setBigDecimal(4, new BigDecimal(repos.getId()));

      pstmt.executeUpdate();
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(pstmt);
      ApiFunctions.close(con);
    }
  }

  /**
   * Delete an existing logical repository. This assumes that repositoryId parameter is the id of an
   * existing repository and that the repository doesn't contain any items and isn't used by any
   * policies, but it doesn't check any of this. Any rows in ARD_LOGICAL_REPOS_USER that refer to
   * the repository are deleted along with the repository itself.
   * 
   * @param repositoryId The id of the logical repository to delete.
   */
  private void logicalRepositoryDelete(String repositoryId) {
    String queryString1 = "delete from ard_logical_repos_user where repository_id = ?";
    String queryString2 = "delete from ard_logical_repos where id = ?";

    Connection con = null;
    PreparedStatement pstmt = null;
    try {
      con = ApiFunctions.getDbConnection();

      pstmt = con.prepareStatement(queryString1);
      pstmt.setBigDecimal(1, new BigDecimal(repositoryId));
      pstmt.executeUpdate();

      pstmt.close();

      pstmt = con.prepareStatement(queryString2);
      pstmt.setBigDecimal(1, new BigDecimal(repositoryId));
      pstmt.executeUpdate();
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(pstmt);
      ApiFunctions.close(con);
    }
  }

  /*
   * Method to validate an id. Makes sure the id contains only alphanumeric characters and
   * underscores
   */
  private boolean isValidId(String id) {
    boolean returnValue = true;

    if (ApiFunctions.isEmpty(id)) {
      returnValue = false;
    }
    else {
      char[] chars = id.toCharArray();
      for (int i = 0; i < chars.length; i++) {
        char c = chars[i];
        if (!Character.isLetterOrDigit(c) && (c != '_')) {
          returnValue = false;
        }
      }
    }

    return returnValue;
  }

  /**
   * Method to validate that an account is valid for manage privilege and manage inventory group
   * transactions
   * 
   * @param account
   * @param restrictionType
   * @param btxDetails
   */
  private void validateAccount(AccountDto account, String restrictionType, BTXDetails btxDetails) {
    String accountId = ApiFunctions.safeTrim(account.getId());
    if (ApiFunctions.isEmpty(accountId)) {
      btxDetails.addActionError(new BtxActionError("error.noValue", "accountId"));
    }
    else {
      try {
        //validate that a valid account id was provided
        ArdaisaccountAccessBean accountBean = new ArdaisaccountAccessBean(new ArdaisaccountKey(
            accountId));
        //system owner accounts should not have any restrictions placed on the inventory groups or
        //privileges they can manage, so if the account specified is a system owner account return
        //an error
        String accountType = accountBean.getArdais_acct_type();
        if (SecurityInfo.ROLE_SYSTEM_OWNER.equalsIgnoreCase(accountType)) {
          btxDetails.addActionError(new BtxActionError("orm.error.account.noRestrictionsAllowed",
              restrictionType, accountId));
        }
      }
      catch (Exception e) {
        btxDetails
            .addActionError(new BtxActionError("orm.error.account.accountNotFound", accountId));
      }
    }
  }

  /**
   * Method to validate that a user is valid for manage privilege, manage inventory group, and
   * manage role transactions
   * 
   * @param user
   * @param btxDetails
   */
  private void validateUser(UserDto user, BTXDetails btxDetails) {
    //Validate that a valid user has been specified
    String userId = ApiFunctions.safeTrim(user.getUserId());
    String accountId = ApiFunctions.safeTrim(user.getAccountId());
    if (ApiFunctions.isEmpty(userId)) {
      btxDetails.addActionError(new BtxActionError("error.noValue", "userId"));
    }
    if (ApiFunctions.isEmpty(accountId)) {
      btxDetails.addActionError(new BtxActionError("error.noValue", "accountId"));
    }
    //Validate that the user has not specified an account inaccessible to them
    if (!ApiFunctions.isEmpty(accountId)
        && !determineAccountChoicesForUser(btxDetails).contains(accountId)) {
      btxDetails.addActionError(new BtxActionError("orm.error.users.invalidAccountId", accountId));
    }
    if (!ApiFunctions.isEmpty(userId) && !ApiFunctions.isEmpty(accountId)) {
      try {
        ArdaisuserAccessBean ardaisUserEB = new ArdaisuserAccessBean(new ArdaisuserKey(userId,
            new ArdaisaccountKey(accountId)));
      }
      catch (Exception e) {
        btxDetails.addActionError(new BtxActionError("orm.error.users.userNotFound", userId,
            accountId));
      }
    }
  }

  /**
   * Do BTX transaction validation for the performManageInventoryGroupsStart performer method.
   */
  private BTXDetails validatePerformManageInventoryGroupsStart(
                                                               BtxDetailsManageInventoryGroups btxDetails) {
    if (Constants.OBJECT_TYPE_ACCOUNT.equalsIgnoreCase(btxDetails.getObjectType())) {
      //make sure the user has the Account Management privilege
      if (!btxDetails.getLoggedInUserSecurityInfo().isHasPrivilege(
          SecurityInfo.PRIV_ORM_ACCOUNT_MANAGEMENT)) {
        btxDetails.addActionError(new BtxActionError("error.noPrivilege", "manage accounts"));
      }
      else {
        validateAccount(btxDetails.getAccountData(), "inventory groups", btxDetails);
        //make sure the user is a System Owner
        if (!btxDetails.getLoggedInUserSecurityInfo().isInRoleSystemOwner()) {
          btxDetails.addActionError(new BtxActionError("error.noPrivilege",
              "manage the assignable inventory groups for this account"));
        }
      }
    }
    else if (Constants.OBJECT_TYPE_USER.equalsIgnoreCase(btxDetails.getObjectType())) {
      validateUser(btxDetails.getUserData(), btxDetails);
      //if the user is valid, see if they have the "View all Logical Repositories" priv. If so,
      //they cannot have restrictions placed upon the inventory groups they are allowed to view
      if (btxDetails.getActionErrors().empty()) {
        try {
          String userId = ApiFunctions.safeTrim(btxDetails.getUserData().getUserId());
          String accountId = ApiFunctions.safeTrim(btxDetails.getUserData().getAccountId());
          OrmUserManagementHome oumHome = (OrmUserManagementHome) EjbHomes.getHome(OrmUserManagementHome.class);
          OrmUserManagement user = oumHome.create();
          List privs = user.getPrivilegesAssignedToUser(userId, accountId);
          Iterator iterator = privs.iterator();
          boolean hasPriv = false;
          while (!hasPriv && iterator.hasNext()) {
            PrivilegeDto priv = (PrivilegeDto) iterator.next();
            if (SecurityInfo.PRIV_VIEW_ALL_LOGICAL_REPOS.equalsIgnoreCase(priv.getId())) {
              hasPriv = true;
            }
          }
          if (hasPriv) {
            btxDetails.addActionError(new BtxActionError("orm.error.users.noRestrictionsAllowed",
                "inventory groups", userId));
          }
        }
        catch (Exception e) {
          ApiFunctions.throwAsRuntimeException(e);
        }
      }
    }
    else {
      //unexpected object type
      btxDetails.addActionError(new BtxActionError("error.badvalue", btxDetails.getObjectType(),
          "objectType", "recognized"));
    }
    return btxDetails;
  }

  /**
   * Perform a BTX transaction: get the information necessary to start the transaction for managing
   * the inventory groups for a user or account
   */
  private BtxDetailsManageInventoryGroupsStart performManageInventoryGroupsStart(
                                                                                 BtxDetailsManageInventoryGroupsStart btxDetails)
      throws Exception {
    try {
      if (Constants.OBJECT_TYPE_ACCOUNT.equalsIgnoreCase(btxDetails.getObjectType())) {
        String accountId = btxDetails.getAccountData().getId();
        AccountSrchSBHome home = (AccountSrchSBHome) EjbHomes.getHome(AccountSrchSBHome.class);
        AccountSrchSB accountInventoryGroups = home.create();
        btxDetails.setAssignedInventoryGroups(accountInventoryGroups
            .getInventoryGroupsAssignedToAccount(accountId));
        btxDetails.setAllInventoryGroups(LogicalRepositoryUtils.getAllLogicalRepositories());
      }
      else if (Constants.OBJECT_TYPE_USER.equalsIgnoreCase(btxDetails.getObjectType())) {
        String userId = btxDetails.getUserData().getUserId();
        String accountId = btxDetails.getUserData().getAccountId();
        List assignedInventoryGroups = LogicalRepositoryUtils
            .getLogicalRepositoriesForUserByFullName(userId);
        List allInventoryGroups = LogicalRepositoryUtils.getAllLogicalRepositories();
        //If the user is not in a system owner role, alter the assigned inventory groups and all
        //inventory groups to contain only those inventory groups authorized for the user's
        // account.
        //This is done so the user only sees inventory groups they are authorized to view.
        SecurityInfo securityInfo = btxDetails.getLoggedInUserSecurityInfo();
        if (!securityInfo.isInRoleSystemOwner()) {
          String account = securityInfo.getAccount();
          AccountSrchSBHome home = (AccountSrchSBHome) EjbHomes.getHome(AccountSrchSBHome.class);
          AccountSrchSB accountInventoryGroups = home.create();
          List authorizedInventoryGroups = accountInventoryGroups
              .getInventoryGroupsAssignedToAccount(account);
          removeUnauthorizedEntities(authorizedInventoryGroups, assignedInventoryGroups);
          removeUnauthorizedEntities(authorizedInventoryGroups, allInventoryGroups);
        }
        btxDetails.setAssignedInventoryGroups(assignedInventoryGroups);
        btxDetails.setAllInventoryGroups(allInventoryGroups);
      }
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    btxDetails.setActionForwardTXCompleted("success");
    return btxDetails;
  }

  /**
   * Do BTX transaction validation for the performManageInventoryGroups performer method.
   */
  private BTXDetails validatePerformManageInventoryGroups(BtxDetailsManageInventoryGroups btxDetails) {
    validatePerformManageInventoryGroupsStart(btxDetails);
    SecurityInfo securityInfo = btxDetails.getLoggedInUserSecurityInfo();
    //make sure the user has not assigned an inventory group that is outside the scope of their
    // control
    if (Constants.OBJECT_TYPE_USER.equalsIgnoreCase(btxDetails.getObjectType())) {
      if (!securityInfo.isInRoleSystemOwner()) {
        String accountId = securityInfo.getAccount();
        try {
          AccountSrchSBHome home = (AccountSrchSBHome) EjbHomes.getHome(AccountSrchSBHome.class);
          AccountSrchSB accountInventoryGroups = home.create();
          List authorizedInventoryGroups = accountInventoryGroups
              .getInventoryGroupsAssignedToAccount(accountId);
          String[] selectedInventoryGroups = btxDetails.getSelectedInventoryGroups();
          List unauthorizedInventoryGroupIds = new ArrayList();
          for (int i = 0; i < selectedInventoryGroups.length; i++) {
            String inventoryGroupId = selectedInventoryGroups[i];
            Iterator iterator = authorizedInventoryGroups.iterator();
            boolean found = false;
            LogicalRepository authorizedInventoryGroup = null;
            while (!found && iterator.hasNext()) {
              authorizedInventoryGroup = (LogicalRepository) iterator.next();
              found = inventoryGroupId.equalsIgnoreCase(authorizedInventoryGroup.getId());
            }
            if (!found) {
              unauthorizedInventoryGroupIds.add(inventoryGroupId);
            }
          }
          if (!ApiFunctions.isEmpty(unauthorizedInventoryGroupIds)) {
            List unauthorizedInventoryGroups = LogicalRepositoryUtils
                .getLogicalRepositoriesById(unauthorizedInventoryGroupIds);
            Iterator iterator = unauthorizedInventoryGroups.iterator();
            boolean addComma = false;
            StringBuffer inventoryGroupNames = new StringBuffer(100);
            while (iterator.hasNext()) {
              if (addComma) {
                inventoryGroupNames.append(", ");
              }
              inventoryGroupNames.append(((LogicalRepository) iterator.next()).getFullName());
            }
            btxDetails.addActionError(new BtxActionError(
                "orm.error.users.unauthorizedPrivilegesOrGroups", "inventory groups",
                inventoryGroupNames.toString()));
          }
        }
        catch (Exception e) {
          ApiFunctions.throwAsRuntimeException(e);
        }
      }
    }
    //make sure every selected inventory group is known
    try {
      List knownInventoryGroups = LogicalRepositoryUtils.getAllLogicalRepositories();
      String[] selectedInventoryGroups = btxDetails.getSelectedInventoryGroups();
      List unknownInventoryGroupIds = new ArrayList();
      for (int i = 0; i < selectedInventoryGroups.length; i++) {
        String inventoryGroupId = selectedInventoryGroups[i];
        Iterator iterator = knownInventoryGroups.iterator();
        boolean found = false;
        LogicalRepository inventoryGroup = null;
        while (!found && iterator.hasNext()) {
          inventoryGroup = (LogicalRepository) iterator.next();
          found = inventoryGroupId.equalsIgnoreCase(inventoryGroup.getId());
        }
        if (!found) {
          unknownInventoryGroupIds.add(inventoryGroupId);
        }
      }
      if (!ApiFunctions.isEmpty(unknownInventoryGroupIds)) {
        btxDetails.addActionError(new BtxActionError("orm.error.users.unknownPrivilegesOrGroups",
            "inventory groups", ApiFunctions.toCommaSeparatedList(ApiFunctions
                .toStringArray(unknownInventoryGroupIds))));
      }
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    //if any errors are going to be returned, set up the inventory group info the UI will need
    if (!btxDetails.getActionErrors().empty()) {
      try {
        //set the assigned inventory groups to be the selected inventory groups, since those are
        // the
        //ones that should show up in the right hand side (so that the user is returned to the page
        //in the same state they left it)
        btxDetails.setAssignedInventoryGroups(LogicalRepositoryUtils
            .getLogicalRepositoriesById(ApiFunctions.safeToList(btxDetails
                .getSelectedInventoryGroups())));
        List allInventoryGroups = LogicalRepositoryUtils.getAllLogicalRepositories();
        //If a user is being updated and the user is not in a system owner role, alter the all
        //inventory group list to contain only those inventory groups authorized for the user's
        //account. This is done so the user only sees inventory groups they are authorized to view.
        if (Constants.OBJECT_TYPE_USER.equalsIgnoreCase(btxDetails.getObjectType())) {
          if (!securityInfo.isInRoleSystemOwner()) {
            String account = securityInfo.getAccount();
            AccountSrchSBHome home = (AccountSrchSBHome) EjbHomes.getHome(AccountSrchSBHome.class);
            AccountSrchSB accountInventoryGroups = home.create();
            List authorizedInventoryGroups = accountInventoryGroups
                .getInventoryGroupsAssignedToAccount(account);
            removeUnauthorizedEntities(authorizedInventoryGroups, allInventoryGroups);
          }
        }
        btxDetails.setAllInventoryGroups(allInventoryGroups);
      }
      catch (Exception e) {
        ApiFunctions.throwAsRuntimeException(e);
      }
    }
    return btxDetails;
  }

  /**
   * Perform a BTX transaction: manage the inventory groups for a user or account
   */
  private BtxDetailsManageInventoryGroups performManageInventoryGroups(
                                                                       BtxDetailsManageInventoryGroups btxDetails)
      throws Exception {
    if (Constants.OBJECT_TYPE_ACCOUNT.equalsIgnoreCase(btxDetails.getObjectType())) {
      String accountId = btxDetails.getAccountData().getId();
      String[] selectedInventoryGroups = btxDetails.getSelectedInventoryGroups();
      // We initialize removedInventoryGroups to be all of the inventory groups to which the
      // account currently has access, and remove any ones that it *still* should have access to
      // below. So it ends up being just the list of inventory groups to which it used to have
      // access but to which it no longer has access after the change we're currently processing.
      AccountSrchSBHome home = (AccountSrchSBHome) EjbHomes.getHome(AccountSrchSBHome.class);
      AccountSrchSB accountInventoryGroups = home.create();
      List removedInventoryGroups = accountInventoryGroups
          .getInventoryGroupsAssignedToAccount(accountId);

      //determine what inventory groups have been added and/or removed
      List addedInventoryGroupIds = new ArrayList();
      int count = selectedInventoryGroups.length;
      for (int i = 0; i < count; i++) {
        String id = selectedInventoryGroups[i];
        Iterator iterator = removedInventoryGroups.iterator();
        boolean found = false;
        while (iterator.hasNext() && !found) {
          LogicalRepository inventoryGroup = (LogicalRepository) iterator.next();
          if (inventoryGroup.getId().equalsIgnoreCase(id)) {
            found = true;
            removedInventoryGroups.remove(inventoryGroup);
          }
        }
        if (!found) {
          addedInventoryGroupIds.add(id);
        }
      }

      //Update the account with the new inventory group list
      accountInventoryGroups.setInventoryGroupsAssignedToAccount(accountId, ApiFunctions
          .safeToList(selectedInventoryGroups));

      // Set the output/result properties for this transaction.
      btxDetails.setRemovedInventoryGroups(removedInventoryGroups);
      btxDetails.setAddedInventoryGroups(LogicalRepositoryUtils
          .getLogicalRepositoriesById(addedInventoryGroupIds));
      //since we're going back to the manage inventory group page, reset the selected and all
      //privileges lists
      btxDetails.setAssignedInventoryGroups(accountInventoryGroups
          .getInventoryGroupsAssignedToAccount(accountId));
      btxDetails.setAllInventoryGroups(LogicalRepositoryUtils.getAllLogicalRepositories());

      //to prevent a "blank" history record, if there were no changes to process
      //(that is, the account's current inventory groups haven't actually changed)
      //mark the transaction as incomplete but still forward to success. If there were
      //SQL statements executed then mark it completed.
      if (addedInventoryGroupIds.size() == 0 && removedInventoryGroups.size() == 0) {
        btxDetails.setActionForwardTXIncomplete("success");
      }
      else {
        btxDetails.setActionForwardTXCompleted("success");
      }
    }
    else if (Constants.OBJECT_TYPE_USER.equalsIgnoreCase(btxDetails.getObjectType())) {
      String userId = btxDetails.getUserData().getUserId();
      String accountId = btxDetails.getUserData().getAccountId();
      String[] selectedInventoryGroups = btxDetails.getSelectedInventoryGroups();
      SecurityInfo securityInfo = btxDetails.getLoggedInUserSecurityInfo();
      // We initialize removedInventoryGroups to be all of the inventory groups to which the user
      // currently has access, and remove any ones that they *still* should have access to below.
      // So it ends up being just the list of inventory groups to which they used to have access but
      // to which they no longer have access after the change we're currently processing. Note that
      // if the user is not in a system owner role, we need to remove any inventory groups to which
      // the user is not authorized to assign/revoke just as we did when starting this transaction.
      // Otherwise, inventory groups that the user is not authorized to assign/revoke would be
      // revoked.
      List removedInventoryGroups = LogicalRepositoryUtils
          .getLogicalRepositoriesForUserByFullName(userId);
      if (!securityInfo.isInRoleSystemOwner()) {
        String account = securityInfo.getAccount();
        AccountSrchSBHome home = (AccountSrchSBHome) EjbHomes.getHome(AccountSrchSBHome.class);
        AccountSrchSB accountInventoryGroups = home.create();
        List authorizedInventoryGroups = accountInventoryGroups
            .getInventoryGroupsAssignedToAccount(account);
        removeUnauthorizedEntities(authorizedInventoryGroups, removedInventoryGroups);
      }

      //determine what inventory groups have been added and/or removed
      List addedInventoryGroupIds = new ArrayList();
      int count = selectedInventoryGroups.length;
      for (int i = 0; i < count; i++) {
        String id = selectedInventoryGroups[i];
        Iterator iterator = removedInventoryGroups.iterator();
        boolean found = false;
        while (iterator.hasNext() && !found) {
          LogicalRepository inventoryGroup = (LogicalRepository) iterator.next();
          if (inventoryGroup.getId().equalsIgnoreCase(id)) {
            found = true;
            removedInventoryGroups.remove(inventoryGroup);
          }
        }
        if (!found) {
          addedInventoryGroupIds.add(id);
        }
      }

      //Remove access to inventory groups to which the user formerly had access but doesn't any
      // more
      removeInventoryGroupsFromUser(userId, removedInventoryGroups);

      //Add access to inventory groups to which the user now has access
      addInventoryGroupsToUser(userId, addedInventoryGroupIds);

      // Set the output/result properties for this transaction.
      btxDetails.setRemovedInventoryGroups(removedInventoryGroups);
      btxDetails.setAddedInventoryGroups(LogicalRepositoryUtils
          .getLogicalRepositoriesById(addedInventoryGroupIds));
      //since we're going back to the manage inventory groups page, reset the selected and all
      //privileges lists
      List assignedInventoryGroups = LogicalRepositoryUtils
          .getLogicalRepositoriesForUserByFullName(userId);
      List allInventoryGroups = LogicalRepositoryUtils.getAllLogicalRepositories();
      //If the user is not in a system owner role, alter the assigned inventory groups and all
      //inventory groups to contain only those inventory groups authorized for the user's account.
      //This is done so the user only sees inventory groups they are authorized to view.
      if (!securityInfo.isInRoleSystemOwner()) {
        String account = securityInfo.getAccount();
        AccountSrchSBHome home = (AccountSrchSBHome) EjbHomes.getHome(AccountSrchSBHome.class);
        AccountSrchSB accountInventoryGroups = home.create();
        List authorizedInventoryGroups = accountInventoryGroups
            .getInventoryGroupsAssignedToAccount(account);
        removeUnauthorizedEntities(authorizedInventoryGroups, assignedInventoryGroups);
        removeUnauthorizedEntities(authorizedInventoryGroups, allInventoryGroups);
      }
      btxDetails.setAssignedInventoryGroups(assignedInventoryGroups);
      btxDetails.setAllInventoryGroups(allInventoryGroups);

      //to prevent a "blank" history record, if there were no changes to process
      //(that is, the user's current privileges haven't actually changed)
      //mark the transaction as incomplete but still forward to success. If there were
      //SQL statements executed then mark it completed.
      //
      if (addedInventoryGroupIds.size() == 0 && removedInventoryGroups.size() == 0) {
        btxDetails.setActionForwardTXIncomplete("success");
      }
      else {
        btxDetails.setActionForwardTXCompleted("success");
      }
    }

    return btxDetails;
  }

  /**
   * Add access rights to the specified inventory groups for the specified user.
   * 
   * @param userId The user id.
   * @param removedInventoryGroups The list of inventory group ids (Strings).
   */
  private void addInventoryGroupsToUser(String userId, List addedInventoryGroups) {
    if (addedInventoryGroups.size() == 0) {
      return;
    }

    String query = "INSERT INTO " + DbConstants.TABLE_LOGICAL_REPOS_USER + " ("
        + DbConstants.LOGICAL_REPOS_USER_REPOSITORY_ID + ", "
        + DbConstants.LOGICAL_REPOS_USER_USER_ID + ") VALUES (?,?)";

    Connection con = null;
    PreparedStatement pstmt = null;
    try {
      con = ApiFunctions.getDbConnection();
      pstmt = con.prepareStatement(query);

      Iterator iter = addedInventoryGroups.iterator();
      while (iter.hasNext()) {
        String inventoryGroupId = (String) iter.next();

        pstmt.setString(1, inventoryGroupId);
        pstmt.setString(2, userId);

        pstmt.executeUpdate();
      }
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(pstmt);
      ApiFunctions.close(con);
    }
  }

  /**
   * Remove access rights to the specified inventory groups for the specified user.
   * 
   * @param userId The user id.
   * @param removedInventoryGroups The list of inventory groups (LogicalRepository).
   */
  private void removeInventoryGroupsFromUser(String userId, List removedInventoryGroups) {
    if (removedInventoryGroups.size() == 0) {
      return;
    }

    String query = "DELETE FROM " + DbConstants.TABLE_LOGICAL_REPOS_USER + " "
        + DbAliases.TABLE_LOGICAL_REPOS_USER + " WHERE " + DbAliases.TABLE_LOGICAL_REPOS_USER + "."
        + DbConstants.LOGICAL_REPOS_USER_REPOSITORY_ID + " = ? AND "
        + DbAliases.TABLE_LOGICAL_REPOS_USER + "." + DbConstants.LOGICAL_REPOS_USER_USER_ID
        + " = ?";

    Connection con = null;
    PreparedStatement pstmt = null;
    try {
      con = ApiFunctions.getDbConnection();
      pstmt = con.prepareStatement(query.toString());

      Iterator iter = removedInventoryGroups.iterator();
      while (iter.hasNext()) {
        LogicalRepository inventoryGroup = (LogicalRepository) iter.next();
        String inventoryGroupId = inventoryGroup.getId();

        pstmt.setString(1, inventoryGroupId);
        pstmt.setString(2, userId);

        pstmt.executeUpdate();
      }
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(pstmt);
      ApiFunctions.close(con);
    }
  }

  /**
   * Do BTX transaction validation for the performMaintainPolicyStart performer method.
   */
  private BTXDetails validatePerformMaintainPolicyStart(BtxDetailsMaintainPolicyStart btxDetails)
      throws Exception {

    // We don't do much validation here, since this is the action that we go to to report
    // validation errors. Because of that, we can expect that there will sometimes be
    // odd data in the fields we get here. We do need some things to be correct, though.
    // For example, the operation field needs to have a valid operation name in it, and for
    // update operations we need a valid policy id so that we can look up its data.

    boolean testProhibitPolicyId = false;
    boolean testInvalidPolicyId = false;

    String operation = btxDetails.getOperation();

    if (ApiFunctions.isEmpty(operation)) {
      btxDetails.addActionError(new BtxActionError("orm.error.policy.requiredOperation"));
    }
    else if (!(Constants.OPERATION_CREATE.equals(operation)
        || Constants.OPERATION_UPDATE.equals(operation) || Constants.OPERATION_DELETE
        .equals(operation))) {
      btxDetails.addActionError(new BtxActionError(
          "orm.error.policy.operationCreateOrUpdateOrDelete"));
    }

    // Decide which tests to do based on the operation.
    if (Constants.OPERATION_CREATE.equals(operation)) {
      testProhibitPolicyId = true;
    } // end operation == Create
    else if (Constants.OPERATION_UPDATE.equals(operation)) {
      testInvalidPolicyId = true;
    } // end operation == Update

    // Now perform the tests...
    String policyId = btxDetails.getPolicy().getPolicyId();

    if (testProhibitPolicyId && !ApiFunctions.isEmpty(policyId)) {
      btxDetails.addActionError(new BtxActionError("orm.error.policy.prohibitPolicyId"));
    }

    if (testInvalidPolicyId) {
      if (ApiFunctions.isEmpty(policyId)) {
        btxDetails.addActionError(new BtxActionError("orm.error.policy.requiredPolicyId"));
      }
      else if (!PolicyUtils.isExistingPolicy(policyId)) {
        btxDetails.addActionError(new BtxActionError("orm.error.policy.invalidPolicyId"));
      }
    }
    return btxDetails;
  }

  /**
   * This is the main BTX entry point for performing the transaction that starts the process of
   * creating, editing, or deleting a policy.
   */
  private BTXDetails performMaintainPolicyStart(BtxDetailsMaintainPolicyStart btxDetails)
      throws Exception {
    SecurityInfo securityInfo = btxDetails.getLoggedInUserSecurityInfo();

    //populate the list of policies
    if (securityInfo.isInRoleSystemOwner()) {
      btxDetails.setPolicies(PolicyUtils.getAllPolicies());
    }
    else {
      String accountId = securityInfo.getAccount();
      btxDetails.getPolicy().setAccountId(accountId);
      btxDetails.setPolicies(PolicyUtils.getPoliciesByAccountId(securityInfo.getAccount(), true,
          true, false));
    }

    //populate the choices for the case and sample registration forms. If the user is in a system
    //owner account get all registration forms across all accounts, otherwise, get all registration
    //forms for the account to which the user belongs. Additionally include choices for when no
    //account has been specified and when no forms are available
    //create a legal value set for use when no registration forms are available for an account
    LegalValueSet noForms = new LegalValueSet();
    noForms.addLegalValue("", "None Available");
    //create a legal value set for use when no account is selected
    LegalValueSet noAccountChosen = new LegalValueSet();
    noAccountChosen.addLegalValue("", "Please select an account");
    PolicyData policy = btxDetails.getPolicy();
    if (securityInfo.isInRoleSystemOwner()) {
      //get all case registration forms across accounts and organize them by account id
      List formDefs = FormUtils.getRegistrationFormDefinitions(null,
          TagTypes.DOMAIN_OBJECT_VALUE_CASE);
      Map accountToForms = new HashMap();
      Iterator formIterator = formDefs.iterator();
      while (formIterator.hasNext()) {
        BigrFormDefinition formDef = (BigrFormDefinition) formIterator.next();
        List accountForms = (List) accountToForms.get(formDef.getAccount());
        if (accountForms == null) {
          accountForms = new ArrayList();
          accountToForms.put(formDef.getAccount(), accountForms);
        }
        accountForms.add(formDef);
      }

      //create the map of account to legal value set for case registration forms
      Map caseMap = new HashMap();
      LegalValueSet allAccounts = IltdsUtils.getAccountList();
      Iterator accountIterator = allAccounts.getIterator();
      while (accountIterator.hasNext()) {
        String accountId = ((LegalValue) accountIterator.next()).getValue();
        List accountFormDefs = (List) accountToForms.get(accountId);
        if (!ApiFunctions.isEmpty(accountFormDefs)) {
          caseMap.put(accountId, FormUtils.getFormsAsLVS(accountFormDefs, true));
        }
        else {
          caseMap.put(accountId, noForms);
        }
      }
      //add a map for when no account is chosen
      caseMap.put("", noAccountChosen);

      //set the map on the btxDetails
      btxDetails.setCaseRegistrationFormChoiceMap(caseMap);

      //get all sample registration forms across accounts and organize them by account id
      formDefs = FormUtils
          .getRegistrationFormDefinitions(null, TagTypes.DOMAIN_OBJECT_VALUE_SAMPLE);
      accountToForms = new HashMap();
      formIterator = formDefs.iterator();
      while (formIterator.hasNext()) {
        BigrFormDefinition formDef = (BigrFormDefinition) formIterator.next();
        List accountForms = (List) accountToForms.get(formDef.getAccount());
        if (accountForms == null) {
          accountForms = new ArrayList();
          accountToForms.put(formDef.getAccount(), accountForms);
        }
        accountForms.add(formDef);
      }

      //create the map of account to legal value set for sample registration forms
      Map sampleMap = new HashMap();
      accountIterator = allAccounts.getIterator();
      while (accountIterator.hasNext()) {
        String accountId = ((LegalValue) accountIterator.next()).getValue();
        List accountFormDefs = (List) accountToForms.get(accountId);
        if (!ApiFunctions.isEmpty(accountFormDefs)) {
          sampleMap.put(accountId, FormUtils.getFormsAsLVS(accountFormDefs, true));
        }
        else {
          sampleMap.put(accountId, noForms);
        }
      }
      //add a map for when no account is chosen
      sampleMap.put("", noAccountChosen);

      //set the map on the btxDetails
      btxDetails.setSampleRegistrationFormChoiceMap(sampleMap);
    }
    else {
      //case choices
      List formDefs = FormUtils.getRegistrationFormDefinitions(securityInfo.getAccount(),
          TagTypes.DOMAIN_OBJECT_VALUE_CASE);
      Map caseMap = new HashMap();
      if (!ApiFunctions.isEmpty(formDefs)) {
        caseMap.put(securityInfo.getAccount(), FormUtils.getFormsAsLVS(formDefs, true));
      }
      else {
        caseMap.put(securityInfo.getAccount(), noForms);
      }
      btxDetails.setCaseRegistrationFormChoiceMap(caseMap);
      //sample choices
      formDefs = FormUtils.getRegistrationFormDefinitions(securityInfo.getAccount(),
          TagTypes.DOMAIN_OBJECT_VALUE_SAMPLE);
      Map sampleMap = new HashMap();
      if (!ApiFunctions.isEmpty(formDefs)) {
        sampleMap.put(securityInfo.getAccount(), FormUtils.getFormsAsLVS(formDefs, true));
      }
      else {
        sampleMap.put(securityInfo.getAccount(), noForms);
      }
      btxDetails.setSampleRegistrationFormChoiceMap(sampleMap);
    }

    String operation = btxDetails.getOperation();

    if (Constants.OPERATION_UPDATE.equals(operation)) {
      if (btxDetails.isPopulatePolicyOutputFields()) {
        String policyId = btxDetails.getPolicy().getPolicyId();
        PolicyExtendedData policyData = (PolicyExtendedData) PolicyUtils.getPolicyData(policyId,
            true, PolicyExtendedData.class);

        // Update the associated irbs.
        policyData.processSecurityInfo(btxDetails.getLoggedInUserSecurityInfo());

        btxDetails.setPolicy(policyData);
        btxDetails.setReleaseRequiredOldValue(policyData.getReleaseRequired());
        btxDetails.setVerifyRequiredOldValue(policyData.getVerifyRequired());
      }
    }

    btxDetails.setActionForwardTXCompleted("success");
    return btxDetails;
  }

  /**
   * Do BTX transaction validation for the performMaintainPolicySave performer method.
   */
  private BTXDetails validatePerformMaintainPolicySave(BtxDetailsMaintainPolicy btxDetails)
      throws Exception {

    boolean testProhibitPolicyId = false;
    boolean testInvalidPolicyId = false;

    boolean testRequiredPolicyName = false;
    boolean testValidAllocationNumerator = false;
    boolean testValidAllocationDenominator = false;
    boolean testRequiredAllocationFormat = false;
    boolean testRequiredDefaultLogicalRepository = false;
    boolean testRequiredConsentVerification = false;
    boolean testRequiredCaseRelease = false;
    boolean testRequiredCaseRegistrationForm = false;
    boolean testVerifyReleaseValueChange = false;
    boolean testValidAccount = false;
    boolean testAllowForUnlinkedCases = false;

    boolean testUnusedOnDelete = false;

    boolean testSampleConfigurationInformation = false;

    String operation = btxDetails.getOperation();

    if (ApiFunctions.isEmpty(operation)) {
      btxDetails.addActionError(new BtxActionError("orm.error.policy.requiredOperation"));
    }
    else if (!(Constants.OPERATION_CREATE.equals(operation)
        || Constants.OPERATION_UPDATE.equals(operation) || Constants.OPERATION_DELETE
        .equals(operation))) {
      btxDetails.addActionError(new BtxActionError(
          "orm.error.policy.operationCreateOrUpdateOrDelete"));
    }

    // Decide which tests to do based on the operation.
    if (Constants.OPERATION_CREATE.equals(operation)) {
      testProhibitPolicyId = true;

      testRequiredPolicyName = true;
      testValidAllocationNumerator = true;
      testValidAllocationDenominator = true;
      testRequiredAllocationFormat = true;
      testRequiredDefaultLogicalRepository = true;
      testRequiredConsentVerification = true;
      testRequiredCaseRelease = true;
      testRequiredCaseRegistrationForm = true;
      testValidAccount = true;
      testAllowForUnlinkedCases = true;
      testSampleConfigurationInformation = true;
    } // end operation == Create
    else if (Constants.OPERATION_UPDATE.equals(operation)) {
      testInvalidPolicyId = true;

      testRequiredPolicyName = true;
      testValidAllocationNumerator = true;
      testValidAllocationDenominator = true;
      testRequiredAllocationFormat = true;
      testRequiredDefaultLogicalRepository = true;
      testRequiredConsentVerification = true;
      testRequiredCaseRelease = true;
      testRequiredCaseRegistrationForm = true;
      testVerifyReleaseValueChange = true;
      testValidAccount = true;
      testAllowForUnlinkedCases = false;
      testSampleConfigurationInformation = true;
    } // end operation == Update
    else if (Constants.OPERATION_DELETE.equals(operation)) {
      testInvalidPolicyId = true;
      testUnusedOnDelete = true;
    } // end operation == Delete

    // Now perform the tests...

    PolicyData policy = btxDetails.getPolicy();
    boolean idOk = true;
    String policyId = policy.getPolicyId();

    if (testProhibitPolicyId && !ApiFunctions.isEmpty(policyId)) {
      idOk = false;
      btxDetails.addActionError(new BtxActionError("orm.error.policy.prohibitPolicyId"));
    }

    if (idOk && (testInvalidPolicyId || testUnusedOnDelete)) {
      if (ApiFunctions.isEmpty(policyId)) {
        idOk = false;
        btxDetails.addActionError(new BtxActionError("orm.error.policy.requiredPolicyId"));
      }
      else if (!PolicyUtils.isExistingPolicy(policyId)) {
        idOk = false;
        btxDetails.addActionError(new BtxActionError("orm.error.policy.invalidPolicyId"));
      }

      if (idOk && testUnusedOnDelete) {
        checkIfSafeToDelete(btxDetails);
      }
    }

    // Run policy name tests.
    if (testRequiredPolicyName) {
      String policyName = policy.getPolicyName();
      if (ApiFunctions.isEmpty(policyName)) {
        btxDetails.addActionError(new BtxActionError("orm.error.policy.requiredPolicyName"));
      }
      else {
        if (idOk && isExistingPolicyWithName(policyName, policyId)) {
          btxDetails.addActionError(new BtxActionError("orm.error.policy.duplicatePolicyName"));
        }

        if (policyName.length() > MAX_POLICY_NAME_LENGTH) {
          btxDetails.addActionError(new BtxActionError("error.lengthExceeded", "Policy Name",
              MAX_POLICY_NAME_LENGTH + " characters"));
        }
      }
    }

    // Run allocation format tests.
    if (testRequiredAllocationFormat) {
      String allocationFormat = policy.getAllocationFormatCid();
      if (ApiFunctions.isEmpty(allocationFormat)) {
        btxDetails.addActionError(new BtxActionError("orm.error.policy.requiredAllocationFormat"));
      }
      else {
        GbossValueSet valueSet = BigrGbossData
            .getValueSet(ArtsConstants.VALUE_SET_ALLOCATION_FORMAT);
        if (!valueSet.containsValue(allocationFormat)) {
          btxDetails.addActionError(new BtxActionError("orm.error.policy.invalidAllocationFormat"));
        }
      }
    }

    // Run policy allocation (numerator & denominator) tests.
    if (testValidAllocationNumerator && testValidAllocationDenominator) {

      boolean nonIntegerFound = false;
      // Check for required field: allocation numerator.
      if (ApiFunctions.isEmpty(policy.getAllocationNumerator())) {
        btxDetails
            .addActionError(new BtxActionError("orm.error.policy.requiredAllocationNumerator"));
        nonIntegerFound = true;
      }
      else if (!ApiFunctions.isInteger(policy.getAllocationNumerator())) {
        btxDetails.addActionError(new BtxActionError("errors.int", "Allocation numerator"));
        nonIntegerFound = true;
      }
      // Check for required field: allocation denominator.
      if (ApiFunctions.isEmpty(policy.getAllocationDenominator())) {
        btxDetails.addActionError(new BtxActionError(
            "orm.error.policy.requiredAllocationDenominator"));
        nonIntegerFound = true;
      }
      else if (!ApiFunctions.isInteger(policy.getAllocationDenominator())) {
        btxDetails.addActionError(new BtxActionError("errors.int", "Allocation denominator"));
        nonIntegerFound = true;
      }

      //if integer values were specified, check their validity
      if (!nonIntegerFound) {
        int allocationNumerator = new Integer(policy.getAllocationNumerator()).intValue();
        int allocationDenominator = new Integer(policy.getAllocationDenominator()).intValue();
        if (allocationNumerator < 0) {
          btxDetails.addActionError(new BtxActionError("orm.error.policy.invalidNumerator"));
        }
        if (allocationNumerator > allocationDenominator) {
          btxDetails.addActionError(new BtxActionError(
              "orm.error.policy.numeratorGreaterThanDenominator"));
        }
        if ((allocationNumerator == 0) && (allocationDenominator != 0)) {
          btxDetails.addActionError(new BtxActionError("orm.error.policy.zeroNumerator"));
        }
        if (allocationDenominator < 0) {
          btxDetails.addActionError(new BtxActionError("orm.error.policy.invalidDenominator"));
        }
      }
    }

    // Run default logical repository tests.
    if (testRequiredDefaultLogicalRepository) {
      if (ApiFunctions.isEmpty(policy.getDefaultLogicalReposId())) {
        btxDetails.addActionError(new BtxActionError(
            "orm.error.policy.requiredDefaultLogicalRepository"));
      }
    }

    // check that consent verification required is not null
    if (testRequiredConsentVerification) {
      if (ApiFunctions.isEmpty(policy.getVerifyRequired())) {
        btxDetails
            .addActionError(new BtxActionError("orm.error.policy.requiredConsentVerification"));
      }
    }

    // check that case release required is not null
    if (testRequiredCaseRelease) {
      if (ApiFunctions.isEmpty(policy.getReleaseRequired())) {
        btxDetails.addActionError(new BtxActionError("orm.error.policy.requiredCaseRelease"));
      }
    }

    // check that case registration form is valid
    if (testRequiredCaseRegistrationForm) {
      String formDefId = btxDetails.getPolicy().getCaseRegistrationFormId();
      if (ApiFunctions.isEmpty(formDefId)) {
        btxDetails.addActionError(new BtxActionError("orm.error.policy.requiredRegistrationForm",
            "cases"));
      }
      if (!ApiFunctions.isEmpty(formDefId)) {
        BigrFormDefinition formDef = FormUtils.getFormDefinition(btxDetails
            .getLoggedInUserSecurityInfo(), formDefId);
        if (formDef == null) {
          btxDetails.addActionError(new BtxActionError("orm.error.policy.invalidRegistrationForm",
              "cases", "does not exist"));
        }
        else if (!formDef.isEnabled()) {
          btxDetails.addActionError(new BtxActionError("orm.error.policy.invalidRegistrationForm",
              "cases", "is not enabled"));
        }
        else if (!formDef.getAccount().equalsIgnoreCase(btxDetails.getPolicy().getAccountId())) {
          btxDetails.addActionError(new BtxActionError("orm.error.policy.invalidRegistrationForm",
              "cases", "does not belong to the same account as the policy"));
        }
        else if (!TagTypes.DOMAIN_OBJECT_VALUE_CASE.equalsIgnoreCase(formDef.getObjectType())) {
          btxDetails.addActionError(new BtxActionError("orm.error.policy.invalidRegistrationForm",
              "cases", "is not applicable for cases"));
        }
        else if (!TagTypes.USES_VALUE_REGISTRATION.equalsIgnoreCase(formDef.getUses())) {
          btxDetails.addActionError(new BtxActionError("orm.error.policy.invalidRegistrationForm",
              "cases", "is not a registration form"));
        }
        else {
          //everything is ok with this form, so store it's name so it can be recorded
          btxDetails.getPolicy().setCaseRegistrationFormName(formDef.getName());
        }
      }
    }

    // check to see if the value of Release Required or Verification Required has changed
    if (testVerifyReleaseValueChange) {
      String queryString = "SELECT count(*) AS consent_count FROM iltds_informed_consent WHERE policy_id = ?";
      // is the policy in use by cases
      String count = calculatePolicyCount(queryString, "consent_count", policyId);
      if (!btxDetails.getVerifyRequiredOldValue().equals(policy.getVerifyRequired())) {
        if (!count.equalsIgnoreCase("0")) {
          btxDetails
              .addActionError(new BtxActionError("orm.error.policy.verificationCannotChange"));
        }
      }
      if (!btxDetails.getReleaseRequiredOldValue().equals(policy.getReleaseRequired())) {
        if (!count.equalsIgnoreCase("0")) {
          btxDetails.addActionError(new BtxActionError("orm.error.policy.releaseCannotChange"));
        }
      }
    }

    if (testValidAccount) {
      String accountId = policy.getAccountId();
      if (ApiFunctions.isEmpty(accountId)) {
        // The user does not belong to the account.
        btxDetails.addActionError(new BtxActionError("orm.error.policy.requiredAccountId"));
      }
      else {
        try {
          ArdaisaccountKey accountKey = new ArdaisaccountKey(accountId);
          ArdaisaccountAccessBean accountAB = new ArdaisaccountAccessBean(accountKey);

          SecurityInfo securityInfo = btxDetails.getLoggedInUserSecurityInfo();
          // Check if the role is not Ardais.
          if (!securityInfo.isInRoleSystemOwner()) {
            // Check if the account id passed in is the same account as the user.
            if (!accountId.equals(securityInfo.getAccount())) {
              // The user does not belong to the account.
              btxDetails
                  .addActionError(new BtxActionError("orm.error.policy.invalidAccountForUser"));
            }
          }
        }
        catch (ObjectNotFoundException e) {
          // Account doesn't exist.
          btxDetails.addActionError(new BtxActionError("orm.error.policy.accountDoesNotExist"));
        }
      }
    }

    if (testAllowForUnlinkedCases) {
      String allowForUnlinkedCases = policy.getAllowForUnlinkedCases();
      if (ApiFunctions.isEmpty(allowForUnlinkedCases)) {
        // The user does not belong to the account.
        btxDetails.addActionError(new BtxActionError(
            "orm.error.policy.requiredAllowForUnlinkedCases"));
      }
    }

    if (testSampleConfigurationInformation) {
      checkSampleTypeConfiguration(btxDetails);
    }

    return btxDetails;
  }

  /**
   * This is the main BTX entry point for performing the transaction that creates, edits, or deletes
   * a policy.
   */
  private BTXDetails performMaintainPolicySave(BtxDetailsMaintainPolicy btxDetails)
      throws Exception {

    // Get the operation being performed.
    String operation = btxDetails.getOperation();
    PolicyData policy = btxDetails.getPolicy();

    // The action we take here depends on the operation.
    if (Constants.OPERATION_CREATE.equals(operation)) {
      // Create the policy.
      policyCreate(btxDetails);

      btxDetails.addActionMessage(new BtxActionMessage("orm.error.policy.confirmCreatePolicy",
          Escaper.htmlEscape(policy.getPolicyName())));
    } // end operation == Create
    else if (Constants.OPERATION_UPDATE.equals(operation)) {

      // Update the policy.
      policyUpdate(btxDetails);

      btxDetails.addActionMessage(new BtxActionMessage("orm.error.policy.confirmUpdatePolicy",
          Escaper.htmlEscape(policy.getPolicyName())));
    } // end operation == Update
    else if (Constants.OPERATION_DELETE.equals(operation)) {
      // Look up the policy we're going to delete so that we can set output parameters.
      PolicyData outputPolicyData = PolicyUtils.getPolicyData(policy.getPolicyId());

      // Populate the detail with data object.
      BigrBeanUtilsBean.SINGLETON.copyProperties(btxDetails.getPolicy(), outputPolicyData);

      // Now delete the policy.
      policyDelete(btxDetails);

      btxDetails.addActionMessage(new BtxActionMessage("orm.error.policy.confirmDeletePolicy",
          Escaper.htmlEscape(policy.getPolicyName())));
    } // end operation == Delete

    btxDetails.setActionForwardTXCompleted("success");
    return btxDetails;
  }

  private void checkIfSafeToDelete(BtxDetailsMaintainPolicy btxDetails) {
    String queryString = null;
    String count = null;

    PolicyData policy = btxDetails.getPolicy();
    String policyId = policy.getPolicyId();
    // This will fix the problem where policy ids would show up instead of the prefixed policy id.
    // Look up the policy we're going to delete so that we can set output parameters.
    PolicyData outputPolicyData = PolicyUtils.getPolicyData(policy.getPolicyId());
    String policyName = Escaper.htmlEscape(outputPolicyData.getPolicyName());

    queryString = "SELECT count(*) AS consent_count FROM iltds_informed_consent WHERE policy_id = ?";
    count = calculatePolicyCount(queryString, "consent_count", policyId);
    if (!count.equalsIgnoreCase("0")) {
      btxDetails.addActionError(new BtxActionError("orm.error.policy.notSafeToDelete", policyName,
          count, "consent"));
    }

    queryString = "SELECT count(*) AS irb_count FROM es_ardais_irb WHERE policy_id = ?";
    count = calculatePolicyCount(queryString, "irb_count", policyId);
    if (!count.equalsIgnoreCase("0")) {
      btxDetails.addActionError(new BtxActionError("orm.error.policy.notSafeToDelete", policyName,
          count, "IRB"));
    }
  }

  private void checkIfSafeToDelete(BtxDetailsMaintainRole btxDetails) {
    String queryString = null;
    String count = null;

    RoleDto role = btxDetails.getRole();
    String roleId = role.getId();
    // Look up the role we're going to delete so that we can set output parameters.
    RoleDto outputRoleData = RoleUtils.getRoleData(roleId);
    String roleName = Escaper.htmlEscape(outputRoleData.getName());

    //commented out the check on privileges, as it could prevent a non-system-owner user
    //from being able to delete a role.  If the role has been assigned a privilege that
    //non-system-owners are not allowed to manage they will not be able to remove that
    //privilege from the role, and thus will be unable to delete the role even though
    //it will appear to them as if no privileges have been assigned to the role.
//    queryString = "SELECT count(*) AS privilege_count FROM BIGR_ROLE_PRIVILEGE WHERE role_id = ?";
//    count = calculateRoleCount(queryString, "consent_count", roleId);
//    if (!count.equalsIgnoreCase("0")) {
//      btxDetails.addActionError(new BtxActionError("orm.error.role.notSafeToDelete", roleName,
//          count, "privilege"));
//    }

    queryString = "SELECT count(*) AS user_count FROM BIGR_ROLE_USER WHERE role_id = ?";
    count = calculateRoleCount(queryString, "user_count", roleId);
    if (!count.equalsIgnoreCase("0")) {
      btxDetails.addActionError(new BtxActionError("orm.error.role.notSafeToDelete", roleName,
          count, "user"));
    }
  }

  private String calculatePolicyCount(String queryString, String countVariable, String pk) {
    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    String count = null;
    ;
    try {
      con = ApiFunctions.getDbConnection();
      pstmt = con.prepareStatement(queryString);
      pstmt.setBigDecimal(1, new BigDecimal(pk));
      rs = ApiFunctions.queryDb(pstmt, con);

      rs.next();
      count = rs.getString(countVariable);
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(con, pstmt, rs);
    }
    return count;
  }

  private String calculateRoleCount(String queryString, String countVariable, String pk) {
    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    String count = null;
    try {
      con = ApiFunctions.getDbConnection();
      pstmt = con.prepareStatement(queryString);
      pstmt.setString(1, pk);
      rs = ApiFunctions.queryDb(pstmt, con);

      rs.next();
      count = rs.getString(countVariable);
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(con, pstmt, rs);
    }
    return count;
  }

  /**
   * Return true if there is a policy with the specified name. The matching is case-insensitive. If
   * the policyId parameter is non-empty, then it won't consider that policy id when matching.
   * 
   * @param name The name to search for.
   * @param policyId if non-empty, this policy id will be excluded from the match (this will return
   *          false if the only policy that would have matched is the specified one).
   * @return true if a matching policy exists.
   */
  private boolean isExistingPolicyWithName(String policyName, String policyId) {
    boolean testPolicyId = (!ApiFunctions.isEmpty(policyId));

    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
      con = ApiFunctions.getDbConnection();

      StringBuffer query = new StringBuffer(128);
      query.append("SELECT 1 FROM ");
      query.append(DbConstants.TABLE_ARD_POLICY);
      query.append(" ");
      query.append(DbAliases.TABLE_ARD_POLICY);
      query.append(" WHERE LOWER(");
      query.append(DbAliases.TABLE_ARD_POLICY);
      query.append(".");
      query.append(DbConstants.POLICY_NAME);
      query.append(") = ?");
      if (testPolicyId) {
        query.append(" AND ID <> ?");
      }

      pstmt = con.prepareStatement(query.toString());
      pstmt.setString(1, ApiFunctions.safeString(policyName).toLowerCase());
      if (testPolicyId) {
        pstmt.setString(2, policyId);
      }
      rs = ApiFunctions.queryDb(pstmt, con);

      return rs.next();
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
      return false; // unreached, keep compiler happy
    }
    finally {
      ApiFunctions.close(con, pstmt, rs);
    }
  }

  /**
   * Return true if there is a role with the specified name within the specified account. The 
   * matching is case-insensitive. If the roleId parameter is non-empty, then it won't consider 
   * that role id when matching.
   * 
   * @param name The name to search for.
   * @param roleId if non-empty, this role id will be excluded from the match (this will return
   *          false if the only role that would have matched is the specified one).
   * @return true if a matching role exists.
   */
  private boolean isExistingRoleWithName(String roleName, String accountId, String roleId) {
    boolean testRoleId = (!ApiFunctions.isEmpty(roleId));

    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
      con = ApiFunctions.getDbConnection();

      StringBuffer query = new StringBuffer(128);
      query.append("SELECT 1 FROM ");
      query.append(DbConstants.TABLE_BIGR_ROLE);
      query.append(" ");
      query.append(DbAliases.TABLE_BIGR_ROLE);
      query.append(" WHERE LOWER(");
      query.append(DbAliases.TABLE_BIGR_ROLE);
      query.append(".");
      query.append(DbConstants.BIGR_ROLE_NAME);
      query.append(") = ?");
      query.append(" AND ");
      query.append(DbAliases.TABLE_BIGR_ROLE);
      query.append(".");
      query.append(DbConstants.BIGR_ROLE_ACCOUNT_ID);
      query.append(" = ?");
      if (testRoleId) {
        query.append(" AND ");
        query.append(DbAliases.TABLE_BIGR_ROLE);
        query.append(".");
        query.append(DbConstants.BIGR_ROLE_ID);
        query.append(" <> ?");
      }

      pstmt = con.prepareStatement(query.toString());
      pstmt.setString(1, ApiFunctions.safeString(roleName).toLowerCase());
      pstmt.setString(2, ApiFunctions.safeString(accountId));
      if (testRoleId) {
        pstmt.setString(3, roleId);
      }
      rs = ApiFunctions.queryDb(pstmt, con);

      return rs.next();
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
      return false; // unreached, keep compiler happy
    }
    finally {
      ApiFunctions.close(con, pstmt, rs);
    }
  }

  private void checkSampleTypeConfiguration(BtxDetailsMaintainPolicy btxDetails) {

    PolicyData policy = btxDetails.getPolicy();
    SampleTypeConfiguration stc = policy.getSampleTypeConfiguration();
    if (stc == null) {
      btxDetails.addActionError(new BtxActionError("error.noValue",
          "sample type configuration information"));
    }
    else {
      // iterate over the sample types, verifying that:
      // 1) it has a known cui.
      // 2) it has a supported value (since supported is a boolean it will have a value so
      //    nothing to do here
      // 3) if it is supported, it must have a registration form specified
      // 4) all specified registration forms are valid (i.e. exist, are enabled, and belong to the
      //    same account as the policy)
      Iterator sampleTypeIterator = stc.getSampleTypeMap().keySet().iterator();
      BigrConceptList knownSampleTypes = SystemConfiguration
          .getConceptList(SystemConfiguration.CONCEPT_LIST_SAMPLE_TYPES);
      while (sampleTypeIterator.hasNext()) {
        Object sampleTypeKey = sampleTypeIterator.next();
        SampleType st = (SampleType) stc.getSampleTypeMap().get(sampleTypeKey);
        if (ApiFunctions.isEmpty(st.getCui())) {
          btxDetails.addActionError(new BtxActionError("error.noValue",
              "CUI value for sample type with key = " + sampleTypeKey));
        }
        else if (!knownSampleTypes.containsConcept(st.getCui())) {
          btxDetails.addActionError(new BtxActionError("error.badValue", st.getCui(),
              "CUI value for sample type with key = " + sampleTypeKey, "a known sample type"));
        }
        else {
          String formDefId = st.getRegistrationFormId();
          if (st.isSupported()) {
            if (ApiFunctions.isEmpty(formDefId)) {
              btxDetails.addActionError(new BtxActionError(
                  "orm.error.policy.requiredRegistrationForm", "sample type "
                      + GbossFactory.getInstance().getDescription(st.getCui())));
            }
          }
          if (!ApiFunctions.isEmpty(formDefId)) {
            BigrFormDefinition formDef = FormUtils.getFormDefinition(btxDetails
                .getLoggedInUserSecurityInfo(), formDefId);
            if (formDef == null) {
              btxDetails.addActionError(new BtxActionError(
                  "orm.error.policy.invalidRegistrationForm", "sample type "
                      + GbossFactory.getInstance().getDescription(st.getCui()), "does not exist"));
            }
            else if (!formDef.isEnabled()) {
              btxDetails.addActionError(new BtxActionError(
                  "orm.error.policy.invalidRegistrationForm", "sample type "
                      + GbossFactory.getInstance().getDescription(st.getCui()), "is not enabled"));
            }
            else if (!formDef.getAccount().equalsIgnoreCase(btxDetails.getPolicy().getAccountId())) {
              btxDetails.addActionError(new BtxActionError(
                  "orm.error.policy.invalidRegistrationForm", "sample type "
                      + GbossFactory.getInstance().getDescription(st.getCui()),
                  "does not belong to the same account as the policy"));
            }
            else if (!TagTypes.DOMAIN_OBJECT_VALUE_SAMPLE.equalsIgnoreCase(formDef.getObjectType())) {
              btxDetails.addActionError(new BtxActionError(
                  "orm.error.policy.invalidRegistrationForm", "sample type "
                      + GbossFactory.getInstance().getDescription(st.getCui()),
                  "is not applicable for samples"));
            }
            else if (!TagTypes.USES_VALUE_REGISTRATION.equalsIgnoreCase(formDef.getUses())) {
              btxDetails.addActionError(new BtxActionError(
                  "orm.error.policy.invalidRegistrationForm", "sample type "
                      + GbossFactory.getInstance().getDescription(st.getCui()),
                  "is not a registration form"));
            }
            else {
              //everything is ok with this form, so set it's name so it can be recorded
              st.setRegistrationFormName(formDef.getName());
            }
          }
        }
      }
    }
  }

  /**
   * Create a new policy using the fields specified in the <code>policyData</code> parameter. This
   * assumes that the id field is null and that all other fields are valid, but it doesn't check any
   * of this. As a side effect, it sets the id field on the <code>policyData</code> parameter to
   * the id assigned to the new policy.
   * 
   * @param btxDetails The description of the policy to create.
   */
  private void policyCreate(BtxDetailsMaintainPolicy btxDetails) {

    PolicyData policy = btxDetails.getPolicy();

    Connection con = null;
    CallableStatement cstmt = null;
    TemporaryClob policyClob = null;
    try {
      con = ApiFunctions.getDbConnection();

      StringBuffer query = new StringBuffer(256);
      query.append("{ CALL INSERT INTO ");
      query.append(DbConstants.TABLE_ARD_POLICY);
      query.append(" (");
      query.append(DbConstants.POLICY_NAME);
      query.append(",");
      query.append(DbConstants.POLICY_ALLOCATION_DENOMINATOR);
      query.append(",");
      query.append(DbConstants.POLICY_ALLOCATION_NUMERATOR);
      query.append(",");
      query.append(DbConstants.POLICY_ALLOCATION_FORMAT_CID);
      query.append(",");
      query.append(DbConstants.POLICY_DEFAULT_LOGICAL_REPOS_ID);
      query.append(",");
      query.append(DbConstants.POLICY_RESTRICTED_LOGICAL_REPOS_ID);
      query.append(",");
      query.append(DbConstants.POLICY_CONSENT_VERIFY_REQUIRED);
      query.append(",");
      query.append(DbConstants.POLICY_CASE_RELEASE_REQUIRED);
      query.append(",");
      query.append(DbConstants.POLICY_ADDITIONAL_QUESTIONS_GRP);
      query.append(",");
      query.append(DbConstants.POLICY_ARDAIS_ACCT_KEY);
      query.append(",");
      query.append(DbConstants.POLICY_ALLOW_FOR_UNLINKED_YN);
      query.append(",");
      query.append(DbConstants.POLICY_POLICY_DATA_ENCODING);
      query.append(",");
      query.append(DbConstants.POLICY_CASE_REGISTRATION_FORM);
      query.append(",");
      query.append(DbConstants.POLICY_POLICY_DATA);
      query.append(") VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?) RETURNING ");
      query.append(DbConstants.POLICY_ID);
      query.append(" INTO ? }");

      cstmt = con.prepareCall(query.toString());

      cstmt.setString(1, policy.getPolicyName());
      cstmt.setBigDecimal(2, new BigDecimal(policy.getAllocationDenominator()));
      cstmt.setBigDecimal(3, new BigDecimal(policy.getAllocationNumerator()));
      cstmt.setString(4, policy.getAllocationFormatCid());
      cstmt.setBigDecimal(5, new BigDecimal(policy.getDefaultLogicalReposId()));
      if (ApiFunctions.isEmpty(policy.getRestrictedLogicalReposId())) {
        cstmt.setBigDecimal(6, null);
      }
      else {
        cstmt.setBigDecimal(6, new BigDecimal(policy.getRestrictedLogicalReposId()));
      }
      cstmt.setString(7, policy.getVerifyRequired());
      cstmt.setString(8, policy.getReleaseRequired());
      cstmt.setString(9, policy.getAdditionalQuestionsGrp());
      cstmt.setString(10, policy.getAccountId());
      cstmt.setString(11, policy.getAllowForUnlinkedCases());
      cstmt.setString(12, policy.getPolicyDataEncoding());
      cstmt.setString(13, policy.getCaseRegistrationFormId());
      policyClob = new TemporaryClob(con, policy.toXml());
      cstmt.setClob(14, policyClob.getSQLClob());

      cstmt.registerOutParameter(15, Types.VARCHAR);

      cstmt.execute();

      // Get the newly created policy id.
      policy.setPolicyId(cstmt.getString(15));

      //update form definition tags as appropriate
      addTagsForPolicy(policy);
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(policyClob, con);
      ApiFunctions.close(cstmt);
      ApiFunctions.close(con);
    }
  }

  /**
   * Update an existing policy to match the fields specified in the <code>policyData</code>
   * parameter. This assumes that the id field is the id of an existing policy and that all other
   * fields are valid, but it doesn't check any of this.
   * 
   * @param btxDetails The description of the policy to update.
   */
  private void policyUpdate(BtxDetailsMaintainPolicy btxDetails) {

    PolicyData policy = btxDetails.getPolicy();

    //get the existing version of the policy, for tag maintenance
    PolicyData oldPolicy = PolicyUtils.getPolicyData(btxDetails.getPolicy().getPolicyId());

    Connection con = null;
    PreparedStatement pstmt = null;
    TemporaryClob policyClob = null;
    try {
      con = ApiFunctions.getDbConnection();

      StringBuffer query = new StringBuffer(256);
      query.append("UPDATE ");
      query.append(DbConstants.TABLE_ARD_POLICY);
      query.append(" SET ");
      query.append(DbConstants.POLICY_NAME);
      query.append(" = ?, ");
      query.append(DbConstants.POLICY_ALLOCATION_DENOMINATOR);
      query.append(" = ?, ");
      query.append(DbConstants.POLICY_ALLOCATION_NUMERATOR);
      query.append(" = ?, ");
      query.append(DbConstants.POLICY_ALLOCATION_FORMAT_CID);
      query.append(" = ?, ");
      query.append(DbConstants.POLICY_DEFAULT_LOGICAL_REPOS_ID);
      query.append(" = ?, ");
      query.append(DbConstants.POLICY_RESTRICTED_LOGICAL_REPOS_ID);
      query.append(" = ?, ");
      query.append(DbConstants.POLICY_CONSENT_VERIFY_REQUIRED);
      query.append(" = ?, ");
      query.append(DbConstants.POLICY_CASE_RELEASE_REQUIRED);
      query.append(" = ?, ");
      query.append(DbConstants.POLICY_ADDITIONAL_QUESTIONS_GRP);
      query.append(" = ?, ");
      query.append(DbConstants.POLICY_ARDAIS_ACCT_KEY);
      query.append(" = ?, ");
      query.append(DbConstants.POLICY_ALLOW_FOR_UNLINKED_YN);
      query.append(" = ?, ");
      query.append(DbConstants.POLICY_POLICY_DATA_ENCODING);
      query.append(" = ?, ");
      query.append(DbConstants.POLICY_CASE_REGISTRATION_FORM);
      query.append(" = ?, ");
      query.append(DbConstants.POLICY_POLICY_DATA);
      query.append(" = ? WHERE ");
      query.append(DbConstants.POLICY_ID);
      query.append(" = ?");

      pstmt = con.prepareStatement(query.toString());

      pstmt.setString(1, policy.getPolicyName());
      pstmt.setBigDecimal(2, new BigDecimal(policy.getAllocationDenominator()));
      pstmt.setBigDecimal(3, new BigDecimal(policy.getAllocationNumerator()));
      pstmt.setString(4, policy.getAllocationFormatCid());
      pstmt.setBigDecimal(5, new BigDecimal(policy.getDefaultLogicalReposId()));
      if (ApiFunctions.isEmpty(policy.getRestrictedLogicalReposId())) {
        pstmt.setBigDecimal(6, null);
      }
      else {
        pstmt.setBigDecimal(6, new BigDecimal(policy.getRestrictedLogicalReposId()));
      }
      pstmt.setString(7, policy.getVerifyRequired());
      pstmt.setString(8, policy.getReleaseRequired());
      pstmt.setString(9, policy.getAdditionalQuestionsGrp());
      pstmt.setString(10, policy.getAccountId());
      pstmt.setString(11, policy.getAllowForUnlinkedCases());
      pstmt.setString(12, policy.getPolicyDataEncoding());
      pstmt.setString(13, policy.getCaseRegistrationFormId());
      policyClob = new TemporaryClob(con, policy.toXml());
      pstmt.setClob(14, policyClob.getSQLClob());

      pstmt.setBigDecimal(15, new BigDecimal(policy.getPolicyId()));

      pstmt.executeUpdate();

      //update form definition tags as appropriate
      removeTagsForPolicy(oldPolicy);
      addTagsForPolicy(policy);

    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(policyClob, con);
      ApiFunctions.close(pstmt);
      ApiFunctions.close(con);
    }
  }

  /**
   * Delete an existing policy. This assumes that policyId specified in the policy on the btxDetails
   * is the id of an existing policy and that the policy doesn't contain any relationships and can
   * be safely deleted, but it doesn't check any of this.
   * 
   * @param policyId The id of the policy to delete.
   */
  private void policyDelete(BtxDetailsMaintainPolicy btxDetails) {

    PolicyData policy = btxDetails.getPolicy();

    Connection con = null;
    PreparedStatement pstmt = null;
    try {
      con = ApiFunctions.getDbConnection();

      StringBuffer query = new StringBuffer(256);
      query.append("DELETE FROM ");
      query.append(DbConstants.TABLE_ARD_POLICY);
      query.append(" WHERE ");
      query.append(DbConstants.POLICY_ID);
      query.append(" = ?");

      pstmt = con.prepareStatement(query.toString());
      pstmt.setBigDecimal(1, new BigDecimal(policy.getPolicyId()));
      pstmt.executeUpdate();

      //update form definition tags as appropriate
      removeTagsForPolicy(policy);
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(pstmt);
      ApiFunctions.close(con);
    }
  }

  private void addTagsForPolicy(PolicyData policy) {
    //a map to store the forms to which we're adding tags. The same form may be
    //referenced by multiple sample types, so rather than make multiple calls for the
    //same form it is more efficient to make one call with all tags to be added.
    Map updatedForms = new HashMap();

    //handle the case form
    String caseRegistrationFormId = policy.getCaseRegistrationFormId();
    if (!ApiFunctions.isEmpty(caseRegistrationFormId)) {
      //cache this form if we haven't already
      FormDefinition formDef = (FormDefinition) updatedForms.get(caseRegistrationFormId);
      if (formDef == null) {
        formDef = new DataFormDefinition();
        formDef.setFormDefinitionId(caseRegistrationFormId);
        updatedForms.put(caseRegistrationFormId, formDef);
      }
      //add the tag that we wish to add to the form
      formDef.addTag(new Tag(TagTypes.REGISTRATION_FORM_CASE, policy.getPolicyId()));
    }

    //now handle the sample types
    Map sampleTypeMap = policy.getSampleTypeConfiguration().getSampleTypeMap();
    Iterator keyIterator = sampleTypeMap.keySet().iterator();
    while (keyIterator.hasNext()) {
      SampleType st = (SampleType) sampleTypeMap.get(keyIterator.next());
      String sampleRegistrationFormId = st.getRegistrationFormId();
      if (!ApiFunctions.isEmpty(sampleRegistrationFormId)) {
        //cache this form if we haven't already
        FormDefinition formDef = (FormDefinition) updatedForms.get(sampleRegistrationFormId);
        if (formDef == null) {
          formDef = new DataFormDefinition();
          formDef.setFormDefinitionId(sampleRegistrationFormId);
          updatedForms.put(sampleRegistrationFormId, formDef);
        }
        //add the tag to the form
        formDef.addTag(new Tag(TagTypes.REGISTRATION_FORM_SAMPLE, policy.getPolicyId() + ":"
            + st.getCui()));
      }
    }

    //now iterate over the updated forms, persisting the new tags
    Iterator formIterator = updatedForms.values().iterator();
    while (formIterator.hasNext()) {
      FormDefinition formDefinition = (FormDefinition) formIterator.next();
      FormDefinitionServiceResponse response = FormDefinitionService.SINGLETON
          .addFormDefinitionTags(formDefinition);
      if (!response.getErrors().empty()) {
        throw new ApiException("Unable to add policy-related tags to form "
            + formDefinition.getFormDefinitionId());
      }
    }
  }

  private void removeTagsForPolicy(PolicyData policy) {
    //a map to store the forms from which we're removing tags. The same form may be
    //referenced by multiple sample types, so rather than make multiple calls for the
    //same form it is more efficient to make one call with all tags to be removed.
    Map updatedForms = new HashMap();

    //handle the case form
    String caseRegistrationFormId = policy.getCaseRegistrationFormId();
    if (!ApiFunctions.isEmpty(caseRegistrationFormId)) {
      //cache this form if we haven't already
      FormDefinition formDef = (FormDefinition) updatedForms.get(caseRegistrationFormId);
      if (formDef == null) {
        formDef = new DataFormDefinition();
        formDef.setFormDefinitionId(caseRegistrationFormId);
        updatedForms.put(caseRegistrationFormId, formDef);
      }
      //add the tag that we wish to remove to the form
      formDef.addTag(new Tag(TagTypes.REGISTRATION_FORM_CASE, policy.getPolicyId()));
    }

    //now handle the sample types
    Map sampleTypeMap = policy.getSampleTypeConfiguration().getSampleTypeMap();
    Iterator keyIterator = sampleTypeMap.keySet().iterator();
    while (keyIterator.hasNext()) {
      SampleType st = (SampleType) sampleTypeMap.get(keyIterator.next());
      String sampleRegistrationFormId = st.getRegistrationFormId();
      if (!ApiFunctions.isEmpty(sampleRegistrationFormId)) {
        //cache this form if we haven't already
        FormDefinition formDef = (FormDefinition) updatedForms.get(sampleRegistrationFormId);
        if (formDef == null) {
          formDef = new DataFormDefinition();
          formDef.setFormDefinitionId(sampleRegistrationFormId);
          updatedForms.put(sampleRegistrationFormId, formDef);
        }
        //add the tag to the form
        formDef.addTag(new Tag(TagTypes.REGISTRATION_FORM_SAMPLE, policy.getPolicyId() + ":"
            + st.getCui()));
      }
    }

    //now iterate over the updated forms, removing the specified tags
    Iterator formIterator = updatedForms.values().iterator();
    while (formIterator.hasNext()) {
      FormDefinition formDefinition = (FormDefinition) formIterator.next();
      FormDefinitionServiceResponse response = FormDefinitionService.SINGLETON
          .deleteFormDefinitionTags(formDefinition);
      if (!response.getErrors().empty()) {
        throw new ApiException("Unable to remove policy-related tags from form "
            + formDefinition.getFormDefinitionId());
      }
    }
  }

  /**
   * Do BTX transaction validation for the performMaintainRoleStart performer method.
   */
  private BTXDetails validatePerformMaintainRoleStart(BtxDetailsMaintainRole btxDetails)
      throws Exception {

    // We don't do much validation here, since this is the action that we go to to report
    // validation errors. Because of that, we can expect that there will sometimes be
    // odd data in the fields we get here. We do need some things to be correct, though.
    // For example, the operation field needs to have a valid operation name in it, and for
    // update operations we need a valid role id so that we can look up its data.
    
    //make sure the user has the Maintain Role privilege
    SecurityInfo securityInfo = btxDetails.getLoggedInUserSecurityInfo();
    boolean hasPriv = securityInfo.isHasPrivilege(SecurityInfo.PRIV_ORM_ROLE_MANAGEMENT);
    if (!hasPriv) {
      btxDetails.addActionError(new BtxActionError("error.noPrivilege", "maintain roles"));
      return btxDetails;
    }

    //make sure the operation has been specified and is valid
    String operation = btxDetails.getOperation();
    if (ApiFunctions.isEmpty(operation)) {
      btxDetails.addActionError(new BtxActionError("orm.error.role.requiredOperation"));
    }
    else if (!(Constants.OPERATION_CREATE.equals(operation)
        || Constants.OPERATION_UPDATE.equals(operation) || Constants.OPERATION_DELETE
        .equals(operation))) {
      btxDetails.addActionError(new BtxActionError(
          "orm.error.role.operationCreateOrUpdateOrDelete"));
    }

    // Decide which tests to do based on the operation.
    boolean testProhibitRoleId = false;
    boolean testInvalidRoleId = false;
    if (Constants.OPERATION_CREATE.equals(operation)) {
      testProhibitRoleId = true;
    }
    else if (Constants.OPERATION_UPDATE.equals(operation) ||
             Constants.OPERATION_DELETE.equals(operation)) {
      testInvalidRoleId = true;
    }

    // Now perform the tests...
    String roleId = btxDetails.getRole().getId();

    if (testProhibitRoleId && !ApiFunctions.isEmpty(roleId)) {
      btxDetails.addActionError(new BtxActionError("orm.error.role.prohibitId"));
    }

    if (testInvalidRoleId) {
      if (ApiFunctions.isEmpty(roleId)) {
        btxDetails.addActionError(new BtxActionError("orm.error.role.requiredId"));
      }
      else {
        RoleDto roleDto = RoleUtils.getRoleData(roleId, false, RoleDto.class);
        if (roleDto == null) {
          btxDetails.addActionError(new BtxActionError("orm.error.role.invalidId"));
        }
        else {
          //if the user is not in the system owner role, the role must belong to their account
          if (!securityInfo.isInRoleSystemOwner() && 
              !securityInfo.getAccount().equalsIgnoreCase(roleDto.getAccountId())) {
            btxDetails.addActionError(new BtxActionError("orm.error.role.invalidId"));
          }
        }
      }
    }
    
    return btxDetails;
  }

  /**
   * This is the main BTX entry point for performing the transaction that starts the process of
   * creating, editing, or deleting a role.
   */
  private BTXDetails performMaintainRoleStart(BtxDetailsMaintainRoleStart btxDetails)
      throws Exception {
    SecurityInfo securityInfo = btxDetails.getLoggedInUserSecurityInfo();

    //populate the list of existing roles
    if (securityInfo.isInRoleSystemOwner()) {
      btxDetails.setRoles(RoleUtils.getAllRoles());
    }
    else {
      String accountId = securityInfo.getAccount();
      btxDetails.getRole().setAccountId(accountId);
      btxDetails.setRoles(RoleUtils.getRolesByAccountId(accountId));
    }
    
    //populate the map of privileges
    try {
      OrmUserManagementHome oumHome = (OrmUserManagementHome) EjbHomes.getHome(OrmUserManagementHome.class);
      OrmUserManagement userPrivileges = oumHome.create();
      Map allPrivileges = userPrivileges.getAllPrivileges();
      //If the user is not in a system owner role, alter the privileges to contain only those 
      //privileges authorized for the user's account. This is done so the user only sees privileges 
      //they are authorized to view.
      if (!securityInfo.isInRoleSystemOwner()) {
        String account = securityInfo.getAccount();
        AccountSrchSBHome home = (AccountSrchSBHome) EjbHomes.getHome(AccountSrchSBHome.class);
        AccountSrchSB accountPrivileges = home.create();
        List authorizedPrivileges = accountPrivileges.getPrivilegesAssignedToAccount(account);
        removeUnauthorizedEntities(authorizedPrivileges, allPrivileges);
      }
      btxDetails.setAllPrivileges(allPrivileges);
      btxDetails.setPrivilegeFilter(com.ardais.bigr.orm.helpers.FormLogic.ALL);
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    
    //populate the map of users
    Map userMap = new HashMap();
    List users = findUsers(securityInfo, null);
    Iterator iterator = users.iterator();
    while (iterator.hasNext()) {
      UserDto user = (UserDto)iterator.next();
      List userList = (List)userMap.get(user.getAccountId());
      if (userList == null) {
        userList = new ArrayList();
        userMap.put(user.getAccountId(), userList);
      }
      userList.add(user);
    }
    btxDetails.setAllUsers(userMap);

    //if the Role on the btxDetails has any users or privileges defined, they will just have
    //ids populated (for example, if we are returning here due to a validation error on a
    //save or update action).  Populate any additional information required by the front end 
    //(privilege description, user first and last name)
    if (!ApiFunctions.isEmpty(btxDetails.getRole().getPrivileges())) {
      Iterator privIterator = btxDetails.getRole().getPrivileges().iterator();
      List privIds = new ArrayList();
      while (privIterator.hasNext()) {
        PrivilegeDto priv = (PrivilegeDto)privIterator.next();
        privIds.add(priv.getId());
      }
      try {
        OrmUserManagementHome oumHome = (OrmUserManagementHome) EjbHomes.getHome(OrmUserManagementHome.class);
        OrmUserManagement privilegeFinder = oumHome.create();
        btxDetails.getRole().setPrivileges(privilegeFinder.getPrivilegesById(privIds));
      }
      catch (Exception e) {
        ApiFunctions.throwAsRuntimeException(e);
      }
    }
    if (!ApiFunctions.isEmpty(btxDetails.getRole().getUsers())) {
      List knownUsers = findUsers(securityInfo, null);
      Iterator userIterator = btxDetails.getRole().getUsers().iterator();
      while (userIterator.hasNext()) {
        UserDto user = (UserDto)userIterator.next();
        Iterator knownUserIterator = knownUsers.iterator();
        boolean found = false;
        while (!found && knownUserIterator.hasNext()) {
          UserDto knownUser = (UserDto) knownUserIterator.next();
          if (knownUser.getUserId().equalsIgnoreCase(user.getUserId())) {
            user.populate(knownUser);
            found = true;
          }
        }
      }
    }
    
    String operation = btxDetails.getOperation();
    if (Constants.OPERATION_UPDATE.equals(operation)) {
      if (btxDetails.isPopulateRoleOutputFields()) {
        String roleId = btxDetails.getRole().getId();
        RoleDto role = RoleUtils.getRoleData(roleId);
        //if the user is not a system owner, remove any privileges that are not in the list of
        //privileges that can be managed by the account to which the user belongs
        if (!securityInfo.isInRoleSystemOwner()) {
          try {
              String account = securityInfo.getAccount();
              AccountSrchSBHome home = (AccountSrchSBHome) EjbHomes.getHome(AccountSrchSBHome.class);
              AccountSrchSB accountPrivileges = home.create();
              List authorizedPrivileges = accountPrivileges.getPrivilegesAssignedToAccount(account);
              removeUnauthorizedEntities(authorizedPrivileges, role.getPrivileges());
          }
          catch (Exception e) {
            ApiFunctions.throwAsRuntimeException(e);
          }
        }
        btxDetails.setRole(role);
      }
    }

    btxDetails.setActionForwardTXCompleted("success");
    return btxDetails;
  }

  /**
   * Do BTX transaction validation for the performMaintainRoleSave performer method.
   */
  private BTXDetails validatePerformMaintainRoleSave(BtxDetailsMaintainRole btxDetails)
      throws Exception {

    boolean testRequiredRoleName = false;
    boolean testValidAccount = false;
    boolean testUnmodifiedAccount = false;
    boolean testValidPrivileges = false;
    boolean testValidUsers = false;
    boolean testUnusedOnDelete = false;
    
    //perform the basic validation as was done at the start of this process.  If any errors
    //occur doing those basic checks, don't bother with further validation
    validatePerformMaintainRoleStart(btxDetails);
    if (!btxDetails.getActionErrors().empty()) {
      return btxDetails;
    }

    SecurityInfo securityInfo = btxDetails.getLoggedInUserSecurityInfo();
    String operation = btxDetails.getOperation();

    // Decide which tests to do based on the operation.
    if (Constants.OPERATION_CREATE.equals(operation)) {
      testRequiredRoleName = true;
      testValidAccount = true;
      testValidPrivileges = true;
      testValidUsers = true;
    }
    else if (Constants.OPERATION_UPDATE.equals(operation)) {
      testRequiredRoleName = true;
      testUnmodifiedAccount = true;
      testValidPrivileges = true;
      testValidUsers = true;
    }
    else if (Constants.OPERATION_DELETE.equals(operation)) {
      testUnusedOnDelete = true;
    }

    if (testUnusedOnDelete) {
      checkIfSafeToDelete(btxDetails);
    }

    RoleDto role = btxDetails.getRole();
    String roleId = role.getId();
    boolean accountIsValid = true;
    if (testValidAccount) {
      String accountId = role.getAccountId();
      if (ApiFunctions.isEmpty(accountId)) {
        btxDetails.addActionError(new BtxActionError("orm.error.policy.requiredAccountId"));
        accountIsValid = false;
      }
      else {
        try {
          ArdaisaccountKey accountKey = new ArdaisaccountKey(accountId);
          ArdaisaccountAccessBean accountAB = new ArdaisaccountAccessBean(accountKey);
          //If the user is not in the system owner role, the account of the role must match
          //their account.
          if (!securityInfo.isInRoleSystemOwner()) {
            if (!accountId.equals(securityInfo.getAccount())) {
              btxDetails.addActionError(new BtxActionError("orm.error.role.invalidAccountForUser"));
              accountIsValid = false;
            }
          }
        }
        catch (ObjectNotFoundException e) {
          // Account doesn't exist.
          btxDetails.addActionError(new BtxActionError("orm.error.role.accountDoesNotExist"));
          accountIsValid = false;
        }
      }
    }
    
    if (testUnmodifiedAccount) {
      String accountId = role.getAccountId();
      String previousAccountId = RoleUtils.getRoleData(roleId).getAccountId();
      if (!previousAccountId.equalsIgnoreCase(accountId)) {
        btxDetails.addActionError(new BtxActionError("orm.error.role.accountModificationDisallowed"));
        accountIsValid = false;
      }
    }
    
    //set the boolean to indicate if the specified account is valid
    accountIsValid = (testValidAccount || testUnmodifiedAccount) && accountIsValid;

    // Run role name tests.
    if (testRequiredRoleName) {
      String roleName = role.getName();
      if (ApiFunctions.isEmpty(roleName)) {
        btxDetails.addActionError(new BtxActionError("orm.error.role.requiredName"));
      }
      else {
        String accountId = role.getAccountId();
        if (accountIsValid && isExistingRoleWithName(roleName, accountId, roleId)) {
          String accountDescriptor = null;
          if (securityInfo.isInRoleSystemOwner()) {
            accountDescriptor = "the specified";
          }
          else {
            accountDescriptor = "your";
          }
          btxDetails.addActionError(new BtxActionError("orm.error.role.duplicateRoleName", accountDescriptor));
        }

        if (roleName.length() > MAX_ROLE_NAME_LENGTH) {
          btxDetails.addActionError(new BtxActionError("error.lengthExceeded", "Name",
              MAX_ROLE_NAME_LENGTH + " characters"));
        }
      }
    }
    
    //run valid privileges test
    if (testValidPrivileges) {
      List privileges = ApiFunctions.safeList(btxDetails.getRole().getPrivileges());
      //make sure no privilege is duplicated
      Set privilegeSet = new HashSet();
      Iterator privilegeIterator = privileges.iterator();
      while (privilegeIterator.hasNext()) {
        if (!privilegeSet.add(((PrivilegeDto)privilegeIterator.next()).getId())) {
          btxDetails.addActionError(new BtxActionError("orm.error.role.entityCanBeAssignedToRoleOnce", "privilege"));
        }
      }
      //make sure all privileges are valid
        try {
          OrmUserManagementHome oumHome = (OrmUserManagementHome) EjbHomes.getHome(OrmUserManagementHome.class);
          OrmUserManagement privilegeFinder = oumHome.create();
          List knownPrivileges = (List) privilegeFinder.getAllPrivileges().get(
              com.ardais.bigr.orm.helpers.FormLogic.ALL);
          List unknownPrivilegeIds = new ArrayList();
          privilegeIterator = privileges.iterator();
          while (privilegeIterator.hasNext()) {
            String privId = ((PrivilegeDto)privilegeIterator.next()).getId();
            Iterator knownPrivilegeIterator = knownPrivileges.iterator();
            boolean found = false;
            PrivilegeDto knownPrivilege = null;
            while (!found && knownPrivilegeIterator.hasNext()) {
              knownPrivilege = (PrivilegeDto) knownPrivilegeIterator.next();
              found = privId.equalsIgnoreCase(knownPrivilege.getId());
            }
            if (!found) {
              unknownPrivilegeIds.add(privId);
            }
          }
          if (!ApiFunctions.isEmpty(unknownPrivilegeIds)) {
            btxDetails.addActionError(new BtxActionError("orm.error.users.unknownPrivilegesOrGroups",
                "privileges", ApiFunctions.toCommaSeparatedList(ApiFunctions
                    .toStringArray(unknownPrivilegeIds))));
          }
        }
        catch (Exception e) {
          ApiFunctions.throwAsRuntimeException(e);
        }
      //make sure user is authorized to grant the privileges
      if (!securityInfo.isInRoleSystemOwner()) {
        String accountId = securityInfo.getAccount();
        try {
          AccountSrchSBHome home = (AccountSrchSBHome) EjbHomes.getHome(AccountSrchSBHome.class);
          AccountSrchSB accountPrivileges = home.create();
          List authorizedPrivileges = accountPrivileges.getPrivilegesAssignedToAccount(accountId);
          List unauthorizedPrivilegeIds = new ArrayList();
          privilegeIterator = privileges.iterator();
          while (privilegeIterator.hasNext()) {
            String privId = ((PrivilegeDto)privilegeIterator.next()).getId();
            Iterator authorizedPrivilegeIterator = authorizedPrivileges.iterator();
            boolean found = false;
            PrivilegeDto authorizedPrivilege = null;
            while (!found && authorizedPrivilegeIterator.hasNext()) {
              authorizedPrivilege = (PrivilegeDto) authorizedPrivilegeIterator.next();
              found = privId.equalsIgnoreCase(authorizedPrivilege.getId());
            }
            if (!found) {
              unauthorizedPrivilegeIds.add(privId);
            }
          }
          if (!ApiFunctions.isEmpty(unauthorizedPrivilegeIds)) {
            OrmUserManagementHome oumHome = (OrmUserManagementHome) EjbHomes.getHome(OrmUserManagementHome.class);
            OrmUserManagement privilegeFinder = oumHome.create();
            List unauthorizedPrivileges = privilegeFinder.getPrivilegesById(unauthorizedPrivilegeIds);
            Iterator iterator = unauthorizedPrivileges.iterator();
            boolean addComma = false;
            StringBuffer privilegeNames = new StringBuffer(100);
            while (iterator.hasNext()) {
              if (addComma) {
                privilegeNames.append(", ");
              }
              privilegeNames.append(((PrivilegeDto) iterator.next()).getDescription());
            }
            btxDetails.addActionError(new BtxActionError(
                "orm.error.users.unauthorizedPrivilegesOrGroups", "privileges", privilegeNames
                    .toString()));
          }
        }
        catch (Exception e) {
          ApiFunctions.throwAsRuntimeException(e);
        }
      }
    }
    
    //run valid user test
    if (testValidUsers) {
      List users = ApiFunctions.safeList(btxDetails.getRole().getUsers());
      //make sure no user is duplicated
      Set userSet = new HashSet();
      Iterator userIterator = users.iterator();
      while (userIterator.hasNext()) {
        if (!userSet.add(((UserDto)userIterator.next()).getUserId())) {
          btxDetails.addActionError(new BtxActionError("orm.error.role.entityCanBeAssignedToRoleOnce", "user"));
        }
      }
      //make sure all users are valid
      List knownUsers = findUsers(securityInfo, null);
      List unknownUserIds = new ArrayList();
      userIterator = users.iterator();
      while (userIterator.hasNext()) {
        String userId = ((UserDto)userIterator.next()).getUserId();
        Iterator knownUserIterator = knownUsers.iterator();
        boolean found = false;
        UserDto knownUser = null;
        while (!found && knownUserIterator.hasNext()) {
          knownUser = (UserDto) knownUserIterator.next();
          found = userId.equalsIgnoreCase(knownUser.getUserId());
        }
        if (!found) {
          unknownUserIds.add(userId);
        }
        else {
          if (accountIsValid && !role.getAccountId().equalsIgnoreCase(knownUser.getAccountId())) {
            btxDetails.addActionError(new BtxActionError("orm.error.role.usersMustBelongToRoleAccount"));
          }
        }
      }
      if (!ApiFunctions.isEmpty(unknownUserIds)) {
        btxDetails.addActionError(new BtxActionError("orm.error.users.unknownPrivilegesOrGroups",
            "users", ApiFunctions.toCommaSeparatedList(ApiFunctions
                .toStringArray(unknownUserIds))));
      }
    }

    return btxDetails;
  }

  /**
   * This is the main BTX entry point for performing the transaction that creates, edits, or deletes
   * a role.
   */
  private BTXDetails performMaintainRoleSave(BtxDetailsMaintainRole btxDetails)
      throws Exception {

    // Get the operation being performed.
    String operation = btxDetails.getOperation();
    RoleDto role = btxDetails.getRole();

    // The action we take here depends on the operation.
    if (Constants.OPERATION_CREATE.equals(operation)) {
      roleCreate(btxDetails);
      btxDetails.addActionMessage(new BtxActionMessage("orm.error.role.confirmCreate",
          Escaper.htmlEscape(role.getName())));
    }
    else if (Constants.OPERATION_UPDATE.equals(operation)) {
      roleUpdate(btxDetails);
      btxDetails.addActionMessage(new BtxActionMessage("orm.error.role.confirmUpdate",
          Escaper.htmlEscape(role.getName())));
    }
    else if (Constants.OPERATION_DELETE.equals(operation)) {
      // Look up the role we're going to delete so that we can set output parameters.
      RoleDto outputRoleData = RoleUtils.getRoleData(role.getId());
      // Populate the detail with data object.
      BigrBeanUtilsBean.SINGLETON.copyProperties(btxDetails.getRole(), outputRoleData);
      // Now delete the role.
      roleDelete(btxDetails);
      btxDetails.addActionMessage(new BtxActionMessage("orm.error.role.confirmDelete",
          Escaper.htmlEscape(role.getName())));
    }

    btxDetails.setActionForwardTXCompleted("success");
    return btxDetails;
  }

  //private method used to get an id for a new role
  private String getIdForNewRole() {
    String roleId = null;
    Connection connection = null;
    CallableStatement cstmt = null;
    ResultSet results = null;
    try {
      connection = ApiFunctions.getDbConnection();
      cstmt = connection.prepareCall("begin BIGR_GET_ROLE_ID(?,?); end;");
      cstmt.registerOutParameter(1, Types.VARCHAR);
      cstmt.registerOutParameter(2, Types.VARCHAR);
      cstmt.executeQuery();
      Object obj = cstmt.getObject(2);
      if (obj != null) {
        String emsg = obj.toString();
        //throw an exception
        throw new ApiException(
          "BIGR_GET_ROLE_ID failed at BtxPerformerOrmOperations.getIdForNewRole(): "
            + emsg);
      }
      else {
        roleId = cstmt.getString(1);
      }
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(connection, cstmt, results);
    }
    return roleId;
  }

  /**
   * Create a new role using the fields specified in the <code>roleDto</code> parameter. This
   * assumes that the id field is null and that all other fields are valid, but it doesn't check any
   * of this. As a side effect, it sets the id field on the <code>roleDto</code> parameter to
   * the id assigned to the new role.
   * 
   * @param btxDetails The btxDetails containg the role to create.
   */
  private void roleCreate(BtxDetailsMaintainRole btxDetails) {
    
    RoleDto role = btxDetails.getRole();
    //get an id for the new role and set it as an output parameter
    role.setId(getIdForNewRole());

    //update the btxDetails output parameters (added users, added privileges, removed users, and
    //removed privileges).  Because this is a create operation there will only be adds, so set
    //the removed users and privs to be empty lists
    btxDetails.setRemovedPrivileges(new ArrayList());
    btxDetails.setRemovedUsers(new ArrayList());
    
    //set the privileges that have been added on the btxDetails
    if (!ApiFunctions.isEmpty(btxDetails.getRole().getPrivileges())) {
      Iterator privIterator = btxDetails.getRole().getPrivileges().iterator();
      List privIds = new ArrayList();
      while (privIterator.hasNext()) {
        PrivilegeDto priv = (PrivilegeDto)privIterator.next();
        privIds.add(priv.getId());
      }
      try {
        OrmUserManagementHome oumHome = (OrmUserManagementHome) EjbHomes.getHome(OrmUserManagementHome.class);
        OrmUserManagement privilegeFinder = oumHome.create();
        btxDetails.setAddedPrivileges(privilegeFinder.getPrivilegesById(privIds));
      }
      catch (Exception e) {
        ApiFunctions.throwAsRuntimeException(e);
      }
    }
    else {
      btxDetails.setAddedPrivileges(new ArrayList());
    }
    
    //set the users that have been added on the btxDetails
    List addedUsers = new ArrayList();
    if (!ApiFunctions.isEmpty(btxDetails.getRole().getUsers())) {
      List knownUsers = findUsers(btxDetails.getLoggedInUserSecurityInfo(), null);
      Iterator userIterator = btxDetails.getRole().getUsers().iterator();
      while (userIterator.hasNext()) {
        UserDto user = (UserDto)userIterator.next();
        Iterator knownUserIterator = knownUsers.iterator();
        boolean found = false;
        while (!found && knownUserIterator.hasNext()) {
          UserDto knownUser = (UserDto) knownUserIterator.next();
          if (knownUser.getUserId().equalsIgnoreCase(user.getUserId())) {
            user.populate(knownUser);
            addedUsers.add(user);
            found = true;
          }
        }
      }
    }
    btxDetails.setAddedUsers(addedUsers);
    
    //now actually create the role and associated users/privs
    Connection con = null;
    PreparedStatement pstmt = null;
    try {
      con = ApiFunctions.getDbConnection();

      //create the role itself
      StringBuffer query = new StringBuffer(256);
      query.append("INSERT INTO ");
      query.append(DbConstants.TABLE_BIGR_ROLE);
      query.append(" (");
      query.append(DbConstants.BIGR_ROLE_ID);
      query.append(", ");
      query.append(DbConstants.BIGR_ROLE_NAME);
      query.append(", ");
      query.append(DbConstants.BIGR_ROLE_ACCOUNT_ID);
      query.append(") VALUES (?, ?, ?)");
      pstmt = con.prepareStatement(query.toString());
      pstmt.setString(1, role.getId());
      pstmt.setString(2, role.getName());
      pstmt.setString(3, role.getAccountId());
      pstmt.executeUpdate();
      ApiFunctions.close(pstmt);
      
      //associate the specified users with the role
      List roleIds = new ArrayList();
      roleIds.add(role.getId());
      Iterator userIterator = btxDetails.getAddedUsers().iterator();
      while (userIterator.hasNext()) {
        UserDto user = (UserDto)userIterator.next();
        addRolesToUser(user.getUserId(), roleIds);
      }
      
      //associate the specified privileges with the role
      addPrivilegesToRole(role.getId(), btxDetails.getAddedPrivileges());

    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(pstmt);
      ApiFunctions.close(con);
    }
  }

  /**
   * Update an existing role to match the fields specified in the <code>roleDto</code>
   * parameter. This assumes that the id field is the id of an existing role and that all other
   * fields are valid, but it doesn't check any of this.
   * 
   * @param btxDetails The btxDetails containg the role to update.
   */
  private void roleUpdate(BtxDetailsMaintainRole btxDetails) {
    
    SecurityInfo securityInfo = btxDetails.getLoggedInUserSecurityInfo();
    RoleDto updatedRole = btxDetails.getRole();
    //get the existing version of the role, for user/priv maintenance
    RoleDto oldRole = RoleUtils.getRoleData(btxDetails.getRole().getId());

    //update the btxDetails output parameters (added users, added privileges, removed users, and
    //removed privileges).
    // We initialize removedPrivileges to be all of the privileges to which the role currently
    // has access, and remove any ones that they *still* should have access to below. So it ends
    // up being just the list of privileges to which they used to have access but to which they
    // no longer have access after the change we're currently processing. Note that if the user
    // is not in a system owner role, we need to remove any privileges to which the user is not
    // authorized to assign/revoke just as we did when starting this transaction. Otherwise,
    // privileges that the user is not authorized to assign/revoke would be revoked.
    List removedPrivileges = oldRole.getPrivileges();
    if (!securityInfo.isInRoleSystemOwner()) {
      try {
        String account = securityInfo.getAccount();
        AccountSrchSBHome home = (AccountSrchSBHome) EjbHomes.getHome(AccountSrchSBHome.class);
        AccountSrchSB accountPrivileges = home.create();
        List authorizedPrivileges = accountPrivileges.getPrivilegesAssignedToAccount(account);
        removeUnauthorizedEntities(authorizedPrivileges, removedPrivileges);
      }
      catch (Exception e) {
        ApiFunctions.throwAsRuntimeException(e);
      }
    }
    //determine what privileges have been added and/or removed
    List addedPrivilegeIds = new ArrayList();
    List privileges = ApiFunctions.safeList(btxDetails.getRole().getPrivileges());
    Iterator privilegeIterator = privileges.iterator();
    while (privilegeIterator.hasNext()) {
      String privId = ((PrivilegeDto)privilegeIterator.next()).getId();
      Iterator removedPrivilegeIterator = removedPrivileges.iterator();
      boolean found = false;
      while (removedPrivilegeIterator.hasNext() && !found) {
        PrivilegeDto removedPrivilege = (PrivilegeDto) removedPrivilegeIterator.next();
        if (removedPrivilege.getId().equalsIgnoreCase(privId)) {
          found = true;
          removedPrivileges.remove(removedPrivilege);
        }
      }
      if (!found) {
        addedPrivilegeIds.add(privId);
      }
    }
    
    //set the privileges that have been removed and added on the btxDetails
    btxDetails.setRemovedPrivileges(removedPrivileges);
    try {
      OrmUserManagementHome oumHome = (OrmUserManagementHome) EjbHomes.getHome(OrmUserManagementHome.class);
      OrmUserManagement privilegeFinder = oumHome.create();
      btxDetails.setAddedPrivileges(privilegeFinder.getPrivilegesById(addedPrivilegeIds));
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
    }

    // We initialize removedUsers to be all of the users currently in the role, and remove any
    // users that *still* should be in the role below. So it ends up being just the list of users 
    // who have been removed from the role.
    List removedUsers = oldRole.getUsers();
    //determine what users have been added and/or removed
    List addedUserIds = new ArrayList();
    List users = ApiFunctions.safeList(btxDetails.getRole().getUsers());
    Iterator userIterator = users.iterator();
    while (userIterator.hasNext()) {
      String userId = ((UserDto)userIterator.next()).getUserId();
      Iterator removedUserIterator = removedUsers.iterator();
      boolean found = false;
      while (removedUserIterator.hasNext() && !found) {
        UserDto removedUser = (UserDto) removedUserIterator.next();
        if (removedUser.getUserId().equalsIgnoreCase(userId)) {
          found = true;
          removedUsers.remove(removedUser);
        }
      }
      if (!found) {
        addedUserIds.add(userId);
      }
    }
    
    //set the users that have been removed and added on the btxDetails
    btxDetails.setRemovedUsers(removedUsers);
    List knownUsers = findUsers(btxDetails.getLoggedInUserSecurityInfo(), null);
    List addedUsers = new ArrayList();
    Iterator addedUserIdIterator = addedUserIds.iterator();
    while (addedUserIdIterator.hasNext()) {
      String userId = (String)addedUserIdIterator.next();
      Iterator iterator = knownUsers.iterator();
      UserDto user = null;
      boolean found = false;
      while (!found && iterator.hasNext()) {
        user = (UserDto) iterator.next();
        found = userId.equalsIgnoreCase(user.getUserId());
      }
      if (found) {
        addedUsers.add(user);
      }
    }
    btxDetails.setAddedUsers(addedUsers);

    Connection con = null;
    PreparedStatement pstmt = null;
    try {
      con = ApiFunctions.getDbConnection();

      //update the role itself.  Note that only the name can be modified
      StringBuffer query = new StringBuffer(256);
      query.append("UPDATE ");
      query.append(DbConstants.TABLE_BIGR_ROLE);
      query.append(" SET ");
      query.append(DbConstants.BIGR_ROLE_NAME);
      query.append(" = ? WHERE ");
      query.append(DbConstants.BIGR_ROLE_ID);
      query.append(" = ?");
      pstmt = con.prepareStatement(query.toString());
      pstmt.setString(1, updatedRole.getName());
      pstmt.setString(2, updatedRole.getId());
      pstmt.executeUpdate();
      ApiFunctions.close(pstmt);
      
      //associate the added users with the role
      List roleIds = new ArrayList();
      roleIds.add(updatedRole.getId());
      userIterator = btxDetails.getAddedUsers().iterator();
      while (userIterator.hasNext()) {
        UserDto user = (UserDto)userIterator.next();
        addRolesToUser(user.getUserId(), roleIds);
      }
      
      //associate the specified privileges with the role
      addPrivilegesToRole(updatedRole.getId(), btxDetails.getAddedPrivileges());
      
      //delete the removed users from the role
      List roles = new ArrayList();
      roles.add(updatedRole);
      userIterator = btxDetails.getRemovedUsers().iterator();
      while (userIterator.hasNext()) {
        UserDto user = (UserDto)userIterator.next();
        removeRolesFromUser(user.getUserId(), roles);
      }
      
      //delete the removed privileges from the role
      removePrivilegesFromRole(updatedRole.getId(), btxDetails.getRemovedPrivileges());
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(pstmt);
      ApiFunctions.close(con);
    }

  }

  /**
   * Delete an existing role. This assumes that id specified in the role on the btxDetails
   * is the id of an existing role, but it doesn't check any of this.
   * 
   * @param btxDetails The btxDetails containg the role to delete.
   */
  private void roleDelete(BtxDetailsMaintainRole btxDetails) {

    RoleDto role = btxDetails.getRole();
    //update the btxDetails output parameters (added users, added privileges, removed users, and
    //removed privileges).  Because this is a delete operation there will be no adds or deletes so 
    //set the added and removed users and privs to be empty lists
    btxDetails.setAddedPrivileges(new ArrayList());
    btxDetails.setAddedUsers(new ArrayList());
    btxDetails.setRemovedPrivileges(new ArrayList());
    btxDetails.setRemovedUsers(new ArrayList());

    Connection con = null;
    PreparedStatement pstmt = null;
    try {
      con = ApiFunctions.getDbConnection();

      //delete any users from the role.  Shouldn't be necessary since validation would have
      //prevented this but better safe than sorry
      StringBuffer query = new StringBuffer(256);
      query.append("DELETE FROM ");
      query.append(DbConstants.TABLE_BIGR_ROLE_USER);
      query.append(" WHERE ");
      query.append(DbConstants.BIGR_ROLE_USER_ROLE_ID);
      query.append(" = ?");
      pstmt = con.prepareStatement(query.toString());
      pstmt.setString(1, role.getId());
      pstmt.executeUpdate();
      ApiFunctions.close(pstmt);
      
      //delete any privileges from the role.  This might be necessary, if a non-system-owner user
      //is deleting the role.  They may not have the ability to assign/revoke all of the privileges
      //that may have been assigned to the role (by a system-owner user).
      query = new StringBuffer(256);
      query.append("DELETE FROM ");
      query.append(DbConstants.TABLE_BIGR_ROLE_PRIVILEGE);
      query.append(" WHERE ");
      query.append(DbConstants.BIGR_ROLE_PRIVILEGE_ROLE_ID);
      query.append(" = ?");
      pstmt = con.prepareStatement(query.toString());
      pstmt.setString(1, role.getId());
      pstmt.executeUpdate();
      ApiFunctions.close(pstmt);
      
      //delete the role itself
      query = new StringBuffer(256);
      query.append("DELETE FROM ");
      query.append(DbConstants.TABLE_BIGR_ROLE);
      query.append(" WHERE ");
      query.append(DbConstants.BIGR_ROLE_ID);
      query.append(" = ?");
      pstmt = con.prepareStatement(query.toString());
      pstmt.setString(1, role.getId());
      pstmt.executeUpdate();
      ApiFunctions.close(pstmt);
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(pstmt);
      ApiFunctions.close(con);
    }
  }

  /**
   * Do BTX transaction validation for the performManageRolesStart performer method.
   */
  private BTXDetails validatePerformManageRolesStart(BtxDetailsManageRoles btxDetails) {
    validateUser(btxDetails.getUserData(), btxDetails);
    return btxDetails;
  }

  //perform common setup for the manage role functionality
  private void performCommonRoleSetup(BtxDetailsManageRoles btxDetails) {
    String userId = btxDetails.getUserData().getUserId();
    String accountId = btxDetails.getUserData().getAccountId();
    List assignedRoles = RoleUtils.getRolesByUserId(userId);
    List allRoles = RoleUtils.getRolesByAccountId(accountId);
    btxDetails.setAssignedRoles(assignedRoles);
    btxDetails.setAllRoles(allRoles);
  }

  /**
   * Perform a BTX transaction: get the information necessary to start the transaction for managing
   * the roles for a user
   */
  private BtxDetailsManageRolesStart performManageRolesStart(BtxDetailsManageRolesStart btxDetails) {
    performCommonRoleSetup(btxDetails);
    btxDetails.setActionForwardTXCompleted("success");
    return btxDetails;
  }

  /**
   * Do BTX transaction validation for the performManageRoles performer method.
   */
  private BTXDetails validatePerformManageRoles(BtxDetailsManageRoles btxDetails) {
    validatePerformManageRolesStart(btxDetails);
    //if any errors occured in the initial validation, don't bother proceeding since most of
    //the additional validation relies on the information the initial validation is checking
    if (!btxDetails.getActionErrors().empty()) {
      return btxDetails;
    }
    //make sure every selected role is known
    List knownRoles = RoleUtils.getRolesByAccountId(btxDetails.getUserData().getAccountId());
    String[] selectedRoles = btxDetails.getSelectedRoles();
    List unknownRoleIds = new ArrayList();
    for (int i = 0; i < selectedRoles.length; i++) {
      String roleId = selectedRoles[i];
      Iterator iterator = knownRoles.iterator();
      boolean found = false;
      RoleDto role = null;
      while (!found && iterator.hasNext()) {
        role = (RoleDto) iterator.next();
        found = roleId.equalsIgnoreCase(role.getId());
      }
      if (!found) {
        unknownRoleIds.add(roleId);
      }
    }
    if (!ApiFunctions.isEmpty(unknownRoleIds)) {
      btxDetails.addActionError(new BtxActionError("orm.error.users.unknownPrivilegesOrGroups",
          "roles", ApiFunctions.toCommaSeparatedList(ApiFunctions
              .toStringArray(unknownRoleIds))));
    }
    //if any errors are going to be returned, set up the role info the UI will need
    if (!btxDetails.getActionErrors().empty()) {
      //set the assigned roles to be the selected roles, since those are the ones that should 
      //show up in the right hand side (so that the user is returned to the page in the same 
      //state they left it)
      btxDetails.setAssignedRoles(RoleUtils.getRolesByIds(ApiFunctions.safeToList(btxDetails
              .getSelectedRoles())));
      List allRoles = RoleUtils.getRolesByAccountId(btxDetails.getUserData().getAccountId());
      btxDetails.setAllRoles(allRoles);
    }
    return btxDetails;
  }

  /**
   * Perform a BTX transaction: manage the roles for a user
   */
  private BtxDetailsManageRoles performManageRoles(BtxDetailsManageRoles btxDetails) {

    String userId = btxDetails.getUserData().getUserId();
    String accountId = btxDetails.getUserData().getAccountId();
    String[] selectedRoles = btxDetails.getSelectedRoles();
    SecurityInfo securityInfo = btxDetails.getLoggedInUserSecurityInfo();
    // We initialize removedRoles to be all of the roles to which the user currently has access, 
    // and remove any ones that they *still* should have access to below. So it ends up being 
    // just the list of roles to which they used to have access but to which they no longer have 
    // access after the change we're currently processing.
    List removedRoles = RoleUtils.getRolesByUserId(userId);

    //determine what roles have been added and/or removed
    List addedRoleIds = new ArrayList();
    int count = selectedRoles.length;
    for (int i = 0; i < count; i++) {
      String id = selectedRoles[i];
      Iterator iterator = removedRoles.iterator();
      boolean found = false;
      while (iterator.hasNext() && !found) {
        RoleDto role = (RoleDto) iterator.next();
        if (role.getId().equalsIgnoreCase(id)) {
          found = true;
          removedRoles.remove(role);
        }
      }
      if (!found) {
        addedRoleIds.add(id);
      }
    }

    //Remove access to roles to which the user formerly had access but doesn't any more
    removeRolesFromUser(userId, removedRoles);

    //Add access to roles to which the user now has access
    addRolesToUser(userId, addedRoleIds);

    // Set the output/result properties for this transaction.
    btxDetails.setRemovedRoles(removedRoles);
    btxDetails.setAddedRoles(RoleUtils.getRolesByIds(addedRoleIds));
    
    //since we're going back to the manage roles page, reset the selected and all roles lists
    performCommonRoleSetup(btxDetails);

    //to prevent a "blank" history record, if there were no changes to process
    //(that is, the user's current roles haven't actually changed)
    //mark the transaction as incomplete but still forward to success. If there were
    //SQL statements executed then mark it completed.
    if (addedRoleIds.size() == 0 && removedRoles.size() == 0) {
      btxDetails.setActionForwardTXIncomplete("success");
    }
    else {
      btxDetails.setActionForwardTXCompleted("success");
    }

    return btxDetails;
  }

  /**
   * Add access rights to the specified roles for the specified user.
   * 
   * @param userId The user id.
   * @param addedRoles The list of role ids (Strings).
   */
  private void addRolesToUser(String userId, List addedRoleIds) {
    if (ApiFunctions.isEmpty(addedRoleIds) || ApiFunctions.isEmpty(userId)) {
      return;
    }

    StringBuffer query = new StringBuffer(256);
    query.append("INSERT INTO ");
    query.append(DbConstants.TABLE_BIGR_ROLE_USER);
    query.append(" (");
    query.append(DbConstants.BIGR_ROLE_USER_ROLE_ID);
    query.append(" ,");
    query.append(DbConstants.BIGR_ROLE_USER_USER_ID);
    query.append(") VALUES (?, ?)");

    Connection con = null;
    PreparedStatement pstmt = null;
    try {
      con = ApiFunctions.getDbConnection();
      pstmt = con.prepareStatement(query.toString());

      Iterator iter = addedRoleIds.iterator();
      while (iter.hasNext()) {
        String roleId = (String) iter.next();
        pstmt.setString(1, roleId);
        pstmt.setString(2, userId);
        pstmt.executeUpdate();
      }
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(pstmt);
      ApiFunctions.close(con);
    }
  }

  /**
   * Remove access rights to the specified roles for the specified user.
   * 
   * @param userId The user id.
   * @param removedRoles The list of roles (RoleDtos).
   */
  private void removeRolesFromUser(String userId, List removedRoles) {
    if (ApiFunctions.isEmpty(removedRoles) || ApiFunctions.isEmpty(userId)) {
      return;
    }

    StringBuffer query = new StringBuffer(256);
    query.append("DELETE FROM ");
    query.append(DbConstants.TABLE_BIGR_ROLE_USER);
    query.append(" WHERE ");
    query.append(DbConstants.BIGR_ROLE_USER_ROLE_ID);
    query.append(" = ? AND ");
    query.append(DbConstants.BIGR_ROLE_USER_USER_ID);
    query.append(" = ?");

    Connection con = null;
    PreparedStatement pstmt = null;
    try {
      con = ApiFunctions.getDbConnection();
      pstmt = con.prepareStatement(query.toString());

      Iterator iter = removedRoles.iterator();
      while (iter.hasNext()) {
        RoleDto role = (RoleDto) iter.next();
        String roleId = role.getId();

        pstmt.setString(1, roleId);
        pstmt.setString(2, userId);

        pstmt.executeUpdate();
      }
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(pstmt);
      ApiFunctions.close(con);
    }
  }
  
  /**
   * Add privileges to the specified role.
   * 
   * @param roleId The role id.
   * @param privileges The list of privilges (PrivilegeDtos).
   */
  private void addPrivilegesToRole(String roleId, Collection privileges) {
    if (ApiFunctions.isEmpty(roleId) || ApiFunctions.isEmpty(privileges)) {
      return;
    }
    
    StringBuffer query = new StringBuffer(256);
    query.append("INSERT INTO ");
    query.append(DbConstants.TABLE_BIGR_ROLE_PRIVILEGE);
    query.append(" (");
    query.append(DbConstants.BIGR_ROLE_PRIVILEGE_ROLE_ID);
    query.append(" ,");
    query.append(DbConstants.BIGR_ROLE_PRIVILEGE_PRIVILEGE_ID);
    query.append(") VALUES (?, ?)");

    Connection con = null;
    PreparedStatement pstmt = null;
    try {
      con = ApiFunctions.getDbConnection();
      pstmt = con.prepareStatement(query.toString());
      Iterator privilegeIterator = privileges.iterator();
      while (privilegeIterator.hasNext()) {
        PrivilegeDto privilege = (PrivilegeDto)privilegeIterator.next();
        pstmt.setString(1, roleId);
        pstmt.setString(2, privilege.getId());
        pstmt.executeUpdate();
      }
      ApiFunctions.close(pstmt);

    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(pstmt);
      ApiFunctions.close(con);
    }
    
  }
  
  /**
   * Remove privileges from the specified role.
   * 
   * @param roleId The role id.
   * @param privileges The list of privilges (PrivilegeDtos).
   */
  private void removePrivilegesFromRole(String roleId, Collection privileges) {
    if (ApiFunctions.isEmpty(roleId) || ApiFunctions.isEmpty(privileges)) {
      return;
    }
    
    StringBuffer query = new StringBuffer(256);
    query.append("DELETE FROM ");
    query.append(DbConstants.TABLE_BIGR_ROLE_PRIVILEGE);
    query.append(" WHERE ");
    query.append(DbConstants.BIGR_ROLE_PRIVILEGE_ROLE_ID);
    query.append(" = ? AND ");
    query.append(DbConstants.BIGR_ROLE_PRIVILEGE_PRIVILEGE_ID);
    query.append(" = ?");

    Connection con = null;
    PreparedStatement pstmt = null;
    try {
      con = ApiFunctions.getDbConnection();
      pstmt = con.prepareStatement(query.toString());
      Iterator privilegeIterator = privileges.iterator();
      while (privilegeIterator.hasNext()) {
        PrivilegeDto privilege = (PrivilegeDto)privilegeIterator.next();
        pstmt.setString(1, roleId);
        pstmt.setString(2, privilege.getId());
        pstmt.executeUpdate();
      }
      ApiFunctions.close(pstmt);

    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(pstmt);
      ApiFunctions.close(con);
    }
    
  }
  
  private void removeTagsForAccount(AccountDto account) {
    String donorRegistrationFormId = account.getDonorRegistrationFormId();
    if (!ApiFunctions.isEmpty(donorRegistrationFormId)) {
      FormDefinition formDef = new DataFormDefinition();
      formDef.setFormDefinitionId(donorRegistrationFormId);
      //add the tag that we wish to remove to the form
      formDef.addTag(new Tag(TagTypes.REGISTRATION_FORM_DONOR, account.getId()));
      FormDefinitionServiceResponse response = FormDefinitionService.SINGLETON
          .deleteFormDefinitionTags(formDef);
      if (!response.getErrors().empty()) {
        throw new ApiException("Unable to remove account-related tags from form "
            + formDef.getFormDefinitionId());
      }
    }
  }

  private void addTagsForAccount(AccountDto account) {
    String donorRegistrationFormId = account.getDonorRegistrationFormId();
    if (!ApiFunctions.isEmpty(donorRegistrationFormId)) {
      FormDefinition formDef = new DataFormDefinition();
      formDef.setFormDefinitionId(donorRegistrationFormId);
      //add the tag that we wish to add to the form
      formDef.addTag(new Tag(TagTypes.REGISTRATION_FORM_DONOR, account.getId()));
      FormDefinitionServiceResponse response = FormDefinitionService.SINGLETON
          .addFormDefinitionTags(formDef);
      if (!response.getErrors().empty()) {
        throw new ApiException("Unable to add account-related tags to form "
            + formDef.getFormDefinitionId());
      }
    }
  }

  /**
   * Create a new url using the fields specified in the <code>btxDetails</code> parameter. This
   * assumes that the id field is null and that all other fields are valid, but it doesn't check any
   * of this. As a side effect, it sets the id field on the <code>btxDetails</code> parameter to
   * the id assigned to the new url.
   * 
   * @param btxDetails The description of the url to create.
   */
  private void urlCreate(BtxDetailsMaintainAccountUrl btxDetails) {
    Connection con = null;
    CallableStatement cstmt = null;
    boolean targetIncluded = !ApiFunctions.isEmpty(btxDetails.getTarget());
    int outputParam = 5;
    try {
      con = ApiFunctions.getDbConnection();

      StringBuffer query = new StringBuffer(256);
      query.append("{ CALL INSERT INTO ");
      query.append(DbConstants.TABLE_ARD_URLS);
      query.append(" (");
      query.append(DbConstants.ARD_URLS_ACCT_KEY);
      query.append(",");
      query.append(DbConstants.ARD_URLS_OBJECT_TYPE);
      query.append(",");
      query.append(DbConstants.ARD_URLS_URL);
      query.append(",");
      query.append(DbConstants.ARD_URLS_LABEL);
      if (targetIncluded) {
        query.append(",");
        query.append(DbConstants.ARD_URLS_TARGET);
      }
      query.append(") VALUES (?, ?, ?, ?");
      if (targetIncluded) {
        query.append(", ?");
      }
      query.append(") RETURNING ");
      query.append(DbConstants.ARD_URLS_ID);
      query.append(" INTO ? }");

      cstmt = con.prepareCall(query.toString());
      cstmt.setString(1, btxDetails.getAccountId());
      cstmt.setString(2, btxDetails.getObjectType());
      cstmt.setString(3, btxDetails.getUrl());
      cstmt.setString(4, btxDetails.getLabel());
      if (targetIncluded) {
        cstmt.setString(5, btxDetails.getTarget());
        outputParam = 6;
      }
      cstmt.registerOutParameter(outputParam, Types.VARCHAR);
      cstmt.execute();

      // Get the newly created url id.
      btxDetails.setUrlId(cstmt.getString(outputParam));
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(cstmt);
      ApiFunctions.close(con);
    }
  }

  /**
   * Update an existing url to match the fields specified in the <code>btxDetails</code>
   * parameter. This assumes that the id field is the id of an existing url and that all other
   * fields are valid, but it doesn't check any of this.
   * 
   * @param btxDetails The description of the url to update.
   */
  private void urlUpdate(BtxDetailsMaintainAccountUrl btxDetails) {
    Connection con = null;
    PreparedStatement pstmt = null;
    try {
      con = ApiFunctions.getDbConnection();

      StringBuffer query = new StringBuffer(256);
      query.append("UPDATE ");
      query.append(DbConstants.TABLE_ARD_URLS);
      query.append(" SET ");
      query.append(DbConstants.ARD_URLS_ACCT_KEY);
      query.append(" = ?, ");
      query.append(DbConstants.ARD_URLS_OBJECT_TYPE);
      query.append(" = ?, ");
      query.append(DbConstants.ARD_URLS_URL);
      query.append(" = ?, ");
      query.append(DbConstants.ARD_URLS_LABEL);
      query.append(" = ?, ");
      query.append(DbConstants.ARD_URLS_TARGET);
      query.append(" = ? WHERE ");
      query.append(DbConstants.ARD_URLS_ID);
      query.append(" = ?");

      pstmt = con.prepareStatement(query.toString());

      pstmt.setString(1, btxDetails.getAccountId());
      pstmt.setString(2, btxDetails.getObjectType());
      pstmt.setString(3, btxDetails.getUrl());
      pstmt.setString(4, btxDetails.getLabel());
      pstmt.setString(5, btxDetails.getTarget());
      pstmt.setString(6, btxDetails.getUrlId());
      pstmt.executeUpdate();
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(pstmt);
      ApiFunctions.close(con);
    }
  }

  /**
   * Delete the existing url specified in the <code>btxDetails</code> parameter. This assumes that
   * the id field is the id of an existing url but it doesn't check this.
   * 
   * @param btxDetails The description of the url to update.
   */
  private void urlDelete(BtxDetailsMaintainAccountUrl btxDetails) {
    Connection con = null;
    PreparedStatement pstmt = null;
    try {
      con = ApiFunctions.getDbConnection();
      StringBuffer query = new StringBuffer(256);
      query.append("DELETE FROM ");
      query.append(DbConstants.TABLE_ARD_URLS);
      query.append(" WHERE ");
      query.append(DbConstants.ARD_URLS_ID);
      query.append(" = ?");

      pstmt = con.prepareStatement(query.toString());
      pstmt.setString(1, btxDetails.getUrlId());
      pstmt.executeUpdate();
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(pstmt);
      ApiFunctions.close(con);
    }
  }

  private BTXDetails performMoveByInventoryGroupStart(BtxDetailsMoveByInventoryGroupStart btxDetails) {
    //populate the list of inventory groups to which the user has access.
    btxDetails.setInventoryGroupChoices(getInventoryGroupChoices(btxDetails
        .getLoggedInUserSecurityInfo()));
    btxDetails.setActionForwardTXCompleted("success");
    return btxDetails;
  }

  private BTXDetails validatePerformMoveByInventoryGroupStart(
                                                              BtxDetailsMoveByInventoryGroupStart btxDetails) {
    return btxDetails;
  }

  private BTXDetails performMoveByInventoryGroup(BtxDetailsMoveByInventoryGroup btxDetails) {

    //get the full name of the inventory group, so it can be logged in the
    //history record
    List igIds = new ArrayList();
    igIds.add(btxDetails.getInventoryGroup());
    List igList = LogicalRepositoryUtils.getLogicalRepositoriesById(igIds);
    LogicalRepository ig = (LogicalRepository) igList.get(0);
    btxDetails.setInventoryGroupName(ig.getFullName());
    
    //the list of sample ids passed in might contain sample alias values.  Convert any such alias
    //values into sample ids.  Assumption here is that validation would have found any issues
    //with being able to do the conversion, so here we just blindly convert.
    List sampleIdList = new ArrayList();
    Iterator sampleIdIterator = btxDetails.getSampleIds().iterator();
    while (sampleIdIterator.hasNext()) {
      String sampleIdOrAlias = (String)sampleIdIterator.next();
      //if the value begins with the prefix for any of our sample ids (FR, PA, SX) then
      //assume it is a sample id and therefore no conversion is necessary.
      if (IltdsUtils.isSystemSampleId(sampleIdOrAlias)) {
        sampleIdList.add(sampleIdOrAlias);
      }
      //if the value does not begin with the prefix for any of our sample ids (FR, PA, SX) then
      //assume it is a sample alias and convert it to a sample id.
      else {
        //pass false to the findSampleIdsFromCustomerId call, to take into account any samples that
        //have been created via a box scan but have not yet been annotated.
        List existingSamples = IltdsUtils.findSamplesFromCustomerId(sampleIdOrAlias, false);
        SampleData sample = (SampleData)existingSamples.get(0);
        sampleIdList.add(sample.getSampleId());
      }
    }
    
    //retrieve information for each sample, so the history record can show it.  We're currently
    //using this to include the sample alias for each sample but should there be a need to show
    //additional info the history record can be modified to store/retrieve it
    List samples = getSampleInfo(sampleIdList);
    btxDetails.setSamples(samples);
    
    //now set up a "single" btxDetails object
    BtxDetailsMoveSample singleBTX = new BtxDetailsMoveSample();
    singleBTX.setAction(btxDetails.getAction());
    if (btxDetails.getAction().equalsIgnoreCase(Constants.OPERATION_ADD)) {
      singleBTX.setTransactionType("orm_addSampleToIg");
    }
    else if (btxDetails.getAction().equalsIgnoreCase(Constants.OPERATION_REMOVE)) {
      singleBTX.setTransactionType("orm_removeSampleFromIg");
    }
    singleBTX.setInventoryGroup(btxDetails.getInventoryGroup());
    singleBTX.setInventoryGroupName(btxDetails.getInventoryGroupName());
    singleBTX.setNote(btxDetails.getNote());
    singleBTX.setBeginTimestamp(btxDetails.getBeginTimestamp());
    singleBTX.setLoggedInUserSecurityInfo(btxDetails.getLoggedInUserSecurityInfo(), true);

    //call the "single" transaction for each sample id in the list
    boolean hadErrors = false;
    Iterator sampleIterator = samples.iterator();
    while (sampleIterator.hasNext()) {
      SampleData sample = (SampleData)sampleIterator.next();
      String sampleId = sample.getSampleId(); 
      String sampleAlias = sample.getSampleAlias(); 
      singleBTX.setSampleId(sampleId);
      singleBTX.setSampleAlias(sampleAlias);
      singleBTX = (BtxDetailsMoveSample) Btx.perform(singleBTX);
      //if any errors were returned from the btxDetails for the individual transaction,
      //add them to the btxDetails for this parent transaction, since that is what will
      //be returned to the front end
      Iterator errors = singleBTX.getActionErrors().get();
      while (errors.hasNext()) {
        hadErrors = true;
        btxDetails.addActionError((BtxActionError) errors.next());
      }
    }

    if (!hadErrors) {
      String action = null;
      if (btxDetails.getAction().equalsIgnoreCase(Constants.OPERATION_ADD)) {
        action = "added";
      }
      else if (btxDetails.getAction().equalsIgnoreCase(Constants.OPERATION_REMOVE)) {
        action = "removed";
      }
      btxDetails.addActionMessage(new BtxActionMessage("moveSample.message.sampleAction", action));
    }

    //populate the list of inventory groups to which the user has access.
    btxDetails.setInventoryGroupChoices(getInventoryGroupChoices(btxDetails
        .getLoggedInUserSecurityInfo()));
    //return success
    btxDetails.setActionForwardTXCompleted("success");
    return btxDetails;

  }

  private BTXDetails validatePerformMoveByInventoryGroup(BtxDetailsMoveByInventoryGroup btxDetails) {
    int MAX_COMMENT_LENGTH = 500;

    List sampleIdList = btxDetails.getSampleIds();
    List duplicateIds = new ArrayList();
    List invalidIds = new ArrayList();
    List alreadyInGroupIds = new ArrayList();
    List notInGroupIds = new ArrayList();
    List singleGroupIds = new ArrayList();

    //validate sample information
    if (ApiFunctions.isEmpty(sampleIdList)) {
      btxDetails.addActionError(new BtxActionError("error.noValuesSpecified", "Sample IDs"));
    }
    else {
      //the list of sample ids passed in might contain sample alias values.  Convert any such alias
      //values into sample ids, returning an error for any that cannot be converted.
      List convertedIdList = new ArrayList();
      Iterator sampleIdIterator = sampleIdList.iterator();
      while (sampleIdIterator.hasNext()) {
        String sampleIdOrAlias = (String)sampleIdIterator.next();
        //if the value begins with the prefix for any of our sample ids (FR, PA, SX) then
        //assume it is a sample id and therefore no conversion is necessary.
        if (IltdsUtils.isSystemSampleId(sampleIdOrAlias)) {
          convertedIdList.add(sampleIdOrAlias);
        }
        //if the value does not begin with the prefix for any of our sample ids (FR, PA, SX) then
        //assume it is a sample alias and handle the alias as follows:
        // - if the alias corresponds to multiple existing sample ids, return an error.
        // - if the alias corresponds to a single existing sample id, see if the account to which that 
        //      sample belongs requires unique aliases.
        //      - if not, return an error since we cannot be positive the sample we found was the one
        //        intended.
        //      - if so, use that sample id
        // - if the alias corresponds to no existing sample return an error.
        else {
          //pass false to the findSampleIdsFromCustomerId call, to take into account any samples that
          //have been created via a box scan but have not yet been annotated.
          List existingSamples = IltdsUtils.findSamplesFromCustomerId(sampleIdOrAlias, false);
          //if multiple samples with the specified alias were found, return an error.
          if (existingSamples.size() > 1) {
            btxDetails.addActionError(new BtxActionError("error.alias.multiplesExist", 
                Escaper.htmlEscapeAndPreserveWhitespace(sampleIdOrAlias)));
          }
          //if a single sample was found, do some further checking
          else if (existingSamples.size() == 1) {
            String accountId = ((SampleData)existingSamples.get(0)).getArdaisAcctKey();
            AccountDto accountDto = IltdsUtils.getAccountById(accountId, false, false);
            boolean aliasMustBeUnique = FormLogic.DB_YES.equalsIgnoreCase(accountDto.getRequireUniqueSampleAliases());
            //if the account to which the sample belongs does not require unique sample aliases,
            //return an error since we cannot be sure the sample we found was the one intended.
            if (!aliasMustBeUnique) {
              btxDetails.addActionError(new BtxActionError("error.alias.notUnique", 
                  Escaper.htmlEscapeAndPreserveWhitespace(sampleIdOrAlias)));
            }
            //otherwise use the sample id
            else {
              SampleData sample = (SampleData)existingSamples.get(0);
              sampleIdOrAlias = sample.getSampleId();
              convertedIdList.add(sampleIdOrAlias);
            }
          }
          //if no matching sample was found, just add the alias to the list.  The code below will
          //handle letting the user know the sample doesn't exit
          else {
            convertedIdList.add(sampleIdOrAlias);
          }          
        }
      }
      sampleIdList = convertedIdList;

      //store any duplicate sample ids, so we can tell the user that there were duplicates
      Set duplicates = ApiFunctions.duplicateSet(sampleIdList);
      Iterator duplicatesIterator = duplicates.iterator();
      while (duplicatesIterator.hasNext()) {
        duplicateIds.add(duplicatesIterator.next());
      }
      //create a set of the sample ids
      Set sampleIdSet = new HashSet(sampleIdList);
      //iterate over that set, making sure each sample is valid. Put any invalid sample
      //ids into the appropriate list
      Iterator sampleIdSetIterator = sampleIdSet.iterator();
      while (sampleIdSetIterator.hasNext()) {
        String sampleId = (String) sampleIdSetIterator.next();
        if (!IltdsUtils.sampleExists(sampleId)
            || !IcpUtils.isItemAccessibleToUserByInvGroup(btxDetails.getLoggedInUserSecurityInfo(),
                sampleId)) {
          invalidIds.add(sampleId);
        }
        else {
          String inventoryGroup = btxDetails.getInventoryGroup();
          if (!ApiFunctions.isEmpty(inventoryGroup)) {
            if (btxDetails.getAction().equalsIgnoreCase(Constants.OPERATION_ADD)
                && IltdsUtils.isInInventoryGroup(inventoryGroup, sampleId)) {
              alreadyInGroupIds.add(sampleId);
            }
            else if (btxDetails.getAction().equalsIgnoreCase(Constants.OPERATION_REMOVE)
                && !IltdsUtils.isInInventoryGroup(inventoryGroup, sampleId)) {
              notInGroupIds.add(sampleId);
            }
          }
          //if this sample belongs to only the specified inventory group, don't allow it to be
          //removed from that group
          if (btxDetails.getAction().equalsIgnoreCase(Constants.OPERATION_REMOVE)) {
            List inventoryGroups = LogicalRepositoryUtils.getLogicalRepositoriesForSample(sampleId,
                btxDetails.getLoggedInUserSecurityInfo());
            if (inventoryGroups.size() == 1) {
              LogicalRepository singleGroup = (LogicalRepository) inventoryGroups.get(0);
              if (singleGroup.getId().equalsIgnoreCase(inventoryGroup)) {
                singleGroupIds.add(sampleId);
              }
            }
          }
        }
      }
      //return any error messages related to sample ids
      if (duplicateIds.size() > 0) {
        btxDetails.addActionError(new BtxActionError("moveSample.error.duplicateSamples",
            ApiFunctions.toCommaSeparatedList(ApiFunctions.toStringArray(duplicateIds))));
      }
      if (invalidIds.size() > 0) {
        btxDetails.addActionError(new BtxActionError("moveSample.error.invalidSamples",
            ApiFunctions.toCommaSeparatedList(ApiFunctions.toStringArray(invalidIds))));
      }
      if (alreadyInGroupIds.size() > 0) {
        btxDetails.addActionError(new BtxActionError(
            "moveSample.error.samplesAlreadyInInventoryGroup", ApiFunctions
                .toCommaSeparatedList(ApiFunctions.toStringArray(alreadyInGroupIds))));
      }
      if (notInGroupIds.size() > 0) {
        btxDetails.addActionError(new BtxActionError("moveSample.error.samplesNotInInventoryGroup",
            ApiFunctions.toCommaSeparatedList(ApiFunctions.toStringArray(notInGroupIds))));
      }
      if (singleGroupIds.size() > 0) {
        btxDetails.addActionError(new BtxActionError(
            "moveSample.error.samplesInSingleInventoryGroup", ApiFunctions
                .toCommaSeparatedList(ApiFunctions.toStringArray(singleGroupIds))));
      }
      //set the list of sample ids to be just the valid ones. Note that
      //the duplicate ids do not need to be removed, as that was done when
      //we turned the sample id list into a set.
      sampleIdSet.removeAll(invalidIds);
      sampleIdSet.removeAll(alreadyInGroupIds);
      sampleIdSet.removeAll(notInGroupIds);
      sampleIdSet.removeAll(singleGroupIds);
      btxDetails.setSampleIds(new ArrayList(sampleIdSet));
    }

    //validate inventory group information
    String inventoryGroup = btxDetails.getInventoryGroup();
    if (ApiFunctions.isEmpty(inventoryGroup)) {
      btxDetails.addActionError(new BtxActionError("error.noValue", "Inventory Group"));
    }
    else {
      // check to see if the user has access to the inventory group
      List invGroup = new ArrayList();
      invGroup.add(inventoryGroup);
      if (!btxDetails.getLoggedInUserSecurityInfo().isAnyLogicalRepositoryAccessible(invGroup)) {
        btxDetails.addActionError(new BtxActionError("moveSample.error.unavailableInventoryGroup"));
      }
    }

    //validate comment information
    String note = btxDetails.getNote();
    if (!ApiFunctions.isEmpty(note) && note.length() > MAX_COMMENT_LENGTH) {
      btxDetails.addActionError(new BtxActionError("error.lengthExceeded", "Comment",
          MAX_COMMENT_LENGTH + " characters"));
    }

    //if there were any errors, populate the list of inventory groups to which the
    //user has access.
    if (!btxDetails.getActionErrors().empty()) {
      btxDetails.setInventoryGroupChoices(getInventoryGroupChoices(btxDetails
          .getLoggedInUserSecurityInfo()));
    }

    return btxDetails;
  }

  private BTXDetails performAddSampleToInventoryGroup(BtxDetailsMoveSample btxDetails) {
    StringBuffer sql = new StringBuffer(250);
    sql.append("INSERT INTO ");
    sql.append(DbConstants.TABLE_LOGICAL_REPOS_ITEM);
    sql.append(" (");
    sql.append(DbConstants.LOGICAL_REPOS_ITEM_REPOSITORY_ID);
    sql.append(", ");
    sql.append(DbConstants.LOGICAL_REPOS_ITEM_ITEM_ID);
    sql.append(", ");
    sql.append(DbConstants.LOGICAL_REPOS_ITEM_ITEM_TYPE);
    sql.append(") VALUES(?, ?, ?)");

    Connection con = null;
    PreparedStatement pstmt = null;
    try {
      con = ApiFunctions.getDbConnection();
      pstmt = con.prepareStatement(sql.toString());
      pstmt.setInt(1, ApiFunctions.safeInteger(btxDetails.getInventoryGroup()).intValue());
      pstmt.setString(2, btxDetails.getSampleId());
      pstmt.setString(3, ProductType.SAMPLE.toString());
      pstmt.execute();
    }
    catch (SQLException e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(pstmt);
      ApiFunctions.close(con);
    }

    return btxDetails;
  }

  private BTXDetails performRemoveSampleFromInventoryGroup(BtxDetailsMoveSample btxDetails) {
    StringBuffer sql = new StringBuffer(250);
    sql.append("DELETE FROM ");
    sql.append(DbConstants.TABLE_LOGICAL_REPOS_ITEM);
    sql.append(" WHERE ");
    sql.append(DbConstants.LOGICAL_REPOS_ITEM_REPOSITORY_ID);
    sql.append(" = ? AND ");
    sql.append(DbConstants.LOGICAL_REPOS_ITEM_ITEM_ID);
    sql.append(" = ? AND ");
    sql.append(DbConstants.LOGICAL_REPOS_ITEM_ITEM_TYPE);
    sql.append(" = ?");

    Connection con = null;
    PreparedStatement pstmt = null;
    try {
      con = ApiFunctions.getDbConnection();
      pstmt = con.prepareStatement(sql.toString());
      pstmt.setInt(1, ApiFunctions.safeInteger(btxDetails.getInventoryGroup()).intValue());
      pstmt.setString(2, btxDetails.getSampleId());
      pstmt.setString(3, ProductType.SAMPLE.toString());
      pstmt.execute();
    }
    catch (SQLException e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(pstmt);
      ApiFunctions.close(con);
    }

    return btxDetails;
  }

  private LegalValueSet getInventoryGroupChoices(SecurityInfo securityInfo) {
    //if the user has access to all logical repositories then execute a query
    //to get them all, otherwise just use what they have
    List inventoryGroups = null;
    if (securityInfo.isHasPrivilege(SecurityInfo.PRIV_VIEW_ALL_LOGICAL_REPOS)) {
      inventoryGroups = LogicalRepositoryUtils.getAllLogicalRepositories();
    }
    else {
      inventoryGroups = securityInfo.getLogicalRepositoriesByFullName();
    }
    LegalValueSet inventoryGroupChoices = new LegalValueSet();
    Iterator inventoryGroupsIterator = inventoryGroups.iterator();
    while (inventoryGroupsIterator.hasNext()) {
      LogicalRepository inventoryGroup = (LogicalRepository) inventoryGroupsIterator.next();
      inventoryGroupChoices.addLegalValue(inventoryGroup.getId(), inventoryGroup.getFullName());
    }
    return inventoryGroupChoices;
  }

  private BTXDetails validatePerformChangePassword(BtxDetailsChangePassword btxDetails)
      throws Exception {
    ArdaisuserAccessBean ardaisUserEB = null;

    //Validate that a valid user has been specified
    String userId = ApiFunctions.safeTrim(btxDetails.getUserData().getUserId());
    String accountId = ApiFunctions.safeTrim(btxDetails.getUserData().getAccountId());
    if (ApiFunctions.isEmpty(userId)) {
      btxDetails.addActionError(new BtxActionError("error.noValue", "userId"));
    }
    if (ApiFunctions.isEmpty(accountId)) {
      btxDetails.addActionError(new BtxActionError("error.noValue", "accountId"));
    }
    //Validate that the user has not specified an account inaccessible to them
    if (!ApiFunctions.isEmpty(accountId) && btxDetails.isRequireLogin()
        && !determineAccountChoicesForUser(btxDetails).contains(accountId)) {
      btxDetails.addActionError(new BtxActionError("orm.error.users.invalidAccountId", accountId));
    }
    if (!ApiFunctions.isEmpty(userId) && !ApiFunctions.isEmpty(accountId)) {
      try {
        ardaisUserEB = new ArdaisuserAccessBean(new ArdaisuserKey(userId, new ArdaisaccountKey(
            accountId)));
      }
      catch (Exception e) {
        btxDetails.addActionError(new BtxActionError("orm.error.users.userNotFound", userId,
            accountId));
      }
    }

    //if the old password is required, make sure it matches the existing password
    if (btxDetails.isRequireOldPassword() && ardaisUserEB != null) {
      String oldPassword = ApiFunctions.safeTrim(ApiFunctions.safeString(btxDetails
          .getOldPassword()));
      if (ApiFunctions.isEmpty(oldPassword)) {
        btxDetails.addActionError(new BtxActionError("error.noValue", "old password"));
      }
      else if (!ApiFunctions.encryptPassword(oldPassword).equalsIgnoreCase(
          ardaisUserEB.getPassword())) {
        btxDetails.addActionError(new BtxActionError("orm.error.password.oldPasswordIncorrect"));
      }
    }

    // MR 7292 enforce password rules.
    String newPassword = btxDetails.getUserData().getPassword1();
    String confirmPassword = btxDetails.getUserData().getPassword2();
    ValidatePasswords validate = new ValidatePasswords(newPassword, confirmPassword, userId,
        accountId);
    validate.checkAllPasswordRules(btxDetails);

    return btxDetails;
  }

  private BTXDetails performChangePassword(BtxDetailsChangePassword btxDetails) throws Exception {

    String userId = ApiFunctions.safeTrim(btxDetails.getUserData().getUserId());
    String accountId = ApiFunctions.safeTrim(btxDetails.getUserData().getAccountId());
    String newPassword = btxDetails.getUserData().getPassword1();

    try {
      ArdaisuserAccessBean ardaisUserEB = new ArdaisuserAccessBean(new ArdaisuserKey(userId,
          new ArdaisaccountKey(accountId)));
      ardaisUserEB.setPassword(ApiFunctions.encryptPassword(newPassword));
      ardaisUserEB.commitCopyHelper();

      // Set a message saying password has changed.
      btxDetails.addActionMessage(new BtxActionMessage("orm.message.password.passwordChanged"));
      btxDetails.setActionForwardTXCompleted("success");
    }
    catch (javax.ejb.ObjectNotFoundException e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    //if the user is not currently logged in (which will be the case when they
    //are changing their password because it has expired), set the userId on
    //the btxDetails to be the userId from the userDto passed in. This must
    //be done to avoid a sql exception when the history for this transaction
    //is recorded.
    if (!btxDetails.isRequireLogin()) {
      btxDetails.setUserId(userId);
    }
    return btxDetails;
  }

  /**
   * Method removeFromHoldList.
   * 
   * @param sampleIds
   * @param securityInfo
   */
  private BTXDetails performLogin(BTXDetailsLogin btx) {
    // We don't know who the user really is, so we set the userId and account that will get
    // logged in BTX transaction history to a generic IT user. See the comments on the
    // GENERIC_BTX_LOGIN_* constants for more details.
    //
    btx.setUserId(ApiProperties.getProperty("api.bigr.bootstrap.user"));
    btx.setUserAccount(ApiProperties.getProperty("api.bigr.bootstrap.account"));

    if (ApiFunctions.isEmpty(btx.getAttemptUser())) {
      btx.addActionError(new BtxActionError("error.noValue", "User ID"));
    }
    if (ApiFunctions.isEmpty(btx.getAttemptAccount())) {
      btx.addActionError(new BtxActionError("error.noValue", "Account ID"));
    }
    if (ApiFunctions.isEmpty(btx.getPassword())) {
      btx.addActionError(new BtxActionError("error.noValue", "Password"));
    }
    if (!btx.getActionErrors().empty()) {
      btx.setActionForwardRetry(new BtxActionError("error.login.retry"));
      return btx;
    }

    boolean isValid = false;
    Vector profile = null;
    List menuUrls = null;
    //determine the JAAS configuration to use
    String configuration = ApiProperties.getProperty(ApiResources.JAAS_LOGIN_CONFIGURATION, Constants.BIGR);
    try {
      AuthenticationManager authenticationManager = SecurityServiceProvider.getAuthenticationManager(configuration);
      isValid = authenticationManager.login(btx.getAttemptUser(), btx.getPassword());
    }
    catch (CSLoginException cs) {
      //invalid credentials encountered, nothing to do
    }
    catch (Exception e) {
      throw new ApiException(e);
    }
    
    //The above code validates the username and password, but does not validate the account.
    //In order to validate the account information, try to retrieve an Ardaisuser instance from
    //the username and account (this instance is used in both the valid and invalid login
    //information paths below).  If that fails, then assume that the login information is invalid.
    ArdaisuserAccessBean ardaisUserEB = null;
    try {
      ardaisUserEB = new ArdaisuserAccessBean(new ArdaisuserKey(btx.getAttemptUser(), 
          new ArdaisaccountKey(btx.getAttemptAccount())));
    }
    catch (Exception e) {
      isValid = false;
      ardaisUserEB = null;
    }
    
    //verify that the user and their account are both active
    if (ardaisUserEB != null) {
      try {
        String userActive = ardaisUserEB.getUser_active_ind();
        if (!FormLogic.DB_YES.equalsIgnoreCase(userActive)) {
          isValid = false;
        }
        String accountActive = ardaisUserEB.getArdaisaccount().getArdais_acct_status();
        if (!Constants.ACCOUNT_STATUS_ACTIVE.equalsIgnoreCase(accountActive)) {
          isValid = false;
        }
      }
      catch (Exception e) {
        isValid = false;
      }
    }
    
    //if the information is not valid, log the failed login attempt
    if (!isValid) {
      // Call on place holder in the event of a failed login.
      BtxDetailsFailedLogin failedLogin = new BtxDetailsFailedLogin();
      failedLogin.setLoggedInUserSecurityInfo(btx.getLoggedInUserSecurityInfo());
      failedLogin.setBeginTimestamp(btx.getBeginTimestamp());

      failedLogin.setUserId(ApiProperties.getProperty("api.bigr.bootstrap.user"));
      failedLogin.setUserAccount(ApiProperties.getProperty("api.bigr.bootstrap.account"));

      failedLogin.setAttemptUser(btx.getAttemptUser());
      failedLogin.setAttemptAccount(btx.getAttemptAccount());

      try {
        failedLogin.setTransactionType("iltds_placeholder");
        Btx.perform(failedLogin);
      }
      catch (Exception e) {
        ApiFunctions.throwAsRuntimeException(e);
      }

      //If the specified user/account exists, update the number of failed login attempts made for 
      //this user, and disable their account if the maximum failed attempts has been reached.
      //A nice future enhancement would be to somehow count and/or record login failures when the 
      //user and/or account id is invalid, since that could indicate an attempt to hack into the 
      //system as well as the ones that we currently track (correct user/account, but 
      //wrong password).
      boolean userAccountWasDisabled = false;
      try {
        if (ardaisUserEB != null) {
          int failedAttempts = ardaisUserEB.getConsecutive_failed_logins().intValue() + 1;
          ardaisUserEB.setConsecutive_failed_logins(new Integer(failedAttempts));
          ardaisUserEB.commitCopyHelper();
          //if the number of attempts is exceeded and the user's account is not yet disabled,
          //disable it
          boolean isActive = FormLogic.DB_YES.equalsIgnoreCase(ardaisUserEB.getUser_active_ind());
          if (failedAttempts >= com.ardais.bigr.orm.helpers.FormLogic.MAX_LOGIN_ATTEMPTS
              && isActive) {
            disableUser(btx.getAttemptAccount(), btx.getAttemptUser(),
                "they tried to log in too many times with the wrong password.", btx
                    .getLoggedInUserSecurityInfo());
            userAccountWasDisabled = true;
          }
        }
      }
      catch (Exception ex) {
        ApiFunctions.throwAsRuntimeException(ex);
      }

      if (userAccountWasDisabled) {
        btx.setActionForwardRetry(new BtxActionError("error.login.failedAndAccountDisabled"));
      }
      else {
        btx.setActionForwardRetry(new BtxActionError("error.login.failed"));
      }
      return btx;
    }
    
    //if the username, password, and account are all valid then proceed with retrieval of
    //user information and other login tasks
    if (isValid) {
      try {
        String user = btx.getAttemptUser();
        String account = btx.getAttemptAccount();
        UserProfHome home = (UserProfHome) EjbHomes.getHome(UserProfHome.class);
        UserProf userProf = home.create();
        profile = userProf.getProfile(user, account);
        menuUrls = userProf.getMenuUrls(btx.getAttemptAccount());
        //reset the number of failed login attempts and failed security answer attempts
        //made for this user (MR8495)
        ardaisUserEB.setConsecutive_failed_logins(new Integer(0));
        ardaisUserEB.setConsecutive_failed_answers(new Integer(0));
        ardaisUserEB.commitCopyHelper();
      }
      catch (Exception e) {
        ApiFunctions.throwAsRuntimeException(e);
      }
    }

    // Account key.
    ArdaisaccountKey accountKey = new ArdaisaccountKey(btx.getAttemptAccount());
    ArdaisaccountAccessBean accountAB = null;
    String accountPasswordPolicyCid = null;
    long passwordLifeSpan = 0;

    // User key.
    ArdaisuserKey userKey = new ArdaisuserKey(btx.getAttemptUser(), accountKey);
    ArdaisuserAccessBean userAB = null;
    String userPasswordPolicyCid = null;
    Timestamp passwordLastChangeDate = null;

    try {
      // Get account bean.
      accountAB = new ArdaisaccountAccessBean(accountKey);
      accountPasswordPolicyCid = accountAB.getPasswordPolicyCid();
      passwordLifeSpan = accountAB.getPasswordLifeSpan();

      // Get user bean.
      userAB = new ArdaisuserAccessBean(userKey);
      userPasswordPolicyCid = userAB.getPasswordPolicyCid();
      passwordLastChangeDate = userAB.getPasswordLastChangeDate();
    }
    catch (Exception ex) {
      ApiFunctions.throwAsRuntimeException(ex);
    }

    btx.setPasswordExpired(false);
    // Check to see if expiration date needs to be calculated.
    if (userPasswordPolicyCid.equalsIgnoreCase(ArtsConstants.PASSWORD_POLICY_EXPIRES)
        || (userPasswordPolicyCid.equalsIgnoreCase(ArtsConstants.PASSWORD_POLICY_INHERIT) && accountPasswordPolicyCid
            .equalsIgnoreCase(ArtsConstants.PASSWORD_POLICY_EXPIRES))) {
      Timestamp today = new Timestamp(System.currentTimeMillis());
      Timestamp expirationDate = new Timestamp(passwordLastChangeDate.getTime()
          + (passwordLifeSpan * 86400000L));

      boolean passwordHasExpired = expirationDate.before(today);
      if (passwordHasExpired) {
        // Password has expired.
        btx.setPasswordExpired(true);
        btx.setActionForwardTXCompleted("passwordExpired");
      }
      else {
        btx.setActionForwardTXCompleted("success");
      }
    }
    else {
      btx.setActionForwardTXCompleted("success");
    }

    // We don't know who the user really was if isValid is false, so we need to be careful
    // only to set UserId and UserAccount on the BTXDetails in the success case. We wouldn't
    // want a login attempt to be logged in BTX transaction history as being performed by
    // a particular user when isValid is false, since there's no reason in that case to believe
    // that AttemptUser is the real user. If this ever gets changed so that the non-success
    // case gets logged in BTX history, the UserId and UserAccount should get set to a generic
    // IT user rather than AttemptUser in the failure case, as we do in performDisableUser.
    //
    btx.setProfile(profile);
    btx.setMenuUrls(menuUrls);
    btx.setUserId(btx.getAttemptUser());
    btx.setUserAccount(btx.getAttemptAccount());
    return btx;
  }

  private BTXDetails performDisableUser(BTXDetailsDisableLogin btx) {
    // We don't know who the user really is yet, so we set the userId and account that will get
    // logged in BTX transaction history to a generic IT user. See the comments on the
    // GENERIC_BTX_LOGIN_* constants for more details.
    //
    btx.setUserId(ApiProperties.getProperty("api.bigr.bootstrap.user"));
    btx.setUserAccount(ApiProperties.getProperty("api.bigr.bootstrap.account"));

    try {
      ArdaisuserAccessBean user = new ArdaisuserAccessBean(new ArdaisuserKey(btx.getAttemptUser(),
          new ArdaisaccountKey(btx.getAttemptAccount())));
      user.setUser_active_ind("N");
      user.commitCopyHelper();
    }
    catch (javax.ejb.ObjectNotFoundException e) {
      // The user id isn't a valid user id, so there's no account to disable.
      // For now we treat this as the transaction not completing, which means that
      // this won't get logged. That's probably a good thing to change in the future
      // since that means if someone tries to log in repeatedly (perhaps trying to break
      // into the system) but they don't know a valid username, we won't log that fact.
      //
      btx.setTransactionCompleted(false);
      return btx;
    }
    catch (Exception e1) {
      // We got an unexpected exception, throw it.
      ApiFunctions.throwAsRuntimeException(e1);
    }

    btx.setTransactionCompleted(true);
    return btx;
  }

  /**
   * Do BTX transaction validation for the performMaintainAccountUrlStart performer method.
   */
  private BTXDetails validatePerformMaintainAccountUrlStart(
                                                            BtxDetailsMaintainAccountUrlStart btxDetails) {

    // We don't do much validation here, since this is the action that we go to to report
    // validation errors. Because of that, we can expect that there will sometimes be
    // odd data in the fields we get here. We do need some things to be correct, though.
    // For example, the operation field needs to have a valid operation name in it, and for
    // update operations we need a valid url id so that we can look up its data.

    boolean testProhibitUrlId = false;
    boolean testInvalidUrlId = false;

    String operation = btxDetails.getOperation();

    if (ApiFunctions.isEmpty(operation)) {
      btxDetails.addActionError(new BtxActionError("orm.error.url.requiredOperation"));
    }
    else if (!(Constants.OPERATION_CREATE.equals(operation)
        || Constants.OPERATION_UPDATE.equals(operation) || Constants.OPERATION_DELETE
        .equals(operation))) {
      btxDetails
          .addActionError(new BtxActionError("orm.error.url.operationCreateOrUpdateOrDelete"));
    }

    // Decide which tests to do based on the operation.
    if (Constants.OPERATION_CREATE.equals(operation)) {
      testProhibitUrlId = true;
    } // end operation == Create
    else if (Constants.OPERATION_UPDATE.equals(operation)) {
      testInvalidUrlId = true;
    } // end operation == Update

    // Now perform the tests...
    String urlId = btxDetails.getUrlId();

    if (testProhibitUrlId && !ApiFunctions.isEmpty(urlId)) {
      btxDetails.addActionError(new BtxActionError("orm.error.url.prohibitUrlId"));
    }

    if (testInvalidUrlId) {
      if (ApiFunctions.isEmpty(urlId)) {
        btxDetails.addActionError(new BtxActionError("orm.error.url.requiredUrlId"));
      }
      else if (!UrlUtils.isExistingUrl(urlId)) {
        btxDetails.addActionError(new BtxActionError("orm.error.url.invalidUrlId"));
      }
    }
    return btxDetails;
  }

  /**
   * This is the main BTX entry point for performing the transaction that starts the process of
   * creating, editing, or deleting a url.
   */
  private BTXDetails performMaintainAccountUrlStart(BtxDetailsMaintainAccountUrlStart btxDetails)
      throws Exception {
    //because this is invoked from a menu item (and therefore the account id may not have been
    // set),
    //if the accountId is null then sete it to be the account of the current user
    if (ApiFunctions.isEmpty(btxDetails.getAccountId())) {
      btxDetails.setAccountId(btxDetails.getUserAccount());
    }

    //if the user is Ardais user, display all urls,
    // otherwise get all the urls that have been defined for this account
    if (btxDetails.getLoggedInUserSecurityInfo().isInRoleSystemOwner()) {
      btxDetails.setUrls(UrlUtils.getAllUrls());
      btxDetails.setAccountList(IltdsUtils.getAccountList());
    }
    else {
      btxDetails.setUrls(UrlUtils.getUrlsByAccountId(btxDetails.getAccountId()));
    }

    String operation = btxDetails.getOperation();

    if (Constants.OPERATION_UPDATE.equals(operation)) {
      if (btxDetails.isPopulateUrlOutputFields()) {
        String urlId = btxDetails.getUrlId();
        UrlDto urlDto = UrlUtils.getUrlDto(urlId);
        BigrBeanUtilsBean.SINGLETON.copyProperties(btxDetails, urlDto);
      }
    }

    btxDetails.setActionForwardTXCompleted("success");
    return btxDetails;
  }

  /**
   * This is the main BTX entry point for performing the transaction that starts the process of
   * finding users.
   */
  private BTXDetails performFindUsersStart(BtxDetailsFindUsers btxDetails) throws Exception {
    //clear any existing data so the search starts fresh
    UserDto userData = new UserDto();
    userData.setAddress(new LocationData());
    btxDetails.setUserData(userData);
    //set the list of accounts id within which the user may search for users
    btxDetails.setAccountChoices(determineAccountChoicesForUser(btxDetails));
    btxDetails.setActionForwardTXCompleted("success");
    return btxDetails;
  }

  /**
   * Do BTX transaction validation for the performFindUsers performer method.
   */
  private BTXDetails validatePerformFindUsers(BtxDetailsFindUsers btxDetails) throws Exception {
    //Validate that the user has not specified an account inaccessible to them
    String accountId = btxDetails.getUserData().getAccountId();
    if (!ApiFunctions.isEmpty(accountId)
        && !determineAccountChoicesForUser(btxDetails).contains(accountId)) {
      btxDetails.addActionError(new BtxActionError("orm.error.users.invalidAccountId", accountId));
    }
    //if any errors are going to be returned, get the list of valid account values
    //so the drop-down contains data
    if (!btxDetails.getActionErrors().empty()) {
      btxDetails.setAccountChoices(determineAccountChoicesForUser(btxDetails));
    }
    return btxDetails;
  }

  /**
   * This is the main BTX entry point for performing the transaction that finds users.
   */
  private BTXDetails performFindUsers(BtxDetailsFindUsers btxDetails) throws Exception {
    //if any users match the criteria return them but if not
    //return a message to that effect, populate the account choices
    //and return a failure.
    List users = findUsers(btxDetails.getLoggedInUserSecurityInfo(), btxDetails.getUserData());
    btxDetails.setMatchingUsers(users);
    if (users.size() <= 0) {
      btxDetails.addActionMessage(new BtxActionMessage("orm.message.users.noUsersFound"));
      btxDetails.setAccountChoices(determineAccountChoicesForUser(btxDetails));
      btxDetails.setActionForwardTXCompleted("retry");
    }
    else {
      btxDetails.setActionForwardTXCompleted("success");
    }
    return btxDetails;
  }

  /**
   * Do BTX transaction validation for the performCreateUserStart performer method.
   */
  private BTXDetails validatePerformCreateUserStart(BtxDetailsCreateUser btxDetails)
      throws Exception {
    //if the user has specified a user id and a user already exists with that id, return
    //an error
    UserDto userData = btxDetails.getUserData();
    String userId = userData.getUserId();
    if (!ApiFunctions.isEmpty(userId)) {
      OrmUserManagementHome oumHome = (OrmUserManagementHome) EjbHomes.getHome(OrmUserManagementHome.class);
      OrmUserManagement userBean = oumHome.create();
      if (userBean.isUserExisting(userId)) {
        btxDetails.addActionError(new BtxActionError("orm.error.users.userAlreadyExists", userData
            .getUserId()));
      }
    }
    //Validate that the user has not specified an account inaccessible to them
    String accountId = btxDetails.getUserData().getAccountId();
    if (!ApiFunctions.isEmpty(accountId)
        && !determineAccountChoicesForUser(btxDetails).contains(accountId)) {
      btxDetails.addActionError(new BtxActionError("orm.error.users.invalidAccountId", accountId));
    }
    //if any errors are going to be returned, get the list of valid account values
    //so the drop-down contains data
    if (!btxDetails.getActionErrors().empty()) {
      btxDetails.setAccountChoices(determineAccountChoicesForUser(btxDetails));
    }
    return btxDetails;
  }

  /**
   * This is the main BTX entry point for performing the transaction that starts the process of
   * creating a user.
   */
  private BTXDetails performCreateUserStart(BtxDetailsCreateUserStart btxDetails) throws Exception {
    UserDto userData = btxDetails.getUserData();
    if (userData == null) {
      userData = new UserDto();
      btxDetails.setUserData(userData);
    }
    userData.setActiveYN(FormLogic.DB_YES);
    //set the list of accounts within which the user may create a user
    btxDetails.setAccountChoices(determineAccountChoicesForUser(btxDetails));
    btxDetails.setActionForwardTXCompleted("success");
    return btxDetails;
  }

  /**
   * Do BTX transaction validation for the performCreateUser performer method.
   */
  private BTXDetails validatePerformCreateUser(BtxDetailsCreateUser btxDetails) throws Exception {
    //first, perform the same validation we did when starting the process of
    //creating a user. if that fails, don't bother with any further validation
    validatePerformCreateUserStart(btxDetails);
    if (!btxDetails.getActionErrors().empty()) {
      return btxDetails;
    }

    UserDto userData = btxDetails.getUserData();
    //validate user id
    userData.setUserId(ApiFunctions.safeTrim(userData.getUserId()));
    String userId = ApiFunctions.safeString(userData.getUserId());
    if (ApiFunctions.isEmpty(userId)) {
      btxDetails.addActionError(new BtxActionError("error.noValue", "User Id"));
    }
    else {
      if (!com.ardais.bigr.orm.helpers.FormLogic.checkUserIdFormat(userId)) {
        btxDetails.addActionError(new BtxActionError("orm.error.users.invalidUserId"));
      }
    }

    //validate account id
    userData.setAccountId(ApiFunctions.safeTrim(userData.getAccountId()));
    String accountId = ApiFunctions.safeString(userData.getAccountId());
    if (ApiFunctions.isEmpty(accountId)) {
      btxDetails.addActionError(new BtxActionError("error.noValue", "Account Id"));
    }
    else {
      if (!determineAccountChoicesForUser(btxDetails).contains(accountId)) {
        btxDetails
            .addActionError(new BtxActionError("orm.error.users.invalidAccountId", accountId));
      }
    }

    //call method to validate common user information
    validateCommonUserInformation(btxDetails);

    //validate passwords
    userData.setPassword1(ApiFunctions.safeTrim(userData.getPassword1()));
    String password1 = userData.getPassword1();
    userData.setPassword2(ApiFunctions.safeTrim(userData.getPassword2()));
    String password2 = userData.getPassword2();
    ValidatePasswords validPassword = new ValidatePasswords(password1, password2, userId, accountId);
    validPassword.checkAllPasswordRules(btxDetails);

    //validate password policy
    validateUserPasswordPolicy(btxDetails);

    //if any errors are going to be returned, get the list of valid account values
    //so the drop-down contains data
    if (!btxDetails.getActionErrors().empty()) {
      btxDetails.setAccountChoices(determineAccountChoicesForUser(btxDetails));
    }
    return btxDetails;
  }

  /**
   * This is the main BTX entry point for performing the transaction that creates a user.
   */
  private BTXDetails performCreateUser(BtxDetailsCreateUser btxDetails) throws Exception {
    UserDto userData = btxDetails.getUserData();
    String username = btxDetails.getLoggedInUserSecurityInfo().getUsername(); 
    OrmUserManagementHome home = (OrmUserManagementHome) EjbHomes.getHome(OrmUserManagementHome.class);
    OrmUserManagement createUser = home.create();
    createUser.createNewUser(userData, username);
    btxDetails.setActionForwardTXCompleted("success");
    return btxDetails;
  }

  /**
   * Do BTX transaction validation for the performModifyUserStart performer method.
   */
  private BTXDetails validatePerformModifyUserStart(BtxDetailsModifyUser btxDetails)
      throws Exception {
    //Validate that a valid user has been specified
    String userId = ApiFunctions.safeTrim(btxDetails.getUserData().getUserId());
    String accountId = ApiFunctions.safeTrim(btxDetails.getUserData().getAccountId());
    if (ApiFunctions.isEmpty(userId)) {
      btxDetails.addActionError(new BtxActionError("error.noValue", "userId"));
    }
    if (ApiFunctions.isEmpty(accountId)) {
      btxDetails.addActionError(new BtxActionError("error.noValue", "accountId"));
    }
    //Validate that the user has not specified an account inaccessible to them
    if (!ApiFunctions.isEmpty(accountId)
        && !determineAccountChoicesForUser(btxDetails).contains(accountId)) {
      btxDetails.addActionError(new BtxActionError("orm.error.users.invalidAccountId", accountId));
    }
    if (!ApiFunctions.isEmpty(userId) && !ApiFunctions.isEmpty(accountId)) {
      try {
        ArdaisuserAccessBean ardaisUserEB = new ArdaisuserAccessBean(new ArdaisuserKey(userId,
            new ArdaisaccountKey(accountId)));
      }
      catch (Exception e) {
        btxDetails.addActionError(new BtxActionError("orm.error.users.userNotFound", userId,
            accountId));
      }
    }

    //if any errors are going to be returned, get the list of valid account values
    //so the drop-down contains data
    if (!btxDetails.getActionErrors().empty()) {
      btxDetails.setAccountChoices(determineAccountChoicesForUser(btxDetails));
    }
    return btxDetails;
  }

  /**
   * This is the main BTX entry point for performing the transaction that starts the process of
   * modifying a user.
   */
  private BTXDetails performModifyUserStart(BtxDetailsModifyUserStart btxDetails) throws Exception {
    String userId = ApiFunctions.safeTrim(btxDetails.getUserData().getUserId());
    String accountId = ApiFunctions.safeTrim(btxDetails.getUserData().getAccountId());
    try {
      ArdaisuserAccessBean ardaisUserEB = new ArdaisuserAccessBean(new ArdaisuserKey(userId,
          new ArdaisaccountKey(accountId)));
      String addressId = String.valueOf(ardaisUserEB.getUser_address_id());
      ArdaisaddressAccessBean ardaisAddressEB = new ArdaisaddressAccessBean(new ArdaisaddressKey(
          new BigDecimal(addressId), accountId));
      UserDto userData = new UserDto(ardaisUserEB);
      userData.setAddress(new LocationData(ardaisAddressEB));
      btxDetails.setUserData(userData);
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    btxDetails.setActionForwardTXCompleted("success");
    return btxDetails;
  }

  /**
   * Do BTX transaction validation for the performModifyUser performer method.
   */
  private BTXDetails validatePerformModifyUser(BtxDetailsModifyUser btxDetails) throws Exception {
    //first, perform the same validation we did when starting the process of
    //modifying a user. if that fails, don't bother with any further validation
    validatePerformModifyUserStart(btxDetails);
    if (!btxDetails.getActionErrors().empty()) {
      return btxDetails;
    }

    validateCommonUserInformation(btxDetails);
    validateUserPasswordPolicy(btxDetails);

    //if any errors are going to be returned, get the list of valid account values
    //so the drop-down contains data
    if (!btxDetails.getActionErrors().empty()) {
      btxDetails.setAccountChoices(determineAccountChoicesForUser(btxDetails));
    }
    return btxDetails;
  }

  /**
   * This is the main BTX entry point for performing the transaction that modifies a user.
   */
  private BTXDetails performModifyUser(BtxDetailsModifyUser btxDetails) throws Exception {
    UserDto userData = btxDetails.getUserData();
    LocationData address = userData.getAddress();
    String userId = ApiFunctions.safeTrim(userData.getUserId());
    String accountId = ApiFunctions.safeTrim(userData.getAccountId());
    try {
      ArdaisuserAccessBean ardaisUserEB = new ArdaisuserAccessBean(new ArdaisuserKey(userId,
          new ArdaisaccountKey(accountId)));
      ardaisUserEB.setUser_firstname(userData.getFirstName());
      ardaisUserEB.setUser_lastname(userData.getLastName());
      ardaisUserEB.setUser_email_address(userData.getEmail());
      ardaisUserEB.setUser_department(userData.getDepartment());
      ardaisUserEB.setUser_title(userData.getTitle());
      ardaisUserEB.setUser_functional_title(userData.getFunctionalTitle());
      ardaisUserEB.setUser_affiliation(userData.getAffiliation());
      ardaisUserEB.setUser_phone_number(userData.getPhoneNumber());
      ardaisUserEB.setUser_phone_ext(userData.getExtension());
      ardaisUserEB.setUser_fax_number(userData.getFaxNumber());
      ardaisUserEB.setUser_mobile_number(userData.getMobileNumber());
      if (FormLogic.DB_NO.equalsIgnoreCase(userData.getActiveYN())) {
        ardaisUserEB.setUser_active_ind(FormLogic.DB_NO);
      }
      else {
        ardaisUserEB.setUser_active_ind(FormLogic.DB_YES);
        //if the user's account is being made active, then reset the number of
        //consecutive failed login and security answer attempts (MR8495)
        ardaisUserEB.setConsecutive_failed_logins(new Integer(0));
        ardaisUserEB.setConsecutive_failed_answers(new Integer(0));
      }
      ardaisUserEB.setPasswordPolicyCid(userData.getPasswordPolicy());
      ardaisUserEB.setUpdated_by(btxDetails.getLoggedInUserSecurityInfo().getUsername());
      ardaisUserEB.setUpdate_date(new java.sql.Timestamp((new java.util.Date()).getTime()));

      int addrID = (ardaisUserEB.getUser_address_id() != null) ? ardaisUserEB.getUser_address_id()
          .intValue() : 0;
      java.math.BigDecimal addressID = new java.math.BigDecimal(String.valueOf(addrID));
      ArdaisaddressAccessBean aradisAddressEB = new ArdaisaddressAccessBean(new ArdaisaddressKey(
          addressID, accountId));
      aradisAddressEB.setAddress_1(address.getAddress1());
      aradisAddressEB.setAddress_2(address.getAddress2());
      aradisAddressEB.setAddr_city(address.getCity());
      aradisAddressEB.setAddr_state(address.getState());
      aradisAddressEB.setAddr_zip_code(address.getZipCode());
      aradisAddressEB.setAddr_country(address.getCountry());
      ardaisUserEB.commitCopyHelper();
      aradisAddressEB.commitCopyHelper();
    }
    catch (Exception e) {
      throw new ApiException();
    }
    btxDetails.getActionMessages().add(new BtxActionMessage("orm.message.users.userModified"));
    btxDetails.setActionForwardTXCompleted("success");
    return btxDetails;
  }

  private BTXDetails validatePerformFindAccountsStart(BtxDetailsFindAccounts btxDetails) {
    //make sure the user has the Account Management privilege
    boolean hasPriv = btxDetails.getLoggedInUserSecurityInfo().isHasPrivilege(
        SecurityInfo.PRIV_ORM_ACCOUNT_MANAGEMENT);
    if (!hasPriv) {
      btxDetails.addActionError(new BtxActionError("error.noPrivilege", "manage accounts"));
    }
    return btxDetails;
  }

  /**
   * This is the main BTX entry point for performing the transaction that starts the process of
   * finding accounts.
   */
  private BTXDetails performFindAccountsStart(BtxDetailsFindAccounts btxDetails) throws Exception {
    //if the user is in the system owner account, they can access any account so
    //bring them to the search page. If they are not in the system owner account
    //then they may only access their own account so bring them to the account
    //management page
    boolean isSystemOwner = btxDetails.getLoggedInUserSecurityInfo().isInRoleSystemOwner();
    if (isSystemOwner) {
      //clear any existing data so the search starts fresh
      AccountDto accountData = new AccountDto();
      btxDetails.setAccountData(accountData);
      btxDetails.setActionForwardTXCompleted("success");
    }
    else {
      AccountDto accountData = new AccountDto();
      accountData.setId(btxDetails.getLoggedInUserSecurityInfo().getAccount());
      btxDetails.setAccountData(accountData);
      btxDetails.setActionForwardTXCompleted("accountDetermined");
    }
    return btxDetails;
  }

  /**
   * Do BTX transaction validation for the performFindAccounts performer method.
   */
  private BTXDetails validatePerformFindAccounts(BtxDetailsFindAccounts btxDetails)
      throws Exception {
    //perform the same validation we did when starting the process of
    //finding accounts.
    validatePerformFindAccountsStart(btxDetails);
    //make sure the user is a system owner - non system owners should not be able to
    //search for accounts but are instead brought directly to the modify account page
    //for their account
    boolean isSystemOwner = btxDetails.getLoggedInUserSecurityInfo().isInRoleSystemOwner();
    if (!isSystemOwner) {
      btxDetails.addActionError(new BtxActionError("error.noPrivilege", "manage accounts"));
    }
    return btxDetails;
  }

  /**
   * This is the main BTX entry point for performing the transaction that finds accounts.
   */
  private BTXDetails performFindAccounts(BtxDetailsFindAccounts btxDetails) throws Exception {
    //if any accounts match the criteria return them but if not return a message to that effect
    //and return a failure.
    List accounts = findAccounts(btxDetails.getLoggedInUserSecurityInfo(), btxDetails
        .getAccountData());
    btxDetails.setMatchingAccounts(accounts);
    if (accounts.size() <= 0) {
      btxDetails.addActionMessage(new BtxActionMessage("orm.message.accounts.noAccountsFound"));
      btxDetails.setActionForwardTXCompleted("retry");
    }
    else {
      btxDetails.setActionForwardTXCompleted("success");
    }
    return btxDetails;
  }

  /**
   * Do BTX transaction validation for the performCreateAccountStart performer method.
   */
  private BTXDetails validatePerformCreateAccountStart(BtxDetailsCreateAccount btxDetails)
      throws Exception {
    //make sure the user has the Account Management privilege
    boolean hasPriv = btxDetails.getLoggedInUserSecurityInfo().isHasPrivilege(
        SecurityInfo.PRIV_ORM_ACCOUNT_MANAGEMENT);
    if (!hasPriv) {
      btxDetails.addActionError(new BtxActionError("error.noPrivilege", "create accounts"));
    }
    //if the user is not in the system owner account, return an error
    boolean isSystemOwner = btxDetails.getLoggedInUserSecurityInfo().isInRoleSystemOwner();
    if (!isSystemOwner) {
      btxDetails.addActionError(new BtxActionError("error.noPrivilege", "create accounts"));
      return btxDetails;
    }
    //if the user has specified an account id and an account already exists with that id or the
    //id contains invalid characters, return an error
    AccountDto accountData = btxDetails.getAccountData();
    String accountId = accountData.getId();
    if (!ApiFunctions.isEmpty(accountId)) {
      if (!isValidId(accountId)) {
        btxDetails.addActionError(new BtxActionError("error.valueContainsInvalidCharacters",
            "account id", "characters that are not letters, digits, or underscores"));
      }
      AccountDto existingAccount = IltdsUtils.getAccountById(accountId, false, false);
      if (existingAccount != null) {
        btxDetails.addActionError(new BtxActionError("orm.error.accounts.accountAlreadyExists",
            "id", accountId));
      }
    }
    //if the user has specified an account name and an account already exists with that name,
    // return
    //an error
    String accountName = accountData.getName();
    if (!ApiFunctions.isEmpty(accountName)) {
      AccountDto existingAccount = IltdsUtils.getAccountByName(accountName, false, false);
      if (existingAccount != null) {
        btxDetails.addActionError(new BtxActionError("orm.error.accounts.accountAlreadyExists",
            "name", accountName));
      }
    }
    return btxDetails;
  }

  /**
   * This is the main BTX entry point for performing the transaction that starts the process of
   * creating an account.
   */
  private BTXDetails performCreateAccountStart(BtxDetailsCreateAccountStart btxDetails)
      throws Exception {
    AccountDto accountData = btxDetails.getAccountData();
    if (accountData == null) {
      accountData = new AccountDto();
      btxDetails.setAccountData(accountData);
    }
    accountData.setStatus(Constants.ACCOUNT_STATUS_ACTIVE);
    accountData.setPasswordPolicy(ArtsConstants.PASSWORD_POLICY_EXPIRES);
    accountData.setPasswordLifeSpan(30 + "");
    accountData.setViewLinkedCasesOnly(FormLogic.DB_NO);
    accountData.setRequireUniqueSampleAliases(FormLogic.DB_YES);
    accountData.setRequireSampleAliases(FormLogic.DB_YES);
    accountData.setContact(new ContactDto());
    btxDetails.setActionForwardTXCompleted("success");
    return btxDetails;
  }

  /**
   * Do BTX transaction validation for the performCreateAccount performer method.
   */
  private BTXDetails validatePerformCreateAccount(BtxDetailsCreateAccount btxDetails)
      throws Exception {
    //first, perform the same validation we did when starting the process of
    //creating an account. if that fails, don't bother with any further validation
    validatePerformCreateAccountStart(btxDetails);
    if (!btxDetails.getActionErrors().empty()) {
      return btxDetails;
    }

    AccountDto accountData = btxDetails.getAccountData();

    //validate account id. Note that the check to see if an account with this id or name
    //already exists is performed in validatePerformCreateAccountStart so it doesn't need to
    //be done here
    accountData.setId(ApiFunctions.safeTrim(accountData.getId()));
    String accountId = accountData.getId();
    if (ApiFunctions.isEmpty(accountId)) {
      btxDetails.addActionError(new BtxActionError("error.noValue", "Account Id"));
    }
    else {
      if (accountId.length() > MAXIMUM_ACCOUNT_ID_LENGTH) {
        btxDetails.addActionError(new BtxActionError("error.lengthExceeded", "Account Id",
            MAXIMUM_ACCOUNT_ID_LENGTH + ""));
      }
    }

    //call method to validate common account information
    validateCommonAccountInformation(btxDetails);
    return btxDetails;
  }

  /**
   * This is the main BTX entry point for performing the transaction that creates an account.
   */
  private BTXDetails performCreateAccount(BtxDetailsCreateAccount btxDetails) throws Exception {
    AccountDto accountData = btxDetails.getAccountData();
    createAccount(accountData);
    btxDetails.setActionForwardTXCompleted("success");
    return btxDetails;
  }

  /**
   * Do BTX transaction validation for the performModifyAccountStart performer method.
   */
  private BTXDetails validatePerformModifyAccountStart(BtxDetailsModifyAccount btxDetails)
      throws Exception {
    //make sure the user has the Account Management privilege
    boolean hasPriv = btxDetails.getLoggedInUserSecurityInfo().isHasPrivilege(
        SecurityInfo.PRIV_ORM_ACCOUNT_MANAGEMENT);
    if (!hasPriv) {
      btxDetails.addActionError(new BtxActionError("error.noPrivilege", "manage accounts"));
      return btxDetails;
    }

    //make sure an account id has been specified, that the user is authorized to
    //modify that account, and that the account exists
    String accountId = ApiFunctions.safeTrim(btxDetails.getAccountData().getId());
    if (ApiFunctions.isEmpty(accountId)) {
      btxDetails.addActionError(new BtxActionError("error.noValue", "account id"));
    }
    else {
      //if the user is not in the system owner account and they have specified an account
      //not their own, return an error
      boolean isSystemOwner = btxDetails.getLoggedInUserSecurityInfo().isInRoleSystemOwner();
      if (!isSystemOwner) {
        if (!accountId.equalsIgnoreCase(btxDetails.getLoggedInUserSecurityInfo().getAccount()))
            btxDetails.addActionError(new BtxActionError("orm.error.accounts.invalidAccountId",
                accountId));
      }
      //make sure the account exists
      AccountDto account = IltdsUtils.getAccountById(accountId, false, false);
      if (account == null) {
        btxDetails
            .addActionError(new BtxActionError("orm.error.account.accountNotFound", accountId));
      }
    }
    return btxDetails;
  }

  /**
   * This is the main BTX entry point for performing the transaction that starts the process of
   * modifying an account.
   */
  private BTXDetails performModifyAccountStart(BtxDetailsModifyAccountStart btxDetails)
      throws Exception {
    String accountId = ApiFunctions.safeTrim(btxDetails.getAccountData().getId());
    AccountDto accountData = IltdsUtils.getAccountById(accountId, true, false);
    btxDetails.setAccountData(accountData);
    //get the donor registration form choices
    populateDonorRegistrationFormChoices(btxDetails);
    btxDetails.setActionForwardTXCompleted("success");
    return btxDetails;
  }

  private void populateDonorRegistrationFormChoices(BtxDetailsModifyAccount btxDetails) {
    List formDefs = FormUtils.getRegistrationFormDefinitions(btxDetails.getAccountData().getId(),
        TagTypes.DOMAIN_OBJECT_VALUE_DONOR);
    if (!ApiFunctions.isEmpty(formDefs)) {
      btxDetails.setDonorRegistrationFormChoices(FormUtils.getFormsAsLVS(formDefs, true));
    }
    else {
      LegalValueSet noForms = new LegalValueSet();
      noForms.addLegalValue("", "None Available");
      btxDetails.setDonorRegistrationFormChoices(noForms);
    }
  }

  /**
   * Do BTX transaction validation for the performModifyAccount performer method.
   */
  private BTXDetails validatePerformModifyAccount(BtxDetailsModifyAccount btxDetails)
      throws Exception {
    //first, perform the same validation we did when starting the process of
    //creating an account. if that fails, don't bother with any further validation
    validatePerformModifyAccountStart(btxDetails);
    if (!btxDetails.getActionErrors().empty()) {
      populateDonorRegistrationFormChoices(btxDetails);
      return btxDetails;
    }
    //make sure the name is not in use by any other account
    String name = btxDetails.getAccountData().getName();
    if (!ApiFunctions.isEmpty(name)) {
      AccountDto existingAccount = IltdsUtils.getAccountByName(name, false, false);
      if (existingAccount != null
          && !existingAccount.getId().equalsIgnoreCase(btxDetails.getAccountData().getId())) {
        btxDetails.addActionError(new BtxActionError("orm.error.accounts.accountAlreadyExists",
            "name", name));
      }
    }

    AccountDto accountData = btxDetails.getAccountData();

    //call method to validate common account information
    validateCommonAccountInformation(btxDetails);

    AccountDto currentAccount = IltdsUtils.getAccountById(accountData.getId(), false, false);
    //disallow certain fields from being updated if the user is not in the system owner role
    if (!btxDetails.getLoggedInUserSecurityInfo().isInRoleSystemOwner()) {
      if (!ApiFunctions.safeString(accountData.getStatus()).equalsIgnoreCase(
          ApiFunctions.safeString(currentAccount.getStatus()))) {
        btxDetails.addActionError(new BtxActionError("orm.error.account.dataChangeNotAllowed",
            "the status"));
      }
      if (!ApiFunctions.safeString(accountData.getType()).equalsIgnoreCase(
          ApiFunctions.safeString(currentAccount.getType()))) {
        btxDetails.addActionError(new BtxActionError("orm.error.account.dataChangeNotAllowed",
            "the type"));
      }
      if (!ApiFunctions.safeString(accountData.getViewLinkedCasesOnly()).equalsIgnoreCase(
          ApiFunctions.safeString(currentAccount.getViewLinkedCasesOnly()))) {
        btxDetails.addActionError(new BtxActionError("orm.error.account.dataChangeNotAllowed",
            "the view linked cases only value"));
      }
    }

    //validate account type change, if any. The rule is that accounts can be
    //"upgraded" (i.e. Customer can be changed to Donor or System Owner, Donor
    //can be changed to System Owner), but cannot be "downgraded" (i.e. Donor
    //cannot be changed to Customer). This prevents the need to deal with
    //location and storage deletion issues, as well as a System Owner account
    //being changed to non-System Owner and then not being able to change it
    //back (since only System Owners can change account types)
    String newAccountType = ApiFunctions.safeString(accountData.getType());
    String currentAccountType = ApiFunctions.safeString(currentAccount.getType());
    if (!newAccountType.equalsIgnoreCase(currentAccountType)) {
      //system owner accounts cannot be changed
      if (Constants.ACCOUNT_TYPE_SYSTEM_OWNER.equalsIgnoreCase(currentAccountType)) {
        btxDetails.addActionError(new BtxActionError("orm.error.account.typeChangeNotAllowed",
            (String) Constants.ACCOUNT_TYPE_MAP.get(currentAccountType),
            (String) Constants.ACCOUNT_TYPE_MAP.get(newAccountType)));
      }
      //donor accounts cannot be changed to customer
      else if (Constants.ACCOUNT_TYPE_DONOR_AND_CONSUMER.equalsIgnoreCase(currentAccountType)) {
        if (Constants.ACCOUNT_TYPE_CONSUMER.equalsIgnoreCase(newAccountType)) {
          btxDetails.addActionError(new BtxActionError("orm.error.account.typeChangeNotAllowed",
              (String) Constants.ACCOUNT_TYPE_MAP.get(currentAccountType),
              (String) Constants.ACCOUNT_TYPE_MAP.get(newAccountType)));
        }
      }
    }

    //if we're returning the user to the modify account page due to errors, populate the
    //donor registration form choices
    if (!btxDetails.getActionErrors().empty()) {
      populateDonorRegistrationFormChoices(btxDetails);
    }
    return btxDetails;
  }

  /**
   * This is the main BTX entry point for performing the transaction that modifies an account.
   */
  private BTXDetails performModifyAccount(BtxDetailsModifyAccount btxDetails) throws Exception {
    AccountDto accountData = btxDetails.getAccountData();
    modifyAccount(accountData);
    btxDetails.getActionMessages()
        .add(new BtxActionMessage("orm.message.accounts.accountModified"));
    //get the donor registration form choices
    populateDonorRegistrationFormChoices(btxDetails);
    btxDetails.setActionForwardTXCompleted("success");
    return btxDetails;
  }

  /**
   * Do BTX transaction validation for the performManageAccountLocations performer method.
   */
  private BTXDetails validatePerformManageAccountLocations(BtxDetailsAccountManagement btxDetails)
      throws Exception {
    //make sure the user has the Account Management privilege
    boolean hasPriv = btxDetails.getLoggedInUserSecurityInfo().isHasPrivilege(
        SecurityInfo.PRIV_ORM_ACCOUNT_MANAGEMENT);
    if (!hasPriv) {
      btxDetails.addActionError(new BtxActionError("error.noPrivilege", "manage accounts"));
      return btxDetails;
    }

    //make sure an account id has been specified, that the user is authorized to
    //modify that account, and that the account exists
    String accountId = ApiFunctions.safeTrim(btxDetails.getAccountData().getId());
    if (ApiFunctions.isEmpty(accountId)) {
      btxDetails.addActionError(new BtxActionError("error.noValue", "account id"));
    }
    else {
      //if the user is not in the system owner account and they have specified an account
      //not their own, return an error
      boolean isSystemOwner = btxDetails.getLoggedInUserSecurityInfo().isInRoleSystemOwner();
      if (!isSystemOwner) {
        if (!accountId.equalsIgnoreCase(btxDetails.getLoggedInUserSecurityInfo().getAccount()))
            btxDetails.addActionError(new BtxActionError("orm.error.accounts.invalidAccountId",
                accountId));
      }
      //make sure the account exists
      AccountDto account = IltdsUtils.getAccountById(accountId, false, false);
      if (account == null) {
        btxDetails
            .addActionError(new BtxActionError("orm.error.account.accountNotFound", accountId));
      }
    }
    return btxDetails;
  }

  /**
   * This is the main BTX entry point for performing the transaction that manages the locations for
   * an account.
   */
  private BTXDetails performManageAccountLocations(BtxDetailsManageAccountLocations btxDetails)
      throws Exception {
    btxDetails.setAccountData(IltdsUtils.getAccountById(btxDetails.getAccountData().getId(), false, true));
    //for now, if the account does not have a primary location then go to create location. If it
    //does, then go to modify location. Once location granularity is implemented this method
    //will go to a page listing the locations for an account, but since there can currently be
    //just a single location for an account it doesn't make sense to go to such a page.
    if (ApiFunctions.isEmpty(btxDetails.getAccountData().getPrimaryLocation())) {
      btxDetails.setActionForwardTXCompleted("createLocation");
    }
    else {
      btxDetails.setActionForwardTXCompleted("modifyLocation");
    }
    return btxDetails;
  }

  /**
   * Do BTX transaction validation for the performCreateAccountLocationStart performer method.
   */
  private BTXDetails validatePerformCreateAccountLocationStart(
                                                               BtxDetailsCreateAccountLocationStart btxDetails)
      throws Exception {
    //perform the same validation that was done for the start of account location management
    validatePerformManageAccountLocations(btxDetails);
    return btxDetails;
  }

  /**
   * This is the main BTX entry point for performing the transaction that begins the creation of a
   * new location for an account.
   */
  private BTXDetails performCreateAccountLocationStart(
                                                       BtxDetailsCreateAccountLocationStart btxDetails)
      throws Exception {
    btxDetails.setAccountData(IltdsUtils.getAccountById(btxDetails.getAccountData().getId(), false, true));
    btxDetails.setActionForwardTXCompleted("success");
    return btxDetails;
  }

  /**
   * Do BTX transaction validation for the performCreateAccountLocation performer method.
   */
  private BTXDetails validatePerformCreateAccountLocation(BtxDetailsCreateAccountLocation btxDetails)
      throws Exception {
    validateCommonAccountLocationData(btxDetails);
    //make sure the id is not in use by any other location
    String addressId = btxDetails.getAccountData().getLocation().getAddressId();
    if (!ApiFunctions.isEmpty(addressId)) {
      LocationData existingLocation = IltdsUtils.getLocationById(addressId, false);
      if (existingLocation != null) {
        btxDetails.addActionError(new BtxActionError("orm.error.account.locationAlreadyExists",
            "id", addressId));
      }
    }
    //make sure the name is not in use by any other location
    String name = btxDetails.getAccountData().getLocation().getName();
    if (!ApiFunctions.isEmpty(name)) {
      LocationData existingLocation = IltdsUtils.getLocationByName(name, false);
      if (existingLocation != null) {
        btxDetails.addActionError(new BtxActionError("orm.error.account.locationAlreadyExists",
            "name", name));
      }
    }
    return btxDetails;
  }

  /**
   * This is the main BTX entry point for performing the transaction that creates a new location for
   * an account.
   */
  private BTXDetails performCreateAccountLocation(BtxDetailsCreateAccountLocation btxDetails)
      throws Exception {
    createAccountLocation(btxDetails.getAccountData());
    createStorageUnits(btxDetails.getAccountData().getLocation());
    btxDetails.getActionMessages().add(
        new BtxActionMessage("orm.message.accounts.locationAction", "created"));
    btxDetails.setActionForwardTXCompleted("success");
    return btxDetails;
  }

  /**
   * Do BTX transaction validation for the performModifyAccountLocationStart performer method.
   */
  private BTXDetails validatePerformModifyAccountLocationStart(
                                                               BtxDetailsModifyAccountLocationStart btxDetails)
      throws Exception {
    //perform the same validation that was done for the start of account location management
    validatePerformManageAccountLocations(btxDetails);
    //make sure the id corresponds to an existing location
    String addressId = btxDetails.getAccountData().getLocation().getAddressId();
    if (!ApiFunctions.isEmpty(addressId)) {
      LocationData existingLocation = IltdsUtils.getLocationById(addressId, false);
      if (existingLocation == null) {
        btxDetails.addActionError(new BtxActionError("orm.error.account.locationDoesNotExists",
            addressId));
      }
    }
    return btxDetails;
  }

  /**
   * This is the main BTX entry point for performing the transaction that begins the modification of
   * an existing location for an account.
   */
  private BTXDetails performModifyAccountLocationStart(
                                                       BtxDetailsModifyAccountLocationStart btxDetails)
      throws Exception {
    btxDetails.setAccountData(IltdsUtils.getAccountById(btxDetails.getAccountData().getId(), false, true));
    btxDetails.setActionForwardTXCompleted("success");
    return btxDetails;
  }

  /**
   * Do BTX transaction validation for the performModifyAccountLocation performer method.
   */
  private BTXDetails validatePerformModifyAccountLocation(BtxDetailsModifyAccountLocation btxDetails)
      throws Exception {
    validateCommonAccountLocationData(btxDetails);
    //make sure the id corresponds to an existing location
    String addressId = btxDetails.getAccountData().getLocation().getAddressId();
    if (!ApiFunctions.isEmpty(addressId)) {
      LocationData existingLocation = IltdsUtils.getLocationById(addressId, false);
      if (existingLocation == null) {
        btxDetails.addActionError(new BtxActionError("orm.error.account.locationDoesNotExists",
            addressId));
      }
    }
    //make sure the name is not in use by any other location
    String name = btxDetails.getAccountData().getLocation().getName();
    if (!ApiFunctions.isEmpty(name)) {
      LocationData existingLocation = IltdsUtils.getLocationByName(name, false);
      if (existingLocation != null
          && !existingLocation.getAddressId().equalsIgnoreCase(
              btxDetails.getAccountData().getLocation().getAddressId())) {
        btxDetails.addActionError(new BtxActionError("orm.error.account.locationAlreadyExists",
            "name", name));
      }
    }

    //if there are errors, retrieve the existing storage unit information since we'll need to
    //display it.
    if (!btxDetails.getActionErrors().empty()) {
      AccountDto existingInfo = IltdsUtils.getAccountById(btxDetails.getAccountData().getId(), false, true);
      btxDetails.getAccountData().getLocation().setExistingStorageUnits(
          existingInfo.getLocation().getExistingStorageUnits());
    }
    return btxDetails;
  }

  /**
   * This is the main BTX entry point for performing the transaction that modifies an existing
   * location for an account.
   */
  private BTXDetails performModifyAccountLocation(BtxDetailsModifyAccountLocation btxDetails)
      throws Exception {
    modifyAccountLocation(btxDetails.getAccountData());
    createStorageUnits(btxDetails.getAccountData().getLocation());
    btxDetails.getActionMessages().add(
        new BtxActionMessage("orm.message.accounts.locationAction", "modified"));
    btxDetails.setActionForwardTXCompleted("success");
    return btxDetails;
  }

  /**
   * Do BTX transaction validation for the performManageAccountLocations performer method.
   */
  private BTXDetails validateCommonAccountLocationData(BtxDetailsAccountManagement btxDetails)
      throws Exception {
    //perform the same validation that was done for the start of account location management
    validatePerformManageAccountLocations(btxDetails);

    LocationData locationData = btxDetails.getAccountData().getLocation();

    //validate the address id
    locationData.setAddressId(ApiFunctions.safeTrim(locationData.getAddressId()));
    String addressId = locationData.getAddressId();
    if (ApiFunctions.isEmpty(addressId)) {
      btxDetails.addActionError(new BtxActionError("error.noValue", "Location Id"));
    }
    else {
      //location ids can only consist of alphanumeric characters and underscores
      if (!isValidId(addressId)) {
        btxDetails.addActionError(new BtxActionError("error.valueContainsInvalidCharacters",
            "location id", "characters that are not letters, digits, or underscores"));
      }
      else {
        if (addressId.length() > MAXIMUM_LOCATION_ID_LENGTH) {
          btxDetails.addActionError(new BtxActionError("error.lengthExceeded", "Location Id",
              MAXIMUM_LOCATION_ID_LENGTH + ""));
        }
      }
    }

    //validate the location name
    locationData.setName(ApiFunctions.safeTrim(locationData.getName()));
    String locationName = locationData.getName();
    if (ApiFunctions.isEmpty(locationName)) {
      btxDetails.addActionError(new BtxActionError("error.noValue", "Location Name"));
    }
    else {
      if (locationName.length() > MAXIMUM_LOCATION_NAME_LENGTH) {
        btxDetails.addActionError(new BtxActionError("error.lengthExceeded", "Location Name",
            MAXIMUM_LOCATION_NAME_LENGTH + ""));
      }
    }

    //validate the address
    locationData.setAddress1(ApiFunctions.safeTrim(locationData.getAddress1()));
    String address1 = locationData.getAddress1();
    if (address1.length() > MAXIMUM_LOCATION_ADDRESS_LENGTH) {
      btxDetails.addActionError(new BtxActionError("error.lengthExceeded", "Address 1",
          MAXIMUM_LOCATION_ADDRESS_LENGTH + ""));
    }
    locationData.setAddress2(ApiFunctions.safeTrim(locationData.getAddress2()));
    String address2 = locationData.getAddress2();
    if (address2.length() > MAXIMUM_LOCATION_ADDRESS_LENGTH) {
      btxDetails.addActionError(new BtxActionError("error.lengthExceeded", "Address 2",
          MAXIMUM_LOCATION_ADDRESS_LENGTH + ""));
    }

    //validate the city
    locationData.setCity(ApiFunctions.safeTrim(locationData.getCity()));
    String city = locationData.getCity();
    if (city.length() > MAXIMUM_LOCATION_CITY_LENGTH) {
      btxDetails.addActionError(new BtxActionError("error.lengthExceeded", "City",
          MAXIMUM_LOCATION_CITY_LENGTH + ""));
    }

    //validate the state
    locationData.setState(ApiFunctions.safeTrim(locationData.getState()));
    String state = locationData.getState();
    if (state.length() > MAXIMUM_LOCATION_STATE_LENGTH) {
      btxDetails.addActionError(new BtxActionError("error.lengthExceeded", "State",
          MAXIMUM_LOCATION_STATE_LENGTH + ""));
    }

    //validate the zip code
    locationData.setZipCode(ApiFunctions.safeTrim(locationData.getZipCode()));
    String zipCode = locationData.getZipCode();
    if (zipCode.length() > MAXIMUM_LOCATION_ZIPCODE_LENGTH) {
      btxDetails.addActionError(new BtxActionError("error.lengthExceeded", "Postal Code",
          MAXIMUM_LOCATION_ZIPCODE_LENGTH + ""));
    }

    //validate the country
    locationData.setCountry(ApiFunctions.safeTrim(locationData.getCountry()));
    String country = locationData.getCountry();
    if (country.length() > MAXIMUM_LOCATION_COUNTRY_LENGTH) {
      btxDetails.addActionError(new BtxActionError("error.lengthExceeded", "Country",
          MAXIMUM_LOCATION_COUNTRY_LENGTH + ""));
    }

    //validate the phone number
    locationData.setPhoneNumber(ApiFunctions.safeTrim(locationData.getPhoneNumber()));
    String phoneNumber = locationData.getPhoneNumber();
    if (phoneNumber.length() > MAXIMUM_LOCATION_PHONE_NUMBER_LENGTH) {
      btxDetails.addActionError(new BtxActionError("error.lengthExceeded", "Phone Number",
          MAXIMUM_LOCATION_PHONE_NUMBER_LENGTH + ""));
    }

    //remove any empty values from the list of new storage locations. This makes for cleaner code
    //in the performer method and prevents empty rows from being "restored" if we return to the
    //UI due to error.
    Iterator newStorageUnitIterator = locationData.getNewStorageUnits().iterator();
    while (newStorageUnitIterator.hasNext()) {
      StorageUnitDto storageUnit = (StorageUnitDto) newStorageUnitIterator.next();
      if (isStorageUnitEmpty(storageUnit)) {
        newStorageUnitIterator.remove();
      }
    }

    //get the list of any existing storage units to make sure the user isn't adding
    //any new unit with the same room and unit name.
    List existingStorageUnits;
    LocationData existingLocation = IltdsUtils.getLocationById(locationData.getAddressId(), true);
    if (existingLocation != null) {
      existingStorageUnits = existingLocation.getExistingStorageUnits();
    }
    else {
      existingStorageUnits = new ArrayList();
    }

    //keep track of the room and unit names being added to make sure there are
    //no duplicates
    Map roomAndUnitNames = new HashMap();

    List newStorageUnits = locationData.getNewStorageUnits();
    for (int i = 0; i < newStorageUnits.size(); i++) {
      StorageUnitDto storageUnit = (StorageUnitDto) newStorageUnits.get(i);
      //first get the room/unit combo to identify the unit in error messages (MR8742)
      storageUnit.setRoomId(ApiFunctions.safeTrim(storageUnit.getRoomId()));
      storageUnit.setUnitName(ApiFunctions.safeTrim(storageUnit.getUnitName()));
      String roomId = storageUnit.getRoomId();
      String unitName = storageUnit.getUnitName();
      StringBuffer buff = new StringBuffer(50);
      buff.append("Room: ");
      if (!ApiFunctions.isEmpty(roomId)) {
        buff.append(roomId);
      }
      else {
        buff.append("<unspecified>");
      }
      buff.append(", Unit: ");
      if (!ApiFunctions.isEmpty(unitName)) {
        buff.append(unitName);
      }
      else {
        buff.append("<unspecified>");
      }
      String storageUnitIdentifier = buff.toString();
      //validate type
      storageUnit.setStorageTypeCui(ApiFunctions.safeTrim(storageUnit.getStorageTypeCui()));
      String unitType = storageUnit.getStorageTypeCui();
      if (ApiFunctions.isEmpty(unitType)) {
        btxDetails.addActionError(new BtxActionError("error.noValue",
            "Storage Type on new storage unit " + storageUnitIdentifier));
      }
      else {
        BigrConceptList legalValues = SystemConfiguration
            .getConceptList(SystemConfiguration.CONCEPT_LIST_STORAGE_TYPES);
        if (!legalValues.containsConcept(unitType)) {
          btxDetails.addActionError(new BtxActionError("error.valueNotInListNoSuggestions",
              "Storage Type on new storage unit " + storageUnitIdentifier));
        }
      }
      //validate room
      if (ApiFunctions.isEmpty(roomId)) {
        btxDetails.addActionError(new BtxActionError("error.noValue", "Room on new storage unit "
            + storageUnitIdentifier));
      }
      else {
        if (roomId.length() > MAXIMUM_STORAGE_UNIT_ROOM_LENGTH) {
          btxDetails.addActionError(new BtxActionError("error.lengthExceeded",
              "Room on new storage unit " + storageUnitIdentifier, MAXIMUM_STORAGE_UNIT_ROOM_LENGTH
                  + ""));
        }
      }
      //validate unit
      if (ApiFunctions.isEmpty(unitName)) {
        btxDetails.addActionError(new BtxActionError("error.noValue",
            "Storage Unit on new storage unit " + storageUnitIdentifier));
      }
      else {
        if (unitName.length() > MAXIMUM_STORAGE_UNIT_UNIT_LENGTH) {
          btxDetails.addActionError(new BtxActionError("error.lengthExceeded",
              "Storage Unit on new storage unit " + storageUnitIdentifier,
              MAXIMUM_STORAGE_UNIT_UNIT_LENGTH + ""));
        }
      }
      //make sure the room id and unit name combination is not a duplicate within the new units
      String roomAndUnit = roomId + unitName;
      if (roomAndUnitNames.containsKey(roomAndUnit)) {
        btxDetails.addActionError(new BtxActionError("error.duplicatedEntities",
            "room and unit name combinations", storageUnitIdentifier));
      }
      else {
        roomAndUnitNames.put(roomAndUnit, roomAndUnit);
      }
      //make sure the room id and unit name combination is not a duplicate within the existing
      // units
      Iterator existingUnitIterator = existingStorageUnits.iterator();
      boolean duplicateFound = false;
      while (existingUnitIterator.hasNext() && !duplicateFound) {
        StorageUnitSummaryDto existingUnit = (StorageUnitSummaryDto) existingUnitIterator.next();
        if (ApiFunctions.safeTrim(existingUnit.getRoomId()).equalsIgnoreCase(roomId)
            && ApiFunctions.safeTrim(existingUnit.getUnitName()).equalsIgnoreCase(unitName)) {
          duplicateFound = true;
        }
      }
      if (duplicateFound) {
        btxDetails.addActionError(new BtxActionError("orm.error.account.storageUnitAlreadyExists",
            roomId, unitName));
      }
      //validate number of drawers
      storageUnit.setNumberOfDrawers(ApiFunctions.safeTrim(storageUnit.getNumberOfDrawers()));
      String numberOfDrawers = storageUnit.getNumberOfDrawers();
      if (ApiFunctions.isEmpty(numberOfDrawers)) {
        btxDetails.addActionError(new BtxActionError("error.noValue",
            "Number of Drawers on new storage unit " + storageUnitIdentifier));
      }
      else {
        if (numberOfDrawers.length() > MAXIMUM_STORAGE_UNIT_NUMBER_OF_DRAWERS_LENGTH) {
          btxDetails.addActionError(new BtxActionError("error.lengthExceeded",
              "Number of Drawers on new storage unit " + storageUnitIdentifier,
              MAXIMUM_STORAGE_UNIT_NUMBER_OF_DRAWERS_LENGTH + ""));
        }
        if (!ApiFunctions.isPositiveInteger(numberOfDrawers)) {
          btxDetails.addActionError(new BtxActionError("error.numericValueOnly",
              "Number of Drawers on new storage unit " + storageUnitIdentifier,
              MAXIMUM_STORAGE_UNIT_NUMBER_OF_DRAWERS_LENGTH + ""));
        }
        else {
          if (Integer.parseInt(numberOfDrawers) < MINIMUM_STORAGE_UNIT_NUMBER_OF_DRAWERS) {
            btxDetails.addActionError(new BtxActionError("error.belowmin",
                "Number of Drawers on new storage unit " + storageUnitIdentifier, numberOfDrawers,
                MINIMUM_STORAGE_UNIT_NUMBER_OF_DRAWERS + ""));
          }
          if (Integer.parseInt(numberOfDrawers) > MAXIMUM_STORAGE_UNIT_NUMBER_OF_DRAWERS) {
            btxDetails.addActionError(new BtxActionError("error.abovemax",
                "Number of Drawers on new storage unit " + storageUnitIdentifier, numberOfDrawers,
                MAXIMUM_STORAGE_UNIT_NUMBER_OF_DRAWERS + ""));
          }

        }
      }
      //validate slots per drawer
      storageUnit.setSlotsPerDrawer(ApiFunctions.safeTrim(storageUnit.getSlotsPerDrawer()));
      String slotsPerDrawer = storageUnit.getSlotsPerDrawer();
      if (ApiFunctions.isEmpty(slotsPerDrawer)) {
        btxDetails.addActionError(new BtxActionError("error.noValue",
            "Slots per Drawer on new storage unit " + storageUnitIdentifier));
      }
      else {
        if (!ApiFunctions.isPositiveInteger(slotsPerDrawer)) {
          btxDetails.addActionError(new BtxActionError("error.numericValueOnly",
              "Slots per Drawer on new storage unit " + storageUnitIdentifier));
        }
        else {
          if (Integer.parseInt(slotsPerDrawer) < MINIMUM_STORAGE_UNIT_SLOTS_PER_DRAWER) {
            btxDetails.addActionError(new BtxActionError("error.belowmin",
                "Slots per Drawer on new storage unit " + storageUnitIdentifier, slotsPerDrawer,
                MINIMUM_STORAGE_UNIT_SLOTS_PER_DRAWER + ""));
          }
          if (Integer.parseInt(slotsPerDrawer) > MAXIMUM_STORAGE_UNIT_SLOTS_PER_DRAWER) {
            btxDetails.addActionError(new BtxActionError("error.abovemax",
                "Slots per Drawer on new storage unit " + storageUnitIdentifier, slotsPerDrawer,
                MAXIMUM_STORAGE_UNIT_SLOTS_PER_DRAWER + ""));
          }
        }
      }
    }
    return btxDetails;
  }

  /**
   * This is the main BTX entry point for performing the transaction that starts the process of
   * changing a user's profile.
   */
  private BTXDetails performChangeProfileStart(BtxDetailsChangeProfileStart btxDetails)
      throws Exception {
    String userId = btxDetails.getLoggedInUserSecurityInfo().getUsername();
    String accountId = btxDetails.getLoggedInUserSecurityInfo().getAccount();
    try {
      ArdaisuserAccessBean ardaisUserEB = new ArdaisuserAccessBean(new ArdaisuserKey(userId,
          new ArdaisaccountKey(accountId)));
      String addressId = String.valueOf(ardaisUserEB.getUser_address_id());
      ArdaisaddressAccessBean ardaisAddressEB = new ArdaisaddressAccessBean(new ArdaisaddressKey(
          new BigDecimal(addressId), accountId));
      UserDto userData = new UserDto(ardaisUserEB);
      userData.setAddress(new LocationData(ardaisAddressEB));
      btxDetails.setUserData(userData);
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    btxDetails.setActionForwardTXCompleted("success");
    return btxDetails;
  }

  /**
   * Do BTX transaction validation for the performChangeProfile performer method.
   */
  private BTXDetails validatePerformChangeProfile(BtxDetailsChangeProfile btxDetails)
      throws Exception {
    validateCommonUserInformation(btxDetails);

    UserDto userData = btxDetails.getUserData();
    //validate password question and answer
    userData.setPasswordQuestion(ApiFunctions.safeTrim(userData.getPasswordQuestion()));
    String passwordQuestion = ApiFunctions.safeString(userData.getPasswordQuestion());
    if (!ApiFunctions.isEmpty(passwordQuestion)) {
      if (passwordQuestion.length() > MAXIMUM_PASSWORD_QUESTION_LENGTH) {
        btxDetails.addActionError(new BtxActionError("error.lengthExceeded",
            "Password Verification Question", MAXIMUM_PASSWORD_QUESTION_LENGTH + ""));
      }
    }
    userData.setPasswordAnswer(ApiFunctions.safeTrim(userData.getPasswordAnswer()));
    String passwordAnswer = ApiFunctions.safeString(userData.getPasswordAnswer());
    if (!ApiFunctions.isEmpty(passwordAnswer)) {
      if (passwordAnswer.length() > MAXIMUM_PASSWORD_ANSWER_LENGTH) {
        btxDetails.addActionError(new BtxActionError("error.lengthExceeded",
            "Password Verification Answer", MAXIMUM_PASSWORD_ANSWER_LENGTH + ""));
      }
    }
    //make sure that if a question is specified then an answer is specified, and vice versa
    if ((!ApiFunctions.isEmpty(passwordQuestion) && ApiFunctions.isEmpty(passwordAnswer))
        || (ApiFunctions.isEmpty(passwordQuestion) && !ApiFunctions.isEmpty(passwordAnswer))) {
      btxDetails.addActionError(new BtxActionError(
          "orm.error.users.missingPasswordVerificationData"));
    }

    return btxDetails;
  }

  /**
   * This is the main BTX entry point for performing the transaction that modifies a user's profile.
   */
  private BTXDetails performChangeProfile(BtxDetailsChangeProfile btxDetails) throws Exception {
    UserDto userData = btxDetails.getUserData();
    LocationData address = userData.getAddress();
    String userId = btxDetails.getLoggedInUserSecurityInfo().getUsername();
    String accountId = btxDetails.getLoggedInUserSecurityInfo().getAccount();
    String oldPasswordQuestion = null;
    String oldPasswordAnswer = null;
    try {
      ArdaisuserAccessBean ardaisUserEB = new ArdaisuserAccessBean(new ArdaisuserKey(userId,
          new ArdaisaccountKey(accountId)));
      //store the value of the old password question/answer, so we can determine what to store in
      //the history record
      oldPasswordQuestion = ApiFunctions.safeString(ardaisUserEB.getUser_verif_question());
      btxDetails.setOldPasswordQuestion(oldPasswordQuestion);
      oldPasswordAnswer = ApiFunctions.safeString(ardaisUserEB.getUser_verif_answer());
      btxDetails.setOldPasswordAnswer(oldPasswordAnswer);
      //make the updates
      ardaisUserEB.setUser_firstname(userData.getFirstName());
      ardaisUserEB.setUser_lastname(userData.getLastName());
      ardaisUserEB.setUser_email_address(userData.getEmail());
      ardaisUserEB.setUser_department(userData.getDepartment());
      ardaisUserEB.setUser_title(userData.getTitle());
      ardaisUserEB.setUser_functional_title(userData.getFunctionalTitle());
      ardaisUserEB.setUser_affiliation(userData.getAffiliation());
      ardaisUserEB.setUser_phone_number(userData.getPhoneNumber());
      ardaisUserEB.setUser_phone_ext(userData.getExtension());
      ardaisUserEB.setUser_fax_number(userData.getFaxNumber());
      ardaisUserEB.setUser_mobile_number(userData.getMobileNumber());
      ardaisUserEB.setUser_verif_question(userData.getPasswordQuestion());
      ardaisUserEB.setUser_verif_answer(userData.getPasswordAnswer());
      //if either the password question or answer changed, reset the number of failed login
      //attempts and failed security answer attempts made for this user (MR8495)
      if (!oldPasswordQuestion.equalsIgnoreCase(userData.getPasswordQuestion())
          || !oldPasswordAnswer.equalsIgnoreCase(userData.getPasswordAnswer())) {
        ardaisUserEB.setConsecutive_failed_logins(new Integer(0));
        ardaisUserEB.setConsecutive_failed_answers(new Integer(0));
      }
      ardaisUserEB.setUpdated_by(btxDetails.getLoggedInUserSecurityInfo().getUsername());
      ardaisUserEB.setUpdate_date(new java.sql.Timestamp((new java.util.Date()).getTime()));

      int addrID = (ardaisUserEB.getUser_address_id() != null) ? ardaisUserEB.getUser_address_id()
          .intValue() : 0;
      java.math.BigDecimal addressID = new java.math.BigDecimal(String.valueOf(addrID));
      ArdaisaddressAccessBean aradisAddressEB = new ArdaisaddressAccessBean(new ArdaisaddressKey(
          addressID, accountId));
      aradisAddressEB.setAddress_1(address.getAddress1());
      aradisAddressEB.setAddress_2(address.getAddress2());
      aradisAddressEB.setAddr_city(address.getCity());
      aradisAddressEB.setAddr_state(address.getState());
      aradisAddressEB.setAddr_zip_code(address.getZipCode());
      aradisAddressEB.setAddr_country(address.getCountry());
      ardaisUserEB.commitCopyHelper();
      aradisAddressEB.commitCopyHelper();
    }
    catch (Exception e) {
      throw new ApiException();
    }
    btxDetails.getActionMessages().add(new BtxActionMessage("orm.message.users.profileModified"));
    btxDetails.setActionForwardTXCompleted("success");
    //set the userid and account id for the logged in user, so the change password functionality
    //will work (MR 8597)
    btxDetails.getUserData().setAccountId(accountId);
    btxDetails.getUserData().setUserId(userId);
    return btxDetails;
  }

  /**
   * Do BTX transaction validation for the performManagePrivilegesStart performer method.
   */
  private BTXDetails validatePerformManagePrivilegesStart(BtxDetailsManagePrivileges btxDetails) {
    if (Constants.OBJECT_TYPE_ACCOUNT.equalsIgnoreCase(btxDetails.getObjectType())) {
      //make sure the user has the Account Management privilege
      if (!btxDetails.getLoggedInUserSecurityInfo().isHasPrivilege(
          SecurityInfo.PRIV_ORM_ACCOUNT_MANAGEMENT)) {
        btxDetails.addActionError(new BtxActionError("error.noPrivilege", "manage accounts"));
      }
      else {
        validateAccount(btxDetails.getAccountData(), "privileges", btxDetails);
        //make sure the user is a System Owner
        if (!btxDetails.getLoggedInUserSecurityInfo().isInRoleSystemOwner()) {
          btxDetails.addActionError(new BtxActionError("error.noPrivilege",
              "manage the assignable privileges for this account"));
        }
      }
    }
    else if (Constants.OBJECT_TYPE_USER.equalsIgnoreCase(btxDetails.getObjectType())) {
      validateUser(btxDetails.getUserData(), btxDetails);
    }
    else {
      //unexpected object type
      btxDetails.addActionError(new BtxActionError("error.badvalue", btxDetails.getObjectType(),
          "objectType", "recognized"));
    }
    return btxDetails;
  }

  /**
   * Perform a BTX transaction: get the information necessary to start the transaction for managing
   * the privileges for a user or account
   */
  private BtxDetailsManagePrivilegesStart performManagePrivilegesStart(
                                                                       BtxDetailsManagePrivilegesStart btxDetails)
      throws Exception {
    try {
      if (Constants.OBJECT_TYPE_ACCOUNT.equalsIgnoreCase(btxDetails.getObjectType())) {
        String accountId = btxDetails.getAccountData().getId();
        AccountSrchSBHome home = (AccountSrchSBHome) EjbHomes.getHome(AccountSrchSBHome.class);
        AccountSrchSB accountPrivileges = home.create();
        btxDetails.setAssignedPrivileges(accountPrivileges.getPrivilegesAssignedToAccount(accountId));
        OrmUserManagementHome oumHome = (OrmUserManagementHome) EjbHomes.getHome(OrmUserManagementHome.class);
        OrmUserManagement userPrivileges = oumHome.create();
        btxDetails.setAllPrivileges(userPrivileges.getAllPrivileges());
      }
      else if (Constants.OBJECT_TYPE_USER.equalsIgnoreCase(btxDetails.getObjectType())) {
        String userId = btxDetails.getUserData().getUserId();
        String accountId = btxDetails.getUserData().getAccountId();
        //get the privileges explicitly assigned to the user
        OrmUserManagementHome oumHome = (OrmUserManagementHome) EjbHomes.getHome(OrmUserManagementHome.class);
        OrmUserManagement userPrivileges = oumHome.create();
        List assignedPrivileges = userPrivileges.getPrivilegesAssignedToUser(userId, accountId);
        //get the privileges assigned to the user via their membership in account roles
        List roleBasedPrivileges = RoleUtils.getPrivilegesViaRoleMembership(userId);
        //get all of the known privileges
        Map allPrivileges = userPrivileges.getAllPrivileges();
        //If the user is not in a system owner role, alter the assigned privileges and all 
        //privileges to contain only those privileges authorized for the user's account. This is 
        //done so the user only sees privileges they are authorized to view.
        SecurityInfo securityInfo = btxDetails.getLoggedInUserSecurityInfo();
        if (!securityInfo.isInRoleSystemOwner()) {
          String account = securityInfo.getAccount();
          AccountSrchSBHome home = (AccountSrchSBHome) EjbHomes.getHome(AccountSrchSBHome.class);
          AccountSrchSB accountPrivileges = home.create();
          List authorizedPrivileges = accountPrivileges.getPrivilegesAssignedToAccount(account);
          removeUnauthorizedEntities(authorizedPrivileges, assignedPrivileges);
          removeUnauthorizedEntities(authorizedPrivileges, allPrivileges);
        }
        btxDetails.setAssignedPrivileges(assignedPrivileges);
        btxDetails.setRoleBasedPrivileges(roleBasedPrivileges);
        btxDetails.setAllPrivileges(allPrivileges);
      }
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    btxDetails.setPrivilegeFilter(com.ardais.bigr.orm.helpers.FormLogic.ALL);
    btxDetails.setActionForwardTXCompleted("success");
    return btxDetails;
  }

  /**
   * Do BTX transaction validation for the performManagePrivileges performer method.
   */
  private BTXDetails validatePerformManagePrivileges(BtxDetailsManagePrivileges btxDetails) {
    validatePerformManagePrivilegesStart(btxDetails);
    SecurityInfo securityInfo = btxDetails.getLoggedInUserSecurityInfo();
    //make sure the user has not assigned a privilege that is outside the scope of their control
    if (Constants.OBJECT_TYPE_USER.equalsIgnoreCase(btxDetails.getObjectType())) {
      if (!securityInfo.isInRoleSystemOwner()) {
        String accountId = securityInfo.getAccount();
        try {
          AccountSrchSBHome home = (AccountSrchSBHome) EjbHomes.getHome(AccountSrchSBHome.class);
          AccountSrchSB accountPrivileges = home.create();
          List authorizedPrivileges = accountPrivileges.getPrivilegesAssignedToAccount(accountId);
          String[] selectedPrivileges = btxDetails.getSelectedPrivileges();
          List unauthorizedPrivilegeIds = new ArrayList();
          for (int i = 0; i < selectedPrivileges.length; i++) {
            String privId = selectedPrivileges[i];
            Iterator iterator = authorizedPrivileges.iterator();
            boolean found = false;
            PrivilegeDto authorizedPrivilege = null;
            while (!found && iterator.hasNext()) {
              authorizedPrivilege = (PrivilegeDto) iterator.next();
              found = privId.equalsIgnoreCase(authorizedPrivilege.getId());
            }
            if (!found) {
              unauthorizedPrivilegeIds.add(privId);
            }
          }
          if (!ApiFunctions.isEmpty(unauthorizedPrivilegeIds)) {
            OrmUserManagementHome oumHome = (OrmUserManagementHome) EjbHomes.getHome(OrmUserManagementHome.class);
            OrmUserManagement privilegeFinder = oumHome.create();
            List unauthorizedPrivileges = privilegeFinder
                .getPrivilegesById(unauthorizedPrivilegeIds);
            Iterator iterator = unauthorizedPrivileges.iterator();
            boolean addComma = false;
            StringBuffer privilegeNames = new StringBuffer(100);
            while (iterator.hasNext()) {
              if (addComma) {
                privilegeNames.append(", ");
              }
              privilegeNames.append(((PrivilegeDto) iterator.next()).getDescription());
            }
            btxDetails.addActionError(new BtxActionError(
                "orm.error.users.unauthorizedPrivilegesOrGroups", "privileges", privilegeNames
                    .toString()));
          }
        }
        catch (Exception e) {
          ApiFunctions.throwAsRuntimeException(e);
        }
      }
    }
    //make sure every selected privilege is known
    try {
      OrmUserManagementHome oumHome = (OrmUserManagementHome) EjbHomes.getHome(OrmUserManagementHome.class);
      OrmUserManagement privilegeFinder = oumHome.create();
      List knownPrivileges = (List) privilegeFinder.getAllPrivileges().get(
          com.ardais.bigr.orm.helpers.FormLogic.ALL);
      String[] selectedPrivileges = btxDetails.getSelectedPrivileges();
      List unknownPrivilegeIds = new ArrayList();
      for (int i = 0; i < selectedPrivileges.length; i++) {
        String privId = selectedPrivileges[i];
        Iterator iterator = knownPrivileges.iterator();
        boolean found = false;
        PrivilegeDto privilege = null;
        while (!found && iterator.hasNext()) {
          privilege = (PrivilegeDto) iterator.next();
          found = privId.equalsIgnoreCase(privilege.getId());
        }
        if (!found) {
          unknownPrivilegeIds.add(privId);
        }
      }
      if (!ApiFunctions.isEmpty(unknownPrivilegeIds)) {
        btxDetails.addActionError(new BtxActionError("orm.error.users.unknownPrivilegesOrGroups",
            "privileges", ApiFunctions.toCommaSeparatedList(ApiFunctions
                .toStringArray(unknownPrivilegeIds))));
      }
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    //if any errors are going to be returned, set up the privilege info the UI will need
    if (!btxDetails.getActionErrors().empty()) {
      try {
        //set the assigned privileges to be the selected privileges, since those are the ones
        //that should show up in the right hand side (so that the user is returned to the page
        //in the same state they left it)
        List safeToList = ApiFunctions.safeToList(btxDetails.getSelectedPrivileges());
        OrmUserManagementHome oumHome = (OrmUserManagementHome) EjbHomes.getHome(OrmUserManagementHome.class);
        OrmUserManagement privilegeFinder = oumHome.create();
        btxDetails.setAssignedPrivileges(privilegeFinder.getPrivilegesById(safeToList));
        Map allPrivileges = privilegeFinder.getAllPrivileges();
        //If a user is being updated and the user is not in a system owner role, alter the all
        //privileges map to contain only those privileges authorized for the user's account. This
        // is
        //done so the user only sees privileges they are authorized to view.
        if (Constants.OBJECT_TYPE_USER.equalsIgnoreCase(btxDetails.getObjectType())) {
          if (!securityInfo.isInRoleSystemOwner()) {
            String account = securityInfo.getAccount();
            AccountSrchSBHome home = (AccountSrchSBHome) EjbHomes.getHome(AccountSrchSBHome.class);
            AccountSrchSB accountPrivileges = home.create();
            List authorizedPrivileges = accountPrivileges.getPrivilegesAssignedToAccount(account);
            removeUnauthorizedEntities(authorizedPrivileges, allPrivileges);
          }
        }
        btxDetails.setAllPrivileges(allPrivileges);
      }
      catch (Exception e) {
        ApiFunctions.throwAsRuntimeException(e);
      }
    }
    return btxDetails;
  }

  /**
   * Perform a BTX transaction: manage the privileges for a user or account
   */
  private BtxDetailsManagePrivileges performManagePrivileges(BtxDetailsManagePrivileges btxDetails)
      throws Exception {
    if (Constants.OBJECT_TYPE_ACCOUNT.equalsIgnoreCase(btxDetails.getObjectType())) {
      String accountId = btxDetails.getAccountData().getId();
      String[] selectedPrivileges = btxDetails.getSelectedPrivileges();
      // We initialize removedPrivileges to be all of the privileges to which the account currently
      // has access, and remove any ones that it *still* should have access to below. So it ends
      // up being just the list of privileges to which it used to have access but to which it
      // no longer has access after the change we're currently processing.
      AccountSrchSBHome home = (AccountSrchSBHome) EjbHomes.getHome(AccountSrchSBHome.class);
      AccountSrchSB accountPrivileges = home.create();
      List removedPrivileges = accountPrivileges.getPrivilegesAssignedToAccount(accountId);

      //determine what privileges have been added and/or removed
      List addedPrivilegeIds = new ArrayList();
      int count = selectedPrivileges.length;
      for (int i = 0; i < count; i++) {
        String id = selectedPrivileges[i];
        Iterator iterator = removedPrivileges.iterator();
        boolean found = false;
        while (iterator.hasNext() && !found) {
          PrivilegeDto privilege = (PrivilegeDto) iterator.next();
          if (privilege.getId().equalsIgnoreCase(id)) {
            found = true;
            removedPrivileges.remove(privilege);
          }
        }
        if (!found) {
          addedPrivilegeIds.add(id);
        }
      }

      //Update the account with the new privilege list
      accountPrivileges.setPrivilegesAssignedToAccount(accountId, ApiFunctions
          .safeToList(selectedPrivileges));

      // Set the output/result properties for this transaction.
      btxDetails.setRemovedPrivileges(removedPrivileges);
      OrmUserManagementHome oumHome = (OrmUserManagementHome) EjbHomes.getHome(OrmUserManagementHome.class);
      OrmUserManagement privilegeFinder = oumHome.create();
      btxDetails.setAddedPrivileges(privilegeFinder.getPrivilegesById(addedPrivilegeIds));
      //since we're going back to the manage privileges page, reset the selected and all
      //privileges lists
      btxDetails.setAssignedPrivileges(accountPrivileges.getPrivilegesAssignedToAccount(accountId));
      btxDetails.setAllPrivileges(privilegeFinder.getAllPrivileges());

      //to prevent a "blank" history record, if there were no changes to process
      //(that is, the user's current privileges haven't actually changed)
      //mark the transaction as incomplete but still forward to success. If there were
      //SQL statements executed then mark it completed.
      if (addedPrivilegeIds.size() == 0 && removedPrivileges.size() == 0) {
        btxDetails.setActionForwardTXIncomplete("success");
      }
      else {
        btxDetails.setActionForwardTXCompleted("success");
      }
    }
    else if (Constants.OBJECT_TYPE_USER.equalsIgnoreCase(btxDetails.getObjectType())) {
      String userId = btxDetails.getUserData().getUserId();
      String accountId = btxDetails.getUserData().getAccountId();
      String[] selectedPrivileges = btxDetails.getSelectedPrivileges();
      SecurityInfo securityInfo = btxDetails.getLoggedInUserSecurityInfo();
      // We initialize removedPrivileges to be all of the privileges to which the user currently
      // has access, and remove any ones that they *still* should have access to below. So it ends
      // up being just the list of privileges to which they used to have access but to which they
      // no longer have access after the change we're currently processing. Note that if the user
      // is not in a system owner role, we need to remove any privileges to which the user is not
      // authorized to assign/revoke just as we did when starting this transaction. Otherwise,
      // privileges that the user is not authorized to assign/revoke would be revoked.
      OrmUserManagementHome oumHome = (OrmUserManagementHome) EjbHomes.getHome(OrmUserManagementHome.class);
      OrmUserManagement userPrivileges = oumHome.create();
      List removedPrivileges = userPrivileges.getPrivilegesAssignedToUser(userId, accountId);
      if (!securityInfo.isInRoleSystemOwner()) {
        String account = securityInfo.getAccount();
        AccountSrchSBHome home = (AccountSrchSBHome) EjbHomes.getHome(AccountSrchSBHome.class);
        AccountSrchSB accountPrivileges = home.create();
        List authorizedPrivileges = accountPrivileges.getPrivilegesAssignedToAccount(account);
        removeUnauthorizedEntities(authorizedPrivileges, removedPrivileges);
      }

      //determine what privileges have been added and/or removed
      List addedPrivilegeIds = new ArrayList();
      int count = selectedPrivileges.length;
      for (int i = 0; i < count; i++) {
        String id = selectedPrivileges[i];
        Iterator iterator = removedPrivileges.iterator();
        boolean found = false;
        while (iterator.hasNext() && !found) {
          PrivilegeDto privilege = (PrivilegeDto) iterator.next();
          if (privilege.getId().equalsIgnoreCase(id)) {
            found = true;
            removedPrivileges.remove(privilege);
          }
        }
        if (!found) {
          addedPrivilegeIds.add(id);
        }
      }

      //Remove access to privileges to which the user formerly had access but doesn't any longer
      List removedPrivilegeIds = new ArrayList();
      Iterator iter = removedPrivileges.iterator();
      while (iter.hasNext()) {
        PrivilegeDto privilege = (PrivilegeDto) iter.next();
        removedPrivilegeIds.add(privilege.getId());
      }
      userPrivileges.removePrivilegesFromUser(userId, accountId, removedPrivilegeIds);

      //Add access to privileges for which the user now has access but didn't before
      userPrivileges.addPrivilegesToUser(userId, accountId, addedPrivilegeIds, btxDetails
          .getLoggedInUserSecurityInfo().getUsername());

      // Set the output/result properties for this transaction.
      btxDetails.setRemovedPrivileges(removedPrivileges);
      btxDetails.setAddedPrivileges(userPrivileges.getPrivilegesById(addedPrivilegeIds));
      //since we're going back to the manage privileges page, reset the selected and all
      //privileges lists as well as the role based privilege list
      List assignedPrivileges = userPrivileges.getPrivilegesAssignedToUser(userId, accountId);
      List roleBasedPrivileges = RoleUtils.getPrivilegesViaRoleMembership(userId);
      Map allPrivileges = userPrivileges.getAllPrivileges();
      //If the user is not in a system owner role, alter the assigned privileges and all
      //privileges to contain only those privileges authorized for the user's account. This is done
      //so the user only sees privileges they are authorized to view.
      if (!securityInfo.isInRoleSystemOwner()) {
        String account = securityInfo.getAccount();
        AccountSrchSBHome home = (AccountSrchSBHome) EjbHomes.getHome(AccountSrchSBHome.class);
        AccountSrchSB accountPrivileges = home.create();
        List authorizedPrivileges = accountPrivileges.getPrivilegesAssignedToAccount(account);
        removeUnauthorizedEntities(authorizedPrivileges, assignedPrivileges);
        removeUnauthorizedEntities(authorizedPrivileges, allPrivileges);
      }
      btxDetails.setAssignedPrivileges(assignedPrivileges);
      btxDetails.setRoleBasedPrivileges(roleBasedPrivileges);
      btxDetails.setAllPrivileges(allPrivileges);

      //to prevent a "blank" history record, if there were no changes to process
      //(that is, the user's current privileges haven't actually changed)
      //mark the transaction as incomplete but still forward to success. If there were
      //SQL statements executed then mark it completed.
      //
      if (addedPrivilegeIds.size() == 0 && removedPrivilegeIds.size() == 0) {
        btxDetails.setActionForwardTXIncomplete("success");
      }
      else {
        btxDetails.setActionForwardTXCompleted("success");
      }
    }

    return btxDetails;
  }

  /**
   * Do BTX transaction validation for the performMaintainAccountUrlSave performer method.
   */
  private BTXDetails validatePerformMaintainAccountUrlSave(BtxDetailsMaintainAccountUrl btxDetails)
      throws Exception {

    boolean testProhibitUrlId = false;
    boolean testRequiredUrlId = false;
    boolean testInvalidUrlId = false;
    boolean testRequiredAccountId = false;
    boolean testInvalidAccountId = false;
    boolean testRequiredObjectType = false;
    boolean testInvalidObjectType = false;
    boolean testRequiredUrl = false;
    boolean testUrlLengthExceeded = false;
    boolean testRequiredLabel = false;
    boolean testLabelLengthExceeded = false;
    boolean testProhibitInsertionStrings = false;
    boolean testInvalidInsertionStrings = false;
    boolean testTargetLengthExceeded = false;

    String operation = btxDetails.getOperation();
    SecurityInfo securityInfo = btxDetails.getLoggedInUserSecurityInfo();

    if (ApiFunctions.isEmpty(operation)) {
      btxDetails.addActionError(new BtxActionError("orm.error.url.requiredOperation"));
    }
    else if (!(Constants.OPERATION_CREATE.equals(operation)
        || Constants.OPERATION_UPDATE.equals(operation) || Constants.OPERATION_DELETE
        .equals(operation))) {
      btxDetails
          .addActionError(new BtxActionError("orm.error.url.operationCreateOrUpdateOrDelete"));
    }

    // Decide which tests to do based on the operation.
    if (Constants.OPERATION_CREATE.equals(operation)) {
      testProhibitUrlId = true;
      testRequiredAccountId = true;
      testInvalidAccountId = true;
      testRequiredObjectType = true;
      testInvalidObjectType = true;
      testRequiredUrl = true;
      testUrlLengthExceeded = true;
      testRequiredLabel = true;
      testLabelLengthExceeded = true;
      testProhibitInsertionStrings = true;
      testInvalidInsertionStrings = true;
      testTargetLengthExceeded = true;
    } // end operation == Create
    else if (Constants.OPERATION_UPDATE.equals(operation)) {
      testRequiredUrlId = true;
      testInvalidUrlId = true;
      testRequiredAccountId = true;
      testInvalidAccountId = true;
      testRequiredObjectType = true;
      testInvalidObjectType = true;
      testRequiredUrl = true;
      testUrlLengthExceeded = true;
      testRequiredLabel = true;
      testLabelLengthExceeded = true;
      testProhibitInsertionStrings = true;
      testInvalidInsertionStrings = true;
      testTargetLengthExceeded = true;
    } // end operation == Update
    else if (Constants.OPERATION_DELETE.equals(operation)) {
      testRequiredUrlId = true;
      testInvalidUrlId = true;
    } // end operation == Delete

    // Now perform the tests...

    boolean idOk = true;
    boolean accountOk = true;
    boolean objectTypeOk = true;
    String urlId = ApiFunctions.safeString(btxDetails.getUrlId());
    String accountId = ApiFunctions.safeString(btxDetails.getAccountId());
    String objectType = ApiFunctions.safeString(btxDetails.getObjectType());

    btxDetails.setUrl(ApiFunctions.safeTrim(btxDetails.getUrl()));
    btxDetails.setLabel(ApiFunctions.safeTrim(btxDetails.getLabel()));
    btxDetails.setTarget(ApiFunctions.safeTrim(btxDetails.getTarget()));

    String url = ApiFunctions.safeString(btxDetails.getUrl());
    String label = ApiFunctions.safeString(btxDetails.getLabel());
    String target = ApiFunctions.safeString(btxDetails.getTarget());

    if (idOk && testProhibitUrlId && !ApiFunctions.isEmpty(urlId)) {
      idOk = false;
      btxDetails.addActionError(new BtxActionError("orm.error.url.prohibitUrlId"));
    }

    if (idOk && testRequiredUrlId && ApiFunctions.isEmpty(urlId)) {
      idOk = false;
      btxDetails.addActionError(new BtxActionError("orm.error.url.requiredUrlId"));
    }

    if (idOk && testInvalidUrlId && !UrlUtils.isExistingUrl(urlId)) {
      idOk = false;
      btxDetails.addActionError(new BtxActionError("orm.error.url.invalidUrlId"));
    }

    if (accountOk && testRequiredAccountId && ApiFunctions.isEmpty(accountId)) {
      accountOk = false;
      btxDetails.addActionError(new BtxActionError("orm.error.url.requiredAccountId"));
    }

    if (accountOk && testInvalidAccountId) {
      if (IltdsUtils.getAccountById(accountId, false, false) == null) {
        accountOk = false;
        btxDetails.addActionError(new BtxActionError("orm.error.url.invalidAccountId"));
      }
    }

    if (objectTypeOk && testRequiredObjectType && ApiFunctions.isEmpty(objectType)) {
      objectTypeOk = false;
      btxDetails.addActionError(new BtxActionError("orm.error.url.requiredObjectType"));
    }

    if (objectTypeOk && testInvalidObjectType
        && !UrlUtils.VALID_OBJECT_TYPES.contains(objectType.toLowerCase())) {
      objectTypeOk = false;
      btxDetails.addActionError(new BtxActionError("orm.error.url.invalidObjectType"));
    }

    if (testRequiredUrl && ApiFunctions.isEmpty(url)) {
      objectTypeOk = false;
      btxDetails.addActionError(new BtxActionError("orm.error.url.requiredUrl"));
    }

    if (testUrlLengthExceeded && (url.length() > UrlUtils.URL_MAX_LENGTH)) {
      btxDetails.addActionError(new BtxActionError("orm.error.url.urlLengthExceeded"));
    }

    if (testRequiredLabel && ApiFunctions.isEmpty(label)) {
      btxDetails.addActionError(new BtxActionError("orm.error.url.requiredLabel"));
    }

    if (testLabelLengthExceeded && (label.length() > UrlUtils.LABEL_MAX_LENGTH)) {
      btxDetails.addActionError(new BtxActionError("orm.error.url.labelLengthExceeded"));
    }

    if (testProhibitInsertionStrings) {
      if (UrlUtils.OBJECT_TYPE_MENU.equalsIgnoreCase(objectType)) {
        String lowerUrl = url.toLowerCase();
        if ((lowerUrl.indexOf(UrlUtils.INSERTION_STRING_DONOR_ID.toLowerCase()) != -1)
            || (lowerUrl.indexOf(UrlUtils.INSERTION_STRING_CASE_ID.toLowerCase()) != -1)
            || (lowerUrl.indexOf(UrlUtils.INSERTION_STRING_SAMPLE_ID.toLowerCase()) != -1)
            || (lowerUrl.indexOf(UrlUtils.INSERTION_STRING_DONOR_ALIAS.toLowerCase()) != -1)
            || (lowerUrl.indexOf(UrlUtils.INSERTION_STRING_CASE_ALIAS.toLowerCase()) != -1)
            || (lowerUrl.indexOf(UrlUtils.INSERTION_STRING_SAMPLE_ALIAS.toLowerCase()) != -1)) {
          btxDetails.addActionError(new BtxActionError("orm.error.url.prohibitInsertionStrings"));
        }
      }
    }

    if (testInvalidInsertionStrings) {
      boolean badStringFound = false;
      String lowerUrl = url.toLowerCase();
      int openPos = lowerUrl.indexOf(UrlUtils.INSERTION_STRING_DELIMITER);
      int closePos = 0;
      while (!badStringFound && openPos > 0) {
        closePos = lowerUrl.indexOf(UrlUtils.INSERTION_STRING_DELIMITER, openPos + 2);
        //if we can't find the closing delimiter, that's an error
        if (closePos == -1) {
          badStringFound = true;
        }
        else {
          //get the insertion string and make sure it's valid
          String iString = UrlUtils.INSERTION_STRING_DELIMITER
              + lowerUrl.substring(openPos + 2, closePos) + UrlUtils.INSERTION_STRING_DELIMITER;
          if (!UrlUtils.VALID_INSERTION_STRINGS.contains(iString)) {
            badStringFound = true;
          }
          //find the start of the next insertion string
          openPos = lowerUrl.indexOf(UrlUtils.INSERTION_STRING_DELIMITER, closePos + 2);
        }
      }
      if (badStringFound) {
        btxDetails.addActionError(new BtxActionError("orm.error.url.invalidInsertionStrings"));
      }
    }

    if (testTargetLengthExceeded && (target.length() > UrlUtils.TARGET_MAX_LENGTH)) {
      btxDetails.addActionError(new BtxActionError("orm.error.url.targetLengthExceeded"));
    }

    return btxDetails;
  }

  /**
   * This is the main BTX entry point for performing the transaction that creates, edits, or deletes
   * a url.
   */
  private BTXDetails performMaintainAccountUrlSave(BtxDetailsMaintainAccountUrl btxDetails)
      throws Exception {

    // Get the operation being performed.
    String operation = btxDetails.getOperation();

    // The action we take here depends on the operation.
    if (Constants.OPERATION_CREATE.equals(operation)) {
      // Create the url.
      urlCreate(btxDetails);
      btxDetails.addActionMessage(new BtxActionMessage("orm.error.url.confirmUrlAction", FormLogic
          .makePrefixedUrlId(btxDetails.getUrlId()), "created"));
    } // end operation == Create

    else if (Constants.OPERATION_UPDATE.equals(operation)) {
      // Update the url.
      urlUpdate(btxDetails);
      btxDetails.addActionMessage(new BtxActionMessage("orm.error.url.confirmUrlAction", FormLogic
          .makePrefixedUrlId(btxDetails.getUrlId()), "updated"));
    } // end operation == Update

    else if (Constants.OPERATION_DELETE.equals(operation)) {
      // Look up the url we're going to delete so that we can set output parameters.
      UrlDto urlDto = UrlUtils.getUrlDto(btxDetails.getUrlId());
      // Populate the detail with data object.
      BigrBeanUtilsBean.SINGLETON.copyProperties(btxDetails, urlDto);
      // Now delete the url.
      urlDelete(btxDetails);
      btxDetails.addActionMessage(new BtxActionMessage("orm.error.url.confirmUrlAction", FormLogic
          .makePrefixedUrlId(btxDetails.getUrlId()), "deleted"));
    } // end operation == Delete

    btxDetails.setActionForwardTXCompleted("success");
    return btxDetails;
  }

  /**
   * Do BTX transaction validation for the performMaintainBoxLayoutStart performer method.
   */
  private BTXDetails validatePerformMaintainBoxLayoutStart(
                                                           BtxDetailsMaintainBoxLayoutStart btxDetails)
      throws Exception {
    // We don't do much validation here, since this is the action that we go to to report
    // validation errors. Because of that, we can expect that there will sometimes be
    // odd data in the fields we get here. We do need some things to be correct, though.
    // For example, the operation field needs to have a valid operation name in it, and for
    // update operations we need a valid box layout id so that we can look up its data.

    boolean testProhibitBoxLayoutId = false;
    boolean testInvalidBoxLayoutId = false;

    String operation = btxDetails.getOperation();

    if (ApiFunctions.isEmpty(operation)) {
      btxDetails.addActionError(new BtxActionError("orm.error.boxLayout.requiredOperation"));
    }
    else if (!(Constants.OPERATION_CREATE.equals(operation)
        || Constants.OPERATION_UPDATE.equals(operation) || Constants.OPERATION_DELETE
        .equals(operation))) {
      btxDetails.addActionError(new BtxActionError(
          "orm.error.boxLayout.operationCreateOrUpdateOrDelete"));
    }

    // Decide which tests to do based on the operation.
    if (Constants.OPERATION_CREATE.equals(operation)) {
      testProhibitBoxLayoutId = true;
    } // end operation == Create
    else if (Constants.OPERATION_UPDATE.equals(operation)) {
      testInvalidBoxLayoutId = true;
    } // end operation == Update

    // Now perform the tests...
    String boxLayoutId = btxDetails.getBoxLayoutId();

    if (testProhibitBoxLayoutId && !ApiFunctions.isEmpty(boxLayoutId)) {
      btxDetails.addActionError(new BtxActionError("orm.error.boxLayout.prohibitBoxLayoutId"));
    }

    if (testInvalidBoxLayoutId) {
      boxLayoutId = BoxLayoutUtils.checkBoxLayoutId(btxDetails.getBoxLayoutId(), btxDetails, false,
          true, true);
    }

    // Get all box layouts.
    btxDetails.setBoxLayouts(BoxLayoutUtils.getAllBoxLayoutDtos());

    return btxDetails;
  }

  /**
   * This is the main BTX entry point for performing the transaction that starts the process of
   * creating, editing, or deleting a box layout.
   */
  private BTXDetails performMaintainBoxLayoutStart(BtxDetailsMaintainBoxLayoutStart btxDetails)
      throws Exception {
    String operation = btxDetails.getOperation();

    if (Constants.OPERATION_UPDATE.equals(operation)) {
      if (btxDetails.isPopulateOutputFields()) {
        String boxLayoutId = btxDetails.getBoxLayoutId();
        BoxLayoutDto boxLayoutDto = BoxLayoutUtils.getBoxLayoutDto(boxLayoutId);

        BigrBeanUtilsBean.SINGLETON.copyProperties(btxDetails, boxLayoutDto);
      }
    }

    btxDetails.setActionForwardTXCompleted("success");
    return btxDetails;
  }

  /**
   * Do BTX transaction validation for the performMaintainBoxLayoutSave performer method.
   */
  private BTXDetails validatePerformMaintainBoxLayoutSave(BtxDetailsMaintainBoxLayout btxDetails)
      throws Exception {
    boolean testProhibitBoxLayoutId = false;
    boolean testInvalidBoxLayoutId = false;

    boolean testValidNumberOfColumns = false;
    boolean testValidNumberOfRows = false;
    boolean testRequiredXaxisLabelCid = false;
    boolean testRequiredYaxisLabelCid = false;
    boolean testRequiredTabIndexCid = false;

    boolean testUniqueBoxLayout = false;

    boolean testUnusedOnUpdate = false;
    boolean testUnusedOnDelete = false;

    String operation = btxDetails.getOperation();

    if (ApiFunctions.isEmpty(operation)) {
      btxDetails.addActionError(new BtxActionError("orm.error.boxLayout.requiredOperation"));
    }
    else if (!(Constants.OPERATION_CREATE.equals(operation)
        || Constants.OPERATION_UPDATE.equals(operation) || Constants.OPERATION_DELETE
        .equals(operation))) {
      btxDetails.addActionError(new BtxActionError(
          "orm.error.boxLayout.operationCreateOrUpdateOrDelete"));
    }

    // Decide which tests to do based on the operation.
    if (Constants.OPERATION_CREATE.equals(operation)) {
      testProhibitBoxLayoutId = true;

      testValidNumberOfColumns = true;
      testValidNumberOfRows = true;
      testRequiredXaxisLabelCid = true;
      testRequiredYaxisLabelCid = true;
      testRequiredTabIndexCid = true;

      testUniqueBoxLayout = true;
    } // end operation == Create
    else if (Constants.OPERATION_UPDATE.equals(operation)) {
      testInvalidBoxLayoutId = true;

      testValidNumberOfColumns = true;
      testValidNumberOfRows = true;
      testRequiredXaxisLabelCid = true;
      testRequiredYaxisLabelCid = true;
      testRequiredTabIndexCid = true;

      testUniqueBoxLayout = true;
      testUnusedOnUpdate = true;
    } // end operation == Update
    else if (Constants.OPERATION_DELETE.equals(operation)) {
      testInvalidBoxLayoutId = true;
      testUnusedOnDelete = true;
    } // end operation == Delete

    // Now perform the tests...
    boolean idOk = true;
    String boxLayoutId = btxDetails.getBoxLayoutId();

    // Run box layout id tests.
    if (testProhibitBoxLayoutId && !ApiFunctions.isEmpty(boxLayoutId)) {
      idOk = false;
      btxDetails.addActionError(new BtxActionError("orm.error.boxLayout.prohibitBoxLayoutId"));
    }

    if (idOk && testInvalidBoxLayoutId) {
      boxLayoutId = BoxLayoutUtils.checkBoxLayoutId(btxDetails.getBoxLayoutId(), btxDetails, false,
          true, true);
      idOk = (boxLayoutId != null);
    }

    // Run unused on delete test.
    if (idOk && testUnusedOnDelete) {
      checkIfSafeToDelete(btxDetails);
    }

    // Run unused on update test.
    if (idOk && testUnusedOnUpdate) {
      checkIfSafeToUpdate(btxDetails);
    }

    // Run number of columns tests.
    if (testValidNumberOfColumns) {
      int numberOfColumns = btxDetails.getNumberOfColumns();

      if ((numberOfColumns < MIN_NUMBER_OF_COLUMNS) || (numberOfColumns > MAX_NUMBER_OF_COLUMNS)) {
        btxDetails.addActionError(new BtxActionError("orm.error.boxLayout.invalidNumberOfColumns", new Integer(MIN_NUMBER_OF_COLUMNS).toString(), new Integer(MAX_NUMBER_OF_COLUMNS).toString()));
      }
    }

    // Run number of rows tests.
    if (testValidNumberOfRows) {
      int numberOfRows = btxDetails.getNumberOfRows();

      if ((numberOfRows < MIN_NUMBER_OF_ROWS) || (numberOfRows > MAX_NUMBER_OF_ROWS)) {
        btxDetails.addActionError(new BtxActionError("orm.error.boxLayout.invalidNumberOfRows", new Integer(MIN_NUMBER_OF_ROWS).toString(), new Integer(MAX_NUMBER_OF_ROWS).toString()));
      }
    }

    // Run x axis label cid tests.
    if (testRequiredXaxisLabelCid) {
      String xaxisLabelCid = btxDetails.getXaxisLabelCid();
      if (ApiFunctions.isEmpty(xaxisLabelCid)) {
        btxDetails.addActionError(new BtxActionError("orm.error.boxLayout.requiredXaxisLabelCid"));
      }
      else {
        GbossValueSet valueSet = BigrGbossData
            .getValueSet(ArtsConstants.VALUE_SET_BOX_LAYOUT_LABEL_TYPE_XY_AXIS);
        if (!valueSet.containsValue(xaxisLabelCid)) {
          btxDetails.addActionError(new BtxActionError("orm.error.boxLayout.invalidXaxisLabelCid"));
        }
      }
    }

    // Run y axis label cid tests.
    if (testRequiredYaxisLabelCid) {
      String yaxisLabelCid = btxDetails.getYaxisLabelCid();
      if (ApiFunctions.isEmpty(yaxisLabelCid)) {
        btxDetails.addActionError(new BtxActionError("orm.error.boxLayout.requiredYaxisLabelCid"));
      }
      else {
        GbossValueSet valueSet = BigrGbossData
            .getValueSet(ArtsConstants.VALUE_SET_BOX_LAYOUT_LABEL_TYPE_XY_AXIS);
        if (!valueSet.containsValue(yaxisLabelCid)) {
          btxDetails.addActionError(new BtxActionError("orm.error.boxLayout.invalidYaxisLabelCid"));
        }
      }
    }

    // Run tab index cid tests.
    if (testRequiredTabIndexCid) {
      String tabIndexCid = btxDetails.getTabIndexCid();
      if (ApiFunctions.isEmpty(tabIndexCid)) {
        btxDetails.addActionError(new BtxActionError("orm.error.boxLayout.requiredTabIndexCid"));
      }
      else {
        GbossValueSet valueSet = BigrGbossData
            .getValueSet(ArtsConstants.VALUE_SET_BOX_LAYOUT_TAB_DIRECTION);
        if (!valueSet.containsValue(tabIndexCid)) {
          btxDetails.addActionError(new BtxActionError("orm.error.boxLayout.invalidTabIndexCid"));
        }
      }
    }

    // Run unique on create test.
    if (testUniqueBoxLayout) {
      checkUniqueBoxLayout(btxDetails);
    }

    return btxDetails;
  }

  /**
   * This is the main BTX entry point for performing the transaction that creates, edits, or deletes
   * a box layout.
   */
  private BTXDetails performMaintainBoxLayoutSave(BtxDetailsMaintainBoxLayout btxDetails)
      throws Exception {
    // Get the operation being performed.
    String operation = btxDetails.getOperation();

    // The action we take here depends on the operation.
    if (Constants.OPERATION_CREATE.equals(operation)) {
      boxLayoutCreate(btxDetails);

      btxDetails.addActionMessage(new BtxActionMessage(
          "orm.message.boxLayout.confirmBoxLayoutAction", Escaper.htmlEscape(btxDetails
              .getBoxLayoutId()), "created"));
    } // end operation == Create
    else if (Constants.OPERATION_UPDATE.equals(operation)) {
      boxLayoutUpdate(btxDetails);

      btxDetails.addActionMessage(new BtxActionMessage(
          "orm.message.boxLayout.confirmBoxLayoutAction", Escaper.htmlEscape(btxDetails
              .getBoxLayoutId()), "updated"));
    } // end operation == Update
    else if (Constants.OPERATION_DELETE.equals(operation)) {
      String boxLayoutId = btxDetails.getBoxLayoutId();

      // Look up the box layout we're going to delete so that we can set output parameters.
      BoxLayoutDto boxLayoutDto = BoxLayoutUtils.getBoxLayoutDto(boxLayoutId);

      // Populate the detail with data object.
      BigrBeanUtilsBean.SINGLETON.copyProperties(btxDetails, boxLayoutDto);

      // Now delete the box layout.
      boxLayoutDelete(boxLayoutId);

      btxDetails.addActionMessage(new BtxActionMessage(
          "orm.message.boxLayout.confirmBoxLayoutAction", Escaper.htmlEscape(boxLayoutId),
          "deleted"));
    } // end operation == Delete

    btxDetails.setActionForwardTXCompleted("success");
    return btxDetails;
  }

  /**
   * This method will check the following tables to see if a box layout is safe to delete:
   * ILTDS_SAMPLE_BOX and ILTDS_ACCOUNT_BOX_LAYOUT. Please note that boxes are not delelted from the
   * ILTDS_SAMPLE_BOX table and therefore any box layouts that are associated with any boxes cannot
   * be deleted regardless of their "status".
   * 
   * @param btxDetails
   */
  private void checkIfSafeToDelete(BtxDetailsMaintainBoxLayout btxDetails) {
    String queryString = null;
    String count = null;

    String boxLayoutId = btxDetails.getBoxLayoutId();

    queryString = "SELECT count(*) AS sample_box_count FROM iltds_sample_box WHERE box_layout_id = ?";
    count = calculateBoxLayoutCount(queryString, "sample_box_count", boxLayoutId);
    if (!count.equalsIgnoreCase("0")) {
      btxDetails.addActionError(new BtxActionError("orm.error.boxLayout.notSafeToDelete",
          boxLayoutId, count, "box(es)"));
    }

    queryString = "SELECT count(*) AS account_box_layout_count FROM iltds_account_box_layout WHERE box_layout_id = ?";
    count = calculateBoxLayoutCount(queryString, "account_box_layout_count", boxLayoutId);
    if (!count.equalsIgnoreCase("0")) {
      btxDetails.addActionError(new BtxActionError("orm.error.boxLayout.notSafeToDelete",
          boxLayoutId, count, "account box layout(s)"));
    }
  }

  private boolean checkIfAccountsBoxLayoutSafeToDelete(BtxDetailsManageAccountBoxLayout btxDetails) {
    String queryString = null;
    String count = null;
    boolean safeToDelete = true;
    String boxLayoutId = btxDetails.getBoxLayoutId();

    queryString = "SELECT count(*) AS sample_box_count FROM iltds_sample_box WHERE box_layout_id = ?";
    count = calculateBoxLayoutCount(queryString, "sample_box_count", boxLayoutId);
    if (!count.equalsIgnoreCase("0")) {
      btxDetails.addActionError(new BtxActionError("orm.error.boxLayout.notSafeToDelete",
          boxLayoutId, count, "box(es)"));
      safeToDelete = false;
    }
    return safeToDelete;
  }

  private void checkIfSafeToUpdate(BtxDetailsMaintainBoxLayout btxDetails) {
    String queryString = null;
    String count = null;

    String boxLayoutId = btxDetails.getBoxLayoutId();
    BoxLayoutDto fetchedBoxLayoutDto = BoxLayoutUtils.getBoxLayoutDto(boxLayoutId);

    queryString = "SELECT count(*) AS sample_box_count FROM iltds_sample_box WHERE box_layout_id = ?";
    count = calculateBoxLayoutCount(queryString, "sample_box_count", boxLayoutId);

    if (!count.equalsIgnoreCase("0")) {
      if ((btxDetails.getNumberOfColumns() != fetchedBoxLayoutDto.getNumberOfColumns())
          || (btxDetails.getNumberOfRows()) != fetchedBoxLayoutDto.getNumberOfRows()) {
        btxDetails.addActionError(new BtxActionError("orm.error.boxLayout.notSafeToUpdate",
            boxLayoutId, count, "sample box(es)"));
      }
    }
  }

  private String calculateBoxLayoutCount(String queryString, String countVariable, String pk) {
    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    String count = null;
    ;
    try {
      con = ApiFunctions.getDbConnection();
      pstmt = con.prepareStatement(queryString);
      pstmt.setString(1, pk);
      rs = ApiFunctions.queryDb(pstmt, con);

      rs.next();
      count = rs.getString(countVariable);
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(con, pstmt, rs);
    }
    return count;
  }

  private void checkUniqueBoxLayout(BtxDetailsMaintainBoxLayout btxDetails) {

    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
      con = ApiFunctions.getDbConnection();

      StringBuffer query = new StringBuffer(128);
      query.append("SELECT 1 FROM ");
      query.append(DbConstants.TABLE_BOX_LAYOUT);
      query.append(" ");
      query.append(DbAliases.TABLE_BOX_LAYOUT);
      query.append(" WHERE ");
      query.append(DbAliases.TABLE_BOX_LAYOUT);
      query.append(".");
      query.append(DbConstants.LY_NUMBER_OF_COLUMNS);
      query.append(" = ? AND ");
      query.append(DbAliases.TABLE_BOX_LAYOUT);
      query.append(".");
      query.append(DbConstants.LY_NUMBER_OF_ROWS);
      query.append(" = ? AND ");
      query.append(DbAliases.TABLE_BOX_LAYOUT);
      query.append(".");
      query.append(DbConstants.LY_X_AXIS_LABEL_CID);
      query.append(" = ? AND ");
      query.append(DbAliases.TABLE_BOX_LAYOUT);
      query.append(".");
      query.append(DbConstants.LY_Y_AXIS_LABEL_CID);
      query.append(" = ? AND ");
      query.append(DbAliases.TABLE_BOX_LAYOUT);
      query.append(".");
      query.append(DbConstants.LY_TAB_INDEX_CID);
      query.append(" = ?");

      pstmt = con.prepareStatement(query.toString());
      pstmt.setBigDecimal(1, new BigDecimal(btxDetails.getNumberOfColumns()));
      pstmt.setBigDecimal(2, new BigDecimal(btxDetails.getNumberOfRows()));
      pstmt.setString(3, btxDetails.getXaxisLabelCid());
      pstmt.setString(4, btxDetails.getYaxisLabelCid());
      pstmt.setString(5, btxDetails.getTabIndexCid());

      rs = ApiFunctions.queryDb(pstmt, con);

      if (rs.next()) {
        // Not unique
        btxDetails.addActionError(new BtxActionError("orm.error.boxLayout.duplicateBoxLayout"));
      }
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(con, pstmt, rs);
    }
  }

  /**
   * Create a new box layout using the fields specified in the <code>btxDetails</code>. This
   * assumes that the id field is null and that all other fields are valid, but it doesn't check any
   * of this. As a side effect, it sets the id field on the <code>btxDetails</code> to the id
   * assigned to the new box layout.
   * 
   * @param btxDetails The description of the box layout to create.
   */
  private void boxLayoutCreate(BtxDetailsMaintainBoxLayout btxDetails) {
    Connection con = null;
    CallableStatement cstmt = null;
    try {
      con = ApiFunctions.getDbConnection();

      StringBuffer query = new StringBuffer(256);
      query.append("{ CALL INSERT INTO ");
      query.append(DbConstants.TABLE_BOX_LAYOUT);
      query.append(" (");
      query.append(DbConstants.LY_NUMBER_OF_COLUMNS);
      query.append(",");
      query.append(DbConstants.LY_NUMBER_OF_ROWS);
      query.append(",");
      query.append(DbConstants.LY_X_AXIS_LABEL_CID);
      query.append(",");
      query.append(DbConstants.LY_Y_AXIS_LABEL_CID);
      query.append(",");
      query.append(DbConstants.LY_TAB_INDEX_CID);
      query.append(") VALUES (?,?,?,?,?) RETURNING ");
      query.append(DbConstants.LY_BOX_LAYOUT_ID);
      query.append(" INTO ? }");

      cstmt = con.prepareCall(query.toString());

      cstmt.setBigDecimal(1, new BigDecimal(btxDetails.getNumberOfColumns()));
      cstmt.setBigDecimal(2, new BigDecimal(btxDetails.getNumberOfRows()));
      cstmt.setString(3, btxDetails.getXaxisLabelCid());
      cstmt.setString(4, btxDetails.getYaxisLabelCid());
      cstmt.setString(5, btxDetails.getTabIndexCid());

      cstmt.registerOutParameter(6, Types.VARCHAR);

      cstmt.execute();

      // Get the newly created box layout id.
      btxDetails.setBoxLayoutId(cstmt.getString(6));
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(cstmt);
      ApiFunctions.close(con);
    }
  }

  /**
   * Update an existing box layout to match the fields specified in the <code>btxDetails</code>.
   * This assumes that the id field is the id of an existing box layout and that all other fields
   * are valid, but it doesn't check any of this.
   * 
   * @param btxDetails The description of the box layout to update.
   */
  private void boxLayoutUpdate(BtxDetailsMaintainBoxLayout btxDetails) {
    Connection con = null;
    PreparedStatement pstmt = null;
    try {
      con = ApiFunctions.getDbConnection();

      StringBuffer query = new StringBuffer(256);
      query.append("UPDATE ");
      query.append(DbConstants.TABLE_BOX_LAYOUT);
      query.append(" SET ");
      query.append(DbConstants.LY_NUMBER_OF_COLUMNS);
      query.append(" = ?, ");
      query.append(DbConstants.LY_NUMBER_OF_ROWS);
      query.append(" = ?, ");
      query.append(DbConstants.LY_X_AXIS_LABEL_CID);
      query.append(" = ?, ");
      query.append(DbConstants.LY_Y_AXIS_LABEL_CID);
      query.append(" = ?, ");
      query.append(DbConstants.LY_TAB_INDEX_CID);
      query.append(" = ? WHERE ");
      query.append(DbConstants.LY_BOX_LAYOUT_ID);
      query.append(" = ?");

      pstmt = con.prepareStatement(query.toString());

      pstmt.setBigDecimal(1, new BigDecimal(btxDetails.getNumberOfColumns()));
      pstmt.setBigDecimal(2, new BigDecimal(btxDetails.getNumberOfRows()));
      pstmt.setString(3, btxDetails.getXaxisLabelCid());
      pstmt.setString(4, btxDetails.getYaxisLabelCid());
      pstmt.setString(5, btxDetails.getTabIndexCid());
      pstmt.setString(6, btxDetails.getBoxLayoutId());

      pstmt.executeUpdate();
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(pstmt);
      ApiFunctions.close(con);
    }
  }

  /**
   * Delete an existing box layout. This assumes that boxLayoutId parameter is the id of an existing
   * box layout and that the box layout doesn't contain any relationships and can be safely deleted,
   * but it doesn't check any of this.
   * 
   * @param boxLayoutId The id of the box layout to delete.
   */
  private void boxLayoutDelete(String boxLayoutId) {
    Connection con = null;
    PreparedStatement pstmt = null;
    try {
      con = ApiFunctions.getDbConnection();

      StringBuffer query = new StringBuffer(256);
      query.append("DELETE FROM ");
      query.append(DbConstants.TABLE_BOX_LAYOUT);
      query.append(" WHERE ");
      query.append(DbConstants.LY_BOX_LAYOUT_ID);
      query.append(" = ?");

      pstmt = con.prepareStatement(query.toString());
      pstmt.setString(1, boxLayoutId);
      pstmt.executeUpdate();
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(pstmt);
      ApiFunctions.close(con);
    }
  }

  /**
   * Do BTX transaction validation for the performManageAccountBoxLayoutStart performer method.
   */
  private BTXDetails validatePerformManageAccountBoxLayoutStart(
                                                                BtxDetailsManageAccountBoxLayoutStart btxDetails)
      throws Exception {
    //make sure the user has the Account Management privilege
    if (!btxDetails.getLoggedInUserSecurityInfo().isHasPrivilege(
        SecurityInfo.PRIV_ORM_ACCOUNT_MANAGEMENT)) {
      btxDetails.addActionError(new BtxActionError("error.noPrivilege", "manage accounts"));
    }
    else {
      // We don't do much validation here, since this is the action that we go to to report
      // validation errors. Because of that, we can expect that there will sometimes be
      // odd data in the fields we get here. We do need some things to be correct, though.
      // For example, the operation field needs to have a valid operation name in it, and for
      // update operations we need a valid box layout id so that we can look up its data.

      boolean testProhibitAccountBoxLayoutId = false;
      boolean testInvalidAccountBoxLayoutId = false;
      boolean testRequiredAccountId = false;
      boolean testInvalidAccountId = false;

      String operation = btxDetails.getOperation();

      if (ApiFunctions.isEmpty(operation)) {
        btxDetails.addActionError(new BtxActionError("orm.error.boxLayout.requiredOperation"));
      }
      else if (!(Constants.OPERATION_CREATE.equals(operation)
          || Constants.OPERATION_UPDATE.equals(operation) || Constants.OPERATION_DELETE
          .equals(operation))) {
        btxDetails.addActionError(new BtxActionError(
            "orm.error.boxLayout.operationCreateOrUpdateOrDelete"));
      }

      // Decide which tests to do based on the operation.
      if (Constants.OPERATION_CREATE.equals(operation)) {
        testProhibitAccountBoxLayoutId = true;
        testRequiredAccountId = true;
        testInvalidAccountId = true;
      } // end operation == Create
      else if (Constants.OPERATION_UPDATE.equals(operation)) {
        testInvalidAccountBoxLayoutId = true;
        testRequiredAccountId = true;
        testInvalidAccountId = true;
      } // end operation == Update
      else if (Constants.OPERATION_DELETE.equals(operation)) {
        testRequiredAccountId = true;
        testInvalidAccountId = true;
      } // end operation == Delete

      // Now perform the tests...
      int accountBoxLayoutId = btxDetails.getAccountBoxLayoutId();
      boolean idOk = true;

      // Run account box layout id tests.
      if (testProhibitAccountBoxLayoutId && (accountBoxLayoutId != 0)) {
        idOk = false;
        btxDetails.addActionError(new BtxActionError(
            "orm.error.boxLayout.prohibitAccountBoxLayoutId"));
      }

      if (idOk && testInvalidAccountBoxLayoutId) {
        if (accountBoxLayoutId == 0) {
          idOk = false;
          btxDetails.addActionError(new BtxActionError(
              "orm.error.boxLayout.requiredAccountBoxLayoutId"));
        }
        else if (!BoxLayoutUtils.isExistingAccountBoxLayout(accountBoxLayoutId)) {
          idOk = false;
          btxDetails.addActionError(new BtxActionError(
              "orm.error.boxLayout.invalidAccountBoxLayoutId"));
        }
      }

      // Run required account id tests.
      boolean accountOk = true;
      String accountId = btxDetails.getAccountId();
      if (accountOk && testRequiredAccountId && ApiFunctions.isEmpty(accountId)) {
        accountOk = false;
        btxDetails.addActionError(new BtxActionError("orm.error.boxLayout.requiredAccountId"));
      }

      if (accountOk && testInvalidAccountId) {
        if (IltdsUtils.getAccountById(accountId, false, false) == null) {
          accountOk = false;
          btxDetails.addActionError(new BtxActionError("orm.error.boxLayout.invalidAccountId"));
        }
      }

      // Set the default box layout id and the account box layouts for this account.
      if (accountOk) {
        // Get the current default box layout id from the account.
        btxDetails.setCurrentDefaultBoxLayoutId(getDefaultBoxLayoutIdByAccountId(accountId));

        // Get all account box layouts for this account.
        btxDetails.setAccountBoxLayouts(BoxLayoutUtils
            .getAccountBoxLayoutDtosByAccountId(accountId));
      }
    }

    return btxDetails;
  }

  /**
   * This is the main BTX entry point for performing the transaction that starts the process of
   * creating, editing, or deleting an account box layout.
   */
  private BTXDetails performManageAccountBoxLayoutStart(
                                                        BtxDetailsManageAccountBoxLayoutStart btxDetails)
      throws Exception {
    String operation = btxDetails.getOperation();

    if (Constants.OPERATION_UPDATE.equals(operation)) {
      if (btxDetails.isPopulateOutputFields()) {
        int accountBoxLayoutId = btxDetails.getAccountBoxLayoutId();
        AccountBoxLayoutDto accountBoxLayoutDto = BoxLayoutUtils
            .getAccountBoxLayoutDto(accountBoxLayoutId);

        BigrBeanUtilsBean.SINGLETON.copyProperties(btxDetails, accountBoxLayoutDto);
      }
    }

    btxDetails.setActionForwardTXCompleted("success");
    return btxDetails;
  }

  /**
   * Get the default box layout id for the account passed in.
   * 
   * @param accountId
   * @return
   */
  private String getDefaultBoxLayoutIdByAccountId(String accountId) throws Exception {

    String defaultBoxLayoutId = null;

    try {
      ArdaisaccountKey accountKey = new ArdaisaccountKey(accountId);
      ArdaisaccountAccessBean accountAB = new ArdaisaccountAccessBean(accountKey);

      defaultBoxLayoutId = accountAB.getDefaultBoxLayoutId();
    }
    catch (Exception ex) {
      ApiFunctions.throwAsRuntimeException(ex);
    }

    return ApiFunctions.safeString(defaultBoxLayoutId);
  }

  /**
   * Do BTX transaction validation for the performManageAccountBoxLayoutSave performer method.
   */
  private BTXDetails validatePerformManageAccountBoxLayoutSave(
                                                               BtxDetailsManageAccountBoxLayout btxDetails)
      throws Exception {
    boolean testProhibitAccountBoxLayoutId = false;
    boolean testInvalidAccountBoxLayoutId = false;

    boolean testRequiredBoxLayoutName = false;

    boolean testRequiredAccountId = false;
    boolean testInvalidAccountId = false;

    boolean testRequiredBoxLayoutId = false;
    boolean testInvalidBoxLayoutId = false;

    boolean testUniqueAccountBoxLayoutName = false;
    boolean testUniqueAccountBoxLayoutId = false;

    String operation = btxDetails.getOperation();

    if (ApiFunctions.isEmpty(operation)) {
      btxDetails.addActionError(new BtxActionError("orm.error.boxLayout.requiredOperation"));
    }
    else if (!(Constants.OPERATION_CREATE.equals(operation)
        || Constants.OPERATION_UPDATE.equals(operation) || Constants.OPERATION_DELETE
        .equals(operation))) {
      btxDetails.addActionError(new BtxActionError(
          "orm.error.boxLayout.operationCreateOrUpdateOrDelete"));
    }

    // Decide which tests to do based on the operation.
    if (Constants.OPERATION_CREATE.equals(operation)) {
      testProhibitAccountBoxLayoutId = true;

      testRequiredBoxLayoutName = true;

      testRequiredAccountId = true;
      testInvalidAccountId = true;

      testRequiredBoxLayoutId = true;
      testInvalidBoxLayoutId = true;

      testUniqueAccountBoxLayoutName = true;
      testUniqueAccountBoxLayoutId = true;
    } // end operation == Create
    else if (Constants.OPERATION_UPDATE.equals(operation)) {
      testInvalidAccountBoxLayoutId = true;

      testRequiredBoxLayoutName = true;

      testRequiredAccountId = true;
      testInvalidAccountId = true;

      testRequiredBoxLayoutId = true;
      testInvalidBoxLayoutId = true;

      testUniqueAccountBoxLayoutName = true;
      testUniqueAccountBoxLayoutId = true;
    } // end operation == Update
    else if (Constants.OPERATION_DELETE.equals(operation)) {
      testInvalidAccountBoxLayoutId = true;
    } // end operation == Delete

    // Now perform the tests...
    int accountBoxLayoutId = btxDetails.getAccountBoxLayoutId();
    boolean idOk = true;

    // Run account box layout id tests.
    if (testProhibitAccountBoxLayoutId && (accountBoxLayoutId != 0)) {
      idOk = false;
      btxDetails
          .addActionError(new BtxActionError("orm.error.boxLayout.prohibitAccountBoxLayoutId"));
    }

    if (idOk && testInvalidAccountBoxLayoutId) {
      if (accountBoxLayoutId == 0) {
        idOk = false;
        btxDetails.addActionError(new BtxActionError(
            "orm.error.boxLayout.requiredAccountBoxLayoutId"));
      }
      else if (!BoxLayoutUtils.isExistingAccountBoxLayout(accountBoxLayoutId)) {
        idOk = false;
        btxDetails.addActionError(new BtxActionError(
            "orm.error.boxLayout.invalidAccountBoxLayoutId"));
      }
    }

    // Run box layout name tests.
    boolean boxLayoutNameOk = true;
    if (idOk && testRequiredBoxLayoutName) {
      String boxLayoutName = ApiFunctions.safeTrim(btxDetails.getBoxLayoutName());
      if (ApiFunctions.isEmpty(boxLayoutName)) {
        boxLayoutNameOk = false;
        btxDetails.addActionError(new BtxActionError("orm.error.boxLayout.requiredBoxLayoutName"));
      }
      else if (boxLayoutName.length() > MAX_BOX_LAYOUT_NAME_LENGTH) {
        boxLayoutNameOk = false;
        btxDetails.addActionError(new BtxActionError("error.lengthExceeded", "Box Layout Name",
            MAX_BOX_LAYOUT_NAME_LENGTH + " characters"));
      }
      else {
        btxDetails.setBoxLayoutName(boxLayoutName);
      }
    }

    // Run account id tests.
    boolean accountOk = true;
    String accountId = btxDetails.getAccountId();
    if (idOk && accountOk && testRequiredAccountId && ApiFunctions.isEmpty(accountId)) {
      accountOk = false;
      btxDetails.addActionError(new BtxActionError("orm.error.boxLayout.requiredAccountId"));
    }

    if (idOk && accountOk && testInvalidAccountId) {
      if (IltdsUtils.getAccountById(accountId, false, false) == null) {
        accountOk = false;
        btxDetails.addActionError(new BtxActionError("orm.error.boxLayout.invalidAccountId"));
      }
    }

    // Run box layout id tests.
    boolean boxLayoutOk = true;
    String boxLayoutId = btxDetails.getBoxLayoutId();
    if (idOk && boxLayoutOk && (testRequiredBoxLayoutId || testInvalidBoxLayoutId)) {
      boxLayoutId = BoxLayoutUtils.checkBoxLayoutId(btxDetails.getBoxLayoutId(), btxDetails, false,
          true, true);
      boxLayoutOk = (boxLayoutId != null);
    }

    if (idOk && accountOk && boxLayoutNameOk && testUniqueAccountBoxLayoutName) {
      checkUniqueAccountBoxLayoutName(btxDetails);
    }

    if (idOk && accountOk && boxLayoutOk && testUniqueAccountBoxLayoutId) {
      checkUniqueAccountBoxLayoutId(btxDetails);
    }

    return btxDetails;
  }

  /**
   * This is the main BTX entry point for performing the transaction that creates, edits, or deletes
   * an account box layout.
   */
  private BTXDetails performManageAccountBoxLayoutSave(BtxDetailsManageAccountBoxLayout btxDetails)
      throws Exception {
    // Get the operation being performed.
    String operation = btxDetails.getOperation();

    // The action we take here depends on the operation.
    if (Constants.OPERATION_CREATE.equals(operation)) {
      accountBoxLayoutCreate(btxDetails);

      // Check to see if this is the default account box layout.
      if (btxDetails.isDefaultAccountBoxLayout()) {
        BoxLayoutUtils.setDefaultBoxLayoutIdByAccountId(btxDetails.getBoxLayoutId(), btxDetails
            .getAccountId());
      }

      btxDetails.addActionMessage(new BtxActionMessage(
          "orm.message.boxLayout.confirmAccountBoxLayoutAction", Escaper.htmlEscape(btxDetails
              .getBoxLayoutName()), "created"));
    } // end operation == Create
    else if (Constants.OPERATION_UPDATE.equals(operation)) {
      // Check to see if this is the default account box layout.
      if (btxDetails.isDefaultAccountBoxLayout()) {
        BoxLayoutUtils.setDefaultBoxLayoutIdByAccountId(btxDetails.getBoxLayoutId(), btxDetails
            .getAccountId());
      }
      else {
        // Check to see if this was the default account box layout.
        String defaultAccountBoxLayoutId = BoxLayoutUtils
            .getDefaultBoxLayoutIdByAccountId(btxDetails.getAccountId());
        String boxLayoutId = accountBoxLayoutSelect(btxDetails);
        if (boxLayoutId.equalsIgnoreCase(defaultAccountBoxLayoutId)) {
          BoxLayoutUtils.setDefaultBoxLayoutIdByAccountId(null, btxDetails.getAccountId());
        }
      }
      accountBoxLayoutUpdate(btxDetails);

      btxDetails.addActionMessage(new BtxActionMessage(
          "orm.message.boxLayout.confirmAccountBoxLayoutAction", Escaper.htmlEscape(btxDetails
              .getBoxLayoutName()), "updated"));
    } // end operation == Update
    else if (Constants.OPERATION_DELETE.equals(operation)) {

      int accountBoxLayoutId = btxDetails.getAccountBoxLayoutId();

      // Look up the account box layout we're going to delete so that we can set output parameters.
      AccountBoxLayoutDto accountBoxLayoutDto = BoxLayoutUtils
          .getAccountBoxLayoutDto(accountBoxLayoutId);

      // Populate the detail with data object.
      BigrBeanUtilsBean.SINGLETON.copyProperties(btxDetails, accountBoxLayoutDto);
      String boxLayoutId = accountBoxLayoutDto.getBoxLayoutId();
      //check to see if layout is not used and safe to delete
      boolean safeToDelete = checkIfAccountsBoxLayoutSafeToDelete(btxDetails);

      // Now delete the accounts box layout if it is not in use.
      if (safeToDelete) {
        accountBoxLayoutDelete(accountBoxLayoutId);
        btxDetails.addActionMessage(new BtxActionMessage(
            "orm.message.boxLayout.confirmAccountBoxLayoutAction", Escaper.htmlEscape(btxDetails
                .getBoxLayoutName()), "deleted"));
      }

      // Check to see if this is the default account box layout.
      String defaultAccountBoxLayoutId = BoxLayoutUtils.getDefaultBoxLayoutIdByAccountId(btxDetails
          .getAccountId());

      if (boxLayoutId.equalsIgnoreCase(defaultAccountBoxLayoutId)) {
        BoxLayoutUtils.setDefaultBoxLayoutIdByAccountId(null, btxDetails.getAccountId());
      }
    } // end operation == Delete

    btxDetails.setActionForwardTXCompleted("success");
    return btxDetails;
  }

  private void checkUniqueAccountBoxLayoutName(BtxDetailsManageAccountBoxLayout btxDetails) {

    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
      con = ApiFunctions.getDbConnection();

      StringBuffer query = new StringBuffer(128);
      query.append("SELECT 1 FROM ");
      query.append(DbConstants.TABLE_ACCOUNT_BOX_LAYOUT);
      query.append(" ");
      query.append(DbAliases.TABLE_ACCOUNT_BOX_LAYOUT);
      query.append(" WHERE ");
      query.append(DbAliases.TABLE_ACCOUNT_BOX_LAYOUT);
      query.append(".");
      query.append(DbConstants.ACCT_LY_ARDAIS_ACCT_KEY);
      query.append(" = ? AND UPPER(");
      query.append(DbAliases.TABLE_ACCOUNT_BOX_LAYOUT);
      query.append(".");
      query.append(DbConstants.ACCT_LY_BOX_LAYOUT_NAME);
      query.append(") = ? AND ");
      query.append(DbAliases.TABLE_ACCOUNT_BOX_LAYOUT);
      query.append(".");
      query.append(DbConstants.ACCT_LY_ID);
      query.append(" <> ?");

      pstmt = con.prepareStatement(query.toString());
      pstmt.setString(1, btxDetails.getAccountId());
      pstmt.setString(2, btxDetails.getBoxLayoutName().toUpperCase());
      pstmt.setBigDecimal(3, new BigDecimal(btxDetails.getAccountBoxLayoutId()));

      rs = ApiFunctions.queryDb(pstmt, con);

      if (rs.next()) {
        // Not unique
        btxDetails.addActionError(new BtxActionError(
            "orm.error.boxLayout.duplicateAccountBoxLayoutName"));
      }
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(con, pstmt, rs);
    }
  }

  private void checkUniqueAccountBoxLayoutId(BtxDetailsManageAccountBoxLayout btxDetails) {

    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
      con = ApiFunctions.getDbConnection();

      StringBuffer query = new StringBuffer(128);
      query.append("SELECT 1 FROM ");
      query.append(DbConstants.TABLE_ACCOUNT_BOX_LAYOUT);
      query.append(" ");
      query.append(DbAliases.TABLE_ACCOUNT_BOX_LAYOUT);
      query.append(" WHERE ");
      query.append(DbAliases.TABLE_ACCOUNT_BOX_LAYOUT);
      query.append(".");
      query.append(DbConstants.ACCT_LY_ARDAIS_ACCT_KEY);
      query.append(" = ? AND ");
      query.append(DbAliases.TABLE_ACCOUNT_BOX_LAYOUT);
      query.append(".");
      query.append(DbConstants.ACCT_LY_BOX_LAYOUT_ID);
      query.append(" = ? AND ");
      query.append(DbAliases.TABLE_ACCOUNT_BOX_LAYOUT);
      query.append(".");
      query.append(DbConstants.ACCT_LY_ID);
      query.append(" <> ?");

      pstmt = con.prepareStatement(query.toString());
      pstmt.setString(1, btxDetails.getAccountId());
      pstmt.setString(2, btxDetails.getBoxLayoutId());
      pstmt.setBigDecimal(3, new BigDecimal(btxDetails.getAccountBoxLayoutId()));

      rs = ApiFunctions.queryDb(pstmt, con);

      if (rs.next()) {
        // Not unique
        btxDetails.addActionError(new BtxActionError(
            "orm.error.boxLayout.duplicateAccountBoxLayoutId"));
      }
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(con, pstmt, rs);
    }
  }

  /**
   * Create a new account box layout using the fields specified in the <code>btxDetails</code>.
   * This assumes that the id field is zero and that all other fields are valid, but it doesn't
   * check any of this. As a side effect, it sets the id field on the <code>btxDetails</code> to
   * the id assigned to the new account box layout.
   * 
   * @param btxDetails The description of the account box layout to create.
   */
  private void accountBoxLayoutCreate(BtxDetailsManageAccountBoxLayout btxDetails) {
    Connection con = null;
    CallableStatement cstmt = null;
    try {
      con = ApiFunctions.getDbConnection();

      StringBuffer query = new StringBuffer(256);
      query.append("{ CALL INSERT INTO ");
      query.append(DbConstants.TABLE_ACCOUNT_BOX_LAYOUT);
      query.append(" (");
      query.append(DbConstants.ACCT_LY_BOX_LAYOUT_NAME);
      query.append(",");
      query.append(DbConstants.ACCT_LY_ARDAIS_ACCT_KEY);
      query.append(",");
      query.append(DbConstants.ACCT_LY_BOX_LAYOUT_ID);
      query.append(") VALUES (?,?,?) RETURNING ");
      query.append(DbConstants.ACCT_LY_ID);
      query.append(" INTO ? }");

      cstmt = con.prepareCall(query.toString());

      cstmt.setString(1, ApiFunctions.safeTrim(btxDetails.getBoxLayoutName()));
      cstmt.setString(2, btxDetails.getAccountId());
      cstmt.setString(3, btxDetails.getBoxLayoutId());

      cstmt.registerOutParameter(4, Types.NUMERIC);

      cstmt.execute();

      // Get the newly created account box layout id.
      btxDetails.setAccountBoxLayoutId(cstmt.getInt(4));
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(cstmt);
      ApiFunctions.close(con);
    }
  }

  /**
   * Update an existing account box layout to match the fields specified in the
   * <code>btxDetails</code>. This assumes that the id field is the id of an existing account box
   * layout and that all other fields are valid, but it doesn't check any of this.
   * 
   * @param btxDetails The description of the account box layout to update.
   */
  private void accountBoxLayoutUpdate(BtxDetailsManageAccountBoxLayout btxDetails) {
    Connection con = null;
    PreparedStatement pstmt = null;
    try {
      con = ApiFunctions.getDbConnection();

      StringBuffer query = new StringBuffer(256);
      query.append("UPDATE ");
      query.append(DbConstants.TABLE_ACCOUNT_BOX_LAYOUT);
      query.append(" SET ");
      query.append(DbConstants.ACCT_LY_BOX_LAYOUT_NAME);
      query.append(" = ?, ");
      query.append(DbConstants.ACCT_LY_ARDAIS_ACCT_KEY);
      query.append(" = ?, ");
      query.append(DbConstants.ACCT_LY_BOX_LAYOUT_ID);
      query.append(" = ? WHERE ");
      query.append(DbConstants.ACCT_LY_ID);
      query.append(" = ?");

      pstmt = con.prepareStatement(query.toString());

      pstmt.setString(1, btxDetails.getBoxLayoutName());
      pstmt.setString(2, btxDetails.getAccountId());
      pstmt.setString(3, btxDetails.getBoxLayoutId());
      pstmt.setBigDecimal(4, new BigDecimal(btxDetails.getAccountBoxLayoutId()));

      pstmt.executeUpdate();
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(pstmt);
      ApiFunctions.close(con);
    }
  }

  /**
   * Select an existing account box layout to match the fields specified in the
   * <code>btxDetails</code>. This assumes that the id field is the id of an existing account box
   * layout and that all other fields are valid, but it doesn't check any of this.
   * 
   * @param btxDetails The description of the account box layout to select.
   */
  private String accountBoxLayoutSelect(BtxDetailsManageAccountBoxLayout btxDetails) {
    ResultSet rs = null;
    Connection con = null;
    PreparedStatement pstmt = null;
    String result = null;
    try {
      con = ApiFunctions.getDbConnection();

      StringBuffer query = new StringBuffer(256);
      query.append("SELECT ");
      query.append(DbConstants.ACCT_LY_BOX_LAYOUT_ID);
      query.append(" FROM ");
      query.append(DbConstants.TABLE_ACCOUNT_BOX_LAYOUT);
      query.append(" WHERE ");
      query.append(DbConstants.ACCT_LY_ID);
      query.append(" = ?");

      pstmt = con.prepareStatement(query.toString());
      pstmt.setBigDecimal(1, new BigDecimal(btxDetails.getAccountBoxLayoutId()));

      rs = ApiFunctions.queryDb(pstmt, con);

      if (rs.next()) {
        result = rs.getString(1);
      }
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(pstmt);
      ApiFunctions.close(con);
    }
    return result;
  }

  /**
   * Delete an existing account box layout. This assumes that accountBoxLayoutId parameter is the id
   * of an existing account box layout and that the account box layout doesn't contain any
   * relationships and can be safely deleted, but it doesn't check any of this.
   * 
   * @param accountBoxLayoutId The id of the account box layout to delete.
   */
  private void accountBoxLayoutDelete(int accountBoxLayoutId) {
    Connection con = null;
    PreparedStatement pstmt = null;
    try {
      con = ApiFunctions.getDbConnection();

      StringBuffer query = new StringBuffer(256);
      query.append("DELETE FROM ");
      query.append(DbConstants.TABLE_ACCOUNT_BOX_LAYOUT);
      query.append(" WHERE ");
      query.append(DbConstants.ACCT_LY_ID);
      query.append(" = ?");

      pstmt = con.prepareStatement(query.toString());
      pstmt.setBigDecimal(1, new BigDecimal(accountBoxLayoutId));
      pstmt.executeUpdate();
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(pstmt);
      ApiFunctions.close(con);
    }
  }

  private BTXDetails validatePerformManageShippingPartnersStart(
                                                                BtxDetailsManageShippingPartners btxDetails) {
    //make sure the user has the Account Management privilege
    if (!btxDetails.getLoggedInUserSecurityInfo().isHasPrivilege(
        SecurityInfo.PRIV_ORM_ACCOUNT_MANAGEMENT)) {
      btxDetails.addActionError(new BtxActionError("error.noPrivilege", "manage accounts"));
    }
    //make sure the user is a System Owner
    if (!btxDetails.getLoggedInUserSecurityInfo().isInRoleSystemOwner()) {
      btxDetails.addActionError(new BtxActionError("error.noPrivilege",
          "manage the shipping partners for this account"));
    }
    return btxDetails;
  }

  /**
   * Perform a BTX transaction: get the information necessary to start the transaction for managing
   * the shipping partners for an account.
   */
  private BTXDetails performManageShippingPartnersStart(BtxDetailsManageShippingPartners btxDetails)
      throws Exception {

    String accountId = btxDetails.getAccountId();

    // Get all shipping partners that have been assigned to this account.
    List assignedShippingPartners = IltdsUtils.getAssignedShippingPartnersByAccount(accountId);

    // Get all shipping partners that are available for this account.
    List availableShippingPartners = IltdsUtils.getAvailableShippingPartnersByAccount(accountId,
        assignedShippingPartners);

    btxDetails.setAvailableShippingPartners(availableShippingPartners);
    btxDetails.setAssignedShippingPartners(assignedShippingPartners);

    btxDetails.setActionForwardTXIncomplete("success");
    return btxDetails;
  }

  private BTXDetails validatePerformManageShippingPartners(
                                                           BtxDetailsManageShippingPartners btxDetails) {
    return btxDetails;
  }

  /**
   * Perform a BTX transaction: add/remove access rights to shipping partners for a specified
   * account.
   */
  private BTXDetails performManageShippingPartners(BtxDetailsManageShippingPartners btxDetails)
      throws Exception {

    String accountId = btxDetails.getAccountId();

    String[] selectedSPs = btxDetails.getSelectedShippingPartners();
    // We initialize removedSPs to be all of the SPs that the account currently has access
    // to, and remove any ones that they *still* should have access to below. So it ends
    // up being just the list of SPs that they used to have access to but don't any longer
    // after the access change we're currently processing.
    List removedSPs = IltdsUtils.getAssignedShippingPartnersByAccount(accountId);

    // Determine what shipping partners have been added and/or removed.
    List addedSPs = new ArrayList();
    int count = selectedSPs.length;
    for (int i = 0; i < count; i++) {
      String id = selectedSPs[i];
      Iterator iterator = removedSPs.iterator();
      boolean found = false;
      while (iterator.hasNext() && !found) {
        ShippingPartnerDto spdto = (ShippingPartnerDto) iterator.next();
        if (spdto.getShippingPartnerId().equals(id)) {
          found = true;
          removedSPs.remove(spdto);
        }
      }
      if (!found) {
        addedSPs.add(id);
      }
    }

    //Remove access to SPs that the account formerly had access to but doesn't any more.
    removeShippingPartnersFromAccount(accountId, removedSPs);

    //Add access to SPs that the account has access to now that they didn't before.
    addShippingPartnersToAccount(accountId, addedSPs);

    // Get all shipping partners that are available for this account.
    List allAvailableShippingPartners = IltdsUtils
        .getAllAvailableShippingPartnersByAccount(accountId);

    // Set the output/result properties for this transaction.
    btxDetails.setRemovedShippingPartners(removedSPs);
    btxDetails.setAddedShippingPartners(IltdsUtils.getShippingPartnersByIds(
        allAvailableShippingPartners, addedSPs));

    // Since we're going back to the manage shipping partners page, reset the selected and all
    // shipping partners lists.

    // Get all shipping partners that have been assigned to this account.
    List assignedShippingPartners = IltdsUtils.getAssignedShippingPartnersByAccount(accountId);

    // Get all shipping partners that are available for this account.
    List availableShippingPartners = IltdsUtils.getAvailableShippingPartners(
        allAvailableShippingPartners, assignedShippingPartners);

    btxDetails.setAvailableShippingPartners(availableShippingPartners);
    btxDetails.setAssignedShippingPartners(assignedShippingPartners);

    // To prevent a "blank" history record, if there were no changes to process
    // (that is, the account's current shipping partner assignments haven't actually changed)
    // mark the transaction as incomplete but still forward to success. If there were
    // SQL statements executed then mark it completed.
    //
    if (addedSPs.size() == 0 && removedSPs.size() == 0) {
      btxDetails.setActionForwardTXIncomplete("success");
    }
    else {
      btxDetails.setActionForwardTXCompleted("success");
    }

    return btxDetails;
  }

  /**
   * Remove access rights to the specified shipping partners for the specified account.
   * 
   * @param accountId The account id.
   * @param removedSPs The list of shipping partners ids (Strings).
   */
  private void removeShippingPartnersFromAccount(String accountId, List removedSPs) {
    if (removedSPs.size() == 0) {
      return;
    }

    Connection con = null;
    PreparedStatement pstmt = null;
    try {
      con = ApiFunctions.getDbConnection();

      StringBuffer query = new StringBuffer(256);
      query.append("DELETE FROM ");
      query.append(DbConstants.TABLE_ILTDS_SHIPPING_PARTNERS);
      query.append(" WHERE ");
      query.append(DbConstants.SHIPPING_PARTNER_ARDAIS_ACCT_KEY);
      query.append(" = ? AND ");
      query.append(DbConstants.SHIPPING_PARTNER_DESTINATION_ID);
      query.append(" = ?");

      pstmt = con.prepareStatement(query.toString());

      Iterator iter = removedSPs.iterator();
      while (iter.hasNext()) {
        ShippingPartnerDto spdto = (ShippingPartnerDto) iter.next();
        String shippingPartnerId = spdto.getShippingPartnerId();

        pstmt.setString(1, accountId);
        pstmt.setString(2, shippingPartnerId);

        pstmt.executeUpdate();
      }
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(pstmt);
      ApiFunctions.close(con);
    }
  }

  /**
   * Add access rights to the specified shipping partners for the specified account.
   * 
   * @param accountId The account id.
   * @param addedSPs The list of shipping partner ids (Strings).
   */
  private void addShippingPartnersToAccount(String accountId, List addedSPs) {
    if (addedSPs.size() == 0) {
      return;
    }

    Connection con = null;
    PreparedStatement pstmt = null;
    try {
      con = ApiFunctions.getDbConnection();

      StringBuffer query = new StringBuffer(256);
      query.append("INSERT INTO ");
      query.append(DbConstants.TABLE_ILTDS_SHIPPING_PARTNERS);
      query.append(" (");
      query.append(DbConstants.SHIPPING_PARTNER_ARDAIS_ACCT_KEY);
      query.append(",");
      query.append(DbConstants.SHIPPING_PARTNER_DESTINATION_ID);
      query.append(") VALUES (?,?)");

      pstmt = con.prepareStatement(query.toString());

      Iterator iter = addedSPs.iterator();
      while (iter.hasNext()) {
        String shippingPartnerId = (String) iter.next();

        pstmt.setString(1, accountId);
        pstmt.setString(2, shippingPartnerId);

        pstmt.executeUpdate();
      }
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(pstmt);
      ApiFunctions.close(con);
    }
  }

  /*
   * Private method to validate account information common to create and modify account
   */
  private void validateCommonAccountInformation(BtxDetailsAccountManagement btxDetails)
      throws Exception {

    //get the account dto containing the account's data
    AccountDto accountData = btxDetails.getAccountData();

    //validate account type
    accountData.setType(ApiFunctions.safeTrim(accountData.getType()));
    String accountType = accountData.getType();
    if (ApiFunctions.isEmpty(accountType)) {
      btxDetails.addActionError(new BtxActionError("error.noValue", "Account Type"));
    }
    else {
      boolean isValidType = (Constants.ACCOUNT_TYPE_MAP.get(accountType) != null);
      if (!isValidType) {
        btxDetails.addActionError(new BtxActionError("error.valueNotInListNoSuggestions",
            "Account Type"));
      }
    }

    //validate account name.
    accountData.setName(ApiFunctions.safeTrim(accountData.getName()));
    String accountName = accountData.getName();
    if (ApiFunctions.isEmpty(accountName)) {
      btxDetails.addActionError(new BtxActionError("error.noValue", "Account Name"));
    }
    else {
      if (accountName.length() > MAXIMUM_ACCOUNT_NAME_LENGTH) {
        btxDetails.addActionError(new BtxActionError("error.lengthExceeded", "Account Name",
            MAXIMUM_ACCOUNT_NAME_LENGTH + ""));
      }
    }

    //validate account status
    accountData.setStatus(ApiFunctions.safeTrim(accountData.getStatus()));
    String accountStatus = accountData.getStatus();
    if (ApiFunctions.isEmpty(accountStatus)) {
      btxDetails.addActionError(new BtxActionError("error.noValue", "Account Status"));
    }
    else {
      boolean isValidStatus = (Constants.ACCOUNT_STATUS_MAP.get(accountStatus) != null);
      if (!isValidStatus) {
        btxDetails.addActionError(new BtxActionError("error.valueNotInListNoSuggestions",
            "Account Status"));
      }
    }

    //validate request manager email
    accountData.setRequestManagerEmail(ApiFunctions.safeTrim(accountData.getRequestManagerEmail()));
    String requestManagerEmail = ApiFunctions.safeString(accountData.getRequestManagerEmail());
    if (!ApiFunctions.isEmpty(requestManagerEmail)) {
      if (!(com.ardais.bigr.orm.helpers.FormLogic.checkEmailAddressFormat(requestManagerEmail))) {
        btxDetails
            .addActionError(new BtxActionError("error.invalidEmailFormat", "Request Manager"));
      }
      if (requestManagerEmail.length() > MAXIMUM_EMAIL_LENGTH) {
        btxDetails.addActionError(new BtxActionError("error.lengthExceeded",
            "Request Manager Email Address", MAXIMUM_EMAIL_LENGTH + ""));
      }
    }

    //validate view linked cases only
    accountData.setViewLinkedCasesOnly(ApiFunctions.safeTrim(accountData.getViewLinkedCasesOnly()));
    String accountViewLinkedCasesOnly = accountData.getViewLinkedCasesOnly();
    if (ApiFunctions.isEmpty(accountViewLinkedCasesOnly)) {
      btxDetails.addActionError(new BtxActionError("error.noValue", "View Linked Cases Only"));
    }
    else {
      boolean isValidViewLinkedCasesOnly = (accountViewLinkedCasesOnly
          .equalsIgnoreCase(FormLogic.DB_YES) || accountViewLinkedCasesOnly
          .equalsIgnoreCase(FormLogic.DB_NO));
      if (!isValidViewLinkedCasesOnly) {
        btxDetails.addActionError(new BtxActionError("error.valueNotInListNoSuggestions",
            "View Linked Cases Only"));
      }
    }

    //validate require unique sample aliases
    accountData.setRequireUniqueSampleAliases(ApiFunctions.safeTrim(accountData.getRequireUniqueSampleAliases()));
    String accountRequireUniqueSampleAliases = accountData.getRequireUniqueSampleAliases();
    if (ApiFunctions.isEmpty(accountRequireUniqueSampleAliases)) {
      btxDetails.addActionError(new BtxActionError("error.noValue", "Require Unique Sample Aliases"));
    }
    else {
      boolean isValidRequireUniqueSampleAliases = (accountRequireUniqueSampleAliases
          .equalsIgnoreCase(FormLogic.DB_YES) || accountRequireUniqueSampleAliases
          .equalsIgnoreCase(FormLogic.DB_NO));
      if (!isValidRequireUniqueSampleAliases) {
        btxDetails.addActionError(new BtxActionError("error.valueNotInListNoSuggestions",
            "Require Unique Sample Aliases"));
      }
      //if the account requires unique sample aliases, make sure there are no duplicated alias
      //values already in existence.  Note that samples that have been entered into the system
      //via a box scan (as well as those that have been fully registered, such as via Create
      //Sample and Derivative Operations) count toward this check.
      if (FormLogic.DB_YES.equalsIgnoreCase(accountRequireUniqueSampleAliases)) {
        String accountId = ApiFunctions.safeTrim(accountData.getId());
        if (!ApiFunctions.isEmpty(accountId)) {
          int duplicatedAliasCount = getSamplesWithDuplicatedAliasesCount(accountId);
          if (duplicatedAliasCount > 0) {
            btxDetails.addActionError(new BtxActionError("orm.error.account.existingDuplicateSampleAliases",
              duplicatedAliasCount + ""));
          }
        }
      }
    }

    //validate require sample aliases
    accountData.setRequireSampleAliases(ApiFunctions.safeTrim(accountData.getRequireSampleAliases()));
    String accountRequireSampleAliases = accountData.getRequireSampleAliases();
    if (ApiFunctions.isEmpty(accountRequireSampleAliases)) {
      btxDetails.addActionError(new BtxActionError("error.noValue", "Require Sample Aliases"));
    }
    else {
      boolean isValidRequireSampleAliases = (accountRequireSampleAliases
          .equalsIgnoreCase(FormLogic.DB_YES) || accountRequireSampleAliases
          .equalsIgnoreCase(FormLogic.DB_NO));
      if (!isValidRequireSampleAliases) {
        btxDetails.addActionError(new BtxActionError("error.valueNotInListNoSuggestions",
            "Require Sample Aliases"));
      }
      //if the account requires sample aliases, make sure there are no missing alias
      //values already in existence.  Note that only fully registered samples are considered for
      //this check, since those samples that have been box-scanned only will be required to have
      //an alias value when they go through Create Sample.
      if (FormLogic.DB_YES.equalsIgnoreCase(accountRequireSampleAliases)) {
        String accountId = ApiFunctions.safeTrim(accountData.getId());
        if (!ApiFunctions.isEmpty(accountId)) {
          int missingAliasCount = getSamplesWithMissingAliasesCount(accountId);
          if (missingAliasCount > 0) {
            btxDetails.addActionError(new BtxActionError("orm.error.account.existingMissingSampleAliases",
                missingAliasCount + ""));
          }
        }
      }
    }

    //validate password policy
    validateAccountPasswordPolicy(btxDetails);

    //validate password lifespan
    accountData.setPasswordLifeSpan(ApiFunctions.safeTrim(accountData.getPasswordLifeSpan()));
    String accountPasswordLifeSpan = accountData.getPasswordLifeSpan();
    if (!ApiFunctions.isPositiveInteger(accountPasswordLifeSpan)) {
      btxDetails.addActionError(new BtxActionError("error.badvalue", accountPasswordLifeSpan,
          "Password Life Span", "a positive integer greater than zero"));
    }
    else {
      int lifeSpan = Integer.parseInt(accountPasswordLifeSpan);
      if (lifeSpan < 1) {
        btxDetails.addActionError(new BtxActionError("error.badvalue", accountPasswordLifeSpan,
            "Password Life Span", "a positive integer greater than zero"));
      }
    }

    //validate donor registration form choice, if any
    String formDefId = accountData.getDonorRegistrationFormId();
    if (!ApiFunctions.isEmpty(formDefId)) {
      BigrFormDefinition formDef = FormUtils.getFormDefinition(btxDetails
          .getLoggedInUserSecurityInfo(), formDefId);
      if (formDef == null) {
        btxDetails.addActionError(new BtxActionError("orm.error.policy.invalidRegistrationForm",
            "donors", "does not exist"));
      }
      else if (!formDef.isEnabled()) {
        btxDetails.addActionError(new BtxActionError("orm.error.policy.invalidRegistrationForm",
            "donors", "is not enabled"));
      }
      else if (!formDef.getAccount().equalsIgnoreCase(accountData.getId())) {
        btxDetails.addActionError(new BtxActionError("orm.error.policy.invalidRegistrationForm",
            "donors", "does not belong to this account"));
      }
      else if (!TagTypes.DOMAIN_OBJECT_VALUE_DONOR.equalsIgnoreCase(formDef.getObjectType())) {
        btxDetails.addActionError(new BtxActionError("orm.error.policy.invalidRegistrationForm",
            "donors", "is not applicable for donors"));
      }
      else if (!TagTypes.USES_VALUE_REGISTRATION.equalsIgnoreCase(formDef.getUses())) {
        btxDetails.addActionError(new BtxActionError("orm.error.policy.invalidRegistrationForm",
            "donors", "is not a registration form"));
      }
      else {
        //everything is ok with this form, so store it's name so it can be recorded
        btxDetails.getAccountData().setDonorRegistrationFormName(formDef.getName());
      }
    }

    //validate contact first name
    accountData.getContact().getAddress().setFirstName(
        ApiFunctions.safeTrim(accountData.getContact().getAddress().getFirstName()));
    String firstName = ApiFunctions
        .safeString(accountData.getContact().getAddress().getFirstName());
    if (!ApiFunctions.isEmpty(firstName)) {
      if (firstName.length() > MAXIMUM_ACCOUNT_CONTACT_FIRST_NAME_LENGTH) {
        btxDetails.addActionError(new BtxActionError("error.lengthExceeded", "First Name",
            MAXIMUM_ACCOUNT_CONTACT_FIRST_NAME_LENGTH + ""));
      }
    }

    //validate contact last name
    accountData.getContact().getAddress().setLastName(
        ApiFunctions.safeTrim(accountData.getContact().getAddress().getLastName()));
    String lastName = ApiFunctions.safeString(accountData.getContact().getAddress().getLastName());
    if (!ApiFunctions.isEmpty(lastName)) {
      if (lastName.length() > MAXIMUM_ACCOUNT_CONTACT_LAST_NAME_LENGTH) {
        btxDetails.addActionError(new BtxActionError("error.lengthExceeded", "Last Name",
            MAXIMUM_ACCOUNT_CONTACT_LAST_NAME_LENGTH + ""));
      }
    }

    //validate contact phone number
    accountData.getContact().setPhoneNumber(
        ApiFunctions.safeTrim(accountData.getContact().getPhoneNumber()));
    String phoneNumber = ApiFunctions.safeString(accountData.getContact().getPhoneNumber());
    if (!ApiFunctions.isEmpty(phoneNumber)) {
      if (phoneNumber.length() > MAXIMUM_ACCOUNT_CONTACT_PHONE_NUMBER_LENGTH) {
        btxDetails.addActionError(new BtxActionError("error.lengthExceeded", "Phone Number",
            MAXIMUM_ACCOUNT_CONTACT_PHONE_NUMBER_LENGTH + ""));
      }
    }

    //validate contact extension
    accountData.getContact().setExtension(
        ApiFunctions.safeTrim(accountData.getContact().getExtension()));
    String extension = ApiFunctions.safeString(accountData.getContact().getExtension());
    if (!ApiFunctions.isEmpty(extension)) {
      if (!com.ardais.bigr.orm.helpers.FormLogic.checkNumeric(extension)) {
        btxDetails.addActionError(new BtxActionError("error.numericValueOnly", "Extension"));
      }
      if (extension.length() > MAXIMUM_ACCOUNT_CONTACT_EXTENSION_LENGTH) {
        btxDetails.addActionError(new BtxActionError("error.lengthExceeded", "Extension",
            MAXIMUM_ACCOUNT_CONTACT_EXTENSION_LENGTH + ""));
      }
    }

    //validate contact fax number
    accountData.getContact().setFaxNumber(
        ApiFunctions.safeTrim(accountData.getContact().getFaxNumber()));
    String faxNumber = ApiFunctions.safeString(accountData.getContact().getFaxNumber());
    if (!ApiFunctions.isEmpty(faxNumber)) {
      if (faxNumber.length() > MAXIMUM_ACCOUNT_CONTACT_FAX_NUMBER_LENGTH) {
        btxDetails.addActionError(new BtxActionError("error.lengthExceeded", "Fax Number",
            MAXIMUM_ACCOUNT_CONTACT_FAX_NUMBER_LENGTH + ""));
      }
    }

    //validate contact mobile number
    accountData.getContact().setMobileNumber(
        ApiFunctions.safeTrim(accountData.getContact().getMobileNumber()));
    String mobileNumber = ApiFunctions.safeString(accountData.getContact().getMobileNumber());
    if (!ApiFunctions.isEmpty(mobileNumber)) {
      if (mobileNumber.length() > MAXIMUM_ACCOUNT_CONTACT_CELL_NUMBER_LENGTH) {
        btxDetails.addActionError(new BtxActionError("error.lengthExceeded", "Mobile Number",
            MAXIMUM_ACCOUNT_CONTACT_CELL_NUMBER_LENGTH + ""));
      }
    }

    //validate contact pager number
    accountData.getContact().setPagerNumber(
        ApiFunctions.safeTrim(accountData.getContact().getPagerNumber()));
    String pagerNumber = ApiFunctions.safeString(accountData.getContact().getPagerNumber());
    if (!ApiFunctions.isEmpty(pagerNumber)) {
      if (pagerNumber.length() > MAXIMUM_ACCOUNT_CONTACT_PAGER_NUMBER_LENGTH) {
        btxDetails.addActionError(new BtxActionError("error.lengthExceeded", "Pager Number",
            MAXIMUM_ACCOUNT_CONTACT_PAGER_NUMBER_LENGTH + ""));
      }
    }

    //validate contact email address
    accountData.getContact().setEmail(ApiFunctions.safeTrim(accountData.getContact().getEmail()));
    String email = ApiFunctions.safeString(accountData.getContact().getEmail());
    if (!ApiFunctions.isEmpty(email)) {
      if (!(com.ardais.bigr.orm.helpers.FormLogic.checkEmailAddressFormat(email))) {
        btxDetails.addActionError(new BtxActionError("error.invalidEmailFormat", "Contact"));
      }
      if (email.length() > MAXIMUM_ACCOUNT_CONTACT_EMAIL_LENGTH) {
        btxDetails.addActionError(new BtxActionError("error.lengthExceeded",
            "Contact Email Address", MAXIMUM_ACCOUNT_CONTACT_EMAIL_LENGTH + ""));
      }
    }

    //validate contact address 1
    accountData.getContact().getAddress().setAddress1(
        ApiFunctions.safeTrim(accountData.getContact().getAddress().getAddress1()));
    String address1 = ApiFunctions.safeString(accountData.getContact().getAddress().getAddress1());
    if (address1.length() > MAXIMUM_ADDRESS_LENGTH) {
      btxDetails.addActionError(new BtxActionError("error.lengthExceeded", "Address 1",
          MAXIMUM_ADDRESS_LENGTH + ""));
    }

    //validate contact address 2
    accountData.getContact().getAddress().setAddress2(
        ApiFunctions.safeTrim(accountData.getContact().getAddress().getAddress2()));
    String address2 = ApiFunctions.safeString(accountData.getContact().getAddress().getAddress2());
    if (address2.length() > MAXIMUM_ADDRESS_LENGTH) {
      btxDetails.addActionError(new BtxActionError("error.lengthExceeded", "Address 2",
          MAXIMUM_ADDRESS_LENGTH + ""));
    }

    //validate contact city
    accountData.getContact().getAddress().setCity(
        ApiFunctions.safeTrim(accountData.getContact().getAddress().getCity()));
    String city = ApiFunctions.safeString(accountData.getContact().getAddress().getCity());
    if (city.length() > MAXIMUM_CITY_LENGTH) {
      btxDetails.addActionError(new BtxActionError("error.lengthExceeded", "City",
          MAXIMUM_CITY_LENGTH + ""));
    }

    //validate contact state
    accountData.getContact().getAddress().setState(
        ApiFunctions.safeTrim(accountData.getContact().getAddress().getState()));
    String state = ApiFunctions.safeString(accountData.getContact().getAddress().getState());
    if (state.length() > MAXIMUM_STATE_LENGTH) {
      btxDetails.addActionError(new BtxActionError("error.lengthExceeded", "State",
          MAXIMUM_STATE_LENGTH + ""));
    }

    //validate contact zip code
    accountData.getContact().getAddress().setZipCode(
        ApiFunctions.safeTrim(accountData.getContact().getAddress().getZipCode()));
    String zipCode = ApiFunctions.safeString(accountData.getContact().getAddress().getZipCode());
    if (zipCode.length() > MAXIMUM_ZIP_CODE_LENGTH) {
      btxDetails.addActionError(new BtxActionError("error.lengthExceeded", "Postal Code",
          MAXIMUM_ZIP_CODE_LENGTH + ""));
    }

    //validate contact country
    accountData.getContact().getAddress().setCountry(
        ApiFunctions.safeTrim(accountData.getContact().getAddress().getCountry()));
    String country = ApiFunctions.safeString(accountData.getContact().getAddress().getCountry());
    if (country.length() > MAXIMUM_COUNTRY_LENGTH) {
      btxDetails.addActionError(new BtxActionError("error.lengthExceeded", "Country",
          MAXIMUM_COUNTRY_LENGTH + ""));
    }

  }

  /*
   * Private method to validate user information common to create and modify user, as well as change
   * profile
   */
  private void validateCommonUserInformation(BtxDetailsUserManagement btxDetails) throws Exception {

    //get the user dto containing the user's data
    UserDto userData = btxDetails.getUserData();

    //validate first name
    userData.setFirstName(ApiFunctions.safeTrim(userData.getFirstName()));
    String firstName = ApiFunctions.safeString(userData.getFirstName());
    if (ApiFunctions.isEmpty(firstName)) {
      btxDetails.addActionError(new BtxActionError("error.noValue", "First Name"));
    }
    else {
      if (firstName.length() > MAXIMUM_USER_NAME_LENGTH) {
        btxDetails.addActionError(new BtxActionError("error.lengthExceeded", "First Name",
            MAXIMUM_USER_NAME_LENGTH + ""));
      }
    }

    //validate last name
    userData.setLastName(ApiFunctions.safeTrim(userData.getLastName()));
    String lastName = ApiFunctions.safeString(userData.getLastName());
    if (ApiFunctions.isEmpty(lastName)) {
      btxDetails.addActionError(new BtxActionError("error.noValue", "Last Name"));
    }
    else {
      if (lastName.length() > MAXIMUM_USER_NAME_LENGTH) {
        btxDetails.addActionError(new BtxActionError("error.lengthExceeded", "Last Name",
            MAXIMUM_USER_NAME_LENGTH + ""));
      }
    }

    //validate email
    userData.setEmail(ApiFunctions.safeTrim(userData.getEmail()));
    String email = ApiFunctions.safeString(userData.getEmail());
    if (ApiFunctions.isEmpty(email)) {
      btxDetails.addActionError(new BtxActionError("error.noValue", "Email"));
    }
    else {
      if (!(com.ardais.bigr.orm.helpers.FormLogic.checkEmailAddressFormat(email))) {
        btxDetails.addActionError(new BtxActionError("error.invalidEmailFormat", ""));
      }
      if (email.length() > MAXIMUM_EMAIL_LENGTH) {
        btxDetails.addActionError(new BtxActionError("error.lengthExceeded", "Email",
            MAXIMUM_EMAIL_LENGTH + ""));
      }
    }

    //validate department
    userData.setDepartment(ApiFunctions.safeTrim(userData.getDepartment()));
    String department = ApiFunctions.safeString(userData.getDepartment());
    if (department.length() > MAXIMUM_DEPARTMENT_LENGTH) {
      btxDetails.addActionError(new BtxActionError("error.lengthExceeded", "Department",
          MAXIMUM_DEPARTMENT_LENGTH + ""));
    }

    //validate title
    userData.setTitle(ApiFunctions.safeTrim(userData.getTitle()));
    String title = ApiFunctions.safeString(userData.getTitle());
    if (!ApiFunctions.isEmpty(title)) {
      if (title.length() > MAXIMUM_TITLE_LENGTH) {
        btxDetails.addActionError(new BtxActionError("error.lengthExceeded", "Title",
            MAXIMUM_TITLE_LENGTH + ""));
      }
    }

    //validate functional title
    userData.setFunctionalTitle(ApiFunctions.safeTrim(userData.getFunctionalTitle()));
    String functionalTitle = ApiFunctions.safeString(userData.getFunctionalTitle());
    if (!ApiFunctions.isEmpty(functionalTitle)) {
      if (functionalTitle.length() > MAXIMUM_FUNCTIONAL_TITLE_LENGTH) {
        btxDetails.addActionError(new BtxActionError("error.lengthExceeded", "Functional Role",
            MAXIMUM_FUNCTIONAL_TITLE_LENGTH + ""));
      }
    }

    //validate affiliation
    userData.setAffiliation(ApiFunctions.safeTrim(userData.getAffiliation()));
    String affiliation = ApiFunctions.safeString(userData.getAffiliation());
    if (!ApiFunctions.isEmpty(affiliation)) {
      if (affiliation.length() > MAXIMUM_AFFILIATION_LENGTH) {
        btxDetails.addActionError(new BtxActionError("error.lengthExceeded", "Affiliation",
            MAXIMUM_AFFILIATION_LENGTH + ""));
      }
    }

    //validate phone number
    userData.setPhoneNumber(ApiFunctions.safeTrim(userData.getPhoneNumber()));
    String phoneNumber = ApiFunctions.safeString(userData.getPhoneNumber());
    if (!ApiFunctions.isEmpty(phoneNumber)) {
      if (phoneNumber.length() > MAXIMUM_PHONE_NUMBER_LENGTH) {
        btxDetails.addActionError(new BtxActionError("error.lengthExceeded", "Phone Number",
            MAXIMUM_PHONE_NUMBER_LENGTH + ""));
      }
    }

    //validate extension
    userData.setExtension(ApiFunctions.safeTrim(userData.getExtension()));
    String extension = ApiFunctions.safeString(userData.getExtension());
    if (!ApiFunctions.isEmpty(extension)) {
      if (!com.ardais.bigr.orm.helpers.FormLogic.checkNumeric(extension)) {
        btxDetails.addActionError(new BtxActionError("error.numericValueOnly", "Extension"));
      }
      if (extension.length() > MAXIMUM_EXTENSION_LENGTH) {
        btxDetails.addActionError(new BtxActionError("error.lengthExceeded", "Extension",
            MAXIMUM_EXTENSION_LENGTH + ""));
      }
    }

    //validate fax number
    userData.setFaxNumber(ApiFunctions.safeTrim(userData.getFaxNumber()));
    String faxNumber = ApiFunctions.safeString(userData.getFaxNumber());
    if (!ApiFunctions.isEmpty(faxNumber)) {
      if (faxNumber.length() > MAXIMUM_PHONE_NUMBER_LENGTH) {
        btxDetails.addActionError(new BtxActionError("error.lengthExceeded", "Fax Number",
            MAXIMUM_PHONE_NUMBER_LENGTH + ""));
      }
    }

    //validate mobile number
    userData.setMobileNumber(ApiFunctions.safeTrim(userData.getMobileNumber()));
    String mobileNumber = ApiFunctions.safeString(userData.getMobileNumber());
    if (!ApiFunctions.isEmpty(mobileNumber)) {
      if (mobileNumber.length() > MAXIMUM_PHONE_NUMBER_LENGTH) {
        btxDetails.addActionError(new BtxActionError("error.lengthExceeded", "Mobile Number",
            MAXIMUM_PHONE_NUMBER_LENGTH + ""));
      }
    }

    //validate address 1
    userData.getAddress().setAddress1(ApiFunctions.safeTrim(userData.getAddress().getAddress1()));
    String address1 = ApiFunctions.safeString(userData.getAddress().getAddress1());
    if (address1.length() > MAXIMUM_ADDRESS_LENGTH) {
      btxDetails.addActionError(new BtxActionError("error.lengthExceeded", "Address 1",
          MAXIMUM_ADDRESS_LENGTH + ""));
    }

    //validate address 2
    userData.getAddress().setAddress2(ApiFunctions.safeTrim(userData.getAddress().getAddress2()));
    String address2 = ApiFunctions.safeString(userData.getAddress().getAddress2());
    if (address2.length() > MAXIMUM_ADDRESS_LENGTH) {
      btxDetails.addActionError(new BtxActionError("error.lengthExceeded", "Address 2",
          MAXIMUM_ADDRESS_LENGTH + ""));
    }

    //validate city
    userData.getAddress().setCity(ApiFunctions.safeTrim(userData.getAddress().getCity()));
    String city = ApiFunctions.safeString(userData.getAddress().getCity());
    if (city.length() > MAXIMUM_CITY_LENGTH) {
      btxDetails.addActionError(new BtxActionError("error.lengthExceeded", "City",
          MAXIMUM_CITY_LENGTH + ""));
    }

    //validate state
    userData.getAddress().setState(ApiFunctions.safeTrim(userData.getAddress().getState()));
    String state = ApiFunctions.safeString(userData.getAddress().getState());
    if (state.length() > MAXIMUM_STATE_LENGTH) {
      btxDetails.addActionError(new BtxActionError("error.lengthExceeded", "State",
          MAXIMUM_STATE_LENGTH + ""));
    }

    //validate zip code
    userData.getAddress().setZipCode(ApiFunctions.safeTrim(userData.getAddress().getZipCode()));
    String zipCode = ApiFunctions.safeString(userData.getAddress().getZipCode());
    if (zipCode.length() > MAXIMUM_ZIP_CODE_LENGTH) {
      btxDetails.addActionError(new BtxActionError("error.lengthExceeded", "Postal Code",
          MAXIMUM_ZIP_CODE_LENGTH + ""));
    }

    //validate country
    userData.getAddress().setCountry(ApiFunctions.safeTrim(userData.getAddress().getCountry()));
    String country = ApiFunctions.safeString(userData.getAddress().getCountry());
    if (country.length() > MAXIMUM_COUNTRY_LENGTH) {
      btxDetails.addActionError(new BtxActionError("error.lengthExceeded", "Country",
          MAXIMUM_COUNTRY_LENGTH + ""));
    }
  }

  /*
   * * Private method to validate the password policy for a user
   */
  private void validateAccountPasswordPolicy(BtxDetailsAccountManagement btxDetails)
      throws Exception {
    AccountDto accountData = btxDetails.getAccountData();
    accountData.setPasswordPolicy(ApiFunctions.safeTrim(accountData.getPasswordPolicy()));
    String passwordPolicy = ApiFunctions.safeString(accountData.getPasswordPolicy());
    validatePasswordPolicy(passwordPolicy, btxDetails);
  }

  /*
   * Private method to validate the password policy for a user
   */
  private void validateUserPasswordPolicy(BtxDetailsUserManagement btxDetails) throws Exception {
    UserDto userData = btxDetails.getUserData();
    userData.setPasswordPolicy(ApiFunctions.safeTrim(userData.getPasswordPolicy()));
    String passwordPolicy = ApiFunctions.safeString(userData.getPasswordPolicy());
    validatePasswordPolicy(passwordPolicy, btxDetails);
  }

  /*
   * Private method to validate a password policy value
   */
  private void validatePasswordPolicy(String passwordPolicy, BTXDetails btxDetails)
      throws Exception {
    if (ApiFunctions.isEmpty(passwordPolicy)) {
      btxDetails.addActionError(new BtxActionError("error.noValue", "Password Policy"));
    }
    else {
      LegalValueSet passwordPolicies = BigrGbossData
          .getValueSetAsLegalValueSet(ArtsConstants.VALUE_SET_USER_PASSWORD_POLICY);
      if (!passwordPolicies.contains(passwordPolicy)) {
        btxDetails.addActionError(new BtxActionError("error.valueNotInListNoSuggestions",
            "Password Policy"));
      }
    }
  }

  /*
   * Private method to determine what accounts are visible to a user
   */
  private LegalValueSet determineAccountChoicesForUser(BTXDetails btxDetails) {
    LegalValueSet returnValue = new LegalValueSet();
    if (btxDetails.getLoggedInUserSecurityInfo().isInRoleSystemOwner()) {
      returnValue = IltdsUtils.getAccountList();
      returnValue.addLegalValue(0, "", "Select");
    }
    else {
      String accountId = btxDetails.getLoggedInUserSecurityInfo().getAccount();
      String nameAndKey = IltdsUtils.getAccountName(accountId) + " (" + accountId + ")";
      returnValue.addLegalValue(accountId, nameAndKey);
    }
    return returnValue;
  }

  /*
   * Method to find the users accessible to the current user
   */
  private List findUsers(SecurityInfo securityInfo, UserDto searchCriteria) {

    List returnValue = new ArrayList();

    //first retrieve the search criteria (if any) specified by the user.
    if (searchCriteria == null) {
      searchCriteria = new UserDto();
    }
    String accountId = ApiFunctions.safeTrim(searchCriteria.getAccountId());
    //if the accountId is empty and the user is not an Ardais user, set the accountId to the
    //current user's account
    if (ApiFunctions.isEmpty(searchCriteria.getAccountId()) && !securityInfo.isInRoleSystemOwner()) {
      accountId = securityInfo.getAccount();
    }

    //build the query to execute
    StringBuffer query = new StringBuffer(300);
    query
        .append("select ardais_user_id, ardais_acct_key, user_lastname, user_firstname, user_active_ind ");
    query.append(" from es_ardais_user");
    query.append(" where lower(ardais_user_id) = nvl(lower(?),lower(ardais_user_id)) and ");
    query.append(" lower(ardais_acct_key) = nvl(lower(?),lower(ardais_acct_key)) and ");
    query.append(" lower(user_lastname) = nvl(lower(?),lower(user_lastname)) and ");
    query.append(" lower(user_firstname) = nvl(lower(?),lower(user_firstname))");
    query.append(" order by ardais_acct_key, user_lastname, user_firstname,ardais_user_id");

    //execute the query
    Connection con = null;
    ResultSet rs = null;
    PreparedStatement pstmt = null;
    try {
      con = ApiFunctions.getDbConnection();
      pstmt = con.prepareStatement(query.toString());
      pstmt.setString(1, ApiFunctions.safeTrim(searchCriteria.getUserId()));
      pstmt.setString(2, accountId);
      pstmt.setString(3, ApiFunctions.safeTrim(searchCriteria.getLastName()));
      pstmt.setString(4, ApiFunctions.safeTrim(searchCriteria.getFirstName()));
      rs = ApiFunctions.queryDb(pstmt, con);
      Map columnNames = DbUtils.getColumnNames(rs);
      while (rs.next()) {
        UserDto user = new UserDto(columnNames, rs);
        returnValue.add(user);
      }
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(con, pstmt, rs);
    }

    return returnValue;
  }

  /*
   * Method to find the accounts accessible to the current user
   */
  private List findAccounts(SecurityInfo securityInfo, AccountDto searchCriteria) {

    List returnValue = new ArrayList();

    //first retrieve the search criteria (if any) specified by the user.
    if (searchCriteria == null) {
      searchCriteria = new AccountDto();
    }
    String accountId = ApiFunctions.safeTrim(searchCriteria.getId());
    //if the accountId is empty and the user is not an Ardais user, set the accountId to the
    //current user's account
    if (ApiFunctions.isEmpty(searchCriteria.getId()) && !securityInfo.isInRoleSystemOwner()) {
      accountId = securityInfo.getAccount();
    }

    //build the query to execute
    StringBuffer query = new StringBuffer(300);
    query
        .append("select ardais_acct_key, ardais_acct_type, ardais_acct_company_desc, ardais_acct_status ");
    query.append(" from es_ardais_account");
    query.append(" where lower(ardais_acct_key) = nvl(lower(?),lower(ardais_acct_key)) and ");
    query.append(" lower(ardais_acct_type) = nvl(lower(?),lower(ardais_acct_type)) and ");
    query
        .append(" lower(ardais_acct_company_desc) = nvl(lower(?),lower(ardais_acct_company_desc))");
    query.append(" order by lower(ardais_acct_company_desc)");

    //execute the query
    Connection con = null;
    ResultSet rs = null;
    PreparedStatement pstmt = null;
    try {
      con = ApiFunctions.getDbConnection();
      pstmt = con.prepareStatement(query.toString());
      pstmt.setString(1, ApiFunctions.safeTrim(accountId));
      pstmt.setString(2, ApiFunctions.safeTrim(searchCriteria.getType()));
      pstmt.setString(3, ApiFunctions.safeTrim(searchCriteria.getName()));
      rs = ApiFunctions.queryDb(pstmt, con);
      Map columnNames = DbUtils.getColumnNames(rs);
      while (rs.next()) {
        AccountDto account = new AccountDto(columnNames, rs);
        returnValue.add(account);
      }
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(con, pstmt, rs);
    }

    return returnValue;
  }

  /*
   * Private method to create a new account
   */
  private void createAccount(AccountDto accountData) {

    //build the query to create the row in es_ardais_account
    StringBuffer createAccountQuery = new StringBuffer(300);
    createAccountQuery.append("insert into es_ardais_account (");
    createAccountQuery
        .append(" ardais_acct_key, ardais_acct_type, ardais_acct_status, ardais_acct_company_desc,");
    createAccountQuery.append(" ardais_parent_acct_comp_desc, ardais_acct_open_date,");
    if (Constants.ACCOUNT_STATUS_ACTIVE.equalsIgnoreCase(accountData.getStatus())) {
      createAccountQuery.append(" ardais_acct_active_date,");
    }
    else {
      createAccountQuery.append(" ardais_acct_deactivate_date,");
    }
    createAccountQuery
        .append(" request_mgr_email_address, linked_cases_only_yn, password_policy_cid, password_life_span,");
    createAccountQuery.append(" contact_phone_number, contact_phone_ext,");
    createAccountQuery.append(" contact_fax_number, contact_email_address, ");
    createAccountQuery.append(" contact_mobile_number, contact_pager_number,");
    createAccountQuery.append(" sample_aliases_unique_yn, sample_aliases_required_yn)");
    createAccountQuery
        .append(" values (?, ?, ?, ?, ?, sysdate, sysdate, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

    //build the query to create the row in ardais_address
    StringBuffer createAddressQuery = new StringBuffer(100);
    createAddressQuery.append("{ CALL INSERT INTO ardais_address (");
    createAddressQuery.append(" address_id, ardais_acct_key, address_type,");
    createAddressQuery.append(" address_1, address_2, addr_city, addr_state,");
    createAddressQuery.append(" addr_zip_code, addr_country,");
    createAddressQuery.append(" first_name, last_name, middle_name)");
    createAddressQuery.append(" VALUES (ARD_ADDRID_SEQ.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ");
    createAddressQuery.append("RETURNING address_id INTO ? }");

    //build the query to update the row in es_ardais_account
    StringBuffer updateAccountQuery = new StringBuffer(300);
    updateAccountQuery.append("update es_ardais_account set contact_address_id = ?");
    updateAccountQuery.append(" where ardais_acct_key = ?");

    //execute the query
    Connection con = null;
    PreparedStatement pstmt1 = null;
    BigrCallableStatement cstmt = null;
    PreparedStatement pstmt2 = null;
    try {
      con = ApiFunctions.getDbConnection();

      //create the account
      pstmt1 = con.prepareStatement(createAccountQuery.toString());
      pstmt1.setString(1, accountData.getId());
      pstmt1.setString(2, accountData.getType());
      pstmt1.setString(3, accountData.getStatus());
      pstmt1.setString(4, accountData.getName());
      pstmt1.setString(5, (String) Constants.ACCOUNT_TYPE_MAP.get(accountData.getType()));
      pstmt1.setString(6, accountData.getRequestManagerEmail());
      pstmt1.setString(7, accountData.getViewLinkedCasesOnly());
      pstmt1.setString(8, accountData.getPasswordPolicy());
      pstmt1.setInt(9, new Integer(accountData.getPasswordLifeSpan()).intValue());
      pstmt1.setString(10, accountData.getContact().getPhoneNumber());
      pstmt1.setString(11, accountData.getContact().getExtension());
      pstmt1.setString(12, accountData.getContact().getFaxNumber());
      pstmt1.setString(13, accountData.getContact().getEmail());
      pstmt1.setString(14, accountData.getContact().getMobileNumber());
      pstmt1.setString(15, accountData.getContact().getPagerNumber());
      pstmt1.setString(16, accountData.getRequireUniqueSampleAliases());
      pstmt1.setString(17, accountData.getRequireSampleAliases());
      pstmt1.execute();

      //create the address
      cstmt = new BigrCallableStatement(con, createAddressQuery.toString());
      cstmt.setString(1, accountData.getId());
      cstmt.setString(2, com.ardais.bigr.es.helpers.FormLogic.ADDR_CONTACT);
      cstmt.setString(3, accountData.getContact().getAddress().getAddress1());
      cstmt.setString(4, accountData.getContact().getAddress().getAddress2());
      cstmt.setString(5, accountData.getContact().getAddress().getCity());
      cstmt.setString(6, accountData.getContact().getAddress().getState());
      cstmt.setString(7, accountData.getContact().getAddress().getZipCode());
      cstmt.setString(8, accountData.getContact().getAddress().getCountry());
      cstmt.setString(9, accountData.getContact().getAddress().getFirstName());
      cstmt.setString(10, accountData.getContact().getAddress().getLastName());
      cstmt.setString(11, accountData.getContact().getAddress().getMiddleName());
      cstmt.registerOutParameter(12, Types.INTEGER);
      cstmt.execute();
      Integer addressId = new Integer(cstmt.getInt(cstmt.getNumBindVariables()));
      ApiFunctions.close(cstmt);

      //update the account
      pstmt2 = con.prepareStatement(updateAccountQuery.toString());
      pstmt2.setInt(1, addressId.intValue());
      pstmt2.setString(2, accountData.getId());
      pstmt2.execute();
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(pstmt1);
      ApiFunctions.close(cstmt);
      ApiFunctions.close(pstmt2);
      ApiFunctions.close(con);
    }
  }

  /*
   * Private method to update an existing account
   */
  private void modifyAccount(AccountDto accountData) {

    //get the existing account data
    AccountDto currentAccount = IltdsUtils.getAccountById(accountData.getId(), true, false);

    boolean isDeactivated = Constants.ACCOUNT_STATUS_ACTIVE.equalsIgnoreCase(currentAccount
        .getStatus())
        && Constants.ACCOUNT_STATUS_INACTIVE.equalsIgnoreCase(accountData.getStatus());

    boolean isActivated = Constants.ACCOUNT_STATUS_INACTIVE.equalsIgnoreCase(currentAccount
        .getStatus())
        && Constants.ACCOUNT_STATUS_ACTIVE.equalsIgnoreCase(accountData.getStatus());

    //build the query to update the row in es_ardais_account
    StringBuffer updateAccountStatement = new StringBuffer(300);
    updateAccountStatement.append("update es_ardais_account set");
    updateAccountStatement.append(" ardais_acct_type = ?, ardais_acct_status = ?,");
    updateAccountStatement
        .append(" ardais_acct_company_desc = ?, ardais_parent_acct_comp_desc = ?,");
    if (isActivated) {
      updateAccountStatement
          .append(" ardais_acct_active_date = sysdate, ardais_acct_deactivate_date = null,");
    }
    if (isDeactivated) {
      updateAccountStatement
          .append(" ardais_acct_active_date = null, ardais_acct_deactivate_date = sysdate,");
    }
    updateAccountStatement
        .append(" request_mgr_email_address = ?, linked_cases_only_yn = ?, password_policy_cid = ?, password_life_span = ?,");
    updateAccountStatement.append(" donor_registration_form = ?,");
    updateAccountStatement.append(" contact_phone_number = ?, contact_phone_ext = ?,");
    updateAccountStatement.append(" contact_fax_number = ?, contact_email_address = ?,");
    updateAccountStatement.append(" contact_mobile_number = ?, contact_pager_number = ?,");
    updateAccountStatement.append(" sample_aliases_unique_yn = ?, sample_aliases_required_yn = ?");
    updateAccountStatement.append(" where ardais_acct_key = ?");

    //build the query to update the row in ardais_address
    StringBuffer updateAddressStatement = new StringBuffer(100);
    updateAddressStatement.append("update ardais_address set");
    updateAddressStatement.append(" address_1 = ?, address_2 = ?, addr_city = ?, addr_state = ?,");
    updateAddressStatement.append(" addr_zip_code = ?, addr_country = ?,");
    updateAddressStatement.append(" first_name = ?, last_name = ?, middle_name = ?");
    updateAddressStatement.append(" where address_id = ?");

    //execute the queries
    Connection con = null;
    PreparedStatement pstmt1 = null;
    PreparedStatement pstmt2 = null;
    try {
      con = ApiFunctions.getDbConnection();

      //update the account
      pstmt1 = con.prepareStatement(updateAccountStatement.toString());
      pstmt1.setString(1, accountData.getType());
      pstmt1.setString(2, accountData.getStatus());
      pstmt1.setString(3, accountData.getName());
      pstmt1.setString(4, (String) Constants.ACCOUNT_TYPE_MAP.get(accountData.getType()));
      pstmt1.setString(5, accountData.getRequestManagerEmail());
      pstmt1.setString(6, accountData.getViewLinkedCasesOnly());
      pstmt1.setString(7, accountData.getPasswordPolicy());
      pstmt1.setInt(8, new Integer(accountData.getPasswordLifeSpan()).intValue());
      pstmt1.setString(9, accountData.getDonorRegistrationFormId());
      pstmt1.setString(10, accountData.getContact().getPhoneNumber());
      pstmt1.setString(11, accountData.getContact().getExtension());
      pstmt1.setString(12, accountData.getContact().getFaxNumber());
      pstmt1.setString(13, accountData.getContact().getEmail());
      pstmt1.setString(14, accountData.getContact().getMobileNumber());
      pstmt1.setString(15, accountData.getContact().getPagerNumber());
      pstmt1.setString(16, accountData.getRequireUniqueSampleAliases());
      pstmt1.setString(17, accountData.getRequireSampleAliases());
      pstmt1.setString(18, accountData.getId());
      pstmt1.execute();

      //update the address
      pstmt2 = con.prepareStatement(updateAddressStatement.toString());
      pstmt2.setString(1, accountData.getContact().getAddress().getAddress1());
      pstmt2.setString(2, accountData.getContact().getAddress().getAddress2());
      pstmt2.setString(3, accountData.getContact().getAddress().getCity());
      pstmt2.setString(4, accountData.getContact().getAddress().getState());
      pstmt2.setString(5, accountData.getContact().getAddress().getZipCode());
      pstmt2.setString(6, accountData.getContact().getAddress().getCountry());
      pstmt2.setString(7, accountData.getContact().getAddress().getFirstName());
      pstmt2.setString(8, accountData.getContact().getAddress().getLastName());
      pstmt2.setString(9, accountData.getContact().getAddress().getMiddleName());
      pstmt2.setString(10, currentAccount.getContactAddressId());
      pstmt2.execute();

      //update form definition tags as appropriate
      removeTagsForAccount(currentAccount);
      addTagsForAccount(accountData);
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(pstmt1);
      ApiFunctions.close(pstmt2);
      ApiFunctions.close(con);
    }
  }

  /*
   * Private method to create a new account location
   */
  private void createAccountLocation(AccountDto accountData) {

    //build the query to create the row in iltds_geography_location
    StringBuffer createLocationQuery = new StringBuffer(300);
    createLocationQuery.append("insert into iltds_geography_location (");
    createLocationQuery
        .append(" location_address_id, location_name, location_address_1, location_address_2,");
    createLocationQuery
        .append(" location_city, location_state, location_zip, location_country, location_phone)");
    createLocationQuery.append(" values (?, ?, ?, ?, ?, ?, ?, ?, ?)");

    //build the query to create the row in ardais_address
    StringBuffer createAddressQuery = new StringBuffer(100);
    createAddressQuery.append("INSERT INTO ardais_address (");
    createAddressQuery.append(" address_id, ardais_acct_key, address_type,");
    createAddressQuery.append(" address_1, address_2, addr_city, addr_state,");
    createAddressQuery.append(" addr_zip_code, addr_country,");
    createAddressQuery.append(" first_name, last_name, middle_name)");
    createAddressQuery.append(" VALUES (ARD_ADDRID_SEQ.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ");

    //build the query to update the row in es_ardais_account
    StringBuffer updateAccountQuery = new StringBuffer(300);
    updateAccountQuery.append("update es_ardais_account set primary_location = ?");
    updateAccountQuery.append(" where ardais_acct_key = ?");

    LocationData locationData = accountData.getLocation();

    Connection con = null;
    PreparedStatement pstmt1 = null;
    PreparedStatement pstmt2 = null;
    PreparedStatement pstmt3 = null;
    try {
      con = ApiFunctions.getDbConnection();

      //create the location
      pstmt1 = con.prepareStatement(createLocationQuery.toString());
      pstmt1.setString(1, locationData.getAddressId());
      pstmt1.setString(2, locationData.getName());
      pstmt1.setString(3, locationData.getAddress1());
      pstmt1.setString(4, locationData.getAddress2());
      pstmt1.setString(5, locationData.getCity());
      pstmt1.setString(6, locationData.getState());
      pstmt1.setString(7, locationData.getZipCode());
      pstmt1.setString(8, locationData.getCountry());
      pstmt1.setString(9, locationData.getPhoneNumber());
      pstmt1.execute();

      //create the ship to address
      pstmt2 = con.prepareStatement(createAddressQuery.toString());
      pstmt2.setString(1, accountData.getId());
      pstmt2.setString(2, com.ardais.bigr.es.helpers.FormLogic.ADDR_SHIP_TO);
      pstmt2.setString(3, locationData.getShipToAddress().getAddress1());
      pstmt2.setString(4, locationData.getShipToAddress().getAddress2());
      pstmt2.setString(5, locationData.getShipToAddress().getCity());
      pstmt2.setString(6, locationData.getShipToAddress().getState());
      pstmt2.setString(7, locationData.getShipToAddress().getZipCode());
      pstmt2.setString(8, locationData.getShipToAddress().getCountry());
      pstmt2.setString(9, locationData.getShipToAddress().getFirstName());
      pstmt2.setString(10, locationData.getShipToAddress().getLastName());
      pstmt2.setString(11, locationData.getShipToAddress().getMiddleName());
      pstmt2.execute();

      //update the account
      pstmt3 = con.prepareStatement(updateAccountQuery.toString());
      pstmt3.setString(1, locationData.getAddressId());
      pstmt3.setString(2, accountData.getId());
      pstmt3.execute();
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(pstmt1);
      ApiFunctions.close(pstmt2);
      ApiFunctions.close(pstmt3);
      ApiFunctions.close(con);
    }
  }

  /*
   * Private method to modify an existing account location
   */
  private void modifyAccountLocation(AccountDto accountData) {

    LocationData locationData = accountData.getLocation();

    //build the query to modify the row in iltds_geography_location
    StringBuffer updateLocationQuery = new StringBuffer(300);
    updateLocationQuery.append("update iltds_geography_location set");
    updateLocationQuery
        .append(" location_name = ?, location_address_1 = ?, location_address_2 = ?,");
    updateLocationQuery.append(" location_city = ?, location_state = ?, location_zip = ?,");
    updateLocationQuery.append(" location_country = ?, location_phone = ?");
    updateLocationQuery.append(" where location_address_id = ?");

    //build the query to update the ship to row in ardais_address
    StringBuffer updateAddressStatement = new StringBuffer(100);
    updateAddressStatement.append("update ardais_address set");
    updateAddressStatement.append(" address_1 = ?, address_2 = ?, addr_city = ?, addr_state = ?,");
    updateAddressStatement.append(" addr_zip_code = ?, addr_country = ?,");
    updateAddressStatement.append(" first_name = ?, last_name = ?, middle_name = ?");
    updateAddressStatement.append(" where ardais_acct_key = ? and address_type = '");
    updateAddressStatement.append(com.ardais.bigr.es.helpers.FormLogic.ADDR_SHIP_TO);
    updateAddressStatement.append("'");

    //execute the query
    Connection con = null;
    PreparedStatement pstmt1 = null;
    PreparedStatement pstmt2 = null;
    try {
      con = ApiFunctions.getDbConnection();

      //update the location
      pstmt1 = con.prepareStatement(updateLocationQuery.toString());
      pstmt1.setString(1, locationData.getName());
      pstmt1.setString(2, locationData.getAddress1());
      pstmt1.setString(3, locationData.getAddress2());
      pstmt1.setString(4, locationData.getCity());
      pstmt1.setString(5, locationData.getState());
      pstmt1.setString(6, locationData.getZipCode());
      pstmt1.setString(7, locationData.getCountry());
      pstmt1.setString(8, locationData.getPhoneNumber());
      pstmt1.setString(9, locationData.getAddressId());
      pstmt1.execute();

      //update the ship to address
      pstmt2 = con.prepareStatement(updateAddressStatement.toString());
      pstmt2.setString(1, locationData.getShipToAddress().getAddress1());
      pstmt2.setString(2, locationData.getShipToAddress().getAddress2());
      pstmt2.setString(3, locationData.getShipToAddress().getCity());
      pstmt2.setString(4, locationData.getShipToAddress().getState());
      pstmt2.setString(5, locationData.getShipToAddress().getZipCode());
      pstmt2.setString(6, locationData.getShipToAddress().getCountry());
      pstmt2.setString(7, locationData.getShipToAddress().getFirstName());
      pstmt2.setString(8, locationData.getShipToAddress().getLastName());
      pstmt2.setString(9, locationData.getShipToAddress().getMiddleName());
      pstmt2.setString(10, accountData.getId());
      pstmt2.execute();
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(pstmt1);
      ApiFunctions.close(pstmt2);
      ApiFunctions.close(con);
    }
  }

  /*
   * Private method to create new storage units for an account location
   */
  private void createStorageUnits(LocationData locationData) {
    List newStorageUnits = locationData.getNewStorageUnits();
    if (ApiFunctions.isEmpty(newStorageUnits)) {
      return;
    }
    //build the query to create the row in iltds_box_location
    StringBuffer createStorageUnitQuery = new StringBuffer(300);
    createStorageUnitQuery.append("insert into iltds_box_location (");
    createStorageUnitQuery.append(" location_address_id, room_id, drawer_id, slot_id,");
    createStorageUnitQuery
        .append(" box_barcode_id, available_ind, location_status, unit_name, storage_type_cid)");
    createStorageUnitQuery.append(" values (?, ?, ?, ?, ?, ?, ?, ?, ?)");

    //execute the query
    Connection con = null;
    PreparedStatement pstmt = null;
    try {
      con = ApiFunctions.getDbConnection();

      //create the storage units
      pstmt = con.prepareStatement(createStorageUnitQuery.toString());

      Iterator newStorageUnitIterator = newStorageUnits.iterator();
      while (newStorageUnitIterator.hasNext()) {
        StorageUnitDto newStorageUnit = (StorageUnitDto) newStorageUnitIterator.next();
        int numberOfDrawers = (new Integer(newStorageUnit.getNumberOfDrawers())).intValue();
        int slotsPerDrawer = (new Integer(newStorageUnit.getSlotsPerDrawer())).intValue();
        for (int i = 1; i <= numberOfDrawers; i++) {
          for (int j = 1; j <= slotsPerDrawer; j++) {
            pstmt.setString(1, locationData.getAddressId());
            pstmt.setString(2, newStorageUnit.getRoomId());
            pstmt.setString(3, i + "");
            pstmt.setString(4, com.ardais.bigr.orm.helpers.FormLogic.translateSlotIdToLetter(j));
            pstmt.setString(5, "");
            pstmt.setString(6, "Y");
            pstmt.setString(7, "A");
            pstmt.setString(8, newStorageUnit.getUnitName());
            pstmt.setString(9, newStorageUnit.getStorageTypeCui());
            pstmt.execute();
          }
        }
      }
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(pstmt);
      ApiFunctions.close(con);
    }
  }

  private boolean isStorageUnitEmpty(StorageUnitDto storageUnit) {
    boolean returnValue = false;
    if (storageUnit == null) {
      returnValue = true;
    }
    else {
      if (ApiFunctions.isEmpty(storageUnit.getNumberOfDrawers())
          && ApiFunctions.isEmpty(storageUnit.getRoomId())
          && ApiFunctions.isEmpty(storageUnit.getSlotsPerDrawer())
          && ApiFunctions.isEmpty(storageUnit.getStorageTypeCui())
          && ApiFunctions.isEmpty(storageUnit.getUnitName())) {
        returnValue = true;
      }
    }
    return returnValue;
  }

  /*
   * Method to determine if there are any accounts or donors that use the specified form definition
   * as the donor registration form
   */

  public boolean isAccountDonorRegForm(String formId) {

    List regForms = new ArrayList();

    FormDefinitionServiceResponse response = FormDefinitionService.SINGLETON
        .findFormDefinitionById(formId);

    if (response.getFormDefinition() != null) {
      DataFormDefinition dataForms[] = response.getDataFormDefinitions();
      for (int i = 0; i < dataForms.length; i++) {
        // there are some forms that use this form definition so need to
        // investigate if any of them are account registration forms. if
        // they are, add them to the list and get the account...
        DataFormDefinition dataForm = dataForms[i];
        Tag tags[] = dataForm.getTags();
        for (int j = 0; j < tags.length; j++) {
          Tag tag = tags[j];
          if (tag.getType().equalsIgnoreCase(TagTypes.REGISTRATION_FORM_DONOR)) {
            return true;
          }
        }
      }
    }

    // if we did not match on the account level, then we need to determine if
    // there are any donors that use this registration form...

    StringBuffer donorQuery = new StringBuffer(300);
    donorQuery.append("select 1 from pdc_ardais_donor ");
    donorQuery.append(" where donor_registration_form = ?");

    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    try {
      con = ApiFunctions.getDbConnection();

      pstmt = con.prepareStatement(donorQuery.toString());
      pstmt.setString(1, formId);
      pstmt.execute();

      rs = ApiFunctions.queryDb(pstmt, con);
      return rs.next();
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(pstmt);
      ApiFunctions.close(con);
    }

    return false;
  }

  /*
   * Method to determine if there are any policies that use the specified form definition as a case
   * registration form
   */

  public boolean isPolicyCaseRegForm(String formId) {

    List regForms = new ArrayList();

    FormDefinitionServiceResponse response = FormDefinitionService.SINGLETON
        .findFormDefinitionById(formId);

    if (response.getFormDefinition() != null) {
      DataFormDefinition dataForms[] = response.getDataFormDefinitions();
      for (int i = 0; i < dataForms.length; i++) {
        // there are some forms that use this form definition so need to
        // investigate if any of them are case registration forms. if
        // they are, add them to the list and get the account...
        DataFormDefinition dataForm = dataForms[i];
        Tag tags[] = dataForm.getTags();
        for (int j = 0; j < tags.length; j++) {
          Tag tag = tags[j];
          if (tag.getType().equalsIgnoreCase(TagTypes.REGISTRATION_FORM_CASE)) {
            regForms.add(tag.getValue());
          }
        }
      }
    }

    if (regForms.isEmpty()) {

      // if we did not match on the case policy level, then we need to determine if
      // there are any cases that use this registration form...

      StringBuffer caseQuery = new StringBuffer(300);
      caseQuery.append("select 1 from iltds_informed_consent ");
      caseQuery.append(" where case_registration_form = ?");

      Connection con = null;
      PreparedStatement pstmt = null;
      ResultSet rs = null;

      try {
        con = ApiFunctions.getDbConnection();

        pstmt = con.prepareStatement(caseQuery.toString());
        pstmt.setString(1, formId);
        pstmt.execute();

        rs = ApiFunctions.queryDb(pstmt, con);
        return rs.next();
      }
      catch (Exception e) {
        ApiFunctions.throwAsRuntimeException(e);
      }
      finally {
        ApiFunctions.close(pstmt);
        ApiFunctions.close(con);
      }

    }
    else {
      return true;
    }

    return false;
  }

  /*
   * Method to determine if there are any sample types in policies that use the specified form
   * definition as a sample registration form
   */

  public boolean isSampleRegForm(String formId) {

    List regForms = new ArrayList();

    FormDefinitionServiceResponse response = FormDefinitionService.SINGLETON
        .findFormDefinitionById(formId);

    if (response.getFormDefinition() != null) {
      DataFormDefinition dataForms[] = response.getDataFormDefinitions();
      for (int i = 0; i < dataForms.length; i++) {
        // there are some forms that use this form definition so need to
        // investigate if any of them are sample registration forms. if
        // they are, add them to the list and get the account...
        DataFormDefinition dataForm = dataForms[i];
        Tag tags[] = dataForm.getTags();
        for (int j = 0; j < tags.length; j++) {
          Tag tag = tags[j];
          if (tag.getType().equalsIgnoreCase(TagTypes.REGISTRATION_FORM_SAMPLE)) {
            regForms.add(tag.getValue());
          }
        }
      }
    }

    if (regForms.isEmpty()) {
      // if we did not match on the sample policy level, then we need to determine if
      // there are any samples that use this registration form...

      StringBuffer sampleQuery = new StringBuffer(300);
      sampleQuery.append("select 1 from iltds_sample ");
      sampleQuery.append(" where sample_registration_form = ?");

      Connection con = null;
      PreparedStatement pstmt = null;
      ResultSet rs = null;

      try {
        con = ApiFunctions.getDbConnection();

        pstmt = con.prepareStatement(sampleQuery.toString());
        pstmt.setString(1, formId);
        pstmt.execute();

        rs = ApiFunctions.queryDb(pstmt, con);
        return rs.next();
      }
      catch (Exception e) {
        ApiFunctions.throwAsRuntimeException(e);
      }
      finally {
        ApiFunctions.close(pstmt);
        ApiFunctions.close(con);
      }

    }
    else {
      return true;
    }
    return false;
  }

  private void removeUnauthorizedEntities(List authorizedEntities, Map entities) {
    if (!ApiFunctions.isEmpty(entities)) {
      Iterator keyIterator = entities.keySet().iterator();
      while (keyIterator.hasNext()) {
        removeUnauthorizedEntities(authorizedEntities, (List) entities.get(keyIterator.next()));
      }
    }
  }

  private void removeUnauthorizedEntities(List authorizedEntities, List entities) {
    if (!ApiFunctions.isEmpty(entities)) {
      entities.retainAll(ApiFunctions.safeList(authorizedEntities));
    }
  }

  public void disableUser(String accountId, String userId, String reason, SecurityInfo securityInfo) {
    BTXDetailsDisableLogin disableDetails = new BTXDetailsDisableLogin();

    // We don't know who the user really is yet, so we set the userId and account that will get
    // logged in BTX transaction history to a generic IT user. See the comments on the
    // GENERIC_BTX_LOGIN_* constants for more details. If we don't do this, then the BeanUtils
    // copyProperties that populates loginDetails initially will copy the userId property
    // from the login form to it, which is wrong.
    disableDetails.setUserId(ApiProperties.getProperty("api.bigr.bootstrap.user"));
    disableDetails.setUserAccount(ApiProperties.getProperty("api.bigr.bootstrap.account"));
    disableDetails.setAttemptUser(userId);
    disableDetails.setAttemptAccount(accountId);
    disableDetails.setLoggedInUserSecurityInfo(securityInfo, true);
    disableDetails.setBeginTimestamp(new Timestamp(System.currentTimeMillis()));
    disableDetails = (BTXDetailsDisableLogin) Btx.perform(disableDetails, "orm_disable_user");
    ApiFunctions.generateEmail(ApiProperties.getProperty(ApiResources.API_MAIL_FROM_DEFAULT),
        ApiProperties.getProperty("api.custserv.email"), "User has been made inactive",
        createEmailBody(userId, accountId, reason));
  }

  /*
   * Method to determine if an account contains any samples with duplicated alias values.  Note
   * that the query performed in this method includes both samples that have been only box scanned
   * as well as samples that have been fully registered, since all samples must be taken into
   * account for this check.  It is insufficient to consider only fully registered samples.
   */
  private int getSamplesWithDuplicatedAliasesCount(String accountId) {
    int result = 0;

    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
      StringBuffer query = new StringBuffer(300);
      query.append("select count(distinct(s1.sample_barcode_id)) count from iltds_sample s1, iltds_sample s2");
      query.append(" where lower(s1.ardais_acct_key) = lower(?)");
      query.append(" and s1.CUSTOMER_ID = s2.CUSTOMER_ID");
      query.append(" and s1.ARDAIS_ACCT_KEY = s2.ARDAIS_ACCT_KEY");
      query.append(" and s1.CUSTOMER_ID is not null");
      query.append(" and s2.CUSTOMER_ID is not null");
      query.append(" and s1.SAMPLE_BARCODE_ID <> s2.SAMPLE_BARCODE_ID");
      
      con = ApiFunctions.getDbConnection();
      pstmt = con.prepareStatement(query.toString());
      pstmt.setString(1, accountId);
      rs = ApiFunctions.queryDb(pstmt, con);
  
      rs.next();
      result = rs.getInt(1);
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(con, pstmt, rs);
    }

    return result;
  }

  /*
   * Method to determine if an account contains any samples with missing alias values.  Note
   * that the query performed in this method includes only samples that have been fully registered, 
   * since any samples that have only gone through box scan will be required to have an alias when
   * they go through full registration.  In other words, it is sufficient to consider only fully 
   * registered samples for this check.
   */
  private int getSamplesWithMissingAliasesCount(String accountId) {
    int result = 0;

    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
      StringBuffer query = new StringBuffer(300);
      query.append("select count(s1.sample_barcode_id) count from iltds_sample s1");
      query.append(" where lower(s1.ardais_acct_key) = lower(?)");
      query.append(" and s1.CUSTOMER_ID is null");
      query.append(" and s1.asm_id is not null");
      
      con = ApiFunctions.getDbConnection();
      pstmt = con.prepareStatement(query.toString());
      pstmt.setString(1, accountId);
      rs = ApiFunctions.queryDb(pstmt, con);
  
      rs.next();
      result = rs.getInt(1);
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(con, pstmt, rs);
    }

    return result;
  }

  /*
   * Method to determine if an account contains any samples with duplicated alias values.  Note
   * that the query performed in this method includes both samples that have been only box scanned
   * as well as samples that have been fully registered, since all samples must be taken into
   * account for this check.  It is insufficient to consider only fully registered samples.
   */
  private List getSampleInfo(List sampleIds) {
    List returnValue = new ArrayList();

    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
      StringBuffer query = new StringBuffer(300);
      query.append("select * from iltds_sample s");
      query.append(" where s.sample_barcode_id in ");
      query.append(ApiFunctions.makeBindParameterList(sampleIds.size()));
      
      con = ApiFunctions.getDbConnection();
      pstmt = con.prepareStatement(query.toString());
      ApiFunctions.bindBindParameterList(pstmt, 1, sampleIds);
      rs = ApiFunctions.queryDb(pstmt, con);
      Map columnNames = DbUtils.getColumnNames(rs);
      while (rs.next()) {
        SampleData sample = new SampleData(columnNames, rs);
        returnValue.add(sample);
      }
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(con, pstmt, rs);
    }

    return returnValue;
  }

  private static String createEmailBody(String userId, String accountId, String reason) {
    String body = "The following user has been made inactive because " + reason + "<BR>"
        + "User ID: " + userId + "<BR>" + "Account ID: " + accountId + "<BR>" + "Regards <BR>"
        + "WebMaster";

    return body;
  }
}
