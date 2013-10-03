package com.ardais.bigr.iltds.performers;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.api.Escaper;
import com.ardais.bigr.api.ValidateIds;
import com.ardais.bigr.btx.BTXDetailsHistoryNote;
import com.ardais.bigr.btx.framework.BtxTransactionPerformerBase;
import com.ardais.bigr.iltds.bizlogic.RequestFinder;
import com.ardais.bigr.iltds.btx.BTXDetails;
import com.ardais.bigr.iltds.btx.BtxActionError;
import com.ardais.bigr.iltds.btx.BtxActionMessage;
import com.ardais.bigr.iltds.databeans.CaseData;
import com.ardais.bigr.iltds.databeans.LogicalRepositoryExtendedData;
import com.ardais.bigr.iltds.databeans.PolicyExtendedData;
import com.ardais.bigr.iltds.databeans.SampleData;
import com.ardais.bigr.iltds.helpers.FormLogic;
import com.ardais.bigr.iltds.helpers.IltdsUtils;
import com.ardais.bigr.iltds.helpers.RequestSelect;
import com.ardais.bigr.iltds.icp.ejb.session.IcpOperation;
import com.ardais.bigr.iltds.icp.ejb.session.IcpOperationHome;
import com.ardais.bigr.javabeans.AccountDto;
import com.ardais.bigr.javabeans.BoxDto;
import com.ardais.bigr.javabeans.BoxLayoutExtendedDto;
import com.ardais.bigr.javabeans.ManifestDto;
import com.ardais.bigr.javabeans.RequestDto;
import com.ardais.bigr.pdc.beans.DDCDonor;
import com.ardais.bigr.pdc.beans.DDCDonorHome;
import com.ardais.bigr.pdc.helpers.PdcUtils;
import com.ardais.bigr.pdc.javabeans.DonorData;
import com.ardais.bigr.security.SecurityInfo;
import com.ardais.bigr.util.BoxLayoutUtils;
import com.ardais.bigr.util.EjbHomes;
import com.ardais.bigr.util.IcpUtils;
import com.ardais.bigr.util.PolicyUtils;

public class BtxPerformerHistoryNoteOperations extends BtxTransactionPerformerBase {

  /**
   * Invoke the specified method on this class.  This is only meant to be
   * called from BtxTransactionPerformerBase, please don't call it from anywhere
   * else as a mechanism to gain access to private methods in this class.  Every
   * object that the BTX framework dispatches to must contain this
   * method definition, and its implementation should be exactly the same
   * in each class.  Please don't alter this method or its implementation
   * in any way.
   */
  protected BTXDetails invokeBtxEntryPoint(Method method, BTXDetails btxDetails) throws Exception {

    // **** DO NOT EDIT THIS METHOD, see the Javadoc comment above.
    return (BTXDetails) method.invoke(this, new Object[] { btxDetails });
  }

  /**
   * This is the main BTX entry point for performing a Placeholder
   * business transaction.  A placeholder transaction just returns its
   * btxDetails argument unchanged.  It is useful for transactions that
   * need to be logged in the BTX transaction log but that haven't been
   * fully migrated to the BTX session bean framework yet (for example,
   * they are still implemented in the op or Action class).
   */
  protected BTXDetails performPlaceholder(BTXDetails btxDetails) throws Exception {
    return btxDetails;
  }

