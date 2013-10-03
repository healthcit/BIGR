package com.ardais.bigr.configuration;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.io.StringReader;
import java.net.URL;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.digester.Digester;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.es.beans.ArdaisaccountAccessBean;
import com.ardais.bigr.es.beans.ArdaisaccountKey;
import com.ardais.bigr.util.BigrXMLParserBase;
import com.ardais.bigr.util.Constants;
import com.ardais.bigr.util.digester.GsbDigester;
import com.gulfstreambio.kc.form.def.Tag;
import com.gulfstreambio.kc.form.def.results.ResultsFormDefinition;
import com.gulfstreambio.kc.form.def.results.ResultsFormDefinitionDataElement;
import com.gulfstreambio.kc.form.def.results.ResultsFormDefinitionElements;
import com.gulfstreambio.kc.form.def.results.ResultsFormDefinitionHostElement;

public class LabelPrintingConfigurationData extends BigrXMLParserBase implements Serializable {
  
  private Map _accountMap = new HashMap();
  private boolean _immutable = false;

  /**
   * Parses the XML in a file.
   * @param  configFis  the <code>FileInputStream</code> for the file containing the 
   * configuration information to parse
   */
  public void initOrRefresh(FileInputStream configFis) {
    parseConfigurationXML(new InputSource(configFis));
  }

  /**
   * Parses the XML in a string.
   * @param  configurationXml  the <code>String</code> containing the configuration information to parse
   */
  public void initOrRefresh(String configurationXml) {
    parseConfigurationXML(new InputSource(new StringReader(configurationXml)));
  }
  
  private void parseConfigurationXML(InputSource inputSource) {

    // Create a digester.
    Digester digester = getDigester();

    //add this object to the digesters stack
    digester.push(this);

    // parse the XML.
    try {
      digester.parse(inputSource);
    }
    catch (SAXException se) {
      throw new ApiException(se);
    }
    catch (IOException ie) {
      throw new ApiException(ie);
    }
  }
  
