package com.ardais.bigr.library.web.column.configuration;

import java.util.Collections;
import java.util.List;

import com.ardais.bigr.library.web.column.AgeAtCollectionColumn;
import com.ardais.bigr.library.web.column.AppearanceColumn;
import com.ardais.bigr.library.web.column.AsmPosColumn;
import com.ardais.bigr.library.web.column.AsmPrepColumn;
import com.ardais.bigr.library.web.column.AsmTissueColumn;
import com.ardais.bigr.library.web.column.BufferTypeColumn;
import com.ardais.bigr.library.web.column.CaseAliasColumn;
import com.ardais.bigr.library.web.column.CaseIdColumn;
import com.ardais.bigr.library.web.column.CellsPerMlColumn;
import com.ardais.bigr.library.web.column.CompositionAcellStromaColumn;
import com.ardais.bigr.library.web.column.CompositionCellStromaColumn;
import com.ardais.bigr.library.web.column.CompositionLesionColumn;
import com.ardais.bigr.library.web.column.CompositionNecrosisColumn;
import com.ardais.bigr.library.web.column.CompositionNormalColumn;
import com.ardais.bigr.library.web.column.CompositionTumorColumn;
import com.ardais.bigr.library.web.column.CompositionValuesColumn;
import com.ardais.bigr.library.web.column.ConcentrationColumn;
import com.ardais.bigr.library.web.column.DateOfCollectionColumn;
import com.ardais.bigr.library.web.column.DateOfPreservationColumn;
import com.ardais.bigr.library.web.column.DeliveryTypeColumn;
import com.ardais.bigr.library.web.column.DiagnosisDdcColumn;
import com.ardais.bigr.library.web.column.DiagnosisIltdsColumn;
import com.ardais.bigr.library.web.column.DiagnosisLimsColumn;
import com.ardais.bigr.library.web.column.DiagnosticResultColumn;
import com.ardais.bigr.library.web.column.DonorAliasColumn;
import com.ardais.bigr.library.web.column.DonorCommentsColumn;
import com.ardais.bigr.library.web.column.DonorIdColumn;
import com.ardais.bigr.library.web.column.ElapsedTimeColumn;
import com.ardais.bigr.library.web.column.FixativeTypeColumn;
import com.ardais.bigr.library.web.column.FormatDetailColumn;
import com.ardais.bigr.library.web.column.GenderColumn;
import com.ardais.bigr.library.web.column.GenericFixedImagesColumn;
import com.ardais.bigr.library.web.column.GenericImagesColumn;
import com.ardais.bigr.library.web.column.HngColumn;
import com.ardais.bigr.library.web.column.HngTnmStageColumn;
import com.ardais.bigr.library.web.column.InventoryStatusColumn;
import com.ardais.bigr.library.web.column.LogicalRepositoryShortNameColumn;
import com.ardais.bigr.library.web.column.NumSectionsColumn;
import com.ardais.bigr.library.web.column.PercentViabilityColumn;
import com.ardais.bigr.library.web.column.PreparedByColumn;
import com.ardais.bigr.library.web.column.ProcedureColumn;
import com.ardais.bigr.library.web.column.ProjectStatusColumn;
import com.ardais.bigr.library.web.column.PvNotesColumn;
import com.ardais.bigr.library.web.column.PvNotesInternalColumn;
import com.ardais.bigr.library.web.column.QcPostedColumn;
import com.ardais.bigr.library.web.column.QcProjInvSalesColumn;
import com.ardais.bigr.library.web.column.QcStatusColumn;
import com.ardais.bigr.library.web.column.RepeatDonorColumn;
import com.ardais.bigr.library.web.column.RnaAmountAvailableColumn;
import com.ardais.bigr.library.web.column.RnaAmountInputColumn;
import com.ardais.bigr.library.web.column.RnaAmountOnHoldForCartColumn;
import com.ardais.bigr.library.web.column.RnaAmountRemainingColumn;
import com.ardais.bigr.library.web.column.RnaAsmPosColumn;
import com.ardais.bigr.library.web.column.RnaBioRatioColumn;
import com.ardais.bigr.library.web.column.RnaCaseExhaustedColumn;
import com.ardais.bigr.library.web.column.RnaHoldStatusColumn;
import com.ardais.bigr.library.web.column.RnaIdColumn;
import com.ardais.bigr.library.web.column.RnaInventoryStatusColumn;
import com.ardais.bigr.library.web.column.RnaNotesColumn;
import com.ardais.bigr.library.web.column.RnaPooledTissueColumn;
import com.ardais.bigr.library.web.column.RnaPrepColumn;
import com.ardais.bigr.library.web.column.RnaQualityColumn;
import com.ardais.bigr.library.web.column.RnaSalesStatusColumn;
import com.ardais.bigr.library.web.column.RowIndexColumn;
import com.ardais.bigr.library.web.column.SalesStatusColumn;
import com.ardais.bigr.library.web.column.SampleActionColumn;
import com.ardais.bigr.library.web.column.SampleAliasColumn;
import com.ardais.bigr.library.web.column.SampleIdColumn;
import com.ardais.bigr.library.web.column.SampleLocationColumn;
import com.ardais.bigr.library.web.column.SampleBoxLocationColumn;
import com.ardais.bigr.library.web.column.SampleSelectIsPvIsRestrictedIsBmsIdColumn;
import com.ardais.bigr.library.web.column.SampleSourceColumn;
import com.ardais.bigr.library.web.column.SampleTypeColumn;
import com.ardais.bigr.library.web.column.SelectColumn;
import com.ardais.bigr.library.web.column.RegistrationSiteColumn;
import com.ardais.bigr.library.web.column.StageGroupingColumn;
import com.ardais.bigr.library.web.column.TissueColumn;
import com.ardais.bigr.library.web.column.TnmColumn;
import com.ardais.bigr.library.web.column.TotalNumOfCellsColumn;
import com.ardais.bigr.library.web.column.VolumeColumn;
import com.ardais.bigr.library.web.column.WeightColumn;
import com.ardais.bigr.library.web.column.YieldColumn;
import com.ardais.bigr.library.web.column.RaceColumn;
import com.ardais.bigr.query.ColumnConstants;
import com.ardais.bigr.query.ViewParams;

