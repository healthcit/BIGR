package com.ardais.bigr.iltds.op;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import javax.ejb.ObjectNotFoundException;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.api.Escaper;
import com.ardais.bigr.iltds.beans.ListGenerator;
import com.ardais.bigr.iltds.beans.ListGeneratorHome;
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
import com.ardais.bigr.util.BoxLayoutUtils;
import com.ardais.bigr.util.EjbHomes;
import com.ardais.bigr.util.WebUtils;
import com.gulfstreambio.gboss.GbossFactory;

/**
 * Insert the type's description here.
 * Creation date: (2/2/2001 2:39:22 PM)
 * @author: Jake Thompson
 */
public class CheckOutBoxConfirm extends com.ardais.bigr.iltds.op.StandardOperation {

  private static final String RETRY_PATH = "/hiddenJsps/iltds/storage/checkOutBox/checkOutBoxScan.jsp";
  /**
   * CheckOutBoxConfirm constructor comment.
   * @param req javax.servlet.http.HttpServletRequest
   * @param res javax.servlet.http.HttpServletResponse
   * @param ctx javax.servlet.ServletContext
   */
  public CheckOutBoxConfirm(HttpServletRequest req, HttpServletResponse res, ServletContext ctx) {
    super(req, res, ctx);
  }

  /**
   * Insert the method's description here.
   * Creation date: (2/2/2001 2:39:22 PM)
   */
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
      }
    }
    BoxLayoutDto boxLayoutDto = BoxLayoutUtils.getBoxLayoutDto(boxLayoutId);
    request.setAttribute("boxLayoutDto", boxLayoutDto);

    // Scan through all of the box cells to find the sample ids and/or aliases.
    // Here we're just accumulating non-null entries, we do validation later.
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
    
    //iterate over the samples, gathering data we'll need for additional checking.  Note that
    //at this point, regardless of whether the sample was scanned via its system id or alias,
    //we have retrieved the sample id, sample alias, and sample type for every sample.
    List systemSampleIds = new ArrayList();
    Iterator i = sampleNumberToSample.keySet().iterator();
    while (i.hasNext()) {
      String sampleNumber = (String)i.next();
      SampleData sample = (SampleData)sampleNumberToSample.get(sampleNumber);
      if (sample != null) {
        String sampleId = sample.getSampleId();
        systemSampleIds.add(sampleId);
      }
    }
    
    //SWP-1100 - if nothing has been scanned in, return an error
    if (sampleNumberToSample.size() == 0) {
      retry("Please enter sample(s) before continuing.");
      return;
    }

    //Now perform some additional checks.  Pass true for the check to make sure that samples
    //exist, because for a check-out operation we always require that all of the samples
    //actually exist.  Note however that since we've already performed that check above this check
    //should always succeed.
    if (!ApiFunctions.isEmpty(systemSampleIds)) {
      //MR7356 Don't allow samples on open requests to be scanned into the new box
      boolean enforceRequestedSampleRestrictions = true;
      SampleOperationHome sampleOpHome = (SampleOperationHome) EjbHomes.getHome(SampleOperationHome.class);
      SampleOperation sampleOp = sampleOpHome.create();
      String validSampleSetError =
        sampleOp.validSamplesForBoxScan(
          systemSampleIds,
          true,
          enforceRequestedSampleRestrictions,
          false,
          securityInfo,
          boxLayoutDto);
      if (validSampleSetError != null) {
        retry(validSampleSetError);
        return;
      }
    }

    Vector staffNames = new Vector();
    String account = securityInfo.getAccount();
    ListGeneratorHome home = (ListGeneratorHome) EjbHomes.getHome(ListGeneratorHome.class);
    ListGenerator list = home.create();
    staffNames = list.lookupArdaisStaffByAccountId(account);
    request.setAttribute("staff", staffNames);

    servletCtx
      .getRequestDispatcher("/hiddenJsps/iltds/storage/checkOutBox/checkOutBoxConfirm.jsp")
      .forward(request, response);
  }

  /**
   * Insert the method's description here.
   * Creation date: (2/8/2001 6:51:19 PM)
   * @param myError java.lang.String
   */
  private void retry(String myError) throws Exception {
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

  private String getSampleInfo(HashMap sampleNumberToSample, SecurityInfo securityInfo) {
    Set problems = new HashSet();
    List encounteredAliases = new ArrayList();
    
    Iterator i = sampleNumberToSample.keySet().iterator();
    while (i.hasNext()) {
      String sampleNumber = (String)i.next();
      String sampleIdOrAlias = (String) sampleNumberToSample.get(sampleNumber);
      String sampleTypeCui = null;
      String sampleAlias = null;
      //if the value begins with the prefix for any of our sample ids (FR, PA, SX) then
      //assume it is a sample id.  Return an error if the sample doesn't exist.
      if (IltdsUtils.isSystemSampleId(sampleIdOrAlias)) {
        try {
          SampleAccessBean sample = new SampleAccessBean(new SampleKey(sampleIdOrAlias));
          sampleTypeCui = sample.getSampleTypeCid();
          sampleAlias = sample.getCustomerId();
        }
        catch (ObjectNotFoundException e) {
          StringBuffer error = new StringBuffer(100);
          error.append("Sample ");
          error.append(sampleIdOrAlias);
          error.append(" does not exist in the database.");
          problems.add(error.toString());
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
      // - if the alias corresponds to no existing sample return an error.
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
        //if no matching sample was found, return an error
        else {
          StringBuffer error = new StringBuffer(100);
          error.append("Sample \"");
          error.append(Escaper.htmlEscapeAndPreserveWhitespace(sampleIdOrAlias));
          error.append("\" does not exist in the database.");
          problems.add(error.toString());
        }          
      }
      //Create a sample data to hold the information
      SampleData sample = new SampleData();
      sample.setSampleId(sampleIdOrAlias);
      sample.setSampleAlias(sampleAlias);
      sample.setSampleTypeCui(sampleTypeCui);
      if (!ApiFunctions.isEmpty(sampleTypeCui)) {
        sample.setSampleType(GbossFactory.getInstance().getDescription(sampleTypeCui));
      }
      //replace the map entry with the sample data object
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
}