  private Digester getDigester() {
    GsbDigester digester = (new BigrXMLParserBase()).makeDigester();

    // Register the location of the label printing configuration DTD.
    URL dtdURL =
      LabelPrintingConfigurationData.class.getResource(ApiFunctions.CLASSPATH_RESOURCES_PREFIX + Constants.LABEL_PRINTING_CONFIGURATION_DTD);
    digester.register(
      "-//HealthCare IT Incorporated//DTD Label Printing Configuration XML1//EN",
      dtdURL.toString());

    // Define the digester rules...

    // Set the properties of the LabelPrintingConfigurationData object.  XML attribute names matching
    // LabelPrintingConfigurationData property names are automatically set.
    digester.addSetProperties("labelPrintingConfigurationData");

    // Create an Account object for each account element.
    digester.addObjectCreate("*/account", Account.class);
    // Set the properties of the Account object.  XML attribute names matching
    // Account property names are automatically set.
    digester.addSetProperties("*/account");
    // Add the Account object to its parent object on the top of the stack.
    digester.addSetNext("*/account", "addAccount", "com.ardais.bigr.configuration.Account");
    
    // Account elements can contain ResultsFormDefinition elements.
    digester.addObjectCreate("*/resultsFormDefinition", ResultsFormDefinition.class);
    digester.addSetProperties("*/resultsFormDefinition");
    digester.addSetNext("*/resultsFormDefinition",
      "addResultsFormDefinition",
      "com.gulfstreambio.kc.form.def.results.ResultsFormDefinition");

    // Create the ResultsFormDefinitionElements object for the formElements element, set its
    // properties, and add it to the ResultsFormDefinition instance.
    digester.addObjectCreate("*/formElements", ResultsFormDefinitionElements.class);
    digester.addSetProperties("*/formElements");
    digester.addSetNextIf("*/formElements",
        "com.gulfstreambio.kc.form.def.results.ResultsFormDefinition", "setResultsFormElements",
        "com.gulfstreambio.kc.form.def.results.ResultsFormDefinitionElements");

    // Create a ResultsFormDefinitionDataElement object for the dataElement element, set its
    // properties, and add it to the ResultsFormDefinitionElements instance
    digester.addObjectCreate("*/dataElement", ResultsFormDefinitionDataElement.class);
    digester.addSetProperties("*/dataElement");
    digester.addSetNextIf("*/dataElement",
        "com.gulfstreambio.kc.form.def.results.ResultsFormDefinitionElements", "addResultsDataElement",
        "com.gulfstreambio.kc.form.def.results.ResultsFormDefinitionDataElement");

    // Create a Tag object for the dataElement element, set its
    // properties, and add it to the ResultsFormDefinitionElements instance
    digester.addObjectCreate("*/dataElement/tag", Tag.class);
    digester.addSetProperties("*/dataElement/tag");
    digester.addSetNextIf("*/dataElement/tag",
        "com.gulfstreambio.kc.form.def.results.ResultsFormDefinitionDataElement", "addTag",
        "com.gulfstreambio.kc.form.def.Tag");

    // Create a ResultsFormDefinitionHostElement object for the hostElement element, set its
    // properties, and add it to the ResultsFormDefinitionElements instance
    digester.addObjectCreate("*/hostElement", ResultsFormDefinitionHostElement.class);
    digester.addSetProperties("*/hostElement");
    digester.addSetNextIf("*/hostElement",
        "com.gulfstreambio.kc.form.def.results.ResultsFormDefinitionElements", "addResultsHostElement",
        "com.gulfstreambio.kc.form.def.results.ResultsFormDefinitionHostElement");

    // Create a Tag object for the hostElement element, set its
    // properties, and add it to the ResultsFormDefinitionElements instance
    digester.addObjectCreate("*/hostElement/tag", Tag.class);
    digester.addSetProperties("*/hostElement/tag");
    digester.addSetNextIf("*/hostElement/tag",
        "com.gulfstreambio.kc.form.def.results.ResultsFormDefinitionHostElement", "addTag",
        "com.gulfstreambio.kc.form.def.Tag");
    
    // Account elements can contain LabelTemplateDefinition elements.
    digester.addObjectCreate("*/labelTemplateDefinition", LabelTemplateDefinition.class);
    digester.addSetProperties("*/labelTemplateDefinition");
    digester.addSetNext("*/labelTemplateDefinition",
      "addLabelTemplateDefinition",
      "com.ardais.bigr.configuration.LabelTemplateDefinition");
    
    // Account elements can contain LabelPrinter elements.
    digester.addObjectCreate("*/labelPrinter", LabelPrinter.class);
    digester.addSetProperties("*/labelPrinter");
    digester.addSetNext("*/labelPrinter",
      "addLabelPrinter",
      "com.ardais.bigr.configuration.LabelPrinter");
    
    // LabelPrinter elements can contain LabelTemplate elements.
    digester.addObjectCreate("*/labelTemplate", LabelTemplate.class);
    digester.addSetProperties("*/labelTemplate");
    digester.addSetNext("*/labelTemplate",
      "addLabelTemplate",
      "com.ardais.bigr.configuration.LabelTemplate");

    return digester;
  }
  
  public void addAccount(Account account) {
    checkImmutable();
    if (_accountMap.put(account.getId().toLowerCase(), account) != null) {
      throw new ApiException("Duplicate account with id " + account.getId() + " encountered.");
    }
  }
  
  public Account getAccount(String id) {
    id = ApiFunctions.safeTrim(id);
    if (ApiFunctions.isEmpty(id)) {
      throw new ApiException("A non-empty account id must be specified.");
    }
    else {
      return (Account)_accountMap.get(id.toLowerCase());
    }
  }
  
  /**
   * Set this instance to be immutable.  Any attempts to modify immutable
   * instances will throw an exception.
   */
  public void setImmutable() {
    _immutable = true;
    Iterator accountIterator = _accountMap.keySet().iterator();
    while (accountIterator.hasNext()) {
      ((Account)_accountMap.get(accountIterator.next())).setImmutable();
    }
    _accountMap = Collections.unmodifiableMap(_accountMap);
  }

  /**
   * Is this instance immutable.
   */
  public boolean isImmutable() {
    return _immutable;
  }

  /**
   * Throw an exception if this instance is immutable.
   */
  private void checkImmutable() {
    if (_immutable) {
      throw new IllegalStateException("Attempted to modify an immutable LabelPrintingConfigurationData: " + this);
    }
  }
  
