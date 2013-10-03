package com.ardais.bigr.javabeans;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.iltds.beans.ArdaisstaffAccessBean;
import com.ardais.bigr.iltds.beans.ArdaisstaffKey;
import com.gulfstreambio.gboss.GbossFactory;

/**
 * Holds all data for a single derivation operation.
 */
public class DerivationDto implements Serializable {

  private String _derivationId;
  private String _operationCui;
  private String _otherOperation;
  private Date _operationDate;
  private String _operationDateAsString;
  private String _preparedBy;
  private String _preparedByName;
  private List _parents;
  private List _children;
  private String _inheritInventoryGroupsYN;
  private String[] _inventoryGroups;
  private String _derivationNumber;
  private String _generatedSampleCount;

  /**
   * Creates a new DerivationDto.
   */
  public DerivationDto() {
    super();
  }

  /**
   * Creates a new DerivationDto, initializing its basic details from the specified
   * DerivationBatchDto.  This in effect performs a shallow clone with information from the 
   * batch DTO. 
   * 
   * @param  batchDto  the DerivationBatchDto 
   */
  public DerivationDto(DerivationBatchDto batchDto) {
    this();
    populateEmptyBasicInformation(batchDto);
  }

  /**
   * Creates a new DerivationDto from the specified DerivationDto instance.  This
   * performs a deep clone. 
   * 
   * @param  dto  the DerivationBatchDto to clone from  
   */
  public DerivationDto(DerivationDto dto) {
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
    setDerivationId(dto.getDerivationId());
    setDerivationNumber(dto.getDerivationNumber());
    Iterator parents = dto.getParents().iterator();
    while (parents.hasNext()) {
      addParent(new SampleData((SampleData) parents.next()));
    }    
    Iterator children = dto.getChildren().iterator();
    while (children.hasNext()) {
      addChild(new SampleData((SampleData) children.next()));
    }
    setInheritInventoryGroupsYN(dto.getInheritInventoryGroupsYN());
    if (!ApiFunctions.isEmpty(dto.getInventoryGroups())) {
      int length = dto.getInventoryGroups().length;
      String[]inventoryGroups = new String[length];
      System.arraycopy(dto.getInventoryGroups(),0,inventoryGroups,0,length);
      setInventoryGroups(inventoryGroups);
    }
    setGeneratedSampleCount(dto.getGeneratedSampleCount());
  }

  /**
   * Populates any empty basic information data on this DerivationDto from the specified
   * DerivationBatchDto. 
   * 
   * @param  batchDto  the DerivationBatchDto
   */
  public void populateEmptyBasicInformation(DerivationBatchDto batchDto) {
    if (ApiFunctions.isEmpty(getOperationCui())) {
      setOperationCui(batchDto.getOperationCui());
    }
    if (getOperationDate() == null) {
      Date date = batchDto.getOperationDate();
      if (date == null) {
        setOperationDate(null);
      }
      else {
        setOperationDate(new Date(date.getTime()));
      }
    }
    if (ApiFunctions.isEmpty(getOtherOperation())) {
      setOtherOperation(batchDto.getOtherOperation());
    }
    if (ApiFunctions.isEmpty(getPreparedBy())) {
      setPreparedBy(batchDto.getPreparedBy());
    }
  }

  public String getDerivationId() {
    return _derivationId;
  }

  public void setDerivationId(String string) {
    _derivationId = string;
  }

  public String getOperation() {
    String operationCui = getOperationCui();
    if (!ApiFunctions.isEmpty(operationCui)) {
      return GbossFactory.getInstance().getDescription(operationCui);
    }
    else {
      return ApiFunctions.EMPTY_STRING;
    }
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

  public SampleData getParent(int index) {    
    return (SampleData) getParents().get(index);
  }

  public void addParent(SampleData sample) {
    getParents().add(sample);
  }

  public List getParents() {
    if (_parents == null) {
      _parents = new ArrayList();
    }
    return _parents;
  }

  public SampleData getChild(int index) {    
    return (SampleData) getChildren().get(index);
  }

  public List getChildren() {    
    if (_children == null) {
      _children = new ArrayList();
    }
    return _children;
  }

  public void addChild(SampleData sample) {
    getChildren().add(sample);
  }
  
  public String getDerivationNumber() {
    return _derivationNumber;
  }

  public void setDerivationNumber(String string) {
    _derivationNumber = string;
  }

  /**
   * @return
   */
  public String getInheritInventoryGroupsYN() {
    return _inheritInventoryGroupsYN;
  }

  /**
   * @param string
   */
  public void setInheritInventoryGroupsYN(String string) {
    _inheritInventoryGroupsYN = string;
  }

  /**
   * @return
   */
  public String[] getInventoryGroups() {
    return _inventoryGroups;
  }

  /**
   * @param strings
   */
  public void setInventoryGroups(String[] strings) {
    _inventoryGroups = strings;
  }
  
  public String getGeneratedSampleCount() {
    return _generatedSampleCount;
  }
  public void setGeneratedSampleCount(String generatedSampleCount) {
    _generatedSampleCount = generatedSampleCount;
  }
}
