package com.ardais.bigr.orm.helpers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.iltds.assistants.LegalValueSet;
import com.ardais.bigr.util.ArtsConstants;
import com.gulfstreambio.gboss.GbossFactory;
import com.gulfstreambio.gboss.GbossValue;
import com.gulfstreambio.gboss.GbossValueSet;

/**
 * Return various BIGR master data derived from GBOSS data.  Master Data is data that rarely changes between
 * releases.  This class caches master data in memory by invoking a specific subject area
 * based on the client's call.
 * <p>
 * Data is cached/refreshed when {@link #initOrRefresh()} method is called
 * or the first time it is used.
 * <p>
 * This a static class so that nobody can instantiate it.  Nobody needs to
 * since all the methods are static.
 */
public class BigrGbossData {

  // A Map of GbossValue objects that represents the other value for each GbossValueSet that has 
  // an other value. Each GbossValue in the _otherValues map is keyed by the GbossValueSet's cui.
  private static Map _otherValues = new HashMap(); // will be initialized lazily

  // A Map of GbossValue objects that represents no value for each GbossValueSet that has 
  // no value.  Each GbossValue in the _noValues map is keyed by the GbossValueSet's cui.
  private static Map _noValues = new HashMap();  // will be initialized lazily
  
  // A Map of GbossValueSet objects with all "other" values removed, used by OCE. 
  private static Map _valueSetsWithoutOthers = new HashMap();  //will be initialized lazily
  
  // A Map of GbossValueSet objects, containing just the alphabetized leaf nodes of the 
  // original value set, used by various dropdowns. 
  private static Map _valueSetsAlphabetized = new HashMap();  //will be initialized lazily
  
  // A value set containing all received diagnosis values (without others)
  private static GbossValueSet _receivedDiagnosisValueSet = null; //will be initialized lazily
  
  // A value set containing all received tissue values (without others)
  private static GbossValueSet _receivedTissueValueSet = null; //will be initialized lazily

  /**
   * The constructor is private to prevent this class from being instantiated.
   * There is no reason to instantiate the class, since all of its methods are static.
   */
  private BigrGbossData() {
    super();
  }

  /**
   * Returns a hierarchical <code>LegalValueSet</code> of code/description pairs for the 
   * specified category and disease.
   *
   * @param  category  the category
   * @param  disease  the disease
   * @return  A <code>LegalValueSet</code> containing the list.
   */
  public static LegalValueSet getPdcStageHierarchyLookupList(String category, String disease) {
    GbossValueSet valueSet = null;
    if (ArtsConstants.VALUE_SET_DDC_DISTANT_METASTASIS.equalsIgnoreCase(category)) {
      valueSet = getValueSet(ArtsConstants.VALUE_SET_DDC_DISTANT_METASTASIS);
    }
    else if (ArtsConstants.VALUE_SET_DDC_LYMPH_NODE_STAGE_DESC.equalsIgnoreCase(category)) {
      valueSet = getValueSet(ArtsConstants.VALUE_SET_DDC_LYMPH_NODE_STAGE_DESC);
    }
    else if (ArtsConstants.VALUE_SET_DDC_STAGE_GROUPING.equalsIgnoreCase(category)) {
      valueSet = getValueSet(ArtsConstants.VALUE_SET_DDC_STAGE_GROUPING);
    }
    else if (ArtsConstants.VALUE_SET_DDC_TUMOR_STAGE_DESC.equalsIgnoreCase(category)) {
      valueSet = getValueSet(ArtsConstants.VALUE_SET_DDC_TUMOR_STAGE_DESC);
    }
    else {
      throw new ApiException("Unknown category (" + category + ") encountered.");
    }
    //get the value corresponding to the disease passed in
    GbossValue valueForDisease = valueSet.getValue(disease);
    //turn the values for this disease into a legal value set.  Note that tumor stage
    //has a sublevel to it (tumor stage type), so if the values have children add them
    //as a sub lvs.
    Iterator valueIterator = valueForDisease.getValues().iterator();
    LegalValueSet lvs = new LegalValueSet();
    while (valueIterator.hasNext()) {
      GbossValue value = (GbossValue) valueIterator.next();
      if (ApiFunctions.isEmpty(value.getValues())) {
        //do nothing, as there are no choices for the tumor stage type
      }
      else {
        Iterator subValueIterator = value.getValues().iterator();
        LegalValueSet sublvs = new LegalValueSet();
        while (subValueIterator.hasNext()) {
          GbossValue subValue = (GbossValue) subValueIterator.next();
          sublvs.addLegalValue(subValue.getCui(), subValue.getDescription());
        }
        lvs.addLegalValue(value.getCui(), value.getDescription(), sublvs);
      }
    }
    return lvs;
  }
 
