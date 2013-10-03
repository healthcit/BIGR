package com.ardais.bigr.query.generator;

import com.ardais.bigr.api.ApiLogger;
import com.ardais.bigr.query.filters.ProductFilters;
import com.ardais.bigr.query.filters.RepeatingFilterData;
import com.ardais.bigr.query.generator.gen.ColumnMetaData;
import com.ardais.bigr.query.generator.gen.FilterMetaData;
import com.ardais.bigr.query.generator.gen.TableMetaData;
import com.ardais.bigr.query.sorting.SortByColumn;
import com.ardais.bigr.util.Constants;
import com.ardais.bigr.util.IdValidator;
import com.ardais.bigr.util.gen.DbAliases;
import com.ardais.bigr.util.gen.DbConstants;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.Set;

/**
 */
public class TissueSummaryQueryBuilder extends ProductSummaryQueryBuilder {

  /**
   * Logger for logging performance-related items.
   */
  private static Log _perfLog = LogFactory.getLog(ApiLogger.BIGR_LOGNAME_LIBRARY_PERFORMANCE);

  private static final String QC_STATUS_OR_GROUP = "orQcStatus";

  private ProductFilters _filters = null;

  private Set<SortByColumn> _sortColumns;

  /**
   * Constructor for TissueSummaryQueryBuilder.
   * @param securityInfo
   */
  public TissueSummaryQueryBuilder() {
    super();
  }

  /**
   * Constructor for TissueSummaryQueryBuilder.
   * @param filters
   * @param securityInfo
   */
  public TissueSummaryQueryBuilder(ProductFilters filters) {
    super(filters);
    _filters = filters;

  }

  public void addFilterPvNotesContains(String keyword) {
    getQueryBuilder().addFilter(FilterMetaData.KEY_LIMS_PE_EXTERNAL_COMMENTS_CONTAINS, keyword);
    getQueryBuilder().addTable(TableMetaData.KEY_TABLE_LIMS_PE);
    addJoinLimsPeSample();
  }

  public void addFilterIDsEqual(String[] rnaIds, String orGroupCode) {
    addFilterSampleEqual(rnaIds, orGroupCode);
  }

  /**
   * Adds the sample id filter for the specified sample ids.
   * @param  ids  the sample ids for the desired samples
   * @param orGroupCode  which or group to put the individual or clauses in
   */
  public void addFilterSampleEqual(String[] ids, String orGroupCode) {
    String[] sampleIds = IdValidator.validSampleIds(ids);
    if (sampleIds.length > 0) {
      getQueryBuilder().addFilterOr(orGroupCode, FilterMetaData.KEY_SAMPLE_ID, sampleIds);
      getQueryBuilder().addTable(TableMetaData.KEY_TABLE_SAMPLE);
    }
  }

  public void addFilterSampleType(String[] sampleTypes) {
    getQueryBuilder().addFilter(FilterMetaData.KEY_SAMPLE_SAMPLE_TYPE_CID, sampleTypes);
    getQueryBuilder().addTable(TableMetaData.KEY_TABLE_SAMPLE);
  }

  /**
   * Adds the bms filter for the specified bms value (Y or N).  
   * @param  bms  the bms value code to query for
   */
  public void addFilterBmsYN(String bms) {
    getQueryBuilder().addFilter(FilterMetaData.KEY_SAMPLE_BMS_YN, bms);
    getQueryBuilder().addTable(TableMetaData.KEY_TABLE_SAMPLE);
  }

  public void addFilterNotPulledOrBmsYNYes() {
    getQueryBuilder().addFilter(
      FilterMetaData.KEY_SAMPLE_NOT_PULLED_OR_BMS_YN_YES);
    getQueryBuilder().addTable(TableMetaData.KEY_TABLE_SAMPLE);
  }

