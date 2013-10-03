package com.ardais.bigr.iltds.bizlogic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.iltds.assistants.SampleStatusAssistant;
import com.ardais.bigr.iltds.helpers.SampleFilter;
import com.ardais.bigr.iltds.helpers.SampleSelect;
import com.ardais.bigr.javabeans.SampleData;
import com.ardais.bigr.query.generator.LogicalRepositoryDetailQueryBuilder;
import com.ardais.bigr.security.SecurityInfo;
import com.ardais.bigr.util.gen.DbAliases;
import com.ardais.bigr.util.gen.DbConstants;
import com.gulfstreambio.kc.form.FormInstance;
import com.gulfstreambio.kc.form.FormInstanceQueryCriteria;
import com.gulfstreambio.kc.form.FormInstanceService;
import com.gulfstreambio.kc.form.def.FormDefinitionService;
import com.gulfstreambio.kc.form.def.data.DataFormDefinition;

/**
 * This class provides static methods that return 
 * {@link com.ardais.bigr.javabeans.SampleData SampleData} objects that match various criteria.
 */
public class SampleFinder {

  // Private to prevent instantiation since this class has all static methods.
  private SampleFinder() {
    super();
  }

  /**
   * Returns an indication of whether the sample with the specified id exists.
   * 
   * @param sampleId the sample id
   * @return <code>true</code> if the sample exists; <code>false</code> otherwise.  
   */
  public static boolean exists(String sampleId) {
    boolean found = false;
    if (sampleId != null) {   
      String sql = "select * from iltds_sample where sample_barcode_id = ?";
      Connection con = null;
      PreparedStatement pstmt = null;
      ResultSet rs = null;
      try {
        con = ApiFunctions.getDbConnection();
        pstmt = con.prepareStatement(sql);
        pstmt.setString(1, sampleId);
        rs = pstmt.executeQuery();
        found = rs.next();
      }
      catch (Exception e) {
        ApiFunctions.throwAsRuntimeException(e);
      }
      finally {
        ApiFunctions.close(con, pstmt, rs);
      }
    }
    return found;
  }

  /**
   * Return the samples that match the specified filters.
   * 
   * @param secInfo The security info of the current user.
   * @param selector The specification of which sample details to populate in the result.
   * @param filter The specification of which samples to return.
   * @return The list of samples, or an empty list if there are no matching samples.  Each
   *   item in the list will be a {@link com.ardais.bigr.javabeans.SampleData SampleData} object.
   */
  public static List find(SecurityInfo secInfo, SampleSelect selector, SampleFilter filter) {
    if (selector == null) {
      throw new IllegalArgumentException("In SampleFinder.find: The selector must not be null.");
    }
    if (filter == null) {
      throw new IllegalArgumentException("In SampleFinder.find: The filter must not be null.");
    }
    if (!selector.isSomethingSelected()) {
      throw new IllegalArgumentException("In SampleFinder.find: The selector must specify that at least one piece of information is to be included.");
    }

    List samples = querySamples(secInfo, selector, filter);
    
    return samples;
  }

  /**
   * Returns the sample with the specified barcode id if one exists, otherwise returns null.
   * 
   * @param secInfo The security info of the current user.
   * @param selector The specification of which sample details to populate in the result.
   * @param sampleId The barcode id of the sample to return.
   * @return The sample, or null if there is no sample with the specified barcode id.
   */
  public static SampleData find(SecurityInfo secInfo, SampleSelect selector, String sampleId) {
    List matches = find(secInfo, selector, new SampleFilter(sampleId));

    if (matches.size() == 0) {
      return null;
    }
    else {
      return (SampleData) matches.get(0);
    }
  }

  /**
   * Returns the sample with the specified barcode id if one exists, otherwise returns null.
   * 
   * @param selector The specification of which sample details to populate in the result.
   * @param sampleId The barcode id of the sample to return.
   * @return The sample, or null if there is no sample with the specified barcode id.
   */
  public static SampleData find(SampleSelect selector, String sampleId) {
    List matches = find(null, selector, new SampleFilter(sampleId));

    if (matches.size() == 0) {
      return null;
    }
    else {
      return (SampleData) matches.get(0);
    }
  }

