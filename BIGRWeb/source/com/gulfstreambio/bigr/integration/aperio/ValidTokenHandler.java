package com.gulfstreambio.bigr.integration.aperio;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/*
<?xml version="1.0" encoding="utf-8"?>
<soap:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
<soap:Body>
<IsValidTokenResponse xmlns="http://www.aperio.com/webservices/">
<IsValidTokenResult>
<ASResult>0</ASResult>
<ASMessage></ASMessage>
</IsValidTokenResult>
<Valid>true</Valid>
</IsValidTokenResponse>
</soap:Body>
</soap:Envelope>  
 */
public class ValidTokenHandler extends DefaultHandler {

  private StringBuffer _result = new StringBuffer();
  private StringBuffer _message = new StringBuffer();
  private StringBuffer _valid = new StringBuffer();
  
  private boolean _inResult = false;
  private boolean _inMessage = false;
  private boolean _inValid = false;
  
  public ValidTokenHandler() {
    super();
  }

  public void characters(char[] ch, int start, int length) throws SAXException {
    StringBuffer buf = null;
    if (_inResult) {
      buf = _result;
    }
    else if (_inMessage) {
      buf = _message;
    }
    else if (_inValid) {
      buf = _valid;
    }
    
    if (buf != null) {
      int end = start + length - 1;
      for (int i = start; i <= end; i++) {
        buf.append(ch[i]);
      }
    }
  }
  
  public void startElement(String uri, String localName, String qName, Attributes attributes)
      throws SAXException {
    _inResult = localName.equalsIgnoreCase("asresult");
    _inMessage = localName.equalsIgnoreCase("asmessage");
    _inValid = localName.equalsIgnoreCase("valid");
  }
  
  String getResult() {
    return _result.toString(); 
  }
  
  String getMessage() {
    return _message.toString();
  }
  
  Boolean isValid() {
    return new Boolean(_valid.toString());
  }
}
