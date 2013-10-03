package com.ardais.bigr.beanutils.converters;

import org.apache.commons.beanutils.Converter;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.api.ApiFunctions;

/**
 * This is the base class for all of the Bigr converter classes in this
 * package.  The primary change beyond what the corresponding Jakarta Commons
 * BeanUtils classes do is that when no default value is specified, the
 * Jakarta classes throw an error when the convert method is invoked on a
 * null value, whereas the Bigr classes provide an additional constructor
 * parameter that controls whether to throw an exception in this case or
 * whether to just return null.  Since there's no way to tell whether a
 * given converter uses default values, the constructor to this class
 * requires you to pass in a flag indicating whether the supplied converter
 * uses default values or not.  You won't get the results you expect if the
 * value you pass in this flag is inconsistent with the supplied converter's
 * actual behavior.
 */
public class BigrConverterBase implements Converter {

    protected boolean _converterSuppliesDefault = false;
    protected boolean _returnNullOnNullWhenNoDefault = false;
    protected Object _nullValueToReturn = null;
    protected Converter _converter = null;

    // ----------------------------------------------------------- Constructors

    /** 
     * See the class comments.
     */
    public BigrConverterBase(
        boolean converterSuppliesDefault,
        boolean returnNullOnNullWhenNoDefault,
        Converter converter) {

        this(
            converterSuppliesDefault,
            returnNullOnNullWhenNoDefault,
            null,
            converter);
    }

    /**
     * Use this form of the constructor for converters whose converted result
     * is an array type.  The nullValueToReturn is the value that will
     * be returned when converting a null value when the
     * returnNullOnNullWhenNoDefault flag is true.  Its value should be either
     * null or an empty array of the appropriate type.  The nullValueToReturn
     * is not used when returnNullOnNullWhenNoDefault is false or when
     * converterSuppliesDefault is true.
     */
    public BigrConverterBase(
        boolean converterSuppliesDefault,
        boolean returnNullOnNullWhenNoDefault,
        Object nullValueToReturn,
        Converter converter) {

        _converterSuppliesDefault = converterSuppliesDefault;
        _returnNullOnNullWhenNoDefault = returnNullOnNullWhenNoDefault;
        _nullValueToReturn = nullValueToReturn;
        _converter = converter;
    }

    /**
     * Use this form of the constructor for converters that provide their own convert method.
     * If a convert method is not supplied by a subclass that calls this constructor, an 
     * exception will be thrown when the convert method is called.
     */
    public BigrConverterBase(
        boolean converterSuppliesDefault,
        boolean returnNullOnNullWhenNoDefault) {
  
        this(
            converterSuppliesDefault,
            returnNullOnNullWhenNoDefault,
            null);
    }

    /**
     * Use this form of the constructor for converters that provide their own convert
     * method and whose converted result is an array or collection type.  The nullValueToReturn 
     * is the value that will be returned when converting a null value when the
     * returnNullOnNullWhenNoDefault flag is true.  Its value should be either
     * null or an empty array or collection of the appropriate type.  The nullValueToReturn
     * is not used when returnNullOnNullWhenNoDefault is false or when
     * converterSuppliesDefault is true.
     * If a convert method is not supplied by a subclass that calls this constructor, an 
     * exception will be thrown when the convert method is called.
     */
    public BigrConverterBase(
        boolean converterSuppliesDefault,
        boolean returnNullOnNullWhenNoDefault,
        Object nullValueToReturn) {
  
        _converterSuppliesDefault = converterSuppliesDefault;
        _returnNullOnNullWhenNoDefault = returnNullOnNullWhenNoDefault;
        _nullValueToReturn = nullValueToReturn;
        _converter = null;;
    }

    // --------------------------------------------------------- Public Methods

    protected boolean shouldReturnNullValue(Object value) {
      boolean isEmptyValue = false;
      if (value == null) {
        isEmptyValue = true;
      }
      else if (value instanceof String) {
        isEmptyValue = (((String)value).length() == 0);
      }
      return (isEmptyValue && !_converterSuppliesDefault && _returnNullOnNullWhenNoDefault);
    }

    /**
     * Convert the specified input object into an output object of the
     * specified type.
     *
     * @param type Data type to which this value should be converted
     * @param value The input value to be converted
     *
     * @exception ConversionException if conversion cannot be performed
     *  successfully
     */
    public Object convert(Class type, Object value) {
      if (_converter == null) {
        throw new ApiException("Attempted to convert using " + this.getClass().getName() + " with a null converter.");
      }

        if (shouldReturnNullValue(value)) {
            return _nullValueToReturn;
        }

        return _converter.convert(type, value);
    }

}
