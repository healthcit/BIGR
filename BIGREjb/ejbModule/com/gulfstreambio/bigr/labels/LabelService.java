package com.gulfstreambio.bigr.labels;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.api.ApiLogger;
import com.ardais.bigr.api.ApiProperties;
import com.ardais.bigr.api.ApiResources;
import com.ardais.bigr.api.Escaper;
import com.ardais.bigr.api.IdGenerator;
import com.ardais.bigr.btx.framework.Btx;
import com.ardais.bigr.configuration.LabelPrinter;
import com.ardais.bigr.configuration.LabelPrintingConfiguration;
import com.ardais.bigr.configuration.LabelPrintingConfigurationUtils;
import com.ardais.bigr.configuration.LabelTemplate;
import com.ardais.bigr.configuration.LabelTemplateDefinition;
import com.ardais.bigr.iltds.helpers.FormLogic;
import com.ardais.bigr.iltds.helpers.IltdsUtils;
import com.ardais.bigr.javabeans.AccountDto;
import com.ardais.bigr.javabeans.ConsentData;
import com.ardais.bigr.javabeans.ProductDto;
import com.ardais.bigr.javabeans.SampleData;
import com.ardais.bigr.kc.form.def.BigrFormDefinition;
import com.ardais.bigr.library.btx.BTXDetailsGetSamples;
import com.ardais.bigr.library.web.column.SampleColumn;
import com.ardais.bigr.library.web.column.SampleRowParams;
import com.ardais.bigr.library.web.column.configuration.ColumnWriterList;
import com.ardais.bigr.library.web.helper.SampleSelectionHelper;
import com.ardais.bigr.pdc.helpers.PdcUtils;
import com.ardais.bigr.pdc.javabeans.DonorData;
import com.ardais.bigr.query.ColumnPermissions;
import com.ardais.bigr.query.ViewParams;
import com.ardais.bigr.security.SecurityInfo;
import com.ardais.bigr.userprofile.ViewProfile;
import com.ardais.bigr.util.Constants;
import com.ardais.bigr.util.DbUtils;
import com.gulfstreambio.bigr.Sample;
import com.gulfstreambio.bigr.error.BigrException;
import com.gulfstreambio.bigr.error.BigrValidationError;
import com.gulfstreambio.bigr.error.BigrValidationErrors;
import com.gulfstreambio.bigr.error.BigrValidationException;
import com.gulfstreambio.kc.form.def.results.ResultsFormDefinition;

public class LabelService {
  
  private static final String LABEL_DATA_SEPERATOR = ", ";
  
  public LabelService() {
    super();
  }
  
  public void printDonorLabels(SecurityInfo securityInfo, String[] ids, String labelCount, 
                               String templateName, String printerName)
      throws BigrValidationException {
    BigrValidationErrors errors = validatePrintDonorLabels(securityInfo, ids, labelCount, templateName, printerName);
    if (errors.getErrors().length > 0) {
      throw new BigrValidationException(errors);
    }
    else {
      performPrintDonorLabels(securityInfo, ids, labelCount, templateName, printerName);
    }
  }

  private BigrValidationErrors validatePrintDonorLabels(SecurityInfo securityInfo, String[] ids, 
                                                        String labelCount, String templateName, String printerName) {
    BigrValidationErrors errors = new BigrValidationErrors();
    String accountId = securityInfo.getAccount();
    
    //make sure the caller has specified at least one id
    if (ApiFunctions.isEmpty(ids) || ApiFunctions.isAllEmpty(ids)) {
      errors.add(new BigrValidationError("com.gulfstreambio.bigr.required", 
          new String[] {"At least one " + Constants.LABEL_PRINTING_OBJECT_TYPE_DONOR + " id or alias"}));
    }
    else {
      //validate the information passed in.  The first step in this process is to attempt to convert 
      //any donor aliases that were passed in into donor ids
      List donorIds = convertDonorAliasesToIds(ids, accountId);

      //now that we've converted any aliases passed in that could be converted, try to retrieve the
      //donor information for each id.  Return an error for any ids that could not be mapped to a
      //donor.
      Map donorMap = getDonors(securityInfo, donorIds, null);
      boolean invalidIdFound = false;
      StringBuffer invalidDonors = new StringBuffer(100);
      boolean addComma = false;
      Iterator donorIdIterator = donorMap.keySet().iterator();
      while (donorIdIterator.hasNext()) {
        String donorId = (String)donorIdIterator.next();
        if (donorMap.get(donorId) == null) {
          invalidIdFound = true;
          if (addComma) {
            invalidDonors.append(", ");
          }
          invalidDonors.append(donorId);
          addComma = true;
        }
      }
      if (invalidIdFound) {
        errors.add(new BigrValidationError("com.gulfstreambio.bigr.unknown", 
        new String[] {"donors", Escaper.htmlEscapeAndPreserveWhitespace(invalidDonors.toString())}));
      }
    }
    
    //perform common label printing data validation
    BigrValidationErrors commonErrors = validateCommonLabelPrintingData(accountId, labelCount, 
        templateName, printerName, Constants.LABEL_PRINTING_OBJECT_TYPE_DONOR);
    errors.addErrors(commonErrors);
    
    return errors;
  }
  
