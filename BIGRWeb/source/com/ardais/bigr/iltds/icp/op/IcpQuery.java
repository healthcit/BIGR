package com.ardais.bigr.iltds.icp.op;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.api.ValidateIds;
import com.ardais.bigr.btx.framework.Btx;
import com.ardais.bigr.iltds.beans.BTXHistoryReader;
import com.ardais.bigr.iltds.beans.BTXHistoryReaderHome;
import com.ardais.bigr.iltds.bizlogic.RequestFinder;
import com.ardais.bigr.iltds.databeans.CaseData;
import com.ardais.bigr.iltds.databeans.IcpData;
import com.ardais.bigr.iltds.databeans.LogicalRepositoryExtendedData;
import com.ardais.bigr.iltds.databeans.PolicyExtendedData;
import com.ardais.bigr.iltds.databeans.SampleData;
import com.ardais.bigr.iltds.helpers.FormLogic;
import com.ardais.bigr.iltds.helpers.IltdsUtils;
import com.ardais.bigr.iltds.helpers.RequestSelect;
import com.ardais.bigr.iltds.helpers.TypeFinder;
import com.ardais.bigr.iltds.icp.ejb.session.IcpOperation;
import com.ardais.bigr.iltds.icp.ejb.session.IcpOperationHome;
import com.ardais.bigr.iltds.op.StandardOperation;
import com.ardais.bigr.javabeans.BoxDto;
import com.ardais.bigr.javabeans.BoxLayoutExtendedDto;
import com.ardais.bigr.javabeans.ManifestDto;
import com.ardais.bigr.javabeans.RequestDto;
import com.ardais.bigr.javabeans.RoleDto;
import com.ardais.bigr.kc.form.def.BigrFormDefinition;
import com.ardais.bigr.kc.form.def.BtxDetailsKcFormDefinitionLookup;
import com.ardais.bigr.kc.form.helpers.FormUtils;
import com.ardais.bigr.library.btx.BTXDetailsGetSamples;
import com.ardais.bigr.library.web.helper.ResultsHelper;
import com.ardais.bigr.pdc.beans.DDCDonor;
import com.ardais.bigr.pdc.beans.DDCDonorHome;
import com.ardais.bigr.pdc.helpers.PdcUtils;
import com.ardais.bigr.pdc.javabeans.DonorData;
import com.ardais.bigr.query.ColumnPermissions;
import com.ardais.bigr.query.ViewParams;
import com.ardais.bigr.security.SecurityInfo;
import com.ardais.bigr.util.BoxLayoutUtils;
import com.ardais.bigr.util.EjbHomes;
import com.ardais.bigr.util.IcpUtils;
import com.ardais.bigr.util.PolicyUtils;
import com.ardais.bigr.util.RoleUtils;
import com.ardais.bigr.util.WebUtils;
import org.apache.struts.Globals;
import org.apache.struts.util.MessageResources;

public class IcpQuery extends StandardOperation {
  
  private String _resultsFormDefinitionId;
  private List _resultsFormDefinitions;

  public IcpQuery(HttpServletRequest req, HttpServletResponse res, ServletContext ctx) {
    super(req, res, ctx);
  }
  
  /**
   * Please keep this method synchronized with the
   * BtxPerformerHistoryNoteOperations.validatePerformAddHistoryNote() method. Any business rule
   * change for determining accessibilty should be reflected in both methods.
   */
  public void invoke() throws Exception {
    SecurityInfo securityInfo = WebUtils.getSecurityInfo(request);

    // Check that the user has ICP privs.  This should never fail, since
    // we take user privs into account when deciding whether to generate
    // ICP links, but since it would be very bad to let an unauthorized
    // user into ICP we double-check here.
    if (!IcpUtils.isHasIcpPrivilege(securityInfo)) {
      throw new SecurityException("You are not authorized to view ICP.");
    }

    //determine if the id passed in corresponds to the id of an ICP enabled entity
    String id = ApiFunctions.safeTrim(request.getParameter("id"));
    ValidateIds validator = new ValidateIds();
    String validatedId = validator.validate(id, ValidateIds.TYPESET_ICP, true);
    
    //if the id passed in is empty, direct the user to retry
    if (ApiFunctions.isEmpty(id)) {
      retry(validator.getErrorMessage("an ICP-supported id"));
      return;
    }

    
    //if the id passed in corresponds to the id of an ICP enabled entity, process it
    if (validatedId != null) {
      processSingleId(securityInfo, validatedId);
    }
    //if the id passed in does not correspond to the id of an ICP enabled entity, 
    //see if the value entered corresponds to an alias for a donor, case, or sample
    else {
      List donorIdsFromAlias = PdcUtils.findDonorIdsFromCustomerId(id);
      List caseIdsFromAlias = IltdsUtils.findConsentIdsFromCustomerId(id);
      List sampleIdsFromAlias = IltdsUtils.findSampleIdsFromCustomerId(id, false);
      List allIds = new ArrayList();
      allIds.addAll(donorIdsFromAlias);
      allIds.addAll(caseIdsFromAlias);
      allIds.addAll(sampleIdsFromAlias);
      //if no corresponding ids were found, return an error
      if (allIds.size() == 0) {
        retry(validator.getErrorMessage("an ICP-supported id"));
      }
      //if a single id was found, just use it as the validated id
      else if (allIds.size() == 1) {
        validatedId = (String)allIds.get(0);
        processSingleId(securityInfo, validatedId);
      }
      //if multiple ids were found, process them
      else {
        processMultipleIds(securityInfo, id, donorIdsFromAlias, caseIdsFromAlias, sampleIdsFromAlias);
      }
    }
  }

