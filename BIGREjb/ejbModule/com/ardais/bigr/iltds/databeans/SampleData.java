package com.ardais.bigr.iltds.databeans;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.api.Escaper;
import com.ardais.bigr.beanutils.BigrBeanUtilsBean;
import com.ardais.bigr.iltds.assistants.LogicalRepository;
import com.ardais.bigr.iltds.helpers.FormLogic;
import com.ardais.bigr.iltds.helpers.IltdsUtils;
import com.ardais.bigr.javabeans.BoxDto;
import com.ardais.bigr.javabeans.IdList;
import com.ardais.bigr.orm.helpers.BigrGbossData;
import com.ardais.bigr.security.SecurityInfo;
import com.ardais.bigr.util.ArtsConstants;
import com.ardais.bigr.util.BoxLayoutUtils;
import com.ardais.bigr.util.IcpUtils;
import com.gulfstreambio.gboss.GbossFactory;

public class SampleData implements java.io.Serializable {
  private final static long serialVersionUID = 6541185974541890525L;
  private List slides = new ArrayList();
  private String donor_id;
  private String case_id;
  private String asm_id;
  private String sample_id;
  private TopLineData topLine;
  private String box_barcode_id;
  private String cell_ref_location;
  private BoxDto box;
  private AsmData parent;
  private boolean _restricted;
  private String _accountId;
  private String account_name;
  private String asm_position;
  private String comment;
  private String type_fixative;
  private String sales_status;
  private String qc_status;
  private Timestamp _pullDate;
  private String inv_status;
  private String _projectStatus;
  private String _holdAccountId;
  private String _holdUserId;
  private DateData receipt_date;
  private SampleStatusData last_transfer_status;
  private boolean exists;

  private boolean _qcPulled;
  private boolean _qcReleased;
  private boolean _qcVerified;

  // fields added for MR 4435
  private String _diMinThicknessCid;
  private String _diMaxThicknessCid;
  private String _diWidthAcrossCid;
  private String _formatDetailCid;
  private String _sampleSizeMeetsSpecs;
  private Integer _hoursInFixative;

  private String _bestMinThicknessCid;
  private String _bestMaxThicknessCid;
  private String _bestWidthAcrossCid;
  private String _histoSubdivisible;
  private String _histoReembedReasonCid;
  private String _otherHistoReembedReason;
  private String _histoNotes;

  private String _parentBarcodeId;
  private Timestamp _subdivisionDate;
  private IdList _subdivisionChildIds = new IdList();

  private Timestamp _bornDate;

  private String _viabletumercells;
  private String _viablenormalcomp;
  private String _cellularstroma;
  private String _acellularstroma;
  private String _necrosis;
  private String _viablelesioncells;
  private String _comments;
  private String _internal_comments;
  private String _dxOther;
  private String _tcOtherOrigin;
  private String _tcOtherFinding;
  private String _tissueOrigin;
  private String _tissueFinding;

  private String _secCount;

  // MR4852
  private String _paraffinFeatureCid;
  private String _otherParaffinFeature;

  private List _logicalRepositories;

  private String _bmsYN;
  private String _lastKnownLocationId;
  
  private String customer_id;
  
  //MR6976
  private List _statuses;
  
  //MR8270
  private List _childSamples;
  private List _parentSamples;
  
  //MR8350
  private String _source;

  public SampleData() {
    super();
  }

  public SampleData(SampleData sampleData) {
    this(sampleData, true);
  }

