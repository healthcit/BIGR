package com.gulfstreambio.kc.web.support;

import com.gulfstreambio.kc.det.DetValueSet;

public interface DataFormElementContext {

  public String getCui();
  public String getSystemName();
  public String getDatatype();
  public boolean isDatatypeDate();
  public boolean isDatatypeInt();
  public boolean isDatatypeFloat();
  public boolean isDatatypeReport();
  public boolean isDatatypeText();
  public boolean isDatatypeVpd();
  public boolean isDatatypeCv();
  public boolean isMultivalued();
  public boolean isRequired();
  public boolean isHasOtherValue();
  public String getOtherValueCui();
  public String getOtherValueDescription();
  public boolean isHasNoValue();
  public String getNoValueCui();
  public boolean isMlvs();
  public boolean isHasAde();
  public String getUnitCui();
  public String getUnitDescription();

  public String getLabel();
  public String getLabelWithUnits();

  public DetValueSet getValueSetNarrow();
  public DetValueSet getValueSetStandard();
  public DetValueSet getValueSetBroad();
  public DetValueSet getValueSetNonAtv();

  public String getValue();
  public String[] getValues();
  public String getValueDescription(int index);
  public String getValueDescription();
  public String getValueOther();
  public String[] getValueOthers();
  public String getValueNote();
  public boolean isValueStandardValue();
  public boolean isValueNoValue();
  public boolean isValueAtvValue();
  public boolean isAllValuesInNarrowValueSet();
  public boolean isAllValuesInStandardValueSet();
  public boolean isAllValuesInBroadValueSet();
  
  public boolean isTreatAsSinglevalued();
  public void setTreatAsSinglevalued(boolean single);

  public String[] getExcludedValues();
  public void setExcludedValues(String[] values);

  public String getJavascriptObjectId();
  public void setJavascriptObjectId(String id);
  public String getHtmlIdContainer();
  public void setHtmlIdContainer(String id);
  public String getHtmlIdPrimary();
  public void setHtmlIdPrimary(String id);
  public String getHtmlIdStandard();
  public void setHtmlIdStandard(String id);
  public String getHtmlIdOther();
  public void setHtmlIdOther(String id);
  public String getHtmlIdOtherText();
  public void setHtmlIdOtherText(String id);
  public String getHtmlIdOtherContainer();
  public void setHtmlIdOtherContainer(String id);
  public String getHtmlIdOtherAdd();
  public void setHtmlIdOtherAdd(String id);
  public String getHtmlIdOtherRemove();
  public void setHtmlIdOtherRemove(String id);
  public String getHtmlIdNote();
  public void setHtmlIdNote(String id);
  public String getHtmlIdNoteContainer();
  public void setHtmlIdNoteContainer(String id);
  public String getHtmlIdNoteButton();
  public void setHtmlIdNoteButton(String id);
  public String getHtmlIdAdeTable();
  public void setHtmlIdAdeTable(String id);
  public String getHtmlIdAdeContainer();
  public void setHtmlIdAdeContainer(String id);
  public String getHtmlIdAdeLink();
  public void setHtmlIdAdeLink(String id);
  public String getHtmlIdValueSet();
  public void setHtmlIdValueSet(String id);
  public String getHtmlIdNarrowValueSetContainer();
  public void setHtmlIdNarrowValueSetContainer(String id);
  public String getHtmlIdStandardValueSetContainer();
  public void setHtmlIdStandardValueSetContainer(String id);
  public String getHtmlIdBroadValueSetContainer();
  public void setHtmlIdBroadValueSetContainer(String id);
  public String getHtmlIdNarrowValueSet();
  public void setHtmlIdNarrowValueSet(String id);
  public String getHtmlIdStandardValueSet();
  public void setHtmlIdStandardValueSet(String id);
  public String getHtmlIdBroadValueSet();
  public void setHtmlIdBroadValueSet(String id);
  public String getHtmlIdStandardLink();
  public void setHtmlIdStandardLink(String id);
  public String[] getHtmlIdBroadLinks();
  public void addHtmlIdBroadLink(String id);
  public String getHtmlNameValueSet();
  public void setHtmlNameValueSet(String name);
  
}
