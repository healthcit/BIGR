package com.ardais.bigr.iltds.databeans;

import com.ardais.bigr.beanutils.BigrBeanUtilsBean;
import com.ardais.bigr.orm.helpers.BigrGbossData;
import com.gulfstreambio.gboss.GbossFactory;

public class TopLineData implements java.io.Serializable {
  private String appearance;
  private String diagnosis;
  private String tissue;
  private String appearance_code;
  private String diagnosis_code;
  private String tissue_code;
  private String _sampleTypeCid;
  private String tissue_finding;
  private String tissue_finding_code;
  /**
   * TopLineData constructor comment.
   */
  public TopLineData() {
    super();
  }
  /**
   * TopLineData constructor comment.
   */
  public TopLineData(TopLineData topLineData) {
    this();
    BigrBeanUtilsBean.SINGLETON.copyProperties(this, topLineData);
  }
  /**
   * Insert the method's description here.
   * Creation date: (4/2/2002 7:44:37 AM)
   * @return java.lang.String
   */
  public java.lang.String getAppearance() {
    if (appearance_code != null)
      return GbossFactory.getInstance().getDescription(appearance_code);

    return null;
  }
  /**
   * Insert the method's description here.
   * Creation date: (4/2/2002 7:44:37 AM)
   * @return java.lang.String
   */
  public java.lang.String getAppearance_code() {
    return appearance_code;
  }
  /**
   * Insert the method's description here.
   * Creation date: (3/22/2002 3:02:21 PM)
   * @return java.lang.String
   */
  public java.lang.String getDiagnosis() {
    if (diagnosis_code != null)
      return BigrGbossData.getDiagnosisDescription(diagnosis_code);
    return null;
  }
  /**
   * Insert the method's description here.
   * Creation date: (3/27/2002 1:05:35 PM)
   * @return java.lang.String
   */
  public java.lang.String getDiagnosis_code() {
    return diagnosis_code;
  }

  public java.lang.String getSampleTypeDisplay() {
    if (_sampleTypeCid != null)
      return GbossFactory.getInstance().getDescription(_sampleTypeCid);

    return null;
  }

  public java.lang.String getSampleTypeCid() {
    return _sampleTypeCid;
  }

  /**
   * Insert the method's description here.
   * Creation date: (3/22/2002 3:02:21 PM)
   * @return java.lang.String
   */
  public java.lang.String getTissue() {
    if (tissue_code != null)
      return BigrGbossData.getTissueDescription(tissue_code);

    return null;
  }
  /**
   * Insert the method's description here.
   * Creation date: (3/27/2002 1:05:35 PM)
   * @return java.lang.String
   */
  public java.lang.String getTissue_code() {
    return tissue_code;
  }
  /**
   * Insert the method's description here.
   * @return java.lang.String
   */
  public java.lang.String getTissueFinding() {
    if (tissue_finding_code != null)
      return BigrGbossData.getTissueDescription(tissue_finding_code);

    return null;
  }
  /**
   * Insert the method's description here.
   * @return java.lang.String
   */
  public java.lang.String getTissueFinding_code() {
    return tissue_finding_code;
  } /**
    * Insert the method's description here.
    * Creation date: (4/2/2002 7:44:37 AM)
    * @param newAppearance java.lang.String
    */
  public void setAppearance(java.lang.String newAppearance) {
    appearance = newAppearance;
  }
  /**
   * Insert the method's description here.
   * Creation date: (4/2/2002 7:44:37 AM)
   * @param newAppearance_code java.lang.String
   */
  public void setAppearance_code(java.lang.String newAppearance_code) {
    appearance_code = newAppearance_code;
  }
  /**
   * Insert the method's description here.
   * Creation date: (3/22/2002 3:02:21 PM)
   * @param newDiagnosis java.lang.String
   */
  public void setDiagnosis(java.lang.String newDiagnosis) {
    diagnosis = newDiagnosis;
  }
  /**
   * Insert the method's description here.
   * Creation date: (3/27/2002 1:05:35 PM)
   * @param newDiagnosis_code java.lang.String
   */
  public void setDiagnosis_code(java.lang.String newDiagnosis_code) {
    diagnosis_code = newDiagnosis_code;
  }

  public void setSampleTypeCid(String sampleTypeCid) {
    _sampleTypeCid = sampleTypeCid;
  }

  /**
   * Insert the method's description here.
   * Creation date: (3/22/2002 3:02:21 PM)
   * @param newTissue java.lang.String
   */
  public void setTissue(java.lang.String newTissue) {
    tissue = newTissue;
  }
  /**
   * Insert the method's description here.
   * Creation date: (3/27/2002 1:05:35 PM)
   * @param newTissue_code java.lang.String
   */
  public void setTissue_code(java.lang.String newTissue_code) {
    tissue_code = newTissue_code;
  }
  /**
   * Insert the method's description here.
   * @param newTissue java.lang.String
   */
  public void setTissueFinding(java.lang.String newTissue) {
    tissue_finding = newTissue;
  }
  /**
   * Insert the method's description here.
   * @param newTissue_code java.lang.String
   */
  public void setTissueFinding_code(java.lang.String newTissue_code) {
    tissue_finding_code = newTissue_code;
  }
}
