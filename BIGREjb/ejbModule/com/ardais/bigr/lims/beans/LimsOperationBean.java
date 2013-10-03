package com.ardais.bigr.lims.beans;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.ejb.ObjectNotFoundException;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.iltds.assistants.LegalValueSet;
import com.ardais.bigr.iltds.beans.SampleAccessBean;
import com.ardais.bigr.iltds.beans.SampleKey;
import com.ardais.bigr.iltds.helpers.FormLogic;
import com.ardais.bigr.javabeans.AsmData;
import com.ardais.bigr.javabeans.ConsentData;
import com.ardais.bigr.javabeans.SampleData;
import com.ardais.bigr.lims.helpers.LimsConstants;
import com.ardais.bigr.lims.javabeans.ImageData;
import com.ardais.bigr.lims.javabeans.IncidentReportLineItem;
import com.ardais.bigr.lims.javabeans.PathologyEvaluationData;
import com.ardais.bigr.lims.javabeans.PathologyEvaluationSampleData;
import com.ardais.bigr.lims.javabeans.SlideData;
import com.ardais.bigr.pdc.javabeans.PathReportData;
import com.ardais.bigr.pdc.javabeans.PathReportSectionData;
import com.ardais.bigr.pdc.oce.Oce;
import com.ardais.bigr.pdc.oce.OceHome;
import com.ardais.bigr.pdc.oce.util.OceUtil;
import com.ardais.bigr.util.DbUtils;
import com.ardais.bigr.util.EjbHomes;
import com.ardais.bigr.util.gen.DbAliases;
import com.ardais.bigr.util.gen.DbConstants;

/**
 * Bean implementation class for Enterprise Bean: LimsOperation
 */
public class LimsOperationBean implements javax.ejb.SessionBean {
  private javax.ejb.SessionContext mySessionCtx;
  /**
   * getSessionContext
   */
  public javax.ejb.SessionContext getSessionContext() {
    return mySessionCtx;
  }
  /**
   * setSessionContext
   */
  public void setSessionContext(javax.ejb.SessionContext ctx) {
    mySessionCtx = ctx;
  }
  /**
   * ejbActivate
   */
  public void ejbActivate() {
  }
  /**
   * ejbCreate
   */
  public void ejbCreate() throws javax.ejb.CreateException {
  }
  /**
   * ejbPassivate
   */
  public void ejbPassivate() {
  }
  /**
   * ejbRemove
   */
  public void ejbRemove() {
  }
  /**
  	 * Creates a new <code>SlideData</code> and populates caseId, sampleId, 
  	 * ASM position, slide number fields for slideId.
  	 *
  	 * @param  <code>String</code> slideId
  	 * @return  A <code>SlideData</code> bean.
  	 */
  public SlideData getPrintLabelData(String slideId) {
    StringBuffer sql = new StringBuffer(256);
    SlideData databean = new SlideData();
    PreparedStatement pstmt = null;
    ResultSet rSet = null;

    sql.append("select slide.sample_barcode_id, slide.section_number, ");
    sql.append("sample.asm_position, asm.consent_id from lims_slide slide,");
    sql.append("iltds_sample sample, iltds_asm asm where slide.slide_id = ?");
    sql.append("and sample.sample_barcode_id = slide.sample_barcode_id ");
    sql.append("and asm.asm_id (+) = sample.asm_id	");

    Connection con = ApiFunctions.getDbConnection();
    try {
      pstmt = con.prepareStatement(sql.toString());
      pstmt.setString(1, slideId);
      rSet = pstmt.executeQuery();

      //populate data bean
      while (rSet.next()) {
        databean.setSlideId(slideId);
        databean.setSampleId(rSet.getString("sample_barcode_id"));
        databean.setSlideNumber(Integer.parseInt(rSet.getString("section_number")));
        databean.setSampleASMPosition(rSet.getString("asm_position"));
        databean.setCaseId(rSet.getString("consent_id"));
      }
    }
    catch (Exception e) {
      throw new ApiException(e);
    }
    finally {
      ApiFunctions.close(con, pstmt, rSet);
    }
    return databean;
  }

  /**
  	 * Returns the sample id to which a slide belongs
  	 *
  	 * @param  <code>String</code> slideId
  	 * @return  A <code>String</code>.
  	 */
  public String getSampleIdForSlide(String slideId) {
    PreparedStatement pstmt = null;
    ResultSet results = null;
    String sql = "SELECT SAMPLE_BARCODE_ID FROM LIMS_SLIDE WHERE SLIDE_ID = ?";
    String sampleId = null;

    Connection con = ApiFunctions.getDbConnection();
    try {
      pstmt = con.prepareStatement(sql);
      pstmt.setString(1, slideId);
      results = pstmt.executeQuery();
      if (results.next()) {
        sampleId = results.getString("SAMPLE_BARCODE_ID");
      }
    }
    catch (Exception e) {
      throw new ApiException(e);
    }
    finally {
      ApiFunctions.close(con, pstmt, results);
    }
    return sampleId;
  }

  /**
  	 * Returns the sample information related to a sample
  	 *
  	 * @param  <code>String</code> sampleId
  	 * @return  A <code>SampleData</code> data bean.
  	 */
  public SampleData getSampleData(String sampleId) {

    SampleData sampleData = null;

    try {
      SampleKey key = new SampleKey(sampleId);
      SampleAccessBean sampleBean = new SampleAccessBean(key);
      sampleData = new SampleData(sampleBean);
    }
    catch (ObjectNotFoundException onfe) {
      sampleData = null;
    }
    catch (Exception ex) {
      throw new ApiException(ex);
    }

    return sampleData;
  }

  /**
  	 * Returns the consent information related to a sample
  	 *
  	 * @param  <code>String</code> sampleId
  	 * @return  A <code>ConsentData</code> data bean.
  	 */
  public ConsentData getConsentData(String sampleId) {
    ConsentData consent = null;
    PreparedStatement pstmt = null;
    ResultSet results = null;
    StringBuffer sql = new StringBuffer(256);
    sql.append("SELECT ");
    sql.append(DbAliases.TABLE_CONSENT);
    sql.append(".");
    sql.append(DbConstants.CONSENT_ID);
    sql.append(" as ");
    sql.append(DbAliases.CONSENT_ID);
    sql.append(", ");
    sql.append(DbAliases.TABLE_CONSENT);
    sql.append(".");
    sql.append(DbConstants.CONSENT_DX);
    sql.append(" as ");
    sql.append(DbAliases.CONSENT_DX);
    sql.append(", ");
    sql.append(DbAliases.TABLE_CONSENT);
    sql.append(".");
    sql.append(DbConstants.CONSENT_DX_OTHER);
    sql.append(" as ");
    sql.append(DbAliases.CONSENT_DX_OTHER);
    sql.append(", ");
    sql.append(DbAliases.TABLE_CONSENT);
    sql.append(".");
    sql.append(DbConstants.DONOR_ID);
    sql.append(" as ");
    sql.append(DbAliases.DONOR_ID);
    sql.append(" FROM ");
    sql.append(DbConstants.TABLE_SAMPLE);
    sql.append(" ");
    sql.append(DbAliases.TABLE_SAMPLE);
    sql.append(",");
    sql.append(DbConstants.TABLE_ASM);
    sql.append(" ");
    sql.append(DbAliases.TABLE_ASM);
    sql.append(",");
    sql.append(DbConstants.TABLE_CONSENT);
    sql.append(" ");
    sql.append(DbAliases.TABLE_CONSENT);
    sql.append(" WHERE ");
    sql.append(DbAliases.TABLE_SAMPLE);
    sql.append(".");
    sql.append(DbConstants.SAMPLE_ID);
    sql.append(" = ? AND ");
    sql.append(DbAliases.TABLE_SAMPLE);
    sql.append(".");
    sql.append(DbConstants.ASM_ID);
    sql.append(" = ");
    sql.append(DbAliases.TABLE_ASM);
    sql.append(".");
    sql.append(DbConstants.ASM_ID);
    sql.append(" AND ");
    sql.append(DbAliases.TABLE_ASM);
    sql.append(".");
    sql.append(DbConstants.CONSENT_ID);
    sql.append(" = ");
    sql.append(DbAliases.TABLE_CONSENT);
    sql.append(".");
    sql.append(DbConstants.CONSENT_ID);

    Connection con = ApiFunctions.getDbConnection();
    try {
      pstmt = con.prepareStatement(sql.toString());
      pstmt.setString(1, sampleId);
      results = pstmt.executeQuery();
      Map columnMap = DbUtils.getColumnNames(results);
      if (results.next()) {
        consent = new ConsentData(columnMap, results);
      }
    }
    catch (Exception e) {
      throw new ApiException(e);
    }
    finally {
      ApiFunctions.close(con, pstmt, results);
    }
    return consent;
  }

