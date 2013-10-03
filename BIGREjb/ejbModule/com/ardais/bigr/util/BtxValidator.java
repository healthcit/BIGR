package com.ardais.bigr.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.api.ValidateIds;
import com.ardais.bigr.iltds.btx.BTXDetails;
import com.ardais.bigr.iltds.btx.BtxActionError;

/**
 * @author dfeldman
 *
 * Utility class for methods that validate conditions in ways that are specific to the 
 * BTX framework, such as using BigrWebResources.properties values and creating BtxError
 * instances, or adding errors to a BTXDetails instance.
 */
public class BtxValidator {

  private BtxValidator() {
    throw new UnsupportedOperationException("Cannot instantiate BtxValidator class");
  }

  /**
   *  Check that the minString and maxString parameters form an appropriate, non-decimal
   *  percent range (0-100).  If there are errors, add them to the BtxDetails object, btx,
   *  (as BtxError instances inside of its BTXErrors instance).
   *  Conditions checked for are:  bad numeric formats, min<0, max>100, min>max.
   *  Null values for minString and maxString are interpreted as 0 and 100, respectively.  
   * 
   * If this method is unable to handle the bad input, a generic error will be added to btx.
   *  
   * @param fieldName - the name of the field, as displayed to the user.
   * @param minString - the String value of the minimum value.
   * @param btx - the BTXDetails object to update with all error values.
   * 
   */
  //  public static void validatePercentRange(String fieldName, String minString, String maxString, BTXDetails btx) {
  //    validateNumericRangeStrings(fieldName, minString, maxString, 0, 100, btx);
  //  }

  /**
   *  see validatePercentRange(String, String, String, BTXDetails).  This is the same but takes
   * int values rather than Strings representing ints.
   */
  public static void validatePercentRange(
    String fieldName,
    Integer min,
    Integer max,
    BTXDetails btx) {
    validateNumericRange(fieldName, min, max, 0, 100, btx);
  }

  /**
   *  See validatePercentRange method.  This behaves the same way, but the caller specifies the
   * min and max values rather than validating against min=0 and max=100.
   * 
   * @param fieldName - the name of the field, as displayed to the user.
   * @param minString - the String value of the minimum value.
   * @param minVal - the int min value to validate the minString against.
   * @param maxVal - the int max value to validate the maxString against.
   * @param btx - the BTXDetails object to update with all error values.
   */
  //  public static void validateNumericRangeStrings(String fieldName, String minString, String maxString, int minVal, int maxVal, BTXDetails btx) {
  //        Integer min;
  //        Integer max;
  //        try {
  //            min = minString==null ? null : new Integer(minString);
  //        } catch (NumberFormatException e) {
  //            btx.addActionError(new BtxActionError("error.badIntegerFormat", fieldName+ "(min value)", minString));
  //            min = minVal;
  //            minString = "<none>";
  //        }
  //        try {
  //            max = maxString == null ? maxVal :Integer.parseInt(maxString);
  //        } catch (NumberFormatException e) {
  //            btx.addActionError(new BtxActionError("error.badIntegerFormat", fieldName + "(max value)", minString));
  //            max = maxVal;
  //            maxString = "<none>";
  //        }    
  //        validateNumericRange(fieldName, min, max, minVal, maxVal, btx);
  //  }

  public static void validateNumericRange(
    String fieldName,
    Integer minInt,
    Integer maxInt,
    int minVal,
    int maxVal,
    BTXDetails btx) {
    if (minInt != null && maxInt != null) {
      int min = minInt.intValue();
      int max = maxInt.intValue();
      if (min > max)
        btx.addActionError(
          new BtxActionError(
            "error.minabovemax",
            fieldName,
            Integer.toString(min),
            Integer.toString(max)));
    }
    if (minInt != null) {
      int min = minInt.intValue();
      if (min < minVal)
        btx.addActionError(
          new BtxActionError(
            "error.belowmin",
            fieldName + "(min value)",
            Integer.toString(min),
            Integer.toString(minVal)));
      if (min > maxVal)
        btx.addActionError(
          new BtxActionError(
            "error.abovemax",
            fieldName + "(min value)",
            Integer.toString(min),
            Integer.toString(minVal)));
    }
    if (maxInt != null) {
      int max = maxInt.intValue();
      if (max < minVal)
        btx.addActionError(
          new BtxActionError(
            "error.belowmin",
            fieldName + "(max value)",
            Integer.toString(max),
            Integer.toString(maxVal)));
      if (max > maxVal)
        btx.addActionError(
          new BtxActionError(
            "error.abovemax",
            fieldName + "(max value)",
            Integer.toString(max),
            Integer.toString(maxVal)));
    }
  }

