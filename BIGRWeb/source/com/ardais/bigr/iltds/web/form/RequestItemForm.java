/*
 * Created on Mar 15, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.ardais.bigr.iltds.web.form;

import java.util.ArrayList;
import java.util.Iterator;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.iltds.btx.BTXBoxLocation;
import com.ardais.bigr.iltds.helpers.FormLogic;
import com.ardais.bigr.javabeans.RequestItemDto;
import com.ardais.bigr.library.web.helper.SampleSelectionHelper;
import com.ardais.bigr.util.BoxLayoutUtils;

/**
 * A Struts ActionForm that represents a requested item.
 */
public class RequestItemForm {
  
  private String _donorId;
  private String _caseId;
  private String _caseAlias;
  private String _itemId;
  private String _itemAlias;
  private String _boxId;
  private String _inventoryGroups;
  private String _limsDiagnosis;
  private String _ddcDiagnosis;
  private String _tissueOfOriginSiteOfFinding;
  private String _appearance;
  private String _formatDetail;
  private String _normalCells;
  private String _lesionCells;
  private String _tumorCells;
  private String _cellularStromaCells;
  private String _acellularStromaCells;
  private String _necrosisCells;
  private String _locationRoomId;
  private String _locationUnitName;
  private String _locationDrawerId;
  private String _locationSlotId;
  private String _cellRef;
  private String _asmPosition;
  private String _asmTissue;
  private String _mostRecentSlides;
  private String _donorInstitution;
  private String _bms;
  private String _sampleType;
  
  /**
   * @return
   */
  public String getAcellularStromaCells() {
    return _acellularStromaCells;
  }

  /**
   * @return
   */
  public String getAppearance() {
    return _appearance;
  }

  /**
   * @return
   */
  public String getAsmPosition() {
    return _asmPosition;
  }

  /**
   * @return
   */
  public String getAsmTissue() {
    return _asmTissue;
  }