  /**
  	 * Returns the asm information related to a sample
  	 *
  	 * @param  <code>String</code> sampleId
  	 * @return  An <code>AsmData</code> data bean.
  	 */
  public AsmData getAsmData(String sampleId) {
    AsmData asm = null;
    PreparedStatement pstmt = null;
    ResultSet results = null;
    StringBuffer sql = new StringBuffer(256);
    sql.append("SELECT ");
    sql.append(DbAliases.TABLE_ASM);
    sql.append(".");
    sql.append(DbConstants.ASM_APPEARANCE);
    sql.append(" as ");
    sql.append(DbAliases.ASM_APPEARANCE);
    sql.append(", ");
    sql.append(DbAliases.TABLE_ASM);
    sql.append(".");
    sql.append(DbConstants.ASM_TISSUE);
    sql.append(" as ");
    sql.append(DbAliases.ASM_TISSUE);
    sql.append(", ");
    sql.append(DbAliases.TABLE_ASM);
    sql.append(".");
    sql.append(DbConstants.ASM_TISSUE_OTHER);
    sql.append(" as ");
    sql.append(DbAliases.ASM_TISSUE_OTHER);
    sql.append(", ");
    sql.append(DbAliases.TABLE_ASM);
    sql.append(".");
    sql.append(DbConstants.ASM_FORM_ID);
    sql.append(" as ");
    sql.append(DbAliases.ASM_FORM_ID);
    sql.append(" FROM ");
    sql.append(DbConstants.TABLE_SAMPLE);
    sql.append(" ");
    sql.append(DbAliases.TABLE_SAMPLE);
    sql.append(",");
    sql.append(DbConstants.TABLE_ASM);
    sql.append(" ");
    sql.append(DbAliases.TABLE_ASM);
    sql.append(" WHERE ");
    sql.append(DbAliases.TABLE_SAMPLE);
    sql.append(".");
    sql.append(DbConstants.SAMPLE_ID);
    sql.append(" = ? AND ");
    sql.append(DbAliases.TABLE_SAMPLE);
    sql.append(".");
    sql.append(DbConstants.ASM_ID);
    sql.append(" = ");
    sql.append(DbAliases.TABLE_ASM);
    sql.append(".");
    sql.append(DbConstants.ASM_ID);

    Connection con = ApiFunctions.getDbConnection();
    try {
      pstmt = con.prepareStatement(sql.toString());
      pstmt.setString(1, sampleId);
      results = pstmt.executeQuery();
      Map columnMap = DbUtils.getColumnNames(results);
      if (results.next()) {
        asm = new AsmData(columnMap, results);
      }
    }
    catch (Exception e) {
      throw new ApiException(e);
    }
    finally {
      ApiFunctions.close(con, pstmt, results);
    }
    return asm;
  }

  /**
  	 * Returns the PDC path report section information related to a sample
  	 *
  	 * @param  <code>String</code> sampleId
  	 * @return  A <code>List of PathReportSectionData</code> data beans.
  	 */
  public List getPdcPathReportSectionData(String sampleId) {
    List sections = new Vector();
    PathReportSectionData section = null;
    PreparedStatement pstmt = null;
    ResultSet results = null;
    StringBuffer sql = new StringBuffer(256);
    sql.append("SELECT ");
    sql.append(DbAliases.TABLE_SECTION);
    sql.append(".");
    sql.append(DbConstants.SECTION_TISSUE_FINDING);
    sql.append(" as ");
    sql.append(DbAliases.SECTION_TISSUE_FINDING);
    sql.append(", ");
    sql.append(DbAliases.TABLE_SECTION);
    sql.append(".");
    sql.append(DbConstants.SECTION_TISSUE_FINDING_OTHER);
    sql.append(" as ");
    sql.append(DbAliases.SECTION_TISSUE_FINDING_OTHER);
    sql.append(", ");
    sql.append(DbAliases.TABLE_SECTION);
    sql.append(".");
    sql.append(DbConstants.SECTION_TISSUE_ORIGIN);
    sql.append(" as ");
    sql.append(DbAliases.SECTION_TISSUE_ORIGIN);
    sql.append(", ");
    sql.append(DbAliases.TABLE_SECTION);
    sql.append(".");
    sql.append(DbConstants.SECTION_TISSUE_ORIGIN_OTHER);
    sql.append(" as ");
    sql.append(DbAliases.SECTION_TISSUE_ORIGIN_OTHER);
    sql.append(", ");
    sql.append(DbAliases.TABLE_SECTION);
    sql.append(".");
    sql.append(DbConstants.SECTION_DX);
    sql.append(" as ");
    sql.append(DbAliases.SECTION_DX);
    sql.append(", ");
    sql.append(DbAliases.TABLE_SECTION);
    sql.append(".");
    sql.append(DbConstants.SECTION_DX_OTHER);
    sql.append(" as ");
    sql.append(DbAliases.SECTION_DX_OTHER);
    sql.append(", ");
    sql.append(DbAliases.TABLE_SECTION);
    sql.append(".");
    sql.append(DbConstants.SECTION_PRIMARY_IND);
    sql.append(" as ");
    sql.append(DbAliases.SECTION_PRIMARY_IND);
    sql.append(", ");
    sql.append(DbAliases.TABLE_SECTION);
    sql.append(".");
    sql.append(DbConstants.SECTION_PATH_REPORT_ID);
    sql.append(" as ");
    sql.append(DbAliases.SECTION_PATH_REPORT_ID);
    sql.append(" FROM ");
    sql.append(DbConstants.TABLE_SAMPLE);
    sql.append(" ");
    sql.append(DbAliases.TABLE_SAMPLE);
    sql.append(",");
    sql.append(DbConstants.TABLE_ASM);
    sql.append(" ");
    sql.append(DbAliases.TABLE_ASM);
    sql.append(",");
    sql.append(DbConstants.TABLE_SECTION);
    sql.append(" ");
    sql.append(DbAliases.TABLE_SECTION);
    sql.append(" WHERE ");
    sql.append(DbAliases.TABLE_SAMPLE);
    sql.append(".");
    sql.append(DbConstants.SAMPLE_ID);
    sql.append(" = ? AND ");
    sql.append(DbAliases.TABLE_SAMPLE);
    sql.append(".");
    sql.append(DbConstants.ASM_ID);
    sql.append(" = ");
    sql.append(DbAliases.TABLE_ASM);
    sql.append(".");
    sql.append(DbConstants.ASM_ID);
    sql.append(" AND ");
    sql.append(DbAliases.TABLE_ASM);
    sql.append(".");
    sql.append(DbConstants.CONSENT_ID);
    sql.append(" = ");
    sql.append(DbAliases.TABLE_SECTION);
    sql.append(".");
    sql.append(DbConstants.CONSENT_ID);

    Connection con = ApiFunctions.getDbConnection();
    try {
      pstmt = con.prepareStatement(sql.toString());
      pstmt.setString(1, sampleId);
      results = pstmt.executeQuery();
      Map columnMap = DbUtils.getColumnNames(results);
      while (results.next()) {
        section = new PathReportSectionData(columnMap, results);
        sections.add(section);
      }
    }
    catch (Exception e) {
      throw new ApiException(e);
    }
    finally {
      ApiFunctions.close(con, pstmt, results);
    }
    return sections;
  }