  public SampleData(SampleData sampleData, boolean copyParent) {
    this();
    BigrBeanUtilsBean.SINGLETON.copyProperties(this, sampleData);
    //any mutable objects must be handled seperately (BigrBeanUtilsBean.copyProperties
    //does a shallow copy.
    if (!ApiFunctions.isEmpty(sampleData.getSlides())) {
      SlideData newSlide = null;
      SlideData originalSlide = null;
      slides.clear();
      Iterator iterator = sampleData.getSlides().iterator();
      while (iterator.hasNext()) {
        //any original slides pointing back to the original sampleData need
        //to be handled specially, to prevent an infinite loop of copying.
        originalSlide = (SlideData)iterator.next();
        if (sampleData.equals(originalSlide.getParent())) {
          newSlide = new SlideData(originalSlide, false);
          newSlide.setParent(this);
        }
        else {
          newSlide = new SlideData(originalSlide);
        }
        slides.add(newSlide);
      }
    }
    if (sampleData.getTopLine() != null) {
      setTopLine(new TopLineData(sampleData.getTopLine()));
    }
    if (sampleData.getBox() != null) {
      setBox(new BoxDto(sampleData.getBox()));
    }
    if (copyParent && sampleData.getParent() != null) {
      setParent(new AsmData(sampleData.getParent()));
    }
    if (sampleData.getPullDate() != null) {
      setPullDate((Timestamp) sampleData.getPullDate().clone());
    }
    if (sampleData.getReceipt_date() != null) {
      setReceipt_date(new DateData(sampleData.getReceipt_date()));
    }
    if (sampleData.getLast_transfer_status() != null) {
      setLast_transfer_status(new SampleStatusData(sampleData.getLast_transfer_status()));
    }
    if (sampleData.getSubdivisionDate() != null) {
      setSubdivisionDate((Timestamp) sampleData.getSubdivisionDate().clone());
    }
    if (sampleData.getSubdivisionChildIds() != null) {
      _subdivisionChildIds = new IdList();
      Iterator iterator = sampleData.getSubdivisionChildIds().getList().iterator();
      while (iterator.hasNext()) {
        _subdivisionChildIds.add((String)iterator.next());
      }
    }
    if (sampleData.getBornDate() != null) {
      setBornDate((Timestamp) sampleData.getBornDate().clone());
    }
    if (!ApiFunctions.isEmpty(sampleData.getLogicalRepositories())) {
      _logicalRepositories.clear();
      Iterator iterator = sampleData.getLogicalRepositories().iterator();
      while (iterator.hasNext()) {
        _logicalRepositories.add(new LogicalRepository((LogicalRepository)iterator.next()));
      }
    }
    if (sampleData.getStatuses() != null) {
      _statuses.clear();
      Iterator iterator = sampleData.getStatuses().iterator();
      while (iterator.hasNext()) {
        _statuses.add((String)iterator.next());
      }
    }
    if (sampleData.getChildSamples() != null) {
      _childSamples.clear();
      Iterator iterator = sampleData.getChildSamples().iterator();
      while (iterator.hasNext()) {
        _childSamples.add(new SampleData((SampleData)iterator.next()));
      }
    }
    if (sampleData.getParentSamples() != null) {
      _parentSamples.clear();
      Iterator iterator = sampleData.getParentSamples().iterator();
      while (iterator.hasNext()) {
        _parentSamples.add(new SampleData((SampleData)iterator.next()));
      }
    }
  }

  public String getTissueFinding() {
    if (_tissueFinding != null)
      return BigrGbossData.getTissueDescription(_tissueFinding);
    return _tissueFinding;
  }

  public String getTissueOrigin() {
    if (_tissueOrigin != null)
      return BigrGbossData.getTissueDescription(_tissueOrigin);
    return _tissueOrigin;
  }

  public String getAccountId() {
    return _accountId;
  }

  public String getAccount_name() {
    return account_name;
  }

  public String getAcellularstroma() {
    return _acellularstroma;
  }

  public boolean isRestricted() {
    return _restricted;
  }

  public String getAsm_id() {
    return asm_id;
  }

  public String getAsm_position() {
    return asm_position;
  }

  public BoxDto getBox() {
    return box;
  }

  public String getBox_barcode_id() {
    return box_barcode_id;
  }

  public String getCase_id() {
    return case_id;
  }

  public String getCell_ref_location() {
    return cell_ref_location;
  }

  public String getCell_ref_location_v() {
    return BoxLayoutUtils.translateCellRef(cell_ref_location, box_barcode_id);
  }

  public String getCellularstroma() {
    return _cellularstroma;
  }

  public String getComment() {
    return comment;
  }

  public String getComments() {
    return _comments;
  }

  public String getComments_js() {
    return Escaper.jsEscapeInXMLAttr(_comments);
  }

  public String getCommentsShort() {
    if (ApiFunctions.isEmpty(_comments))
      return null;

    int length = _comments.trim().length();
    if (length > 30)
      return _comments.substring(0, 30) + " ...";
    else
      return _comments.trim();
  }