  /**
   * Verify that the configuration data in this object is valid. Throws an IllegalStateException 
   * if invalid data is found
   */
  public void validate() {
    StringBuffer problems = new StringBuffer(100);
    Iterator accountIterator = _accountMap.keySet().iterator();
    //iterate over all of the accounts, making sure the data in each is valid
    while (accountIterator.hasNext()) {
      Account account = (Account)_accountMap.get(accountIterator.next());
      //validate the account information is valid
      validateAccount(account, problems);
    }
    if (problems.length() > 0) {
      throw new IllegalStateException("Label printing configuration is in an invalid state:" + problems.toString());
    }
  }
  
  private void validateAccount(Account account, StringBuffer problems) {
    boolean appendComma = problems.length() > 0;
    //validate the account id exists and is a known account id
    String id = ApiFunctions.safeTrim(account.getId());
    if (ApiFunctions.isEmpty(id)) {
      if (appendComma) {
        problems.append(",");
      }
      problems.append(" account with empty id encountered");
      appendComma = true;
    }
    else {
      ArdaisaccountAccessBean ardaisAccount = null;
      try {
        ardaisAccount = new ArdaisaccountAccessBean(new ArdaisaccountKey(id)); 
      }
      catch (Exception e) {
        if (appendComma) {
          problems.append(",");
        }
        problems.append(" account with unknown id (" + id + ") encountered");
        appendComma = true;
      }
    }
    
    //validate that the bartender command line exists and has the correct insertion points
    String bartenderCommandLine = account.getBartenderCommandLine();
    if (ApiFunctions.isEmpty(bartenderCommandLine)) {
      if (appendComma) {
        problems.append(",");
      }
      problems.append(" account with empty bartenderCommandLine encountered.");
      appendComma = true;
    }
    else {
      if (bartenderCommandLine.indexOf(Constants.LABEL_PRINTING_INSERTION_STRING_LABEL_PRINTER) <= 0) {
        if (appendComma) {
          problems.append(",");
        }
        problems.append(" account with bartenderCommandLine having no printer insertion string encountered.");
        appendComma = true;
      }
      if (bartenderCommandLine.indexOf(Constants.LABEL_PRINTING_INSERTION_STRING_LABEL_TEMPLATE) <= 0) {
        if (appendComma) {
          problems.append(",");
        }
        problems.append(" account with bartenderCommandLine having no template insertion string encountered.");
        appendComma = true;
      }
    }
    
    //validate the results form definitions in the account
    validateAccountResultsFormDefinitions(account, problems);
    
    //validate the label template definitions in the account
    validateAccountLabelTemplateDefinitions(account, problems);
    
    //validate the label printers in the account
    validateAccountLabelPrinters(account, problems);
  }
  
  private void validateAccountResultsFormDefinitions(Account account, StringBuffer problems) {
    boolean appendComma = problems.length() > 0;
    Map formDefinitions = account.getResultsFormDefinitions();
    //if there are no results form definitions defined for the account, return an error
    if (formDefinitions.isEmpty()) {
      if (appendComma) {
        problems.append(",");
      }
      problems.append(" account with id = " + account.getId() + " has no results form definitions defined.");
      appendComma = true;              
    }
    else {
      Iterator iterator = formDefinitions.keySet().iterator();
      while (iterator.hasNext()) {
        ResultsFormDefinition formDef = (ResultsFormDefinition)formDefinitions.get(iterator.next());
        if (!LabelPrintingConfigurationUtils.isResultsFormDefinitionValid(formDef)) {
          if (appendComma) {
            problems.append(",");
          }
          problems.append(" results form definition with id = " + formDef.getFormDefinitionId() 
              + " in account with id = " + account.getId() + " is invalid.");
          appendComma = true;              
        }
      }
    }
  }
  