  /**
   * Allow samples that are in the QC Statuses listed in the 
   * string array parameter of Constants.  
   * @param stats -- contains any/all of:
   *    not verified - Constants.QC_NOTVER
   *    verified - Constants.QC_VER_ONLY
   *    released - Constants.QC_REL_ONLY
   *    posted   - Constants.QC_POST
   *    pulled - Constants.QC_PULL
   */
  public void addFilterSampleQcStatuses(String[] stats) {
    String orGrp = QC_STATUS_OR_GROUP;
    for (int i = 0; i < stats.length; i++) {
      if (Constants.QC_NOTVER.equals(stats[i])) {
        getQueryBuilder().addFilterOr(orGrp, FilterMetaData.KEY_SAMPLE_QCVERIFIED_NOT, Constants.PV_STATUS_UNPVED);
        getQueryBuilder().addTable(TableMetaData.KEY_TABLE_SAMPLE);
      }
      else if (Constants.QC_VER_ONLY.equals(stats[i])) {
        getQueryBuilder().addFilterOr(orGrp, FilterMetaData.KEY_SAMPLE_QCVERIFIED_ONLY, Constants.PV_STATUS_PVED);
        getQueryBuilder().addTable(TableMetaData.KEY_TABLE_SAMPLE);
      }
      else if (Constants.QC_REL_ONLY.equals(stats[i])) {
        getQueryBuilder().addFilterOr(orGrp, FilterMetaData.KEY_SAMPLE_QCRELEASED_ONLY);
        getQueryBuilder().addTable(TableMetaData.KEY_TABLE_SAMPLE);
      }
      else if (Constants.QC_POST.equals(stats[i])) {
        getQueryBuilder().addFilterOr(orGrp, FilterMetaData.KEY_SAMPLE_QCPOSTED);
        getQueryBuilder().addTable(TableMetaData.KEY_TABLE_SAMPLE);
      }
      else if (Constants.QC_PULL.equals(stats[i])) {
        getQueryBuilder().addFilterOr(orGrp, FilterMetaData.KEY_SAMPLE_PULLED);
        getQueryBuilder().addTable(TableMetaData.KEY_TABLE_SAMPLE);
      }
    }
  }

  public void addFilterLogicalRepository(RepeatingFilterData logicalRepositories) {
    //if no logical repository ids were passed in, the user doesn't have
    //access to any logical repositories and therefore no results should 
    //be returned.
    if (logicalRepositories.isEmpty()) {
      addFilterReturnNoResults();
    }
    else {
      getQueryBuilder().addFilter(
        FilterMetaData.KEY_SAMPLE_IN_LOGICAL_REPOSITORY,
        logicalRepositories);
    }
  }

  public void addFilterLocalSamples(String location) {
    getQueryBuilder().addFilter(FilterMetaData.KEY_ONLY_LOCAL_SAMPLES, location);
  }

  /**
   * Returns the SQL for the tissue sample query specified by this class.
   *
   * @return  The SQL statement for the sample query.
   */
  protected String doGetSummarySingleQuerySql() {

    ProductQueryBuilder query = getQueryBuilder();

    // Add the columns used for summary information.
    addColumnInSample(ColumnMetaData.KEY_SAMPLE_ASM_ID);
    addColumnInSample(ColumnMetaData.KEY_SAMPLE_CONSENT_ID);
    addColumnInSample(ColumnMetaData.KEY_SAMPLE_ARDAIS_ID);
    addColumnInSample(ColumnMetaData.KEY_SAMPLE_ID);
    addColumnInSample(ColumnMetaData.KEY_SAMPLE_SAMPLE_TYPE_CID);

	insertSortingColumns();

    // Append all of the clauses to form the query.
    StringBuffer sql = new StringBuffer(1024);
    query.getQuery(sql);

    sql.append("\n")
		.append("GROUP BY ")
		.append(DbAliases.TABLE_SAMPLE)
		.append(".")
		.append(DbConstants.SAMPLE_ARDAIS_ID)
		.append(", ")
		.append(DbAliases.TABLE_SAMPLE)
		.append(".")
		.append(DbConstants.SAMPLE_CONSENT_ID)
		.append(", ")
		.append(DbAliases.TABLE_SAMPLE)
		.append(".")
		.append(DbConstants.SAMPLE_SAMPLE_TYPE_CID)
		.append(", ")
		.append(DbAliases.TABLE_SAMPLE)
		.append(".")
		.append(DbConstants.SAMPLE_ASM_ID)
		.append(", ")
		.append(DbAliases.TABLE_SAMPLE)
		.append(".")
		.append(DbConstants.SAMPLE_ID);

	  if (!CollectionUtils.isEmpty(this._sortColumns))
	  {
		  for (SortByColumn column : this._sortColumns)
		  {
			  final ColumnMetaData metaData = ProductQueryMetaData.getColumnMetaData(column.getColumn());
			  sql.append(", ")
				  .append(metaData.getTableAlias())
				  .append(".")
				  .append(metaData.getColumn());
		  }
	  }

    return sql.toString();
  }