  private void performPrintDonorLabels(SecurityInfo securityInfo, String[] ids, String labelCount, 
                                       String templateName, String printerName)
    throws BigrValidationException {
    String accountId = securityInfo.getAccount();
    String bartenderCommandLine = LabelPrintingConfiguration.getBartenderCommandLine(accountId);
    LabelPrinter printer = LabelPrintingConfiguration.getPrinter(accountId, printerName);
    LabelTemplate template = (LabelTemplate)printer.getLabelTemplates().get(templateName.toLowerCase());
    //insert the printer name into the bartender command line
    int insertionPoint = bartenderCommandLine.indexOf(Constants.LABEL_PRINTING_INSERTION_STRING_LABEL_PRINTER);
    bartenderCommandLine = bartenderCommandLine.substring(0, insertionPoint) + printer.getName()
          + bartenderCommandLine.substring(insertionPoint + Constants.LABEL_PRINTING_INSERTION_STRING_LABEL_PRINTER.length());
    //insert the template name into the bartender command line
    insertionPoint = bartenderCommandLine.indexOf(Constants.LABEL_PRINTING_INSERTION_STRING_LABEL_TEMPLATE);
    bartenderCommandLine = bartenderCommandLine.substring(0, insertionPoint) + template.getFullyQualifiedName()
          + bartenderCommandLine.substring(insertionPoint + Constants.LABEL_PRINTING_INSERTION_STRING_LABEL_TEMPLATE.length());
    //convert any donor aliases that were passed in into donor ids.  Because validation has already
    //been performed on the ids we can assume that each id passed in is either already a donor id
    //or is an alias that can be mapped to an accessible donor.
    List donorIds = convertDonorAliasesToIds(ids, accountId);
    StringBuffer body = new StringBuffer(200);
    body.append(bartenderCommandLine);
    body.append("\r\n");
    Map donorMap = getDonors(securityInfo, donorIds, null);
    Iterator donorIdIterator = donorIds.iterator();
    while (donorIdIterator.hasNext()) {
      String donorId = (String)donorIdIterator.next();
      DonorData donorData = (DonorData)donorMap.get(donorId);
      //for now, we just always include the donor id and donor alias in the output.  When donor searches are 
      //implemented, this code should be modified to perform a query returning the data called for 
      //in the results form of the specified template.
      int loopMax = new Integer(labelCount).intValue();
      for (int loopCount=0; loopCount<loopMax; loopCount++) {
        body.append("\"");
        body.append(donorData.getArdaisId());
        body.append("\"");
        body.append(LABEL_DATA_SEPERATOR);
        body.append("\"");
        body.append(donorData.getCustomerId());
        body.append("\"");
        body.append("\r\n");
      }
    }
    //now handle the printing of the labels
    if (printer.isFileBasedPrintingEnabled()) {
      //create (and transfer, if appropriate) the file
      createLabelFile(Constants.LABEL_PRINTING_OBJECT_TYPE_DONOR, printer.getFileName(), 
          printer.getFileExtension(), body.toString(), printer.getFileTransferCommand());
    }
    if (printer.isEmailBasedPrintingEnabled()) {
      generateEmail(Constants.LABEL_PRINTING_OBJECT_TYPE_DONOR, printer.getEmailAddress(),
          printer.getEmailSubject(), body.toString());
    }
    if (printer.isTcpIpBasedPrintingEnabled()) {
      //send the label data via tcp/ip
      transferDataViaTcpIp(Constants.LABEL_PRINTING_OBJECT_TYPE_DONOR, printer.getTcpIpHost(),
          printer.getTcpIpPort(), body.toString());
    }
  }
  
  private List convertDonorAliasesToIds(String[] ids, String accountId) {
    List donorIds = new ArrayList();
    Iterator idIterator =ApiFunctions.safeToList(ids).iterator();
    while (idIterator.hasNext()) {
      String id = (String)idIterator.next();
      //if the value begins with the prefix for any of our donor ids (AI, AU, AX) then
      //assume it is already a donor id and therefore no conversion is necessary.
      if (IltdsUtils.isSystemDonorId(id)) {
        donorIds.add(id);
      }
      //if the value does not begin with the prefix for any of our donor ids (AI, AU, AX) then
      //assume it is an alias and handle it as follows:
      // - since donor aliases are required to be unique, see if the value matches an existing
      //   donor in the specified account.  If so use that donor's system id and if not return the 
      //   specified alias - the caller is responsible for realizing the alias could not be 
      //   converted.
      else {
        String donorId = PdcUtils.findDonorIdFromCustomerId(id, accountId);
        if (!ApiFunctions.isEmpty(donorId)) {
          donorIds.add(donorId);
        }
        else {
          donorIds.add(id);
        }
      }
    }
    return donorIds;
  }
  
  public void printCaseLabels(SecurityInfo securityInfo, String[] ids, String labelCount, 
                              String templateName, String printerName)
      throws BigrValidationException {
    BigrValidationErrors errors = validatePrintCaseLabels(securityInfo, ids, labelCount, templateName, printerName);
    if (errors.getErrors().length > 0) {
      throw new BigrValidationException(errors);
    }
    else {
      performPrintCaseLabels(securityInfo, ids, labelCount, templateName, printerName);
    }
  }


  private BigrValidationErrors validatePrintCaseLabels(SecurityInfo securityInfo, String[] ids, 
                                                       String labelCount, String templateName, String printerName) {
    BigrValidationErrors errors = new BigrValidationErrors();
    String accountId = securityInfo.getAccount();
    
    //make sure the caller has specified at least one id
    if (ApiFunctions.isEmpty(ids) || ApiFunctions.isAllEmpty(ids)) {
      errors.add(new BigrValidationError("com.gulfstreambio.bigr.required", 
          new String[] {"At least one " + Constants.LABEL_PRINTING_OBJECT_TYPE_CASE + " id or alias"}));
    }
    else {
      //validate the information passed in.  The first step in this process is to attempt to convert 
      //any case aliases that were passed in into case ids
      List caseIds = convertCaseAliasesToIds(ids, accountId);

      //now that we've converted any aliases passed in that could be converted, try to retrieve the
      //case information for each id.  Return an error for any ids that could not be mapped to a
      //case.
      Map caseMap = getCases(securityInfo, caseIds, null);
      boolean invalidIdFound = false;
      StringBuffer invalidCases = new StringBuffer(100);
      boolean addComma = false;
      Iterator caseIdIterator = caseMap.keySet().iterator();
      while (caseIdIterator.hasNext()) {
        String caseId = (String)caseIdIterator.next();
        if (caseMap.get(caseId) == null) {
          invalidIdFound = true;
          if (addComma) {
            invalidCases.append(", ");
          }
          invalidCases.append(caseId);
          addComma = true;
        }
      }
      if (invalidIdFound) {
        errors.add(new BigrValidationError("com.gulfstreambio.bigr.unknown", 
        new String[] {"cases", Escaper.htmlEscapeAndPreserveWhitespace(invalidCases.toString())}));
      }
    }
    
    //perform common label printing data validation
    BigrValidationErrors commonErrors = validateCommonLabelPrintingData(accountId, labelCount, 
        templateName, printerName, Constants.LABEL_PRINTING_OBJECT_TYPE_CASE);
    errors.addErrors(commonErrors);
    
    return errors;
  }
  
