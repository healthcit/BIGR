package com.gulfstreambio.kc.detviewer;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;

import com.ardais.bigr.api.ApiFunctions;
import com.gulfstreambio.kc.det.DataElementTaxonomy;
import com.gulfstreambio.kc.det.DetAde;
import com.gulfstreambio.kc.det.DetAdeElement;
import com.gulfstreambio.kc.det.DetCategory;
import com.gulfstreambio.kc.det.DetConcept;
import com.gulfstreambio.kc.det.DetDataElement;
import com.gulfstreambio.kc.det.DetElement;
import com.gulfstreambio.kc.det.DetElementDatatypes;
import com.gulfstreambio.kc.det.DetService;
import com.gulfstreambio.kc.det.DetUnit;
import com.gulfstreambio.kc.det.DetValue;
import com.gulfstreambio.kc.det.DetValueSet;
import com.gulfstreambio.kc.det.DetValueSetValue;

public class DetViewerQuery {

  public final static String PARAM_CUISYS = "cuisys";   // cui or system name
  public final static String PARAM_CUI = "cui";         // cui
  public final static String PARAM_SYS = "sys";         // system name
  public final static String PARAM_DESC = "desc";       // description
  public final static String PARAM_CTYPE = "ctype";     // concept type
  public final static String PARAM_DT = "dt";           // datatype
  public final static String PARAM_MULTI = "multi";     // multivalued?
  public final static String PARAM_EAV = "eav";         // eav?
  public final static String PARAM_OCE = "oce";         // oce-enabled?
  public final static String PARAM_MLVS = "mlvs";       // multi-level value set?
  public final static String PARAM_NONATV = "nonatv";   // non atv value set?
  public final static String PARAM_NOVAL = "noval";     // no val?
  public final static String PARAM_ADE = "ade";         // has ade?
  public final static String PARAM_UNIT = "unit";       // has unit?
  public final static String PARAM_MIN = "min";         // has min?
  public final static String PARAM_MAX = "max";         // has max?

  private final SortedSet EMPTY_TREESET = new TreeSet();
  
  private final static String TYPE_ADE = "ade";
  private final static String TYPE_ADE_ELEMENT = "adee";
  private final static String TYPE_CATEGORY = "cat";
  private final static String TYPE_DATA_ELEMENT = "de";
  private final static String TYPE_UNIT = "unit";
  private final static String TYPE_VALUE_SET = "valueset";
  private final static String TYPE_VALUE = "value";
  private final static Set _types = new HashSet();
  static {
    _types.add(TYPE_ADE);
    _types.add(TYPE_ADE_ELEMENT);
    _types.add(TYPE_CATEGORY);
    _types.add(TYPE_DATA_ELEMENT);
    _types.add(TYPE_UNIT);
    _types.add(TYPE_VALUE_SET);
    _types.add(TYPE_VALUE);
  }

  private static Map _conceptsByType;
  private static Map _elementsByDatatype;
  private static Map _elementsByIsMultivalued;
  private static Map _elementsByIsSinglevalued;
  private static Map _elementsByHasUnit;
  private static Map _elementsByNotHasUnit;
  private static Map _elementsByHasOtherValue;
  private static Map _elementsByNotHasOtherValue;
  private static Map _elementsByHasMin;
  private static Map _elementsByNotHasMin;
  private static Map _elementsByHasMax;
  private static Map _elementsByNotHasMax;
  private static Map _elementsByHasMlvs;
  private static Map _elementsByNotHasMlvs;
  private static Map _elementsByHasNoVal;
  private static Map _elementsByNotHasNoVal;
  private static Map _elementsByIsEav;
  private static Map _elementsByIsConventional;
  private static SortedSet _dataElementsByHasAde;
  private static SortedSet _dataElementsByNotHasAde;
  private static SortedSet _dataElementsByHasNonAtv;
  private static SortedSet _dataElementsByNotHasNonAtv;

  
  /**
   * A Map of all concepts by their description.  The keys are lower-case descriptions, and the 
   * values are Maps.  Each Map maps concept types (one of the TYPE_* constants in this class)
   * to SortedSets of instances of the corresponding metadata object (e.g. DetDataElement), 
   * sorted by the metadata object descriptions. 
   */
  private static Map _conceptsByDescription;

  /**
   * A Map of all data elements by ADE.  The keys are ADE CUIs, and the values are SortedSets of
   * DetDataElement instances, sorted by the DetDataElement descriptions. 
   */
  private static Map _dataElementsByAde;

  /**
   * A Map of all data elements by unit.  The keys are unit CUIs, and the values are SortedSets of
   * DetDataElement instances, sorted by the DetDataElement descriptions.
   */
  private static Map _dataElementsByUnit;

  /**
  * A Map of all ADE elements by unit.  The keys are unit CUIs, and the values are SortedSets of
  * DetAdeElement instances, sorted by the DetAdeElement descriptions.
  */
  private static Map _adeElementsByUnit;
  
  /**
   * A Map of all data elements by value set.  The keys are value set CUIs, and the values are 
   * SortedSets of DetDataElement instances, sorted by the DetDataElement descriptions.
   */
  private static Map _dataElementsByValueSet;

  /**
  * A Map of all ADE elements by value set.  The keys are value set CUIs, and the values are 
  * SortedSets of DetAdeElement instances, sorted by the DetAdeElement descriptions.
  */
  private static Map _adeElementsByValueSet;

