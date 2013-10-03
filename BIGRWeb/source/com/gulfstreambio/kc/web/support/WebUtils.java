package com.gulfstreambio.kc.web.support;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.Properties;

import javax.servlet.ServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.api.Escaper;
import com.ardais.bigr.util.UniqueIdGenerator;
import com.gulfstreambio.kc.form.Ade;
import com.gulfstreambio.kc.form.AdeElement;
import com.gulfstreambio.kc.form.AdeElementValue;
import com.gulfstreambio.kc.form.DataElement;
import com.gulfstreambio.kc.form.DataElementValue;
import com.gulfstreambio.kc.form.FormInstance;

public class WebUtils {
  
  public static final String NOTE_BUTTON_VALUE_ADD = "Add Note";
  public static final String NOTE_BUTTON_VALUE_DELETE = "Delete Note";
  public static final String STANDARD_VS_VALUE = "ss";
  public static final String BROAD_VS_VALUE = "bs";
  
  public static final String PROPERTY_KC_CONFIG_FILE = "com.gulfstreambio.kc.configuration";
  public static final String PROPERTY_JSP_PATH = "com.gulfstreambio.kc.jsppath";
  public static final String PROPERTY_HOST_ELEMENT_FACTORY = 
    "com.gulfstreambio.kc.HostElementRendererFactory";
  public static final String PROPERTY_KC_ELEMENT_FACTORY = 
    "com.gulfstreambio.kc.KcElementRendererFactory";
  
  private static final String DEFAULT_KC_ELEMENT_FACTORY = 
    "com.gulfstreambio.kc.web.support.KcElementRendererFactory";

  private static Properties _properties = new Properties();
  private static String _jspPath;
  private static ElementRendererFactory _hostElementRendererFactory;
  private static ElementRendererFactory _kcElementRendererFactory;
  
  private static boolean _isInitialized = false;
  static {
    initialize();
  }


  public static void appendQueryParameter(StringBuffer buf, String paramName, String paramValue) {
    try {
      buf.append(URLEncoder.encode(paramName, "UTF-8"));
      buf.append('=');
      buf.append(URLEncoder.encode(paramValue, "UTF-8"));                
    }
    catch (UnsupportedEncodingException e) {
      throw new ApiException(e);
    }
  }

