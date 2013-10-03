package com.ardais.bigr.generator.btx;

import com.ardais.bigr.btx.framework.BtxTransactionMetaData;

/**
 * Utility methods for use by the <code>BtxDocGenerator</code> and its Velocity template.
 */
public class BtxDocGeneratorHelper {

  public BtxDocGeneratorHelper() {
    super();
  }

  public String getPerformerTypeOnly(BtxTransactionMetaData metadata) {
    String fullType = metadata.getPerformerType();
    return fullType.substring(fullType.lastIndexOf('.') + 1, fullType.length());
  }
  
  public String getPerformerJavadocLink(BtxTransactionMetaData metadata) {
    StringBuffer buf = new StringBuffer(128);
    buf.append("https://integration.ardais.com/javadoc/BIGR/");
    String fullType = metadata.getPerformerType();
    buf.append(fullType.replace('.', '/'));
    buf.append(".html");
    return buf.toString();
  }

}
