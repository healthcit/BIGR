package com.ardais.bigr.iltds.beans;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.FinderException;
import javax.ejb.RemoveException;
import javax.naming.NamingException;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.api.CMPDefaultValues;
import com.ardais.bigr.iltds.helpers.FormLogic;
import com.ibm.ivj.ejb.associations.interfaces.Link;
import com.ibm.ivj.ejb.associations.interfaces.SingleLink;
import com.ibm.ivj.ejb.runtime.AccessBeanHashtable;
/**
 * This is an Entity Bean class with CMP fields
 */
public class SamplestatusBean implements EntityBean {
	public static final java.lang.String DEFAULT_sample_sample_barcode_id = null;
	public static final String DEFAULT_status_type_code = null;
	public static final java.sql.Timestamp DEFAULT_sample_status_datetime = null;
	public static final java.math.BigDecimal DEFAULT_id = null;
  
	public java.lang.String sample_sample_barcode_id;
	public java.sql.Timestamp sample_status_datetime;
  public String status_type_code;

	private javax.ejb.EntityContext entityContext = null;
  private javax.ejb.EntityContext myEntityCtx;
  
	final static long serialVersionUID = 3206093459760846163L;
  
	private transient com.ibm.ivj.ejb.associations.interfaces.SingleLink sampleLink =
		null;
    
	private static final com.ardais.bigr.api.CMPDefaultValues _fieldDefaultValues =
		new com.ardais.bigr.api.CMPDefaultValues(
			com.ardais.bigr.iltds.beans.SamplestatusBean.class);