  /**
   * Returns the samples that match the specified filter, returning a list of 
   * {@link com.ardais.bigr.javabeans.SampleData SampleData} objects containing the data requested 
   * in the specified selector.
   * 
   * @param selector The specification of which sample details to populate in the result.
   * @param filter The specification of which samples to return.
   * @return The list of matching samples as SampleData objects.
   */
  private static List querySamples(SecurityInfo secInfo, SampleSelect selector, SampleFilter filter) {
    List samples = querySampleBasics(selector, filter);
    if (selector.isIncludeSampleTypeConfig()) {
      querySampleConfig(samples);
    }
    querySampleStatuses(samples, selector);
    querySampleInventoryGroups(secInfo, samples, selector);
    
    return samples;
  }

  /**
   * Performs a basic sample query to find the samples that match the specified filter, and returns 
   * a list of {@link com.ardais.bigr.javabeans.SampleData SampleData} objects containing the query 
   * results.
   * 
   * We do this query even if selector.isIncludeBasic() is false.  When selector.isIncludeBasic()
   * is false, the only basic properties that will be defined in the resulting SampleData
   * objects will be the sample barcode id, sample alias and sample type.  When 
   * selector.isIncludeBasic() is true, all of the basic sample properties will be populated.
   * 
   * @param selector The specification of which sample details to populate in the result.
   * @param filter The specification of which samples to return.
   * @return The list of matching samples as SampleData objects.
   */
  private static List querySampleBasics(SampleSelect selector, SampleFilter filter) {

    List samples = new ArrayList();

    boolean isIncludeBasic = selector.isIncludeBasic();
    boolean isIncludeExtendedBasicImported =
      (selector.getExtendedBasic() == SampleSelect.EXTENDED_BASIC_FOR_IMPORTED);
    String sampleWhereClauses = filter.getSqlWhereClauses(DbAliases.TABLE_SAMPLE);

    StringBuffer query = new StringBuffer(512);
    query.append("select ");
    if (isIncludeBasic) {
      query.append(DbAliases.TABLE_SAMPLE);
      query.append(".*");
      if (isIncludeExtendedBasicImported) {
        query.append(", "); 
        query.append(DbAliases.TABLE_CONSENT);
        query.append("."); 
        query.append(DbConstants.CONSENT_CUSTOMER_ID);
        query.append(" as "); 
        query.append(DbAliases.CONSENT_CUSTOMER_ID);

        query.append(", "); 
        query.append(DbAliases.TABLE_DONOR);
        query.append("."); 
        query.append(DbConstants.DONOR_CUSTOMER_ID);
        query.append(" as "); 
        query.append(DbAliases.DONOR_CUSTOMER_ID);

        query.append(", "); 
        query.append(DbAliases.TABLE_ASM);
        query.append("."); 
        query.append(DbConstants.ASM_TISSUE);
        query.append(" as "); 
        query.append(DbAliases.ASM_TISSUE);

        query.append(", "); 
        query.append(DbAliases.TABLE_ASM);
        query.append("."); 
        query.append(DbConstants.ASM_TISSUE_OTHER);
        query.append(" as "); 
        query.append(DbAliases.ASM_TISSUE_OTHER);

        query.append(", "); 
        query.append(DbAliases.TABLE_ASM);
        query.append("."); 
        query.append(DbConstants.ASM_APPEARANCE);
        query.append(" as "); 
        query.append(DbAliases.ASM_APPEARANCE);

        query.append(", "); 
        query.append(DbAliases.TABLE_ASM_FORM);
        query.append("."); 
        query.append(DbConstants.ASM_FORM_PROCEDURE);
        query.append(" as "); 
        query.append(DbAliases.ASM_FORM_PROCEDURE);

        query.append(", "); 
        query.append(DbAliases.TABLE_ASM_FORM);
        query.append("."); 
        query.append(DbConstants.ASM_FORM_PROCEDURE_OTHER);
        query.append(" as "); 
        query.append(DbAliases.ASM_FORM_PROCEDURE_OTHER);

        query.append(", "); 
        query.append(DbAliases.TABLE_ASM_FORM);
        query.append("."); 
        query.append(DbConstants.ASM_FORM_PREPARED_BY);
        query.append(" as "); 
        query.append(DbAliases.ASM_FORM_PREPARED_BY);
      }
    }
    else {
      query.append(DbAliases.TABLE_SAMPLE);
      query.append("."); 
      query.append(DbConstants.SAMPLE_ID);
      query.append(" as "); 
      query.append(DbAliases.SAMPLE_ID);

      query.append(", "); 
      query.append(DbAliases.TABLE_SAMPLE);
      query.append("."); 
      query.append(DbConstants.SAMPLE_SAMPLE_TYPE_CID);
      query.append(" as "); 
      query.append(DbAliases.SAMPLE_SAMPLE_TYPE_CID);

      query.append(", "); 
      query.append(DbAliases.TABLE_SAMPLE);
      query.append("."); 
      query.append(DbConstants.SAMPLE_CUSTOMER_ID);
      query.append(" as "); 
      query.append(DbAliases.SAMPLE_CUSTOMER_ID);
    }

    // Add the FROM clause.
    query.append("\n from ");
    query.append(DbConstants.TABLE_SAMPLE);
    query.append(" ");
    query.append(DbAliases.TABLE_SAMPLE);
    if (isIncludeExtendedBasicImported) {
      query.append(", ");
      query.append(DbConstants.TABLE_ASM);
      query.append(" ");
      query.append(DbAliases.TABLE_ASM);

      query.append(", ");
      query.append(DbConstants.TABLE_ASM_FORM);
      query.append(" ");
      query.append(DbAliases.TABLE_ASM_FORM);

      query.append(", ");
      query.append(DbConstants.TABLE_CONSENT);
      query.append(" ");
      query.append(DbAliases.TABLE_CONSENT);

      query.append(", ");
      query.append(DbConstants.TABLE_DONOR);
      query.append(" ");
      query.append(DbAliases.TABLE_DONOR);
    }

    // Add the WHERE clause.
    query.append("\n where ");
    query.append('(');
    query.append(sampleWhereClauses);
    query.append(") ");
    if (isIncludeExtendedBasicImported) {
      query.append("\n  and ");
      query.append(DbAliases.TABLE_SAMPLE);
      query.append(".");
      query.append(DbConstants.SAMPLE_CONSENT_ID);
      query.append(" = ");
      query.append(DbAliases.TABLE_CONSENT);
      query.append(".");
      query.append(DbConstants.CONSENT_ID);
      query.append("(+)");

      query.append("\n  and ");
      query.append(DbAliases.TABLE_SAMPLE);
      query.append(".");
      query.append(DbConstants.SAMPLE_ARDAIS_ID);
      query.append(" = ");
      query.append(DbAliases.TABLE_DONOR);
      query.append(".");
      query.append(DbConstants.DONOR_ID);
      query.append("(+)");

      query.append("\n  and ");
      query.append(DbAliases.TABLE_SAMPLE);
      query.append(".");
      query.append(DbConstants.SAMPLE_ASM_ID);
      query.append(" = ");
      query.append(DbAliases.TABLE_ASM);
      query.append(".");
      query.append(DbConstants.ASM_ID);
      query.append("(+)");

      query.append("\n  and ");
      query.append(DbAliases.TABLE_ASM);
      query.append(".");
      query.append(DbConstants.ASM_FORM_ID);
      query.append(" = ");
      query.append(DbAliases.TABLE_ASM_FORM);
      query.append(".");
      query.append(DbConstants.ASM_FORM_FORM_ID);
      query.append("(+)");
    }

    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
      con = ApiFunctions.getDbConnection();
      pstmt = con.prepareStatement(query.toString());
      filter.bindSqlWhereClauses(pstmt, 1);
      
      rs = ApiFunctions.queryDb(pstmt, con);
      while (rs.next()) {
        SampleData sampleData = new SampleData();
        sampleData.populateBasicInfo(rs);
        if (isIncludeExtendedBasicImported) {
          sampleData.populateBasicImportedInfo(rs);
        }
        samples.add(sampleData);
      }
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(con, pstmt, rs);
    }
    
