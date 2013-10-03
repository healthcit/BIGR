package com.ardais.bigr.lims.beans;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.api.ApiLogger;
import com.ardais.bigr.iltds.helpers.FormLogic;
import com.ardais.bigr.lims.helpers.LimsConstants;
import com.ardais.bigr.lims.javabeans.PathologyEvaluationFeatureData;
import com.ardais.bigr.pdc.oce.util.OceUtil;

/**
 * Bean implementation class for Enterprise Bean: PathologyEvaluation
 */
public class PathologyEvaluationBean implements javax.ejb.EntityBean {
	
	public static final String DEFAULT_evaluationId  = null;
	public static final String DEFAULT_slideId  = null;
	public static final String DEFAULT_sampleId  = null;	
	public static final String DEFAULT_microscopicAppearance  = null;	
	public static final String DEFAULT_reportedYN  = null;	
	public static final String DEFAULT_createMethod  = null;	
	public static final Integer DEFAULT_tumorCells  = new Integer(0);	
	public static final Integer DEFAULT_normalCells  = new Integer(0);
	public static final Integer DEFAULT_hypoacellularstromaCells  = new Integer(0);
	public static final Integer DEFAULT_necrosisCells  = new Integer(0);
	public static final Integer DEFAULT_lesionCells  = new Integer(0);
	public static final Integer DEFAULT_cellularstromaCells  = new Integer(0);
	public static final String DEFAULT_diagnosis  = null;
	public static final String DEFAULT_tissueOfOrigin  = null;
	public static final java.sql.Timestamp DEFAULT_createDate  = null;
	public static final String DEFAULT_pathologist  = null;
	public static final String DEFAULT_migratedYN  = "N";
	public static final String DEFAULT_tissueOfFinding  = null;
	public static final String DEFAULT_tissueOfFindingOther  = null;
	public static final String DEFAULT_diagnosisOther  = null;
	public static final String DEFAULT_tissueOfOriginOther = null;
	public static final String DEFAULT_concatenatedExternalComments  = null;
	public static final String DEFAULT_concatenatedInternalComments  = null;
	public static final String DEFAULT_sourceEvaluationId  = null;
	public static final java.sql.Timestamp DEFAULT_reportedDate  = null;
  
  private javax.ejb.EntityContext myEntityCtx;
  
  private List _lesions;
  private List _inflammations;
  private List _structures;
  private List _tumorFeatures;
  private List _cellularStromaFeatures;
  private List _hypoacellularStromaFeatures;
  private List _externalFeatures;
  private List _internalFeatures;
  private boolean _new = true;
  private boolean _featuresModified = false;
	
	/**
	 * Implementation field for persistent attribute: evaluationId
	 */
	public java.lang.String evaluationId;
	/**
	 * Implementation field for persistent attribute: slideId
	 */
	public java.lang.String slideId;
	/**
	 * Implementation field for persistent attribute: sampleId
	 */
	public java.lang.String sampleId;
	/**
	 * Implementation field for persistent attribute: microscopicAppearance
	 */
	public java.lang.String microscopicAppearance;
	/**
	 * Implementation field for persistent attribute: reportedYN
	 */
	public java.lang.String reportedYN;
	/**
	 * Implementation field for persistent attribute: createMethod
	 */
	public java.lang.String createMethod;
	/**
	 * Implementation field for persistent attribute: tumorCells
	 */
	public Integer tumorCells;
	/**
	 * Implementation field for persistent attribute: normalCells
	 */
	public Integer normalCells;
	/**
	 * Implementation field for persistent attribute: hypoacellularstromaCells
	 */
	public Integer hypoacellularstromaCells;
	/**
	 * Implementation field for persistent attribute: necrosisCells
	 */
	public Integer necrosisCells;
	/**
	 * Implementation field for persistent attribute: lesionCells
	 */
	public Integer lesionCells;
    /**
     * Implementation field for persistent attribute: cellularstromaCells
     */
    public Integer cellularstromaCells;
	/**
	 * Implementation field for persistent attribute: diagnosis
	 */
	public java.lang.String diagnosis;
	/**
	 * Implementation field for persistent attribute: tissueOfOrigin
	 */
	public java.lang.String tissueOfOrigin;
	/**
	 * Implementation field for persistent attribute: createDate
	 */
	public java.sql.Timestamp createDate;
	/**
	 * Implementation field for persistent attribute: pathologist
	 */
	public java.lang.String pathologist;
	/**
	 * Implementation field for persistent attribute: migratedYN
	 */
	public java.lang.String migratedYN;
	/**
	 * Implementation field for persistent attribute: tissueOfFinding
	 */
	public java.lang.String tissueOfFinding;
	/**
	 * Implementation field for persistent attribute: tissueOfFindingOther
	 */
	public java.lang.String tissueOfFindingOther;
	/**
	 * Implementation field for persistent attribute: diagnosisOther
	 */
	public java.lang.String diagnosisOther;
	/**
	 * Implementation field for persistent attribute: tissueOfOriginOther
	 */
	public java.lang.String tissueOfOriginOther;
	/**
	 * Implementation field for persistent attribute: concatenatedExternalComments
	 */
	public java.lang.String concatenatedExternalComments;
	/**
	 * Implementation field for persistent attribute: concatenatedInternalComments
	 */
	public java.lang.String concatenatedInternalComments;
	/**
	 * Implementation field for persistent attribute: sourceEvaluationId
	 */
	public java.lang.String sourceEvaluationId;
    /**
     * Implementation field for persistent attribute: reportedDate
     */
    public java.sql.Timestamp reportedDate;
	
