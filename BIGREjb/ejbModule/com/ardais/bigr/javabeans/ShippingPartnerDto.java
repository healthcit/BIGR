package com.ardais.bigr.javabeans;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;


/**
 * A Data Transfer Object that represents a shipping partner.
 */
public class ShippingPartnerDto implements Serializable {
  static final long serialVersionUID = -2758638053807044849L;

  public static final String SHIPPING_PARTNER_ID = "shipping_partner_id";
  public static final String SHIPPING_PARTNER_NAME = "shipping_partner_name";

  private String _shippingPartnerId;
  private String _shippingPartnerName;

  /**
   * Creates a new BoxLayoutDto.
   */
  public ShippingPartnerDto() {
    super();
  }

  public void populateFromResultSet(Map columns, ResultSet rs) throws SQLException {

    if (columns.containsKey(SHIPPING_PARTNER_ID)) {
      setShippingPartnerId(rs.getString(SHIPPING_PARTNER_ID));
    }
    if (columns.containsKey(SHIPPING_PARTNER_NAME)) {
      setShippingPartnerName(rs.getString(SHIPPING_PARTNER_NAME));
    }
  }

  /**
   * @return
   */
  public String getShippingPartnerId() {
    return _shippingPartnerId;
  }

  /**
   * @return
   */
  public String getShippingPartnerName() {
    return _shippingPartnerName;
  }

  /**
   * @param string
   */
  public void setShippingPartnerId(String string) {
    _shippingPartnerId = string;
  }

  /**
   * @param string
   */
  public void setShippingPartnerName(String string) {
    _shippingPartnerName = string;
  }
}