  /**
  	 * Returns the information for the reported pathology evaluation for a sample
  	 *
  	 * @param  <code>String</code> sampleId
  	 * @return  A <code>PathologyEvaluationData</code> data bean.
  	 */
  public PathologyEvaluationData getReportedPathologyEvaluation(String sampleId) {
    PathologyEvaluationData evaluation = null;
    PreparedStatement pstmt = null;
    ResultSet results = null;
    StringBuffer sql = new StringBuffer(256);
    sql.append("SELECT ");
    sql.append(DbAliases.TABLE_LIMS_PE);
    sql.append(".");
    sql.append(DbConstants.LIMS_PE_CREATE_USER);
    sql.append(" as ");
    sql.append(DbAliases.LIMS_PE_CREATE_USER);
    sql.append(", ");
    sql.append(DbAliases.TABLE_LIMS_PE);
    sql.append(".");
    sql.append(DbConstants.LIMS_PE_REPORTED_DATE);
    sql.append(" as ");
    sql.append(DbAliases.LIMS_PE_REPORTED_DATE);
    sql.append(", ");
    sql.append(DbAliases.TABLE_LIMS_PE);
    sql.append(".");
    sql.append(DbConstants.LIMS_PE_SAMPLE_ID);
    sql.append(" as ");
    sql.append(DbAliases.LIMS_PE_SAMPLE_ID);
    sql.append(", ");
    sql.append(DbAliases.TABLE_LIMS_PE);
    sql.append(".");
    sql.append(DbConstants.LIMS_PE_SLIDE_ID);
    sql.append(" as ");
    sql.append(DbAliases.LIMS_PE_SLIDE_ID);
    sql.append(", ");
    sql.append(DbAliases.TABLE_LIMS_PE);
    sql.append(".");
    sql.append(DbConstants.PE_ID);
    sql.append(" as ");
    sql.append(DbAliases.PE_ID);
    sql.append(", ");
    sql.append(DbAliases.TABLE_LIMS_PE);
    sql.append(".");
    sql.append(DbConstants.LIMS_PE_APPEARANCE);
    sql.append(" as ");
    sql.append(DbAliases.LIMS_PE_APPEARANCE);
    sql.append(", ");
    sql.append(DbAliases.TABLE_LIMS_PE);
    sql.append(".");
    sql.append(DbConstants.LIMS_PE_DX);
    sql.append(" as ");
    sql.append(DbAliases.LIMS_PE_DX);
    sql.append(", ");
    sql.append(DbAliases.TABLE_LIMS_PE);
    sql.append(".");
    sql.append(DbConstants.LIMS_PE_OTHER_DIAGNOSIS);
    sql.append(" as ");
    sql.append(DbAliases.LIMS_PE_OTHER_DIAGNOSIS);
    sql.append(", ");
    sql.append(DbAliases.TABLE_LIMS_PE);
    sql.append(".");
    sql.append(DbConstants.LIMS_PE_TISSUE_ORIGIN);
    sql.append(" as ");
    sql.append(DbAliases.LIMS_PE_TISSUE_ORIGIN);
    sql.append(", ");
    sql.append(DbAliases.TABLE_LIMS_PE);
    sql.append(".");
    sql.append(DbConstants.LIMS_PE_OTHER_TISSUE_ORIGIN);
    sql.append(" as ");
    sql.append(DbAliases.LIMS_PE_OTHER_TISSUE_ORIGIN);
    sql.append(", ");
    sql.append(DbAliases.TABLE_LIMS_PE);
    sql.append(".");
    sql.append(DbConstants.LIMS_PE_TISSUE_FINDING);
    sql.append(" as ");
    sql.append(DbAliases.LIMS_PE_TISSUE_FINDING);
    sql.append(", ");
    sql.append(DbAliases.TABLE_LIMS_PE);
    sql.append(".");
    sql.append(DbConstants.LIMS_PE_OTHER_TISSUE_FINDING);
    sql.append(" as ");
    sql.append(DbAliases.LIMS_PE_OTHER_TISSUE_FINDING);
    sql.append(" FROM ");
    sql.append(DbConstants.TABLE_LIMS_PE);
    sql.append(" ");
    sql.append(DbAliases.TABLE_LIMS_PE);
    sql.append(" WHERE ");
    sql.append(DbAliases.TABLE_LIMS_PE);
    sql.append(".");
    sql.append(DbConstants.LIMS_PE_SAMPLE_ID);
    sql.append(" = ?");
    sql.append(" AND ");
    sql.append(DbAliases.TABLE_LIMS_PE);
    sql.append(".");
    sql.append(DbConstants.LIMS_PE_REPORTED);
    sql.append(" = '");
    sql.append(LimsConstants.LIMS_DB_YES);
    sql.append("'");

    Connection con = ApiFunctions.getDbConnection();
    try {
      pstmt = con.prepareStatement(sql.toString());
      pstmt.setString(1, sampleId);
      results = pstmt.executeQuery();
      Map columnMap = DbUtils.getColumnNames(results);
      if (results.next()) {
        evaluation = new PathologyEvaluationData(columnMap, results);
      }
    }
    catch (Exception e) {
      throw new ApiException(e);
    }
    finally {
      ApiFunctions.close(con, pstmt, results);
    }
    return evaluation;
  }

