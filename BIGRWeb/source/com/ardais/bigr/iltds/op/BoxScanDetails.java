package com.ardais.bigr.iltds.op;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ejb.ObjectNotFoundException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.api.Escaper;
import com.ardais.bigr.iltds.beans.SampleAccessBean;
import com.ardais.bigr.iltds.beans.SampleKey;
import com.ardais.bigr.iltds.beans.SampleOperation;
import com.ardais.bigr.iltds.beans.SampleOperationHome;
import com.ardais.bigr.iltds.helpers.FormLogic;
import com.ardais.bigr.iltds.helpers.IltdsUtils;
import com.ardais.bigr.javabeans.AccountDto;
import com.ardais.bigr.javabeans.BoxLayoutDto;
import com.ardais.bigr.javabeans.SampleData;
import com.ardais.bigr.orm.helpers.BoxScanData;
import com.ardais.bigr.security.SecurityInfo;
import com.ardais.bigr.util.ArtsConstants;
import com.ardais.bigr.util.BoxLayoutUtils;
import com.ardais.bigr.util.EjbHomes;
import com.ardais.bigr.util.WebUtils;
import com.gulfstreambio.gboss.GbossFactory;

public class BoxScanDetails extends StandardOperation {

  private static final String RETRY_PATH = "/hiddenJsps/iltds/storage/boxScanDetails.jsp";

  public BoxScanDetails(HttpServletRequest req, HttpServletResponse res, ServletContext ctx) {
    super(req, res, ctx);
  }

