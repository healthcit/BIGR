package com.ardais.bigr.configuration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.api.ApiLogger;
import com.ardais.bigr.api.ApiResources;
import com.ardais.bigr.iltds.databeans.SampleType;
import com.ardais.bigr.util.Constants;
import com.gulfstreambio.gboss.GbossFactory;
import com.gulfstreambio.kc.form.def.results.ResultsFormDefinition;

public class LabelPrintingConfiguration {
   
  private static LabelPrintingConfigurationData _labelPrintingConfigurationData = null;
  private static boolean _hasBeenInitialized = false;
  private static boolean _configFileExists = false;
  private static long _lastRefreshTimestamp;
  
  /**
   * The constructor is private to prevent this class from being instantiated.
   * There is no reason to instantiate the class, since all of its methods are static.
   */
  private LabelPrintingConfiguration() {
    super();
  }
  
  private static void initIfNecessary() {
    
    boolean refreshRequired = false;
    
    //if initialization has never been performed, refresh
    if (!_hasBeenInitialized) {
      refreshRequired = true;
    }
    
    //see if the configuration file exists
    String labelPrintingConfigFilename = ApiResources.getResourcePath(Constants.LABEL_PRINTING_CONFIGURATION_FILENAME);
    File labelPrintingConfigFile = new File(labelPrintingConfigFilename);
    
    //if there was no configuration file the last time we refreshed but there is now, refresh
    if (!_configFileExists && labelPrintingConfigFile.exists()) {
      refreshRequired = true;
    }
    
    //if the configuration file exists and it has been updated since the last refresh, refresh
    if (labelPrintingConfigFile.exists() && 
        labelPrintingConfigFile.lastModified() > _lastRefreshTimestamp) {
      refreshRequired = true;
    }
    
    //if the configuration file does not exist, clear out any configuration data that may have
    //previously been loaded
    if (!labelPrintingConfigFile.exists()) {
      _labelPrintingConfigurationData = null;
      _configFileExists = false;
    }
    
    //if a refresh is required, do it
    if (refreshRequired) {
      initOrRefresh();
    }
  }

  /**
   * Initialize or refresh the label printing configuration information.
   */
  public static synchronized void initOrRefresh() {
    //indicate that initialization has taken place
    _hasBeenInitialized = true;
    
    //clear out any existing configuration data
    _labelPrintingConfigurationData = null;
    
    // Since initializing the label printing configuration might eventually call GBOSS, 
    // we want to make sure that GBOSS is initialized as well.  Even though it would 
    // get lazily initialized, we want to initialize it here explicitly since we've run 
    // into some strange unexplained issues when parsing the system configuration XML with 
    // the digester, and then in the middle of that parsing the GBOSS XML with the digester.
    GbossFactory.getInstance();

    // This gets called from a cron job that runs every night.  We want to be sure that if
    // there's an error it gets logged, which it might not be if we didn't do something here due 
    // to this being called from a command shell.  So we add an extra logging call here in case of
    // an exception.
    FileInputStream labelPrintingConfigFis = null;
    try {
      ApiLogger.getLog().info("Started initializing Label Printing Configuration...");
      LabelPrintingConfigurationData configData = new LabelPrintingConfigurationData();
      String labelPrintingConfigFilename = ApiResources.getResourcePath(Constants.LABEL_PRINTING_CONFIGURATION_FILENAME);
      labelPrintingConfigFis = new FileInputStream(labelPrintingConfigFilename);
      //indicate that the configuration file exists
      _configFileExists = true;
      configData.initOrRefresh(labelPrintingConfigFis);
      configData.setImmutable();
      configData.validate();
      //if we made it here then we have a valid configuration data object, so
      //it's ok to point the data member to it
      _labelPrintingConfigurationData = configData;
      //record the time of the successful refresh
      _lastRefreshTimestamp = System.currentTimeMillis();
      ApiLogger.getLog().info("Finished initializing Label Printing Configuration.");
    }
    catch (FileNotFoundException fnfe) {
      //indicate that the configuration file does not exist
      _configFileExists = false;
    }
    catch (IllegalStateException e) {
      //log the error and rethrow it
      ApiLogger.log("Label Printing Configuration refresh failed.", e);
      throw e;
    }
    finally {
      // Don't use the usual ApiFunctions .close() method or exception-tranformation
      // function here -- because of when this code gets called very early in
      // application initialization, we don't want to risk any circular initialization
      // situations between the static class initializers in this class and the ones
      // in ApiFunctions.
      if (labelPrintingConfigFis != null) {
        try {
          labelPrintingConfigFis.close();
        }
        catch (Exception e) {
        }
      }
    }
  }
  