  public String getDiMaxThicknessCid() {
    return _diMaxThicknessCid;
  }

  public String getDiMaxThicknessCid_display() {
    return GbossFactory.getInstance().getDescription(/*"PARAFFIN_DIMENSIONS",*/
    _diMaxThicknessCid);
  }

  public String getDiMinThicknessCid() {
    return _diMinThicknessCid;
  }

  public String getDiMinThicknessCid_display() {
    return GbossFactory.getInstance().getDescription(/*"PARAFFIN_DIMENSIONS",*/
    _diMinThicknessCid);
  }

  public String getDiWidthAcrossCid() {
    return _diWidthAcrossCid;
  }

  public String getDiWidthAcrossCid_display() {
    return GbossFactory.getInstance().getDescription(/*"PARAFFIN_DIMENSIONS",*/
    _diWidthAcrossCid);
  }

  public String getBestMaxThicknessCid() {
    return _bestMaxThicknessCid;
  }

  private boolean isHasBestMaxThicknessCid() {
    String cid = getBestMaxThicknessCid();
    return (!ApiFunctions.isEmpty(cid) && !ArtsConstants.PARAFFIN_DIMENSION_NODATA.equals(cid));
  }

  public String getBestMaxThicknessCid_display() {
    if (!isHasBestMaxThicknessCid()) {
      return "?";
    }
    return GbossFactory.getInstance().getDescription(_bestMaxThicknessCid);
  }

  public String getBestMinThicknessCid() {
    return _bestMinThicknessCid;
  }

  private boolean isHasBestMinThicknessCid() {
    String cid = getBestMinThicknessCid();
    return (!ApiFunctions.isEmpty(cid) && !ArtsConstants.PARAFFIN_DIMENSION_NODATA.equals(cid));
  }

  public String getBestMinThicknessCid_display() {
    if (!isHasBestMinThicknessCid()) {
      return "?";
    }
    return GbossFactory.getInstance().getDescription(_bestMinThicknessCid);
  }

  public String getBestWidthAcrossCid() {
    return _bestWidthAcrossCid;
  }

  private boolean isHasBestWidthAcrossCid() {
    String cid = getBestWidthAcrossCid();
    return (!ApiFunctions.isEmpty(cid) && !ArtsConstants.PARAFFIN_DIMENSION_NODATA.equals(cid));
  }

  public String getBestWidthAcrossCid_display() {
    if (!isHasBestWidthAcrossCid()) {
      return "?";
    }
    return GbossFactory.getInstance().getDescription(_bestWidthAcrossCid);
  }

  public String getDonor_id() {
    return donor_id;
  }

  public String getDxOther() {
    return _dxOther;
  }

  public String getFormatDetailCid() {
    return _formatDetailCid;
  }

  public String getFormatDetailCid_display() {
    return GbossFactory.getInstance().getDescription(/*"SAMPLE_FORMAT_DETAIL",*/
    _formatDetailCid);
  }

  public String getHoldAccountId() {
    return _holdAccountId;
  }

  public String getHoldUserId() {
    return _holdUserId;
  }

  public String getInternal_comments() {
    return _internal_comments;
  }

  public String getInternal_comments_js() {
    return Escaper.jsEscapeInXMLAttr(_internal_comments);
  }

  public String getInternalCommentsShort() {
    if (ApiFunctions.isEmpty(_internal_comments))
      return null;

    int length = _internal_comments.trim().length();
    if (length > 30)
      return _internal_comments.substring(0, 30) + " ...";
    else
      return _internal_comments.trim();
  }

  public String getInv_status() {
    return inv_status;
  }

  public String getInventoryStatusDisplay() {
    return FormLogic.lookupInvStatus(inv_status);
  }

  public String getPathQcStatusDisplay(SecurityInfo securityInfo) {
    StringBuffer sb = new StringBuffer(64);

    String status = getQc_status();

    if (isQcPulled()) {
      sb.append("Pulled on ");
      Escaper.htmlEscape(IcpUtils.formatDate(getPullDate(), false), sb);
      sb.append(".  ");
    }
    else if (isQcReleased()) {
      sb.append("Verified and released.  ");
    }
    else if (isQcVerified()) {
      sb.append("Verified.  ");
    }
    else if (FormLogic.SMPL_QCAWAITING.equals(status)) {
      sb.append("Awaiting verification.  ");
    }
    else if (ApiFunctions.isEmpty(status)) {
      sb.append("Not performed.  ");
    }

    if (FormLogic.SMPL_QCINPROCESS.equals(status)) {
      sb.append("In process.");
    }

    return sb.toString();
  }

