package com.ardais.bigr.iltds.btx;

import java.util.HashSet;
import java.util.Set;
import java.util.Vector;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.iltds.assistants.LegalValueSet;
import com.ardais.bigr.javabeans.BoxDto;
import com.ardais.bigr.security.SecurityInfo;
import com.ardais.bigr.util.IcpUtils;

/**
 * Represent the details of an update box location business transaction.
 * <p>
 * The transaction-specific fields are described below.  See also
 * {@link BTXDetails BTXDetails} for fields that are shared by all
 * business transactions.
 *
 * <h4>Required input fields</h4>
 * <ul>
 * <li>{@link #setBoxId(String) BoxDto id}: The id of the box whose location is to be updated.</li>
 * <li>{@link #setUpdatePart(String) Update part}: The indicator of what phase of the
 *     transaction is to be performed.</li>
 * <li>{@link #setNewBoxLocation(BTXBoxLocation) New box location}: The new storage location
 *     to be assigned to the box.</li>
 * </ul>
 *
 * <h4>Optional input fields</h4>
 * <ul>
 * <li>None.</li>
 * </ul>
 *
 * <h4>Output fields</h4>
 * <ul>
 * <li>{@link #setOldBoxLocation(BTXBoxLocation) Old box location}: The storage location that was
 *     previously assigned to the box, or null if none.</li>
 * <li>{@link #setRoomList(Vector) Room list}: The list of possible storage rooms for the new location.</li>
 * <li>{@link #setUnitList(Vector) Unit list}: The list of possible storage units for the new location.</li>
 * <li>{@link #setDrawerList(Vector) Drawer list}: The list of possible storage drawers for the new location.</li>
 * <li>{@link #setSlotList(Vector) Slot list}: The list of possible storage slots for the new location.</li>
 * </ul>
 */
public class BTXDetailsUpdateBoxLocation extends BTXDetails implements java.io.Serializable {
	private static final long serialVersionUID = -2623913577636355346L;