  /**
   * Please keep this method synchronized with the
   * IcpQuery.invoke() method.  Any business rule change for determining accessibilty
   * should be reflected in both methods, and new types of ICP objects need to be
   * reflected in both places.
   * 
   * @param btxDetails
   * @return
   * @throws Exception
   */
  private BTXDetails validatePerformAddHistoryNote(BTXDetailsHistoryNote btxDetails) throws Exception {
    // Trim the note of whitespaces.
    String note = ApiFunctions.safeTrim(btxDetails.getNote());
    btxDetails.setNote(note);
    
    // Check if the note is empty.
    if (ApiFunctions.isEmpty(note)) {
      btxDetails.addActionError(new BtxActionError("error.noValue", "note"));
    }

    // Check if the set of objects is empty
    Set involvedObjects = btxDetails.getInvolvedObjects();
    if (involvedObjects.isEmpty()) {
      btxDetails.addActionError(new BtxActionError("iltds.error.icp.historyNote.emptyList"));
    }
    
    ValidateIds validator = new ValidateIds();
    
    //walk the set of involved objects, creating a set of system ids.  This step replaces any 
    //aliases that may have been passed in with the corresponding system id, if one can be located.  
    //If we cannot determine the system id from a given alias, return an error
    Set systemIds = new HashSet();
    Iterator idIterator = involvedObjects.iterator();
    while (idIterator.hasNext()) {
      String id = ApiFunctions.safeTrim((String)idIterator.next());
      String validatedId = validator.validate(id, ValidateIds.TYPESET_ICP, true);
      //if the id passed in corresponds to the id of an ICP enabled entity, just add it to the
      //set of system ids
      if (validatedId != null) {
        systemIds.add(validatedId);
      }
      //otherwise, check to see if it is an alias.  If so, the following rules apply:
      // 1) If there are multiple objects in the system with the specified alias (i.e. a donor
      //    and a case, 2 samples, etc) return an error to let the user know that they must specify
      //    the system id to use
      // 2) If there is a single object in the system with the specified alias,
      //    - if the object is a donor or case, use it
      //    - if the object is a sample id
      //      - if the account to which that sample belongs requires unique aliases, use it
      //      - if the account to which that sample belongs does not require unique aliases, return
      //        an error because we cannot be certain the sample we found is the one the user
      //        intended.
      // 3) If there are no objects in the system with the specified alias return an error
      else {
        List donorIdsFromAlias = PdcUtils.findDonorIdsFromCustomerId(id);
        List caseIdsFromAlias = IltdsUtils.findConsentIdsFromCustomerId(id);
        //note - retrieve a list of samples (instead of just ids) so we can
        //determine if the alias uniquely identifies the sample
        List samplesFromAlias = IltdsUtils.findSamplesFromCustomerId(id, false);
        List allMatches = new ArrayList();
        allMatches.addAll(donorIdsFromAlias);
        allMatches.addAll(caseIdsFromAlias);
        allMatches.addAll(samplesFromAlias);
        //if no corresponding ids were found, return an error
        if (allMatches.size() > 1) {
          btxDetails.addActionError(new BtxActionError("error.alias.multiplesExist", Escaper.htmlEscapeAndPreserveWhitespace(id)));
        }
        //if a single id was found, just use it as the validated id if it is a donor or case
        //otherwise make sure it uniquely identifies a sample.
        else if (allMatches.size() == 1) {
          if (donorIdsFromAlias.size() == 1 || caseIdsFromAlias.size() == 1) {
            validatedId = (String)allMatches.get(0);
            systemIds.add(validatedId);
          }
          else {
            com.ardais.bigr.javabeans.SampleData sample = (com.ardais.bigr.javabeans.SampleData)allMatches.get(0);
            String accountId = sample.getArdaisAcctKey();
            AccountDto accountDto = IltdsUtils.getAccountById(accountId, false, false);
            boolean aliasMustBeUnique = FormLogic.DB_YES.equalsIgnoreCase(accountDto.getRequireUniqueSampleAliases());
            //if the account to which the sample belongs does not require unique sample aliases,
            //return an error since we cannot be sure the sample we found was the one intended.
            if (!aliasMustBeUnique) {
              btxDetails.addActionError(new BtxActionError("error.alias.notUnique", Escaper.htmlEscapeAndPreserveWhitespace(id)));
            }
            else {
              validatedId = sample.getSampleId();
              systemIds.add(validatedId);
            }
          }
        }
        else if (allMatches.size() == 0) {
          btxDetails.addActionError(new BtxActionError("iltds.error.icp.historyNote.doesNotExist", Escaper.htmlEscapeAndPreserveWhitespace(id)));
        }
      }
    }

    Set validInvolvedObjects = new HashSet();

    Iterator iterator = systemIds.iterator();
    while (iterator.hasNext()) {

      String id = ApiFunctions.safeTrim((String)iterator.next());
      String validatedId = validator.validate(id, ValidateIds.TYPESET_ICP, true);
      
      if (ApiFunctions.isEmpty(validatedId)) {
        btxDetails.addActionError(new BtxActionError("iltds.error.icp.historyNote.invalidId", Escaper.htmlEscape(id)));
      }
      else {
        validInvolvedObjects.add(validatedId);
        // validatedIdType is one of the type codes from the ValidateIds class.  These codes
        // are different from the type codes that go into the IcpData.setType() field (the latter
        // type codes are the codes defined in the TypeFinder class).
        //
        String validatedIdType = validator.getType();
        
        // ********NOTE********: Please keep this method synchronized with the
        // IcpQuery.invoke() method.  Any business rule change for determining accessibilty
        // should be reflected in both methods, and new types of ICP objects need to be
        // reflected in both places.

        if (ValidateIds.TYPESET_SAMPLE.contains(validatedIdType)) {
          processSample(btxDetails, validatedId);
        }
        else if (ValidateIds.TYPESET_CASE.contains(validatedIdType)) {
          processCase(btxDetails, validatedId);
        }
        else if (ValidateIds.TYPESET_DONOR.contains(validatedIdType)) {
          processDonor(btxDetails, validatedId);
        }
        else if (ValidateIds.TYPESET_BOX.contains(validatedIdType)) {
          processBox(btxDetails, validatedId);
        }
        else if (ValidateIds.TYPESET_MANIFEST.contains(validatedIdType)) {
          processManifest(btxDetails, validatedId);
        }
        else if (ValidateIds.TYPESET_REQUEST.contains(validatedIdType)) {
          processRequest(btxDetails, validatedId);
        }
        else if (ValidateIds.TYPESET_LOGICAL_REPOSITORY.contains(validatedIdType)) {
          processLogicalRepository(btxDetails, validatedId);
        }
        else if (ValidateIds.TYPESET_POLICY.contains(validatedIdType)) {
          processPolicy(btxDetails, validatedId);
        }
        else if (ValidateIds.TYPESET_BOX_LAYOUT.contains(validatedIdType)) {
          processBoxLayout(btxDetails, validatedId);
        }
        else {
          processGenericType(btxDetails, validatedId);
        }
      }
    }
    btxDetails.setInvolvedObjects(validInvolvedObjects);
    return btxDetails;
  }

