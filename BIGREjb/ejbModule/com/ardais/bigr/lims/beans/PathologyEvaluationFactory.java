package com.ardais.bigr.lims.beans;
import javax.ejb.*;
import java.rmi.RemoteException;
import javax.naming.NamingException;
import com.ibm.etools.ejb.client.runtime.*;
/**
 * PathologyEvaluationFactory
 * @generated
 */
public class PathologyEvaluationFactory extends AbstractEJBFactory {
  /**
   * PathologyEvaluationFactory
   * @generated
   */
  public PathologyEvaluationFactory() {
    super();
  }
  /**
   * _acquirePathologyEvaluationHome
   * @generated
   */
  protected com.ardais.bigr.lims.beans.PathologyEvaluationHome _acquirePathologyEvaluationHome()
    throws java.rmi.RemoteException {
    return (com.ardais.bigr.lims.beans.PathologyEvaluationHome) _acquireEJBHome();
  }
  /**
   * acquirePathologyEvaluationHome
   * @generated
   */
  public com.ardais.bigr.lims.beans.PathologyEvaluationHome acquirePathologyEvaluationHome()
    throws javax.naming.NamingException {
    return (com.ardais.bigr.lims.beans.PathologyEvaluationHome) acquireEJBHome();
  }
  /**
   * getDefaultJNDIName
   * @generated
   */
  public String getDefaultJNDIName() {
    return "ejb/com/ardais/bigr/lims/beans/PathologyEvaluationHome";
  }
  /**
   * getHomeInterface
   * @generated
   */
  protected Class getHomeInterface() {
    return com.ardais.bigr.lims.beans.PathologyEvaluationHome.class;
  }
  /**
   * resetPathologyEvaluationHome
   * @generated
   */
  public void resetPathologyEvaluationHome() {
    resetEJBHome();
  }
  /**
   * setPathologyEvaluationHome
   * @generated
   */
  public void setPathologyEvaluationHome(com.ardais.bigr.lims.beans.PathologyEvaluationHome home) {
    setEJBHome(home);
  }
  /**
   * findBySampleId
   * @generated
   */
  public java.util.Enumeration findBySampleId(java.lang.String sampleId)
    throws javax.ejb.FinderException,
    java.rmi.RemoteException {
    return _acquirePathologyEvaluationHome().findBySampleId(sampleId);
  }
  /**
   * findAllNonMigrated
   * @generated
   */
  public java.util.Enumeration findAllNonMigrated()
    throws javax.ejb.FinderException,
    java.rmi.RemoteException {
    return _acquirePathologyEvaluationHome().findAllNonMigrated();
  }
  /**
   * findAll
   * @generated
   */
  public java.util.Enumeration findAll() throws javax.ejb.FinderException, java.rmi.RemoteException {
    return _acquirePathologyEvaluationHome().findAll();
  }
  /**
   * create
   * @generated
   */
  public com.ardais.bigr.lims.beans.PathologyEvaluation create(java.lang.String evaluationId)
    throws javax.ejb.CreateException,
    java.rmi.RemoteException {
    return _acquirePathologyEvaluationHome().create(evaluationId);
  }
  /**
   * create
   * @generated
   */
  public com.ardais.bigr.lims.beans.PathologyEvaluation create(
    java.lang.String evaluationId,
    java.lang.String slideID,
    java.lang.String sampleId,
    java.lang.String microscopicAppearance,
    java.lang.String reportedYN,
    java.lang.String createMethod,
    java.lang.String diagnosis,
    java.lang.String tissueOfOrigin,
    java.lang.String tissueOfFinding,
    java.lang.Integer tumorCells,
    java.lang.Integer normalCells,
    java.lang.Integer hypoacellularstromaCells,
    java.lang.Integer necrosisCells,
    java.lang.Integer lesionCells,
    java.lang.Integer cellularstromaCells)
    throws javax.ejb.CreateException,
    java.rmi.RemoteException {
    return _acquirePathologyEvaluationHome().create(
      evaluationId,
      slideID,
      sampleId,
      microscopicAppearance,
      reportedYN,
      createMethod,
      diagnosis,
      tissueOfOrigin,
      tissueOfFinding,
      tumorCells,
      normalCells,
      hypoacellularstromaCells,
      necrosisCells,
      lesionCells,
      cellularstromaCells);
  }
  /**
   * findByPrimaryKey
   * @generated
   */
  public com.ardais.bigr.lims.beans.PathologyEvaluation findByPrimaryKey(java.lang.String primaryKey)
    throws javax.ejb.FinderException,
    java.rmi.RemoteException {
    return _acquirePathologyEvaluationHome().findByPrimaryKey(primaryKey);
  }
}
