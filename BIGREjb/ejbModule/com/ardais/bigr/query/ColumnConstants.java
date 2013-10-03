package com.ardais.bigr.query;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.configuration.SimpleList;
import com.ardais.bigr.configuration.SystemConfiguration;
import com.ardais.bigr.security.SecurityInfo;

/**
 * @author dfeldman
 *
 * Manages the global list of available columns and associated classes.  Front-end or other
 * external components can update this list, since we don't know here what columns are
 * available.
 * 
 * responsibilities:  Registers instances, builds unique keys, enforces uniqueness, creates 
 * instances via introspection.
 * 
 */
public class ColumnConstants {

  // ----------------- predefined column constants   ----------------------
  //NOTE: if you change or add to these values, please make sure to make any corresponding
  //      changes to systemConfiguration.xml and defaultResultsView.xml, as the values of
  //      these constants are used in both files.
  public static final String _AGE_AT_COLLECTION = "AGE_AT_COLLECTION";
  public static final String _APPEARANCE = "APPEARANCE";
  public static final String _ASM_POS = "ASM_POS_WITH_PV";
  public static final String _ASM_OR_PREP = "ASM_OR_PREP";
  public static final String _ASM_TISSUE = "ASM_TISSUE";
  public static final String _BUFFER_TYPE = "BUFFER_TYPE";
  public static final String _CASE_ALIAS = "CASE_ALIAS";
  public static final String _CASE_ID = "CASE_ID";
  public static final String _CELLS_PER_ML = "CELLS_PER_ML";
  public static final String _COMPOSITION_ACELLSTROMA = "COMPOSITION_ACELLSTROMA";
  public static final String _COMPOSITION_ALL = "COMPOSITION_ALL";
  public static final String _COMPOSITION_CELLSTROMA = "COMPOSITION_CELLSTROMA";
  public static final String _COMPOSITION_LESION = "COMPOSITION_LESION";
  public static final String _COMPOSITION_NECROSIS = "COMPOSITION_NECROSIS";
  public static final String _COMPOSITION_NORMAL = "COMPOSITION_NORMAL";
  public static final String _COMPOSITION_TUMOR = "COMPOSITION_TUMOR";
  public static final String _CONCENTRATION = "CONCENTRATION";
  public static final String _DATE_OF_COLLECTION = "DATE_OF_COLLECTION";
  public static final String _DATE_OF_PRESERVATION = "DATE_OF_PRESERVATION";
  public static final String _DELIVERY_TYPE_DROPDOWN_CTRL = "DELIVERY_TYPE_DROPDOWN_CTRL";
  public static final String _DIAGNOSIS_DDC = "DIAGNOSIS_DDC";
  public static final String _DIAGNOSIS_ILTDS = "DIAGNOSIS_ILTDS";
  public static final String _DIAGNOSIS_LIMS = "DIAGNOSIS_LIMS";
  public static final String _DIAGNOSTIC_RESULT = "DIAGNOSTIC_RESULT";
  public static final String _DONOR_ALIAS = "DONOR_ALIAS";
  public static final String _DONOR_COMMENTS = "DONOR_COMMENTS";
  public static final String _DONOR_ID = "DONOR_ID";
  public static final String _ELAPSED_TIME = "ELAPSED_TIME";
  public static final String _FIXATIVE_TYPE = "FIXATIVE_TYPE";
  public static final String _FORMAT_DETAIL = "FORMAT_DETAIL";
  public static final String _GENDER = "GENDER";
  public static final String _HNG = "HNG";
  public static final String _HNG_TNM_STAGE = "HNG_TNM_STAGE";
  public static final String _IMAGES = "IMAGES";
  public static final String _IMAGES_FIXED = "IMAGES_FIXED";
  public static final String _INVENTORY_STATUS = "INVENTORY_STATUS";
  public static final String _KC_DATA = "KC_DATA";
  public static final String _LOGICAL_REPOSITORY = "LOGICAL_REPOSITORY";
  public static final String _NUM_SECTIONS = "NUM_SECTIONS";
  public static final String _PERCENT_VIABILITY = "PERCENT_VIABILITY";
  public static final String _PREPARED_BY = "PREPARED_BY";
  public static final String _PROCEDURE = "PROCEDURE";
  public static final String _PROJECT_STATUS = "PROJECT_STATUS";
  public static final String _PV_NOTES = "PV_NOTES";
  public static final String _PV_NOTES_INTERNAL = "PV_NOTES_INTERNAL";
  public static final String _QC_POSTED = "QC_POSTED";
  public static final String _QC_PROJ_INV_SALES = "QC_PROJ_INV_SALES";
  public static final String _QC_STATUS = "QC_STATUS";
  public static final String _REPEAT_DONOR = "REPEAT_DONOR";
  public static final String _RNA_AMT_AVAIL = "RNA_AMT_AVAIL";
  public static final String _RNA_AMT_INPUT_CTRL = "RNA_AMT_INPUT_CTRL";
  public static final String _RNA_AMT_REMAINING = "RNA_AMT_REMAINING";
  public static final String _RNA_ASM_POS = "RNA_ASM_POS";
  public static final String _RNA_BIO_RATIO = "RNA_BIO_RATIO";
  public static final String _RNA_CASE_EXHAUSTED = "RNA_CASE_EXHAUSTED";
  public static final String _RNA_HOLD_STATUS = "RNA_HOLD_STATUS";
  public static final String _RNA_ID = "RNA_ID";
  public static final String _RNA_IMAGE_PREVIEW = "RNA_IMAGE_PREVIEW";
  public static final String _RNA_INVENTORY_STATUS = "RNA_INVENTORY_STATUS";
  public static final String _RNA_NOTES = "RNA_NOTES";
  public static final String _RNA_ON_HOLD_THIS_CART = "RNA_ON_HOLD_THIS_CART";
  public static final String _RNA_POOLED_TISSUE = "RNA_POOLED_TISSUE";
  public static final String _RNA_PREP = "RNA_PREP";
  public static final String _RNA_QUALITY = "RNA_QUALITY";
  public static final String _RNA_SALES_STATUS = "RNA_SALES_STATUS";
  public static final String _RNA_VIAL_ID = "RNAVIALID";
  public static final String _ROW_INDEX = "ROW_INDEX";
  public static final String _SALES_STATUS = "SALES_STATUS";
  public static final String _SAMPLE_ACTION = "SAMPLE_ACTION";
  public static final String _SAMPLE_ALIAS = "SAMPLE_ALIAS";
  public static final String _SAMPLE_ID = "SAMPLE_ID";
  public static final String _SAMPLE_LOCATION = "SAMPLE_LOCATION";
  public static final String _SAMPLE_TYPE = "SAMPLE_TYPE";
  public static final String _SAMPLE_SOURCE = "SAMPLE_SOURCE";
  public static final String _SELECT_BOX_WITH_PV = "SELECT_BOX_WITH_PV";
  public static final String _SAMPLE_BOX_LOCATION ="SAMPLE_BOX_LOCATION";
  public static final String _SELECT_COMPOSITE = "SELECT_COLUMN_COMPOSITE";
  public static final String _SOURCE = "SOURCE";
  public static final String _STAGE_GROUPING = "STAGE_GROUPING";
  public static final String _TISSUE = "TISSUE";
  public static final String _TNM = "TNM";
  public static final String _TOTAL_NUM_OF_CELLS = "TOTAL_NUM_OF_CELLS";
  public static final String _VOLUME = "VOLUME";
  public static final String _WEIGHT = "WEIGHT";
  public static final String _YIELD = "YIELD";
  public static final String _RACE = "RACE";

