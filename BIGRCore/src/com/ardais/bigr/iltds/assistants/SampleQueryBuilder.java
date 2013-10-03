package com.ardais.bigr.iltds.assistants;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.iltds.helpers.FormLogic;

/**
 */
public class SampleQueryBuilder implements Serializable {

	// Data types of selected fields.
	private final static int DATATYPE_STRING = 1;
	private final static int DATATYPE_INTEGER = 2;
	private final static int DATATYPE_BOOLEAN = 3;
	private final static int DATATYPE_DATE = 4;
	
	// SELECT clause keys.
	private final static String SELECT_ARDAISID = "001";
	private final static String SELECT_CONSENTID = "002";
	private final static String SELECT_SAMPLEID = "003";
	private final static String SELECT_ASM_APPEARANCE = "004";
	private final static String SELECT_ALLOCATION = "005";
	private final static String SELECT_ASM_POSITION = "006";
	private final static String SELECT_ASM_TISSUE_CODE = "007";
	private final static String SELECT_CONSENT_DIAGNOSIS_CODE = "008";
	private final static String SELECT_SAMPLE_TISSUE_CODE = "009";
	private final static String SELECT_SAMPLE_DIAGNOSIS_CODE = "010";
	private final static String SELECT_QCVERIFIED = "011";
	private final static String SELECT_QCSTATUS = "012";
	private final static String SELECT_REQUESTED_DATE = "013";
	private final static String SELECT_CART_ACCOUNT = "014";
	private final static String SELECT_CART_USER = "015";
	private final static String SELECT_LIMS_SAMPLEID = "016";
	private final static String SELECT_ACELLULARSTROMA = "017";
	private final static String SELECT_VIABLETUMERCELLS = "018";
	private final static String SELECT_VIABLELESIONALCELLS = "019";
	private final static String SELECT_VIABLENORMALCOMP = "020";
	private final static String SELECT_CELLULARSTROMA = "021";
	private final static String SELECT_NECROSIS = "022";
	private final static String SELECT_COMMENTS = "023";
	private final static String SELECT_INTERNAL_COMMENTS = "024";
	private final static String SELECT_LINEITEMID = "025";

	// FROM clause keys.
	private final static String FROM_CONSENT = "001";
	private final static String FROM_ASM = "002";
	private final static String FROM_SAMPLE = "003";
	private final static String FROM_DONOR = "005";
	private final static String FROM_PTS_SAMPLE = "006";
	private final static String FROM_CART_DETAIL = "007";
	private final static String FROM_ORDER_LINE = "008";
	private final static String FROM_LIMS = "012";
  private final static String FROM_ARD_LOG_RES = "013";

	// WHERE clause keys.
	private final static String WHERE_SAMPLE_EQUAL = "001";
	private final static String WHERE_CONSENT_JOIN = "002";
	private final static String WHERE_CONSENT_EQUAL = "003";
	private final static String WHERE_ASM_JOIN = "004";
	private final static String WHERE_DONOR_EQUAL = "005";
	private final static String WHERE_DONOR_JOIN = "009";
	private final static String WHERE_LINEITEM = "010";
	private final static String WHERE_PROJECT = "011";
	private final static String WHERE_IN_PROJECT = "012";
	private final static String WHERE_ASM_TISSUE_NOT_NULL = "013";
	private final static String WHERE_CONSENT_NOT_PULLED = "014";
	private final static String WHERE_CONSENT_NOT_REVOKED = "015";
	private final static String WHERE_SALES_STATUS_EQUAL = "016";
	private final static String WHERE_SALES_STATUS_NOT_EQUAL = "017";
	private final static String WHERE_INV_STATUS_NOT_EQUAL = "018";
	private final static String WHERE_QC_STATUS_NOT_EQUAL = "019";
	private final static String WHERE_QC_STATUS_NOT_NULL = "020";
	private final static String WHERE_IN_REPOSITORY = "022";
	private final static String WHERE_NOT_ON_HOLD = "023";
	private final static String WHERE_ON_HOLD = "024";
	private final static String WHERE_NOT_IN_PROJECT = "025";
	private final static String WHERE_UNREST_OR_REST_ACCOUNT = "026";
	private final static String WHERE_IN_SHOPPING_CART = "027";
	private final static String WHERE_ORDER_EQUAL = "028";
	private final static String JOIN_ORDER = "029";
	private final static String WHERE_LINKED_EQUAL = "036";
	private final static String WHERE_GENDER_EQUAL = "037";
	private final static String WHERE_YOB_GREATER_EQUAL = "038";
	private final static String WHERE_YOB_LESS_EQUAL = "039";
	private final static String WHERE_IS_PATH_VERIFIED = "040";
	private final static String WHERE_APPEARANCE_PAIRED = "041";
	private final static String WHERE_REQUESTED = "042";
	private final static String WHERE_LIMS = "043";
  private final static String WHERE_PULLED_SAMPLE = "044";
  private final static String WHERE_LOGICAL_REPOS = "045";

	// Maps to hold all possible clauses.  The Maps are keyed such that a consistent order
	// is provided for all instances of SampleQueryBuilder.
	private static Map _selectClauses;  
	private static Map _fromClauses; 
	private static Map _whereClauses; 
//	private Map _selectClauses;  
//	private Map _fromClauses; 
//	private Map _whereClauses; 

