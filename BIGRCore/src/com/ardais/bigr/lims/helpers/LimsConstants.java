package com.ardais.bigr.lims.helpers;

import java.util.Collection;
import java.util.Vector;

import com.ardais.bigr.iltds.databeans.LabelValueBean;

public class LimsConstants {
	
	//Constant used for default feature display order
	public final static int LIMS_DEFAULT_FEATURE_DISPLAY_ORDER = -1;
  
  //Constants used in the building of pathology evaluation external comments
  public final static String LIMS_PV_EXT_COMMENT_HEADER_TUMOR = "Tumor: ";
  public final static String LIMS_PV_EXT_COMMENT_HEADER_CELLULAR = "Tumor Stroma (Cellular): ";
  public final static String LIMS_PV_EXT_COMMENT_HEADER_HYPOACELLULAR = "Tumor Stroma (Hypo/Acellular): ";
  public final static String LIMS_PV_EXT_COMMENT_HEADER_LESION = "Lesion ";
  public final static String LIMS_PV_EXT_COMMENT_HEADER_INFLAMMATION = "Inflammation: ";
  public final static String LIMS_PV_EXT_COMMENT_HEADER_STRUCTURES = "Non Tumor Structures: ";
  public final static String LIMS_PV_EXT_COMMENT_HEADER_OTHERS = "Other Features/Comments: ";
	
	//Constants used for yes/no values in the database
	public final static String LIMS_DB_YES = "Y";
	public final static String LIMS_DB_YES_TEXT = "Yes";
	public final static String LIMS_DB_NO = "N";
	public final static String LIMS_DB_NO_TEXT = "No";
	
	//Constants for LIMS PV Status
	public final static String PV_STATUS_UNPVED = "Not PVed";
	public final static String PV_STATUS_PVED = "PVed";
	public final static String PV_STATUS_PULLED = "Pulled";
	public final static String PV_STATUS_RELEASED = "Released";
	public final static String PV_STATUS_QCPOSTED = "QCPosted";
	
	//Lims chunking constants
	public final static String LIMS_CHUNK_INDEX = "limsChunkIndex";
	public final static String LIMS_CURRENT_CHUNK = "limsCurrentChunk";
	
	//Constant prepended to evaluation ids
	public final static String PE_ID_PREFIX = "PV";
	
	//Constant prepended to the reason provided for marking a sample discordant in the
	//"Mark Sample Discordant" transaction
	public final static String LIMS_DISCORDANCE_PREFIX = "Major Discordance: ";
	
	//Constants prepended to any sample-related tissue and diagnosis fields on the 
	//Create and Manage Evaluations forms that are "others"
	public final static String LIMS_OTHER_DIAGNOSIS_PREFIX = "Other Diagnosis: ";
	public final static String LIMS_OTHER_TISSUE_PREFIX = "Other Tissue: ";
	
	//Automatic pull/discordant reasons
	public final static String AUTO_PULL_REASON = "Automatically pulled based on Microscopic Appearance rule: \"Tumor, Lesion, and Normal cells total 0.\"";
	public final static String AUTO_DISCORDANT_REASON1 = "Automatically marked major discordant based on Microscopic Appearance rule: \"PV Evaluation for sample with NON-NEOPLASTIC or NO DIAGNOSTIC ABNORMALITY Donor Institution Dx had a PV Tumor Cell value > 0.\"";
	public final static String AUTO_DISCORDANT_REASON2 = "Automatically marked major discordant based on Microscopic Appearance rule: \"PV Evaluation for sample with NON-NEOPLASTIC Donor Institution Dx was given PV Dx of NEOPLASTIC.\"";

  //Constants for Incident Reports
  public final static String INCIDENT_ACTION_OTHER = "CA01296C";
  public final static String INCIDENT_ACTION_REPV = "CA01292C";
  public final static String INCIDENT_ACTION_PULL = "CA01295C";
  public final static String INCIDENT_RE_PV_REASON_WRONG_FIELD = "CA01297C";
	
	//Constants used for coding the method used to create a pathology evaluation
	public final static String LIMS_PE_CREATE_TYPE_COPY = "C";
	public final static String LIMS_PE_CREATE_TYPE_EDIT = "E";
	public final static String LIMS_PE_CREATE_TYPE_NEW = "N";
	
	//Constants used to indicate that a pathology evaluation feature is "other"
	public final static String OTHER_TUMOR = "CA00620C";
	public final static String OTHER_CELLULAR = "CA00616C";
	public final static String OTHER_HYPOACELLULAR = "CA00617C";
	public final static String OTHER_INFLAMMATION = "CA00615C";
	public final static String OTHER_EXTERNAL = "CA00619C";
	public final static String OTHER_INTERNAL = "CA00618C";
	public final static String OTHER_STRUCTURE = "CA00872C";
  
