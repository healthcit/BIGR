package com.gulfstreambio.kc.form.def;

import java.util.HashSet;
import java.util.Set;

public class FormDefinitionCategoryTypes {

  public static final String PAGE = "page";
  public static final String HEADING = "heading";

  private static String[] _validTypes = new String[] {PAGE, HEADING};

  private FormDefinitionCategoryTypes() {
    super();
  }

  public static boolean isValidCategoryType(String type) {
    for (int i = 0; i < _validTypes.length; i++) {
      if (_validTypes[i].equals(type)) {
        return true;
      }
    }
    return false;
  }
  
  public static String[] getCategoryTypes() {
    return _validTypes;
  }
}