  public String getSalesStatusAndHoldInfoDisplay(SecurityInfo securityInfo) {
    String salesStatusDisplay = FormLogic.lookupSalesStatus(sales_status);

    // If the sample is on hold, we show additional information beyond the status itself.
    // We only show specifics of what user/account has the item on hold if the current
    // user is an ICP Superuser or is in the same account as the user who has it on hold.
    // Otherwise, we just display the on-hold sales status but don't say who has it on hold.

    String holdAccount = getHoldAccountId();
    if (ApiFunctions.isEmpty(holdAccount)) {
      return salesStatusDisplay;
    }
    else if (
      securityInfo.isHasPrivilege(SecurityInfo.PRIV_ICP_SUPERUSER)
        || holdAccount.equals(securityInfo.getAccount())) {
      StringBuffer sb = new StringBuffer(64);
      sb.append(salesStatusDisplay);
      sb.append(": ");
      Escaper.htmlEscape(getHoldUserId(), sb);
      sb.append(" (");
      Escaper.htmlEscape(holdAccount, sb);
      sb.append(')');
      return sb.toString();
    }
    else {
      return salesStatusDisplay;
    }
  }

  public SlideData getLatestSlide() {
    if (slides != null && slides.size() > 0) {
      return (SlideData) slides.get(0);
    }
    else {
      return null;
    }
  }

  public String getNecrosis() {
    return _necrosis;
  }

  public AsmData getParent() {
    return parent;
  }

  public String getProjectStatus() {
    return _projectStatus;
  }

  public String getQc_status() {
    return qc_status;
  }

  public DateData getReceipt_date() {
    return receipt_date;
  }

  public String getSales_status() {
    return sales_status;
  }

  public String getSample_id() {
    return sample_id;
  }

  public String getLogicalRepositoryNamesHtml(SecurityInfo securityInfo) {
    List reposList = getLogicalRepositories();

    if (ApiFunctions.isEmpty(reposList)) {
      return "None";
    }

    StringBuffer sb = new StringBuffer(100);

    // If the user is someone who is allowed to see ICP pages for Logical Repositories,
    // make the names ICP links.  Most users can't see these ICP pages so we don't taunt
    // them by making them links for everyone.

    boolean useIcpLinks = IcpUtils.isAuthorizedToViewInventoryGroupIcpPages(securityInfo);

    Iterator iterator = reposList.iterator();
    boolean insertComma = false;
    while (iterator.hasNext()) {
      LogicalRepository lr = (LogicalRepository) iterator.next();
      if (insertComma) {
        sb.append(", ");
      }
      else {
        insertComma = true;
      }
      if (useIcpLinks) {
        sb.append(
          IcpUtils.prepareLink(
            FormLogic.makePrefixedLogicalRepositoryId(lr.getId()),
            lr.getFullName(),
            securityInfo));
      }
      else {
        Escaper.htmlEscape(lr.getFullName(), sb);
      }
    }

    return sb.toString();
  }

  public String getSample_id_v(SecurityInfo securityInfo, boolean useIcpLink) {
    StringBuffer sb = new StringBuffer(128);

    if (useIcpLink) {
      sb.append(IcpUtils.prepareLink(sample_id, securityInfo));
    }
    else {
      Escaper.htmlEscape(sample_id, sb);
    }
    
    if (!ApiFunctions.isEmpty(getCustomer_id())) {
      sb.append(" (");
      Escaper.htmlEscapeAndPreserveWhitespace(getCustomer_id(), sb);
      sb.append(")");
    }

    return sb.toString();
  }

  public String getSampleSizeMeetsSpecs() {
    return _sampleSizeMeetsSpecs;
  }

  public SampleStatusData getLast_transfer_status() {
    return last_transfer_status;
  }

  public int getSlide_count() {
    if (slides != null) {
      return slides.size();
    }
    else {
      return 0;
    }
  }

  public List getSlides() {
    return slides;
  }

  public String getTcOtherOrigin() {
    return _tcOtherOrigin;
  }

