package com.ardais.bigr.pdc.javabeans;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtilsBean;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.beanutils.BigrBeanUtilsBean;
import com.ardais.bigr.btx.framework.BtxHistoryAttributes;
import com.ardais.bigr.btx.framework.DescribableAsHistoryObject;
import com.ardais.bigr.iltds.helpers.FormLogic;
import com.ardais.bigr.orm.helpers.BigrGbossData;
import com.ardais.bigr.util.gen.DbAliases;
import com.gulfstreambio.gboss.GbossFactory;

/**
 * This class represents the data for a pathology report section.
 */
public class PathReportSectionData implements Serializable, DescribableAsHistoryObject {
	private String _pathReportSectionId;
	private String _consentId;
	private String _cellInfiltrateAmount;
	private String _createUser;
	private String _diagnosis;
	private String _diagnosisOther;
	private String _distantMetastasis;
	private String _distantMetastasisInd;
	private String _distantMetastasisOther;
	private String _extensiveIntraductalComp;
	private String _extranodalSpread;
	private Integer _gleasonPrimary;
	private Integer _gleasonSecondary;
	private Integer _gleasonTotal;
	private String _histologicalNuclearGrade;
	private String _histologicalNuclearGradeOther;
	private String _histologicalType;
	private String _histologicalTypeOther;
	private String _inflammCellInfiltrate;
	private String _jointComponent;
	private String _lastUpdateUser;
	private String _lymphaticVascularInvasion;
	private String _lymphNodeNotes;
	private String _lymphNodeStage;
	private String _lymphNodeStageOther;
	private String _lymphNodeStageInd;
	private String _marginsInvolved;
	private String _marginsUninvolved;
	private String _pathReportId;
	private String _perineuralInvasion;
	private String _primary;
	private String _sectionIdentifier;
	private String _sectionNotes;
	private String _sizeLargestNodalMets;
	private String _stageGrouping;
	private String _stageGroupingOther;
	private String _tissueFinding;
	private String _tissueFindingOther;
	private String _tissueOrigin;
	private String _tissueOriginOther;
	private String _totalNodesPositive;
	private String _totalNodesExamined;
	private String _tumorConfig;
	private String _tumorSize;
	private String _tumorStageDesc;
	private String _tumorStageDescInd;
	private String _tumorStageDescOther;
	private String _tumorStageType;
	private String _tumorStageTypeOther;
	private String _tumorWeight;
	private String _venousVesselInvasion;