  private void validateAccountLabelTemplateDefinitions(Account account, StringBuffer problems) {
    boolean appendComma = problems.length() > 0;
    Map templateDefinitions = account.getLabelTemplateDefinitions();
    //if there are no label template definitions defined for the account, return an error
    if (templateDefinitions.isEmpty()) {
      if (appendComma) {
        problems.append(",");
      }
      problems.append(" account with id = " + account.getId() + " has no label template definitions defined.");
      appendComma = true;              
    }
    else {
      Map displayNames = new HashMap();
      Iterator iterator = templateDefinitions.keySet().iterator();
      while (iterator.hasNext()) {
        LabelTemplateDefinition templateDefinition = (LabelTemplateDefinition)templateDefinitions.get(iterator.next());
        String name = templateDefinition.getName();
        //check that the name by which the template will be displayed (display name if present, 
        //otherwise name) is unique
        String displayName = templateDefinition.getDisplayName();
        if (ApiFunctions.isEmpty(displayName)) {
          displayName = name;
        }
        if (displayNames.put(displayName, displayName) != null) {
          if (appendComma) {
            problems.append(",");
          }
          problems.append(" label template definition with name = " + name
              + " has the same display name as another template definition in the account");
          appendComma = true;              
        }
        //check that the object type is valid
        String objectType = templateDefinition.getObjectType();
        if (ApiFunctions.isEmpty(objectType)) {
          if (appendComma) {
            problems.append(",");
          }
          problems.append(" label template definition with name = " + name
              + " has an empty object type");
          appendComma = true;              
        }
        else {
          if (Constants.LABEL_PRINTING_OBJECT_TYPES.get(objectType) == null) {
            if (appendComma) {
              problems.append(",");
            }
            problems.append(" label template definition with name = " + name
                + " has an unknown object type");
            appendComma = true;              
          }
        }
        //check that the object subtype(s), if present, is/are valid
        Collection objectSubtypes = templateDefinition.getObjectSubtypesCollection();
        if (!ApiFunctions.isEmpty(objectSubtypes)) {
          //currently only sample objects can have a subtype
          if (!Constants.LABEL_PRINTING_OBJECT_TYPE_SAMPLE.equalsIgnoreCase(objectType)) {
            if (appendComma) {
              problems.append(",");
            }
            problems.append(" label template definition with name = " + name
                + " has object subtype(s) specified but does not have an object type of "
                + Constants.LABEL_PRINTING_OBJECT_TYPE_SAMPLE);
            appendComma = true;              
          }
          Iterator subtypeIterator = objectSubtypes.iterator();
          while (subtypeIterator.hasNext()) {
            String objectSubtype = (String)subtypeIterator.next();
            //make sure the subtype(s) is/are valid sample type(s)
            if (SystemConfiguration.getSampleType(objectSubtype) == null) {
              if (appendComma) {
                problems.append(",");
              }
              problems.append(" label template definition with name = " + name
                  + " has an unknown object subtype value of " + objectSubtype);
              appendComma = true;              
            }
          }
        }
        //check that the results form definition name is valid
        String formDefinitionName = templateDefinition.getResultsFormDefinitionName();
        if (ApiFunctions.isEmpty(formDefinitionName)) {
          if (appendComma) {
            problems.append(",");
          }
          problems.append(" label template definition with name = " + name
              + " has no results form definition name");
          appendComma = true;              
        }
        else {
          if (templateDefinition.getParent().getResultsFormDefinitions().get(formDefinitionName.toLowerCase()) == null) {
            if (appendComma) {
              problems.append(",");
            }
            problems.append(" label template definition with name = " + name
                + " references an unknown results form definition name (" + formDefinitionName + ")");
            appendComma = true;              
          }
        }
      }
    }
  }
  
