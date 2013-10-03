package com.ardais.bigr.orm.helpers;

import java.io.IOException;
import java.io.Serializable;
import java.io.StringReader;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.digester.Digester;
import org.xml.sax.SAXException;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.iltds.assistants.LogicalRepository;
import com.ardais.bigr.iltds.helpers.IltdsUtils;
import com.ardais.bigr.javabeans.PrivilegeDto;
import com.ardais.bigr.util.BigrXMLParserBase;
import com.ardais.bigr.util.BigrXmlUtils;
import com.ardais.bigr.util.gen.DbAliases;
import com.ardais.bigr.util.gen.DbConstants;

public class AccountData extends BigrXMLParserBase implements Serializable {
  //static final long serialVersionUID = -1710661269833625543L;

  private String _accountId = null; 
  private String _accountData = null; 
  private List _assignableInventoryGroups = null;
  private List _assignablePrivileges = null;

  public AccountData() {
    super();
  }
  
  /**
   * This method parses the account data XML string. As certain patterns are matched, the
   * appropriate method is called in return.
   */
  private void parseAccountData() {
    setAssignableInventoryGroups(null);
    setAssignablePrivileges(null);
    
    // Get the policy data xml.
    String xml = getAccountData();
    
    //if there is no xml data just return
    if (ApiFunctions.isEmpty(xml)) {
      return;
    }

    // Create a default digester.
    Digester digester = makeDigester();

    // Register the location of the category configuration DTD.
    URL dtdURL = getClass().getResource(ApiFunctions.CLASSPATH_RESOURCES_PREFIX + "accountXML1.dtd");
    digester.register("-//Ardais Corporation//DTD Account Encoding XML1//EN", dtdURL.toString());

    digester.push(this);
    
    // Parse inventory groups.
    digester.addObjectCreate("*/assignableInventoryGroups/inventoryGroup", LogicalRepository.class);
    digester.addSetProperties("*/assignableInventoryGroups/inventoryGroup");
    digester.addSetRoot("*/assignableInventoryGroups/inventoryGroup",
      "addAssignableInventoryGroup",
      "com.ardais.bigr.iltds.assistants.LogicalRepository");
    
    // Parse privileges.
    digester.addObjectCreate("*/assignablePrivileges/privilege", PrivilegeDto.class);
    digester.addSetProperties("*/assignablePrivileges/privilege");
    digester.addSetRoot("*/assignablePrivileges/privilege",
      "addAssignablePrivilege",
      "com.ardais.bigr.javabeans.PrivilegeDto");

    // Now that everything is set up, parse the file.
    try {
      digester.parse(new StringReader(xml));
    }
    catch (SAXException se) {
      throw new ApiException(se);
    }
    catch (IOException ie) {
      throw new ApiException(ie);
    }
  }

  /**
   * Convert the policy object into XML. This method in turn will call the toXml
   * method of the sample type configuration object contained within this object.
   * 
   * @return The XML representation of the policy object.
   */
  public String toXml() {
    StringBuffer xml = new StringBuffer();
    
    xml.append("<?xml");
    BigrXmlUtils.writeAttribute(xml, "version", "1.0");
    BigrXmlUtils.writeAttribute(xml, "encoding", "UTF-8");
    xml.append("?>\n");
    xml.append("<!DOCTYPE account PUBLIC \"-//Ardais Corporation//DTD Account Encoding XML1//EN\" \"accountXML1.dtd\">\n");

    xml.append("<account>\n");
    xml.append("  <assignableInventoryGroups>\n");
    Iterator igIterator = getAssignableInventoryGroups().iterator();
    while (igIterator.hasNext()) {
      LogicalRepository inventoryGroup = (LogicalRepository)igIterator.next();
      xml.append("    <inventoryGroup");
      BigrXmlUtils.writeAttribute(xml, "id", inventoryGroup.getId());
      BigrXmlUtils.writeAttribute(xml, "fullName", inventoryGroup.getFullName());
      xml.append("/>\n");
    }
    xml.append("  </assignableInventoryGroups>\n");
    xml.append("  <assignablePrivileges>\n");
    Iterator pIterator = getAssignablePrivileges().iterator();
    while (pIterator.hasNext()) {
      PrivilegeDto privilege = (PrivilegeDto)pIterator.next();
      xml.append("    <privilege");
      BigrXmlUtils.writeAttribute(xml, "id", privilege.getId());
      BigrXmlUtils.writeAttribute(xml, "description", privilege.getDescription());
      xml.append("/>\n");
    }
    xml.append("  </assignablePrivileges>\n");
    xml.append("</account>\n");
    return xml.toString();
  }
  /**
   * @return
   */
  public String getAccountData() {
    return _accountData;
  }

  /**
   * @return
   */
  public String getAccountId() {
    return _accountId;
  }

  /**
   * @return
   */
  public List getAssignableInventoryGroups() {
    if (ApiFunctions.isEmpty(_assignableInventoryGroups)) {
      _assignableInventoryGroups = new ArrayList();
    }
    return _assignableInventoryGroups;
  }

  /**
   * @return
   */
  public List getAssignablePrivileges() {
    if (ApiFunctions.isEmpty(_assignablePrivileges)) {
      _assignablePrivileges = new ArrayList();
    }
    return _assignablePrivileges;
  }

  /**
   * @param string
   */
  public void setAccountData(String string) {
    _accountData = string;
    parseAccountData();
  }

  /**
   * @param string
   */
  public void setAccountId(String string) {
    _accountId = string;
  }

  /**
   * @param list
   */
  public void setAssignableInventoryGroups(List list) {
    _assignableInventoryGroups = list;
  }

  /**
   * @param list
   */
  public void setAssignablePrivileges(List list) {
    _assignablePrivileges = list;
  }
  
  public void addAssignableInventoryGroup(LogicalRepository inventoryGroup) {
    getAssignableInventoryGroups().add(inventoryGroup);
  }
  
  public void addAssignablePrivilege(PrivilegeDto privilege) {
    getAssignablePrivileges().add(privilege);
  }

}