  /**
   * @return
   */
  public String getBms() {
    return _bms;
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
  public String getCaseId() {
    return _caseId;
  }

  /**
   * @return
   */
  public String getCaseAlias() {
    return _caseAlias;
  }

  /**
   * @return
   */
  public String getCellularStromaCells() {
    return _cellularStromaCells;
  }

  /**
   * @return
   */
  public String getDdcDiagnosis() {
    return _ddcDiagnosis;
  }

  /**
   * @return
   */
  public String getDonorId() {
    return _donorId;
  }

  /**
   * @return
   */
  public String getDonorInstitution() {
    return _donorInstitution;
  }

  /**
   * @return
   */
  public String getFormatDetail() {
    return _formatDetail;
  }

  /**
   * @return
   */
  public String getInventoryGroups() {
    return _inventoryGroups;
  }

  /**
   * @return
   */
  public String getItemId() {
    return _itemId;
  }

  /**
   * @return
   */
  public String getItemAlias() {
    return _itemAlias;
  }

  /**
   * @return
   */
  public String getLesionCells() {
    return _lesionCells;
  }

  /**
   * @return
   */
  public String getLimsDiagnosis() {
    return _limsDiagnosis;
  }

  /**
   * @return
   */
  public String getCellRef() {
    return _cellRef;
  }

  /**
   * @return
   */
  public String getTranslatedCellRef() {
    return BoxLayoutUtils.translateCellRef(_cellRef, _boxId);
  }

  /**
   * @return
   */
  public String getLocationDrawerId() {
    return _locationDrawerId;
  }

  /**
   * @return
   */
  public String getLocationRoomId() {
    return _locationRoomId;
  }

  /**
   * @return
   */
  public String getLocationSlotId() {
    return _locationSlotId;
  }

  /**
   * @return
   */
  public String getLocationUnitName() {
    return _locationUnitName;
  }

  /**
   * @return
   */
  public String getMostRecentSlides() {
    return _mostRecentSlides;
  }

  /**
   * @return
   */
  public String getNecrosisCells() {
    return _necrosisCells;
  }

  /**
   * @return
   */
  public String getNormalCells() {
    return _normalCells;
  }

  /**
   * @return
   */
  public String getSampleType() {
    return _sampleType;
  }

  /**
   * @return
   */
  public String getTissueOfOriginSiteOfFinding() {
    return _tissueOfOriginSiteOfFinding;
  }

  /**
   * @return
   */
  public String getTumorCells() {
    return _tumorCells;
  }

  /**
   * @param string
   */
  public void setAcellularStromaCells(String string) {
    _acellularStromaCells = string;
  }

  /**
   * @param string
   */
  public void setAppearance(String string) {
    _appearance = string;
  }

  /**
   * @param string
   */
  public void setAsmPosition(String string) {
    _asmPosition = string;
  }

  /**
   * @param string
   */
  public void setAsmTissue(String string) {
    _asmTissue = string;
  }

  /**
   * @param string
   */
  public void setBms(String string) {
    _bms = string;
  }

  /**
   * @param string
   */
  public void setBoxId(String string) {
    _boxId = string;
  }

  /**
   * @param string
   */
  public void setCaseId(String string) {
    _caseId = string;
  }

  /**
   * @param string
   */
  public void setCaseAlias(String string) {
    _caseAlias = string;
  }

  /**
   * @param string
   */
  public void setCellularStromaCells(String string) {
    _cellularStromaCells = string;
  }

  /**
   * @param string
   */
  public void setDdcDiagnosis(String string) {
    _ddcDiagnosis = string;
  }

  /**
   * @param string
   */
  public void setDonorId(String string) {
    _donorId = string;
  }

  /**
   * @param string
   */
  public void setDonorInstitution(String string) {
    _donorInstitution = string;
  }

  /**
   * @param string
   */
  public void setFormatDetail(String string) {
    _formatDetail = string;
  }

  /**
   * @param string
   */
  public void setInventoryGroups(String string) {
    _inventoryGroups = string;
  }

  /**
   * @param string
   */
  public void setItemId(String string) {
    _itemId = string;
  }

  /**
   * @param string
   */
  public void setItemAlias(String string) {
    _itemAlias = string;
  }

  /**
   * @param string
   */
  public void setLesionCells(String string) {
    _lesionCells = string;
  }

  /**
   * @param string
   */
  public void setLimsDiagnosis(String string) {
    _limsDiagnosis = string;
  }

  /**
   * @param string
   */
  public void setCellRef(String string) {
    _cellRef = string;
  }

  /**
   * @param string
   */
  public void setLocationDrawerId(String string) {
    _locationDrawerId = string;
  }

  /**
   * @param string
   */
  public void setLocationRoomId(String string) {
    _locationRoomId = string;
  }

  /**
   * @param string
   */
  public void setLocationSlotId(String string) {
    _locationSlotId = string;
  }

  /**
   * @param string
   */
  public void setLocationUnitName(String string) {
    _locationUnitName = string;
  }

  /**
   * @param string
   */
  public void setMostRecentSlides(String string) {
    _mostRecentSlides = string;
  }

  /**
   * @param string
   */
  public void setNecrosisCells(String string) {
    _necrosisCells = string;
  }

  /**
   * @param string
   */
  public void setNormalCells(String string) {
    _normalCells = string;
  }

  /**
   * @param string
   */
  public void setSampleType(String string) {
    _sampleType = string;
  }

  /**
   * @param string
   */
  public void setTissueOfOriginSiteOfFinding(String string) {
    _tissueOfOriginSiteOfFinding = string;
  }

  /**
   * @param string
   */
  public void setTumorCells(String string) {
    _tumorCells = string;
  }

  /**
   * 
   * @param dto
   */
  public void describeIntoDto(RequestItemDto dto) {
    dto.setItemId(getItemId());
  }

  /**
   * 
   * @param dto
   */
  public void populateFromDto(RequestItemDto dto) {
    //if the Product data on the dto is null, then just set the item id and return
    if (dto.getItem() == null) {
      setItemId(dto.getItemId());
      return;
    }
    //otherwise do a full population
    SampleSelectionHelper helper = new SampleSelectionHelper(dto.getItem());
    setDonorId(helper.getDonorId());
    setCaseId(helper.getConsentId());
    setCaseAlias(helper.getConsentCustomerId());
    String boxId = dto.getItem().getSampleData().getBoxBarcodeId();
    if (!ApiFunctions.isEmpty(boxId)) {
      setBoxId(boxId);
    }
    else {
      setBoxId("");
    }
    setItemId(helper.getSampleId());
    setItemAlias(helper.getSampleAlias());
    setInventoryGroups(helper.getLogicalRepositoryShortNames());
    setLimsDiagnosis(helper.getLimsDiagnosis());
    setDdcDiagnosis(helper.getDdcDiagnosis());
    StringBuffer result = new StringBuffer(30);
    if (helper.isVerified()) {
      result.append(helper.getTissueOfOrigin());
      result.append("/ ");
      result.append(helper.getTissueOfFinding());
    }
    else {
      result.append(helper.getSampleTissueOfOrigin());
      result.append("/ ");
      result.append(helper.getAsmTissue());
    }
    setTissueOfOriginSiteOfFinding(result.toString());
    setAppearance(helper.getAppearanceBest());
    setFormatDetail(helper.getSampleFormatDetail());
    setNormalCells(helper.getNormal());
    setLesionCells(helper.getLesion());
    setTumorCells(helper.getTumor());
    setCellularStromaCells(helper.getCellularStroma());
    setAcellularStromaCells(helper.getAcellularStroma());
    setNecrosisCells(helper.getNecrosis());
    BTXBoxLocation location = dto.getItem().getStorageLocation();
    String roomId = null;
    String unitName = null;
    String drawerId = null;
    String slotId = null;
    if (location != null) {
      roomId = location.getRoomID();
      unitName = location.getUnitName();
      drawerId = location.getDrawerID();
      slotId = location.getSlotID();
    }
    if (!ApiFunctions.isEmpty(roomId)) {
      setLocationRoomId(roomId);
    }
    else {
      setLocationRoomId("");
    }
    if (!ApiFunctions.isEmpty(unitName)) {
      setLocationUnitName(unitName);
    }
    else {
      setLocationUnitName("");
    }
    if (!ApiFunctions.isEmpty(drawerId)) {
      setLocationDrawerId(drawerId);
    }
    else {
      setLocationDrawerId("");
    }
    if (!ApiFunctions.isEmpty(slotId)) {
      setLocationSlotId(slotId);
    }
    else {
      setLocationSlotId("");
    }
    String cellRef = dto.getItem().getSampleData().getLocation();
    if (!ApiFunctions.isEmpty(cellRef)) {
      // Don't know why Formlogic.translateCellRef() was called here. You don't want to store the
      // cell name into the cell ref field... I suspect the call was doing nothing, as the method returns
      // the passed in value if it can't translate.
      setCellRef(cellRef);
    }
    else {
      setCellRef("");
    }
    setAsmPosition(helper.getAsmPosition());
    setAsmTissue(helper.getAsmTissue());
    setSampleType(helper.getSampleTypeDisplay());
    ArrayList slides = dto.getItem().getSampleData().getSlides();
    if (!ApiFunctions.isEmpty(slides)) {
      StringBuffer buff = new StringBuffer(21);
      Iterator iterator = slides.iterator();
      boolean includeSemicolon = false;
      while (iterator.hasNext()) {
        if (includeSemicolon) {
          buff.append(";");
        }
        includeSemicolon = true;
        buff.append((String)iterator.next());
      }
      setMostRecentSlides(buff.toString());
    }
    else {
      setMostRecentSlides("");
    }
    setDonorInstitution(helper.getConsentLocation());
    setBms(helper.getBmsYesNoDisplayText());
  }
}
