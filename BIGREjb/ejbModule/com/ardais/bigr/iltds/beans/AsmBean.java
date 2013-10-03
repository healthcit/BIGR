package com.ardais.bigr.iltds.beans;

import javax.ejb.EJBException;
import javax.ejb.EntityBean;
/**
 * This is an Entity Bean class with CMP fields
 */
public class AsmBean implements EntityBean {
	public static final String DEFAULT_asm_id = null;
	public static final String DEFAULT_organ_site_concept_id = null;
	public static final String DEFAULT_specimen_type = null;
	public static final java.sql.Timestamp DEFAULT_asm_entry_date = null;
	public static final String DEFAULT_organ_site_concept_id_other = null;
	public static final String DEFAULT_asm_form_id = null;
	public static final String DEFAULT_consent_id = null;
	public static final String DEFAULT_ardais_id = null;
	public static final String DEFAULT_pfin_meets_specs = null;
	public static final Integer DEFAULT_module_weight = null;
	public static final String DEFAULT_module_comments = null;

	public String asm_id;
	public String organ_site_concept_id;
	public String specimen_type;
	public java.sql.Timestamp asm_entry_date;
	public java.lang.String organ_site_concept_id_other;

	private javax.ejb.EntityContext entityContext = null;
	final static long serialVersionUID = 3206093459760846163L;
	private transient com
		.ibm
		.ivj
		.ejb
		.associations
		.interfaces
		.ManyLink sampleLink =
		null;
	private static final com
		.ardais
		.bigr
		.api
		.CMPDefaultValues _fieldDefaultValues =
		new com.ardais.bigr.api.CMPDefaultValues(
			com.ardais.bigr.iltds.beans.AsmBean.class);
	/**
	 * Implementation field for persistent attribute: asm_form_id
	 */
	public java.lang.String asm_form_id;
	/**
	 * Implementation field for persistent attribute: consent_id
	 */
	public java.lang.String consent_id;
	/**
	 * Implementation field for persistent attribute: ardais_id
	 */
	public java.lang.String ardais_id;
	/**
	 * Implementation field for persistent attribute: pfin_meets_specs
	 */
	public java.lang.String pfin_meets_specs;
	/**
	 * Implementation field for persistent attribute: module_weight
	 */
	public java.lang.Integer module_weight;
	/**
	 * Implementation field for persistent attribute: module_comments
	 */
	public java.lang.String module_comments;
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
	 * This method was generated for supporting the association named Sample REFILTDS_ASM7 Asm.  
	 * 	It will be deleted/edited when the association is deleted/edited.
	 * @param aSample com.ardais.bigr.iltds.beans.Sample
	 */
	/* WARNING: THIS METHOD WILL BE REGENERATED. */
	public void addSample(com.ardais.bigr.iltds.beans.Sample aSample)
		throws java.rmi.RemoteException {
		this.getSampleLink().addElement(aSample);
	}
	/**
	 * ejbActivate method comment
	 */
	public void ejbActivate() throws java.rmi.RemoteException {
		_initLinks();
	}
	/**
	 * ejbCreate method for a CMP entity bean.
	 */
	public com.ardais.bigr.iltds.beans.AsmKey ejbCreate(
		java.lang.String asm_id)
		throws javax.ejb.CreateException, EJBException {
    _fieldDefaultValues.assignTo(this);
		_initLinks();
		this.asm_id = asm_id;
		return null;
	}


	/**
	 * ejbLoad method comment
	 */
	public void ejbLoad() throws java.rmi.RemoteException {
		_initLinks();
	}
	/**
	 * ejbPassivate method comment
	 */
	public void ejbPassivate() throws java.rmi.RemoteException {
	}
	/**
	 * ejbPostCreate
	 */
	public void ejbPostCreate(java.lang.String asm_id)
		throws javax.ejb.CreateException, EJBException {
	}