  /**
   * Populate <code>icpData</code> with itransaction history nformation about the item
   * whose id is in <code>icpData.getId()</code>.  The information will be placed in the
   * {@link IcpData.getHistory()} property.
   * 
   * @param securityInfo The SecurityInfo for the logged-in user.
   * @param icpData The icpData that specifies the item id, and that will be populated with
   *    result data.
   */
  private void populateHistory(SecurityInfo securityInfo, IcpData icpData) throws Exception {
    String itemId = icpData.getId();

    BTXHistoryReaderHome readerHome = (BTXHistoryReaderHome) EjbHomes.getHome(BTXHistoryReaderHome.class);
    BTXHistoryReader btxHist = readerHome.create();
    List history = btxHist.getHistoryDisplayLines(itemId, securityInfo);

    icpData.setHistory(history);
  }
  
  private void processMultipleIds(SecurityInfo securityInfo, String alias, 
                                  List donorIds, List caseIds, List sampleIds) throws Exception {
    //iterate over each list, removing any ids the user is not allowed to access and storing the
    //information for those those they can access
    List allIds = new ArrayList();
    List donors = new ArrayList();
    List cases = new ArrayList();
    List samples = new ArrayList();
    Iterator donorIterator = donorIds.iterator();
    while (donorIterator.hasNext()) {
      String donorId = (String) donorIterator.next();
      IcpData icpData = new IcpData();
      icpData.setId(donorId);
      String msg = processDonor(securityInfo, icpData);
      if (ApiFunctions.isEmpty(msg)) {
        allIds.add(donorId);
        donors.add(icpData.getData());
      }
    }
    Iterator caseIterator = caseIds.iterator();
    while (caseIterator.hasNext()) {
      String caseId = (String) caseIterator.next();
      IcpData icpData = new IcpData();
      icpData.setId(caseId);
      String msg = processCase(securityInfo, icpData);
      if (ApiFunctions.isEmpty(msg)) {
        allIds.add(caseId);
        cases.add(icpData.getData());
      }
    }
    Iterator sampleIterator = sampleIds.iterator();
    while (sampleIterator.hasNext()) {
      String sampleId = (String) sampleIterator.next();
      IcpData icpData = new IcpData();
      icpData.setId(sampleId);
      String msg = processSample(securityInfo, icpData);
      if (ApiFunctions.isEmpty(msg)) {
        allIds.add(sampleId);
        samples.add(icpData.getData());
      }
    }
    
    //if no ids were left, return an error
    if (allIds.size() == 0) {
      retry("No information is available for " + alias);
    }
    //if a single id remains, process it
    else if (allIds.size() == 1) {
      String validatedId = (String)allIds.get(0);
      processSingleId(securityInfo, validatedId);
    }
    //if multiple ids remain, return the user to a page where they can select the 
    //specific object to view.
    else {
      Map map = new HashMap();
      map.put("alias", alias);
      map.put("donors", donors);
      map.put("cases", cases);
      map.put("samples", samples);
      IcpData icpData = new IcpData();
      icpData.setType(TypeFinder.MULTIPLEIDS);
      icpData.setData(map);
      request.setAttribute("icpData", icpData);
      servletCtx.getRequestDispatcher("/hiddenJsps/iltds/icp/icpResults.jsp").forward(
          request,
          response);
    }
  }
  