  //Constant used to represent a diagnosis hierarchy label
  public final static String NON_NEOPLASTIC_DISEASE = "NON-NEOPLASTIC DISEASE";
	
	//Constants used for Composition_Type column in LIMS_PE_FEATURES table
	public final static String FEATURE_TYPE_TUMOR = "TUMOR";
	public final static String FEATURE_TYPE_CELLULAR = "CELLULAR";
	public final static String FEATURE_TYPE_HYPOACELLULAR = "HYPOACELLULAR";
	public final static String FEATURE_TYPE_LESION = "LESION";
	public final static String FEATURE_TYPE_INFLAMMATION = "INFLAMMATION";
	public final static String FEATURE_TYPE_EXTERNAL = "EXTERNAL";
	public final static String FEATURE_TYPE_INTERNAL = "INTERNAL";
	public final static String FEATURE_TYPE_STRUCTURE = "STRUCTURE";
	
	//Constants used to indicate the severity of an inflammation
	public final static int INFLAMMATION_MILD = 1;
	public final static String INFLAMMATION_MILD_TEXT = "Mild";
	public final static int INFLAMMATION_MODERATE = 2;
	public final static String INFLAMMATION_MODERATE_TEXT = "Moderate";
	public final static int INFLAMMATION_SEVERE = 3;
	public final static String INFLAMMATION_SEVERE_TEXT = "Severe";
  
  //Constants used to specify the maximum length of features
  public final static int LIMS_OCE_FEATURE_MAXLENGTH = 200;
  public final static int LIMS_NON_OCE_FEATURE_MAXLENGTH = 1000;
  public final static int LIMS_CONCATENATED_COMMENT_MAXLENGTH = 4000;
	
	//Constants for default select values for Path QC
	public final static String DEFAULT_ALL = "All (Default)";
	public final static String PATH_QC_SAMPLES_NOT_PVD = "notPVd";	
	public final static String PATH_QC_SAMPLES_FOR_RELEASE = "release";
	public final static String PATH_QC_RELEASED_SAMPLES = "releasedSamples";
	public final static String PATH_QC_PULLED_SAMPLES = "pulledSamples";	
	public final static String PATH_QC_POSTING_SAMPLES = "postingSamples";
	public final static String PATH_QC_INPROCESS_SAMPLES = "inprocessSamples";
	public final static String PATH_QC_AWAITING_SAMPLES = "awaitingSamples";
	public final static String PATH_QC_BOTH_SAMPLES = "bothSamples";
	
	//Constants for PathQC sort columns
	//Note - if you add/remove any constants here, you must update BtxPerformerPathologyOperation.performGetPathQCSampleSummary
	public final static String PATH_QC_SORT_QCSTATUS = "QCStatus";
	public final static String PATH_QC_SORT_QCSTATUS_LABEL = "QC Status";
	public final static String PATH_QC_SORT_CASEID = "CaseId";
	public final static String PATH_QC_SORT_CASEID_LABEL = "Case ID/ASM position";
	public final static String PATH_QC_SORT_SAMPLEID = "SampleId";
	public final static String PATH_QC_SORT_SAMPLEID_LABEL = "Sample ID";
	public final static String PATH_QC_SORT_PATHOLOGIST = "Pathologist";
	public final static String PATH_QC_SORT_PATHOLOGIST_LABEL = "Pathologist Creating Evaluation";
	public final static String PATH_QC_SORT_REPORTEDDATE = "ReportedDate";
	public final static String PATH_QC_SORT_REPORTEDDATE_LABEL = "Evaluation Reported Date";
	public final static String PATH_QC_SORT_PULLDATE = "PullDate";
	public final static String PATH_QC_SORT_PULLDATE_LABEL = "Pull Date";
	public final static String PATH_QC_SORT_CREATIONDATE = "CreationDate";
	public final static String PATH_QC_SORT_CREATIONDATE_LABEL = "Evaluation Creation Date";
	
	//Constants for PathQC sort order
	public final static String SORT_ORDER_ASC = "asc";
	public final static String SORT_ORDER_ASC_LABEL = "Ascending";
	public final static String SORT_ORDER_DESC = "desc";
	public final static String SORT_ORDER_DESC_LABEL = "Descending";
	
	//Constants used for btx action forward
	public final static String BTX_ACTION_FORWARD_SUCCESS = "success";
    public final static String BTX_ACTION_FORWARD_GENSLIDES = "genSlides";
    public final static String BTX_ACTION_FORWARD_GENREPORT = "getReport";
    public final static String BTX_ACTION_FORWARD_WARNING = "warning";
	
