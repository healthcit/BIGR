package com.ardais.bigr.validation;

/**
 * An interface for providing a shared context for validating a collection of validators.  This
 * interface has no methods and is simply a marker interface to indicate that an implementor
 * supplies such a context.  As such all callers of its implementors must either cast to 
 * the concrete implementing class or use reflection to determine the implementing class'
 * methods.  Nevertheless, it is useful to have such an interface to allow its type to be passed
 * to method calls, such as 
 * {@link ValidatorContextUpdater#update(ValidatorContext) update}.
 */
public interface ValidatorContext {

}
