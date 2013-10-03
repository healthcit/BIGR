package com.ardais.bigr.kc.form.def;

import java.io.Serializable;
import java.util.Iterator;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.beanutils.BigrBeanUtilsBean;
import com.ardais.bigr.configuration.SystemConfiguration;
import com.ardais.bigr.iltds.assistants.LegalValue;
import com.ardais.bigr.iltds.assistants.LegalValueSet;
import com.gulfstreambio.kc.form.def.FormDefinition;
import com.gulfstreambio.kc.form.def.FormDefinitionFactory;
import com.gulfstreambio.kc.form.def.Tag;
import com.gulfstreambio.kc.form.def.shared.SharedViewService;

/**
 * Represents a form definition in BIGR.  Can be initialized with or return a KnowledgeCapture 
 * {@link com.gulfstreambio.kc.form.def.data.DataFormDefinition DataFormDefinition} instance.
 */
public class BigrFormDefinition implements Serializable {

  public static String SYSTEM_DEFAULT_RESULTS_FORM_NAME = "BIGR default view";
  public static String ANONYMOUS_FORM_NAME = "<None>";
  
  private static int MAXIMUM_NAME_DISPLAY_WIDTH = 30;

  private String _formDefinitionId;
  private String _formType;
  private String _name;
  private String _formDefinitionXml;
  private String _objectType;
  private String _uses;
  private String _account;
  private String _userName;
  private boolean _enabled;
  private FormDefinition _kcFormDef;
  private boolean _immutable = false;
  private boolean _anonymous = false;
  private String[] rolesSharedTo;
  private String[] defaultSharedViews;

  /**
   * Creates a new <code>BigrFormDefinition</code>. 
   */
  public BigrFormDefinition() {
    super();
  }

  /**
   * Creates a new <code>BigrFormDefinition</code> from the specified KnowledgeCapture 
   * <code>DataFormDefinition</code>.  All of this fields of this <code>BigrFormDefinition</code> 
   * are initialized from the KnowledgeCapture <code>DataFormDefinition</code>.
   * 
   * @param  kcFormDef  the KnowledgeCapture <code>DataFormDefinition</code>
   */
  public BigrFormDefinition(FormDefinition kcFormDef) {
    super();

    // Copy all of the simple properties from the KC form definition to this BIGR form definition.
    BigrBeanUtilsBean.SINGLETON.copyProperties(this, kcFormDef);
    setFormType(kcFormDef.getType());

    // Set the XML by converting the KC form definition to XML.
    setFormDefinitionXml(kcFormDef.toXml());

    // Use the KC form definition tags to set specific fields in the BIGR form definition.
    Tag[] tags = kcFormDef.getTags();
    for (int i = 0; i < tags.length; i++) {
      Tag tag = tags[i];
      String tagType = tag.getType();
      if (TagTypes.ACCOUNT.equalsIgnoreCase(tagType)) {
        setAccount(tag.getValue());
      }
      else if (TagTypes.DOMAIN_OBJECT.equalsIgnoreCase(tagType)) {
        setObjectType(tag.getValue());
      }
      else if (TagTypes.USES.equalsIgnoreCase(tagType)) {
        setUses(tag.getValue());
      }
      else if (TagTypes.USERNAME.equalsIgnoreCase(tagType)) {
        setUserName(tag.getValue());
      }
      else if (TagTypes.ANONYMOUS.equalsIgnoreCase(tagType)) {
        setAnonymous(Boolean.valueOf(tag.getValue()).booleanValue());
      }      
    }
  }

