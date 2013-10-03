package com.ardais.bigr.javabeans;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.beanutils.BigrBeanUtilsBean;
import com.ardais.bigr.iltds.assistants.Packable;
import com.ardais.bigr.iltds.databeans.DateData;
import com.ardais.bigr.iltds.databeans.SampleData;
import com.ardais.bigr.security.SecurityInfo;
import com.ardais.bigr.util.BoxLayoutUtils;
import com.ardais.bigr.util.IcpUtils;
import com.ardais.bigr.util.gen.DbAliases;
import com.ardais.bigr.util.gen.DbConstants;
import com.gulfstreambio.gboss.GbossFactory;

/**
 * This object represents a box of samples (both the box id and the box contents).
 */
public class BoxDto implements Serializable, Packable {
  static final long serialVersionUID = 1699400084073233152L;

  private String _boxId = null;
  private List _samples = new ArrayList();

  private String _location;
  private String _room;
  private String _drawer;
  private String _slot;
  private String _unitName;
  
  private String _storageTypeCid;
  private String _storageTypeDesc;

  private String _manifestId;
  private DateData _checkInDate;
  private DateData _checkOutDate;
  private String _checkOutReason;
  private boolean _exists = false;

  private String _manifestOrder;
  private String _boxStatus;
  private String _boxNote;
  private String _checkoutUser;

  private int _boxSampleCount = 0;
  private boolean _containsPulledOrRevokedSamples = false;
  
  private String boxLayoutId = null;
  private BoxLayoutDto boxLayoutDto = null;

  private HashMap _contents = new HashMap();
  private HashMap _sampleTypes = new HashMap();
  private HashMap _sampleAliases = new HashMap();
  
  private String _availableStorageTypes;
  private String _warning;

  public BoxDto() {
    super();
  }

  /**
   * Create a new BoxDto, initialized with the data contained in the BoxDto passed in.
   *
   * @param boxDto the BoxDto.
   */
  public BoxDto(BoxDto boxDto) {
    this();
    BigrBeanUtilsBean.SINGLETON.copyProperties(this, boxDto);
    //any mutable objects must be handled seperately (BigrBeanUtilsBean.copyProperties
    //does a shallow copy.
    //must set box layout dto before adding samples
    if (boxDto.getBoxLayoutDto() != null) {
      setBoxLayoutDto(new BoxLayoutDto(boxDto.getBoxLayoutDto()));
    }
    if (!ApiFunctions.isEmpty(boxDto.getSamples())) {
      _samples.clear();
      Iterator iterator = boxDto.getSamples().iterator();
      while (iterator.hasNext()) {
        _samples.add(new SampleData((SampleData)iterator.next()));
      }
    }
    if (boxDto.getCheckInDate() != null) {
      setCheckInDate(new DateData(boxDto.getCheckInDate()));
    }
    if (boxDto.getCheckOutDate() != null) {
      setCheckOutDate(new DateData(boxDto.getCheckOutDate()));
    }
    //box contents is a hashmap of strings
    if (boxDto.getContents() != null && !boxDto.getContents().isEmpty()) {
      _contents = new HashMap();
      Iterator iterator = boxDto.getContents().keySet().iterator();
      while (iterator.hasNext()) {
        Object key = iterator.next();
        _contents.put(key, boxDto.getContents().get(key));
      }
    }
    //sample types is a hashmap of strings
    if (boxDto.getSampleTypes() != null && !boxDto.getSampleTypes().isEmpty()) {
      _sampleTypes = new HashMap();
      Iterator iterator = boxDto.getSampleTypes().keySet().iterator();
      while (iterator.hasNext()) {
        Object key = iterator.next();
        _sampleTypes.put(key, boxDto.getSampleTypes().get(key));
      }
    }
    //sample aliases is a hashmap of strings
    if (boxDto.getSampleAliases() != null && !boxDto.getSampleAliases().isEmpty()) {
      _sampleAliases = new HashMap();
      Iterator iterator = boxDto.getSampleAliases().keySet().iterator();
      while (iterator.hasNext()) {
        Object key = iterator.next();
        _sampleAliases.put(key, boxDto.getSampleAliases().get(key));
      }
    }
  }

  /**
   * Initialize a box given the box id.  Its initial contents will be null.
   *
   * @param packedBoxContents the packed string representation of the box's contents.
   */
  public BoxDto(String boxId) {
    this();
    setBoxId(boxId);
  }

