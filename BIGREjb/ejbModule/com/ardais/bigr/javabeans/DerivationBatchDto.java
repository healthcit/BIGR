package com.ardais.bigr.javabeans;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.iltds.beans.ArdaisstaffAccessBean;
import com.ardais.bigr.iltds.beans.ArdaisstaffKey;
import com.ardais.bigr.iltds.helpers.DateHelper;

/**
 * Holds all data for a batch of derivation operations.
 */
public class DerivationBatchDto implements Serializable {

  private String _operationCui;
  private String _otherOperation;
  private Date _operationDate;
  private String _operationDateAsString;
  private String _preparedBy;
  private String _preparedByName;
  private List _derivationDtos;
  private String _childIdsStyle;

  /**
   * Creates a new DerivationBatchDto.
   */
  public DerivationBatchDto() {
    super();
  }

  /**
   * Creates a new DerivationBatchDto from the specified DerivationBatchDto instance.  This
   * performs a deep clone. 
   * 
   * @param  dto  the DerivationBatchDto to clone from  
   */
  public DerivationBatchDto(DerivationBatchDto dto) {
    this();

    setOperationCui(dto.getOperationCui());
    Date date = dto.getOperationDate();
    if (date == null) {
      setOperationDate(null);
    }
    else {
      setOperationDate(new Date(date.getTime()));
    }
    setOtherOperation(dto.getOtherOperation());
    setPreparedBy(dto.getPreparedBy());

    Iterator derivations = dto.getDerivations().iterator();
    while (derivations.hasNext()) {
      addDerivation(new DerivationDto((DerivationDto) derivations.next()));
    }
    setChildIdsStyle(dto.getChildIdsStyle());
  }

  public String getOperationCui() {
    return _operationCui;
  }

  public void setOperationCui(String string) {
    _operationCui = string;
  }

  public String getOtherOperation() {
    return _otherOperation;
  }

  public void setOtherOperation(String string) {
    _otherOperation = string;
  }

  public Date getOperationDate() {
    return _operationDate;
  }

  public void setOperationDate(Date date) {
    _operationDate = date;
    _operationDateAsString = ApiFunctions.sqlDateToString(date);
  }

  public String getOperationDateAsString() {
    return ApiFunctions.safeString(_operationDateAsString);
  }

  public void setOperationDateAsString(String date) {
    _operationDateAsString = date;
    _operationDate = ApiFunctions.safeDate(date);
  }

  public String getPreparedBy() {
    return _preparedBy;
  }

  public void setPreparedBy(String userId) {
    _preparedBy = userId;
    _preparedByName = null;
  }

  public DerivationDto getDerivation(int index) {    
    return (DerivationDto) getDerivations().get(index);
  }

  public void addDerivation(DerivationDto dto) {    
    getDerivations().add(dto); 
  }

  public List getDerivations() {
    if (_derivationDtos == null) {
      _derivationDtos = new ArrayList();
    }
    return _derivationDtos; 
  }

  public String getPreparedByName() {
    if (_preparedByName == null) {
      if (!ApiFunctions.isEmpty(_preparedBy)) {
        try {
          ArdaisstaffAccessBean staff = new ArdaisstaffAccessBean(new ArdaisstaffKey(_preparedBy));
          _preparedByName = (staff.getArdais_staff_fname() + " " + staff.getArdais_staff_lname());
        }
        catch (Exception e) {
          _preparedByName = "";
        }
      }
      else {
        _preparedByName = "";
      }
    }
    return _preparedByName;
  }

  
  public boolean isGenerateChildIds() {
    return "generated".equals(getChildIdsStyle());
  }
  
  public String getChildIdsStyle() {
    // return scanned by default.
    return (_childIdsStyle == null) ? "scanned" : _childIdsStyle;
  }
  public void setChildIdsStyle(String childIdsStyle) {
    _childIdsStyle = childIdsStyle;
  }
}