  private void performPrintCaseLabels(SecurityInfo securityInfo, String[] ids, String labelCount, 
                                      String templateName, String printerName)
    throws BigrValidationException {
    String accountId = securityInfo.getAccount();
    String bartenderCommandLine = LabelPrintingConfiguration.getBartenderCommandLine(accountId);
    LabelPrinter printer = LabelPrintingConfiguration.getPrinter(accountId, printerName);
    LabelTemplate template = (LabelTemplate)printer.getLabelTemplates().get(templateName.toLowerCase());
    //insert the printer name into the bartender command line
    int insertionPoint = bartenderCommandLine.indexOf(Constants.LABEL_PRINTING_INSERTION_STRING_LABEL_PRINTER);
    bartenderCommandLine = bartenderCommandLine.substring(0, insertionPoint) + printer.getName()
          + bartenderCommandLine.substring(insertionPoint + Constants.LABEL_PRINTING_INSERTION_STRING_LABEL_PRINTER.length());
    //insert the template name into the bartender command line
    insertionPoint = bartenderCommandLine.indexOf(Constants.LABEL_PRINTING_INSERTION_STRING_LABEL_TEMPLATE);
    bartenderCommandLine = bartenderCommandLine.substring(0, insertionPoint) + template.getFullyQualifiedName()
          + bartenderCommandLine.substring(insertionPoint + Constants.LABEL_PRINTING_INSERTION_STRING_LABEL_TEMPLATE.length());
    //convert any case aliases that were passed in into case ids.  Because validation has already
    //been performed on the ids we can assume that each id passed in is either already a case id
    //or is an alias that can be mapped to an accessible case.
    List caseIds = convertCaseAliasesToIds(ids, accountId);
    StringBuffer body = new StringBuffer(200);
    body.append(bartenderCommandLine);
    body.append("\r\n");
    Map caseMap = getCases(securityInfo, caseIds, null);
    Iterator caseIdIterator = caseIds.iterator();
    while (caseIdIterator.hasNext()) {
      String caseId = (String)caseIdIterator.next();
      ConsentData caseData = (ConsentData)caseMap.get(caseId);
      //for now, we just always include the case id and case alias in the output.  When case searches are 
      //implemented, this code should be modified to perform a query returning the data called for 
      //in the results form of the specified template.
      int loopMax = new Integer(labelCount).intValue();
      for (int loopCount=0; loopCount<loopMax; loopCount++) {
        body.append("\"");
        body.append(caseData.getConsentId());
        body.append("\"");
        body.append(LABEL_DATA_SEPERATOR);
        body.append("\"");
        body.append(caseData.getCustomerId());
        body.append("\"");
        body.append("\r\n");
      }
    }
    //now handle the printing of the labels
    if (printer.isFileBasedPrintingEnabled()) {
      //create (and transfer, if appropriate) the file
      createLabelFile(Constants.LABEL_PRINTING_OBJECT_TYPE_CASE, printer.getFileName(), 
          printer.getFileExtension(), body.toString(), printer.getFileTransferCommand());
    }
    if (printer.isEmailBasedPrintingEnabled()) {
      generateEmail(Constants.LABEL_PRINTING_OBJECT_TYPE_CASE, printer.getEmailAddress(),
          printer.getEmailSubject(), body.toString());
    }
    if (printer.isTcpIpBasedPrintingEnabled()) {
      //send the label data via tcp/ip
      transferDataViaTcpIp(Constants.LABEL_PRINTING_OBJECT_TYPE_CASE, printer.getTcpIpHost(),
          printer.getTcpIpPort(), body.toString());
    }
  }
  
  private List convertCaseAliasesToIds(String[] ids, String accountId) {
    List caseIds = new ArrayList();
    Iterator idIterator =ApiFunctions.safeToList(ids).iterator();
    while (idIterator.hasNext()) {
      String id = (String)idIterator.next();
      //if the value begins with the prefix for any of our case ids (CI, CU, CX) then
      //assume it is already a case id and therefore no conversion is necessary.
      if (IltdsUtils.isSystemCaseId(id)) {
        caseIds.add(id);
      }
      //if the value does not begin with the prefix for any of our case ids (CI, CU, CX) then
      //assume it is an alias and handle it as follows:
      // - since case aliases are required to be unique, see if the value matches an existing
      //   case in the specified account.  If so use that case's system id and if not return the 
      //   specified alias - the caller is responsible for realizing the alias could not be 
      //   converted.
      else {
        ConsentData consent = IltdsUtils.findConsentFromCustomerId(id, accountId);
        if (consent != null) {
          caseIds.add(consent.getConsentId());
        }
        else {
          caseIds.add(id);
        }
      }
    }
    return caseIds;
  }
  
  public void printSampleLabels(SecurityInfo securityInfo, String[] ids, String labelCount, 
                                String templateName, String printerName)
      throws BigrValidationException {
    BigrValidationErrors errors = validatePrintSampleLabels(securityInfo, ids, labelCount, templateName, printerName);
    if (errors.getErrors().length > 0) {
      throw new BigrValidationException(errors);
    }
    else {
      performPrintSampleLabels(securityInfo, ids, labelCount, templateName, printerName);
    }
  }

