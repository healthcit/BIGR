package com.ardais.bigr.query.filters;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.ardais.bigr.iltds.btx.BTXDetails;
import com.ardais.bigr.query.generator.NoMatchTissueOrDiagnosisException;
import com.ardais.bigr.query.generator.NoMatchingTissuesAndDiagnosesException;
import com.ardais.bigr.query.generator.ProductSummaryQueryBuilder;
import com.ardais.bigr.query.kc.filters.FilterKc;
import com.ardais.bigr.util.BtxValidator;
import com.ardais.bigr.util.IndentingStringBuffer;

/**
 * Holds query parameters for a product query.  Since a product is
 * either a tissue sample or derived from a tissue sample, most of the
 * query parameters are related to tissue samples.  If a parameter is not 
 * specified, then it does not affect the query that is ultimately created 
 * from the parameters (i.e. in general, the parameter acts as if an "any" 
 * or "all" choice has been specified).
 */
public abstract class ProductFilters extends FilterSet implements Serializable {

  private static final List displayOrder = Arrays.asList(new String[] {
    // major ordering (first sort) is by or group
    OR_GROUP_IDS,
      OR_GROUP_CASE_DX,
      OR_GROUP_LIMS_DX,
      OR_GROUP_SAMPLE_ORIGIN,
      OR_GROUP_TISSUE_ORIGIN,
      OR_GROUP_GROSS_ORIGIN,
      OR_GROUP_SAMPLE_FINDING,
      OR_GROUP_TISSUE_FINDING,
      OR_GROUP_GROSS_FINDING,
      OR_GROUP_DIAGNOSTIC_TEST,
      
    // minor ordering (second sort) is by data element (key)
    FilterConstants.KEY_DONORID,
      FilterConstants.KEY_CASEID,
      FilterConstants.KEY_TISSUEID,
      FilterConstants.KEY_AGEATCOLLECTION,
      FilterConstants.KEY_GENDER,
      FilterConstants.KEY_PATHVERIFIED_STATUS,
      FilterConstants.KEY_SAMPLE_TYPE,
      FilterConstants.KEY_LINKED,
      FilterConstants.KEY_RESTRICTED_FOR_DI,
      FilterConstants.KEY_RESTRICTED_RNA_FOR_DI,
      FilterConstants.KEY_LOGICAL_REPOSITORY,
      FilterConstants.KEY_LOCAL_SAMPLES_ONLY,
      FilterConstants.KEY_BMS_YN,
      FilterConstants.KEY_BMS_YN_YES,
      FilterConstants.KEY_NOT_PULLED_OR_BMS_YN_YES,
      FilterConstants.KEY_DDCCASEDIAGNOSIS,
      FilterConstants.KEY_DDCCASEDIAGNOSISLIKE,
      FilterConstants.KEY_ILTDSCASEDIAGNOSIS,
      FilterConstants.KEY_ILTDSCASEDIAGNOSISLIKE,
      FilterConstants.KEY_BESTCASEDIAGNOSIS,
      FilterConstants.KEY_BESTCASEDIAGNOSISLIKE,
      FilterConstants.KEY_SAMPLEPATHOLOGY,
      FilterConstants.KEY_SAMPLEPATHOLOGYLIKE,
      FilterConstants.KEY_DIAGNOSTIC_TEST,
      FilterConstants.KEY_DIAGNOSTIC_TEST_RESULT,
      FilterConstants.KEY_SAMPLE_APPEARANCE_BEST,
      FilterConstants.KEY_SAMPLE_APPEARANCE_BEST_NOT,
      FilterConstants.KEY_COMP_NORMAL,
      FilterConstants.KEY_COMP_LESION,
      FilterConstants.KEY_COMP_TUMOR,
      FilterConstants.KEY_COMP_ACS,
      FilterConstants.KEY_COMP_CS,
      FilterConstants.KEY_COMP_NECROSIS,
      FilterConstants.KEY_STAGE,
      FilterConstants.KEY_TUMORSTAGE,
      FilterConstants.KEY_LYMPHNODESTAGE,
      FilterConstants.KEY_DISTANTMETASTASIS,
      FilterConstants.KEY_HNG,
      FilterConstants.KEY_PATHNOTESCONTAINS,
      FilterConstants.KEY_PVNOTESCONTAINS,
      FilterConstants.KEY_NOT_OTHER_DDC_DIAGNOSIS,
      FilterConstants.KEY_NOT_OTHER_LIMS_DIAGNOSIS,
      FilterConstants.KEY_TISSUEORIGIN,
      FilterConstants.KEY_TISSUEORIGINNOT,
      FilterConstants.KEY_TISSUEORIGINLIKE,
      FilterConstants.KEY_TISSUEORIGINLIKENOT,
      FilterConstants.KEY_TISSUEFINDING,
      FilterConstants.KEY_TISSUEFINDINGNOT,
      FilterConstants.KEY_TISSUEFINDINGLIKE,
      FilterConstants.KEY_TISSUEFINDINGLIKENOT,
      FilterConstants.KEY_RNA_STATUS,
      FilterConstants.KEY_RNA_NOT_RESTRICTED,
      FilterConstants.KEY_RNA_AMOUNT_AVAILABLE,
      FilterConstants.KEY_RNA_QUALITY,
      FilterConstants.KEY_NOT_IN_PROJECT,
      FilterConstants.KEY_NOT_PULLED,
      FilterConstants.KEY_INVENTORY_STATUS,
      FilterConstants.KEY_GENRELEASED,
      FilterConstants.KEY_NOT_ON_HOLD,
      FilterConstants.KEY_DDC_DIAGNOSIS_EXISTS,
      FilterConstants.KEY_CONSENT_NOT_PULLED,
      FilterConstants.KEY_CONSENT_NOT_REVOKED,
      FilterConstants.KEY_IN_ARDAIS_REPOSITORY,
      FilterConstants.KEY_RECEIVED_AT_ARDAIS,
      FilterConstants.KEY_HOLD_SOLD_STATUS,
      FilterConstants.KEY_SAMPLE_DATE_RECEIVED, 
      FilterConstants.KEY_DRE,
      FilterConstants.KEY_DRE_EXISTS,
      FilterConstants.KEY_PSA,
      FilterConstants.KEY_PSA_EXISTS
      });

