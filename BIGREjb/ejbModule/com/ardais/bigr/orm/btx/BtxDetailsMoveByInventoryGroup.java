package com.ardais.bigr.orm.btx;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.api.Escaper;
import com.ardais.bigr.btx.framework.BtxHistoryAttributes;
import com.ardais.bigr.iltds.assistants.LegalValueSet;
import com.ardais.bigr.iltds.btx.BTXDetails;
import com.ardais.bigr.iltds.btx.BTXHistoryRecord;
import com.ardais.bigr.iltds.helpers.FormLogic;
import com.ardais.bigr.iltds.helpers.IltdsUtils;
import com.ardais.bigr.javabeans.IdList;
import com.ardais.bigr.javabeans.SampleData;
import com.ardais.bigr.security.SecurityInfo;
import com.ardais.bigr.util.Constants;
import com.ardais.bigr.util.IcpUtils;

public class BtxDetailsMoveByInventoryGroup extends BTXDetails {

  private String _inventoryGroup;
  private String _inventoryGroupName;
  private String _action;
  private String _note;
  private List _sampleIds;
  private LegalValueSet _inventoryGroupChoices;
  private List _samples;
  
  /**
   * 
   */
  public BtxDetailsMoveByInventoryGroup() {
    super();
  }

  /* (non-Javadoc)
   * @see com.ardais.bigr.iltds.btx.BTXDetails#getBTXType()
   */
  public String getBTXType() {
    return BTXDetails.BTX_TYPE_MOVE_BY_INVENTORY_GROUP;
  }

  /**
   * @see com.ardais.bigr.iltds.btx.BTXDetails#getDirectlyInvolvedObjects()
   */
  public Set getDirectlyInvolvedObjects() {
    Set set = new HashSet();
    set.add(FormLogic.makePrefixedLogicalRepositoryId(getInventoryGroup()));
    return set;
  }

  /**
   * @see com.ardais.bigr.iltds.btx.BTXDetails#doGetDetailsAsHTML()
   */
  protected String doGetDetailsAsHTML() {
    SecurityInfo securityInfo = getLoggedInUserSecurityInfo();
    
    StringBuffer sb = new StringBuffer(128);
    sb.append("The following samples were ");
    if (getAction().equalsIgnoreCase(Constants.OPERATION_ADD)) {
      sb.append("added to ");
    }
    else if (getAction().equalsIgnoreCase(Constants.OPERATION_REMOVE)) {
      sb.append("removed from ");
    }
    sb.append("inventory group ");
    String prefixedInventoryGroupId = FormLogic.makePrefixedLogicalRepositoryId(getInventoryGroup());
    sb.append(IcpUtils.prepareLink(prefixedInventoryGroupId, getInventoryGroupName(), securityInfo));
    sb.append(":");
    
    //if the output parameter of samples was set, create a map of sample ids to samples for
    //easy access later on
    Map sampleIdToSample = new HashMap();
    if (!ApiFunctions.isEmpty(getSamples())) {
      Iterator sampleIterator = getSamples().iterator();
      while (sampleIterator.hasNext()) {
        SampleData sample = (SampleData)sampleIterator.next();
        sampleIdToSample.put(sample.getSampleId(),sample);
      }
    }

    Iterator sampleIterator = getSampleIds().iterator();
    boolean addComma = false;
    while (sampleIterator.hasNext()) {
      if (addComma) {
        sb.append(", ");
      }
      addComma = true;
      String sampleId = (String) sampleIterator.next(); 
      SampleData sample = (SampleData)sampleIdToSample.get(sampleId);
      String linkText = null;
      if (sample == null) {
        linkText = sampleId;
      }
      else {
        linkText = IltdsUtils.getSampleIdAndAlias(sample);
      }
      sb.append(IcpUtils.prepareLink(sampleId, linkText, securityInfo));        
    }    
    String note = getNote();
    if (!ApiFunctions.isEmpty(note)) {
      sb.append("<br>");
      sb.append("Comment: ");
      Escaper.htmlEscapeAndPreserveWhitespace(note,sb);
    }
    return sb.toString();
  }

  /**
   * @see com.ardais.bigr.iltds.btx.BTXDetails#describeIntoHistoryRecord(com.ardais.bigr.iltds.btx.BTXHistoryRecord)
   */
  public void describeIntoHistoryRecord(BTXHistoryRecord history) {
    super.describeIntoHistoryRecord(history);
    history.setAttrib1(getAction());
    history.setAttrib2(getInventoryGroup());
    history.setAttrib3(getInventoryGroupName());
    history.setAttrib4(getNote());
    history.setIdList1(new IdList(getSampleIds()));
    history.setHistoryObject(describeAsHistoryObject());
  }
  
