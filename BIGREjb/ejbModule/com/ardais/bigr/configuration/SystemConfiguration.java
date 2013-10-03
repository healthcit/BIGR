package com.ardais.bigr.configuration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.List;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.api.ApiLogger;
import com.ardais.bigr.api.ApiResources;
import com.ardais.bigr.concept.BigrConceptList;
import com.ardais.bigr.iltds.assistants.LegalValueSet;
import com.ardais.bigr.iltds.databeans.DerivativeOperation;
import com.ardais.bigr.iltds.databeans.SampleType;
import com.ardais.bigr.kc.form.def.BigrFormDefinition;
import com.ardais.bigr.util.Constants;
import com.gulfstreambio.gboss.GbossFactory;

/**
 * @author JEsielionis
 * Created on Oct 11, 2005
 */
public class SystemConfiguration {

  public final static String CONCEPT_LIST_DERIVATION_OPERATIONS = "derivation_operations";
  public final static String CONCEPT_LIST_SAMPLE_TYPES = "sample_types";
  public final static String CONCEPT_LIST_STORAGE_TYPES = "storage_types";
  public final static String CONCEPT_LIST_SAMPLE_ATTRIBUTES = "sample_attributes";
  public final static String CONCEPT_LIST_CASE_ATTRIBUTES = "case_attributes";
  public final static String CONCEPT_LIST_DONOR_ATTRIBUTES = "donor_attributes";
  public final static String SIMPLE_LIST_RESULTS_VIEW_COLUMNS = "results_view_columns";
  public final static String SIMPLE_LIST_RESULTS_VIEW_COLUMNS_SO_ONLY = "results_view_columns_so_only";
  public final static String SIMPLE_LIST_RESULTS_VIEW_COLUMNS_SO_AND_DI_ONLY = "results_view_columns_so_and_di_only";
  public final static String LEGAL_VALUE_SET_KC_ANNOTATED_OBJECTS = "kc_annotated_objects";
  public final static String LEGAL_VALUE_SET_DATA_FORM_USES = "data_form_uses";
   
  private static SystemConfigurationData _systemConfigurationData = null;
  
  /**
   * The constructor is private to prevent this class from being instantiated.
   * There is no reason to instantiate the class, since all of its methods are static.
   */
  private SystemConfiguration() {
    super();
  }

  /**
   * Initialize or refresh the system configuration information.
   */
  public static synchronized void initOrRefresh() {
    // Since we know that initializing the system configuration will eventually call GBOSS, we
    // want to make sure that GBOSS is initialized as well.  Even though it would get lazily
    // initialized, we want to initialize it here explicitly since we've run into some strange
    // unexplained issues when parsing the system configuration XML with the digester, and then
    // in the middle of that parsing the GBOSS XML with the digester.
    GbossFactory.initOrRefresh();

    // This gets called from a cron job that runs every night.  We want
    // to be sure that if there's an error it gets logged, which it might
    // not be if we didn't do something here due to this being called
    // from a command shell.  So we add an extra logging call here in case of
    // an exception.
    FileInputStream systemConfigFis = null;
    FileInputStream defaultResultsViewFis = null;
    try {
      ApiLogger.getLog().info("Started initializing SystemConfiguration...");
      SystemConfigurationData configData = new SystemConfigurationData();
      String systemConfigFilename = ApiResources.getResourcePath(Constants.SYSTEM_CONFIGURATION_FILENAME);
      String defaultResultsViewFilename = ApiResources.getResourcePath(Constants.DEFAULT_RESULTS_VIEW_FILENAME);
      String defaultResultsLocalViewFilename = ApiResources.getResourcePath(Constants.DEFAULT_RESULTS_LOCALVIEW_FILENAME);     
      systemConfigFis = new FileInputStream(systemConfigFilename);
      try {
        defaultResultsViewFis = new FileInputStream(defaultResultsLocalViewFilename);
        }
      catch (FileNotFoundException e) {
        defaultResultsViewFis = new FileInputStream(defaultResultsViewFilename);      
      }
      configData.initOrRefresh(systemConfigFis, defaultResultsViewFis);
      configData.setImmutable();
      configData.validate();
      //if we made it here then we have a valid configuration data object, so
      //it's ok to point the data member to it
      _systemConfigurationData = configData;
      ApiLogger.getLog().info("Finished initializing SystemConfiguration.");
    }
    catch (Exception e) {
      ApiLogger.log("SystemConfiguration refresh failed.", e);
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      // Don't use the usual ApiFunctions .close() method or exception-tranformation
      // function here -- because of when this code gets called very early in
      // application initialization, we don't want to risk any circular initialization
      // situations between the static class initializers in this class and the ones
      // in ApiFunctions.
      //
      if (systemConfigFis != null) {
        try {
          systemConfigFis.close();
        }
        catch (Exception e) {
        }
      }
      if (defaultResultsViewFis != null) {
        try {
          defaultResultsViewFis.close();
        }
        catch (Exception e) {
        }
      }
    }
  }
  