  // ==================== 

  public ProductFilters() {
    super();
  }

  public ProductFilters(Map filters) {
    super(filters);
  }

  public abstract ProductFilters copy();

  /**
   * @see com.ardais.bigr.query.Filters#validate(BTXDetails)
   */
  public void validate(BTXDetails btx) {
    //TODO: instead of having all of the validation logic here, we should call a validate()
    // method on each filter so it could validate itself.
    
    // id fields
    FilterStringsEqual fsamp = (FilterStringsEqual) getFilter(FilterConstants.KEY_TISSUEID);
    if (fsamp != null)
      BtxValidator.validateProductIds(fsamp.getFilterValues(), btx);

    FilterStringsEqual fcase = (FilterStringsEqual) getFilter(FilterConstants.KEY_CASEID);
    if (fcase != null)
      BtxValidator.validateCaseIds(fcase.getFilterValues(), btx);

    FilterStringsEqual fdonor = (FilterStringsEqual) getFilter(FilterConstants.KEY_DONORID);
    if (fdonor != null)
      BtxValidator.validateDonorIds(fdonor.getFilterValues(), btx);

    // @todo: add rna ids as separate collection

    // attributes
    validateNumericRangeInteger(FilterConstants.KEY_AGEATCOLLECTION, "Age at collection", 0, 130, btx);
    validateIntermediaSearch(
      FilterConstants.KEY_ASCIIREPORTCONTAINS,
      "Raw Path Report Keyword Search",
      btx);
    validateDateRange(FilterConstants.KEY_SAMPLE_DATE_RECEIVED, "First Arrived at Supplier Biorepository", btx);

    // null is always OK.  If data, validate for each field
    validateStringVal(FilterConstants.KEY_GENDER, "Gender", new String[] { "M", "F" }, btx);
    validateStringVal(
      FilterConstants.KEY_RESTRICTED_FOR_DI,
      "Restricted status",
      new String[] { "R", "U", "MIU", "MIR", "MIUR" },
      btx);
    validateStringVal(FilterConstants.KEY_LINKED, "Linked", new String[] { "Y", "N" }, btx);
    validateStringVal(
      FilterConstants.KEY_HOLD_SOLD_STATUS,
      "Hold/Sold status",
      new String[] { "ALL", "USERCASE", "ACCOUNTCASE", "USERCASENOT", "ACCOUNTCASENOT" },
      btx);
    validateStringVal(FilterConstants.KEY_BMS_YN, "BMS", new String[] { "Y", "N" }, btx);

    // appearance
    validatePercent(FilterConstants.KEY_COMP_NORMAL, "% Normal", btx);
    validatePercent(FilterConstants.KEY_COMP_LESION, "% Lesion", btx);
    validatePercent(FilterConstants.KEY_COMP_TUMOR, "% Tumor", btx);
    validatePercent(FilterConstants.KEY_COMP_CS, "% Cellular Stroma", btx);
    validatePercent(FilterConstants.KEY_COMP_ACS, "% Hypo/Acellular Stroma", btx);
    validatePercent(FilterConstants.KEY_COMP_NECROSIS, "% Necrosis", btx);

    // diagnosis
    validateIntermediaSearch(
      FilterConstants.KEY_PATHNOTESCONTAINS,
      "Pathology Notes Keyword Search",
      btx);
    validateIntermediaSearch(
      FilterConstants.KEY_PVNOTESCONTAINS,
      "Pathology Verification Notes Keyword Search",
      btx);
    // check all three diagnosis types, even though only one will exist
    validateDbLikeSearch(FilterConstants.KEY_BESTCASEDIAGNOSISLIKE, "Case Diagnosis contains", btx);
    validateDbLikeSearch(
      FilterConstants.KEY_ILTDSCASEDIAGNOSISLIKE,
      "Case Diagnosis contains",
      btx);
    validateDbLikeSearch(FilterConstants.KEY_DDCCASEDIAGNOSISLIKE, "Case Diagnosis contains", btx);
    validateDbLikeSearch(FilterConstants.KEY_SAMPLEPATHOLOGYLIKE, "Sample Pathology contains", btx);

    // tissue
    validateDbLikeSearch(FilterConstants.KEY_TISSUEFINDINGLIKE, "Site of Finding", btx);
    validateDbLikeSearch(FilterConstants.KEY_TISSUEFINDINGLIKENOT, "Site of Finding", btx);
    validateDbLikeSearch(
      FilterConstants.KEY_TISSUEORIGINLIKE,
      "Tissue of Origin of Diagnosis",
      btx);
    validateDbLikeSearch(
      FilterConstants.KEY_TISSUEORIGINLIKENOT,
      "Tissue of Origin of Diagnosis",
      btx);
    
    // Validate the KC filters.
    Map filters = getFilters();
    Iterator i = filters.keySet().iterator();
    while (i.hasNext()) {
      String key = (String) i.next();
      if (FilterKc.isKcFilterKey(key)) {
        FilterKc filter = (FilterKc) filters.get(key);
        filter.validate(btx);
      }
    }    
  }