  public static LegalValueSet getDiagnosticResults() {
    GbossValueSet testTypes = BigrGbossData.getValueSet(ArtsConstants.VALUE_SET_DIAGNOSTICS_DDC1);
    Iterator testTypeIterator = testTypes.getValues().iterator();
    LegalValueSet lvs = new LegalValueSet();

    // loop through all the test types...
    while (testTypeIterator.hasNext()) {
      // get the test type
      GbossValue testType = (GbossValue) testTypeIterator.next();
      // use that test type to get its children which are diagnostic test names...
      Iterator testNamesIterator = testType.getValues().iterator();
      // loop through the test names...
      while (testNamesIterator.hasNext()) {
        GbossValue testName = (GbossValue) testNamesIterator.next();
        // use that test name to get its children which are test results...
        Iterator testResultsIterator = testName.getValues().iterator();
        LegalValueSet sublvs = new LegalValueSet();
        while (testResultsIterator.hasNext()) {
          GbossValue testResult = (GbossValue) testResultsIterator.next();
          sublvs.addLegalValue(testResult.getCui(), testResult.getDescription());
        }
        // add each test name with its results as a sub legal value set
        lvs.addLegalValue(testName.getCui(), testName.getDescription(), sublvs);
      }
    }

    return lvs;
  }

  public static LegalValueSet getDiagnosticTests() {
    GbossValueSet testTypes = BigrGbossData.getValueSet(ArtsConstants.VALUE_SET_DIAGNOSTICS_DDC1);
    Iterator testTypeIterator = testTypes.getValues().iterator();
    LegalValueSet lvs = new LegalValueSet();
    
    // loop through all the test types...
    while (testTypeIterator.hasNext()) {
      // get the test type
      GbossValue testType = (GbossValue) testTypeIterator.next();
      // use that test type to get its children which are diagnostic test names...
      Iterator testNamesIterator = testType.getValues().iterator();
      LegalValueSet sublvs = new LegalValueSet();
      // loop through the test names...
      while (testNamesIterator.hasNext()) {
        GbossValue testName = (GbossValue) testNamesIterator.next();
        sublvs.addLegalValue(testName.getCui(), testName.getDescription());
      }
      // add each diagnostic test name with its results as a sub legal value set
      lvs.addLegalValue(testType.getCui(), testType.getDescription(), sublvs);
    }
    return lvs;
  }

  public static LegalValueSet getDiagnosticTypes() {
    return getValueSetAsLegalValueSet(ArtsConstants.VALUE_SET_DIAGNOSTICS_DDC1);
  }
  
  /**
   * Returns the GbossValueSet with the specified cui.
   * 
   * @param  valueSetCui  the cui of the desired GbossValueSet.
   * @return the GbossValueSet.  If no such GbossValueSet exists, null is returned.
   */
  public static synchronized GbossValueSet getValueSet(String valueSetCui)  {
    return GbossFactory.getInstance().getValueSets().getValueSet(valueSetCui);
  }
  
  /**
   * Returns the GbossValueSet with the specified cui, with all "other" values removed.
   * 
   * @param  valueSetCui  the cui of the desired GbossValueSet.
   * @return the GbossValueSet, with all "Other" values removed.  If no such GbossValueSet exists, 
   * null is returned.
   */
  public static synchronized GbossValueSet getValueSetWithoutOthers(String valueSetCui)  {
    GbossValueSet returnValue = (GbossValueSet)_valueSetsWithoutOthers.get(valueSetCui);
    if (returnValue == null) {
      GbossValueSet originalValueSet = getValueSet(valueSetCui);
      if (originalValueSet != null) {
        //create a new value set from the original value set
        returnValue = new GbossValueSet(originalValueSet);
        //find the set of "other" cuis
        Set cuisToRemove = findOtherCuis(returnValue);
        //remove the GbossValues correspoding to those cuis
        returnValue.removeValues(cuisToRemove);
        //mark the new value set as immutable
        returnValue.setImmutable();
        //add the value set to the map, so we don't have to rebuild it if it's requested again
        _valueSetsWithoutOthers.put(valueSetCui, returnValue);
      }
    }
    return returnValue;
  }
  
