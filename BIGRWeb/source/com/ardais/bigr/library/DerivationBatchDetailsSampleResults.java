package com.ardais.bigr.library;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.btx.framework.Btx;
import com.ardais.bigr.iltds.helpers.IltdsUtils;
import com.ardais.bigr.javabeans.DerivationBatchDto;
import com.ardais.bigr.javabeans.DerivationDto;
import com.ardais.bigr.javabeans.ProductDto;
import com.ardais.bigr.javabeans.SampleData;
import com.ardais.bigr.library.btx.BTXDetailsGetSamples;
import com.ardais.bigr.query.SampleResults;
import com.ardais.bigr.query.SampleSelectionSummary;
import com.ardais.bigr.security.SecurityInfo;

public class DerivationBatchDetailsSampleResults extends SampleResults {

  private DerivationBatchDto _derivationBatchDto;
  private String[] _childSampleIds;
  private Map _childSampleToDerivation;

  /**
   * Constructor
   */
  public DerivationBatchDetailsSampleResults(SecurityInfo secInfo, DerivationBatchDto derivationBatchDto) {
    super(secInfo);
    _derivationBatchDto = derivationBatchDto;
 }

  /**
   * @see com.ardais.bigr.library.SampleSet#getSampleData()
   */
  public BTXDetailsGetSamples getSampleData(BTXDetailsGetSamples btx) {
    try {
      createChildSampleStructures(btx);
      btx.setSampleIds(_childSampleIds);
      btx.setTransactionType("library_get_details");
      btx = (BTXDetailsGetSamples) Btx.perform(btx);
      //categorize the child samples
      categorizeSamples(btx);
      // Get the sample types from the sample ids.
      String[] sampleIds = btx.getSampleIds();
      Set sampleTypes = IltdsUtils.getSampleTypesBySampleIds(ApiFunctions.safeToList(sampleIds));
      btx.setSampleTypes(sampleTypes);
      return btx;
    }
    catch (Exception e) {
      throw new ApiException(e);
    }
  }
  
  //private method to walk the child samples of all derivations and create the necessary
  //data structures for processing them.
  private void createChildSampleStructures(BTXDetailsGetSamples btx) {
    _childSampleToDerivation = new HashMap();
    List sampleIds = new ArrayList();
    Iterator derivationIterator = _derivationBatchDto.getDerivations().iterator();
    while (derivationIterator.hasNext()) {
      DerivationDto derivation = (DerivationDto)derivationIterator.next();
      Iterator childIterator = derivation.getChildren().iterator();
      while (childIterator.hasNext()) {
        SampleData child = (SampleData)childIterator.next();
        sampleIds.add(child.getSampleId());
        //map the sample to
        _childSampleToDerivation.put(child.getSampleId(), derivation.getDerivationId());
      }
    }
    _childSampleIds = ApiFunctions.toStringArray(sampleIds);
  }
  
  private void categorizeSamples(BTXDetailsGetSamples btxDetails) {
    Set categoriesToDetermine = btxDetails.getCategoriesToDetermine();
    if (categoriesToDetermine == null || categoriesToDetermine.isEmpty()) {
      btxDetails.setDetailsResultCategories(Collections.EMPTY_MAP);
      return;
    }
    //any specified category will be a derivation id, so for every such id find
    //any samples that belong to it
    Map categoryMap = new HashMap();
    Iterator categoryIterator = categoriesToDetermine.iterator();
    while (categoryIterator.hasNext()) {
      categoryMap.put(categoryIterator.next(), new ArrayList());
    }
    Iterator sampleIterator = btxDetails.getSampleDetailsResult().iterator();
    while (sampleIterator.hasNext()) {
      ProductDto sample = (ProductDto) sampleIterator.next();
      String derivationId = (String) _childSampleToDerivation.get(sample.getId());
      if (!ApiFunctions.isEmpty(derivationId)) {
        List derivationSamples = (List) categoryMap.get(derivationId);
        if (derivationSamples != null) {
          derivationSamples.add(sample);
        }
      }
    }
    btxDetails.setDetailsResultCategories(categoryMap);
  }

  /**
   * @see com.ardais.bigr.library.SampleSet#getSummary()
   */
  public SampleSelectionSummary getSummary() {
    return null;
  }

  /**
   * @see com.ardais.bigr.library.SampleSet#getSampleIds()
   */
  public String[] getSampleIds(int chunk) {
    throw new UnsupportedOperationException("DerivationBatchDetailsSampleResults does not support getSampleIds()");
  }

  /**
   * @see com.ardais.bigr.query.SampleResults#clearSelectedIds()
   */
  public void clearSelectedIds() {
    // do nothing - I do not ever have any Ids selected
  }

  public void addSelectedIds(String[] ignore) {
    // do nothing - I do not ever have any Ids selected
  }

  public void clearSelectedIdsForCurrentChunk() {
    // do nothing - I do not ever have any Ids selected
  }

  
  /**
   * @see com.ardais.bigr.query.SampleResults#getSelectedIds()
   */
  public String[] getSelectedIds() {
    return new String[0];
  }

  /**
   * @see com.ardais.bigr.query.SampleResults#getSampleAmounts()
   */
  public Map getSampleAmounts() {
    return Collections.EMPTY_MAP;
  }

  /**
   * @see com.ardais.bigr.query.SampleResults#getDisplay()
   */
  public String getDisplay() {
    return "Derivation Batch Details";
  }

  /**
   * @see com.ardais.bigr.query.SampleResults#getProductType()
   */
  public String getProductType() {
    return "";
  }


}