  /**
   * Creates a new <code>BigrFormDefinition</code> by combining the specified 
   * <code>BigrFormDefinition</code> and KnowledgeCapture <code>FormDefinition</code>.  This is 
   * intended to be used by a caller that has a KnowledgeCapture <code>FormDefinition</code>
   * that was created from an XML document.  Thus, the fields that are present in the form 
   * definition XML document, including name and the XML itself, are initialized from the
   * KnowledgeCapture <code>FormDefinition</code>, while all other fields, such as account and
   * object type, are initialized from the <code>BigrFormDefinition</code>.  In addition, the
   * fields of the KnowledgeCapture <code>FormDefinition</code> that are not obtained from the
   * form definition XML document are initialized from the <code>BigrFormDefinition</code>.
   * 
   * @param  kcFormDef  the <code>BigrFormDefinition</code>
   * @param  kcFormDef  the KnowledgeCapture <code>FormDefinition</code>
   */
  public BigrFormDefinition(BigrFormDefinition formDef, FormDefinition kcFormDef) {
    super();
    
    _kcFormDef = kcFormDef;
    
    // Copy just the name from the KC form definition to this BIGR form definition.
    setName(kcFormDef.getName());

    // If the XML from the BIGR form definition is present then use it, otherwise set the XML 
    // by converting the KC form definition to XML.
    String xml = formDef.getFormDefinitionXml();
    if (!ApiFunctions.isEmpty(xml)) {
      setFormDefinitionXml(xml);
    }
    else {
      setFormDefinitionXml(kcFormDef.toXml());
    }

    // Initialize the rest of the fields from the supplied BIGR form definition.
    setFormType(formDef.getFormType());
    setAccount(formDef.getAccount());
    setUserName(formDef.getUserName());
    setEnabled(formDef.isEnabled());
    setFormDefinitionId(formDef.getFormDefinitionId());
    setObjectType(formDef.getObjectType());
    setUses(formDef.getUses());
    setAnonymous(formDef.isAnonymous());

    kcFormDef.setEnabled(isEnabled());
    kcFormDef.setFormDefinitionId(getFormDefinitionId());
    if (!ApiFunctions.isEmpty(getAccount())) {
      kcFormDef.addTag(new Tag(TagTypes.ACCOUNT, getAccount()));
    }
    if (!ApiFunctions.isEmpty(getObjectType())) {
      kcFormDef.addTag(new Tag(TagTypes.DOMAIN_OBJECT, getObjectType()));
    }
    if (!ApiFunctions.isEmpty(getUses())) {
      kcFormDef.addTag(new Tag(TagTypes.USES, getUses()));
    }
    if (!ApiFunctions.isEmpty(getUserName())) {
      kcFormDef.addTag(new Tag(TagTypes.USERNAME, getUserName()));
    }
    if (isAnonymous()) {
      kcFormDef.addTag(new Tag(TagTypes.ANONYMOUS, "true"));
    }
  }

  public String getFormType() {
    return _formType;
  }

  public void setFormType(String formType) {
    checkImmutable();
    _formType = formType;
  }

  public String getName() {
    String returnValue = _name;
    //if this form is anonymous ignore whatever name the form might have and return the
    //name for anonymous forms
    if (isAnonymous()) {
      returnValue = ANONYMOUS_FORM_NAME;
    }
    return returnValue;
  }
  
  public String getShortName() {
    String returnValue = getName();
    if (!ApiFunctions.isEmpty(returnValue)) {
      if (returnValue.length() > MAXIMUM_NAME_DISPLAY_WIDTH) {
        returnValue = returnValue.substring(0,MAXIMUM_NAME_DISPLAY_WIDTH) + "...";
      }
    }
    return returnValue;
  }

  public void setName(String name) {
    checkImmutable();
    _name = name;
  }

  public String getFormDefinitionId() {
    return _formDefinitionId;
  }

  public void setFormDefinitionId(String id) {
    checkImmutable();
    _formDefinitionId = id;
  }

  public String getFormDefinitionXml() {
    return _formDefinitionXml;
  }

  public void setFormDefinitionXml(String xml) {
    checkImmutable();
    _formDefinitionXml = xml;
  }

  public String getObjectType() {
    return _objectType;
  }

  public void setObjectType(String type) {
    checkImmutable();
    _objectType = type;
  }

  public String getUses() {
    return _uses;
  }

  public void setUses(String uses) {
    checkImmutable();
    _uses = uses;
  }

  public String getAccount() {
    return _account;
  }

  public void setAccount(String account) {
    checkImmutable();
    _account = account;
  }

  public String getUserName() {
    return _userName;
  }
  
  public void setUserName(String userName) {
    checkImmutable();
    _userName = userName;
  }

  public boolean isEnabled() {
    return _enabled;
  }

  public void setEnabled(boolean enabled) {
    checkImmutable();
    _enabled = enabled;
  }

  /**
   * @return Returns the anonymous.
   */
  public boolean isAnonymous() {
    return _anonymous;
  }
  
  /**
   * @param anonymous The anonymous to set.
   */
  public void setAnonymous(boolean anonymous) {
    checkImmutable();
    _anonymous = anonymous;
  }

  /**
   * Sets this instance to be immutable.  Any attempts to modify immutable instances will throw 
   * an exception.
   */
  public void setImmutable() {
    _immutable = true;
  }

