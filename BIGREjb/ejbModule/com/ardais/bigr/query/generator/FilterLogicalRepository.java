/*
 * Created on Dec 9, 2003
 */
package com.ardais.bigr.query.generator;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.iltds.assistants.LogicalRepository;
import com.ardais.bigr.query.filters.FilterConstants;
import com.ardais.bigr.query.filters.FilterRepeating;
import com.ardais.bigr.query.filters.InitializableFromFilter;
import com.ardais.bigr.query.filters.RepeatingFilterData;
import com.ardais.bigr.query.filters.RepeatingSingleData;
import com.ardais.bigr.util.LogicalRepositoryUtils;

/**
 * Filter to limit results to certain logical repositories.
 */
public class FilterLogicalRepository extends FilterRepeating {
  
  private String[] _ids;
  private String[] _displayValues;
       

  /**
   * Constructor for FilterLogicalRepository.
   */
  public FilterLogicalRepository(RepeatingFilterData repeatingValues) {
    
    super(FilterConstants.KEY_LOGICAL_REPOSITORY, repeatingValues);
    List lrs = LogicalRepositoryUtils.getLogicalRepositoriesById(Arrays.asList(((RepeatingSingleData) repeatingValues.getValues().get(0)).getRepeatValues()));
    populateIdsAndDisplayValues(lrs);
  }

  /**
   * @see com.ardais.bigr.query.filters.QueryBuilderFilter#addToQueryBuilder(InitializableFromFilter)
   * This method causes this filter to be added to a query builder.
   */
  public void addToQueryBuilder(InitializableFromFilter queryBuilder) {
    ProductSummaryQueryBuilder qb = (ProductSummaryQueryBuilder) queryBuilder;
    qb.addFilterLogicalRepository(getRepeatingValues());
  }

  /**
   * @see com.ardais.bigr.query.filters.Filter#displayName()
   */
  protected String displayName() {
    return "Inventory Group";
  }

  /**
   * The filter values that are visible.  Subclasses can have implicit, invisible values.
   */
  protected String[] getDisplayedFilterValues() {
      return _displayValues;
  }
  
  /* 
   * Private method to get the ids from a list of LogicalRepository objects.  Has to be static
   * so it can be called from the constructor that takes in a list of LogicalRepository objects
   */
  private static String[] getIdsFromLogicalRepositories(List logicalRepositories) {
    //if we get a null list, throw an exception
    if (logicalRepositories == null) {
      throw new ApiException("null List passed to FilterLogicalRepository.getIdsFromLogicalRepositories");
    }
    //create a String array of ids from the logical repositories
    String[] ids = new String[logicalRepositories.size()];
    Iterator iterator = logicalRepositories.iterator();
    int count = 0;    
    while (iterator.hasNext()) {
      LogicalRepository lr = (LogicalRepository)iterator.next();
      ids[count] = lr.getId();
      count = count + 1;
    }
    return ids;
  }
  
  private void populateIdsAndDisplayValues(List logicalRepositories) {
    //if we get a null list, throw an exception
    if (logicalRepositories == null) {
      throw new ApiException("null List passed to FilterLogicalRepository constructor");
    }
    //create two String arrays from the logical repositories - one for the ids,
    //and one for the repository names
    _ids = new String[logicalRepositories.size()];
    _displayValues = new String[logicalRepositories.size()];
    Iterator iterator = logicalRepositories.iterator();
    int count = 0;    
    while (iterator.hasNext()) {
      LogicalRepository lr = (LogicalRepository)iterator.next();
      _ids[count] = lr.getId();
      _displayValues[count] = lr.getShortName();
      count = count + 1;
    }
    ((RepeatingSingleData) getRepeatingValues().getValues().get(0)).setSelectedValues(_displayValues);
  }

  public String toString() {
    StringBuffer buf = new StringBuffer();
    
    Iterator iter = getRepeatingValues().getValues().iterator();
    boolean first = true;
    while(iter.hasNext()) {
      RepeatingSingleData singleData = (RepeatingSingleData) iter.next();
      if (!singleData.isEmpty()) {
        if (!first) {
          buf.append("\n"); 
          buf.append("OR ");
        }
        buf.append(singleData.getFilterName());
        buf.append(" is ");
        String[] selectedValues = singleData.getSelectedValues();
        if (selectedValues.length > 1)
            buf.append("one of:\n      "); // break and indent
        for (int i=0; i < selectedValues.length; i++) {
          if (i>0)
          buf.append("; ");
          buf.append(selectedValues[i]);
        }
        first = false;
      }
    }
    return buf.toString();
  }

}
