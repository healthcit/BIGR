package com.gulfstreambio.kc.form.def.query;

public class QueryFormDefinitionValueTypes {

  public static final String ANY = "any";
  public static final String COMPARISON = "comparison";
  public static final String VALUESET = "set";

  private static String[] _validTypes = new String[] {ANY, COMPARISON, VALUESET};

  private QueryFormDefinitionValueTypes() {
    super();
  }

  public static boolean isValidType(String type) {
    for (int i = 0; i < _validTypes.length; i++) {
      if (_validTypes[i].equals(type)) {
        return true;
      }
    }
    return false;
  }

  public static String[] getQueryFormDefinitionValueTypes() {
    return _validTypes;
  }

}