  protected BTXDetails performAddHistoryNote(BTXDetailsHistoryNote btxDetails) throws Exception {

    Set involvedObjects = btxDetails.getDirectlyInvolvedObjects();
    boolean start = false;
    StringBuffer objectIds = new StringBuffer();
    
    //in order for the success message to include donor/case/sample aliases, retrieve that
    //information from the database
    Map systemIdsToAliases = null;
    if (involvedObjects.size() > 0) {
      List systemIds = new ArrayList(involvedObjects);
      systemIdsToAliases = getAliasesForSystemIds(systemIds);
    }
    
    if (involvedObjects.size() > 0) {
      Iterator objects = involvedObjects.iterator();
      while (objects.hasNext()) {
        String objectId = objects.next().toString();
        String alias = Escaper.htmlEscapeAndPreserveWhitespace((String)systemIdsToAliases.get(objectId));
        if (!start) {
          start = true;
          objectIds.append(objectId);
          if (!ApiFunctions.isEmpty(alias)) {
            objectIds.append(" (");
            objectIds.append(alias);
            objectIds.append(")");
          }
        }
        else {
          objectIds.append(", ");
          objectIds.append(objectId);
          if (!ApiFunctions.isEmpty(alias)) {
            objectIds.append(" (");
            objectIds.append(alias);
            objectIds.append(")");
          }
        }        
      }
    }
    btxDetails.addActionMessage(new BtxActionMessage("iltds.message.icp.historyNote.historyNoteAdded", objectIds.toString()));
    btxDetails.setActionForwardTXCompleted("success");

    return btxDetails;
  }
  