  /**
   * A Map of all data elements by value.  The keys are value CUIs, and the values are SortedSets
   * of DetDataElement instances, sorted by the DetDataElement descriptions.
   */
  private static Map _dataElementsByValue;

  /**
  * A Map of all ADE elements by value.  The keys are value CUIs, and the values are SortedSets of
  * DetAdeElement instances, sorted by the DetAdeElement descriptions.
  */
  private static Map _adeElementsByValue;

  /**
   * A Map of all value sets by value.  The keys are value CUIs, and the values are SortedSets
   * of DetValueSet instances, sorted by the DetValueSet descriptions.
   */
  private static Map _valueSetsByValue;

  private String _cuisys;      // cui or system name
  private String _cui;         // cui
  private String _sys;         // system name
  private String _desc;        // description
  private String _ctype;       // concept type
  private String _dt;          // datatype
  private String _multi;       // multivalued?
  private String _eav;         // eav?
  private String _oce;         // oce-enabled?
  private String _mlvs;        // multi-level value set?
  private String _nonatv;      // non atv value set?
  private String _noval;       // no val?
  private String _ade;         // has ade?
  private String _unit;        // has unit?
  private String _min;         // has min?
  private String _max;         // has max?
  
  private static Comparator _descriptionComparator = new Comparator() {
    public int compare(Object o1, Object o2) {
      if ((o1 instanceof DetConcept) && (o2 instanceof DetConcept)) {
        String s1 = ((DetConcept) o1).getDescription();
        String s2 = ((DetConcept) o2).getDescription();
        return s1.compareTo(s2);
      }
      return 0;
    }      
  };

  static {
    loadCache();
  }

  public DetViewerQuery() {
    super();
  }

  public DetViewerQuery(HttpServletRequest request) {
    this();
    String p = request.getParameter(DetViewerQuery.PARAM_CUISYS);
    if (!ApiFunctions.isEmpty(p)) {
      setCuisys(p);
    }
    p = request.getParameter(DetViewerQuery.PARAM_CUI);
    if (!ApiFunctions.isEmpty(p)) {
      setCui(p);
    }
    p = request.getParameter(DetViewerQuery.PARAM_DESC);
    if (!ApiFunctions.isEmpty(p)) {
      setDesc(p);
    }
    p = request.getParameter(DetViewerQuery.PARAM_DT);
    if (!ApiFunctions.isEmpty(p)) {
      setDt(p);
    }
    p = request.getParameter(DetViewerQuery.PARAM_EAV);
    if (!ApiFunctions.isEmpty(p)) {
      setEav(p);
    }
    p = request.getParameter(DetViewerQuery.PARAM_MLVS);
    if (!ApiFunctions.isEmpty(p)) {
      setMlvs(p);
    }
    p = request.getParameter(DetViewerQuery.PARAM_MULTI);
    if (!ApiFunctions.isEmpty(p)) {
      setMulti(p);
    }
    p = request.getParameter(DetViewerQuery.PARAM_NONATV);
    if (!ApiFunctions.isEmpty(p)) {
      setNonatv(p);
    }
    p = request.getParameter(DetViewerQuery.PARAM_NOVAL);
    if (!ApiFunctions.isEmpty(p)) {
      setNoval(p);
    }
    p = request.getParameter(DetViewerQuery.PARAM_OCE);
    if (!ApiFunctions.isEmpty(p)) {
      setOce(p);
    }
    p = request.getParameter(DetViewerQuery.PARAM_SYS);
    if (!ApiFunctions.isEmpty(p)) {
      setSys(p);
    }
    p = request.getParameter(DetViewerQuery.PARAM_CTYPE);
    if (!ApiFunctions.isEmpty(p)) {
      setCtype(p);
    }
    p = request.getParameter(DetViewerQuery.PARAM_ADE);
    if (!ApiFunctions.isEmpty(p)) {
      setAde(p);
    }
    p = request.getParameter(DetViewerQuery.PARAM_UNIT);
    if (!ApiFunctions.isEmpty(p)) {
      setUnit(p);
    }
    p = request.getParameter(DetViewerQuery.PARAM_MIN);
    if (!ApiFunctions.isEmpty(p)) {
      setMin(p);
    }
    p = request.getParameter(DetViewerQuery.PARAM_MAX);
    if (!ApiFunctions.isEmpty(p)) {
      setMax(p);
    }
  }
  
  public String getCui() {
    return _cui;
  }
  public void setCui(String cui) {
    _cui = cui;
  }
  
  public String getDesc() {
    return _desc;
  }
  public void setDesc(String desc) {
    _desc = desc;
  }
  
  public String getDt() {
    return _dt;
  }
  public void setDt(String dt) {
    _dt = dt;
  }
  
  public String getEav() {
    return _eav;
  }
  public void setEav(String eav) {
    _eav = eav;
  }
  
  public String getMlvs() {
    return _mlvs;
  }
  public void setMlvs(String mlvs) {
    _mlvs = mlvs;
  }
  
  public String getMulti() {
    return _multi;
  }
  public void setMulti(String multi) {
    _multi = multi;
  }
  
  public String getNonatv() {
    return _nonatv;
  }
  public void setNonatv(String nonatv) {
    _nonatv = nonatv;
  }
  
  public String getNoval() {
    return _noval;
  }
  public void setNoval(String noval) {
    _noval = noval;
  }
  
  public String getOce() {
    return _oce;
  }
  public void setOce(String oce) {
    _oce = oce;
  }
  
  public String getSys() {
    return _sys;
  }
  public void setSys(String sys) {
    _sys = sys;
  }
  