  private void processSingleId(SecurityInfo securityInfo, String validatedId) throws Exception {
    // validatedIdType is one of the type codes from the ValidateIds class.  These codes
    // are different from the type codes that go into the IcpData.setType() field (the latter
    // type codes are the codes defined in the TypeFinder class).
    ValidateIds validator = new ValidateIds();
    validatedId = validator.validate(validatedId, ValidateIds.TYPESET_ICP, true);
    String validatedIdType = validator.getType();

    IcpData icpData = new IcpData();
    icpData.setId(validatedId);
    
    //if the user has specified a results form definition to use for viewing any sample data, 
    //set it's value so that the various process* methods can make use of it
    setResultsFormDefinitionId(request.getParameter("resultsFormDefinitionId"));

    String errorMessage = null;
    
    // ********NOTE********: Please keep this method synchronized with the
    // BtxPerformerHistoryNoteOperations.validatePerformAddHistoryNote() method.
    // Any business rule change for determining accessibilty should be reflected in both
    // methods, and new types of ICP objects need to be reflected in both places.
    
    if (ValidateIds.TYPESET_SAMPLE.contains(validatedIdType)) {
      errorMessage = processSample(securityInfo, icpData);
    }
    else if (ValidateIds.TYPESET_CASE.contains(validatedIdType)) {
      errorMessage = processCase(securityInfo, icpData);
    }
    else if (ValidateIds.TYPESET_DONOR.contains(validatedIdType)) {
      errorMessage = processDonor(securityInfo, icpData);
    }
    else if (ValidateIds.TYPESET_BOX.contains(validatedIdType)) {
      errorMessage = processBox(securityInfo, icpData);
    }
    else if (ValidateIds.TYPESET_MANIFEST.contains(validatedIdType)) {
      errorMessage = processManifest(securityInfo, icpData);
    }
    else if (ValidateIds.TYPESET_REQUEST.contains(validatedIdType)) {
      errorMessage = processRequest(securityInfo, icpData);
    }
    else if (ValidateIds.TYPESET_LOGICAL_REPOSITORY.contains(validatedIdType)) {
      errorMessage = processLogicalRepository(securityInfo, icpData);
    }
    else if (ValidateIds.TYPESET_POLICY.contains(validatedIdType)) {
      errorMessage = processPolicy(securityInfo, icpData);
    }
    else if (ValidateIds.TYPESET_BOX_LAYOUT.contains(validatedIdType)) {
      errorMessage = processBoxLayout(securityInfo, icpData);
    }
    else if (ValidateIds.TYPESET_FORMDEFINITION.contains(validatedIdType)) {
      errorMessage = processFormDefinition(securityInfo, icpData);
    }
    else if (ValidateIds.TYPESET_ROLE.contains(validatedIdType)) {
      errorMessage = processRole(securityInfo, icpData);
    }
    else {
      errorMessage = processGenericType(securityInfo, icpData);
    }

    if (!ApiFunctions.isEmpty(errorMessage)) {
      retry(errorMessage);
      return;
    }

    if (icpData.getData() == null && ApiFunctions.isEmpty(icpData.getHistory())) {
      retry("No information is available for " + validatedId);
      return;
    }

    request.setAttribute("icpData", icpData);

    boolean printerFriendly = "Y".equalsIgnoreCase(ApiFunctions.safeTrim(request.getParameter("printerFriendly")));
    icpData.setPrinterFriendly(printerFriendly);
    
    //any process* methods that return sample information will have set the id of the results 
    //form definition used to retrieve sample information as well as the list of results form 
    //definitions from which the user may select, so pass that information back in the request
    request.setAttribute("resultsFormDefinitionId", getResultsFormDefinitionId());
    request.setAttribute("resultsFormDefinitions", getResultsFormDefinitions());

    servletCtx.getRequestDispatcher("/hiddenJsps/iltds/icp/icpResults.jsp").forward(
      request,
      response);
    
  }