  private static String[] _standardTissueColumns;
  private static String[] _standardRnaColumns;
  private static String[] _standardGenericColumns;
  private static Set _externallyVisibleColumns;
  private static Set _allColumns;

  // -------- standard profile definitions -----

  public static String[] standardTissueColumns() {
    if (_standardTissueColumns == null) {
      _standardTissueColumns =
        new String[] {
          _ROW_INDEX,
          _SELECT_BOX_WITH_PV,
          _SAMPLE_ACTION,
          _QC_PROJ_INV_SALES,
          _INVENTORY_STATUS,
          _PROJECT_STATUS,
          _DONOR_ID,
          _DONOR_ALIAS,
          _CASE_ID,
          _CASE_ALIAS,
          _ASM_POS,
          _SAMPLE_TYPE,
          _SAMPLE_SOURCE,
          _FORMAT_DETAIL,
          _FIXATIVE_TYPE,
          _LOGICAL_REPOSITORY,
          _SAMPLE_LOCATION,
          _DIAGNOSIS_LIMS,
          _DIAGNOSIS_DDC,
          _TISSUE,
          _ASM_TISSUE,
          _APPEARANCE,
          _COMPOSITION_ALL,
          _PV_NOTES,
          _IMAGES,
          _PV_NOTES_INTERNAL,
          _DONOR_COMMENTS,
          _DIAGNOSIS_ILTDS,
          _DIAGNOSTIC_RESULT,
          _HNG_TNM_STAGE,
          _AGE_AT_COLLECTION,
          _GENDER,
          _SAMPLE_ID,
          _SAMPLE_ALIAS,
          _SOURCE,
          _NUM_SECTIONS,
          _REPEAT_DONOR,
          _VOLUME,
          _WEIGHT,
          _BUFFER_TYPE,
          _TOTAL_NUM_OF_CELLS,
          _CELLS_PER_ML,
          _PERCENT_VIABILITY,
          _DATE_OF_COLLECTION,
          _DATE_OF_PRESERVATION,
          _ELAPSED_TIME,
          _CONCENTRATION,
          _YIELD,
          _PREPARED_BY,
          _PROCEDURE,
          _IMAGES_FIXED};
    }
    return _standardTissueColumns;
  }