  /**
   * Returns a BtxHistoryAttributes that describes the  samples involved
   * in the box scan.  This method creates an attribute for each sample id, with a value
   * of a BtxHistoryAttributes object that contains various values for each sample.  For now
   * the only value that we store is the sample alias, but additional attributes may be added
   * as needed.
   */
  private Object describeAsHistoryObject() {
    BtxHistoryAttributes attributes = null;
    List samples = getSamples();
    if (!ApiFunctions.isEmpty(samples)) {
      attributes = new BtxHistoryAttributes();
      Iterator sampleIterator = samples.iterator();
      while (sampleIterator.hasNext()) {
        SampleData sample = (SampleData) sampleIterator.next();
        BtxHistoryAttributes sampleAttributes = new BtxHistoryAttributes();
        attributes.setAttribute(sample.getSampleId(), sampleAttributes);
        sampleAttributes.setAttribute("sampleAlias", sample.getSampleAlias());
      }
    }
    return attributes;
  }

  /**
   * @see com.ardais.bigr.iltds.btx.BTXDetails#populateFromHistoryRecord(com.ardais.bigr.iltds.btx.BTXHistoryRecord)
   */
  public void populateFromHistoryRecord(BTXHistoryRecord history) {
    super.populateFromHistoryRecord(history);
    setAction(history.getAttrib1());
    setInventoryGroup(history.getAttrib2());
    setInventoryGroupName(history.getAttrib3());
    setNote(history.getAttrib4());
    setSampleIds(history.getIdList1().getList());
    BtxHistoryAttributes attributes = (BtxHistoryAttributes)history.getHistoryObject();
    if (attributes != null) {
      List samples = new ArrayList();
      setSamples(samples);
      Map map = attributes.asMap();
      if (!map.isEmpty()) {
        Iterator sampleIdIterator = map.keySet().iterator();
        while (sampleIdIterator.hasNext()) {
          SampleData sample = new SampleData();
          sample.setSampleId((String)sampleIdIterator.next());
          populateSampleAttributesFromHistoryObject(attributes, sample);
          samples.add(sample);
        }
      }
    }
  }
  
  private void populateSampleAttributesFromHistoryObject(BtxHistoryAttributes attributes, SampleData sample) {
    if (attributes != null) {
      BtxHistoryAttributes sampleAttributes = (BtxHistoryAttributes)attributes.getAttribute(sample.getSampleId());
      if (sampleAttributes != null) {
        if (sampleAttributes.containsAttribute("sampleAlias")) {
          sample.setSampleAlias((String)sampleAttributes.getAttribute("sampleAlias"));
        }
      }
    }
  }

  /**
   * @return
   */
  public String getAction() {
    return _action;
  }

  /**
   * @return
   */
  public String getInventoryGroup() {
    return _inventoryGroup;
  }

  /**
   * @param string
   */
  public void setAction(String string) {
    _action = string;
  }

  /**
   * @param string
   */
  public void setInventoryGroup(String string) {
    _inventoryGroup = string;
  }


  /**
   * @return
   */
  public List getSampleIds() {
    return _sampleIds;
  }

  /**
   * @param list
   */
  public void setSampleIds(List list) {
    _sampleIds = list;
  }

  /**
   * @return
   */
  public String getNote() {
    return _note;
  }

  /**
   * @param string
   */
  public void setNote(String string) {
    _note = string;
  }

  /**
   * @return
   */
  public LegalValueSet getInventoryGroupChoices() {
    return _inventoryGroupChoices;
  }

  /**
   * @param set
   */
  public void setInventoryGroupChoices(LegalValueSet set) {
    _inventoryGroupChoices = set;
  }

  /**
   * @return
   */
  public String getInventoryGroupName() {
    return _inventoryGroupName;
  }

  /**
   * @param string
   */
  public void setInventoryGroupName(String string) {
    _inventoryGroupName = string;
  }

  /**
   * @return Returns the samples.  Note that this is an output parameter, used to capture
   * additional information about the samples specified via the sampleIds list.
   */
  public List getSamples() {
    return _samples;
  }
  
  /**
   * @param samples The samples to set.  Note that this is an output parameter, used to capture
   * additional information about the samples specified via the sampleIds list.
   */
  public void setSamples(List samples) {
    _samples = samples;
  }
}
