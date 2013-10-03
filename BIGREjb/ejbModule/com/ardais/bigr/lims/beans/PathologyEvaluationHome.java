package com.ardais.bigr.lims.beans;
/**
 * Home interface for Enterprise Bean: PathologyEvaluation
 */
public interface PathologyEvaluationHome extends javax.ejb.EJBHome {
	/**
	 * Creates an instance from a key for Entity Bean: PathologyEvaluation
	 */
	public com.ardais.bigr.lims.beans.PathologyEvaluation create(
		java.lang.String evaluationId)
		throws javax.ejb.CreateException, java.rmi.RemoteException;
	/**
	 * Finds an instance using a key for Entity Bean: PathologyEvaluation
	 */
	public com.ardais.bigr.lims.beans.PathologyEvaluation findByPrimaryKey(
		java.lang.String primaryKey)
		throws javax.ejb.FinderException, java.rmi.RemoteException;
	public java.util.Enumeration findBySampleId(java.lang.String sampleId) throws javax.ejb.FinderException, java.rmi.RemoteException;
	/**
	 * ejbCreate method for a CMP entity bean.
	 */
	public com.ardais.bigr.lims.beans.PathologyEvaluation create(
		String evaluationId,
		String slideID,
		String sampleId,
		String microscopicAppearance,
		String reportedYN,
		String createMethod,
		String diagnosis,
		String tissueOfOrigin,
        String tissueOfFinding,
		Integer tumorCells,
		Integer normalCells,
		Integer hypoacellularstromaCells,
		Integer necrosisCells,
		Integer lesionCells,
		Integer cellularstromaCells)
		throws javax.ejb.CreateException, java.rmi.RemoteException;
   /**
    * Finds all instances where MIGRATED_YN = 'N'.
    */    
	public java.util.Enumeration findAllNonMigrated()
		throws javax.ejb.FinderException, java.rmi.RemoteException;
  /**
    * Finds all instances of rows in table.
    */    
    public java.util.Enumeration findAll()
        throws javax.ejb.FinderException, java.rmi.RemoteException;      
}