  /*
   * Find the cuis of all "other" leaf nodes
   */
  private static Set findOtherCuis(GbossValueSet valueSet) {
    Set returnValue = new HashSet();
    Iterator iterator = valueSet.depthFirstIterator();
    while (iterator.hasNext()) {
      GbossValue value = (GbossValue) iterator.next();
      if (value.isOtherValue() && ApiFunctions.isEmpty(value.getValues())) {
        returnValue.add(value.getCui());
      }
    }
    return returnValue;
  }
  
  public static synchronized GbossValueSet getValueSetAlphabetized(String valueSetCui)  {
    return getValueSetAlphabetized(valueSetCui, true);
  }
  
  /**
   * Returns a version of the specified GbossValueSet containing just the alphabetized leaf nodes
   * 
   * @param  valueSetCui  the cui of the desired GbossValueSet.
   * @param  includeOthers  a boolean indicating if others should be included in the value set.
   * @return a version of the specified GbossValueSet containing just the alphabetized leaf nodes.  
   * If no such GbossValueSet exists, null is returned.
   */
  public static synchronized GbossValueSet getValueSetAlphabetized(String valueSetCui, boolean includeOthers)  {
    String key = valueSetCui + includeOthers;
    GbossValueSet returnValue = (GbossValueSet)_valueSetsAlphabetized.get(key);
    if (returnValue == null) {
      GbossValueSet originalValueSet = getValueSet(valueSetCui);
      if (originalValueSet != null) {
        Map unSortedValues = new HashMap();
        GbossValue otherValue = null;
        //iterate over the value set, adding all non-other leaf nodes to a map
        Iterator iterator = originalValueSet.depthFirstIterator();
        while (iterator.hasNext()) {
          GbossValue value = (GbossValue)iterator.next();
          if (ApiFunctions.isEmpty(value.getValues())) {
            if (value.isOtherValue()) {
              otherValue = value; //the other value will be added to the end
            }
            else {
              unSortedValues.put(value.getDescription().toUpperCase(), value);
            }
          }
        }
        //sort the values
        TreeMap sortedValues = new TreeMap(unSortedValues);
        //create a new GbossValueSet and add the sorted values to it
        returnValue = new GbossValueSet();
        iterator = sortedValues.values().iterator();
        while (iterator.hasNext()) {
          returnValue.addValue(new GbossValue((GbossValue)iterator.next()));
        }
        //add the other value to the end, if indicated
        if (otherValue != null && includeOthers) {
          returnValue.addValue(new GbossValue(otherValue));
        }
        returnValue.setImmutable();
        //add the value set to the map, so we don't have to rebuild it if it's requested again
        _valueSetsAlphabetized.put(key, returnValue);
      }
    }
    return returnValue;
  }

  /**
   * Returns true if there is a GbossValueSet with the specified cui.
   * 
   * @param  valueSetCui the cui for the desired GbossValueSet.
   * @return true if the GbossValueSet exists.
   */
  public static boolean isExistsValueSet(String valueSetCui) {
    return (getValueSet(valueSetCui) != null);
  }

  /**
   * Returns a hierarchical <code>LegalValueSet</code> of code/description pairs for the 
   * specified category and disease.
   *
   * @param  category  the category
   * @param  disease  the disease
   * @return  A <code>LegalValueSet</code> containing the list.
   */
  public static GbossValueSet getValueSetByDiagnosis(String valueSetCui, String diagnosisCui) {
    GbossValueSet valueSet = getValueSet(valueSetCui);
    
    GbossValueSet returnValue = new GbossValueSet();

    //get the value corresponding to the diagnosis passed in
    GbossValue valueForDisease = valueSet.getValue(diagnosisCui);
    
    //add the values for this diagnosis to the GbossValueSet we will return.
    Iterator valueIterator = valueForDisease.getValues().iterator();
    while (valueIterator.hasNext()) {
      returnValue.addValue(new GbossValue((GbossValue)valueIterator.next()));
    }
    
    returnValue.setImmutable();
    return returnValue;
  }

