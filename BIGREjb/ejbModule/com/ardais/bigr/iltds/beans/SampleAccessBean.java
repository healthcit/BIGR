package com.ardais.bigr.iltds.beans;
import javax.rmi.*;
import com.ibm.ivj.ejb.runtime.*;
/**
 * SampleAccessBean
 * @generated
 */
public class SampleAccessBean
  extends AbstractEntityAccessBean
  implements com.ardais.bigr.iltds.beans.SampleAccessBeanData {
  /**
   * @generated
   */
  private Sample __ejbRef;
  /**
   * @generated
   */
  private java.lang.String init_sample_barcode_id;
  /**
   * @generated
   */
  private java.lang.String init_importedYN;
  /**
   * @generated
   */
  private java.lang.String init_sampleTypeCid;
  /**
   * getPullYN
   * @generated
   */
  public java.lang.String getPullYN()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.String) __getCache("pullYN")));
  }
  /**
   * setPullYN
   * @generated
   */
  public void setPullYN(java.lang.String newValue) {
    __setCache("pullYN", newValue);
  }
  /**
   * getHistoMaxThicknessPfinCid
   * @generated
   */
  public java.lang.String getHistoMaxThicknessPfinCid()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.String) __getCache("histoMaxThicknessPfinCid")));
  }
  /**
   * setHistoMaxThicknessPfinCid
   * @generated
   */
  public void setHistoMaxThicknessPfinCid(java.lang.String newValue) {
    __setCache("histoMaxThicknessPfinCid", newValue);
  }
  /**
   * getOtherHistoReembedReason
   * @generated
   */
  public java.lang.String getOtherHistoReembedReason()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.String) __getCache("otherHistoReembedReason")));
  }
  /**
   * setOtherHistoReembedReason
   * @generated
   */
  public void setOtherHistoReembedReason(java.lang.String newValue) {
    __setCache("otherHistoReembedReason", newValue);
  }
  /**
   * getProject_status_date
   * @generated
   */
  public java.sql.Timestamp getProject_status_date()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.sql.Timestamp) __getCache("project_status_date")));
  }
  /**
   * getDiagnosisCuiBest
   * @generated
   */
  public java.lang.String getDiagnosisCuiBest()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.String) __getCache("diagnosisCuiBest")));
  }
  /**
   * getHoursInFixative
   * @generated
   */
  public java.lang.Integer getHoursInFixative()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.Integer) __getCache("hoursInFixative")));
  }
  /**
   * setHoursInFixative
   * @generated
   */
  public void setHoursInFixative(java.lang.Integer newValue) {
    __setCache("hoursInFixative", newValue);
  }
  /**
   * getBufferTypeCui
   * @generated
   */
  public java.lang.String getBufferTypeCui()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.String) __getCache("bufferTypeCui")));
  }
  /**
   * setBufferTypeCui
   * @generated
   */
  public void setBufferTypeCui(java.lang.String newValue) {
    __setCache("bufferTypeCui", newValue);
  }
  /**
   * getDiMinThicknessPfinCid
   * @generated
   */
  public java.lang.String getDiMinThicknessPfinCid()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.String) __getCache("diMinThicknessPfinCid")));
  }
  /**
   * setDiMinThicknessPfinCid
   * @generated
   */
  public void setDiMinThicknessPfinCid(java.lang.String newValue) {
    __setCache("diMinThicknessPfinCid", newValue);
  }
  /**
   * getProject_status
   * @generated
   */
  public java.lang.String getProject_status()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.String) __getCache("project_status")));
  }
  /**
   * getReleasedYN
   * @generated
   */
  public java.lang.String getReleasedYN()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.String) __getCache("releasedYN")));
  }
  /**
   * setReleasedYN
   * @generated
   */
  public void setReleasedYN(java.lang.String newValue) {
    __setCache("releasedYN", newValue);
  }
  /**
   * getHistoNotes
   * @generated
   */
  public java.lang.String getHistoNotes()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.String) __getCache("histoNotes")));
  }
  /**
   * setHistoNotes
   * @generated
   */
  public void setHistoNotes(java.lang.String newValue) {
    __setCache("histoNotes", newValue);
  }
  /**
   * getSales_status_date
   * @generated
   */
  public java.sql.Timestamp getSales_status_date()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.sql.Timestamp) __getCache("sales_status_date")));
  }
  /**
   * getFormatDetailCid
   * @generated
   */
  public java.lang.String getFormatDetailCid()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.String) __getCache("formatDetailCid")));
  }
  /**
   * setFormatDetailCid
   * @generated
   */
  public void setFormatDetailCid(java.lang.String newValue) {
    __setCache("formatDetailCid", newValue);
  }
  /**
   * getQc_verified_date
   * @generated
   */
  public java.sql.Timestamp getQc_verified_date()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.sql.Timestamp) __getCache("qc_verified_date")));
  }
  /**
   * getCollection_datetime
   * @generated
   */
  public java.sql.Timestamp getCollection_datetime()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.sql.Timestamp) __getCache("collection_datetime")));
  }
  /**
   * setCollection_datetime
   * @generated
   */
  public void setCollection_datetime(java.sql.Timestamp newValue) {
    __setCache("collection_datetime", newValue);
  }
  /**
   * getReceiptDate
   * @generated
   */
  public java.sql.Timestamp getReceiptDate()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.sql.Timestamp) __getCache("receiptDate")));
  }
  /**
   * setReceiptDate
   * @generated
   */
  public void setReceiptDate(java.sql.Timestamp newValue) {
    __setCache("receiptDate", newValue);
  }
  /**
   * getOtherParaffinFeature
   * @generated
   */
  public java.lang.String getOtherParaffinFeature()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.String) __getCache("otherParaffinFeature")));
  }
  /**
   * setOtherParaffinFeature
   * @generated
   */
  public void setOtherParaffinFeature(java.lang.String newValue) {
    __setCache("otherParaffinFeature", newValue);
  }
  /**
   * getPullDate
   * @generated
   */
  public java.sql.Timestamp getPullDate()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.sql.Timestamp) __getCache("pullDate")));
  }
  /**
   * setPullDate
   * @generated
   */
  public void setPullDate(java.sql.Timestamp newValue) {
    __setCache("pullDate", newValue);
  }
  /**
   * getSales_status
   * @generated
   */
  public java.lang.String getSales_status()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.String) __getCache("sales_status")));
  }
  /**
   * getSampleSizeMeetsSpecs
   * @generated
   */
  public java.lang.String getSampleSizeMeetsSpecs()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.String) __getCache("sampleSizeMeetsSpecs")));
  }
  /**
   * setSampleSizeMeetsSpecs
   * @generated
   */
  public void setSampleSizeMeetsSpecs(java.lang.String newValue) {
    __setCache("sampleSizeMeetsSpecs", newValue);
  }
  /**
   * getDiMaxThicknessPfinCid
   * @generated
   */
  public java.lang.String getDiMaxThicknessPfinCid()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.String) __getCache("diMaxThicknessPfinCid")));
  }
  /**
   * setDiMaxThicknessPfinCid
   * @generated
   */
  public void setDiMaxThicknessPfinCid(java.lang.String newValue) {
    __setCache("diMaxThicknessPfinCid", newValue);
  }
  /**
   * getPullReason
   * @generated
   */
  public java.lang.String getPullReason()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.String) __getCache("pullReason")));
  }
  /**
   * setPullReason
   * @generated
   */
  public void setPullReason(java.lang.String newValue) {
    __setCache("pullReason", newValue);
  }
  /**
   * getTissueOriginCid
   * @generated
   */
  public java.lang.String getTissueOriginCid()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.String) __getCache("tissueOriginCid")));
  }
  /**
   * getPreservation_datetime
   * @generated
   */
  public java.sql.Timestamp getPreservation_datetime()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.sql.Timestamp) __getCache("preservation_datetime")));
  }
  /**
   * setPreservation_datetime
   * @generated
   */
  public void setPreservation_datetime(java.sql.Timestamp newValue) {
    __setCache("preservation_datetime", newValue);
  }
  /**
   * getQc_status_date
   * @generated
   */
  public java.sql.Timestamp getQc_status_date()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.sql.Timestamp) __getCache("qc_status_date")));
  }
  /**
   * getQcpostedYN
   * @generated
   */
  public java.lang.String getQcpostedYN()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.String) __getCache("qcpostedYN")));
  }
  /**
   * setQcpostedYN
   * @generated
   */
  public void setQcpostedYN(java.lang.String newValue) {
    __setCache("qcpostedYN", newValue);
  }
  /**
   * getConsentId
   * @generated
   */
  public java.lang.String getConsentId()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.String) __getCache("consentId")));
  }
  /**
   * getTissueOriginOther
   * @generated
   */
  public java.lang.String getTissueOriginOther()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.String) __getCache("tissueOriginOther")));
  }
  /**
   * getSampleboxKey
   * @generated
   */
  public com.ardais.bigr.iltds.beans.SampleboxKey getSampleboxKey()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((com.ardais.bigr.iltds.beans.SampleboxKey) __getCache("sampleboxKey")));
  }
  /**
   * getPreservation_datetime_dpc
   * @generated
   */
  public java.lang.String getPreservation_datetime_dpc()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.String) __getCache("preservation_datetime_dpc")));
  }
  /**
   * setPreservation_datetime_dpc
   * @generated
   */
  public void setPreservation_datetime_dpc(java.lang.String newValue) {
    __setCache("preservation_datetime_dpc", newValue);
  }
  /**
   * getVolume
   * @generated
   */
  public java.math.BigDecimal getVolume()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.math.BigDecimal) __getCache("volume")));
  }
  /**
   * setVolume
   * @generated
   */
  public void setVolume(java.math.BigDecimal newValue) {
    __setCache("volume", newValue);
  }
  /**
   * getInv_status_date
   * @generated
   */
  public java.sql.Timestamp getInv_status_date()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.sql.Timestamp) __getCache("inv_status_date")));
  }
  /**
   * getSubdivision_date
   * @generated
   */
  public java.sql.Timestamp getSubdivision_date()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.sql.Timestamp) __getCache("subdivision_date")));
  }
  /**
   * setSubdivision_date
   * @generated
   */
  public void setSubdivision_date(java.sql.Timestamp newValue) {
    __setCache("subdivision_date", newValue);
  }
  /**
   * getAsm_position
   * @generated
   */
  public java.lang.String getAsm_position()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.String) __getCache("asm_position")));
  }
  /**
   * setAsm_position
   * @generated
   */
  public void setAsm_position(java.lang.String newValue) {
    __setCache("asm_position", newValue);
  }
  /**
   * getSampleTypeCid
   * @generated
   */
  public java.lang.String getSampleTypeCid()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.String) __getCache("sampleTypeCid")));
  }
  /**
   * setSampleTypeCid
   * @generated
   */
  public void setSampleTypeCid(java.lang.String newValue) {
    __setCache("sampleTypeCid", newValue);
  }
  /**
   * getQc_status
   * @generated
   */
  public java.lang.String getQc_status()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.String) __getCache("qc_status")));
  }
  /**
   * getBms_sample
   * @generated
   */
  public java.lang.String getBms_sample()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.String) __getCache("bms_sample")));
  }
  /**
   * getInv_status
   * @generated
   */
  public java.lang.String getInv_status()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.String) __getCache("inv_status")));
  }
  /**
   * getSectionCount
   * @generated
   */
  public java.lang.Integer getSectionCount()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.Integer) __getCache("sectionCount")));
  }
  /**
   * getTissueFindingOther
   * @generated
   */
  public java.lang.String getTissueFindingOther()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.String) __getCache("tissueFindingOther")));
  }
  /**
   * getCustomerId
   * @generated
   */
  public java.lang.String getCustomerId()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.String) __getCache("customerId")));
  }
  /**
   * setCustomerId
   * @generated
   */
  public void setCustomerId(java.lang.String newValue) {
    __setCache("customerId", newValue);
  }
  /**
   * getCell_ref_location
   * @generated
   */
  public java.lang.String getCell_ref_location()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.String) __getCache("cell_ref_location")));
  }
  /**
   * setCell_ref_location
   * @generated
   */
  public void setCell_ref_location(java.lang.String newValue) {
    __setCache("cell_ref_location", newValue);
  }
  /**
   * getBufferTypeOther
   * @generated
   */
  public java.lang.String getBufferTypeOther()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.String) __getCache("bufferTypeOther")));
  }
  /**
   * setBufferTypeOther
   * @generated
   */
  public void setBufferTypeOther(java.lang.String newValue) {
    __setCache("bufferTypeOther", newValue);
  }
  /**
   * getOrphan_datetime
   * @generated
   */
  public java.sql.Timestamp getOrphan_datetime()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.sql.Timestamp) __getCache("orphan_datetime")));
  }
  /**
   * setOrphan_datetime
   * @generated
   */
  public void setOrphan_datetime(java.sql.Timestamp newValue) {
    __setCache("orphan_datetime", newValue);
  }
  /**
   * getImportedYN
   * @generated
   */
  public java.lang.String getImportedYN()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.String) __getCache("importedYN")));
  }
  /**
   * setImportedYN
   * @generated
   */
  public void setImportedYN(java.lang.String newValue) {
    __setCache("importedYN", newValue);
  }
  /**
   * getCellsPerMl
   * @generated
   */
  public java.math.BigDecimal getCellsPerMl()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.math.BigDecimal) __getCache("cellsPerMl")));
  }
  /**
   * setCellsPerMl
   * @generated
   */
  public void setCellsPerMl(java.math.BigDecimal newValue) {
    __setCache("cellsPerMl", newValue);
  }
  /**
   * getTotalNumOfCells
   * @generated
   */
  public java.math.BigDecimal getTotalNumOfCells()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.math.BigDecimal) __getCache("totalNumOfCells")));
  }
  /**
   * setTotalNumOfCells
   * @generated
   */
  public void setTotalNumOfCells(java.math.BigDecimal newValue) {
    __setCache("totalNumOfCells", newValue);
  }
  /**
   * getDiagnosisOtherBest
   * @generated
   */
  public java.lang.String getDiagnosisOtherBest()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.String) __getCache("diagnosisOtherBest")));
  }
  /**
   * getAllocation_ind
   * @generated
   */
  public java.lang.String getAllocation_ind()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.String) __getCache("allocation_ind")));
  }
  /**
   * setAllocation_ind
   * @generated
   */
  public void setAllocation_ind(java.lang.String newValue) {
    __setCache("allocation_ind", newValue);
  }
  /**
   * getTissueFindingCid
   * @generated
   */
  public java.lang.String getTissueFindingCid()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.String) __getCache("tissueFindingCid")));
  }
  /**
   * getDiscordantYN
   * @generated
   */
  public java.lang.String getDiscordantYN()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.String) __getCache("discordantYN")));
  }
  /**
   * setDiscordantYN
   * @generated
   */
  public void setDiscordantYN(java.lang.String newValue) {
    __setCache("discordantYN", newValue);
  }
  /**
   * getLastknownlocationid
   * @generated
   */
  public java.lang.String getLastknownlocationid()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.String) __getCache("lastknownlocationid")));
  }
  /**
   * setLastknownlocationid
   * @generated
   */
  public void setLastknownlocationid(java.lang.String newValue) {
    __setCache("lastknownlocationid", newValue);
  }
  /**
   * getType_of_fixative
   * @generated
   */
  public java.lang.String getType_of_fixative()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.String) __getCache("type_of_fixative")));
  }
  /**
   * setType_of_fixative
   * @generated
   */
  public void setType_of_fixative(java.lang.String newValue) {
    __setCache("type_of_fixative", newValue);
  }
  /**
   * getBorn_date
   * @generated
   */
  public java.sql.Timestamp getBorn_date()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.sql.Timestamp) __getCache("born_date")));
  }
  /**
   * setBorn_date
   * @generated
   */
  public void setBorn_date(java.sql.Timestamp newValue) {
    __setCache("born_date", newValue);
  }
  /**
   * getCollection_datetime_dpc
   * @generated
   */
  public java.lang.String getCollection_datetime_dpc()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.String) __getCache("collection_datetime_dpc")));
  }
  /**
   * setCollection_datetime_dpc
   * @generated
   */
  public void setCollection_datetime_dpc(java.lang.String newValue) {
    __setCache("collection_datetime_dpc", newValue);
  }
  /**
   * getTotalNumOfCellsExRepCui
   * @generated
   */
  public java.lang.String getTotalNumOfCellsExRepCui()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.String) __getCache("totalNumOfCellsExRepCui")));
  }
  /**
   * setTotalNumOfCellsExRepCui
   * @generated
   */
  public void setTotalNumOfCellsExRepCui(java.lang.String newValue) {
    __setCache("totalNumOfCellsExRepCui", newValue);
  }
  /**
   * getParent_barcode_id
   * @generated
   */
  public java.lang.String getParent_barcode_id()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.String) __getCache("parent_barcode_id")));
  }
  /**
   * setParent_barcode_id
   * @generated
   */
  public void setParent_barcode_id(java.lang.String newValue) {
    __setCache("parent_barcode_id", newValue);
  }
  /**
   * getParaffinFeatureCid
   * @generated
   */
  public java.lang.String getParaffinFeatureCid()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.String) __getCache("paraffinFeatureCid")));
  }
  /**
   * setParaffinFeatureCid
   * @generated
   */
  public void setParaffinFeatureCid(java.lang.String newValue) {
    __setCache("paraffinFeatureCid", newValue);
  }
  /**
   * getHistoWidthAcrossPfinCid
   * @generated
   */
  public java.lang.String getHistoWidthAcrossPfinCid()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.String) __getCache("histoWidthAcrossPfinCid")));
  }
  /**
   * setHistoWidthAcrossPfinCid
   * @generated
   */
  public void setHistoWidthAcrossPfinCid(java.lang.String newValue) {
    __setCache("histoWidthAcrossPfinCid", newValue);
  }
  /**
   * getAsm_asm_id
   * @generated
   */
  public java.lang.String getAsm_asm_id()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.String) __getCache("asm_asm_id")));
  }
  /**
   * setAsm_asm_id
   * @generated
   */
  public void setAsm_asm_id(java.lang.String newValue) {
    __setCache("asm_asm_id", newValue);
  }
  /**
   * getDiWidthAcrossPfinCid
   * @generated
   */
  public java.lang.String getDiWidthAcrossPfinCid()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.String) __getCache("diWidthAcrossPfinCid")));
  }
  /**
   * setDiWidthAcrossPfinCid
   * @generated
   */
  public void setDiWidthAcrossPfinCid(java.lang.String newValue) {
    __setCache("diWidthAcrossPfinCid", newValue);
  }
  /**
   * getSamplebox_box_barcode_id
   * @generated
   */
  public java.lang.String getSamplebox_box_barcode_id()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.String) __getCache("samplebox_box_barcode_id")));
  }
  /**
   * setSamplebox_box_barcode_id
   * @generated
   */
  public void setSamplebox_box_barcode_id(java.lang.String newValue) {
    __setCache("samplebox_box_barcode_id", newValue);
  }
  /**
   * getAsm_note
   * @generated
   */
  public java.lang.String getAsm_note()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.String) __getCache("asm_note")));
  }
  /**
   * setAsm_note
   * @generated
   */
  public void setAsm_note(java.lang.String newValue) {
    __setCache("asm_note", newValue);
  }
  /**
   * getAppearanceBest
   * @generated
   */
  public java.lang.String getAppearanceBest()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.String) __getCache("appearanceBest")));
  }
  /**
   * getAsmKey
   * @generated
   */
  public com.ardais.bigr.iltds.beans.AsmKey getAsmKey()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((com.ardais.bigr.iltds.beans.AsmKey) __getCache("asmKey")));
  }
  /**
   * getHistoMinThicknessPfinCid
   * @generated
   */
  public java.lang.String getHistoMinThicknessPfinCid()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.String) __getCache("histoMinThicknessPfinCid")));
  }
  /**
   * setHistoMinThicknessPfinCid
   * @generated
   */
  public void setHistoMinThicknessPfinCid(java.lang.String newValue) {
    __setCache("histoMinThicknessPfinCid", newValue);
  }
  /**
   * getHistoReembedReasonCid
   * @generated
   */
  public java.lang.String getHistoReembedReasonCid()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.String) __getCache("histoReembedReasonCid")));
  }
  /**
   * setHistoReembedReasonCid
   * @generated
   */
  public void setHistoReembedReasonCid(java.lang.String newValue) {
    __setCache("histoReembedReasonCid", newValue);
  }
  /**
   * getBarcode_scan_datetime
   * @generated
   */
  public java.sql.Timestamp getBarcode_scan_datetime()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.sql.Timestamp) __getCache("barcode_scan_datetime")));
  }
  /**
   * setBarcode_scan_datetime
   * @generated
   */
  public void setBarcode_scan_datetime(java.sql.Timestamp newValue) {
    __setCache("barcode_scan_datetime", newValue);
  }
  /**
   * getArdais_acct_key
   * @generated
   */
  public java.lang.String getArdais_acct_key()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.String) __getCache("ardais_acct_key")));
  }
  /**
   * setArdais_acct_key
   * @generated
   */
  public void setArdais_acct_key(java.lang.String newValue) {
    __setCache("ardais_acct_key", newValue);
  }
  /**
   * getHistoSubdividable
   * @generated
   */
  public java.lang.String getHistoSubdividable()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.String) __getCache("histoSubdividable")));
  }
  /**
   * setHistoSubdividable
   * @generated
   */
  public void setHistoSubdividable(java.lang.String newValue) {
    __setCache("histoSubdividable", newValue);
  }
  /**
   * getArdaisId
   * @generated
   */
  public java.lang.String getArdaisId()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.String) __getCache("ardaisId")));
  }
  /**
   * getQc_verified
   * @generated
   */
  public java.lang.String getQc_verified()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.String) __getCache("qc_verified")));
  }
  /**
   * setInit_sample_barcode_id
   * @generated
   */
  public void setInit_sample_barcode_id(java.lang.String newValue) {
    this.init_sample_barcode_id = (newValue);
  }
  /**
   * setInit_importedYN
   * @generated
   */
  public void setInit_importedYN(java.lang.String newValue) {
    this.init_importedYN = (newValue);
  }
  /**
   * setInit_sampleTypeCid
   * @generated
   */
  public void setInit_sampleTypeCid(java.lang.String newValue) {
    this.init_sampleTypeCid = (newValue);
  }
  /**
   * SampleAccessBean
   * @generated
   */
  public SampleAccessBean() {
    super();
  }
  /**
   * SampleAccessBean
   * @generated
   */
  public SampleAccessBean(javax.ejb.EJBObject o) throws java.rmi.RemoteException {
    super(o);
  }
  /**
   * defaultJNDIName
   * @generated
   */
  public String defaultJNDIName() {
    return "com/ardais/bigr/iltds/beans/Sample";
  }
  /**
   * ejbHome
   * @generated
   */
  private com.ardais.bigr.iltds.beans.SampleHome ejbHome()
    throws java.rmi.RemoteException,
    javax.naming.NamingException {
    return (com.ardais.bigr.iltds.beans.SampleHome) PortableRemoteObject.narrow(
      getHome(),
      com.ardais.bigr.iltds.beans.SampleHome.class);
  }
  /**
   * ejbRef
   * @generated
   */
  private com.ardais.bigr.iltds.beans.Sample ejbRef() throws java.rmi.RemoteException {
    if (ejbRef == null)
      return null;
    if (__ejbRef == null)
      __ejbRef = (com.ardais.bigr.iltds.beans.Sample) PortableRemoteObject.narrow(
        ejbRef,
        com.ardais.bigr.iltds.beans.Sample.class);

    return __ejbRef;
  }
  /**
   * instantiateEJB
   * @generated
   */
  protected void instantiateEJB()
    throws javax.naming.NamingException,
    javax.ejb.CreateException,
    java.rmi.RemoteException {
    if (ejbRef() != null)
      return;

    ejbRef = ejbHome().create(init_sample_barcode_id, init_importedYN, init_sampleTypeCid);
  }
  /**
   * instantiateEJBByPrimaryKey
   * @generated
   */
  protected boolean instantiateEJBByPrimaryKey()
    throws javax.ejb.CreateException,
    java.rmi.RemoteException,
    javax.naming.NamingException {
    boolean result = false;

    if (ejbRef() != null)
      return true;

    try {
      com.ardais.bigr.iltds.beans.SampleKey pKey = (com.ardais.bigr.iltds.beans.SampleKey) this
        .__getKey();
      if (pKey != null) {
        ejbRef = ejbHome().findByPrimaryKey(pKey);
        result = true;
      }
    } catch (javax.ejb.FinderException e) {
    }
    return result;
  }
  /**
   * refreshCopyHelper
   * @generated
   */
  public void refreshCopyHelper()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    refreshCopyHelper(ejbRef());
  }
  /**
   * commitCopyHelper
   * @generated
   */
  public void commitCopyHelper()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    commitCopyHelper(ejbRef());
  }
  /**
   * findSampleByAsm
   * @generated
   */
  public java.util.Enumeration findSampleByAsm(com.ardais.bigr.iltds.beans.AsmKey inKey)
    throws javax.naming.NamingException,
    javax.ejb.FinderException,
    java.rmi.RemoteException {
    com.ardais.bigr.iltds.beans.SampleHome localHome = ejbHome();
    java.util.Enumeration ejbs = localHome.findSampleByAsm(inKey);
    return (java.util.Enumeration) createAccessBeans(ejbs);
  }
  /**
   * findAll
   * @generated
   */
  public java.util.Enumeration findAll()
    throws javax.naming.NamingException,
    javax.ejb.FinderException,
    java.rmi.RemoteException {
    com.ardais.bigr.iltds.beans.SampleHome localHome = ejbHome();
    java.util.Enumeration ejbs = localHome.findAll();
    return (java.util.Enumeration) createAccessBeans(ejbs);
  }
  /**
   * findSampleBySamplebox
   * @generated
   */
  public java.util.Enumeration findSampleBySamplebox(com.ardais.bigr.iltds.beans.SampleboxKey inKey)
    throws javax.naming.NamingException,
    javax.ejb.FinderException,
    java.rmi.RemoteException {
    com.ardais.bigr.iltds.beans.SampleHome localHome = ejbHome();
    java.util.Enumeration ejbs = localHome.findSampleBySamplebox(inKey);
    return (java.util.Enumeration) createAccessBeans(ejbs);
  }
  /**
   * SampleAccessBean
   * @generated
   */
  public SampleAccessBean(com.ardais.bigr.iltds.beans.SampleKey key)
    throws javax.naming.NamingException, javax.ejb.FinderException, javax.ejb.CreateException,
    java.rmi.RemoteException {
    ejbRef = ejbHome().findByPrimaryKey(key);
  }
  /**
   * getSamplebox
   * @generated
   */
  public com.ardais.bigr.iltds.beans.SampleboxAccessBean getSamplebox()
    throws javax.naming.NamingException,
    javax.ejb.FinderException,
    javax.ejb.CreateException,
    java.rmi.RemoteException {
    instantiateEJB();
    com.ardais.bigr.iltds.beans.Samplebox localEJBRef = ejbRef().getSamplebox();
    if (localEJBRef != null)
      return new com.ardais.bigr.iltds.beans.SampleboxAccessBean(localEJBRef);
    else
      return null;
  }
  /**
   * setAsm
   * @generated
   */
  public void setAsm(com.ardais.bigr.iltds.beans.Asm anAsm)
    throws javax.naming.NamingException,
    javax.ejb.CreateException,
    java.rmi.RemoteException {
    instantiateEJB();
    ejbRef().setAsm(anAsm);
  }
  /**
   * privateSetSampleboxKey
   * @generated
   */
  public void privateSetSampleboxKey(com.ardais.bigr.iltds.beans.SampleboxKey inKey)
    throws javax.naming.NamingException,
    javax.ejb.CreateException,
    java.rmi.RemoteException {
    instantiateEJB();
    ejbRef().privateSetSampleboxKey(inKey);
  }
  /**
   * secondaryAddSamplestatus
   * @generated
   */
  public void secondaryAddSamplestatus(com.ardais.bigr.iltds.beans.Samplestatus aSamplestatus)
    throws javax.naming.NamingException,
    javax.ejb.CreateException,
    java.rmi.RemoteException {
    instantiateEJB();
    ejbRef().secondaryAddSamplestatus(aSamplestatus);
  }
  /**
   * getSamplestatus
   * @generated
   */
  public java.util.Enumeration getSamplestatus()
    throws javax.naming.NamingException,
    javax.ejb.FinderException,
    javax.ejb.CreateException,
    java.rmi.RemoteException {
    instantiateEJB();
    return ejbRef().getSamplestatus();
  }
  /**
   * setSamplebox
   * @generated
   */
  public void setSamplebox(com.ardais.bigr.iltds.beans.Samplebox aSamplebox)
    throws javax.naming.NamingException,
    javax.ejb.CreateException,
    java.rmi.RemoteException {
    instantiateEJB();
    ejbRef().setSamplebox(aSamplebox);
  }
  /**
   * secondaryRemoveSamplestatus
   * @generated
   */
  public void secondaryRemoveSamplestatus(com.ardais.bigr.iltds.beans.Samplestatus aSamplestatus)
    throws javax.naming.NamingException,
    javax.ejb.CreateException,
    java.rmi.RemoteException {
    instantiateEJB();
    ejbRef().secondaryRemoveSamplestatus(aSamplestatus);
  }
  /**
   * privateSetAsmKey
   * @generated
   */
  public void privateSetAsmKey(com.ardais.bigr.iltds.beans.AsmKey inKey)
    throws javax.naming.NamingException,
    javax.ejb.CreateException,
    java.rmi.RemoteException {
    instantiateEJB();
    ejbRef().privateSetAsmKey(inKey);
  }
  /**
   * getAsm
   * @generated
   */
  public com.ardais.bigr.iltds.beans.AsmAccessBean getAsm()
    throws javax.naming.NamingException,
    javax.ejb.FinderException,
    javax.ejb.CreateException,
    java.rmi.RemoteException {
    instantiateEJB();
    com.ardais.bigr.iltds.beans.Asm localEJBRef = ejbRef().getAsm();
    if (localEJBRef != null)
      return new com.ardais.bigr.iltds.beans.AsmAccessBean(localEJBRef);
    else
      return null;
  }
  /**
   * secondarySetSamplebox
   * @generated
   */
  public void secondarySetSamplebox(com.ardais.bigr.iltds.beans.Samplebox aSamplebox)
    throws javax.naming.NamingException,
    javax.ejb.CreateException,
    java.rmi.RemoteException {
    instantiateEJB();
    ejbRef().secondarySetSamplebox(aSamplebox);
  }
  /**
   * secondarySetAsm
   * @generated
   */
  public void secondarySetAsm(com.ardais.bigr.iltds.beans.Asm anAsm)
    throws javax.naming.NamingException,
    javax.ejb.CreateException,
    java.rmi.RemoteException {
    instantiateEJB();
    ejbRef().secondarySetAsm(anAsm);
  }
  /**
   * addSamplestatus
   * @generated
   */
  public void addSamplestatus(com.ardais.bigr.iltds.beans.Samplestatus aSamplestatus)
    throws javax.naming.NamingException,
    javax.ejb.CreateException,
    java.rmi.RemoteException {
    instantiateEJB();
    ejbRef().addSamplestatus(aSamplestatus);
  }
  /**
   * getPercentViability
   * @generated
   */
  public java.lang.Integer getPercentViability()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.Integer) __getCache("percentViability")));
  }
  /**
   * setPercentViability
   * @generated
   */
  public void setPercentViability(java.lang.Integer newValue) {
    __setCache("percentViability", newValue);
  }
  /**
   * getSource
   * @generated
   */
  public java.lang.String getSource()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.String) __getCache("source")));
  }
  /**
   * setSource
   * @generated
   */
  public void setSource(java.lang.String newValue) {
    __setCache("source", newValue);
  }
  /**
   * getYield
   * @generated
   */
  public java.math.BigDecimal getYield()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.math.BigDecimal) __getCache("yield")));
  }
  /**
   * setYield
   * @generated
   */
  public void setYield(java.math.BigDecimal newValue) {
    __setCache("yield", newValue);
  }
  /**
   * getConcentration
   * @generated
   */
  public java.math.BigDecimal getConcentration()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.math.BigDecimal) __getCache("concentration")));
  }
  /**
   * setConcentration
   * @generated
   */
  public void setConcentration(java.math.BigDecimal newValue) {
    __setCache("concentration", newValue);
  }
  /**
   * getVolumeUnitCui
   * @generated
   */
  public java.lang.String getVolumeUnitCui()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.String) __getCache("volumeUnitCui")));
  }
  /**
   * setVolumeUnitCui
   * @generated
   */
  public void setVolumeUnitCui(java.lang.String newValue) {
    __setCache("volumeUnitCui", newValue);
  }
  /**
   * getWeightInMg
   * @generated
   */
  public java.math.BigDecimal getWeightInMg()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.math.BigDecimal) __getCache("weightInMg")));
  }
  /**
   * setWeightInMg
   * @generated
   */
  public void setWeightInMg(java.math.BigDecimal newValue) {
    __setCache("weightInMg", newValue);
  }
  /**
   * getWeight
   * @generated
   */
  public java.math.BigDecimal getWeight()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.math.BigDecimal) __getCache("weight")));
  }
  /**
   * setWeight
   * @generated
   */
  public void setWeight(java.math.BigDecimal newValue) {
    __setCache("weight", newValue);
  }
  /**
   * getVolumeInUl
   * @generated
   */
  public java.math.BigDecimal getVolumeInUl()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.math.BigDecimal) __getCache("volumeInUl")));
  }
  /**
   * setVolumeInUl
   * @generated
   */
  public void setVolumeInUl(java.math.BigDecimal newValue) {
    __setCache("volumeInUl", newValue);
  }
  /**
   * getWeightUnitCui
   * @generated
   */
  public java.lang.String getWeightUnitCui()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.String) __getCache("weightUnitCui")));
  }
  /**
   * setWeightUnitCui
   * @generated
   */
  public void setWeightUnitCui(java.lang.String newValue) {
    __setCache("weightUnitCui", newValue);
  }
  /**
   * getSampleRegistrationForm
   * @generated
   */
  public java.lang.String getSampleRegistrationForm()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.String) __getCache("sampleRegistrationForm")));
  }
  /**
   * setSampleRegistrationForm
   * @generated
   */
  public void setSampleRegistrationForm(java.lang.String newValue) {
    __setCache("sampleRegistrationForm", newValue);
  }
}
