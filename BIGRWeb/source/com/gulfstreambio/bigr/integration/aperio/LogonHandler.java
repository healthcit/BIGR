package com.gulfstreambio.bigr.integration.aperio;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/*
<?xml version="1.0" encoding="utf-8"?>
<soap:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
<soap:Body>
<LogonResponse xmlns="http://www.aperio.com/webservices/">
<LogonResult>
<ASResult>0</ASResult>
<ASMessage>No rows in result set</ASMessage>
</LogonResult>
<Token>f5gHzmgSn6wTL8T6s4XDCfojhO5rk7xD3uAe1-uwrxTUSdhKpjgryg==</Token>
<UserData>
<UserId>3</UserId>
<FullName>Spectrum Administrator</FullName>
<LoginName>administrator</LoginName>
<Phone></Phone>
<E_Mail></E_Mail>
<UserMustChangePassword>False</UserMustChangePassword>
<Privileges>
<AdminUser>True</AdminUser>
</Privileges>
<LastLoginTime>2008-10-15 12:53</LastLoginTime>
<PasswordDaysLeft>-1</PasswordDaysLeft>
<AutoView>false</AutoView>
</UserData>
</LogonResponse>
</soap:Body>
</soap:Envelope>  
 */
public class LogonHandler extends DefaultHandler {

  private StringBuffer _result = new StringBuffer();
  private StringBuffer _message = new StringBuffer();
  private StringBuffer _token = new StringBuffer();
  
  private boolean _inResult = false;
  private boolean _inMessage = false;
  private boolean _inToken = false;
  
  public LogonHandler() {
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
    else if (_inToken) {
      buf = _token;
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
    _inToken = localName.equalsIgnoreCase("token");
  }
  
  String getResult() {
    return _result.toString(); 
  }
  
  String getMessage() {
    return _message.toString();
  }
  
  String getToken() {
    return _token.toString();
  }
}