  public TopLineData getTopLine() {
    return topLine;
  }

  public String getType_fixative() {
    return type_fixative;
  }

  public String getViablelesioncells() {
    return _viablelesioncells;
  }

  public String getViablenormalcomp() {
    return _viablenormalcomp;
  }

  public String getViabletumercells() {
    return _viabletumercells;
  }

  public boolean isExists() {
    return exists;
  }

  public void removeSlide(SlideData newSlide) {
    slides.remove(newSlide);
  }

  public void setAccountId(String newAccountId) {
    _accountId = newAccountId;
  }

  public void setAccount_name(String newAccount_name) {
    account_name = newAccount_name;
  }

  public void setAcellularstroma(String newAcellularstroma) {
    _acellularstroma = newAcellularstroma;
  }

  public void setRestricted(boolean newRestricted) {
    _restricted = newRestricted;
  }

  public void setAsm_id(String newAsm_id) {
    asm_id = newAsm_id;
  }

  public void setAsm_position(String newAsm_position) {
    asm_position = newAsm_position;
  }

  public void setBox(BoxDto newBox) {
    box = newBox;
  }

  public void setBox_barcode_id(String newBox_barcode_id) {
    box_barcode_id = newBox_barcode_id;
  }

  public void setCase_id(String newCase_id) {
    case_id = newCase_id;
  }

  public void setCell_ref_location(String newCell_ref_location) {
    cell_ref_location = newCell_ref_location;
  }

  public void setCellularstroma(String newCellularstroma) {
    _cellularstroma = newCellularstroma;
  }

  public void setComment(String newComment) {
    comment = newComment;
  }

  public void setComments(String newComments) {
    _comments = newComments;
  }

  public void setDiMaxThicknessCid(String newDiMaxThicknessCid) {
    _diMaxThicknessCid = newDiMaxThicknessCid;
  }

  public void setDiMinThicknessCid(String newDiMinThicknessCid) {
    _diMinThicknessCid = newDiMinThicknessCid;
  }

  public void setDiWidthAcrossCid(String newDiWidthAcrossCid) {
    _diWidthAcrossCid = newDiWidthAcrossCid;
  }

  public void setBestMaxThicknessCid(String newBestMaxThicknessCid) {
    _bestMaxThicknessCid = newBestMaxThicknessCid;
  }

  public void setBestMinThicknessCid(String newBestMinThicknessCid) {
    _bestMinThicknessCid = newBestMinThicknessCid;
  }

  public void setBestWidthAcrossCid(String newBestWidthAcrossCid) {
    _bestWidthAcrossCid = newBestWidthAcrossCid;
  }

  public void setDonor_id(String newDonor_id) {
    donor_id = newDonor_id;
  }

  public void setDxOther(String dxOther) {
    _dxOther = dxOther;
  }

  public void setExists(boolean newExists) {
    exists = newExists;
  }

  public void setFormatDetailCid(String newFormatDetailCid) {
    _formatDetailCid = newFormatDetailCid;
  }

  public void setHoldAccountId(String newHoldAccountId) {
    _holdAccountId = newHoldAccountId;
  }

  public void setHoldUserId(String newHoldUserId) {
    _holdUserId = newHoldUserId;
  }

  public void setInternal_comments(String newInternal_comments) {
    _internal_comments = newInternal_comments;
  }

  public void setInv_status(String newInv_status) {
    inv_status = newInv_status;
  }

  public void setNecrosis(String newNecrosis) {
    _necrosis = newNecrosis;
  }

  public void setParent(AsmData newParent) {
    parent = newParent;
  }

  public void setProjectStatus(String projectStatus) {
    _projectStatus = projectStatus;
  }

  public void setQc_status(String newQc_status) {
    qc_status = newQc_status;
  }

  public void setReceipt_date(DateData newReceipt_date) {
    receipt_date = newReceipt_date;
  }

  public void setSales_status(String newSales_status) {
    sales_status = newSales_status;
  }

  public void setSample_id(String newSample_id) {
    sample_id = newSample_id;
  }

  public void setSampleSizeMeetsSpecs(String newSampleSizeMeetsSpecs) {
    _sampleSizeMeetsSpecs = newSampleSizeMeetsSpecs;
  }

