package com.ardais.bigr.lims.beans;
import java.util.List;
/**
 * Remote interface for Enterprise Bean: PathologyEvaluation
 */
public interface PathologyEvaluation extends javax.ejb.EJBObject, com.ibm.ivj.ejb.runtime.CopyHelper {

















































	/**
	 * Get accessor for persistent attribute: slideId
	 */
	public java.lang.String getSlideId() throws java.rmi.RemoteException;
	/**
	 * Set accessor for persistent attribute: slideId
	 */
	public void setSlideId(java.lang.String newSlideId)
		throws java.rmi.RemoteException;
	/**
	 * Get accessor for persistent attribute: sampleId
	 */
	public java.lang.String getSampleId() throws java.rmi.RemoteException;
	/**
	 * Set accessor for persistent attribute: sampleId
	 */
	public void setSampleId(java.lang.String newSampleId)
		throws java.rmi.RemoteException;
	/**
	 * Get accessor for persistent attribute: microscopicAppearance
	 */
	public java.lang.String getMicroscopicAppearance()
		throws java.rmi.RemoteException;
	/**
	 * Set accessor for persistent attribute: microscopicAppearance
	 */
	public void setMicroscopicAppearance(
		java.lang.String newMicroscopicAppearance)
		throws java.rmi.RemoteException;
	/**
	 * Get accessor for persistent attribute: reportedYN
	 */
	public java.lang.String getReportedYN() throws java.rmi.RemoteException;
	/**
	 * Set accessor for persistent attribute: reportedYN
	 */
	public void setReportedYN(java.lang.String newReportedYN)
		throws java.rmi.RemoteException;
	/**
	 * Get accessor for persistent attribute: createMethod
	 */
	public java.lang.String getCreateMethod() throws java.rmi.RemoteException;
	/**
	 * Set accessor for persistent attribute: createMethod
	 */
	public void setCreateMethod(java.lang.String newCreateMethod)
		throws java.rmi.RemoteException;
	/**
	 * Get accessor for persistent attribute: tumorCells
	 */
	public Integer getTumorCells() throws java.rmi.RemoteException;
	/**
	 * Set accessor for persistent attribute: tumorCells
	 */
	public void setTumorCells(Integer newTumorCells)
		throws java.rmi.RemoteException;
	/**
	 * Get accessor for persistent attribute: normalCells
	 */
	public Integer getNormalCells() throws java.rmi.RemoteException;
	/**
	 * Set accessor for persistent attribute: normalCells
	 */
	public void setNormalCells(Integer newNormalCells)
		throws java.rmi.RemoteException;
	/**
	 * Get accessor for persistent attribute: hypoacellularstromaCells
	 */
	public Integer getHypoacellularstromaCells() throws java.rmi.RemoteException;
	/**
	 * Set accessor for persistent attribute: hypoacellularstromaCells
	 */
	public void setHypoacellularstromaCells(Integer newHypoacellularstromaCells)
		throws java.rmi.RemoteException;
	/**
	 * Get accessor for persistent attribute: necrosisCells
	 */
	public Integer getNecrosisCells() throws java.rmi.RemoteException;
	/**
	 * Set accessor for persistent attribute: necrosisCells
	 */
	public void setNecrosisCells(Integer newNecrosisCells)
		throws java.rmi.RemoteException;
	/**
	 * Get accessor for persistent attribute: lesionCells
	 */
	public Integer getLesionCells() throws java.rmi.RemoteException;
	/**
	 * Set accessor for persistent attribute: lesionCells
	 */
	public void setLesionCells(Integer newLesionCells)
		throws java.rmi.RemoteException;
	/**
	 * Get accessor for persistent attribute: diagnosis
	 */
	public java.lang.String getDiagnosis() throws java.rmi.RemoteException;
	/**
	 * Set accessor for persistent attribute: diagnosis
	 */
	public void setDiagnosis(java.lang.String newDiagnosis)
		throws java.rmi.RemoteException;
	/**
	 * Get accessor for persistent attribute: tissueOfOrigin
	 */
	public java.lang.String getTissueOfOrigin()
		throws java.rmi.RemoteException;
	/**
	 * Set accessor for persistent attribute: tissueOfOrigin
	 */
	public void setTissueOfOrigin(java.lang.String newTissueOfOrigin)
		throws java.rmi.RemoteException;
	/**
	 * Get accessor for persistent attribute: createDate
	 */
	public java.sql.Timestamp getCreateDate() throws java.rmi.RemoteException;
	/**
	 * Set accessor for persistent attribute: createDate
	 */
	public void setCreateDate(java.sql.Timestamp newCreateDate)
		throws java.rmi.RemoteException;
	/**
	 * Get accessor for persistent attribute: pathologist
	 */
	public java.lang.String getPathologist() throws java.rmi.RemoteException;
	/**
	 * Set accessor for persistent attribute: pathologist
	 */
	public void setPathologist(java.lang.String newPathologist)
		throws java.rmi.RemoteException;
	/**
	 * Get accessor for persistent attribute: migratedYN
	 */
	public java.lang.String getMigratedYN() throws java.rmi.RemoteException;
	/**
	 * Set accessor for persistent attribute: migratedYN
	 */
	public void setMigratedYN(java.lang.String newMigratedYN)
		throws java.rmi.RemoteException;
	/**
	 * Get accessor for persistent attribute: tissueOfFinding
	 */
	public java.lang.String getTissueOfFinding()
		throws java.rmi.RemoteException;
	/**
	 * Set accessor for persistent attribute: tissueOfFinding
	 */
	public void setTissueOfFinding(java.lang.String newTissueOfFinding)
		throws java.rmi.RemoteException;
	/**
	 * Get accessor for persistent attribute: tissueOfFindingOther
	 */
	public java.lang.String getTissueOfFindingOther()
		throws java.rmi.RemoteException;
	/**
	 * Set accessor for persistent attribute: tissueOfFindingOther
	 */
	public void setTissueOfFindingOther(
		java.lang.String newTissueOfFindingOther)
		throws java.rmi.RemoteException;
	/**
	 * Get accessor for persistent attribute: diagnosisOther
	 */
	public java.lang.String getDiagnosisOther()
		throws java.rmi.RemoteException;
	/**
	 * Set accessor for persistent attribute: diagnosisOther
	 */
	public void setDiagnosisOther(java.lang.String newDiagnosisOther)
		throws java.rmi.RemoteException;
	/**
	 * Get accessor for persistent attribute: tissueOfOriginOther
	 */
	public java.lang.String getTissueOfOriginOther()
		throws java.rmi.RemoteException;
	/**
	 * Set accessor for persistent attribute: tissueOfOriginOther
	 */
	public void setTissueOfOriginOther(java.lang.String newTissueOfOriginOther)
		throws java.rmi.RemoteException;
	/**
	 * Get accessor for persistent attribute: concatenatedExternalComments
	 */
	public java.lang.String getConcatenatedExternalComments()
		throws java.rmi.RemoteException;
	/**
	 * Set accessor for persistent attribute: concatenatedExternalComments
	 */
	public void setConcatenatedExternalComments(
		java.lang.String newConcatenatedExternalComments)
		throws java.rmi.RemoteException;
	/**
	 * Get accessor for persistent attribute: concatenatedInternalComments
	 */
	public java.lang.String getConcatenatedInternalComments()
		throws java.rmi.RemoteException;
	/**
	 * Set accessor for persistent attribute: concatenatedInternalComments
	 */
	public void setConcatenatedInternalComments(
		java.lang.String newConcatenatedInternalComments)
		throws java.rmi.RemoteException;
	/**
	 * Get accessor for persistent attribute: sourceEvaluationId
	 */
	public java.lang.String getSourceEvaluationId()
		throws java.rmi.RemoteException;
	/**
	 * Set accessor for persistent attribute: sourceEvaluationId
	 */
	public void setSourceEvaluationId(java.lang.String newSourceEvaluationId)
		throws java.rmi.RemoteException;
	/**
	 * Get accessor for persistent attribute: cellularstromaCells
	 */
	public Integer getCellularstromaCells() throws java.rmi.RemoteException;
	/**
	 * Set accessor for persistent attribute: cellularstromaCells
	 */
	public void setCellularstromaCells(Integer newCellularstromaCells)
		throws java.rmi.RemoteException;
	/**
	 * Returns the lesions.
	 * @return List
	 */
	public List getLesions() throws java.rmi.RemoteException;
	/**
	 * Sets the lesions.
	 * @param lesions The lesions to set
	 */
	public void setLesions(List lesions) throws java.rmi.RemoteException;
	/**
	 * Returns the cellularStromaFeatures.
	 * @return List
	 */
	public List getCellularStromaFeatures() throws java.rmi.RemoteException;
	/**
	 * Returns the externalFeatures.
	 * @return List
	 */
	public List getExternalFeatures() throws java.rmi.RemoteException;
	/**
	 * Returns the hypoacellularStromaFeatures.
	 * @return List
	 */
	public List getHypoacellularStromaFeatures()
		throws java.rmi.RemoteException;
	/**
	 * Returns the inflammations.
	 * @return List
	 */
	public List getInflammations() throws java.rmi.RemoteException;
	/**
	 * Returns the internalFeatures.
	 * @return List
	 */
	public List getInternalFeatures() throws java.rmi.RemoteException;
	/**
	 * Returns the structures.
	 * @return List
	 */
	public List getStructures() throws java.rmi.RemoteException;
	/**
	 * Returns the tumorFeatures.
	 * @return List
	 */
	public List getTumorFeatures() throws java.rmi.RemoteException;
	/**
	 * Sets the cellularStromaFeatures.
	 * @param cellularStromaFeatures The cellularStromaFeatures to set
	 */
	public void setCellularStromaFeatures(List cellularStromaFeatures)
		throws java.rmi.RemoteException;
	/**
	 * Sets the externalFeatures.
	 * @param externalFeatures The externalFeatures to set
	 */
	public void setExternalFeatures(List externalFeatures)
		throws java.rmi.RemoteException;
	/**
	 * Sets the hypoacellularStromaFeatures.
	 * @param hypoacellularStromaFeatures The hypoacellularStromaFeatures to set
	 */
	public void setHypoacellularStromaFeatures(List hypoacellularStromaFeatures)
		throws java.rmi.RemoteException;
	/**
	 * Sets the inflammations.
	 * @param inflammations The inflammations to set
	 */
	public void setInflammations(List inflammations)
		throws java.rmi.RemoteException;
	/**
	 * Sets the internalFeatures.
	 * @param internalFeatures The internalFeatures to set
	 */
	public void setInternalFeatures(List internalFeatures)
		throws java.rmi.RemoteException;
	/**
	 * Sets the structures.
	 * @param structures The structures to set
	 */
	public void setStructures(List structures) throws java.rmi.RemoteException;
	/**
	 * Sets the tumorFeatures.
	 * @param tumorFeatures The tumorFeatures to set
	 */
	public void setTumorFeatures(List tumorFeatures)
		throws java.rmi.RemoteException;
	/**
	 * Get accessor for persistent attribute: reportedDate
	 */
	public java.sql.Timestamp getReportedDate()
		throws java.rmi.RemoteException;
	/**
	 * Set accessor for persistent attribute: reportedDate
	 */
	public void setReportedDate(java.sql.Timestamp newReportedDate)
		throws java.rmi.RemoteException;
}
