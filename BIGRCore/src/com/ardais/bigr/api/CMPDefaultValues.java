package com.ardais.bigr.api;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

public final class CMPDefaultValues implements java.io.Serializable {
    /**
     * The bean class whose CMP field default values are described by this
     * object.
     */
    private Class _class = null;

    /**
     * The default values of the bean's CMP fields.  Each element of this
     * array is a Map.Entry object whose key is a Field object and whose
     * value is the bean field's default value.  Default values for fields of
     * a primitive type are represented by an instance of the wrapper
     * class for that type (for example, Integer).
     */
    private Map.Entry[] _fieldDefaultValues = new Map.Entry[0];

    /**
     * Create an object that represents the default values of data fields in
     * a container-managed entity bean of the specified class.
     *
     * <p><b>IMPORTANT</b>:  Reflection is used to find the data fields and
     * their default values.  The default value for a field named x must be
     * represented by a public static final field named DEFAULT_x whose
     * value is the default value.  The type of DEFAULT_x must be assignable
     * to field x.  If any of these conditions aren't met, an exception or
     * runtime exception is thrown.
     * 
     * <p><b>Textually, the declarations and
     * initializers of these DEFAULT_x fields must appear before the declaration
     * of the field that is initialized to an instance of CMPDefaultValues
     * for that class.  Otherwise, the DEFAULT_x fields may not have their
     * initial values set before this class tries to retrieve those values
     * via reflection.</b>
     * 
     * <p>We assume that any field on the specified class or any of its
     * superclasses that is public, not final, not static, and not transient
     * is a container-managed field of the class.
     *
     * @param clazz the class of the CMP entity bean.  This must be the bean
     *    class itself, and not one of the related classes such as the home or
     *    remote interface.  If the specified class does not implement
     *    javax.ejb.EntityBean, a runtime exception is thrown.
     */
    public CMPDefaultValues(Class clazz) {
        // NOTE: Throwing ApiExceptions here causes VAJ to hang for some
        // reason while trying to initialize ApiProperties, so we don't
        // throw ApiExceptions.

        super();

        try {
            if (clazz == null) {
                throw new IllegalArgumentException("CMPDefaultValues constructor: class must not be null.");
            }

            if (!javax.ejb.EntityBean.class.isAssignableFrom(clazz)) {
                throw new IllegalArgumentException(
                    "CMPDefaultValues constructor: the specified class ("
                        + clazz.getName()
                        + ") does not implement the javax.ejb.EntityBean interface.");
            }

            _class = clazz;

            // Initialize _fieldDefaultValues.
            //
            initializeFieldDefaultValues();
        }
        catch (Exception e) {
            // Normally we don't log an exception if we're also throwing it --
            // we save logging for the last possible catcher.  But in this
            // case the consequences of a failure in this class can be very
            // serious -- EJB fields may not get initialized correctly,
            // potentially resulting in transaction rollbacks and even data
            // corruptions.
            //
            ApiLogger.log(
                "SERIOUS ERROR in entity bean data field initialization.  "
                    + "This exception may not have obvious symptoms but data corruptions and "
                    + "transaction failures may result from this.  Please investigate and correct the problem.",
                e);

            ApiFunctions.throwAsRuntimeException(e);
        }
    }

    /**
     * Assign default values to the container-managed fields of the specified
     * object.
     * 
     * <p>Setting field values using reflection is much slower than setting
     * field values directly, and if this is called a very large number of
     * times it will add up.  If this ever becomes a problem, there are other
     * more complicated ways this could be done that should be faster.
     * To give a feel for the speed, it takes about one second to assign
     * values to fields on 15,000 objects (nine fields per object) on a
     * 1GHz desktop computer.
     *
     * @param obj the object to assign values to.  This object must not be
     *    null and must be of the same class as returned by
     *    {@link #getBeanClass() getBeanClass}.
     */
    public void assignTo(javax.ejb.EntityBean obj) {
        // NOTE: Throwing ApiExceptions here causes VAJ to hang for some
        // reason while trying to initialize ApiProperties, so we don't
        // throw ApiExceptions.

        // We put the "try" right at the beginning because it is very important
        // that we catch and log all exceptions thrown from this method.  See
        // the catch block below for more details.
        //
        try {
            if (obj == null) {
                throw new IllegalArgumentException(
                    "CMPDefaultValue.assignTo (for class "
                        + _class.getName()
                        + "):  object must not be null.");
            }

            if (!_class.equals(obj.getClass())) {
                throw new IllegalArgumentException(
                    "CMPDefaultValues.assignTo: wrong object type (expected "
                        + _class.getName()
                        + ", got "
                        + obj.getClass().getName()
                        + ").");
            }

            int arrayLen = _fieldDefaultValues.length;
            for (int j = 0; j < arrayLen; j++) {
                Map.Entry mapEntry = _fieldDefaultValues[j];
                Field field = (Field) mapEntry.getKey();
                Object defaultValue = mapEntry.getValue();
                field.set(obj, defaultValue);
            }
        }
        catch (Exception e) {
            // Normally we don't log an exception if we're also throwing it --
            // we save logging for the last possible catcher.  But in this
            // case the consequences of a failure in this class can be very
            // serious -- EJB fields may not get initialized correctly,
            // potentially resulting in transaction rollbacks and even data
            // corruptions.
            //
            ApiLogger.log(
                "SERIOUS ERROR in entity bean data field initialization.  "
                    + "This exception may not have obvious symptoms but data corruptions and "
                    + "transaction failures may result from this.  Please investigate and correct the problem.",
                e);

            ApiFunctions.throwAsRuntimeException(e);
        }
    }

