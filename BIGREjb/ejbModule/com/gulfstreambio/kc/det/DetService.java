package com.gulfstreambio.kc.det;

import com.gulfstreambio.gboss.GbossFactory;
 
/**
 * Provides all of the services necessary to retrieve a Data Element Taxonomy. 
 */
public class DetService {
  
  // The singleton instance for this class.
  public static DetService SINGLETON = new DetService();
  
  // static data created from GBOSS
  private static DataElementTaxonomy _det = new DataElementTaxonomy(GbossFactory.getInstance());
  
  public DataElementTaxonomy getDataElementTaxonomy() {
    return _det;
  }

  private DetService() {
    super();
  }

}
