package com.gulfstreambio.kc.det;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.ardais.bigr.util.ArtsConstants;
import com.ardais.bigr.util.SystemNameUtils;
import com.gulfstreambio.gboss.Gboss;
import com.gulfstreambio.gboss.GbossAde;
import com.gulfstreambio.gboss.GbossCategory;
import com.gulfstreambio.gboss.GbossDataElementTaxonomy;
import com.gulfstreambio.gboss.GbossFactory;
import com.gulfstreambio.gboss.GbossValue;


public class DataElementTaxonomy implements Serializable {

  public final static String SYSTEM_STANDARD_VALUE_NOT_SOUGHT = "CA01257C";
  public final static String SYSTEM_STANDARD_VALUE_NOT_REPORTED = "CA01258C";
  public final static String SYSTEM_STANDARD_VALUE_SEE_NOTE = "CA01259C";

  private DatabaseFormInstance _databaseFormInstance;

  /**
   * Direct child categories.  Each item is a DetCategory instance.
   */
  private List _childCategories;

  /**
   * All descendant categories, in the order they appear in the DET XML document.  Each item is a 
   * DetCategory instance.
   */
  private List _descendantCategories;

  /**
   * A cache of all descendant child categories.  Each value is a DetCategory instance, keyed by
   * category CUI and canonical system name.
   */
  private Map _categoryCache;

  /**
   * All of the ADEs.  Each item is an DetAde instance.  The map is keyed by the ADE CUI and
   * canonical system name.
   */
  private Map _adeCache;
  private DetAde[] _ades;
  
  /**
   * All descendant data elements, in the order they appear in the DET XML document.  Each value 
   * is a DetDataElement instance. 
   */
  private List _descendantDataElements;

  /**
   * A cache of all descendant data elements.  Each value is a DetDataElement instance, 
   * keyed by the data element's CUI and canonical system name.
   */
  private Map _dataElementCache;

  /**
   * A cache of all descendant ADE elements of all of the ADEs.  Each value is an DetAdeElement 
   * instance, keyed by the ADE element's CUI and canonical system name.
   */
  private Map _adeElementCache;

  /**
   * All descendant ADE elements, in the order they appear in the DET XML document.  Each value is 
   * a DetAdeElement instance. 
   */
  private List _descendantAdeElements;

  /**
   * All units used by all data elements and all ADE elements.  Each value is a DetUnit instance,
   * keyed by the unit's CUI.
   */
  private Map _units;

  /**
   * All value sets used by all data elements and all ADE elements.  Each value is a DetValueSet
   * instance, keyed by the value sets's CUI.
   */
  private Map _valueSets;

  /**
   * All element values, lazily cached.  Each value is a DetValue, keyed by the value's CUI.
   */
  private Map _elementValues = new HashMap();

  /**
   * The value set that holds the system standard values.
   */
  private DetValueSet _systemStandardValueSet;
  
 
  DataElementTaxonomy(Gboss gboss) {
    super();
    // load the data from DET
    loadAdes(gboss.getAncillaryDataElements().getAncillaryDataElements());
    loadCategories(gboss.getDataElementTaxonomy());
    finishDet();
    _systemStandardValueSet = new DetValueSet(GbossFactory.getInstance().getValueSets().getValueSet(ArtsConstants.STANDARD_VALUE_CUI));
    _databaseFormInstance = new DatabaseFormInstance();
  }
  
  private void loadAdes(List ades) {
    int numberOfAdes = ades.size();
    
    _adeCache = new HashMap();
    _ades = new DetAde[numberOfAdes];
    
    for (int i = 0; i < numberOfAdes; i++) {
      GbossAde ade = (GbossAde) ades.get(i);
      DetAde adeMetadata = new DetAde(this, ade);
      _adeCache.put(ade.getCui(), adeMetadata);
      _adeCache.put(ade.getSystemName(), adeMetadata);
      _ades[i] = adeMetadata;
    }
  }
  
  private void loadCategories(GbossDataElementTaxonomy det) {
    _childCategories = new ArrayList();
    Iterator i = det.getCategories().iterator();
    while (i.hasNext()) {
      GbossCategory category = (GbossCategory) i.next();
      _childCategories.add(new DetCategory(this, category));
    }
  }
  
  private void finishDet() {
    _descendantCategories = new ArrayList();
    _categoryCache = new HashMap();
    _descendantDataElements = new ArrayList();
    _dataElementCache = new HashMap();
    _descendantAdeElements = new ArrayList();
    _adeElementCache = new HashMap();
    _units = new HashMap();
    _valueSets = new HashMap();

    for (int i = 0; i < _ades.length; i++) {
      DetAde ade = _ades[i];
      DetAdeElement[] adeElements = ade.getAdeElements();
      for (int j = 0; j < adeElements.length; j++) {
        DetAdeElement adeElement = adeElements[j];
        adeElement.associateAde(ade);
        _adeElementCache.put(adeElement.getCui(), adeElement);
        _adeElementCache.put(adeElement.getSystemName(), adeElement);
        _descendantAdeElements.add(adeElement);
        String unitCui = adeElement.getUnitCui();
        if ((unitCui != null) && (!_units.containsKey(unitCui))) {
          _units.put(unitCui, new DetUnit(unitCui, getCuiDescription(unitCui)));
        }
        if (adeElement.isDatatypeCv()) {
          DetValueSet broadValueSet = adeElement.getBroadValueSet();
          String broadValueSetCui = broadValueSet.getCui();
          if (!_valueSets.containsKey(broadValueSetCui)) {
            _valueSets.put(broadValueSetCui, broadValueSet);
          }
        }
      }
    }
    
    Iterator i = _childCategories.iterator();
    while (i.hasNext()) {
      DetCategory category = (DetCategory) i.next();
      finishCategory(category);
    }
  }
  
