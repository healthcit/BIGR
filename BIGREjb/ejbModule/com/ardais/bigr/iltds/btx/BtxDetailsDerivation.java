package com.ardais.bigr.iltds.btx;

import java.io.Serializable;
import java.sql.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.btx.framework.BtxHistoryAttributes;
import com.ardais.bigr.iltds.helpers.IltdsUtils;
import com.ardais.bigr.javabeans.DerivationDto;
import com.ardais.bigr.javabeans.IdList;
import com.ardais.bigr.javabeans.SampleData;
import com.ardais.bigr.security.SecurityInfo;
import com.ardais.bigr.util.ArtsConstants;
import com.ardais.bigr.util.IcpUtils;
import com.gulfstreambio.gboss.GbossFactory;

/**
 * Holds the details and logs a single derivation operation.
 * <p>
 * The transaction-specific fields are described below.  See also
 * {@link BTXDetails BTXDetails} for fields that are shared by all
 * business transactions.
 * 
 * <h4>Required input fields</h4>
 * <ul>
 * <li>{@link #setInputDto(DerivationDto) inputDto}: The DerivationDto containing all necessary 
 * input data for the derivation operation.
 * </li>
 * </ul>
 *
 * <h4>Optional input fields</h4>
 * <ul>
 * <li>None.</li>
 * </ul>
 *
 * <h4>Output fields</h4>
 * <ul>
 * <li>{@link #setOutputDto(DerivationDto) outputDto}: The DerivationDto containing all output 
 * data for the derivation operation.
 * </li>
 * </ul>
 */
public class BtxDetailsDerivation extends BTXDetails implements Serializable {

  private DerivationDto _dto;

  private BtxHistoryAttributes _attributes;

  public BtxDetailsDerivation() {
    super();
  }

  /* (non-Javadoc)
   * @see com.ardais.bigr.iltds.btx.BTXDetails#getBTXType()
   */
  public String getBTXType() {
    return BTXDetails.BTX_TYPE_DERIVATION;
  }

  /* (non-Javadoc)
   * @see com.ardais.bigr.iltds.btx.BTXDetails#getDirectlyInvolvedObjects()
   */
  public Set getDirectlyInvolvedObjects() {
    DerivationDto dto = getDto();
    Set set = new HashSet();

    set.add(dto.getDerivationId());

    Iterator i = dto.getParents().iterator();
    while (i.hasNext()) {
      SampleData sample = (SampleData) i.next(); 
      set.add(sample.getSampleId());
    }

    i = dto.getChildren().iterator();
    while (i.hasNext()) {
      SampleData sample = (SampleData) i.next(); 
      set.add(sample.getSampleId());
    }

    return set;
  }

  /* (non-Javadoc)
   * @see com.ardais.bigr.iltds.btx.BTXDetails#doGetDetailsAsHTML()
   */
  protected String doGetDetailsAsHTML() {
    // The HTML has the following form:
    // <operation description> operation <id> was performed on <date> by <user>.  Samples 
    // <children> were derived from samples <parents>.

    SecurityInfo securityInfo = getLoggedInUserSecurityInfo();
    StringBuffer sb = new StringBuffer(2048);

    DerivationDto dto = getDto();

    String operation = dto.getOperationCui();
    if (operation.equals(ArtsConstants.OTHER_DERIVATION_OPERATION)) {
      sb.append(dto.getOtherOperation());
    }
    else {
      sb.append(GbossFactory.getInstance().getDescription(operation));
    }
    sb.append(" operation ");    
    sb.append(IcpUtils.prepareLink(dto.getDerivationId(), securityInfo));
    sb.append(" was performed");
    Date operationDate = dto.getOperationDate();
    if (operationDate != null) {
      sb.append(" on ");
      sb.append(ApiFunctions.sqlDateToString(operationDate));
    }
    String preparedBy = dto.getPreparedBy();
    if (preparedBy != null) {
      sb.append(" by ");    
      sb.append(preparedBy);
    }
    sb.append(".");    

    List samples = dto.getChildren();
    boolean oneSample = (samples.size() == 1);
    if (oneSample) {
      sb.append(" Sample ");    
    }
    else {
      sb.append(" Samples ");    
    }
    for (int i = 0; i < samples.size(); i++) {
      SampleData sample = (SampleData) samples.get(i);
      if (i > 0) {
        sb.append(", ");
      }
      String linkText = IltdsUtils.getSampleIdAndAlias(sample);
      sb.append(IcpUtils.prepareLink(sample.getSampleId(), linkText, securityInfo));
    }

    if (oneSample) {
      sb.append(" was ");    
    }
    else {
      sb.append(" were ");    
    }
    sb.append("derived from ");    
    samples = dto.getParents();
    oneSample = (samples.size() == 1);
    if (oneSample) {
      sb.append(" sample ");    
    }
    else {
      sb.append(" samples ");    
    }
    for (int i = 0; i < samples.size(); i++) {
      SampleData sample = (SampleData) samples.get(i);
      if (i > 0) {
        sb.append(", ");
      }
      String linkText = IltdsUtils.getSampleIdAndAlias(sample);
      sb.append(IcpUtils.prepareLink(sample.getSampleId(), linkText, securityInfo));
    }
    sb.append(".");
    
    return sb.toString();
  }