  /**
  	 * Returns the information for a specified pathology evaluation. Currently only returns
  	 * sample id and slide id, but if the need for additional information arises this method
  	 * can easily be expanded to include that info.
  	 *
  	 * @param  <code>String</code> evaluationId
  	 * @return  A <code>PathologyEvaluationData</code> data bean.
  	 */
  public PathologyEvaluationData getPathologyEvaluationData(String evaluationId) {
    PathologyEvaluationData evaluation = null;
    PreparedStatement pstmt = null;
    ResultSet results = null;
    StringBuffer sql = new StringBuffer(256);
    sql.append("SELECT ");
    sql.append(DbAliases.TABLE_LIMS_PE);
    sql.append(".");
    sql.append(DbConstants.PE_ID);
    sql.append(" as ");
    sql.append(DbAliases.PE_ID);
    sql.append(", ");
    sql.append(DbAliases.TABLE_LIMS_PE);
    sql.append(".");
    sql.append(DbConstants.LIMS_PE_SLIDE_ID);
    sql.append(" as ");
    sql.append(DbAliases.LIMS_PE_SLIDE_ID);
    sql.append(", ");
    sql.append(DbAliases.TABLE_LIMS_PE);
    sql.append(".");
    sql.append(DbConstants.LIMS_PE_SAMPLE_ID);
    sql.append(" as ");
    sql.append(DbAliases.LIMS_PE_SAMPLE_ID);
    sql.append(", ");
    sql.append(DbAliases.TABLE_LIMS_PE);
    sql.append(".");
    sql.append(DbConstants.LIMS_PE_APPEARANCE);
    sql.append(" as ");
    sql.append(DbAliases.LIMS_PE_APPEARANCE);
    sql.append(", ");
    sql.append(DbAliases.TABLE_LIMS_PE);
    sql.append(".");
    sql.append(DbConstants.LIMS_PE_REPORTED);
    sql.append(" as ");
    sql.append(DbAliases.LIMS_PE_REPORTED);
    sql.append(", ");
    sql.append(DbAliases.TABLE_LIMS_PE);
    sql.append(".");
    sql.append(DbConstants.LIMS_PE_CREATE_TYPE);
    sql.append(" as ");
    sql.append(DbAliases.LIMS_PE_CREATE_TYPE);
    sql.append(", ");
    sql.append(DbAliases.TABLE_LIMS_PE);
    sql.append(".");
    sql.append(DbConstants.LIMS_PE_TMR);
    sql.append(" as ");
    sql.append(DbAliases.LIMS_PE_TMR);
    sql.append(", ");
    sql.append(DbAliases.TABLE_LIMS_PE);
    sql.append(".");
    sql.append(DbConstants.LIMS_PE_NRM);
    sql.append(" as ");
    sql.append(DbAliases.LIMS_PE_NRM);
    sql.append(", ");
    sql.append(DbAliases.TABLE_LIMS_PE);
    sql.append(".");
    sql.append(DbConstants.LIMS_PE_TAS);
    sql.append(" as ");
    sql.append(DbAliases.LIMS_PE_TAS);
    sql.append(", ");
    sql.append(DbAliases.TABLE_LIMS_PE);
    sql.append(".");
    sql.append(DbConstants.LIMS_PE_NEC);
    sql.append(" as ");
    sql.append(DbAliases.LIMS_PE_NEC);
    sql.append(", ");
    sql.append(DbAliases.TABLE_LIMS_PE);
    sql.append(".");
    sql.append(DbConstants.LIMS_PE_LSN);
    sql.append(" as ");
    sql.append(DbAliases.LIMS_PE_LSN);
    sql.append(", ");
    sql.append(DbAliases.TABLE_LIMS_PE);
    sql.append(".");
    sql.append(DbConstants.LIMS_PE_DX);
    sql.append(" as ");
    sql.append(DbAliases.LIMS_PE_DX);
    sql.append(", ");
    sql.append(DbAliases.TABLE_LIMS_PE);
    sql.append(".");
    sql.append(DbConstants.LIMS_PE_TISSUE_ORIGIN);
    sql.append(" as ");
    sql.append(DbAliases.LIMS_PE_TISSUE_ORIGIN);
    sql.append(", ");
    sql.append(DbAliases.TABLE_LIMS_PE);
    sql.append(".");
    sql.append(DbConstants.LIMS_PE_CREATE_DATE);
    sql.append(" as ");
    sql.append(DbAliases.LIMS_PE_CREATE_DATE);
    sql.append(", ");
    sql.append(DbAliases.TABLE_LIMS_PE);
    sql.append(".");
    sql.append(DbConstants.LIMS_PE_CREATE_USER);
    sql.append(" as ");
    sql.append(DbAliases.LIMS_PE_CREATE_USER);
    sql.append(", ");
    sql.append(DbAliases.TABLE_LIMS_PE);
    sql.append(".");
    sql.append(DbConstants.LIMS_PE_MIGRATED);
    sql.append(" as ");
    sql.append(DbAliases.LIMS_PE_MIGRATED);
    sql.append(", ");
    sql.append(DbAliases.TABLE_LIMS_PE);
    sql.append(".");
    sql.append(DbConstants.LIMS_PE_TISSUE_FINDING);
    sql.append(" as ");
    sql.append(DbAliases.LIMS_PE_TISSUE_FINDING);
    sql.append(", ");
    sql.append(DbAliases.TABLE_LIMS_PE);
    sql.append(".");
    sql.append(DbConstants.LIMS_PE_OTHER_TISSUE_FINDING);
    sql.append(" as ");
    sql.append(DbAliases.LIMS_PE_OTHER_TISSUE_FINDING);
    sql.append(", ");
    sql.append(DbAliases.TABLE_LIMS_PE);
    sql.append(".");
    sql.append(DbConstants.LIMS_PE_OTHER_DIAGNOSIS);
    sql.append(" as ");
    sql.append(DbAliases.LIMS_PE_OTHER_DIAGNOSIS);
    sql.append(", ");
    sql.append(DbAliases.TABLE_LIMS_PE);
    sql.append(".");
    sql.append(DbConstants.LIMS_PE_OTHER_TISSUE_ORIGIN);
    sql.append(" as ");
    sql.append(DbAliases.LIMS_PE_OTHER_TISSUE_ORIGIN);
    sql.append(", ");
    sql.append(DbAliases.TABLE_LIMS_PE);
    sql.append(".");
    sql.append(DbConstants.LIMS_PE_EXTERNAL_COMMENTS);
    sql.append(" as ");
    sql.append(DbAliases.LIMS_PE_EXTERNAL_COMMENTS);
    sql.append(", ");
    sql.append(DbAliases.TABLE_LIMS_PE);
    sql.append(".");
    sql.append(DbConstants.LIMS_PE_INTERNAL_COMMENTS);
    sql.append(" as ");
    sql.append(DbAliases.LIMS_PE_INTERNAL_COMMENTS);
    sql.append(", ");
    sql.append(DbAliases.TABLE_LIMS_PE);
    sql.append(".");
    sql.append(DbConstants.LIMS_PE_SOURCE_ID);
    sql.append(" as ");
    sql.append(DbAliases.LIMS_PE_SOURCE_ID);
    sql.append(", ");
    sql.append(DbAliases.TABLE_LIMS_PE);
    sql.append(".");
    sql.append(DbConstants.LIMS_PE_TCS);
    sql.append(" as ");
    sql.append(DbAliases.LIMS_PE_TCS);
    sql.append(" FROM ");
    sql.append(DbConstants.TABLE_LIMS_PE);
    sql.append(" ");
    sql.append(DbAliases.TABLE_LIMS_PE);
    sql.append(" WHERE ");
    sql.append(DbAliases.TABLE_LIMS_PE);
    sql.append(".");
    sql.append(DbConstants.PE_ID);
    sql.append(" = ? ");

    Connection con = ApiFunctions.getDbConnection();
    try {
      pstmt = con.prepareStatement(sql.toString());
      pstmt.setString(1, evaluationId);
      results = pstmt.executeQuery();
      Map columnMap = DbUtils.getColumnNames(results);
      if (results.next()) {
        evaluation = new PathologyEvaluationData(columnMap, results);
      }
    }
    catch (Exception e) {
      throw new ApiException(e);
    }
    finally {
      ApiFunctions.close(con, pstmt, results);
    }
    return evaluation;
  }

  /**
  	 * Returns the information for a specified pathology evaluation, including feature lists.
  	 * Most of the time this isn't necessary, but it is when the user wants to view the Ardais
  	 * PV report for an evaluation and when a user is creating a new evaluation using an existing
  	 * evaluation as a source
  	 *
  	 * @param  <code>String</code> evaluationId
  	 * @return  A <code>PathologyEvaluationData</code> data bean.
  	 */
  public PathologyEvaluationData getPathologyEvaluationDataWithFeatureLists(String evaluationId) {
    PathologyEvaluationAccessBean peAccess = null;
    PathologyEvaluationData peData = null;
    try {
      peAccess = new PathologyEvaluationAccessBean(evaluationId);
    }
    catch (Exception e) {
      //log the error, and return an error to the front end.
      com.ardais.bigr.api.ApiLogger.log(
        "Error instantiating PathologyEvaluationAccessBean with id = "
          + evaluationId
          + ": Error = "
          + e.getLocalizedMessage());
      throw new ApiException(e);
    }
    peData = new PathologyEvaluationData();
    peData.populate(peAccess);
    return peData;
  }

  /**
  	 * Returns all slides created from a sample, along with the images for that slide
  	 *
  	 * @param  <code>String</code> sampleId
  	 * @return  A <code>List of SlideData</code> data beans.
  	 */
  public List getSlidesForSample(String sampleId) {
    List slides = new Vector();
    SlideData slide = null;
    PreparedStatement pstmt = null;
    ResultSet results = null;
    StringBuffer sql = new StringBuffer(256);
    sql.append("SELECT ");
    sql.append(DbAliases.TABLE_SLIDE);
    sql.append(".");
    sql.append(DbConstants.SLIDE_ID);
    sql.append(" as ");
    sql.append(DbAliases.SLIDE_ID);
    sql.append(", ");
    sql.append(DbAliases.TABLE_SLIDE);
    sql.append(".");
    sql.append(DbConstants.SLIDE_SECTION_PROCEDURE);
    sql.append(" as ");
    sql.append(DbAliases.SLIDE_SECTION_PROCEDURE);
    sql.append(" FROM ");
    sql.append(DbConstants.TABLE_SLIDE);
    sql.append(" ");
    sql.append(DbAliases.TABLE_SLIDE);
    sql.append(" WHERE ");
    sql.append(DbAliases.TABLE_SLIDE);
    sql.append(".");
    sql.append(DbConstants.SLIDE_SAMPLE_ID);
    sql.append(" = ? ");
    //most recent slides are on top
    sql.append("ORDER BY ");
    sql.append(DbAliases.TABLE_SLIDE);
    sql.append(".");
    sql.append(DbConstants.CREATE_DATE);
    sql.append(" DESC, ");
    sql.append(DbAliases.TABLE_SLIDE);
    sql.append(".");
    sql.append(DbConstants.SLIDE_ID);
    sql.append(" DESC ");
    Connection con = ApiFunctions.getDbConnection();
    try {
      pstmt = con.prepareStatement(sql.toString());
      pstmt.setString(1, sampleId);
      results = pstmt.executeQuery();
      Map columnMap = DbUtils.getColumnNames(results);
      while (results.next()) {
        slide = new SlideData(columnMap, results);
        slide.setImages(getImagesForSlide(slide.getSlideId()));
        slides.add(slide);
      }
    }
    catch (Exception e) {
      throw new ApiException(e);
    }
    finally {
      ApiFunctions.close(con, pstmt, results);
    }
    return slides;
  }

