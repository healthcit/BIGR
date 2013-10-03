package com.ardais.bigr.query;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.util.ArtsConstants;
import com.ardais.bigr.util.DbUtils;
import com.ardais.bigr.util.gen.DbAliases;

/**
 * Holds the partial results of a product query.
 */
public class ProductSummary implements SampleSelectionSummary, Serializable {

  /**
   * Product IDs.
   */
  private List _ids = new ArrayList();
  
  private List _consentIds;

  /**
   * Number of donors.
   */
  private Integer _donors;

  /**
   * Number of consents.
   */
  private Integer _consents;

  /**
   * Number of ASMs.
   */
  private Integer _asms;

  /**
   * Count of samples of each sample type.  The keys are sample types, and the values are Integers
   * that contain the count of samples of that type.
   */
  private Map _samples = new HashMap();

  /**
   * The sample type of each sample.  The keys are sample ids, and the values are the sample
   * types, as ARTS codes.
   */
  private Map _sampleTypes = new HashMap();

  /**
   * create an empty, uninitialized summary
   */
  private ProductSummary() {
  }

  /**
   * Creates a new <code>ProductSummary</code> from the specified
   * <code>ResultSet</code>.  It is assumed that the columns present in the 
   * result set are defined in {@link com.ardais.bigr.util.DbAliases},  and
   * may include one or more of the following: DONOR_ID, CONSENT_ID, ASM_ID,
   * SAMPLE_TYPE, SAMPLE_ID, RNA_RNAVIALID.  In addition, it is assumed 
   * that ids are not duplicated in the result set.
   * 
   * @param  rs  the <code>ResultSet</code>
   */
  public ProductSummary(ResultSet rs) {
  	Set donors = new HashSet();
  	Set consents = new HashSet();
  	Set asms = new HashSet();
    Map samples = new HashMap();
    Map columns = DbUtils.getColumnNames(rs);

		try {
			while (rs.next()) {
				if (columns.containsKey(DbAliases.DONOR_ID)) {
					donors.add(rs.getString(DbAliases.DONOR_ID));
				}
	
				if (columns.containsKey(DbAliases.CONSENT_ID)) {
					consents.add(rs.getString(DbAliases.CONSENT_ID));
				}

				if (columns.containsKey(DbAliases.ASM_ID)) {
					asms.add(rs.getString(DbAliases.ASM_ID));
				}
     
        // Get the sample's ID and sample type.  This will either be from the sample table
        // or RNA table. 
        String id = null;
        String sampleType = null;

				if (columns.containsKey(DbAliases.SAMPLE_ID)) {
					id = rs.getString(DbAliases.SAMPLE_ID);

          if (columns.containsKey(DbAliases.SAMPLE_SAMPLE_TYPE_CID)) {
            sampleType = rs.getString(DbAliases.SAMPLE_SAMPLE_TYPE_CID);
          }
          if (sampleType == null) {
            sampleType = ArtsConstants.SAMPLE_TYPE_UNKNOWN;
          }
                   
				}

        if (columns.containsKey(DbAliases.RNA_RNAVIALID)) {
          id = rs.getString(DbAliases.RNA_RNAVIALID);
          sampleType = ArtsConstants.SAMPLE_TYPE_RNA;
        }
        
        // Save the ID and sample type, and add the sample to the appropriate set.
        if ((id != null) && (sampleType != null)) {
          _ids.add(id);
          _sampleTypes.put(id, sampleType);

          Set sampleSet = (Set) samples.get(sampleType);
          if (sampleSet == null) {
            sampleSet = new HashSet();
            samples.put(sampleType, sampleSet);
          }
          sampleSet.add(id);
          _sampleTypes.put(id, sampleType);
        }
			}
		}
		catch (SQLException e) {
			ApiFunctions.throwAsRuntimeException(e);
		}
		
		if (!donors.isEmpty()) {
			_donors = new Integer(donors.size());
		}
		if (!consents.isEmpty()) {
			_consents = new Integer(consents.size());
      //sort the consent ids by converting to a TreeSet first.
      _consentIds = new ArrayList(new TreeSet(consents));
		}
		if (!asms.isEmpty()) {
			_asms = new Integer(asms.size());
		}

    Iterator sampleTypes = samples.keySet().iterator();
    while (sampleTypes.hasNext()) {
      String sampleType = (String) sampleTypes.next();
      Set sampleSet = (Set) samples.get(sampleType);
      _samples.put(sampleType, new Integer(sampleSet.size()));
    }
  }