  /**
   * Populate <code>icpData</code> with information about the item whose id is in
   * <code>icpData.getId()</code>.  This method assumes that the item id is the only
   * field already populated in <code>icpData</code>.  If there is an error that would
   * prevent this item from being displayed to the user in ICP, this method will return
   * a non-empty string that is the error message to be displayed to the user.  Otherwise
   * this returns a null or empty string.
   * 
   * <p>This does NOT return an error message if there's no information available for the
   * specified item.  In that case, it icpData's <code>data</code> and <code>history</code>
   * properties will both be empty, and there's item-type-independent code elsewhere that
   * tells the user that there's no information to display in that case.  An example of a
   * situation where an error message IS returned is when the user is not authorized to view
   * the specified item in ICP. 
   * 
   * <p>When this returns a non-empty string (indicating that there was some error), fields
   * in icpData other than the id field should be treated as undefined.
   * 
   * @param securityInfo The SecurityInfo for the logged-in user.
   * @param icpData The icpData that specifies the item id, and that will be populated with
   *    result data.
   * @return A non-empty string if there is a reason that the item can't or shouldn't be
   *    displayed to the user, otherwise a null or empty string.
   */
  private String processSample(SecurityInfo securityInfo, IcpData icpData) throws Exception {

	  if (!securityInfo.isHasPrivilege(SecurityInfo.PRIV_ORM_ACCESS_SAMPLE_VIEW)) // change what view (case, donor, sample)
	  {
		  final MessageResources messages = (MessageResources)servletCtx.getAttribute(Globals.MESSAGES_KEY);
		  request.setAttribute("myError", messages.getMessage("error.noPrivilege", "to see this info"));
		  servletCtx.getRequestDispatcher("/hiddenJsps/reportError/onlyError.jsp").forward(
			  request,
			  response);
	  }

    String itemId = icpData.getId();


    // check to see if the sample exists
    if (!IltdsUtils.sampleExists(itemId)) return null;

    // A user is allowed to view the sample if it is in an inventory group that they have
    // access to.  We don't check whether the user has the ICP_SUPERUSER privilege, since
    // that privilege does NOT trump inventory groups.

    if (!IcpUtils.isItemAccessibleToUserByInvGroup(securityInfo, itemId)) {
      return unauthorizedMessage(itemId);
    }

    icpData.setType(TypeFinder.SAMPLE);

    SampleData sampleData = new SampleData();
    sampleData.setSample_id(itemId);
    IcpOperationHome home = (IcpOperationHome) EjbHomes.getHome(IcpOperationHome.class);
    IcpOperation icpOp = home.create();
    sampleData = icpOp.getSampleData(sampleData, securityInfo, true, true);

    if (sampleData.isExists()) {
      icpData.setData(sampleData);

      //if the user is restricted to viewing only linked cases, return an error
      //message if this sample belongs to a case that is not linked
      // note: this will not handle imported cases, but we do not believe the linked case
      //        privilege will be used with those cases...      
      if (securityInfo.isHasPrivilege(SecurityInfo.PRIV_LINKED_CASES_ONLY)
        && !ApiFunctions.isEmpty(sampleData.getCase_id())
        && !ValidateIds.PREFIX_CASE_LINKED.equalsIgnoreCase(
          sampleData.getCase_id().substring(0, 2))) {
        return unauthorizedMessage(itemId + " - it does not belong to a linked case");
      }

      //get the information for the sample-selection-style table view of this sample
      String txType = ResultsHelper.TX_TYPE_ICP;
      ResultsHelper helper = ResultsHelper.get(txType, request);
      synchronized (helper) {
        helper.setIcpItemId(sampleData.getSample_id());
        helper.setProductType(ResultsHelper.PRODUCT_TYPE_SAMPLE);
        BTXDetailsGetSamples btx = new BTXDetailsGetSamples();
        btx.setLoggedInUserSecurityInfo(WebUtils.getSecurityInfo(request));
        int prod = ColumnPermissions.PROD_TISSUE;
        int tx = ResultsHelper.getTxBits(txType);
        int scrn = ColumnPermissions.SCRN_ICP;
        ViewParams vp = new ViewParams(securityInfo, prod, scrn, tx);
        btx.setViewParams(vp);
        //if a results form definition to use for querying/rendering the details
        //was specified, pass it along to the code that will get the results.  Note that
        //it's value was set in invoke()
        btx.setResultsFormDefinitionId(getResultsFormDefinitionId());
        helper.updateHelpers(btx);
        //the code that gets the results will have set the view profile it used, so set that id
        //so we can pass it back along with the list of results form definitions from which the
        //user may select
        setResultsFormDefinitionId(btx.getViewProfile().getResultsFormDefinitionId());
        setResultsFormDefinitions(FormUtils.getResultsFormDefinitionsForUser(securityInfo, true));
      }
    }

    // Since we do delete sample records in some cases (e.g. BIGR_DATA_UTILS.del_invalid_sample),
    // it is possible for a sample id to have history records but not exist in ILTDS_SAMPLE.
    // Since such samples are probably not still recorded as being in any inventory group (if
    // they were ever in one), history records for deleted samples will most likely only
    // be visible to users with the PRIV_VIEW_ALL_LOGICAL_REPOS privilege.
    //
    populateHistory(securityInfo, icpData);

    return null;
  }

