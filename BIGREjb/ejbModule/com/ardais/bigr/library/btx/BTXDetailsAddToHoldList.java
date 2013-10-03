package com.ardais.bigr.library.btx;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.ardais.bigr.api.Escaper;
import com.ardais.bigr.iltds.btx.BTXDetails;
import com.ardais.bigr.iltds.btx.BTXHistoryRecord;
import com.ardais.bigr.javabeans.IdList;
import com.ardais.bigr.security.SecurityInfo;
import com.ardais.bigr.util.IcpUtils;

/**
 * @author JThompson
 *
 * setSamples is the input parameter
 * getUnavailableSamples is the output parameter
 * 
 * <p>
 * 
 * Behavior is to add all the items specified by id via setSamples() to the hold list
 * and return any items that could not be placed on hold, because they are unavailable.
 */
public class BTXDetailsAddToHoldList extends BTXDetails {
    private static final String isPickListDBFlag = "typePickList";
    private static final String notPickListDBFlag = "typeNotPickList";
    
    private Map _idsAndAmounts = new HashMap();
    private String[] _unavailSamples;
    private boolean _isPickList = false;

    public BTXDetailsAddToHoldList() {
    }
    
    /**
     * @see com.ardais.bigr.iltds.btx.BTXDetails#getBTXType()
     */
    public String getBTXType() {
        return BTXDetails.BTX_TYPE_ADD_TO_HOLD_LIST;
    }

    /**
     * @see com.ardais.bigr.iltds.btx.BTXDetails#getDirectlyInvolvedObjects()
     */
    public Set getDirectlyInvolvedObjects() {
        Set objs = new HashSet();
        objs.addAll(_idsAndAmounts.keySet());
        return objs;
    }

    /**
     * @see com.ardais.bigr.iltds.btx.BTXDetails#doGetDetailsAsHTML()
     */
    protected String doGetDetailsAsHTML() {
        StringBuffer sb = new StringBuffer(512);
        
        SecurityInfo securityInfo = getLoggedInUserSecurityInfo();

        // The result has this form:
        //    <user>@<account> placed the following on hold <samples>

        sb.append("User: "); 
        Escaper.htmlEscape(getUserId(), sb);
        sb.append(" attempted to place the following items on hold ");

        Iterator it = _idsAndAmounts.keySet().iterator();
        while (it.hasNext()) {
            String sid = (String) it.next();
            sb.append(IcpUtils.prepareLink(sid, securityInfo));
            sb.append(' ');
        }
        if (_unavailSamples != null && _unavailSamples.length > 0) {
            sb.append(" : The following samples were unavailable: ");
            for (int i = 0; i < _unavailSamples.length; i++) {
                sb.append(IcpUtils.prepareLink(_unavailSamples[i], securityInfo));
            sb.append(' ');
            }
        }
        return sb.toString();
    }

    /**
     * Returns the samples.
     * @return String[]
     */
    public String[] getSamples() {
        return (String[]) _idsAndAmounts.keySet()
                  .toArray(new String[0]);
    }

    /**
     * Returns the unavailSamples.
     * @return String[]
     */
    public String[] getUnavailSamples() {
        return _unavailSamples;
    }

    public void setIdsNoAmounts(String[] ids) {
      Map m = new HashMap();
      for (int i=0; i<ids.length; i++) {
        m.put(ids[i], null);
      }
      setIdsAndAmounts(m);
    }
    
    /**
     * Sets the sample ids and amounts on hold for each.
     * @param samples The samples to set with an Integer specifying the amoutn requested for hold
     */
    public void setIdsAndAmounts(Map idsAndAmts) {
        _idsAndAmounts = idsAndAmts;
    }
    /**
     * Get the sample ids and the Integer amount on hold for each.
     * @param samples The samples to set
     */
    public Map getIdsAndAmounts() {
        return _idsAndAmounts;
    }

    /**
     * Sets the unavailSamples.
     * @param unavailSamples The unavailSamples to set
     */
    public void setUnavailSamples(String[] unavailSamples) {
        _unavailSamples = unavailSamples;
    }

    /**
     * @see com.ardais.bigr.iltds.btx.BTXDetails#describeIntoHistoryRecord(BTXHistoryRecord)
     */
    public void describeIntoHistoryRecord(BTXHistoryRecord history) {
        super.describeIntoHistoryRecord(history);
        history.setIdList1(new IdList(new ArrayList(_idsAndAmounts.keySet())));
        if (_unavailSamples != null && _unavailSamples.length > 0) {
            history.setIdList2(new IdList(Arrays.asList(_unavailSamples)));
        }
        // @todo:  add amounts
    }

    /**
     * @see com.ardais.bigr.iltds.btx.BTXDetails#populateFromHistoryRecord(BTXHistoryRecord)
     */
    public void populateFromHistoryRecord(BTXHistoryRecord history) {
        super.populateFromHistoryRecord(history);
        _idsAndAmounts = new HashMap(); // clear any old dat (there should be none)
        List ids = history.getIdList1().getList();
                
        Iterator it = ids.iterator();
        while (it.hasNext()) {
          _idsAndAmounts.put(it.next(), null);
          // @todo:  add amounts
        }
        _unavailSamples = (String[]) history.getIdList2().getList().toArray(new String[] {
        });
    }

}