  private void validateStringVal(
    String key,
    String fieldName,
    String[] allowable,
    BTXDetails btx) {
    FilterStringEquals f = (FilterStringEquals) getFilter(key);
    if (f == null)
      return;
    BtxValidator.validateFixedList(fieldName, f.getFilterValue(), allowable, btx);
  }

  private void validatePercent(String key, String fieldName, BTXDetails btx) {
    FilterNumericRangeInteger f = (FilterNumericRangeInteger) getFilter(key);
    if (f == null)
      return;
    BtxValidator.validatePercentRange(fieldName, f.getMin(), f.getMax(), btx);
  }

  private void validateNumericRangeInteger(
    String key,
    String fieldName,
    int minAllowed,
    int maxAllowed,
    BTXDetails btx) {
    FilterNumericRangeInteger f = (FilterNumericRangeInteger) getFilter(key);
    if (f == null)
      return;
    BtxValidator.validateNumericRange(
      fieldName,
      f.getMin(),
      f.getMax(),
      minAllowed,
      maxAllowed,
      btx);
  }

  private void validateDateRange(String key, String fieldName, BTXDetails btx) {
    FilterDateRange f = (FilterDateRange) getFilter(key);
    if (f == null)
      return;
    BtxValidator.validateDateRange(fieldName, f.getStartForDisplay(), f.getEndForDisplay(), btx);
  }

