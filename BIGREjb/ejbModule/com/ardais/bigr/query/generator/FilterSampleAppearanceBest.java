package com.ardais.bigr.query.generator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.ardais.bigr.query.filters.FilterConstants;
import com.ardais.bigr.query.filters.FilterStringsEqual;
import com.ardais.bigr.query.filters.InitializableFromFilter;
import com.ardais.bigr.util.Constants;

/**
 * Filter to include only samples with particular appearances.  
 */
public class FilterSampleAppearanceBest extends FilterStringsEqual {

    static Map displayCodes = new HashMap();
    static {
        displayCodes.put(Constants.APPEARANCE_GROSS_NORMAL, "(Gross) Normal");
        displayCodes.put(Constants.APPEARANCE_GROSS_DISEASED, "(Gross) Diseased");
        displayCodes.put(Constants.APPEARANCE_GROSS_UNKNOWN, "(Gross) Unknown");
        displayCodes.put(Constants.APPEARANCE_MICRO_LESION, "(Microscopic) Lesion");
        displayCodes.put(Constants.APPEARANCE_MICRO_NORMAL, "(Microscopic) Normal");
        displayCodes.put(Constants.APPEARANCE_MICRO_TUMOR, "(Microscopic) Tumor");
        displayCodes.put(Constants.APPEARANCE_MICRO_OTHER, "(Microscopic) Other");
    }
    
    /**
     * Constructor for FilterSampleAppearanceBest.  Note that diseased implies searching for mixed also.
     * 
     * @param appearanceCodes -- an array whos elements may include:  
     *          Constants.APPEARANCE_GROSS_DISEASED, 
     *          Constants.APPEARANCE_GROSS_NORMAL, and
     *          Constants.APPEARANCE_GROSS_UNKNOWN.  
     */
    public FilterSampleAppearanceBest(String[] appearanceCodes) {
        super(FilterConstants.KEY_SAMPLE_APPEARANCE_BEST, appearanceCodes);
    }

    public String[] getFilterValues() {
        return getAppearanceWithMixed(super.getFilterValues());
    }
    
    protected String[] getDisplayedFilterValues() {
        String[] vals = super.getFilterValues();
        List dispList = new ArrayList(vals.length);
        for (int i=0; i<vals.length; i++) {
            if (!Constants.APPEARANCE_GROSS_MIXED.equals(vals[i])) {
                dispList.add(vals[i]);
            }
        }
        return (String[]) dispList.toArray(new String[0]);
    }
    
    /*
     * If the user checked "diseased" we actually mean "diseased" and "mixed" in terms of the
     * back-end appearances.
     */
    private static String[] getAppearanceWithMixed(String[] appearanceCodes) {
        Set appearances = new HashSet(Arrays.asList(appearanceCodes));
        if (appearances.contains(Constants.APPEARANCE_GROSS_DISEASED))
            appearances.add(Constants.APPEARANCE_GROSS_MIXED);
        return (String[]) appearances.toArray(new String[0]);
    }
    

    
    /**
     * @see com.ardais.bigr.query.filters.Filter#codeDisplayMap()
     */
    protected Map codeDisplayMap() {
        return displayCodes;
    }

    /**
     * @see com.ardais.bigr.query.filters.Filter#displayName()
     */
    protected String displayName() {
        return "Sample Appearance";
    }

  public void addToQueryBuilder(InitializableFromFilter queryBuilder) {
    ProductSummaryQueryBuilder qb = (ProductSummaryQueryBuilder) queryBuilder;
    qb.addFilterSampleAppearanceBest(getFilterValues());
  }
}
