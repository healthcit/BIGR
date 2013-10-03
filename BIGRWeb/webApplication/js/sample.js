function Sample() {
  this.setBufferTypeCui = setBufferTypeCui;
  this.getBufferTypeCui = getBufferTypeCui;
  this.setBufferTypeOther = setBufferTypeOther;
  this.getBufferTypeOther = getBufferTypeOther;
  this.setCellsPerMl = setCellsPerMl;
  this.getCellsPerMl = getCellsPerMl;
  this.setComment = setComment;
  this.getComment = getComment;
  this.setConcentration = setConcentration;
  this.getConcentration = getConcentration;
  this.setCollectionDateTime = setCollectionDateTime;
  this.setCollectionDateDate = setCollectionDateDate;
  this.setCollectionDateHour = setCollectionDateHour;
  this.setCollectionDateMinute = setCollectionDateMinute;
  this.setCollectionDateMeridian = setCollectionDateMeridian;
  this.getCollectionDateTime = getCollectionDateTime;
  this.getCollectionDateDate = getCollectionDateDate;
  this.getCollectionDateHour = getCollectionDateHour;
  this.getCollectionDateMinute = getCollectionDateMinute;
  this.getCollectionDateMeridian = getCollectionDateMeridian;
  this.setPreservationDateTime = setPreservationDateTime;
  this.setPreservationDateDate = setPreservationDateDate;
  this.setPreservationDateHour = setPreservationDateHour;
  this.setPreservationDateMinute = setPreservationDateMinute;
  this.setPreservationDateMeridian = setPreservationDateMeridian;
  this.getPreservationDateTime = getPreservationDateTime;
  this.getPreservationDateDate = getPreservationDateDate;
  this.getPreservationDateHour = getPreservationDateHour;
  this.getPreservationDateMinute = getPreservationDateMinute;
  this.getPreservationDateMeridian = getPreservationDateMeridian;
  this.setFixativeType = setFixativeType;
  this.getFixativeType = getFixativeType;
  this.setFormatDetail = setFormatDetail;
  this.getFormatDetail = getFormatDetail;
  this.setGrossAppearance = setGrossAppearance;
  this.getGrossAppearance = getGrossAppearance;
  this.setPercentViability = setPercentViability;
  this.getPercentViability = getPercentViability;
  this.setPreparedBy = setPreparedBy; 
  this.getPreparedBy = getPreparedBy; 
  this.setProcedure = setProcedure; 
  this.getProcedure = getProcedure; 
  this.setProcedureOther = setProcedureOther;
  this.getProcedureOther = getProcedureOther;
  this.setTissue = setTissue;
  this.getTissue = getTissue;
  this.setTissueOther = setTissueOther;
  this.getTissueOther = getTissueOther;
  this.setTotalNumOfCells = setTotalNumOfCells;
  this.getTotalNumOfCells = getTotalNumOfCells;
  this.setTotalNumOfCellsUnit = setTotalNumOfCellsUnit;
  this.getTotalNumOfCellsUnit = getTotalNumOfCellsUnit;
  this.setVolume = setVolume;
  this.getVolume = getVolume;
  this.setVolumeUnitCui = setVolumeUnit;
  this.getVolumeUnitCui = getVolumeUnit;
  this.setWeight = setWeight;
  this.getWeight = getWeight;
  this.setWeightUnitCui = setWeightUnit;
  this.getWeightUnitCui = getWeightUnit;
  this.setYield = setYield;
  this.getYield = getYield;
  this.setSource = setSource;
  this.getSource = getSource;
  this.getDataElements = getDataElements;
  
  var _bufferTypeCui = null;
  var _bufferTypeOther = null;
  var _cellsPerMl = null;
  var _comment = null;
  var _concentration = null;
  var _collectionDateTime = null;
  var _collectionDateDate = null;
  var _collectionDateHour = null;
  var _collectionDateMinute = null;
  var _collectionDateMeridian = null;
  var _preservationDateTime = null;
  var _preservationDateDate = null;
  var _preservationDateHour = null;
  var _preservationDateMinute = null;
  var _preservationDateMeridian = null;
  var _fixativeType = null;
  var _formatDetail = null;
  var _grossAppearance = null;
  var _percentViability = null;
  var _preparedBy = null;
  var _procedure = null;
  var _procedureOther = null;
  var _tissue = null;
  var _tissueOther = null;
  var _totalNumOfCells = null;
  var _totalNumOfCellsUnit = null;
  var _volume = null;
  var _volumeUnit = null;
  var _weight = null;
  var _weightUnit = null;
  var _yield = null;
  var _source = null;
  var _dataElements = new DataElements();
  
  function getBufferTypeCui() {
    return _bufferTypeCui;
  }
  
  function setBufferTypeCui(bufferTypeCui) {
    return _bufferTypeCui = bufferTypeCui;
  }
  
  function getBufferTypeOther() {
    return _bufferTypeOther;
  }
  
  function setBufferTypeOther(bufferTypeOther) {
    return _bufferTypeOther = bufferTypeOther;
  }
  
  function getCellsPerMl() {
    return _cellsPerMl;
  }
  
  function setCellsPerMl(cellsPerMl) {
    return _cellsPerMl = cellsPerMl;
  }
  
  function getComment() {
    return _comment;
  }
  
  function setComment(comment) {
    return _comment = comment;
  }
  
  function getConcentration() {
    return _concentration;
  }
  
  function setConcentration(concentration) {
    return _concentration = concentration;
  }
  
  function getCollectionDateTime() {
    return _collectionDateTime;
  }
  
  function getCollectionDateDate() {
    return _collectionDateDate;
  }
  
  function getCollectionDateHour() {
    return _collectionDateHour;
  }
  
  function getCollectionDateMinute() {
    return _collectionDateMinute;
  }
   
  function getCollectionDateMeridian() {
    return _collectionDateMeridian;
  }
  
  function setCollectionDateTime(collectionDateTime) {
    return _collectionDateTime = collectionDateTime;
  }
  
  function setCollectionDateDate(collectionDateDate) {
    return _collectionDateDate = collectionDateDate;
  }
  
  function setCollectionDateHour(collectionDateHour) {
    return _collectionDateHour = collectionDateHour;
  }
  
  function setCollectionDateMinute(collectionDateMinute) {
    return _collectionDateMinute = collectionDateMinute;
  }
  
  function setCollectionDateMeridian(collectionDateMeridian) {
    return _collectionDateMeridian = collectionDateMeridian;
  }
  
  function getPreservationDateTime() {
    return _preservationDateTime;
  }
  
  function getPreservationDateDate() {
    return _preservationDateDate;
  }
  
  function getPreservationDateHour() {
    return _preservationDateHour;
  }
  
  function getPreservationDateMinute() {
    return _preservationDateMinute;
  }
  
  function getPreservationDateMeridian() {
    return _preservationDateMeridian;
  }
  
  function setPreservationDateTime(preservationDateTime) {
    return _preservationDateTime = preservationDateTime;
  }
  
  function setPreservationDateDate(preservationDateDate) {
    return _preservationDateDate = preservationDateDate;
  }
  
  function setPreservationDateHour(preservationDateHour) {
    return _preservationDateHour = preservationDateHour;
  }
  
  function setPreservationDateMinute(preservationDateMinute) {
    return _preservationDateMinute = preservationDateMinute;
  }
  
  function setPreservationDateMeridian(preservationDateMeridian) {
    return _preservationDateMeridian = preservationDateMeridian;
  }
  
  function getFixativeType() {
    return _fixativeType;
  }
  
  function setFixativeType(fixativeType) {
    return _fixativeType = fixativeType;
  }
  
  function getFormatDetail() {
    return _formatDetail;
  }
  
  function setFormatDetail(formatDetail) {
    return _formatDetail = formatDetail;
  }
  
  function getGrossAppearance() {
    return _grossAppearance;
  }
  
  function setGrossAppearance(grossAppearance) {
    return _grossAppearance = grossAppearance;
  }
  
  function getPercentViability() {
    return _percentViability;
  }
  
  function setPercentViability(percentViability) {
    return _percentViability = percentViability;
  }
  
  function getPreparedBy() {
    return _preparedBy;
  }
  
  function setPreparedBy(preparedBy) {
    return _preparedBy = preparedBy;
  }
  
  function getProcedure() {
    return _procedure;
  }
  
  function setProcedure(procedure) {
    return _procedure = procedure;
  }
  
  function getProcedureOther() {
    return _procedureOther;
  }
  
  function setProcedureOther(procedureOther) {
    return _procedureOther = procedureOther;
  }
  
  function getTissue() {
    return _tissue;
  }
  
  function setTissue(tissue) {
    return _tissue = tissue;
  }
  
  function getTissueOther() {
    return _tissueOther;
  }
  
  function setTissueOther(tissueOther) {
    return _tissueOther = tissueOther;
  }
  
  function getTotalNumOfCells() {
    return _totalNumOfCells;
  }
  
  function setTotalNumOfCells(totalNumOfCells) {
    return _totalNumOfCells = totalNumOfCells;
  }
  
  function getTotalNumOfCellsUnit() {
    return _totalNumOfCellsUnit;
  }
  
  function setTotalNumOfCellsUnit(totalNumOfCellsUnit) {
    return _totalNumOfCellsUnit = totalNumOfCellsUnit;
  }
  
  function getVolume() {
    return _volume;
  }
  
  function setVolume(volume) {
    return _volume = volume;
  }
  function setVolumeUnit(volumeUnit){
  return _volumeUnit = volumeUnit;
  }
  function getVolumeUnit(){
  return _volumeUnit;
  }
  function getWeight() {
    return _weight;
  }
  
  function setWeight(weight) {
    return _weight = weight;
  }
  function setWeightUnit(weightUnit){
  return _weightUnit = weightUnit;
  }
  function getWeightUnit(){
  return _weightUnit;
  }
  
  
  function getYield() {
    return _yield;
  }
  
  function setYield(yield) {
    return _yield = yield;
  }
  
  function getSource() {
    return _source;
  }
  
  function setSource(source) {
    return _source = source;
  }
  
  function getDataElements() {
    return _dataElements;
  }
  
}