  private void finishCategory(DetCategory category ) {
    _descendantCategories.add(category);
    _categoryCache.put(category.getCui(), category);
    _categoryCache.put(category.getSystemName(), category);

    DetCategory[] detCategories = category.getCategories();    
    for (int i = 0; i < detCategories.length; i++) {
    	finishCategory(detCategories[i]);
    }

    DetDataElement[] detDataElements = category.getDataElements();    
    for (int i = 0; i < detDataElements.length; i++) {
      DetDataElement dataElement = detDataElements[i];
      dataElement.associateAde(this);
      _dataElementCache.put(dataElement.getCui(), dataElement);
      _dataElementCache.put(dataElement.getSystemName(), dataElement);
      _descendantDataElements.add(dataElement); 
      String unitCui = dataElement.getUnitCui();
      if ((unitCui != null) && (!_units.containsKey(unitCui))) {
        _units.put(unitCui, new DetUnit(unitCui, getCuiDescription(unitCui)));
      }
      if (dataElement.isDatatypeCv()) {
        DetValueSet broadValueSet = dataElement.getBroadValueSet();
        String broadValueSetCui = broadValueSet.getCui();
        if (!_valueSets.containsKey(broadValueSetCui)) {
          _valueSets.put(broadValueSetCui,broadValueSet);
        }
      }
    }
  }
  
  public DetCategory[] getCategories() {
    return (DetCategory[]) _childCategories.toArray(new DetCategory[0]);
  }
  
  public DetCategory[] getAllCategories() {
    return (DetCategory[]) _descendantCategories.toArray(new DetCategory[0]);
  }
  
  public DetCategory getCategory(String cuiOrSystemName) {
    DetCategory metadata = (DetCategory) _categoryCache.get(cuiOrSystemName);
    if ((metadata == null) && (cuiOrSystemName != null)) {
      String canonicalSystemName = SystemNameUtils.convertToCanonicalForm(cuiOrSystemName);
      metadata = (DetCategory) _categoryCache.get(canonicalSystemName);
    }
    return metadata;
  }

  public DetDataElement[] getAllDataElements() {
    return (DetDataElement[]) _descendantDataElements.toArray(new DetDataElement[0]);    
  }

  public DetDataElement getDataElement(String cuiOrSystemName) {
    DetDataElement metadata = (DetDataElement) _dataElementCache.get(cuiOrSystemName);
    if ((metadata == null) && (cuiOrSystemName != null)) {
      String canonicalSystemName = SystemNameUtils.convertToCanonicalForm(cuiOrSystemName);
      metadata = (DetDataElement) _dataElementCache.get(canonicalSystemName);
    }
    return metadata;
  }

  public DetAde[] getAllAdes() {
    return _ades;
  }

  public DetAde getAde(String cuiOrSystemName) {
    DetAde metadata = (DetAde) _adeCache.get(cuiOrSystemName);
    if ((metadata == null) && (cuiOrSystemName != null)) {
      String canonicalSystemName = SystemNameUtils.convertToCanonicalForm(cuiOrSystemName);
      metadata = (DetAde) _adeCache.get(canonicalSystemName);
    }
    return metadata;
  }

  public DetAdeElement[] getAllAdeElements() {
    return (DetAdeElement[]) _descendantAdeElements.toArray(new DetAdeElement[0]);        
  }

  public DetAdeElement getAdeElement(String cuiOrSystemName) {
    DetAdeElement metadata = (DetAdeElement) _adeElementCache.get(cuiOrSystemName);
    if ((metadata == null) && (cuiOrSystemName != null)) {
      String canonicalSystemName = SystemNameUtils.convertToCanonicalForm(cuiOrSystemName);
      metadata = (DetAdeElement) _adeElementCache.get(canonicalSystemName);
    }
    return metadata;
  }
  
  public DetUnit[] getAllUnits() {
    return (DetUnit[]) _units.values().toArray(new DetUnit[0]);
  }
  
  public DetUnit getUnit(String cui) {
    return (DetUnit) _units.get(cui);
  }

  public DetValueSet[] getAllValueSets() {
    return (DetValueSet[]) _valueSets.values().toArray(new DetValueSet[0]);
  }
  
  public DetValueSet getValueSet(String cui) {
    return (DetValueSet) _valueSets.get(cui);
  }

  public DetValue getValue(String cui) {
    DetValue value = (DetValue) _elementValues.get(cui);
    if (value == null) {
      try {
        GbossValue gbossValue = GbossFactory.getInstance().getValue(cui);
        value = new DetValue(gbossValue);
        _elementValues.put(value.getCui(), value);          
      }
      catch (IllegalArgumentException e) {
        // Intentionally do nothing.  This is thrown if there is no GBOSS value with the specified
        // CUI, so in this case we'll just return null.
      }
    }
    return value;
  }

  public String getCuiDescription(String cui) {
    return GbossFactory.getInstance().getDescription(cui);
  }

  public DetValueSet getSystemStandardValues() {
    return _systemStandardValueSet;
  }
  
  /**
   * Returns metadata that describes the database implementation of the form instance as a whole.  
   * Use {@link DetElement.getDatabaseElement()} to get metadata concerning individual data 
   * elements and ADE elements.
   * 
   * @return The <code>DatabaseFormInstance</code>.
   */
  public DatabaseFormInstance getDatabaseFormInstance() {
    return _databaseFormInstance;
  }


}
