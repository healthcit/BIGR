package com.ardais.bigr.iltds.helpers;

/**
 * Specifies which sample details should be returned from a sample query that uses
 * {@link com.ardais.bigr.iltds.bizlogic.SampleFinder SampleFinder}.
 * 
 * @see SampleFilter
 */
public class SampleSelect {

  /**
   * Specifies to not return any extended basic information.
   */
  public final static int EXTENDED_BASIC_NONE = 0;

  /**
   * Specifies to return the extended basic information for imported samples, which includes a bit 
   * more than just the basic sample information (e.g. procedure, tissue, gross appearance which
   * are from other objects like the ASM, ASM form, etc.). 
   */
  public final static int EXTENDED_BASIC_FOR_IMPORTED = 1;

  /**
   * Specifies to not return any of the sample statuses (from ILTDS_SAMPLE_STATUS).
   */
  public final static int STATUSES_NONE = 0;

  /**
   * Specifies to return all of the sample statuses (from ILTDS_SAMPLE_STATUS) in
   * reverse chronological order.
   */
  public final static int STATUSES_ALL_REV_CHRON = 1;

  /**
   * Specifies to not return the inventory groups to which the sample belongs.
   */
  public final static int INVENTORY_GROUPS_NONE = 0;

  /**
   * Specifies to return the inventory groups to which the sample belongs.
   */
  public final static int INVENTORY_GROUPS_ALL = 1;

  /**
   * A selector that retrieves only the basic details.
   */
  public final static SampleSelect BASIC =
    new SampleSelect(true, true, false, EXTENDED_BASIC_NONE, STATUSES_NONE, INVENTORY_GROUPS_NONE);

  /**
   * A selector that retrieves the basic details plus the extended basics for imported samples.
   */
  public final static SampleSelect BASIC_IMPORTED =
    new SampleSelect(true, true, false, EXTENDED_BASIC_FOR_IMPORTED, STATUSES_NONE, INVENTORY_GROUPS_NONE);

  /**
   * A selector that retrieves the basic details plus the extended basics for imported samples
   * plus all of the statuses.
   */
  public final static SampleSelect BASIC_IMPORTED_STATUSES =
    new SampleSelect(true, true, false, EXTENDED_BASIC_FOR_IMPORTED, STATUSES_ALL_REV_CHRON, INVENTORY_GROUPS_NONE);

  /**
   * A selector that retrieves the basic details plus the extended basics for imported samples
   * plus the sample type config.
   */
  public final static SampleSelect BASIC_IMPORTED_CONFIG =
    new SampleSelect(true, true, true, EXTENDED_BASIC_FOR_IMPORTED, STATUSES_NONE, INVENTORY_GROUPS_NONE);

  /**
   * A selector that retrieves the basic details plus the extended basics for imported samples
   * plus the sample type config plus all of the statuses.
   */
  public final static SampleSelect BASIC_IMPORTED_CONFIG_STATUSES =
    new SampleSelect(true, true, true, EXTENDED_BASIC_FOR_IMPORTED, STATUSES_ALL_REV_CHRON, INVENTORY_GROUPS_NONE);

  /**
   * A selector that retrieves the basic details plus the extended basics for imported samples
   * plus the sample type config.
   */
  public final static SampleSelect BASIC_IMPORTED_CONFIG_INVENTORYGROUPS =
    new SampleSelect(true, true, true, EXTENDED_BASIC_FOR_IMPORTED, STATUSES_NONE, INVENTORY_GROUPS_ALL);

  private boolean _immutable = false;
  private boolean _includeBasic = true;
  private boolean _includeSampleTypeConfig = false;
  private int _extendedBasic = EXTENDED_BASIC_NONE;
  private int _statuses = STATUSES_NONE;
  private int _inventoryGroups = INVENTORY_GROUPS_NONE;

  /**
   * Creates a new SampleSelect initialized to select only basic sample information.
   */
  public SampleSelect() {
    super();
  }

  /**
   * Creates a new SampleSelect initialized with the specified settings.  If the 
   * <code>immutable</code> parameter is true, the new instance is made immutable.
   */
  public SampleSelect(
    boolean immutable,
    boolean includeBasic,
    boolean includeSampleTypeConfig,
    int extendedBasic,
    int statuses,
    int inventoryGroups) {

    this();
    setIncludeBasic(includeBasic);
    setIncludeSampleTypeConfig(includeSampleTypeConfig);
    setExtendedBasic(extendedBasic);
    setStatuses(statuses);
    setInventoryGroups(inventoryGroups);
    if (immutable) {
      setImmutable();
    }
  }

  /**
   * Returns an indication of whether the basic sample properties should be returned.  By default
   * this is <code>true</code>, i.e. the basic sample properties are returned.  
   * 
   * @return <code>true</code> if the basic sample properties should be populated in the query
   * result objects.
   * @see #setIncludeBasic(boolean)
   */
  public boolean isIncludeBasic() {
    return _includeBasic;
  }