  public void setLast_transfer_status(SampleStatusData newLast_transfer_status) {
    last_transfer_status = newLast_transfer_status;
  }

  public void setSlides(List newSlides) {
    slides = newSlides;
  }

  public void setTcOtherOrigin(String tcOtherOrigin) {
    _tcOtherOrigin = tcOtherOrigin;
  }

  public void setTopLine(TopLineData newTopLine) {
    topLine = newTopLine;
  }

  public void setType_fixative(String newType_fixative) {
    type_fixative = newType_fixative;
  }

  public void setViablelesioncells(String newViablelesioncells) {
    _viablelesioncells = newViablelesioncells;
  }

  public void setViablenormalcomp(String newViablenormalcomp) {
    _viablenormalcomp = newViablenormalcomp;
  }

  public void setViabletumercells(String newViabletumercells) {
    _viabletumercells = newViabletumercells;
  }

  public String getTcOtherFinding() {
    return _tcOtherFinding;
  }

  public void setTcOtherFinding(String tcOtherFinding) {
    _tcOtherFinding = tcOtherFinding;
  }

  public void setTissueFinding(String tissueFinding) {
    _tissueFinding = tissueFinding;
  }

  public void setTissueOrigin(String tissueOrigin) {
    _tissueOrigin = tissueOrigin;
  }

  public Timestamp getPullDate() {
    return _pullDate;
  }

  public void setPullDate(Timestamp pullDate) {
    _pullDate = pullDate;
  }

  public Integer getHoursInFixative() {
    return _hoursInFixative;
  }

  public void setHoursInFixative(Integer hoursInFixative) {
    this._hoursInFixative = hoursInFixative;
  }

  public String getSecCount() {
    return _secCount;
  }

  public void setSecCount(String secCount) {
    _secCount = secCount;
  }

  public String getParaffinFeatureCid() {
    return _paraffinFeatureCid;
  }

  public void setParaffinFeatureCid(String paraffinFeatureCid) {
    _paraffinFeatureCid = paraffinFeatureCid;
  }

  public String getOtherParaffinFeature() {
    return _otherParaffinFeature;
  }

  public void setOtherParaffinFeature(String otherParaffinFeature) {
    _otherParaffinFeature = otherParaffinFeature;
  }

  /**
   * @return The list of logical repositories that the sample is in to which the current
   *    user has access (this may be a subset of the complete set of logical repositories
   *    that the sample is in).  Each item in the list is a {@link LogicalRepository} object. 
   */
  public List getLogicalRepositories() {
    return _logicalRepositories;
  }

  /**
   * @param list The list of logical repositories that the sample is in to which the current
   *    user has access (this may be a subset of the complete set of logical repositories
   *    that the sample is in).  Each item in the list is a {@link LogicalRepository} object. 
   */
  public void setLogicalRepositories(List list) {
    _logicalRepositories = list;
  }

  public String getBmsYN() {
    return _bmsYN;
  }

  public void setBmsYN(String string) {
    _bmsYN = string;
  }

  public String getLastKnownLocationId() {
    return _lastKnownLocationId;
  }

  public void setLastKnownLocationId(String string) {
    _lastKnownLocationId = string;
  }

  public String getSampleLocationDisplay(SecurityInfo securityInfo) {
    boolean isBoxStored = false;
    BoxDto box = getBox();
    if (box != null) {
      isBoxStored = box.isActiveInv();
    }
    if (isBoxStored) {
      return box.getBoxLocationDisplay(securityInfo);
    }
    else {
      StringBuffer sb = new StringBuffer(64);
      sb.append("Not In Active Inventory");
      String lastLoc = getLastKnownLocationId();
      if (!ApiFunctions.isEmpty(lastLoc)) {
        sb.append(".  Last stored at ");
        Escaper.htmlEscape(lastLoc, sb);
        sb.append('.');
      }
      return sb.toString();
    }
  }

  public String getHistoReembedReasonCid() {
    return _histoReembedReasonCid;
  }

  public String getHistoReembedReasonCid_display() {
    return GbossFactory.getInstance().getDescription(_histoReembedReasonCid);
  }

  public void setHistoReembedReasonCid(String histoReembedReasonCid) {
    _histoReembedReasonCid = histoReembedReasonCid;
  }

  public String getOtherHistoReembedReason() {
    return _otherHistoReembedReason;
  }

