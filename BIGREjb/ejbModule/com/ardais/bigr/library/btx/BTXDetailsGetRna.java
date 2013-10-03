package com.ardais.bigr.library.btx;

import java.util.Set;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.iltds.btx.BTXDetails;
import com.ardais.bigr.javabeans.ProductDto;

import java.util.List;

/**
 * @author dfeldman
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 * 
 * @deprecated.  Use BTXDetailsGetSamples for all samples, including RNA and Tissue
 */
public class BTXDetailsGetRna extends BTXDetails {
    
    private String[] ids;
    private List sampSelectDataList;
  
    public BTXDetailsGetRna() {} // null-arg constructor for breakpoint on construction only
    
	/**
	 * @see com.ardais.bigr.iltds.btx.BTXDetails#getBTXType()
	 */
	public String getBTXType() {
		return BTXDetails.BTX_TYPE_GET_RNA;
	}

	/**
	 * @see com.ardais.bigr.iltds.btx.BTXDetails#getDirectlyInvolvedObjects()
	 */
	public Set getDirectlyInvolvedObjects() {
		return null;
	}

	/**
	 * @see com.ardais.bigr.iltds.btx.BTXDetails#doGetDetailsAsHTML()
	 */
	protected String doGetDetailsAsHTML() {
    String msg = "BTXDetailsGetRna.doGetDetailsAsHTML() method called in error!";
    return msg;
	}
    
    /**
     * Returns the sampleIds.
     * @return String[]
     */
    public String[] getIds() {
    	return ids;
    }
    
    /**
     * Sets the sampleIds.
     * @param sampleIds The sampleIds to set
     */
    public void setIds(String[] ids) {
    	this.ids = ids;
    }
    
    /**
     * Returns the sampleDetailsResult.
     * @return List
     */
    public List getRnaDetailsResult() {
    	return sampSelectDataList;
    }
    
    /**
     * Sets the sampleDetailsResult.
     * @param sampleDetailsResult The sampleDetailsResult to set
     */
    public void setRnaDetailsResult(List sampleDetailsResult) {
    	this.sampSelectDataList = sampleDetailsResult;
    }
  /**
   *  Set the array of vial IDs to a singleton array with this id
   */
  
  
  // =========   Single RNA usage ============================
  // -- this is convenient when using introspection to set the rnaVialId
  
  public void setRnaVialId(String id) {
    setIds(new String[] { id });
  }

  /**
   * Get the vial ID.  assumes this object is configured with one (single) id.
   */
  public String getRnaVialId() {
    return getIds()[0];
  }
  
  public ProductDto getSingleData() {
    List res = getRnaDetailsResult();
    if (res.isEmpty())
      throw new ApiException("No matching RNA sample could be found");
    return (ProductDto) res.get(0);
  }
}