  /**
   * @see #processSample(SecurityInfo, IcpData)
   */
  private String processCase(SecurityInfo securityInfo, IcpData icpData) throws Exception {

	  if (!securityInfo.isHasPrivilege(SecurityInfo.PRIV_ORM_ACCESS_CASE_VIEW)) // change what view (case, donor, sample)
	  {
		  final MessageResources messages = (MessageResources)servletCtx.getAttribute(Globals.MESSAGES_KEY);
		  request.setAttribute("myError", messages.getMessage("error.noPrivilege", "to see this info"));
		  servletCtx.getRequestDispatcher("/hiddenJsps/reportError/onlyError.jsp").forward(
			  request,
			  response);
	  }

    String itemId = icpData.getId();

    // check to see if the case exists
    if (!IltdsUtils.caseExists(itemId)) return null;
    
    //if the user is not authorized to view this case, return a message telling them so
    if (!IcpUtils.isAuthorizedToViewCase(securityInfo, itemId)) {
      return unauthorizedMessage(itemId);
    }

    //if the user is restricted to linked cases only and the case id is not for a linked case,
    //return a message telling them they cannot access the case
    // note: this will not handle imported cases, but we do not believe the linked case
    //        privilege will be used with those cases...
    if (securityInfo.isHasPrivilege(SecurityInfo.PRIV_LINKED_CASES_ONLY)
      && !ValidateIds.PREFIX_CASE_LINKED.equalsIgnoreCase(itemId.substring(0, 2))) {
      return unauthorizedMessage(itemId + " - it is not a linked case");
    }

    icpData.setType(TypeFinder.CASE);

    CaseData caseData = new CaseData();
    caseData.setCase_id(itemId);
    //go get the case information, but don't retrieve the ASM and sample
    //information - we no longer show ASM information in ICP (MR7322) and the 
    //samples will be retrieved below.
    IcpOperationHome home = (IcpOperationHome) EjbHomes.getHome(IcpOperationHome.class);
    IcpOperation icpOp = home.create();
    caseData = icpOp.getCaseData(caseData, securityInfo, true);

    if (caseData.isExists()) {
      icpData.setData(caseData);
      //get the information for the samples in this case
      String txType = ResultsHelper.TX_TYPE_ICP;
      ResultsHelper helper = ResultsHelper.get(txType, request);
      synchronized (helper) {
        helper.setIcpItemId(caseData.getCase_id());
        helper.setProductType(ResultsHelper.PRODUCT_TYPE_SAMPLE);
        BTXDetailsGetSamples btx = new BTXDetailsGetSamples();
        btx.setLoggedInUserSecurityInfo(WebUtils.getSecurityInfo(request));
        int prod = ColumnPermissions.PROD_TISSUE;
        int tx = ResultsHelper.getTxBits(txType);
        int scrn = ColumnPermissions.SCRN_ICP;
        ViewParams vp = new ViewParams(securityInfo, prod, scrn, tx);
        btx.setViewParams(vp);
        //if a results form definition to use for querying/rendering the details
        //was specified, pass it along to the code that will get the results.  Note that
        //it's value was set in invoke()
        btx.setResultsFormDefinitionId(getResultsFormDefinitionId());
        helper.updateHelpers(btx);
        //the code that gets the results will have set the view profile it used, so set that id
        //so we can pass it back along with the list of results form definitions from which the
        //user may select
        setResultsFormDefinitionId(btx.getViewProfile().getResultsFormDefinitionId());
        setResultsFormDefinitions(FormUtils.getResultsFormDefinitionsForUser(securityInfo, true));
      }
    }

    // Since we do delete case records in some cases (e.g. BIGR_DATA_UTILS.relink_asm_form),
    // it is possible for a case id to have history records but not exist in ILTDS_INFORMED_CONSENT.
    // Since samples in such cases have also been deleted, they are probably not still
    // recorded as being in any inventory group (if they were ever in one), so history records
    // for deleted cases will most likely only be visible to users with the
    // PRIV_VIEW_ALL_LOGICAL_REPOS privilege.
    //
    populateHistory(securityInfo, icpData);

    return null;
  }

  /**
   * @see #processSample(SecurityInfo, IcpData)
   */
  private String processDonor(SecurityInfo securityInfo, IcpData icpData) throws Exception {

	  if (!securityInfo.isHasPrivilege(SecurityInfo.PRIV_ORM_ACCESS_DONOR_VIEW)) // change what view (case, donor, sample)
	  {
		  final MessageResources messages = (MessageResources)servletCtx.getAttribute(Globals.MESSAGES_KEY);
		  request.setAttribute("myError", messages.getMessage("error.noPrivilege", "to see this info"));
		  servletCtx.getRequestDispatcher("/hiddenJsps/reportError/onlyError.jsp").forward(
			  request,
			  response);
	  }

    String itemId = icpData.getId();

    // check to see if donor exists
    DonorData donorData = new DonorData();
    donorData.setArdaisId(itemId);
    DDCDonorHome home = (DDCDonorHome) EjbHomes.getHome(DDCDonorHome.class);
    DDCDonor ddcOp = home.create();
    donorData = ddcOp.buildDonorData(donorData);

    if (!donorData.isExists()) return null;
    

    //if the user is not authorized to view this donor, return a message telling them so
    if (!IcpUtils.isAuthorizedToViewDonor(securityInfo, itemId)) {
      return unauthorizedMessage(itemId);
    }

    icpData.setType(TypeFinder.DONOR);

    if (donorData.isExists()) {
      icpData.setData(donorData);
      //get the information for this donor's samples
      String txType = ResultsHelper.TX_TYPE_ICP;
      ResultsHelper helper = ResultsHelper.get(txType, request);
      synchronized (helper) {
        helper.setIcpItemId(donorData.getArdaisId());
        helper.setProductType(ResultsHelper.PRODUCT_TYPE_SAMPLE);
        BTXDetailsGetSamples btx = new BTXDetailsGetSamples();
        btx.setLoggedInUserSecurityInfo(WebUtils.getSecurityInfo(request));
        int prod = ColumnPermissions.PROD_TISSUE;
        int tx = ResultsHelper.getTxBits(txType);
        int scrn = ColumnPermissions.SCRN_ICP;
        ViewParams vp = new ViewParams(securityInfo, prod, scrn, tx);
        btx.setViewParams(vp);
        //if a results form definition to use for querying/rendering the details
        //was specified, pass it along to the code that will get the results.  Note that
        //it's value was set in invoke()
        btx.setResultsFormDefinitionId(getResultsFormDefinitionId());
        helper.updateHelpers(btx);
        //the code that gets the results will have set the view profile it used, so set that id
        //so we can pass it back along with the list of results form definitions from which the
        //user may select
        setResultsFormDefinitionId(btx.getViewProfile().getResultsFormDefinitionId());
        setResultsFormDefinitions(FormUtils.getResultsFormDefinitionsForUser(securityInfo, true));
      }
    }

    // Since we may in the future delete donors in some cases,
    // it could be possible for a donor id to have history records but not currently exist.
    // Since samples in such donors have probably also been deleted, they are probably not still
    // recorded as being in any inventory group (if they were ever in one), so history records
    // for deleted donors will most likely only be visible to users with the
    // PRIV_VIEW_ALL_LOGICAL_REPOS privilege.
    //
    populateHistory(securityInfo, icpData);

    return null;
  }

