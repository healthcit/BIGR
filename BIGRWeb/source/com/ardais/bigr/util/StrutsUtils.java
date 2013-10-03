package com.ardais.bigr.util;

import java.util.Iterator;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;

import com.ardais.bigr.iltds.btx.BtxActionError;
import com.ardais.bigr.iltds.btx.BtxActionErrors;

/**
 * This class contains various Struts-related static utility methods.
 */
public class StrutsUtils {

  /**
   * All methods in this class should be static and so there's no need
   * to ever instantiate it.  To enforce that we make the constructor
   * private.
   */
  private StrutsUtils() {
    super();
  }

  /**
   * Adds contents of one <code>ActionErrors</code> to another <code>ActionErrors</code>.
   * @param <code>ActionErrors</code> AddTo 
   * @param <code>ActionErrors</code> toBeAdded
   * @return <code>ActionErrors</code> 
   */
  public static ActionErrors addActionErrors(ActionErrors addTo, ActionErrors toBeAdded) {
    if ((addTo == null) && (toBeAdded == null)) {
      return null;
    }
    if ((addTo == null) && (toBeAdded != null)) {
      return toBeAdded;
    }
    if (toBeAdded == null) {
      return addTo;
    }

    Iterator properties = toBeAdded.properties();
    while (properties.hasNext()) {
      String property = (String) properties.next();
      Iterator errorsForProperty = toBeAdded.get(property);
      while (errorsForProperty.hasNext()) {
        ActionError error = (ActionError) errorsForProperty.next();
        addTo.add(property, error);
      }
    }

    return addTo;
  }

  public static ActionErrors convertBtxActionErrorsToStruts(BtxActionErrors btxActionErrors) {
    if (btxActionErrors == null) {
      return null;
    }

    ActionErrors strutsErrors = new ActionErrors();

    Iterator properties = btxActionErrors.properties();
    while (properties.hasNext()) {
      String property = (String) properties.next();
      Iterator errorsForProperty = btxActionErrors.get(property);
      while (errorsForProperty.hasNext()) {
        BtxActionError btxError = (BtxActionError) errorsForProperty.next();
        strutsErrors.add(property, StrutsUtils.convertBtxActionErrorToStruts(btxError));
      }
    }

    return strutsErrors;
  }

  public static ActionError convertBtxActionErrorToStruts(BtxActionError btxActionError) {
    String key = btxActionError.getKey();
    String[] values = btxActionError.getValues();

    switch (values.length) {
      case 0 :
        return new ActionError(key);
      case 1 :
        return new ActionError(key, values[0]);
      case 2 :
        return new ActionError(key, values[0], values[1]);
      case 3 :
        return new ActionError(key, values[0], values[1], values[2]);
      case 4 :
        return new ActionError(key, values[0], values[1], values[2], values[3]);
      default :
        throw new RuntimeException(
          "Unexpected number of BtxActionError replacement parameters ("
            + values.length
            + ") for error key "
            + key);
    }
  }
}