  public void setOtherHistoReembedReason(String otherHistoReembedReason) {
    _otherHistoReembedReason = otherHistoReembedReason;
  }

  public String getHistoSubdivisible() {
    return _histoSubdivisible;
  }

  public void setHistoSubdivisible(String histoSubdivisible) {
    _histoSubdivisible = histoSubdivisible;
  }

  public String getHistoNotes() {
    return _histoNotes;
  }

  public void setHistoNotes(String histoNotes) {
    _histoNotes = histoNotes;
  }

  public String getParentBarcodeId() {
    return _parentBarcodeId;
  }

  public void setParentBarcodeId(String parentBarcodeId) {
    _parentBarcodeId = parentBarcodeId;
  }

  public Timestamp getBornDate() {
    return _bornDate;
  }

  public String getBornDateDisplay() {
    return IcpUtils.formatDate(getBornDate(), false);
  }

  public void setBornDate(Timestamp bornDate) {
    _bornDate = bornDate;
  }

  public Timestamp getSubdivisionDate() {
    return _subdivisionDate;
  }

  public void setSubdivisionDate(Timestamp subdivisionDate) {
    _subdivisionDate = subdivisionDate;
  }

  public IdList getSubdivisionChildIds() {
    return _subdivisionChildIds;
  }

  public void setSubdivisionChildIds(IdList subdivisionChildIds) {
    _subdivisionChildIds = subdivisionChildIds;
  }

  /**
   * Return true if the {@link #getHistoInfoHtml(SecurityInfo)} method would have anything
   * to display.
   * 
   * @return True if there is histology summary information to display.
   */
  public boolean isHasHistoInfo() {
    return (
      (getSubdivisionDate() != null)
        || isHasBestMinThicknessCid()
        || isHasBestMaxThicknessCid()
        || isHasBestWidthAcrossCid()
        || !ApiFunctions.isEmpty(getHistoReembedReasonCid())
        || !ApiFunctions.isEmpty(getOtherHistoReembedReason())
        || !ApiFunctions.isEmpty(getHistoSubdivisible())
        || !ApiFunctions.isEmpty(getParentBarcodeId())
        || !ApiFunctions.isEmpty(getHistoNotes()));
  }
  
  public boolean isBms() {
    boolean bms = false;
    if (!ApiFunctions.isEmpty(_bmsYN)) {
      if ("Y".equals(_bmsYN)) {
        bms = true;
      }
    }
    return bms;
  }

  public String getHistoInfoHtml(SecurityInfo securityInfo) {
    // When you update this, you may also need to update isHasHistoInfo, which returns
    // a boolean value indicating whether this method would have anything to display.

    StringBuffer sb = new StringBuffer(1024);

    sb.append("<ul style=\"margin-left: 20px; margin-bottom: 0px;\">");

    if (isHasBestMinThicknessCid() || isHasBestMaxThicknessCid() || isHasBestWidthAcrossCid()) {
      sb.append("<li>Thickness: min = ");
      Escaper.htmlEscape(getBestMinThicknessCid_display(), sb);
      sb.append(", max = ");
      Escaper.htmlEscape(getBestMaxThicknessCid_display(), sb);
      sb.append("; Width: ");
      Escaper.htmlEscape(getBestWidthAcrossCid_display(), sb);
      sb.append("</li>");
    }

    if (!ApiFunctions.isEmpty(getHistoReembedReasonCid())
      || !ApiFunctions.isEmpty(getOtherHistoReembedReason())) {
      sb.append("<li>Re-embedded: ");
      if (FormLogic.OTHER_HISTO_REEMBED_REASON.equals(getHistoReembedReasonCid())) {
        Escaper.htmlEscape(getOtherHistoReembedReason(), sb);
      }
      else {
        Escaper.htmlEscape(getHistoReembedReasonCid_display(), sb);
      }
      sb.append("</li>");
    }

    if (!ApiFunctions.isEmpty(getHistoSubdivisible())
      || !ApiFunctions.isEmpty(getParentBarcodeId())
      || getSubdivisionDate() != null) {

      sb.append("<li>");
      if (!ApiFunctions.isEmpty(getParentBarcodeId())) {
        // This sample is a subdivision child.
        sb.append("Derived from ");
        sb.append(IcpUtils.prepareLink(getParentBarcodeId(), securityInfo));
        sb.append(" on ");
        Escaper.htmlEscape(IcpUtils.formatDate(getBornDate(), false), sb);
      }
      else if (getSubdivisionDate() != null) {
        // This sample is a subdivision parent.
        sb.append("Subdivided into ");
        getSubdivisionChildIds().appendICPHTML(sb, securityInfo);
        sb.append(" on ");
        Escaper.htmlEscape(IcpUtils.formatDate(getSubdivisionDate(), false), sb);
      }
      else if (!ApiFunctions.isEmpty(getHistoSubdivisible())) {
        // This sample is neither a parent nor child, but it has been evaluated
        // for subdivisibility.
        if ("Y".equalsIgnoreCase(getHistoSubdivisible())) {
          sb.append("This sample is subdivisible.");
        }
        else {
          sb.append("This sample is not subdivisible.");
        }
      }

      sb.append("</li>");
    }

    if (!ApiFunctions.isEmpty(getHistoNotes())) {
      sb.append("<li>Notes: ");
      Escaper.htmlEscape(getHistoNotes(), sb);
      sb.append("</li>");
    }

    sb.append("</ul>");

    return sb.toString();
  }