  /* (non-Javadoc)
   * @see com.ardais.bigr.iltds.btx.BTXDetails#describeIntoHistoryRecord(com.ardais.bigr.iltds.btx.BTXHistoryRecord)
   */
  public void describeIntoHistoryRecord(BTXHistoryRecord history) {
    super.describeIntoHistoryRecord(history);

    DerivationDto dto = getDto();
    
    history.setAttrib1(dto.getDerivationId());
    history.setAttrib2(dto.getOperationCui());
    history.setAttrib3(dto.getOtherOperation());
    history.setAttrib4(ApiFunctions.sqlDateToString(dto.getOperationDate()));
    history.setAttrib5(dto.getPreparedByName());

    IdList itemIds = new IdList();
    Iterator i = dto.getParents().iterator();
    while (i.hasNext()) {
      SampleData sample = (SampleData) i.next(); 
      itemIds.add(sample.getSampleId());
    }
    history.setIdList1(itemIds);

    itemIds = new IdList();
    i = dto.getChildren().iterator();
    while (i.hasNext()) {
      SampleData sample = (SampleData) i.next(); 
      itemIds.add(sample.getSampleId());
    }
    history.setIdList2(itemIds);
    history.setHistoryObject(describeAsHistoryObject());

  }

  /* (non-Javadoc)
   * @see com.ardais.bigr.iltds.btx.BTXDetails#populateFromHistoryRecord(com.ardais.bigr.iltds.btx.BTXHistoryRecord)
   */
  public void populateFromHistoryRecord(BTXHistoryRecord history) {
    super.populateFromHistoryRecord(history);
    _attributes = (BtxHistoryAttributes)history.getHistoryObject();

    DerivationDto dto = new DerivationDto();
    dto.setDerivationId(history.getAttrib1());
    dto.setOperationCui(history.getAttrib2());
    dto.setOtherOperation(history.getAttrib3());
    dto.setOperationDate(ApiFunctions.safeDate(history.getAttrib4()));
    dto.setPreparedBy(history.getAttrib5());

    Iterator idIterator = history.getIdList1().iterator();
    while (idIterator.hasNext()) {
      SampleData sample = new SampleData();
      sample.setSampleId((String)idIterator.next());
      populateSampleAttributesFromHistoryObject(sample);
      dto.addParent(sample);
    }

    idIterator = history.getIdList2().iterator();
    while (idIterator.hasNext()) {
      SampleData sample = new SampleData();
      sample.setSampleId((String)idIterator.next());
      populateSampleAttributesFromHistoryObject(sample);
      dto.addChild(sample);
    }

    setDto(dto);
  }

  private void populateSampleAttributesFromHistoryObject(SampleData sample) {
    if (_attributes != null) {
      BtxHistoryAttributes sampleAttributes = (BtxHistoryAttributes)_attributes.getAttribute(sample.getSampleId());
      if (sampleAttributes != null) {
        if (sampleAttributes.containsAttribute("sampleAlias")) {
          sample.setSampleAlias((String)sampleAttributes.getAttribute("sampleAlias"));
        }
      }
    }
  }
  
  public DerivationDto getDto() {
    return _dto;
  }

  public void setDto(DerivationDto dto) {
    _dto = dto;
  }
  
  /**
   * Returns a BtxHistoryAttributes that describes the parent and child samples involved
   * in the derivation.  This method creates an attribute for each sample id, with a value
   * of a BtxHistoryAttributes object that contains various values for each sample.  For now
   * the only value that we store is the sample alias, but additional attributes may be added
   * as needed.
   */
  private Object describeAsHistoryObject() {
    BtxHistoryAttributes attributes = new BtxHistoryAttributes();
    DerivationDto dto = getDto();
    List parentSamples = dto.getParents();
    Iterator parentIterator = parentSamples.iterator();
    while (parentIterator.hasNext()) {
      SampleData parent = (SampleData) parentIterator.next();
      BtxHistoryAttributes sampleAttributes = new BtxHistoryAttributes();
      attributes.setAttribute(parent.getSampleId(), sampleAttributes);
      sampleAttributes.setAttribute("sampleAlias", parent.getSampleAlias());
    }
    List childSamples = dto.getChildren();
    Iterator childIterator = childSamples.iterator();
    while (childIterator.hasNext()) {
      SampleData child = (SampleData) childIterator.next();
      BtxHistoryAttributes sampleAttributes = new BtxHistoryAttributes();
      attributes.setAttribute(child.getSampleId(), sampleAttributes);
      sampleAttributes.setAttribute("sampleAlias", child.getSampleAlias());
    }
    return attributes;
  }

}
