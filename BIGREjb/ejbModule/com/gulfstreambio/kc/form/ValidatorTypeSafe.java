package com.gulfstreambio.kc.form;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.apache.commons.beanutils.ConversionException;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.iltds.btx.BtxActionErrors;
import com.ardais.bigr.validation.AbstractValidator;
import com.ardais.bigr.validation.Validator;
import com.ardais.bigr.validation.ValidatorErrorListener;
import com.gulfstreambio.kc.det.DetElementDatatypes;
import com.gulfstreambio.kc.det.DetService;
import com.gulfstreambio.kc.det.DetValueSet;

/**
 * Validates that a value has the proper syntax.
 * <p>
 * This validator may return one error as follows, with insertion strings listed below the error:
 * <ul>
 * <li>{@link #ERROR_KEY1} - "{0}" can't be coverted to type "{1}".
 * </li>
 * </ul> 
 */
public class ValidatorTypeSafe extends AbstractValidator {
  
  /**
   * The key of the error that may be returned from this validator. 
   */
  public final static String ERROR_KEY_UNKNOWN = "errors.typesafe";
  public final static String ERROR_KEY_INT = "errors.int2";
  public final static String ERROR_KEY_FLOAT = "errors.float2";
  public final static String ERROR_KEY_DATE = "errors.date2";
  public final static String ERROR_KEY_VPD = "errors.partialDate2"; 
  public final static String ERROR_YEAR_YYYY = "kc.error.forminst.yearMustBeYYYY";
  public final static String ERROR_MONTH_DAY = "kc.error.forminst.invalidDayorMonth";
  public final static int DATE_LEN = 4;

  
  private String _value;
  private String _datatype;
  private Comparable _typeSafeValue;

  // The default error listener.
  class DefaultErrorListener implements ValidatorErrorListener {
    public boolean validatorError(Validator v, String errorKey) {
      ValidatorTypeSafe v1 = (ValidatorTypeSafe) v;
      v1.addErrorMessage(
          errorKey, 
          v1.getValue(),
          v1.getPropertyDisplayName(),          
          v1.getDatatype());
      return true;
    }
  }

  /**
   * Creates a new <code>ValidatorTypeSafe</code> validator.
   */
  public ValidatorTypeSafe() {
    super();
    addErrorKey(ERROR_KEY_UNKNOWN);
    addErrorKey(ERROR_KEY_INT);
    addErrorKey(ERROR_KEY_FLOAT);
    addErrorKey(ERROR_KEY_DATE);
    addErrorKey(ERROR_KEY_VPD);
    addErrorKey(ERROR_YEAR_YYYY);
    addErrorKey(ERROR_MONTH_DAY);
    addValidatorErrorListener(new DefaultErrorListener(), ERROR_KEY_UNKNOWN);
    addValidatorErrorListener(new DefaultErrorListener(), ERROR_KEY_INT);
    addValidatorErrorListener(new DefaultErrorListener(), ERROR_KEY_FLOAT);
    addValidatorErrorListener(new DefaultErrorListener(), ERROR_KEY_DATE);
    addValidatorErrorListener(new DefaultErrorListener(), ERROR_KEY_VPD); 
    addValidatorErrorListener(new DefaultErrorListener(), ERROR_YEAR_YYYY); 
    addValidatorErrorListener(new DefaultErrorListener(), ERROR_MONTH_DAY);  
  }
  
  /* (non-Javadoc)
   * @see com.ardais.bigr.javabeans.AbstractValidator#validate()
   */
   public BtxActionErrors validate() {
     String value = getValue();  
     String datatype = getDatatype();
     DetValueSet standardValues = 
       DetService.SINGLETON.getDataElementTaxonomy().getSystemStandardValues();
     if (!ApiFunctions.isEmpty(value) && !standardValues.containsValue(value)) {
       // verify that dates have four character years...
       if ((DetElementDatatypes.DATE.equals(datatype)) || (DetElementDatatypes.VPD.equals(datatype))) {
           if (!(checkYYYYDate(value))) {
             notifyValidatorErrorListener(ERROR_YEAR_YYYY); 
             }
           else if (!(checkMonthDay(value)))
             notifyValidatorErrorListener(ERROR_MONTH_DAY);
           }
       }
       try {
         setTypeSafeValue(KcFormInstanceValidationService.CastToComparableObj(value, datatype));
       }
       catch (ConversionException e) {         
         if (DetElementDatatypes.DATE.equals(datatype)) {
           notifyValidatorErrorListener(ERROR_KEY_DATE);
         }
         else if (DetElementDatatypes.VPD.equals(datatype)) {
           notifyValidatorErrorListener(ERROR_KEY_VPD);
         }
         else if (DetElementDatatypes.FLOAT.equals(datatype)) {
           notifyValidatorErrorListener(ERROR_KEY_FLOAT);
         }
         else if (DetElementDatatypes.INT.equals(datatype)) {
           notifyValidatorErrorListener(ERROR_KEY_INT);
         }
         else {
           notifyValidatorErrorListener(ERROR_KEY_UNKNOWN);
         } 
       }  
     return getActionErrors();
   }

  public String getValue() {
    return _value;
  }

  public void setValue(String value) {
    _value = value;
  }
  public String getDatatype() {
    return _datatype;
  }
  public void setDatatype(String datatype) {
    _datatype = datatype;
  }
  public Comparable getTypeSafeValue() {
    return _typeSafeValue;
  }
  public void setTypeSafeValue(Comparable typeSafeValue) {
    _typeSafeValue = typeSafeValue;
  }
  
  public boolean checkYYYYDate(String s) {
    if ((s == null) || (s.trim().length() == 0)) {
      return false;
    }
    else {
      String[] dateValues = ApiFunctions.splitAndTrim(s.toString(), "/");
      if ( (dateValues.length < 1) || (dateValues.length > 3)) {
        return false;
      }
      else {
        int i = dateValues.length;
        String dateValue = dateValues[i-1];
        if (dateValue.length() != DATE_LEN)
          return false;
        else
          return true;
        }
      }
    }
 
  private boolean checkMonthDay(String s) {
    if ((s == null) || (s.trim().length() == 0)) {
      return false;
    }
    else {
      String[] dateValues = ApiFunctions.splitAndTrim(s.toString(), "/");
      
      SimpleDateFormat sDate;
      switch (dateValues.length) {
        case 1:
          return true;
        case 2:
          sDate = new SimpleDateFormat("MM/yyyy");
          break;
        case 3:
          sDate = new SimpleDateFormat("MM/dd/yyyy");
          break;
        default:
          return false;
      }
      
      // do not accept dates with days > 31, months > 12
      // without the setLenient, it will treat 36 months
      //  as an addition of 3 years to the year value, etc...
      sDate.setLenient(false);  
      try {
        java.util.Date dt = sDate.parse(s);
        }
      catch (ParseException e) { 
        return false;
        }
      }     

    return true;
  }
 
}
