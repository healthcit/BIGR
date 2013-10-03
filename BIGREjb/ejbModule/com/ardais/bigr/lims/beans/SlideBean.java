package com.ardais.bigr.lims.beans;

import com.ardais.bigr.lims.helpers.LimsConstants;

/**
 * Bean implementation class for Enterprise Bean: Slide
 */
public class SlideBean implements javax.ejb.EntityBean {
  
  public static final String DEFAULT_slideId = null;
  public static final java.sql.Timestamp DEFAULT_createDate = null;
  public static final java.sql.Timestamp DEFAULT_destroyDate = null;
  public static final int DEFAULT_sectionNumber = 1;  
  public static final int DEFAULT_sectionLevel = 1; 
  public static final String DEFAULT_currentLocation = LimsConstants.LIMS_LOCATION_HISTOLOGY;  
  public static final String DEFAULT_sampleBarcodeId = null;  
  public static final String DEFAULT_createUser = null;
  public static final String DEFAULT_sectionProc = "H&E";

	private javax.ejb.EntityContext myEntityCtx;
	/**
	 * Implementation field for persistent attribute: slideId
	 */
	public java.lang.String slideId;
	/**
	 * Implementation field for persistent attribute: createDate
	 */
	public java.sql.Timestamp createDate;
	/**
	 * Implementation field for persistent attribute: destroyDate
	 */
	public java.sql.Timestamp destroyDate;
	/**
	 * Implementation field for persistent attribute: sectionNumber
	 */
	public int sectionNumber;
	/**
	 * Implementation field for persistent attribute: sectionLevel
	 */
	public int sectionLevel;
	/**
	 * Implementation field for persistent attribute: currentLocation
	 */
	public java.lang.String currentLocation;
	/**
	 * Implementation field for persistent attribute: sampleBarcodeId
	 */
	public java.lang.String sampleBarcodeId;
	/**
	 * Implementation field for persistent attribute: createUser
	 */
	public java.lang.String createUser;
	/**
	 * Implementation field for persistent attribute: sectionProc
	 */
	public java.lang.String sectionProc;
	  