    //if we're retrieving basic information, retrieve the registration form definition
    //and instance (if any) for all samples retrieved
    if (isIncludeBasic) {
      Iterator sampleIterator = samples.iterator();
      while (sampleIterator.hasNext()) {
        SampleData sample = (SampleData) sampleIterator.next();
        String registrationFormDefinitionId = sample.getRegistrationFormId();
        if (!ApiFunctions.isEmpty(registrationFormDefinitionId)) {
          DataFormDefinition formDef = FormDefinitionService.SINGLETON.findFormDefinitionById(registrationFormDefinitionId).getDataFormDefinition();
          sample.setRegistrationForm(formDef);
          FormInstanceQueryCriteria qc = new FormInstanceQueryCriteria();
          qc.addDomainObjectId(sample.getSampleId());
          qc.addFormDefinitionId(registrationFormDefinitionId);
          FormInstance[] formInstances = FormInstanceService.SINGLETON.findFormInstances(qc).getFormInstances();
          if (formInstances.length > 0) {
            if (formInstances.length > 1) {
              throw new IllegalStateException("Multiple registration form instances found for sample " + sample.getSampleId());
            }
            else {
              //the above query doesn't return the details for the form instance, so go get them now
              FormInstance registrationForm = FormInstanceService.SINGLETON.findFormInstanceById(formInstances[0].getFormInstanceId()).getFormInstance();
              sample.setRegistrationFormInstance(registrationForm);
            }
          }
        }
      }
    }