  /**
   * Sets whether the basic sample properties should be returned.  This includes all of the simple 
   * properties on {@link com.ardais.bigr.javabeans.SampleData} such as barcode, alias, sample 
   * type, consent, donor, and in general all of the columns in ILTDS_SAMPLE.  It does not include 
   * properties that are complex subobjects, such as details of the box the sample is currently in, 
   * if any.  By default this is <code>true</code>, i.e. the basic sample properties are returned.
   * Setting it to false causes only the following basic identifiers to be returned - sample 
   * barcode, sample alias and sample type.  
   * 
   * @param  includeBasic  <code>true</code> if the basic sample properties should be populated 
   * in the query results objects. 
   */
  public void setIncludeBasic(boolean includeBasic) {
    checkImmutable();
    _includeBasic = includeBasic;
  }

  /**
   * Returns an indication of whether this instance includes at least one of the categories of 
   * information that can be included.
   * 
   * @return  <code>true</code> if this instance includes at least one category of information.
   */
  public boolean isSomethingSelected() {
    return isIncludeBasic()
      || isIncludeSampleTypeConfig()
      || (getExtendedBasic() != EXTENDED_BASIC_NONE)
      || (getStatuses() != STATUSES_NONE);
  }

  /**
   * Returns an indication of whether this instance is immutable.
   * 
   * @return <code>true</code> if this instance is immutable.
   */
  public boolean isImmutable() {
    return _immutable;
  }

  /**
   * Sets this instance to be immutable.  Any attempts to modify this instance after it is 
   * made immutable will throw an exception.
   */
  public void setImmutable() {
    _immutable = true;
  }

  /**
   * Throws an exception if this instance is immutable.
   */
  private void checkImmutable() {
    if (_immutable) {
      throw new IllegalStateException("Attempted to modify an immutable instance.");
    }
  }

  /**
   * Returns whether sample type configuration information is returned or not.
   *  
   * @return  <code>true</code> if sample type configuration information is returned; 
   *          <code>false</code> otherwise.  The default is <code>false</code>.
   */
  public boolean isIncludeSampleTypeConfig() {
    return _includeSampleTypeConfig;
  }

  /**
   * Sets whether sample type configuration information is returned or not.
   * 
   * @param  include  <code>true</code> to have sample type configuration information returned
   */
  public void setIncludeSampleTypeConfig(boolean include) {
    _includeSampleTypeConfig = include;
  }

  /**
   * Returns the extended basic level of information to include.
   *  
   * @return  The extended basic level of information.  This will be one of the EXTENDED_BASIC_* 
   *          constants in the class.  The default value is EXTENDED_BASIC_NONE.
   */
  public int getExtendedBasic() {
    return _extendedBasic;
  }

  /**
   * Sets the extended basic level of information to include.  If the level is something other
   * than EXTENDED_BASIC_NONE, then basic information is also returned.
   * 
   * @param  level  the extended basic level of information.  Must be one of the EXTENDED_BASIC_*
   *                constants in this class.
   */
  public void setExtendedBasic(int level) {
    _extendedBasic = level;
    if (level != EXTENDED_BASIC_NONE) {
      setIncludeBasic(true);
    }
  }

  /**
   * Returns the level of detail to include for sample statuses.
   *  
   * @return  The sample statuses level of detail.  This will be one of the STATUSES_* constants.
   *          The default value is STATUSES_NONE.
   */
  public int getStatuses() {
    return _statuses;
  }

  /**
   * Sets the level of detail to include for sample statuses. 
   * 
   * @param  level  the sample statuses level of detail.  Must be one of the STATUSES_* constants
   *                in this class.
   */
  public void setStatuses(int level) {
    checkImmutable();
    if (level != STATUSES_NONE && level != STATUSES_ALL_REV_CHRON) {
      throw new IllegalArgumentException("Invalid statuses code: " + level);
    }
    _statuses = level;
  }

  /**
   * Returns the level of detail to include for sample inventory groups.
   *  
   * @return  The sample inventory group level of detail.  This will be one of the 
   *          INVENTORY_GROUPS_* constants.
   *          The default value is INVENTORY_GROUPS_NONE.
   */
  public int getInventoryGroups() {
    return _inventoryGroups;
  }

  /**
   * Sets the level of detail to include for sample inventory groups. 
   * 
   * @param  level  the sample inventory group level of detail.  Must be one of the 
   *                INVENTORY_GROUPS_* constants in this class.
   */
  public void setInventoryGroups(int level) {
    checkImmutable();
    if (level != INVENTORY_GROUPS_NONE && level != INVENTORY_GROUPS_ALL) {
      throw new IllegalArgumentException("Invalid inventory group code: " + level);
    }
    _inventoryGroups = level;
  }

}