  private static final com.ardais.bigr.api.CMPDefaultValues _fieldDefaultValues =
    new com.ardais.bigr.api.CMPDefaultValues(
      com.ardais.bigr.lims.beans.SlideBean.class);

/**
	 * getEntityContext
	 */
	public javax.ejb.EntityContext getEntityContext() {
		return myEntityCtx;
	}
	/**
	 * setEntityContext
	 */
	public void setEntityContext(javax.ejb.EntityContext ctx) {
		myEntityCtx = ctx;
	}
	/**
	 * unsetEntityContext
	 */
	public void unsetEntityContext() {
		myEntityCtx = null;
	}
	/**
	 * ejbActivate
	 */
	public void ejbActivate() {
		_initLinks();
	}
	/**
	 * ejbCreate method for a CMP entity bean.
	 */
	public java.lang.String ejbCreate(java.lang.String slideId)
		throws javax.ejb.CreateException {
    _fieldDefaultValues.assignTo(this);
		_initLinks();
		this.slideId = slideId;
		return null;
	}
	/**
	 * ejbCreate method for a CMP entity bean.
	 */
	public java.lang.String ejbCreate(java.lang.String slideId, java.sql.Timestamp createDate,
									   java.lang.String sampleBarcodeId, java.lang.String createUser)
		throws javax.ejb.CreateException {
    _fieldDefaultValues.assignTo(this);
		_initLinks();
		this.slideId = slideId;
		this.createDate = createDate;
		this.sampleBarcodeId = sampleBarcodeId;
		this.createUser = createUser;
		return null;
	}
	/**
	 * ejbLoad
	 */
	public void ejbLoad() {
		_initLinks();
	}
	/**
	 * ejbPassivate
	 */
	public void ejbPassivate() {
	}
	/**
	 * ejbPostCreate
	 */
	public void ejbPostCreate(java.lang.String slideId)
		throws javax.ejb.CreateException {
	}
	/**
	 * ejbPostCreate
	 */
	public void ejbPostCreate(java.lang.String slideId, java.sql.Timestamp createDate,
							   java.lang.String sampleBarcodeId, java.lang.String createUser)
		throws javax.ejb.CreateException {
	}
	/**
	 * ejbRemove
	 */
	public void ejbRemove() throws javax.ejb.RemoveException {
		try {
			_removeLinks();
		} catch (java.rmi.RemoteException e) {
			throw new javax.ejb.RemoveException(e.getMessage());
		}
	}
	/**
	 * ejbStore
	 */
	public void ejbStore() {
	}
	/**
	 * This method was generated for supporting the associations.
	 */
	protected void _initLinks() {
	}
	/**
	 * This method was generated for supporting the associations.
	 */
	protected java.util.Vector _getLinks() {
		java.util.Vector links = new java.util.Vector();
		return links;
	}
	/**
	 * This method was generated for supporting the associations.
	 */
	protected void _removeLinks()
		throws java.rmi.RemoteException, javax.ejb.RemoveException {
		java.util.List links = _getLinks();
		for (int i = 0; i < links.size(); i++) {
			try {
				((com.ibm.ivj.ejb.associations.interfaces.Link) links.get(i))
					.remove();
			} catch (javax.ejb.FinderException e) {
			} //Consume Finder error since I am going away
		}
	}
	/**
	 * Get accessor for persistent attribute: createDate
	 */
	public java.sql.Timestamp getCreateDate() {
		return createDate;
	}
	/**
	 * Set accessor for persistent attribute: createDate
	 */
	public void setCreateDate(java.sql.Timestamp newCreateDate) {
		createDate = newCreateDate;
	}
	/**
	 * Get accessor for persistent attribute: destroyDate
	 */
	public java.sql.Timestamp getDestroyDate() {
		return destroyDate;
	}
	/**
	 * Set accessor for persistent attribute: destroyDate
	 */
	public void setDestroyDate(java.sql.Timestamp newDestroyDate) {
		destroyDate = newDestroyDate;
	}
	/**
	 * Get accessor for persistent attribute: sectionNumber
	 */
	public int getSectionNumber() {
		return sectionNumber;
	}
	/**
	 * Set accessor for persistent attribute: sectionNumber
	 */
	public void setSectionNumber(int newSectionNumber) {
		sectionNumber = newSectionNumber;
	}
	/**
	 * Get accessor for persistent attribute: sectionLevel
	 */
	public int getSectionLevel() {
		return sectionLevel;
	}
	/**
	 * Set accessor for persistent attribute: sectionLevel
	 */
	public void setSectionLevel(int newSectionLevel) {
		sectionLevel = newSectionLevel;
	}
	/**
	 * Get accessor for persistent attribute: currentLocation
	 */
	public java.lang.String getCurrentLocation() {
		return currentLocation;
	}
	/**
	 * Set accessor for persistent attribute: currentLocation
	 */
	public void setCurrentLocation(java.lang.String newCurrentLocation) {
		currentLocation = newCurrentLocation;
	}
	/**
	 * Get accessor for persistent attribute: sampleBarcodeId
	 */
	public java.lang.String getSampleBarcodeId() {
		return sampleBarcodeId;
	}
	/**
	 * Set accessor for persistent attribute: sampleBarcodeId
	 */
	public void setSampleBarcodeId(java.lang.String newSampleBarcodeId) {
		sampleBarcodeId = newSampleBarcodeId;
	}
	/**
	 * Get accessor for persistent attribute: createUser
	 */
	public java.lang.String getCreateUser() {
		return createUser;
	}
	/**
	 * Set accessor for persistent attribute: createUser
	 */
	public void setCreateUser(java.lang.String newCreateUser) {
		createUser = newCreateUser;
	}
	/**
	 * Get accessor for persistent attribute: sectionProc
	 */
	public java.lang.String getSectionProc() {
		return sectionProc;
	}
	/**
	 * Set accessor for persistent attribute: sectionProc
	 */
	public void setSectionProc(java.lang.String newSectionProc) {
		sectionProc = newSectionProc;
	}
  /**
   * _copyFromEJB
   */
  public java.util.Hashtable _copyFromEJB() {
    com.ibm.ivj.ejb.runtime.AccessBeanHashtable h = new com.ibm.ivj.ejb.runtime.AccessBeanHashtable();

    h.put("sectionLevel", new Integer(getSectionLevel()));
    h.put("destroyDate", getDestroyDate());
    h.put("sampleBarcodeId", getSampleBarcodeId());
    h.put("currentLocation", getCurrentLocation());
    h.put("sectionNumber", new Integer(getSectionNumber()));
    h.put("createDate", getCreateDate());
    h.put("sectionProc", getSectionProc());
    h.put("createUser", getCreateUser());
    h.put("__Key", getEntityContext().getPrimaryKey());

    return h;
  }
  /**
   * _copyToEJB
   */
  public void _copyToEJB(java.util.Hashtable h) {
    Integer localSectionLevel = (Integer) h.get("sectionLevel");
    java.sql.Timestamp localDestroyDate = (java.sql.Timestamp) h.get("destroyDate");
    java.lang.String localSampleBarcodeId = (java.lang.String) h.get("sampleBarcodeId");
    java.lang.String localCurrentLocation = (java.lang.String) h.get("currentLocation");
    Integer localSectionNumber = (Integer) h.get("sectionNumber");
    java.sql.Timestamp localCreateDate = (java.sql.Timestamp) h.get("createDate");
    java.lang.String localSectionProc = (java.lang.String) h.get("sectionProc");
    java.lang.String localCreateUser = (java.lang.String) h.get("createUser");

    if (h.containsKey("sectionLevel"))
      setSectionLevel((localSectionLevel).intValue());
    if (h.containsKey("destroyDate"))
      setDestroyDate((localDestroyDate));
    if (h.containsKey("sampleBarcodeId"))
      setSampleBarcodeId((localSampleBarcodeId));
    if (h.containsKey("currentLocation"))
      setCurrentLocation((localCurrentLocation));
    if (h.containsKey("sectionNumber"))
      setSectionNumber((localSectionNumber).intValue());
    if (h.containsKey("createDate"))
      setCreateDate((localCreateDate));
    if (h.containsKey("sectionProc"))
      setSectionProc((localSectionProc));
    if (h.containsKey("createUser"))
      setCreateUser((localCreateUser));
  }
}