	private List _findings;

	
/**
 * Creates a new, empty <code>PathReportSectionData</code>.
 */
public PathReportSectionData() {
	super();
}
  
/**
 * Creates a new <code>PathReportSectionData</code>, initialized from
 * the data in the PathReportSectionData passed in.
 *
 * @param  pathReportSectionData  the <code>PathReportSectionData</code>
 */
public PathReportSectionData(PathReportSectionData pathReportSectionData) {
  this();
  BigrBeanUtilsBean.SINGLETON.copyProperties(this, pathReportSectionData);
  //any mutable objects must be handled seperately (BigrBeanUtilsBean.copyProperties
  //does a shallow copy.
  if (!ApiFunctions.isEmpty(pathReportSectionData.getFindings())) {
    _findings.clear();
    Iterator iterator = pathReportSectionData.getFindings().iterator();
    while (iterator.hasNext()) {
      _findings.add(new PathReportSectionFindingData((PathReportSectionFindingData)iterator.next()));
    }
  }
}

/**
 * Creates a new <code>PathReportSectionData</code>, initialized from
 * the data in the current row of the result set.
 */
public PathReportSectionData(ResultSet rs) {
    this();
    try {
        ResultSetMetaData meta = rs.getMetaData();
        int columnCount = meta.getColumnCount();
        HashMap lookup = new HashMap();

        for (int i = 0; i < columnCount; i++) {
            lookup.put(meta.getColumnName(i + 1).toLowerCase(), null);
        }

        if (lookup.containsKey("cell_infiltrate_amt"))
        	setCellInfiltrateAmount(rs.getString("cell_infiltrate_amt"));
        
       
        if (lookup.containsKey("consent_id"))
	        setConsentId(rs.getString("consent_id"));
        
        if (lookup.containsKey("create_user"))
        	setCreateUser(rs.getString("create_user"));
        
        if (lookup.containsKey("diagnosis_concept_id"))
	        setDiagnosis(rs.getString("diagnosis_concept_id"));
        
        if (lookup.containsKey("distant_metastasis"))
	        setDistantMetastasis(rs.getString("distant_metastasis"));
        
        if (lookup.containsKey("distant_metastasis_ind"))
	        setDistantMetastasisInd(rs.getString("distant_metastasis_ind"));
        
        if (lookup.containsKey("extensive_intraductal_comp"))
	        setExtensiveIntraductalComp(rs.getString("extensive_intraductal_comp"));
        
        if (lookup.containsKey("extranodal_spread"))
	        setExtranodalSpread(rs.getString("extranodal_spread"));
        
        if (lookup.containsKey("histological_nuclear_grade"))
	        setHistologicalNuclearGrade(rs.getString("histological_nuclear_grade"));
        
        if (lookup.containsKey("histological_type"))
	        setHistologicalType(rs.getString("histological_type"));
        
        if (lookup.containsKey("inflamm_cell_infiltrate"))
	        setInflammCellInfiltrate(rs.getString("inflamm_cell_infiltrate"));
        
        if (lookup.containsKey("joint_component"))
	        setJointComponent(rs.getString("joint_component"));
        
        if (lookup.containsKey("last_update_user"))
	        setLastUpdateUser(rs.getString("last_update_user"));
        
        if (lookup.containsKey("lymph_node_notes"))
	        setLymphNodeNotes(rs.getString("lymph_node_notes"));
        
        if (lookup.containsKey("lymph_node_stage"))
	        setLymphNodeStage(rs.getString("lymph_node_stage"));
        
        if (lookup.containsKey("lymph_node_stage_ind"))
	        setLymphNodeStageInd(rs.getString("lymph_node_stage_ind"));
        
        if (lookup.containsKey("lymphatic_vascular_invasion"))
	        setLymphaticVascularInvasion(rs.getString("lymphatic_vascular_invasion"));
        
        if (lookup.containsKey("margins_involved_desc"))
	        setMarginsInvolved(rs.getString("margins_involved_desc"));
        
        if (lookup.containsKey("margins_uninvolved_desc"))
	        setMarginsUninvolved(rs.getString("margins_uninvolved_desc"));

	    if (lookup.containsKey("other_distant_metastasis"))
        	setDistantMetastasisOther(rs.getString("other_distant_metastasis"));
        
        if (lookup.containsKey("other_dx_name"))
	        setDiagnosisOther(rs.getString("other_dx_name"));
	         
        if (lookup.containsKey("other_finding_tc_name"))
        	setTissueFindingOther(rs.getString("other_finding_tc_name"));

        if (lookup.containsKey("other_histo_nuclear_grade"))
	        setHistologicalNuclearGradeOther(rs.getString("other_histo_nuclear_grade"));
        
        if (lookup.containsKey("other_histological_type"))
	        setHistologicalTypeOther(rs.getString("other_histological_type"));
        
        if (lookup.containsKey("other_lymph_node_stage"))
        	setLymphNodeStageOther(rs.getString("other_lymph_node_stage"));

        if (lookup.containsKey("other_origin_tc_name"))
        	setTissueOriginOther(rs.getString("other_origin_tc_name"));
        
        if (lookup.containsKey("other_stage_grouping"))
        	setStageGroupingOther(rs.getString("other_stage_grouping"));

        if (lookup.containsKey("other_tumor_stage_desc"))
        	setTumorStageDescOther(rs.getString("other_tumor_stage_desc"));

        if (lookup.containsKey("other_tumor_stage_type"))
        	setTumorStageTypeOther(rs.getString("other_tumor_stage_type"));
        
        if (lookup.containsKey("path_report_id"))
        	setPathReportId(rs.getString("path_report_id"));
        
        if (lookup.containsKey("path_report_section_id"))
        	setPathReportSectionId(rs.getString("path_report_section_id"));

        if (lookup.containsKey("path_section_notes"))
            setSectionNotes(ApiFunctions.getStringFromClob(rs.getClob("path_section_notes")));
        
        if (lookup.containsKey("perineural_invasion_ind"))
	        setPerineuralInvasion(rs.getString("perineural_invasion_ind"));
        
        if (lookup.containsKey("primary_gleason_score")) {
	        String score = rs.getString("primary_gleason_score");
	        if (score != null) 
	        	setGleasonPrimary(new Integer(score));
        }
        
        if (lookup.containsKey("primary_ind"))
        	setPrimary(rs.getString("primary_ind"));
        
        if (lookup.containsKey("secondary_gleason_score")) {
	        String score = rs.getString("secondary_gleason_score");
	        if (score != null)
	        	setGleasonSecondary(new Integer(score));
        }
        
        if (lookup.containsKey("section_identifier"))
        	setSectionIdentifier(rs.getString("section_identifier"));
        
        if (lookup.containsKey("size_largest_nodal_mets"))
        	setSizeLargestNodalMets(rs.getString("size_largest_nodal_mets"));

        if (lookup.containsKey("stage_grouping"))
        	setStageGrouping(rs.getString("stage_grouping"));

        if (lookup.containsKey("tissue_finding_concept_id"))
	        setTissueFinding(rs.getString("tissue_finding_concept_id"));
        
        if (lookup.containsKey("tissue_origin_concept_id"))
        	setTissueOrigin(rs.getString("tissue_origin_concept_id"));
        
        if (lookup.containsKey("total_gleason_score")) {
	        String score = rs.getString("total_gleason_score");
	        if (score != null) 
	        	setGleasonTotal(new Integer(score));
        }
        
        if (lookup.containsKey("total_nodes_examined"))
        	setTotalNodesExamined(rs.getString("total_nodes_examined"));

        if (lookup.containsKey("total_nodes_positive"))
        	setTotalNodesPositive(rs.getString("total_nodes_positive"));
        
        if (lookup.containsKey("tumor_configuration"))
        	setTumorConfiguration(rs.getString("tumor_configuration"));
        
        if (lookup.containsKey("tumor_size"))
        	setTumorSize(rs.getString("tumor_size"));
        
        if (lookup.containsKey("tumor_stage_desc"))
        	setTumorStageDesc(rs.getString("tumor_stage_desc"));
        
        if (lookup.containsKey("tumor_stage_desc_ind"))
        	setTumorStageDescInd(rs.getString("tumor_stage_desc_ind"));
        
        if (lookup.containsKey("tumor_stage_type"))
        	setTumorStageType(rs.getString("tumor_stage_type"));
        
        if (lookup.containsKey("tumor_weight"))
        	setTumorWeight(rs.getString("tumor_weight"));
        
        if (lookup.containsKey("venous_vessel_invasion"))
        	setVenousVesselInvasion(rs.getString("venous_vessel_invasion"));        
        
    } catch (SQLException e) {
        throw new ApiException(e);
    }
}
	
/**
 * Creates a new, populated <code>PathReportSectionData</code>.
 */
public PathReportSectionData(Map columns, ResultSet rs) {
    this();
    populate(columns, rs);
}
/**
 * Creates a new, populated <code>PathReportSectionData</code>.
 */
public PathReportSectionData(BtxHistoryAttributes attributes) {
    this();
    populate(attributes);
}

