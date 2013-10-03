package com.ardais.bigr.library.performers;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.btx.framework.Btx;
import com.ardais.bigr.btx.framework.BtxTransactionPerformerBase;
import com.ardais.bigr.es.beans.ArdaisorderAccessBean;
import com.ardais.bigr.es.beans.ArdaisorderKey;
import com.ardais.bigr.es.beans.ArdaisuserKey;
import com.ardais.bigr.es.beans.OrderlineAccessBean;
import com.ardais.bigr.iltds.btx.BTXDetails;
import com.ardais.bigr.javabeans.OrderData;
import com.ardais.bigr.library.btx.BTXDetailsGetSamples;
import com.ardais.bigr.security.SecurityInfo;
import com.ibm.ivj.ejb.runtime.AccessBeanEnumeration;

public class BtxPerformerOrderLineManager extends BtxTransactionPerformerBase {

  /**
   * Invoke the specified method on this class.  This is only meant to be
   * called from BtxTransactionPerformerBase, please don't call it from anywhere
   * else as a mechanism to gain access to private methods in this class.  Every
   * object that the BTX framework dispatches to must contain this
   * method definition, and its implementation should be exactly the same
   * in each class.  Please don't alter this method or its implementation
   * in any way.
   */
  protected BTXDetails invokeBtxEntryPoint(Method method, BTXDetails btxDetails) throws Exception {

    // **** DO NOT EDIT THIS METHOD, see the Javadoc comment above.
    return (BTXDetails) method.invoke(this, new Object[] { btxDetails });
  }

  public String[] getOrderDetailIds(SecurityInfo securityInfo, String orderId) {
    try {
      OrderlineAccessBean orderLine = new OrderlineAccessBean();
      AccessBeanEnumeration detailEnum =
        (AccessBeanEnumeration) orderLine.findOrderlineByArdaisorder(
          new ArdaisorderKey(new BigDecimal(orderId)));

      ArrayList sampleIds = new ArrayList();
      while (detailEnum.hasMoreElements()) {
        OrderlineAccessBean line = (OrderlineAccessBean) detailEnum.nextElement();
        sampleIds.add(line.getBarcode_id());
      }

      return (String[]) sampleIds.toArray(new String[0]);
    }
    catch (Exception e) {
      throw new ApiException(e);
    }
  }

  public OrderData[] getOrderIds(SecurityInfo securityInfo) {
    try {
      ArdaisorderAccessBean order = new ArdaisorderAccessBean();
      AccessBeanEnumeration enum1 =
        (AccessBeanEnumeration) order.findUserOpenOrders(
          securityInfo.getUsername(),
          securityInfo.getAccount());

      ArrayList results = new ArrayList();
      while (enum1.hasMoreElements()) {
        ArdaisorderAccessBean tmpOrder = (ArdaisorderAccessBean) enum1.nextElement();
        OrderData data = new OrderData();
        data.setOrderNumber(((ArdaisorderKey) tmpOrder.__getKey()).order_number.toString());
        data.setStatus(tmpOrder.getOrder_status());
        data.setCreationDate(tmpOrder.getOrder_date());
        results.add(data);
      }
      return (OrderData[]) results.toArray(new OrderData[results.size()]);
    }
    catch (Exception e) {
      throw new ApiException(e);
    }
  }
  
    public OrderData getOrderData(SecurityInfo securityInfo, String orderNumber) {
      try {
        ArdaisorderAccessBean order =
          new ArdaisorderAccessBean(new ArdaisorderKey(new BigDecimal(orderNumber)));
        OrderData data = new OrderData();
        data.setOrderNumber(((ArdaisorderKey) order.__getKey()).order_number.toString());
        data.setStatus(order.getOrder_status());
        data.setCreationDate(order.getOrder_date());
        data.setUserId(((ArdaisuserKey) order.getArdaisuser().__getKey()).ardais_user_id);
        data.setAccountId(
          ((ArdaisuserKey) order.getArdaisuser().__getKey()).ardaisaccount_ardais_acct_key);
        data.setUserFullName(
          order.getArdaisuser().getUser_firstname() + " " + order.getArdaisuser().getUser_lastname());
        return data;
      }
      catch (Exception e) {
        throw new ApiException(e);
      }
    }

    /**
     * Retreive the list of sampleData's that are on a persons hold list.
     * @param securityInfo
     * @return A list of <code>SampleData</code> objects.
     */
    private BTXDetailsGetSamples performGetOrderLineData(BTXDetailsGetSamples btx) {
      SecurityInfo securityInfo = btx.getLoggedInUserSecurityInfo();
      String orderNumber = btx.getOrderNumber();
      try {
        String[] sampleIds = getOrderDetailIds(securityInfo, orderNumber);
        btx.setSampleIds(sampleIds); // set sample IDs to the Order contents
        btx.setTransactionType("library_get_details");
        btx = (BTXDetailsGetSamples) Btx.perform(btx);
        return btx; // sample data, ids and ColumnList
      }
      catch (Exception e) {
        throw new ApiException(e);
      }
    }

}
