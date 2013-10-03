package com.gulfstreambio.kc.form.def;

public class FormDefinitionTypes {

  public static final String DATA = "data"; 
  public static final String QUERY = "query"; 
  public static final String RESULTS = "results";
  
  private static String[] _validTypes = new String[] {DATA, QUERY, RESULTS};
  
  private FormDefinitionTypes() {
    super();
  }

  public static boolean isValidFormDefinitionType(String type) {
    for (int i = 0; i < _validTypes.length; i++) {
      if (_validTypes[i].equals(type)) {
        return true;
      }
    }
    return false;
  }
  
  public static String[] getFormDefinitionTypes() {
    return _validTypes;
  }
}
