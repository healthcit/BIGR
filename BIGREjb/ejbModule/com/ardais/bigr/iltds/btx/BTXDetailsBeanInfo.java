package com.ardais.bigr.iltds.btx;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;
import java.util.ArrayList;
import java.util.List;

import com.ardais.bigr.api.ApiFunctions;

/**
 * Define a special BeanInfo class for BTXDetails that doesn't expose
 * the {@link BTXDetails#getDetailsAsHTML()} and
 * {@link BTXDetails#getDirectlyInvolvedObjects()} methods as bean properties.
 * The main reason this is important is that in the BTX framework, we use
 * {@link com.ardais.bigr.beanutils.BigrBeanUtilsBean#copyProperties(Object,Object)}
 * to copy information from a BTXDetails object to other objects, such as
 * an ActionForm.  In some cases these methods would throw exceptions because
 * they were called on incomplete BTXDetails objects.  Rather than require
 * these two methods to be robust enough to handle this unusual situation,
 * we hide them via this BeanInfo class so that the reflection-based
 * copyProperties method won't try to copy them.  It seems fine to hide these
 * because these two methods were really only meant to be used by the BTX
 * history recording/display mechanism.
 */
public class BTXDetailsBeanInfo extends SimpleBeanInfo {

    /**
     * Constructor for BTXDetailsBeanInfo.
     */
    public BTXDetailsBeanInfo() {
        super();
    }

    /**
     * See the class comment on this class for details on what this does.
     * 
     * @see java.beans.BeanInfo#getPropertyDescriptors()
     */
    public PropertyDescriptor[] getPropertyDescriptors() {
        BeanInfo info = null;
        try {
            info =
                Introspector.getBeanInfo(
                    BTXDetails.class,
                    Introspector.IGNORE_IMMEDIATE_BEANINFO);
        }
        catch (IntrospectionException e) {
            ApiFunctions.throwAsRuntimeException(e);
        }
        PropertyDescriptor[] descs = info.getPropertyDescriptors();

        List propertyList = new ArrayList(descs.length);
        for (int i = 0; i < descs.length; i++) {
            PropertyDescriptor desc = descs[i];
            String propName = desc.getName();
            
            // exclude the properties that this class is meant to exclude
            //
            if (!propName.equalsIgnoreCase("detailsAsHTML")
                && !propName.equalsIgnoreCase("directlyInvolvedObjects")) {
                propertyList.add(desc);
            }
        }

        return (PropertyDescriptor[]) propertyList.toArray(
            new PropertyDescriptor[0]);
    }

}