    return samples;
  }

  /**
   * Looks up the sample type configuration for each sample in the specified list and sets it
   * on each sample.
   * 
   * @param  samples  the list of samples
   */
  private static void querySampleConfig(List samples) {
    for (int i = 0; i < samples.size(); i++) {
      SampleData sampleData = (SampleData) samples.get(i);
      
      // Since the getSampleTypeConfiguration() will lazily query for the sample type config 
      // information, we simply need to call the getter.
      sampleData.getSampleTypeConfiguration();
    }
  }

  /**
   * Looks up the statuses for each sample in the specified list based on the selector and sets
   * all of the statuses on each sample.
   * 
   * @param  samples  the list of samples
   * @param  selector  the specification of which status details to populate in the result
   */
  private static void querySampleStatuses(List samples, SampleSelect selector) {
    switch (selector.getStatuses()) {
      case SampleSelect.STATUSES_NONE :
        break;

      case SampleSelect.STATUSES_ALL_REV_CHRON :
        String query =
          "select * from iltds_sample_status where sample_barcode_id = ? order by sample_status_datetime desc";
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
          con = ApiFunctions.getDbConnection();
          pstmt = con.prepareStatement(query.toString());
          for (int i = 0; i < samples.size(); i++) {
            SampleData sampleData = (SampleData) samples.get(i);
            String sampleId = sampleData.getSampleId();

            pstmt.setString(1, sampleId);
            rs = ApiFunctions.queryDb(pstmt, con);
            while (rs.next()) {
              String type = rs.getString("status_type_code");
              Timestamp time = rs.getTimestamp("sample_status_datetime");
              SampleStatusAssistant status = new SampleStatusAssistant(sampleId, type, time.getTime());
              sampleData.addSampleStatus(status);
            }
            rs.close();
          }        
        }
        catch (Exception e) {
          ApiFunctions.throwAsRuntimeException(e);
        }
        finally {
          ApiFunctions.close(con, pstmt, rs);
        }
        break;

    } // end switch
  }

  /**
   * Looks up the inventory groups for each sample in the specified list based on the selector and sets
   * the inventory groups on each sample.
   * 
   * @param  samples  the list of samples
   * @param  selector  the specification of whether or not to populate inventory group information
   *         in the result
   */
  private static void querySampleInventoryGroups(SecurityInfo secInfo, List samples, SampleSelect selector) {
    switch (selector.getInventoryGroups()) {
      case SampleSelect.INVENTORY_GROUPS_NONE :
        break;

      case SampleSelect.INVENTORY_GROUPS_ALL :
      
        Map samplesToIds = new HashMap();
        for (int i=0; i<samples.size(); i++) {
          SampleData sample = (SampleData)samples.get(i);
          samplesToIds.put(sample.getSampleId(), sample);
        }
        LogicalRepositoryDetailQueryBuilder query = new LogicalRepositoryDetailQueryBuilder(secInfo);
        query.addColumnItemId();
        query.addColumnRepositoryFullName();
        query.addColumnRepositoryId();
        query.addColumnRepositoryShortName();
        query.getDetails(samplesToIds);
        break;

    } // end switch
  }
}
