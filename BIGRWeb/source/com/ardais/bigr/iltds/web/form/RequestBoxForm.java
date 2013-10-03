package com.ardais.bigr.iltds.web.form;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;

import com.ardais.bigr.beanutils.BigrBeanUtilsBean;
import com.ardais.bigr.javabeans.BoxDto;
import com.ardais.bigr.javabeans.BoxLayoutDto;
import com.ardais.bigr.javabeans.RequestBoxDto;
import com.ardais.bigr.web.action.BigrActionMapping;
import com.ardais.bigr.web.form.BigrActionForm;

/**
 * A Struts ActionForm that represents an request box from manage request.
 */
public class RequestBoxForm extends BigrActionForm {

  private String _boxId;
  private boolean _shipped;
  private HashMap _contents = new HashMap();
  private HashMap _sampleAliases = new HashMap();
  
  private String _boxLayoutId;
  private BoxLayoutDto _boxLayoutDto;

  /**
   * @see com.ardais.bigr.web.form.BigrActionForm#doReset(com.ardais.bigr.web.action.BigrActionMapping, javax.servlet.http.HttpServletRequest)
   */
  protected void doReset(BigrActionMapping mapping, HttpServletRequest request) {
    _boxId = null;
    _contents = null;
    _sampleAliases = null;
  }

  /**
   * @see com.ardais.bigr.web.form.BigrActionForm#doValidate(com.ardais.bigr.web.action.BigrActionMapping, javax.servlet.http.HttpServletRequest)
   */
  protected ActionErrors doValidate(BigrActionMapping mapping, HttpServletRequest request) {
    // Nothing to validate here.  All validation takes place in the BTX performer.
    return null;
  }

  /**
   * 
   * @param dto
   */
  public void describeIntoDto(RequestBoxDto dto) {
    // Copy the form fields to the request box dto.
    BigrBeanUtilsBean.SINGLETON.copyProperties(dto, this);

    // Copy the form fields to the box dto.
    BoxDto boxDto = new BoxDto();
    BigrBeanUtilsBean.SINGLETON.copyProperties(boxDto, this);

    dto.setBoxDto(boxDto);
  }

  /**
   * 
   * @param dto
   */
  public void populateFromDto(RequestBoxDto dto) {
    BigrBeanUtilsBean.SINGLETON.copyProperties(this, dto);
    
    BoxDto boxDto = dto.getBoxDto();
    BigrBeanUtilsBean.SINGLETON.copyProperties(this, boxDto);
    
    setBoxId(dto.getBoxId());
  }

  public String getCellContent(int cellRef) {
    return getCellContent(Integer.toString(cellRef));
  }

  public String getCellContent(String cellRef) {
    return ((String)_contents.get(cellRef));
  }

  public void setCellContent(int cellRef, String cellContent) {
    setCellContent(Integer.toString(cellRef), cellContent);
  }

  public void setCellContent(String cellRef, String cellContent) {
    _contents.put(cellRef, cellContent);
  }

  public String getCellSampleAlias(int cellRef) {
    return getCellSampleAlias(Integer.toString(cellRef));
  }

  public String getCellSampleAlias(String cellRef) {
    return ((String)_sampleAliases.get(cellRef));
  }

  public void setCellSampleAlias(int cellRef, String sampleAlias) {
    setCellSampleAlias(Integer.toString(cellRef), sampleAlias);
  }

  public void setCellSampleAlias(String cellRef, String sampleAlias) {
    _sampleAliases.put(cellRef, sampleAlias);
  }

  /**
   * @return
   */
  public String getBoxId() {
    return _boxId;
  }

  /**
   * @return
   */
  public BoxLayoutDto getBoxLayoutDto() {
    return _boxLayoutDto;
  }

  /**
   * @return
   */
  public String getBoxLayoutId() {
    return _boxLayoutId;
  }

  /**
   * @return
   */
  public HashMap getContents() {
    return _contents;
  }
  
  public HashMap getSampleAliases() {
    return _sampleAliases;
  }

  /**
   * @return
   */
  public boolean isShipped() {
    return _shipped;
  }

  /**
   * @param string
   */
  public void setBoxId(String string) {
    _boxId = string;
  }

  /**
   * @param dto
   */
  public void setBoxLayoutDto(BoxLayoutDto dto) {
    _boxLayoutDto = dto;
  }

  /**
   * @param string
   */
  public void setBoxLayoutId(String string) {
    _boxLayoutId = string;
  }

  /**
   * @param map
   */
  public void setContents(HashMap map) {
    _contents = map;
  }

  /**
   * @param map
   */
  public void setSampleAliases(HashMap map) {
    _sampleAliases = map;
  }

  /**
   * @param b
   */
  public void setShipped(boolean b) {
    _shipped = b;
  }
}