	static {
//	{
		_selectClauses = new HashMap();  
		_fromClauses = new HashMap(); 
		_whereClauses = new HashMap(); 

		try {
			Class beanClass = Class.forName("com.ardais.bigr.iltds.assistants.SampleDataBean");
			Class methodArgsString[] = { Class.forName("java.lang.String") };
			//Class methodArgsInteger[] = { Class.forName("java.lang.Integer") };
			Class methodArgsBoolean[] = { Class.forName("java.lang.Boolean") };
			Class methodArgsDate[] = { Class.forName("java.sql.Date") };

			// All of the possible SELECT clauses.
			_selectClauses.put(SELECT_ARDAISID, new MasterSelectData(
				"consent.ardais_id", 
				"ardais_id", 
				beanClass.getMethod("setArdaisId", methodArgsString), 
				DATATYPE_STRING));

			_selectClauses.put(SELECT_CONSENTID, new MasterSelectData(
				"consent.consent_id", 
				"consent_id", 
				beanClass.getMethod("setCaseId", methodArgsString), 
				DATATYPE_STRING));

			_selectClauses.put(SELECT_SAMPLEID,  new MasterSelectData(
				"sample.sample_barcode_id", 
				"sample_barcode_id", 
				beanClass.getMethod("setId", methodArgsString), 
				DATATYPE_STRING));
		 
			_selectClauses.put(SELECT_ASM_APPEARANCE, new MasterSelectData(
				"asm.specimen_type AS asm_appearance", 
				"asm_appearance", 
				beanClass.getMethod("setAsmAppearance", methodArgsString), 
				DATATYPE_STRING));
		 
			_selectClauses.put(SELECT_ALLOCATION, new MasterSelectData(
				"sample.allocation_ind AS allocation", 
				"allocation", 
				beanClass.getMethod("setRestricted", methodArgsBoolean), 
				DATATYPE_BOOLEAN,
				"R"));
		 
			_selectClauses.put(SELECT_ASM_POSITION, new MasterSelectData(
				"sample.asm_position", 
				"asm_position", 
				beanClass.getMethod("setAsmPosition", methodArgsString), 
				DATATYPE_STRING));
		 
			_selectClauses.put(SELECT_ASM_TISSUE_CODE, new MasterSelectData(
				"asm.organ_site_concept_id AS asm_tissue_code", 
				"asm_tissue_code", 
				beanClass.getMethod("setAsmTissue", methodArgsString), 
				DATATYPE_STRING));
		 
			_selectClauses.put(SELECT_CONSENT_DIAGNOSIS_CODE, new MasterSelectData(
				"consent.disease_concept_id AS consent_dx_code", 
				"consent_dx_code", 
				beanClass.getMethod("setConsentDiagnosis", methodArgsString), 
				DATATYPE_STRING));
		 
			_selectClauses.put(SELECT_SAMPLE_TISSUE_CODE, new MasterSelectData(
				"sample.tissue_origin_cui AS product_tissue_code", 
				"product_tissue_code", 
				beanClass.getMethod("setProductTissue", methodArgsString), 
				DATATYPE_STRING));
		 
			_selectClauses.put(SELECT_SAMPLE_DIAGNOSIS_CODE, new MasterSelectData(
				"sample.diagnosis_cui_best AS product_dx_code", 
				"product_dx_code", 
				beanClass.getMethod("setProductDiagnosis", methodArgsString), 
				DATATYPE_STRING));

			_selectClauses.put(SELECT_QCVERIFIED, new MasterSelectData(
				"sample.qc_verified", 
				"qc_verified", 
				beanClass.getMethod("setPathVerified", methodArgsBoolean), 
				DATATYPE_BOOLEAN,
				"Y"));
		 
			_selectClauses.put(SELECT_QCSTATUS, new MasterSelectData(
				"sample.qc_status", 
				"qc_status", 
				beanClass.getMethod("setPathStatus", methodArgsString), 
				DATATYPE_STRING));
		 
			_selectClauses.put(SELECT_REQUESTED_DATE, new MasterSelectData(
				"requested.sample_status_datetime as requested_date", 
				"requested_date", 
				beanClass.getMethod("setRequestedDate", methodArgsDate), 
				DATATYPE_DATE));
		 
			_selectClauses.put(SELECT_CART_ACCOUNT, new MasterSelectData(
				"cart_detail.ardais_acct_key AS cart_account", 
				"cart_account", 
				beanClass.getMethod("setCartAccount", methodArgsString), 
				DATATYPE_STRING));

			_selectClauses.put(SELECT_CART_USER, new MasterSelectData(
				"cart_detail.ardais_user_id AS cart_user", 
				"cart_user", 
				beanClass.getMethod("setCartUser", methodArgsString), 
				DATATYPE_STRING));

			_selectClauses.put(SELECT_LIMS_SAMPLEID, new MasterSelectData(
				"lims.sample_id", 
				"sample_id", 
				beanClass.getMethod("setId", methodArgsString), 
				DATATYPE_STRING));
				
			_selectClauses.put(SELECT_ACELLULARSTROMA, new MasterSelectData(
				"lims.acellularstroma", 
				"acellularstroma", 
				beanClass.getMethod("setAcellularStroma", methodArgsString), 
				DATATYPE_STRING));
				
			_selectClauses.put(SELECT_VIABLETUMERCELLS, new MasterSelectData(
				"lims.viabletumercells", 
				"viabletumercells", 
				beanClass.getMethod("setViableTumorCell", methodArgsString), 
				DATATYPE_STRING));
				
			_selectClauses.put(SELECT_VIABLELESIONALCELLS, new MasterSelectData(
				"lims.viablelesionalcells", 
				"viablelesionalcells", 
				beanClass.getMethod("setViableLesionalCell", methodArgsString), 
				DATATYPE_STRING));
				
			_selectClauses.put(SELECT_VIABLENORMALCOMP, new MasterSelectData(
				"lims.viablenormalcomp", 
				"viablenormalcomp", 
				beanClass.getMethod("setViableNiContent", methodArgsString), 
				DATATYPE_STRING));
				
			_selectClauses.put(SELECT_CELLULARSTROMA, new MasterSelectData(
				"lims.cellularstroma", 
				"cellularstroma", 
				beanClass.getMethod("setCellularStroma", methodArgsString), 
				DATATYPE_STRING));
				
			_selectClauses.put(SELECT_NECROSIS, new MasterSelectData(
				"lims.necrosis", 
				"necrosis", 
				beanClass.getMethod("setNecrosis", methodArgsString), 
				DATATYPE_STRING));

	  		_selectClauses.put(SELECT_COMMENTS, new MasterSelectData(
				"lims.comments", 
				"comments", 
				beanClass.getMethod("setComments", methodArgsString), 
				DATATYPE_STRING));
		 
			_selectClauses.put(SELECT_INTERNAL_COMMENTS, new MasterSelectData(
				"lims.internal_comments", 
				"internal_comments", 
				beanClass.getMethod("setInternalComments", methodArgsString), 
				DATATYPE_STRING));

			_selectClauses.put(SELECT_LINEITEMID, new MasterSelectData(
				"pts_sample.lineitemid", 
				"lineitemid", 
				beanClass.getMethod("setLineItemId", methodArgsString), 
				DATATYPE_STRING));
		}
		catch (ClassNotFoundException e) {}
		catch (NoSuchMethodException e) {}

		// All of the possible FROM clauses.
	    _fromClauses.put(FROM_CONSENT, "iltds_informed_consent consent");
	    _fromClauses.put(FROM_ASM, "iltds_asm asm");
	    _fromClauses.put(FROM_SAMPLE, "iltds_sample sample");
	    _fromClauses.put(FROM_DONOR, "pdc_ardais_donor donor");
	    _fromClauses.put(FROM_PTS_SAMPLE, "pts_sample pts_sample");
	    _fromClauses.put(FROM_CART_DETAIL, "es_shopping_cart_detail cart_detail");
	    _fromClauses.put(FROM_ORDER_LINE, "es_order_line order_line");
	    _fromClauses.put(FROM_LIMS, "lims_bigr_library_v lims");
      _fromClauses.put(FROM_ARD_LOG_RES, "ard_logical_repos_item alri");

		// All of the possible WHERE clauses.
		MasterWhereData masterWhereData = null;
		
		_whereClauses.put(WHERE_SAMPLE_EQUAL, new MasterWhereData(
			"sample.sample_barcode_id = ?", 1, "OR"));

		_whereClauses.put(WHERE_CONSENT_JOIN, new MasterWhereData(
			"consent.consent_id = asm.consent_id"));

		_whereClauses.put(WHERE_CONSENT_EQUAL, new MasterWhereData(
			"consent.consent_id = ?", 1, "OR"));

		_whereClauses.put(WHERE_ASM_JOIN, new MasterWhereData(
			"sample.asm_id = asm.asm_id"));

		_whereClauses.put(WHERE_DONOR_EQUAL, new MasterWhereData(
			"consent.ardais_id = ?", 1, "OR"));

		_whereClauses.put(WHERE_DONOR_JOIN, new MasterWhereData(
			"donor.ardais_id = asm.ardais_id"));

	    _whereClauses.put(WHERE_LINEITEM, new MasterWhereData(
		    "pts_sample.lineitemid = ?", 1, "OR"));
		    
	    _whereClauses.put(WHERE_PROJECT, new MasterWhereData(
		    "pts_sample.projectid = ?", 1, "OR"));

	    _whereClauses.put(WHERE_IN_PROJECT, new MasterWhereData(
		    "sample.sample_barcode_id = pts_sample.sample_barcode_id"));

	    _whereClauses.put(WHERE_ASM_TISSUE_NOT_NULL, new MasterWhereData(
		    "asm.organ_site_concept_id IS NOT NULL"));

	    _whereClauses.put(WHERE_CONSENT_NOT_PULLED, new MasterWhereData(
			"consent.consent_pull_staff_id IS NULL" 
			+ " AND consent.consent_pull_request_by IS NULL"
			+ " AND consent.consent_pull_datetime IS NULL"
			+ " AND consent.consent_pull_reason_cd IS NULL"));

	    _whereClauses.put(WHERE_CONSENT_NOT_REVOKED, new MasterWhereData(
		    "consent.consent_id NOT IN (SELECT consent_id FROM iltds_revoked_consent_archive)"));

	    _whereClauses.put(WHERE_SALES_STATUS_EQUAL, new MasterWhereData(
		    "sample.sales_status = ?", 1, "OR"));

	    _whereClauses.put(WHERE_SALES_STATUS_NOT_EQUAL, new MasterWhereData(
		    "sample.sales_status <> ?", 1, "AND"));

	    _whereClauses.put(WHERE_INV_STATUS_NOT_EQUAL, new MasterWhereData(
		    "sample.inv_status <> ?", 1, "AND"));

	    _whereClauses.put(WHERE_QC_STATUS_NOT_EQUAL, new MasterWhereData(
		    "sample.qc_status <> ?", 1, "AND"));

	    _whereClauses.put(WHERE_QC_STATUS_NOT_NULL, new MasterWhereData(
		    "sample.qc_status IS NOT NULL"));

	    _whereClauses.put(WHERE_IN_REPOSITORY, new MasterWhereData(
			"sample.box_barcode_id IS NOT NULL"
			+ " AND EXISTS (SELECT 1 FROM iltds_box_location loc WHERE sample.box_barcode_id = loc.box_barcode_id)"));

	    _whereClauses.put(WHERE_NOT_ON_HOLD, new MasterWhereData(
			"NOT EXISTS (SELECT 1 FROM es_shopping_cart_detail cart WHERE cart.barcode_id = sample.sample_barcode_id)"));

	    _whereClauses.put(WHERE_ON_HOLD, new MasterWhereData(
		    "sample.sample_barcode_id = cart_detail.barcode_id"));

	    _whereClauses.put(WHERE_NOT_IN_PROJECT, new MasterWhereData(
		    "(sample.project_status IS NULL OR sample.project_status <> '" + FormLogic.PRJ_ADDED + "')"));
	    
		masterWhereData = 
			new MasterWhereData("((sample.allocation_ind = 'U') OR (sample.allocation_ind = 'R' AND sample.ardais_acct_key = ?))");
		masterWhereData.setNumBindParams(1);
	    _whereClauses.put(WHERE_UNREST_OR_REST_ACCOUNT, masterWhereData);

		masterWhereData = 
			new MasterWhereData("sample.sample_barcode_id in (SELECT cart_detail1.barcode_id"
			+ " FROM es_shopping_cart cart1, es_shopping_cart_detail cart_detail1"
			+ " WHERE cart1.shopping_cart_id = cart_detail1.shopping_cart_id"
			+ " AND cart1.ardais_acct_key = cart_detail1.ardais_acct_key"
			+ " AND cart1.ardais_user_id = cart_detail1.ardais_user_id"
			+ " AND cart_detail1.ardais_user_id = ?)");
		masterWhereData.setNumBindParams(1);
	    _whereClauses.put(WHERE_IN_SHOPPING_CART, masterWhereData);

		masterWhereData = 
			new MasterWhereData("order_line.order_number = ?", 1, "OR");
		masterWhereData.setDataType(DATATYPE_INTEGER);
	    _whereClauses.put(WHERE_ORDER_EQUAL, masterWhereData);

	    _whereClauses.put(JOIN_ORDER, new MasterWhereData(
		    "sample.sample_barcode_id = order_line.barcode_id"));

		_whereClauses.put(WHERE_LINKED_EQUAL, new MasterWhereData(
			"consent.linked = ?", 1, "OR"));

		_whereClauses.put(WHERE_GENDER_EQUAL, new MasterWhereData(
			"donor.gender = ?", 1, "OR"));

		masterWhereData = 
			new MasterWhereData("donor.yyyy_dob >= ?");
		masterWhereData.setNumBindParams(1);
		_whereClauses.put(WHERE_YOB_GREATER_EQUAL, masterWhereData);

		masterWhereData = 
			new MasterWhereData("donor.yyyy_dob <= ?");
		masterWhereData.setNumBindParams(1);
		_whereClauses.put(WHERE_YOB_LESS_EQUAL, masterWhereData);

		_whereClauses.put(WHERE_IS_PATH_VERIFIED, new MasterWhereData(
			"sample.qc_verified = 'Y'"));

		_whereClauses.put(WHERE_APPEARANCE_PAIRED, new MasterWhereData(
			"asm.specimen_type IN ('N', 'D')"
			+ " AND EXISTS (SELECT 1 FROM iltds_asm asm_sub, iltds_informed_consent consent_sub"
			+ " WHERE asm_sub.consent_id = consent_sub.consent_id"
			+ " AND consent_sub.consent_id = consent.consent_id"
			+ " AND asm_sub.organ_site_concept_id = asm.organ_site_concept_id"
			+ " AND asm_sub.specimen_type = decode(asm.specimen_type, 'N', 'D', 'D', 'N'))"));

		_whereClauses.put(WHERE_REQUESTED, new MasterWhereData(
		    "sample.sample_barcode_id = requested.sample_barcode_id(+)"));

	  _whereClauses.put(WHERE_LIMS, new MasterWhereData(
		    "lims.sample_id = ?", 1, "OR"));
        
    _whereClauses.put(WHERE_PULLED_SAMPLE, new MasterWhereData(
        "sample.pull_yn <> 'Y'"));

    masterWhereData = new MasterWhereData("sample.sample_barcode_id = alri.item_id and " +
    "(alri.repository_id in (select repository_id from ard_logical_repos_user where ardais_user_id = ?)) ");     
    masterWhereData.setNumBindParams(1);       
    _whereClauses.put(WHERE_LOGICAL_REPOS, masterWhereData);

	}  // End static initialization block

