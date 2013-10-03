package com.gulfstreambio.kc.util;

import com.ardais.bigr.util.SystemNameUtils;
import com.gulfstreambio.gboss.GbossAde;
import com.gulfstreambio.gboss.GbossCategory;
import com.gulfstreambio.gboss.GbossDataElement;
import com.gulfstreambio.kc.det.DetAde;
import com.gulfstreambio.kc.det.DetCategory;
import com.gulfstreambio.kc.det.DetElement;

public class KcNamingUtils {

  /**
   * Since this class only has static methods we'd normally never need to instantiate it.
   * However, we want to use methods on this class inside Velocity templates and to do so
   * we must create an instance of the class and place it in the VelocityContext.  So, we
   * provide a constant containing a singleton instance.  When using this class from Java
   * code there's no reason to use this contant.
   */
  public static final KcNamingUtils SINGLETON = new KcNamingUtils();
  
  public static String getDatabaseTableNameConventional(DetCategory category) {
    return getDatabaseTableNameConventional(category.getSystemName());
  }

  public static String getDatabaseTableNameConventional(GbossCategory category) {
    return getDatabaseTableNameConventional(category.getSystemName());
  }

  private static String getDatabaseTableNameConventional(String systemName) {
    return "CIR_" + SystemNameUtils.convertToCanonicalForm(systemName).toUpperCase();
  }

  public static String getDatabaseViewNameConventional(GbossCategory category) {
    return getDatabaseViewNameConventional(category.getSystemName());
  }

  private static String getDatabaseViewNameConventional(String systemName) {
    return SystemNameUtils.convertToCanonicalForm(systemName).toUpperCase() + "_V";
  }

  public static String getDatabaseTableNameEav(DetCategory category) {
    return KcNamingUtils.getDatabaseTableNameEav(category.getSystemName());
  }

  public static String getDatabaseTableNameEav(GbossCategory category) {
    return KcNamingUtils.getDatabaseTableNameEav(category.getSystemName());
  }

  private static String getDatabaseTableNameEav(String systemName) {
    return KcNamingUtils.getDatabaseTableNameConventional(systemName) + "_EAV";
  }

  public static String getDatabaseViewNameEav(GbossCategory category) {
    return KcNamingUtils.getDatabaseViewNameEav(category.getSystemName());
  }

  private static String getDatabaseViewNameEav(String systemName) {
    return SystemNameUtils.convertToCanonicalForm(systemName).toUpperCase() + "_EAV_V";
  }

  public static String getDatabaseTableNameMulti(DetCategory category) {
    return KcNamingUtils.getDatabaseTableNameMulti(category.getSystemName());
  }

  public static String getDatabaseTableNameMulti(GbossCategory category) {
    return KcNamingUtils.getDatabaseTableNameMulti(category.getSystemName());
  }

  private static String getDatabaseTableNameMulti(String systemName) {
    return KcNamingUtils.getDatabaseTableNameConventional(systemName) + "_MULTI";
  }

  public static String getDatabaseViewNameMulti(GbossCategory category) {
    return KcNamingUtils.getDatabaseViewNameMulti(category.getSystemName());
  }

  private static String getDatabaseViewNameMulti(String systemName) {
    return SystemNameUtils.convertToCanonicalForm(systemName).toUpperCase() + "_MULTI_V";
  }

  public static String getDatabaseTableNameNote(DetCategory category) {
    return KcNamingUtils.getDatabaseTableNameNote(category.getSystemName());
  }

  public static String getDatabaseTableNameNote(GbossCategory category) {
    return KcNamingUtils.getDatabaseTableNameNote(category.getSystemName());
  }

  private static String getDatabaseTableNameNote(String systemName) {
    return KcNamingUtils.getDatabaseTableNameConventional(systemName) + "_NOTE";
  }

  public static String getDatabaseViewNameNote(GbossCategory category) {
    return KcNamingUtils.getDatabaseViewNameNote(category.getSystemName());
  }

  private static String getDatabaseViewNameNote(String systemName) {
    return SystemNameUtils.convertToCanonicalForm(systemName).toUpperCase() + "_NOTE_V";
  }

  public static String getDatabaseTableNameConventional(DetAde ade) {
    return "CIR_" + SystemNameUtils.convertToCanonicalForm(ade.getSystemName()).toUpperCase();
  }

  public static String getDatabaseTableNameConventional(GbossAde ade) {
    return "CIR_" + SystemNameUtils.convertToCanonicalForm(ade.getSystemName()).toUpperCase();
  }

  public static String getDatabaseViewNameConventional(GbossAde ade) {
    return SystemNameUtils.convertToCanonicalForm(ade.getSystemName()).toUpperCase() + "_V";
  }

  public static String getDatabaseTableNameMulti(DetAde ade) {
    return getDatabaseTableNameConventional(ade) + "_MULTI";
  }

  public static String getDatabaseTableNameMulti(GbossAde ade) {
    return getDatabaseTableNameConventional(ade) + "_MULTI";
  }

  public static String getDatabaseViewNameMulti(GbossAde ade) {
    return SystemNameUtils.convertToCanonicalForm(ade.getSystemName()).toUpperCase() + "_MULTI_V";
  }

  public static String getDatabaseColumnName(DetElement dataElement) {
    if (dataElement.isDatatypeCv()) {
      return dataElement.getSystemName().toUpperCase() + "_CUI";      
    }
    else {
      return dataElement.getSystemName().toUpperCase();      
    }
  }
  
  public static String getDatabaseColumnNameStandardValue(DetElement dataElement) {
    if (dataElement.isDatatypeCv()) {
      return KcNamingUtils.getDatabaseColumnName(dataElement);
    }
    else {
      return KcNamingUtils.getDatabaseColumnName(dataElement) + "_CUI";      
    }
  }

  public static String getDatabaseColumnNameOther(DetElement dataElement) {
    return "OTHER_" + dataElement.getSystemName().toUpperCase();
  }

  public static String getDatabaseColumnNameDpc(DetElement dataElement) {
    return dataElement.getSystemName().toUpperCase() + "_DPC";
  }
  
  private KcNamingUtils() {
    super();
  }

}
