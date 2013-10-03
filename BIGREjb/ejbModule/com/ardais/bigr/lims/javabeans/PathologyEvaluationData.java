package com.ardais.bigr.lims.javabeans;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.api.Escaper;
import com.ardais.bigr.beanutils.BigrBeanUtilsBean;
import com.ardais.bigr.iltds.helpers.FormLogic;
import com.ardais.bigr.lims.beans.PathologyEvaluationAccessBean;
import com.ardais.bigr.lims.helpers.LimsConstants;
import com.ardais.bigr.orm.helpers.BigrGbossData;
import com.ardais.bigr.util.gen.DbAliases;

/**
 * Represents the raw data of a pathology evaluation.
 */
public class PathologyEvaluationData implements Serializable {
	private static final long serialVersionUID = -6025161041912258893L;
	private String _evaluationId;
	private String _slideId;
	private String _sampleId;
	private String _microscopicAppearance;
	private boolean _isReported;
	private String _createMethod;
	private int _tumorCells;
	private int _normalCells;
	private int _hypoacellularStromaCells;
	private int _necrosisCells;
	private int _lesionCells;
	private String _diagnosis;
	private String _diagnosisOther;
	private String _tissueOfOrigin;
	private String _tissueOfOriginOther;
	private Timestamp _creationDate;
	private Timestamp _reportedDate;
	private String _pathologist;
	private boolean _isMigrated;
	private String _tissueOfFinding;
	private String _tissueOfFindingOther;
	private String _concatenatedExternalData;
	private String _concatenatedInternalData;
	private String _sourceEvaluationId;
	private int _cellularStromaCells;	
	private List _lesions;
	private List _inflammations;
	private List _structures;
	private List _tumorFeatures;
	private List _cellularStromaFeatures;
	private List _hypoacellularStromaFeatures;
	private List _externalFeatures;
	private List _internalFeatures;

	/**
	 * Creates a new <code>PathologyEvaluationData</code>.
	 */
	public PathologyEvaluationData() {
	}
  
  /**
   * Creates a new <code>PathologyEvaluationData</code>, initialized from
   * the data in the current row of the result set.
   *
   * @param  pathologyEvaluationData  the <code>PathologyEvaluationData</code>
   */
  public PathologyEvaluationData(PathologyEvaluationData pathologyEvaluationData) {
    this();
    BigrBeanUtilsBean.SINGLETON.copyProperties(this, pathologyEvaluationData);
    //any mutable objects must be handled seperately (BigrBeanUtilsBean.copyProperties
    //does a shallow copy.
    if (pathologyEvaluationData.getCreationDate() != null) {
      setCreationDate((Timestamp) pathologyEvaluationData.getCreationDate().clone());
    }
    if (pathologyEvaluationData.getReportedDate() != null) {
      setReportedDate((Timestamp) pathologyEvaluationData.getReportedDate().clone());
    }
    if (!ApiFunctions.isEmpty(pathologyEvaluationData.getLesions())) {
      _lesions.clear();
      Iterator iterator = pathologyEvaluationData.getLesions().iterator();
      while (iterator.hasNext()) {
        _lesions.add(new PathologyEvaluationFeatureData((PathologyEvaluationFeatureData)iterator.next()));
      }
    }
    if (!ApiFunctions.isEmpty(pathologyEvaluationData.getInflammations())) {
      _inflammations.clear();
      Iterator iterator = pathologyEvaluationData.getInflammations().iterator();
      while (iterator.hasNext()) {
        _inflammations.add(new PathologyEvaluationFeatureData((PathologyEvaluationFeatureData)iterator.next()));
      }
    }
    if (!ApiFunctions.isEmpty(pathologyEvaluationData.getStructures())) {
      _structures.clear();
      Iterator iterator = pathologyEvaluationData.getStructures().iterator();
      while (iterator.hasNext()) {
        _structures.add(new PathologyEvaluationFeatureData((PathologyEvaluationFeatureData)iterator.next()));
      }
    }
    if (!ApiFunctions.isEmpty(pathologyEvaluationData.getTumorFeatures())) {
      _tumorFeatures.clear();
      Iterator iterator = pathologyEvaluationData.getTumorFeatures().iterator();
      while (iterator.hasNext()) {
        _tumorFeatures.add(new PathologyEvaluationFeatureData((PathologyEvaluationFeatureData)iterator.next()));
      }
    }
    if (!ApiFunctions.isEmpty(pathologyEvaluationData.getCellularStromaFeatures())) {
      _cellularStromaFeatures.clear();
      Iterator iterator = pathologyEvaluationData.getCellularStromaFeatures().iterator();
      while (iterator.hasNext()) {
        _cellularStromaFeatures.add(new PathologyEvaluationFeatureData((PathologyEvaluationFeatureData)iterator.next()));
      }
    }
    if (!ApiFunctions.isEmpty(pathologyEvaluationData.getHypoacellularStromaFeatures())) {
      _hypoacellularStromaFeatures.clear();
      Iterator iterator = pathologyEvaluationData.getHypoacellularStromaFeatures().iterator();
      while (iterator.hasNext()) {
        _hypoacellularStromaFeatures.add(new PathologyEvaluationFeatureData((PathologyEvaluationFeatureData)iterator.next()));
      }
    }
    if (!ApiFunctions.isEmpty(pathologyEvaluationData.getExternalFeatures())) {
      _externalFeatures.clear();
      Iterator iterator = pathologyEvaluationData.getExternalFeatures().iterator();
      while (iterator.hasNext()) {
        _externalFeatures.add(new PathologyEvaluationFeatureData((PathologyEvaluationFeatureData)iterator.next()));
      }
    }
    if (!ApiFunctions.isEmpty(pathologyEvaluationData.getInternalFeatures())) {
      _internalFeatures.clear();
      Iterator iterator = pathologyEvaluationData.getInternalFeatures().iterator();
      while (iterator.hasNext()) {
        _internalFeatures.add(new PathologyEvaluationFeatureData((PathologyEvaluationFeatureData)iterator.next()));
      }
    }
  }
	
