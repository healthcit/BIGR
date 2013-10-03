package com.ardais.bigr.lims.beans;
import javax.ejb.*;
import javax.rmi.*;
import com.ibm.ivj.ejb.runtime.*;
/**
 * PathologyEvaluationAccessBean
 * @generated
 */
public class PathologyEvaluationAccessBean
	extends AbstractEntityAccessBean
	implements com.ardais.bigr.lims.beans.PathologyEvaluationAccessBeanData {
  /**
   * @generated
   */
  private PathologyEvaluation __ejbRef;
  /**
   * @generated
   */
  private java.lang.String init_evaluationId;
  /**
   * @generated
   */
  private java.lang.String init_slideID;
  /**
   * @generated
   */
  private java.lang.String init_sampleId;
  /**
   * @generated
   */
  private java.lang.String init_microscopicAppearance;
  /**
   * @generated
   */
  private java.lang.String init_reportedYN;
  /**
   * @generated
   */
  private java.lang.String init_createMethod;
  /**
   * @generated
   */
  private java.lang.String init_diagnosis;
  /**
   * @generated
   */
  private java.lang.String init_tissueOfOrigin;
  /**
   * @generated
   */
  private java.lang.String init_tissueOfFinding;
  /**
   * @generated
   */
  private java.lang.Integer init_tumorCells;
  /**
   * @generated
   */
  private java.lang.Integer init_normalCells;
  /**
   * @generated
   */
  private java.lang.Integer init_hypoacellularstromaCells;
  /**
   * @generated
   */
  private java.lang.Integer init_necrosisCells;
  /**
   * @generated
   */
  private java.lang.Integer init_lesionCells;
  /**
   * @generated
   */
  private java.lang.Integer init_cellularstromaCells;
  /**
   * getLesionCells
   * @generated
   */
  public java.lang.Integer getLesionCells()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.Integer) __getCache("lesionCells")));
  }
  /**
   * setLesionCells
   * @generated
   */
  public void setLesionCells(java.lang.Integer newValue) {
    __setCache("lesionCells", newValue);
  }
  /**
   * getSlideId
   * @generated
   */
  public java.lang.String getSlideId()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.String) __getCache("slideId")));
  }
  /**
   * setSlideId
   * @generated
   */
  public void setSlideId(java.lang.String newValue) {
    __setCache("slideId", newValue);
  }
  /**
   * getHypoacellularstromaCells
   * @generated
   */
  public java.lang.Integer getHypoacellularstromaCells()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.Integer) __getCache("hypoacellularstromaCells")));
  }
  /**
   * setHypoacellularstromaCells
   * @generated
   */
  public void setHypoacellularstromaCells(java.lang.Integer newValue) {
    __setCache("hypoacellularstromaCells", newValue);
  }
  /**
   * getMicroscopicAppearance
   * @generated
   */
  public java.lang.String getMicroscopicAppearance()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.String) __getCache("microscopicAppearance")));
  }
  /**
   * setMicroscopicAppearance
   * @generated
   */
  public void setMicroscopicAppearance(java.lang.String newValue) {
    __setCache("microscopicAppearance", newValue);
  }
  /**
   * getTissueOfFinding
   * @generated
   */
  public java.lang.String getTissueOfFinding()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.String) __getCache("tissueOfFinding")));
  }
  /**
   * setTissueOfFinding
   * @generated
   */
  public void setTissueOfFinding(java.lang.String newValue) {
    __setCache("tissueOfFinding", newValue);
  }
  /**
   * getDiagnosis
   * @generated
   */
  public java.lang.String getDiagnosis()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.String) __getCache("diagnosis")));
  }
  /**
   * setDiagnosis
   * @generated
   */
  public void setDiagnosis(java.lang.String newValue) {
    __setCache("diagnosis", newValue);
  }
  /**
   * getCreateDate
   * @generated
   */
  public java.sql.Timestamp getCreateDate()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.sql.Timestamp) __getCache("createDate")));
  }
  /**
   * setCreateDate
   * @generated
   */
  public void setCreateDate(java.sql.Timestamp newValue) {
    __setCache("createDate", newValue);
  }
  /**
   * getConcatenatedInternalComments
   * @generated
   */
  public java.lang.String getConcatenatedInternalComments()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.String) __getCache("concatenatedInternalComments")));
  }
  /**
   * setConcatenatedInternalComments
   * @generated
   */
  public void setConcatenatedInternalComments(java.lang.String newValue) {
    __setCache("concatenatedInternalComments", newValue);
  }
  /**
   * getTissueOfFindingOther
   * @generated
   */
  public java.lang.String getTissueOfFindingOther()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.String) __getCache("tissueOfFindingOther")));
  }
  /**
   * setTissueOfFindingOther
   * @generated
   */
  public void setTissueOfFindingOther(java.lang.String newValue) {
    __setCache("tissueOfFindingOther", newValue);
  }
  /**
   * getTissueOfOrigin
   * @generated
   */
  public java.lang.String getTissueOfOrigin()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.String) __getCache("tissueOfOrigin")));
  }
  /**
   * setTissueOfOrigin
   * @generated
   */
  public void setTissueOfOrigin(java.lang.String newValue) {
    __setCache("tissueOfOrigin", newValue);
  }
  /**
   * getSampleId
   * @generated
   */
  public java.lang.String getSampleId()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.String) __getCache("sampleId")));
  }
  /**
   * setSampleId
   * @generated
   */
  public void setSampleId(java.lang.String newValue) {
    __setCache("sampleId", newValue);
  }
  /**
   * getSourceEvaluationId
   * @generated
   */
  public java.lang.String getSourceEvaluationId()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.String) __getCache("sourceEvaluationId")));
  }
  /**
   * setSourceEvaluationId
   * @generated
   */
  public void setSourceEvaluationId(java.lang.String newValue) {
    __setCache("sourceEvaluationId", newValue);
  }
  /**
   * getDiagnosisOther
   * @generated
   */
  public java.lang.String getDiagnosisOther()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.String) __getCache("diagnosisOther")));
  }
  /**
   * setDiagnosisOther
   * @generated
   */
  public void setDiagnosisOther(java.lang.String newValue) {
    __setCache("diagnosisOther", newValue);
  }
  /**
   * getPathologist
   * @generated
   */
  public java.lang.String getPathologist()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.String) __getCache("pathologist")));
  }
  /**
   * setPathologist
   * @generated
   */
  public void setPathologist(java.lang.String newValue) {
    __setCache("pathologist", newValue);
  }
  /**
   * getConcatenatedExternalComments
   * @generated
   */
  public java.lang.String getConcatenatedExternalComments()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.String) __getCache("concatenatedExternalComments")));
  }
  /**
   * setConcatenatedExternalComments
   * @generated
   */
  public void setConcatenatedExternalComments(java.lang.String newValue) {
    __setCache("concatenatedExternalComments", newValue);
  }
  /**
   * getReportedYN
   * @generated
   */
  public java.lang.String getReportedYN()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.String) __getCache("reportedYN")));
  }
  /**
   * setReportedYN
   * @generated
   */
  public void setReportedYN(java.lang.String newValue) {
    __setCache("reportedYN", newValue);
  }
  /**
   * getMigratedYN
   * @generated
   */
  public java.lang.String getMigratedYN()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.String) __getCache("migratedYN")));
  }
  /**
   * setMigratedYN
   * @generated
   */
  public void setMigratedYN(java.lang.String newValue) {
    __setCache("migratedYN", newValue);
  }
  /**
   * getNormalCells
   * @generated
   */
  public java.lang.Integer getNormalCells()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.Integer) __getCache("normalCells")));
  }
  /**
   * setNormalCells
   * @generated
   */
  public void setNormalCells(java.lang.Integer newValue) {
    __setCache("normalCells", newValue);
  }
  /**
   * getNecrosisCells
   * @generated
   */
  public java.lang.Integer getNecrosisCells()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.Integer) __getCache("necrosisCells")));
  }
  /**
   * setNecrosisCells
   * @generated
   */
  public void setNecrosisCells(java.lang.Integer newValue) {
    __setCache("necrosisCells", newValue);
  }
  /**
   * getCellularstromaCells
   * @generated
   */
  public java.lang.Integer getCellularstromaCells()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.Integer) __getCache("cellularstromaCells")));
  }
  /**
   * setCellularstromaCells
   * @generated
   */
  public void setCellularstromaCells(java.lang.Integer newValue) {
    __setCache("cellularstromaCells", newValue);
  }
  /**
   * getTissueOfOriginOther
   * @generated
   */
  public java.lang.String getTissueOfOriginOther()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.String) __getCache("tissueOfOriginOther")));
  }
  /**
   * setTissueOfOriginOther
   * @generated
   */
  public void setTissueOfOriginOther(java.lang.String newValue) {
    __setCache("tissueOfOriginOther", newValue);
  }
  /**
   * getReportedDate
   * @generated
   */
  public java.sql.Timestamp getReportedDate()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.sql.Timestamp) __getCache("reportedDate")));
  }
  /**
   * setReportedDate
   * @generated
   */
  public void setReportedDate(java.sql.Timestamp newValue) {
    __setCache("reportedDate", newValue);
  }
  /**
   * getCreateMethod
   * @generated
   */
  public java.lang.String getCreateMethod()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.String) __getCache("createMethod")));
  }
  /**
   * setCreateMethod
   * @generated
   */
  public void setCreateMethod(java.lang.String newValue) {
    __setCache("createMethod", newValue);
  }
  /**
   * getTumorCells
   * @generated
   */
  public java.lang.Integer getTumorCells()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.Integer) __getCache("tumorCells")));
  }
  /**
   * setTumorCells
   * @generated
   */
  public void setTumorCells(java.lang.Integer newValue) {
    __setCache("tumorCells", newValue);
  }
  /**
   * setInit_evaluationId
   * @generated
   */
  public void setInit_evaluationId(java.lang.String newValue) {
    this.init_evaluationId = (newValue);
  }
  /**
   * setInit_slideID
   * @generated
   */
  public void setInit_slideID(java.lang.String newValue) {
    this.init_slideID = (newValue);
  }
  /**
   * setInit_sampleId
   * @generated
   */
  public void setInit_sampleId(java.lang.String newValue) {
    this.init_sampleId = (newValue);
  }
  /**
   * setInit_microscopicAppearance
   * @generated
   */
  public void setInit_microscopicAppearance(java.lang.String newValue) {
    this.init_microscopicAppearance = (newValue);
  }
  /**
   * setInit_reportedYN
   * @generated
   */
  public void setInit_reportedYN(java.lang.String newValue) {
    this.init_reportedYN = (newValue);
  }
  /**
   * setInit_createMethod
   * @generated
   */
  public void setInit_createMethod(java.lang.String newValue) {
    this.init_createMethod = (newValue);
  }
  /**
   * setInit_diagnosis
   * @generated
   */
  public void setInit_diagnosis(java.lang.String newValue) {
    this.init_diagnosis = (newValue);
  }
  /**
   * setInit_tissueOfOrigin
   * @generated
   */
  public void setInit_tissueOfOrigin(java.lang.String newValue) {
    this.init_tissueOfOrigin = (newValue);
  }
  /**
   * setInit_tissueOfFinding
   * @generated
   */
  public void setInit_tissueOfFinding(java.lang.String newValue) {
    this.init_tissueOfFinding = (newValue);
  }
  /**
   * setInit_tumorCells
   * @generated
   */
  public void setInit_tumorCells(java.lang.Integer newValue) {
    this.init_tumorCells = (newValue);
  }
  /**
   * setInit_normalCells
   * @generated
   */
  public void setInit_normalCells(java.lang.Integer newValue) {
    this.init_normalCells = (newValue);
  }
  /**
   * setInit_hypoacellularstromaCells
   * @generated
   */
  public void setInit_hypoacellularstromaCells(java.lang.Integer newValue) {
    this.init_hypoacellularstromaCells = (newValue);
  }
  /**
   * setInit_necrosisCells
   * @generated
   */
  public void setInit_necrosisCells(java.lang.Integer newValue) {
    this.init_necrosisCells = (newValue);
  }
  /**
   * setInit_lesionCells
   * @generated
   */
  public void setInit_lesionCells(java.lang.Integer newValue) {
    this.init_lesionCells = (newValue);
  }
  /**
   * setInit_cellularstromaCells
   * @generated
   */
  public void setInit_cellularstromaCells(java.lang.Integer newValue) {
    this.init_cellularstromaCells = (newValue);
  }
  /**
   * PathologyEvaluationAccessBean
   * @generated
   */
  public PathologyEvaluationAccessBean() {
    super();
  }
  /**
   * PathologyEvaluationAccessBean
   * @generated
   */
  public PathologyEvaluationAccessBean(javax.ejb.EJBObject o) throws java.rmi.RemoteException {
    super(o);
  }
  /**
   * defaultJNDIName
   * @generated
   */
  public String defaultJNDIName() {
    return "ejb/com/ardais/bigr/lims/beans/PathologyEvaluationHome";
  }
  /**
   * ejbHome
   * @generated
   */
  private com.ardais.bigr.lims.beans.PathologyEvaluationHome ejbHome()
    throws java.rmi.RemoteException,
    javax.naming.NamingException {
    return (com.ardais.bigr.lims.beans.PathologyEvaluationHome) PortableRemoteObject.narrow(
      getHome(),
      com.ardais.bigr.lims.beans.PathologyEvaluationHome.class);
  }
  /**
   * ejbRef
   * @generated
   */
  private com.ardais.bigr.lims.beans.PathologyEvaluation ejbRef() throws java.rmi.RemoteException {
    if (ejbRef == null)
      return null;
    if (__ejbRef == null)
      __ejbRef = (com.ardais.bigr.lims.beans.PathologyEvaluation) PortableRemoteObject.narrow(
        ejbRef,
        com.ardais.bigr.lims.beans.PathologyEvaluation.class);

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

    ejbRef = ejbHome().create(
      init_evaluationId,
      init_slideID,
      init_sampleId,
      init_microscopicAppearance,
      init_reportedYN,
      init_createMethod,
      init_diagnosis,
      init_tissueOfOrigin,
      init_tissueOfFinding,
      init_tumorCells,
      init_normalCells,
      init_hypoacellularstromaCells,
      init_necrosisCells,
      init_lesionCells,
      init_cellularstromaCells);
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
      java.lang.String pKey = (java.lang.String) this.__getKey();
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
   * findBySampleId
   * @generated
   */
  public java.util.Enumeration findBySampleId(java.lang.String sampleId)
    throws javax.naming.NamingException,
    javax.ejb.FinderException,
    java.rmi.RemoteException {
    com.ardais.bigr.lims.beans.PathologyEvaluationHome localHome = ejbHome();
    java.util.Enumeration ejbs = localHome.findBySampleId(sampleId);
    return (java.util.Enumeration) createAccessBeans(ejbs);
  }
  /**
   * findAllNonMigrated
   * @generated
   */
  public java.util.Enumeration findAllNonMigrated()
    throws javax.naming.NamingException,
    javax.ejb.FinderException,
    java.rmi.RemoteException {
    com.ardais.bigr.lims.beans.PathologyEvaluationHome localHome = ejbHome();
    java.util.Enumeration ejbs = localHome.findAllNonMigrated();
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
    com.ardais.bigr.lims.beans.PathologyEvaluationHome localHome = ejbHome();
    java.util.Enumeration ejbs = localHome.findAll();
    return (java.util.Enumeration) createAccessBeans(ejbs);
  }
  /**
   * PathologyEvaluationAccessBean
   * @generated
   */
  public PathologyEvaluationAccessBean(java.lang.String primaryKey)
    throws javax.naming.NamingException, javax.ejb.FinderException, javax.ejb.CreateException,
    java.rmi.RemoteException {
    ejbRef = ejbHome().findByPrimaryKey(primaryKey);
  }
  /**
   * setTumorFeatures
   * @generated
   */
  public void setTumorFeatures(java.util.List tumorFeatures)
    throws javax.naming.NamingException,
    javax.ejb.CreateException,
    java.rmi.RemoteException {
    instantiateEJB();
    ejbRef().setTumorFeatures(tumorFeatures);
  }
  /**
   * setStructures
   * @generated
   */
  public void setStructures(java.util.List structures)
    throws javax.naming.NamingException,
    javax.ejb.CreateException,
    java.rmi.RemoteException {
    instantiateEJB();
    ejbRef().setStructures(structures);
  }
  /**
   * getHypoacellularStromaFeatures
   * @generated
   */
  public java.util.List getHypoacellularStromaFeatures()
    throws javax.naming.NamingException,
    javax.ejb.CreateException,
    java.rmi.RemoteException {
    instantiateEJB();
    return ejbRef().getHypoacellularStromaFeatures();
  }
  /**
   * setExternalFeatures
   * @generated
   */
  public void setExternalFeatures(java.util.List externalFeatures)
    throws javax.naming.NamingException,
    javax.ejb.CreateException,
    java.rmi.RemoteException {
    instantiateEJB();
    ejbRef().setExternalFeatures(externalFeatures);
  }
  /**
   * getTumorFeatures
   * @generated
   */
  public java.util.List getTumorFeatures()
    throws javax.naming.NamingException,
    javax.ejb.CreateException,
    java.rmi.RemoteException {
    instantiateEJB();
    return ejbRef().getTumorFeatures();
  }
  /**
   * getInflammations
   * @generated
   */
  public java.util.List getInflammations()
    throws javax.naming.NamingException,
    javax.ejb.CreateException,
    java.rmi.RemoteException {
    instantiateEJB();
    return ejbRef().getInflammations();
  }
  /**
   * getInternalFeatures
   * @generated
   */
  public java.util.List getInternalFeatures()
    throws javax.naming.NamingException,
    javax.ejb.CreateException,
    java.rmi.RemoteException {
    instantiateEJB();
    return ejbRef().getInternalFeatures();
  }
  /**
   * setHypoacellularStromaFeatures
   * @generated
   */
  public void setHypoacellularStromaFeatures(java.util.List hypoacellularStromaFeatures)
    throws javax.naming.NamingException,
    javax.ejb.CreateException,
    java.rmi.RemoteException {
    instantiateEJB();
    ejbRef().setHypoacellularStromaFeatures(hypoacellularStromaFeatures);
  }
  /**
   * setInternalFeatures
   * @generated
   */
  public void setInternalFeatures(java.util.List internalFeatures)
    throws javax.naming.NamingException,
    javax.ejb.CreateException,
    java.rmi.RemoteException {
    instantiateEJB();
    ejbRef().setInternalFeatures(internalFeatures);
  }
  /**
   * getExternalFeatures
   * @generated
   */
  public java.util.List getExternalFeatures()
    throws javax.naming.NamingException,
    javax.ejb.CreateException,
    java.rmi.RemoteException {
    instantiateEJB();
    return ejbRef().getExternalFeatures();
  }
  /**
   * getLesions
   * @generated
   */
  public java.util.List getLesions()
    throws javax.naming.NamingException,
    javax.ejb.CreateException,
    java.rmi.RemoteException {
    instantiateEJB();
    return ejbRef().getLesions();
  }
  /**
   * getCellularStromaFeatures
   * @generated
   */
  public java.util.List getCellularStromaFeatures()
    throws javax.naming.NamingException,
    javax.ejb.CreateException,
    java.rmi.RemoteException {
    instantiateEJB();
    return ejbRef().getCellularStromaFeatures();
  }
  /**
   * setLesions
   * @generated
   */
  public void setLesions(java.util.List lesions)
    throws javax.naming.NamingException,
    javax.ejb.CreateException,
    java.rmi.RemoteException {
    instantiateEJB();
    ejbRef().setLesions(lesions);
  }
  /**
   * getStructures
   * @generated
   */
  public java.util.List getStructures()
    throws javax.naming.NamingException,
    javax.ejb.CreateException,
    java.rmi.RemoteException {
    instantiateEJB();
    return ejbRef().getStructures();
  }
  /**
   * setInflammations
   * @generated
   */
  public void setInflammations(java.util.List inflammations)
    throws javax.naming.NamingException,
    javax.ejb.CreateException,
    java.rmi.RemoteException {
    instantiateEJB();
    ejbRef().setInflammations(inflammations);
  }
  /**
   * setCellularStromaFeatures
   * @generated
   */
  public void setCellularStromaFeatures(java.util.List cellularStromaFeatures)
    throws javax.naming.NamingException,
    javax.ejb.CreateException,
    java.rmi.RemoteException {
    instantiateEJB();
    ejbRef().setCellularStromaFeatures(cellularStromaFeatures);
  }
}