  public String getCtype() {
    return _ctype;
  }
  public void setCtype(String ctype) {
    _ctype = ctype;
  }
  
  public String getAde() {
    return _ade;
  }
  public void setAde(String ade) {
    _ade = ade;
  }
  
  
  public String getMax() {
    return _max;
  }
  public void setMax(String max) {
    _max = max;
  }

  public String getMin() {
    return _min;
  }
  public void setMin(String min) {
    _min = min;
  }
  
  public String getUnit() {
    return _unit;
  }
  public void setUnit(String unit) {
    _unit = unit;
  }
  
  public String getCuisys() {
    return _cuisys;
  }
  public void setCuisys(String cuisys) {
    _cuisys = cuisys;
  }
  
  public DetViewerQueryResults find() {
    DetViewerQueryResults results = null;
    if (!ApiFunctions.isEmpty(getCui())) {
      results = findByCuiOrSystemName(getCui());
    }
    else if (!ApiFunctions.isEmpty(getSys())) {
      results = findByCuiOrSystemName(getSys());
    }
    else if (!ApiFunctions.isEmpty(getCuisys())) {
      results = findByCuiOrSystemName(getCuisys());
    }
    else if (!ApiFunctions.isEmpty(getDesc())) {
      results = findByDescription(getDesc());      
    }
    else {
      results = findByQueryParameters();
    }
    
    findAssociatedForResults(results);
    return results;
  }
  
  private DetViewerQueryResults findByCuiOrSystemName(String cuiOrSystemName) {
    DataElementTaxonomy det = DetService.SINGLETON.getDataElementTaxonomy();
    DetViewerQueryResults results = new DetViewerQueryResults();

    DetAde ade = det.getAde(cuiOrSystemName);
    if (ade != null) {
      results.addAde(ade);
    }

    DetAdeElement adeElement = det.getAdeElement(cuiOrSystemName);
    if (adeElement != null) {
      results.addAdeElement(adeElement);
    }

    DetCategory category = det.getCategory(cuiOrSystemName);
    if (category != null) {
      results.addCategory(category);
    }

    DetDataElement dataElement = det.getDataElement(cuiOrSystemName);
    if (dataElement != null) {
      results.addDataElement(dataElement);
    }

    DetUnit unit = det.getUnit(cuiOrSystemName);
    if (unit != null) {
      results.addUnit(unit);
    }

    DetValueSet valueSet = det.getValueSet(cuiOrSystemName);
    if (valueSet != null) {
      results.addValueSet(valueSet);      
    }

    DetValue value = det.getValue(cuiOrSystemName);
    if (value != null) {
      results.addValue(value);      
    }
    
    return results;    
  }

  private DetViewerQueryResults findByDescription(String description) {
    DetViewerQueryResults results = new DetViewerQueryResults();
    
    Map byConceptType = (Map) _conceptsByDescription.get(description.trim().toLowerCase());
    if (byConceptType != null) {
      Iterator conceptTypes = byConceptType.keySet().iterator();
      while (conceptTypes.hasNext()) {
        String conceptType = (String) conceptTypes.next();
        SortedSet concepts = (SortedSet) byConceptType.get(conceptType);
        Iterator i = concepts.iterator();
        while (i.hasNext()) {
          if (conceptType.equals(TYPE_ADE)) {
            results.addAde((DetAde) i.next());
          }
          else if (conceptType.equals(TYPE_ADE_ELEMENT)) {
            results.addAdeElement((DetAdeElement) i.next());
          }
          else if (conceptType.equals(TYPE_CATEGORY)) {
            results.addCategory((DetCategory) i.next());            
          }
          else if (conceptType.equals(TYPE_DATA_ELEMENT)) {
            results.addDataElement((DetDataElement) i.next());
          }
          else if (conceptType.equals(TYPE_UNIT)) {
            results.addUnit((DetUnit) i.next());
          }
          else if (conceptType.equals(TYPE_VALUE_SET)) {
            results.addValueSet((DetValueSet) i.next());
          }
          else if (conceptType.equals(TYPE_VALUE)) {
            results.addValue((DetValue) i.next());
          }
        }
      }
    }
    
    return results;
  }
  
