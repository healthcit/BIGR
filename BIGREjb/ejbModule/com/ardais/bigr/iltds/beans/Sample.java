package com.ardais.bigr.iltds.beans;

/**
 * Remote interface for Enterprise Bean: Sample
 */
public interface Sample extends javax.ejb.EJBObject, com.ibm.ivj.ejb.runtime.CopyHelper {















/**
 * This method was generated for supporting the association named Samplestatus REFILTDS_SAMPLE25 Sample.  
 * 	It will be deleted/edited when the association is deleted/edited.
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
void addSamplestatus(com.ardais.bigr.iltds.beans.Samplestatus aSamplestatus) throws java.rmi.RemoteException;
/**
 * Getter method for allocation_ind
 * @return java.lang.String
 */
java.lang.String getAllocation_ind() throws java.rmi.RemoteException;
/**
 * 
 * @return java.lang.String
 */
java.lang.String getArdais_acct_key() throws java.rmi.RemoteException;
/**
 * This method was generated for supporting the association named Sample REFILTDS_ASM7 Asm.  
 * 	It will be deleted/edited when the association is deleted/edited.
 * @return com.ardais.bigr.iltds.beans.Asm
 * @exception javax.ejb.FinderException The exception description.
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
com.ardais.bigr.iltds.beans.Asm getAsm() throws java.rmi.RemoteException, javax.ejb.FinderException;
/**
 * Getter method for asm_note
 * @return java.lang.String
 */
java.lang.String getAsm_note() throws java.rmi.RemoteException;
/**
 * 
 * @return java.lang.String
 */
java.lang.String getAsm_position() throws java.rmi.RemoteException;
/**
 * This method was generated for supporting the association named Sample REFILTDS_ASM7 Asm.  
 * 	It will be deleted/edited when the association is deleted/edited.
 * @return com.ardais.bigr.iltds.beans.AsmKey
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
com.ardais.bigr.iltds.beans.AsmKey getAsmKey() throws java.rmi.RemoteException;
/**
 * Getter method for barcode_scan_datetime
 * @return java.sql.Timestamp
 */
java.sql.Timestamp getBarcode_scan_datetime() throws java.rmi.RemoteException;
/**
 * 
 * @return java.sql.Timestamp
 */
java.sql.Timestamp getBorn_date() throws java.rmi.RemoteException;
/**
 * Getter method for cell_ref_location
 * @return java.lang.String
 */
java.lang.String getCell_ref_location() throws java.rmi.RemoteException;
/**
 * 
 * @return String
 * @throws java.rmi.RemoteException
 */
String getConsentId() throws java.rmi.RemoteException;
/**
 * 
 * @return String
 * @throws java.rmi.RemoteException
 */
String getArdaisId() throws java.rmi.RemoteException;
/**
 * 
 * @return java.lang.String
 */
java.lang.String getInv_status() throws java.rmi.RemoteException;
/**
 * 
 * @return java.sql.Timestamp
 */
java.sql.Timestamp getInv_status_date() throws java.rmi.RemoteException;
/**
 * 
 * @return java.lang.String
 */
java.lang.String getTissueFindingCid() throws java.rmi.RemoteException;
/**
 * 
 * @return java.lang.String
 */
java.lang.String getTissueFindingOther() throws java.rmi.RemoteException;
/**
 * 
 * @return java.lang.String
 */
java.lang.String getAppearanceBest() throws java.rmi.RemoteException;
/**
 * 
 * @return java.lang.String
 */
java.lang.String getDiagnosisCuiBest() throws java.rmi.RemoteException;
/**
 * 
 * @return java.lang.String
 */
java.lang.String getDiagnosisOtherBest() throws java.rmi.RemoteException;
/**
 * 
 * @return java.lang.String
 */
java.lang.String getTissueOriginCid() throws java.rmi.RemoteException;
/**
 * 
 * @return java.lang.String
 */
java.lang.String getTissueOriginOther() throws java.rmi.RemoteException;
/**
 * Getter method for orphan_datetime
 * @return java.sql.Timestamp
 */
java.sql.Timestamp getOrphan_datetime() throws java.rmi.RemoteException;
/**
 * 
 * @return java.lang.String
 */
java.lang.String getParent_barcode_id() throws java.rmi.RemoteException;
/**
 * 
 * @return java.lang.String
 */
java.lang.String getProject_status() throws java.rmi.RemoteException;
/**
 * 
 * @return java.sql.Timestamp
 */
java.sql.Timestamp getProject_status_date() throws java.rmi.RemoteException;
/**
 * 
 * @return java.lang.String
 */
java.lang.String getQc_status() throws java.rmi.RemoteException;
/**
 * 
 * @return java.sql.Timestamp
 */
java.sql.Timestamp getQc_status_date() throws java.rmi.RemoteException;
/**
 * 
 * @return java.lang.String
 */
java.lang.String getQc_verified() throws java.rmi.RemoteException;
/**
 * 
 * @return java.sql.Timestamp
 */
java.sql.Timestamp getQc_verified_date() throws java.rmi.RemoteException;
/**
 * 
 * 
 * @return java.lang.String
 */
java.lang.String getSales_status() throws java.rmi.RemoteException;
/**
 * 
 * @return java.sql.Timestamp
 */
java.sql.Timestamp getSales_status_date() throws java.rmi.RemoteException;
/**
 * 
 * @return Integer
 */
Integer getSectionCount() throws java.rmi.RemoteException;
/**
 * This method was generated for supporting the association named Sample REFILTDS_SAMPLE_BOX10 Samplebox.  
 * 	It will be deleted/edited when the association is deleted/edited.
 * @return com.ardais.bigr.iltds.beans.Samplebox
 * @exception javax.ejb.FinderException The exception description.
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
com.ardais.bigr.iltds.beans.Samplebox getSamplebox() throws java.rmi.RemoteException, javax.ejb.FinderException;
/**
 * 
 * @return java.lang.String
 */
java.lang.String getSamplebox_box_barcode_id() throws java.rmi.RemoteException;
/**
 * This method was generated for supporting the association named Sample REFILTDS_SAMPLE_BOX10 Samplebox.  
 * 	It will be deleted/edited when the association is deleted/edited.
 * @return com.ardais.bigr.iltds.beans.SampleboxKey
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
com.ardais.bigr.iltds.beans.SampleboxKey getSampleboxKey() throws java.rmi.RemoteException;
/**
 * This method was generated for supporting the association named Samplestatus REFILTDS_SAMPLE25 Sample.  
 * 	It will be deleted/edited when the association is deleted/edited.
 * @return java.util.Enumeration
 * @exception javax.ejb.FinderException The exception description.
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
public java.util.Enumeration getSamplestatus() throws java.rmi.RemoteException, javax.ejb.FinderException;
/**
 * 
 * @return java.sql.Timestamp
 */
java.sql.Timestamp getSubdivision_date() throws java.rmi.RemoteException;
/**
 * Getter method for type_of_fixative
 * @return java.lang.String
 */
java.lang.String getType_of_fixative() throws java.rmi.RemoteException;
/**
 * This method was generated for supporting the association named Sample REFILTDS_ASM7 Asm.  
 * 	It will be deleted/edited when the association is deleted/edited.
 * @param inKey com.ardais.bigr.iltds.beans.AsmKey
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
void privateSetAsmKey(com.ardais.bigr.iltds.beans.AsmKey inKey) throws java.rmi.RemoteException;
/**
 * This method was generated for supporting the association named Sample REFILTDS_SAMPLE_BOX10 Samplebox.  
 * 	It will be deleted/edited when the association is deleted/edited.
 * @param inKey com.ardais.bigr.iltds.beans.SampleboxKey
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
void privateSetSampleboxKey(com.ardais.bigr.iltds.beans.SampleboxKey inKey) throws java.rmi.RemoteException;
/**
 * This method was generated for supporting the association named Samplestatus REFILTDS_SAMPLE25 Sample.  
 * 	It will be deleted/edited when the association is deleted/edited.
 * @param aSamplestatus com.ardais.bigr.iltds.beans.Samplestatus
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
public void secondaryAddSamplestatus(com.ardais.bigr.iltds.beans.Samplestatus aSamplestatus) throws java.rmi.RemoteException;
/**
 * This method was generated for supporting the association named Samplestatus REFILTDS_SAMPLE25 Sample.  
 * 	It will be deleted/edited when the association is deleted/edited.
 * @param aSamplestatus com.ardais.bigr.iltds.beans.Samplestatus
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
public void secondaryRemoveSamplestatus(com.ardais.bigr.iltds.beans.Samplestatus aSamplestatus) throws java.rmi.RemoteException;
/**
 * This method was generated for supporting the association named Sample REFILTDS_ASM7 Asm.  
 * 	It will be deleted/edited when the association is deleted/edited.
 * @param anAsm com.ardais.bigr.iltds.beans.Asm
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
void secondarySetAsm(com.ardais.bigr.iltds.beans.Asm anAsm) throws java.rmi.RemoteException;
/**
 * This method was generated for supporting the association named Sample REFILTDS_SAMPLE_BOX10 Samplebox.  
 * 	It will be deleted/edited when the association is deleted/edited.
 * @param aSamplebox com.ardais.bigr.iltds.beans.Samplebox
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
void secondarySetSamplebox(com.ardais.bigr.iltds.beans.Samplebox aSamplebox) throws java.rmi.RemoteException;
/**
 * Setter method for allocation_ind
 * @param newValue java.lang.String
 */
void setAllocation_ind(java.lang.String newValue) throws java.rmi.RemoteException;
/**
 * 
 * @return void
 * @param newValue java.lang.String
 */
void setArdais_acct_key(java.lang.String newValue) throws java.rmi.RemoteException;
/**
 * This method was generated for supporting the association named Sample REFILTDS_ASM7 Asm.  
 * 	It will be deleted/edited when the association is deleted/edited.
 * @param anAsm com.ardais.bigr.iltds.beans.Asm
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
void setAsm(com.ardais.bigr.iltds.beans.Asm anAsm) throws java.rmi.RemoteException;
/**
 * Setter method for asm_note
 * @param newValue java.lang.String
 */
void setAsm_note(java.lang.String newValue) throws java.rmi.RemoteException;
/**
 * 
 * @return void
 * @param newValue java.lang.String
 */
void setAsm_position(java.lang.String newValue) throws java.rmi.RemoteException;
/**
 * Setter method for barcode_scan_datetime
 * @param newValue java.sql.Timestamp
 */
void setBarcode_scan_datetime(java.sql.Timestamp newValue) throws java.rmi.RemoteException;
/**
 * 
 * @return void
 * @param newValue java.sql.Timestamp
 */
void setBorn_date(java.sql.Timestamp newValue) throws java.rmi.RemoteException;
/**
 * Setter method for cell_ref_location
 * @param newValue java.lang.String
 */
void setCell_ref_location(java.lang.String newValue) throws java.rmi.RemoteException;
/**
 * Setter method for orphan_datetime
 * @param newValue java.sql.Timestamp
 */
void setOrphan_datetime(java.sql.Timestamp newValue) throws java.rmi.RemoteException;
/**
 * 
 * @return void
 * @param newValue java.lang.String
 */
void setParent_barcode_id(java.lang.String newValue) throws java.rmi.RemoteException;
/**
 * This method was generated for supporting the association named Sample REFILTDS_SAMPLE_BOX10 Samplebox.  
 * 	It will be deleted/edited when the association is deleted/edited.
 * @param aSamplebox com.ardais.bigr.iltds.beans.Samplebox
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
void setSamplebox(com.ardais.bigr.iltds.beans.Samplebox aSamplebox) throws java.rmi.RemoteException;
/**
 * 
 * @return void
 * @param newSamplebox_box_barcode_id java.lang.String
 */
void setSamplebox_box_barcode_id(java.lang.String newSamplebox_box_barcode_id) throws java.rmi.RemoteException;
/**
 * 
 * @return void
 * @param newValue java.sql.Timestamp
 */
void setSubdivision_date(java.sql.Timestamp newValue) throws java.rmi.RemoteException;
/**
 * Setter method for type_of_fixative
 * @param newValue java.lang.String
 */
void setType_of_fixative(java.lang.String newValue) throws java.rmi.RemoteException;
	/**
	 * Get accessor for persistent attribute: pullYN
	 */
	public java.lang.String getPullYN() throws java.rmi.RemoteException;
	/**
	 * Set accessor for persistent attribute: pullYN
	 */
	public void setPullYN(java.lang.String newPullYN)
		throws java.rmi.RemoteException;
	/**
	 * Get accessor for persistent attribute: pullReason
	 */
	public java.lang.String getPullReason() throws java.rmi.RemoteException;
	/**
	 * Set accessor for persistent attribute: pullReason
	 */
	public void setPullReason(java.lang.String newPullReason)
		throws java.rmi.RemoteException;
	/**
	 * Get accessor for persistent attribute: releasedYN
	 */
	public java.lang.String getReleasedYN() throws java.rmi.RemoteException;
	/**
	 * Set accessor for persistent attribute: releasedYN
	 */
	public void setReleasedYN(java.lang.String newReleasedYN)
		throws java.rmi.RemoteException;
	/**
	 * Get accessor for persistent attribute: discordantYN
	 */
	public java.lang.String getDiscordantYN() throws java.rmi.RemoteException;
	/**
	 * Set accessor for persistent attribute: discordantYN
	 */
	public void setDiscordantYN(java.lang.String newDiscordantYN)
		throws java.rmi.RemoteException;
	/**
	 * Get accessor for persistent attribute: pullDate
	 */
	public java.sql.Timestamp getPullDate() throws java.rmi.RemoteException;
	/**
	 * Set accessor for persistent attribute: pullDate
	 */
	public void setPullDate(java.sql.Timestamp newPullDate)
		throws java.rmi.RemoteException;
	/**
	 * Get accessor for persistent attribute: diMinThicknessPfinCid
	 */
	public java.lang.String getDiMinThicknessPfinCid()
		throws java.rmi.RemoteException;
	/**
	 * Set accessor for persistent attribute: diMinThicknessPfinCid
	 */
	public void setDiMinThicknessPfinCid(
		java.lang.String newDiMinThicknessPfinCid)
		throws java.rmi.RemoteException;
	/**
	 * Get accessor for persistent attribute: diMaxThicknessPfinCid
	 */
	public java.lang.String getDiMaxThicknessPfinCid()
		throws java.rmi.RemoteException;
	/**
	 * Set accessor for persistent attribute: diMaxThicknessPfinCid
	 */
	public void setDiMaxThicknessPfinCid(
		java.lang.String newDiMaxThicknessPfinCid)
		throws java.rmi.RemoteException;
	/**
	 * Get accessor for persistent attribute: diWidthAcrossPfinCid
	 */
	public java.lang.String getDiWidthAcrossPfinCid()
		throws java.rmi.RemoteException;
	/**
	 * Set accessor for persistent attribute: diWidthAcrossPfinCid
	 */
	public void setDiWidthAcrossPfinCid(
		java.lang.String newDiWidthAcrossPfinCid)
		throws java.rmi.RemoteException;
	/**
	 * Get accessor for persistent attribute: formatDetailCid
	 */
	public java.lang.String getFormatDetailCid()
		throws java.rmi.RemoteException;
	/**
	 * Set accessor for persistent attribute: formatDetailCid
	 */
	public void setFormatDetailCid(java.lang.String newFormatDetailCid)
		throws java.rmi.RemoteException;
	/**
	 * Get accessor for persistent attribute: sampleSizeMeetsSpecs
	 */
	public java.lang.String getSampleSizeMeetsSpecs()
		throws java.rmi.RemoteException;
	/**
	 * Set accessor for persistent attribute: sampleSizeMeetsSpecs
	 */
	public void setSampleSizeMeetsSpecs(
		java.lang.String newSampleSizeMeetsSpecs)
		throws java.rmi.RemoteException;
	/**
	 * Get accessor for persistent attribute: hoursInFixative
	 */
	public java.lang.Integer getHoursInFixative()
		throws java.rmi.RemoteException;
	/**
	 * Set accessor for persistent attribute: hoursInFixative
	 */
	public void setHoursInFixative(java.lang.Integer newHoursInFixative)
		throws java.rmi.RemoteException;
	/**
	 * Get accessor for persistent attribute: qcpostedYN
	 */
	public java.lang.String getQcpostedYN() throws java.rmi.RemoteException;
	/**
	 * Set accessor for persistent attribute: qcpostedYN
	 */
	public void setQcpostedYN(java.lang.String newQcpostedYN)
		throws java.rmi.RemoteException;
	/**
	 * Get accessor for persistent attribute: histoMinThicknessPfinCid
	 */
	public java.lang.String getHistoMinThicknessPfinCid()
		throws java.rmi.RemoteException;
	/**
	 * Set accessor for persistent attribute: histoMinThicknessPfinCid
	 */
	public void setHistoMinThicknessPfinCid(
		java.lang.String newHistoMinThicknessPfinCid)
		throws java.rmi.RemoteException;
	/**
	 * Get accessor for persistent attribute: histoMaxThicknessPfinCid
	 */
	public java.lang.String getHistoMaxThicknessPfinCid()
		throws java.rmi.RemoteException;
	/**
	 * Set accessor for persistent attribute: histoMaxThicknessPfinCid
	 */
	public void setHistoMaxThicknessPfinCid(
		java.lang.String newHistoMaxThicknessPfinCid)
		throws java.rmi.RemoteException;
	/**
	 * Get accessor for persistent attribute: histoWidthAcrossPfinCid
	 */
	public java.lang.String getHistoWidthAcrossPfinCid()
		throws java.rmi.RemoteException;
	/**
	 * Set accessor for persistent attribute: histoWidthAcrossPfinCid
	 */
	public void setHistoWidthAcrossPfinCid(
		java.lang.String newHistoWidthAcrossPfinCid)
		throws java.rmi.RemoteException;
	/**
	 * Get accessor for persistent attribute: histoReembedReasonCid
	 */
	public java.lang.String getHistoReembedReasonCid()
		throws java.rmi.RemoteException;
	/**
	 * Set accessor for persistent attribute: histoReembedReasonCid
	 */
	public void setHistoReembedReasonCid(
		java.lang.String newHistoReembedReasonCid)
		throws java.rmi.RemoteException;
	/**
	 * Get accessor for persistent attribute: histoSubdividable
	 */
	public java.lang.String getHistoSubdividable()
		throws java.rmi.RemoteException;
	/**
	 * Set accessor for persistent attribute: histoSubdividable
	 */
	public void setHistoSubdividable(java.lang.String newHistoSubdividable)
		throws java.rmi.RemoteException;
	/**
	 * Get accessor for persistent attribute: histoNotes
	 */
	public java.lang.String getHistoNotes() throws java.rmi.RemoteException;
	/**
	 * Set accessor for persistent attribute: histoNotes
	 */
	public void setHistoNotes(java.lang.String newHistoNotes)
		throws java.rmi.RemoteException;
	/**
	 * Get accessor for persistent attribute: otherHistoReembedReason
	 */
	public java.lang.String getOtherHistoReembedReason()
		throws java.rmi.RemoteException;
	/**
	 * Set accessor for persistent attribute: otherHistoReembedReason
	 */
	public void setOtherHistoReembedReason(
		java.lang.String newOtherHistoReembedReason)
		throws java.rmi.RemoteException;
	/**
	 * Get accessor for persistent attribute: paraffinFeatureCid
	 */
	public java.lang.String getParaffinFeatureCid()
		throws java.rmi.RemoteException;
	/**
	 * Set accessor for persistent attribute: paraffinFeatureCid
	 */
	public void setParaffinFeatureCid(java.lang.String newParaffinFeatureCid)
		throws java.rmi.RemoteException;
	/**
	 * Get accessor for persistent attribute: otherParaffinFeature
	 */
	public java.lang.String getOtherParaffinFeature()
		throws java.rmi.RemoteException;
	/**
	 * Set accessor for persistent attribute: otherParaffinFeature
	 */
	public void setOtherParaffinFeature(
		java.lang.String newOtherParaffinFeature)
		throws java.rmi.RemoteException;
  /**
   * Get accessor for persistent attribute: receiptDate
   */
  public java.sql.Timestamp getReceiptDate() throws java.rmi.RemoteException;
  /**
   * Set accessor for persistent attribute: receiptDate
   */
  public void setReceiptDate(java.sql.Timestamp newReceiptDate) throws java.rmi.RemoteException;
  /**
   * Get accessor for persistent attribute: lastknownlocationid
   */
  public java.lang.String getLastknownlocationid() throws java.rmi.RemoteException;
  /**
   * Set accessor for persistent attribute: lastknownlocationid
   */
  public void setLastknownlocationid(java.lang.String newLastknownlocationid)
    throws java.rmi.RemoteException;
  /**
   * Get accessor for persistent attribute: importedYN
   */
  public java.lang.String getImportedYN() throws java.rmi.RemoteException;
  /**
   * Set accessor for persistent attribute: importedYN
   */
  public void setImportedYN(java.lang.String newImportedYN) throws java.rmi.RemoteException;
  /**
   * Get accessor for persistent attribute: customerId
   */
  public java.lang.String getCustomerId() throws java.rmi.RemoteException;
  /**
   * Set accessor for persistent attribute: customerId
   */
  public void setCustomerId(java.lang.String newCustomerId) throws java.rmi.RemoteException;
  /**
   * Get accessor for persistent attribute: sampleTypeCid
   */
  public java.lang.String getSampleTypeCid() throws java.rmi.RemoteException;
  /**
   * Set accessor for persistent attribute: sampleTypeCid
   */
  public void setSampleTypeCid(java.lang.String newSampleTypeCid) throws java.rmi.RemoteException;
  /**
   * Get accessor for persistent attribute: collection_datetime
   */
  public java.sql.Timestamp getCollection_datetime() throws java.rmi.RemoteException;
  /**
   * Set accessor for persistent attribute: collection_datetime
   */
  public void setCollection_datetime(java.sql.Timestamp newCollection_datetime)
    throws java.rmi.RemoteException;
  /**
   * Get accessor for persistent attribute: preservation_datetime
   */
  public java.sql.Timestamp getPreservation_datetime() throws java.rmi.RemoteException;
  /**
   * Set accessor for persistent attribute: preservation_datetime
   */
  public void setPreservation_datetime(java.sql.Timestamp newPreservation_datetime)
    throws java.rmi.RemoteException;
  /**
   * Get accessor for persistent attribute: volume
   */
  public java.math.BigDecimal getVolume() throws java.rmi.RemoteException;
  /**
   * Set accessor for persistent attribute: volume
   */
  public void setVolume(java.math.BigDecimal newVolume) throws java.rmi.RemoteException;
  /**
   * Get accessor for persistent attribute: collection_datetime_dpc
   */
  public java.lang.String getCollection_datetime_dpc() throws java.rmi.RemoteException;
  /**
   * Set accessor for persistent attribute: collection_datetime_dpc
   */
  public void setCollection_datetime_dpc(java.lang.String newCollection_datetime_dpc)
    throws java.rmi.RemoteException;
  /**
   * Get accessor for persistent attribute: preservation_datetime_dpc
   */
  public java.lang.String getPreservation_datetime_dpc() throws java.rmi.RemoteException;
  /**
   * Set accessor for persistent attribute: preservation_datetime_dpc
   */
  public void setPreservation_datetime_dpc(java.lang.String newPreservation_datetime_dpc)
    throws java.rmi.RemoteException;
  /**
   * Get accessor for persistent attribute: bufferTypeCui
   */
  public java.lang.String getBufferTypeCui() throws java.rmi.RemoteException;
  /**
   * Set accessor for persistent attribute: bufferTypeCui
   */
  public void setBufferTypeCui(java.lang.String newBufferTypeCui) throws java.rmi.RemoteException;
  /**
   * Get accessor for persistent attribute: bufferTypeOther
   */
  public java.lang.String getBufferTypeOther() throws java.rmi.RemoteException;
  /**
   * Set accessor for persistent attribute: bufferTypeOther
   */
  public void setBufferTypeOther(java.lang.String newBufferTypeOther)
    throws java.rmi.RemoteException;
  /**
   * Get accessor for persistent attribute: totalNumOfCells
   */
  public java.math.BigDecimal getTotalNumOfCells() throws java.rmi.RemoteException;
  /**
   * Set accessor for persistent attribute: totalNumOfCells
   */
  public void setTotalNumOfCells(java.math.BigDecimal newTotalNumOfCells)
    throws java.rmi.RemoteException;
  /**
   * Get accessor for persistent attribute: totalNumOfCellsExRepCui
   */
  public java.lang.String getTotalNumOfCellsExRepCui() throws java.rmi.RemoteException;
  /**
   * Set accessor for persistent attribute: totalNumOfCellsExRepCui
   */
  public void setTotalNumOfCellsExRepCui(java.lang.String newTotalNumOfCellsExRepCui)
    throws java.rmi.RemoteException;
  /**
   * Get accessor for persistent attribute: cellsPerMl
   */
  public java.math.BigDecimal getCellsPerMl() throws java.rmi.RemoteException;
  /**
   * Set accessor for persistent attribute: cellsPerMl
   */
  public void setCellsPerMl(java.math.BigDecimal newCellsPerMl) throws java.rmi.RemoteException;
  /**
   * Get accessor for persistent attribute: percentViability
   */
  public java.lang.Integer getPercentViability() throws java.rmi.RemoteException;
  /**
   * Set accessor for persistent attribute: percentViability
   */
  public void setPercentViability(java.lang.Integer newPercentViability)
    throws java.rmi.RemoteException;
  /**
   * Get accessor for persistent attribute: source
   */
  public java.lang.String getSource() throws java.rmi.RemoteException;
  /**
   * Set accessor for persistent attribute: source
   */
  public void setSource(java.lang.String newSource) throws java.rmi.RemoteException;
  /**
   * Get accessor for persistent attribute: concentration
   */
  public java.math.BigDecimal getConcentration() throws java.rmi.RemoteException;
  /**
   * Set accessor for persistent attribute: concentration
   */
  public void setConcentration(java.math.BigDecimal newConcentration)
    throws java.rmi.RemoteException;
  /**
   * Get accessor for persistent attribute: yield
   */
  public java.math.BigDecimal getYield() throws java.rmi.RemoteException;
  /**
   * Set accessor for persistent attribute: yield
   */
  public void setYield(java.math.BigDecimal newYield) throws java.rmi.RemoteException;
  /**
   * Get accessor for persistent attribute: volumeUnitCui
   */
  public java.lang.String getVolumeUnitCui() throws java.rmi.RemoteException;
  /**
   * Set accessor for persistent attribute: volumeUnitCui
   */
  public void setVolumeUnitCui(java.lang.String newVolumeUnitCui) throws java.rmi.RemoteException;
  /**
   * Set accessor for persistent attribute: volumeInUl
   */
  public void setVolumeInUl(java.math.BigDecimal newVolume) throws java.rmi.RemoteException;
  /**
   * Get accessor for persistent attribute: volumeInUl
   */
  public java.math.BigDecimal getVolumeInUl() throws java.rmi.RemoteException;
  /**
   * Get accessor for persistent attribute: weight
   */
  public java.math.BigDecimal getWeight() throws java.rmi.RemoteException;
  /**
   * Set accessor for persistent attribute: weight
   */
  public void setWeight(java.math.BigDecimal newWeight) throws java.rmi.RemoteException;
  /**
   * Get accessor for persistent attribute: weightInMg
   */
  public java.math.BigDecimal getWeightInMg() throws java.rmi.RemoteException;
  /**
   * Set accessor for persistent attribute: weightInMg
   */
  public void setWeightInMg(java.math.BigDecimal newWeight) throws java.rmi.RemoteException;
  /**
   * Get accessor for persistent attribute: volumeUnitCui
   */
  public java.lang.String getWeightUnitCui() throws java.rmi.RemoteException;
  /**
   * Set accessor for persistent attribute: volumeUnitCui
   */
  public void setWeightUnitCui(java.lang.String newWeightUnitCui) throws java.rmi.RemoteException;
  /**
   * Get accessor for persistent attribute: sampleRegistrationForm
   */
  public java.lang.String getSampleRegistrationForm() throws java.rmi.RemoteException;
  /**
   * Set accessor for persistent attribute: sampleRegistrationForm
   */
  public void setSampleRegistrationForm(java.lang.String newSampleRegistrationForm)
    throws java.rmi.RemoteException;
}
