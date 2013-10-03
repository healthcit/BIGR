package com.ardais.bigr.web.kc;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.jsp.PageContext;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.util.ArtsConstants;
import com.gulfstreambio.kc.web.support.ElementRenderer;
import com.gulfstreambio.kc.web.support.FormContext;
import com.gulfstreambio.kc.web.support.WebUtils;

public class BigrElementRenderer implements ElementRenderer {

  private static Map _elementEditMapping = new HashMap();
  static {
    // Donor attributes.
    _elementEditMapping.put(ArtsConstants.ATTRIBUTE_DONOR_ETHNICITY, "donorEthnicity.jsp");
    _elementEditMapping.put(ArtsConstants.ATTRIBUTE_DONOR_GENDER, "donorGender.jsp");
    _elementEditMapping.put(ArtsConstants.ATTRIBUTE_DONOR_NOTES, "donorNotes.jsp");
    _elementEditMapping.put(ArtsConstants.ATTRIBUTE_DONOR_RACE, "donorRace.jsp");
    _elementEditMapping.put(ArtsConstants.ATTRIBUTE_DONOR_YOB, "donorYob.jsp");
    _elementEditMapping.put(ArtsConstants.ATTRIBUTE_DONOR_ZIP_CODE, "donorZipCode.jsp");

    // Case attributes.
    _elementEditMapping.put(ArtsConstants.ATTRIBUTE_CASE_COMMENTS, "caseComments.jsp");
    _elementEditMapping.put(ArtsConstants.ATTRIBUTE_CASE_DIAGNOSIS, "caseDiagnosis.jsp");

    // Sample attributes.
    _elementEditMapping.put(ArtsConstants.ATTRIBUTE_BUFFER_TYPE, "bufferType.jsp");
    _elementEditMapping.put(ArtsConstants.ATTRIBUTE_CELLS_PER_ML, "cellsPerMl.jsp");
    _elementEditMapping.put(ArtsConstants.ATTRIBUTE_COMMENT, "comment.jsp");
    _elementEditMapping.put(ArtsConstants.ATTRIBUTE_CONCENTRATION, "concentration.jsp");
    _elementEditMapping.put(ArtsConstants.ATTRIBUTE_DATE_OF_COLLECTION, "dateCollection.jsp");
    _elementEditMapping.put(ArtsConstants.ATTRIBUTE_DATE_OF_PRESERVATION, "datePreservation.jsp");
    _elementEditMapping.put(ArtsConstants.ATTRIBUTE_FIXATIVE_TYPE, "fixativeType.jsp");
    _elementEditMapping.put(ArtsConstants.ATTRIBUTE_FORMAT_DETAIL, "formatDetail.jsp");
    _elementEditMapping.put(ArtsConstants.ATTRIBUTE_GROSS_APPEARANCE, "grossAppearance.jsp");
    _elementEditMapping.put(ArtsConstants.ATTRIBUTE_PERCENT_VIABILITY, "percentViability.jsp");
    _elementEditMapping.put(ArtsConstants.ATTRIBUTE_PREPARED_BY, "preparedBy.jsp");
    _elementEditMapping.put(ArtsConstants.ATTRIBUTE_PROCEDURE, "procedure.jsp");
    _elementEditMapping.put(ArtsConstants.ATTRIBUTE_SOURCE, "sampleSource.jsp");
    _elementEditMapping.put(ArtsConstants.ATTRIBUTE_TISSUE, "tissue.jsp");
    _elementEditMapping.put(ArtsConstants.ATTRIBUTE_TOTAL_NUMBER_OF_CELLS, "totalCells.jsp");
    _elementEditMapping.put(ArtsConstants.ATTRIBUTE_VOLUME, "volume.jsp");
    _elementEditMapping.put(ArtsConstants.ATTRIBUTE_WEIGHT, "weight.jsp");
    _elementEditMapping.put(ArtsConstants.ATTRIBUTE_YIELD, "yield.jsp");
  }
  