  private DetViewerQueryResults findByQueryParameters() {
    DetViewerQueryResults results = new DetViewerQueryResults();
    
    // A map that is keyed by concept type, and contains a list of sorted sets to be merged
    // that hold the requested concepts.
    Map resultsByType = new HashMap();
    resultsByType.put(TYPE_ADE, new ArrayList());
    resultsByType.put(TYPE_ADE_ELEMENT, new ArrayList());
    resultsByType.put(TYPE_CATEGORY, new ArrayList());
    resultsByType.put(TYPE_DATA_ELEMENT, new ArrayList());
    resultsByType.put(TYPE_UNIT, new ArrayList());
    resultsByType.put(TYPE_VALUE_SET, new ArrayList());
    resultsByType.put(TYPE_VALUE, new ArrayList());

    // If concept type was specified, then add the sorted set for the concept type.  Make sure to
    // also add empty sets for all other concept types, which will effectively ensure that such
    // concepts are not returned when the sets for each concept type are merged below. 
    String ctype = getCtype();
    if (!ApiFunctions.isEmpty(ctype)) {
      ((List)resultsByType.get(ctype)).add(_conceptsByType.get(ctype));
      Iterator i = _types.iterator();
      while (i.hasNext()) {
        String type = (String) i.next();
        if (!ctype.equals(type)) {
          ((List)resultsByType.get(type)).add(EMPTY_TREESET);          
        }
      }
    }
    
    // If datatype was specified, then add the sorted set for the datatype.
    String dt = getDt();
    if (!ApiFunctions.isEmpty(dt)) {
      Map byConceptType = (Map) _elementsByDatatype.get(dt);
      Iterator i = byConceptType.keySet().iterator();
      while (i.hasNext()) {
        ctype = (String) i.next();
        ((List)resultsByType.get(ctype)).add(byConceptType.get(ctype));        
      }
    }
    
    // If ADE was specified, then add the appropriate sorted set.
    String ade = getAde();
    if ("y".equals(ade)) {
      ((List)resultsByType.get(TYPE_DATA_ELEMENT)).add(_dataElementsByHasAde);        
    }
    else if ("n".equals(ade)) {
      ((List)resultsByType.get(TYPE_DATA_ELEMENT)).add(_dataElementsByNotHasAde);        
    }

    // If non-ATV was specified, then add the appropriate sorted set.
    String nonAtv = getNonatv();
    if ("y".equals(nonAtv)) {
      ((List)resultsByType.get(TYPE_DATA_ELEMENT)).add(_dataElementsByHasNonAtv);        
    }
    else if ("n".equals(nonAtv)) {
      ((List)resultsByType.get(TYPE_DATA_ELEMENT)).add(_dataElementsByNotHasNonAtv);        
    }
    
    // Add the sorted sets for all other boolean parameters that were specified.
    addSortedSetForBoolean(getMulti(), resultsByType, _elementsByIsMultivalued, _elementsByIsSinglevalued);
    addSortedSetForBoolean(getUnit(), resultsByType, _elementsByHasUnit, _elementsByNotHasUnit);
    addSortedSetForBoolean(getOce(), resultsByType, _elementsByHasOtherValue, _elementsByNotHasOtherValue);
    addSortedSetForBoolean(getMin(), resultsByType, _elementsByHasMin, _elementsByNotHasMin);
    addSortedSetForBoolean(getMax(), resultsByType, _elementsByHasMax, _elementsByNotHasMax);
    addSortedSetForBoolean(getMlvs(), resultsByType, _elementsByHasMlvs, _elementsByNotHasMlvs);
    addSortedSetForBoolean(getNoval(), resultsByType, _elementsByHasNoVal, _elementsByNotHasNoVal);
    addSortedSetForBoolean(getEav(), resultsByType, _elementsByIsEav, _elementsByIsConventional);

    // Now merge all of the sorted sets to keep the concepts that are in their intersection.
    SortedSet ades = mergeSortedSets((List) resultsByType.get(TYPE_ADE));
    SortedSet adeElements = mergeSortedSets((List) resultsByType.get(TYPE_ADE_ELEMENT));
    SortedSet categories = mergeSortedSets((List) resultsByType.get(TYPE_CATEGORY));
    SortedSet dataElements = mergeSortedSets((List) resultsByType.get(TYPE_DATA_ELEMENT));
    SortedSet units = mergeSortedSets((List) resultsByType.get(TYPE_UNIT));
    SortedSet valueSets = mergeSortedSets((List) resultsByType.get(TYPE_VALUE_SET));
    SortedSet values = mergeSortedSets((List) resultsByType.get(TYPE_VALUE));

    // Now add the merged sets to the results.
    Iterator i = ades.iterator();
    while (i.hasNext()) {
      results.addAde((DetAde) i.next());      
    }
    i = adeElements.iterator();
    while (i.hasNext()) {
      results.addAdeElement((DetAdeElement) i.next());      
    }
    i = categories.iterator();
    while (i.hasNext()) {
      results.addCategory((DetCategory) i.next());      
    }
    i = dataElements.iterator();
    while (i.hasNext()) {
      results.addDataElement((DetDataElement) i.next());      
    }
    i = units.iterator();
    while (i.hasNext()) {
      results.addUnit((DetUnit) i.next());      
    }
    i = valueSets.iterator();
    while (i.hasNext()) {
      results.addValueSet((DetValueSet) i.next());      
    }
    i = values.iterator();
    while (i.hasNext()) {
      results.addValue((DetValue) i.next());      
    }
    
    return results;        
  }

  private void addSortedSetForBoolean(String param, Map resultsByType, Map yes, Map no) {
    Map toUse = null;
    if ("y".equals(param)) {
      toUse = yes;
    }
    else if ("n".equals(param)) {
      toUse = no;
    }

    if (toUse != null) {
      Iterator i = toUse.keySet().iterator();
      while (i.hasNext()) {
        String ctype = (String) i.next();
        ((List)resultsByType.get(ctype)).add(toUse.get(ctype));        
      }
    }
  }

  private SortedSet mergeSortedSets(List sortedSets) {
    SortedSet merged = new TreeSet(_descriptionComparator);
    for (int i = 0; i < sortedSets.size(); i++) {      
      SortedSet set = (SortedSet) sortedSets.get(i);
      if (i == 0) {
        merged.addAll(set);
      }
      else {
        merged.retainAll(set);
      }
    }
    return merged;
  }