  public static void validateDateRange(
    String fieldName,
    String start,
    String end,
    BTXDetails btx) {
    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
    Date startDate = null;
    Date endDate = null;

    sdf.setLenient(false);

    //make sure the start date is valid    
    if (!ApiFunctions.isEmpty(start)) {
      try {
        startDate = sdf.parse(start);
        if (startDate.before(sdf.parse("1/1/1000"))) {
          btx.addActionError(new BtxActionError("error.notFourDigitYear", fieldName + " Start"));
        }
        if (startDate.after(new Date(System.currentTimeMillis()))) {
          btx.addActionError(new BtxActionError("error.dateMustBeBeforeCurrentDate", fieldName + " Start Date"));
        }
      }
      catch (ParseException e) {
        btx.addActionError(new BtxActionError("error.invalidDate", fieldName + " Start Date"));
      }
    }
    
    //make sure the end date is valid    
    if (!ApiFunctions.isEmpty(end)) {
      try {
        endDate = sdf.parse(end);
        if (endDate.before(sdf.parse("1/1/1000"))) {
          btx.addActionError(new BtxActionError("error.notFourDigitYear", fieldName + " End"));
        }
      }
      catch (ParseException e) {
        btx.addActionError(new BtxActionError("error.invalidDate", fieldName + " End"));
      }
    }
    
    // if we have two valid dates, verify the first is before the second
    if (startDate != null && endDate != null) {
      if (startDate.after(endDate)) {
        btx.addActionError(new BtxActionError("error.invalidDateRange", fieldName, start, end));
      }
    }

  }

  /**
  * ensure that all ids are valid ids for some product (Tissue Sample or RNA at this writing)
  * @param ids - the strings to check
  * @param btx - the BTXDetails object to add errors to, if found
  */
  public static void validateProductIds(String[] ids, BTXDetails btx) {
    ValidateIds validId = new ValidateIds();

    if (ids != null) {
      for (int i = 0; i < ids.length; i++) {
        if (ApiFunctions.isEmpty(validId.validate(ids[i], ValidateIds.TYPESET_SAMPLE, false)) && !IdValidator.validRnaId(ids[i]))
          btx.addActionError(new BtxActionError("library.ss.error.invalidSampleId", ids[i]));
      }
    }
  }

  public static void validateSampleIds(String[] ids, BTXDetails btx) {
    ValidateIds validId = new ValidateIds();

    if (ids != null) {
      for (int i = 0; i < ids.length; i++) {
        if (ApiFunctions.isEmpty(validId.validate(ids[i], ValidateIds.TYPESET_SAMPLE, false)))
          btx.addActionError(new BtxActionError("library.ss.error.invalidSampleId", ids[i]));
      }
    }
  }

  public static void validateDonorIds(String[] ids, BTXDetails btx) {
    ValidateIds validId = new ValidateIds();

    if (ids != null) {
      for (int i = 0; i < ids.length; i++) {
        if (ApiFunctions.isEmpty(validId.validate(ids[i], ValidateIds.TYPESET_DONOR, false)))
          btx.addActionError(new BtxActionError("library.ss.error.invalidDonorId", ids[i]));
      }
    }
  }

  public static void validateCaseIds(String[] ids, BTXDetails btx) {
    ValidateIds validId = new ValidateIds();

    if (ids != null) {
      for (int i = 0; i < ids.length; i++) {
        if (ApiFunctions.isEmpty(validId.validate(ids[i], ValidateIds.TYPESET_CASE, false)))
          btx.addActionError(new BtxActionError("library.ss.error.invalidCaseId", ids[i]));
      }
    }
  }

  public static void validateRnaIds(String[] ids, BTXDetails btx) {
    if (ids != null) {
      for (int i = 0; i < ids.length; i++) {
        if (!IdValidator.validRnaId(ids[i]))
          btx.addActionError(new BtxActionError("library.ss.error.invalidRnaId", ids[i]));
      }
    }
  }

  public static void validateFixedList(
    String fieldName,
    String val,
    String[] validList,
    BTXDetails btx) {
    if (val == null) {
      btx.addActionError(new BtxActionError("error.noValue", fieldName));
      return;
    }

    boolean inList = false;
    for (int i = 0; i < validList.length; i++) {
      if (val.equals(validList[i])) {
        inList = true;
        break;
      }
    }
    if (!inList)
      btx.addActionError(
        new BtxActionError("error.valueNotInList", fieldName, validOptionsDisplay(validList)));

  }

  public static void validateDbLikeSearch(String fieldName, String keywordString, BTXDetails btx) {
    if (!ApiFunctions.isEmpty(keywordString)) {
      try {
        SearchTermValidator.validateDBLikeString(keywordString);
      }
      catch (SearchTermValidator.ValidationException e) {
        btx.addActionError(
          new BtxActionError("error.invalidDbLikeSearch", fieldName, e.getMessage()));
      }
    }
  }

  public static void validateIntermediaSearch(
    String fieldName,
    String keywordString,
    BTXDetails btx) {
    if (!ApiFunctions.isEmpty(keywordString)) {
      try {
        SearchTermValidator.validateIntermediaSearchString(keywordString);
      }
      catch (SearchTermValidator.ValidationException e) {
        btx.addActionError(
          new BtxActionError("error.invalidIntermediaSearch", fieldName, e.getMessage()));
      }
    }
  }

  private static String validOptionsDisplay(String[] validOptions) {
    int len = validOptions.length;
    StringBuffer sb = new StringBuffer();
    for (int i = 0; i < len; i++) {
      sb.append(validOptions[i]);
      if (i != len - 1)
        sb.append(", ");
    }
    return sb.toString();
  }
}