public class ColumnWriterList {
  // here is where the front-end / back-end mapping of column keys to the classes that
  // actually render those columns as HTML is made.
  // NOTE in WSAD, browsing references to these classes will not show this usage!
  static {
    ColumnManager cm = ColumnManager.instance();
    cm.register(ColumnConstants._ROW_INDEX, RowIndexColumn.class);
    cm.register(ColumnConstants._AGE_AT_COLLECTION, AgeAtCollectionColumn.class);
    cm.register(ColumnConstants._APPEARANCE, AppearanceColumn.class);
    cm.register(ColumnConstants._ASM_POS, AsmPosColumn.class);
    cm.register(ColumnConstants._ASM_OR_PREP, AsmPrepColumn.class);
    cm.register(ColumnConstants._CASE_ID, CaseIdColumn.class);
    cm.register(ColumnConstants._DONOR_ID, DonorIdColumn.class);
   
    cm.register(ColumnConstants._SELECT_COMPOSITE, SelectColumn.class);
    cm.register(
      ColumnConstants._SELECT_BOX_WITH_PV,
      SampleSelectIsPvIsRestrictedIsBmsIdColumn.class);
    cm.register(ColumnConstants._DELIVERY_TYPE_DROPDOWN_CTRL, DeliveryTypeColumn.class);
    cm.register(ColumnConstants._RNA_ON_HOLD_THIS_CART, RnaAmountOnHoldForCartColumn.class);
    cm.register(ColumnConstants._RNA_AMT_INPUT_CTRL, RnaAmountInputColumn.class);
    cm.register(ColumnConstants._DIAGNOSIS_LIMS, DiagnosisLimsColumn.class);
    cm.register(ColumnConstants._DIAGNOSIS_DDC, DiagnosisDdcColumn.class);
    cm.register(ColumnConstants._TISSUE, TissueColumn.class);
    cm.register(ColumnConstants._ASM_TISSUE, AsmTissueColumn.class);

    cm.register(ColumnConstants._COMPOSITION_ACELLSTROMA, CompositionAcellStromaColumn.class);
    cm.register(ColumnConstants._COMPOSITION_CELLSTROMA, CompositionCellStromaColumn.class);
    cm.register(ColumnConstants._COMPOSITION_LESION, CompositionLesionColumn.class);
    cm.register(ColumnConstants._COMPOSITION_NECROSIS, CompositionNecrosisColumn.class);
    cm.register(ColumnConstants._COMPOSITION_NORMAL, CompositionNormalColumn.class);
    cm.register(ColumnConstants._COMPOSITION_TUMOR, CompositionTumorColumn.class);
    cm.register(ColumnConstants._COMPOSITION_ALL, CompositionValuesColumn.class);
    cm.register(ColumnConstants._PV_NOTES, PvNotesColumn.class);
    cm.register(ColumnConstants._RNA_BIO_RATIO, RnaBioRatioColumn.class);
    cm.register(ColumnConstants._IMAGES, GenericImagesColumn.class);
    cm.register(ColumnConstants._IMAGES_FIXED, GenericFixedImagesColumn.class);
    cm.register(ColumnConstants._DIAGNOSIS_ILTDS, DiagnosisIltdsColumn.class);
    cm.register(ColumnConstants._HNG_TNM_STAGE, HngTnmStageColumn.class);
    cm.register(ColumnConstants._HNG, HngColumn.class);
    cm.register(ColumnConstants._TNM, TnmColumn.class);
    cm.register(ColumnConstants._STAGE_GROUPING, StageGroupingColumn.class);

    cm.register(ColumnConstants._DIAGNOSTIC_RESULT, DiagnosticResultColumn.class);

    cm.register(ColumnConstants._QC_PROJ_INV_SALES, QcProjInvSalesColumn.class);

    cm.register(ColumnConstants._PV_NOTES_INTERNAL, PvNotesInternalColumn.class);
    cm.register(ColumnConstants._DONOR_COMMENTS, DonorCommentsColumn.class);
    cm.register(ColumnConstants._GENDER, GenderColumn.class);
    cm.register(ColumnConstants._SAMPLE_ACTION, SampleActionColumn.class);
    cm.register(ColumnConstants._SAMPLE_ID, SampleIdColumn.class);
    cm.register(ColumnConstants._SAMPLE_TYPE, SampleTypeColumn.class);
    cm.register(ColumnConstants._SAMPLE_SOURCE, SampleSourceColumn.class);
    cm.register(ColumnConstants._SAMPLE_BOX_LOCATION, SampleBoxLocationColumn.class);
    cm.register(ColumnConstants._RACE, RaceColumn.class);

    cm.register(ColumnConstants._FORMAT_DETAIL, FormatDetailColumn.class);
    cm.register(ColumnConstants._FIXATIVE_TYPE, FixativeTypeColumn.class);
    cm.register(ColumnConstants._SOURCE, RegistrationSiteColumn.class);
    cm.register(ColumnConstants._NUM_SECTIONS, NumSectionsColumn.class);
    cm.register(ColumnConstants._REPEAT_DONOR, RepeatDonorColumn.class);

    cm.register(ColumnConstants._RNA_ID, RnaIdColumn.class);
    cm.register(ColumnConstants._RNA_AMT_AVAIL, RnaAmountAvailableColumn.class);
    cm.register(ColumnConstants._RNA_AMT_REMAINING, RnaAmountRemainingColumn.class);
    cm.register(ColumnConstants._RNA_ASM_POS, RnaAsmPosColumn.class);
    cm.register(ColumnConstants._RNA_CASE_EXHAUSTED, RnaCaseExhaustedColumn.class);
    cm.register(ColumnConstants._RNA_INVENTORY_STATUS, RnaInventoryStatusColumn.class);
    cm.register(ColumnConstants._RNA_SALES_STATUS, RnaSalesStatusColumn.class);
    cm.register(ColumnConstants._RNA_HOLD_STATUS, RnaHoldStatusColumn.class);
    cm.register(ColumnConstants._RNA_PREP, RnaPrepColumn.class);
    cm.register(ColumnConstants._RNA_POOLED_TISSUE, RnaPooledTissueColumn.class);
    cm.register(ColumnConstants._RNA_QUALITY, RnaQualityColumn.class);
    cm.register(ColumnConstants._RNA_NOTES, RnaNotesColumn.class);

    cm.register(ColumnConstants._INVENTORY_STATUS, InventoryStatusColumn.class);
    cm.register(ColumnConstants._QC_STATUS, QcStatusColumn.class);
    cm.register(ColumnConstants._SALES_STATUS, SalesStatusColumn.class);
    cm.register(ColumnConstants._QC_POSTED, QcPostedColumn.class);
    cm.register(ColumnConstants._PROJECT_STATUS, ProjectStatusColumn.class);

    cm.register(ColumnConstants._LOGICAL_REPOSITORY, LogicalRepositoryShortNameColumn.class);

    cm.register(ColumnConstants._SAMPLE_LOCATION, SampleLocationColumn.class);

    cm.register(ColumnConstants._DONOR_ALIAS, DonorAliasColumn.class);
    cm.register(ColumnConstants._CASE_ALIAS, CaseAliasColumn.class);
    cm.register(ColumnConstants._SAMPLE_ALIAS, SampleAliasColumn.class);

    cm.register(ColumnConstants._VOLUME, VolumeColumn.class);
    cm.register(ColumnConstants._WEIGHT, WeightColumn.class);
    cm.register(ColumnConstants._BUFFER_TYPE, BufferTypeColumn.class);
    cm.register(ColumnConstants._TOTAL_NUM_OF_CELLS, TotalNumOfCellsColumn.class);
    cm.register(ColumnConstants._CELLS_PER_ML, CellsPerMlColumn.class);
    cm.register(ColumnConstants._PERCENT_VIABILITY, PercentViabilityColumn.class);
    cm.register(ColumnConstants._DATE_OF_COLLECTION, DateOfCollectionColumn.class);
    cm.register(ColumnConstants._DATE_OF_PRESERVATION, DateOfPreservationColumn.class);
    cm.register(ColumnConstants._ELAPSED_TIME, ElapsedTimeColumn.class);
    cm.register(ColumnConstants._CONCENTRATION, ConcentrationColumn.class);
    cm.register(ColumnConstants._YIELD, YieldColumn.class);
    cm.register(ColumnConstants._PREPARED_BY, PreparedByColumn.class);
    cm.register(ColumnConstants._PROCEDURE, ProcedureColumn.class);
  }

  private ViewParams _viewParams = null;
  private List _columns = null;

  public ColumnWriterList(List columnDescriptors, ViewParams vp) {
    _viewParams = vp;
    _columns = ColumnManager.instance().newInstances(columnDescriptors, _viewParams);
  }

  public List getColumns() {
    return Collections.unmodifiableList(_columns);
  }
}
