package com.gulfstreambio.kc.det;

public class DetElementDatatypes {

  public static final String CV = "cv"; 
  public static final String DATE = "date"; 
  public static final String FLOAT = "float"; 
  public static final String INT = "int"; 
  public static final String VPD = "vpd"; 
  public static final String REPORT = "report"; 
  public static final String TEXT = "text";  
 
  
  private static String[] _validTypes = new String[] {CV, DATE, FLOAT, INT, REPORT, VPD, TEXT};
  
  private DetElementDatatypes() {
    super();
  }
  
  public static boolean isValidDatatype(String type) {
    for (int i = 0; i < _validTypes.length; i++) {
      if (_validTypes[i].equals(type)) {
        return true;
      }
    }
    return false;
  }
  
  public static String[] getDatatypes() {
    return _validTypes;
  }
  
}
