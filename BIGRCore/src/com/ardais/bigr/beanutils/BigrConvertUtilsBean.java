package com.ardais.bigr.beanutils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;

import org.apache.commons.beanutils.ConvertUtilsBean;

import com.ardais.bigr.beanutils.converters.BigrBigDecimalConverter;
import com.ardais.bigr.beanutils.converters.BigrBigIntegerConverter;
import com.ardais.bigr.beanutils.converters.BigrBooleanArrayConverter;
import com.ardais.bigr.beanutils.converters.BigrBooleanConverter;
import com.ardais.bigr.beanutils.converters.BigrByteArrayConverter;
import com.ardais.bigr.beanutils.converters.BigrByteConverter;
import com.ardais.bigr.beanutils.converters.BigrCharacterArrayConverter;
import com.ardais.bigr.beanutils.converters.BigrCharacterConverter;
import com.ardais.bigr.beanutils.converters.BigrClassConverter;
import com.ardais.bigr.beanutils.converters.BigrDoubleArrayConverter;
import com.ardais.bigr.beanutils.converters.BigrDoubleConverter;
import com.ardais.bigr.beanutils.converters.BigrFloatArrayConverter;
import com.ardais.bigr.beanutils.converters.BigrFloatConverter;
import com.ardais.bigr.beanutils.converters.BigrIntegerArrayConverter;
import com.ardais.bigr.beanutils.converters.BigrIntegerConverter;
import com.ardais.bigr.beanutils.converters.BigrListConverter;
import com.ardais.bigr.beanutils.converters.BigrLongArrayConverter;
import com.ardais.bigr.beanutils.converters.BigrLongConverter;
import com.ardais.bigr.beanutils.converters.BigrShortArrayConverter;
import com.ardais.bigr.beanutils.converters.BigrShortConverter;
import com.ardais.bigr.beanutils.converters.BigrSqlDateConverter;
import com.ardais.bigr.beanutils.converters.BigrSqlTimeConverter;
import com.ardais.bigr.beanutils.converters.BigrSqlTimestampConverter;
import com.ardais.bigr.beanutils.converters.BigrStringArrayConverter;
import com.ardais.bigr.beanutils.converters.BigrStringConverter;

/**
 * <p>Utility methods for converting String scalar values to objects of the
 * specified Class, String arrays to arrays of the specified Class.  The
 * actual {@link org.apache.commons.beanutils.Converter} instance to be used
 * can be registered for each possible destination Class.  This class
 * extends and replaces some of the functionality provided by the
 * corresponding class from the Jakarta Commons BeanUtils package
 * ({@link org.apache.commons.beanutils.ConvertUtilsBean}).  In general, use this
 * class rather than ConvertUtilsBean so that you don't bypass the BIGR extensions.
 * 
 * <p>One important method that this class supplies is
 * {@link #initialize()}, which overrides the Jakarta default
 * converters with Bigr versions of those converters.  One important feature of
 * the Bigr converters that we register is that they throw exceptions
 * on conversion errors instead of quietly substituting a default value.
 */
public class BigrConvertUtilsBean extends ConvertUtilsBean {

  /**
   * The singleton instance of BigrConvertUtilsBean.
   */
  public static BigrConvertUtilsBean SINGLETON = new BigrConvertUtilsBean();  


  /**
   * Returns the BigrConvertUtilsBean instance with which BigrBeanUtilsBean was initialized.
   * 
   * @return  The BigrConvertUtilsBean.
   */
  protected static ConvertUtilsBean getInstance() {
    return SINGLETON;
  }

  /**
   * Constructs a new BigrConvertUtilsBean.  As a side effect, registers standard converters by
   * calling {@link #deregister()}.
   */
  private BigrConvertUtilsBean() {
    super();
  }

  /**
   * Removes all registered {@link org.apache.commons.beanutils.Converter}s,
   * and re-establish the BIGR converters as the default Converters.
   * 
   * @see org.apache.commons.beanutils.ConvertUtilsBean#deregister()
   */
  public void deregister() {
    super.deregister();

    boolean booleanArray[] = new boolean[0];
    byte byteArray[] = new byte[0];
    char charArray[] = new char[0];
    double doubleArray[] = new double[0];
    float floatArray[] = new float[0];
    int intArray[] = new int[0];
    long longArray[] = new long[0];
    short shortArray[] = new short[0];
    String stringArray[] = new String[0];

    register(new BigrBigDecimalConverter(true), BigDecimal.class);
    register(new BigrBigIntegerConverter(true), BigInteger.class);
    register(new BigrBooleanConverter(false), Boolean.TYPE);
    register(new BigrBooleanConverter(true), Boolean.class);
    register(new BigrBooleanArrayConverter(true), booleanArray.getClass());
    register(new BigrByteConverter(false), Byte.TYPE);
    register(new BigrByteConverter(true), Byte.class);
    register(new BigrByteArrayConverter(true), byteArray.getClass());
    register(new BigrCharacterConverter(false), Character.TYPE);
    register(new BigrCharacterConverter(true), Character.class);
    register(new BigrCharacterArrayConverter(true), charArray.getClass());
    register(new BigrClassConverter(true), Class.class);
    register(new BigrDoubleConverter(false), Double.TYPE);
    register(new BigrDoubleConverter(true), Double.class);
    register(new BigrDoubleArrayConverter(true), doubleArray.getClass());
    register(new BigrFloatConverter(false), Float.TYPE);
    register(new BigrFloatConverter(true), Float.class);
    register(new BigrFloatArrayConverter(true), floatArray.getClass());
    register(new BigrIntegerConverter(false), Integer.TYPE);
    register(new BigrIntegerConverter(true), Integer.class);
    register(new BigrIntegerArrayConverter(true), intArray.getClass());
    register(new BigrLongConverter(false), Long.TYPE);
    register(new BigrLongConverter(true), Long.class);
    register(new BigrLongArrayConverter(true), longArray.getClass());
    register(new BigrShortConverter(false), Short.TYPE);
    register(new BigrShortConverter(true), Short.class);
    register(new BigrShortArrayConverter(true), shortArray.getClass());
    register(new BigrStringConverter(true), String.class);
    register(new BigrStringArrayConverter(true), stringArray.getClass());
    register(new BigrSqlDateConverter(true), Date.class);
    register(new BigrSqlTimeConverter(true), Time.class);
    register(new BigrSqlTimestampConverter(true), Timestamp.class);
    register(new BigrListConverter(true), List.class);
  }

}
