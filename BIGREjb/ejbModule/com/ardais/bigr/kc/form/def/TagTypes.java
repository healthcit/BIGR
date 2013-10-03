package com.ardais.bigr.kc.form.def;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.gulfstreambio.kc.form.def.FormDefinition;
import com.gulfstreambio.kc.form.def.FormDefinitionService;
import com.gulfstreambio.kc.form.def.FormDefinitionServiceResponse;
import com.gulfstreambio.kc.form.def.Tag;
import com.gulfstreambio.kc.form.def.data.DataFormDefinition;

/**
 * Contains constants that specify the valid types of tags that BIGR uses in KC form definitions,
 * as well as tag values where appropriate.
 */
public class TagTypes {
  
  /**
   * Specifies the account to which a data, query or results form belongs.
   * <ul>
   * <li>Level: form definition</li>
   * <li>Cardinality: 1</li>
   * <li>Required?: required on all data, query and results forms</li>
   * <li>Value: an account id</li>
   * </ul>
   */
  public final static String ACCOUNT = "account";

  /**
   * Specifies that the results form is the user's anonymous results form.  It is essentially a
   * Boolean tag, so it's value will always be "true" when present, and it will not be associated
   * with non-anonymous results forms.
   * <ul>
   * <li>Level: form definition</li>
   * <li>Cardinality: 1</li>
   * <li>Required?: no, only associated with a user's anonymous results form</li>
   * <li>Value: always "true"</li>
   * </ul>
   */
  public final static String ANONYMOUS = "anonymous";
  
  /**
   * For data form definitions, and more specifically sample registration forms, specifies  
   * for each host element and KC data element whether the value of the element should default
   * to that of the parent(s) in child samples created in a derivative operation. 
   * <ul>
   * <li>Level: element</li>
   * <li>Cardinality: 1</li>
   * <li>Required?: no.  If not supplied, "false" is assumed.</li>
   * <li>Value: "true" or "false"</li>
   * </ul>
   */  
  public final static String DERIV_DEFAULTS = "derivativeDefaults";

  /**
   * For data form definitions, and more specifically sample registration forms, specifies  
   * for each host element and KC data element whether the value of the element should inherit
   * that of the parent(s) in child samples created in a derivative operation. 
   * <ul>
   * <li>Level: element</li>
   * <li>Cardinality: 1</li>
   * <li>Required?: no.  If not supplied, "false" is assumed.</li>
   * <li>Value: "true" or "false"</li>
   * </ul>
   */  
  public final static String DERIV_INHERITS = "derivativeInherits";

  /**
   * For data form definitions, and more specifically sample registration forms, specifies  
   * for each host element and KC data element whether the element is rendered for child samples 
   * created in a derivative operation. 
   * <ul>
   * <li>Level: element</li>
   * <li>Cardinality: 1</li>
   * <li>Required?: no.  If not supplied, "false" is assumed.</li>
   * <li>Value: "true" or "false"</li>
   * </ul>
   */  
  public final static String DERIV_IGNORES = "derivativeIgnores";

  /**
   * For data form definitions, specifies the domain object type of the form.  For query and 
   * results forms, specifies the domain object type of each KC data element within the form.
   * <ul>
   * <li>Level: form definition and element</li>
   * <li>Cardinality: 1</li>
   * <li>Required?: yes, for all data forms, and for each KC data element within a query or
   * results form</li>
   * <li>Value: One of {@link #DOMAIN_OBJECT_VALUE_DONOR}, {@link #DOMAIN_OBJECT_VALUE_CASE}
   * or {@link #DOMAIN_OBJECT_VALUE_SAMPLE}</li>
   * </ul>
   */  
  public final static String DOMAIN_OBJECT = "domainObjectType";
  
  // Note: These values should match the values in the kc_annotated_objects legal value set in 
  // systemConfiguration.xml exactly.
  public final static String DOMAIN_OBJECT_VALUE_DONOR = "donor";
  public final static String DOMAIN_OBJECT_VALUE_CASE = "case";
  public final static String DOMAIN_OBJECT_VALUE_SAMPLE = "sample";
  