  /**
   * @see #processSample(SecurityInfo, IcpData)
   */
  private String processBox(SecurityInfo securityInfo, IcpData icpData) throws Exception {
    String itemId = icpData.getId();

    // check to see if the box exists
    if (!IltdsUtils.boxExists(itemId)) return null;

    // A user is allowed to view the box if they are a repository manager or ICP Superuser.
    if (!(securityInfo.isInRole(SecurityInfo.ROLE_REPOSITORY_MANAGER)
      || securityInfo.isHasPrivilege(SecurityInfo.PRIV_ICP_SUPERUSER))) {
      return unauthorizedMessage(itemId);
    }

    icpData.setType(TypeFinder.BOX);

    BoxDto boxData = new BoxDto();
    boxData.setBoxId(itemId);
    IcpOperationHome home = (IcpOperationHome) EjbHomes.getHome(IcpOperationHome.class);
    IcpOperation icpOp = home.create();
    boxData = icpOp.getBoxData(boxData, securityInfo, true);

    if (boxData.isExists()) {
      icpData.setData(boxData);

      // We only show box attributes (as opposed to transaction history) to certain
      // users: users at the same location as the box is stored, or ICP superusers
      // regardless of where the box is stored or whether it is even stored anywhere at all).

      if (!securityInfo.isHasPrivilege(SecurityInfo.PRIV_ICP_SUPERUSER)) {
        String userLocation = securityInfo.getUserLocationId();
        String boxLocation = boxData.getLocation();
        if (ApiFunctions.isEmpty(userLocation)
          || ApiFunctions.isEmpty(boxLocation)
          || !userLocation.equals(boxLocation)) {
          // The value of the icpBoxAttributesRestricted attribute doesn't matter, we just
          // check for its presence/absence.
          request.setAttribute("icpBoxAttributesRestricted", "defined");
        }
      }
    }

    // Get history even if the item doesn't exist now, to robustly handle the 
    // case of items that may have been deleted but still have history.
    //
    populateHistory(securityInfo, icpData);

    return null;
  }

  /**
   * @see #processSample(SecurityInfo, IcpData)
   */
  private String processManifest(SecurityInfo securityInfo, IcpData icpData) throws Exception {
    String itemId = icpData.getId();

    // A manifest is visible to
    //  (1) ICP Superusers, and
    //  (2) Repository managers whose account is on either the sending or
    //      receiving end of the manifest.
    //
    // When the specified manifest doesn't exist, there could still be history data to display
    // (for example, a manifest record was deleted for some reason).  In that case, we can't
    // determine what the sending/receiving ends of the manifest are, so we only allow
    // ICP Superusers to see those items.

    icpData.setType(TypeFinder.MANIFEST);

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

      if (!IcpUtils.isInvolvedObjectId(securityInfo, itemId)) return null;

      if (!securityInfo.isHasPrivilege(SecurityInfo.PRIV_ICP_SUPERUSER)) {
        return unauthorizedMessage(itemId);
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
          return unauthorizedMessage(itemId);
        }
      }