  public static String[] standardRnaColumns() {
    if (_standardRnaColumns == null) {
      _standardRnaColumns =
        new String[] {
          _ROW_INDEX,
          _SELECT_BOX_WITH_PV,
          _SAMPLE_ACTION,
          _RNA_ON_HOLD_THIS_CART,
          _RNA_AMT_INPUT_CTRL,
          _RNA_INVENTORY_STATUS,
          _RNA_SALES_STATUS,
          _RNA_HOLD_STATUS,
          _DONOR_ID,
          _DONOR_ALIAS,
          _CASE_ID,
          _CASE_ALIAS,
          _LOGICAL_REPOSITORY,
          _RNA_ASM_POS,
          _RNA_PREP,
          _RNA_AMT_AVAIL,
          _RNA_AMT_REMAINING,
          _RNA_POOLED_TISSUE,
          _RNA_CASE_EXHAUSTED,
          _RNA_QUALITY,
          _DIAGNOSIS_LIMS,
          _DIAGNOSIS_DDC,
          _TISSUE,
          _APPEARANCE,
          _COMPOSITION_ALL,
          _PV_NOTES,
          _RNA_BIO_RATIO,
          _RNA_NOTES,
          _IMAGES,
          _PV_NOTES_INTERNAL,
          _DONOR_COMMENTS,
          _DIAGNOSIS_ILTDS,
          _DIAGNOSTIC_RESULT,
          _HNG_TNM_STAGE,
          _AGE_AT_COLLECTION,
          _GENDER,
          _RNA_ID,
          _FORMAT_DETAIL,
          _FIXATIVE_TYPE,
          _SOURCE,
          _REPEAT_DONOR,
          _IMAGES_FIXED };
    }
    return _standardRnaColumns;
  }