  public static LabelTemplateDefinition getLabelTemplateDefinition(String accountId, String templateDefinitionName) {
    initIfNecessary();
    LabelTemplateDefinition returnValue = null;
    if (_labelPrintingConfigurationData != null) {
      Account account = _labelPrintingConfigurationData.getAccount(accountId);
      if (account != null) {
        Map templateDefinitions = account.getLabelTemplateDefinitions();
        Iterator templateDefinitionIterator = templateDefinitions.keySet().iterator();
        boolean templateDefinitionFound = false;
        while (templateDefinitionIterator.hasNext() && !templateDefinitionFound) {
          LabelTemplateDefinition templateDefinition = 
            (LabelTemplateDefinition)templateDefinitions.get(templateDefinitionIterator.next());
          if (templateDefinition.getName().equalsIgnoreCase(templateDefinitionName)) {
            returnValue = templateDefinition;
            templateDefinitionFound = true;
          }
        }
      }
    }
    return returnValue;
  }
  
  public static ResultsFormDefinition getResultsFormDefinition(String accountId, 
                                                               String resultsFormDefinitionName) {
    initIfNecessary();
    ResultsFormDefinition returnValue = null;
    if (_labelPrintingConfigurationData != null) {
      Account account = _labelPrintingConfigurationData.getAccount(accountId);
      if (account != null) {
        Map resultsFormDefinitions = account.getResultsFormDefinitions();
        Iterator resultsFormDefinitionIterator = resultsFormDefinitions.keySet().iterator();
        boolean resultsFormDefinitionFound = false;
        while (resultsFormDefinitionIterator.hasNext() && !resultsFormDefinitionFound) {
          ResultsFormDefinition resultsFormDefinition = 
            (ResultsFormDefinition)resultsFormDefinitions.get(resultsFormDefinitionIterator.next());
          if (resultsFormDefinition.getName().equalsIgnoreCase(resultsFormDefinitionName)) {
            returnValue = resultsFormDefinition;
            resultsFormDefinitionFound = true;
          }
        }
      }
    }
    return returnValue;
  }
  
  public static Collection getPrintersForLabelTemplateDefinition(String accountId, String templateDefinitionName) {
    initIfNecessary();
    List returnValue = new ArrayList();
    if (_labelPrintingConfigurationData != null) {
      Account account = _labelPrintingConfigurationData.getAccount(accountId);
      if (account != null) {
        Map printers = account.getLabelPrinters();
        Iterator printerIterator = printers.keySet().iterator();
        while (printerIterator.hasNext()) {
          LabelPrinter printer = (LabelPrinter)printers.get(printerIterator.next());
          Map templates = printer.getLabelTemplates();
          Iterator templateIterator = templates.keySet().iterator();
          boolean templateFound = false;
          while (templateIterator.hasNext() && !templateFound) {
            LabelTemplate template = (LabelTemplate)templates.get(templateIterator.next());
            if (template.getLabelTemplateDefinitionName().equalsIgnoreCase(templateDefinitionName)) {
              returnValue.add(printer);
              templateFound = true;
            }
          }
        }
      }
    }
    //sort the printers alphabetically by display name
    if (returnValue.size() > 1) {
      Collections.sort(returnValue);
    }
    return returnValue;
  }
  
  public static LabelPrinter getPrinter(String accountId, String labelPrinterName) {
    initIfNecessary();
    LabelPrinter returnValue = null;
    if (_labelPrintingConfigurationData != null) {
      Account account = _labelPrintingConfigurationData.getAccount(accountId);
      if (account != null) {
        Map printers = account.getLabelPrinters();
        Iterator printerIterator = printers.keySet().iterator();
        boolean printerFound = false;
        while (printerIterator.hasNext() && !printerFound) {
          LabelPrinter printer = (LabelPrinter)printers.get(printerIterator.next());
          if (printer.getName().equalsIgnoreCase(labelPrinterName)) {
            returnValue = printer;
            printerFound = true;
          }
        }
      }
    }
    return returnValue;
  }
  
  public static String getBartenderCommandLine(String accountId) {
    initIfNecessary();
    String returnValue = null;
    if (_labelPrintingConfigurationData != null) {
      Account account = _labelPrintingConfigurationData.getAccount(accountId);
      if (account != null) {
        returnValue = account.getBartenderCommandLine();
      }
    }
    return returnValue;
  }
  
