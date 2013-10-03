package com.ardais.bigr.query.generator;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.query.filters.FilterConstants;
import com.ardais.bigr.query.filters.FilterNumericRangeInteger;
import com.ardais.bigr.query.filters.InitializableFromFilter;

/**
 * Filter out RNA with quality outside a numeric range.  
 */
public class FilterRnaQuality extends FilterNumericRangeInteger {
    
    /**
     * This constructor satisfies the FilterNumericRange pattern for the FilterManager to 
     * automatically instantiate this from serialized format.  
     * @param min -- the minimum quality
     * @param max -- not used MUST be null, only for conformance to NumericRange constructor
     * so that introspection can work in de-serializing this from xml
     */
    public FilterRnaQuality(Integer min, Integer max) {
      
      super(FilterConstants.KEY_RNA_QUALITY);
      setMin(min);
      setMax(max);
      
      if (max != null)
        throw new ApiException("Max quality not supported in RNA quality filter");
      if(min == null) 
        throw new ApiException("Min quality must be specified for RNA quality filter");
    }
    
    /**
     * Constructor
     * @param min  Th minimum quality RNA to retreive
     */
    public FilterRnaQuality(int min) {
        this(new Integer(min), null);
    }

    /**
     * @see com.ardais.bigr.query.filters.QueryBuilderFilter#addToQueryBuilder(InitializableFromFilter)
     * This method causes this filter to be added to a query builder.
     */
    // note this uses a direct callback, rather than going into a generic case statement on the query builder
    // because this is not a generic filter who's logic depends on the key
    // this also demands (has a parameter type of) an RnaSummaryQueryBuilder
    public void addToQueryBuilder(InitializableFromFilter queryBuilder) {
        RnaSummaryQueryBuilder rnaSQB = (RnaSummaryQueryBuilder) queryBuilder;
        rnaSQB.addFilterQualityGreater(getMin().toString());
    }
    
}