	/**
	 * ejbRemove method comment
	 */
	public void ejbRemove()
		throws java.rmi.RemoteException, javax.ejb.RemoveException {
		_removeLinks();
	}
	/**
	 * ejbStore method comment
	 */
	public void ejbStore() throws java.rmi.RemoteException {
	}
	/**
	 * Getter method for asm_entry_date
	 * @return java.sql.Timestamp
	 */
	public java.sql.Timestamp getAsm_entry_date() {
		return asm_entry_date;
	}
	/**
	 * getEntityContext method comment
	 * @return javax.ejb.EntityContext
	 */
	public javax.ejb.EntityContext getEntityContext() {
		return entityContext;
	}
	/**
	 * Getter method for organ_site_concept_id
	 * @return java.lang.String
	 */
	public java.lang.String getOrgan_site_concept_id() {
		return organ_site_concept_id;
	}
	/**
	 * Insert the method's description here.
	 * Creation date: (10/16/2001 1:02:05 PM)
	 * @return java.lang.String
	 */
	public java.lang.String getOrgan_site_concept_id_other() {
		return organ_site_concept_id_other;
	}
	/**
	 * This method was generated for supporting the association named Sample REFILTDS_ASM7 Asm.  
	 * 	It will be deleted/edited when the association is deleted/edited.
	 * @return java.util.Enumeration
	 * @exception javax.ejb.FinderException The exception description.
	 */
	/* WARNING: THIS METHOD WILL BE REGENERATED. */
	public java.util.Enumeration getSample()
		throws java.rmi.RemoteException, javax.ejb.FinderException {
		return this.getSampleLink().enumerationValue();
	}
	/**
	 * This method was generated for supporting the association named Sample REFILTDS_ASM7 Asm.  
	 * 	It will be deleted/edited when the association is deleted/edited.
	 * @return com.ibm.ivj.ejb.associations.interfaces.ManyLink
	 */
	/* WARNING: THIS METHOD WILL BE REGENERATED. */
	protected com
		.ibm
		.ivj
		.ejb
		.associations
		.interfaces
		.ManyLink getSampleLink() {
		if (sampleLink == null)
			sampleLink = new AsmToSampleLink(this);
		return sampleLink;
	}
	/**
	 * Getter method for specimen_type
	 * @return java.lang.String
	 */
	public java.lang.String getSpecimen_type() {
		return specimen_type;
	}
	/**
	 * This method was generated for supporting the association named Sample REFILTDS_ASM7 Asm.  
	 * 	It will be deleted/edited when the association is deleted/edited.
	 * @param aSample com.ardais.bigr.iltds.beans.Sample
	 */
	/* WARNING: THIS METHOD WILL BE REGENERATED. */
	public void removeSample(com.ardais.bigr.iltds.beans.Sample aSample)
		throws java.rmi.RemoteException {
		this.getSampleLink().removeElement(aSample);
	}
	/**
	 * This method was generated for supporting the association named Sample REFILTDS_ASM7 Asm.  
	 * 	It will be deleted/edited when the association is deleted/edited.
	 * @param aSample com.ardais.bigr.iltds.beans.Sample
	 */
	/* WARNING: THIS METHOD WILL BE REGENERATED. */
	public void secondaryAddSample(
		com.ardais.bigr.iltds.beans.Sample aSample) {
		this.getSampleLink().secondaryAddElement(aSample);
	}
	/**
	 * This method was generated for supporting the association named Sample REFILTDS_ASM7 Asm.  
	 * 	It will be deleted/edited when the association is deleted/edited.
	 * @param aSample com.ardais.bigr.iltds.beans.Sample
	 */
	/* WARNING: THIS METHOD WILL BE REGENERATED. */
	public void secondaryRemoveSample(
		com.ardais.bigr.iltds.beans.Sample aSample) {
		this.getSampleLink().secondaryRemoveElement(aSample);
	}
	/**
	 * Setter method for asm_entry_date
	 * @param newValue java.sql.Timestamp
	 */
	public void setAsm_entry_date(java.sql.Timestamp newValue) {
		this.asm_entry_date = newValue;
	}
	/**
	 * setEntityContext method comment
	 * @param ctx javax.ejb.EntityContext
	 */
	public void setEntityContext(javax.ejb.EntityContext ctx)
		throws java.rmi.RemoteException {
		entityContext = ctx;
	}
	/**
	 * Setter method for organ_site_concept_id
	 * @param newValue java.lang.String
	 */
	public void setOrgan_site_concept_id(java.lang.String newValue) {
		this.organ_site_concept_id = newValue;
	}
	/**
	 * Insert the method's description here.
	 * Creation date: (10/16/2001 1:02:05 PM)
	 * @param newOrgan_site_concept_id_other java.lang.String
	 */
	public void setOrgan_site_concept_id_other(
		java.lang.String newOrgan_site_concept_id_other) {
		organ_site_concept_id_other = newOrgan_site_concept_id_other;
	}
	/**
	 * Setter method for specimen_type
	 * @param newValue java.lang.String
	 */
	public void setSpecimen_type(java.lang.String newValue) {
		this.specimen_type = newValue;
	}
	/**
	 * unsetEntityContext method comment
	 */
	public void unsetEntityContext() throws java.rmi.RemoteException {
		entityContext = null;
	}
	/**
	 * Get accessor for persistent attribute: asm_form_id
	 */
	public java.lang.String getAsm_form_id() {
		return asm_form_id;
	}
	/**
	 * Set accessor for persistent attribute: asm_form_id
	 */
	public void setAsm_form_id(java.lang.String newAsm_form_id) {
		asm_form_id = newAsm_form_id;
	}
	/**
	 * Get accessor for persistent attribute: consent_id
	 */
	public java.lang.String getConsent_id() {
		return consent_id;
	}
	/**
	 * Set accessor for persistent attribute: consent_id
	 */
	public void setConsent_id(java.lang.String newConsent_id) {
		consent_id = newConsent_id;
	}
	/**
	 * Get accessor for persistent attribute: ardais_id
	 */
	public java.lang.String getArdais_id() {
		return ardais_id;
	}
	/**
	 * Set accessor for persistent attribute: ardais_id
	 */
	public void setArdais_id(java.lang.String newArdais_id) {
		ardais_id = newArdais_id;
	}
	/**
	 * Get accessor for persistent attribute: pfin_meets_specs
	 */
	public java.lang.String getPfin_meets_specs() {
		return pfin_meets_specs;
	}
	/**
	 * Set accessor for persistent attribute: pfin_meets_specs
	 */
	public void setPfin_meets_specs(java.lang.String newPfin_meets_specs) {
		pfin_meets_specs = newPfin_meets_specs;
	}
	/**
	 * Get accessor for persistent attribute: module_weight
	 */
	public java.lang.Integer getModule_weight() {
		return module_weight;
	}
	/**
	 * Set accessor for persistent attribute: module_weight
	 */
	public void setModule_weight(java.lang.Integer newModule_weight) {
		module_weight = newModule_weight;
	}
	/**
	 * Get accessor for persistent attribute: module_comments
	 */
	public java.lang.String getModule_comments() {
		return module_comments;
	}
	/**
	 * Set accessor for persistent attribute: module_comments
	 */
	public void setModule_comments(java.lang.String newModule_comments) {
		module_comments = newModule_comments;
	}
  /**
   * _copyFromEJB
   */
  public java.util.Hashtable _copyFromEJB() {
    com.ibm.ivj.ejb.runtime.AccessBeanHashtable h = new com.ibm.ivj.ejb.runtime.AccessBeanHashtable();

    h.put("specimen_type", getSpecimen_type());
    h.put("ardais_id", getArdais_id());
    h.put("consent_id", getConsent_id());
    h.put("organ_site_concept_id_other", getOrgan_site_concept_id_other());
    h.put("asm_entry_date", getAsm_entry_date());
    h.put("organ_site_concept_id", getOrgan_site_concept_id());
    h.put("asm_form_id", getAsm_form_id());
    h.put("module_weight", getModule_weight());
    h.put("pfin_meets_specs", getPfin_meets_specs());
    h.put("module_comments", getModule_comments());
    h.put("__Key", getEntityContext().getPrimaryKey());

    return h;
  }
  /**
   * _copyToEJB
   */
  public void _copyToEJB(java.util.Hashtable h) {
    java.lang.String localSpecimen_type = (java.lang.String) h.get("specimen_type");
    java.lang.String localArdais_id = (java.lang.String) h.get("ardais_id");
    java.lang.String localConsent_id = (java.lang.String) h.get("consent_id");
    java.lang.String localOrgan_site_concept_id_other = (java.lang.String) h
      .get("organ_site_concept_id_other");
    java.sql.Timestamp localAsm_entry_date = (java.sql.Timestamp) h.get("asm_entry_date");
    java.lang.String localOrgan_site_concept_id = (java.lang.String) h.get("organ_site_concept_id");
    java.lang.String localAsm_form_id = (java.lang.String) h.get("asm_form_id");
    java.lang.Integer localModule_weight = (java.lang.Integer) h.get("module_weight");
    java.lang.String localPfin_meets_specs = (java.lang.String) h.get("pfin_meets_specs");
    java.lang.String localModule_comments = (java.lang.String) h.get("module_comments");

    if (h.containsKey("specimen_type"))
      setSpecimen_type((localSpecimen_type));
    if (h.containsKey("ardais_id"))
      setArdais_id((localArdais_id));
    if (h.containsKey("consent_id"))
      setConsent_id((localConsent_id));
    if (h.containsKey("organ_site_concept_id_other"))
      setOrgan_site_concept_id_other((localOrgan_site_concept_id_other));
    if (h.containsKey("asm_entry_date"))
      setAsm_entry_date((localAsm_entry_date));
    if (h.containsKey("organ_site_concept_id"))
      setOrgan_site_concept_id((localOrgan_site_concept_id));
    if (h.containsKey("asm_form_id"))
      setAsm_form_id((localAsm_form_id));
    if (h.containsKey("module_weight"))
      setModule_weight((localModule_weight));
    if (h.containsKey("pfin_meets_specs"))
      setPfin_meets_specs((localPfin_meets_specs));
    if (h.containsKey("module_comments"))
      setModule_comments((localModule_comments));
  }
}
