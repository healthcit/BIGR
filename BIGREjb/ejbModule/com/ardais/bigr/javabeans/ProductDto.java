package com.ardais.bigr.javabeans;

import java.io.Serializable;
import java.util.List;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.iltds.assistants.ProjectDataBean;
import com.ardais.bigr.iltds.btx.BTXBoxLocation;
import com.ardais.bigr.iltds.helpers.ProductType;
import com.ardais.bigr.lims.javabeans.DiagnosticTestResultData;
import com.ardais.bigr.lims.javabeans.PathologyEvaluationData;
import com.ardais.bigr.pdc.javabeans.PathReportData;
import com.ardais.bigr.pdc.javabeans.PathReportSectionData;
import com.ardais.bigr.security.SecurityInfo;

/**
 * Represents data returned from a Sample Selection query.
 */
public class ProductDto implements Serializable {
  // TODO: Now that we're using this class as a generic wrapper for an inventory item (tissue, RNA,
  // ...), we could clean things up by making this a base class and deriving SampleData and
  // RnaData from it.  That would eliminate a lot of the branching in methods to test for
  // product type, and eliminate some very similar copies of method implementations in SampleData
  // and RnaData.

  private AsmData _asmData;
  private ConsentData _consentData;
  private DonorData _donorData;
  private OrderData _orderData;
  private PathologyEvaluationData _peData;
  private PathReportData _pathData;
  private PathReportSectionData _sectionData;
  private ProjectDataBean _projectData;
  private RnaData _rnaData;
  private SampleData _sampleData;
  private DiagnosticTestResultData _diagnosticTestResultData;
  private ProductType _type = null;

  /**
   * Creates a new <code>ProductDto</code>.
   */
  public ProductDto() {
  }

  public void setRnaData(RnaData rnaData) {
    _rnaData = rnaData;
  }

  public void setSampleData(SampleData sampleData) {
    _sampleData = sampleData;
  }

  public void setType(ProductType type) {
    _type = type;
  }

  public RnaData getRnaData() {
    return _rnaData;
  }

  public SampleData getSampleData() {
    if ((_sampleData == null) && (getRnaData() != null)) {
      _sampleData = getRnaData().getRepresentativeSample();
    }
    return _sampleData;
  }

  /**
   * Return the product type.  If the type hasn't been explicitly set to a non-null value,
   * we try to infer the product type from the values in other fields such as
   * {@link #getRnaData}.  If we can't infer the product type, we return null.
   * 
   * @return The product type.  This will be the value of one of the type constants
   *   defined in the ProductType class.
   */
  public ProductType getType() {
    if (_type == null) {
      // sampleData gets set for both RNA and tissue samples, so we have to do the
      // check for sampleData last.
      if (getRnaData() != null) {
        setType(ProductType.RNA);
      }
      else if (getSampleData() != null) {
        setType(ProductType.SAMPLE);
      }
    }
    return _type;
  }
  
  public List getLogicalRepositories() {
    ProductType type = getType();
    if (type == ProductType.SAMPLE) {
      return getSampleData().getLogicalRepositories();
    }
    else if (type == ProductType.RNA) {
      return getRnaData().getLogicalRepositories();
    }
    else {
      return null;
    }
  }
  
  public BTXBoxLocation getStorageLocation() {
    ProductType type = getType();
    if (type == ProductType.SAMPLE) {
      return getSampleData().getStorageLocation();
    }
    else if (type == ProductType.RNA) {
      return getRnaData().getStorageLocation();
    }
    else {
      return null;
    }
  }
  
  public boolean isStoredLocally(SecurityInfo securityInfo) {
    ProductType type = getType();
    if (type == ProductType.SAMPLE) {
      return getSampleData().isStoredLocally(securityInfo);
    }
    else if (type == ProductType.RNA) {
      return getRnaData().isStoredLocally(securityInfo);
    }
    else {
      return false;
    }
  }