	// Data Structures to hold the clauses that are selected for a specific query.
	// These data structures are sorted and hold the keys of the Maps that hold
	// all of the clauses, to provide consistent order and lookup into the static
	// data structures.  There is a separate set for any LIMS fields that are
	// selected, since we'll do a separate query for those fields for performance.
	private SortedSet _select = new TreeSet();  
	private SortedSet _from = new TreeSet(); 
	private SortedMap _where = new TreeMap(); 

	private SortedSet _select2 = new TreeSet();  
	private SortedSet _from2 = new TreeSet(); 
	private SortedMap _where2 = new TreeMap();
  private String _hint2 = "index(lims LIMS_PATHOLOGY_EVALUATION_IN1)";

 
	// Inner class to hold the pieces of data needed for each SELECT clause.
	static class MasterSelectData {
//	class MasterSelectData {
		private String _clause;
		private String _alias;
		private Method _beanMethod;
		private int _dataType;
		private String _trueValue;

		MasterSelectData(String clause, String alias, Method beanMethod, int dataType) {
			_clause = clause;
			_alias = alias;
			_beanMethod = beanMethod;
			_dataType = dataType;
		}
		MasterSelectData(String clause, String alias, Method beanMethod, int dataType, String trueValue) {
			_clause = clause;
			_alias = alias;
			_beanMethod = beanMethod;
			_dataType = dataType;
			_trueValue = trueValue;
		}
		String getClause() { return _clause; }
		String getAlias() { return _alias; }
		Method getBeanMethod() { return _beanMethod; }
		int getDataType() { return _dataType; }
		String getTrueValue() { return _trueValue; }
	}

