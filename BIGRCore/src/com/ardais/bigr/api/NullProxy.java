package com.ardais.bigr.api;

/**
 * The Apache Commins XML Digester has some problems with passing null as parameters to
 * method calls in CallMethodRule.  Sometimes when a parameter is null it decides not to
 * actually call the method.  The NullProxy class exists to allow us to work around this problem.
 * It is simply a non-null object that stands in for null during XML parsing.  It doesn't have
 * any behavior.
 */
public class NullProxy {

  public NullProxy() {
    super();
  }

}