  /**
   * @return True if any of the logical repositories that the product is in
   * is accessible to the user represented by <code>securityInfo</code>.  If the user
   * has the View All Logical Repositories privilege, this always returns true even if the
   * product is not in any logical repositories.
   * 
   * @param securityInfo The user's security information.
   */
  public boolean isAccessibleToUser(SecurityInfo securityInfo) {
    ProductType type = getType();
    if (type == ProductType.SAMPLE) {
      return getSampleData().isAccessibleToUser(securityInfo);
    }
    else if (type == ProductType.RNA) {
      return getRnaData().isAccessibleToUser(securityInfo);
    }
    else {
      return false;
    }
  }

  /**
   * @return True if the item is in any BMS logical repository.  It is possible for an item
   * to be in some BMS logical repository (so that
   * {@link #isBms()} will return true) but for the item not to be a BMS sample from the user's
   * perspective (so that {@link #isBmsFromUsersPerspective()} will return false).  This happens
   * when an item is in some
   * non-BMS logical repository that the user does have access to, but all of the BMS repositories
   * that the item is part of are inaccessible to the user.
   * 
   * @see #isBmsFromUsersPerspective()
   */
  public boolean isBms() {
    ProductType type = getType();
    if (type == ProductType.SAMPLE) {
      return getSampleData().isBms();
    }
    else if (type == ProductType.RNA) {
      return getRnaData().isBms();
    }
    else {
      return false;
    }
  }

  /**
   * @return True if any of the logical repositories that the product is in
   * is both a BMS logical repository and is accessible to the user represented by
   * <code>securityInfo</code>.  This tells us whether the product is a BMS item from this user's
   * perspective.  It is possible for an item to be in some BMS logical repository (so that
   * {@link #isBms()} will return true) but for the item not to be a BMS item from the user's
   * perspective (so that this method will return false).  This happens when an item is in some
   * non-BMS logical repository that the user does have access to, but all of the BMS repositories
   * that the item is part of are inaccessible to the user.
   * 
   * @param securityInfo The user's security information.
   * @see #isBms()
   */
  public boolean isBmsFromUsersPerspective(SecurityInfo securityInfo) {
    ProductType type = getType();
    if (type == ProductType.SAMPLE) {
      return getSampleData().isBmsFromUsersPerspective(securityInfo);
    }
    else if (type == ProductType.RNA) {
      return getRnaData().isBmsFromUsersPerspective(securityInfo);
    }
    else {
      return false;
    }
  }

  public AsmData getAsmData() {
    if ((_asmData == null) && (getSampleData() != null)) {
      _asmData = getSampleData().getAsmData();
    }
    return _asmData;
  }

  public OrderData getOrderData() {
    if ((_orderData == null) && (getSampleData() != null)) {
      _orderData = getSampleData().getOrderData();
    }
    return _orderData;
  }

  public ConsentData getConsentData() {
    if ((_consentData == null) && (getAsmData() != null)) {
      _consentData = getAsmData().getConsentData();
    }
    return _consentData;
  }

  public PathologyEvaluationData getPathologyEvaluationData() {
    if ((_peData == null) && (getSampleData() != null)) {
      _peData = getSampleData().getPathologyEvaluationData();
    }
    return _peData;
  }

  public PathReportData getPathData() {
    if ((_pathData == null) && (getConsentData() != null)) {
      _pathData = getConsentData().getPathReportData();
    }
    return _pathData;
  }

  public DonorData getDonorData() {
    if ((_donorData == null) && (getConsentData() != null)) {
      _donorData = getConsentData().getDonorData();
    }
    return _donorData;
  }

  public PathReportSectionData getSectionData() {
    if ((_sectionData == null) && (getPathData() != null)) {
      _sectionData = getPathData().getPrimarySectionData();
    }
    return _sectionData;
  }

  public ProjectDataBean getProjectData() {
    if ((_projectData == null) && (getSampleData() != null)) {
      _projectData = getSampleData().getProjectData();
    }
    return _projectData;
  }

  /**
   * Return the item's ID
   */
  public String getId() {
    return ((_rnaData != null) ? _rnaData.getRnaVialId() : _sampleData.getSampleId());
  }

  public boolean isRna() {
    return _rnaData != null; // if we have RNA data in addition to sample data, this is RNA
  }
}