//TODO - ADE's for DataElementValue?

function DataElementValue() {
  this.setValue = setValue;
  this.getValue = getValue;
  this.setValueOther = setValueOther;
  this.getValueOther = getValueOther;
  
  var _value;
  var _valueOther;
  
  function setValue(value) {
    _value = value;
  }
  
  function getValue() {
    return _value;
  }
  
  function setValueOther(valueOther) {
    _valueOther = valueOther;
  }
  
  function getValueOther() {
    return _valueOther;
  }
}

function DataElement() {
  this.setCui = setCui;
  this.getCui = getCui;
  this.addDataElementValue = addDataElementValue;
  this.getFirstDataElementValue = getFirstDataElementValue;
  this.getNextDataElementValue = getNextDataElementValue;
  this.setNote = setNote;
  this.getNote = getNote;
  
  var _cui;
  var _dataElementValues = new LinkedList(false);
  var _note;
  var _self = this;
  
  function setCui(cui) {
    _cui = cui;
  }
  
  function getCui() {
    return _cui;
  }

  function addDataElementValue(dataElementValue) {
    _dataElementValues.insertBefore(null, dataElementValue, null);
  }
  
  function getFirstDataElementValue() {
	  this.currentCell = _dataElementValues.getFirstCell();
	  if (this.currentCell == null) {
		  return null;
	  }
	  else {
		  return this.currentCell.item;
	  }
  }
  
  function getNextDataElementValue() {
	  if (this.currentCell == null) {
		  return null;
	  }
	  else {
		  this.currentCell = _dataElementValues.getNextCell(this.currentCell);
		  if (this.currentCell == null) {
			  return null;
		  }
		  else {
			  return this.currentCell.item;
		  }
	  }
  }
  
  function setNote(note) {
    _note = note;
  }
  
  function getNote() {
    return _note;
  }
}

function DataElements() {
  this.addDataElement = addDataElement;
  this.getDataElement = getDataElement; 
  this.getFirstDataElement = getFirstDataElement;
  this.getNextDataElement = getNextDataElement;
  
  var _dataElements = new LinkedList(true);
  var _self = this;  

  function addDataElement(dataElement) {
	  _dataElements.insertBefore(null, dataElement, dataElement.getCui());
  }

  function getDataElement(cui) {
	  var cell = _dataElements.getCellByKey(cui);	
	  return (cell == null) ? null : cell.item;
  }
  
  function getFirstDataElement() {
	  this.currentCell = _dataElements.getFirstCell();
	  if (this.currentCell == null) {
		  return null;
	  }
	  else {
		  return this.currentCell.item;
	  }
  }
  
  function getNextDataElement() {
	  if (this.currentCell == null) {
		  return null;
	  }
	  else {
		  this.currentCell = _dataElements.getNextCell(this.currentCell);
		  if (this.currentCell == null) {
			  return null;
		  }
		  else {
			  return this.currentCell.item;
		  }
	  }
  }
}
