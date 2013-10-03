package com.gulfstreambio.kc.form.def;

import com.ardais.bigr.api.ApiFunctions;
import com.gulfstreambio.kc.det.DetValueSet;
import com.gulfstreambio.kc.det.DetValueSetValue;
import com.gulfstreambio.kc.form.def.data.DataFormDefinitionValue;
import com.gulfstreambio.kc.form.def.data.DataFormDefinitionValueSet;

/**
 * @author sthomashow
 *
 *  functions used by multiple validators
 */
class ValidatorUtils {

  /**
   * Constructor is private to prevent instantiation.
   */
  private ValidatorUtils() {
    super();
  }

  /**
   * Returns an indication of whether the dataform value set definition is a subset of the specified
   * value set definition.
   * 
   * @return  <code>true</code> if the dataform value set definition is a subset of the specified
   *          value set definition; <code>false</code> otherwise.  If this dataform value set definition has
   *          no values, then it is considered a subset of any other value set, and thus
   *          <code>true</code> is returned. 
   */  
  static boolean subsetOf(DataFormDefinitionValueSet valueSet, DetValueSet superSet) {
    DataFormDefinitionValue[] values = valueSet.getValues();
    for (int i = 0; i < values.length; i++) {
      DataFormDefinitionValue value = values[i];
      DetValueSetValue superValue = superSet.getValue(value.getCui());
      if (superValue == null) {
        return false;
      }     
      if (!subsetOf(value, superValue)) {
        return false;
      }
    }
    return true;
}  

  
  static boolean subsetOf(DataFormDefinitionValue value, DetValueSetValue superValue) {
    if (!(value.getCui().equals(superValue.getCui()))) {
      return false;
    }
    DataFormDefinitionValue[] values = value.getValues();
    for (int i = 0; i < values.length; i++) {
      DataFormDefinitionValue subValue = values[i];
      DetValueSetValue superSubValue = superValue.getValue(subValue.getCui());
      if (superSubValue == null) {
        return false;
      }     
      if (!subsetOf(subValue, superSubValue)) {
        return false;
      }
    }
    return true;
  }
  
  
  
  /**
   * Returns an indication of whether this dataform value set definition is a subset of the specified
   * value set definition.
   * 
   * @return  <code>true</code> if this dataform value set definition is a subset of the specified
   *          value set definition; <code>false</code> otherwise.  If this dataform value set definition has
   *          no values, then it is considered a subset of any other value set, and thus
   *          <code>true</code> is returned. 
   */
  static boolean subsetOf(DataFormDefinitionValueSet valueSet, DataFormDefinitionValueSet superSet) {
    DataFormDefinitionValue[] values = valueSet.getValues();
    for (int i = 0; i < values.length; i++) {
      DataFormDefinitionValue value = values[i];
      DataFormDefinitionValue superValue = superSet.getValue(value.getCui());
      if (superValue == null) {
        return false;
      }     
      if (!subsetOf(value, superValue)) {
        return false;
      }
    }
    return true;
  }



  /**
   * Returns an indication of whether this value definition is a subset of the specified
   * value definition.  For this method to return <code>true</code>, this value and the
   * specified super value have to be equals, and any sub-values of this value have to also
   * be sub-values of the super value.  
   * 
   * @return  <code>true</code> if this value definition is a subset of the specified
   *          value definition; <code>false</code> otherwise.
   */
  static boolean subsetOf(DataFormDefinitionValue value, DataFormDefinitionValue superValue) {
    if (!(value.getCui().equals(superValue.getCui()))) {
      return false;
    }
    if (!ApiFunctions.safeEquals(value.getDisplayName(), superValue.getDisplayName())) {
      return false;
    }
    
    DataFormDefinitionValue[] values = value.getValues();
    for (int i = 0; i < values.length; i++) {
      DataFormDefinitionValue subValue = values[i];
      DataFormDefinitionValue superSubValue = superValue.getValue(subValue.getCui());
      if (superSubValue == null) {
        return false;
      }     
      if (!subsetOf(subValue, superSubValue)) {
        return false;
      }
    }
    return true;
  }


  
  
}