  private void findAssociatedForResults(DetViewerQueryResults results) {
    if (results.getResultsCount() != 1) {
      return;
    }

    DetAde[] ades = results.getAdes();
    if (ades.length == 1) {
      findAssociatedDataElementsForAde(results, ades[0].getCui());
    }

    DetUnit[] units = results.getUnits();
    if (units.length > 0) {
      findAssociatedDataElementsForUnit(results, units[0].getCui());
      findAssociatedAdeElementsForUnit(results, units[0].getCui());
    }    

    DetValueSet[] valueSets = results.getValueSets();
    if (valueSets.length > 0) {
      findAssociatedDataElementsForValueSet(results, valueSets[0].getCui());
      findAssociatedAdeElementsForValueSet(results, valueSets[0].getCui());
    }    

    DetValue[] values = results.getValues();
    if (values.length > 0) {
      findAssociatedDataElementsForValue(results, values[0].getCui());
      findAssociatedAdeElementsForValue(results, values[0].getCui());
      findAssociatedValueSetsForValue(results, values[0].getCui());
    }    
  }

  private void findAssociatedDataElementsForAde(DetViewerQueryResults results, String cui) {
    SortedSet dataElements = (SortedSet )_dataElementsByAde.get(cui);
    if (dataElements != null) {
      Iterator i = dataElements.iterator();
      while (i.hasNext()) {
        results.addAssociatedDataElement((DetDataElement)i.next());        
      }
    }
  }

  private void findAssociatedDataElementsForUnit(DetViewerQueryResults results, String cui) {
    SortedSet dataElements = (SortedSet)_dataElementsByUnit.get(cui);
    if (dataElements != null) {
      Iterator i = dataElements.iterator();
      while (i.hasNext()) {
        results.addAssociatedDataElement((DetDataElement)i.next());        
      }
    }
  }

  private void findAssociatedAdeElementsForUnit(DetViewerQueryResults results, String cui) {
    SortedSet adeElements = (SortedSet)_adeElementsByUnit.get(cui);
    if (adeElements != null) {
      Iterator i = adeElements.iterator();
      while (i.hasNext()) {
        results.addAssociatedAdeElement((DetAdeElement)i.next());        
      }
    }
  }
  
  private void findAssociatedDataElementsForValueSet(DetViewerQueryResults results, String cui) {
    SortedSet dataElements = (SortedSet)_dataElementsByValueSet.get(cui);
    if (dataElements != null) {
      Iterator i = dataElements.iterator();
      while (i.hasNext()) {
        results.addAssociatedDataElement((DetDataElement)i.next());        
      }
    }
  }

  private void findAssociatedAdeElementsForValueSet(DetViewerQueryResults results, String cui) {
    SortedSet adeElements = (SortedSet)_adeElementsByValueSet.get(cui);
    if (adeElements != null) {
      Iterator i = adeElements.iterator();
      while (i.hasNext()) {
        results.addAssociatedAdeElement((DetAdeElement)i.next());        
      }
    }
  }
  
  private void findAssociatedDataElementsForValue(DetViewerQueryResults results, String cui) {
    SortedSet dataElements = (SortedSet)_dataElementsByValue.get(cui);
    if (dataElements != null) {
      Iterator i = dataElements.iterator();
      while (i.hasNext()) {
        results.addAssociatedDataElement((DetDataElement)i.next());        
      }
    }
  }

  private void findAssociatedAdeElementsForValue(DetViewerQueryResults results, String cui) {
    SortedSet adeElements = (SortedSet)_adeElementsByValue.get(cui);
    if (adeElements != null) {
      Iterator i = adeElements.iterator();
      while (i.hasNext()) {
        results.addAssociatedAdeElement((DetAdeElement)i.next());        
      }
    }
  }

  private void findAssociatedValueSetsForValue(DetViewerQueryResults results, String cui) {
    SortedSet valueSets = (SortedSet)_valueSetsByValue.get(cui);
    if (valueSets != null) {
      Iterator i = valueSets.iterator();
      while (i.hasNext()) {
        results.addAssociatedValueSet((DetValueSet)i.next()); 
      }
    }
  }
  