  private void validateDbLikeSearch(String key, String fieldName, BTXDetails btx) {
    FilterStringLike f = (FilterStringLike) getFilter(key);
    if (f == null)
      return;
    BtxValidator.validateDbLikeSearch(fieldName, f.getPatternForDisplay(), btx);
  }

  private void validateIntermediaSearch(String key, String fieldName, BTXDetails btx) {
    FilterStringContains f = (FilterStringContains) getFilter(key);
    if (f == null)
      return;
    BtxValidator.validateIntermediaSearch(fieldName, f.getPhrase(), btx);
  }

  //-------------------   formatting readable summary of query below ----------

  /**
   * Formats the overall set of filters as an AND/OR tree, with indenting
   */
  // @todo:  FilterStrinsEqual has indents also, so it would be nice to pass the indent
  // level into that class as well.  A StringBuffer that knew the current indent level
  // might be nice.
  public String toString() {
    IndentingStringBuffer buf = new IndentingStringBuffer();

    String[] keys = (String[]) getFilters().keySet().toArray(new String[0]);
    Arrays.sort(keys, new DisplayOrder());
    boolean firstConjunct = true;
    for (int i = 0; i < keys.length; i++) {
      String key = keys[i];
      if (!firstConjunct)
        buf.append("AND ");
      if (isInOrGrouop(key)) { // write a complex OR group conjunct
        buf.increaseIndent(4); // indent or group
        addOrGroupToStringBuffer(key, buf);
        buf.decreaseIndent(4); // done with or group
      }
      else { // write a simple, single-filter conjunct
        Filter f = (Filter) getFilters().get(key);
        buf.append(f.toString());
      }
      if (i < keys.length - 1)
        buf.append('\n'); // separate with newline
      firstConjunct = false;
    }

    return buf.toString();
  }

  public String toXml(String name) {
    StringBuffer buf = new StringBuffer();
    buf.append("<query name=\"" + name + "\">");
    addXmlToBuffer(getFilters(), buf);
    buf.append("</query>");
    return buf.toString();
  }

  /* non-javadoc
   * write the filters to an XML buffer.  For each entry in the maps, write the filter XML,
   * (by delegating to the filter) or write the or-group XML, by wrapping the filters in
   * the or-group with OR tags.
   */
  private void addXmlToBuffer(Map filts, StringBuffer buf) {
    String[] keys = (String[]) filts.keySet().toArray(new String[filts.size()]);
    Arrays.sort(keys, new DisplayOrder()); // optional, gives good consistency
    for (int i = 0; i < keys.length; i++) {
      String key = keys[i];
      if (isInOrGrouop(key)) {
        buf.append("<OR>");
        Map m = (Map) filts.get(key); // or groups' values in the map are other maps
        addXmlToBuffer(m, buf); // recursively put these filters inside the OR tags
        buf.append("</OR>");
      }
      else {
        Filter f = (Filter) filts.get(key); // other than or groups, Map has Filter objs.
        f.toXml(buf);
      }
    }
  }

  // writes a description of the or group.  Analagous to a filter writing its description.
  // note that neither Filter.toString() nor this method ends with a newline.
  // the caller will handle separators (newline, comma, etc.) if appropriate
  private void addOrGroupToStringBuffer(String orGroupCode, IndentingStringBuffer sb) {
    Map m = (Map) getOrGroupMap(orGroupCode);
    if (m == null)
      return;
    boolean first = true;
    Iterator it = m.keySet().iterator();
    while (it.hasNext()) {
      String key = (String) it.next();
      Filter f = (Filter) m.get(key);
      if (!first)
        sb.append("OR ");
      sb.append(f.toString());
      if (it.hasNext())
        sb.append('\n'); // separate with newline.
      first = false;
    }
  }

  private static class DisplayOrder implements Comparator {
    public int compare(Object o1, Object o2) {
      int ordinal1 = displayOrder.indexOf(o1);
      int ordinal2 = displayOrder.indexOf(o2);
      return ordinal1 < ordinal2 ? -1 : ordinal1 == ordinal2 ? 0 : 1;
    }
    public boolean equals(Object o1, Object o2) {
      return o1.equals(o2);
    }
  }