	/**
	 * Creates a new <code>PathologyEvaluationData</code>, initialized from
	 * the data in the current row of the result set.
	 *
	 * @param  columns  a <code>Map</code> containing a key for each column in
	 * 									 the <code>ResultSet</code>.  Each key must be one of the
	 * 									 column alias constants in {@link com.ardais.bigr.util.DbAliases}
	 * 									 and the corresponding value is ignored.
	 * @param  rs  the <code>ResultSet</code>
	 */
	public PathologyEvaluationData(Map columns, ResultSet rs) {
    this();
    populate(columns, rs);
	}
	
	/**
	 * Populates this <code>PathologyEvaluationData</code> from the data in the current row of 
	 * the result set.
	 * 
	 * @param  columns  a <code>Map</code> containing a key for each column in
	 * 									 the <code>ResultSet</code>.  Each key must be one of the
	 * 									 column alias constants in {@link com.ardais.bigr.util.DbAliases}
	 * 									 and the corresponding value is ignored.
	 * @param  rs  the <code>ResultSet</code>
	 */
	public void populate(Map columns, ResultSet rs) {
	    try {
		    if (columns.containsKey(DbAliases.PE_ID)) {
		    	setEvaluationId(rs.getString(DbAliases.PE_ID));
		    }
		    if (columns.containsKey(DbAliases.LIMS_PE_SLIDE_ID)) {
		    	setSlideId(rs.getString(DbAliases.LIMS_PE_SLIDE_ID));
		    }
		    if (columns.containsKey(DbAliases.LIMS_PE_SAMPLE_ID)) {
		    	setSampleId(rs.getString(DbAliases.LIMS_PE_SAMPLE_ID));
		    }
		    if (columns.containsKey(DbAliases.LIMS_PE_APPEARANCE)) {
		    	setMicroscopicAppearance(rs.getString(DbAliases.LIMS_PE_APPEARANCE));
		    }
		    if (columns.containsKey(DbAliases.LIMS_PE_REPORTED)) {
		    	setIsReported(rs.getString(DbAliases.LIMS_PE_REPORTED).equals(LimsConstants.LIMS_DB_YES));
		    }
		    if (columns.containsKey(DbAliases.LIMS_PE_CREATE_TYPE)) {
		    	setCreateMethod(rs.getString(DbAliases.LIMS_PE_CREATE_TYPE));
		    }
		    if (columns.containsKey(DbAliases.LIMS_PE_TMR)) {
		    	if (rs.getString(DbAliases.LIMS_PE_TMR) != null) {
			    	setTumorCells(Integer.parseInt(rs.getString(DbAliases.LIMS_PE_TMR)));
		    	}
		    }
		    if (columns.containsKey(DbAliases.LIMS_PE_NRM)) {
		    	if (rs.getString(DbAliases.LIMS_PE_NRM) != null) {
			    	setNormalCells(Integer.parseInt(rs.getString(DbAliases.LIMS_PE_NRM)));
		    	}
		    }
		    if (columns.containsKey(DbAliases.LIMS_PE_TAS)) {
		    	if (rs.getString(DbAliases.LIMS_PE_TAS) != null) {
			    	setHypoacellularStromaCells(Integer.parseInt(rs.getString(DbAliases.LIMS_PE_TAS)));
		    	}
		    }
		    if (columns.containsKey(DbAliases.LIMS_PE_NEC)) {
		    	if (rs.getString(DbAliases.LIMS_PE_NEC) != null) {
			    	setNecrosisCells(Integer.parseInt(rs.getString(DbAliases.LIMS_PE_NEC)));
		    	}
		    }
		    if (columns.containsKey(DbAliases.LIMS_PE_LSN)) {
		    	if (rs.getString(DbAliases.LIMS_PE_LSN) != null) {
			    	setLesionCells(Integer.parseInt(rs.getString(DbAliases.LIMS_PE_LSN)));
		    	}
		    }
		    if (columns.containsKey(DbAliases.LIMS_PE_TCS)) {
		    	if (rs.getString(DbAliases.LIMS_PE_TCS) != null) {
			    	setCellularStromaCells(Integer.parseInt(rs.getString(DbAliases.LIMS_PE_TCS)));
		    	}
		    }
		    if (columns.containsKey(DbAliases.LIMS_PE_DX)) {
		    	setDiagnosis(rs.getString(DbAliases.LIMS_PE_DX));
		    }
		    if (columns.containsKey(DbAliases.LIMS_PE_TISSUE_ORIGIN)) {
		    	setTissueOfOrigin(rs.getString(DbAliases.LIMS_PE_TISSUE_ORIGIN));
		    }
		    if (columns.containsKey(DbAliases.LIMS_PE_CREATE_DATE)) {
		    	setCreationDate(rs.getTimestamp(DbAliases.LIMS_PE_CREATE_DATE));
		    }
		    if (columns.containsKey(DbAliases.LIMS_PE_REPORTED_DATE)) {
		    	setReportedDate(rs.getTimestamp(DbAliases.LIMS_PE_REPORTED_DATE));
		    }
		    if (columns.containsKey(DbAliases.LIMS_PE_CREATE_USER)) {
		    	setPathologist(rs.getString(DbAliases.LIMS_PE_CREATE_USER));
		    }
		    if (columns.containsKey(DbAliases.LIMS_PE_MIGRATED)) {
		    	setIsMigrated(rs.getString(DbAliases.LIMS_PE_MIGRATED).equals(LimsConstants.LIMS_DB_YES));
		    }
		    if (columns.containsKey(DbAliases.LIMS_PE_TISSUE_FINDING)) {
		    	setTissueOfFinding(rs.getString(DbAliases.LIMS_PE_TISSUE_FINDING));
		    }
		    if (columns.containsKey(DbAliases.LIMS_PE_OTHER_TISSUE_FINDING)) {
		    	setTissueOfFindingOther(rs.getString(DbAliases.LIMS_PE_OTHER_TISSUE_FINDING));
		    }
		    if (columns.containsKey(DbAliases.LIMS_PE_OTHER_DIAGNOSIS)) {
		    	setDiagnosisOther(rs.getString(DbAliases.LIMS_PE_OTHER_DIAGNOSIS));
		    }
		    if (columns.containsKey(DbAliases.LIMS_PE_OTHER_TISSUE_ORIGIN)) {
		    	setTissueOfOriginOther(rs.getString(DbAliases.LIMS_PE_OTHER_TISSUE_ORIGIN));
		    }
		    if (columns.containsKey(DbAliases.LIMS_PE_EXTERNAL_COMMENTS)) {
		    	setConcatenatedExternalData(rs.getString(DbAliases.LIMS_PE_EXTERNAL_COMMENTS));
		    }
		    if (columns.containsKey(DbAliases.LIMS_PE_INTERNAL_COMMENTS)) {
		    	setConcatenatedInternalData(rs.getString(DbAliases.LIMS_PE_INTERNAL_COMMENTS));
		    }
		    if (columns.containsKey(DbAliases.LIMS_PE_SOURCE_ID)) {
		    	setSourceEvaluationId(rs.getString(DbAliases.LIMS_PE_SOURCE_ID));
		    }
	    } 
	    catch (SQLException e) {
	        ApiFunctions.throwAsRuntimeException(e);
	    }
	}
	
