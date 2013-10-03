package com.gulfstreambio.bigr.integration.aperio;

import java.io.BufferedReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForward;
import org.json.JSONObject;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.api.ApiProperties;
import com.ardais.bigr.iltds.btx.BtxActionErrors;
import com.ardais.bigr.iltds.btx.BtxActionMessage;
import com.ardais.bigr.iltds.btx.BtxActionMessages;
import com.ardais.bigr.web.action.BigrActionMapping;
import com.ardais.bigr.web.form.BigrActionForm;
import com.gulfstreambio.bigr.integration.IntegrationAction;

/**
 * Performs all server-side work related to integrating the Aperio imaging system with BIGR.
 * Supports querying for the image id of an H&E image for a specified sample using a SOAP 
 * message.  Since the user must logon and present a valid authentication token to the Aperio 
 * DataServer method, this action ensures that a user is logged on.  This action works in 
 * conjunction with JavaScript in bigrAperio.js.
 */
public class AperioAction extends IntegrationAction {

  /*
   * Since there is no better place to put this information, we'll put it here.
   * 
   * To call Spectrum directly with a predefined query (in this case Aperio's "BlockId" is equal to
   * BIGR's sample id, add the following URL to Maintain URLs, of course substituting the actual
   * ImageServer address or host name for <image-server-host>.  This will bring up a new window 
   * with Spectrum, and thus does not go to the BIGR server and does not need to be supported by 
   * this action.
   *
   * http://<image-server-host>/SubmitSearch.php?
   *   TableName=Slide&SearchTitle=&FieldName%5B%5D=BlockId&Table%5B%5D=Slide
   *   &FieldOperator%5B%5D=%3D&FieldValue%5B%5D=$$sample_id$$&FieldValue2%5B%5D=
   *
   * To get the H&E image for a specified sample id, add the following URL to Maintain URLs,
   * substituting the actual ImageServer address or host name for <image-server-host> and the 
   * actual DataServer address or host name for <data-server-host>.  This will call a JavaScript
   * function that will perform an AJAX call to this action to get the image id, and username
   * and password if necessary to logon to the dataserver.
   * 
   * javascript:GSBIO.bigr.integration.aperio.HeImage.init(
   *   {sampleId:\'$$sample_id$$\',dataServerBaseUrl:\'http://<data-server-host>\',
   *    imageServerBaseUrl:\'http://<image-server-host>\'});
   *   GSBIO.bigr.integration.aperio.HeImage.displayHeImage();
   */

  public String getPartnerName() {
    return "aperio";
  }
  