  private void validateAccountLabelPrinters(Account account, StringBuffer problems) {
    boolean appendComma = problems.length() > 0;
    Map printers = account.getLabelPrinters();
    //if there are no label printers defined for the account, return an error
    if (printers.isEmpty()) {
      if (appendComma) {
        problems.append(",");
      }
      problems.append(" account with id = " + account.getId() + " has no printers defined.");
      appendComma = true;              
    }
    else {
      Map displayNames = new HashMap();
      Iterator iterator = printers.keySet().iterator();
      while (iterator.hasNext()) {
        LabelPrinter printer = (LabelPrinter)printers.get(iterator.next());
        String name = printer.getName();
        //check that the name by which the printer will be displayed (display name if present, 
        //otherwise name) is unique
        String displayName = printer.getDisplayName();
        if (ApiFunctions.isEmpty(displayName)) {
          displayName = name;
        }
        if (displayNames.put(displayName, displayName) != null) {
          if (appendComma) {
            problems.append(",");
          }
          problems.append(" printer with name = " + name
              + " has the same display name as another printer in the account");
          appendComma = true;              
        }
        //make sure the printer is set up for at least one type of printing mechanism
        //(i.e. tcp/ip, email, etc)
        if (!printer.isEmailBasedPrintingEnabled() &&
            !printer.isFileBasedPrintingEnabled() &&
            !printer.isTcpIpBasedPrintingEnabled()) {
          if (appendComma) {
            problems.append(",");
          }
          problems.append(" printer with name = " + name + " has no printing mechanism enabled");
          appendComma = true;              
        }
        //make sure all required printing information has been specified
        else {
          if (printer.isEmailBasedPrintingEnabled()) {
            if (ApiFunctions.isEmpty(printer.getEmailAddress())) {
              if (appendComma) {
                problems.append(",");
              }
              problems.append(" printer with name = " + name + " has no email address.");
              appendComma = true;              
            }
            if (ApiFunctions.isEmpty(printer.getEmailSubject())) {
              if (appendComma) {
                problems.append(",");
              }
              problems.append(" printer with name = " + name + " has no email subject.");
              appendComma = true;              
            }
          }
          if (printer.isFileBasedPrintingEnabled()) {
            if (!ApiFunctions.isEmpty(printer.getFileTransferCommand()) &&
                printer.getFileTransferCommand().indexOf(Constants.LABEL_PRINTING_TRANSFER_COMMAND_FILE_INSERTION_STRING) < 0) {
              if (appendComma) {
                problems.append(",");
              }
              problems.append(" printer with name = " + name + " has a file transfer command with no insertion point for the filename.");
              appendComma = true;              
            }
            if (ApiFunctions.isEmpty(printer.getFileName())) {
              if (appendComma) {
                problems.append(",");
              }
              problems.append(" printer with name = " + name + " has no file name.");
              appendComma = true;              
            }
            if (ApiFunctions.isEmpty(printer.getFileExtension())) {
              if (appendComma) {
                problems.append(",");
              }
              problems.append(" printer with name = " + name + " has no file extension.");
              appendComma = true;              
            }
          }
          if (printer.isTcpIpBasedPrintingEnabled()) {
            if (ApiFunctions.isEmpty(printer.getTcpIpHost())) {
              if (appendComma) {
                problems.append(",");
              }
              problems.append(" printer with name = " + name + " has no tcp/ip host.");
              appendComma = true;              
            }
            String tcpIpPort = printer.getTcpIpPort();
            if (ApiFunctions.isEmpty(tcpIpPort)) {
              if (appendComma) {
                problems.append(",");
              }
              problems.append(" printer with name = " + name + " has no tcp/ip port.");
              appendComma = true;              
            }
            else {
              if (!ApiFunctions.isInteger(tcpIpPort)) {
                if (appendComma) {
                  problems.append(",");
                }
                problems.append(" printer with name = " + name + " has an invalid tcp/ip port.");
                appendComma = true;              
              }
            }
          }
        }
        //validate the label templates specified as available on this printer
        validatePrinterLabelTemplates(account, printer, problems, appendComma);
      }
    }
  }
  
  private void validatePrinterLabelTemplates(Account account, LabelPrinter printer, StringBuffer problems, boolean appendComma) {
    Map templates = printer.getLabelTemplates();
    //if there are no label templates defined for the printer, return an error
    if (templates.isEmpty()) {
      if (appendComma) {
        problems.append(",");
      }
      problems.append(" printer with name = " + printer.getName() + " in account with id = " 
          + account.getId() + " has no label templates defined.");
      appendComma = true;              
    }
    else {
      Iterator iterator = templates.keySet().iterator();
      while (iterator.hasNext()) {
        LabelTemplate template = (LabelTemplate)templates.get(iterator.next());
        //make sure every template refers to a known template definition
        String templateDefinitionName = template.getLabelTemplateDefinitionName();
        if (printer.getParent().getLabelTemplateDefinitions().get(templateDefinitionName.toLowerCase()) == null) {
          if (appendComma) {
            problems.append(",");
          }
          problems.append(" label template for printer (" + printer.getName() 
              + ") references an unknown labelTemplateDefinition (" + templateDefinitionName + ")");
          appendComma = true;              
        }
        //make sure every template has a fully qualified name value
        String fullName = template.getFullyQualifiedName();
        if (ApiFunctions.isEmpty(fullName)) {
          if (appendComma) {
            problems.append(",");
          }
          problems.append(" label template with labelTemplateDefinitionName = " + templateDefinitionName
              + " has no fully qualified name value");
          appendComma = true;              
        }
      }
    }
  }
      
}