	/**
	 * Populates this <code>PathologyEvaluationData</code> from the data in the PathologyEvaluationAccessBean.
	 * 
	 * @param  peAccess  a <code>PathologyEvaluationAccessBean</code>.
	 */
	public void populate(PathologyEvaluationAccessBean peAccess) {
		if (peAccess == null)
			return;
		String peId = null;
		try {
			setCellularStromaCells(peAccess.getCellularstromaCells().intValue());
			setCellularStromaFeatures(peAccess.getCellularStromaFeatures());
			setConcatenatedExternalData(peAccess.getConcatenatedExternalComments());
			setConcatenatedInternalData(peAccess.getConcatenatedInternalComments());
			setCreateMethod(peAccess.getCreateMethod());
			setCreationDate(peAccess.getCreateDate());
			setDiagnosis(peAccess.getDiagnosis());
			setDiagnosisOther(peAccess.getDiagnosisOther());
			peId = (String)peAccess.getEJBRef().getPrimaryKey();
			setEvaluationId(peId);
			setExternalFeatures(peAccess.getExternalFeatures());
			setHypoacellularStromaCells(peAccess.getHypoacellularstromaCells().intValue());
			setHypoacellularStromaFeatures(peAccess.getHypoacellularStromaFeatures());
			setInflammations(peAccess.getInflammations());
			setInternalFeatures(peAccess.getInternalFeatures());
			setIsMigrated(peAccess.getMigratedYN().equals(LimsConstants.LIMS_DB_YES));
			setIsReported(peAccess.getReportedYN().equals(LimsConstants.LIMS_DB_YES));
			setLesionCells(peAccess.getLesionCells().intValue());
			setLesions(peAccess.getLesions());
			setMicroscopicAppearance(peAccess.getMicroscopicAppearance());
			setNecrosisCells(peAccess.getNecrosisCells().intValue());
			setNormalCells(peAccess.getNormalCells().intValue());
			setPathologist(peAccess.getPathologist());
			setReportedDate(peAccess.getReportedDate());
			setSampleId(peAccess.getSampleId());
			setSlideId(peAccess.getSlideId());
			setSourceEvaluationId(peAccess.getSourceEvaluationId());
			setStructures(peAccess.getStructures());
			setTissueOfFinding(peAccess.getTissueOfFinding());
			setTissueOfFindingOther(peAccess.getTissueOfFindingOther());
			setTissueOfOrigin(peAccess.getTissueOfOrigin());
			setTissueOfOriginOther(peAccess.getTissueOfOriginOther());
			setTumorCells(peAccess.getTumorCells().intValue());
			setTumorFeatures(peAccess.getTumorFeatures());
		}
		catch (Exception e) {
			//log the error, and return an error to the front end.
			com.ardais.bigr.api.ApiLogger.log(
				"Error retrieving data from PathologyEvaluationAccessBean with Id = " + peId
				+ ": Error = " + e.getLocalizedMessage());
			throw new ApiException(e);
		}
	}

	/**
	 * Returns the cellularStromaCells.
	 * @return int
	 */
	public int getCellularStromaCells() {
		return _cellularStromaCells;
	}

	/**
	 * Returns the cellularStromaFeatures.
	 * @return List
	 */
	public List getCellularStromaFeatures() {
		return _cellularStromaFeatures;
	}

  /**
   * Returns the concatenatedExternalData, with any special HTML characters
   * replaced with their entity equivalents.
   * @return String
   */
  public String getHTMLSafeConcatenatedExternalData() {
    return Escaper.htmlEscape(getConcatenatedExternalData());
  }

	/**
	 * Returns the concatenatedExternalData.
	 * @return String
	 */
	public String getConcatenatedExternalData() {
		if (_concatenatedExternalData == null || _concatenatedExternalData.equals("")) {
			_concatenatedExternalData = concatenateComments("external");
		}
		return _concatenatedExternalData;
	}