  private void getSoapEnvelopeDetails(StringBuffer buff) {
    buff.append(" xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\"");
    buff.append(" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"");
    buff.append(" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\">");    
  }
  
  protected ActionForward doPerform(BigrActionMapping mapping, BigrActionForm form,
                                    HttpServletRequest request, HttpServletResponse response)
      throws Exception {
    try {
      
      HttpSession session = request.getSession();
      AperioForm aperioForm = (AperioForm) form;
      String dataServerHost = ApiProperties.getProperty("aperio.dataServer.host");
      String dataServerPort = ApiProperties.getProperty("aperio.dataServer.port");
      String dataServerContextString = ApiProperties.getProperty("aperio.dataServer.contextString");
 //     String baseUrl = aperioForm.getDataServerBaseUrl();
      String baseUrl = "http://" + dataServerHost + ":" + dataServerPort + dataServerContextString;
      boolean validToken = false;
      JSONObject jsonObject = null;
      String forward = null;

      // If a username or password were entered, then attempt to login to Spectrum using the
      // username and password.  If we could not logon, then forward back to the logon page to
      // display the error message and allow the user to make another attempt.
      String username = aperioForm.getSpectrumUsername();
      String password = aperioForm.getSpectrumPassword();
      if (!ApiFunctions.isEmpty(username) || !ApiFunctions.isEmpty(password)) {
        String token = logonToSpectrum(request, baseUrl, username, password);
        if (token == null) {
          jsonObject = new JSONObject();
          jsonObject.put("dialog", true);
          forward = "logon";
        }
        else {
          setSessionAttribute(session, "token", token);
          validToken = true;
        }
      }
      
      // If there is no authentication token in the session, then either the user has not attempted
      // to logon or their attempt failed.  In either case, make them logon.
      String token = (String) getSessionAttribute(session, "token");
      if (token == null) {
        jsonObject = new JSONObject();
        jsonObject.put("dialog", true);
        forward = "logon";
      }

      // If we have a token, then check to make sure that it is still valid (if we did not just
      // logon).
      else if (!validToken) {
        Boolean valid = isValidToken(request, baseUrl, token);
        if ((valid == null) || (!valid.booleanValue())) {
          jsonObject = new JSONObject();
          jsonObject.put("dialog", true);
          forward = "logon";
        }
        else {
          validToken = true;
        }
      }

      // If we have a valid token, then get the Spectrum image id of the H&E image that correpsonds 
      // to the sample id and return it, along with the logon token which is needed to view the image
      // in ImageServer.  If there is no image id, then return a message indicating that there is
      // no corresponding H&E image.  Note that the message is added to the response by the
      // getImageId method.
      if (validToken) {
        String imageId = getImageId(request, baseUrl, token, aperioForm.getBigrSampleId());
        if (imageId == null) {
          jsonObject = new JSONObject();
          jsonObject.put("dialog", true);
          response.addHeader("X-JSON", jsonObject.toString());
          forward = "message";
        }
        else {
          jsonObject = new JSONObject();
          jsonObject.put("dialog", false);
          jsonObject.put("imageId", imageId);
          jsonObject.put("token", token);
          jsonObject.put("chost",ApiProperties.getProperty("aperio.imageServer.host") + ":" + ApiProperties.getProperty("aperio.imageServer.sisPort"));
          forward = "message";
        }
      }

      // If a JSON object was defined, then add it as a header to the response, and forward to
      // the appropriate page.
      if (jsonObject != null) {
        response.addHeader("X-JSON", jsonObject.toString());
      }
      return mapping.findForward(forward);
    }
    catch (Exception e) {
      JSONObject jsonObject = new JSONObject();
      jsonObject.put("exception", true);
      response.addHeader("X-JSON", jsonObject.toString());
      throw e;
    }
  }

  /**
   * Makes the SOAP call to DataServer to logon to Spectrum with the specified username and 
   * password, and returns the token returned by DataServer.
   * 
   * @param request the HTTP request
   * @param baseUrl the base URL of the Aperio DataServer
   * @param username the username
   * @param password the password
   * @return  The token returned by DataServer on a successful logon, or <code>null</code> if the
   * logon was not successful.
   */
  private String logonToSpectrum(HttpServletRequest request, String baseUrl, String username, String password) throws Exception {
    Writer out = null;
    BufferedReader in = null;
    try {
      // Create the connection.
      URL url = new URL(baseUrl + "/Aperio.Security/Security2.asmx");
      HttpURLConnection conn = (HttpURLConnection) url.openConnection();
      conn.setAllowUserInteraction(false);
      conn.setInstanceFollowRedirects(false);
      conn.setRequestMethod("POST");
      conn.setRequestProperty("User-Agent", "BIGR");
      conn.setRequestProperty("SOAPAction", "\"http://www.aperio.com/webservices/Logon\"");
      conn.setDoOutput(true);
      conn.setDoInput(true);

      // Create the SOAP request. 
      StringBuffer soapRequest = new StringBuffer();
      soapRequest.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
      soapRequest.append("<SOAP-ENV:Envelope");
      getSoapEnvelopeDetails(soapRequest);
      soapRequest.append("<SOAP-ENV:Body>");
      soapRequest.append("<Logon xmlns=\"http://www.aperio.com/webservices/\">");
      soapRequest.append("<UserName>");
      soapRequest.append(username);
      soapRequest.append("</UserName>");
      soapRequest.append("<PassWord>");
      soapRequest.append(password);
      soapRequest.append("</PassWord>");
      soapRequest.append("</Logon>");
      soapRequest.append("</SOAP-ENV:Body>");
      soapRequest.append("</SOAP-ENV:Envelope>");
      conn.setRequestProperty("Content-Length", String.valueOf(soapRequest.length()));

      // Send the SOAP request. 
      out = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
      out.write(soapRequest.toString());
      out.flush();
      out.close();
      out = null;

      // Create a parser and parse the response. 
      SAXParserFactory factory = SAXParserFactory.newInstance();
      factory.setNamespaceAware(true); 
      factory.setValidating(false);
      SAXParser parser = factory.newSAXParser();
      LogonHandler handler = new LogonHandler();
      parser.parse(conn.getInputStream(), handler);
      
      // If the result is 0 then it was a successful call so return the token.
      String result = handler.getResult();
      if (result.equals("0")) {
        return handler.getToken();
      }

      // If the result is not 0 then there was an error, so save the error message as an
      // action error and return null.
      else {
        ActionErrors errors = new ActionErrors();
        ActionError error = new ActionError("integration.error.aperio.message", handler.getMessage());
        errors.add(BtxActionErrors.GLOBAL_ERROR, error);
        saveErrors(request, errors, true);
        return null;
      }
    }
    finally {
      if (in != null) {
        in.close();
        in = null;
      }
      if (out != null) {
        out.close();
        out = null;
      }
    }
  }
  
  
 
  
  /**
   * Makes the SOAP call to DataServer to determine of the specified token is valid.
   * 
   * @param request the HTTP request
   * @param baseUrl the base URL of the Aperio DataServer
   * @param token the token
   * @return  <code>true</code> if the token is valid; <code>false</code> otherwise.
   */
  private Boolean isValidToken(HttpServletRequest request, String baseUrl, String token) throws Exception {
    Writer out = null;
    BufferedReader in = null;
    try {
      // Create the connection.
      URL url = new URL(baseUrl + "/Aperio.Security/Security2.asmx");
      HttpURLConnection conn = (HttpURLConnection) url.openConnection();
      conn.setAllowUserInteraction(false);
      conn.setInstanceFollowRedirects(false);
      conn.setRequestMethod("POST");
      conn.setRequestProperty("User-Agent", "BIGR");
      conn.setRequestProperty("SOAPAction", "\"http://www.aperio.com/webservices/IsValidToken\"");
      conn.setDoOutput(true);
      conn.setDoInput(true);

      // Create the SOAP request. 
      StringBuffer soapRequest = new StringBuffer();
      soapRequest.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
      soapRequest.append("<SOAP-ENV:Envelope");
      getSoapEnvelopeDetails(soapRequest);
      soapRequest.append("<SOAP-ENV:Body>");
      soapRequest.append("<IsValidToken xmlns=\"http://www.aperio.com/webservices/\">");
      soapRequest.append("<Token>");
      soapRequest.append(token);
      soapRequest.append("</Token>");
      soapRequest.append("</IsValidToken>");
      soapRequest.append("</SOAP-ENV:Body>");
      soapRequest.append("</SOAP-ENV:Envelope>");
      conn.setRequestProperty("Content-Length", String.valueOf(soapRequest.length()));

      // Send the SOAP request. 
      out = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
      out.write(soapRequest.toString());
      out.flush();
      out.close();
      out = null;

      // Create a parser and parse the response. 
      SAXParserFactory factory = SAXParserFactory.newInstance();
      factory.setNamespaceAware(true); 
      factory.setValidating(false);
      SAXParser parser = factory.newSAXParser();
      ValidTokenHandler handler = new ValidTokenHandler();
      parser.parse(conn.getInputStream(), handler);
      
      // If the result is 0 then it was a successful call so return the token.
      String result = handler.getResult();
      if (result.equals("0")) {
        Boolean valid = handler.isValid();
        if (!valid.booleanValue()) {
          ActionErrors errors = new ActionErrors();
          ActionError error = new ActionError("integration.message.aperio.logonTimeout", handler.getMessage());
          errors.add(BtxActionErrors.GLOBAL_ERROR, error);
          saveErrors(request, errors, true);
        }
        return handler.isValid();
      }

      // If the result is not 0 then there was an error, so save the error message as an
      // action error and return null.
      else {
        ActionErrors errors = new ActionErrors();
        ActionError error = new ActionError("integration.error.aperio.message", handler.getMessage());
        errors.add(BtxActionErrors.GLOBAL_ERROR, error);
        saveErrors(request, errors, true);
        return null;
      }
    }
    finally {
      if (in != null) {
        in.close();
        in = null;
      }
      if (out != null) {
        out.close();
        out = null;
      }
    }
  }
  
  /**
   * Makes the SOAP call to DataServer to get the imageId for the specified sample.
   * 
   * @param request the HTTP request
   * @param baseUrl the base URL of the Aperio DataServer
   * @param token the authentication token
   * @param sampleId the BIGR sample id
   * @return  The image id, or <code>null</code> if no H&E image exists for the specified sample.  
   */
  
  

  
  private String getImageId(HttpServletRequest request, String baseUrl, String token, String sampleId) throws Exception {
    Writer out = null;
    BufferedReader in = null;
    try {
      // Create the connection.
      URL url = new URL(baseUrl + "/Aperio.Images/Image.asmx");
      HttpURLConnection conn = (HttpURLConnection) url.openConnection();
      conn.setAllowUserInteraction(false);
      conn.setInstanceFollowRedirects(false);
      conn.setRequestMethod("POST");
      conn.setRequestProperty("User-Agent", "BIGR");
      conn.setRequestProperty("SOAPAction", "\"http://www.aperio.com/webservices/GetImageData2\"");
      conn.setDoOutput(true);
      conn.setDoInput(true);

      // Create the SOAP request. 
      StringBuffer soapRequest = new StringBuffer();
      soapRequest.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
      soapRequest.append("<SOAP-ENV:Envelope");
      getSoapEnvelopeDetails(soapRequest);
      soapRequest.append("<SOAP-ENV:Body>");
      soapRequest.append("<GetImageData2 xmlns=\"http://www.aperio.com/webservices/\">");
      soapRequest.append("<Token>");
      soapRequest.append(token);
      soapRequest.append("</Token>");
      soapRequest.append("<BarcodeId>");
 //     soapRequest.append("S12-16378;S8;ccf");
      soapRequest.append(sampleId);
      soapRequest.append("</BarcodeId>");
//      soapRequest.append("<CompressedFileLocation>");
//      soapRequest.append("\\\\cc-l2nas51\\aperioimages\\2012\\2012-01-05\\ORTHO_10068528_Adamantinoma.svs");
//      soapRequest.append("</CompressedFileLocation>");
      soapRequest.append("</GetImageData2>");
      soapRequest.append("</SOAP-ENV:Body>");
      soapRequest.append("</SOAP-ENV:Envelope>");
      conn.setRequestProperty("Content-Length", String.valueOf(soapRequest.length()));

      // Send the SOAP request. 
      out = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
      out.write(soapRequest.toString());
      out.flush();
      out.close();
      out = null;

      // Create a parser and parse the response. 
      SAXParserFactory factory = SAXParserFactory.newInstance();
      factory.setNamespaceAware(true); 
      factory.setValidating(false);
      SAXParser parser = factory.newSAXParser();
      ImageDataHandler handler = new ImageDataHandler();
      parser.parse(conn.getInputStream(), handler);

      // If the result is 0 then the SOAP call executed without error, but it does not mean that
      // an H&E image id was found.  Therefore if there is an image id then return it, otherwise
      // save an action message.
      String result = handler.getResult();
      if (result.equals("0")) {
        String imageId = handler.getImageId();
        if (ApiFunctions.isEmpty(imageId)) {
          BtxActionMessages msgs = new BtxActionMessages();
          BtxActionMessage msg = new BtxActionMessage("integration.message.aperio.nohe", sampleId);
          msgs.add(msg);
          saveMessages(request, msgs);
          return null;
        }
        else {
          return imageId;
        }
      }

      // If the result is not 0 then there was an error, so save the error message as an
      // action error and return null.
      else {
        ActionErrors errors = new ActionErrors();
        ActionError error = new ActionError("integration.error.aperio.message", handler.getMessage());
        errors.add(BtxActionErrors.GLOBAL_ERROR, error);
        saveErrors(request, errors, true);
        return null;
      }
    }
    finally {
      if (in != null) {
        in.close();
        in = null;
      }
      if (out != null) {
        out.close();
        out = null;
      }
    }
  }
  
}