  // NOTE: standardGenericColumns is the list of columns that are shown in places like the
  // hold list view where a single table can display a mixture of regular samples and RNA.
  // If you add a column here, please make sure that the column is smart enough to display
  // the right information for the particular type of item being displayed in a row.
  // For example, if the column should show something different based on whether the row
  // is a tissue row or an RNA row, make sure that your column class actually handles that.
  //
  public static String[] standardGenericColumns() {
    if (_standardGenericColumns == null) {
      _standardGenericColumns =
        new String[] {
          _ROW_INDEX,
          _SELECT_COMPOSITE,
          _SAMPLE_ACTION,
          _RNA_ON_HOLD_THIS_CART,
          _RNA_AMT_INPUT_CTRL,
          _SAMPLE_TYPE,
          _SAMPLE_SOURCE,
          _INVENTORY_STATUS,
          _PROJECT_STATUS,
          _DONOR_ID,
          _DONOR_ALIAS,
          _CASE_ID,
          _CASE_ALIAS,
          _ASM_OR_PREP,
          _FORMAT_DETAIL,
          _FIXATIVE_TYPE,
          _LOGICAL_REPOSITORY,
          _SAMPLE_LOCATION,
          _DIAGNOSIS_LIMS,
          _DIAGNOSIS_DDC,
          _TISSUE,
          _ASM_TISSUE,
          _APPEARANCE,
          _COMPOSITION_ALL,
          _PV_NOTES,
          _RNA_BIO_RATIO,
          _IMAGES,
          _DIAGNOSIS_ILTDS,
          _DIAGNOSTIC_RESULT,
          _HNG_TNM_STAGE,
          _AGE_AT_COLLECTION,
          _GENDER,
          _SAMPLE_ID,
          _SAMPLE_ALIAS,
          _VOLUME,
          _WEIGHT,
          _BUFFER_TYPE,
          _TOTAL_NUM_OF_CELLS,
          _CELLS_PER_ML,
          _PERCENT_VIABILITY,
          _DATE_OF_COLLECTION,
          _DATE_OF_PRESERVATION,
          _ELAPSED_TIME,
          _CONCENTRATION,
          _YIELD,
          _PREPARED_BY,
          _PROCEDURE,
          _IMAGES_FIXED};
    }
    return _standardGenericColumns;
  }

  // NOTE: resultsViewColumns is the list of all columns that are available for creating result 
  // views.  This list contains the universe of available columns, and it should be noted that
  // not all columns are available for all user types.  Columns that are exclusive to a given 
  // user type are listed below. 
  public static List getResultsViewColumns() {
    SimpleList list = SystemConfiguration.getSimpleList(SystemConfiguration.SIMPLE_LIST_RESULTS_VIEW_COLUMNS);
    if (list == null) {
      list = new SimpleList();
    }
    return new ArrayList(list.getListItems());
  }
  
  // NOTE: resultsViewSystemOwnerOnlyColumns is the list of columns that are available for creating 
  // results views only available to system owner users
  public static List getResultsViewSystemOwnerColumns() {
    SimpleList list = SystemConfiguration.getSimpleList(SystemConfiguration.SIMPLE_LIST_RESULTS_VIEW_COLUMNS_SO_ONLY);
    if (list == null) {
      list = new SimpleList();
    }
    return new ArrayList(list.getListItems());
  }
  
  // NOTE: resultsViewSystemOwnerAndDIOnlyColumns is the list of columns that are available for creating 
  // result views only available to system owner and DI users
  public static List getResultsViewSystemOwnerAndDIOnlyColumns() {
    SimpleList list = SystemConfiguration.getSimpleList(SystemConfiguration.SIMPLE_LIST_RESULTS_VIEW_COLUMNS_SO_AND_DI_ONLY);
    if (list == null) {
      list = new SimpleList();
    }
    return new ArrayList(list.getListItems());
  }
  
  public static Set getAccessibleResultsViewColumns(SecurityInfo securityInfo) {
    List bigrColumns = getResultsViewColumns();
    //customer users need to have the system owner and DI columns removed
    if (securityInfo.isInRoleCustomer()) {
      bigrColumns.removeAll(getResultsViewSystemOwnerColumns());
      bigrColumns.removeAll(getResultsViewSystemOwnerAndDIOnlyColumns());
    }
    //DI users need to have the system owner columns removed
    else if (securityInfo.isInRoleDi()) {
      bigrColumns.removeAll(getResultsViewSystemOwnerColumns());
    }
    //SO users don't have any columns removed
    else {
    }
    return ApiFunctions.safeSet(bigrColumns.toArray()); 
    
  }