  public String[] getIds() {
    return (String[]) _ids.toArray(new String[0]);
  }

  public String[] getConsentIds() {
    if (_consentIds == null) {
      _consentIds = new ArrayList();
    }
    return (String[])(_consentIds.toArray(new String[0]));
  }

  public String[] getIds(int fromIndex, int numIds) {
    if ((fromIndex < 0) || (numIds < 0)) {
      throw new IllegalArgumentException("Negative argument specified");
    } else if (numIds == 0) {
      return new String[0];
    } else {
      int toIndex = fromIndex + numIds;
      int maxIndex = getTotalSampleCount();
      toIndex = (toIndex > maxIndex) ? maxIndex : toIndex;
      return (String[]) (_ids.subList(fromIndex, toIndex)).toArray(
        new String[0]);
    }
  }

  public int getTotalSampleCount() {
    return _ids.size();
  }

  public Integer getDonorCount() {
    return _donors;
  }

  public Integer getConsentCount() {
    return _consents;
  }

  public Integer getAsmCount() {
    return _asms;
  }
  
  public Set getSampleTypes() {
    return new HashSet(_samples.keySet());
  }

  public Integer getSampleCount(String sampleType) {
    Integer count = (Integer) _samples.get(sampleType);
    return (count == null) ? new Integer(0) : count;
  }
  
  public String getSampleType(String sampleId) {
    return (String) _sampleTypes.get(sampleId);
  }
  
  /**
   * @see com.ardais.bigr.query.SampleSelectionSummary#plus(SampleSelectionSummary)
   */
  public SampleSelectionSummary plus(SampleSelectionSummary addToSSS) {

    ProductSummary addTo = (ProductSummary) addToSSS;
    
    ProductSummary result = new ProductSummary();
    
    // combine the lists of IDs
    result._ids = addLists(_ids, addTo._ids);
    
    //combine the lists of consent ids
    result._consentIds = addLists(_consentIds, addTo._consentIds);

    // combine the sample type maps
    result._sampleTypes = (_sampleTypes == null) ? new HashMap() : new HashMap(_sampleTypes);
    if (addTo._sampleTypes != null) {
      result._sampleTypes.putAll(addTo._sampleTypes);
    }
    
    // add the ASM, consent and donor counts
    result._asms = addInts(_asms, addTo._asms);
    result._consents = addInts(_consents, addTo._consents);
    result._donors = addInts(_donors, addTo._donors);

    // add the sample counts by sample type
    Set allSampleTypes = (_samples == null) ? new HashSet() : new HashSet(_samples.keySet());
    if (addTo._samples != null) {
      allSampleTypes.addAll(addTo._samples.keySet());
    }
    Iterator sampleTypes = allSampleTypes.iterator();
    while (sampleTypes.hasNext()) {
      String sampleType = (String) sampleTypes.next();
      Integer sampleCount = (Integer) _samples.get(sampleType);
      Integer addToSampleCount = (Integer) addTo._samples.get(sampleType);
      result._samples.put(sampleType, addInts(sampleCount, addToSampleCount));
    }

    return result;
  }

  // add two integers, with null representing 0.  two nulls add to a null.
  private Integer addInts(Integer i, Integer j) {
    if (i==null && j==null)
      return null;
    int ii = i==null ? 0 : i.intValue();
    int jj = j==null ? 0 : j.intValue();
    return new Integer(ii+jj);
  }
  
  // combine two lists, without duplicates.  May change order.
  private List addLists(List l, List k) {
    Set res = new HashSet(l);
    res.addAll(k);
    return  new ArrayList(res);    
  }
}