  /*
   *  This is a generic routine for returning a simple (1-level) GbossValueSet as a legal value set
   */
  public static LegalValueSet getValueSetAsLegalValueSet(String valueSetCui) {

    GbossValueSet valueSet = BigrGbossData.getValueSet(valueSetCui);
    return getValueSetAsLegalValueSet(valueSet);
  }

  /*
   *  This is a generic routine for returning a simple (1-level) GbossValueSet as a legal value set
   */
  public static LegalValueSet getValueSetAsLegalValueSet(GbossValueSet valueSet) {
    Iterator valueIterator = valueSet.getValues().iterator();
    // okay, return this as a LegalSet
    LegalValueSet lvs = new LegalValueSet();
    while (valueIterator.hasNext()) {
      GbossValue value = (GbossValue) valueIterator.next();
      lvs.addLegalValue(value.getCui(), value.getDescription());
    }
    return lvs;
  }

  /**
   * Returns the GbossValue that represents the other value for the GbossValueSet with
   * the specified cui.
   * 
   * @param  cui  the cui of the desired GbossValueSet
   * @return a GbossValue representing the other value
   */
  public static GbossValue getOtherValueValue(String valueSetCui) {

    // Get the other value from the cache.
    GbossValue otherValue = (GbossValue)_otherValues.get(valueSetCui);    

    // If the other value is not cached, then iterate over the GbossValueSet to find it and
    // cache it for next time.
    if (otherValue == null) {
      GbossValueSet valueSet = getValueSet(valueSetCui);
      if (valueSet == null) {
        throw new IllegalArgumentException("No GbossValueSet with cui = " + valueSetCui + " exists.");
      }
      Iterator valueIterator = valueSet.depthFirstIterator();
      while (valueIterator.hasNext()) {
        GbossValue value = (GbossValue)valueIterator.next();
        if (value.isOtherValue()) {

          // If we already found an other value within this GbossValueSet throw an exception since
          // there should only be one.
          if (otherValue != null) {
            throw new IllegalArgumentException("GbossValue Set " + valueSet.getDescription() + " (cui = " + valueSet.getCui() + ") has more than one other value");
          }
          otherValue = value;
          _otherValues.put(valueSetCui, otherValue);
        }
      }
      
      // If we could not find the other value for the specified GbossValueSet, then throw an exception
      if (otherValue == null) {
        throw new IllegalArgumentException("GbossValue Set " + valueSet.getDescription() + " (cui = " + valueSet.getCui() + " does not have an other concept");
      }
    }
    
    return otherValue;
  }

  /**
   * Return the GbossValue within the specified GbossValueSet that represents the "other" value.
   */
  public static GbossValue getOtherValueValue(GbossValueSet valueSet) {
    return getOtherValueValue(valueSet.getCui());
  }

  /**
   * Returns the GbossValue that represents the no value for the GbossValueSet with
   * the specified cui.
   * 
   * @param  cui  the cui of the desired GbossValueSet
   * @return a GbossValue representing the no value, or null if the specified value set does not have a no value.
   */
  public static synchronized GbossValue getNoValueValue(String valueSetCui) {

    // Get the no value GbossValue from the cache.
    GbossValue noValue = (GbossValue)_noValues.get(valueSetCui);    

    // If the no value GbossValue is not cached, then iterate over the GbossValueSet to find it and
    // cache it for next time.
    if (noValue == null) {
      GbossValueSet valueSet = getValueSet(valueSetCui);    
      if (valueSet == null) {
        throw new IllegalArgumentException("No GbossValueSet with cui = " + valueSetCui + " exists.");
      }
      Iterator values = valueSet.depthFirstIterator();
      while (values.hasNext()) {
        GbossValue value = (GbossValue)values.next();
        if (value.isNoValue()) {

          // If we already found a no value GbossValue within this GbossValueSet, then throw an exception since
          // there should only be one.
          if (noValue != null) {
            throw new IllegalArgumentException("GbossValue Set " + valueSet.getDescription() + " (cui = " + valueSet.getCui() + ") has more than one no value");
          }
          noValue = value;
          _noValues.put(valueSetCui, noValue);
        }
      }
    }
    
    return noValue;
  }