  /**
   * Returns the formatted (i.e. bolded section headers) concatenatedExternalData.
   * @return String
   */
  public String getFormattedConcatenatedExternalData() {
    String original = getHTMLSafeConcatenatedExternalData();
    
    /* For each component that comprises the external comments, see if it's header is in the 
     * string.  If so, bold the header and create a new string with that header bolded.
     * This is somewhat inefficient, and the original solution used a single string and single
     * stringbuffer, but samples that had the text of a component header in it's "Other comments"
     * section caused a StringIndexOutOfBoundsException
     */
    
    int startPos = 0;
    int endPos = 0;
    //if the tumor section is present, bold it
    startPos = original.indexOf(LimsConstants.LIMS_PV_EXT_COMMENT_HEADER_TUMOR);
    if (startPos >= 0) {
      //the new string length will be the original plus the bolding tags
      //for the category (7 characters * 7 categories)
      StringBuffer buff = new StringBuffer(original.length() + 7);
      endPos = startPos + LimsConstants.LIMS_PV_EXT_COMMENT_HEADER_TUMOR.length();
      if (startPos > 0) {
        buff.append(original.substring(0,startPos));
      }
      buff.append("<b>");
      buff.append(original.substring(startPos,endPos));
      buff.append("</b>");
      buff.append(original.substring(endPos));
      original = buff.toString();
    }
    
    //if the cellular section is present, bold it
    startPos = original.indexOf(LimsConstants.LIMS_PV_EXT_COMMENT_HEADER_CELLULAR);
    if (startPos >= 0) {
      //the new string length will be the original plus the bolding tags
      //for the category (7 characters * 7 categories)
      StringBuffer buff = new StringBuffer(original.length() + 7);
      endPos = startPos + LimsConstants.LIMS_PV_EXT_COMMENT_HEADER_CELLULAR.length();
      if (startPos > 0) {
        buff.append(original.substring(0,startPos));
      }
      buff.append("<b>");
      buff.append(original.substring(startPos,endPos));
      buff.append("</b>");
      buff.append(original.substring(endPos));
      original = buff.toString();
    }
    
    //if the hypoacellular section is present, bold it
    startPos = original.indexOf(LimsConstants.LIMS_PV_EXT_COMMENT_HEADER_HYPOACELLULAR);
    if (startPos >= 0) {
      //the new string length will be the original plus the bolding tags
      //for the category (7 characters * 7 categories)
      StringBuffer buff = new StringBuffer(original.length() + 7);
      endPos = startPos + LimsConstants.LIMS_PV_EXT_COMMENT_HEADER_HYPOACELLULAR.length();
      if (startPos > 0) {
        buff.append(original.substring(0,startPos));
      }
      buff.append("<b>");
      buff.append(original.substring(startPos,endPos));
      buff.append("</b>");
      buff.append(original.substring(endPos));
      original = buff.toString();
    }
    
    //if the lesion section is present, bold it
    startPos = original.indexOf(LimsConstants.LIMS_PV_EXT_COMMENT_HEADER_LESION);
    if (startPos >= 0) {
      //the new string length will be the original plus the bolding tags
      //for the category (7 characters * 7 categories)
      StringBuffer buff = new StringBuffer(original.length() + 7);
      endPos = startPos + LimsConstants.LIMS_PV_EXT_COMMENT_HEADER_LESION.length();
      if (startPos > 0) {
        buff.append(original.substring(0,startPos));
      }
      buff.append("<b>");
      buff.append(original.substring(startPos,endPos));
      buff.append("</b>");
      buff.append(original.substring(endPos));
      original = buff.toString();
    }
    
    //if the inflammation section is present, bold it
    startPos = original.indexOf(LimsConstants.LIMS_PV_EXT_COMMENT_HEADER_INFLAMMATION);
    if (startPos >= 0) {
      //the new string length will be the original plus the bolding tags
      //for the category (7 characters * 7 categories)
      StringBuffer buff = new StringBuffer(original.length() + 7);
      endPos = startPos + LimsConstants.LIMS_PV_EXT_COMMENT_HEADER_INFLAMMATION.length();
      if (startPos > 0) {
        buff.append(original.substring(0,startPos));
      }
      buff.append("<b>");
      buff.append(original.substring(startPos,endPos));
      buff.append("</b>");
      buff.append(original.substring(endPos));
      original = buff.toString();
    }
    
    //if the structure section is present, bold it
    startPos = original.indexOf(LimsConstants.LIMS_PV_EXT_COMMENT_HEADER_STRUCTURES);
    if (startPos >= 0) {
      //the new string length will be the original plus the bolding tags
      //for the category (7 characters * 7 categories)
      StringBuffer buff = new StringBuffer(original.length() + 7);
      endPos = startPos + LimsConstants.LIMS_PV_EXT_COMMENT_HEADER_STRUCTURES.length();
      if (startPos > 0) {
        buff.append(original.substring(0,startPos));
      }
      buff.append("<b>");
      buff.append(original.substring(startPos,endPos));
      buff.append("</b>");
      buff.append(original.substring(endPos));
      original = buff.toString();
    }
    
    //if the other section is present, bold it
    startPos = original.indexOf(LimsConstants.LIMS_PV_EXT_COMMENT_HEADER_OTHERS);
    if (startPos >= 0) {
      //the new string length will be the original plus the bolding tags
      //for the category (7 characters * 7 categories)
      StringBuffer buff = new StringBuffer(original.length() + 7);
      endPos = startPos + LimsConstants.LIMS_PV_EXT_COMMENT_HEADER_OTHERS.length();
      if (startPos > 0) {
        buff.append(original.substring(0,startPos));
      }
      buff.append("<b>");
      buff.append(original.substring(startPos,endPos));
      buff.append("</b>");
      buff.append(original.substring(endPos));
      original = buff.toString();
    }

    return original;
  }

  /**
   * Returns the concatenatedInternalData, with any special HTML characters
   * replacedwith their entity equivalents.
   * @return String
   */
  public String getHTMLSafeConcatenatedInternalData() {
    return Escaper.htmlEscape(getConcatenatedInternalData());
  }

	/**
	 * Returns the concatenatedInternalData.
	 * @return String
	 */
	public String getConcatenatedInternalData() {
		if (_concatenatedInternalData == null || _concatenatedInternalData.equals("")) {
			_concatenatedInternalData = concatenateComments("internal");
		}
		return _concatenatedInternalData;
	}

	/**
	 * Returns the createMethod.
	 * @return String
	 */
	public String getCreateMethod() {
		return _createMethod;
	}

	/**
	 * Returns the creationDate.
	 * @return Date
	 */
	public Timestamp getCreationDate() {
		return _creationDate;
	}

