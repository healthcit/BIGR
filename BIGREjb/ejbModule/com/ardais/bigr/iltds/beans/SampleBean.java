package com.ardais.bigr.iltds.beans;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

import javax.ejb.CreateException;
import javax.ejb.EJBException;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.iltds.helpers.FormLogic;

/**
 * Bean implementation class for Enterprise Bean: Sample
 */
public class SampleBean implements javax.ejb.EntityBean {

  public static final String DEFAULT_sample_barcode_id = null;
  public static final java.lang.String DEFAULT_asm_asm_id = null;
  public static final java.lang.String DEFAULT_samplebox_box_barcode_id = null;
  public static final String DEFAULT_cell_ref_location = null;
  public static final String DEFAULT_asm_note = null;
  public static final String DEFAULT_type_of_fixative = null;
  public static final String DEFAULT_allocation_ind = null;
  public static final java.sql.Timestamp DEFAULT_barcode_scan_datetime = null;
  public static final java.sql.Timestamp DEFAULT_orphan_datetime = null;
  public static final String DEFAULT_asm_position = null;
  public static final String DEFAULT_ardais_acct_key = null;
  public static final java.sql.Timestamp DEFAULT_subdivision_date = null;
  public static final java.sql.Timestamp DEFAULT_born_date = null;
  public static final String DEFAULT_parent_barcode_id = null;
  public static final String DEFAULT_discordantYN = "N";
  public static final String DEFAULT_pullYN = "N";
  public static final String DEFAULT_pullReason = null;
  public static final String DEFAULT_releasedYN = "N";
  public static final java.sql.Timestamp DEFAULT_pullDate = null;
  public static final String DEFAULT_diMinThicknessPfinCid = null;
  public static final String DEFAULT_diMaxThicknessPfinCid = null;
  public static final String DEFAULT_diWidthAcrossPfinCid = null;
  public static final String DEFAULT_formatDetailCid = null;
  public static final String DEFAULT_sampleSizeMeetsSpecs = "Y";
  public static final java.lang.Integer DEFAULT_hoursInFixative = null;
  public static final String DEFAULT_qcpostedYN = "N";
  public static final String DEFAULT_histoMinThicknessPfinCid = null;
  public static final String DEFAULT_histoMaxThicknessPfinCid = null;
  public static final String DEFAULT_histoWidthAcrossPfinCid = null;
  public static final String DEFAULT_histoReembedReasonCid = null;
  public static final String DEFAULT_histoSubdividable = null;
  public static final String DEFAULT_histoNotes = null;
  public static final String DEFAULT_otherHistoReembedReason = null;
  public static final String DEFAULT_paraffinFeatureCid = null;
  public static final String DEFAULT_otherParaffinFeature = null;
  public static final java.sql.Timestamp DEFAULT_receiptDate = null;
  public static final String DEFAULT_lastknownlocationid = null;
  public static final String DEFAULT_importedYN = null;
  public static final String DEFAULT_customerId = null;
  public static final String DEFAULT_sampleTypeCid = null;
  public static final java.sql.Timestamp DEFAULT_collection_datetime = null;
  public static final String DEFAULT_collection_datetime_dpc = null;
  public static final java.sql.Timestamp DEFAULT_preservation_datetime = null;
  public static final String DEFAULT_preservation_datetime_dpc = null;
  public static final BigDecimal DEFAULT_volume = null;
  public static final String DEFAULT_volumeUnitCui = null;
  public static final BigDecimal DEFAULT_volumeInUl = null;
  public static final BigDecimal DEFAULT_weight = null;
  public static final String DEFAULT_weightUnitCui = null;
  public static final BigDecimal DEFAULT_weightInMg = null;
  public static final String DEFAULT_bufferTypeCui = null;
  public static final String DEFAULT_bufferTypeOther = null;
  public static final BigDecimal DEFAULT_totalNumOfCells = null;
  public static final String DEFAULT_totalNumOfCellsExRepCui = null;
  public static final BigDecimal DEFAULT_cellsPerMl = null;
  public static final Integer DEFAULT_percentViability = null;
  public static final String DEFAULT_source = null;
  public static final BigDecimal DEFAULT_concentration = null;
  public static final BigDecimal DEFAULT_yield = null;
 public static final String DEFAULT_sampleRegistrationForm = null;

  public String sample_barcode_id;
  public java.lang.String asm_asm_id;
  public java.lang.String samplebox_box_barcode_id;
  public String cell_ref_location;
  public String asm_note;
  public String type_of_fixative;
  public String allocation_ind;
  public java.sql.Timestamp barcode_scan_datetime;
  public java.sql.Timestamp orphan_datetime;
  public String asm_position;
  public String ardais_acct_key;
  public Timestamp subdivision_date;
  public Timestamp born_date;
  public String parent_barcode_id;
  /**
   * Implementation field for persistent attribute: discordantYN
   */
  public java.lang.String discordantYN;
  /**
   * Implementation field for persistent attribute: pullYN
   */
  public java.lang.String pullYN;
  /**
   * Implementation field for persistent attribute: pullReason
   */
  public java.lang.String pullReason;
  /**
   * Implementation field for persistent attribute: releasedYN
   */
  public java.lang.String releasedYN;
  /**
   * Implementation field for persistent attribute: pullDate
   */
  public java.sql.Timestamp pullDate;
  /**
   * Implementation field for persistent attribute: diMinThicknessPfinCid
   */
  public java.lang.String diMinThicknessPfinCid;
  /**
   * Implementation field for persistent attribute: diMaxThicknessPfinCid
   */
  public java.lang.String diMaxThicknessPfinCid;
  /**
   * Implementation field for persistent attribute: diWidthAcrossPfinCid
   */
  public java.lang.String diWidthAcrossPfinCid;
  /**
   * Implementation field for persistent attribute: formatDetailCid
   */
  public java.lang.String formatDetailCid;
  /**
   * Implementation field for persistent attribute: sampleSizeMeetsSpecs
   */
  public java.lang.String sampleSizeMeetsSpecs;
  /**
   * Implementation field for persistent attribute: hoursInFixative
   */
  public java.lang.Integer hoursInFixative;
  /**
   * Implementation field for persistent attribute: qcpostedYN
   */
  public java.lang.String qcpostedYN;
  /**
   * Implementation field for persistent attribute: histoMinThicknessPfinCid
   */
  public java.lang.String histoMinThicknessPfinCid;
  /**
   * Implementation field for persistent attribute: histoMaxThicknessPfinCid
   */
  public java.lang.String histoMaxThicknessPfinCid;
  /**
   * Implementation field for persistent attribute: histoWidthAcrossPfinCid
   */
  public java.lang.String histoWidthAcrossPfinCid;
  /**
   * Implementation field for persistent attribute: histoReembedReasonCid
   */
  public java.lang.String histoReembedReasonCid;
  /**
   * Implementation field for persistent attribute: histoSubdividable
   */
  public java.lang.String histoSubdividable;
  /**
   * Implementation field for persistent attribute: histoNotes
   */
  public java.lang.String histoNotes;
  /**
   * Implementation field for persistent attribute: otherHistoReembedReason
   */
  public java.lang.String otherHistoReembedReason;
  /**
   * Implementation field for persistent attribute: paraffinFeatureCid
   */
  public java.lang.String paraffinFeatureCid;
  /**
   * Implementation field for persistent attribute: otherParaffinFeature
   */
  public java.lang.String otherParaffinFeature;
  /**
   * This method was generated for supporting the associations.
   * @return java.util.Vector
   */

  private boolean jdbcFields = false;
  private java.lang.String inv_status;
  private String tissueFindingCid;
  private String tissueFindingOther;
  private java.lang.String qc_status;
  private java.lang.String sales_status;
  private java.lang.String qc_verified;
  private java.sql.Timestamp qc_verified_date;
  private String project_status;
  private java.sql.Timestamp inv_status_date;
  private java.sql.Timestamp sales_status_date;
  private java.sql.Timestamp qc_status_date;
  private java.sql.Timestamp project_status_date;
  private java.lang.Integer sectionCount;
  private java.lang.String bms_sample;
  private String consentId;
  private String ardaisId;
  private String appearanceBest;
  private String diagnosisCuiBest;
  private String diagnosisOtherBest;
  private String tissueOriginCid;
  private String tissueOriginOther;

  private javax.ejb.EntityContext entityContext = null;

  final static long serialVersionUID = 3206093459760846163L;

  private transient com.ibm.ivj.ejb.associations.interfaces.SingleLink asmLink = null;
  private transient com.ibm.ivj.ejb.associations.interfaces.SingleLink limsproductLink = null;
  private transient com.ibm.ivj.ejb.associations.interfaces.SingleLink sampleboxLink = null;
  private transient com.ibm.ivj.ejb.associations.interfaces.ManyLink samplestatusLink = null;