  /**
   * Initialize a box given the box id and a packed string representing the
   * box contents.
   *
   * @param boxId the box id
   * @param packedBoxContents the packed string representation of the box's contents.
   *
   * @see #unpack(String) unpack
   * @see Packable
   */
  public BoxDto(String boxId, String packedBoxContents) {
    this(boxId);
    unpack(packedBoxContents);
  }

  /**
   * The packed form is a comma-separated list of cell name/sample-id pairs, where
   * the cell name is separated from the sample id by a colon (:).  We include spaces
   * after the commas and colons so that it can be used in places that need to be human
   * readable.  Only cells with non-null contents are represented in the packed string.
   * 
   * @return
   */
  public String pack() {
    
    // Don't bother packing, if there isn't anything to pack.
    if (getContents().isEmpty()) {
      return null;
    }

    BoxLayoutDto boxLayoutDto = getBoxLayoutDto();
    // Throw an exception if box layout is not set.
    if (boxLayoutDto == null) {
      throw (new ApiException("The box layout was not set. Please provide a box layout prior to packing."));
    }

    StringBuffer sb = new StringBuffer();

    sb.append(Integer.toString(boxLayoutDto.getNumberOfColumns()));
    sb.append(",");
    sb.append(Integer.toString(boxLayoutDto.getNumberOfRows()));
    sb.append(",");
    sb.append(boxLayoutDto.getXaxisLabelCid());
    sb.append(",");
    sb.append(boxLayoutDto.getYaxisLabelCid());
    sb.append("#");
    
    Set keys = _contents.keySet();

    boolean first = true;
    Iterator iterator = keys.iterator();
    while (iterator.hasNext()) {
      String cellRef = (String)iterator.next();
      String sampleId = (String)_contents.get(cellRef);

      if (!ApiFunctions.isEmpty(sampleId)) {
        if (first) {
          first = false;
        }
        else {
          sb.append(", ");
        }

        sb.append(cellRef);
        sb.append(": ");
        sb.append(sampleId);
      }
    }
    
    return sb.toString();
  }
  
  /**
   * See the "pack" method for a description of the packed data format
   * we expect here.  We don't check that it has the expected format, we
   * just assume that it does.
   * 
   * @param packedData
   */
  public void unpack(String packedData) {
    // First empty out all of the existing contents.
    _contents.clear();
    
    if (ApiFunctions.isEmpty(packedData)) {
      return;
    }
    
    BoxLayoutDto boxLayoutDto = new BoxLayoutDto();

    StringTokenizer st = new StringTokenizer(packedData, ",: #", false);

    boxLayoutDto.setNumberOfColumns(Integer.parseInt(st.nextToken()));
    boxLayoutDto.setNumberOfRows(Integer.parseInt(st.nextToken()));
    boxLayoutDto.setXaxisLabelCid(st.nextToken());
    boxLayoutDto.setYaxisLabelCid(st.nextToken());
    
    while (st.hasMoreTokens()) {
      String cellRef = st.nextToken();
      String sampleId = st.nextToken();

      setCellContent(cellRef, sampleId);
    }
    
    setBoxLayoutDto(boxLayoutDto);
  }

  /**
   * Return a set of the object ids of the objects that are directly involved
   * in this object.  This is simply the set of all sample ids in the box contents
   * plus the id of the box itself.
   *
   * <p>This is a helper method for implementations of
   * com.ardais.bigr.iltds.btx.BTXDetails#getDirectlyInvolvedObjects().
   *
   * @return the set of directly involved object ids.
   */
  public Set getDirectlyInvolvedObjects() {
    Set set = new HashSet();

    if (_contents != null) {
      Collection objects =_contents.values();
      
      Iterator iterator = objects.iterator();
      while (iterator.hasNext()) {
        String sample = (String)iterator.next();
        if (!ApiFunctions.isEmpty(sample)) {
          set.add(sample);
        }
      }
    }

    if (_boxId != null) {
      set.add(_boxId);
    }
    return set;
  }

  /**
   * Append to the supplied string buffer an HTML representation
   * of the box and its contents that will contains ICP hyperlinks
   * for the object ids involved.
   *
   * @param buffer the string buffer to append to.
   */
  public void appendICPHTML(StringBuffer buffer, SecurityInfo securityInfo) {
    appendICPHTML(buffer, false, securityInfo);
  }