  /**
   * Method update.
   * @param productSummaryQueryBuilder
   */
  public void update(ProductSummaryQueryBuilder psqb) {
    updateQueryBuilderFromMap(psqb, getFilters());
  }

  // recursively apply the filter and the or groups of filters
  private void updateQueryBuilderFromMap(ProductSummaryQueryBuilder psqb, Map m) {
    List noMatchErrors = new ArrayList();
    Iterator it = m.values().iterator();
    while (it.hasNext()) {
      Object filterOrGroup = it.next();
      if (filterOrGroup instanceof Filter) { // simple filter?
        Filter f = (Filter) filterOrGroup;
        try {
          f.addToQueryBuilder(psqb);
        }
        catch (NoMatchTissueOrDiagnosisException e) {
          e.setFilterDescription(f.displayName());
          if (f instanceof FilterStringLike) { // rewrite pattern to be human readable?
            e.setPattern(((FilterStringLike) f).getPatternForDisplay());
          }
          noMatchErrors.add(e);
        }
      }
      else { // it is a map representing a whole or group (recurse)
        try {
          updateQueryBuilderFromMap(psqb, ((Map) filterOrGroup));
        }
        catch (NoMatchingTissuesAndDiagnosesException e) { // recusive call can get many messages
          noMatchErrors.addAll(e.getExceptions());
        }
      }
    }
    if (!noMatchErrors.isEmpty()) {
      // throw a batch-oriented message with the strings of each individual no match exception
      throw new NoMatchingTissuesAndDiagnosesException(noMatchErrors);
    }
  }

  //    protected class FilterIterator implements Iterator {
  //        private FilterIterator parent; // have a parent iterator to do nested/recursive iteration
  //        private Iterator valueIterator;
  //        public FilterIterator(Map m, FilterIterator parent) {
  //            this.parent = parent;
  //            valueIterator = m.values().iterator();
  //        }
  //        private FilterIterator(Iterator it, FilterIterator parent) {
  //            this.parent = parent;
  //            valueIterator = it;
  //        }
  //        public boolean hasNext() {
  //            if (valueIterator.hasNext())
  //                return true;
  //            if (parent != null && parent.hasNext())
  //                return true;
  //            return false;
  //        }
  //        public Object next() {
  //            if (valueIterator.hasNext()) { // this iterator done.  pop up a level
  //                return valueIterator.next();
  //            }
  //            else {
  //                pop();
  //                Object o = next(); // recurse now that we are "up" a level
  //                if (o instanceof Map) {
  //                    push((Map)o); // go "into" this map
  //                    return next(); // recurse into the new level
  //                } else {
  //                    return o;
  //                }
  //            }
  //        }
  //        private void pop() {
  //            if (parent.valueIterator==null) // nothing to pop up to
  //                throw new IndexOutOfBoundsException("No more filters in iterator");
  //            valueIterator = parent.valueIterator;  // get the old value iterator back from the parent
  //            parent = parent.parent; // get the old parent back             
  //        }
  //        private void push(Map m) {
  //            parent = new FilterIterator(valueIterator, parent); // store away my values in a parent
  //            valueIterator = m.values().iterator(); // start the new iterator
  //        }
  //        public void remove() {
  //            throw new UnsupportedOperationException("no remove from FilterIterator");
  //        }
  //    }

  /** need EJB server to run (JNDI/DxHeirarchy bean)
      public static void main(String[] args) {
          ProductFilters pf = new SampleFilters();
          pf.addFilter(new FilterSampleId(new String[] {"id001", "id002", "id003"}));
          pf.addFilter(new FilterCaseDiagnosisBest(new String[]{"dxcode83443", "dxcode34343"}));
          pf.addFilter(new FilterCaseDiagnosisLikeBest("*dxcontainsstring*"));
          System.out.println(pf.toString());
          
          ProductFilters pf2 = new SampleFilters();
          pf2.addFilter(new FilterCaseDiagnosisBest(new String[]{"dxcode83443justOne"}));
          pf2.addFilter(new FilterCaseDiagnosisLikeBest("*dxcontainsstring*"));
          System.out.println(pf2.toString());
        }
      ***/

}