  /**
   * Return the GbossValue within the specified GbossValueSet that represents the "no value" value.
   */
  public static GbossValue getNoValueValue(GbossValueSet valueSet) {
    return getNoValueValue(valueSet.getCui());
  }
  
  private static String getTopLevelDiagnosisCui(String cui) {
    String returnValue = null;
    GbossValueSet diagnosisHierarchy = getValueSet(ArtsConstants.VALUE_SET_DIAGNOSIS_HIERARCHY);
    //get the value corresponding to the code passed in
    GbossValue value = diagnosisHierarchy.getValue(cui);
    if (value != null) {
      //now walk the hierarchy until we get to the topmost value
      while (value.getParentValue() != null) {
        value = value.getParentValue();
      }
      returnValue = value.getCui();
    }
    return returnValue;
  }

  /* Method to determine if the top level diagnosis of a given diagnosis is Neoplastic. */
  public static boolean isTopLevelDiagnosisNeoplastic(String cui) {
    String topLevelCui = getTopLevelDiagnosisCui(cui);
    return (ArtsConstants.DIAGNOSIS_NEOPLASTIC.equalsIgnoreCase(topLevelCui));
  }

  /* Method to determine if the top level diagnosis of a given diagnosis is Non-Neoplastic. */
  public static boolean isTopLevelDiagnosisNonNeoplastic(String cui) {
    String topLevelCui = getTopLevelDiagnosisCui(cui);
    return (ArtsConstants.DIAGNOSIS_NON_NEOPLASTIC.equalsIgnoreCase(topLevelCui));
  }

  /* Method to determine if the top level diagnosis of a given diagnosis is Other. */
  public static boolean isTopLevelDiagnosisOther(String cui) {
    String topLevelCui = getTopLevelDiagnosisCui(cui);
    return (ArtsConstants.DIAGNOSIS_OTHER.equalsIgnoreCase(topLevelCui));
  }

  /* Method to determine if the top level diagnosis of a given diagnosis is No Abnormality. */
  public static boolean isTopLevelDiagnosisNoAbnormality(String cui) {
    String topLevelCui = getTopLevelDiagnosisCui(cui);
    return (ArtsConstants.DIAGNOSIS_NO_ABNORMALITY.equalsIgnoreCase(topLevelCui));
  }
  
  public static String getDiagnosisDescription(String cui) {
    return getDescription(ArtsConstants.VALUE_SET_DIAGNOSIS_HIERARCHY, cui);
  }
  
  public static String getProcedureDescription(String cui) {
    return getDescription(ArtsConstants.VALUE_SET_PROCEDURE_HIERARCHY, cui);
  }

  public static String getTissueDescription(String cui) {
    return getDescription(ArtsConstants.VALUE_SET_TISSUE_HIERARCHY, cui);
  }
  
  public static GbossValueSet getReceivedDiagnosisValueSet() {
    if (_receivedDiagnosisValueSet == null) {
      //create a copy of the diagnosis hierarchy value set, without others
      GbossValueSet diagnosisValueSet = getValueSetWithoutOthers(ArtsConstants.VALUE_SET_DIAGNOSIS_HIERARCHY);
      _receivedDiagnosisValueSet = new GbossValueSet(diagnosisValueSet);      
      //now remove all diagnoses that have not been received
      Set cuisToRemove = getAllLeafNodeCuis(_receivedDiagnosisValueSet);
      cuisToRemove.removeAll(getReceivedDiagnosis());
      _receivedDiagnosisValueSet.removeValues(cuisToRemove);
      //finally, mark the value set immutable
      _receivedDiagnosisValueSet.setImmutable();
    }
    return _receivedDiagnosisValueSet;
  }
  