  private static void initIfNecessary() {
    if (_systemConfigurationData == null) {
      initOrRefresh();
    }
  }
  
  /**
   * Retrieve the BigrConceptList with the specified name, if one exists
   * @param name  the name of the BigrConceptList to retrieve.
   * @return  a BigrConceptList with the specified name, if one exists
   */
  public static BigrConceptList getConceptList(String name) {
    initIfNecessary();
    return _systemConfigurationData.getConceptList(name);
  }
  
  /**
   * Retrieve all known BigrConceptLists
   * @return  a List of BigrConceptLists
   */
  public static List getConceptLists() {
    initIfNecessary();
    return _systemConfigurationData.getConceptListList();
  }
  
  /**
   * Retrieve the SimpleList with the specified name, if one exists
   * @param name  the name of the SimpleList to retrieve.
   * @return  a SimpleList with the specified name, if one exists
   */
  public static SimpleList getSimpleList(String name) {
    initIfNecessary();
    return _systemConfigurationData.getSimpleList(name);
  }
  
  /**
   * Retrieve all known SimpleLists
   * @return  a List of SimpleLists
   */
  public static List getSimpleLists() {
    initIfNecessary();
    return _systemConfigurationData.getSimpleListList();
  }
  
  /**
   * Retrieve the LegalValueSet with the specified name, if one exists
   * @param name  the name of the LegalValueSet to retrieve.
   * @return  a LegalValueSet List with the specified name, if one exists
   */
  public static LegalValueSet getLegalValueSet(String name) {
    initIfNecessary();
    return _systemConfigurationData.getLegalValueSet(name);
  }
  
  /**
   * Retrieve all known LegalValueSets
   * @return  a List of LegalValueSets
   */
  public static List getLegalValueSets() {
    initIfNecessary();
    return _systemConfigurationData.getLegalValueSetList();
  }

  public static BigrFormDefinition getDefaultResultsView() {
    initIfNecessary();
    return _systemConfigurationData.getDefaultResultsView();
  }
  
  /**
   * Retrieve all known SampleTypes
   * @return  a List of SampleTypes
   */
  public static List getSampleTypes() {
    initIfNecessary();
    return _systemConfigurationData.getSampleTypeConfiguration().getSampleTypeList();
  }
  
  /**
   * Retrieve the SampleType specified by the cui
   * @param sampleTypeCui  the cui of a SampleType.
   * @return  the SampleType corresponding to the specified cui
   */
  public static SampleType getSampleType(String sampleTypeCui) {
    if (ApiFunctions.isEmpty(ApiFunctions.safeTrim(sampleTypeCui))) {
      throw new ApiException("sampleTypeCui cannot be empty");
    }
    SampleType returnValue = null;
    initIfNecessary();
    Iterator iterator = 
      _systemConfigurationData.getSampleTypeConfiguration().getSampleTypeList().iterator();
    while (iterator.hasNext() && returnValue == null) {
      SampleType sampleType = (SampleType)iterator.next();
      if (sampleType.getCui().equalsIgnoreCase(sampleTypeCui)) {
        returnValue = sampleType;
      }
    }
    return returnValue;
  }
  
  /**
   * Retrieve all known DerivativeOperations
   * @return  a List of DerivativeOperations
   */
  public static List getDerivativeOperations() {
    initIfNecessary();
    return _systemConfigurationData.getDerivativeOperationConfiguration().getDerivativeOperationList();
  }
  
  /**
   * Retrieve the DerivativeOperation specified by the cui
   * @param derivativeOperationCui  the cui of a DerivativeOperation.
   * @return  the DerivativeOperation corresponding to the specified cui
   */
  public static DerivativeOperation getDerivativeOperation(String derivativeOperationCui) {
    if (ApiFunctions.isEmpty(ApiFunctions.safeTrim(derivativeOperationCui))) {
      throw new ApiException("derivativeOperationCui cannot be empty");
    }
    DerivativeOperation returnValue = null;
    initIfNecessary();
    Iterator iterator = 
      _systemConfigurationData.getDerivativeOperationConfiguration().getDerivativeOperationList().iterator();
    while (iterator.hasNext() && returnValue == null) {
      DerivativeOperation dop = (DerivativeOperation)iterator.next();
      if (dop.getCui().equalsIgnoreCase(derivativeOperationCui)) {
        returnValue = dop;
      }
    }
    return returnValue;
  }

}
