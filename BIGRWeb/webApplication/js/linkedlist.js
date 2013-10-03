// ********** START of LinkedList class ***********
// This class combines features of a doubly-linked list and a
// collection that supports lookups by key.  Pass true to the constructor
// to get the keyed behavior, false to get ordinary linked list behavior.
// The main differences between the two are that in the keyed version,
// (1) the getCellByKey and getItemKey methods can be used (they return null
// in the non-keyed version) and (2) if an item with the key that is passed to
// insertBefore/insertAfter already exists, that item's value is set to the
// value to be inserted, but no new cell is inserted, and the existing cell
// isn't moved to a new location in the list.
//
function LinkedList(keyed) {
  // Public methods:
  this.isKeyed = llIsKeyed;
  this.getCellByKey = llGetCellByKey;
  this.getFirstCell = llGetFirstCell;
  this.getLastCell = llGetLastCell;
  this.getNextCell = llGetNextCell;
  this.getPrevCell = llGetPrevCell;
  this.deleteCell = llDeleteCell;
  this.deleteAll = llDeleteAll;
  this.insertAfter = llInsertAfter;
  this.insertBefore = llInsertBefore;
  this.isEmpty = llIsEmpty;
  
  // Data fields (all of these should be treated as private):
  this.keyed = (keyed == true); // coerce to boolean
  this.list = new LinkedListCell(); // dummy node that points to itself
  this.itemMap = new Object();      // key-to-cell map
}

function LinkedListCell() {
  // Public methods:
  this.getItemKey = llcGetItemKey;

  // Public members:
  this.item = null;

  // Private members:
  this.key = null;
  this.prev = this;
  this.next = this;
}

function llcGetItemKey() {
  return this.key;
}

function llIsKeyed() {
  return this.keyed;
}

function llGetCellByKey(key) {
  if (this.keyed) {
    return (this.itemMap[key] || null);
  }
  else return null;
}

function llGetFirstCell() {
  return this.getNextCell(this.list);
}

function llGetLastCell() {
  return this.getPrevCell(this.list);
}

function llGetNextCell(cell) {
  return ((cell.next == this.list) ? null : cell.next);
}

function llGetPrevCell(cell) {
  return ((cell.prev == this.list) ? null : cell.prev);
}

function llDeleteCell(cell) {
  if (this.keyed) delete this.itemMap[cell.key];
  cell.prev.next = cell.next;
  cell.next.prev = cell.prev;
  return this;
}

function llDeleteAll() {
  this.list = new LinkedListCell();
  this.itemMap = new Object();
  return this;
}

function llInsertBefore(cell, item, key) {
  var n = null;
  if (this.keyed) {
    n = this.getCellByKey(key);
    if (null != n) {
      n.item = item;
      return n;
    }
  }
  if (null == cell) cell = this.list; // insert at end of list when cell is null
  n = new LinkedListCell();
  n.item = item;
  cell.prev.next = n;
  n.prev = cell.prev;
  n.next = cell;
  cell.prev = n;
  if (this.keyed) {
    n.key = key;
    this.itemMap[key] = n;
  }
  return n;
}

function llInsertAfter(cell, item, key) {
  var n = null;
  if (this.keyed) {
    n = this.getCellByKey(key);
    if (null != n) {
      n.item = item;
      return n;
    }
  }
  if (null == cell) cell = this.list; // insert at head of list when cell is null
  n = new LinkedListCell();
  n.item = item;
  cell.next.prev = n;
  n.prev = cell;
  n.next = cell.next;
  cell.next = n;
  if (this.keyed) {
    n.key = key;
    this.itemMap[key] = n;
  }
  return n;
}

function llIsEmpty() {
  return (this.list == this.list.next);
}

// ********** END of LinkedList class ***********