	private static final com.ardais.bigr.api.CMPDefaultValues _fieldDefaultValues =
		new com.ardais.bigr.api.CMPDefaultValues(
			com.ardais.bigr.lims.beans.PathologyEvaluationBean.class);
	/**
	 * getEntityContext
	 */
	public javax.ejb.EntityContext getEntityContext() {
		return myEntityCtx;
	}
	/**
	 * setEntityContext
	 */
	public void setEntityContext(javax.ejb.EntityContext ctx) {
		myEntityCtx = ctx;
	}
	/**
	 * unsetEntityContext
	 */
	public void unsetEntityContext() {
		myEntityCtx = null;
	}
	/**
	 * ejbActivate
	 */
	public void ejbActivate() {
		_initLinks();
	}
	/**
	 * ejbCreate method for a CMP entity bean.
	 */
	public java.lang.String ejbCreate(java.lang.String evaluationId)
		throws javax.ejb.CreateException {
		_fieldDefaultValues.assignTo(this);	
		_initLinks();
		this.evaluationId = evaluationId;
        
        // An ejbCreate method must explicitly initialize EVERY field,
        // otherwise when a bean instance is reused from a pool it could have
        // inappropriate values left over on it from the last object that
        // was bound to the instance.
        // The comment regarding setNew below is just one case -- in reality
        // ALL fields must be initialized.
        //
        setLesions(new ArrayList());
        setInflammations(new ArrayList());
        setStructures(new ArrayList());
        setTumorFeatures(new ArrayList());
        setCellularStromaFeatures(new ArrayList());
        setHypoacellularStromaFeatures(new ArrayList());
        setExternalFeatures(new ArrayList());
        setInternalFeatures(new ArrayList());
        setFeaturesModified(false);
        
		//set the new flag to true.  If we don't do this, it's possible that we will retrieve a bean from
		//the pool that has a new value of false.  The CreateEvaluation code will then set the evaluation
		//features (which will set the featuresModified flag to true), and when we hit the ejbStore method
		//an exception will be thrown (since we have new = false and modified = true).  Since we're creating
		//a new evaluation here, explicitly set the new flag to be true to avoid this situation
		setNew(true);
        
		return null;
	}
	
