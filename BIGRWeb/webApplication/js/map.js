function Map() {
  this.put = put;
  this.get = get;
  this.containsKey = containsKey;
  this.remove = remove;
  this.isEmpty = isEmpty;
  this.keySet = keySet;
  
  var _items = new Array();
  var _self = this;
  
  function put(key, item) {  
    if (containsKey(key)) {
      var myItem = get(key);
      myItem.setItem(item);
    }
    else {
      var mapItem = new MapItem(key, item);
      _items[_items.length] = mapItem;
    }
  }
  
  function keySet() {
    var returnValue = new Array();
    var len = _items.length;
    for (var i=0; i<len; i++) {
      returnValue[returnValue.length] = _items[i].getKey();
    }
    return returnValue;
  }

  function get(key) {
    var len = _items.length;
    for (var i=0; i<len; i++) {
      var mapItem = _items[i];
      if (mapItem.getKey() == key) {
        return _items[i];
      }
    }
    return null;
  }
    
  function containsKey(key) {
    var len = _items.length;
    for (var i=0; i<len; i++) {
      var mapItem = _items[i];
      if (mapItem.getKey() == key) {
        return true;
      }
    }
    return false;
  }

  function remove(key) {
    var len = _items.length;
    var foundIndex = -1;
    for (var i=0; i<len; i++) {
      var mapItem = _items[i];
      if (mapItem.getKey() == key) {
        delete _items[i];
        foundIndex = i;
      }
    }
    //if we deleted an entry, remove it from the array so that the array doesn't contain
    //any empty values
    if (foundIndex >= 0) {
      _items.splice(foundIndex, 1);
    }
  }
  
  function isEmpty() {
    if (_items.length > 0) {
      return false;
    }
    else {
      return true;
    }
  }
}


function MapItem(key, item) {
  this.getKey = getKey;
  this.getItem = getItem;
  this.setItem = setItem;
  
  this._key = key;
  this._item = item;
  var _self = this;

  function getKey() {
    return this._key;
  } 
  
  function getItem() {
    return this._item;
  }
  
  function setItem(item) {
    this._item = item;
  }
  
}