  /**
   * Implementation field for persistent attribute: receiptDate
   */
  public java.sql.Timestamp receiptDate;
  /**
   * Implementation field for persistent attribute: lastknownlocationid
   */
  public java.lang.String lastknownlocationid;
  /**
   * Implementation field for persistent attribute: importedYN
   */
  public java.lang.String importedYN;
  /**
   * Implementation field for persistent attribute: customerId
   */
  public java.lang.String customerId;
  /**
   * Implementation field for persistent attribute: sampleTypeCid
   */
  public java.lang.String sampleTypeCid;
  /**
   * Implementation field for persistent attribute: collection_datetime
   */
  public java.sql.Timestamp collection_datetime;
  /**
   * Implementation field for persistent attribute: preservation_datetime
   */
  public java.sql.Timestamp preservation_datetime;
  /**
   * Implementation field for persistent attribute: volume
   */
  public java.math.BigDecimal volume;
   /**
   * Implementation field for persistent attribute: volumeInUl
   */
  public java.math.BigDecimal volumeInUl;
  /**
   * Implementation field for persistent attribute: volumeUnitCui
   */
  public java.lang.String volumeUnitCui;
  /**
   * Implementation field for persistent attribute: weight
   */
  public java.math.BigDecimal weight;
  /**
  * Implementation field for persistent attribute: weightInMg
  */
 public java.math.BigDecimal weightInMg;
 /**
  * Implementation field for persistent attribute: weightUnitCui
  */
 public java.lang.String weightUnitCui;
 /**
  * Implementation field for persistent attribute: collection_datetime_dpc
  */
   public java.lang.String collection_datetime_dpc;
  /**
   * Implementation field for persistent attribute: preservation_datetime_dpc
   */
  public java.lang.String preservation_datetime_dpc;
  /**
   * Implementation field for persistent attribute: bufferTypeCui
   */
  public java.lang.String bufferTypeCui;
  /**
   * Implementation field for persistent attribute: bufferTypeOther
   */
  public java.lang.String bufferTypeOther;
  /**
   * Implementation field for persistent attribute: totalNumOfCells
   */
  public java.math.BigDecimal totalNumOfCells;
  /**
   * Implementation field for persistent attribute: totalNumOfCellsExRepCui
   */
  public java.lang.String totalNumOfCellsExRepCui;
  /**
   * Implementation field for persistent attribute: cellsPerMl
   */
  public java.math.BigDecimal cellsPerMl;
  /**
   * Implementation field for persistent attribute: percentViability
   */
  public java.lang.Integer percentViability;
  /**
   * Implementation field for persistent attribute: source
   */
  public java.lang.String source;

  // IMPORTANT: Put this after all other field definitions to ensure that
  // it initializes properly.
  //
  private static final com.ardais.bigr.api.CMPDefaultValues _fieldDefaultValues =
    new com.ardais.bigr.api.CMPDefaultValues(com.ardais.bigr.iltds.beans.SampleBean.class);
  /**
   * Implementation field for persistent attribute: concentration
   */
  public java.math.BigDecimal concentration;
  /**
   * Implementation field for persistent attribute: yield
   */
  public java.math.BigDecimal yield;
  /**
   * Implementation field for persistent attribute: sampleRegistrationForm
   */
  public java.lang.String sampleRegistrationForm;
  /**
   * This method was generated for supporting the associations.
   */
  protected java.util.Vector _getLinks() {
    java.util.Vector links = new java.util.Vector();
    links.add(getSampleboxLink());
    links.add(getSamplestatusLink());
    links.add(getAsmLink());
    return links;
  }
  /**
   * This method was generated for supporting the associations.
   */
  protected void _initLinks() {
    sampleboxLink = null;
    samplestatusLink = null;
    asmLink = null;
  }
  /**
   * This method was generated for supporting the associations.
   */
  protected void _removeLinks() throws java.rmi.RemoteException, javax.ejb.RemoveException {
    java.util.List links = _getLinks();
    for (int i = 0; i < links.size(); i++) {
      try {
        ((com.ibm.ivj.ejb.associations.interfaces.Link) links.get(i)).remove();
      }
      catch (javax.ejb.FinderException e) {
      } //Consume Finder error since I am going away
    }
  }
  /**
   * This method was generated for supporting the association named Samplestatus REFILTDS_SAMPLE25 Sample.  
   *  It will be deleted/edited when the association is deleted/edited.
   */
  /* WARNING: THIS METHOD WILL BE REGENERATED. */
  public void addSamplestatus(com.ardais.bigr.iltds.beans.Samplestatus aSamplestatus)
    throws java.rmi.RemoteException {
    this.getSamplestatusLink().addElement(aSamplestatus);
  }
  /**
   * ejbActivate method comment
   */
  public void ejbActivate() throws java.rmi.RemoteException {
    _initLinks();
  }
  /**
   * ejbCreate method for a CMP entity bean.
   */
  public SampleKey ejbCreate(
    String sample_barcode_id,
    String importedYN,
    String sampleTypeCid)
    throws CreateException, EJBException {

    // Next line is Ardais-added:
    _fieldDefaultValues.assignTo(this);

    _initLinks();
    this.sample_barcode_id = sample_barcode_id;
    this.importedYN = importedYN;
    this.sampleTypeCid = sampleTypeCid;

    // Next two lines are Ardais-added:
    born_date = new java.sql.Timestamp(System.currentTimeMillis());
    this.jdbcFields = false;

    return null;
  }
  /**
   * ejbLoad method comment
   */
  public void ejbLoad() throws java.rmi.RemoteException {
    _initLinks();
    this.jdbcFields = false;
  }
  /**
   * ejbPassivate method comment
   */
  public void ejbPassivate() throws java.rmi.RemoteException {
  }
  /**
   * ejbPostCreate
   */
  public void ejbPostCreate(
    String sample_barcode_id,
    String importedYN,
    String sampleTypeCid)
    throws javax.ejb.CreateException, javax.ejb.EJBException {
  }
  /**
   * ejbRemove method comment
   */
  public void ejbRemove() throws java.rmi.RemoteException, javax.ejb.RemoveException {
    _removeLinks();
  }
  /**
   * ejbStore method comment
   */
  public void ejbStore() throws java.rmi.RemoteException {
  }
  /**
   * Getter method for allocation_ind
   * @return java.lang.String
   */
  public java.lang.String getAllocation_ind() {
    return allocation_ind;
  }
  /**
   * Getter method for ardais_acct_key
   */
  public java.lang.String getArdais_acct_key() {
    return ardais_acct_key;
  }
  /**
   * This method was generated for supporting the association named Sample REFILTDS_ASM7 Asm.  
   *  It will be deleted/edited when the association is deleted/edited.
   * @return com.ardais.bigr.iltds.beans.Asm
   * @exception javax.ejb.FinderException The exception description.
   */
  /* WARNING: THIS METHOD WILL BE REGENERATED. */
  public com.ardais.bigr.iltds.beans.Asm getAsm()
    throws java.rmi.RemoteException, javax.ejb.FinderException {
    return (com.ardais.bigr.iltds.beans.Asm) this.getAsmLink().value();
  }
  /**
   * Insert the method's description here.
   * Creation date: (3/27/01 11:06:28 AM)
   * @return java.lang.String
   */
  public java.lang.String getAsm_asm_id() {
    return asm_asm_id;
  }
  /**
   * Getter method for asm_note
   * @return java.lang.String
   */
  public java.lang.String getAsm_note() {
    return asm_note;
  }
  /**
   * Getter method for asm_position
   */
  public java.lang.String getAsm_position() {
    return asm_position;
  }

  /**
   * This method was generated for supporting the association named Sample REFILTDS_ASM7 Asm.  
   *  It will be deleted/edited when the association is deleted/edited.
   * @return com.ardais.bigr.iltds.beans.AsmKey
   */
  /* WARNING: THIS METHOD WILL BE REGENERATED. */
  public com.ardais.bigr.iltds.beans.AsmKey getAsmKey() {
    com.ardais.bigr.iltds.beans.AsmKey temp = null;
    temp = new com.ardais.bigr.iltds.beans.AsmKey();
    boolean asm_NULLTEST = true;
    asm_NULLTEST &= (asm_asm_id == null);
    temp.asm_id = asm_asm_id;
    if (asm_NULLTEST)
      temp = null;
    return temp;
  }
  /**
   * This method was generated for supporting the association named Sample REFILTDS_ASM7 Asm.  
   *  It will be deleted/edited when the association is deleted/edited.
   * @return com.ibm.ivj.ejb.associations.interfaces.SingleLink
   */
  /* WARNING: THIS METHOD WILL BE REGENERATED. */
  protected com.ibm.ivj.ejb.associations.interfaces.SingleLink getAsmLink() {
    if (asmLink == null)
      asmLink = new SampleToAsmLink(this);
    return asmLink;
  }
  /**
   * Getter method for barcode_scan_datetime
   * @return java.sql.Timestamp
   */
  public java.sql.Timestamp getBarcode_scan_datetime() {
    return barcode_scan_datetime;
  }
  /**
   * Insert the method's description here.
   * Creation date: (11/5/2001 10:39:02 AM)
   * @return boolean
   */
  public java.lang.String getBms_sample() {
    if (!jdbcFields) {
      populateJDBCFields();
    }
    return bms_sample;
  }

