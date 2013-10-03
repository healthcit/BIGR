package com.ardais.bigr.iltds.btx;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;


/**
 * <p>A class that encapsulates the error messages being reported in
 * the course of performing a business transaction.</p>
 * 
 * <p>This class mirrors the Struts <code>ActionErrors</code> class.
 * We can't use the <code>ActionErrors</code> class directly in the BTX
 * framework, since the Struts classes are designed to be used only within
 * a web application and not, for example, in an EJB component.  The BTX
 * framework provides a way to construct a Struts <code>ActionErrors</code>
 * object that represents the same errors as a given
 * <code>BtxActionErrors</code> object.</p>
 *
 * <p>Each individual error is described by an <code>BtxActionError</code>
 * object, which contains a message key (to be looked up in an appropriate
 * Struts message resources database), and up to four placeholder arguments
 * used for parametric substitution in the resulting message.  Since the
 * mapping of message keys to actual messages is maintained by Struts,
 * a <code>BtxActionError</code> object can only be converted to its final
 * message text with web application code (not elsewhere, such as EJB code).</p>
 *
 * <p><strong>IMPLEMENTATION NOTE</strong> - It is assumed that these objects
 * are created and manipulated only within the context of a single thread.
 * Therefore, no synchronization is required for access to internal
 * collections.</p>
 */
public class BtxActionErrors implements Serializable {
    
    // ARDAIS NOTE: The code for this class is derived directly from the Struts
    // ActionErrors source code, with all due credit to the Struts authors.
    // It has not been rewritten to use Ardais coding conventions.
    //
    // In the BTX framework, every BTXDetails object has a non-null
    // BtxActionErrors object embedded in it.  To reduce space requirements
    // in some situations where many BTXDetails objects are in memory at
    // once (but with no errors), we've made some small changes to this class
    // so that the errors HashMap isn't created until it is actually needed.
    // We've also made a change to the add method so that it won't add the
    // exact same error more than once to the collection.

    // ----------------------------------------------------- Manifest Constants


    /**
     * The "property name" marker to use for global errors, as opposed to
     * those related to a specific property.
     */
    public static final String GLOBAL_ERROR =
        "org.apache.struts.action.GLOBAL_ERROR";


    // ----------------------------------------------------- Instance Variables


    /**
     * The accumulated set of <code>BtxActionError</code> objects (represented
     * as an ArrayList) for each property, keyed by property name.
     */
    protected HashMap errors = null;


    // --------------------------------------------------------- Public Methods


    /**
     * Add an error message to the set of errors for the specified property.
     * This is slightly different from the same method on the Struts
     * ActionErrors class:  this method won't add the BtxActionError
     * to the errors if the errors already contain an <code>equals</code>
     * BtxActionError object under the same <code>property</code>.
     *
     * @param property Property name (or BtxActionErrors.GLOBAL_ERROR)
     * @param error The error message to be added
     */
    public void add(String property, BtxActionError error) {
        
        if (errors == null) errors = new HashMap();

        ArrayList list = (ArrayList) errors.get(property);
        if (list == null) {
            list = new ArrayList();
            errors.put(property, list);
        }
        if (! list.contains(error)) {
            list.add(error);    
        }
    }

    /**
     * Adds all of the errors from the input BtxActionErrors to this BtxActionErrors. 
     * 
     * @param errors the BtxActionErrors that are to be added.  If this is null or empty, then
     *               no action is taken.
     */
    public void addErrors(BtxActionErrors newErrors) {
        if (newErrors != null && !newErrors.empty()) {
            Iterator props = newErrors.properties();
            while (props.hasNext()) {
                String prop = (String) props.next();
                Iterator i = newErrors.get(prop);
                while (i.hasNext()) {
                    add(prop, (BtxActionError) i.next());
                }
            }
        }
    }

    /**
     * Clear all error messages recorded by this object.
     */
    public void clear() {

        if (errors != null) errors.clear();

    }


    /**
     * Return <code>true</code> if there are no error messages recorded
     * in this collection, or <code>false</code> otherwise.
     */
    public boolean empty() {

        return ((errors == null) || (errors.size() == 0));

    }


    /**
     * Return the set of all recorded error messages, without distinction
     * by which property the messages are associated with.  If there are
     * no error messages recorded, an empty enumeration is returned.
     */
    public Iterator get() {

        if ((errors == null) || (errors.size() == 0))
            return (Collections.EMPTY_LIST.iterator());
        ArrayList results = new ArrayList();
        Iterator props = errors.keySet().iterator();
        while (props.hasNext()) {
            String prop = (String) props.next();
            Iterator errors = ((ArrayList) this.errors.get(prop)).iterator();
            while (errors.hasNext())
                results.add(errors.next());
        }
        return (results.iterator());

    }


    /**
     * Return the set of error messages related to a specific property.
     * If there are no such errors, an empty enumeration is returned.
     *
     * @param property Property name (or BtxActionErrors.GLOBAL_ERROR)
     */
    public Iterator get(String property) {
        
        if (errors == null)
            return (Collections.EMPTY_LIST.iterator());

        ArrayList list = (ArrayList) errors.get(property);
        if (list == null)
            return (Collections.EMPTY_LIST.iterator());
        else
            return (list.iterator());

    }


    /**
     * Return the set of property names for which at least one error has
     * been recorded.  If there are no errors, an empty Iterator is returned.
     * If you have recorded global errors, the String value of
     * <code>BtxActionErrors.GLOBAL_ERROR</code> will be one of the returned
     * property names.
     */
    public Iterator properties() {
        
        if (errors == null)
            return (Collections.EMPTY_LIST.iterator());

        return (errors.keySet().iterator());

    }


    /**
     * Return the number of errors recorded for all properties (including
     * global errors).  <strong>NOTE</strong> - it is more efficient to call
     * <code>empty()</code> if all you care about is whether or not there are
     * any error messages at all.
     */
    public int size() {
        
        if (errors == null) return 0;

        int total = 0;
        Iterator keys = errors.keySet().iterator();
        while (keys.hasNext()) {
            String key = (String) keys.next();
            ArrayList list = (ArrayList) errors.get(key);
            total += list.size();
        }
        return (total);

    }


    /**
     * Return the number of errors associated with the specified property.
     *
     * @param property Property name (or BtxActionErrors.GLOBAL_ERROR)
     */
    public int size(String property) {
        
        if (errors == null) return 0;

        ArrayList list = (ArrayList) errors.get(property);
        if (list == null)
            return (0);
        else
            return (list.size());

    }


}
