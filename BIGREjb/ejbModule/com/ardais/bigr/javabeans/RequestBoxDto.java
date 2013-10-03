package com.ardais.bigr.javabeans;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.util.gen.DbAliases;

/**
 * This represents a box that was assembled as part of fulfilling a request.
 * This isn't a first-class object, request boxes only exist as part of a {@link RequestDto}.
 * Request boxes are stored in the database in ILTDS_REQUEST_BOX.
 * 
 * <p>It is important to note that ILTDS_REQUEST_BOX only stores the ids of boxes that
 * the request samples were placed into at the time of fulfilling a request.  These records
 * remain in the database after the request has been fulfilled, but after a certain point
 * in a request's lifecycle (which may depend on the request types), these boxes are no
 * longer needed for the request and may be reused for other purposes in a repository.
 * So, after a certain point, the box contents of these boxes will no longer accurately
 * reflect what was stored in them at the time of fulfilling a request.  Keep this in mind
 * when using the contents represented inside the {@link BoxDto} object that the
 * {@link #getBox()} method returns.
 */
public class RequestBoxDto implements Serializable {
  static final long serialVersionUID = -204129624912576660L;

  // We allow the box id to be stored independently from from the
  // Box object that represents the box because it is sometimes useful to be able
  // to just specify the id without creating a fullblown Box object.
  // If _box is set to a non-null value, we ignore any value that may have been
  // explicitly set into _boxId and return the actual box id from _box instead.
  // 
  private String _boxId;
  private BoxDto _boxDto;
  private boolean _shipped;

  /**
   * Populates this <code>RequestBoxDto</code> from the data in the current row
   * of the result set.
   * 
   * @param  columns  a <code>Map</code> containing a key for each column in
   *                   the <code>ResultSet</code>.  Each key must be one of the
   *                   column alias constants in {@link com.ardais.bigr.util.DbAliases}
   *                   and the corresponding value is ignored.
   * @param  rs  the <code>ResultSet</code>
   */
  public void populate(Map columns, ResultSet rs) {
    try {
      if (columns.containsKey(DbAliases.REQUEST_BOX_BOX_BARCODE_ID)) {
        setBoxId(rs.getString(DbAliases.REQUEST_BOX_BOX_BARCODE_ID));
      }
      if (columns.containsKey(DbAliases.REQUEST_BOX_SHIPPED_YN)) {
        setShipped("Y".equals(rs.getString(DbAliases.REQUEST_BOX_SHIPPED_YN).toUpperCase()));
      }
    } catch (SQLException e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
  }

  /**
   * @return The box.
   */
  public BoxDto getBoxDto() {
    return _boxDto;
  }

  /**
   * @return The box id.  If {@link #getBox()} is non-null, this returns that BoxDto's
   * id and ignores any value that may have been supplied by calling the
   * {@link #setBoxId(String)} method.
   */
  public String getBoxId() {
    BoxDto boxDto = getBoxDto();
    return ((boxDto == null) ? _boxId : boxDto.getBoxId());
  }

  /**
   * @return True if the box has been shipped.  This is only defined and meaningful for
   *   request types that involve shipping, such as Transfer requests.
   */
  public boolean isShipped() {
    return _shipped;
  }

  /**
   * @param data The box.
   */
  public void setBoxDto(BoxDto data) {
    _boxDto = data;
  }

  /**
   * @param string The box id.  If {@link #getBox()} is non-null, this returns that BoxDto's
   * id and ignores any value that may have been supplied by calling the
   * {@link #setBoxId(String)} method.
   * @see #getBoxId()
   */
  public void setBoxId(String string) {
    _boxId = string;
  }

  /**
   * @param b True if the box has been shipped.  This is only defined and meaningful for
   *   request types that involve shipping, such as Transfer requests.
   */
  public void setShipped(boolean b) {
    _shipped = b;
  }
}