  public boolean isBms_sample() {
    if (!jdbcFields) {
      populateJDBCFields();
    }

    if (bms_sample.equals((String) "Y"))
      return true;
    else
      return false;

  }

  /**
   * Getter method for born_date
   */
  public java.sql.Timestamp getBorn_date() {
    return born_date;
  }
  /**
   * Getter method for cell_ref_location
   * @return java.lang.String
   */
  public java.lang.String getCell_ref_location() {
    return cell_ref_location;
  }
  /**
   * getEntityContext method comment
   * @return javax.ejb.EntityContext
   */
  public javax.ejb.EntityContext getEntityContext() {
    return entityContext;
  }
  /**
   * 
   * @return
   */
  public String getConsentId() {
    if (!jdbcFields) {
      populateJDBCFields();
    }
    return consentId;
  }
  /**
   * 
   * @return
   */
  public String getArdaisId() {
    if (!jdbcFields) {
      populateJDBCFields();
    }
    return ardaisId;
  }
  /**
   * Insert the method's description here.
   * Creation date: (11/5/2001 10:39:02 AM)
   * @return java.lang.String
   */
  public java.lang.String getInv_status() {
    if (!jdbcFields) {
      populateJDBCFields();
    }
    return inv_status;
  }
  /**
   * Getter method for inv_status_date
   */
  public java.sql.Timestamp getInv_status_date() {
    if (!jdbcFields) {
      populateJDBCFields();
    }
    return inv_status_date;
  }
  /**
   * Insert the method's description here.
   * Creation date: (11/5/2001 10:39:02 AM)
   * @return java.lang.String
   */
  public java.lang.String getTissueFindingCid() {
    if (!jdbcFields) {
      populateJDBCFields();
    }
    return tissueFindingCid;
  }
  /**
   * Insert the method's description here.
   * Creation date: (11/5/2001 10:39:02 AM)
   * @return java.lang.String
   */
  public java.lang.String getTissueFindingOther() {
    if (!jdbcFields) {
      populateJDBCFields();
    }
    return tissueFindingOther;
  }
  /**
   * Getter method for appearanceBest
   * @return java.lang.String
   */
  public java.lang.String getAppearanceBest() {
    if (!jdbcFields) {
      populateJDBCFields();
    }
    return appearanceBest;
  }
  /**
   * Getter method for diagnosisCuiBest
   * @return java.lang.String
   */
  public java.lang.String getDiagnosisCuiBest() {
    if (!jdbcFields) {
      populateJDBCFields();
    }
    return diagnosisCuiBest;
  }
  /**
   * Getter method for diagnosisCuiBest
   * @return java.lang.String
   */
  public java.lang.String getDiagnosisOtherBest() {
    if (!jdbcFields) {
      populateJDBCFields();
    }
    return diagnosisOtherBest;
  }
  /**
   * Insert the method's description here.
   * Creation date: (11/5/2001 10:39:02 AM)
   * @return java.lang.String
   */
  public java.lang.String getTissueOriginCid() {
    if (!jdbcFields) {
      populateJDBCFields();
    }
    return tissueOriginCid;
  }
  /**
   * Insert the method's description here.
   * Creation date: (11/5/2001 10:39:02 AM)
   * @return java.lang.String
   */
  public java.lang.String getTissueOriginOther() {
    if (!jdbcFields) {
      populateJDBCFields();
    }
    return tissueOriginOther;
  }
  /**
   * Getter method for orphan_datetime
   * @return java.sql.Timestamp
   */
  public java.sql.Timestamp getOrphan_datetime() {
    return orphan_datetime;
  }
  /**
   * Getter method for parent_barcode_id
   */
  public java.lang.String getParent_barcode_id() {
    return parent_barcode_id;
  }
  /**
   * Getter method for project_status
   */
  public java.lang.String getProject_status() {
    if (!jdbcFields) {
      populateJDBCFields();
    }
    return project_status;
  }
  /**
   * Getter method for project_status_date
   */
  public java.sql.Timestamp getProject_status_date() {
    if (!jdbcFields) {
      populateJDBCFields();
    }
    return project_status_date;
  }
  /**
   * Insert the method's description here.
   * Creation date: (11/5/2001 10:39:53 AM)
   * @return java.lang.String
   */
  public java.lang.String getQc_status() {
    if (!jdbcFields) {
      populateJDBCFields();
    }
    return qc_status;
  }
  /**
   * Getter method for qc_status_date
   */
  public java.sql.Timestamp getQc_status_date() {
    if (!jdbcFields) {
      populateJDBCFields();
    }
    return qc_status_date;
  }
  /**
   * Insert the method's description here.
   * Creation date: (11/5/2001 10:40:53 AM)
   * @return java.lang.String
   */
  public java.lang.String getQc_verified() {
    if (!jdbcFields) {
      populateJDBCFields();
    }
    return qc_verified;
  }
  /**
   * Insert the method's description here.
   * Creation date: (11/5/2001 10:41:34 AM)
   * @return java.sql.Timestamp
   */
  public java.sql.Timestamp getQc_verified_date() {
    if (!jdbcFields) {
      populateJDBCFields();
    }
    return qc_verified_date;
  }
  /**
   * Insert the method's description here.
   * Creation date: (11/5/2001 10:40:13 AM)
   * @return java.lang.String
   */
  public java.lang.String getSales_status() {
    if (!jdbcFields) {
      populateJDBCFields();
    }
    return sales_status;
  }
  /**
   * Getter method for sales_status_date
   */
  public java.sql.Timestamp getSales_status_date() {
    if (!jdbcFields) {
      populateJDBCFields();
    }
    return sales_status_date;
  }
  /**
   * This method was generated for supporting the association named Sample REFILTDS_SAMPLE_BOX10 Samplebox.  
   *  It will be deleted/edited when the association is deleted/edited.
   * @return com.ardais.bigr.iltds.beans.Samplebox
   * @exception javax.ejb.FinderException The exception description.
   */
  /* WARNING: THIS METHOD WILL BE REGENERATED. */
  public com.ardais.bigr.iltds.beans.Samplebox getSamplebox()
    throws java.rmi.RemoteException, javax.ejb.FinderException {
    return (com.ardais.bigr.iltds.beans.Samplebox) this.getSampleboxLink().value();
  }
  /**
   * Insert the method's description here.
   * Creation date: (2/26/01 10:37:33 AM)
   * @return java.lang.String
   */
  public java.lang.String getSamplebox_box_barcode_id() {
    return samplebox_box_barcode_id;
  }
  /**
   * This method was generated for supporting the association named Sample REFILTDS_SAMPLE_BOX10 Samplebox.  
   *  It will be deleted/edited when the association is deleted/edited.
   * @return com.ardais.bigr.iltds.beans.SampleboxKey
   */
  /* WARNING: THIS METHOD WILL BE REGENERATED. */
  public com.ardais.bigr.iltds.beans.SampleboxKey getSampleboxKey() {
    com.ardais.bigr.iltds.beans.SampleboxKey temp = null;
    temp = new com.ardais.bigr.iltds.beans.SampleboxKey();
    boolean samplebox_NULLTEST = true;
    samplebox_NULLTEST &= (samplebox_box_barcode_id == null);
    temp.box_barcode_id = samplebox_box_barcode_id;
    if (samplebox_NULLTEST)
      temp = null;
    return temp;
  }
  /**
   * This method was generated for supporting the association named Sample REFILTDS_SAMPLE_BOX10 Samplebox.  
   *  It will be deleted/edited when the association is deleted/edited.
   * @return com.ibm.ivj.ejb.associations.interfaces.SingleLink
   */
  /* WARNING: THIS METHOD WILL BE REGENERATED. */
  protected com.ibm.ivj.ejb.associations.interfaces.SingleLink getSampleboxLink() {
    if (sampleboxLink == null)
      sampleboxLink = new SampleToSampleboxLink(this);
    return sampleboxLink;
  }
  /**
   * This method was generated for supporting the association named Samplestatus REFILTDS_SAMPLE25 Sample.  
   *  It will be deleted/edited when the association is deleted/edited.
   * @return java.util.Enumeration
   * @exception javax.ejb.FinderException The exception description.
   */
  /* WARNING: THIS METHOD WILL BE REGENERATED. */
  public java.util.Enumeration getSamplestatus()
    throws java.rmi.RemoteException, javax.ejb.FinderException {
    return this.getSamplestatusLink().enumerationValue();
  }
  /**
   * This method was generated for supporting the association named Samplestatus REFILTDS_SAMPLE25 Sample.  
   *  It will be deleted/edited when the association is deleted/edited.
   * @return com.ibm.ivj.ejb.associations.interfaces.ManyLink
   */
  /* WARNING: THIS METHOD WILL BE REGENERATED. */
  protected com.ibm.ivj.ejb.associations.interfaces.ManyLink getSamplestatusLink() {
    if (samplestatusLink == null)
      samplestatusLink = new SampleToSamplestatusLink(this);
    return samplestatusLink;
  }
  /**
   * Getter method for sales_status_date
   */
  public Integer getSectionCount() {
    if (!jdbcFields) {
      populateJDBCFields();
    }
    return sectionCount;
  }
  /**
   * Getter method for subdivision_date
   */
  public java.sql.Timestamp getSubdivision_date() {
    return subdivision_date;
  }
  /**
   * Getter method for type_of_fixative
   * @return java.lang.String
   */
  public java.lang.String getType_of_fixative() {
    return type_of_fixative;
  }
  /**
   * Insert the method's description here.
   * Creation date: (2/14/2002 11:52:06 AM)
   */
  public void populateJDBCFields() {
    Connection con = null;
    PreparedStatement ps = null;
    try {
      con = com.ardais.bigr.api.ApiFunctions.getDbConnection();
      String query =
        "SELECT T1.INV_STATUS, T1.INV_STATUS_DATE, T1.QC_STATUS, T1.QC_STATUS_DATE, "
        + "T1.SALES_STATUS, T1.SALES_STATUS_DATE, T1.QC_VERIFIED, T1.QC_VERIFIED_DATE, "
        + "T1.PROJECT_STATUS, T1.PROJECT_STATUS_DATE, T1.SECTION_COUNT, "
        + "T1.BMS_YN, T1.CONSENT_ID, T1.ARDAIS_ID, T1.TISSUE_FINDING_CUI, T1.TISSUE_FINDING_OTHER, "
        + "T1.APPEARANCE_BEST, T1.DIAGNOSIS_CUI_BEST, T1.DIAGNOSIS_OTHER_BEST, "
        + "T1.TISSUE_ORIGIN_CUI, T1.TISSUE_ORIGIN_OTHER FROM ILTDS_SAMPLE T1 "
        + "WHERE T1.SAMPLE_BARCODE_ID = ? ";

      ps = con.prepareStatement(query);
      ps.setString(1, this.sample_barcode_id);

      ResultSet rs = ps.executeQuery();
      rs.next();

      this.inv_status = rs.getString(1);
      this.inv_status_date = rs.getTimestamp(2);
      this.qc_status = rs.getString(3);
      this.qc_status_date = rs.getTimestamp(4);
      this.sales_status = rs.getString(5);
      this.sales_status_date = rs.getTimestamp(6);
      this.qc_verified = rs.getString(7);
      this.qc_verified_date = rs.getTimestamp(8);
      this.project_status = rs.getString(9);
      this.project_status_date = rs.getTimestamp(10);

      BigDecimal bigDec = rs.getBigDecimal(11);
      this.sectionCount = ((bigDec == null) ? null : new Integer(bigDec.intValue()));

      this.bms_sample = rs.getString(12);
      this.consentId = rs.getString(13);
      this.ardaisId = rs.getString(14);
      this.tissueFindingCid = rs.getString(15);
      this.tissueFindingOther = rs.getString(16);
      this.appearanceBest = rs.getString(17);
      this.diagnosisCuiBest = rs.getString(18);
      this.diagnosisOtherBest = rs.getString(19);
      this.tissueOriginCid = rs.getString(20);
      this.tissueOriginOther = rs.getString(21);

      jdbcFields = true;
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(ps);
      ApiFunctions.close(con);
    }

  }
  /**
   * This method was generated for supporting the association named Sample REFILTDS_ASM7 Asm.  
   *  It will be deleted/edited when the association is deleted/edited.
   * @param inKey com.ardais.bigr.iltds.beans.AsmKey
   */
  /* WARNING: THIS METHOD WILL BE REGENERATED. */
  public void privateSetAsmKey(com.ardais.bigr.iltds.beans.AsmKey inKey) {
    boolean asm_NULLTEST = (inKey == null);
    if (asm_NULLTEST)
      asm_asm_id = null;
    else
      asm_asm_id = inKey.asm_id;
  }
  /**
   * This method was generated for supporting the association named Sample REFILTDS_SAMPLE_BOX10 Samplebox.  
   *  It will be deleted/edited when the association is deleted/edited.
   * @param inKey com.ardais.bigr.iltds.beans.SampleboxKey
   */
  /* WARNING: THIS METHOD WILL BE REGENERATED. */
  public void privateSetSampleboxKey(com.ardais.bigr.iltds.beans.SampleboxKey inKey) {
    boolean samplebox_NULLTEST = (inKey == null);
    if (samplebox_NULLTEST)
      samplebox_box_barcode_id = null;
    else
      samplebox_box_barcode_id = inKey.box_barcode_id;
  }
  /**
   * This method was generated for supporting the association named Samplestatus REFILTDS_SAMPLE25 Sample.  
   *  It will be deleted/edited when the association is deleted/edited.
   * @param aSamplestatus com.ardais.bigr.iltds.beans.Samplestatus
   */
  /* WARNING: THIS METHOD WILL BE REGENERATED. */
  public void secondaryAddSamplestatus(com.ardais.bigr.iltds.beans.Samplestatus aSamplestatus) {
    this.getSamplestatusLink().secondaryAddElement(aSamplestatus);
  }
  /**
   * This method was generated for supporting the association named Samplestatus REFILTDS_SAMPLE25 Sample.  
   *  It will be deleted/edited when the association is deleted/edited.
   * @param aSamplestatus com.ardais.bigr.iltds.beans.Samplestatus
   */
  /* WARNING: THIS METHOD WILL BE REGENERATED. */
  public void secondaryRemoveSamplestatus(com.ardais.bigr.iltds.beans.Samplestatus aSamplestatus) {
    this.getSamplestatusLink().secondaryRemoveElement(aSamplestatus);
  }
  /**
   * This method was generated for supporting the association named Sample REFILTDS_ASM7 Asm.  
   *  It will be deleted/edited when the association is deleted/edited.
   * @param anAsm com.ardais.bigr.iltds.beans.Asm
   */
  /* WARNING: THIS METHOD WILL BE REGENERATED. */
  public void secondarySetAsm(com.ardais.bigr.iltds.beans.Asm anAsm)
    throws java.rmi.RemoteException {
    this.getAsmLink().secondarySet(anAsm);
  }
  /**
   * This method was generated for supporting the association named Sample REFILTDS_SAMPLE_BOX10 Samplebox.  
   *  It will be deleted/edited when the association is deleted/edited.
   * @param aSamplebox com.ardais.bigr.iltds.beans.Samplebox
   */
  /* WARNING: THIS METHOD WILL BE REGENERATED. */
  public void secondarySetSamplebox(com.ardais.bigr.iltds.beans.Samplebox aSamplebox)
    throws java.rmi.RemoteException {
    this.getSampleboxLink().secondarySet(aSamplebox);
  }
  /**
   * Setter method for allocation_ind
   * @param newValue java.lang.String
   */
  public void setAllocation_ind(java.lang.String newValue) {
    this.allocation_ind = newValue;
  }
  /**
   * Setter method for ardais_acct_key
   */
  public void setArdais_acct_key(java.lang.String newValue) {
    this.ardais_acct_key = newValue;
  }
  /**
   * This method was generated for supporting the association named Sample REFILTDS_ASM7 Asm.  
   *  It will be deleted/edited when the association is deleted/edited.
   * @param anAsm com.ardais.bigr.iltds.beans.Asm
   */
  /* WARNING: THIS METHOD WILL BE REGENERATED. */
  public void setAsm(com.ardais.bigr.iltds.beans.Asm anAsm) throws java.rmi.RemoteException {
    this.getAsmLink().set(anAsm);
  }
  /**
   * Insert the method's description here.
   * Creation date: (3/27/01 11:06:28 AM)
   * @param newAsm_asm_id java.lang.String
   */
  public void setAsm_asm_id(java.lang.String newAsm_asm_id) {
    asm_asm_id = newAsm_asm_id;
  }
  /**
   * Setter method for asm_note
   * @param newValue java.lang.String
   */
  public void setAsm_note(java.lang.String newValue) {
    this.asm_note = newValue;
  }
  /**
   * Setter method for asm_position
   */
  public void setAsm_position(java.lang.String newValue) {
    this.asm_position = newValue;
  }
  /**
   * Setter method for barcode_scan_datetime
   * @param newValue java.sql.Timestamp
   */
  public void setBarcode_scan_datetime(java.sql.Timestamp newValue) {
    this.barcode_scan_datetime = newValue;
  }
  /**
   * Setter method for born_date
   */
  public void setBorn_date(java.sql.Timestamp newValue) {
    this.born_date = newValue;
  }
  /**
   * Setter method for cell_ref_location
   * @param newValue java.lang.String
   */
  public void setCell_ref_location(java.lang.String newValue) {
    this.cell_ref_location = newValue;
  }
  /**
   * setEntityContext method comment
   * @param ctx javax.ejb.EntityContext
   */
  public void setEntityContext(javax.ejb.EntityContext ctx) throws java.rmi.RemoteException {
    entityContext = ctx;
  }
  /**
   * Setter method for orphan_datetime
   * @param newValue java.sql.Timestamp
   */
  public void setOrphan_datetime(java.sql.Timestamp newValue) {
    this.orphan_datetime = newValue;
  }
  /**
   * Setter method for parent_barcode_id
   */
  public void setParent_barcode_id(java.lang.String newValue) {
    this.parent_barcode_id = newValue;
  }
  /**
   * This method was generated for supporting the association named Sample REFILTDS_SAMPLE_BOX10 Samplebox.  
   *  It will be deleted/edited when the association is deleted/edited.
   * @param aSamplebox com.ardais.bigr.iltds.beans.Samplebox
   */
  /* WARNING: THIS METHOD WILL BE REGENERATED. */
  public void setSamplebox(com.ardais.bigr.iltds.beans.Samplebox aSamplebox)
    throws java.rmi.RemoteException {
    this.getSampleboxLink().set(aSamplebox);
  }
  /**
   * Insert the method's description here.
   * Creation date: (2/26/01 10:37:33 AM)
   * @param newSamplebox_box_barcode_id java.lang.String
   */
  public void setSamplebox_box_barcode_id(java.lang.String newSamplebox_box_barcode_id) {
    samplebox_box_barcode_id = newSamplebox_box_barcode_id;
  }
  /**
   * Setter method for subdivision_date
   */
  public void setSubdivision_date(java.sql.Timestamp newValue) {
    this.subdivision_date = newValue;
  }
  /**
   * Setter method for type_of_fixative
   * @param newValue java.lang.String
   */
  public void setType_of_fixative(java.lang.String newValue) {
    this.type_of_fixative = newValue;
  }
  /**
   * unsetEntityContext method comment
   */
  public void unsetEntityContext() throws java.rmi.RemoteException {
    entityContext = null;
  }
  /**
   * Get accessor for persistent attribute: pullYN
   */
  public java.lang.String getPullYN() {
    return pullYN;
  }
  /**
   * Set accessor for persistent attribute: pullYN
   */
  public void setPullYN(java.lang.String newPullYN) {
    pullYN = newPullYN;
  }
  /**
   * Get accessor for persistent attribute: pullReason
   */
  public java.lang.String getPullReason() {
    return pullReason;
  }
  /**
   * Set accessor for persistent attribute: pullReason
   */
  public void setPullReason(java.lang.String newPullReason) {
    pullReason = newPullReason;
  }
  /**
   * Get accessor for persistent attribute: releasedYN
   */
  public java.lang.String getReleasedYN() {
    return releasedYN;
  }
  /**
   * Set accessor for persistent attribute: releasedYN
   */
  public void setReleasedYN(java.lang.String newReleasedYN) {
    releasedYN = newReleasedYN;
  }
  /**
   * Get accessor for persistent attribute: discordantYN
   */
  public java.lang.String getDiscordantYN() {
    return discordantYN;
  }
  /**
   * Set accessor for persistent attribute: discordantYN
   */
  public void setDiscordantYN(java.lang.String newDiscordantYN) {
    discordantYN = newDiscordantYN;
  }
  /**
   * Get accessor for persistent attribute: pullDate
   */
  public java.sql.Timestamp getPullDate() {
    return pullDate;
  }
  /**
   * Set accessor for persistent attribute: pullDate
   */
  public void setPullDate(java.sql.Timestamp newPullDate) {
    pullDate = newPullDate;
  }
  /**
   * Get accessor for persistent attribute: diMinThicknessPfinCid
   */
  public java.lang.String getDiMinThicknessPfinCid() {
    return diMinThicknessPfinCid;
  }
  /**
   * Set accessor for persistent attribute: diMinThicknessPfinCid
   */
  public void setDiMinThicknessPfinCid(java.lang.String newDiMinThicknessPfinCid) {
    diMinThicknessPfinCid = newDiMinThicknessPfinCid;
  }
  /**
   * Get accessor for persistent attribute: diMaxThicknessPfinCid
   */
  public java.lang.String getDiMaxThicknessPfinCid() {
    return diMaxThicknessPfinCid;
  }
  /**
   * Set accessor for persistent attribute: diMaxThicknessPfinCid
   */
  public void setDiMaxThicknessPfinCid(java.lang.String newDiMaxThicknessPfinCid) {
    diMaxThicknessPfinCid = newDiMaxThicknessPfinCid;
  }
  /**
   * Get accessor for persistent attribute: diWidthAcrossPfinCid
   */
  public java.lang.String getDiWidthAcrossPfinCid() {
    return diWidthAcrossPfinCid;
  }
  /**
   * Set accessor for persistent attribute: diWidthAcrossPfinCid
   */
  public void setDiWidthAcrossPfinCid(java.lang.String newDiWidthAcrossPfinCid) {
    diWidthAcrossPfinCid = newDiWidthAcrossPfinCid;
  }
  /**
   * Get accessor for persistent attribute: formatDetailCid
   */
  public java.lang.String getFormatDetailCid() {
    return formatDetailCid;
  }
  /**
   * Set accessor for persistent attribute: formatDetailCid
   */
  public void setFormatDetailCid(java.lang.String newFormatDetailCid) {
    formatDetailCid = newFormatDetailCid;
  }
  /**
   * Get accessor for persistent attribute: sampleSizeMeetsSpecs
   */
  public java.lang.String getSampleSizeMeetsSpecs() {
    return sampleSizeMeetsSpecs;
  }
  /**
   * Set accessor for persistent attribute: sampleSizeMeetsSpecs
   */
  public void setSampleSizeMeetsSpecs(java.lang.String newSampleSizeMeetsSpecs) {
    sampleSizeMeetsSpecs = newSampleSizeMeetsSpecs;
  }
  /**
   * Get accessor for persistent attribute: hoursInFixative
   */
  public java.lang.Integer getHoursInFixative() {
    return hoursInFixative;
  }
  /**
   * Set accessor for persistent attribute: hoursInFixative
   */
  public void setHoursInFixative(java.lang.Integer newHoursInFixative) {
    hoursInFixative = newHoursInFixative;
  }
  /**
   * Get accessor for persistent attribute: qcpostedYN
   */
  public java.lang.String getQcpostedYN() {
    return qcpostedYN;
  }
  /**
   * Set accessor for persistent attribute: qcpostedYN
   */
  public void setQcpostedYN(java.lang.String newQcpostedYN) {
    qcpostedYN = newQcpostedYN;
  }
  /**
   * Get accessor for persistent attribute: histoMinThicknessPfinCid
   */
  public java.lang.String getHistoMinThicknessPfinCid() {
    return histoMinThicknessPfinCid;
  }
  /**
   * Set accessor for persistent attribute: histoMinThicknessPfinCid
   */
  public void setHistoMinThicknessPfinCid(java.lang.String newHistoMinThicknessPfinCid) {
    histoMinThicknessPfinCid = newHistoMinThicknessPfinCid;
  }
  /**
   * Get accessor for persistent attribute: histoMaxThicknessPfinCid
   */
  public java.lang.String getHistoMaxThicknessPfinCid() {
    return histoMaxThicknessPfinCid;
  }
  /**
   * Set accessor for persistent attribute: histoMaxThicknessPfinCid
   */
  public void setHistoMaxThicknessPfinCid(java.lang.String newHistoMaxThicknessPfinCid) {
    histoMaxThicknessPfinCid = newHistoMaxThicknessPfinCid;
  }
  /**
   * Get accessor for persistent attribute: histoWidthAcrossPfinCid
   */
  public java.lang.String getHistoWidthAcrossPfinCid() {
    return histoWidthAcrossPfinCid;
  }
  /**
   * Set accessor for persistent attribute: histoWidthAcrossPfinCid
   */
  public void setHistoWidthAcrossPfinCid(java.lang.String newHistoWidthAcrossPfinCid) {
    histoWidthAcrossPfinCid = newHistoWidthAcrossPfinCid;
  }
  /**
   * Get accessor for persistent attribute: histoReembedReasonCid
   */
  public java.lang.String getHistoReembedReasonCid() {
    return histoReembedReasonCid;
  }
  /**
   * Set accessor for persistent attribute: histoReembedReasonCid
   */
  public void setHistoReembedReasonCid(java.lang.String newHistoReembedReasonCid) {
    histoReembedReasonCid = newHistoReembedReasonCid;
  }
  /**
   * Get accessor for persistent attribute: histoSubdividable
   */
  public java.lang.String getHistoSubdividable() {
    return histoSubdividable;
  }
  /**
   * Set accessor for persistent attribute: histoSubdividable
   */
  public void setHistoSubdividable(java.lang.String newHistoSubdividable) {
    histoSubdividable = newHistoSubdividable;
  }
  /**
   * Get accessor for persistent attribute: histoNotes
   */
  public java.lang.String getHistoNotes() {
    return histoNotes;
  }
  /**
   * Set accessor for persistent attribute: histoNotes
   */
  public void setHistoNotes(java.lang.String newHistoNotes) {
    histoNotes = newHistoNotes;
  }
  /**
   * Get accessor for persistent attribute: otherHistoReembedReason
   */
  public java.lang.String getOtherHistoReembedReason() {
    return otherHistoReembedReason;
  }
  /**
   * Set accessor for persistent attribute: otherHistoReembedReason
   */
  public void setOtherHistoReembedReason(java.lang.String newOtherHistoReembedReason) {
    otherHistoReembedReason = newOtherHistoReembedReason;
  }
  /**
   * Get accessor for persistent attribute: paraffinFeatureCid
   */
  public java.lang.String getParaffinFeatureCid() {
    return paraffinFeatureCid;
  }
  /**
   * Set accessor for persistent attribute: paraffinFeatureCid
   */
  public void setParaffinFeatureCid(java.lang.String newParaffinFeatureCid) {
    paraffinFeatureCid = newParaffinFeatureCid;
  }
  /**
   * Get accessor for persistent attribute: otherParaffinFeature
   */
  public java.lang.String getOtherParaffinFeature() {
    return otherParaffinFeature;
  }
  /**
   * Set accessor for persistent attribute: otherParaffinFeature
   */
  public void setOtherParaffinFeature(java.lang.String newOtherParaffinFeature) {
    otherParaffinFeature = newOtherParaffinFeature;
  }
  /**
   * Get accessor for persistent attribute: receiptDate
   */
  public java.sql.Timestamp getReceiptDate() {
    return receiptDate;
  }
  /**
   * Set accessor for persistent attribute: receiptDate
   */
  public void setReceiptDate(java.sql.Timestamp newReceiptDate) {
    receiptDate = newReceiptDate;
  }
  /**
   * Get accessor for persistent attribute: lastknownlocationid
   */
  public java.lang.String getLastknownlocationid() {
    return lastknownlocationid;
  }
  /**
   * Set accessor for persistent attribute: lastknownlocationid
   */
  public void setLastknownlocationid(java.lang.String newLastknownlocationid) {
    lastknownlocationid = newLastknownlocationid;
  }
  /**
   * Get accessor for persistent attribute: importedYN
   */
  public java.lang.String getImportedYN() {
    return importedYN;
  }
  /**
   * Set accessor for persistent attribute: importedYN
   */
  public void setImportedYN(java.lang.String newImportedYN) {
    importedYN = newImportedYN;
  }
  /**
   * Get accessor for persistent attribute: customerId
   */
  public java.lang.String getCustomerId() {
    return customerId;
  }
  /**
   * Set accessor for persistent attribute: customerId
   */
  public void setCustomerId(java.lang.String newCustomerId) {
    customerId = newCustomerId;
  }
  /**
   * Get accessor for persistent attribute: sampleTypeCid
   */
  public java.lang.String getSampleTypeCid() {
    return sampleTypeCid;
  }
  /**
   * Set accessor for persistent attribute: sampleTypeCid
   */
  public void setSampleTypeCid(java.lang.String newSampleTypeCid) {
    sampleTypeCid = newSampleTypeCid;
  }
  /**
   * Get accessor for persistent attribute: collection_datetime
   */
  public java.sql.Timestamp getCollection_datetime() {
    return collection_datetime;
  }
  /**
   * Set accessor for persistent attribute: collection_datetime
   */
  public void setCollection_datetime(java.sql.Timestamp newCollection_datetime) {
    collection_datetime = newCollection_datetime;
  }
  /**
   * Get accessor for persistent attribute: preservation_datetime
   */
  public java.sql.Timestamp getPreservation_datetime() {
    return preservation_datetime;
  }
  /**
   * Set accessor for persistent attribute: preservation_datetime
   */
  public void setPreservation_datetime(java.sql.Timestamp newPreservation_datetime) {
    preservation_datetime = newPreservation_datetime;
  }
    /**
   * Get accessor for persistent attribute: volume
   */
  public java.math.BigDecimal getVolume() {
    return volume;
  }
  /**
   * Set accessor for persistent attribute: volume
   */
  public void setVolume(java.math.BigDecimal newVolume) {
    volume = newVolume;
  }
  /**
   * Get accessor for persistent attribute: volumeUnitCui
   */
  public java.lang.String getVolumeUnitCui(){
   return volumeUnitCui;   
  }
  /**
   * Set accessor for persistent attribute: volumeUnitCui
   */
  public void setVolumeUnitCui(java.lang.String newVolumeUnitCui){
     volumeUnitCui = newVolumeUnitCui; 
   }
  /**
   * Set accessor for persistent attribute: volumeInUl
   */
    public void setVolumeInUl(java.math.BigDecimal newVolume) {
    volumeInUl = newVolume;
      }
  /**
   * Get accessor for persistent attribute: volumeInUl
   */
  public java.math.BigDecimal getVolumeInUl() {
    return volumeInUl;
  }
  /**
   * Get accessor for persistent attribute: weight
   */
  public java.math.BigDecimal getWeight() {
    return weight;
  }
  /**
   * Set accessor for persistent attribute: weight
   */
  public void setWeight(java.math.BigDecimal newWeight) {
    weight = newWeight;
  }
  /**
   * Get accessor for persistent attribute: weightInMg
   */
  public java.math.BigDecimal getWeightInMg() {
    return weightInMg;
  }
  /**
   * Set accessor for persistent attribute: weightInMg
   */
  public void setWeightInMg(java.math.BigDecimal newWeight) {
    weightInMg = newWeight;
  }
  /**
   * Get accessor for persistent attribute: weightUnitCui
   */
  public java.lang.String getWeightUnitCui(){
   return weightUnitCui;   
  }
  /**
   * Set accessor for persistent attribute: weightUnitCui
   */
  public void setWeightUnitCui(java.lang.String newWeightUnitCui){
     weightUnitCui = newWeightUnitCui; 
   }
  
