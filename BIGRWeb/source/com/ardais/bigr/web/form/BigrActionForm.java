package com.ardais.bigr.web.form;

import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.ardais.bigr.beanutils.BigrBeanUtilsBean;
import com.ardais.bigr.iltds.btx.BTXDetails;
import com.ardais.bigr.iltds.btx.PopulatableFromBtxDetails;
import com.ardais.bigr.web.action.BigrActionMapping;

public abstract class BigrActionForm
    extends ActionForm
    implements PopulatableFromBtxDetails, PopulatesRequestFromBtxDetails {

    /**
     * @see ActionForm#reset(ActionMapping, HttpServletRequest)
     */
    public final void reset(
        ActionMapping mapping,
        HttpServletRequest request) {

        if ((mapping != null) && !(mapping instanceof BigrActionMapping)) {
            throw new IllegalArgumentException(
                "ActionMapping supplied to a BigrActionForm must be a "
                    + "BigrActionMapping: "
                    + mapping.getClass().getName());
        }

        doReset((BigrActionMapping) mapping, request);
    }

    /**
     * @see ActionForm#reset(ActionMapping, HttpServletRequest)
     */
    protected abstract void doReset(
        BigrActionMapping mapping,
        HttpServletRequest request);

    /**
     * @see ActionForm#validate(ActionMapping, HttpServletRequest)
     */
    public final ActionErrors validate(
        ActionMapping mapping,
        HttpServletRequest request) {

        if ((mapping != null) && !(mapping instanceof BigrActionMapping)) {
            throw new IllegalArgumentException(
                "ActionMapping supplied to a BigrActionForm must be a "
                    + "BigrActionMapping: "
                    + mapping.getClass().getName());
        }

        return doValidate((BigrActionMapping) mapping, request);
    }

    /**
     * @see ActionForm#validate(ActionMapping, HttpServletRequest)
     */
    protected abstract ActionErrors doValidate(
        BigrActionMapping mapping,
        HttpServletRequest request);

    public static void appendErrors(
        String formName,
        ActionErrors from,
        ActionErrors to) {
        if (from == null)
            return;
        if (to == null)
            to = new ActionErrors();
        Iterator keys = from.properties();
        while (keys.hasNext()) {
            String key = (String) keys.next();
            Iterator values = from.get(key);
            while (values.hasNext()) {
                to.add(formName + "." + key, (ActionError) values.next());
            }
        }
    }

    /**
     * Initialize the supplied BTXDetails object based on information in
     * this form, the current {@link BigrActionMapping}, and/or the
     * {@link HttpServletRequest}.  This will not be called if form validation
     * fails (doValidate returns a non-empty error collection).
     * 
     * <p>The default implementation copies bean properties from the form to
     * matching properties in the supplied BTXDetails instance.  Basic type
     * conversions are done, for example properties that are represented by a
     * String on the form but an integer in the BTXDetails are converted.  If
     * the data can't be converted for some reason a runtime exception is
     * thrown, so if this is an issue make sure your doValidate implementation
     * checks for the correct formats before this is called.  The conversions
     * are similar to those performed by the Jakarta Commons BeanUtils
     * library and more details are available at {@link BigrBeanUtilsBean}.
     * 
     * <p>If you need to copy properties in a different way, override this
     * method.
     */
    public void describeIntoBtxDetails(
        BTXDetails btxDetails0,
        BigrActionMapping mapping,
        HttpServletRequest request) {
            
        BigrBeanUtilsBean.SINGLETON.copyProperties(btxDetails0, this);
    }

    /**
     * This copies bean properties from the supplied BTXDetails to matching
     * properties in this form instance.  Basic type conversions are
     * done, for example properties that are represented by a String on
     * the BTXDetails but an integer in the form are converted.  If the
     * data can't be converted for some reason a runtime exception is thrown,
     * so if this is an issue make sure your doValidate implementation
     * checks for the correct formats before this is called.  The conversions
     * are similar to those performed by the Jakarta Commons BeanUtils
     * library and more details are available at {@link BigrBeanUtilsBean}.
     * 
     * <p>If you need to copy properties in a different way, override this
     * method.
     * 
     * @see com.ardais.bigr.iltds.btx.PopulatableFromBtxDetails#populateFromBtxDetails(BTXDetails)
     */
    public void populateFromBtxDetails(BTXDetails btxDetails) {
        BigrBeanUtilsBean.SINGLETON.copyProperties(this, btxDetails);
    }
    
    /**
     * Default "do-nothing" implementation.  Classes extending this one should
     * override this method as necessary to store information in the request,
     * session, etc.
     */
    public void populateRequestFromBtxDetails(BTXDetails btxDetails, 
                                              BigrActionMapping mapping,
                                              HttpServletRequest request) {
                                         
    }

}