
function User() {
  this.setUserId = setUserId;
  this.getUserId = getUserId;
  this.setFirstName = setFirstName;
  this.getFirstName = getFirstName;
  this.setLastName = setLastName;
  this.getLastName = getLastName;
  this.getFullName = getFullName;
  
  var _self = this;
  var _userId;
  var _firstName;
  var _lastName;
  
  function setUserId(userId) {
    _userId = userId;
  }
  
  function getUserId() {
    return _userId;
  }
  
  function setFirstName(firstName) {
    _firstName = firstName;
  }
  
  function getFirstName() {
    return _firstName;
  }
  
  function setLastName(lastName) {
    _lastName = lastName;
  }
  
  function getLastName() {
    return _lastName;
  }
  
  function getFullName() {
    return getLastName() + ", " + getFirstName();
  }
}

function UserList() {
  this.addUser = addUser;
  this.getUser = getUser; 
  this.getFirstUser = getFirstUser;
  this.getNextUser = getNextUser;
  
  var _users = new LinkedList(true);
  var _self = this;
  

  function addUser(user) {
	_users.insertBefore(null, user, user.getUserId());
  }

  function getUser(userId) {
	var cell = _users.getCellByKey(userId);	
	return (cell == null) ? null : cell.item;
  }
  
  function getFirstUser() {
	this.currentCell = _users.getFirstCell();
	if (this.currentCell == null) {
		return null;
	}
	else {
		return this.currentCell.item;
	}
  }
  
  function getNextUser() {
	if (this.currentCell == null) {
		return null;
	}
	else {
		this.currentCell = _users.getNextCell(this.currentCell);
		if (this.currentCell == null) {
			return null;
		}
		else {
			return this.currentCell.item;
		}
	}
  }
}