	/**
	 * Populates this <code>PathReportSectionData</code> from the data in the 
	 * current row of the result set.
	 * 
	 * @param  columns  a <code>Map</code> containing a key for each column in
	 * 									 the <code>ResultSet</code>.  Each key must be one of the
	 * 									 column alias constants in {@link com.ardais.bigr.util.DbAliases}
	 * 									 and the corresponding value is ignored.
	 * @param  rs  the <code>ResultSet</code>
	 */
	public void populate(Map columns, ResultSet rs) {
    try {
	    if (columns.containsKey(DbAliases.SECTION_DX)) {
	    	setDiagnosis(rs.getString(DbAliases.SECTION_DX));
	    }
	    if (columns.containsKey(DbAliases.SECTION_DX_OTHER)) {
	    	setDiagnosisOther(rs.getString(DbAliases.SECTION_DX_OTHER));
	    }
	    if (columns.containsKey(DbAliases.SECTION_HNG)) {
	    	setHistologicalNuclearGrade(rs.getString(DbAliases.SECTION_HNG));
	    }
	    if (columns.containsKey(DbAliases.SECTION_ID)) {
	    	setPathReportSectionId(rs.getString(DbAliases.SECTION_ID));
	    }
	    if (columns.containsKey(DbAliases.SECTION_GLEASON_PRIMARY)) {
	    	if (rs.getString(DbAliases.SECTION_GLEASON_PRIMARY) != null) {
		    	setGleasonPrimary(new Integer(rs.getInt(DbAliases.SECTION_GLEASON_PRIMARY)));
	    	}
	    }
	    if (columns.containsKey(DbAliases.SECTION_GLEASON_SECONDARY)) {
	    	if (rs.getString(DbAliases.SECTION_GLEASON_SECONDARY) != null) {
		    	setGleasonSecondary(new Integer(rs.getInt(DbAliases.SECTION_GLEASON_SECONDARY)));
	    	}
	    }
	    if (columns.containsKey(DbAliases.SECTION_GLEASON_TOTAL)) {
	    	if (rs.getString(DbAliases.SECTION_GLEASON_TOTAL) != null) {
		    	setGleasonTotal(new Integer(rs.getInt(DbAliases.SECTION_GLEASON_TOTAL)));
	    	}
	    }
	    if (columns.containsKey(DbAliases.SECTION_LYMPH_STAGE)) {
	    	setLymphNodeStage(rs.getString(DbAliases.SECTION_LYMPH_STAGE));
	    }
	    if (columns.containsKey(DbAliases.SECTION_METASTASIS)) {
	    	setDistantMetastasis(rs.getString(DbAliases.SECTION_METASTASIS));
	    }
	    if (columns.containsKey(DbAliases.SECTION_STAGE_GROUPING)) {
	    	setStageGrouping(rs.getString(DbAliases.SECTION_STAGE_GROUPING));
	    }
	    if (columns.containsKey(DbAliases.SECTION_TISSUE_FINDING)) {
	    	setTissueFinding(rs.getString(DbAliases.SECTION_TISSUE_FINDING));
	    }
	    if (columns.containsKey(DbAliases.SECTION_TISSUE_FINDING_OTHER)) {
	    	setTissueFindingOther(rs.getString(DbAliases.SECTION_TISSUE_FINDING_OTHER));
	    }
	    if (columns.containsKey(DbAliases.SECTION_TISSUE_ORIGIN)) {
	    	setTissueOrigin(rs.getString(DbAliases.SECTION_TISSUE_ORIGIN));
	    }
	    if (columns.containsKey(DbAliases.SECTION_TISSUE_ORIGIN_OTHER)) {
	    	setTissueOriginOther(rs.getString(DbAliases.SECTION_TISSUE_ORIGIN_OTHER));
	    }
	    if (columns.containsKey(DbAliases.SECTION_TUMOR_STAGE)) {
	    	setTumorStageDesc(rs.getString(DbAliases.SECTION_TUMOR_STAGE));
	    }
	    if (columns.containsKey(DbAliases.SECTION_TUMOR_STAGE_TYPE)) {
	    	setTumorStageType(rs.getString(DbAliases.SECTION_TUMOR_STAGE_TYPE));
	    }
	    if (columns.containsKey(DbAliases.SECTION_PRIMARY_IND)) {
	    	setPrimary(rs.getString(DbAliases.SECTION_PRIMARY_IND));
	    }
	    if (columns.containsKey(DbAliases.SECTION_PATH_REPORT_ID)) {
	    	setPathReportId(rs.getString(DbAliases.SECTION_PATH_REPORT_ID));
	    }
	    if (columns.containsKey(DbAliases.SECTION_TUMOR_SIZE)) {
	    	setTumorSize(rs.getString(DbAliases.SECTION_TUMOR_SIZE));
	    }
	    if (columns.containsKey(DbAliases.SECTION_TUMOR_WEIGHT)) {
	    	setTumorWeight(rs.getString(DbAliases.SECTION_TUMOR_WEIGHT));
	    }
    } catch (SQLException e) {
			ApiFunctions.throwAsRuntimeException(e);
    }
	}
  