   /**
   * Get accessor for persistent attribute: collection_datetime_dpc
   */
  public java.lang.String getCollection_datetime_dpc() {
    return collection_datetime_dpc;
  }
  /**
   * Set accessor for persistent attribute: collection_datetime_dpc
   */
  public void setCollection_datetime_dpc(java.lang.String newCollection_datetime_dpc) {
    collection_datetime_dpc = newCollection_datetime_dpc;
  }
  /**
   * Get accessor for persistent attribute: preservation_datetime_dpc
   */
  public java.lang.String getPreservation_datetime_dpc() {
    return preservation_datetime_dpc;
  }
  /**
   * Set accessor for persistent attribute: preservation_datetime_dpc
   */
  public void setPreservation_datetime_dpc(java.lang.String newPreservation_datetime_dpc) {
    preservation_datetime_dpc = newPreservation_datetime_dpc;
  }
  /**
   * Get accessor for persistent attribute: bufferTypeCui
   */
  public java.lang.String getBufferTypeCui() {
    return bufferTypeCui;
  }
  /**
   * Set accessor for persistent attribute: bufferTypeCui
   */
  public void setBufferTypeCui(java.lang.String newBufferTypeCui) {
    bufferTypeCui = newBufferTypeCui;
  }
  /**
   * Get accessor for persistent attribute: bufferTypeOther
   */
  public java.lang.String getBufferTypeOther() {
    return bufferTypeOther;
  }
  /**
   * Set accessor for persistent attribute: bufferTypeOther
   */
  public void setBufferTypeOther(java.lang.String newBufferTypeOther) {
    bufferTypeOther = newBufferTypeOther;
  }
  /**
   * Get accessor for persistent attribute: totalNumOfCells
   */
  public java.math.BigDecimal getTotalNumOfCells() {
    return totalNumOfCells;
  }
  /**
   * Set accessor for persistent attribute: totalNumOfCells
   */
  public void setTotalNumOfCells(java.math.BigDecimal newTotalNumOfCells) {
    totalNumOfCells = newTotalNumOfCells;
  }
  /**
   * Get accessor for persistent attribute: totalNumOfCellsExRepCui
   */
  public java.lang.String getTotalNumOfCellsExRepCui() {
    return totalNumOfCellsExRepCui;
  }
  /**
   * Set accessor for persistent attribute: totalNumOfCellsExRepCui
   */
  public void setTotalNumOfCellsExRepCui(java.lang.String newTotalNumOfCellsExRepCui) {
    totalNumOfCellsExRepCui = newTotalNumOfCellsExRepCui;
  }
  /**
   * Get accessor for persistent attribute: cellsPerMl
   */
  public java.math.BigDecimal getCellsPerMl() {
    return cellsPerMl;
  }
  /**
   * Set accessor for persistent attribute: cellsPerMl
   */
  public void setCellsPerMl(java.math.BigDecimal newCellsPerMl) {
    cellsPerMl = newCellsPerMl;
  }
  /**
   * _copyFromEJB
   */
  public java.util.Hashtable _copyFromEJB() {
    com.ibm.ivj.ejb.runtime.AccessBeanHashtable h = new com.ibm.ivj.ejb.runtime.AccessBeanHashtable();

    h.put("pullYN", getPullYN());
    h.put("histoMaxThicknessPfinCid", getHistoMaxThicknessPfinCid());
    h.put("otherHistoReembedReason", getOtherHistoReembedReason());
    h.put("project_status_date", getProject_status_date());
    h.put("diagnosisCuiBest", getDiagnosisCuiBest());
    h.put("hoursInFixative", getHoursInFixative());
    h.put("bufferTypeCui", getBufferTypeCui());
    h.put("diMinThicknessPfinCid", getDiMinThicknessPfinCid());
    h.put("project_status", getProject_status());
    h.put("releasedYN", getReleasedYN());
    h.put("histoNotes", getHistoNotes());
    h.put("sales_status_date", getSales_status_date());
    h.put("formatDetailCid", getFormatDetailCid());
    h.put("qc_verified_date", getQc_verified_date());
    h.put("collection_datetime", getCollection_datetime());
    h.put("receiptDate", getReceiptDate());
    h.put("otherParaffinFeature", getOtherParaffinFeature());
    h.put("pullDate", getPullDate());
    h.put("sales_status", getSales_status());
    h.put("sampleSizeMeetsSpecs", getSampleSizeMeetsSpecs());
    h.put("diMaxThicknessPfinCid", getDiMaxThicknessPfinCid());
    h.put("pullReason", getPullReason());
    h.put("tissueOriginCid", getTissueOriginCid());
    h.put("preservation_datetime", getPreservation_datetime());
    h.put("qc_status_date", getQc_status_date());
    h.put("qcpostedYN", getQcpostedYN());
    h.put("consentId", getConsentId());
    h.put("tissueOriginOther", getTissueOriginOther());
    h.put("sampleboxKey", getSampleboxKey());
    h.put("preservation_datetime_dpc", getPreservation_datetime_dpc());
    h.put("volume", getVolume());
    h.put("inv_status_date", getInv_status_date());
    h.put("subdivision_date", getSubdivision_date());
    h.put("asm_position", getAsm_position());
    h.put("sampleTypeCid", getSampleTypeCid());
    h.put("qc_status", getQc_status());
    h.put("bms_sample", getBms_sample());
    h.put("inv_status", getInv_status());
    h.put("sectionCount", getSectionCount());
    h.put("tissueFindingOther", getTissueFindingOther());
    h.put("customerId", getCustomerId());
    h.put("cell_ref_location", getCell_ref_location());
    h.put("bufferTypeOther", getBufferTypeOther());
    h.put("orphan_datetime", getOrphan_datetime());
    h.put("importedYN", getImportedYN());
    h.put("cellsPerMl", getCellsPerMl());
    h.put("totalNumOfCells", getTotalNumOfCells());
    h.put("diagnosisOtherBest", getDiagnosisOtherBest());
    h.put("allocation_ind", getAllocation_ind());
    h.put("tissueFindingCid", getTissueFindingCid());
    h.put("discordantYN", getDiscordantYN());
    h.put("lastknownlocationid", getLastknownlocationid());
    h.put("type_of_fixative", getType_of_fixative());
    h.put("born_date", getBorn_date());
    h.put("collection_datetime_dpc", getCollection_datetime_dpc());
    h.put("totalNumOfCellsExRepCui", getTotalNumOfCellsExRepCui());
    h.put("parent_barcode_id", getParent_barcode_id());
    h.put("paraffinFeatureCid", getParaffinFeatureCid());
    h.put("histoWidthAcrossPfinCid", getHistoWidthAcrossPfinCid());
    h.put("asm_asm_id", getAsm_asm_id());
    h.put("diWidthAcrossPfinCid", getDiWidthAcrossPfinCid());
    h.put("samplebox_box_barcode_id", getSamplebox_box_barcode_id());
    h.put("asm_note", getAsm_note());
    h.put("appearanceBest", getAppearanceBest());
    h.put("asmKey", getAsmKey());
    h.put("histoMinThicknessPfinCid", getHistoMinThicknessPfinCid());
    h.put("histoReembedReasonCid", getHistoReembedReasonCid());
    h.put("barcode_scan_datetime", getBarcode_scan_datetime());
    h.put("ardais_acct_key", getArdais_acct_key());
    h.put("histoSubdividable", getHistoSubdividable());
    h.put("ardaisId", getArdaisId());
    h.put("qc_verified", getQc_verified());
    h.put("percentViability", getPercentViability());
    h.put("source", getSource());
    h.put("yield", getYield());
    h.put("concentration", getConcentration());
    h.put("volumeUnitCui", getVolumeUnitCui());
    h.put("weightInMg", getWeightInMg());
    h.put("weight", getWeight());
    h.put("volumeInUl", getVolumeInUl());
    h.put("weightUnitCui", getWeightUnitCui());
    h.put("sampleRegistrationForm", getSampleRegistrationForm());
    h.put("__Key", getEntityContext().getPrimaryKey());

    return h;
  }
  /**
   * _copyToEJB
   */
  public void _copyToEJB(java.util.Hashtable h) {
    java.lang.String localPullYN = (java.lang.String) h.get("pullYN");
    java.lang.String localHistoMaxThicknessPfinCid = (java.lang.String) h
      .get("histoMaxThicknessPfinCid");
    java.lang.String localOtherHistoReembedReason = (java.lang.String) h
      .get("otherHistoReembedReason");
    java.lang.Integer localHoursInFixative = (java.lang.Integer) h.get("hoursInFixative");
    java.lang.String localBufferTypeCui = (java.lang.String) h.get("bufferTypeCui");
    java.lang.String localDiMinThicknessPfinCid = (java.lang.String) h.get("diMinThicknessPfinCid");
    java.lang.String localReleasedYN = (java.lang.String) h.get("releasedYN");
    java.lang.String localHistoNotes = (java.lang.String) h.get("histoNotes");
    java.lang.String localFormatDetailCid = (java.lang.String) h.get("formatDetailCid");
    java.sql.Timestamp localCollection_datetime = (java.sql.Timestamp) h.get("collection_datetime");
    java.sql.Timestamp localReceiptDate = (java.sql.Timestamp) h.get("receiptDate");
    java.lang.String localOtherParaffinFeature = (java.lang.String) h.get("otherParaffinFeature");
    java.sql.Timestamp localPullDate = (java.sql.Timestamp) h.get("pullDate");
    java.lang.String localSampleSizeMeetsSpecs = (java.lang.String) h.get("sampleSizeMeetsSpecs");
    java.lang.String localDiMaxThicknessPfinCid = (java.lang.String) h.get("diMaxThicknessPfinCid");
    java.lang.String localPullReason = (java.lang.String) h.get("pullReason");
    java.sql.Timestamp localPreservation_datetime = (java.sql.Timestamp) h
      .get("preservation_datetime");
    java.lang.String localQcpostedYN = (java.lang.String) h.get("qcpostedYN");
    java.lang.String localPreservation_datetime_dpc = (java.lang.String) h
      .get("preservation_datetime_dpc");
    java.math.BigDecimal localVolume = (java.math.BigDecimal) h.get("volume");
    java.sql.Timestamp localSubdivision_date = (java.sql.Timestamp) h.get("subdivision_date");
    java.lang.String localAsm_position = (java.lang.String) h.get("asm_position");
    java.lang.String localSampleTypeCid = (java.lang.String) h.get("sampleTypeCid");
    java.lang.String localCustomerId = (java.lang.String) h.get("customerId");
    java.lang.String localCell_ref_location = (java.lang.String) h.get("cell_ref_location");
    java.lang.String localBufferTypeOther = (java.lang.String) h.get("bufferTypeOther");
    java.sql.Timestamp localOrphan_datetime = (java.sql.Timestamp) h.get("orphan_datetime");
    java.lang.String localImportedYN = (java.lang.String) h.get("importedYN");
    java.math.BigDecimal localCellsPerMl = (java.math.BigDecimal) h.get("cellsPerMl");
    java.math.BigDecimal localTotalNumOfCells = (java.math.BigDecimal) h.get("totalNumOfCells");
    java.lang.String localAllocation_ind = (java.lang.String) h.get("allocation_ind");
    java.lang.String localDiscordantYN = (java.lang.String) h.get("discordantYN");
    java.lang.String localLastknownlocationid = (java.lang.String) h.get("lastknownlocationid");
    java.lang.String localType_of_fixative = (java.lang.String) h.get("type_of_fixative");
    java.sql.Timestamp localBorn_date = (java.sql.Timestamp) h.get("born_date");
    java.lang.String localCollection_datetime_dpc = (java.lang.String) h
      .get("collection_datetime_dpc");
    java.lang.String localTotalNumOfCellsExRepCui = (java.lang.String) h
      .get("totalNumOfCellsExRepCui");
    java.lang.String localParent_barcode_id = (java.lang.String) h.get("parent_barcode_id");
    java.lang.String localParaffinFeatureCid = (java.lang.String) h.get("paraffinFeatureCid");
    java.lang.String localHistoWidthAcrossPfinCid = (java.lang.String) h
      .get("histoWidthAcrossPfinCid");
    java.lang.String localAsm_asm_id = (java.lang.String) h.get("asm_asm_id");
    java.lang.String localDiWidthAcrossPfinCid = (java.lang.String) h.get("diWidthAcrossPfinCid");
    java.lang.String localSamplebox_box_barcode_id = (java.lang.String) h
      .get("samplebox_box_barcode_id");
    java.lang.String localAsm_note = (java.lang.String) h.get("asm_note");
    java.lang.String localHistoMinThicknessPfinCid = (java.lang.String) h
      .get("histoMinThicknessPfinCid");
    java.lang.String localHistoReembedReasonCid = (java.lang.String) h.get("histoReembedReasonCid");
    java.sql.Timestamp localBarcode_scan_datetime = (java.sql.Timestamp) h
      .get("barcode_scan_datetime");
    java.lang.String localArdais_acct_key = (java.lang.String) h.get("ardais_acct_key");
    java.lang.String localHistoSubdividable = (java.lang.String) h.get("histoSubdividable");
    java.lang.Integer localPercentViability = (java.lang.Integer) h.get("percentViability");
    java.lang.String localSource = (java.lang.String) h.get("source");
    java.math.BigDecimal localYield = (java.math.BigDecimal) h.get("yield");
    java.math.BigDecimal localConcentration = (java.math.BigDecimal) h.get("concentration");
    java.lang.String localVolumeUnitCui = (java.lang.String) h.get("volumeUnitCui");
    java.math.BigDecimal localWeightInMg = (java.math.BigDecimal) h.get("weightInMg");
    java.math.BigDecimal localWeight = (java.math.BigDecimal) h.get("weight");
    java.math.BigDecimal localVolumeInUl = (java.math.BigDecimal) h.get("volumeInUl");
    java.lang.String localWeightUnitCui = (java.lang.String) h.get("weightUnitCui");
    java.lang.String localSampleRegistrationForm = (java.lang.String) h
      .get("sampleRegistrationForm");

    if (h.containsKey("pullYN"))
      setPullYN((localPullYN));
    if (h.containsKey("histoMaxThicknessPfinCid"))
      setHistoMaxThicknessPfinCid((localHistoMaxThicknessPfinCid));
    if (h.containsKey("otherHistoReembedReason"))
      setOtherHistoReembedReason((localOtherHistoReembedReason));
    if (h.containsKey("hoursInFixative"))
      setHoursInFixative((localHoursInFixative));
    if (h.containsKey("bufferTypeCui"))
      setBufferTypeCui((localBufferTypeCui));
    if (h.containsKey("diMinThicknessPfinCid"))
      setDiMinThicknessPfinCid((localDiMinThicknessPfinCid));
    if (h.containsKey("releasedYN"))
      setReleasedYN((localReleasedYN));
    if (h.containsKey("histoNotes"))
      setHistoNotes((localHistoNotes));
    if (h.containsKey("formatDetailCid"))
      setFormatDetailCid((localFormatDetailCid));
    if (h.containsKey("collection_datetime"))
      setCollection_datetime((localCollection_datetime));
    if (h.containsKey("receiptDate"))
      setReceiptDate((localReceiptDate));
    if (h.containsKey("otherParaffinFeature"))
      setOtherParaffinFeature((localOtherParaffinFeature));
    if (h.containsKey("pullDate"))
      setPullDate((localPullDate));
    if (h.containsKey("sampleSizeMeetsSpecs"))
      setSampleSizeMeetsSpecs((localSampleSizeMeetsSpecs));
    if (h.containsKey("diMaxThicknessPfinCid"))
      setDiMaxThicknessPfinCid((localDiMaxThicknessPfinCid));
    if (h.containsKey("pullReason"))
      setPullReason((localPullReason));
    if (h.containsKey("preservation_datetime"))
      setPreservation_datetime((localPreservation_datetime));
    if (h.containsKey("qcpostedYN"))
      setQcpostedYN((localQcpostedYN));
    if (h.containsKey("preservation_datetime_dpc"))
      setPreservation_datetime_dpc((localPreservation_datetime_dpc));
    if (h.containsKey("volume"))
      setVolume((localVolume));
    if (h.containsKey("subdivision_date"))
      setSubdivision_date((localSubdivision_date));
    if (h.containsKey("asm_position"))
      setAsm_position((localAsm_position));
    if (h.containsKey("sampleTypeCid"))
      setSampleTypeCid((localSampleTypeCid));
    if (h.containsKey("customerId"))
      setCustomerId((localCustomerId));
    if (h.containsKey("cell_ref_location"))
      setCell_ref_location((localCell_ref_location));
    if (h.containsKey("bufferTypeOther"))
      setBufferTypeOther((localBufferTypeOther));
    if (h.containsKey("orphan_datetime"))
      setOrphan_datetime((localOrphan_datetime));
    if (h.containsKey("importedYN"))
      setImportedYN((localImportedYN));
    if (h.containsKey("cellsPerMl"))
      setCellsPerMl((localCellsPerMl));
    if (h.containsKey("totalNumOfCells"))
      setTotalNumOfCells((localTotalNumOfCells));
    if (h.containsKey("allocation_ind"))
      setAllocation_ind((localAllocation_ind));
    if (h.containsKey("discordantYN"))
      setDiscordantYN((localDiscordantYN));
    if (h.containsKey("lastknownlocationid"))
      setLastknownlocationid((localLastknownlocationid));
    if (h.containsKey("type_of_fixative"))
      setType_of_fixative((localType_of_fixative));
    if (h.containsKey("born_date"))
      setBorn_date((localBorn_date));
    if (h.containsKey("collection_datetime_dpc"))
      setCollection_datetime_dpc((localCollection_datetime_dpc));
    if (h.containsKey("totalNumOfCellsExRepCui"))
      setTotalNumOfCellsExRepCui((localTotalNumOfCellsExRepCui));
    if (h.containsKey("parent_barcode_id"))
      setParent_barcode_id((localParent_barcode_id));
    if (h.containsKey("paraffinFeatureCid"))
      setParaffinFeatureCid((localParaffinFeatureCid));
    if (h.containsKey("histoWidthAcrossPfinCid"))
      setHistoWidthAcrossPfinCid((localHistoWidthAcrossPfinCid));
    if (h.containsKey("asm_asm_id"))
      setAsm_asm_id((localAsm_asm_id));
    if (h.containsKey("diWidthAcrossPfinCid"))
      setDiWidthAcrossPfinCid((localDiWidthAcrossPfinCid));
    if (h.containsKey("samplebox_box_barcode_id"))
      setSamplebox_box_barcode_id((localSamplebox_box_barcode_id));
    if (h.containsKey("asm_note"))
      setAsm_note((localAsm_note));
    if (h.containsKey("histoMinThicknessPfinCid"))
      setHistoMinThicknessPfinCid((localHistoMinThicknessPfinCid));
    if (h.containsKey("histoReembedReasonCid"))
      setHistoReembedReasonCid((localHistoReembedReasonCid));
    if (h.containsKey("barcode_scan_datetime"))
      setBarcode_scan_datetime((localBarcode_scan_datetime));
    if (h.containsKey("ardais_acct_key"))
      setArdais_acct_key((localArdais_acct_key));
    if (h.containsKey("histoSubdividable"))
      setHistoSubdividable((localHistoSubdividable));
    if (h.containsKey("percentViability"))
      setPercentViability((localPercentViability));
    if (h.containsKey("source"))
      setSource((localSource));
    if (h.containsKey("yield"))
      setYield((localYield));
    if (h.containsKey("concentration"))
      setConcentration((localConcentration));
    if (h.containsKey("volumeUnitCui"))
      setVolumeUnitCui((localVolumeUnitCui));
    if (h.containsKey("weightInMg"))
      setWeightInMg((localWeightInMg));
    if (h.containsKey("weight"))
      setWeight((localWeight));
    if (h.containsKey("volumeInUl"))
      setVolumeInUl((localVolumeInUl));
    if (h.containsKey("weightUnitCui"))
      setWeightUnitCui((localWeightUnitCui));
    if (h.containsKey("sampleRegistrationForm"))
      setSampleRegistrationForm((localSampleRegistrationForm));
  }
  /**
   * Get accessor for persistent attribute: percentViability
   */
  public java.lang.Integer getPercentViability() {
    return percentViability;
  }
  /**
   * Set accessor for persistent attribute: percentViability
   */
  public void setPercentViability(java.lang.Integer newPercentViability) {
    percentViability = newPercentViability;
  }
  /**
   * Get accessor for persistent attribute: source
   */
  public java.lang.String getSource() {
    return source;
  }
  /**
   * Set accessor for persistent attribute: source
   */
  public void setSource(java.lang.String newSource) {
    source = newSource;
  }
  /**
   * Get accessor for persistent attribute: concentration
   */
  public java.math.BigDecimal getConcentration() {
    return concentration;
  }
  /**
   * Set accessor for persistent attribute: concentration
   */
  public void setConcentration(java.math.BigDecimal newConcentration) {
    concentration = newConcentration;
  }
  /**
   * Get accessor for persistent attribute: yield
   */
  public java.math.BigDecimal getYield() {
    return yield;
  }
  /**
   * Set accessor for persistent attribute: yield
   */
  public void setYield(java.math.BigDecimal newYield) {
    yield = newYield;
  }
  /**
   * Get accessor for persistent attribute: sampleRegistrationForm
   */
  public java.lang.String getSampleRegistrationForm() {
    return sampleRegistrationForm;
  }
  /**
   * Set accessor for persistent attribute: sampleRegistrationForm
   */
  public void setSampleRegistrationForm(java.lang.String newSampleRegistrationForm) {
    sampleRegistrationForm = newSampleRegistrationForm;
  }
}