  /**
   * Append to the supplied string buffer an HTML representation
   * of the box and its contents that will contains ICP hyperlinks
   * for the object ids involved.
   *
   * @param buffer the string buffer to append to.
   */
  public void appendICPHTML(StringBuffer buffer, boolean isBig, SecurityInfo securityInfo) {
    int cellpadding = 0;
    String bigString = "";
    if (isBig) {
      bigString = "Big";
      cellpadding = 2;
    }

    String boxId = getBoxId();

    if (_contents.isEmpty()) {
      buffer.append(IcpUtils.prepareLink(boxId, securityInfo));
    }
    else {
      BoxLayoutDto boxLayoutDto = getBoxLayoutDto();
      int numberOfRows = boxLayoutDto.getNumberOfRows();
      int numberOfColumns = boxLayoutDto.getNumberOfColumns();

      buffer.append("<table class=\"boxTable");
      buffer.append(bigString);
      buffer.append("\" cellspacing=\"0\" cellpadding=\"");
      buffer.append(cellpadding);
      buffer.append("\">\n");
      buffer.append("<colgroup><col></colgroup>\n");
      buffer.append("<colgroup><col span=\"" + Integer.toString(numberOfColumns) + "\" width=\"");
      buffer.append(((isBig) ? "20" : "15"));
      buffer.append("\"></colgroup>\n");
      buffer.append("<tr><td class=\"boxTableBlankCorner");
      buffer.append(bigString);
      buffer.append("\" rowspan=\"2\">&nbsp;</td><td class=\"boxTableBoxId");
      buffer.append(bigString);
      buffer.append("\" colspan=\"" + Integer.toString(numberOfColumns) + "\">");
      buffer.append(IcpUtils.prepareLink(boxId, securityInfo));
      buffer.append("</td></tr>\n");
      buffer.append("<tr class=\"boxTableColumnLabels");
      buffer.append(bigString);
      buffer.append("\">");

      for (int column = 1; column <= numberOfColumns; column++) {
        buffer.append("<td>" + BoxLayoutUtils.convertToXaxisLabel(column, boxLayoutDto) + "</td>");
      }
      buffer.append("</tr>\n");

      int cellRef = 1;
      for (int row = 1; row <= numberOfRows; row++) {
        buffer.append("<tr");
        if (row == numberOfRows) {
          buffer.append(" class=\"boxTableLastRow");
          buffer.append(bigString);
          buffer.append('\"');
        }
        buffer.append("><td class=\"boxTableRowLabel");
        buffer.append(bigString);
        buffer.append("\">");
        buffer.append(BoxLayoutUtils.convertToYaxisLabel(row, boxLayoutDto));
        buffer.append("</td>");

        for (int column = 1; column <= numberOfColumns; column++, cellRef++) {
          buffer.append("<td>");
          String cellContent = getCellContent(cellRef);
          if (ApiFunctions.isEmpty(cellContent)) {
            buffer.append("&nbsp;");
          }
          else {
            buffer.append(IcpUtils.prepareLink(cellContent, securityInfo));
          }
          buffer.append("</td>");
        }
        buffer.append("</tr>");
      }
      buffer.append("</table>\n");
    }
  }

  public String boxIcpHtml(SecurityInfo securityInfo) {
    if (!isActiveInv()) {
      return "<b>Box " + getBoxId() + " is not in active inventory.</b>";
    }
    else {
      BoxDto boxDto = new BoxDto();
      boxDto.setBoxId(getBoxId());
      boxDto.setBoxLayoutId(getBoxLayoutId());
      boxDto.setBoxLayoutDto(getBoxLayoutDto());

      for (int i = 0; i < _samples.size(); i++) {
        SampleData sample = (SampleData) _samples.get(i);
        boxDto.setCellContent(
          sample.getCell_ref_location(),
          sample.getSample_id_v(securityInfo, true),
          true);
      }

      StringBuffer results = new StringBuffer();
      boxDto.appendICPHTML(results, true, securityInfo);
      return results.toString();
    }
  }