  /**
   * For results form definitions, specifies the order of the host element or KC data element 
   * in the results view. 
   * <ul>
   * <li>Level: element</li>
   * <li>Cardinality: 1</li>
   * <li>Required?: yes, for each host element and KC data element</li>
   * <li>Value: an integer</li>
   * </ul>
   */  
  public final static String ORDER = "order";

  /**
   * For results form definitions, specifies the parent data form definition id for each KC
   * data element in the results form. 
   * <ul>
   * <li>Level: element (KC data elements only)</li>
   * <li>Cardinality: 1</li>
   * <li>Required?: yes, for each KC data element</li>
   * <li>Value: a data form definition id</li>
   * </ul>
   */  
  public final static String PARENT = "parent";
    
  /**
   * For data form definitions, and more specifically registration forms, specifies the policies 
   * using this form as the case registration form. 
   * <ul>
   * <li>Level: form definition</li>
   * <li>Cardinality: N.  There will be an instance of this tag per policy that uses the form
   * as its case registration form.</li>
   * <li>Required?: no</li>
   * <li>Value: a policy id</li>
   * </ul>
   */  
  public final static String REGISTRATION_FORM_CASE = "regFormCase";
  
  /**
   * For data form definitions, and more specifically registration forms, specifies the account(s) 
   * using this form as the donor registration form. 
   * <ul>
   * <li>Level: form definition</li>
   * <li>Cardinality: N.  There will be an instance of this tag per account that uses the form
   * as its donor registration form.</li>
   * <li>Required?: no</li>
   * <li>Value: an account id</li>
   * </ul>
   */  
  public final static String REGISTRATION_FORM_DONOR = "regFormDonor";

  /**
   * For data form definitions, and more specifically registration forms, specifies the sample
   * types within policies using this form as the sample registration form. 
   * <ul>
   * <li>Level: form definition</li>
   * <li>Cardinality: N.  There will be an instance of this tag per sample type and policy that 
   * uses the form as its sample registration form.</li>
   * <li>Required?: no</li>
   * <li>Value: a policy id, followed by a ":", followed by a sample type CUI</li>
   * </ul>
   */  
  public final static String REGISTRATION_FORM_SAMPLE = "regFormSample";

  /**
   * Specifies the user to which a results form belongs.
   * <ul>
   * <li>Level: form definition</li>
   * <li>Cardinality: 1</li>
   * <li>Required?: required on all results forms</li>
   * <li>Value: a user id</li>
   * </ul>
   */
  public final static String USERNAME = "username";  
  
  /**
   * For data form definitions, specifies the intended uses for the form.
   * <ul>
   * <li>Level: form definition</li>
   * <li>Cardinality: 1</li>
   * <li>Required?: yes</li>
   * <li>Value: One of {@link #USES_VALUE_ANNOTATION} or {@link #USES_VALUE_REGISTRATION}.
   * </ul>
   */  
  public final static String USES = "uses";  

  // Note: These values should match the values in the data_form_uses legal value set in 
  // systemConfiguration.xml exactly.
  public static String USES_VALUE_ANNOTATION = "annotation";
  public static String USES_VALUE_REGISTRATION = "registration";  
  
  private static Set _validTypesFormDefinition = new HashSet();
  static {
    _validTypesFormDefinition.add(ACCOUNT);
    _validTypesFormDefinition.add(ANONYMOUS);
    _validTypesFormDefinition.add(DOMAIN_OBJECT);
    _validTypesFormDefinition.add(USES);
    _validTypesFormDefinition.add(USERNAME);
    _validTypesFormDefinition.add(REGISTRATION_FORM_DONOR);
    _validTypesFormDefinition.add(REGISTRATION_FORM_CASE);
    _validTypesFormDefinition.add(REGISTRATION_FORM_SAMPLE);    
  }
  