  /**
  * Returns overall order by clause
  *
  * @return  The order by clause for the sample query.
  */
  protected String doGetSummaryOrderBy() {
	  return ") ORDER BY " + getOrderColumnsString();
  }

  /**
  * Returns overall select clause
  *
  * @return  The select clause for the sample query.
  */
  protected String doGetSummarySelectClause()
  {
	  final StringBuilder sb = new StringBuilder();
	  sb.append("Select \"sample_ardais_id\" as ardais_id, \"sample_consent_id\" as consent_id, \"sample_asm_id\" as asm_id, sample_barcode_id, sample_type_cid ");
	  if (!CollectionUtils.isEmpty(this._sortColumns))
	  {
		  for (SortByColumn column : this._sortColumns)
		  {
			  if (!Arrays.asList(ColumnMetaData.KEY_SAMPLE_ASM_ID,
								 ColumnMetaData.KEY_SAMPLE_CONSENT_ID,
								 ColumnMetaData.KEY_SAMPLE_ARDAIS_ID,
								 ColumnMetaData.KEY_SAMPLE_ID,
								 ColumnMetaData.KEY_SAMPLE_SAMPLE_TYPE_CID)
				  .contains(column.getColumn()))
			  {
				  sb.append(", ");
				  final ColumnMetaData metadata = ProductQueryMetaData.getColumnMetaData(column.getColumn());
				  final String fragment = metadata.getSelectFragment();
				  if (fragment.contains(".") && !fragment.toLowerCase().contains(" as "))
				  {
					  sb.append(StringUtils.split(fragment, ".")[1]);
				  }
				  else
				  {
					  final String alias = metadata.getColumnAlias();
					  sb.append("\"")
						  .append(alias)
						  .append("\" as ")
						  .append(alias);
				  }
			  }
		  }
	  }
	  sb.append(" FROM (");

	  return sb.toString();
  }

  /**
   * @see com.ardais.bigr.query.generator.ProductSummaryQueryBuilder#newQueryBuilder()
   */
  protected ProductSummaryQueryBuilder newQueryBuilder() {
    return new TissueSummaryQueryBuilder();
  }

	public Set<SortByColumn> getSortColumns()
	{
		return _sortColumns;
	}

	public void setSortColumns(Set<SortByColumn> _sortColumns)
	{
		this._sortColumns = _sortColumns;
	}

	protected boolean joinConsent(String key)
	{
		return Arrays.asList(ColumnMetaData.KEY_CONSENT_CUSTOMER_ID,
							 ColumnMetaData.KEY_CONSENT_LOCATION)
			.contains(key);
	}

	protected boolean joinDonor(String key)
	{
		return Arrays.asList(ColumnMetaData.KEY_DONOR_CUSTOMER_ID,
							 ColumnMetaData.KEY_DONOR_RACE,
							 ColumnMetaData.KEY_DONOR_GENDER)
			.contains(key);
	}

	protected String getOrderColumnsString()
	{
		if (CollectionUtils.isEmpty(this._sortColumns))
		{
			return " ardais_id, consent_id, sample_type_cid, asm_id, sample_barcode_id";
		}
		else
		{
			return getOrderSortableColumnsString();
		}
	}

	protected String getOrderSortableColumnsString()
	{
		String result = " ";
		boolean first = true;
		for (SortByColumn column : this._sortColumns)
		{
			if (!first)
			{
				result += ", ";
			}
			final ColumnMetaData metaData = ProductQueryMetaData.getColumnMetaData(column.getColumn());
			if ("sample_consent_id".equalsIgnoreCase(metaData.getColumnAlias()))
			{
				result += "consent_id";
			}
			else if ("sample_ardais_id".equalsIgnoreCase(metaData.getColumnAlias()))
			{
				result += "ardais_id";
			}
			else if ("sample_asm_id".equalsIgnoreCase(metaData.getColumnAlias()))
			{
				result += "asm_id";
			}
			else
			{
				result += metaData.getColumnAlias();
			}
			result += " " + column.getOrder().name();
			result += " nulls last";
			first = false;
		}
		return result;
	}

	protected void insertSortingColumns()
	{
		if (!CollectionUtils.isEmpty(this._sortColumns))
		{
			for (SortByColumn column : this._sortColumns)
			{
				final String key = column.getColumn();
				addColumnInSample(key);
				if (joinConsent(key))
				{
					addJoinSampleConsent();
				}
				if (joinDonor(key))
				{
					addJoinSampleDonor();
				}
			}
		}
	}
}