  /**
  	 * Returns all child sample ids from a parent sample.
  	 *
  	 * @param  <code>String</code> sampleId
  	 * @return  A <code>List of Strings</code> containing child sample ids.
  	 */
  public List getChildSampleIdsForSample(String sampleId) {

    List childSamples = new Vector();
    PreparedStatement pstmt = null;
    ResultSet results = null;
    StringBuffer sql = new StringBuffer(256);

    sql.append("SELECT ");
    sql.append(DbAliases.TABLE_SAMPLE);
    sql.append(".");
    sql.append(DbConstants.SAMPLE_ID);
    sql.append(" as ");
    sql.append(DbAliases.SAMPLE_ID);
    sql.append(" FROM ");
    sql.append(DbConstants.TABLE_SAMPLE);
    sql.append(" ");
    sql.append(DbAliases.TABLE_SAMPLE);
    sql.append(" WHERE ");
    sql.append(DbAliases.TABLE_SAMPLE);
    sql.append(".");
    sql.append(DbConstants.SAMPLE_PARENT_ID);
    sql.append(" = ? ");

    Connection con = ApiFunctions.getDbConnection();
    try {
      pstmt = con.prepareStatement(sql.toString());
      pstmt.setString(1, sampleId);
      results = pstmt.executeQuery();

      while (results.next()) {
        childSamples.add(results.getString(DbAliases.SAMPLE_ID));
      }
    }
    catch (Exception e) {
      throw new ApiException(e);
    }
    finally {
      ApiFunctions.close(con, pstmt, results);
    }
    return childSamples;
  }
  
  //private method to get the Bms value for a slide.  The value is "Y" if the slide belongs
  //to a BMS sample, and "N" otherwise.
  public String getBmsValueForSlide(String slideId) {
    String result = FormLogic.DB_NO;
    StringBuffer sql = new StringBuffer(256);
    sql.append("SELECT SAMPLE.BMS_YN FROM ILTDS_SAMPLE SAMPLE, LIMS_SLIDE SLIDE ");
    sql.append("WHERE SLIDE.SLIDE_ID = ? AND SLIDE.SAMPLE_BARCODE_ID = SAMPLE.SAMPLE_BARCODE_ID");
    Connection con = ApiFunctions.getDbConnection();
    PreparedStatement pstmt = null;
    ResultSet results = null;
    try {
      pstmt = con.prepareStatement(sql.toString());
      pstmt.setString(1, slideId);
      results = pstmt.executeQuery();
      while (results.next()) {
        result = results.getString("BMS_YN");
      }
    }
    catch (Exception e) {
      throw new ApiException(e);
    }
    finally {
      ApiFunctions.close(con, pstmt, results);
    }
    return result;
  }

  //private method to get the images for a slide.	Images should be in the following order: 4x, 20x, 
  //then numerical starting lowest to highest.  In the case of multiple 4X or 20X images, they should 
  //be listed with the most recent image first. 
  private List getImagesForSlide(String slideId) {
    List images = new Vector();
    ImageData image = null;
    PreparedStatement pstmt = null;
    ResultSet results = null;
    StringBuffer sql = new StringBuffer(256);
    //sort the images by magnification, and within magnification by capture date so that the most recent
    //image within a magnification is first.
    sql.append(
      "SELECT * FROM ADS_IMAGET_SYN WHERE SLIDEID = ? ORDER BY MAGNIFICATION DESC, CAPTUREDATE DESC, IMAGEID DESC");
    Connection con = ApiFunctions.getDbConnection();
    try {
      pstmt = con.prepareStatement(sql.toString());
      pstmt.setString(1, slideId);
      results = pstmt.executeQuery();
      Vector image4X = new Vector();
      Vector image20X = new Vector();
      while (results.next()) {
        image = new ImageData();
        image.setCaptureDate(results.getDate("CAPTUREDATE"));
        image.setImageFilename(results.getString("IMAGEFILENAME"));
        image.setImageType(results.getString("IMAGETYPE"));
        String magnification = results.getString("MAGNIFICATION");
        if (magnification == null)
          magnification = "";
        image.setMagnification(magnification);
        image.setNotes(results.getString("IMAGENOTES"));
        image.setSlideId(slideId);
        //If this image is 4X or 20X, hang on to it so it can be added at the 
        //front of the vector.  Otherwise add it in the appropriate location in the vector
        if (magnification.equalsIgnoreCase("4X")) {
          //we need to handle multiple 4X images, so store them in a vector.  Add it to the end of 
          //the vector, so that the 4X images are ordered by capture date with the most recent image first
          image4X.add(image);
        }
        else if (magnification.equalsIgnoreCase("20X")) {
          //we need to handle multiple 20X images, so store them in a vector. Add it to the end of 
          //the vector, so that the 20X images are ordered by capture date with the most recent image first
          image20X.add(image);
        }
        else {
          //find the magnification number, and if one doesn't exist
          //or it's invalid just add this image to the end of the vector
          int newXLoc = magnification.toUpperCase().indexOf("X");
          if (newXLoc == -1) {
            images.add(image);
          }
          else {
            try {
              int newImageMag = Integer.parseInt(magnification.substring(0, newXLoc));
              //if we got here, this image has a numeric magnification.
              //Now walk the images we've put into the vector so far, and
              //figure out where this one goes.  If we get to an image that
              //has a missing or nonnumeric magnification, or one with a
              //magnification greater than this new one, put this image
              //right before it
              boolean imagePlaced = false;
              for (int i = 0; i < images.size(); i++) {
                String existMagnification = ((ImageData) images.get(i)).getMagnification();
                int existingXLoc = existMagnification.toUpperCase().indexOf("X");
                if (existingXLoc == -1) {
                  images.add(i, image);
                }
                else
                  try {
                    int existImageMag = Integer.parseInt(magnification.substring(0, existingXLoc));
                    if (existImageMag > newImageMag) {
                      images.add(i, image);
                      imagePlaced = true;
                      break;
                    }
                  }
                  catch (NumberFormatException e) {
                    images.add(i, image);
                  }
              }
              //if we haven't found a place for this image yet, add it to the end
              //of the vector
              if (!imagePlaced) {
                images.add(image);
              }
            }
            catch (NumberFormatException e) {
              images.add(image);
            }
          }
        }
      }
      //put the 20x images in the front, ordered by capture date with the most recent image first
      if (image20X.size() > 0) {
        for (int i = image20X.size(); i > 0; i--) {
          images.add(0, image20X.get(i - 1));
        }
      }
      //put the 4x images in the front, ordered by capture date with the most recent image first
      if (image4X.size() > 0) {
        for (int i = image4X.size(); i > 0; i--) {
          images.add(0, image4X.get(i - 1));
        }
      }
    }
    catch (Exception e) {
      throw new ApiException(e);
    }
    finally {
      ApiFunctions.close(con, pstmt, results);
    }
    return images;
  }