  private static Set _validTypesFormDefinitionSingle = new HashSet();
  static {
    _validTypesFormDefinitionSingle.add(ACCOUNT);
    _validTypesFormDefinitionSingle.add(ANONYMOUS);
    _validTypesFormDefinitionSingle.add(DOMAIN_OBJECT);
    _validTypesFormDefinitionSingle.add(USES);
    _validTypesFormDefinitionSingle.add(USERNAME);
  }

  private static Set _validTypesElement = new HashSet();
  static {
    _validTypesElement.add(DOMAIN_OBJECT);
    _validTypesElement.add(ORDER);
    _validTypesElement.add(PARENT);
    _validTypesElement.add(DERIV_DEFAULTS);
    _validTypesElement.add(DERIV_INHERITS);
    _validTypesElement.add(DERIV_IGNORES);
  }
  
  private TagTypes() {
    super();
  }

  public static boolean isValidFormDefinitionTagType(String type) {
    return _validTypesFormDefinition.contains(type);
  }

  public static boolean isValidElementTagType(String type) {
    return _validTypesElement.contains(type);
  }

  public static boolean isValidTagType(String type) {
    return isValidFormDefinitionTagType(type) || isValidElementTagType(type);
  }
  
  /**
   * Adds tags that are already persisted on a form to a form to be updated.  This is useful
   * when calling one of the update methods on the <code>FormDefinitionService</code> since
   * they remove all tags and then add only those that are in the input form definition.  Thus
   * calling this method before <code>FormDefinitionService</code> will correctly preserve
   * tags that are not in the input form definition but are persisted in the database.
   *  
   * @param updateForm  the form to be updated.  This may be altered by having extra tags added 
   * to it.
   */
  static void addPersistedTagsToUpdateForm(FormDefinition updateForm) {

    // Get the persisted version of the form.  If it was not found, then return.
    FormDefinitionServiceResponse response = 
      FormDefinitionService.SINGLETON.findFormDefinitionById(updateForm.getFormDefinitionId());
    FormDefinition persistedForm = response.getFormDefinition();
    if (persistedForm == null) {
      return;
    }

    // Put all of the tags to be updated in a Map for easier processing.  The Map is keyed by
    // the tag type, and contains a list of all tags with that tag type.
    Map updateTagsByType = new HashMap();
    Tag[] tags = updateForm.getTags();
    for (int i = 0; i < tags.length; i++) {
      Tag tag = tags[i];
      String tagType = tag.getType();
      List tagsByType = (List) updateTagsByType.get(tagType);
      if (tagsByType == null) {
        tagsByType = new ArrayList();
        updateTagsByType.put(tagType, tagsByType);
      }
      tagsByType.add(tag);
    }
        
    // Iterate over all of the tags in the persisted form.  If a tag that is already persisted
    // is not in the update then add that tag to the update form. 
    tags = persistedForm.getTags();
    for (int i = 0; i < tags.length; i++) {
      Tag persistedTag = tags[i];
      String tagType = persistedTag.getType();
      List tagsByType = (List) updateTagsByType.get(tagType);
      
      // The persisted tag is not in the update form, so add it.
      if (tagsByType == null) {
        updateForm.addTag(persistedTag);
      }

      // One or more tags with the same type as the persisted tag is in the update form.  If the
      // cardinality of the tag is one (i.e. it is in _validTypesFormDefinitionSingle) then the
      // update form tag will essentially overwrite the persisted form tag, which is what we want.
      // If the cardinality of the tag is not one, then add the persisted tag to the update form
      // if the persisted tag type and value does not exactly match an update form tag (since we
      // know for such tags the combination of type and value must be unique).
      else if (!_validTypesFormDefinitionSingle.contains(tagType)) {
        for (int j = 0; j < tagsByType.size(); j++) {
          Tag updateTag = (Tag) tagsByType.get(j);
          if (!updateTag.equals(persistedTag)) {
            updateForm.addTag(persistedTag);            
          }
        }
      }
    }
  }
  
}