	/**
	 * ejbCreate method for a CMP entity bean.
	 */
	public java.lang.String ejbCreate(String evaluationId,
									   String slideId,
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
		throws javax.ejb.CreateException {
		_fieldDefaultValues.assignTo(this);	
		_initLinks();
		this.evaluationId = evaluationId;
		this.slideId = slideId;
		this.sampleId = sampleId;
		this.microscopicAppearance = microscopicAppearance;
		this.reportedYN = reportedYN;
		this.diagnosis = diagnosis;
		this.tissueOfOrigin = tissueOfOrigin;
        this.tissueOfFinding = tissueOfFinding;
		this.createMethod = createMethod;
		this.tumorCells = tumorCells;
		this.normalCells = normalCells;
		this.hypoacellularstromaCells = hypoacellularstromaCells;
		this.necrosisCells = necrosisCells;
		this.lesionCells = lesionCells;
		this.cellularstromaCells = cellularstromaCells;
        
        // An ejbCreate method must explicitly initialize EVERY field,
        // otherwise when a bean instance is reused from a pool it could have
        // inappropriate values left over on it from the last object that
        // was bound to the instance.
        // The comment regarding setNew below is just one case -- in reality
        // ALL fields must be initialized.
        //
        setLesions(new ArrayList());
        setInflammations(new ArrayList());
        setStructures(new ArrayList());
        setTumorFeatures(new ArrayList());
        setCellularStromaFeatures(new ArrayList());
        setHypoacellularStromaFeatures(new ArrayList());
        setExternalFeatures(new ArrayList());
        setInternalFeatures(new ArrayList());
        setFeaturesModified(false);

		//set the new flag to true.  If we don't do this, it's possible that we will retrieve a bean from
		//the pool that has a new value of false.  The CreateEvaluation code will then set the evaluation
		//features (which will set the featuresModified flag to true), and when we hit the ejbStore method
		//an exception will be thrown (since we have new = false and modified = true).  Since we're creating
		//a new evaluation here, explicitly set the new flag to be true to avoid this situation
		setNew(true);
        
		return null;
	}
	/**
	 * ejbLoad
	 */
	public void ejbLoad() {
		_initLinks();
		loadFeatures();
		setNew(false);
	}
	/**
	 * ejbPassivate
	 */
	public void ejbPassivate() {
	}
	/**
	 * ejbPostCreate
	 */
	public void ejbPostCreate(java.lang.String evaluationId)
		throws javax.ejb.CreateException {
	}
	
	/**
	 * ejbPostCreate
	 */
	public void ejbPostCreate(String evaluationId,
									   String slideId,
									   String sampleId,
									   String microscopicAppearance,
									   String reportedYN,
									   String createMethod,
									   String diagnosis,
									   String tissueOfOrigin,
                                       String tissueOfFinding,
									   int tumorCells,
									   int normalCells,
									   int hypoacellularstromaCells,
									   int necrosisCells,
									   int lesionCells,
									   int cellularstromaCells)
		throws javax.ejb.CreateException {
	}
	/**
	 * ejbRemove
	 */
	public void ejbRemove() throws javax.ejb.RemoveException {
		try {
			_removeLinks();
		} catch (java.rmi.RemoteException e) {
			throw new javax.ejb.RemoveException(e.getMessage());
		}
	}
	/**
	 * ejbStore
	 */
	public void ejbStore() {
		//Insert features into table only if instance is new and
		//any of feature list modified.
		if (isNew() && isFeaturesModified()) {
			storeFeatures(getLesions(), LimsConstants.FEATURE_TYPE_LESION);
			storeFeatures(getInflammations(), LimsConstants.FEATURE_TYPE_INFLAMMATION);
			storeFeatures(getStructures(), LimsConstants.FEATURE_TYPE_STRUCTURE);
			storeFeatures(getTumorFeatures(), LimsConstants.FEATURE_TYPE_TUMOR);
			storeFeatures(getCellularStromaFeatures(), LimsConstants.FEATURE_TYPE_CELLULAR);
			storeFeatures(getHypoacellularStromaFeatures(), LimsConstants.FEATURE_TYPE_HYPOACELLULAR);
			storeFeatures(getExternalFeatures(), LimsConstants.FEATURE_TYPE_EXTERNAL);
			storeFeatures(getInternalFeatures(), LimsConstants.FEATURE_TYPE_INTERNAL);
		}
		//If instance is not new i.e it represents a row in LIMS_PATHOLOGY_EVALUATION
		//and features list modified, throw exception.
		else if (!isNew() && isFeaturesModified()) {
			StringBuffer msg = new StringBuffer(256);
			msg.append("PathologyEvaluationBean.ejbStore: One of the ");
			msg.append("features list has been modified for existing evaluation.\n");
			msg.append("Features cannot be modified for existing evaluation.");
			throw new UnsupportedOperationException(msg.toString());
		}
	}
	/**
	 * Inserts features into LIMS_PE_FEATURES table.
	 * @param <code>List</code> 
	 * @param <code>String</code> 
	 */
	private void storeFeatures(List list, String compositionType) {
		String id = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection con = null;
		String sql = "select LIMS_PE_FEATURES_SEQ.nextval as value from dual";
		StringBuffer insert = null;		
		try {
			if (!ApiFunctions.isEmpty(list)) {
				//set the display order on each feature if we're dealing with structures.  Assumes the
				//features were passed in in the correct order and numbers them sequentially from 1 to n.
				if (compositionType.equals(LimsConstants.FEATURE_TYPE_STRUCTURE)) {
					Iterator iterator = list.iterator();
					int displayOrder = 1;
					while (iterator.hasNext()) {
						PathologyEvaluationFeatureData feature = (PathologyEvaluationFeatureData)iterator.next();
						feature.setDisplayOrder(displayOrder);
						displayOrder = displayOrder + 1;
					}
				}
                con = ApiFunctions.getDbConnection();
				for (int i =0; i < list.size(); i++) {
					stmt = con.prepareStatement(sql);
					rs = stmt.executeQuery();
					while (rs.next()) {
						id = rs.getString("value");
					}
                    ApiFunctions.close(rs);
                    rs = null;
					ApiFunctions.close(stmt);
                    stmt = null;
					PathologyEvaluationFeatureData data =
						(PathologyEvaluationFeatureData)list.get(i);
					insert = new StringBuffer(512);
					if (id != null) {
						//Build SQL insert statement.
						//For Other external and internal comments no need
						//to insert value for "value" column.
						if (LimsConstants.FEATURE_TYPE_EXTERNAL.equals(compositionType) ||
						    LimsConstants.FEATURE_TYPE_INTERNAL.equals(compositionType)) {
							insert.append("INSERT INTO LIMS_PE_FEATURES (ID, ");
							insert.append(" PE_ID, COMPOSITION_TYPE, ");
							insert.append(" CODE, OTHER_TEXT ) ") ;
							insert.append(" VALUES (? ,? ,? , ?, ? )");	
						}
						//Structures have an additional column (display_order) filled in
						else if (LimsConstants.FEATURE_TYPE_STRUCTURE.equals(compositionType)) {
							insert.append("INSERT INTO LIMS_PE_FEATURES (ID, ");
							insert.append(" PE_ID, COMPOSITION_TYPE, ");
							insert.append(" CODE, OTHER_TEXT, VALUE, DISPLAY_ORDER ) ") ;
							insert.append(" VALUES (?, ?, ?, ?, ?, ?, ?)");
						}
						else {							
							insert.append("INSERT INTO LIMS_PE_FEATURES (ID, ");
							insert.append(" PE_ID, COMPOSITION_TYPE, ");
							insert.append(" CODE, OTHER_TEXT, VALUE ) ") ;
							insert.append(" VALUES (?, ?, ?, ?, ?, ?)");
						}
						stmt = con.prepareStatement(insert.toString());
						stmt.setString(1, id);
						stmt.setString(2, evaluationId);
						stmt.setString(3, compositionType);						
						stmt.setString(4, data.getCode());
						stmt.setString(5,data.getOtherText());
						//Insert value for "value" column for column
						//other than comments.
						if ((!LimsConstants.FEATURE_TYPE_EXTERNAL.equals(compositionType)) &&
						    (!LimsConstants.FEATURE_TYPE_INTERNAL.equals(compositionType))) {
							stmt.setInt(6, data.getValue());
						}
						//insert display_order for structures
						if (LimsConstants.FEATURE_TYPE_STRUCTURE.equals(compositionType)) {
							stmt.setInt(7, data.getDisplayOrder());
						}
						stmt.executeUpdate();
						//set the id on the pe feature so that the "CreatePathologyEvaluation"
						//functionality can handle OCE requirements (see that code for details)
						data.setId(id);
						ApiFunctions.close(stmt);
                        stmt = null;
						

										
					}
				}
			}
			
		} catch (SQLException e) {
			ApiFunctions.throwAsRuntimeException(e);
		}finally {
			ApiFunctions.close(con, stmt, rs);
		}
	}
	
	/**
	 * Populates features from LIMS_PE_FEATURES table for <code>evaluationId</code>.
	 */
	private void loadFeatures() {
		List lesions = new ArrayList();		
		List inflammations = new ArrayList();
		List structures = new ArrayList();
		List tumors = new ArrayList();
		List cellularStromas = new ArrayList();
		List hypoacellularStromas = new ArrayList();
		List externals = new ArrayList();
		List internals = new ArrayList();
		ResultSet rs = null;
		PreparedStatement stmt = null;
		Connection con = null;
		StringBuffer sql = new StringBuffer(512);
		sql.append("SELECT ID, VALUE, CODE, OTHER_TEXT, COMPOSITION_TYPE, DISPLAY_ORDER FROM LIMS_PE_FEATURES") ;
		sql.append(" WHERE PE_ID= ? ORDER BY DISPLAY_ORDER ASC, ID ASC");
		try {
            con = ApiFunctions.getDbConnection();
			stmt = con.prepareStatement(sql.toString());
			stmt.setString(1, evaluationId);
			rs = stmt.executeQuery();
			String compositionType = null;
			while (rs.next()) {
				PathologyEvaluationFeatureData data = new PathologyEvaluationFeatureData();
				data.setCode(rs.getString("CODE"));
				data.setOtherText(rs.getString("OTHER_TEXT"));
				if (ApiFunctions.isInteger(rs.getString("VALUE"))) {
					data.setValue(Integer.parseInt(rs.getString("VALUE")));
				}
				if (ApiFunctions.isInteger(rs.getString("DISPLAY_ORDER"))) {
					data.setDisplayOrder(Integer.parseInt(rs.getString("DISPLAY_ORDER")));
				}
				compositionType = rs.getString("COMPOSITION_TYPE");
				data.setCompositionType(compositionType);
				data.setPeId(evaluationId);
				data.setId(rs.getString("ID"));
				if (compositionType.equalsIgnoreCase(LimsConstants.FEATURE_TYPE_LESION))
					lesions.add(data);
				else if (compositionType.equalsIgnoreCase(LimsConstants.FEATURE_TYPE_INFLAMMATION))
					inflammations.add(data);
				else if (compositionType.equalsIgnoreCase(LimsConstants.FEATURE_TYPE_STRUCTURE))
					structures.add(data);
				else if (compositionType.equalsIgnoreCase(LimsConstants.FEATURE_TYPE_TUMOR))
					tumors.add(data);
				else if (compositionType.equalsIgnoreCase(LimsConstants.FEATURE_TYPE_CELLULAR))
					cellularStromas.add(data);
				else if (compositionType.equalsIgnoreCase(LimsConstants.FEATURE_TYPE_HYPOACELLULAR))
					hypoacellularStromas.add(data);
				else if (compositionType.equalsIgnoreCase(LimsConstants.FEATURE_TYPE_EXTERNAL))
					externals.add(data);
				else if (compositionType.equalsIgnoreCase(LimsConstants.FEATURE_TYPE_INTERNAL))
					internals.add(data);
				else
					throw new ApiException("Unknown composition type (" + compositionType + ") encountered in PathologyEvaluation.ejbLoad");
			}
			
		} catch (SQLException e) {
			ApiFunctions.throwAsRuntimeException(e);
		}finally {			
			ApiFunctions.close(con, stmt, rs);
		}

        setLesions(lesions);
        setInflammations(inflammations);
        setStructures(structures);
        setTumorFeatures(tumors);
        setCellularStromaFeatures(cellularStromas);
        setHypoacellularStromaFeatures(hypoacellularStromas);
        setExternalFeatures(externals);
        setInternalFeatures(internals);
        //Set featurs modified flag as false. All the setter methods 
        //for features sets this flag as true. But in this case setter methods
        //are used to load the values.
        setFeaturesModified(false);
	}
	
	/**
	 * This method was generated for supporting the associations.
	 */
	protected void _initLinks() {
	}
	/**
	 * This method was generated for supporting the associations.
	 */
	protected java.util.Vector _getLinks() {
		java.util.Vector links = new java.util.Vector();
		return links;
	}
	/**
	 * This method was generated for supporting the associations.
	 */
	protected void _removeLinks()
		throws java.rmi.RemoteException, javax.ejb.RemoveException {
		java.util.List links = _getLinks();
		for (int i = 0; i < links.size(); i++) {
			try {
				((com.ibm.ivj.ejb.associations.interfaces.Link) links.get(i))
					.remove();
			} catch (javax.ejb.FinderException e) {
			} //Consume Finder error since I am going away
		}
	}
	/**
	 * Get accessor for persistent attribute: slideId
	 */
	public java.lang.String getSlideId() {
		return slideId;
	}
	/**
	 * Set accessor for persistent attribute: slideId
	 */
	public void setSlideId(java.lang.String newSlideId) {
		slideId = newSlideId;
	}
	/**
	 * Get accessor for persistent attribute: sampleId
	 */
	public java.lang.String getSampleId() {
		return sampleId;
	}
	/**
	 * Set accessor for persistent attribute: sampleId
	 */
	public void setSampleId(java.lang.String newSampleId) {
		sampleId = newSampleId;
	}
	/**
	 * Get accessor for persistent attribute: microscopicAppearance
	 */
	public java.lang.String getMicroscopicAppearance() {
		return microscopicAppearance;
	}
	/**
	 * Set accessor for persistent attribute: microscopicAppearance
	 */
	public void setMicroscopicAppearance(
		java.lang.String newMicroscopicAppearance) {
		microscopicAppearance = newMicroscopicAppearance;
	}
	/**
	 * Get accessor for persistent attribute: reportedYN
	 */
	public java.lang.String getReportedYN() {
		return reportedYN;
	}
	/**
	 * Set accessor for persistent attribute: reportedYN
	 */
	public void setReportedYN(java.lang.String newReportedYN) {
		reportedYN = newReportedYN;
	}
	/**
	 * Get accessor for persistent attribute: createMethod
	 */
	public java.lang.String getCreateMethod() {
		return createMethod;
	}
	/**
	 * Set accessor for persistent attribute: createMethod
	 */
	public void setCreateMethod(java.lang.String newCreateMethod) {
		createMethod = newCreateMethod;
	}
	/**
	 * Get accessor for persistent attribute: tumorCells
	 */
	public Integer getTumorCells() {
		return tumorCells;
	}
	/**
	 * Set accessor for persistent attribute: tumorCells
	 */
	public void setTumorCells(Integer newTumorCells) {
		tumorCells = newTumorCells;
	}
	/**
	 * Get accessor for persistent attribute: normalCells
	 */
	public Integer getNormalCells() {
		return normalCells;
	}
	/**
	 * Set accessor for persistent attribute: normalCells
	 */
	public void setNormalCells(Integer newNormalCells) {
		normalCells = newNormalCells;
	}
	/**
	 * Get accessor for persistent attribute: hypoacellularstromaCells
	 */
	public Integer getHypoacellularstromaCells() {
		return hypoacellularstromaCells;
	}
	/**
	 * Set accessor for persistent attribute: hypoacellularstromaCells
	 */
	public void setHypoacellularstromaCells(Integer newHypoacellularstromaCells) {
		hypoacellularstromaCells = newHypoacellularstromaCells;
	}
	/**
	 * Get accessor for persistent attribute: necrosisCells
	 */
	public Integer getNecrosisCells() {
		return necrosisCells;
	}
	/**
	 * Set accessor for persistent attribute: necrosisCells
	 */
	public void setNecrosisCells(Integer newNecrosisCells) {
		necrosisCells = newNecrosisCells;
	}
	/**
	 * Get accessor for persistent attribute: lesionCells
	 */
	public Integer getLesionCells() {
		return lesionCells;
	}
	/**
	 * Set accessor for persistent attribute: lesionCells
	 */
	public void setLesionCells(Integer newLesionCells) {
		lesionCells = newLesionCells;
	}
	/**
	 * Get accessor for persistent attribute: diagnosis
	 */
	public java.lang.String getDiagnosis() {
		return diagnosis;
	}
	/**
	 * Set accessor for persistent attribute: diagnosis
	 */
	public void setDiagnosis(java.lang.String newDiagnosis) {
		diagnosis = newDiagnosis;
	}
	/**
	 * Get accessor for persistent attribute: tissueOfOrigin
	 */
	public java.lang.String getTissueOfOrigin() {
		return tissueOfOrigin;
	}
	/**
	 * Set accessor for persistent attribute: tissueOfOrigin
	 */
	public void setTissueOfOrigin(java.lang.String newTissueOfOrigin) {
		tissueOfOrigin = newTissueOfOrigin;
	}
	/**
	 * Get accessor for persistent attribute: createDate
	 */
	public java.sql.Timestamp getCreateDate() {
		return createDate;
	}
	/**
	 * Set accessor for persistent attribute: createDate
	 */
	public void setCreateDate(java.sql.Timestamp newCreateDate) {
		createDate = newCreateDate;
	}
	/**
	 * Get accessor for persistent attribute: pathologist
	 */
	public java.lang.String getPathologist() {
		return pathologist;
	}
	/**
	 * Set accessor for persistent attribute: pathologist
	 */
	public void setPathologist(java.lang.String newPathologist) {
		pathologist = newPathologist;
	}
	/**
	 * Get accessor for persistent attribute: migratedYN
	 */
	public java.lang.String getMigratedYN() {
		return migratedYN;
	}
	/**
	 * Set accessor for persistent attribute: migratedYN
	 */
	public void setMigratedYN(java.lang.String newMigratedYN) {
		migratedYN = newMigratedYN;
	}
	/**
	 * Get accessor for persistent attribute: tissueOfFinding
	 */
	public java.lang.String getTissueOfFinding() {
		return tissueOfFinding;
	}
	/**
	 * Set accessor for persistent attribute: tissueOfFinding
	 */
	public void setTissueOfFinding(java.lang.String newTissueOfFinding) {
		tissueOfFinding = newTissueOfFinding;
	}
	/**
	 * Get accessor for persistent attribute: tissueOfFindingOther
	 */
	public java.lang.String getTissueOfFindingOther() {
		return tissueOfFindingOther;
	}
	/**
	 * Set accessor for persistent attribute: tissueOfFindingOther
	 */
	public void setTissueOfFindingOther(
		java.lang.String newTissueOfFindingOther) {
		tissueOfFindingOther = newTissueOfFindingOther;
	}
	/**
	 * Get accessor for persistent attribute: diagnosisOther
	 */
	public java.lang.String getDiagnosisOther() {
		return diagnosisOther;
	}
	/**
	 * Set accessor for persistent attribute: diagnosisOther
	 */
	public void setDiagnosisOther(java.lang.String newDiagnosisOther) {
		diagnosisOther = newDiagnosisOther;
	}
	/**
	 * Get accessor for persistent attribute: tissueOfOriginOther
	 */
	public java.lang.String getTissueOfOriginOther() {
		return tissueOfOriginOther;
	}
	/**
	 * Set accessor for persistent attribute: tissueOfOriginOther
	 */
	public void setTissueOfOriginOther(
		java.lang.String newTissueOfOriginOther) {
		tissueOfOriginOther = newTissueOfOriginOther;
	}
	/**
	 * Get accessor for persistent attribute: concatenatedExternalComments
	 */
	public java.lang.String getConcatenatedExternalComments() {
		return concatenatedExternalComments;
	}
	/**
	 * Set accessor for persistent attribute: concatenatedExternalComments
	 */
	public void setConcatenatedExternalComments(
		java.lang.String newConcatenatedExternalComments) {
		concatenatedExternalComments = newConcatenatedExternalComments;
	}
	/**
	 * Get accessor for persistent attribute: concatenatedInternalComments
	 */
	public java.lang.String getConcatenatedInternalComments() {
		return concatenatedInternalComments;
	}
	/**
	 * Set accessor for persistent attribute: concatenatedInternalComments
	 */
	public void setConcatenatedInternalComments(
		java.lang.String newConcatenatedInternalComments) {
		concatenatedInternalComments = newConcatenatedInternalComments;
	}
	/**
	 * Get accessor for persistent attribute: sourceEvaluationId
	 */
	public java.lang.String getSourceEvaluationId() {
		return sourceEvaluationId;
	}
	/**
	 * Set accessor for persistent attribute: sourceEvaluationId
	 */
	public void setSourceEvaluationId(java.lang.String newSourceEvaluationId) {
		sourceEvaluationId = newSourceEvaluationId;
	}
	/**
	 * Get accessor for persistent attribute: cellularstromaCells
	 */
	public Integer getCellularstromaCells() {
		return cellularstromaCells;
	}
	/**
	 * Set accessor for persistent attribute: cellularstromaCells
	 */
	public void setCellularstromaCells(Integer newCellularstromaCells) {
		cellularstromaCells = newCellularstromaCells;
	}

	/**
	 * Returns the lesions.
	 * @return List
	 */
	public List getLesions() {
		return _lesions;
	}

	/**
	 * Sets the lesions.
	 * @param lesions The lesions to set
	 */
	public void setLesions(List lesions) {
		_lesions = lesions;
		setFeaturesModified(true);
	}

	/**
	 * Returns the cellularStromaFeatures.
	 * @return List
	 */
	public List getCellularStromaFeatures() {
		return _cellularStromaFeatures;
	}

	/**
	 * Returns the externalFeatures.
	 * @return List
	 */
	public List getExternalFeatures() {
		return _externalFeatures;
	}

	/**
	 * Returns the hypoacellularStromaFeatures.
	 * @return List
	 */
	public List getHypoacellularStromaFeatures() {
		return _hypoacellularStromaFeatures;
	}

	/**
	 * Returns the inflammations.
	 * @return List
	 */
	public List getInflammations() {
		return _inflammations;
	}

	/**
	 * Returns the internalFeatures.
	 * @return List
	 */
	public List getInternalFeatures() {
		return _internalFeatures;
	}

	/**
	 * Returns the structures.
	 * @return List
	 */
	public List getStructures() {
		return _structures;
	}

	/**
	 * Returns the tumorFeatures.
	 * @return List
	 */
	public List getTumorFeatures() {
		return _tumorFeatures;
	}

	/**
	 * Sets the cellularStromaFeatures.
	 * @param cellularStromaFeatures The cellularStromaFeatures to set
	 */
	public void setCellularStromaFeatures(List cellularStromaFeatures) {
		_cellularStromaFeatures = cellularStromaFeatures;
		setFeaturesModified(true);
	}

	/**
	 * Sets the externalFeatures.
	 * @param externalFeatures The externalFeatures to set
	 */
	public void setExternalFeatures(List externalFeatures) {
		_externalFeatures = externalFeatures;
        setFeaturesModified(true);
	}

	/**
	 * Sets the hypoacellularStromaFeatures.
	 * @param hypoacellularStromaFeatures The hypoacellularStromaFeatures to set
	 */
	public void setHypoacellularStromaFeatures(List hypoacellularStromaFeatures) {
		_hypoacellularStromaFeatures = hypoacellularStromaFeatures;
		setFeaturesModified(true);
	}

	/**
	 * Sets the inflammations.
	 * @param inflammations The inflammations to set
	 */
	public void setInflammations(List inflammations) {
		_inflammations = inflammations;
		setFeaturesModified(true);
	}

	/**
	 * Sets the internalFeatures.
	 * @param internalFeatures The internalFeatures to set
	 */
	public void setInternalFeatures(List internalFeatures) {
		_internalFeatures = internalFeatures;
        setFeaturesModified(true);
	}

	/**
	 * Sets the structures.
	 * @param structures The structures to set
	 */
	public void setStructures(List structures) {
		_structures = structures;
		setFeaturesModified(true);
	}

	/**
	 * Sets the tumorFeatures.
	 * @param tumorFeatures The tumorFeatures to set
	 */
	public void setTumorFeatures(List tumorFeatures) {
		_tumorFeatures = tumorFeatures;
		setFeaturesModified(true);
	}

	/**
	 * Get accessor for persistent attribute: reportedDate
	 */
	public java.sql.Timestamp getReportedDate() {
		return reportedDate;
	}
	/**
	 * Set accessor for persistent attribute: reportedDate
	 */
	public void setReportedDate(java.sql.Timestamp newReportedDate) {
		reportedDate = newReportedDate;
	}
	/**
	 * Returns the featuresModified.
	 * @return boolean
	 */
	private boolean isFeaturesModified() {
		return _featuresModified;
	}

	/**
	 * Returns the n.
	 * @return boolean
	 */
	private boolean isNew() {
		return _new;
	}

	/**
	 * Sets the featuresModified.
	 * @param featuresModified The featuresModified to set
	 */
	private void setFeaturesModified(boolean featuresModified) {
		_featuresModified = featuresModified;
	}

	/**
	 * Sets the n.
	 * @param n The n to set
	 */
	private void setNew(boolean n) {
		_new = n;
	}

  /**
   * _copyFromEJB
   */
  public java.util.Hashtable _copyFromEJB() {
    com.ibm.ivj.ejb.runtime.AccessBeanHashtable h = new com.ibm.ivj.ejb.runtime.AccessBeanHashtable();

    h.put("lesionCells", getLesionCells());
    h.put("slideId", getSlideId());
    h.put("hypoacellularstromaCells", getHypoacellularstromaCells());
    h.put("microscopicAppearance", getMicroscopicAppearance());
    h.put("tissueOfFinding", getTissueOfFinding());
    h.put("diagnosis", getDiagnosis());
    h.put("createDate", getCreateDate());
    h.put("concatenatedInternalComments", getConcatenatedInternalComments());
    h.put("tissueOfFindingOther", getTissueOfFindingOther());
    h.put("tissueOfOrigin", getTissueOfOrigin());
    h.put("sampleId", getSampleId());
    h.put("sourceEvaluationId", getSourceEvaluationId());
    h.put("diagnosisOther", getDiagnosisOther());
    h.put("pathologist", getPathologist());
    h.put("concatenatedExternalComments", getConcatenatedExternalComments());
    h.put("reportedYN", getReportedYN());
    h.put("migratedYN", getMigratedYN());
    h.put("normalCells", getNormalCells());
    h.put("necrosisCells", getNecrosisCells());
    h.put("cellularstromaCells", getCellularstromaCells());
    h.put("tissueOfOriginOther", getTissueOfOriginOther());
    h.put("reportedDate", getReportedDate());
    h.put("createMethod", getCreateMethod());
    h.put("tumorCells", getTumorCells());
    h.put("__Key", getEntityContext().getPrimaryKey());

    return h;
  }
  /**
   * _copyToEJB
   */
  public void _copyToEJB(java.util.Hashtable h) {
    java.lang.Integer localLesionCells = (java.lang.Integer) h.get("lesionCells");
    java.lang.String localSlideId = (java.lang.String) h.get("slideId");
    java.lang.Integer localHypoacellularstromaCells = (java.lang.Integer) h
      .get("hypoacellularstromaCells");
    java.lang.String localMicroscopicAppearance = (java.lang.String) h.get("microscopicAppearance");
    java.lang.String localTissueOfFinding = (java.lang.String) h.get("tissueOfFinding");
    java.lang.String localDiagnosis = (java.lang.String) h.get("diagnosis");
    java.sql.Timestamp localCreateDate = (java.sql.Timestamp) h.get("createDate");
    java.lang.String localConcatenatedInternalComments = (java.lang.String) h
      .get("concatenatedInternalComments");
    java.lang.String localTissueOfFindingOther = (java.lang.String) h.get("tissueOfFindingOther");
    java.lang.String localTissueOfOrigin = (java.lang.String) h.get("tissueOfOrigin");
    java.lang.String localSampleId = (java.lang.String) h.get("sampleId");
    java.lang.String localSourceEvaluationId = (java.lang.String) h.get("sourceEvaluationId");
    java.lang.String localDiagnosisOther = (java.lang.String) h.get("diagnosisOther");
    java.lang.String localPathologist = (java.lang.String) h.get("pathologist");
    java.lang.String localConcatenatedExternalComments = (java.lang.String) h
      .get("concatenatedExternalComments");
    java.lang.String localReportedYN = (java.lang.String) h.get("reportedYN");
    java.lang.String localMigratedYN = (java.lang.String) h.get("migratedYN");
    java.lang.Integer localNormalCells = (java.lang.Integer) h.get("normalCells");
    java.lang.Integer localNecrosisCells = (java.lang.Integer) h.get("necrosisCells");
    java.lang.Integer localCellularstromaCells = (java.lang.Integer) h.get("cellularstromaCells");
    java.lang.String localTissueOfOriginOther = (java.lang.String) h.get("tissueOfOriginOther");
    java.sql.Timestamp localReportedDate = (java.sql.Timestamp) h.get("reportedDate");
    java.lang.String localCreateMethod = (java.lang.String) h.get("createMethod");
    java.lang.Integer localTumorCells = (java.lang.Integer) h.get("tumorCells");

    if (h.containsKey("lesionCells"))
      setLesionCells((localLesionCells));
    if (h.containsKey("slideId"))
      setSlideId((localSlideId));
    if (h.containsKey("hypoacellularstromaCells"))
      setHypoacellularstromaCells((localHypoacellularstromaCells));
    if (h.containsKey("microscopicAppearance"))
      setMicroscopicAppearance((localMicroscopicAppearance));
    if (h.containsKey("tissueOfFinding"))
      setTissueOfFinding((localTissueOfFinding));
    if (h.containsKey("diagnosis"))
      setDiagnosis((localDiagnosis));
    if (h.containsKey("createDate"))
      setCreateDate((localCreateDate));
    if (h.containsKey("concatenatedInternalComments"))
      setConcatenatedInternalComments((localConcatenatedInternalComments));
    if (h.containsKey("tissueOfFindingOther"))
      setTissueOfFindingOther((localTissueOfFindingOther));
    if (h.containsKey("tissueOfOrigin"))
      setTissueOfOrigin((localTissueOfOrigin));
    if (h.containsKey("sampleId"))
      setSampleId((localSampleId));
    if (h.containsKey("sourceEvaluationId"))
      setSourceEvaluationId((localSourceEvaluationId));
    if (h.containsKey("diagnosisOther"))
      setDiagnosisOther((localDiagnosisOther));
    if (h.containsKey("pathologist"))
      setPathologist((localPathologist));
    if (h.containsKey("concatenatedExternalComments"))
      setConcatenatedExternalComments((localConcatenatedExternalComments));
    if (h.containsKey("reportedYN"))
      setReportedYN((localReportedYN));
    if (h.containsKey("migratedYN"))
      setMigratedYN((localMigratedYN));
    if (h.containsKey("normalCells"))
      setNormalCells((localNormalCells));
    if (h.containsKey("necrosisCells"))
      setNecrosisCells((localNecrosisCells));
    if (h.containsKey("cellularstromaCells"))
      setCellularstromaCells((localCellularstromaCells));
    if (h.containsKey("tissueOfOriginOther"))
      setTissueOfOriginOther((localTissueOfOriginOther));
    if (h.containsKey("reportedDate"))
      setReportedDate((localReportedDate));
    if (h.containsKey("createMethod"))
      setCreateMethod((localCreateMethod));
    if (h.containsKey("tumorCells"))
      setTumorCells((localTumorCells));
  }
}