  /**
   * Populates this <code>PathReportSectionData</code> from the data in the 
   * <code>BtxHistoryAttributes</code> object.
   * 
   * @param  attributes  a <code>BtxHistoryAttributes</code>.
   */
  public void populate(BtxHistoryAttributes attributes) {
    if (attributes == null) {
      return;
    }
    else {
      try {
        Map map = (Map) attributes.asMap();
        Iterator names = map.keySet().iterator();
        PropertyUtilsBean bean = new PropertyUtilsBean();
        while (names.hasNext()) {
          String name = (String) names.next();
          if (bean.isWriteable(this, name)) {
            Class[] params = bean.getPropertyDescriptor(this, name).getWriteMethod().getParameterTypes();
            if ("java.lang.String".equalsIgnoreCase(((Class)params[0]).getName())) {
              bean.setSimpleProperty(this, name, map.get(name));
            }
            else if ("java.lang.Integer".equalsIgnoreCase(((Class)params[0]).getName())) {
              bean.setSimpleProperty(this, name, new Integer((String)map.get(name)));
            }
            else {
              throw new ApiException("Unexpected class " + ((Class)params[0]).getName() + "encountered.");
            }
          }
        }
      }
      catch (Exception e) {
        throw new ApiException(e);
      }
    }
  }


/**
 * Returns this path report section's cell infiltrate amount.
 *
 * @return  the cell infiltrate amount
 */
public String getCellInfiltrateAmount() {
	return _cellInfiltrateAmount;
}

/**
 * Returns this path report section's consent id.
 *
 * @return  the consent id
 */
public String getConsentId() {
	return _consentId;
}
/**
 * Returns the user that created this path report section.
 *
 * @return  the created user
 */
public String getCreateUser() {
	return _createUser;
}
/**
 * Returns the diagnosis of this path report section.
 *
 * @return  the diagnosis
 */
public String getDiagnosis() {
	return _diagnosis;
}
/**
 * Returns this path report section's "other" diagnosis.
 *
 * @return  the "other" diagnosis
 */
public String getDiagnosisOther() {
	return _diagnosisOther;
}
/**
 * Returns this path report section's distant metastasis.
 *
 * @return  the distant metastasis
 */
public String getDistantMetastasis() {
	return _distantMetastasis;
}
/**
 * Returns this path report section's distant metastasis indicator.
 *
 * @return  the distant metastasis indicator
 */
public String getDistantMetastasisInd() {
	return _distantMetastasisInd;
}
/**
 * Returns this path report section's "other" distant metastasis.
 *
 * @return  the "other" distant metastasis
 */
public String getDistantMetastasisOther() {
	return _distantMetastasisOther;
}
/**
 * Returns this path report section's extensive intraductal comp.
 *
 * @return  the extensive intraductal comp
 */
public String getExtensiveIntraductalComp() {
	return _extensiveIntraductalComp;
}
/**
 * Returns this path report section's extranodal spread.
 *
 * @return  the extranodal spread
 */
public String getExtranodalSpread() {
	return _extranodalSpread;
}
/**
 * Returns this path report section's tumor size.
 *
 * @return  the tumor size
 */
public List getFindings() {
	return _findings;
}
/**
 * Returns this path report section's primary gleason score.
 *
 * @return  the primary gleason score
 */
public Integer getGleasonPrimary() {
	return _gleasonPrimary;
}
/**
 * Returns this path report section's secondary gleason score.
 *
 * @return  the secondary gleason score
 */
public Integer getGleasonSecondary() {
	return _gleasonSecondary;
}
/**
 * Returns this path report section's total gleason score.
 *
 * @return  the total gleason score
 */
public Integer getGleasonTotal() {
	return _gleasonTotal;
}
/**
 * Returns this path report section's histological nuclear grade.
 *
 * @return  the histological nuclear grade
 */
public String getHistologicalNuclearGrade() {
	return _histologicalNuclearGrade;
}
/**
 * Returns this path report section's "other" histological nuclear grade.
 *
 * @return  the "other" histological nuclear grade
 */
public String getHistologicalNuclearGradeOther() {
	return _histologicalNuclearGradeOther;
}
/**
 * Returns this path report section's histological type code.
 *
 * @return  the histological type code
 */
public String getHistologicalType() {
	return _histologicalType;
}
/**
 * Returns this path report section's "other" histological type.
 *
 * @return  The "other" histological type.
 */
public String getHistologicalTypeOther() {
	return _histologicalTypeOther;
}
/**
 * Returns this path report section's inflamm cell infiltrate.
 *
 * @return  the inflamm cell infiltrate.
 */
public String getInflammCellInfiltrate() {
	return _inflammCellInfiltrate;
}
/**
 * Returns this path report section's joint component.
 *
 * @return  the joint component
 */
public String getJointComponent() {
	return _jointComponent;
}
/**
 * Returns the user that last updated this path report section.
 *
 * @return  the last update user
 */
public String getLastUpdateUser() {
	return _lastUpdateUser;
}
/**
 * Returns this path report section's lymphatic vascular invasion.
 *
 * @return  the lymphatic vascular invasion
 */
public String getLymphaticVascularInvasion() {
	return _lymphaticVascularInvasion;
}
/**
 * Returns this path report section's lymph node notes.
 *
 * @return  the lymph node notes
 */
public String getLymphNodeNotes() {
	return _lymphNodeNotes;
}
/**
 * Returns this path report section's lymph node stage.
 *
 * @return  the lymph node stage
 */
public String getLymphNodeStage() {
	return _lymphNodeStage;
}
/**
 * Returns this path report section's lymph node stage indicator.
 *
 * @return  the lymph node stage indicator
 */
public String getLymphNodeStageInd() {
	return _lymphNodeStageInd;
}
/**
 * Returns this path report section's "other" lymph node stage.
 *
 * @return  the "other" lymph node stage
 */
public String getLymphNodeStageOther() {
	return _lymphNodeStageOther;
}
/**
 * Returns this path report section's involved margins.
 *
 * @return  the involved margins
 */
public String getMarginsInvolved() {
	return _marginsInvolved;
}
/**
 * Returns this path report section's uninvolved margins.
 *
 * @return  the uninvolved margins
 */
public String getMarginsUninvolved() {
	return _marginsUninvolved;
}
/**
 * Returns the id of the path report with which this path report section
 * is associated.
 *
 * @return  the path report id
 */
public String getPathReportId() {
	return _pathReportId;
}
/**
 * Returns this path report section's id.
 *
 * @return  the id
 */
public String getPathReportSectionId() {
	return _pathReportSectionId;
}
/**
 * Returns this path report section's perineural invasion.
 *
 * @return  the perineural invasion
 */
public String getPerineuralInvasion() {
	return _perineuralInvasion;
}
/**
 * Returns an indicator of wheter this path report section is the
 * primary section or not.
 *
 * @return  'Y' if this is the primary section; 'N' otherwise
 */
public String getPrimary() {
	return _primary;
}

/**
 * Returns an indicator of wheter this path report section is the
 * primary section or not.
 *
 * @return  true if this is the primary section; false otherwise
 */
public boolean isPrimarySection() {
	return ("Y".equalsIgnoreCase(getPrimary()));
}

/**
 * Returns this path report section's identifier.
 *
 * @return  the section identifier
 */
public String getSectionIdentifier() {
	return _sectionIdentifier;
}
/**
 * Returns this path report section's notes.
 *
 * @return  the section notes
 */
public String getSectionNotes() {
	return _sectionNotes;
}
/**
 * Returns this path report section's size of the largest nodal mets.
 *
 * @return  the size of the largest nodal mets
 */
public String getSizeLargestNodalMets() {
	return _sizeLargestNodalMets;
}
/**
 * Returns this path report section's stage grouping.
 *
 * @return  the stage grouping
 */
public String getStageGrouping() {
	return _stageGrouping;
}
/**
 * Returns this path report section's "other" stage grouping.
 *
 * @return  the "other" stage grouping
 */
public String getStageGroupingOther() {
	return _stageGroupingOther;
}
/**
 * Returns the finding tissue of this path report section.
 *
 * @return  the finding tissue
 */
public String getTissueFinding() {
	return _tissueFinding;
}
/**
 * Returns this path report section's "other" finding tissue.
 *
 * @return  the "other" finding tissue
 */
public String getTissueFindingOther() {
	return _tissueFindingOther;
}
/**
 * Returns the origin tissue of this path report section.
 *
 * @return  the origin tissue
 */
public String getTissueOrigin() {
	return _tissueOrigin;
}
/**
 * Returns this path report section's "other" origin tissue.
 *
 * @return  the "other" origin tissue
 */
public String getTissueOriginOther() {
	return _tissueOriginOther;
}
/**
 * Returns this path report section's total number of nodes that were examined.
 *
 * @return  the total number of nodes that are were examined
 */
public String getTotalNodesExamined() {
	return _totalNodesExamined;
}
/**
 * Returns this path report section's total number of nodes that are positive.
 *
 * @return  the total number of nodes that are positive
 */
public String getTotalNodesPositive() {
	return _totalNodesPositive;
}
/**
 * Returns this path report section's tumor configuration.
 *
 * @return  the tumor configuration
 */
public String getTumorConfig() {
	return _tumorConfig;
}
/**
 * Returns this path report section's tumor size.
 *
 * @return  the tumor size
 */
public String getTumorSize() {
	return _tumorSize;
}
/**
 * Returns this path report section's tumor stage description code.
 *
 * @return  the tumor stage description code.
 */
public String getTumorStageDesc() {
	return _tumorStageDesc;
}
/**
 * Returns this path report section's tumor stage description indicator.
 *
 * @return  the tumor stage description indicator
 */
public String getTumorStageDescInd() {
	return _tumorStageDescInd;
}
/**
 * Returns this path report section's "other" tumor stage description code.
 *
 * @return  The "other" tumor stage description.
 */
public String getTumorStageDescOther() {
	return _tumorStageDescOther;
}
/**
 * Returns this path report section's tumor stage type code.
 *
 * @return  the tumor stage type code
 */
public String getTumorStageType() {
	return _tumorStageType;
}
/**
 * Returns this path report section's "other" tumor stage type.
 *
 * @return  the "other" tumor stage type
 */
public String getTumorStageTypeOther() {
	return _tumorStageTypeOther;
}
/**
 * Returns this path report section's tumor weight.
 *
 * @return  the tumor weight
 */
public String getTumorWeight() {
	return _tumorWeight;
}
/**
 * Returns this path report section's venous vessel invasion.
 *
 * @return  the venous vessel invasion
 */
public String getVenousVesselInvasion() {
	return _venousVesselInvasion;
}
/**
 * Sets this path report section's cell infiltrate amount.
 *
 * @param  amount  the cell infiltrate amount
 */
public void setCellInfiltrateAmount(String amount) {
	_cellInfiltrateAmount = amount;
}

/**
 * Sets the consent id for this path report section.
 *
 * @param  id  the consent id
 */
public void setConsentId(String id) {
	_consentId = id;
}
/**
 * Sets the create user for this path report section.
 *
 * @param  user  the create user
 */
public void setCreateUser(String user) {
	_createUser = user;
}
/**
 * Sets this path report section's diagnosis.
 *
 * @param  dx  the diagnosis
 */
public void setDiagnosis(String dx) {
	_diagnosis = dx;
}
/**
 * Sets this path report section's "other" diagnosis.
 *
 * @param  dx  the "other" diagnosis
 */
public void setDiagnosisOther(String dx) {
	_diagnosisOther = dx;
}
/**
 * Sets this path report section's distant metastasis.
 *
 * @param  metastasis  the distant metastasis
 */
public void setDistantMetastasis(String metastasis) {
	_distantMetastasis = metastasis;
}
/**
 * Sets this path report section's distant metastasis indicator.
 *
 * @param  ind  the distant metastasis indicator
 */
public void setDistantMetastasisInd(String ind) {
	_distantMetastasisInd = ind;
}
/**
 * Sets this path report section's "other" distant metastasis.
 *
 * @param  metastasis  the "other" distant metastasis
 */
public void setDistantMetastasisOther(String metastasis) {
	_distantMetastasisOther = metastasis;
}
/**
 * Sets this path report section's extensive intraductal comp.
 *
 * @param  value  the extensive intraductal comp
 */
public void setExtensiveIntraductalComp(String value) {
	_extensiveIntraductalComp = value;
}
/**
 * Sets this path report section's extranodal spread.
 *
 * @param  value  the extranodal spread
 */
public void setExtranodalSpread(String value) {
	_extranodalSpread = value;
}
/**
 * Sets the create user for this path report section.
 *
 * @param  user  the create user
 */
public void setFindings(List findings) {
    _findings = findings;
}
/**
 * Sets this path report section's primary gleason score.
 *
 * @param  score  the primary gleason score
 */
public void setGleasonPrimary(Integer score) {
	_gleasonPrimary = score;
}
/**
 * Sets this path report section's secondary gleason score.
 *
 * @param  score  the secondary gleason score
 */
public void setGleasonSecondary(Integer score) {
	_gleasonSecondary = score;
}
/**
 * Sets this path report section's total gleason score.
 *
 * @param  score  the total gleason score
 */
public void setGleasonTotal(Integer score) {
	_gleasonTotal = score;
}
/**
 * Sets this path report section's histological nuclear grade.
 *
 * @param  hng  the histological nuclear grade
 */
public void setHistologicalNuclearGrade(String hng) {
	_histologicalNuclearGrade = hng;
}
/**
 * Sets this path report section's "other" histological nuclear grade.
 *
 * @param  hng  the "other" histological nuclear grade
 */
public void setHistologicalNuclearGradeOther(String hng) {
	_histologicalNuclearGradeOther = hng;
}
/**
 * Sets this path report section's histological type code.
 *
 * @param  type  the histological type code
 */
public void setHistologicalType(String type) {
	_histologicalType = type;
}
/**
 * Sets this path report section's "other" histological type.
 *
 * @param  other  the "other" histological type
 */
public void setHistologicalTypeOther(String other) {
	_histologicalTypeOther = other;
}
/**
 * Sets this path report section's inflamm cell infiltrate value.
 *
 * @param  value  the inflamm cell infiltrate value
 */
public void setInflammCellInfiltrate(String value) {
	_inflammCellInfiltrate = value;
}
/**
 * Sets this path report section's joint component value.
 *
 * @param  value  the joint component value
 */
public void setJointComponent(String value) {
	_jointComponent = value;
}
/**
 * Sets the last update user for this path report section.
 *
 * @param  user  the last update user
 */
public void setLastUpdateUser(String user) {
	_lastUpdateUser = user;
}
/**
 * Sets this path report section's lymphatic vascular invasion value.
 *
 * @param  value  the lymphatic vascular invasion value
 */
public void setLymphaticVascularInvasion(String value) {
	_lymphaticVascularInvasion = value;
}
/**
 * Sets this path report section's lymph node notes.
 *
 * @param  notes  the lymph node notes
 */
public void setLymphNodeNotes(String notes) {
	_lymphNodeNotes = notes;
}
/**
 * Sets this path report section's lymph node stage.
 *
 * @param  stage  the lymph node stage
 */
public void setLymphNodeStage(String stage) {
	_lymphNodeStage = stage;
}
/**
 * Sets this path report section's lymph node stage indicator.
 *
 * @param  ind  the lymph node stage indicator
 */
public void setLymphNodeStageInd(String ind) {
	_lymphNodeStageInd = ind;
}
/**
 * Sets this path report section's "other" lymph node stage.
 *
 * @param  stage  the "other" lymph node stage
 */
public void setLymphNodeStageOther(String stage) {
	_lymphNodeStageOther = stage;
}
/**
 * Sets this path report section's involved margins value.
 *
 * @param  value  the new involved margins value
 */
public void setMarginsInvolved(String value) {
	_marginsInvolved = value;
}
/**
 * Sets this path report section's uninvolved margins value.
 *
 * @param  value  the new uninvolved margins value
 */
public void setMarginsUninvolved(String value) {
	_marginsUninvolved = value;
}
/**
 * Sets the id of the path report with which this path report section's 
 * is associated.
 *
 * @param  id  the path report id
 */
public void setPathReportId(String id) {
	_pathReportId = id;
}
/**
 * Sets this path report section's id.
 *
 * @param  id  the id
 */
public void setPathReportSectionId(String id) {
	_pathReportSectionId = id;
}
/**
 * Sets this path report section's perinueral invasion.
 *
 * @param  value  the new perinueral invasion value
 */
public void setPerineuralInvasion(String value) {
	_perineuralInvasion = value;
}
/**
 * Sets an idicator of whether this path report section is the
 * primary section or not.
 *
 * @param  primary  'Y' if this is the primary section; 'N' otherwise
 */
public void setPrimary(String primary) {
	_primary = primary;
}
/**
 * Sets this path report section's identifier.
 *
 * @param  identifier  the section identifier
 */
public void setSectionIdentifier(String identifier) {
	_sectionIdentifier = identifier;
}
/**
 * Sets this path report section's notes.
 *
 * @param  notes  the section notes
 */
public void setSectionNotes(String notes) {
	_sectionNotes = notes;
}
/**
 * Sets this path report section's size of largest nodal mets.
 *
 * @param  size  the size of largest nodal mets
 */
public void setSizeLargestNodalMets(String size) {
	_sizeLargestNodalMets = size;
}
/**
 * Sets this path report section's stage grouping.
 *
 * @param  grouping  the stage grouping
 */
public void setStageGrouping(String grouping) {
	_stageGrouping = grouping;
}
/**
 * Sets this path report section's "other" stage grouping.
 *
 * @param  grouping  the "other" stage grouping
 */
public void setStageGroupingOther(String grouping) {
	_stageGroupingOther = grouping;
}
/**
 * Sets this path report section's finding tissue.
 *
 * @param  tissue  the finding tissue
 */
public void setTissueFinding(String tissue) {
	_tissueFinding = tissue;
}
/**
 * Sets this path report section's "other" finding tissue.
 *
 * @param  tissue  the "other" finding tissue
 */
public void setTissueFindingOther(String tissue) {
	_tissueFindingOther = tissue;
}
/**
 * Sets this path report section's origin tissue.
 *
 * @param  tissue  the origin tissue
 */
public void setTissueOrigin(String tissue) {
	_tissueOrigin = tissue;
}
/**
 * Sets this path report section's "other" origin tissue.
 *
 * @param  tissue  the "other" origin tissue
 */
public void setTissueOriginOther(String tissue) {
	_tissueOriginOther = tissue;
}
/**
 * Sets this path report section's total number of examined nodes.
 *
 * @param  total  the total number of examined nodes
 */
public void setTotalNodesExamined(String total) {
	_totalNodesExamined = total;
}
/**
 * Sets this path report section's total number of positive nodes.
 *
 * @param  total  the total number of positive nodes
 */
public void setTotalNodesPositive(String total) {
	_totalNodesPositive = total;
}
/**
 * Sets this path report section's tumor configuration.
 *
 * @param  config  the tumor configuration
 */
public void setTumorConfiguration(String config) {
	_tumorConfig = config;
}
/**
 * Sets this path report section's tumor size.
 *
 * @param  size  the tumor size
 */
public void setTumorSize(String size) {
	_tumorSize = size;
}
/**
 * Sets this path report section's tumor stage description code.
 *
 * @param  desc  the tumor stage description code.
 */
public void setTumorStageDesc(String desc) {
	_tumorStageDesc = desc;
}
/**
 * Sets this path report section's tumor stage description indicator.
 *
 * @param  ind  the tumor stage description indicator
 */
public void setTumorStageDescInd(String ind) {
	_tumorStageDescInd = ind;
}
/**
 * Sets this path report section's "other" tumor stage description code.
 *
 * @param  desc  the "other" tumor stage description
 */
public void setTumorStageDescOther(String desc) {
	_tumorStageDescOther = desc;
}
/**
 * Sets this path report section's tumor stage type code.
 *
 * @param  type  the tumor stage type code
 */
public void setTumorStageType(String type) {
	_tumorStageType = type;
}
/**
 * Sets this path report section's other tumor stage type.
 *
 * @param  other  the "other" tumor stage type
 */
public void setTumorStageTypeOther(String other) {
	_tumorStageTypeOther = other;
}
/**
 * Sets this path report section's tumor weight.
 *
 * @param  weight  the tumor weight
 */
public void setTumorWeight(String weight) {
	_tumorWeight = weight;
}
/**
 * Sets this path report section's venous vessel invasion.
 *
 * @param  value  the venous vessel invasion value
 */
public void setVenousVesselInvasion(String value) {
	_venousVesselInvasion = value;
}
		
