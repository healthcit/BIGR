package com.ardais.bigr.library.btx;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.api.Escaper;
import com.ardais.bigr.btx.framework.BtxHistoryAttributes;
import com.ardais.bigr.iltds.btx.BTXDetails;
import com.ardais.bigr.iltds.btx.BTXHistoryRecord;
import com.ardais.bigr.iltds.helpers.IltdsUtils;
import com.ardais.bigr.iltds.helpers.ProductType;
import com.ardais.bigr.javabeans.IdList;
import com.ardais.bigr.javabeans.ProductDto;
import com.ardais.bigr.javabeans.RequestItemDto;
import com.ardais.bigr.javabeans.SampleData;
import com.ardais.bigr.security.SecurityInfo;
import com.ardais.bigr.util.IcpUtils;

/**
 * @author JThompson
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class BTXDetailsRemoveFromHoldList extends BTXDetails {
    private String[] _samples;  //sample ids
    private String _holdListOwner;
    private List _sampleDtos;  //SampleData objects to allow additional info to be logged

    /**
     * @see com.ardais.bigr.iltds.btx.BTXDetails#getBTXType()
     */
    public String getBTXType() {
        return BTXDetails.BTX_TYPE_REMOVE_FROM_HOLD_LIST;
    }

    /**
     * @see com.ardais.bigr.iltds.btx.BTXDetails#getDirectlyInvolvedObjects()
     */
    public Set getDirectlyInvolvedObjects() {
        HashSet objs = new HashSet();
        objs.addAll(Arrays.asList(_samples));
        return objs;
    }

    /**
     * @see com.ardais.bigr.iltds.btx.BTXDetails#doGetDetailsAsHTML()
     */
    protected String doGetDetailsAsHTML() {
        StringBuffer sb = new StringBuffer(512);
       
        SecurityInfo securityInfo = getLoggedInUserSecurityInfo();
        
        Map sampleIdToSampleMap = new HashMap();
        if (!ApiFunctions.isEmpty(getSampleDtos())) {
          Iterator sampleIterator = getSampleDtos().iterator();
          while (sampleIterator.hasNext()) {
            SampleData sample = (SampleData)sampleIterator.next();
            sampleIdToSampleMap.put(sample.getSampleId(), sample);
          }
        }

        // If no holdListOwner value was passed in, the result has this form:
        //    <user> removed the following items from hold: <samples>
        // If a holdListOwner value was passed in, the result has this form:
        //    <user> removed the following items from hold for <owner>: <samples>

        sb.append("User ");
        Escaper.htmlEscape(getUserId(), sb);
        sb.append(" removed the following items from hold");
        String holdOwner = getHoldListOwner();
        if (!ApiFunctions.isEmpty(holdOwner)) { 
          sb.append(" for user ");
          Escaper.htmlEscape(holdOwner, sb);
        }
        sb.append(": ");
        for (int i = 0; i < _samples.length; i++) {
          String id = _samples[i];
          SampleData sample = (SampleData)sampleIdToSampleMap.get(id);
          StringBuffer linkText = new StringBuffer(50);
          if (sample != null) {
            linkText.append(IltdsUtils.getSampleIdAndAlias(sample));
          }
          else {
            linkText.append(id);
          }
          sb.append(IcpUtils.prepareLink(id, linkText.toString(), securityInfo));
          sb.append(' ');
        }
        return sb.toString();
    }

    /**
     * Returns the samples.
     * @return String[]
     */
    public String[] getSamples() {
        return _samples;
    }

  /**
   * @return
   */
  public String getHoldListOwner() {
    return _holdListOwner;
  }

    /**
     * @return Returns the sampleDtos.
     */
    public List getSampleDtos() {
      return _sampleDtos;
    }

    /**
     * Sets the samples.
     * @param samples The samples to set
     */
    public void setSamples(String[] samples) {
        _samples = samples;
    }

  /**
   * @param string
   */
  public void setHoldListOwner(String string) {
    _holdListOwner = string;
  }
  
  /**
   * @param sampleDtos The sampleDtos to set.
   */
  public void setSampleDtos(List sampleDtos) {
    _sampleDtos = sampleDtos;
  }

    /**
     * @see com.ardais.bigr.iltds.btx.BTXDetails#describeIntoHistoryRecord(BTXHistoryRecord)
     */
    public void describeIntoHistoryRecord(BTXHistoryRecord history) {
        super.describeIntoHistoryRecord(history);
        history.setIdList1(new IdList(Arrays.asList(_samples)));
        if (!ApiFunctions.isEmpty(getHoldListOwner())) {
          history.setAttrib1(getHoldListOwner());
        }
        history.setHistoryObject(describeAsHistoryObject());
    }
    
    /**
     * Returns a BtxHistoryAttributes that describes the samples involved
     * in the request.  This method creates an attribute for each sample id, with a value
     * of a BtxHistoryAttributes object that contains various values for each sample.  For now
     * the only value that we store is the sample alias, but additional attributes may be added
     * as needed.
     */
    private Object describeAsHistoryObject() {
      BtxHistoryAttributes attributes = null;
      if (!ApiFunctions.isEmpty(getSampleDtos())) {
        attributes = new BtxHistoryAttributes();
        Iterator sampleIterator = getSampleDtos().iterator();
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
     * @see com.ardais.bigr.iltds.btx.BTXDetails#populateFromHistoryRecord(BTXHistoryRecord)
     */
    public void populateFromHistoryRecord(BTXHistoryRecord history) {
        super.populateFromHistoryRecord(history);
        _samples = (String[]) history.getIdList1().getList().toArray(ApiFunctions.EMPTY_STRING_ARRAY);
        setHoldListOwner(history.getAttrib1());
        
        //if we stored information about the samples, repopulate them
        List sampleDtos = new ArrayList();
        setSampleDtos(sampleDtos);
        BtxHistoryAttributes attributes = (BtxHistoryAttributes)history.getHistoryObject();
        if (attributes != null) {
          Map map = attributes.asMap();
          if (!map.isEmpty()) {
            Iterator sampleIdIterator = map.keySet().iterator();
            while (sampleIdIterator.hasNext()) {
              String sampleId = (String)sampleIdIterator.next();
              SampleData sample = new SampleData();
              sample.setSampleId(sampleId);
              populateSampleAttributesFromHistoryObject(attributes, sample);
              sampleDtos.add(sample);
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

}
