package com.ardais.bigr.configuration;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.api.ApiFunctions;

public class LabelPrinter implements Serializable, Comparable {
  private String _name;
  private String _displayName;
  private boolean _fileBasedPrintingEnabled;
  private String _fileDirectory;
  private String _fileName;
  private String _fileExtension;
  private String _fileTransferCommand;
  private boolean _emailBasedPrintingEnabled;
  private String _emailAddress;
  private String _emailSubject;
  private boolean _tcpIpBasedPrintingEnabled;
  private String _tcpIpHost;
  private String _tcpIpPort;
  private Map _labelTemplateMap = new HashMap();
  private Account _parent;
  private boolean _immutable = false;
  
  /**
   * @return Returns the name.
   */
  public String getName() {
    return _name;
  }
  
  /**
   * @return Returns the displayName.
   */
  public String getDisplayName() {
    return _displayName;
  }
  
  /**
   * @return Returns the emailAddress.
   */
  public String getEmailAddress() {
    return _emailAddress;
  }
  
  /**
   * @return Returns the emailBasedPrintingEnabled.
   */
  public boolean isEmailBasedPrintingEnabled() {
    return _emailBasedPrintingEnabled;
  }
  
  /**
   * @return Returns the emailSubject.
   */
  public String getEmailSubject() {
    return _emailSubject;
  }
  
  /**
   * @return Returns the fileBasedPrintingEnabled.
   */
  public boolean isFileBasedPrintingEnabled() {
    return _fileBasedPrintingEnabled;
  }
  
  /**
   * @return Returns the fileDirectory.
   */
  public String getFileDirectory() {
    return _fileDirectory;
  }
  
  /**
   * @return Returns the fileExtension.
   */
  public String getFileExtension() {
    return _fileExtension;
  }
  
  /**
   * @return Returns the fileName.
   */
  public String getFileName() {
    return _fileName;
  }
  
  /**
   * @return Returns the fileTransferCommand.
   */
  public String getFileTransferCommand() {
    return _fileTransferCommand;
  }
  
  /**
   * @return Returns the tcpIpBasedPrintingEnabled.
   */
  public boolean isTcpIpBasedPrintingEnabled() {
    return _tcpIpBasedPrintingEnabled;
  }
  
  /**
   * @return Returns the tcpIpHost.
   */
  public String getTcpIpHost() {
    return _tcpIpHost;
  }
  
  /**
   * @return Returns the tcpIpPort.
   */
  public String getTcpIpPort() {
    return _tcpIpPort;
  }
  
  Account getParent() {
    return _parent;
  }
  
  /**
   * @param id The name to set.
   */
  public void setName(String name) {
    checkImmutable();
   _name = name;
  }
  
  /**
   * @param displayName The displayName to set.
   */
  public void setDisplayName(String displayName) {
    checkImmutable();
    _displayName = displayName;
  }
  
  /**
   * @param emailAddress The emailAddress to set.
   */
  public void setEmailAddress(String emailAddress) {
    checkImmutable();
    _emailAddress = emailAddress;
  }
  
  /**
   * @param emailBasedPrintingEnabled The emailBasedPrintingEnabled to set.
   */
  public void setEmailBasedPrintingEnabled(boolean emailBasedPrintingEnabled) {
    checkImmutable();
    _emailBasedPrintingEnabled = emailBasedPrintingEnabled;
  }
  
  /**
   * @param emailSubject The emailSubject to set.
   */
  public void setEmailSubject(String emailSubject) {
    checkImmutable();
    _emailSubject = emailSubject;
  }
  
  /**
   * @param fileBasedPrintingEnabled The fileBasedPrintingEnabled to set.
   */
  public void setFileBasedPrintingEnabled(boolean fileBasedPrintingEnabled) {
    checkImmutable();
    _fileBasedPrintingEnabled = fileBasedPrintingEnabled;
  }
  
  /**
   * @param fileDirectory The fileDirectory to set.
   */
  public void setFileDirectory(String fileDirectory) {
    checkImmutable();
    _fileDirectory = fileDirectory;
  }
  
  /**
   * @param fileExtension The fileExtension to set.
   */
  public void setFileExtension(String fileExtension) {
    checkImmutable();
    _fileExtension = fileExtension;
  }
  
  /**
   * @param fileName The fileName to set.
   */
  public void setFileName(String fileName) {
    checkImmutable();
    _fileName = fileName;
  }
  
  /**
   * @param fileTransferCommand The fileTransferCommand to set.
   */
  public void setFileTransferCommand(String fileTransferCommand) {
    checkImmutable();
    _fileTransferCommand = fileTransferCommand;
  }
  
  /**
   * @param tcpIpBasedPrintingEnabled The tcpIpBasedPrintingEnabled to set.
   */
  public void setTcpIpBasedPrintingEnabled(boolean tcpIpBasedPrintingEnabled) {
    checkImmutable();
    _tcpIpBasedPrintingEnabled = tcpIpBasedPrintingEnabled;
  }
  
  /**
   * @param tcpIpHost The tcpIpHost to set.
   */
  public void setTcpIpHost(String tcpIpHost) {
    checkImmutable();
    _tcpIpHost = tcpIpHost;
  }
  
  /**
   * @param tcpIpPort The tcpIpPort to set.
   */
  public void setTcpIpPort(String tcpIpPort) {
    checkImmutable();
    _tcpIpPort = tcpIpPort;
  }
  
  void setParent(Account parent) {
    checkImmutable();
    _parent = parent;
  }
  
  public void addLabelTemplate(LabelTemplate labelTemplate) {
    checkImmutable();
    String name = ApiFunctions.safeTrim(labelTemplate.getLabelTemplateDefinitionName());
    if (ApiFunctions.isEmpty(name)) {
      throw new ApiException("Attempted to add a label template with an empty labelTemplateDefinitionName.");
    }
    else {
      if (_labelTemplateMap.put(name.toLowerCase(), labelTemplate) != null) {
        throw new ApiException("Label template with duplicate labelTemplateDefinitionName " + name + " encountered.");
      }
      labelTemplate.setParent(this);
    }
  }

  /**
   * Throw an exception if this instance is immutable.
   */
  private void checkImmutable() {
    if (_immutable) {
      throw new IllegalStateException("Attempted to modify an immutable LabelPrinter: " + this);
    }
  }
  
  /**
   * Mark this instance as immutable.
   */
  public void setImmutable() {
    _immutable = true;
    Iterator labelTemplateIterator = _labelTemplateMap.keySet().iterator();
    while (labelTemplateIterator.hasNext()) {
      ((LabelTemplate)_labelTemplateMap.get(labelTemplateIterator.next())).setImmutable();
    }
    _labelTemplateMap = Collections.unmodifiableMap(_labelTemplateMap);
  }
  
  public Map getLabelTemplates() {
    return _labelTemplateMap;
  }

  /* (non-Javadoc)
   * @see java.lang.Comparable#compareTo(java.lang.Object)
   * @return  a negative integer, zero, or a positive integer as this object
   *    is less than, equal to, or greater than the specified object.
   * 
   * @throws ClassCastException if the specified object's type prevents it
   *         from being compared to this Object.
   */
  public int compareTo(Object o) {
    LabelPrinter otherLabelPrinter = (LabelPrinter)o;
    return getDisplayName().compareTo(otherLabelPrinter.getDisplayName());
  }
  
}
