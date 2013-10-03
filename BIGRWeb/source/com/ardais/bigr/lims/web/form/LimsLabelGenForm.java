package com.ardais.bigr.lims.web.form;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.api.Escaper;
import com.ardais.bigr.api.ValidateIds;
import com.ardais.bigr.iltds.btx.BTXDetails;
import com.ardais.bigr.lims.btx.BTXDetailsCreateSlides;
import com.ardais.bigr.lims.btx.BTXDetailsCreateSlidesPrepare;
import com.ardais.bigr.lims.javabeans.CreateSlidesPrepareData;
import com.ardais.bigr.lims.javabeans.SlideData;
import com.ardais.bigr.web.action.BigrActionMapping;
import com.ardais.bigr.web.form.BigrActionForm;

/**
 * @author JThompson
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class LimsLabelGenForm extends BigrActionForm {

  private String _limsUserId;
  private String _sampleId;
  private String[] _sampleList;
  private String[] _slideCountlist;

  private String[] _blockLabelList;
  private String[] _slideLabelList;
  private String[] _procedureList;
  private String[] _cutLevelList;
  private String[] _isNewList;
  private String[] _isPulledList;
  private ArrayList _slideDataList;
  private String[] _slideNumberList;
  private String _printOneSlide;
  private String _printAllSlides;
  private String[] _hasBeenPrinted;
  private String[] _isRequestedList;
  private String _getLabels;
  private String _message;

  private String _operationType;
  /**
   * @see com.ardais.bigr.web.form.BigrActionForm#doReset(BigrActionMapping, HttpServletRequest)
   */
  protected void doReset(BigrActionMapping mapping, HttpServletRequest request) {
    _sampleList = null;
    _slideCountlist = null;
    _blockLabelList = null;
    _slideLabelList = null;
    _procedureList = null;
    _cutLevelList = null;
    _isNewList = null;
    _isPulledList = null;
    _slideDataList = null;
    _slideNumberList = null;
    _printOneSlide = null;
    _printAllSlides = null;
    _hasBeenPrinted = null;
    _isRequestedList = null;
    _message = null;    
    
  }

  /**
   * @see com.ardais.bigr.web.form.BigrActionForm#doValidate(BigrActionMapping, HttpServletRequest)
   */
  public ActionErrors doValidate(BigrActionMapping mapping, HttpServletRequest request) {
    ActionErrors errors = new ActionErrors();
    //Label gen
    if (mapping.getPath().equals("/lims/limsLabelGen")) {

      // If there's a slide label, validate it and convert it to canonical form.
      String sampleId = getSampleId();
      if (!ApiFunctions.isEmpty(sampleId)) {
        // Canonicalize the id here
        String canonicalSampleId =
          ValidateIds.validateId(sampleId, ValidateIds.TYPESET_SAMPLE, true);
        if (canonicalSampleId == null) {
          errors.add(
            "sampleId",
            new ActionError("lims.error.invalidSample", Escaper.htmlEscape(sampleId)));
        }
        else {
          sampleId = canonicalSampleId;
          setSampleId(sampleId);
        }
      }

      //validate valid user id.		
      if (ApiFunctions.isEmpty(getLimsUserId())) {
        errors.add("user", new ActionError("lims.error.invalidLIMSUser"));
        setLimsUserId(null);
      }
      //No sample entered and "Get Label(s)" button pressed.
      else if (ApiFunctions.isEmpty(getSampleId()) && ApiFunctions.isEmpty(getGetLabels())) {
        //Set operation type.        
        setOperationType(BTXDetailsCreateSlidesPrepare.OP_TYPE_SCAN_USER);
      }
      //Sample Id entered and "Get Label(s)" button not pressed.
      else if (!ApiFunctions.isEmpty(getSampleId()) && ApiFunctions.isEmpty(getGetLabels())) {
        //Set operation type.        
        setOperationType(BTXDetailsCreateSlidesPrepare.OP_TYPE_ADD_SAMPLE);
        //Check for the duplicate sample.
        if (getSampleList() != null) {
          List samples = Arrays.asList(getSampleList());
          if (samples.contains(getSampleId())) {
            errors.add(
              "duplicateSample",
              new ActionError("lims.error.duplicateSample", getSampleId()));
            setSampleId(null);
          }
        }
      }
      //"Get Label(s)" button pressed.
      else if (!ApiFunctions.isEmpty(getGetLabels())) {
        //Set operation type.
        setOperationType(BTXDetailsCreateSlidesPrepare.OP_TYPE_GET_LABEL);
        //valid list of slide numbers
        String[] slideNumbers = getSlideCountlist();
        if (slideNumbers != null) {
          for (int i = 0; i < slideNumbers.length; i++) {
            try {
              new Integer(StringUtils.trim(slideNumbers[i]));
            }
            catch (NumberFormatException num) {
              errors.add(
                "slideNumber" + i,
                new ActionError("lims.error.invalidSlideNumber", slideNumbers[i]));
            }
          }
        }
      }
      //If something other happens.
      else {
        errors.add("user", new ActionError("lims.invalid.operation"));
      }

    }
    //Save Labels	
    else if (mapping.getPath().equals("/lims/limsLabelGenSaveLabel")) {
      //Set operation type.
      setOperationType(BTXDetailsCreateSlides.OP_TYPE_SAVE_LABELS);
      //valid procedure and valid cut level	
      String[] procedure = getProcedureList();
      String[] cutLevel = getCutLevelList();
      String[] isNew = getIsNewList();
      for (int i = 0; i < procedure.length; i++) {
        if (isNew[i].trim().equals("true")) {
          try {
            int blah = new Integer(cutLevel[i].trim()).intValue();
            if (blah <= 0) {
              errors.add(
                "cutLevel" + i,
                new ActionError("lims.error.invalidCutLevel", cutLevel[i]));
            }
          }
          catch (NumberFormatException num) {
            errors.add("cutLevel" + i, new ActionError("lims.error.invalidCutLevel", cutLevel[i]));
          }
          if (procedure[i] == null || procedure[i].length() < 1) {
            errors.add(
              "procedure" + i,
              new ActionError("lims.error.invalidProcedure", procedure[i]));
          }
        }
      }
    }
    return errors;
  }

  /**
   * @see com.ardais.bigr.iltds.btx.DescribableIntoBtxDetails#describeIntoBtxDetails(BTXDetails, BigrActionMapping, HttpServletRequest)
   */
  public void describeIntoBtxDetails(
    BTXDetails btxDetails,
    BigrActionMapping mapping,
    HttpServletRequest request) {
    //If user entered User Id.
    if (getOperationType().equals(BTXDetailsCreateSlidesPrepare.OP_TYPE_SCAN_USER)) {
      BTXDetailsCreateSlidesPrepare createSlidesPrepare =
        (BTXDetailsCreateSlidesPrepare) btxDetails;
      createSlidesPrepare.setOperationType(BTXDetailsCreateSlidesPrepare.OP_TYPE_SCAN_USER);
      createSlidesPrepare.setLimsUserId(getLimsUserId());
    }
    //If user entered/scanned sample Id.
    else if (getOperationType().equals(BTXDetailsCreateSlidesPrepare.OP_TYPE_ADD_SAMPLE)) {
      BTXDetailsCreateSlidesPrepare createSlidesPrepare =
        (BTXDetailsCreateSlidesPrepare) btxDetails;
      createSlidesPrepare.setOperationType(BTXDetailsCreateSlidesPrepare.OP_TYPE_ADD_SAMPLE);
      createSlidesPrepare.setLimsUserId(getLimsUserId());
      createSlidesPrepare.setCurrentSample(getSampleId());
    }
    //If "Get Label(s)" button pressed
    else if (getOperationType().equals(BTXDetailsCreateSlidesPrepare.OP_TYPE_GET_LABEL)) {
      BTXDetailsCreateSlidesPrepare createSlidesPrepare =
        (BTXDetailsCreateSlidesPrepare) btxDetails;
      createSlidesPrepare.setOperationType(BTXDetailsCreateSlidesPrepare.OP_TYPE_GET_LABEL);

      ArrayList csps = new ArrayList();
      //Get cut number for the sliides
      for (int i = 0; i < getSampleList().length; i++) {
        CreateSlidesPrepareData cspData = new CreateSlidesPrepareData();
        cspData.setNumberOfSlides(
          (new Integer(StringUtils.trim(getSlideCountlist()[i]))).intValue());
        cspData.setSampleId(getSampleList()[i]);
        csps.add(cspData);
      }
      createSlidesPrepare.setLimsUserId(getLimsUserId());
      createSlidesPrepare.setCreateSlidesPrepareData(csps);

    }
    //Save Labels
    else if (getOperationType().equals(BTXDetailsCreateSlides.OP_TYPE_SAVE_LABELS)) {
      BTXDetailsCreateSlides createSlides = (BTXDetailsCreateSlides) btxDetails;
      String[] blocks = getBlockLabelList();
      String[] cutLevels = getCutLevelList();
      String[] procedures = getProcedureList();
      String[] news = getIsNewList();
      String[] slides = getSlideLabelList();
      String[] isNew = getIsNewList();
      ArrayList slideDatas = new ArrayList();
      SlideData slide;

      String currentSample = "";
      int currentSampleCount = 1;

      for (int i = 0; i < blocks.length; i++) {
        if (currentSample.equals("")) {
          currentSample = blocks[i].trim();
        }
        else if (currentSample.equals(blocks[i].trim())) {
          currentSampleCount++;
        }
        else if (!currentSample.equals(blocks[i].trim())) {
          currentSample = blocks[i].trim();
          currentSampleCount = 1;
        }

        if (isNew[i].equals("true")) {
          slide = new SlideData();
          slide.setSampleId(blocks[i].trim());
          slide.setSlideId(slides[i].trim());
          slide.setCutLevel(Integer.parseInt(cutLevels[i].trim()));
          slide.setProcedure(procedures[i].trim());
          slide.setNew(Boolean.getBoolean(news[i].trim()));
          slide.setSlideNumber(currentSampleCount);
          slideDatas.add(slide);
        }
      }

      createSlides.setSlideData(slideDatas);
      createSlides.setLimsUserId(getLimsUserId());
    }
  }

  /**
   * @see com.ardais.bigr.web.form.BigrActionForm#populateFromBtxDetails(BTXDetails)
   */
  public void populateFromBtxDetails(BTXDetails btxDetails) {
    //If user enters/scans user Id.
    if (getOperationType().equals(BTXDetailsCreateSlidesPrepare.OP_TYPE_SCAN_USER)) {
      BTXDetailsCreateSlidesPrepare createSlidesPrepare =
        (BTXDetailsCreateSlidesPrepare) btxDetails;
      if (!createSlidesPrepare.isTransactionCompleted()) {
        setLimsUserId(null);
      }
    }
    //If user enters/scans sample Id.
    if (getOperationType().equals(BTXDetailsCreateSlidesPrepare.OP_TYPE_ADD_SAMPLE)) {
      BTXDetailsCreateSlidesPrepare createSlidesPrepare =
        (BTXDetailsCreateSlidesPrepare) btxDetails;
      if (createSlidesPrepare.isTransactionCompleted()) {
        //add sample to sample list and add pulled/requested to their lists.
        setSampleList(addItemToArray(getSampleId(), getSampleList()));
        setIsPulledList(
          addItemToArray(
            String.valueOf(createSlidesPrepare.isCurrentSamplePulled()),
            getIsPulledList()));
        setIsRequestedList(
          addItemToArray(
            String.valueOf(createSlidesPrepare.isCurrentSampleRequested()),
            getIsRequestedList()));
      }
      setSampleId(null);
    }
    //If "Get Label(s)" button pressed.
    else if (getOperationType().equals(BTXDetailsCreateSlidesPrepare.OP_TYPE_GET_LABEL)) {
      BTXDetailsCreateSlidesPrepare createSlidesPrepare =
        (BTXDetailsCreateSlidesPrepare) btxDetails;
      //Initialize lists                                    
      ArrayList blockLabelList = new ArrayList();
      ArrayList slideLabelList = new ArrayList();
      ArrayList procedureList = new ArrayList();
      ArrayList cutLevelList = new ArrayList();
      ArrayList isNewList = new ArrayList();
      ArrayList isPulledList = new ArrayList();      

      //Populate lists 
      Iterator sampleIter = createSlidesPrepare.getCreateSlidesPrepareData().iterator();
      while (sampleIter.hasNext()) {
        CreateSlidesPrepareData cspData = (CreateSlidesPrepareData) sampleIter.next();
        Iterator slideIter = cspData.getSlideData().iterator();
        while (slideIter.hasNext()) {
          SlideData slide = (SlideData) slideIter.next();
          blockLabelList.add(slide.getSampleId());
          slideLabelList.add(slide.getSlideId());
          //if procedure is null, we need to provide a value or 
          //we get a null pointer exception.
          //If the slide is new, default the procedure to "H&E", 
          //otherwise put a blank space 
          //into the list.
          if (slide.getProcedure() == null) {
            if (slide.isNew()) {
              procedureList.add("H&E");
            }
            else {
              procedureList.add("");
            }
          }
          else {
            procedureList.add(slide.getProcedure());
          }
          //if cut level is less than 1, and this is a new slide, 
          //default the value to 1.
          if (slide.isNew() && slide.getCutLevel() < 1) {
            cutLevelList.add("1");
          }
          else {
            cutLevelList.add(String.valueOf(slide.getCutLevel()));
          }
          isNewList.add(String.valueOf(slide.isNew()));
        }
      }
      //Set lists to form
      setBlockLabelList((String[]) blockLabelList.toArray(new String[0]));
      setSlideLabelList((String[]) slideLabelList.toArray(new String[0]));
      setProcedureList((String[]) procedureList.toArray(new String[0]));
      setCutLevelList((String[]) cutLevelList.toArray(new String[0]));
      setIsNewList((String[]) isNewList.toArray(new String[0]));
      setIsPulledList((String[]) isPulledList.toArray(new String[0]));

    }
    //Save labels
    else if (getOperationType().equals(BTXDetailsCreateSlides.OP_TYPE_SAVE_LABELS)) {
      BTXDetailsCreateSlides createSlides = (BTXDetailsCreateSlides) btxDetails;
      List SlideDataList = createSlides.getSlideData();
      //Initialize LimsLabelGenForm with list of Slide Data
      int size = SlideDataList.size();
      String[] blocks = new String[size];
      String[] slides = new String[size];
      String[] procedures = new String[size];
      String[] cutLevels = new String[size];
      String[] slideNumbers = new String[size];
      String[] hasBeenPrinted = new String[size];

      for (int i = 0; i < size; i++) {
        SlideData databean = (SlideData) SlideDataList.get(i);
        blocks[i] = databean.getSampleId();
        slides[i] = databean.getSlideId();
        procedures[i] = databean.getProcedure();
        cutLevels[i] = String.valueOf(databean.getCutLevel());
        slideNumbers[i] = String.valueOf(databean.getSlideNumber());
        hasBeenPrinted[i] = new String("false");
      }

      setBlockLabelList(blocks);
      setSlideLabelList(slides);
      setProcedureList(procedures);
      setCutLevelList(cutLevels);
      setSlideNumberList(slideNumbers);
      setHasBeenPrinted(hasBeenPrinted);
    }
  }

  /**
   * Adds item to myArray.
   * @param <code>String</code> item
   * @param <code>String[]</code> myArray.
   */
  private String[] addItemToArray(String item, String[] myArray) {
    if (!ApiFunctions.isEmpty(item)) {
      if (ApiFunctions.isEmpty(myArray)) {
        return myArray = new String[] { item };
      }
      else {
        String[] temp = new String[myArray.length + 1];
        System.arraycopy(myArray, 0, temp, 0, myArray.length);
        temp[temp.length - 1] = item;
        return temp;
      }
    }
    else {
      return null;
    }
  }

  /**
   * Returns the _limsUserId.
   * @return String
   */
  public String getLimsUserId() {
    return _limsUserId;
  }

  /**
   * Returns the _sampleList.
   * @return String[]
   */
  public String[] getSampleList() {
    return _sampleList;
  }

  /**
   * Returns the _slideCountlist.
   * @return String[]
   */
  public String[] getSlideCountlist() {
    return _slideCountlist;
  }

  /**
   * Returns the sampleId.
   * @return String
   */
  public String getSampleId() {
    return _sampleId;
  }

  /**
   * Sets the _limsUserId.
   * @param _limsUserId The _limsUserId to set
   */
  public void setLimsUserId(String limsUserId) {
    _limsUserId = limsUserId;
  }

  /**
   * Sets the _sampleList.
   * @param _sampleList The _sampleList to set
   */
  public void setSampleList(String[] sampleList) {
    _sampleList = sampleList;
  }

  /**
   * Sets the _slideCountlist.
   * @param _slideCountlist The _slideCountlist to set
   */
  public void setSlideCountlist(String[] slideCountlist) {
    _slideCountlist = slideCountlist;
  }

  /**
   * Returns the _blockLabelList.
   * @return String[]
   */
  public String[] getBlockLabelList() {
    return _blockLabelList;
  }

  /**
   * Returns the _cutLevelList.
   * @return String[]
   */
  public String[] getCutLevelList() {
    return _cutLevelList;
  }

  /**
   * Returns the _procedureList.
   * @return String[]
   */
  public String[] getProcedureList() {
    return _procedureList;
  }

  /**
   * Returns the _slideLabelList.
   * @return String[]
   */
  public String[] getSlideLabelList() {
    return _slideLabelList;
  }

  /**
   * Sets the _blockLabelList.
   * @param _blockLabelList The _blockLabelList to set
   */
  public void setBlockLabelList(String[] blockLabelList) {
    _blockLabelList = blockLabelList;
  }

  /**
   * Sets the _cutLevelList.
   * @param _cutLevelList The _cutLevelList to set
   */
  public void setCutLevelList(String[] cutLevelList) {
    _cutLevelList = cutLevelList;
  }

  /**
   * Sets the _procedureList.
   * @param _procedureList The _procedureList to set
   */
  public void setProcedureList(String[] procedureList) {
    _procedureList = procedureList;
  }

  /**
   * Sets the _slideLabelList.
   * @param _slideLabelList The _slideLabelList to set
   */
  public void setSlideLabelList(String[] slideLabelList) {
    _slideLabelList = slideLabelList;
  }

  /**
   * Sets the sampleId.
   * @param sampleId The sampleId to set
   */
  public void setSampleId(String sampleId) {
    _sampleId = sampleId;
  }

  /**
   * Returns the isNewList.
   * @return String[]
   */
  public String[] getIsNewList() {
    return _isNewList;
  }

  /**
   * Returns the isPulledList.
   * @return String[]
   */
  public String[] getIsPulledList() {
    return _isPulledList;
  }

  /**
   * Sets the isNewList.
   * @param isNewList The isNewList to set
   */
  public void setIsNewList(String[] isNewList) {
    _isNewList = isNewList;
  }

  /**
   * Sets the isPulledList.
   * @param isPulledList The isPulledList to set
   */
  public void setIsPulledList(String[] isPulledList) {
    _isPulledList = isPulledList;
  }

  /**
   * Returns the slideDataList.
   * @return ArrayList
   */
  public ArrayList getSlideDataList() {
    return _slideDataList;
  }

  /**
   * Sets the slideDataList.
   * @param slideDataList The slideDataList to set
   */
  public void setSlideDataList(ArrayList slideDataList) {
    _slideDataList = slideDataList;
  }

  /**
   * Returns the slideNumberList.
   * @return String[]
   */
  public String[] getSlideNumberList() {
    return _slideNumberList;
  }

  /**
   * Sets the slideNumberList.
   * @param slideNumberList The slideNumberList to set
   */
  public void setSlideNumberList(String[] slideNumberList) {
    _slideNumberList = slideNumberList;
  }

  /**
   * Returns the hasBeenPrinted.
   * @return String[]
   */
  public String[] getHasBeenPrinted() {
    return _hasBeenPrinted;
  }

  /**
   * Returns the printAllSlides.
   * @return String
   */
  public String getPrintAllSlides() {
    return _printAllSlides;
  }

  /**
   * Returns the printOneSlide.
   * @return String
   */
  public String getPrintOneSlide() {
    return _printOneSlide;
  }

  /**
   * Sets the hasBeenPrinted.
   * @param hasBeenPrinted The hasBeenPrinted to set
   */
  public void setHasBeenPrinted(String[] hasBeenPrinted) {
    _hasBeenPrinted = hasBeenPrinted;
  }

  /**
   * Sets the printAllSlides.
   * @param printAllSlides The printAllSlides to set
   */
  public void setPrintAllSlides(String printAllSlides) {
    _printAllSlides = printAllSlides;
  }

  /**
   * Sets the printOneSlide.
   * @param printOneSlide The printOneSlide to set
   */
  public void setPrintOneSlide(String printOneSlide) {
    _printOneSlide = printOneSlide;
  }
  /**
   * Returns the isRequestedList.
   * @return String[]
   */
  public String[] getIsRequestedList() {
    return _isRequestedList;
  }

  /**
   * Sets the isRequestedList.
   * @param isRequestedList The isRequestedList to set
   */
  public void setIsRequestedList(String[] isRequestedList) {
    _isRequestedList = isRequestedList;
  }
  /**
   * Returns the operationType.
   * @return String
   */
  public String getOperationType() {
    return _operationType;
  }

  /**
   * Sets the operationType.
   * @param operationType The operationType to set
   */
  public void setOperationType(String operationType) {
    _operationType = operationType;
  }

  /**
   * Returns the getLabels.
   * @return String
   */
  public String getGetLabels() {
    return _getLabels;
  }

  /**
   * Sets the getLabels.
   * @param getLabels The getLabels to set
   */
  public void setGetLabels(String getLabels) {
    _getLabels = getLabels;
  }

  /**
   * Returns the message.
   * @return String
   */
  public String getMessage() {
    return _message;
  }

  /**
   * Sets the message.
   * @param message The message to set
   */
  public void setMessage(String message) {
    _message = message;
  }

}