	/** Returns the diagnosis name */
	public String getDiagnosisName() {
		String returnValue = "";
		String code = getDiagnosis();
		if (code == null || code.equals("")) {
			//nothing to do
		}
		else if (code.equals(FormLogic.OTHER_DX)) {
			returnValue = getDiagnosisOther();
		}
		else {
			returnValue = BigrGbossData.getDiagnosisDescription(code);
		}
		return returnValue;
	}
		
	/** Returns the tissue of origin name */
	public String getTissueOriginName() {
		String returnValue = "";
		String code = getTissueOrigin();
		if (code == null || code.equals("")) {
			//nothing to do
		}
		else if (code.equals(FormLogic.OTHER_TISSUE)) {
			returnValue = getTissueOriginOther();
		}
		else {
			returnValue = BigrGbossData.getTissueDescription(code);
		}
		return returnValue;
	}
		
	/** Returns the tissue of finding name */
	public String getTissueFindingName() {
		String returnValue = "";
		String code = getTissueFinding();
		if (code == null || code.equals("")) {
			//nothing to do
		}
		else if (code.equals(FormLogic.OTHER_TISSUE)) {
			returnValue = getTissueFindingOther();
		}
		else {
			returnValue = BigrGbossData.getTissueDescription(code);
		}
		return returnValue;
	}
		