  /**
   * 
   * @param btxDetails
   * @param itemId
   * @throws Exception
   */
  private void processSample(BTXDetailsHistoryNote btxDetails, String itemId) throws Exception {
    
    SecurityInfo securityInfo = btxDetails.getLoggedInUserSecurityInfo();
    
    SampleData sampleData = new SampleData();
    sampleData.setSample_id(itemId);
    IcpOperationHome home = (IcpOperationHome) EjbHomes.getHome(IcpOperationHome.class);
    IcpOperation icpOp = home.create();
    sampleData = icpOp.getSampleData(sampleData, securityInfo, true, true);
    
    // A user must be a ICP_SUPERUSER or sample is controlled by inventory group access and
    // sample is BMS.
    // Per MR 7515, these rules are different than the ICP visibility rules in that here we
    // allow ICP Superuser and only allow access based on inventory group membership for BMS
    // samples.
    if (!securityInfo.isHasPrivilege(SecurityInfo.PRIV_ICP_SUPERUSER)) {
      if (!(IcpUtils.isItemAccessibleToUserByInvGroup(securityInfo, itemId) && sampleData.isBms())) {
        unauthorizedMessage(btxDetails, itemId);
      }
    }

    // Check of item exist.
    if (sampleData.isExists()) {

      // If the user is restricted to viewing only linked cases, return an error
      // message if this sample belongs to a case that is not linked
      // note: this will not handle imported cases, but we do not believe the linked case
      //        privilege will be used with those cases...      
      if (securityInfo.isHasPrivilege(SecurityInfo.PRIV_LINKED_CASES_ONLY)
        && !ApiFunctions.isEmpty(sampleData.getCase_id())
        && !ValidateIds.PREFIX_CASE_LINKED.equalsIgnoreCase(
          sampleData.getCase_id().substring(0, 2))) {
        unauthorizedMessage(btxDetails, itemId + " - it does not belong to a linked case");
      }
    }
    else {
      btxDetails.addActionError(new BtxActionError("iltds.error.icp.historyNote.doesNotExist", Escaper.htmlEscape(itemId)));
    }
  }
  
  /**
   * 
   * @param btxDetails
   * @param itemId
   * @throws Exception
   */
  private void processCase(BTXDetailsHistoryNote btxDetails, String itemId) throws Exception {

    SecurityInfo securityInfo = btxDetails.getLoggedInUserSecurityInfo();
    
    // The user can see the case if any of the following are true:
    // 1) they are an ICP super-user
    // 2) they are in the account that created the case
    // If the user is not authorized to view this case, return a message telling them so.
    // Per MR 7515, these rules are different than the ICP visibility rules in that here we
    // don't allow access based on inventory group membership.
    if (!isAuthorizedToViewCase(securityInfo, itemId)) {
      unauthorizedMessage(btxDetails, itemId);
    }

    // If the user is restricted to linked cases only and the case id is not for a linked case,
    // return a message telling them they cannot access the case
    // note: this will not handle imported cases, but we do not believe the linked case
    //        privilege will be used with those cases...
    if (securityInfo.isHasPrivilege(SecurityInfo.PRIV_LINKED_CASES_ONLY)
      && !ValidateIds.PREFIX_CASE_LINKED.equalsIgnoreCase(itemId.substring(0, 2))) {
      unauthorizedMessage(btxDetails, itemId + " - it is not a linked case");
    }

    CaseData caseData = new CaseData();
    caseData.setCase_id(itemId);

    // Go get the case information, but don't retrieve the ASM and sample
    // information - we no longer show ASM information in ICP (MR7322) and the 
    // samples will be retrieved below.
    IcpOperationHome home = (IcpOperationHome) EjbHomes.getHome(IcpOperationHome.class);
    IcpOperation icpOp = home.create();
    caseData = icpOp.getCaseData(caseData, securityInfo, true);

    // Check of item exist.
    if (!caseData.isExists()) {
      btxDetails.addActionError(new BtxActionError("iltds.error.icp.historyNote.doesNotExist", Escaper.htmlEscape(itemId)));
    }
  }
  
