
function Privilege() {
  this.setId = setId;
  this.getId = getId;
  this.setDescription = setDescription;
  this.getDescription = getDescription;
  
  var _self = this;
  var _id;
  var _description;
  
  function setId(id) {
    _id = id;
  }
  
  function getId() {
    return _id;
  }
  
  function setDescription(description) {
    _description = description;
  }
  
  function getDescription() {
    return _description;
  }
}

function PrivilegeList() {
  this.addPrivilege = addPrivilege;
  this.getPrivilege = getPrivilege; 
  this.getFirstPrivilege = getFirstPrivilege;
  this.getNextPrivilege = getNextPrivilege;
  
  var _privileges = new LinkedList(true);
  var _self = this;
  

  function addPrivilege(privilege) {
	_privileges.insertBefore(null, privilege, privilege.getId());
  }

  function getPrivilege(id) {
	var cell = _privileges.getCellByKey(id);	
	return (cell == null) ? null : cell.item;
  }
  
  function getFirstPrivilege() {
	this.currentCell = _privileges.getFirstCell();
	if (this.currentCell == null) {
		return null;
	}
	else {
		return this.currentCell.item;
	}
  }
  
  function getNextPrivilege() {
	if (this.currentCell == null) {
		return null;
	}
	else {
		this.currentCell = _privileges.getNextCell(this.currentCell);
		if (this.currentCell == null) {
			return null;
		}
		else {
			return this.currentCell.item;
		}
	}
  }
}