  public void invoke() throws Exception {
    SecurityInfo securityInfo = WebUtils.getSecurityInfo(request);

    // Check if box layout is valid for this account.
    String boxLayoutId = request.getParameter("boxLayoutId");
    boxLayoutId = BoxLayoutUtils.checkBoxLayoutId(boxLayoutId, false, true, true);
    if (!ApiFunctions.isEmpty(boxLayoutId)) {
      String accountId = securityInfo.getAccount();
      if (!BoxLayoutUtils.isValidBoxLayoutForAccount(boxLayoutId, accountId)) {
        // The box layout id is not part of the account box layout group.
        retry("The box layout selected does not belong to this account.");
        return;
      }
    }
    BoxLayoutDto boxLayoutDto = BoxLayoutUtils.getBoxLayoutDto(boxLayoutId);
    request.setAttribute("boxLayoutDto", boxLayoutDto);

    // Scan through all of the box cells to find the sample ids and/or aliases.
    // Here's we're just accumulating non-null entries, we do validation later.
    //
    HashMap sampleNumberToSample = new HashMap();
    for (int i = 0; i < boxLayoutDto.getBoxCapacity(); i++) {
      String sampleNumber = "smpl" + (i + 1);
      String sampleIdOrAlias = ApiFunctions.safeString(request.getParameter(sampleNumber)).trim();
      if (!ApiFunctions.isEmpty(sampleIdOrAlias)) {
        sampleNumberToSample.put(sampleNumber, sampleIdOrAlias);
      }
    }

    // Get relevant information about the samples, and populate the request with that information.
    // If there was a problem getting the information return an error (but populate any retrieved 
    // sample information first so it is retained for existing samples)
    String error = getSampleInfo(sampleNumberToSample, securityInfo);
    for (int i = 0; i < boxLayoutDto.getBoxCapacity(); i++) {
      String sampleNumber = "smpl" + (i + 1);
      String sampleIdOrAlias = ApiFunctions.safeString(request.getParameter(sampleNumber)).trim();
      if (!ApiFunctions.isEmpty(sampleIdOrAlias)) {
        SampleData sample = (SampleData)sampleNumberToSample.get(sampleNumber);
        request.setAttribute("smplType" + (i + 1), sample.getSampleType());
        request.setAttribute("smplAlias" + (i + 1), sample.getSampleAlias());
      }
    }    
    if (!ApiFunctions.isEmpty(error)) {
      retry(error);
      return;
    }
    
    //iterate over the samples, gathering data we'll need for additional checking
    List nonExistingSampleIds = new ArrayList();
    List systemSampleIds = new ArrayList();
    Iterator i = sampleNumberToSample.keySet().iterator();
    while (i.hasNext()) {
      String sampleNumber = (String)i.next();
      SampleData sample = (SampleData)sampleNumberToSample.get(sampleNumber);
      String sampleId = sample.getSampleId();
      if (IltdsUtils.isSystemSampleId(sampleId)) {
        systemSampleIds.add(sampleId);
        try {
          SampleAccessBean sampleBean = new SampleAccessBean(new SampleKey(sampleId));
        }
        catch (ObjectNotFoundException e) {
          nonExistingSampleIds.add(sampleId);
        }
        catch (Exception e) {
          ApiFunctions.throwAsRuntimeException(e);
        }
      }
      //any aliases that mapped to a single existing sample will have had the corresponding
      //system id filled in, so if the id is an alias that means the alias didn't map to an
      //existing sample so it automatically goes into the nonExistingSampleIds list.
      else {
        nonExistingSampleIds.add(sampleId);
      }
    }
    
    //now perform some additional checking on the scanned samples.  First check to see if all of
    //the samples must exist and return an error if that is the case and there are any samples 
    //with a sample type of unknown (i.e. they don't exist).  We have to do this check here instead 
    //of via the validSamplesForBoxScan call below since that call cannot handle new samples that 
    //have just an alias specified.  That call deals solely with system ids.
    boolean checkThatSamplesExist = (!securityInfo.isInRoleSystemOwnerOrDi());
    if (checkThatSamplesExist && !ApiFunctions.isEmpty(nonExistingSampleIds)) {
      retry("The following samples do not exist in the database: "
      + StringUtils.join(nonExistingSampleIds.iterator(), ", ") + ".");
    }
    
    //SWP-1100 - if nothing has been scanned in, return an error
    if (sampleNumberToSample.size() == 0) {
      retry("Please enter sample(s) before continuing.");
      return;
    }

    //for all samples that have a system id (existing samples that were scanned via their system id,
    //existing samples that were scanned via their alias, or new samples that were scanned via
    //their system id) perform some additional checks.  Pass false for the check to make sure that 
    //samples exist, since we've already performed that check above.
    //MR7356 Don't allow samples on open requests to be scanned into the new box
    if (!ApiFunctions.isEmpty(systemSampleIds)) {
      boolean enforceRequestedSampleRestrictions = true;
      SampleOperationHome home = (SampleOperationHome) EjbHomes.getHome(SampleOperationHome.class);
      SampleOperation sampleOp = home.create();
      String validSampleSetError =
        sampleOp.validSamplesForBoxScan(
          systemSampleIds,
          false,
          enforceRequestedSampleRestrictions,
          true,
          securityInfo,
          boxLayoutDto);
      if (validSampleSetError != null) {
        retry(Escaper.htmlEscapeAndPreserveWhitespace(validSampleSetError));
        return;
      }
    }
    
    Set uniqueSampleTypes = IltdsUtils.getSampleTypesBySampleIds(systemSampleIds);
    Set accountStorageTypes = IltdsUtils.getStorageTypesByAccount(securityInfo);
    if (!uniqueSampleTypes.isEmpty()) {
      accountStorageTypes.retainAll(IltdsUtils.getCommonStorageTypesBySampleTypes(uniqueSampleTypes));
    }

    if (accountStorageTypes.isEmpty()) {
      retry("No storage types could be found.");
      return;
    }
    else if (accountStorageTypes.size() > 1) {
      request.setAttribute("storageTypeChoices", IltdsUtils.getStorageTypesAsLVS(accountStorageTypes));
      request.setAttribute("confirmBoxStorageType", FormLogic.DB_YES);
    }
    else {
      Iterator iterator = accountStorageTypes.iterator();
      String defaultStorageTypeCid = (String)iterator.next();
      request.setAttribute("defaultStorageTypeCid", defaultStorageTypeCid);
    }
    
    //add the list of nonExisting sample ids to the request, so they can be displayed in a way
    //that makes it obvious to the user that they are samples that will be created as part of the
    //box scan
    request.setAttribute("nonExistingSampleIds", nonExistingSampleIds);

    servletCtx.getRequestDispatcher("/hiddenJsps/iltds/storage/boxScanConfirm.jsp").forward(
      request,
      response);
  }