  /**
   * 
   * @param btxDetails
   * @param itemId
   * @throws Exception
   */
  private void processDonor(BTXDetailsHistoryNote btxDetails, String itemId) throws Exception {

    SecurityInfo securityInfo = btxDetails.getLoggedInUserSecurityInfo();
    
    // The user can see the case if any of the following are true:
    // 1) they are an ICP super-user
    // 2) they are in the account that created the donor or one of the donor's cases
    //  We have to check both the donor account and the donor's cases' accounts because currently,
    //  we sometimes have case records for a donor but no donor record in PDC_ARDAIS_DONOR.
    // If the user is not authorized to view this donor, return a message telling them so.
    // Per MR 7515, these rules are different than the ICP visibility rules in that here we
    // don't allow access based on inventory group membership.
    if (!isAuthorizedToViewDonor(securityInfo, itemId)) {
      unauthorizedMessage(btxDetails, itemId);
    }

    DonorData donorData = new DonorData();
    donorData.setArdaisId(itemId);
    DDCDonorHome home = (DDCDonorHome) EjbHomes.getHome(DDCDonorHome.class);
    DDCDonor ddcOp = home.create();
    donorData = ddcOp.buildDonorData(donorData);

    // Check of item exist.
    if (!donorData.isExists()) {
      btxDetails.addActionError(new BtxActionError("iltds.error.icp.historyNote.doesNotExist", Escaper.htmlEscape(itemId)));
    }
  }
  
  /**
   * 
   * @param btxDetails
   * @param itemId
   * @throws Exception
   */
  private void processBox(BTXDetailsHistoryNote btxDetails, String itemId) throws Exception {

    SecurityInfo securityInfo = btxDetails.getLoggedInUserSecurityInfo();

    // A user is allowed to view the box if they are a repository manager or ICP Superuser.
    if (!(securityInfo.isInRole(SecurityInfo.ROLE_REPOSITORY_MANAGER)
      || securityInfo.isHasPrivilege(SecurityInfo.PRIV_ICP_SUPERUSER))) {
      unauthorizedMessage(btxDetails, itemId);
    }

    BoxDto boxData = new BoxDto();
    boxData.setBoxId(itemId);
    IcpOperationHome home = (IcpOperationHome) EjbHomes.getHome(IcpOperationHome.class);
    IcpOperation icpOp = home.create();
    boxData = icpOp.getBoxData(boxData, securityInfo, true);

    // Check of item exist.
    if (!boxData.isExists()) {
      btxDetails.addActionError(new BtxActionError("iltds.error.icp.historyNote.doesNotExist", Escaper.htmlEscape(itemId)));
    }
  }
  
