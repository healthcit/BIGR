package com.gulfstreambio.kc.det;

import java.io.Serializable;

import com.ardais.bigr.api.ApiFunctions;
import com.gulfstreambio.gboss.GbossDataElement;
import com.gulfstreambio.gboss.GbossValueSet;

public class DetDataElement extends DetElementBase implements Serializable {

  private String _adeSystemName;
  private DetAde _ade;
  private DetValueSet _nonAtvValueSet;
  private DatabaseDataElement _databaseDataElement; 
 
  DetDataElement(DataElementTaxonomy det, GbossDataElement dataElement) {
    super();
    setCui(dataElement.getCui());
    setDescription(dataElement.getDescription());      
 
    String datatype = dataElement.getDatatype(); 
    setDatatype(datatype);
    
    setMaxInclusive(dataElement.isMaximumInclusive());
    setMaxValue(dataElement.getMaxValue());
    setMinInclusive(dataElement.isMinimumInclusive());
    setMinValue(dataElement.getMinValue());
    setMultilevelValueSet(dataElement.isMlvsEnabled());
    setMultivalued(dataElement.isMultiValued());


    String cui = dataElement.getNoValueCui();
    setHasNoValue(cui != null);
    if (cui != null) {
      setNoValueCui(cui);
      setNoValueDescription(det.getCuiDescription(cui));      
    }

    cui = dataElement.getOtherValueCui();
    setHasOtherValue(cui != null);
    if (cui != null) {
      setOtherValueCui(cui);
      setOtherValueDescription(det.getCuiDescription(cui));      
    }

    setSystemName(dataElement.getSystemName());

    cui = dataElement.getUnitCui();
    setHasUnit(cui != null);
    if (cui != null) {
      setUnitCui(cui);
      setUnitDescription(det.getCuiDescription(cui));      
    }

    if (dataElement.getAde() != null) {
    	setAdeSystemName(dataElement.getAde().getSystemName());
    }
    
    if (datatype.equals(DetElementDatatypes.CV)) {
      setBroadValueSet(new DetValueSet(dataElement.getBroadValueSet()));
      GbossValueSet valSet = dataElement.getNonAtvValueSet();
      if (valSet != null) {
        setNonAdeTriggerValueSet(new DetValueSet(valSet));
      }
    }
  }


  private String getAdeSystemName() {
    return _adeSystemName;
  }
  
  private void setAdeSystemName(String adeSystemName) {
    _adeSystemName = adeSystemName;
  }

  void associateAde(DataElementTaxonomy det) {
    _ade = det.getAde(getAdeSystemName());
  }
  
  public DetAde getAde() {
    return _ade;
  }
  
  public boolean isHasAde() {
    return !ApiFunctions.isEmpty(getAdeSystemName());
  }

  public DetValueSet getNonAdeTriggerValueSet() {
    return _nonAtvValueSet;
  }
  
  private void setNonAdeTriggerValueSet(DetValueSet nonAdeTriggerValueSet) {
    _nonAtvValueSet = nonAdeTriggerValueSet;
  }

  public DatabaseElement getDatabaseElement() {
    if (_databaseDataElement == null) {
      _databaseDataElement = new DatabaseDataElement(this);
    }
    return _databaseDataElement;
  }
}