	/** Returns the histologic nuclear grade name */
	public String getHistologicalNuclearGradeName() {
		String returnValue = "";
		String code = getHistologicalNuclearGrade();
		if (code == null || code.equals("")) {
			//nothing to do
		}
		else if (code.equals(FormLogic.OTHER_HISTOLOGICAL_NUCLEAR_GRADE)) {
			returnValue = getHistologicalNuclearGradeOther();
		}
		else {
			returnValue = GbossFactory.getInstance().getDescription(code);
		}
		return returnValue;
	}
		
	/** Returns the tumor stage description name */
	public String getTumorStageDescName() {
		String returnValue = "";
		String code = getTumorStageDesc();
		if (code == null || code.equals("")) {
			//nothing to do
		}
		else if (code.equals(FormLogic.OTHER_TUMOR_STAGE_DESC)) {
			returnValue = getTumorStageDescOther();
		}
		else {
			returnValue = GbossFactory.getInstance().getDescription(code);
		}
		return returnValue;
	}
		
	/** Returns the lymph node stage name */
	public String getLymphNodeStageName() {
		String returnValue = "";
		String code = getLymphNodeStage();
		if (code == null || code.equals("")) {
			//nothing to do
		}
		else if (code.equals(FormLogic.OTHER_LYMPH_NODE_STAGE_DESC)) {
			returnValue = getLymphNodeStageOther();
		}
		else {
			returnValue = GbossFactory.getInstance().getDescription(code);
		}
		return returnValue;
	}
		