  private static Map _elementSummaryMapping = new HashMap();
  static {
    // Donor attributes.
    _elementSummaryMapping.put(ArtsConstants.ATTRIBUTE_DONOR_ETHNICITY, "donorEthnicitySummary.jsp");
    _elementSummaryMapping.put(ArtsConstants.ATTRIBUTE_DONOR_GENDER, "donorGenderSummary.jsp");
    _elementSummaryMapping.put(ArtsConstants.ATTRIBUTE_DONOR_NOTES, "donorNotesSummary.jsp");
    _elementSummaryMapping.put(ArtsConstants.ATTRIBUTE_DONOR_RACE, "donorRaceSummary.jsp");
    _elementSummaryMapping.put(ArtsConstants.ATTRIBUTE_DONOR_YOB, "donorYobSummary.jsp");
    _elementSummaryMapping.put(ArtsConstants.ATTRIBUTE_DONOR_ZIP_CODE, "donorZipCodeSummary.jsp");

    // Case attributes.
    _elementSummaryMapping.put(ArtsConstants.ATTRIBUTE_CASE_COMMENTS, "caseCommentsSummary.jsp");
    _elementSummaryMapping.put(ArtsConstants.ATTRIBUTE_CASE_DIAGNOSIS, "caseDiagnosisSummary.jsp");

    // Sample attributes.
    _elementSummaryMapping.put(ArtsConstants.ATTRIBUTE_BUFFER_TYPE, "bufferTypeSummary.jsp");
    _elementSummaryMapping.put(ArtsConstants.ATTRIBUTE_CELLS_PER_ML, "cellsPerMlSummary.jsp");
    _elementSummaryMapping.put(ArtsConstants.ATTRIBUTE_COMMENT, "commentSummary.jsp");
    _elementSummaryMapping.put(ArtsConstants.ATTRIBUTE_CONCENTRATION, "concentrationSummary.jsp");
    _elementSummaryMapping.put(ArtsConstants.ATTRIBUTE_DATE_OF_COLLECTION, "dateCollectionSummary.jsp");
    _elementSummaryMapping.put(ArtsConstants.ATTRIBUTE_DATE_OF_PRESERVATION, "datePreservationSummary.jsp");
    _elementSummaryMapping.put(ArtsConstants.ATTRIBUTE_FIXATIVE_TYPE, "fixativeTypeSummary.jsp");
    _elementSummaryMapping.put(ArtsConstants.ATTRIBUTE_FORMAT_DETAIL, "formatDetailSummary.jsp");
    _elementSummaryMapping.put(ArtsConstants.ATTRIBUTE_GROSS_APPEARANCE, "grossAppearanceSummary.jsp");
    _elementSummaryMapping.put(ArtsConstants.ATTRIBUTE_PERCENT_VIABILITY, "percentViabilitySummary.jsp");
    _elementSummaryMapping.put(ArtsConstants.ATTRIBUTE_PREPARED_BY, "preparedBySummary.jsp");
    _elementSummaryMapping.put(ArtsConstants.ATTRIBUTE_PROCEDURE, "procedureSummary.jsp");
    _elementSummaryMapping.put(ArtsConstants.ATTRIBUTE_SOURCE, "sampleSourceSummary.jsp");
    _elementSummaryMapping.put(ArtsConstants.ATTRIBUTE_TISSUE, "tissueSummary.jsp");
    _elementSummaryMapping.put(ArtsConstants.ATTRIBUTE_TOTAL_NUMBER_OF_CELLS, "totalCellsSummary.jsp");
    _elementSummaryMapping.put(ArtsConstants.ATTRIBUTE_VOLUME, "volumeSummary.jsp");
    _elementSummaryMapping.put(ArtsConstants.ATTRIBUTE_WEIGHT, "weightSummary.jsp");
    _elementSummaryMapping.put(ArtsConstants.ATTRIBUTE_YIELD, "yieldSummary.jsp");
  }

  BigrElementRenderer() {
    super();
  }

  public void renderForEdit(String hostId, FormContext formContext, PageContext pageContext) {
    render(hostId, pageContext, (String) _elementEditMapping.get(hostId));
  }

  public void renderForSummary(String hostId, FormContext formContext, PageContext pageContext) {
    render(hostId, pageContext, (String) _elementSummaryMapping.get(hostId));
  }
  
  private void render(String hostId, PageContext pageContext, String page) {
    if (page != null) {
      try {
        pageContext.include("/hiddenJsps/dataImport/attr/" + page);
      }
      catch (Exception e) {
        throw new ApiException(e);
      }                  
    }
    else {
      StringBuffer buf = new StringBuffer();
      buf.append("<div class=\"kcElement\" style=\"color: red;\">");
      buf.append("IMPLEMENT HOST ELEMENT ");
      buf.append(hostId);
      buf.append("!!!</div>");
      WebUtils.tagWrite(pageContext, buf.toString());
    }    
  }
}