  /**
   * @return
   */
  private boolean isQcVerified() {
    return _qcVerified;
  }

  /**
   * @return
   */
  private boolean isQcReleased() {
    return _qcReleased;
  }

  /**
   * @return
   */
  private boolean isQcPulled() {
    return _qcPulled;
  }

  /**
   * @param b
   */
  public void setQcPulled(boolean b) {
    _qcPulled = b;
  }

  /**
   * @param b
   */
  public void setQcReleased(boolean b) {
    _qcReleased = b;
  }

  /**
   * @param b
   */
  public void setQcVerified(boolean b) {
    _qcVerified = b;
  }

  /**
   * @return
   */
  public List getStatuses() {
    return _statuses;
  }

  /**
   * @param list
   */
  public void setStatuses(List list) {
    _statuses = list;
  }

  /**
   * @return
   */
  public String getCustomer_id() {
    return customer_id;
  }

  /**
   * @param string
   */
  public void setCustomer_id(String string) {
    customer_id = string;
  }

  /**
   * @return
   */
  public List getChildSamples() {
    return _childSamples;
  }

  /**
   * @return
   */
  public String getChildSampleIdsHtml(SecurityInfo securityInfo) {
    List ids = new ArrayList();
    List idTexts = new ArrayList();
    if (!ApiFunctions.isEmpty(_childSamples)) {
      Iterator iterator = _childSamples.iterator();
      while (iterator.hasNext()) {
        SampleData sample = (SampleData)iterator.next();
        ids.add(sample.getSample_id());
        idTexts.add(IltdsUtils.getSampleIdAndAlias(sample));
      }
    }
    StringBuffer returnValue = new StringBuffer(100);
    IdList list = new IdList(ids);
    list.appendICPHTML(returnValue, idTexts, securityInfo);
    return returnValue.toString();
  }

  /**
   * @return
   */
  public List getParentSamples() {
    return _parentSamples;
  }

  /**
   * @return
   */
  public String getParentSampleIdsHtml(SecurityInfo securityInfo) {
    List ids = new ArrayList();
    List idTexts = new ArrayList();
    if (!ApiFunctions.isEmpty(_parentSamples)) {
      Iterator iterator = _parentSamples.iterator();
      while (iterator.hasNext()) {
        SampleData sample = (SampleData)iterator.next();
        ids.add(sample.getSample_id());
        idTexts.add(IltdsUtils.getSampleIdAndAlias(sample));
      }
    }
    StringBuffer returnValue = new StringBuffer(100);
    IdList list = new IdList(ids);
    list.appendICPHTML(returnValue, idTexts, securityInfo);
    return returnValue.toString();
  }

  /**
   * @param list
   */
  public void setChildSamples(List list) {
    _childSamples = list;
  }

  /**
   * @param list
   */
  public void setParentSamples(List list) {
    _parentSamples = list;
  }

  /**
   * @return
   */
  public String getSource() {
    return _source;
  }

  /**
   * @param string
   */
  public void setSource(String string) {
    _source = string;
  }

}