  /**
   * 
   * @param btxDetails
   * @param itemId
   * @throws Exception
   */
  private void processManifest(BTXDetailsHistoryNote btxDetails, String itemId) throws Exception {

    SecurityInfo securityInfo = btxDetails.getLoggedInUserSecurityInfo();

    // A manifest is visible to
    //  (1) ICP Superusers, and
    //  (2) Repository managers whose account is on either the sending or
    //      receiving end of the manifest.
    //
    // When the specified manifest doesn't exist, there could still be history data to display
    // (for example, a manifest record was deleted for some reason).  In that case, we can't
    // determine what the sending/receiving ends of the manifest are, so we only allow
    // ICP Superusers to see those items.

    // When we get the manifest data, we only get the basic data plus to/from address
    // information, we don't get details about manifest contents.  Because of the
    // way we store the information about boxes for manifests, it is only reliable/accurate
    // for a limited time in the manifest's life cycle.  In particular, after a manifest
    // has been received, the manifest-box information in the database can no longer be
    // relied upon to accurately reflect what boxes were in the manifest or what the contents
    // of those boxes were.  The only reliable way for an ICP user to view the manifest
    // contents is to look at the manifest's transaction history records.

    ManifestDto manifestDto = IltdsUtils.getManifestById(itemId, false);

    if (manifestDto == null) {
      // Item doesn't exist, but may still have history.  Only let ICP Superusers see
      // items like this.

      if (!securityInfo.isHasPrivilege(SecurityInfo.PRIV_ICP_SUPERUSER)) {
        unauthorizedMessage(btxDetails, itemId);
      }
      else {
        // Check if manifest id exist in involved object table.
        if (!IcpUtils.isInvolvedObjectId(securityInfo, itemId)) {
          btxDetails.addActionError(new BtxActionError("iltds.error.icp.historyNote.doesNotExist", Escaper.htmlEscape(itemId)));
        }
      }
    }
    else {
      // The manifest exists.  The user must either be an ICP superuser, or a repository manager
      // in the same account as the sending or receiving end of the manifest.

      if (!securityInfo.isHasPrivilege(SecurityInfo.PRIV_ICP_SUPERUSER)) {
        boolean canAccess = false;
        if (securityInfo.isInRole(SecurityInfo.ROLE_REPOSITORY_MANAGER)) {
          String userAccount = securityInfo.getAccount();
          if (userAccount.equals(manifestDto.getShipFromAddress().getAddressAccountId())
            || userAccount.equals(manifestDto.getShipToAddress().getAddressAccountId())) {
            canAccess = true;
          }
        }
        if (!canAccess) {
          unauthorizedMessage(btxDetails, itemId);
        }
      }
    }
  }
  
  /**
   * 
   * @param btxDetails
   * @param itemId
   * @throws Exception
   */
  private void processRequest(BTXDetailsHistoryNote btxDetails, String itemId) throws Exception {

    SecurityInfo securityInfo = btxDetails.getLoggedInUserSecurityInfo();

    // A request is visible to
    //  (1) ICP Superusers, and
    //  (2) The user who created the request, and
    //  (3) Anyone who has the Manage Requests privilege who is in the same account
    //      as the user who created the request (the intent is that request managers can
    //      see the same requests in ICP that they can see in the Manager Requests page).
    //
    // When the specified request doesn't exist, there could still be history data to display
    // (for example, a request record was deleted for some reason).  In that case, we can't
    // determine who created the request, so we only allow ICP Superusers to see those
    // items.

    RequestDto requestDto =
      RequestFinder.find(securityInfo, RequestSelect.BASIC_PLUS_ITEM_BASICS, itemId);

    if (requestDto == null) {
      // Item doesn't exist, but may still have history.  Only let ICP Superusers see
      // items like this.

      if (!securityInfo.isHasPrivilege(SecurityInfo.PRIV_ICP_SUPERUSER)) {
        unauthorizedMessage(btxDetails, itemId);
      }
      else {
        // Check if request id exist in involved object table.
        if (!IcpUtils.isInvolvedObjectId(securityInfo, itemId)) {
          btxDetails.addActionError(new BtxActionError("iltds.error.icp.historyNote.doesNotExist", Escaper.htmlEscape(itemId)));
        }
      }
    }
    else {
      // The request exists.  The user must either be an ICP superuser, the user who created the
      // request, or a request manager in the same account as the user who created the request.

      if (!securityInfo.isHasPrivilege(SecurityInfo.PRIV_ICP_SUPERUSER)
        && !securityInfo.getUsername().equals(requestDto.getRequesterId())) {
        if ((!securityInfo.isHasPrivilege(SecurityInfo.PRIV_ILTDS_MANAGE_REQUESTS))
          || (!securityInfo.getAccount().equals(requestDto.getRequesterAccount()))) {
          unauthorizedMessage(btxDetails, itemId);
        }
      }
    }
  }
  