  public BigrValidationErrors validatePrintSampleLabels(SecurityInfo securityInfo, String[] ids, 
                                                         String labelCount, String templateName, 
                                                         String printerName) {
    BigrValidationErrors errors = new BigrValidationErrors();
    String accountId = securityInfo.getAccount();
    
    //make sure the caller has specified at least one id
    if (ApiFunctions.isEmpty(ids) || ApiFunctions.isAllEmpty(ids)) {
      errors.add(new BigrValidationError("com.gulfstreambio.bigr.required", 
          new String[] {"At least one " + Constants.LABEL_PRINTING_OBJECT_TYPE_SAMPLE + " id or alias"}));
    }
    else {
      //make sure all specified sample ids correspond to an existing sample that is accessible
      //to the user.  Return an error for any sample ids that do not.
      //the first step in this process is to convert any alias values passed in into system ids.
      //We use a different approach than that used for donors and cases as samples involve
      //more checking (since sample alias values do not have to be unique)
      List nonUniqueAliases = new ArrayList();
      List multipleAliases = new ArrayList();
      List searchableSampleIds = new ArrayList();
      List specifiedSampleIds = ApiFunctions.safeToList(ids);
      Iterator specifiedSampleIdIterator = specifiedSampleIds.iterator();
      while (specifiedSampleIdIterator.hasNext()) {
        String sampleId = (String)specifiedSampleIdIterator.next();
        //if the value begins with the prefix for any of our sample ids (FR, PA, SX) then
        //assume it is a sample id.
        if (IltdsUtils.isSystemSampleId(sampleId)) {
          searchableSampleIds.add(sampleId);
        }
        //if the value does not begin with the prefix for any of our sample ids (FR, PA, SX) then
        //assume it is a sample alias and handle the alias as follows:
        // - if the alias corresponds to multiple existing sample ids, add it to the non-unique
        //   alias list.
        // - if the alias corresponds to a single existing sample id, see if the account to which that 
        //      sample belongs requires unique aliases.
        //      - if not, add it to the non-unique alias list.
        //      - if so, add that sample id to the sample id list
        // - if the alias corresponds to no existing sample id add the alias to the sample id list
        //   and let the downstream code determine that the sample is unknown.
        else {
          //pass false to the findSampleIdsFromCustomerId call, to take into account any samples that
          //have been created via a box scan but have not yet been annotated.
          List existingSamples = IltdsUtils.findSamplesFromCustomerId(sampleId, false);
          //if multiple samples with the specified alias were found, add the alias to the non-unique
          //list.
          if (existingSamples.size() > 1) {
            multipleAliases.add(sampleId);
          }
          //if a single sample was found, do some further checking
          else if (existingSamples.size() == 1) {
            String sampleAccountId = ((SampleData)existingSamples.get(0)).getArdaisAcctKey();
            AccountDto accountDto = IltdsUtils.getAccountById(sampleAccountId, false, false);
            boolean aliasMustBeUnique = FormLogic.DB_YES.equalsIgnoreCase(accountDto.getRequireUniqueSampleAliases());
            //if the account to which the sample belongs does not require unique sample aliases,
            //add the alias to the non-unique list since we cannot be sure the sample we found was 
            //the one intended.
            if (!aliasMustBeUnique) {
              nonUniqueAliases.add(sampleId);
            }
            //otherwise use the corresponding sample id
            else {
              SampleData sample = (SampleData)existingSamples.get(0);
              searchableSampleIds.add(sample.getSampleId());
            }
          }
          //if no matching sample was found, just add the alias to the sample id list and let the
          //downstream code realize it is an invalid id
          else {
            searchableSampleIds.add(sampleId);
          }          
        }
      }
      
      //for all sample ids we found, try to retrieve their information
      Map sampleMap = getSamples(securityInfo, searchableSampleIds, null);
      boolean invalidIdFound = false;
      StringBuffer invalidSamples = new StringBuffer(100);
      boolean addComma = false;
      Iterator sampleIdIterator = sampleMap.keySet().iterator();
      while (sampleIdIterator.hasNext()) {
        String sampleId = (String)sampleIdIterator.next();
        if (sampleMap.get(sampleId) == null) {
          invalidIdFound = true;
          if (addComma) {
            invalidSamples.append(", ");
          }
          invalidSamples.append(sampleId);
          addComma = true;
        }
      }
      
      //if any alias values that map to multiple samples were specified, return an error
      if (!ApiFunctions.isEmpty(multipleAliases)) {
        if (multipleAliases.size() == 1) {
          String alias = Escaper.htmlEscapeAndPreserveWhitespace((String)multipleAliases.get(0));
          errors.add(new BigrValidationError("com.gulfstreambio.bigr.labels.alias.multiplesExist", 
              new String[] {alias}));
          
        }
        else {
          String aliases =
            Escaper.htmlEscapeAndPreserveWhitespace(ApiFunctions.toCommaSeparatedList(ApiFunctions.toStringArray(multipleAliases)));
          errors.add(new BigrValidationError("com.gulfstreambio.bigr.labels.aliases.multiplesExist", 
              new String[] {aliases}));
        }
      }
      //if any non-unique alias values were specified, return an error
      if (!ApiFunctions.isEmpty(nonUniqueAliases)) {
        if (nonUniqueAliases.size() == 1) {
          String alias = Escaper.htmlEscapeAndPreserveWhitespace((String)nonUniqueAliases.get(0));
          errors.add(new BigrValidationError("com.gulfstreambio.bigr.labels.alias.notUnique", 
              new String[] {alias}));
          
        }
        else {
          String aliases =
            Escaper.htmlEscapeAndPreserveWhitespace(ApiFunctions.toCommaSeparatedList(ApiFunctions.toStringArray(nonUniqueAliases)));
          errors.add(new BigrValidationError("com.gulfstreambio.bigr.labels.aliases.notUnique", 
              new String[] {aliases}));
        }
      }
      //if any invalid ids/aliases were found, return an error
      if (invalidIdFound) {
        errors.add(new BigrValidationError("com.gulfstreambio.bigr.unknown", 
        new String[] {"samples", Escaper.htmlEscapeAndPreserveWhitespace(invalidSamples.toString())}));
      }
    }
    
    //perform common label printing data validation
    BigrValidationErrors commonErrors = validateCommonLabelPrintingData(accountId, labelCount, 
        templateName, printerName, Constants.LABEL_PRINTING_OBJECT_TYPE_SAMPLE);
    errors.addErrors(commonErrors);
    
    return errors;
  }
  
