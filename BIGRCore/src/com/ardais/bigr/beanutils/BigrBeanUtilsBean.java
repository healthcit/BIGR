package com.ardais.bigr.beanutils;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.ConvertUtilsBean;
import org.apache.commons.beanutils.PropertyUtilsBean;

import com.ardais.bigr.api.ApiFunctions;

/**
 * Utility methods for populating JavaBeans properties via reflection.
 * This class extends and replaces some of the functionality provided by the
 * corresponding class from the Jakarta Commons BeanUtils package
 * ({@link org.apache.commons.beanutils.BeanUtilsBean}).  In general, use this
 * class rather than BeanUtils so that you don't bypass the BIGR extensions.
 */
public class BigrBeanUtilsBean extends BeanUtilsBean {

  /**
   * The singleton instance of BigrBeanUtilsBean.
   */
  public static BigrBeanUtilsBean SINGLETON = new BigrBeanUtilsBean();  

  /**
   * Constructs an instance using the Commons property utils and the BIGR convert utils. 
   */
  public BigrBeanUtilsBean() {
    super(BigrConvertUtilsBean.SINGLETON, new PropertyUtilsBean());
  }
  
  /**
   * Returns the singleton instance of this class.  Use the public {@link #SINGLETON} field instead
   * of this method.  We override this so that our BigrBeanUtilsBean instance will be returned
   * instead of the base BeanUtilsBean should this method ever be called in error.
   * 
   * @return  the singleton instance of BigrBeanUtilsBean
   */
  public synchronized static BeanUtilsBean getInstance() {
    return SINGLETON;
  }  

  /**
   * Throws an UnsupportedOperationException and should not be called.  We explicitly override this 
   * to throw the exception so that it will not be called in error.  There is no need to set the 
   * BigrBeanUtilsBean instance since it is set statically and returned via the public 
   * {@link #SINGLETON} field of this class.
   */
  public synchronized static void setInstance(BeanUtilsBean newInstance) {
    throw new UnsupportedOperationException("BigrBeanUtilsBean.setInstance is not supported and it is not necessary to call it.  Use BigrBeanUtilsBean.SINGLETON to get the singleton instance of BigrBeanUtilsBean.");
  }
  
  /**
   * Overrides {@link org.apache.commons.beanutils.BeanUtilsBean#copyProperties(java.lang.Object, java.lang.Object)}
   * to throw runtime exceptions rather than checked exceptions to make life more convenient for 
   * the caller.
   */
  public void copyProperties(Object dest, Object orig) {
    try {
      super.copyProperties(dest, orig);
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
  }

  /**
   * Overrides {@link org.apache.commons.beanutils.BeanUtilsBean#copyProperty(java.lang.Object, java.lang.String, java.lang.Object)}
   * to throw runtime exceptions rather than checked exceptions to make life more convenient for 
   * the caller.
   */
  public void copyProperty(Object bean, String name, Object value) {
    try {
      super.copyProperty(bean, name, value);
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
  }

}