  public void populateFromResultSet(Map columns, ResultSet rs) throws SQLException {
    if (columns.containsKey(DbConstants.SAMPLE_BOX_BOX_BARCODE_ID)) {
      setBoxId(rs.getString(DbConstants.SAMPLE_BOX_BOX_BARCODE_ID));
    }
    else if (columns.containsKey(DbAliases.SAMPLE_BOX_BOX_BARCODE_ID)) {
      setBoxId(rs.getString(DbAliases.SAMPLE_BOX_BOX_BARCODE_ID));
    }

    if (columns.containsKey(DbConstants.SAMPLE_BOX_NOTE)) {
      setBoxNote(rs.getString(DbConstants.SAMPLE_BOX_NOTE));
    }
    else if (columns.containsKey(DbAliases.SAMPLE_BOX_NOTE)) {
      setBoxNote(rs.getString(DbAliases.SAMPLE_BOX_NOTE));
    }

    if (columns.containsKey(DbConstants.SAMPLE_BOX_STATUS)) {
      setBoxStatus(rs.getString(DbConstants.SAMPLE_BOX_STATUS));
    }
    else if (columns.containsKey(DbAliases.SAMPLE_BOX_STATUS)) {
      setBoxStatus(rs.getString(DbAliases.SAMPLE_BOX_STATUS));
    }

    if (columns.containsKey(DbConstants.SAMPLE_BOX_CHECK_IN_DATE)) {
      setCheckInDate(new DateData(rs.getTimestamp(DbConstants.SAMPLE_BOX_CHECK_IN_DATE)));
    }
    else if (columns.containsKey(DbAliases.SAMPLE_BOX_CHECK_IN_DATE)) {
      setCheckInDate(new DateData(rs.getTimestamp(DbAliases.SAMPLE_BOX_CHECK_IN_DATE)));
    }
    
    if (columns.containsKey(DbConstants.SAMPLE_BOX_CHECK_OUT_DATE)) {
      setCheckOutDate(new DateData(rs.getTimestamp(DbConstants.SAMPLE_BOX_CHECK_OUT_DATE)));
    }
    else if (columns.containsKey(DbAliases.SAMPLE_BOX_CHECK_OUT_DATE)) {
      setCheckOutDate(new DateData(rs.getTimestamp(DbAliases.SAMPLE_BOX_CHECK_OUT_DATE)));
    }

    if (columns.containsKey(DbConstants.SAMPLE_BOX_CHECK_OUT_REASON)) {
      setCheckOutReason(rs.getString(DbConstants.SAMPLE_BOX_CHECK_OUT_REASON));
    }
    else if (columns.containsKey(DbAliases.SAMPLE_BOX_CHECK_OUT_REASON)) {
      setCheckOutReason(rs.getString(DbAliases.SAMPLE_BOX_CHECK_OUT_REASON));
    }
    
    if (columns.containsKey(DbConstants.SAMPLE_BOX_CHECK_OUT_STAFF_ID)) {
      setCheckoutUser(rs.getString(DbConstants.SAMPLE_BOX_CHECK_OUT_STAFF_ID));
    }
    else if (columns.containsKey(DbAliases.SAMPLE_BOX_CHECK_OUT_STAFF_ID)) {
      setCheckoutUser(rs.getString(DbAliases.SAMPLE_BOX_CHECK_OUT_STAFF_ID));
    }

    if (columns.containsKey(DbConstants.SAMPLE_BOX_STORAGE_TYPE_CID)) {
      setStorageTypeCid(rs.getString(DbConstants.SAMPLE_BOX_STORAGE_TYPE_CID));
    }
    else if (columns.containsKey(DbAliases.SAMPLE_BOX_STORAGE_TYPE_CID)) {
      setStorageTypeCid(rs.getString(DbAliases.SAMPLE_BOX_STORAGE_TYPE_CID));
    }
    
    if (columns.containsKey(DbConstants.SAMPLE_BOX_MANIFEST_NUMBER)) {
      setManifestId(rs.getString(DbConstants.SAMPLE_BOX_MANIFEST_NUMBER));
    }
    else if (columns.containsKey(DbAliases.SAMPLE_BOX_MANIFEST_NUMBER)) {
      setManifestId(rs.getString(DbAliases.SAMPLE_BOX_MANIFEST_NUMBER));
    }

    if (columns.containsKey(DbConstants.SAMPLE_BOX_MANIFEST_ORDER)) {
      setManifestOrder(rs.getString(DbConstants.SAMPLE_BOX_MANIFEST_ORDER));
    }
    else if (columns.containsKey(DbAliases.SAMPLE_BOX_MANIFEST_ORDER)) {
      setManifestOrder(rs.getString(DbAliases.SAMPLE_BOX_MANIFEST_ORDER));
    }
    
    if (columns.containsKey(DbConstants.BOX_LOCATION_ADDRESS_ID)) {
      setLocation(rs.getString(DbConstants.BOX_LOCATION_ADDRESS_ID));
    }
    else if (columns.containsKey(DbAliases.BOX_LOCATION_ADDRESS_ID)) {
      setLocation(rs.getString(DbAliases.BOX_LOCATION_ADDRESS_ID));      
    }
    
    if (columns.containsKey(DbConstants.BOX_LOCATION_ROOM_ID)) {
      setRoom(rs.getString(DbConstants.BOX_LOCATION_ROOM_ID));
    }
    else if (columns.containsKey(DbAliases.BOX_LOCATION_ROOM_ID)) {
      setRoom(rs.getString(DbAliases.BOX_LOCATION_ROOM_ID));
    }
    
    if (columns.containsKey(DbConstants.BOX_LOCATION_UNIT_NAME)) {
      setUnitName(rs.getString(DbConstants.BOX_LOCATION_UNIT_NAME));
    }
    else if (columns.containsKey(DbAliases.BOX_LOCATION_UNIT_NAME)) {
      setUnitName(rs.getString(DbAliases.BOX_LOCATION_UNIT_NAME));
    }
    
    if (columns.containsKey(DbConstants.BOX_LOCATION_DRAWER_ID)) {
      setDrawer(rs.getString(DbConstants.BOX_LOCATION_DRAWER_ID));
    }
    else if (columns.containsKey(DbAliases.BOX_LOCATION_DRAWER_ID)) {      
      setDrawer(rs.getString(DbAliases.BOX_LOCATION_DRAWER_ID));
    }
    
    if (columns.containsKey(DbConstants.BOX_LOCATION_SLOT_ID)) {
      setSlot(rs.getString(DbConstants.BOX_LOCATION_SLOT_ID));
    }
    else if (columns.containsKey(DbAliases.BOX_LOCATION_SLOT_ID)) {
      setSlot(rs.getString(DbAliases.BOX_LOCATION_SLOT_ID));
    }

    if (columns.containsKey(DbConstants.SAMPLE_BOX_LAYOUT_ID)) {
      setBoxLayoutId(rs.getString(DbConstants.SAMPLE_BOX_LAYOUT_ID));
    }
    else if (columns.containsKey(DbAliases.SAMPLE_BOX_LAYOUT_ID)) {
      setBoxLayoutId(rs.getString(DbAliases.SAMPLE_BOX_LAYOUT_ID));
    }

    if (!ApiFunctions.isEmpty(getBoxLayoutId())) {
      setBoxLayoutDto(BoxLayoutUtils.getBoxLayoutDto(getBoxLayoutId()));
    }

  }