  /**
  	 * Returns all evaluations created for a sample
  	 *
  	 * @param  <code>String</code> sampleId
  	 * @return  A <code>List of PathologyEvaluationData</code> data beans.
  	 */
  public List getPathologyEvaluationsForSample(String sampleId) {
    List evaluations = new Vector();
    PathologyEvaluationData evaluation = null;
    PreparedStatement pstmt = null;
    ResultSet results = null;
    StringBuffer sql = new StringBuffer(256);
    sql.append("SELECT ");
    sql.append(DbAliases.TABLE_LIMS_PE);
    sql.append(".");
    sql.append(DbConstants.PE_ID);
    sql.append(" as ");
    sql.append(DbAliases.PE_ID);
    sql.append(", ");
    sql.append(DbAliases.TABLE_LIMS_PE);
    sql.append(".");
    sql.append(DbConstants.LIMS_PE_SLIDE_ID);
    sql.append(" as ");
    sql.append(DbAliases.LIMS_PE_SLIDE_ID);
    sql.append(", ");
    sql.append(DbAliases.TABLE_LIMS_PE);
    sql.append(".");
    sql.append(DbConstants.LIMS_PE_SAMPLE_ID);
    sql.append(" as ");
    sql.append(DbAliases.LIMS_PE_SAMPLE_ID);
    sql.append(", ");
    sql.append(DbAliases.TABLE_LIMS_PE);
    sql.append(".");
    sql.append(DbConstants.LIMS_PE_APPEARANCE);
    sql.append(" as ");
    sql.append(DbAliases.LIMS_PE_APPEARANCE);
    sql.append(", ");
    sql.append(DbAliases.TABLE_LIMS_PE);
    sql.append(".");
    sql.append(DbConstants.LIMS_PE_REPORTED);
    sql.append(" as ");
    sql.append(DbAliases.LIMS_PE_REPORTED);
    sql.append(", ");
    sql.append(DbAliases.TABLE_LIMS_PE);
    sql.append(".");
    sql.append(DbConstants.LIMS_PE_CREATE_TYPE);
    sql.append(" as ");
    sql.append(DbAliases.LIMS_PE_CREATE_TYPE);
    sql.append(", ");
    sql.append(DbAliases.TABLE_LIMS_PE);
    sql.append(".");
    sql.append(DbConstants.LIMS_PE_TMR);
    sql.append(" as ");
    sql.append(DbAliases.LIMS_PE_TMR);
    sql.append(", ");
    sql.append(DbAliases.TABLE_LIMS_PE);
    sql.append(".");
    sql.append(DbConstants.LIMS_PE_NRM);
    sql.append(" as ");
    sql.append(DbAliases.LIMS_PE_NRM);
    sql.append(", ");
    sql.append(DbAliases.TABLE_LIMS_PE);
    sql.append(".");
    sql.append(DbConstants.LIMS_PE_TAS);
    sql.append(" as ");
    sql.append(DbAliases.LIMS_PE_TAS);
    sql.append(", ");
    sql.append(DbAliases.TABLE_LIMS_PE);
    sql.append(".");
    sql.append(DbConstants.LIMS_PE_NEC);
    sql.append(" as ");
    sql.append(DbAliases.LIMS_PE_NEC);
    sql.append(", ");
    sql.append(DbAliases.TABLE_LIMS_PE);
    sql.append(".");
    sql.append(DbConstants.LIMS_PE_LSN);
    sql.append(" as ");
    sql.append(DbAliases.LIMS_PE_LSN);
    sql.append(", ");
    sql.append(DbAliases.TABLE_LIMS_PE);
    sql.append(".");
    sql.append(DbConstants.LIMS_PE_DX);
    sql.append(" as ");
    sql.append(DbAliases.LIMS_PE_DX);
    sql.append(", ");
    sql.append(DbAliases.TABLE_LIMS_PE);
    sql.append(".");
    sql.append(DbConstants.LIMS_PE_TISSUE_ORIGIN);
    sql.append(" as ");
    sql.append(DbAliases.LIMS_PE_TISSUE_ORIGIN);
    sql.append(", ");
    sql.append(DbAliases.TABLE_LIMS_PE);
    sql.append(".");
    sql.append(DbConstants.LIMS_PE_CREATE_DATE);
    sql.append(" as ");
    sql.append(DbAliases.LIMS_PE_CREATE_DATE);
    sql.append(", ");
    sql.append(DbAliases.TABLE_LIMS_PE);
    sql.append(".");
    sql.append(DbConstants.LIMS_PE_CREATE_USER);
    sql.append(" as ");
    sql.append(DbAliases.LIMS_PE_CREATE_USER);
    sql.append(", ");
    sql.append(DbAliases.TABLE_LIMS_PE);
    sql.append(".");
    sql.append(DbConstants.LIMS_PE_MIGRATED);
    sql.append(" as ");
    sql.append(DbAliases.LIMS_PE_MIGRATED);
    sql.append(", ");
    sql.append(DbAliases.TABLE_LIMS_PE);
    sql.append(".");
    sql.append(DbConstants.LIMS_PE_TISSUE_FINDING);
    sql.append(" as ");
    sql.append(DbAliases.LIMS_PE_TISSUE_FINDING);
    sql.append(", ");
    sql.append(DbAliases.TABLE_LIMS_PE);
    sql.append(".");
    sql.append(DbConstants.LIMS_PE_OTHER_TISSUE_FINDING);
    sql.append(" as ");
    sql.append(DbAliases.LIMS_PE_OTHER_TISSUE_FINDING);
    sql.append(", ");
    sql.append(DbAliases.TABLE_LIMS_PE);
    sql.append(".");
    sql.append(DbConstants.LIMS_PE_OTHER_DIAGNOSIS);
    sql.append(" as ");
    sql.append(DbAliases.LIMS_PE_OTHER_DIAGNOSIS);
    sql.append(", ");
    sql.append(DbAliases.TABLE_LIMS_PE);
    sql.append(".");
    sql.append(DbConstants.LIMS_PE_OTHER_TISSUE_ORIGIN);
    sql.append(" as ");
    sql.append(DbAliases.LIMS_PE_OTHER_TISSUE_ORIGIN);
    sql.append(", ");
    sql.append(DbAliases.TABLE_LIMS_PE);
    sql.append(".");
    sql.append(DbConstants.LIMS_PE_EXTERNAL_COMMENTS);
    sql.append(" as ");
    sql.append(DbAliases.LIMS_PE_EXTERNAL_COMMENTS);
    sql.append(", ");
    sql.append(DbAliases.TABLE_LIMS_PE);
    sql.append(".");
    sql.append(DbConstants.LIMS_PE_INTERNAL_COMMENTS);
    sql.append(" as ");
    sql.append(DbAliases.LIMS_PE_INTERNAL_COMMENTS);
    sql.append(", ");
    sql.append(DbAliases.TABLE_LIMS_PE);
    sql.append(".");
    sql.append(DbConstants.LIMS_PE_SOURCE_ID);
    sql.append(" as ");
    sql.append(DbAliases.LIMS_PE_SOURCE_ID);
    sql.append(", ");
    sql.append(DbAliases.TABLE_LIMS_PE);
    sql.append(".");
    sql.append(DbConstants.LIMS_PE_TCS);
    sql.append(" as ");
    sql.append(DbAliases.LIMS_PE_TCS);
    sql.append(" FROM ");
    sql.append(DbConstants.TABLE_LIMS_PE);
    sql.append(" ");
    sql.append(DbAliases.TABLE_LIMS_PE);
    sql.append(" WHERE ");
    sql.append(DbAliases.TABLE_LIMS_PE);
    sql.append(".");
    sql.append(DbConstants.LIMS_PE_SAMPLE_ID);
    sql.append(" = ? ORDER BY ");
    sql.append(DbAliases.TABLE_LIMS_PE);
    sql.append(".");
    sql.append(DbConstants.LIMS_PE_CREATE_DATE);
    sql.append(" DESC");

    Connection con = ApiFunctions.getDbConnection();
    try {
      pstmt = con.prepareStatement(sql.toString());
      pstmt.setString(1, sampleId);
      results = pstmt.executeQuery();
      Map columnMap = DbUtils.getColumnNames(results);
      while (results.next()) {
        evaluation = new PathologyEvaluationData(columnMap, results);
        evaluations.add(evaluation);
      }
    }
    catch (Exception e) {
      throw new ApiException(e);
    }
    finally {
      ApiFunctions.close(con, pstmt, results);
    }
    return evaluations;
  }