  private void performPrintSampleLabels(SecurityInfo securityInfo, String[] ids, String labelCount, 
                                        String templateName, String printerName) 
    throws BigrValidationException {
    String accountId = securityInfo.getAccount();
    String bartenderCommandLine = LabelPrintingConfiguration.getBartenderCommandLine(accountId);
    LabelPrinter printer = LabelPrintingConfiguration.getPrinter(accountId, printerName);
    LabelTemplate template = (LabelTemplate)printer.getLabelTemplates().get(templateName.toLowerCase());
    //insert the printer name into the bartender command line
    int insertionPoint = bartenderCommandLine.indexOf(Constants.LABEL_PRINTING_INSERTION_STRING_LABEL_PRINTER);
    bartenderCommandLine = bartenderCommandLine.substring(0, insertionPoint) + printer.getName()
          + bartenderCommandLine.substring(insertionPoint + Constants.LABEL_PRINTING_INSERTION_STRING_LABEL_PRINTER.length());
    //insert the template name into the bartender command line
    insertionPoint = bartenderCommandLine.indexOf(Constants.LABEL_PRINTING_INSERTION_STRING_LABEL_TEMPLATE);
    bartenderCommandLine = bartenderCommandLine.substring(0, insertionPoint) + template.getFullyQualifiedName()
          + bartenderCommandLine.substring(insertionPoint + Constants.LABEL_PRINTING_INSERTION_STRING_LABEL_TEMPLATE.length());
    StringBuffer body = new StringBuffer(200);
    body.append(bartenderCommandLine);
    body.append("\r\n");
    //determine the results form definition to use to retrieve the sample information
    LabelTemplateDefinition templateDefinition = LabelPrintingConfiguration.getLabelTemplateDefinition(accountId,template.getLabelTemplateDefinitionName());
    String resultsFormDefinitionName = templateDefinition.getResultsFormDefinitionName();
    ResultsFormDefinition rfDef = LabelPrintingConfiguration.getResultsFormDefinition(accountId, resultsFormDefinitionName);
    BigrFormDefinition formDef = new BigrFormDefinition(rfDef);
    //before attempting to retrieve sample information,convert any aliases into ids
    List sampleIds = ApiFunctions.safeToList(ids);
    Iterator sampleIdIterator = sampleIds.iterator();
    List searchableSampleIds = new ArrayList();
    while (sampleIdIterator.hasNext()) {
      String sampleId = (String)sampleIdIterator.next();
      //if the value begins with the prefix for any of our sample ids (FR, PA, SX) then
      //assume it is a sample id.
      if (IltdsUtils.isSystemSampleId(sampleId)) {
        searchableSampleIds.add(sampleId);
      }
      //if the value does not begin with the prefix for any of our sample ids (FR, PA, SX) then
      //assume it is a sample alias.  Since we've already done validation we can just assume that
      //it maps to a single sample id
      else {
        //pass false to the findSampleIdsFromCustomerId call, to take into account any samples that
        //have been created via a box scan but have not yet been annotated.
        List existingSamples = IltdsUtils.findSamplesFromCustomerId(sampleId, false);
        SampleData sample = (SampleData)existingSamples.get(0);
        searchableSampleIds.add(sample.getSampleId());
      }
    }
    //now that we've got all ids, retrieve the sample information
    Map sampleMap = getSamples(securityInfo, searchableSampleIds, formDef);
    //determine the columns to write and the order in which to write them
    ViewProfile viewProfile = new ViewProfile(securityInfo);
    viewProfile.setResultsFormDefinition(formDef);
    List columnDescriptors = viewProfile.toColumnDescriptors(true);
    ViewParams viewParams = new ViewParams(securityInfo, ColumnPermissions.ALL, ColumnPermissions.ALL, ColumnPermissions.ALL);
    List columns = (new ColumnWriterList(columnDescriptors, viewParams)).getColumns();
    //now walk each sample and output the data
    Iterator idIterator = searchableSampleIds.iterator();
    while (idIterator.hasNext()) {
      String sampleId = (String)idIterator.next();
      ProductDto productDto = (ProductDto)sampleMap.get(sampleId);
      SampleSelectionHelper helper = new SampleSelectionHelper(productDto);
      int loopMax = new Integer(labelCount).intValue();
      for (int loopCount=0; loopCount<loopMax; loopCount++) {
        boolean includeSeperator = false;
        Iterator columnIterator = columns.iterator();
        while (columnIterator.hasNext()) {
          if (includeSeperator) {
            body.append(LABEL_DATA_SEPERATOR);
          }
          SampleColumn column = (SampleColumn)columnIterator.next();
          try {
            body.append("\"");
            String text = column.getRawBody(new SampleRowParams(1, helper, null));
            //since we're using double quotes to seperate data values, convert any 
            //double quotes in the data value to a single quote 
            text = ApiFunctions.replace(text, "\"", "'");
            body.append(text);
            body.append("\"");
          }
          catch (IOException e) {
            throw new BigrException(e);
          }
          includeSeperator = true;
        }
        body.append("\r\n");
      }
    }

    //now handle the printing of the labels
    if (printer.isFileBasedPrintingEnabled()) {
      //create (and transfer, if appropriate) the file
      createLabelFile(Constants.LABEL_PRINTING_OBJECT_TYPE_SAMPLE, printer.getFileName(), 
          printer.getFileExtension(), body.toString(), printer.getFileTransferCommand());
    }
    if (printer.isEmailBasedPrintingEnabled()) {
      generateEmail(Constants.LABEL_PRINTING_OBJECT_TYPE_SAMPLE, printer.getEmailAddress(),
          printer.getEmailSubject(), body.toString());
    }
    if (printer.isTcpIpBasedPrintingEnabled()) {
      //send the label data via tcp/ip
      transferDataViaTcpIp(Constants.LABEL_PRINTING_OBJECT_TYPE_SAMPLE, printer.getTcpIpHost(),
          printer.getTcpIpPort(), body.toString());
    }
  }
  
  public void printSampleLabels(SecurityInfo securityInfo, Sample[] samples, String labelCount, 
                                String templateName, String printerName)
      throws BigrValidationException {
    BigrValidationErrors errors = validatePrintSampleLabels(securityInfo, samples, labelCount, templateName, printerName);
    if (errors.getErrors().length > 0) {
      throw new BigrValidationException(errors);
    }
    else {
      performPrintSampleLabels(securityInfo, samples, labelCount, templateName, printerName);
    }
  }

  public BigrValidationErrors validatePrintSampleLabels(SecurityInfo securityInfo, Sample[] samples, 
                                                        String labelCount, String templateName, 
                                                        String printerName) {
    BigrValidationErrors errors = new BigrValidationErrors();
    String accountId = securityInfo.getAccount();
    
    //make sure the caller has specified at least one sample
    if (samples == null || samples.length <= 0) {
      errors.add(new BigrValidationError("com.gulfstreambio.bigr.required", 
          new String[] {"At least one sample"}));
    }
    
    //perform common label printing data validation
    BigrValidationErrors commonErrors = validateCommonLabelPrintingData(accountId, labelCount, 
        templateName, printerName, Constants.LABEL_PRINTING_OBJECT_TYPE_SAMPLE);
    errors.addErrors(commonErrors);
    
    return errors;
  }
  