	private String _boxId = null;
	private BTXBoxLocation _newBoxLocation = null;
	private BTXBoxLocation _oldBoxLocation = null;
	private String _updatePart = null;
  private LegalValueSet _storageTypes = null;
	private Vector _roomList = null;
	private Vector _unitList = null;
	private Vector _drawerList = null;
	private Vector _slotList = null;
	public BTXDetailsUpdateBoxLocation() {
		super();
	}
	/**
	 * Fill a business transaction history record object with information
	 * from this transaction details object.  This method will set <b>all</b>
	 * fields on the history record, even ones not used by the this type of
	 * transaction.  Fields that aren't used by this transaction type will be
	 * set to their initial default values.
	 * <p>
	 * This method is only meant to be used internally by the business
	 * transaction framework implementation.  Please don't use it anywhere else.
	 *
	 * @param history the history record object that will have its fields set to
	 *    the transaction information.
	 */
	public void describeIntoHistoryRecord(BTXHistoryRecord history) {
		super.describeIntoHistoryRecord(history);

		history.setBox(new BoxDto(getBoxId()));
		history.setBoxLocation1(getNewBoxLocation());
		history.setBoxLocation2(getOldBoxLocation());
	}
  protected String doGetDetailsAsHTML() {
		// NOTE: This method must not make use of any fields that aren't
		// set by the populateFromHistoryRecord method.
		//
		// For this object type, the fields we can use here are:
		//   getBoxId
		//   getNewBoxLocation
		//   getOldBoxLocation
        
        SecurityInfo securityInfo = getLoggedInUserSecurityInfo();

		StringBuffer sb = new StringBuffer(512);

		// The result has this form:
		//    Moved box <box id> to <new location> from <old location>

		sb.append("Moved box ");
		sb.append(IcpUtils.prepareLink(getBoxId(), securityInfo));
		sb.append(" to ");
		getNewBoxLocation().appendICPHTML(sb, securityInfo);
		sb.append(" from ");

		BTXBoxLocation oldLoc = getOldBoxLocation();
		if (oldLoc == null) {
			sb.append("an unknown location");
		} else {
			oldLoc.appendICPHTML(sb, securityInfo);
		}

		sb.append('.');

		return sb.toString();
	}
	/**
	 * Return the id of the box involved in the transaction.
	 *
	 * @return the box id.
	 */
	public String getBoxId() {
		return _boxId;
	}
	public String getBTXType() {
		return BTXDetails.BTX_TYPE_UPDATE_BOX_LOCATION;
	}
	public java.util.Set getDirectlyInvolvedObjects() {
		Set set = null;

		set = new HashSet();

		if (ApiFunctions.safeStringLength(_boxId) > 0) {
			set.add(_boxId);
		}

		return set;
	}
	/**
	 * Return a list of the possible storage drawers for the new location.
	 *
	 * @return the storage drawer list.
	 */
	public Vector getDrawerList() {
		return _drawerList;
	}
	/**
	 * Return the new location assigned to the box involved in the transaction.
	 *
	 * @return the new box location.
	 */
	public BTXBoxLocation getNewBoxLocation() {
		return _newBoxLocation;
	}
	/**
	 * Return the old location that was previously assigned to the box involved in the transaction.
	 *
	 * @return the old box location.
	 */
	public BTXBoxLocation getOldBoxLocation() {
		return _oldBoxLocation;
	}
	/**
	 * Return a list of the possible rooms for the new location.
	 *
	 * @return the room list.
	 */
	public Vector getRoomList() {
		return _roomList;
	}
	/**
	 * Return a list of the possible storage slots for the new location.
	 *
	 * @return the storage slot list.
	 */
	public Vector getSlotList() {
		return _slotList;
	}
	/**
	 * Return a list of the possible storage units for the new location.
	 *
	 * @return the storage unit list.
	 */
	public Vector getUnitList() {
		return _unitList;
	}
	/**
	 * Return the indicator of what phase of the update we're in.  There are several phases,
	 * all but the last gather progressively details storage location information from
	 * the user ragarding the new location (room, unit, drawer, slot).  The value will be
	 * one of 'room', 'freezer', 'drawer', or 'finish'.
	 *
	 * @return the update phase indicator.
	 */
	public String getUpdatePart() {
		return _updatePart;
	}
	/**
	 * Populate the fields of this object with information contained in a
	 * business transaction history record object.  This method must set <b>all</b>
	 * fields on this object, as if it had been newly created immediately before
	 * this method was called.  A runtime exception is thrown if the transaction type
	 * represented by the history record doesn't match the transaction type represented
	 * by this object.
	 * <p>
	 * This method is only meant to be used internally by the business
	 * transaction framework implementation.  Please don't use it anywhere else.
	 *
	 * @param history the history record object that will be used as the
	 *    information source.  A runtime exception is thrown if this is null.
	 */
	public void populateFromHistoryRecord(BTXHistoryRecord history) {
		super.populateFromHistoryRecord(history);

		setBoxId(history.getBoxId());
		setNewBoxLocation(history.getBoxLocation1());
		setOldBoxLocation(history.getBoxLocation2());

		// These fields don't correspond to anything in the history record
		// but we must set them anyways.
		//
		setUpdatePart(null);
    setStorageTypes(null);
		setRoomList(null);
		setUnitList(null);
		setDrawerList(null);
		setSlotList(null);
	}
	/**
	 * Set the id of the box involved in the transaction.
	 *
	 * @param newBoxId the box id.
	 */
	public void setBoxId(String newBoxId) {
		_boxId = newBoxId;
	}
	/**
	 * Set the list of the possible storage drawers for the new location.
	 *
	 * @param newDrawerList the storage drawer list.
	 */
	public void setDrawerList(Vector newDrawerList) {
		_drawerList = newDrawerList;
	}
	/**
	 * Set the new location assigned to the box involved in the transaction.
	 *
	 * @param newNewBoxLocation the new box location.
	 */
	public void setNewBoxLocation(BTXBoxLocation newNewBoxLocation) {
		_newBoxLocation = newNewBoxLocation;
	}
	/**
	 * Set the old location that was previously assigned to the box involved in the transaction.
	 *
	 * @param newOldBoxLocation the old box location.
	 */
	public void setOldBoxLocation(BTXBoxLocation newOldBoxLocation) {
		_oldBoxLocation = newOldBoxLocation;
	}
	/**
	 * Set the list of the possible rooms for the new location.
	 *
	 * @param newRoomList the room list.
	 */
	public void setRoomList(Vector newRoomList) {
		_roomList = newRoomList;
	}
	/**
	 * Set the list of the possible storage slots for the new location.
	 *
	 * @param newSlotList the storage slot list.
	 */
	public void setSlotList(Vector newSlotList) {
		_slotList = newSlotList;
	}
	/**
	 * Set the list of the possible storage units for the new location.
	 *
	 * @param newUnitList the storage unit list.
	 */
	public void setUnitList(Vector newUnitList) {
		_unitList = newUnitList;
	}
	/**
	 * Set the indicator of what phase of the update we're in.  There are several phases,
	 * all but the last gather progressively details storage location information from
	 * the user ragarding the new location (room, unit, drawer, slot).  The value will be
	 * one of 'room', 'freezer', 'drawer', or 'finish'.
	 *
	 * @param newUpdatePart the update phase indicator.
	 */
	public void setUpdatePart(String newUpdatePart) {
		_updatePart = newUpdatePart;
	}

  /**
   * @return
   */
  public LegalValueSet getStorageTypes() {
    return _storageTypes;
  }

  /**
   * @param set
   */
  public void setStorageTypes(LegalValueSet set) {
    _storageTypes = set;
  }
}