	//Constants and collections used for sample locations.
	//NOTE - if you add new locations here, make sure you add them to the
	//collection of valid (and user selectable, if applicable) locations in 
	//the static initialization block below, and update the getLocationDescription method.
	public final static String LIMS_LOCATION_HISTOLOGY = "2";
	public final static String LIMS_LOCATION_HISTOLOGY_TEXT = "Histology Lab";
	public final static String LIMS_LOCATION_TRASH = "3";
	public final static String LIMS_LOCATION_TRASH_TEXT = "Trash";
	public final static String LIMS_LOCATION_PATHOLOGY = "4";
	public final static String LIMS_LOCATION_PATHOLOGY_TEXT = "Pathology Lab";
	public final static String LIMS_LOCATION_STORAGE = "5";
	public final static String LIMS_LOCATION_STORAGE_TEXT = "Storage";
	
	public final static String LIMS_TX_RELEASE = "release";
	public final static String LIMS_TX_UNRELEASE = "unrelease";
	public final static String LIMS_TX_PULL = "Pull";
	public final static String LIMS_TX_UNPULL = "Unpull";
	public final static String LIMS_TX_QCPOST = "qcpost";
	public final static String LIMS_TX_UNQCPOST = "unqcpost";
  public final static String LIMS_TX_QCEDITPV = "qceditpv";
	public final static String LIMS_TX_UPDATEPV = "updatepv";
    public final static String LIMS_TX_NONE = "none";
	
	//Path Verification varning messages
	public final static String LIMS_WARNING_PULL = "Values you entered for Microscopic Features (Tumor, Lesion, Normal) will cause this sample to be pulled.\\nIf the sample is already reported that evaluation will be marked unreported, and if the sample is released it will be marked unreleased.\\nPress OK to continue, Cancel to edit the values.";
	public final static String LIMS_WARNING_PULL_NOTREPORT = "Values you entered for Microscopic Features (Tumor, Lesion, Normal) will cause this sample to be pulled and sample will not be automatically reported.\\nIf the sample is already reported that evaluation will be marked unreported, and if the sample is released it will be marked unreleased.\\nPress OK to continue, Cancel to edit the values.";
	public final static String LIMS_WARNING_DISC = "Values you entered for Microscopic Features and/or Diagnosis will cause this sample to be marked discordant.\\nIf the sample is already reported that evaluation will be marked unreported, and if the sample is released it will be marked unreleased.\\nPress OK to continue, Cancel to edit the values.";
	public final static String LIMS_WARNING_DISC_NOTREPORT = "Values you entered for Microscopic Features and/or Diagnosis will cause this sample to be marked discordant and sample will not be automatically reported.\\nIf the sample is already reported that evaluation will be marked unreported, and if the sample is released it will be marked unreleased.\\nPress OK to continue, Cancel to edit the values.";

	
	public final static Collection VALID_LIMS_LOCATIONS = new Vector();
	public final static Collection VALID_USER_SELECTABLE_LIMS_LOCATIONS = new Vector();
    
    public final static String MSG_PRINTED_SINGLE_SLIDELABEL = "Printed label for slide #.";
    public final static String MSG_PRINTED_ALL_SLIDELABELS = "Printed # slide(s).";
	
	static {
		VALID_LIMS_LOCATIONS.add(LIMS_LOCATION_HISTOLOGY);
		VALID_LIMS_LOCATIONS.add(LIMS_LOCATION_PATHOLOGY);
		VALID_LIMS_LOCATIONS.add(LIMS_LOCATION_STORAGE);
		VALID_LIMS_LOCATIONS.add(LIMS_LOCATION_TRASH);

		VALID_USER_SELECTABLE_LIMS_LOCATIONS.add(new LabelValueBean(LIMS_LOCATION_HISTOLOGY_TEXT,LIMS_LOCATION_HISTOLOGY));
		VALID_USER_SELECTABLE_LIMS_LOCATIONS.add(new LabelValueBean(LIMS_LOCATION_PATHOLOGY_TEXT,LIMS_LOCATION_PATHOLOGY));
		VALID_USER_SELECTABLE_LIMS_LOCATIONS.add(new LabelValueBean(LIMS_LOCATION_TRASH_TEXT,LIMS_LOCATION_TRASH));
	}
	
	public final static String getLocationDescription(String locationCode) {
		if (locationCode == null)
			return "Unknown location";
		else if (locationCode.equals(LIMS_LOCATION_HISTOLOGY))
			return LIMS_LOCATION_HISTOLOGY_TEXT;
		else if (locationCode.equals(LIMS_LOCATION_PATHOLOGY))
			return LIMS_LOCATION_PATHOLOGY_TEXT;
		else if (locationCode.equals(LIMS_LOCATION_STORAGE))
			return LIMS_LOCATION_STORAGE_TEXT;
		else if (locationCode.equals(LIMS_LOCATION_TRASH))
			return LIMS_LOCATION_TRASH_TEXT;
		else
			return "Unknown location";
	}
}