  public String getBoxLocationDisplay(SecurityInfo securityInfo) {
    StringBuffer buffer = new StringBuffer();

    // The full location details are visible only if the user is either an ICP Superuser
    // or is at the same location as the box.  Otherwise, we only show the Location, not
    // the room, unit, drawer, etc.

    // Format of result:
    //   <loc id> <room>: <FR/PA> <unit>/<drawer>/<slot>

    if (!isActiveInv()) {
      return ("Not In Active Inventory");
    }

    boolean isFullDetailsViewable = securityInfo.isHasPrivilege(SecurityInfo.PRIV_ICP_SUPERUSER);
    if (!isFullDetailsViewable) {
      String userLocation = securityInfo.getUserLocationId();
      String boxLocation = getLocation();
      if (!ApiFunctions.isEmpty(userLocation)
        && !ApiFunctions.isEmpty(boxLocation)
        && userLocation.equals(boxLocation)) {
        isFullDetailsViewable = true;
      }
    }

    buffer.append(getLocation());

    if (isFullDetailsViewable) {
      buffer.append(" ");
      buffer.append(getStorageTypeDesc());
      buffer.append(" ");
      buffer.append(getRoom());
      buffer.append("- ");
      buffer.append(getUnitName());
      buffer.append("- ");
      buffer.append(getDrawer());
      buffer.append("- ");
      buffer.append(getSlot());
    }

    return buffer.toString();
  }

  public List getSamples() {
    return _samples;
  }

  public void setSamples(List newSamples) {
    _samples = newSamples;
    
    // Throw an exception if box layout is not set.
    if (ApiFunctions.isEmpty(getBoxLayoutId())) {
      throw (new ApiException("Please provide a box layout prior to setting samples."));
    }
  }