  /**
   * 
   * @param btxDetails
   * @param itemId
   * @throws Exception
   */
  private void processLogicalRepository(BTXDetailsHistoryNote btxDetails, String itemId) throws Exception {

    SecurityInfo securityInfo = btxDetails.getLoggedInUserSecurityInfo();

    // A user is allowed to view the inventory group only if they are either an ICP Superuser
    // or have the Maintain Inventory Groups privilege.  We currently don't check that the user
    // has access rights to the inventory group, since currently we all people to manage
    // inventory groups that they can't necessarily see samples in. 

    // The real is as stored in the database is just a number, not the IG... id that users
    // see in ICP, so we have to translate.
    //
    String realId = FormLogic.makeRealLogicalRepositoryId(itemId);

    if (!IcpUtils.isAuthorizedToViewInventoryGroupIcpPages(securityInfo)) {
      unauthorizedMessage(btxDetails, itemId);
    }

    IcpOperationHome home = (IcpOperationHome) EjbHomes.getHome(IcpOperationHome.class);
    IcpOperation icpOp = home.create();
    LogicalRepositoryExtendedData data = icpOp.getLogicalRepositoryData(realId, securityInfo);

    // Check of item exist.
    if (!data.isExists()) {
      btxDetails.addActionError(new BtxActionError("iltds.error.icp.historyNote.doesNotExist", Escaper.htmlEscape(itemId)));
    }
  }
  
  /**
   * 
   * @param btxDetails
   * @param itemId
   * @throws Exception
   */
  private void processPolicy(BTXDetailsHistoryNote btxDetails, String itemId) throws Exception {

    SecurityInfo securityInfo = btxDetails.getLoggedInUserSecurityInfo();

    // A user is allowed to view the policy only if they are either an ICP Superuser
    // or have the Maintain Policy privilege.

    // The real id as stored in the database is just a number, not the PY... id that users
    // see in ICP, so we have to translate.
    String realId = FormLogic.makeRealPolicyId(itemId);

    if ((!securityInfo.isHasPrivilege(SecurityInfo.PRIV_ICP_SUPERUSER))
      && (!securityInfo.isHasPrivilege(SecurityInfo.PRIV_ORM_MAINTAIN_POLICIES))) {
      unauthorizedMessage(btxDetails, itemId);
    }

    PolicyExtendedData data =
      (PolicyExtendedData) PolicyUtils.getPolicyData(realId, false, PolicyExtendedData.class);

    // Check of item exist.
    if (data == null) {
      btxDetails.addActionError(new BtxActionError("iltds.error.icp.historyNote.doesNotExist", Escaper.htmlEscape(itemId)));
    }
  }
  
  /**
   * 
   * @param btxDetails
   * @param itemId
   * @throws Exception
   */
  private void processBoxLayout(BTXDetailsHistoryNote btxDetails, String itemId) throws Exception {

    SecurityInfo securityInfo = btxDetails.getLoggedInUserSecurityInfo();

    if (!IcpUtils.isAuthorizedToViewBoxLayout(securityInfo, itemId)) {
      unauthorizedMessage(btxDetails, itemId);
    }

    BoxLayoutExtendedDto data =
      (BoxLayoutExtendedDto) BoxLayoutUtils.getBoxLayoutDto(
        itemId,
        false,
        BoxLayoutExtendedDto.class);

    // Check of item exist.
    if (data == null) {
      btxDetails.addActionError(new BtxActionError("iltds.error.icp.historyNote.doesNotExist", Escaper.htmlEscape(itemId)));
    }
  }
  
  /**
   * 
   * @param btxDetails
   * @param itemId
   * @throws Exception
   */
  private void processGenericType(BTXDetailsHistoryNote btxDetails, String itemId) throws Exception {

    SecurityInfo securityInfo = btxDetails.getLoggedInUserSecurityInfo();

    if (!securityInfo.isHasPrivilege(SecurityInfo.PRIV_ICP_SUPERUSER)) {
      unauthorizedMessage(btxDetails, itemId);
    }
  }
  