	/**
	 * Returns the diagnosis.
	 * @return String
	 */
	public String getDiagnosis() {
		return _diagnosis;
	}

	/**
	 * Returns the evaluationId.
	 * @return String
	 */
	public String getEvaluationId() {
		return _evaluationId;
	}

	/**
	 * Returns the externalFeatures.
	 * @return List
	 */
	public List getExternalFeatures() {
		return _externalFeatures;
	}

	/**
	 * Returns the hypoacellularStromaCells.
	 * @return int
	 */
	public int getHypoacellularStromaCells() {
		return _hypoacellularStromaCells;
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
	 * Returns the isMigrated.
	 * @return boolean
	 */
	public boolean isMigrated() {
		return _isMigrated;
	}

	/**
	 * Returns the isReported.
	 * @return boolean
	 */
	public boolean isReported() {
		return _isReported;
	}

	/**
	 * Returns the lesionCells.
	 * @return int
	 */
	public int getLesionCells() {
		return _lesionCells;
	}

	/**
	 * Returns the lesions.
	 * @return List
	 */
	public List getLesions() {
		return _lesions;
	}

	/**
	 * Returns the microscopicAppearance.
	 * @return String
	 */
	public String getMicroscopicAppearance() {
		return _microscopicAppearance;
	}

	/**
	 * Returns the necrosisCells.
	 * @return int
	 */
	public int getNecrosisCells() {
		return _necrosisCells;
	}

	/**
	 * Returns the normalCells.
	 * @return int
	 */
	public int getNormalCells() {
		return _normalCells;
	}

	/**
	 * Returns the diagnosisOther.
	 * @return String
	 */
	public String getDiagnosisOther() {
		return _diagnosisOther;
	}

	/**
	 * Returns the tissueOfFindingOther.
	 * @return String
	 */
	public String getTissueOfFindingOther() {
		return _tissueOfFindingOther;
	}

	/**
	 * Returns the tissueOfOriginOther.
	 * @return String
	 */
	public String getTissueOfOriginOther() {
		return _tissueOfOriginOther;
	}

	/**
	 * Returns the pathologist.
	 * @return String
	 */
	public String getPathologist() {
		return _pathologist;
	}

	/**
	 * Returns the sampleId.
	 * @return String
	 */
	public String getSampleId() {
		return _sampleId;
	}

	/**
	 * Returns the slideId.
	 * @return String
	 */
	public String getSlideId() {
		return _slideId;
	}

	/**
	 * Returns the sourceEvaluationId.
	 * @return String
	 */
	public String getSourceEvaluationId() {
		return _sourceEvaluationId;
	}

	/**
	 * Returns the structures.
	 * @return List
	 */
	public List getStructures() {
		return _structures;
	}

	/**
	 * Returns the tissueOfFinding.
	 * @return String
	 */
	public String getTissueOfFinding() {
		return _tissueOfFinding;
	}

	/**
	 * Returns the tissueOfOrigin.
	 * @return String
	 */
	public String getTissueOfOrigin() {
		return _tissueOfOrigin;
	}

	/**
	 * Returns the tumorCells.
	 * @return int
	 */
	public int getTumorCells() {
		return _tumorCells;
	}

	/**
	 * Returns the tumorFeatures.
	 * @return List
	 */
	public List getTumorFeatures() {
		return _tumorFeatures;
	}

	/**
	 * Sets the cellularStromaCells.
	 * @param cellularStromaCells The cellularStromaCells to set
	 */
	public void setCellularStromaCells(int cellularStromaCells) {
		_cellularStromaCells = cellularStromaCells;
	}

	/**
	 * Sets the cellularStromaFeatures.
	 * @param cellularStromaFeatures The cellularStromaFeatures to set
	 */
	public void setCellularStromaFeatures(List cellularStromaFeatures) {
		_cellularStromaFeatures = cellularStromaFeatures;
	}

	/**
	 * Sets the concatenatedExternalData.
	 * @param concatenatedExternalData The concatenatedExternalData to set
	 */
	public void setConcatenatedExternalData(String concatenatedExternalData) {
		_concatenatedExternalData = concatenatedExternalData;
	}

	/**
	 * Sets the concatenatedInternalData.
	 * @param concatenatedInternalData The concatenatedInternalData to set
	 */
	public void setConcatenatedInternalData(String concatenatedInternalData) {
		_concatenatedInternalData = concatenatedInternalData;
	}

	/**
	 * Sets the createMethod.
	 * @param createMethod The createMethod to set
	 */
	public void setCreateMethod(String createMethod) {
		_createMethod = createMethod;
	}

	/**
	 * Sets the creationDate.
	 * @param creationDate The creationDate to set
	 */
	public void setCreationDate(Timestamp creationDate) {
		_creationDate = creationDate;
	}

	/**
	 * Sets the diagnosis.
	 * @param diagnosis The diagnosis to set
	 */
	public void setDiagnosis(String diagnosis) {
		_diagnosis = diagnosis;
	}

	/**
	 * Sets the evaluationId.
	 * @param evaluationId The evaluationId to set
	 */
	public void setEvaluationId(String evaluationId) {
		_evaluationId = evaluationId;
	}

	/**
	 * Sets the externalFeatures.
	 * @param externalFeatures The externalFeatures to set
	 */
	public void setExternalFeatures(List externalFeatures) {
		_externalFeatures = externalFeatures;
	}

	/**
	 * Sets the hypoacellularStromaCells.
	 * @param hypoacellularStromaCells The hypoacellularStromaCells to set
	 */
	public void setHypoacellularStromaCells(int hypoacellularStromaCells) {
		_hypoacellularStromaCells = hypoacellularStromaCells;
	}

	/**
	 * Sets the hypoacellularStromaFeatures.
	 * @param hypoacellularStromaFeatures The hypoacellularStromaFeatures to set
	 */
	public void setHypoacellularStromaFeatures(List hypoacellularStromaFeatures) {
		_hypoacellularStromaFeatures = hypoacellularStromaFeatures;
	}

	/**
	 * Sets the inflammations.
	 * @param inflammations The inflammations to set
	 */
	public void setInflammations(List inflammations) {
		_inflammations = inflammations;
	}

	/**
	 * Sets the internalFeatures.
	 * @param internalFeatures The internalFeatures to set
	 */
	public void setInternalFeatures(List internalFeatures) {
		_internalFeatures = internalFeatures;
	}

	/**
	 * Sets the isMigrated.
	 * @param isMigrated The isMigrated to set
	 */
	public void setIsMigrated(boolean isMigrated) {
		_isMigrated = isMigrated;
	}

	/**
	 * Sets the isReported.
	 * @param isReported The isReported to set
	 */
	public void setIsReported(boolean isReported) {
		_isReported = isReported;
	}

	/**
	 * Sets the lesionCells.
	 * @param lesionCells The lesionCells to set
	 */
	public void setLesionCells(int lesionCells) {
		_lesionCells = lesionCells;
	}

	/**
	 * Sets the lesions.
	 * @param lesions The lesions to set
	 */
	public void setLesions(List lesions) {
		_lesions = lesions;
	}

	/**
	 * Sets the microscopicAppearance.
	 * @param microscopicAppearance The microscopicAppearance to set
	 */
	public void setMicroscopicAppearance(String microscopicAppearance) {
		_microscopicAppearance = microscopicAppearance;
	}

	/**
	 * Sets the necrosisCells.
	 * @param necrosisCells The necrosisCells to set
	 */
	public void setNecrosisCells(int necrosisCells) {
		_necrosisCells = necrosisCells;
	}

	/**
	 * Sets the normalCells.
	 * @param normalCells The normalCells to set
	 */
	public void setNormalCells(int normalCells) {
		_normalCells = normalCells;
	}

	/**
	 * Sets the diagnosisOther.
	 * @param diagnosisOther The diagnosisOther to set
	 */
	public void setDiagnosisOther(String diagnosisOther) {
		_diagnosisOther = diagnosisOther;
	}

	/**
	 * Sets the tissueOfFindingOther.
	 * @param tissueOfFindingOther The tissueOfFindingOther to set
	 */
	public void setTissueOfFindingOther(String tissueOfFindingOther) {
		_tissueOfFindingOther = tissueOfFindingOther;
	}

	/**
	 * Sets the tissueOfOriginOther.
	 * @param otherTissueOfOrigin The otherTissueOfOrigin to set
	 */
	public void setTissueOfOriginOther(String tissueOfOriginOther) {
		_tissueOfOriginOther = tissueOfOriginOther;
	}

	/**
	 * Sets the pathologist.
	 * @param pathologist The pathologist to set
	 */
	public void setPathologist(String pathologist) {
		_pathologist = pathologist;
	}

	/**
	 * Sets the sampleId.
	 * @param sampleId The sampleId to set
	 */
	public void setSampleId(String sampleId) {
		_sampleId = sampleId;
	}

	/**
	 * Sets the slideId.
	 * @param slideId The slideId to set
	 */
	public void setSlideId(String slideId) {
		_slideId = slideId;
	}

	/**
	 * Sets the sourceEvaluationId.
	 * @param sourceEvaluationId The sourceEvaluationId to set
	 */
	public void setSourceEvaluationId(String sourceEvaluationId) {
		_sourceEvaluationId = sourceEvaluationId;
	}

	/**
	 * Sets the structures.
	 * @param structures The structures to set
	 */
	public void setStructures(List structures) {
		_structures = structures;
	}

	/**
	 * Sets the tissueOfFinding.
	 * @param tissueOfFinding The tissueOfFinding to set
	 */
	public void setTissueOfFinding(String tissueOfFinding) {
		_tissueOfFinding = tissueOfFinding;
	}

	/**
	 * Sets the tissueOfOrigin.
	 * @param tissueOfOrigin The tissueOfOrigin to set
	 */
	public void setTissueOfOrigin(String tissueOfOrigin) {
		_tissueOfOrigin = tissueOfOrigin;
	}

	/**
	 * Sets the tumorCells.
	 * @param tumorCells The tumorCells to set
	 */
	public void setTumorCells(int tumorCells) {
		_tumorCells = tumorCells;
	}

	/**
	 * Sets the tumorFeatures.
	 * @param tumorFeatures The tumorFeatures to set
	 */
	public void setTumorFeatures(List tumorFeatures) {
		_tumorFeatures = tumorFeatures;
	}
	
	//private method used to concatenate internal and external comments
	private String concatenateComments(String type) {
		StringBuffer resultBuffer = new StringBuffer(250);
		String result = "";
		String seperator = "; ";
		//concatenate internal comments.  These should be concatenated in the following order:
		//1) choices selected from the listbox (i.e. code is not other)
		//2) free form entries (i.e. code is other)
		//all choices should be seperated by ;
		if (type.equalsIgnoreCase("internal")) {
			List internalComments = getInternalFeatures();
			if (internalComments != null && !internalComments.isEmpty()) {
				boolean otherHasValues = false;
				boolean nonOtherHasValues = false;
				StringBuffer nonOther = new StringBuffer(250);
				StringBuffer other = new StringBuffer(250);
				Iterator iterator = internalComments.iterator();
				while (iterator.hasNext()) {
					PathologyEvaluationFeatureData feature = (PathologyEvaluationFeatureData)iterator.next();
					String text = feature.getFeatureName();
					if (feature.getCode().equals(LimsConstants.OTHER_INTERNAL)) {
						if (otherHasValues) {
							other.append(seperator);
						}
						other.append(text);
						otherHasValues = true;
					}
					else {
						if (nonOtherHasValues) {
							nonOther.append(seperator);
						}
						nonOther.append(text);
						nonOtherHasValues = true;
					}
				}
				//now bring everything together.  If we have nonOther features they go first.  If we're
				//going to then append other features, include a seperator.
				if (nonOtherHasValues) {
					resultBuffer.append(nonOther.toString());
					if (otherHasValues) {
						resultBuffer.append(seperator);
					}
				}
				//append the other features to whatever we've already got.
				if (otherHasValues) {
					resultBuffer.append(other.toString());
				}		
			}
			result = resultBuffer.toString();
		}
		//concatenate external comments.  These should be concatenated in the following order:
		//1) tumor
		//2) cellular
		//3) hypoacellular
		//4) lesion
		//5) inflammation
		//6) structures
		//7) external comments
		//if any category is empty, it shouldn't be included in the concatenation.  all categories
		//should be seperated by ";", and within a category items should be seperated by ","
		else {
			boolean tumorHasValues = false;
			StringBuffer tumorBuffer = new StringBuffer(250);
			boolean cellularHasValues = false;
			StringBuffer cellularBuffer = new StringBuffer(250);
			boolean hypoAcellularHasValues = false;
			StringBuffer hypoAcellularBuffer = new StringBuffer(250);
			boolean lesionHasValues = false;
			StringBuffer lesionBuffer = new StringBuffer(250);
			boolean inflammationHasValues = false;
			StringBuffer inflammationBuffer = new StringBuffer(250);
			boolean structureHasValues = false;
			StringBuffer structureBuffer = new StringBuffer(250);
			boolean externalCommentsHasValues = false;
			StringBuffer externalCommentsBuffer = new StringBuffer(250);
			String itemSeperator = ", ";
			List features;
			//do the tumors
			features = getTumorFeatures();
			if (features != null && !features.isEmpty()) {
				Iterator iterator = features.iterator();
        tumorBuffer.append(LimsConstants.LIMS_PV_EXT_COMMENT_HEADER_TUMOR);
				while (iterator.hasNext()) {
					PathologyEvaluationFeatureData feature = (PathologyEvaluationFeatureData)iterator.next();
					if (tumorHasValues) {
						tumorBuffer.append(itemSeperator);
					}
					String text = feature.getFeatureName();
					tumorBuffer.append(text);
					tumorHasValues = true;
				}
			}
			//do the cellular stromas
			features = getCellularStromaFeatures();
			if (features != null && !features.isEmpty()) {
				Iterator iterator = features.iterator();
        cellularBuffer.append(LimsConstants.LIMS_PV_EXT_COMMENT_HEADER_CELLULAR);
				while (iterator.hasNext()) {
					PathologyEvaluationFeatureData feature = (PathologyEvaluationFeatureData)iterator.next();
					if (cellularHasValues) {
						cellularBuffer.append(itemSeperator);
					}
					String text = feature.getFeatureName();
					cellularBuffer.append(text);
					cellularHasValues = true;
				}
			}
			//do the hypoacellular stromas
			features = getHypoacellularStromaFeatures();
			if (features != null && !features.isEmpty()) {
				Iterator iterator = features.iterator();
        hypoAcellularBuffer.append(LimsConstants.LIMS_PV_EXT_COMMENT_HEADER_HYPOACELLULAR);
				while (iterator.hasNext()) {
					PathologyEvaluationFeatureData feature = (PathologyEvaluationFeatureData)iterator.next();
					if (hypoAcellularHasValues) {
						hypoAcellularBuffer.append(itemSeperator);
					}
					String text = feature.getFeatureName();
					hypoAcellularBuffer.append(text);
					hypoAcellularHasValues = true;
				}
			}
			//do the lesions
			features = getLesions();
			if (features != null && !features.isEmpty()) {
				Iterator iterator = features.iterator();
        lesionBuffer.append(LimsConstants.LIMS_PV_EXT_COMMENT_HEADER_LESION);
				lesionBuffer.append("(");
				lesionBuffer.append(getLesionCells());
				lesionBuffer.append("%): ");
				while (iterator.hasNext()) {
					PathologyEvaluationFeatureData feature = (PathologyEvaluationFeatureData)iterator.next();
					if (lesionHasValues) {
						lesionBuffer.append(itemSeperator);
					}
					String text = feature.getFeatureName();
					lesionBuffer.append(text);
					lesionBuffer.append(" ");
					lesionBuffer.append(feature.getValue());
					lesionBuffer.append("%");
					lesionHasValues = true;
				}
			}
			//do the inflammations
			features = getInflammations();
			if (features != null && !features.isEmpty()) {
				Iterator iterator = features.iterator();
        inflammationBuffer.append(LimsConstants.LIMS_PV_EXT_COMMENT_HEADER_INFLAMMATION);
				while (iterator.hasNext()) {
					PathologyEvaluationFeatureData feature = (PathologyEvaluationFeatureData)iterator.next();
					if (inflammationHasValues) {
						inflammationBuffer.append(itemSeperator);
					}
					int value = feature.getValue();
					if (value == LimsConstants.INFLAMMATION_MILD) {
						inflammationBuffer.append(LimsConstants.INFLAMMATION_MILD_TEXT);
					}
					else if (value == LimsConstants.INFLAMMATION_MODERATE) {
						inflammationBuffer.append(LimsConstants.INFLAMMATION_MODERATE_TEXT);
					}
					else if (value == LimsConstants.INFLAMMATION_SEVERE) {
						inflammationBuffer.append(LimsConstants.INFLAMMATION_SEVERE_TEXT);
					}
					else {
						inflammationBuffer.append("Unknown severity");
					}
					inflammationBuffer.append(" ");
					String text = feature.getFeatureName();
					inflammationBuffer.append(text);
					inflammationHasValues = true;
				}
			}
			//do the structures
			features = getStructures();
			if (features != null && !features.isEmpty()) {
				Iterator iterator = features.iterator();
        structureBuffer.append(LimsConstants.LIMS_PV_EXT_COMMENT_HEADER_STRUCTURES);
				
				while (iterator.hasNext()) {
					PathologyEvaluationFeatureData feature = (PathologyEvaluationFeatureData)iterator.next();
					if (structureHasValues) {
						structureBuffer.append(itemSeperator);
					}
					String text = feature.getFeatureName();
					structureBuffer.append(feature.getValue());
					structureBuffer.append("% ");
					structureBuffer.append(text);
					structureHasValues = true;
				}
			}
			//do the external comments.  Append the nonOther comments first, and then the
			//other comments
			features = getExternalFeatures();
			if (features != null && !features.isEmpty()) {
				boolean otherHasValues = false;
				boolean nonOtherHasValues = false;
				//MR5393 - only include the "Other Features/Comments" prefix if there are
				//non-external comment features on the evaluation.  If the only features on 
				//the evaluation are external comment features then don't include this prefix
				if (tumorHasValues || cellularHasValues || hypoAcellularHasValues ||
					lesionHasValues || inflammationHasValues || structureHasValues) {
          externalCommentsBuffer.append(LimsConstants.LIMS_PV_EXT_COMMENT_HEADER_OTHERS);
				}
				StringBuffer nonOther = new StringBuffer(250);
				StringBuffer other = new StringBuffer(250);
				Iterator iterator = features.iterator();
				while (iterator.hasNext()) {
					PathologyEvaluationFeatureData feature = (PathologyEvaluationFeatureData)iterator.next();
					String code = feature.getCode();
					String text = feature.getFeatureName();
					if (code.equals(LimsConstants.OTHER_EXTERNAL)) {
						if (otherHasValues) {
							other.append(itemSeperator);
						}
						other.append(text);
						otherHasValues = true;
					}
					else {
						if (nonOtherHasValues) {
							nonOther.append(itemSeperator);
						}
						nonOther.append(text);
						nonOtherHasValues = true;
					}
				}
				//now bring everything together.  If we have nonOther features they go first.  If we're
				//going to then append other features, include a seperator.
				if (nonOtherHasValues) {
					externalCommentsHasValues = true;
					externalCommentsBuffer.append(nonOther.toString());
					if (otherHasValues) {
						externalCommentsBuffer.append(itemSeperator);
					}
				}
				//append the other features to whatever we've already got.
				if (otherHasValues) {
					externalCommentsHasValues = true;
					externalCommentsBuffer.append(other.toString());
				}		
			}
			//now bring all the external stuff together
			boolean seperatorNeeded = false;			
			if (tumorHasValues) {
				resultBuffer.append(tumorBuffer.toString());
				seperatorNeeded = true;
			}
			if (cellularHasValues) {
				if (seperatorNeeded) {
					resultBuffer.append(seperator);
				}
				resultBuffer.append(cellularBuffer.toString());
				seperatorNeeded = true;
			}
			if (hypoAcellularHasValues) {
				if (seperatorNeeded) {
					resultBuffer.append(seperator);
				}
				resultBuffer.append(hypoAcellularBuffer.toString());
				seperatorNeeded = true;
			}
			if (lesionHasValues) {
				if (seperatorNeeded) {
					resultBuffer.append(seperator);
				}
				resultBuffer.append(lesionBuffer.toString());
				seperatorNeeded = true;
			}
			if (inflammationHasValues) {
				if (seperatorNeeded) {
					resultBuffer.append(seperator);
				}
				resultBuffer.append(inflammationBuffer.toString());
				seperatorNeeded = true;
			}
			if (structureHasValues) {
				if (seperatorNeeded) {
					resultBuffer.append(seperator);
				}
				resultBuffer.append(structureBuffer.toString());
				seperatorNeeded = true;
			}
			if (externalCommentsHasValues) {
				if (seperatorNeeded) {
					resultBuffer.append(seperator);
				}
				resultBuffer.append(externalCommentsBuffer.toString());
				seperatorNeeded = true;
			}
			result = resultBuffer.toString();
		}
		return result;
	}
		
	/** Returns the diagnosis name */
	public String getDiagnosisName() {
		String returnValue = "";
		String code = getDiagnosis();
		if (code == null || code.equals("")) {
			throw new ApiException("PathologyEvaluationData.getDiagnosisName() called on blank diagnosis.");
		}
		if (code.equals(FormLogic.OTHER_DX)) {
			returnValue = getDiagnosisOther();
		}
		else {
			returnValue = BigrGbossData.getDiagnosisDescription(code);
		}
		return returnValue;
	}
		
	/** Returns the tissue of origin name */
	public String getTissueOfOriginName() {
		String returnValue = "";
		String code = getTissueOfOrigin();
		if (code == null || code.equals("")) {
			throw new ApiException("PathologyEvaluationData.getTissueOfOriginName() called on blank tissue of origin.");
		}
		if (code.equals(FormLogic.OTHER_TISSUE)) {
			returnValue = getTissueOfOriginOther();
		}
		else {
			returnValue = BigrGbossData.getTissueDescription(code);
		}
		return returnValue;
	}
		
	/** Returns the tissue of finding name */
	public String getTissueOfFindingName() {
		String returnValue = "";
		String code = getTissueOfFinding();
		if (code == null || code.equals("")) {
			throw new ApiException("PathologyEvaluationData.getTissueOfFindingName() called on blank tissue of finding.");
		}
		if (code.equals(FormLogic.OTHER_TISSUE)) {
			returnValue = getTissueOfFindingOther();
		}
		else {
			returnValue = BigrGbossData.getTissueDescription(code);
		}
		return returnValue;
	}
	
	/** Returns the microscopic appearance name */
	public String getMicroscopicAppearanceName() {
		if (_microscopicAppearance == null) {
			return "";
		}
		else {
			return FormLogic.lookupAppearance(_microscopicAppearance);
		}
	}


	/**
	 * Returns the reportedDate.
	 * @return Date
	 */
	public Timestamp getReportedDate() {
		return _reportedDate;
	}

	/**
	 * Sets the reportedDate.
	 * @param reportedDate The reportedDate to set
	 */
	public void setReportedDate(Timestamp reportedDate) {
		_reportedDate = reportedDate;
	}

}