  /**
   * Retrieve all of the label template definitions and the printers on which they are available
   * within the specified account for the specified object type.  Note that this method ignores
   * any objectSubtype value on the label template definitions.  Also note that any templates for
   * which there are no printers available are not returned.
   * @param accountId
   * @param objectType
   * @return A Map with keys consisting of the label template definitions and values consisting
   * of a Collection of printers on which that template is available. 
   */
  public static Map getLabelTemplateDefinitionsAndPrintersForObjectType(String accountId, String objectType) {
    initIfNecessary();
    Map returnValue = new HashMap();
    if (_labelPrintingConfigurationData != null) {
      Account account = _labelPrintingConfigurationData.getAccount(accountId);
      if (account != null) {
        Map templateDefinitions = account.getLabelTemplateDefinitions();
        Iterator templateDefinitionIterator = templateDefinitions.keySet().iterator();
        while (templateDefinitionIterator.hasNext()) {
          LabelTemplateDefinition templateDefinition = (LabelTemplateDefinition)templateDefinitions.get(templateDefinitionIterator.next());
          if (templateDefinition.getObjectType().equalsIgnoreCase(objectType)) {
            Collection supportingPrinters = getPrintersForLabelTemplateDefinition(accountId, templateDefinition.getName());
            //only include templates for which there is at least one supporting printer
            if (!ApiFunctions.isEmpty(supportingPrinters)) {
              returnValue.put(templateDefinition, supportingPrinters);
            }
          }
        }
      }
    }
    //return a TreeMap so the templates are sorted alphabetically by display name
    return new TreeMap(returnValue);
  }
  
  /**
   * Retrieve all of the sample label template definitions and the printers on which they are 
   * available within the specified account for all sample types.  Note that any templates for
   * which there are no printers available are not returned.
   * @param accountId
   * @return A Map with keys consisting of the sample type cuis and values consisting
   * of Maps with keys consisting of the label template definitions and values consisting
   * of a Collection of printers on which that template is available. 
   */
  public static Map getSampleLabelTemplateDefinitionsAndPrintersBySampleType(String accountId) {
    initIfNecessary();
    Map returnValue = new HashMap();
    if (_labelPrintingConfigurationData != null) {
      //get a map of all sample label template definitions for the specified account
      Map allSampleTemplateDefinitions = getLabelTemplateDefinitionsAndPrintersForObjectType(accountId, Constants.LABEL_PRINTING_OBJECT_TYPE_SAMPLE);
      //if that map is not empty, iterate over it adding each label template definition to the map for each sample
      //type to which it is applicable
      if (!ApiFunctions.isEmpty(allSampleTemplateDefinitions)) {
        //initialize the map to be returned by creating a key for each sample type and a value
        //of an empty TreeMap (so the templates for each subtype will be sorted by display name)
        List sampleTypes = SystemConfiguration.getSampleTypes();
        Iterator sampleTypeIterator = sampleTypes.iterator();
        while (sampleTypeIterator.hasNext()) {
          SampleType sampleType = (SampleType)sampleTypeIterator.next();
          returnValue.put(sampleType.getCui(), new TreeMap());
        }
        //now iterate over all of the label template definitions, adding each one to every sample 
        //type to which it is applicable
        Iterator templateDefinitionIterator = allSampleTemplateDefinitions.keySet().iterator();
        while (templateDefinitionIterator.hasNext()) {
          LabelTemplateDefinition templateDefinition = (LabelTemplateDefinition)templateDefinitionIterator.next();
          Collection supportedPrinters = (Collection)allSampleTemplateDefinitions.get(templateDefinition);
          Collection subtypes = templateDefinition.getObjectSubtypesCollection();
          //if there is/are no subtypes specified, the template is applicable to all sample types so
          //add it to every one of them
          if (ApiFunctions.isEmpty(subtypes)) {
            sampleTypeIterator = returnValue.keySet().iterator();
            while (sampleTypeIterator.hasNext()) {
              String sampleType = (String)sampleTypeIterator.next();
              ((Map)returnValue.get(sampleType)).put(templateDefinition, supportedPrinters);
            }
          }
          //otherwise add the template definition only to the subtypes to which it applies
          else {
            Iterator subtypeIterator = subtypes.iterator();
            while (subtypeIterator.hasNext()) {
              String subtype = (String)subtypeIterator.next();
              ((Map)returnValue.get(subtype)).put(templateDefinition, supportedPrinters);
            }
          }
        }
      }
    }
    return returnValue;
  }

}
