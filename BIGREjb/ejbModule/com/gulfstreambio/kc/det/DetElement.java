package com.gulfstreambio.kc.det;

public interface DetElement extends DetConcept {

  public String getDatatype();
  public boolean isDatatypeCv();
  public boolean isDatatypeInt();
  public boolean isDatatypeFloat();
  public boolean isDatatypeDate();
  public boolean isDatatypeVpd();
  public boolean isDatatypeReport();
  public boolean isDatatypeText();
  public boolean isMaxInclusive();
  public boolean isMinInclusive();
  public boolean isMultilevelValueSet();
  public boolean isMultivalued();
  public boolean isHasOtherValue();
  public boolean isHasNoValue();
  public boolean isHasUnit();
  public String getMaxValue();
  public String getMinValue();
  public String getNoValueCui();
  public String getNoValueDescription();
  public String getOtherValueCui();
  public String getOtherValueDescription();
  public String getSystemName();
  public String getUnitCui();
  public String getUnitDescription();
  public DetValueSet getBroadValueSet();
  public DetAde getAde();

  /**
   * Returns metadata that describes the database implementation of this element.
   * 
   * @return The <code>DatabaseElement</code>.
   */
  public DatabaseElement getDatabaseElement();
 
}
