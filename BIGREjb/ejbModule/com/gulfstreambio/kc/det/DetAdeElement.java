package com.gulfstreambio.kc.det;

import java.io.Serializable;

import com.gulfstreambio.gboss.GbossAdeElement;

public class DetAdeElement extends DetElementBase implements Serializable {

  private DetAde _ade;
  private DatabaseAdeElement _databaseAdeElement; 

  DetAdeElement(DataElementTaxonomy det, GbossAdeElement adeElement) {
    super();
    setCui(adeElement.getCui());
    setDescription(adeElement.getDescription());      
  
    String datatype = adeElement.getDatatype();
    setDatatype(datatype);
    
    setMaxInclusive(adeElement.isMaximumInclusive());
    setMaxValue(adeElement.getMaxValue());
    setMinInclusive(adeElement.isMinimumInclusive());
    setMinValue(adeElement.getMinValue());
    setMultilevelValueSet(adeElement.isMlvsEnabled());
    setMultivalued(adeElement.isMultiValued());

    String cui = adeElement.getNoValueCui();
    setHasNoValue(cui != null);
    if (cui != null) {
      setNoValueCui(cui);
      setNoValueDescription(det.getCuiDescription(cui));      
    }

    cui = adeElement.getOtherValueCui();
    setHasOtherValue(cui != null);
    if (cui != null) {
      setOtherValueCui(cui);
      setOtherValueDescription(det.getCuiDescription(cui));      
    }

    setSystemName(adeElement.getSystemName());

    cui = adeElement.getUnitCui();
    setHasUnit(cui != null);
    if (cui != null) {
      setUnitCui(cui);
      setUnitDescription(det.getCuiDescription(cui));      
    }

    if (datatype.equals(DetElementDatatypes.CV)) {
      setBroadValueSet(new DetValueSet(adeElement.getBroadValueSet()));
    }
  }

  void associateAde(DetAde ade) {
    _ade = ade;
  }
  
  public DetAde getAde() {
    return _ade;
  }

  /* (non-Javadoc)
   * @see com.gulfstreambio.kc.det.DetElement#getDatabaseElement()
   */
  public DatabaseElement getDatabaseElement() {
    if (_databaseAdeElement == null) {
      _databaseAdeElement = new DatabaseAdeElement(this);
    }
    return _databaseAdeElement;
  }
}