  /**
   * Writes the specified text to the specified page context's <code>JspWriter</code>.
   *
   * @param  pageContext  the JSP page context
   * @param  text  the text to wtite
   * @throws JspException if an IOException is thrown by the underlying <code>JspWriter</code>
   */
  public static void tagWrite(PageContext pageContext, String text) throws ApiException {
    JspWriter writer = pageContext.getOut();
    try {
      writer.print(text);
    }
    catch (IOException e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
  }
  
  /**
   * Writes the HTML attribute specified by the name and value parameters to the specified 
   * page context's <code>JspWriter</code>.  The value is escaped appropriately, and a space is 
   * inserted before the attribute, so that multiple calls to this method can be made without 
   * the caller having to insert a space into the response stream.  If the value is 
   * <code>null</code>, then the attribute is not written.
   *
   * @param  pageContext  the JSP page context
   * @param  name  the attribute name
   * @param  value  the attribute value
   */
  public static void writeHtmlAttribute(PageContext pageContext, String name, String value) 
    throws JspException {
    // Note: do not change this to check for empty, since we explicitly want to allow
    // empty values to be written (e.g. value="" is acceptable and sometimes desired).
    if (value != null) {
      WebUtils.tagWrite(pageContext, " ");
      WebUtils.tagWrite(pageContext, name);
      WebUtils.tagWrite(pageContext, "=\"");
      WebUtils.tagWrite(pageContext, Escaper.htmlEscape(value));
      WebUtils.tagWrite(pageContext, "\"");
    }
  }

  /**
   * Writes the HTML attribute specified by the name and value parameters to the specified 
   * <code>StringBuffer</code>.  The value is escaped appropriately, and a space is 
   * inserted before the attribute, so that multiple calls to this method can be made without 
   * the caller having to insert a space into the response stream.  If the value is 
   * <code>null</code>, then the attribute is not written.
   *
   * @param  buf  the <code>StringBuffer</code>
   * @param  name  the attribute name
   * @param  value  the attribute value
   */
  public static void writeHtmlAttribute(StringBuffer buf, String name, String value) {
    // Note: do not change this to check for empty, since we explicitly want to allow
    // empty values to be written (e.g. value="" is acceptable and sometimes desired).
    if (value != null) {
      buf.append(' ');
      buf.append(name);
      buf.append("=\"");
      buf.append(Escaper.htmlEscape(value));
      buf.append("\"");
    }
  }

  public static String getFullPath(String contextPath) {
    return contextPath + WebUtils.getContextRelativePath();
  }

  public static String getContextRelativePath() {
    return _jspPath;
  }
  
  public static String getImageSourceExpand(String contextPath) {
    return WebUtils.getFullPath(contextPath) + "/images/MenuClosed.gif";
  }
  
  public static String getImageSourceCollapse(String contextPath) {
    return WebUtils.getFullPath(contextPath) + "/images/MenuOpened.gif";
  }
  
  public static ElementRendererFactory getKcElementRendererFactory() {
    if (_kcElementRendererFactory == null) {
      throw new ApiException("Attempt to get KC ElementRendererFactory when it was not properly initialized.");
    }
    return _kcElementRendererFactory;
  }
  
  public static ElementRendererFactory getHostElementRendererFactory() {
    if (_hostElementRendererFactory == null) {
      throw new ApiException("Attempt to get host ElementRendererFactory when it was not properly initialized.");
    }
    return _hostElementRendererFactory;
  }
  
  public static String getJavaScriptReferenceQueryElements() {
    return HtmlElementNamer.getJavaScriptReferenceQueryElements();
  }  

  public static String getJavaScriptQueryElementsToRequestParameter() {
    return HtmlElementNamer.getJavaScriptQueryElementsToRequestParameter();
  }

  /**
   * Returns a buffer to be used to append JavaScript, creating a new buffer if necessary.
   * Callers should call this method to get the JavaScript buffer, append their JavaScript to the
   * buffer, and then call {@link #writeJavaScriptBuffer(PageContext) writeJavaScriptBuffer} to 
   * write the contents of the buffer at the appropriate time.
   * 
   * @param  pageContext  the <code>PageContext</code> of the caller
   * @return  the <code>StringBuffer</code>
   */
  public static StringBuffer getJavaScriptBuffer(PageContext pageContext) {
    ServletRequest request = pageContext.getRequest();
    StringBuffer buf = (StringBuffer) request.getAttribute("jsbuffer");
    if (buf == null) {
      buf = new StringBuffer(1024);
      request.setAttribute("jsbuffer", buf);
    }
    return buf;
  }
  
  /**
   * Writes all buffered JavaScript to the response, surrounding the output with the appropriate
   * HTML SCRIPT tag.  This method writes the contents of the buffer obtained with the 
   * {@link #getJavaScriptBuffer(PageContext) getJavaScriptBuffer}.  If this buffer was not used,
   * then this method quietly does nothing.
   * <p>
   * After this method call the buffer obtained with <code>getJavaScriptBuffer</code> is 
   * effectively discarded, so <code>getJavaScriptBuffer</code> must be called again to obtain
   * a new buffer. 
   * 
   * @param  pageContext  the <code>PageContext</code> of the caller
   */
  public static void writeJavaScriptBuffer(PageContext pageContext) 
    throws JspException {
    StringBuffer buf = (StringBuffer) pageContext.getRequest().getAttribute("jsbuffer");
    if ((buf != null) && (buf.length() > 0)) {
      WebUtils.tagWrite(pageContext, "<script type=\"text/javascript\">");
      WebUtils.tagWrite(pageContext, buf.toString());    
      WebUtils.tagWrite(pageContext, "</script>");

      // Remove the buffer from the request to force another call to getJavaScriptBuffer, which
      // will create a new buffer.
      pageContext.getRequest().removeAttribute("jsbuffer");
    }
  }

  /**
   * Converts a serialized JSON form instance to a <code>FormInstance</code> object.
   * 
   * @param jsonFormInstance the serialized JSON form instance
   * @return  A <code>FormInstance</code> that contains the data elements and values specified in
   *          the serialized JSON form instance.
   */
  public static FormInstance convertToFormInstance(String jsonFormInstance) {
    String jsonString;
    FormInstance form = new FormInstance();
    try {
      JSONObject jsonObj = new JSONObject(jsonFormInstance);
      jsonString = jsonObj.isNull("formInstanceId") ? null : jsonObj.getString("formInstanceId"); 
      form.setFormInstanceId(jsonString);
      jsonString = jsonObj.isNull("formDefinitionId") ? null : jsonObj.getString("formDefinitionId"); 
      form.setFormDefinitionId(jsonString);
      jsonString = jsonObj.isNull("domainObjectId") ? null : jsonObj.getString("domainObjectId"); 
      form.setDomainObjectId(jsonString);
      jsonString = jsonObj.isNull("domainObjectType") ? null : jsonObj.getString("domainObjectType"); 
      form.setDomainObjectType(jsonString);
      JSONArray jsonDataElements = jsonObj.optJSONArray("elements");
      if (jsonDataElements != null) {
        for (int i = 0; i < jsonDataElements.length(); i++) {
          JSONObject jsonDataElement = jsonDataElements.getJSONObject(i);
          DataElement dataElement = new DataElement(jsonDataElement.getString("systemName"));
          form.addDataElement(dataElement);
          jsonString = jsonDataElement.isNull("valueNote") ? null : jsonDataElement.getString("valueNote"); 
          dataElement.setValueNote(jsonString);
          JSONArray jsonValues = jsonDataElement.optJSONArray("values");
          if (jsonValues != null) {
            for (int j = 0; j < jsonValues.length(); j++) {
              JSONObject jsonValue = jsonValues.getJSONObject(j);
              DataElementValue value = dataElement.createDataElementValue();
              jsonString = jsonValue.isNull("value") ? null : jsonValue.getString("value"); 
              value.setValue(jsonString);
              jsonString = jsonValue.isNull("valueOther") ? null : jsonValue.getString("valueOther"); 
              value.setValueOther(jsonString);
              JSONObject jsonAde = jsonValue.optJSONObject("ade");
              if (jsonAde != null) {
                Ade ade = value.createAde();
                JSONArray jsonAdeElements = jsonAde.optJSONArray("elements");
                if (jsonAdeElements != null) {
                  for (int k = 0; k < jsonAdeElements.length(); k++) {
                    JSONObject jsonAdeElement = jsonAdeElements.getJSONObject(k);
                    AdeElement adeElement = 
                      ade.createAdeElement(jsonAdeElement.getString("systemName"));
                    JSONArray jsonAdeValues = jsonAdeElement.optJSONArray("values");
                    if (jsonAdeValues != null) {
                      for (int m = 0; m < jsonAdeValues.length(); m++) {
                        JSONObject jsonAdeValue = jsonAdeValues.getJSONObject(m);
                        AdeElementValue adeValue = adeElement.createAdeElementValue();
                        jsonString = jsonAdeValue.isNull("value") ? null : jsonAdeValue.getString("value"); 
                        adeValue.setValue(jsonString);
                        jsonString = jsonAdeValue.isNull("valueOther") ? null : jsonAdeValue.getString("valueOther"); 
                        adeValue.setValueOther(jsonString);                        
                      } // for each ADE element values
                    } // if there are ADE element values
                  } // for each ADE element
                } // if there are ADE elements
              } // if the data element has an ADE
            } // for each data element value
          } // if there are data element values
        } // for each data element
      } // if there are data elements      
    }
    catch (JSONException e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    return form;
  }

  private static synchronized void initialize() {
    if (_isInitialized) return;

    readKcProperties();
    
    _jspPath = _properties.getProperty(PROPERTY_JSP_PATH, "");

    // Get the name of the KC element renderer factory, and instantiate it.
    String kcFactory = 
      _properties.getProperty(PROPERTY_KC_ELEMENT_FACTORY, DEFAULT_KC_ELEMENT_FACTORY);
    if (!ApiFunctions.isEmpty(kcFactory)) {
      try {
        _kcElementRendererFactory = 
          (ElementRendererFactory) Class.forName(kcFactory).newInstance();        
      }
      catch (Exception e) {
        ApiFunctions.throwAsRuntimeException(e);
      }
    }
    
    // Get the name of the host KC element renderer factory, and instantiate it.  As a small
    // optimization, if the host factory is the same as the KC factory, then don't instantiate
    // another one.
    String hostFactory = _properties.getProperty(PROPERTY_HOST_ELEMENT_FACTORY, "");
    if (!ApiFunctions.isEmpty(hostFactory)) {      
      if (hostFactory.equals(kcFactory)) {
        _hostElementRendererFactory = _kcElementRendererFactory;
      }
      else {
        try {
          _hostElementRendererFactory = 
            (ElementRendererFactory) Class.forName(hostFactory).newInstance();        
        }
        catch (Exception e) {
          ApiFunctions.throwAsRuntimeException(e);
        }        
      }
    }     
    
    _isInitialized = true;
  }

  private static synchronized void readKcProperties() {
    String configFile = System.getProperty(PROPERTY_KC_CONFIG_FILE);    
    FileInputStream fis = null;
    try {
      fis = new FileInputStream(configFile);
      _properties.load(fis);

      // Trim each value.
      Enumeration propNames = _properties.propertyNames();
      while (propNames.hasMoreElements()) {
        String propName = (String) propNames.nextElement();
        String propValue = _properties.getProperty(propName);
        if (propValue != null) {
          _properties.setProperty(propName, propValue.trim());
        }
      }
    }
    catch (IOException e) {
      throw new ApiException("Error in WebUtils.readKcProperties", e);
    }
    finally {
      if (fis != null) {
        try {
          fis.close();
        }
        catch (Exception e) {
        }
      }
    }
  }
  
  private WebUtils() {
    super();
  }

}
