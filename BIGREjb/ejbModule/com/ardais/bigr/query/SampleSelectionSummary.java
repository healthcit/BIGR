package com.ardais.bigr.query;

import java.util.Set;
import java.util.Map;


/**
 * Represents the partial results of a Sample Selection query.  Partial results are defined as the 
 * IDs of the matching samples and the consent ids corresponding to the IDs of the matching 
 * samples, along with the counts of donors, consents, ASMs and samples, available both as the
 * total number of samples and by sample type.  The IDs are ordered, and can be used to get full 
 * details of each product.
 */
public interface SampleSelectionSummary {

  /**
   * Returns the list of IDs of samples matching the query.
   * 
   * @return  An array of IDs.
   */
  public String[] getIds();

  /**
   * Returns the specified number of IDs of samples matching the query,
   * starting at the specified index.  If more IDs are requested than 
   * are remaining, then just the remaining IDs will be returned.  The 
   * caller can check the length of the returned array to determine how 
   * many IDs were actually returned.
   * 
   * @param  fromIndex  the index from which to start, inclusive
   * @param  numIds  the number of IDs to return
   * @return  The array of IDs.
   * @throws  java.lang.IllegalArgumentException if fromIndex &lt; 0 or numIds &lt; 0
   */
  public String[] getIds(int fromIndex, int numIds);

  /**
   * Returns the sample type for the sample with the specified ID.
   * 
   * @param  sampleId  the ID of the sample
   * @return  The sample's type, as an ARTS code.
   */
  public String getSampleType(String sampleId);

  /**
   * Returns the total count of samples of all sample types matching the query.
   * 
   * @return  The total count of all samples.
   */
  public int getTotalSampleCount();

  /**
   * Returns the count of samples of the specified sample type.
   * 
   * @param  sampleType  the sample type CUI
   * @return  The count of samples.  Always returns an Integer, never null.
   */
  public Integer getSampleCount(String sampleType);

  /**
   * Returns the number of donors that contain products that match
   * the query.
   * 
   * @return  The number of donors.
   */
  public Integer getDonorCount();

  /**
   * Returns the list of IDs of consents for products matching the query.
   * 
   * @return  A string array of IDs.
   */
  public String[] getConsentIds();

  /**
   * Returns the number of consents that contain products that match
   * the query.
   * 
   * @return  The number of consents.
   */
  public Integer getConsentCount();

  /**
   * Returns the number of ASMs that contain products that match
   * the query.
   * 
   * @return  The number of ASMs.
   */
  public Integer getAsmCount();

  /**
   * @return a SampleSelectionSummary representing the union of the ids and counts of this
   * and the parameter.
   * @param addTo the other summary to add to this one.
   */
  public SampleSelectionSummary plus(SampleSelectionSummary addTo);
  
  /**
   * Returns the sample types used in this sample selection query.
   * 
   * @return The set of sample types.
   */
  public Set getSampleTypes();
  
} 