  private void performPrintSampleLabels(SecurityInfo securityInfo, Sample[] samples, 
                                        String labelCount, String templateName, String printerName) 
    throws BigrValidationException {
    String accountId = securityInfo.getAccount();
    String bartenderCommandLine = LabelPrintingConfiguration.getBartenderCommandLine(accountId);
    LabelPrinter printer = LabelPrintingConfiguration.getPrinter(accountId, printerName);
    LabelTemplate template = (LabelTemplate)printer.getLabelTemplates().get(templateName.toLowerCase());
    //insert the printer name into the bartender command line
    int insertionPoint = bartenderCommandLine.indexOf(Constants.LABEL_PRINTING_INSERTION_STRING_LABEL_PRINTER);
    bartenderCommandLine = bartenderCommandLine.substring(0, insertionPoint) + printer.getName()
          + bartenderCommandLine.substring(insertionPoint + Constants.LABEL_PRINTING_INSERTION_STRING_LABEL_PRINTER.length());
    //insert the template name into the bartender command line
    insertionPoint = bartenderCommandLine.indexOf(Constants.LABEL_PRINTING_INSERTION_STRING_LABEL_TEMPLATE);
    bartenderCommandLine = bartenderCommandLine.substring(0, insertionPoint) + template.getFullyQualifiedName()
          + bartenderCommandLine.substring(insertionPoint + Constants.LABEL_PRINTING_INSERTION_STRING_LABEL_TEMPLATE.length());
    StringBuffer body = new StringBuffer(200);
    body.append(bartenderCommandLine);
    body.append("\r\n");
    //determine the results form definition to use to retrieve the sample information
    LabelTemplateDefinition templateDefinition = LabelPrintingConfiguration.getLabelTemplateDefinition(accountId,template.getLabelTemplateDefinitionName());
    String resultsFormDefinitionName = templateDefinition.getResultsFormDefinitionName();
    ResultsFormDefinition rfDef = LabelPrintingConfiguration.getResultsFormDefinition(accountId, resultsFormDefinitionName);
    BigrFormDefinition formDef = new BigrFormDefinition(rfDef);
    //determine the columns to write and the order in which to write them
    ViewProfile viewProfile = new ViewProfile(securityInfo);
    viewProfile.setResultsFormDefinition(formDef);
    List columnDescriptors = viewProfile.toColumnDescriptors(true);
    ViewParams viewParams = new ViewParams(securityInfo, ColumnPermissions.ALL, ColumnPermissions.ALL, ColumnPermissions.ALL);
    List columns = (new ColumnWriterList(columnDescriptors, viewParams)).getColumns();
    //now walk each sample and output the data
    for (int index=0; index<samples.length; index++) {
      ProductDto productDto = createProductDto(samples[index]);
      SampleSelectionHelper helper = new SampleSelectionHelper(productDto);
      int loopMax = new Integer(labelCount).intValue();
      for (int loopCount=0; loopCount<loopMax; loopCount++) {
        boolean includeSeperator = false;
        Iterator columnIterator = columns.iterator();
        while (columnIterator.hasNext()) {
          if (includeSeperator) {
            body.append(LABEL_DATA_SEPERATOR);
          }
          SampleColumn column = (SampleColumn)columnIterator.next();
          try {
            body.append("\"");
            String text = column.getRawBody(new SampleRowParams(1, helper, null));
            //since we're using double quotes to seperate data values, convert any 
            //double quotes in the data value to a single quote 
            text = ApiFunctions.replace(text, "\"", "'");
            body.append(text);
            body.append("\"");
          }
          catch (IOException e) {
            throw new BigrException(e);
          }
          includeSeperator = true;
        }
        body.append("\r\n");
      }
    }

    //now handle the printing of the labels
    if (printer.isFileBasedPrintingEnabled()) {
      //create (and transfer, if appropriate) the file
      createLabelFile(Constants.LABEL_PRINTING_OBJECT_TYPE_SAMPLE, printer.getFileName(), 
          printer.getFileExtension(), body.toString(), printer.getFileTransferCommand());
    }
    if (printer.isEmailBasedPrintingEnabled()) {
      generateEmail(Constants.LABEL_PRINTING_OBJECT_TYPE_SAMPLE, printer.getEmailAddress(),
          printer.getEmailSubject(), body.toString());
    }
    if (printer.isTcpIpBasedPrintingEnabled()) {
      //send the label data via tcp/ip
      transferDataViaTcpIp(Constants.LABEL_PRINTING_OBJECT_TYPE_SAMPLE, printer.getTcpIpHost(),
          printer.getTcpIpPort(), body.toString());
    }    
  }
  
  private Map getDonors(SecurityInfo securityInfo, List donorIds, BigrFormDefinition rfDef) {
    //given a list of donor ids, this method returns a map with keys consisting of the specified
    //ids and values consisting of the corresponding donors.  If an id does not correspond to a
    //donor the value will be null.
    Map returnValue = new HashMap();
    if (!ApiFunctions.isEmpty(donorIds)) {
      //initialize the map with the keys
      Iterator donorIdIterator = donorIds.iterator();
      while (donorIdIterator.hasNext()) {
        returnValue.put(donorIdIterator.next(), null);
      }
      Connection con = null;
      PreparedStatement pstmt = null;
      ResultSet rs = null;
      try {
        con = ApiFunctions.getDbConnection();
        StringBuffer query = new StringBuffer(100);
        //for now, we ignore any results form definition passed in.  When donor searches are 
        //implemented, this code should be modified to perform a query returning the data called for 
        //in the results form.
        query.append("SELECT * FROM PDC_ARDAIS_DONOR WHERE ARDAIS_ID IN ");
        query.append(ApiFunctions.makeBindParameterList(donorIds.size()));
        pstmt = con.prepareStatement(query.toString());
        ApiFunctions.bindBindParameterList(pstmt, 1, donorIds);
        rs = ApiFunctions.queryDb(pstmt, con);
        while (rs.next()) {
          DonorData donor = new DonorData(rs);
          String donorId = donor.getArdaisId();
          //per Gail, allow printing if the donor exists and don't worry about accessibility
          //if (PdcUtils.isDonorAccessibleByDdcUser(securityInfo, donorId)) {
            returnValue.put(donor.getArdaisId(), donor);
          //}
        }
      }
      catch (Exception e) {
        ApiFunctions.throwAsRuntimeException(e);
      }
      finally {
        ApiFunctions.close(con, pstmt, rs);
      }
    }
    return returnValue;
  }
  