	/** Returns the distant metastasis name */
	public String getDistantMetastasisName() {
		String returnValue = "";
		String code = getDistantMetastasis();
		if (code == null || code.equals("")) {
			//nothing to do
		}
		else if (code.equals(FormLogic.OTHER_DISTANT_METASTASIS)) {
			returnValue = getDistantMetastasisOther();
		}
		else {
			returnValue = GbossFactory.getInstance().getDescription(code);
		}
		return returnValue;
	}
		
	/** Returns the stage grouping name */
	public String getStageGroupingName() {
		String returnValue = "";
		String code = getStageGrouping();
		if (code == null || code.equals("")) {
			//nothing to do
		}
		else if (code.equals(FormLogic.OTHER_STAGE_GROUPING)) {
			returnValue = getStageGroupingOther();
		}
		else {
			returnValue = GbossFactory.getInstance().getDescription(code);
		}
		return returnValue;
	}
  
  /* (non-Javadoc)
   * @see com.ardais.bigr.btx.framework.DescribableAsHistoryObject#describeAsHistoryObject()
   */
  public Object describeAsHistoryObject() {
    BtxHistoryAttributes attributes = new BtxHistoryAttributes();
    if (!ApiFunctions.isEmpty(getConsentId())) {
      attributes.setAttribute("consentId", getConsentId());
    }
    if (!ApiFunctions.isEmpty(getCellInfiltrateAmount())) {
      attributes.setAttribute("cellInfiltrateAmount", GbossFactory.getInstance().getDescription(getCellInfiltrateAmount()));
    }
    if (!ApiFunctions.isEmpty(getDiagnosis())) {
      attributes.setAttribute("diagnosis", BigrGbossData.getDiagnosisDescription(getDiagnosis()));
    }
    if (!ApiFunctions.isEmpty(getDiagnosisOther())) {
      attributes.setAttribute("diagnosisOther", getDiagnosisOther());
    }
    if (!ApiFunctions.isEmpty(getDistantMetastasis())) {
      attributes.setAttribute("distantMetastasis", GbossFactory.getInstance().getDescription(getDistantMetastasis()));
    }
    if (!ApiFunctions.isEmpty(getDistantMetastasisInd())) {
      attributes.setAttribute("distantMetastasisInd", getDistantMetastasisInd());
    }
    if (!ApiFunctions.isEmpty(getDistantMetastasisOther())) {
      attributes.setAttribute("distantMetastasisOther", getDistantMetastasisOther());
    }
    if (!ApiFunctions.isEmpty(getExtensiveIntraductalComp())) {
      attributes.setAttribute("extensiveIntraductalComp", GbossFactory.getInstance().getDescription(getExtensiveIntraductalComp()));
    }
    if (!ApiFunctions.isEmpty(getExtranodalSpread())) {
      attributes.setAttribute("extranodalSpread", GbossFactory.getInstance().getDescription(getExtranodalSpread()));
    }
    if (getGleasonPrimary() != null && !ApiFunctions.isEmpty(getGleasonPrimary().toString())) {
      attributes.setAttribute("gleasonPrimary", getGleasonPrimary().toString());
    }
    if (getGleasonSecondary() != null && !ApiFunctions.isEmpty(getGleasonSecondary().toString())) {
      attributes.setAttribute("gleasonSecondary", getGleasonSecondary().toString());
    }
    if (getGleasonTotal() != null && !ApiFunctions.isEmpty(getGleasonTotal().toString())) {
      attributes.setAttribute("gleasonTotal", getGleasonTotal().toString());
    }
    if (!ApiFunctions.isEmpty(getHistologicalNuclearGrade())) {
      attributes.setAttribute("histologicalNuclearGrade", GbossFactory.getInstance().getDescription(getHistologicalNuclearGrade()));
    }
    if (!ApiFunctions.isEmpty(getHistologicalNuclearGradeOther())) {
      attributes.setAttribute("histologicalNuclearGradeOther", getHistologicalNuclearGradeOther());
    }
    if (!ApiFunctions.isEmpty(getHistologicalType())) {
      attributes.setAttribute("histologicalType", GbossFactory.getInstance().getDescription(getHistologicalType()));
    }
    if (!ApiFunctions.isEmpty(getHistologicalTypeOther())) {
      attributes.setAttribute("histologicalTypeOther", getHistologicalTypeOther());
    }
    if (!ApiFunctions.isEmpty(getInflammCellInfiltrate())) {
      attributes.setAttribute("inflammCellInfiltrate", GbossFactory.getInstance().getDescription(getInflammCellInfiltrate()));
    }
    if (!ApiFunctions.isEmpty(getJointComponent())) {
      attributes.setAttribute("jointComponent", GbossFactory.getInstance().getDescription(getJointComponent()));
    }
    if (!ApiFunctions.isEmpty(getLymphaticVascularInvasion())) {
      attributes.setAttribute("lymphaticVascularInvasion", GbossFactory.getInstance().getDescription(getLymphaticVascularInvasion()));
    }
    if (!ApiFunctions.isEmpty(getLymphNodeNotes())) {
      attributes.setAttribute("lymphNodeNotes", getLymphNodeNotes());
    }
    if (!ApiFunctions.isEmpty(getLymphNodeStage())) {
      attributes.setAttribute("lymphNodeStage", GbossFactory.getInstance().getDescription(getLymphNodeStage()));
    }
    if (!ApiFunctions.isEmpty(getLymphNodeStageOther())) {
      attributes.setAttribute("lymphNodeStageOther", getLymphNodeStageOther());
    }
    if (!ApiFunctions.isEmpty(getLymphNodeStageInd())) {
      attributes.setAttribute("lymphNodeStageInd", getLymphNodeStageInd());
    }
    if (!ApiFunctions.isEmpty(getMarginsInvolved())) {
      attributes.setAttribute("marginsInvolved", getMarginsInvolved());
    }
    if (!ApiFunctions.isEmpty(getMarginsUninvolved())) {
      attributes.setAttribute("marginsUninvolved", getMarginsUninvolved());
    }
    if (!ApiFunctions.isEmpty(getPerineuralInvasion())) {
      attributes.setAttribute("perineuralInvasion", GbossFactory.getInstance().getDescription(getPerineuralInvasion()));
    }
    if (!ApiFunctions.isEmpty(getPrimary())) {
      attributes.setAttribute("primary", getPrimary());
    }
    if (!ApiFunctions.isEmpty(getSectionIdentifier())) {
      attributes.setAttribute("sectionIdentifier", GbossFactory.getInstance().getDescription(getSectionIdentifier()));
    }
    if (!ApiFunctions.isEmpty(getSectionNotes())) {
      attributes.setAttribute("sectionNotes", getSectionNotes());
    }
    if (!ApiFunctions.isEmpty(getSizeLargestNodalMets())) {
      attributes.setAttribute("sizeLargestNodalMets", getSizeLargestNodalMets());
    }
    if (!ApiFunctions.isEmpty(getStageGrouping())) {
      attributes.setAttribute("stageGrouping", GbossFactory.getInstance().getDescription(getStageGrouping()));
    }
    if (!ApiFunctions.isEmpty(getStageGroupingOther())) {
      attributes.setAttribute("stageGroupingOther", getStageGroupingOther());
    }
    if (!ApiFunctions.isEmpty(getTissueFinding())) {
      attributes.setAttribute("tissueFinding", BigrGbossData.getTissueDescription(getTissueFinding()));
    }
    if (!ApiFunctions.isEmpty(getTissueFindingOther())) {
      attributes.setAttribute("tissueFindingOther", getTissueFindingOther());
    }
    if (!ApiFunctions.isEmpty(getTissueOrigin())) {
      attributes.setAttribute("tissueOrigin", BigrGbossData.getTissueDescription(getTissueOrigin()));
    }
    if (!ApiFunctions.isEmpty(getTissueOriginOther())) {
      attributes.setAttribute("tissueOriginOther", getTissueOriginOther());
    }
    if (!ApiFunctions.isEmpty(getTotalNodesExamined())) {
      attributes.setAttribute("totalNodesExamined", GbossFactory.getInstance().getDescription(getTotalNodesExamined()));
    }
    if (!ApiFunctions.isEmpty(getTotalNodesPositive())) {
      attributes.setAttribute("totalNodesPositive", GbossFactory.getInstance().getDescription(getTotalNodesPositive()));
    }
    if (!ApiFunctions.isEmpty(getTumorConfig())) {
      attributes.setAttribute("tumorConfig", GbossFactory.getInstance().getDescription(getTumorConfig()));
    }
    if (!ApiFunctions.isEmpty(getTumorSize())) {
      attributes.setAttribute("tumorSize", getTumorSize());
    }
    if (!ApiFunctions.isEmpty(getTumorStageDesc())) {
      attributes.setAttribute("tumorStageDesc", GbossFactory.getInstance().getDescription(getTumorStageDesc()));
    }
    if (!ApiFunctions.isEmpty(getTumorStageDescInd())) {
      attributes.setAttribute("tumorStageDescInd", getTumorStageDescInd());
    }
    if (!ApiFunctions.isEmpty(getTumorStageDescOther())) {
      attributes.setAttribute("tumorStageDescOther", getTumorStageDescOther());
    }
    if (!ApiFunctions.isEmpty(getTumorStageType())) {
      attributes.setAttribute("tumorStageType", GbossFactory.getInstance().getDescription(getTumorStageType()));
    }
    if (!ApiFunctions.isEmpty(getTumorStageTypeOther())) {
      attributes.setAttribute("tumorStageTypeOther", getTumorStageTypeOther());
    }
    if (!ApiFunctions.isEmpty(getTumorWeight())) {
      attributes.setAttribute("tumorWeight", getTumorWeight());
    }
    if (!ApiFunctions.isEmpty(getVenousVesselInvasion())) {
      attributes.setAttribute("venousVesselInvasion", GbossFactory.getInstance().getDescription(getVenousVesselInvasion()));
    }
    return attributes;
  }
}