  public int getSampleCount() {
    return (_samples != null) ? _samples.size() : 0;
  }

  public int getSamplesRestricted() {
    int count = 0;

    for (int i = 0; i < _samples.size(); i++) {
      SampleData sample = (SampleData) _samples.get(i);
      if (sample.isRestricted()) {
        count++;
      }
    }

    return count;
  }
  
  public int getContentCount() {
    return _contents.size();
  }

  public boolean isActiveInv() {
    return (getUnitName() != null);
  }

  public String getCellContent(int cellRef) {
    return getCellContent(Integer.toString(cellRef));
  }

  public String getCellContent(String cellRef) {
    return ((String)_contents.get(cellRef));
  }

  public void setCellContent(int cellRef, String cellContent) {
    setCellContent(Integer.toString(cellRef), cellContent);
  }

  public void setCellContent(int cellRef, String cellContent, boolean isFormatted) {
    setCellContent(Integer.toString(cellRef), cellContent, isFormatted);
  }

  public void setCellContent(String cellRef, String cellContent) {
    setCellContent(cellRef, cellContent, false);
  }

  public void setCellContent(String cellRef, String cellContent, boolean isFormatted) {
    // If the string is not null but length is 0, then null out the cell content.
    if (ApiFunctions.isEmpty(cellContent)) {
      cellContent = null;
    }

    // Spaces, commas, colons, or hash marks in the sample ids would confuse the pack/unpack
    // methods, so we explicitly check for them here.
    //
    if (!ApiFunctions.isEmpty(cellContent)) {
      if (!isFormatted
        && ((cellContent.indexOf(' ') >= 0)
          || (cellContent.indexOf(',') >= 0)
          || (cellContent.indexOf(':') >= 0)
          || (cellContent.indexOf('#') >= 0))) {
        throw new IllegalArgumentException(
          "BoxDto.setCellContent: sample id must not contain spaces, commas, colons, or hash marks: "
            + cellContent
            + ".");
      }
    }
    _contents.put(cellRef, cellContent);
  }

  public String getCellSampleType(int cellRef) {
    return getCellSampleType(Integer.toString(cellRef));
  }

  public String getCellSampleType(String cellRef) {
    return ((String)_sampleTypes.get(cellRef));
  }

  public void setCellSampleType(int cellRef, String sampleType) {
    setCellSampleType(Integer.toString(cellRef), sampleType);
  }

  public void setCellSampleType(String cellRef, String sampleType) {
    _sampleTypes.put(cellRef, sampleType);
  }

  public String getCellSampleAlias(int cellRef) {
    return getCellSampleAlias(Integer.toString(cellRef));
  }

  public String getCellSampleAlias(String cellRef) {
    return ((String)_sampleAliases.get(cellRef));
  }

  public void setCellSampleAlias(int cellRef, String sampleAlias) {
    setCellSampleAlias(Integer.toString(cellRef), sampleAlias);
  }

  public void setCellSampleAlias(String cellRef, String sampleAlias) {
    _sampleAliases.put(cellRef, sampleAlias);
  }

  /**
   * @return
   */
  public String getBoxId() {
    return _boxId;
  }

  /**
   * @return
   */
  public String getBoxNote() {
    return _boxNote;
  }

  /**
   * @return
   */
  public int getBoxSampleCount() {
    return _boxSampleCount;
  }

  /**
   * @return
   */
  public String getBoxStatus() {
    return _boxStatus;
  }

  /**
   * @return
   */
  public DateData getCheckInDate() {
    return _checkInDate;
  }

  /**
   * @return
   */
  public DateData getCheckOutDate() {
    return _checkOutDate;
  }

  /**
   * @return
   */
  public String getCheckOutReason() {
    return _checkOutReason;
  }

  /**
   * @return
   */
  public String getCheckoutUser() {
    return _checkoutUser;
  }

  /**
   * @return
   */
  public boolean isContainsPulledOrRevokedSamples() {
    return _containsPulledOrRevokedSamples;
  }

  /**
   * @return
   */
  public HashMap getContents() {
    return _contents;
  }

  /**
   * @return
   */
  public String getDrawer() {
    return _drawer;
  }

  /**
   * @return
   */
  public boolean isExists() {
    return _exists;
  }