  /**
  	 * Returns all pathologist that have performed an evaluation
  	 *
  	 * 
  	 * @return  A List of Ardais User Ids 
  	 */
  public List getPathologistList() {
    List pathologists = new Vector();
    PreparedStatement pstmt = null;
    ResultSet results = null;
    StringBuffer sql = new StringBuffer(256);
    sql.append("SELECT distinct(");
    sql.append(DbConstants.LIMS_PE_CREATE_USER);
    sql.append(") as ");
    sql.append(DbAliases.LIMS_PE_CREATE_USER);
    sql.append(" FROM ");
    sql.append(DbConstants.TABLE_LIMS_PE);
    sql.append(" ORDER BY UPPER(");
    sql.append(DbAliases.LIMS_PE_CREATE_USER);
    sql.append(")");

    Connection con = ApiFunctions.getDbConnection();
    try {
      pstmt = con.prepareStatement(sql.toString());
      results = pstmt.executeQuery();

      while (results.next()) {
        pathologists.add(results.getString(DbAliases.LIMS_PE_CREATE_USER));
      }
    }
    catch (Exception e) {
      throw new ApiException(e);
    }
    finally {
      ApiFunctions.close(con, pstmt, results);
    }
    return pathologists;
  }

  /**
   * Returns a new PathologyEvaluationSampleData data bean.  This routine
   * is called from any transaction that returns sample data for a pathology operation (currently
   * CreatePathologyEvaluationPrepare and GetPathologyEvaluations), so make sure all data
   * required for those screens is populated. 
   * @param <code>String</code> sampleId.
   * @return <code>PathologyEvaluationSampleData</code>
   */
  public PathologyEvaluationSampleData getEvaluationSampleData(String sampleId) {
    PathologyEvaluationSampleData peSampleData = new PathologyEvaluationSampleData();

    peSampleData.setSample(getSampleData(sampleId));
    peSampleData.setConsent(getConsentData(sampleId));
    peSampleData.setAsm(getAsmData(sampleId));
    peSampleData.setPathReportSections(getPdcPathReportSectionData(sampleId));
    //Get PDC path report data.         
    peSampleData.setPathReport(getPdcPathReportData(sampleId));
    Iterator iterator = peSampleData.getPathReportSections().iterator();
    while (iterator.hasNext()) {
      PathReportSectionData section = (PathReportSectionData) iterator.next();
      if (section.isPrimarySection()) {
        peSampleData.setPrimarySection(section);
      }
    }
    peSampleData.setReportedEvaluation(getReportedPathologyEvaluation(sampleId));
    peSampleData.setSlides(getSlidesForSample(sampleId));

    return peSampleData;
  }

  /**
  	 * Returns Throughput report details of user/all users
  	 * within specified dates. 
  	 * @param  <code>String</code> userId.
  	 * @param <code>Timestamp</code> fromDate.
  	 * @param <code>Timestamp</code> toDate.
  	 * @return <code>LegalValueSet</code>.
  	 */
  public LegalValueSet getPvThroughputReport(String userId, Timestamp fromDate, Timestamp toDate) {
    StringBuffer sql = new StringBuffer(256);
    PreparedStatement pstmt = null;
    ResultSet rSet = null;
    LegalValueSet lSet = new LegalValueSet();

    sql.append("select count(*) count, create_user ");
    sql.append("from lims_pathology_evaluation ");
    sql.append("where create_date > ? ");
    sql.append("and create_date < ? ");
    if (!LimsConstants.DEFAULT_ALL.equals(userId)) {
      sql.append(" and create_user = ? ");
    }
    sql.append(" group by create_user ");
    sql.append(" order by upper(create_user)");

    Connection con = ApiFunctions.getDbConnection();
    try {
      pstmt = con.prepareStatement(sql.toString());
      pstmt.setTimestamp(1, fromDate);
      pstmt.setTimestamp(2, toDate);
      if (!LimsConstants.DEFAULT_ALL.equals(userId)) {
        pstmt.setString(3, userId);
      }
      rSet = pstmt.executeQuery();

      while (rSet.next()) {
        lSet.addLegalValue(rSet.getString("create_user"), rSet.getString("count"));
      }
    }
    catch (Exception e) {
      throw new ApiException(e);
    }
    finally {
      ApiFunctions.close(con, pstmt, rSet);
    }
    return lSet;
  }

  /**
   * Returns PathReportData for the specified sample.  It doesn't fill in all of the data
   * fields on the PathReportData object, just the ones that we need for our
   * LIMS Pathology-related purposes here.  Specifically, the fields that will be filled in are:
   * {@link PathReportData#getPathReportId() getPathReportId} and
   * {@link PathReportData#getRawPathReport() getRawPathReport}.
   * 
   * @param  sampleId the sample for which to retrieve DDC path report information
   * @return the PathReportData object as described above if the specified sample has a DDC
   *   path report, otherwise null.
   */
  private PathReportData getPdcPathReportData(String sampleId) {
    //At this point only a few path report fields are used.  If we ever need a lot of the
    //fields, we should just have this call
    //DDCPathologyBean::getPathReportSummary(PathReportData pathReport) instead of replicating
    //all of the code here.

    StringBuffer sql = new StringBuffer(512);
    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rSet = null;
    PathReportData pathReportData = null;

    // The PathReportData that we pass the result set to below expects columns to have the same
    // names as in the underlying table, so we deliberately don't use column aliases in the
    // select list below.
    //
    sql.append("SELECT ");
    sql.append(DbAliases.TABLE_PATH);
    sql.append(".");
    sql.append(DbConstants.PATH_ID);
    sql.append(", ");
    sql.append(DbAliases.TABLE_PATH);
    sql.append(".");
    sql.append(DbConstants.PATH_ASCII_REPORT);
    sql.append(" ");
    sql.append(" FROM ");
    sql.append(DbConstants.TABLE_SAMPLE);
    sql.append(" ");
    sql.append(DbAliases.TABLE_SAMPLE);
    sql.append(",");
    sql.append(DbConstants.TABLE_ASM);
    sql.append(" ");
    sql.append(DbAliases.TABLE_ASM);
    sql.append(",");
    sql.append(DbConstants.TABLE_PATH);
    sql.append(" ");
    sql.append(DbAliases.TABLE_PATH);
    sql.append(" WHERE ");
    sql.append(DbAliases.TABLE_SAMPLE);
    sql.append(".");
    sql.append(DbConstants.SAMPLE_ID);
    sql.append(" = ? AND ");
    sql.append(DbAliases.TABLE_SAMPLE);
    sql.append(".");
    sql.append(DbConstants.ASM_ID);
    sql.append(" = ");
    sql.append(DbAliases.TABLE_ASM);
    sql.append(".");
    sql.append(DbConstants.ASM_ID);
    sql.append(" AND ");
    sql.append(DbAliases.TABLE_ASM);
    sql.append(".");
    sql.append(DbConstants.CONSENT_ID);
    sql.append(" = ");
    sql.append(DbAliases.TABLE_PATH);
    sql.append(".");
    sql.append(DbConstants.CONSENT_ID);

    try {
      con = ApiFunctions.getDbConnection();

      pstmt = con.prepareStatement(sql.toString());
      pstmt.setString(1, sampleId);

      rSet = pstmt.executeQuery();

      if (rSet.next()) {
        pathReportData = new PathReportData(rSet);
      }
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(con, pstmt, rSet);
    }

    return pathReportData;
  }