    /**
     * Return the bean class whose CMP field default values are described by
     * this object.
     * 
     * @return the bean class.
     */
    public Class getBeanClass() {
        return _class;
    }

    /**
     * Return the default value for the specified field, which must be a field
     * of the class represented by this CMPDefaultValues object instance.
     * 
     * <p>We find the default value using reflection.  We expect that for a
     * field named x, there is a corresponding public static final field
     * named DEFAULT_x whose value is the default value of field x.  The
     * type of DEFAULT_x must be assignable to field x.  If any of these
     * conditions aren't met, an exception or runtime exception is thrown.
     * 
     * <p>IMPORTANT:  See the comments on the constructor
     * ({@link #CMPDefaultValues(Class)}) for an important note
     * how the declarations of the DEFAULT_x fields must be textually ordered
     * in their class definition for this method to behave correctly.
     *
     * @return the default value for the specified field.  If the default value
     *    field is of a primitive type, the object returned is an instance of
     *    the wrapper class for that primitive type (for example, Integer).
     */
    private Object getDefaultValue(Field field)
        throws NoSuchFieldException, IllegalAccessException {
        // NOTE: Throwing ApiExceptions here causes VAJ to hang for some reason while trying to
        // initialize ApiProperties, so we don't throw ApiExceptions.

        String defaultFieldName = "DEFAULT_" + field.getName();

        Field defaultField = null;
        try {
            defaultField = _class.getField(defaultFieldName);
        }
        catch (NoSuchFieldException e) {
            throw new RuntimeException(
                "CMPDefaultValues.getDefaultValue: There must be a public static final field named "
                    + defaultFieldName
                    + " in class "
                    + _class.getName()
                    + ".");
        }

        // Verify that the default value field is static and final.
        //
        int modifiers = defaultField.getModifiers();
        if ((!Modifier.isFinal(modifiers))
            || (!Modifier.isStatic(modifiers))) {

            throw new RuntimeException(
                "CMPDefaultValues.getDefaultValue: There must be a public static final field named "
                    + defaultFieldName
                    + " in class "
                    + _class.getName()
                    + ".");
        }

        // Verify that the type of the default value field is assignable to the
        // type of the original field.
        //
        if (!field.getType().isAssignableFrom(defaultField.getType())) {
            throw new RuntimeException(
                "CMPDefaultValues.getDefaultValue: The type of field "
                    + defaultFieldName
                    + " ("
                    + defaultField.getType().getName()
                    + ") must be assignable to the type of field "
                    + field.getName()
                    + " in class "
                    + _class.getName()
                    + " ("
                    + field.getType().getName()
                    + ").");
        }

        return defaultField.get(null);
    }

    /**
     * Initialize the mapping of container-managed fields to their default
     * values.
     * 
     * <p>We assume that any field on the specified class or any of its
     * superclasses that is public, not final, not static, and not transient
     * is a container-managed field of the class.
     * 
     * <p>We expect that for each container-managed field x, there is a
     * corresponding public static final field named DEFAULT_x whose value
     * is the default value of field x.  See the
     * {@link #getDefaultValue(Field) getDefaultValue} method
     * for more details on conventions that default value fields must obey.
     */
    private void initializeFieldDefaultValues()
        throws NoSuchFieldException, IllegalAccessException {
        // NOTE: Throwing ApiExceptions here causes VAJ to hang for some reason while trying to
        // initialize ApiProperties, so we don't throw ApiExceptions.

        Map map = new HashMap();

        Field[] fields = _class.getFields();
        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            int modifiers = field.getModifiers();
            if ((!Modifier.isFinal(modifiers))
                && (!Modifier.isStatic(modifiers))
                && (!Modifier.isTransient(modifiers))) {

                map.put(field, getDefaultValue(field));
            }
        }

        _fieldDefaultValues =
            (Map.Entry[]) map.entrySet().toArray(new Map.Entry[0]);
    }
}