  public static Set externallyVisibleColumns() {
    if (_externallyVisibleColumns == null) {
      Set s = new HashSet();
      s.add(_ROW_INDEX);
      s.add(_SELECT_BOX_WITH_PV);
      s.add(_SELECT_COMPOSITE);
      s.add(_SAMPLE_ACTION);
      s.add(_DELIVERY_TYPE_DROPDOWN_CTRL);
      s.add(_RNA_ON_HOLD_THIS_CART);
      s.add(_RNA_AMT_INPUT_CTRL);
      s.add(_RNA_AMT_INPUT_CTRL);
      s.add(_CASE_ID);
      s.add(_DONOR_ID);
      s.add(_ASM_POS);
      s.add(_FORMAT_DETAIL);
      s.add(_FIXATIVE_TYPE);
      s.add(_SAMPLE_TYPE);
      s.add(_SAMPLE_SOURCE);
      s.add(_ASM_OR_PREP);
      s.add(_RNA_PREP);
      s.add(_LOGICAL_REPOSITORY);
      s.add(_SAMPLE_LOCATION);
      s.add(_DIAGNOSIS_LIMS);
      s.add(_DIAGNOSIS_DDC);
      s.add(_TISSUE);
      s.add(_APPEARANCE);
      s.add(_COMPOSITION_ALL);
      s.add(_COMPOSITION_NORMAL);
      s.add(_COMPOSITION_CELLSTROMA);
      s.add(_COMPOSITION_ACELLSTROMA);
      s.add(_COMPOSITION_LESION);
      s.add(_COMPOSITION_TUMOR);
      s.add(_COMPOSITION_NECROSIS);
      s.add(_PV_NOTES);
      s.add(_RNA_BIO_RATIO);
      s.add(_RNA_NOTES);
      s.add(_IMAGES);
      s.add(_IMAGES_FIXED);
      s.add(_DONOR_COMMENTS);
      s.add(_DIAGNOSIS_ILTDS);
      s.add(_DIAGNOSTIC_RESULT);
      s.add(_HNG_TNM_STAGE);
      s.add(_HNG);
      s.add(_TNM);
      s.add(_STAGE_GROUPING);
      s.add(_AGE_AT_COLLECTION);
      s.add(_GENDER);
      s.add(_SAMPLE_ID);
      s.add(_INVENTORY_STATUS);
      s.add(_PROJECT_STATUS);
      s.add(_RNA_AMT_AVAIL);
      s.add(_RNA_AMT_REMAINING);
      s.add(_RNA_ID);
      s.add(_DONOR_ALIAS);
      s.add(_CASE_ALIAS);
      s.add(_SAMPLE_ALIAS);
      s.add(_RNA_HOLD_STATUS);
      s.add(_VOLUME);
      s.add(_WEIGHT);
      s.add(_BUFFER_TYPE);
      s.add(_TOTAL_NUM_OF_CELLS);
      s.add(_CELLS_PER_ML);
      s.add(_PERCENT_VIABILITY);
      s.add(_DATE_OF_COLLECTION);
      s.add(_DATE_OF_PRESERVATION);
      s.add(_ELAPSED_TIME);
      s.add(_CONCENTRATION);
      s.add(_YIELD);
      s.add(_KC_DATA);
      s.add(_PREPARED_BY);
      s.add(_PROCEDURE);
      s.add(_SAMPLE_BOX_LOCATION);
      s.add(_RACE);
      _externallyVisibleColumns = Collections.unmodifiableSet(s);
    }
    return _externallyVisibleColumns;
  }

  public static Set allColumns() {
    if (_allColumns == null) {
      Set s = new HashSet(externallyVisibleColumns());

      String[] columns = standardTissueColumns();
      for (int i = 0; i < columns.length; i++) {
        s.add(columns[i]);
      }

      columns = standardRnaColumns();
      for (int i = 0; i < columns.length; i++) {
        s.add(columns[i]);
      }

      columns = standardGenericColumns();
      for (int i = 0; i < columns.length; i++) {
        s.add(columns[i]);
      }

      _allColumns = Collections.unmodifiableSet(s);
    }
    return _allColumns;
  }
}
