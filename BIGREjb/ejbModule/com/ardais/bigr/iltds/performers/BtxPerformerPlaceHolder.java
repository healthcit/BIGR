package com.ardais.bigr.iltds.performers;

import java.lang.reflect.Method;

import com.ardais.bigr.btx.framework.BtxTransactionPerformerBase;
import com.ardais.bigr.iltds.btx.BTXDetails;

public class BtxPerformerPlaceHolder extends BtxTransactionPerformerBase {

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

  /**
   * This is the main BTX entry point for performing a Placeholder
   * business transaction.  A placeholder transaction just returns its
   * btxDetails argument unchanged.  It is useful for transactions that
   * need to be logged in the BTX transaction log but that haven't been
   * fully migrated to the BTX session bean framework yet (for example,
   * they are still implemented in the op or Action class).
   */
  protected BTXDetails performPlaceholder(BTXDetails btxDetails) throws Exception {
    return btxDetails;
  }

}
