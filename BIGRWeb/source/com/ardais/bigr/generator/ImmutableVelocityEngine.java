package com.ardais.bigr.generator;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.Writer;
import java.util.Properties;

import org.apache.commons.collections.ExtendedProperties;
import org.apache.velocity.Template;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.context.Context;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.runtime.configuration.Configuration;

/**
 * This class wraps a {@link VelocityEngine} to make it unmodifiable.  All calls to methods
 * that would modify the engine throw {@link UnsupportedOperationException} exceptions.
 */
public class ImmutableVelocityEngine extends VelocityEngine {
  private VelocityEngine _engine = null;

  /**
   * Constructor for ImmutableVelocityEngine.
   */
  public ImmutableVelocityEngine(VelocityEngine engine) {
    super();

    if (engine == null) {
      throw new IllegalArgumentException("The engine parameter must not be null.");
    }

    _engine = engine;
  }

  /**
   * @see org.apache.velocity.app.VelocityEngine#addProperty(String, Object)
   */
  public void addProperty(String key, Object value) {
    throw new UnsupportedOperationException();
  }

  /**
   * @see org.apache.velocity.app.VelocityEngine#clearProperty(String)
   */
  public void clearProperty(String key) {
    throw new UnsupportedOperationException();
  }

  /**
   * @see org.apache.velocity.app.VelocityEngine#debug(Object)
   */
  public void debug(Object message) {
    _engine.debug(message);
  }

  /**
   * @see org.apache.velocity.app.VelocityEngine#error(Object)
   */
  public void error(Object message) {
    _engine.error(message);
  }

  /**
   * @see org.apache.velocity.app.VelocityEngine#evaluate(Context, Writer, String, InputStream)
   * @deprecated
   */
  public boolean evaluate(Context context, Writer writer, String logTag, InputStream instream)
    throws ParseErrorException, MethodInvocationException, ResourceNotFoundException, IOException {
    return _engine.evaluate(context, writer, logTag, instream);
  }

  /**
   * @see org.apache.velocity.app.VelocityEngine#evaluate(Context, Writer, String, Reader)
   */
  public boolean evaluate(Context context, Writer writer, String logTag, Reader reader)
    throws ParseErrorException, MethodInvocationException, ResourceNotFoundException, IOException {
    return _engine.evaluate(context, writer, logTag, reader);
  }

  /**
   * @see org.apache.velocity.app.VelocityEngine#evaluate(Context, Writer, String, String)
   */
  public boolean evaluate(Context context, Writer out, String logTag, String instring)
    throws ParseErrorException, MethodInvocationException, ResourceNotFoundException, IOException {
    return _engine.evaluate(context, out, logTag, instring);
  }

  /**
   * @see org.apache.velocity.app.VelocityEngine#getProperty(String)
   */
  public Object getProperty(String key) {
    return _engine.getProperty(key);
  }

  /**
   * @see org.apache.velocity.app.VelocityEngine#getTemplate(String, String)
   */
  public Template getTemplate(String name, String encoding)
    throws ResourceNotFoundException, ParseErrorException, Exception {
    return _engine.getTemplate(name, encoding);
  }

  /**
   * @see org.apache.velocity.app.VelocityEngine#getTemplate(String)
   */
  public Template getTemplate(String name)
    throws ResourceNotFoundException, ParseErrorException, Exception {
    return _engine.getTemplate(name);
  }

  /**
   * @see org.apache.velocity.app.VelocityEngine#info(Object)
   */
  public void info(Object message) {
    _engine.info(message);
  }

  /**
   * @see org.apache.velocity.app.VelocityEngine#init()
   */
  public void init() throws Exception {
    throw new UnsupportedOperationException();
  }

  /**
   * @see org.apache.velocity.app.VelocityEngine#init(Properties)
   */
  public void init(Properties p) throws Exception {
    throw new UnsupportedOperationException();
  }

  /**
   * @see org.apache.velocity.app.VelocityEngine#init(String)
   */
  public void init(String propsFilename) throws Exception {
    throw new UnsupportedOperationException();
  }

  /**
   * @see org.apache.velocity.app.VelocityEngine#invokeVelocimacro(String, String, String[], Context, Writer)
   */
  public boolean invokeVelocimacro(
    String vmName,
    String logTag,
    String[] params,
    Context context,
    Writer writer)
    throws Exception {
    return _engine.invokeVelocimacro(vmName, logTag, params, context, writer);
  }

  /**
   * @see org.apache.velocity.app.VelocityEngine#mergeTemplate(String, Context, Writer)
   */
  public boolean mergeTemplate(String templateName, Context context, Writer writer)
    throws ResourceNotFoundException, ParseErrorException, MethodInvocationException, Exception {
    return _engine.mergeTemplate(templateName, context, writer);
  }

  /**
   * @see org.apache.velocity.app.VelocityEngine#mergeTemplate(String, String, Context, Writer)
   */
  public boolean mergeTemplate(
    String templateName,
    String encoding,
    Context context,
    Writer writer)
    throws ResourceNotFoundException, ParseErrorException, MethodInvocationException, Exception {
    return _engine.mergeTemplate(templateName, encoding, context, writer);
  }

  /**
   * @see org.apache.velocity.app.VelocityEngine#setApplicationAttribute(Object, Object)
   */
  public void setApplicationAttribute(Object key, Object value) {
    throw new UnsupportedOperationException();
  }

  /**
   * @see org.apache.velocity.app.VelocityEngine#setConfiguration(Configuration)
   * @deprecated
   */
  public void setConfiguration(Configuration configuration) {
    throw new UnsupportedOperationException();
  }

  /**
   * @see org.apache.velocity.app.VelocityEngine#setExtendedProperties(ExtendedProperties)
   */
  public void setExtendedProperties(ExtendedProperties configuration) {
    throw new UnsupportedOperationException();
  }

  /**
   * @see org.apache.velocity.app.VelocityEngine#setProperty(String, Object)
   */
  public void setProperty(String key, Object value) {
    throw new UnsupportedOperationException();
  }

  /**
   * @see org.apache.velocity.app.VelocityEngine#templateExists(String)
   */
  public boolean templateExists(String templateName) {
    return _engine.templateExists(templateName);
  }

  /**
   * @see org.apache.velocity.app.VelocityEngine#warn(Object)
   */
  public void warn(Object message) {
    _engine.warn(message);
  }

}