  private static void loadCache() {
    if (_conceptsByType == null) {
      _conceptsByType = new HashMap();
      _conceptsByType.put(TYPE_ADE, new TreeSet(_descriptionComparator));
      _conceptsByType.put(TYPE_ADE_ELEMENT, new TreeSet(_descriptionComparator));
      _conceptsByType.put(TYPE_CATEGORY, new TreeSet(_descriptionComparator));
      _conceptsByType.put(TYPE_DATA_ELEMENT, new TreeSet(_descriptionComparator));
      _conceptsByType.put(TYPE_UNIT, new TreeSet(_descriptionComparator));
      _conceptsByType.put(TYPE_VALUE_SET, new TreeSet(_descriptionComparator));
      
      _elementsByDatatype = new HashMap();
      Map byConceptType = new HashMap();
      byConceptType.put(TYPE_ADE_ELEMENT, new TreeSet(_descriptionComparator));
      byConceptType.put(TYPE_DATA_ELEMENT, new TreeSet(_descriptionComparator));
      _elementsByDatatype.put(DetElementDatatypes.CV, byConceptType);
      
      byConceptType = new HashMap();
      byConceptType.put(TYPE_ADE_ELEMENT, new TreeSet(_descriptionComparator));
      byConceptType.put(TYPE_DATA_ELEMENT, new TreeSet(_descriptionComparator));
      _elementsByDatatype.put(DetElementDatatypes.DATE, byConceptType);
      
      byConceptType = new HashMap();
      byConceptType.put(TYPE_ADE_ELEMENT, new TreeSet(_descriptionComparator));
      byConceptType.put(TYPE_DATA_ELEMENT, new TreeSet(_descriptionComparator));
      _elementsByDatatype.put(DetElementDatatypes.FLOAT, byConceptType);
      
      byConceptType = new HashMap();
      byConceptType.put(TYPE_ADE_ELEMENT, new TreeSet(_descriptionComparator));
      byConceptType.put(TYPE_DATA_ELEMENT, new TreeSet(_descriptionComparator));
      _elementsByDatatype.put(DetElementDatatypes.INT, byConceptType);
      
      byConceptType = new HashMap();
      byConceptType.put(TYPE_ADE_ELEMENT, new TreeSet(_descriptionComparator));
      byConceptType.put(TYPE_DATA_ELEMENT, new TreeSet(_descriptionComparator));
      _elementsByDatatype.put(DetElementDatatypes.VPD, byConceptType);
      
      byConceptType = new HashMap();
      byConceptType.put(TYPE_ADE_ELEMENT, new TreeSet(_descriptionComparator));
      byConceptType.put(TYPE_DATA_ELEMENT, new TreeSet(_descriptionComparator));
      _elementsByDatatype.put(DetElementDatatypes.REPORT, byConceptType);
      
      byConceptType = new HashMap();
      byConceptType.put(TYPE_ADE_ELEMENT, new TreeSet(_descriptionComparator));
      byConceptType.put(TYPE_DATA_ELEMENT, new TreeSet(_descriptionComparator));
      _elementsByDatatype.put(DetElementDatatypes.TEXT, byConceptType);

      _elementsByIsMultivalued = new HashMap();
      _elementsByIsMultivalued.put(TYPE_ADE_ELEMENT, new TreeSet(_descriptionComparator));
      _elementsByIsMultivalued.put(TYPE_DATA_ELEMENT, new TreeSet(_descriptionComparator));
      
      _elementsByIsSinglevalued = new HashMap();
      _elementsByIsSinglevalued.put(TYPE_ADE_ELEMENT, new TreeSet(_descriptionComparator));
      _elementsByIsSinglevalued.put(TYPE_DATA_ELEMENT, new TreeSet(_descriptionComparator));

      _elementsByHasUnit = new HashMap();
      _elementsByHasUnit.put(TYPE_ADE_ELEMENT, new TreeSet(_descriptionComparator));
      _elementsByHasUnit.put(TYPE_DATA_ELEMENT, new TreeSet(_descriptionComparator));

      _elementsByNotHasUnit = new HashMap();
      _elementsByNotHasUnit.put(TYPE_ADE_ELEMENT, new TreeSet(_descriptionComparator));
      _elementsByNotHasUnit.put(TYPE_DATA_ELEMENT, new TreeSet(_descriptionComparator));

      _elementsByHasOtherValue = new HashMap();
      _elementsByHasOtherValue.put(TYPE_ADE_ELEMENT, new TreeSet(_descriptionComparator));
      _elementsByHasOtherValue.put(TYPE_DATA_ELEMENT, new TreeSet(_descriptionComparator));

      _elementsByNotHasOtherValue = new HashMap();
      _elementsByNotHasOtherValue.put(TYPE_ADE_ELEMENT, new TreeSet(_descriptionComparator));
      _elementsByNotHasOtherValue.put(TYPE_DATA_ELEMENT, new TreeSet(_descriptionComparator));

      _elementsByHasMin = new HashMap();
      _elementsByHasMin.put(TYPE_ADE_ELEMENT, new TreeSet(_descriptionComparator));
      _elementsByHasMin.put(TYPE_DATA_ELEMENT, new TreeSet(_descriptionComparator));

      _elementsByNotHasMin = new HashMap();
      _elementsByNotHasMin.put(TYPE_ADE_ELEMENT, new TreeSet(_descriptionComparator));
      _elementsByNotHasMin.put(TYPE_DATA_ELEMENT, new TreeSet(_descriptionComparator));

      _elementsByHasMax = new HashMap();
      _elementsByHasMax.put(TYPE_ADE_ELEMENT, new TreeSet(_descriptionComparator));
      _elementsByHasMax.put(TYPE_DATA_ELEMENT, new TreeSet(_descriptionComparator));

      _elementsByNotHasMax = new HashMap();
      _elementsByNotHasMax.put(TYPE_ADE_ELEMENT, new TreeSet(_descriptionComparator));
      _elementsByNotHasMax.put(TYPE_DATA_ELEMENT, new TreeSet(_descriptionComparator));

      _elementsByHasMlvs = new HashMap();
      _elementsByHasMlvs.put(TYPE_ADE_ELEMENT, new TreeSet(_descriptionComparator));
      _elementsByHasMlvs.put(TYPE_DATA_ELEMENT, new TreeSet(_descriptionComparator));

      _elementsByNotHasMlvs = new HashMap();
      _elementsByNotHasMlvs.put(TYPE_ADE_ELEMENT, new TreeSet(_descriptionComparator));
      _elementsByNotHasMlvs.put(TYPE_DATA_ELEMENT, new TreeSet(_descriptionComparator));

      _elementsByHasNoVal= new HashMap();
      _elementsByHasNoVal.put(TYPE_ADE_ELEMENT, new TreeSet(_descriptionComparator));
      _elementsByHasNoVal.put(TYPE_DATA_ELEMENT, new TreeSet(_descriptionComparator));

      _elementsByNotHasNoVal= new HashMap();
      _elementsByNotHasNoVal.put(TYPE_ADE_ELEMENT, new TreeSet(_descriptionComparator));
      _elementsByNotHasNoVal.put(TYPE_DATA_ELEMENT, new TreeSet(_descriptionComparator));

      _elementsByIsEav = new HashMap();
      _elementsByIsEav.put(TYPE_ADE_ELEMENT, new TreeSet(_descriptionComparator));
      _elementsByIsEav.put(TYPE_DATA_ELEMENT, new TreeSet(_descriptionComparator));

      _elementsByIsConventional = new HashMap();
      _elementsByIsConventional.put(TYPE_ADE_ELEMENT, new TreeSet(_descriptionComparator));
      _elementsByIsConventional.put(TYPE_DATA_ELEMENT, new TreeSet(_descriptionComparator));

      _dataElementsByHasAde = new TreeSet(_descriptionComparator);      
      _dataElementsByNotHasAde = new TreeSet(_descriptionComparator);

      _dataElementsByHasNonAtv = new TreeSet(_descriptionComparator);
      _dataElementsByNotHasNonAtv = new TreeSet(_descriptionComparator);

      _conceptsByDescription = new HashMap();
      _dataElementsByAde = new HashMap();
      _dataElementsByUnit = new HashMap();
      _adeElementsByUnit = new HashMap();
      _dataElementsByValueSet = new HashMap();
      _adeElementsByValueSet = new HashMap();
      _dataElementsByValue = new HashMap();
      _adeElementsByValue = new HashMap();
      _valueSetsByValue = new HashMap();

      DataElementTaxonomy det = DetService.SINGLETON.getDataElementTaxonomy();

      DetDataElement[] dataElements = det.getAllDataElements();
      for (int j = 0; j < dataElements.length; j++) {
        DetDataElement dataElement = dataElements[j];
        updateDescriptionCache(dataElement, TYPE_DATA_ELEMENT);
        updateMetadataCache(dataElement, TYPE_DATA_ELEMENT);
        ((SortedSet)_conceptsByType.get(TYPE_DATA_ELEMENT)).add(dataElement);
        
        if (dataElement.isHasUnit()) {
          String unitCui = dataElement.getUnitCui();
          SortedSet deByUnitSet = (SortedSet) _dataElementsByUnit.get(unitCui);
          if (deByUnitSet == null) {
            deByUnitSet = new TreeSet(_descriptionComparator);
            _dataElementsByUnit.put(unitCui, deByUnitSet);
          }
          deByUnitSet.add(dataElement);
        }
        
        if (dataElement.isHasAde()) {
          _dataElementsByHasAde.add(dataElement);      
          String adeCui = dataElement.getAde().getCui();
          SortedSet deByAdeSet = (SortedSet) _dataElementsByAde.get(adeCui);
          if (deByAdeSet == null) {
            deByAdeSet = new TreeSet(_descriptionComparator);
            _dataElementsByAde.put(adeCui, deByAdeSet);
          }
          deByAdeSet.add(dataElement);          
        }
        else {
          _dataElementsByNotHasAde.add(dataElement);
        }
        
        DetValueSet broad = dataElement.getBroadValueSet();
        if (broad != null) {
          String broadValueSetCui = broad.getCui();
          SortedSet deByValueSetSet = (SortedSet) _dataElementsByValueSet.get(broadValueSetCui);
          if (deByValueSetSet == null) {
            deByValueSetSet = new TreeSet(_descriptionComparator);
            _dataElementsByValueSet.put(broadValueSetCui, deByValueSetSet);
          }
          deByValueSetSet.add(dataElement);

          loadValueSetAndElementByValueCache(broad, broad.getValues(), dataElement, 
              _dataElementsByValue);
        }

        DetValueSet nonAtv = dataElement.getNonAdeTriggerValueSet();
        if ((nonAtv == null) || (nonAtv.getValues().length == 0)) {
          _dataElementsByNotHasNonAtv.add(dataElement);
        }
        else {
          _dataElementsByHasNonAtv.add(dataElement);
        }
      }
      
      DetAdeElement[] adeElements = det.getAllAdeElements();
      for (int j = 0; j < adeElements.length; j++) {
        DetAdeElement adeElement = adeElements[j];
        updateDescriptionCache(adeElement, TYPE_ADE_ELEMENT);
        updateMetadataCache(adeElement, TYPE_ADE_ELEMENT);
        ((SortedSet)_conceptsByType.get(TYPE_ADE_ELEMENT)).add(adeElement);

        if (adeElement.isHasUnit()) {
          String unitCui = adeElement.getUnitCui();
          SortedSet adeByUnitSet = (SortedSet) _adeElementsByUnit.get(unitCui);
          if (adeByUnitSet == null) {
            adeByUnitSet = new TreeSet(_descriptionComparator);
            _adeElementsByUnit.put(unitCui, adeByUnitSet);
          }
          adeByUnitSet.add(adeElement);
        }

        DetValueSet broad = adeElement.getBroadValueSet();
        if (broad != null) {
          String broadValueSetCui = broad.getCui();
          SortedSet deByValueSetSet = (SortedSet) _adeElementsByValueSet.get(broadValueSetCui);
          if (deByValueSetSet == null) {
            deByValueSetSet = new TreeSet(_descriptionComparator);
            _adeElementsByValueSet.put(broadValueSetCui, deByValueSetSet);
          }
          deByValueSetSet.add(adeElement);

          loadValueSetAndElementByValueCache(broad, broad.getValues(), adeElement, 
              _adeElementsByValue);
        }
      }

      DetAde[] ades = det.getAllAdes();
      for (int i = 0; i < ades.length; i++) {
        DetAde ade = ades[i];
        updateDescriptionCache(ade, TYPE_ADE);
        ((SortedSet)_conceptsByType.get(TYPE_ADE)).add(ade);
      }
      
      DetCategory[] categories = det.getAllCategories();
      for (int i = 0; i < categories.length; i++) {
        DetCategory category = categories[i];
        updateDescriptionCache(category, TYPE_CATEGORY);
        ((SortedSet)_conceptsByType.get(TYPE_CATEGORY)).add(category);
      }

      DetValueSet[] valueSets = det.getAllValueSets();
      for (int i = 0; i < valueSets.length; i++) {
        DetValueSet valueSet = valueSets[i];
        updateDescriptionCache(valueSet, TYPE_VALUE_SET);
        ((SortedSet)_conceptsByType.get(TYPE_VALUE_SET)).add(valueSet);
      }      

      DetUnit[] units = det.getAllUnits();
      for (int i = 0; i < units.length; i++) {
        DetUnit unit = units[i];
        updateDescriptionCache(unit, TYPE_UNIT);
        ((SortedSet)_conceptsByType.get(TYPE_UNIT)).add(unit);
      }      
    }    
  }