  public static GbossValueSet getReceivedTissueValueSet() {
    if (_receivedTissueValueSet == null) {
      //create a copy of the tissue hierarchy value set, without others
      GbossValueSet tissueValueSet = getValueSetWithoutOthers(ArtsConstants.VALUE_SET_TISSUE_HIERARCHY);
      _receivedTissueValueSet = new GbossValueSet(tissueValueSet);
      //now remove all tissues that have not been received
      Set cuisToRemove = getAllLeafNodeCuis(_receivedTissueValueSet);
      cuisToRemove.removeAll(getReceivedTissue());
      _receivedTissueValueSet.removeValues(cuisToRemove);
      //finally, mark the value set immutable
      _receivedTissueValueSet.setImmutable();
    }
    return _receivedTissueValueSet;
  }
  
  /*
   * Find the cuis of all leaf nodes
   */
  private static Set getAllLeafNodeCuis(GbossValueSet valueSet) {
    Set returnValue = new HashSet();
    Iterator iterator = valueSet.depthFirstIterator();
    while (iterator.hasNext()) {
      GbossValue value = (GbossValue) iterator.next();
      if (ApiFunctions.isEmpty(value.getValues())) {
        returnValue.add(value.getCui());
      }
    }
    return returnValue;
  }

  private static String getDescription(String valueSetName, String cui) {
    String description = null;
    if (!ApiFunctions.isEmpty(cui) && !ApiFunctions.isEmpty(valueSetName)) {
      GbossValueSet valueSet = getValueSet(valueSetName);
      //get the value corresponding to the code passed in
      GbossValue value = valueSet.getValue(cui);
      if (value != null) {
        description = value.getDescription();
      }
      else {
        throw new ApiException("Unknown cui (" + cui + ") encountered.");
      }
    }
    return description;
  }
  
  private static Set getReceivedDiagnosis() throws ApiException {
    StringBuffer queryBuf = new StringBuffer();
    queryBuf.append(
      "select disease_concept_id as owner_code from iltds_informed_consent where disease_concept_id is not null\n");
    queryBuf.append(" UNION\n");
    queryBuf.append(
      " (select diagnosis_concept_id as owner_code from lims_pathology_evaluation where reported_yn = 'Y' and diagnosis_concept_id is not null\n");
    queryBuf.append(" UNION\n");
    queryBuf.append(
      " (select diagnosis_concept_id as owner_code from pdc_pathology_report where diagnosis_concept_id is not null))\n");

    Set result = new HashSet();
    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
      con = ApiFunctions.getDbConnection();
      pstmt = con.prepareStatement(queryBuf.toString());
      rs = pstmt.executeQuery();
      while (rs.next()) {
        result.add(rs.getString("OWNER_CODE"));
      }
    }
    catch (SQLException e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(con, pstmt, rs);
    }
    return result;
  }
  
  private static Set getReceivedTissue() throws ApiException {
    StringBuffer queryBuf = new StringBuffer();
    queryBuf.append(
      "select organ_site_concept_id as owner_code from iltds_asm where organ_site_concept_id is not null\n");
    queryBuf.append("UNION\n");
    queryBuf.append(
      "select tissue_origin_concept_id as owner_code from lims_pathology_evaluation where reported_yn = 'Y' and tissue_origin_concept_id  is not null\n");
    queryBuf.append("UNION\n");
    queryBuf.append(
      "select tissue_finding_concept_id as owner_code from lims_pathology_evaluation where reported_yn = 'Y' and tissue_finding_concept_id  is not null\n");
    queryBuf.append("UNION\n");
    queryBuf.append(
      "select tissue_origin_concept_id as owner_code from pdc_path_report_section where tissue_origin_concept_id is not null and PRIMARY_IND = 'Y'\n");
    queryBuf.append("UNION\n");
    queryBuf.append(
      "select tissue_finding_concept_id as owner_code from pdc_path_report_section where tissue_finding_concept_id is not null and PRIMARY_IND = 'Y'\n");

    Set result = new HashSet();
    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
      con = ApiFunctions.getDbConnection();
      pstmt = con.prepareStatement(queryBuf.toString());
      rs = ApiFunctions.queryDb(pstmt, con);
      while (rs.next()) {
        result.add(rs.getString("OWNER_CODE"));
      }
    }
    catch (SQLException e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(con, pstmt, rs);
    }
    return result;
  }

}