  private Map getCases(SecurityInfo securityInfo, List caseIds, BigrFormDefinition rfDef) {
    //given a list of case ids, this method returns a map with keys consisting of the specified
    //ids and values consisting of the corresponding cases.  If an id does not correspond to a
    //case the value will be null.
    Map returnValue = new HashMap();
    if (!ApiFunctions.isEmpty(caseIds)) {
      //initialize the map with the keys
      Iterator caseIdIterator = caseIds.iterator();
      while (caseIdIterator.hasNext()) {
        returnValue.put(caseIdIterator.next(), null);
      }
      Connection con = null;
      PreparedStatement pstmt = null;
      ResultSet rs = null;
      try {
        con = ApiFunctions.getDbConnection();
        StringBuffer query = new StringBuffer(100);
        //for now, we ignore any results form definition passed in.  When case searches are 
        //implemented, this code should be modified to perform a query returning the data called for 
        //in the results form.
        query.append("SELECT * FROM ILTDS_INFORMED_CONSENT WHERE CONSENT_ID IN ");
        query.append(ApiFunctions.makeBindParameterList(caseIds.size()));
        pstmt = con.prepareStatement(query.toString());
        ApiFunctions.bindBindParameterList(pstmt, 1, caseIds);
        rs = ApiFunctions.queryDb(pstmt, con);
        Map columnNames = DbUtils.getColumnNames(rs);
        while (rs.next()) {
          ConsentData caseData = new ConsentData(columnNames,rs);
          String caseId = caseData.getConsentId();
          //per Gail, allow printing if the case exists and don't worry about accessibility
          //if (securityInfo.isInRoleSystemOwner() || IltdsUtils.caseImportedByAccount(caseId, securityInfo.getAccount())) {
            returnValue.put(caseId, caseData);
          //}
        }
      }
      catch (Exception e) {
        ApiFunctions.throwAsRuntimeException(e);
      }
      finally {
        ApiFunctions.close(con, pstmt, rs);
      }
    }
    return returnValue;
  }
  
  private Map getSamples(SecurityInfo securityInfo, List sampleIds, BigrFormDefinition rfDef) {
    //given a list of sample ids, this method returns a map with keys consisting of the specified
    //ids and values consisting of the corresponding samples.  If an id does not correspond to a
    //sample the value will be null.
    Map returnValue = new HashMap();
    if (!ApiFunctions.isEmpty(sampleIds)) {
      //initialize the map with the keys
      Iterator sampleIdIterator = sampleIds.iterator();
      while (sampleIdIterator.hasNext()) {
        returnValue.put(sampleIdIterator.next(), null);
      }
      //per Gail, allow printing if the sample exists and don't worry about accessibility.  So
      //we first eliminate any samples that do not exist
      List existingSampleIds = new ArrayList();
      Connection con = null;
      PreparedStatement pstmt = null;
      ResultSet rs = null;
      try {
        con = ApiFunctions.getDbConnection();
        StringBuffer query = new StringBuffer(100);
        query.append("SELECT * FROM ILTDS_SAMPLE WHERE SAMPLE_BARCODE_ID IN ");
        query.append(ApiFunctions.makeBindParameterList(sampleIds.size()));
        pstmt = con.prepareStatement(query.toString());
        ApiFunctions.bindBindParameterList(pstmt, 1, sampleIds);
        rs = ApiFunctions.queryDb(pstmt, con);
        while (rs.next()) {
          existingSampleIds.add(rs.getString("SAMPLE_BARCODE_ID"));
        }
      }
      catch (Exception e) {
        ApiFunctions.throwAsRuntimeException(e);
      }
      finally {
        ApiFunctions.close(con, pstmt, rs);
      }
      
      //now for the existing samples call out to sample selection to get the sample details.
      if (!ApiFunctions.isEmpty(existingSampleIds)) {
        BTXDetailsGetSamples btxDetailsGetSamples = new BTXDetailsGetSamples();
        btxDetailsGetSamples.setBeginTimestamp(new java.sql.Timestamp(System.currentTimeMillis()));
        btxDetailsGetSamples.setLoggedInUserSecurityInfo(securityInfo);
        if (rfDef != null) {
          btxDetailsGetSamples.setResultsFormDefinition(rfDef);
        }
        btxDetailsGetSamples.setSampleIds(ApiFunctions.toStringArray(existingSampleIds));
        btxDetailsGetSamples.setTransactionType("library_get_details");
        btxDetailsGetSamples = (BTXDetailsGetSamples)Btx.perform(btxDetailsGetSamples);
        //now walk each sample and populate the map values
        Iterator productDtoIterator = ApiFunctions.safeList(btxDetailsGetSamples.getSampleDetailsResult()).iterator();
        while (productDtoIterator.hasNext()) {
          ProductDto productDto = (ProductDto)productDtoIterator.next();
          returnValue.put(productDto.getId(), productDto);
        }
      }
    }
    return returnValue;
  }
  
  private synchronized void createLabelFile(String objectType, String name, String extension, 
                                     String contents, String transferCommand) throws BigrValidationException {
    String directory = ApiResources.getResourcePath(Constants.LABEL_PRINTING_FILE_DIRECTORY);
    ApiFunctions.ensureDirectoryPathExists(directory);
    StringBuffer fileNameBuffer = new StringBuffer(128);
    fileNameBuffer.append(directory);
    fileNameBuffer.append(File.separator);
    fileNameBuffer.append(name);
    fileNameBuffer.append(IdGenerator.genUniqueId(new Long(System.currentTimeMillis()).toString()));
    fileNameBuffer.append(".");
    fileNameBuffer.append(extension);
    String fileName = fileNameBuffer.toString();
    File newFile = new File(fileName);
    FileWriter fileWriter = null;
    try {
      boolean fileCreated = newFile.createNewFile();
      fileWriter = new FileWriter(newFile);
      fileWriter.write(contents);
    }
    catch (IOException ioe) {
      ApiLogger.log("An exception occurred when creating the label file: ", ioe);
      BigrValidationErrors errors = new BigrValidationErrors();
      errors.add(new BigrValidationError("com.gulfstreambio.bigr.labels.unableToCreateFile", 
          new String[] {ApiFunctions.capitalize(objectType)}));
      throw new BigrValidationException(errors);
    }
    finally {
      ApiFunctions.close(fileWriter);
    }
    //if the file is to be transferred vai a transfer command, do that now
    if (!ApiFunctions.isEmpty(transferCommand)) {
      //substitute the file name at the correct insertion point
      int insertionPoint = transferCommand.indexOf(Constants.LABEL_PRINTING_TRANSFER_COMMAND_FILE_INSERTION_STRING);
      transferCommand = transferCommand.substring(0, insertionPoint) + fileName
          + transferCommand.substring(insertionPoint + Constants.LABEL_PRINTING_TRANSFER_COMMAND_FILE_INSERTION_STRING.length());
      try {
        Runtime.getRuntime().exec(transferCommand);
      }
      catch (IOException ioe) {
        ApiLogger.log("An exception occurred when executing the label file transfer command: ", ioe);
        BigrValidationErrors errors = new BigrValidationErrors();
        errors.add(new BigrValidationError("com.gulfstreambio.bigr.labels.unableToTransferFile", 
            new String[] {ApiFunctions.capitalize(objectType)}));
        throw new BigrValidationException(errors);
      }
    }
  }
  