	/**
	 * Implementation field for persistent attribute: id
	 */
	public java.math.BigDecimal id;
	/**
	 * This method was generated for supporting the associations.
	 */
	protected java.util.Vector _getLinks() {
		java.util.Vector links = new java.util.Vector();
		links.add(getSampleLink());
		return links;
	}
	/**
	 * This method was generated for supporting the associations.
	 */
	protected void _initLinks() {
		sampleLink = null;
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
	 * ejbActivate
	 */
	public void ejbActivate() throws java.rmi.RemoteException {
		_initLinks();
	}
	/**
	 * ejbCreate method for a CMP entity bean
	 * @param argStatus_type_code java.lang.String
	 * @param argSample com.ardais.bigr.iltds.beans.SampleKey
	 * @exception javax.ejb.CreateException The exception description.
	 */
	public SamplestatusKey ejbCreate(
		java.lang.String argStatus_type_code,
		com.ardais.bigr.iltds.beans.SampleKey argSample,
		java.sql.Timestamp argSample_status_datetime)
		throws javax.ejb.CreateException, javax.ejb.EJBException {
		_fieldDefaultValues.assignTo(this);
		_initLinks();
		// All CMP fields should be initialized here.
        
        //Call to SequenceGenAccessbean modified to avoid two phase commit
        //problem. 
        //It is decided to call SequenceGenBean call directly instead 
        //of using EJB framework. See MR 5224 for more details.
		SequenceGenBean seq = new SequenceGenBean();
		java.math.BigDecimal _newId = null;
		try {
			String temp =
				seq.getSeqNextVal(
					com.ardais.bigr.iltds.helpers.FormLogic.ILTDS_SAMPLE_STATUS_PK_SEQ);
			_newId = new java.math.BigDecimal(temp);
		} catch (Exception ex) {
			throw new ApiException(
				"Error retrieving sequence generated Id for sample status.",
				ex);
		} 
		id = _newId;
		status_type_code = argStatus_type_code;
		sample_status_datetime = argSample_status_datetime;
		boolean sample_NULLTEST = (argSample == null);
		if (sample_NULLTEST)
			sample_sample_barcode_id = null;
		else
			sample_sample_barcode_id = argSample.sample_barcode_id;
		return null;
	}
	/**
	 * ejbCreate method for a CMP entity bean
	 * @param argStatus_type_code java.lang.String
	 * @param argSample com.ardais.bigr.iltds.beans.SampleKey
	 * @param argSample_status_datetime java.sql.Timestamp
	 * @param argId java.lang.Integer
	 * @exception javax.ejb.CreateException The exception description.
	 */
	public SamplestatusKey ejbCreate(
		java.lang.String argStatus_type_code,
		SampleKey argSample,
		java.sql.Timestamp argSample_status_datetime,
		java.math.BigDecimal argId)
		throws javax.ejb.CreateException, javax.ejb.EJBException {
		_fieldDefaultValues.assignTo(this);
		_initLinks();
		// All CMP fields should be initialized here.
		status_type_code = argStatus_type_code;
		boolean sample_NULLTEST = (argSample == null);
		if (sample_NULLTEST)
			sample_sample_barcode_id = null;
		else
			sample_sample_barcode_id = argSample.sample_barcode_id;
		sample_status_datetime = argSample_status_datetime;
		id = argId;
		return null;
	}
	/**
	 * ejbLoad
	 */
	public void ejbLoad() throws java.rmi.RemoteException {
		_initLinks();
	}
	/**
	 * ejbPassivate
	 */
	public void ejbPassivate() throws java.rmi.RemoteException {
	}
	/**
	 * ejbPostCreate method for a CMP entity bean
	 * @param argStatus_type_code java.lang.String
	 * @param argSample com.ardais.bigr.iltds.beans.SampleKey
	 */
	public void ejbPostCreate(
		java.lang.String argStatus_type_code,
		com.ardais.bigr.iltds.beans.SampleKey argSample,
		java.sql.Timestamp argSample_status_datetime)
		throws javax.ejb.EJBException {
	}
	/**
	 * ejbPostCreate method for a CMP entity bean
	 * @param argStatus_type_code java.lang.String
	 * @param argSample com.ardais.bigr.iltds.beans.SampleKey
	 * @param argSample_status_datetime java.sql.Timestamp
	 * @param argId java.lang.Integer
	 */
	public void ejbPostCreate(
		java.lang.String argStatus_type_code,
		SampleKey argSample,
		java.sql.Timestamp argSample_status_datetime,
		java.math.BigDecimal argId)
		throws javax.ejb.EJBException {
	}
	/**
	 * ejbRemove
	 */
	public void ejbRemove()
		throws javax.ejb.RemoveException, java.rmi.RemoteException {
		try {
			_removeLinks();
		} catch (java.rmi.RemoteException e) {
			throw new javax.ejb.RemoveException(e.getMessage());
		}
	}
	/**
	 * ejbStore
	 */
	public void ejbStore() throws java.rmi.RemoteException {
	}
	/**
	 * getEntityContext
	 */
	public javax.ejb.EntityContext getEntityContext() {
		return myEntityCtx;
	}
	/**
	 * This method was generated for supporting the association named Samplestatus REFILTDS_SAMPLE25 Sample.  
	 * 	It will be deleted/edited when the association is deleted/edited.
	 * @return com.ardais.bigr.iltds.beans.Sample
	 * @exception javax.ejb.FinderException The exception description.
	 */
	/* WARNING: THIS METHOD WILL BE REGENERATED. */
	public com.ardais.bigr.iltds.beans.Sample getSample()
		throws java.rmi.RemoteException, javax.ejb.FinderException {
		return (com.ardais.bigr.iltds.beans.Sample) this.getSampleLink().value();
	}
	/**
	 * Getter method for sample_status_datetime
	 * @return java.sql.Timestamp
	 */
	public java.sql.Timestamp getSample_status_datetime() {
		return sample_status_datetime;
	}
	/**
	 * This method was generated for supporting the association named Samplestatus REFILTDS_SAMPLE25 Sample.  
	 * 	It will be deleted/edited when the association is deleted/edited.
	 * @return com.ardais.bigr.iltds.beans.SampleKey
	 */
	/* WARNING: THIS METHOD WILL BE REGENERATED. */
	public com.ardais.bigr.iltds.beans.SampleKey getSampleKey() {
		com.ardais.bigr.iltds.beans.SampleKey temp = null;
		temp = new com.ardais.bigr.iltds.beans.SampleKey();
		boolean sample_NULLTEST = true;
		sample_NULLTEST &= (sample_sample_barcode_id == null);
		temp.sample_barcode_id = sample_sample_barcode_id;
		if (sample_NULLTEST)
			temp = null;
		return temp;
	}
	/**
	 * This method was generated for supporting the association named Samplestatus REFILTDS_SAMPLE25 Sample.  
	 * 	It will be deleted/edited when the association is deleted/edited.
	 * @return com.ibm.ivj.ejb.associations.interfaces.SingleLink
	 */
	/* WARNING: THIS METHOD WILL BE REGENERATED. */
	protected com.ibm.ivj.ejb.associations.interfaces.SingleLink getSampleLink() {
		if (sampleLink == null)
			sampleLink = new SamplestatusToSampleLink(this);
		return sampleLink;
	}
	/**
	 * Getter method for status_type_code
	 */
	public java.lang.String getStatus_type_code() {
		return status_type_code;
	}
	/**
	 * This method was generated for supporting the association named Samplestatus REFILTDS_SAMPLE25 Sample.  
	 * 	It will be deleted/edited when the association is deleted/edited.
	 * @param inKey com.ardais.bigr.iltds.beans.SampleKey
	 */
	/* WARNING: THIS METHOD WILL BE REGENERATED. */
	public void privateSetSampleKey(com.ardais.bigr.iltds.beans.SampleKey inKey) {
		boolean sample_NULLTEST = (inKey == null);
		if (sample_NULLTEST)
			sample_sample_barcode_id = null;
		else
			sample_sample_barcode_id = inKey.sample_barcode_id;
	}
	/**
	 * This method was generated for supporting the association named Samplestatus REFILTDS_SAMPLE25 Sample.  
	 * 	It will be deleted/edited when the association is deleted/edited.
	 */
	/* WARNING: THIS METHOD WILL BE REGENERATED. */
	public void secondarySetSample(com.ardais.bigr.iltds.beans.Sample aSample)
		throws java.rmi.RemoteException {
		this.getSampleLink().secondarySet(aSample);
	}
	/**
	 * setEntityContext
	 */
	public void setEntityContext(javax.ejb.EntityContext ctx)
		throws java.rmi.RemoteException {
		myEntityCtx = ctx;
	}
	/**
	 * This method was generated for supporting the association named Samplestatus REFILTDS_SAMPLE25 Sample.  
	 * 	It will be deleted/edited when the association is deleted/edited.
	 * @param aSample com.ardais.bigr.iltds.beans.Sample
	 */
	/* WARNING: THIS METHOD WILL BE REGENERATED. */
	public void setSample(com.ardais.bigr.iltds.beans.Sample aSample)
		throws java.rmi.RemoteException {
		this.getSampleLink().set(aSample);
	}
	/**
	 * Setter method for sample_status_datetime
	 * @param newValue java.sql.Timestamp
	 */
	public void setSample_status_datetime(java.sql.Timestamp newValue) {
		this.sample_status_datetime = newValue;
	}
	/**
	 * Setter method for status_type_code
	 */
	public void setStatus_type_code(java.lang.String newValue) {
		this.status_type_code = newValue;
	}
	/**
	 * unsetEntityContext
	 */
	public void unsetEntityContext() throws java.rmi.RemoteException {
		myEntityCtx = null;
	}
	/**
	 * ejbCreate method for a CMP entity bean.
	 */
	public com.ardais.bigr.iltds.beans.SamplestatusKey ejbCreate(
		java.math.BigDecimal id,
		com.ardais.bigr.iltds.beans.Sample argSample)
		throws javax.ejb.CreateException {
    _fieldDefaultValues.assignTo(this);
		_initLinks();
		this.id = id;
		try {
			setSample(argSample);
		} catch (java.rmi.RemoteException remoteEx) {
			throw new javax.ejb.CreateException(remoteEx.getMessage());
		}
		return null;
	}
	/**
	 * ejbPostCreate
	 */
	public void ejbPostCreate(
		java.math.BigDecimal id,
		com.ardais.bigr.iltds.beans.Sample argSample)
		throws javax.ejb.CreateException {
		try {
			setSample(argSample);
		} catch (java.rmi.RemoteException remoteEx) {
			throw new javax.ejb.CreateException(remoteEx.getMessage());
		}
	}
  /**
   * _copyFromEJB
   */
  public java.util.Hashtable _copyFromEJB() {
    com.ibm.ivj.ejb.runtime.AccessBeanHashtable h = new com.ibm.ivj.ejb.runtime.AccessBeanHashtable();

    h.put("status_type_code", getStatus_type_code());
    h.put("sampleKey", getSampleKey());
    h.put("sample_status_datetime", getSample_status_datetime());
    h.put("__Key", getEntityContext().getPrimaryKey());

    return h;
  }
  /**
   * _copyToEJB
   */
  public void _copyToEJB(java.util.Hashtable h) {
    java.lang.String localStatus_type_code = (java.lang.String) h.get("status_type_code");
    java.sql.Timestamp localSample_status_datetime = (java.sql.Timestamp) h
      .get("sample_status_datetime");

    if (h.containsKey("status_type_code"))
      setStatus_type_code((localStatus_type_code));
    if (h.containsKey("sample_status_datetime"))
      setSample_status_datetime((localSample_status_datetime));
  }
}