  /**
   * Returns an indication of whether this <code>BigrFormDefinition</code> is immutable.
   * 
   * @return  <code>true</code> if this <code>BigrFormDefinition</code> is immutable;
   *          <code>false</code> otherwise.
   */
  public boolean isImmutable() {
    return _immutable;
  }

	public String[] getRolesSharedTo()
	{
		return rolesSharedTo;
	}

	public void setRolesSharedTo(String[] rolesSharedTo)
	{
		this.rolesSharedTo = rolesSharedTo;
	}

	public String[] getDefaultSharedViews()
	{
		return defaultSharedViews;
	}

	public void setDefaultSharedViews(String[] defaultSharedViews)
	{
		this.defaultSharedViews = defaultSharedViews;
	}

  /**
   * Returns the KnowledgeCapture <code>FormDefinition</code> that is derived from this BIGR
   * form definition. 
   *  
   * @return  The KnowledgeCapture <code>FormDefinition</code>.
   */
  public FormDefinition getKcFormDefinition() {
    
    // If this instance is immutable return a copy of the KC form definition
    // so the caller cannot modify the original.
    if (isImmutable()) {
      return makeKcFormDefinition();
    }
    else {
      if (_kcFormDef == null) {
        _kcFormDef = makeKcFormDefinition();
      }
      return _kcFormDef;
    }
  }

  private FormDefinition makeKcFormDefinition() {
    FormDefinition returnForm = null;
    
    // Create the KC form definition from the XML, if present.
    String xml = getFormDefinitionXml();
    if (ApiFunctions.isEmpty(xml)) {
      returnForm = FormDefinitionFactory.SINGLETON.create(getFormType());
    }
    else {
      returnForm = FormDefinitionFactory.SINGLETON.createFromXml(getFormType(), xml);
    }
      
    // Copy all of the simple properties from this BIGR form definition to the KC form definition.
    returnForm.setEnabled(isEnabled());
    returnForm.setFormDefinitionId(getFormDefinitionId());
      
    // Set the object type as a tag.
    String objectType = getObjectType();
    if (!ApiFunctions.isEmpty(objectType)) {
      returnForm.addTag(new Tag(TagTypes.DOMAIN_OBJECT, objectType));
    }

    // Set uses as a tag.
    String uses = getUses();
    if (!ApiFunctions.isEmpty(uses)) {
      returnForm.addTag(new Tag(TagTypes.USES, uses));
    }

    // Set the account as a tag.
    String account = getAccount();
    if (!ApiFunctions.isEmpty(account)) {
      returnForm.addTag(new Tag(TagTypes.ACCOUNT, account));
    }
    
    // Set the user name as a tag.
    String userName = getUserName();
    if (!ApiFunctions.isEmpty(userName)) {
      returnForm.addTag(new Tag(TagTypes.USERNAME, userName));
    }
    
    //if the form is anonymous, create a tag to represent that
    if (isAnonymous()) {
      returnForm.addTag(new Tag(TagTypes.ANONYMOUS, "true"));
    }

	SharedViewService.SINGLETON.populateSharedViews(returnForm);

    return returnForm;
  }

  /**
   * Throws an exception if this instance is immutable.
   */
  private void checkImmutable() {
    if (_immutable) {
      throw new IllegalStateException("Attempted to modify an immutable BigrFormDefinition: " + this);
    }
  }
  
  public static boolean isValidObjectType(String type) {
    LegalValueSet objectTypes =
      SystemConfiguration.getLegalValueSet(SystemConfiguration.LEGAL_VALUE_SET_KC_ANNOTATED_OBJECTS);
    Iterator legalValues = objectTypes.getIterator();
    while (legalValues.hasNext()) {
      LegalValue legalValue = (LegalValue) legalValues.next();
      if (legalValue.getValue().equals(type)) {
        return true;
      }
    }
    return false;
  }

  public static boolean isValidUse(String use) {
    LegalValueSet uses =
      SystemConfiguration.getLegalValueSet(SystemConfiguration.LEGAL_VALUE_SET_DATA_FORM_USES);
    Iterator legalValues = uses.getIterator();
    while (legalValues.hasNext()) {
      LegalValue legalValue = (LegalValue) legalValues.next();
      if (legalValue.getValue().equals(use)) {
        return true;
      }
    }
    return false;
  }
}