	// Inner class to hold the pieces of data needed for each WHERE clause.
	static class MasterWhereData {
//	class MasterWhereData {
		private String _clause;
		private int _numBindParams = 0;
		private String _multiConditionOperator;
		private int _dataType = DATATYPE_STRING;

		MasterWhereData(String clause) {
			_clause = clause;
		}
		MasterWhereData(String clause, int numBindParams, String multiConditionOperator) {
			_clause = clause;
			_numBindParams = numBindParams;
			_multiConditionOperator = multiConditionOperator;
		}
		String getClause() { return _clause; }
		int getNumBindParams() { return _numBindParams; }
		void setNumBindParams(int numBindParams) { _numBindParams = numBindParams; }
		String getMultiConditionOperator() { return _multiConditionOperator; }
		void setMultiConditionOperator(String op) { _multiConditionOperator = op; }
		int getDataType() { return _dataType; }
		void setDataType(int dataType) { _dataType = dataType; }
	}

	class WhereData implements Serializable {
		private int _numClauses = 1;
		private String _paramString;
		private java.sql.Date _paramDate;
		private Integer _paramInt;
		private List _params;
		private boolean _orNext;

		WhereData() {}
		WhereData(int numClauses) {
			_numClauses = numClauses;
		}
		int getNumClauses() { return _numClauses; }
		String getParameterString() { return _paramString; }
		java.sql.Date getParameterDate() { return _paramDate; }
		Integer getParameterInteger() { return _paramInt; }
		void setParameter(String param) { _paramString = param; }
		void setParameter(java.sql.Date param) { _paramDate = param; }
		void setParameter(Integer param) { _paramInt = param; }
		List getParameters() { return _params; }
		void setParameters(List params) { _params = params; }
		boolean isOrNext() { return _orNext; }
		void setOrNext(boolean orNext) { _orNext = orNext; }
	}	
/**
 * Creates a new <code>SampleQueryBuilder</code>.
 */
public SampleQueryBuilder() {
    super();
    _from.add(FROM_SAMPLE);
    _from.add(FROM_ASM);
}
/**
 */
private void bindAllVariables(PreparedStatement pstmt, SortedMap whereMap) 
	throws SQLException {

	if (!whereMap.isEmpty()) {
		Iterator whereKeys = whereMap.keySet().iterator();
		int paramIndex = 1;
		while (whereKeys.hasNext()) {
			String whereKey = (String)whereKeys.next();

			WhereData whereData = (WhereData)whereMap.get(whereKey);
			int numClauses = whereData.getNumClauses();
			
			MasterWhereData masterData = (MasterWhereData)_whereClauses.get(whereKey);
			int dataType = masterData.getDataType();
			int numBindParams = masterData.getNumBindParams() * numClauses;

			if (numBindParams == 1) {
				switch (dataType) {
					case DATATYPE_STRING:
						String paramString = whereData.getParameterString();
						if (paramString == null) {
							numBindParams++;	//hack so the next if will execute
						}
						else {
							pstmt.setString(paramIndex++, paramString);
						}
						break;
					case DATATYPE_INTEGER:
						Integer paramInt = whereData.getParameterInteger();
						if (paramInt == null) {
							numBindParams++;	//hack so the next if will execute
						}
						else {
							pstmt.setInt(paramIndex++, paramInt.intValue());
						}
						break;
					case DATATYPE_DATE:
						java.sql.Date paramDate = whereData.getParameterDate();
						if (paramDate == null) {
							numBindParams++;	//hack so the next if will execute
						}
						else {
							pstmt.setDate(paramIndex++, paramDate);
						}
						break;
					case DATATYPE_BOOLEAN:
						throw new ApiException("Not supported");
				}
			}
			
			if (numBindParams > 1) {
				Iterator params = whereData.getParameters().iterator();
				while (params.hasNext()) {
					switch (dataType) {
						case DATATYPE_STRING:
							pstmt.setString(paramIndex++, (String)params.next());
							break;
						case DATATYPE_INTEGER:
							pstmt.setInt(paramIndex++, ((Integer)params.next()).intValue());
							break;
						case DATATYPE_DATE:
							pstmt.setDate(paramIndex++, (java.sql.Date)params.next());
							break;
						case DATATYPE_BOOLEAN:
							throw new ApiException("Not supported");
					}
				}
			}
		}
	}
}
public void bindConsentEquals(String id) {
	bindSingleParam(WHERE_CONSENT_EQUAL, id);
}
public void bindConsentEquals(List consentIds) {
	bindParameterList(WHERE_CONSENT_EQUAL, consentIds);
}
public void bindDonorEquals(String id) {
	bindSingleParam(WHERE_DONOR_EQUAL, id);
}
public void bindGenderEquals(String gender) {
	bindSingleParam(WHERE_GENDER_EQUAL, gender);
}
public void bindGenderEquals(List genders) {
	bindParameterList(WHERE_GENDER_EQUAL, genders);
}
public void bindInShoppingCart(String cartUserId) {
	bindSingleParam(WHERE_IN_SHOPPING_CART, cartUserId);
}
public void bindInventoryStatusNotEqual(String status) {
	bindSingleParam(WHERE_INV_STATUS_NOT_EQUAL, status);
}
public void bindInventoryStatusNotEqual(List statuses) {
	bindParameterList(WHERE_INV_STATUS_NOT_EQUAL, statuses);
}
public void bindLineItemId(String id) {
	bindSingleParam(WHERE_LINEITEM, id);
}
public void bindLinkedEquals(String linked) {
	bindSingleParam(WHERE_LINKED_EQUAL, linked);
}
public void bindLinkedEquals(List linked) {
	bindParameterList(WHERE_LINKED_EQUAL, linked);
}

public void bindLogicalRepository(String userId) {
  bindSingleParam(WHERE_LOGICAL_REPOS, userId);
}

public void bindOrderEqual(Integer orderNumber) {
	bindSingleParam(WHERE_ORDER_EQUAL, orderNumber);
}
private void bindParameterList(String whereKey, List params) {
	WhereData whereData = (WhereData)_where.get(whereKey);
	if (whereData == null) {
		throw new ApiException("Error in SampleQueryBuilder: Attempt to bind parameters for non-existent WHERE clause");
	}
	else {
		whereData.setParameters(params);
	}
}
public void bindProjectId(String id) {
	bindSingleParam(WHERE_PROJECT, id);
}
public void bindQcStatusNotEqual(String status) {
	bindSingleParam(WHERE_QC_STATUS_NOT_EQUAL, status);
}
private void bindQuery2SampleIds(List ids) {
	WhereData whereData = (WhereData)_where2.get(WHERE_LIMS);
	if (whereData == null) {
		throw new ApiException("Error in SampleQueryBuilder: Attempt to bind parameters for non-existent WHERE clause");
	}
	else {
		whereData.setParameters(ids);
	}
}
public void bindSalesStatusEqual(String status) {
	bindSingleParam(WHERE_SALES_STATUS_EQUAL, status);
}
public void bindSalesStatusNotEqual(String status) {
	bindSingleParam(WHERE_SALES_STATUS_NOT_EQUAL, status);
}
public void bindSampleEquals(String sampleId) {
	bindSingleParam(WHERE_SAMPLE_EQUAL, sampleId);
}
public void bindSampleEquals(List sampleIds) {
	bindParameterList(WHERE_SAMPLE_EQUAL, sampleIds);
}
private void bindSingleParam(String whereKey, Integer param) {
	WhereData whereData = (WhereData)_where.get(whereKey);
	if (whereData == null) {
		throw new ApiException("Error in SampleQueryBuilder: Attempt to bind parameter for non-existent WHERE clause");
	}
	else {
		whereData.setParameter(param);
	}
}
private void bindSingleParam(String whereKey, String param) {
	WhereData whereData = (WhereData)_where.get(whereKey);
	if (whereData == null) {
		throw new ApiException("Error in SampleQueryBuilder: Attempt to bind parameter for non-existent WHERE clause");
	}
	else {
		whereData.setParameter(param);
	}
}
public void bindUnrestrictedOrRestrictedForAccount(String accountId) {
	bindSingleParam(WHERE_UNREST_OR_REST_ACCOUNT, accountId);
}
public void bindYearOfBirthBetween(String yearFrom, String yearTo) {
	bindSingleParam(WHERE_YOB_GREATER_EQUAL, yearFrom);
	bindSingleParam(WHERE_YOB_LESS_EQUAL, yearTo);
}
public String getFromClause() {
	StringBuffer buf = new StringBuffer(128);
	getFromClause(buf);
	return buf.toString();
}
public void getFromClause(StringBuffer buf) {
	getFromClause(buf, _from);
}
private void getFromClause(StringBuffer buf, SortedSet from) {
	if (from.isEmpty()) {
		throw new ApiException("Attempt to build a sample query without specifying a FROM clause");
	}

	Iterator froms = from.iterator();
	buf.append("\n FROM ");
	String fromKey = (String) froms.next();
	buf.append("\n   ");
	buf.append((String) _fromClauses.get(fromKey));
	while (froms.hasNext()) {
		buf.append(",\n   ");
		fromKey = (String) froms.next();
		buf.append((String) _fromClauses.get(fromKey));
	}
}
public String getFromClause2() {
	StringBuffer buf = new StringBuffer(128);
	getFromClause2(buf);
	return buf.toString();
}
public void getFromClause2(StringBuffer buf) {
	getFromClause(buf, _from2);
}
public String getQuery() {
	StringBuffer buf = new StringBuffer(1024);
	getQuery(buf);
	return buf.toString();
}
public void getQuery(StringBuffer buf) {
	getSelectClause(buf);
	getFromClause(buf);
	getWhereClause(buf);
}
public String getQuery2() {
	StringBuffer buf = new StringBuffer(1024);
	getQuery2(buf);
	return buf.toString();
}
public void getQuery2(StringBuffer buf) {
	getSelectClause2(buf);
	getFromClause2(buf);
	getWhereClause2(buf);
}
public String getSelectClause() {
	StringBuffer buf = new StringBuffer(128);
	getSelectClause(buf);
	return buf.toString();
}
public void getSelectClause(StringBuffer buf) {
	getSelectClause(buf, _select, null);
}
private void getSelectClause(StringBuffer buf, SortedSet select, String hint) {
	if (select.isEmpty()) {
		throw new ApiException("Attempt to build a sample query without specifying a SELECT clause");
	}

	Iterator selects = select.iterator();
	buf.append(" SELECT ");
  if (! ApiFunctions.isEmpty(hint)) {
    buf.append('/');
    buf.append("*+ ");
    buf.append(hint);
    buf.append('*');
    buf.append("/ ");
  }
	String selectKey = (String) selects.next();
	MasterSelectData selectData = (MasterSelectData)_selectClauses.get(selectKey);
	buf.append("\n   ");
	buf.append(selectData.getClause());
	while (selects.hasNext()) {
		buf.append(",\n   ");
		selectKey = (String) selects.next();
		selectData = (MasterSelectData)_selectClauses.get(selectKey);
		buf.append(selectData.getClause());
	}
}
public String getSelectClause2() {
	StringBuffer buf = new StringBuffer(128);
	getSelectClause2(buf);
	return buf.toString();
}
public void getSelectClause2(StringBuffer buf) {
	getSelectClause(buf, _select2, _hint2);
}
public String getWhereClause() {
	StringBuffer buf = new StringBuffer(128);
	getWhereClause(buf);
	return buf.toString();
}
public void getWhereClause(StringBuffer buf) {
	getWhereClause(buf, _where);
}
private void getWhereClause(StringBuffer buf, SortedMap where) {
	if (where.isEmpty()) return;

	boolean first = true;
	Iterator wheres = where.keySet().iterator();

	for (String separator = "\n WHERE "; wheres.hasNext(); separator = "\n   AND ") {
		buf.append(separator);
		String whereKey = (String)wheres.next();

		WhereData whereData = (WhereData)where.get(whereKey);
		boolean orNext = whereData.isOrNext();
		boolean addRightParen = orNext;
		if (orNext) buf.append("((");
		
		do {
			whereData = (WhereData)where.get(whereKey);
			int numClauses = whereData.getNumClauses();
			orNext = whereData.isOrNext();

			MasterWhereData masterData = (MasterWhereData)_whereClauses.get(whereKey);
			String clause = masterData.getClause();
			String multiOp = masterData.getMultiConditionOperator();

			if (numClauses == 1) {
				if (first) buf.append("\n   ");
				buf.append(clause);
			}
			else {
				if (first) buf.append("\n   ");
				buf.append("((");
				buf.append(clause);
				for (int i = 1; i < numClauses; i++) {
					buf.append(") ");
					buf.append(multiOp);
					buf.append(" (");
					buf.append(clause);
				}
				buf.append("))");
			}
			if (orNext) {
				buf.append(") OR (");
				whereKey = (String)wheres.next();
			}
			first = false;
		} while (orNext);

		if (addRightParen) buf.append("))");
	}
}
public String getWhereClause2() {
	StringBuffer buf = new StringBuffer(128);
	getWhereClause2(buf);
	return buf.toString();
}
public void getWhereClause2(StringBuffer buf) {
	getWhereClause(buf, _where2);
}
private void joinOrder() {
	_from.add(FROM_ORDER_LINE);
	_where.put(JOIN_ORDER, new WhereData());
}
public List performQuery() {
	StringBuffer buf = new StringBuffer(1024);
	getQuery(buf);
	buf.append(" ORDER BY asm.ardais_id, asm.consent_id, asm.asm_id, sample.sample_barcode_id");
//System.out.println("Time: " + (new java.util.Date(System.currentTimeMillis())).toString());
//System.out.println(buf.toString());

	Method beanMethod = null;
	Connection con = ApiFunctions.getDbConnection();
	try {

		// Create the PreparedStatement and bind all variables.
		PreparedStatement pstmt = con.prepareStatement(buf.toString());
		bindAllVariables(pstmt, _where);

		// Execute the query.
//System.out.println("Before query: " + System.currentTimeMillis());
		ResultSet rs = pstmt.executeQuery();
//System.out.println("Before process query: " + System.currentTimeMillis());

		// Iterate over the results and create a SampleDataBean per row.
		List samples = new ArrayList();
		Map sampleMap = null;
		List sampleList = null;
		boolean isQuery2 = !_select2.isEmpty();
		if (isQuery2) {
			sampleMap = new HashMap();
			sampleList = new ArrayList();
		}
		String[] selects = (String[])_select.toArray(new String[0]);
		int numSelects = selects.length;
		while (rs.next()) {
			SampleDataBean dataBean = new SampleDataBean();
			for (int i = 0; i < numSelects; i++) {
				MasterSelectData selectData = (MasterSelectData)_selectClauses.get(selects[i]);
				String alias = selectData.getAlias();
				beanMethod = selectData.getBeanMethod();
				Object[] args = new Object[1];
				switch (selectData.getDataType()) {
					case DATATYPE_STRING:
						args[0] = rs.getString(alias);
						beanMethod.invoke(dataBean, args);
						break;
					case DATATYPE_INTEGER:
						if (rs.getString(alias) != null) {
							args[0] = new Integer(rs.getInt(alias));
							beanMethod.invoke(dataBean, args);
						}
						break;
					case DATATYPE_DATE:
						args[0] = rs.getDate(alias);
						beanMethod.invoke(dataBean, args);
						break;
					case DATATYPE_BOOLEAN:
						String booleanCheck = rs.getString(alias);
						if ((booleanCheck != null) && (booleanCheck.equals(selectData.getTrueValue()))) {
							args[0] = new Boolean(true);
						}
						else {
							args[0] = new Boolean(false);
						}
						beanMethod.invoke(dataBean, args);
						break;
				}
			}
			samples.add(dataBean);
			if (isQuery2) {
				String sampleId = rs.getString("sample_barcode_id");
				sampleList.add(sampleId);
				sampleMap.put(sampleId, dataBean);
			}
		}
		rs.close();
//System.out.println("After process query: " + System.currentTimeMillis());
		if (isQuery2 && !sampleList.isEmpty()) {
			String sampleIdAlias = whereQuery2SampleIdIn(sampleList.size());
			bindQuery2SampleIds(sampleList);
			performQuery2(con, sampleMap, sampleIdAlias);
		}
		return samples;
	}
	catch (IllegalAccessException e) {
		throw new ApiException("Error invoking method '" + beanMethod.getName() + "' while performing sample query", e);
	}
	catch (InvocationTargetException e) {
		throw new ApiException("Error invoking method '" + beanMethod.getName() + "' while performing sample query", e);
	}
	catch (SQLException e) {
		throw new ApiException("Error performing sample query", e);
	}
	finally {
		ApiFunctions.closeDbConnection(con);
	}	
}
public void performQuery2(Connection con, Map samples, String sampleIdAlias) {
	StringBuffer buf = new StringBuffer(1024);
	getQuery2(buf);
//System.out.println("Query 2: " + buf.toString());

	Method beanMethod = null;
	try {

		// Create the PreparedStatement and bind all variables.
		PreparedStatement pstmt = con.prepareStatement(buf.toString());

		bindAllVariables(pstmt, _where2);		

		// Execute the query.
//System.out.println("Before query 2: " + System.currentTimeMillis());
		ResultSet rs = pstmt.executeQuery();
//System.out.println("Before process query 2: " + System.currentTimeMillis());

		// Iterate over the results and update the SampleDataBean that is
		// in the samples Map passed to this method.
		String[] selects = (String[])_select2.toArray(new String[0]);
		int numSelects = selects.length;
		while (rs.next()) {
			String sampleId = rs.getString(sampleIdAlias);
			SampleDataBean dataBean = (SampleDataBean)samples.get(sampleId);
			for (int i = 0; i < numSelects; i++) {
				MasterSelectData selectData = (MasterSelectData)_selectClauses.get(selects[i]);
				String alias = selectData.getAlias();
				if (!alias.equals(sampleIdAlias)) {
					beanMethod = selectData.getBeanMethod();
					Object[] args = new Object[1];
					switch (selectData.getDataType()) {
						case DATATYPE_STRING:
							args[0] = rs.getString(alias);
							beanMethod.invoke(dataBean, args);
							break;
						case DATATYPE_INTEGER:
							if (rs.getString(alias) != null) {
								args[0] = new Integer(rs.getInt(alias));
								beanMethod.invoke(dataBean, args);
							}
							break;
						case DATATYPE_DATE:
							args[0] = rs.getDate(alias);
							beanMethod.invoke(dataBean, args);
							break;
						case DATATYPE_BOOLEAN:
							String booleanCheck = rs.getString(alias);
							if ((booleanCheck != null) && (booleanCheck.equals(selectData.getTrueValue()))) {
								args[0] = new Boolean(true);
							}
							else {
								args[0] = new Boolean(false);
							}
							beanMethod.invoke(dataBean, args);
							break;
					}
				}
			}
		}
		rs.close();
//System.out.println("After process query: " + System.currentTimeMillis());
	}
	catch (IllegalAccessException e) {
		throw new ApiException("Error invoking method '" + beanMethod.getName() + "' while performing sample query", e);
	}
	catch (InvocationTargetException e) {
		throw new ApiException("Error invoking method '" + beanMethod.getName() + "' while performing sample query", e);
	}
	catch (SQLException e) {
		throw new ApiException("Error performing sample query 2", e);
	}
}
public void selectAllocation() {
  _select.add(SELECT_ALLOCATION);
  _from.add(FROM_SAMPLE);
}
public void selectArdaisId() {
	_select.add(SELECT_ARDAISID);
	whereConsentJoin();
}
public void selectAsmAppearance() {
	_select.add(SELECT_ASM_APPEARANCE);
	whereAsmJoin();
}
public void selectAsmPosition() {
  _select.add(SELECT_ASM_POSITION);
  _from.add(FROM_SAMPLE);
}
public void selectAsmTissueCode() {
	_select.add(SELECT_ASM_TISSUE_CODE);
	whereAsmJoin();
}
public void selectCartAccount() {
  _select.add(SELECT_CART_ACCOUNT);
  whereOnHold();
}
public void selectCartUser() {
  _select.add(SELECT_CART_USER);
  whereOnHold();
}
public void selectConsentDiagnosisCode() {
	_select.add(SELECT_CONSENT_DIAGNOSIS_CODE);
  	whereConsentJoin();
}
public void selectConsentId() {
	_select.add(SELECT_CONSENTID);
	whereConsentJoin();
}
public void selectLimsComments() {
	_select.add(SELECT_SAMPLEID);
	_select2.add(SELECT_LIMS_SAMPLEID);
	_select2.add(SELECT_COMMENTS);
	_from2.add(FROM_LIMS);
}
public void selectLimsInternalComments() {
	_select.add(SELECT_SAMPLEID);
	_select2.add(SELECT_LIMS_SAMPLEID);
	_select2.add(SELECT_INTERNAL_COMMENTS);
	_from2.add(FROM_LIMS);
}
public void selectLimsMicroscopic() {
	_select.add(SELECT_SAMPLEID);
	_select2.add(SELECT_LIMS_SAMPLEID);
	_select2.add(SELECT_ACELLULARSTROMA);
	_select2.add(SELECT_VIABLETUMERCELLS);
	_select2.add(SELECT_VIABLENORMALCOMP);
	_select2.add(SELECT_CELLULARSTROMA);
	_select2.add(SELECT_NECROSIS);

	// 2/24/03 removed the clause checking for api.lims.pathverif.sixth 
	_select2.add(SELECT_VIABLELESIONALCELLS);

	_from2.add(FROM_LIMS);
}
public void selectLineItemId() {
  _select.add(SELECT_LINEITEMID);
  _from.add(FROM_PTS_SAMPLE);
  _where.put(WHERE_IN_PROJECT, null);
}
public void selectSampleDiagnosisCode() {
	_select.add(SELECT_SAMPLE_DIAGNOSIS_CODE);
  _from.add(FROM_SAMPLE);
}
public void selectSampleTissueCode() {
	_select.add(SELECT_SAMPLE_TISSUE_CODE);
  _from.add(FROM_SAMPLE);
}
public void selectQcStatus() {
  _select.add(SELECT_QCSTATUS);
  _from.add(FROM_SAMPLE);
}
public void selectQcVerified() {
  _select.add(SELECT_QCVERIFIED);
  _from.add(FROM_SAMPLE);
}
public void selectSampleId() {
  _select.add(SELECT_SAMPLEID);
  _from.add(FROM_SAMPLE);
}
private void whereAsmJoin() {
	_from.add(FROM_SAMPLE);
	_from.add(FROM_ASM);
	_where.put(WHERE_ASM_JOIN, new WhereData());
}
public void whereConsentEquals() {
	whereConsentJoin();
	_where.put(WHERE_CONSENT_EQUAL, new WhereData());
}
public void whereConsentEquals(int numConsents) {
	whereConsentJoin();
	_where.put(WHERE_CONSENT_EQUAL, new WhereData(numConsents));
}
private void whereConsentJoin() {
	whereAsmJoin();
	_from.add(FROM_CONSENT);
	_where.put(WHERE_CONSENT_JOIN, new WhereData());
}
public void whereConsentNotPulled() {
	whereConsentJoin();
	_where.put(WHERE_CONSENT_NOT_PULLED, new WhereData());
}
public void whereConsentNotRevoked() {
	whereConsentJoin();
	_where.put(WHERE_CONSENT_NOT_REVOKED, new WhereData());
}
public void whereDonorEquals() {
	whereConsentJoin();
	_where.put(WHERE_DONOR_EQUAL, new WhereData());
}
private void whereDonorJoin() {
	whereAsmJoin();
	_from.add(FROM_DONOR);
	_where.put(WHERE_DONOR_JOIN, new WhereData());
}
public void whereInRepository() {
	_from.add(FROM_SAMPLE);
	_where.put(WHERE_IN_REPOSITORY, new WhereData());
}
public void whereInShoppingCart() {
	_from.add(FROM_SAMPLE);
	_where.put(WHERE_IN_SHOPPING_CART, new WhereData());
}
public void whereInventoryStatusNotEqual() {
	_from.add(FROM_SAMPLE);
	_where.put(WHERE_INV_STATUS_NOT_EQUAL, new WhereData());
}
public void whereInventoryStatusNotEqual(int numStatuses) {
	_from.add(FROM_SAMPLE);
	_where.put(WHERE_INV_STATUS_NOT_EQUAL, new WhereData(numStatuses));
}
public void whereIsPathVerified() {
	_from.add(FROM_SAMPLE);
	_where.put(WHERE_IS_PATH_VERIFIED, new WhereData());
}
public void whereLineItem() {
	_from.add(FROM_PTS_SAMPLE);
	_where.put(WHERE_LINEITEM, new WhereData());
	_where.put(WHERE_IN_PROJECT, new WhereData());
}
public void whereLinkedEquals() {
	whereConsentJoin();
	_where.put(WHERE_LINKED_EQUAL, new WhereData());
}
public void whereLinkedEquals(int numLinked) {
	whereConsentJoin();
	_where.put(WHERE_LINKED_EQUAL, new WhereData(numLinked));
}

public void whereLogicalRepository() {
  _from.add(FROM_ARD_LOG_RES);
  _where.put(WHERE_LOGICAL_REPOS, new WhereData());
}

public void whereNotInProject() {
	_from.add(FROM_SAMPLE);
	_where.put(WHERE_NOT_IN_PROJECT, new WhereData());
}
public void whereNotOnHold() {
	_from.add(FROM_SAMPLE);
	_where.put(WHERE_NOT_ON_HOLD, new WhereData());
}
public void whereOnHold() {
	_from.add(FROM_CART_DETAIL);
	_where.put(WHERE_ON_HOLD, new WhereData());
}
public void whereOrderEqual() {
	joinOrder();
	_where.put(WHERE_ORDER_EQUAL, new WhereData());
}
public void whereProject() {
	_from.add(FROM_PTS_SAMPLE);
	_where.put(WHERE_PROJECT, new WhereData());
	_where.put(WHERE_IN_PROJECT, new WhereData());
}
public void wherePulledSample() {
  _from.add(FROM_SAMPLE);
  _where.put(WHERE_PULLED_SAMPLE, new WhereData());
}

public void whereQcStatusNotEqual() {
	_from.add(FROM_SAMPLE);
	_where.put(WHERE_QC_STATUS_NOT_EQUAL, new WhereData());
}
public void whereQcStatusNotNull() {
	_from.add(FROM_SAMPLE);
	_where.put(WHERE_QC_STATUS_NOT_NULL, new WhereData());
}
private String whereQuery2SampleIdIn(int numSampleIds) {
	_from2.add(FROM_LIMS);
	_where2.put(WHERE_LIMS, new WhereData(numSampleIds));
	MasterSelectData sampleIdSelect = (MasterSelectData)_selectClauses.get(SELECT_LIMS_SAMPLEID);
	return sampleIdSelect.getAlias();
}
public void whereSalesStatusEqual() {
	_from.add(FROM_SAMPLE);
	_where.put(WHERE_SALES_STATUS_EQUAL, new WhereData());
}
public void whereSalesStatusNotEqual() {
	_from.add(FROM_SAMPLE);
	_where.put(WHERE_SALES_STATUS_NOT_EQUAL, new WhereData());
}
public void whereSampleEquals() {
	_from.add(FROM_SAMPLE);
	_where.put(WHERE_SAMPLE_EQUAL, new WhereData());
}
public void whereSampleEquals(int numSamples) {
	_from.add(FROM_SAMPLE);
	_where.put(WHERE_SAMPLE_EQUAL, new WhereData(numSamples));
}
public void whereUnrestrictedOrRestrictedForAccount() {
	_from.add(FROM_SAMPLE);
	_where.put(WHERE_UNREST_OR_REST_ACCOUNT, new WhereData());
}
}