  private synchronized void transferDataViaTcpIp(String objectType, String tcpIpHost, String tcpIpPort, String body)
    throws BigrValidationException {
    if (!ApiFunctions.isEmpty(tcpIpHost) && !ApiFunctions.isEmpty(tcpIpPort)) {
      Socket socket = null;
      OutputStream os = null;
      try {
        socket = new Socket(tcpIpHost, new Integer(tcpIpPort).intValue());
        os = socket.getOutputStream();
        byte[] bytes = body.toString().getBytes();
        os.write(bytes);
        os.flush();
      }
      catch (UnknownHostException uhe) {
        ApiLogger.log("An exception occurred when transferring the label data via tcp/ip: ", uhe);
        BigrValidationErrors errors = new BigrValidationErrors();
        errors.add(new BigrValidationError("com.gulfstreambio.bigr.labels.unknownTcpIpHost", 
            new String[] {ApiFunctions.capitalize(objectType)}));
        throw new BigrValidationException(errors);
      }
      catch (IOException ioe) {
        ApiLogger.log("An exception occurred when transferring the label data via tcp/ip: ", ioe);
        BigrValidationErrors errors = new BigrValidationErrors();
        errors.add(new BigrValidationError("com.gulfstreambio.bigr.labels.tcpIpTransferError", 
            new String[] {ApiFunctions.capitalize(objectType)}));
        throw new BigrValidationException(errors);
      }
      finally {
        ApiFunctions.close(os);
        ApiFunctions.close(socket);
      }
    }
  }
  
  private synchronized void generateEmail(String objectType, String toAddress, String subject, String body) throws BigrValidationException {
    boolean success = ApiFunctions.generateEmail(ApiProperties.getProperty(ApiResources.API_MAIL_FROM_DEFAULT), 
        toAddress, subject, body, false);
    if (!success) {
      BigrValidationErrors errors = new BigrValidationErrors();
      errors.add(new BigrValidationError("com.gulfstreambio.bigr.labels.emailUnavailable", 
          new String[] {ApiFunctions.capitalize(objectType)}));
      throw new BigrValidationException(errors);
    }
  }
  
  private ProductDto createProductDto(Sample sample) {
    ProductDto productDto = new ProductDto();
    SampleData sampleData = new SampleData();
    sampleData.setSampleId(sample.getSampleId());
    sampleData.setSampleTypeCui(sample.getSampleTypeCui());
    productDto.setSampleData(sampleData);
    return productDto;
  }
  
  public BigrValidationErrors validateCommonLabelPrintingData(String accountId, 
                                               String labelCount, String templateName, 
                                               String printerName, String objectType) {
    
    BigrValidationErrors errors = new BigrValidationErrors();
    //make sure the label count is valid
    if (!ApiFunctions.isPositiveInteger(labelCount)|| new Integer(labelCount).intValue() == 0) {
      errors.add(new BigrValidationError("com.gulfstreambio.bigr.invalid", 
          new String[] {"the number of times to print the label(s)", "a positive integer"}));
    }
    //make sure a template has been specified
    if (ApiFunctions.isEmpty(templateName)) {
      errors.add(new BigrValidationError("com.gulfstreambio.bigr.required", 
          new String[] {"A label template"}));
    }
    //make sure a printer has been specified
    if (ApiFunctions.isEmpty(printerName)){
      errors.add(new BigrValidationError("com.gulfstreambio.bigr.required", 
          new String[] {"A printer"}));
    }
    
    try {
      //if both a template and printer have been specified, perform additional checks
      if (!ApiFunctions.isEmpty(templateName) && !ApiFunctions.isEmpty(printerName)) {
        //make sure the template name is recognized
        LabelTemplateDefinition template = LabelPrintingConfiguration.getLabelTemplateDefinition(accountId, templateName);
        if (template == null) {
          errors.add(new BigrValidationError("com.gulfstreambio.bigr.labels.unknownTemplate", 
              new String[] {templateName}));
        }
        else {
          //make sure the specified template is for the correct object type.  Note that we do not 
          //enforce that the subtype (if any) matches the object type - this is because the caller 
          //should be free to use whatever template they want as long as it's for the correct
          //object type.  This is because there are some transactions (such as re-printing labels) 
          //for which all templates of a given type must be offered, since the subtype is not known 
          //when the template choices must be presented.
          if (!objectType.equalsIgnoreCase(template.getObjectType())) {
            errors.add(new BigrValidationError("com.gulfstreambio.bigr.labels.unsupportedObjectTypeForTemplate", 
                new String[] {templateName, objectType}));
          }
          //if we're dealing with sample labels, verify the results form definition is valid.  This 
          //is necessary because the results form definition may contain KC data elements, and it is 
          //possible that the parent KC form definition(s) containing that/those element(s) was/were 
          //changed after the label printing configuration file was loaded in a way that invalidates 
          //the result form definition.  For example, the KC data element may have been removed from 
          //the form.  Note that when results form definitions are implemented for other object
          //types (such as case and donor), then the check that the object type is for sample should
          //be removed
          if (Constants.LABEL_PRINTING_OBJECT_TYPE_SAMPLE.equalsIgnoreCase(objectType)) {
            String formDefName = template.getResultsFormDefinitionName();
            ResultsFormDefinition formDef = LabelPrintingConfiguration.getResultsFormDefinition(accountId, formDefName);
            if (!LabelPrintingConfigurationUtils.isResultsFormDefinitionValid(formDef)) {
              errors.add(new BigrValidationError("com.gulfstreambio.bigr.labels.invalidPrintingConfiguration", 
                  new String[0]));
            }
          }
          //make sure the specified template is supported by the specified printer
          Collection printers = LabelPrintingConfiguration.getPrintersForLabelTemplateDefinition(accountId, templateName);
          Iterator printerIterator = printers.iterator();
          boolean printerFound = false;
          while (printerIterator.hasNext() && !printerFound) {
            LabelPrinter printer = (LabelPrinter)printerIterator.next();
            printerFound = printer.getName().equalsIgnoreCase(printerName);
          }
          if (!printerFound) {
            errors.add(new BigrValidationError("com.gulfstreambio.bigr.labels.unsupportedPrinterForTemplate", 
                new String[] {templateName, printerName}));
          }
        }
      }
    }
    catch (IllegalStateException ise) {
      //log the error so the problems are documented
      ApiLogger.log("Label Printing Configuration refresh failed.", ise);
      errors.add(new BigrValidationError("com.gulfstreambio.bigr.labels.invalidPrintingConfiguration", 
          new String[0]));
    }
    return errors;
  }

}