  /**
   * @return
   */
  public String getLocation() {
    return _location;
  }

  /**
   * @return
   */
  public String getManifestId() {
    return _manifestId;
  }

  /**
   * @return
   */
  public String getManifestOrder() {
    return _manifestOrder;
  }

  /**
   * @return
   */
  public String getRoom() {
    return _room;
  }

  /**
   * @return
   */
  public String getSlot() {
    return _slot;
  }

  /**
   * @return
   */
  public String getUnitName() {
    return _unitName;
  }

  /**
   * @return
   */
  public BoxLayoutDto getBoxLayoutDto() {
    return boxLayoutDto;
  }

  /**
   * @return
   */
  public String getBoxLayoutId() {
    return boxLayoutId;
  }

  /**
   * @param string
   */
  public void setBoxId(String string) {
    _boxId = string;
  }

  /**
   * @param string
   */
  public void setBoxNote(String string) {
    _boxNote = string;
  }

  /**
   * @param i
   */
  public void setBoxSampleCount(int i) {
    _boxSampleCount = i;
  }

  /**
   * @param string
   */
  public void setBoxStatus(String string) {
    _boxStatus = string;
  }

  /**
   * @param data
   */
  public void setCheckInDate(DateData data) {
    _checkInDate = data;
  }

  /**
   * @param data
   */
  public void setCheckOutDate(DateData data) {
    _checkOutDate = data;
  }

  /**
   * @param string
   */
  public void setCheckOutReason(String string) {
    _checkOutReason = string;
  }

  /**
   * @param string
   */
  public void setCheckoutUser(String string) {
    _checkoutUser = string;
  }

  /**
   * @param b
   */
  public void setContainsPulledOrRevokedSamples(boolean b) {
    _containsPulledOrRevokedSamples = b;
  }

  /**
   * @param map
   */
  public void setContents(HashMap map) {
    _contents = map;
  }

  /**
   * @param string
   */
  public void setDrawer(String string) {
    _drawer = string;
  }

  /**
   * @param b
   */
  public void setExists(boolean b) {
    _exists = b;
  }

  /**
   * @param string
   */
  public void setLocation(String string) {
    _location = string;
  }

  /**
   * @param string
   */
  public void setManifestId(String string) {
    _manifestId = string;
  }

  /**
   * @param string
   */
  public void setManifestOrder(String string) {
    _manifestOrder = string;
  }

  /**
   * @param string
   */
  public void setRoom(String string) {
    _room = string;
  }

  /**
   * @param string
   */
  public void setSlot(String string) {
    _slot = string;
  }

  /**
   * @param string
   */
  public void setUnitName(String string) {
    _unitName = string;
  }

  /**
   * @param dto
   */
  public void setBoxLayoutDto(BoxLayoutDto dto) {
    boxLayoutDto = dto;
  }

  /**
   * @param string
   */
  public void setBoxLayoutId(String string) {
    boxLayoutId = string;
  }

  /**
   * @return
   */
  public String getStorageTypeCid() {
    return _storageTypeCid;
  }

  /**
   * @return
   */
  public String getStorageTypeDesc() {
    return _storageTypeDesc;
  }

  /**
   * @param string
   */
  public void setStorageTypeCid(String string) {
    _storageTypeCid = string;
    if (!ApiFunctions.isEmpty(string)) {
      setStorageTypeDesc(GbossFactory.getInstance().getDescription(_storageTypeCid));
    }
  }

  /**
   * @param string
   */
  public void setStorageTypeDesc(String string) {
    _storageTypeDesc = string;
  }

  /**
   * @return
   */
  public String getAvailableStorageTypes() {
    return _availableStorageTypes;
  }

  /**
   * @param string
   */
  public void setAvailableStorageTypes(String string) {
    _availableStorageTypes = string;
  }

  /**
   * @return
   */
  public String getWarning() {
    return _warning;
  }

  /**
   * @param string
   */
  public void setWarning(String string) {
    _warning = string;
  }
  /**
   * @return
   */
  public HashMap getSampleTypes() {
    return _sampleTypes;
  }

  /**
   * @param map
   */
  public void setSampleTypes(HashMap map) {
    _sampleTypes = map;
  }
  
  /**
   * @return
   */
  public HashMap getSampleAliases() {
    return _sampleAliases;
  }

  /**
   * @param map
   */
  public void setSampleAliases(HashMap map) {
    _sampleAliases = map;
  }

}