  private static void loadValueSetAndElementByValueCache(DetValueSet broad, 
                                                         DetValueSetValue[] values,
                                                         DetElement element, 
                                                         Map elementsByValue) {
    DataElementTaxonomy det = DetService.SINGLETON.getDataElementTaxonomy();
    for (int i = 0; i < values.length; i++) {
      DetValueSetValue value = values[i];
      String valueCui = value.getCui();
      updateDescriptionCache(det.getValue(valueCui), TYPE_VALUE);
      SortedSet deByValueSet = (SortedSet) elementsByValue.get(valueCui);
      if (deByValueSet == null) {
        deByValueSet = new TreeSet(_descriptionComparator);
        elementsByValue.put(valueCui, deByValueSet);
      }
      deByValueSet.add(element);
      
      SortedSet vsByValueSet = (SortedSet) _valueSetsByValue.get(valueCui);
      if (vsByValueSet == null) {
        vsByValueSet = new TreeSet(_descriptionComparator);
        _valueSetsByValue.put(valueCui, vsByValueSet);
      }
      vsByValueSet.add(broad);
      
      loadValueSetAndElementByValueCache(broad, value.getValues(), element, elementsByValue);
    }
    
  }
  
  private static void updateDescriptionCache(DetConcept concept, String conceptType) {
    String desc = concept.getDescription().toLowerCase();
    Map byConceptType = (Map) _conceptsByDescription.get(desc);
    SortedSet concepts = null;
    if (byConceptType == null) {
      byConceptType = new HashMap();
      concepts = new TreeSet(_descriptionComparator);
      byConceptType.put(conceptType, concepts);
      _conceptsByDescription.put(desc, byConceptType);
    }
    else {
      concepts = (SortedSet) byConceptType.get(conceptType);
      if (concepts == null) {
        concepts = new TreeSet(_descriptionComparator);
        byConceptType.put(conceptType, concepts);
      }
    }
    concepts.add(concept);
  }
  