  /**
   * 
   * @param btxDetails
   * @param str
   */
  private void unauthorizedMessage(BTXDetailsHistoryNote btxDetails, String str) {
    btxDetails.addActionError(new BtxActionError("iltds.error.icp.historyNote.notAuthorized", Escaper.htmlEscape(str)));
  }

  /**
   * Return true if the specified user is authorized to view the ICP page for a given donor.
   * 
   * @param securityInfo - the logged in user's security info
   * @param donorId - the donor id they want to view
   * @return boolean - true if the user can view the donor, false otherwise
   */
  private boolean isAuthorizedToViewDonor(SecurityInfo securityInfo, String donorId) {
    //The user can see the case if any of the following are true:
    //1) they are an ICP super-user
    //2) they are in the account that created the donor or one of the donor's cases
    // We have to check both the donor account and the donor's cases' accounts because currently,
    // we sometimes have case records for a donor but no donor record in PDC_ARDAIS_DONOR.
    // Per MR 7515, these rules are different than the ICP visibility rules in that here we
    // don't allow access based on inventory group membership.
   return securityInfo.isHasPrivilege(SecurityInfo.PRIV_ICP_SUPERUSER)
      || IcpUtils.isDonorAccessibleToUserByAccount(securityInfo, donorId);
  }

  /**
   * Return true if the specified user is authorized to view the ICP page for a given case.
   * 
   * @param securityInfo - the logged in user's security info
   * @param caseId - the case id they want to view
   * @return boolean - true if the user can view the case, false otherwise
   */
  public static boolean isAuthorizedToViewCase(SecurityInfo securityInfo, String caseId) {
    //The user can see the case if any of the following are true:
    //1) they are an ICP super-user
    //2) they are in the account that created the case
    // Per MR 7515, these rules are different than the ICP visibility rules in that here we
    // don't allow access based on inventory group membership.
    return securityInfo.isHasPrivilege(SecurityInfo.PRIV_ICP_SUPERUSER)
      || securityInfo.getAccount().equalsIgnoreCase(IltdsUtils.getAccountForCase(caseId));
  }

  //private method used to get alias values for a list of system ids
  private Map getAliasesForSystemIds(List systemIds) {
    Map returnValue = new HashMap();
    Connection connection = null;
    PreparedStatement pstmt = null;
    ResultSet results = null;
    StringBuffer query = new StringBuffer(200);
    
    //build the query
    query.append("select d.ardais_id system_id, d.customer_id ");
    query.append("from pdc_ardais_donor d ");
    query.append("where d.ardais_id in ");
    query.append(ApiFunctions.makeBindParameterList(systemIds.size()));
    query.append(" union ");
    query.append("select c.consent_id system_id, c.customer_id ");
    query.append("from iltds_informed_consent c ");
    query.append("where c.consent_id in ");
    query.append(ApiFunctions.makeBindParameterList(systemIds.size()));
    query.append(" union ");
    query.append("select s.sample_barcode_id system_id, s.customer_id ");
    query.append("from iltds_sample s ");
    query.append("where s.sample_barcode_id in ");
    query.append(ApiFunctions.makeBindParameterList(systemIds.size()));
    try {
      connection = ApiFunctions.getDbConnection();
      pstmt = connection.prepareStatement(query.toString());
      ApiFunctions.bindBindParameterList(pstmt, 1, systemIds);
      ApiFunctions.bindBindParameterList(pstmt, 1 + systemIds.size(), systemIds);
      ApiFunctions.bindBindParameterList(pstmt, 1 + systemIds.size() + systemIds.size(), systemIds);
      results = ApiFunctions.queryDb(pstmt, connection);
      while (results.next()) {
        returnValue.put(results.getString(1), results.getString(2));
      }
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(connection, pstmt, results);
    }
    return returnValue;
  }
}