  private String getSampleInfo(HashMap sampleNumberToSample, SecurityInfo securityInfo) {
    Set problems = new HashSet();
    List encounteredAliases = new ArrayList();
    AccountDto currentUserAccountDto = IltdsUtils.getAccountById(securityInfo.getAccount(), 
                                                                  false, false);
    boolean currentUserAccountAliasMustBeUnique = FormLogic.DB_YES.equalsIgnoreCase(
                          currentUserAccountDto.getRequireUniqueSampleAliases());
    
    Iterator i = sampleNumberToSample.keySet().iterator();
    while (i.hasNext()) {
      String sampleNumber = (String)i.next();
      String sampleIdOrAlias = (String) sampleNumberToSample.get(sampleNumber);
      String sampleTypeCui = null;
      String sampleAlias = null;
      //if the value begins with the prefix for any of our sample ids (FR, PA, SX) then
      //assume it is a sample id.
      if (IltdsUtils.isSystemSampleId(sampleIdOrAlias)) {
        try {
          SampleAccessBean sample = new SampleAccessBean(new SampleKey(sampleIdOrAlias));
          sampleTypeCui = sample.getSampleTypeCid();
          sampleAlias = sample.getCustomerId();
        }
        catch (ObjectNotFoundException e) {
          sampleTypeCui = ArtsConstants.SAMPLE_TYPE_UNKNOWN; 
        }
        catch (Exception e) {
          ApiFunctions.throwAsRuntimeException(e);
        }
      }
      //if the value does not begin with the prefix for any of our sample ids (FR, PA, SX) then
      //assume it is a sample alias and handle the alias as follows:
      // - if the alias corresponds to multiple existing sample ids, return an error.
      // - if the alias corresponds to a single existing sample id, see if the account to which that 
      //      sample belongs requires unique aliases.
      //      - if not, return an error since we cannot be positive the sample we found was the one
      //        intended.
      //      - if so
      //         - if the specified alias has already been encountered return an error since the 
      //           same sample cannot be scanned into multiple locations in a box.  
      //         - if the alias has not yet been encountered then get the information for that 
      //           sample. 
      // - if the alias corresponds to no existing sample id assume the user wishes to create a 
      //      new sample within their account.  If their account mandates unique sample alias 
      //      values return an error if the same alias is encountered multiple times (we don't have 
      //      to worry about checking to see if the alias is already in use, because the first two 
      //      scenarios handle that situation).
      else {
        //pass false to the findSampleIdsFromCustomerId call, to take into account any samples that
        //have been created via a box scan but have not yet been annotated.
        List existingSamples = IltdsUtils.findSamplesFromCustomerId(sampleIdOrAlias, false);
        //if multiple samples with the specified alias were found, return an error.
        if (existingSamples.size() > 1) {
          StringBuffer error = new StringBuffer(100);
          error.append("The alias \"");
          error.append(Escaper.htmlEscapeAndPreserveWhitespace(sampleIdOrAlias));
          error.append("\" corresponds to multiple existing samples.  Please scan the specific");
          error.append(" sample id you wish to use.");
          problems.add(error.toString());
        }
        //if a single sample was found, do some further checking
        else if (existingSamples.size() == 1) {
          String accountId = ((SampleData)existingSamples.get(0)).getArdaisAcctKey();
          AccountDto accountDto = IltdsUtils.getAccountById(accountId, false, false);
          boolean aliasMustBeUnique = FormLogic.DB_YES.equalsIgnoreCase(accountDto.getRequireUniqueSampleAliases());
          //if the account to which the sample belongs does not require unique sample aliases,
          //return an error since we cannot be sure the sample we found was the one intended.
          if (!aliasMustBeUnique) {
            StringBuffer error = new StringBuffer(100);
            error.append("The alias \"");
            error.append(Escaper.htmlEscapeAndPreserveWhitespace(sampleIdOrAlias));
            error.append("\" does not uniquely identify a sample.");
            error.append(" Please scan the specific sample id you wish to use.");
            problems.add(error.toString());            
          }
          //if we've already encountered this alias, return an error (the same sample cannot
          //be scanned into multiple locations in a box)
          else if (encounteredAliases.contains(sampleIdOrAlias.toUpperCase())) {
            StringBuffer error = new StringBuffer(100);
            error.append("The alias \"");
            error.append(Escaper.htmlEscapeAndPreserveWhitespace(sampleIdOrAlias));
            error.append("\" appears more than once.");
            problems.add(error.toString());
            //still fill in the sample information so the resulting screen looks correct (otherwise
            //the first instance of this alias has information but the second does not)
            SampleData sample = (SampleData)existingSamples.get(0);
            sampleIdOrAlias = sample.getSampleId();
            sampleTypeCui = sample.getSampleTypeCui();
            sampleAlias = sample.getSampleAlias();
          }
          //otherwise store the encountered alias (in uppercase, so we can compare subsequent 
          //aliases to this one without case accounting for any differences) and get the information 
          //for the sample
          else {
            encounteredAliases.add(sampleIdOrAlias.toUpperCase());
            SampleData sample = (SampleData)existingSamples.get(0);
            sampleIdOrAlias = sample.getSampleId();
            sampleTypeCui = sample.getSampleTypeCui();
            sampleAlias = sample.getSampleAlias();
          }
        }
        //if no matching sample was found, assume the user is going to create a new sample within
        //their account.  If that account requiers unique alias values and we've encountered this
        //alias then return an error.  Otherwise, store the encountered alias (in uppercase, 
        //so we can compare subsequent aliases to this one without case accounting for any 
        //differences) and set the sample type to be unknown
        else {
          if (encounteredAliases.contains(sampleIdOrAlias.toUpperCase()) && 
                  currentUserAccountAliasMustBeUnique) {
            StringBuffer error = new StringBuffer(100);
            error.append("You cannot create more than one sample with the alias \"");
            error.append(Escaper.htmlEscapeAndPreserveWhitespace(sampleIdOrAlias));
            error.append("\".");
            problems.add(error.toString());
            //still fill in the sample information so the resulting screen looks correct (otherwise
            //the first instance of this alias has information but the second does not)
            sampleTypeCui = ArtsConstants.SAMPLE_TYPE_UNKNOWN; 
          }
          else {
            encounteredAliases.add(sampleIdOrAlias.toUpperCase());
            sampleTypeCui = ArtsConstants.SAMPLE_TYPE_UNKNOWN; 
          }
        }          
      }
      //create a sample data to hold the information
      SampleData sample = new SampleData();
      sample.setSampleId(sampleIdOrAlias);
      sample.setSampleAlias(sampleAlias);
      sample.setSampleTypeCui(sampleTypeCui);
      if (!ApiFunctions.isEmpty(sampleTypeCui)) {
        sample.setSampleType(GbossFactory.getInstance().getDescription(sampleTypeCui));
      }
      //replace the sampleIdOrAlias string with the sample data object
      sampleNumberToSample.put(sampleNumber, sample);
    }
    
    String returnValue = null;
    if (!problems.isEmpty()) {
      StringBuffer buff = new StringBuffer("The following problems were found: ");
      Iterator problemIterator = problems.iterator();
      while (problemIterator.hasNext()) {
        buff.append((String)problemIterator.next());
        buff.append("  ");
      }
      returnValue = buff.toString();
    }
    return returnValue;
  }
  
  private void retry(String myError) throws IOException, ServletException, Exception {
    // Get the security info.
    SecurityInfo securityInfo = WebUtils.getSecurityInfo(request);

    String boxLayoutId = request.getParameter("boxLayoutId");
    boxLayoutId = BoxLayoutUtils.checkBoxLayoutId(boxLayoutId, false, true, true);
    if (!ApiFunctions.isEmpty(boxLayoutId)) {
      String accountId = securityInfo.getAccount();
      if (!BoxLayoutUtils.isValidBoxLayoutForAccount(boxLayoutId, accountId)) {
        // The box layout id is not part of the account box layout group.
        BoxScanData bsd = BoxLayoutUtils.prepareForBoxScan(securityInfo);
        boxLayoutId = bsd.getDefaultBoxLayoutId();
      }
    }
    BoxLayoutDto boxLayoutDto = BoxLayoutUtils.getBoxLayoutDto(boxLayoutId);
    request.setAttribute("boxLayoutDto", boxLayoutDto);

    retry(myError, RETRY_PATH);
  }
}