  private static void updateMetadataCache(DetElement element, String conceptType) {
    String datatype = element.getDatatype();
    Map byConceptType = (Map) _elementsByDatatype.get(datatype);
    SortedSet elements = (SortedSet) byConceptType.get(conceptType);
    elements.add(element);

    if (ApiFunctions.isEmpty(element.getMaxValue())) {
      elements = (SortedSet) _elementsByNotHasMax.get(conceptType);
      elements.add(element);
    }
    else {
      elements = (SortedSet) _elementsByHasMax.get(conceptType);
      elements.add(element);
    }
    
    if (ApiFunctions.isEmpty(element.getMinValue())) {
      elements = (SortedSet) _elementsByNotHasMin.get(conceptType);
      elements.add(element);      
    }
    else {
      elements = (SortedSet) _elementsByHasMin.get(conceptType);
      elements.add(element);      
    }

    if (element.isMultilevelValueSet()) {
      elements = (SortedSet) _elementsByHasMlvs.get(conceptType);
      elements.add(element);      
    }
    else {
      elements = (SortedSet) _elementsByNotHasMlvs.get(conceptType);
      elements.add(element);      
    }

    if (element.isHasNoValue()) {
      elements = (SortedSet) _elementsByHasNoVal.get(conceptType);
      elements.add(element);      
    }
    else {
      elements = (SortedSet) _elementsByNotHasNoVal.get(conceptType);
      elements.add(element);      
    }

    if (element.isHasOtherValue()) {
      elements = (SortedSet) _elementsByHasOtherValue.get(conceptType);
      elements.add(element);      
    }
    else {
      elements = (SortedSet) _elementsByNotHasOtherValue.get(conceptType);
      elements.add(element);      
    }

    if (element.isHasUnit()) {
      elements = (SortedSet) _elementsByHasUnit.get(conceptType);
      elements.add(element);      
    }
    else {
      elements = (SortedSet) _elementsByNotHasUnit.get(conceptType);
      elements.add(element);      
    }

    if (element.isMultivalued()) {
      elements = (SortedSet) _elementsByIsMultivalued.get(conceptType);
      elements.add(element);      
    }
    else {
      elements = (SortedSet) _elementsByIsSinglevalued.get(conceptType);
      elements.add(element);            
    }

    if (element.getDatabaseElement().isTableEav()) {
      elements = (SortedSet) _elementsByIsEav.get(conceptType);
      elements.add(element);      
    }
    else {
      elements = (SortedSet) _elementsByIsConventional.get(conceptType);
      elements.add(element);      
    }
  }
}