      icpData.setData(manifestDto);
    }

    // Get history even if the item doesn't exist now, to robustly handle the 
    // case of items that may have been deleted but still have history.
    //
    populateHistory(securityInfo, icpData);

    return null;
  }

  /**
   * @see #processSample(SecurityInfo, IcpData)
   */
  private String processRequest(SecurityInfo securityInfo, IcpData icpData) throws Exception {
    String itemId = icpData.getId();

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

    icpData.setType(TypeFinder.REQUEST);

    RequestDto requestDto =
      RequestFinder.find(securityInfo, RequestSelect.BASIC_PLUS_ITEM_BASICS, itemId);

    if (requestDto == null) {
      // Item doesn't exist, but may still have history.  Only let ICP Superusers see
      // items like this.

      if (!IcpUtils.isInvolvedObjectId(securityInfo, itemId)) return null;

      if (!securityInfo.isHasPrivilege(SecurityInfo.PRIV_ICP_SUPERUSER)) {
        return unauthorizedMessage(itemId);
      }
    }
    else {
      // The request exists.  The user must either be an ICP superuser, the user who created the
      // request, or a request manager in the same account as the user who created the request.

      if (!securityInfo.isHasPrivilege(SecurityInfo.PRIV_ICP_SUPERUSER)
        && !securityInfo.getUsername().equals(requestDto.getRequesterId())) {
        if ((!securityInfo.isHasPrivilege(SecurityInfo.PRIV_ILTDS_MANAGE_REQUESTS))
          || (!securityInfo.getAccount().equals(requestDto.getRequesterAccount()))) {
          return unauthorizedMessage(itemId);
        }
      }

      icpData.setData(requestDto);
    }

    // Get history even if the item doesn't exist now, to robustly handle the 
    // case of items that may have been deleted but still have history.
    //
    populateHistory(securityInfo, icpData);

    return null;
  }

  /**
   * @see #processSample(SecurityInfo, IcpData)
   */
  private String processLogicalRepository(SecurityInfo securityInfo, IcpData icpData)
    throws Exception {

    String itemId = icpData.getId();



    // A user is allowed to view the inventory group only if they are either an ICP Superuser
    // or have the Maintain Inventory Groups privilege.  We currently don't check that the user
    // has access rights to the inventory group, since currently we all people to manage
    // inventory groups that they can't necessarily see samples in. 

    icpData.setType(TypeFinder.LOGICALREPOSITORY);

    // The real is as stored in the database is just a number, not the IG... id that users
    // see in ICP, so we have to translate.
    //
    String realId = FormLogic.makeRealLogicalRepositoryId(itemId);

    // check to see if the repository exists
    if (!IltdsUtils.isExistingLogicalRepository(realId)) return null;

    if (!IcpUtils.isAuthorizedToViewInventoryGroupIcpPages(securityInfo)) {
      return unauthorizedMessage(itemId);
    }

    IcpOperationHome home = (IcpOperationHome) EjbHomes.getHome(IcpOperationHome.class);
    IcpOperation icpOp = home.create();
    LogicalRepositoryExtendedData data = icpOp.getLogicalRepositoryData(realId, securityInfo);

    if (data.isExists()) {
      icpData.setData(data);
    }

    // Get history even if the item doesn't exist now, to robustly handle the 
    // case of items that may have been deleted but still have history.
    //
    populateHistory(securityInfo, icpData);

    return null;
  }

  /**
   * @see #processSample(SecurityInfo, IcpData)
   */
  private String processPolicy(SecurityInfo securityInfo, IcpData icpData) throws Exception {

    String itemId = icpData.getId();

    // A user is allowed to view the policy only if they are either an ICP Superuser
    // or have the Maintain Policy privilege.

    icpData.setType(TypeFinder.POLICY);

    // The real id as stored in the database is just a number, not the PY... id that users
    // see in ICP, so we have to translate.
    String realId = FormLogic.makeRealPolicyId(itemId);

    // check to see if the policy exists
    if (!IltdsUtils.policyExists(realId)) return null;

    if ((!securityInfo.isHasPrivilege(SecurityInfo.PRIV_ICP_SUPERUSER))
      && (!securityInfo.isHasPrivilege(SecurityInfo.PRIV_ORM_MAINTAIN_POLICIES))) {
      return unauthorizedMessage(itemId);
    }

    PolicyExtendedData data =
      (PolicyExtendedData) PolicyUtils.getPolicyData(realId, false, PolicyExtendedData.class);

    if (data != null) {
      data.processSecurityInfo(securityInfo);
      icpData.setData(data);
    }

    // Get history even if the item doesn't exist now, to robustly handle the 
    // case of items that may have been deleted but still have history.
    //
    populateHistory(securityInfo, icpData);

    return null;
  }

  /**
   * @see #processSample(SecurityInfo, IcpData)
   */
  private String processBoxLayout(SecurityInfo securityInfo, IcpData icpData) throws Exception {

    String itemId = icpData.getId();

    // check to see if the box layout exists
    if (!IltdsUtils.boxLayoutExists(itemId)) return null;

    icpData.setType(TypeFinder.BOXLAYOUT);

    if (!IcpUtils.isAuthorizedToViewBoxLayout(securityInfo, itemId)) {
      return unauthorizedMessage(itemId);
    }

    BoxLayoutExtendedDto data =
      (BoxLayoutExtendedDto) BoxLayoutUtils.getBoxLayoutDto(
        itemId,
        false,
        BoxLayoutExtendedDto.class);

    if (data != null) {
      data.updateAssociatedAccountBoxLayouts(securityInfo);
      icpData.setData(data);
    }

    // Get history even if the item doesn't exist now, to robustly handle the 
    // case of items that may have been deleted but still have history.
    //
    populateHistory(securityInfo, icpData);

    return null;
  }

  /**
   * @see #processSample(SecurityInfo, IcpData)
   */
  private String processFormDefinition(SecurityInfo securityInfo, IcpData icpData) throws Exception {
    String itemId = icpData.getId();
    // check to see if the Form definition exists
    //we access the Form Definition through Bigr layer instead of direct KC
    //Also sice we go through Btx, we don't do any security checks ourselves
    BtxDetailsKcFormDefinitionLookup btxDetails = new BtxDetailsKcFormDefinitionLookup();
    BigrFormDefinition form = new BigrFormDefinition();
    form.setFormDefinitionId(itemId);
    btxDetails.setFormDefinition(form);
    btxDetails.setLoggedInUserSecurityInfo(securityInfo);
    btxDetails.setBeginTimestamp(new java.sql.Timestamp(System.currentTimeMillis()));
    btxDetails = (BtxDetailsKcFormDefinitionLookup)Btx.perform(btxDetails, "kc_form_def_lookup");
    if (!btxDetails.isTransactionCompleted())
      return null;      

    icpData.setType(TypeFinder.FORMDEFINITION);
    if (btxDetails.getFormDefinition() != null) //was a real Form Def retrieved
      icpData.setData(btxDetails.getFormDefinition());
   
    // Get history even if the item doesn't exist now, to robustly handle the 
    // case of items that may have been deleted but still have history.
    //
    populateHistory(securityInfo, icpData);

    return null;
  }

  /**
   * @see #processRole(SecurityInfo, IcpData)
   */
  private String processRole(SecurityInfo securityInfo, IcpData icpData) throws Exception {
    String itemId = icpData.getId();
    icpData.setType(TypeFinder.ROLE);

    // check to see if the role exists
    RoleDto role = RoleUtils.getRoleData(itemId, false, RoleDto.class);
    boolean roleExists = !(role == null);

    if (roleExists) {
      // A user is allowed to view the role if they are an ICP Superuser or are in the same
      // account to which the role belongs.
      if (!securityInfo.isHasPrivilege(SecurityInfo.PRIV_ICP_SUPERUSER) &&
          !securityInfo.getAccount().equalsIgnoreCase(role.getAccountId())) {
        return unauthorizedMessage(itemId);
      }
      icpData.setData(role);
    }

    // Get history even if the item doesn't exist now, to robustly handle the 
    // case of items that may have been deleted but still have history.
    populateHistory(securityInfo, icpData);

    return null;
  }

  /**
   * This method is for when a valid ID type of an ICP-support type entered, but we
   * don't have a specific detail-gatherer here for that type.  In this case, just
   * show transaction history.  Currently we don't have a good way to know the id type
   * in the form that's needed here so we set the type to the "unspecified" type for now.
   * 
   * <p>When the item type doesn't have a type-specific handler, we use a conservative
   * visibility rule: only ICP Superusers can view the item.  If that rule isn't right
   * for your type, you'll need to define a type-specific handler method.
   * 
   * @see #processSample(SecurityInfo, IcpData)
   */
  private String processGenericType(SecurityInfo securityInfo, IcpData icpData) throws Exception {
    String itemId = icpData.getId();

    if (!securityInfo.isHasPrivilege(SecurityInfo.PRIV_ICP_SUPERUSER)) {
      return unauthorizedMessage(itemId);
    }

    icpData.setType(TypeFinder.NAID);

    icpData.setShowHistoryOnly(true);

    populateHistory(securityInfo, icpData);

    return null;
  }

  /**
   * Return the message that should be displayed to the user when they are not authorized
   * to view the item with the specified id.
   * 
   * @param itemId The item id.
   * @return The message.
   */
  private String unauthorizedMessage(String itemId) {
    return "You are not authorized to view " + itemId + ".";
  }

  private void retry(String error) throws Exception {
    request.setAttribute("myError", error);
    servletCtx.getRequestDispatcher("/hiddenJsps/iltds/icp/icpQuery.jsp").forward(
      request,
      response);
  }
  
  public String getResultsFormDefinitionId() {
    return _resultsFormDefinitionId;
  }
  
  public List getResultsFormDefinitions() {
    return _resultsFormDefinitions;
  }
  
  public void setResultsFormDefinitionId(String resultsFormDefinitionId) {
    _resultsFormDefinitionId = resultsFormDefinitionId;
  }
  
  public void setResultsFormDefinitions(List resultsFormDefinitions) {
    _resultsFormDefinitions = resultsFormDefinitions;
  }
}