  /**
   * Updates rows which are not fixed in ARD_OTHER_CODE_EDITS table for 
   * pathology evaluation created for sampleId with specified statusFlag.
   * @param sampleId
   * @param statusFlag
   */
  public void updateLimsOCEDataStatus(String sampleId, String statusFlag, String userId) {
    StringBuffer sql = new StringBuffer(256);
    PreparedStatement pstmt = null;
    ResultSet rSet = null;
    String pe_id = null;

    Oce oceBean = null;

    sql.append("SELECT PE_ID, " + DbConstants.LIMS_PE_DX + ", ");
    sql.append(DbConstants.LIMS_PE_TISSUE_ORIGIN + ", ");
    sql.append(DbConstants.LIMS_PE_TISSUE_FINDING + " ");
    sql.append("FROM LIMS_PATHOLOGY_EVALUATION ");
    sql.append("WHERE SAMPLE_BARCODE_ID = ?");

    Connection con = ApiFunctions.getDbConnection();
    try {
      pstmt = con.prepareStatement(sql.toString());
      pstmt.setString(1, sampleId);

      rSet = pstmt.executeQuery();

      while (rSet.next()) {
        pe_id = rSet.getString("PE_ID");
        //Update status of diagnosis if code is Other diagnosis
        if (FormLogic.OTHER_DX.equals(rSet.getString(DbConstants.LIMS_PE_DX))) {
          if (oceBean == null) {
            OceHome home = (OceHome) EjbHomes.getHome(OceHome.class);
            oceBean = home.create();
          }
          oceBean.updateStatus(
            DbConstants.TABLE_LIMS_PE.toUpperCase(),
            DbConstants.LIMS_PE_DX.toUpperCase(),
            OceUtil.OCE_TYPECODE_DIAGNOSIS_IND,
            pe_id,
            userId,
            statusFlag);
        }
        //Update status of tissue of origin if code is Other tissue
        if (FormLogic.OTHER_TISSUE.equals(rSet.getString(DbConstants.LIMS_PE_TISSUE_ORIGIN))) {
          if (oceBean == null) {
            OceHome home = (OceHome) EjbHomes.getHome(OceHome.class);
            oceBean = home.create();
          }
          oceBean.updateStatus(
            DbConstants.TABLE_LIMS_PE.toUpperCase(),
            DbConstants.LIMS_PE_TISSUE_ORIGIN.toUpperCase(),
            OceUtil.OCE_TYPECODE_TISSUE_IND,
            pe_id,
            userId,
            statusFlag);
        }
        //Update status of tissue of origin if code is Other tissue
        if (FormLogic.OTHER_TISSUE.equals(rSet.getString(DbConstants.LIMS_PE_TISSUE_FINDING))) {
          if (oceBean == null) {
            OceHome home = (OceHome) EjbHomes.getHome(OceHome.class);
            oceBean = home.create();
          }
          oceBean.updateStatus(
            DbConstants.TABLE_LIMS_PE.toUpperCase(),
            DbConstants.LIMS_PE_TISSUE_FINDING.toUpperCase(),
            OceUtil.OCE_TYPECODE_TISSUE_IND,
            pe_id,
            userId,
            statusFlag);
        }

      }

    }
    catch (Exception e) {
      throw new ApiException(e);
    }
    finally {
      ApiFunctions.close(con, pstmt, rSet);
    }
  }
  /**
     * Returns all incidents of a given type created for a sample
     * since a given date
     *
     * @param  <code>String</code> sampleId
     * @param  <code>String</code> action
     * @param  <code>Timestamp</code> fromDate
     * @return  A <code>List of IncidentReportLineItem</code> data beans.
     */
  public List getIncidentsForSample(String sampleId, String action, Timestamp fromDate) {
    List incidents = new ArrayList();
    IncidentReportLineItem incident = null;
    PreparedStatement pstmt = null;
    ResultSet results = null;
    StringBuffer sql = new StringBuffer(256);
    sql.append("SELECT ");
    sql.append(DbAliases.TABLE_LIMS_INCIDENTS);
    sql.append(".");
    sql.append(DbConstants.INCIDENT_ID);
    sql.append(" as ");
    sql.append(DbAliases.INCIDENT_ID);
    sql.append(", ");
    sql.append(DbAliases.TABLE_LIMS_INCIDENTS);
    sql.append(".");
    sql.append(DbConstants.LIMS_INCIDENT_CREATE_USER);
    sql.append(" as ");
    sql.append(DbAliases.LIMS_INCIDENT_CREATE_USER);
    sql.append(", ");
    sql.append(DbAliases.TABLE_LIMS_INCIDENTS);
    sql.append(".");
    sql.append(DbConstants.LIMS_INCIDENT_CREATE_DATE);
    sql.append(" as ");
    sql.append(DbAliases.LIMS_INCIDENT_CREATE_DATE);
    sql.append(", ");
    sql.append(DbAliases.TABLE_LIMS_INCIDENTS);
    sql.append(".");
    sql.append(DbConstants.LIMS_INCIDENT_SAMPLE_ID);
    sql.append(" as ");
    sql.append(DbAliases.LIMS_INCIDENT_SAMPLE_ID);
    sql.append(", ");
    sql.append(DbAliases.TABLE_LIMS_INCIDENTS);
    sql.append(".");
    sql.append(DbConstants.LIMS_INCIDENT_CONSENT_ID);
    sql.append(" as ");
    sql.append(DbAliases.LIMS_INCIDENT_CONSENT_ID);
    sql.append(", ");
    sql.append(DbAliases.TABLE_LIMS_INCIDENTS);
    sql.append(".");
    sql.append(DbConstants.LIMS_INCIDENT_ACTION);
    sql.append(" as ");
    sql.append(DbAliases.LIMS_INCIDENT_ACTION);
    sql.append(", ");
    sql.append(DbAliases.TABLE_LIMS_INCIDENTS);
    sql.append(".");
    sql.append(DbConstants.LIMS_INCIDENT_REASON);
    sql.append(" as ");
    sql.append(DbAliases.LIMS_INCIDENT_REASON);
    sql.append(", ");
    sql.append(DbAliases.TABLE_LIMS_INCIDENTS);
    sql.append(".");
    sql.append(DbConstants.LIMS_INCIDENT_SLIDE_ID);
    sql.append(" as ");
    sql.append(DbAliases.LIMS_INCIDENT_SLIDE_ID);
    sql.append(", ");
    sql.append(DbAliases.TABLE_LIMS_INCIDENTS);
    sql.append(".");
    sql.append(DbConstants.LIMS_INCIDENT_PATHOLOGIST);
    sql.append(" as ");
    sql.append(DbAliases.LIMS_INCIDENT_PATHOLOGIST);
    sql.append(", ");
    sql.append(DbAliases.TABLE_LIMS_INCIDENTS);
    sql.append(".");
    sql.append(DbConstants.LIMS_INCIDENT_RE_PV_REQUESTOR_CODE);
    sql.append(" as ");
    sql.append(DbAliases.LIMS_INCIDENT_RE_PV_REQUESTOR_CODE);
    sql.append(", ");
    sql.append(DbAliases.TABLE_LIMS_INCIDENTS);
    sql.append(".");
    sql.append(DbConstants.LIMS_INCIDENT_ACTION_OTHER);
    sql.append(" as ");
    sql.append(DbAliases.LIMS_INCIDENT_ACTION_OTHER);
    sql.append(", ");
    sql.append(DbAliases.TABLE_LIMS_INCIDENTS);
    sql.append(".");
    sql.append(DbConstants.LIMS_INCIDENT_COMMENTS);
    sql.append(" as ");
    sql.append(DbAliases.LIMS_INCIDENT_COMMENTS);
    sql.append(" FROM ");
    sql.append(DbConstants.TABLE_LIMS_INCIDENTS);
    sql.append(" ");
    sql.append(DbAliases.TABLE_LIMS_INCIDENTS);
    sql.append(" WHERE ");
    sql.append(DbAliases.TABLE_LIMS_INCIDENTS);
    sql.append(".");
    sql.append(DbConstants.LIMS_INCIDENT_SAMPLE_ID);
    sql.append(" = ? AND ");
    sql.append(DbAliases.TABLE_LIMS_INCIDENTS);
    sql.append(".");
    sql.append(DbConstants.LIMS_INCIDENT_ACTION);
    sql.append(" = ? AND ");
    sql.append(DbAliases.TABLE_LIMS_INCIDENTS);
    sql.append(".");
    sql.append(DbConstants.LIMS_INCIDENT_CREATE_DATE);
    sql.append(" > ? ORDER BY ");
    sql.append(DbAliases.TABLE_LIMS_INCIDENTS);
    sql.append(".");
    sql.append(DbConstants.LIMS_INCIDENT_CREATE_DATE);
    sql.append(" DESC");

    Connection con = ApiFunctions.getDbConnection();
    try {
      pstmt = con.prepareStatement(sql.toString());
      pstmt.setString(1, sampleId);
      pstmt.setString(2, action);
      pstmt.setTimestamp(3, fromDate);
      results = pstmt.executeQuery();
      Map columnMap = DbUtils.getColumnNames(results);
      while (results.next()) {
        incident = new IncidentReportLineItem(columnMap, results);
        incidents.add(incident);
      }
    }
    catch (Exception e) {
      throw new ApiException(e);
    }
    finally {
      ApiFunctions.close(con, pstmt, results);
    }
    return incidents;
  }
}
