package com.ardais.bigr.userprofile;

import java.io.Serializable;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.ardais.bigr.query.filters.Filter;
import com.ardais.bigr.query.filters.FilterSet;
import com.ardais.bigr.query.filters.ProductFilters;
import com.ardais.bigr.query.filters.SampleFilters;
import com.ardais.bigr.query.generator.FilterCaseDiagnosisLikeBest;
import com.ardais.bigr.query.generator.FilterDonorId;
import com.ardais.bigr.query.generator.FilterSampleId;

/**
 * @author dfeldman
 *
 * The columns to show for a view of data.  This is a back-end object and should be used
 * for communicating among back-end compoents.  To expose this sort of information to the 
 * front end, use ColumnList.
 */
public class QueryProfile extends FilterSet implements UserProfileTopic, Serializable {

  /*pkg private*/
  static final String XML_TOPIC_NAME = "queryProfile";

  /**
   * Constructor for ProfileTopic.
   */
  public QueryProfile() {

  }
  
  public QueryProfile(Map m) {
    Iterator it = m.values().iterator();
    while (it.hasNext()) {
      Filter f = (Filter) it.next();
      this.addFilter(f);
    }
  }

  /**
   * @see com.ardais.bigr.query.filters.FilterSet#getNotApplicableFilterKeys()
   */
  protected List getNotApplicableFilterKeys() {
    return Collections.EMPTY_LIST;
  }

  public void addToUserProfileTopics(String name, UserProfileTopics topics) {
    topics.addQueryProfile(name, this);
  }

  // -------- parsing support ------------------

  public String toXml(String name) {
    StringBuffer buf = new StringBuffer();
    buf.append("<");
    buf.append(XML_TOPIC_NAME);
    buf.append(" name=\"" + name + "\">");
    addXmlToBuffer(getFilters(), buf);
    buf.append("</"+XML_TOPIC_NAME+">");
    return buf.toString();
  }

  /* non-javadoc
   * write the filters to an XML buffer.  For each entry in the maps, write the filter XML,
   * (by delegating to the filter) or write the or-group XML, by wrapping the filters in
   * the or-group with OR tags.
   */
  private void addXmlToBuffer(Map filts, StringBuffer buf) {
    String[] keys = (String[]) filts.keySet().toArray(new String[filts.size()]);
    for (int i = 0; i < keys.length; i++) {
      String key = keys[i];
      if (isInOrGrouop(key)) {
//        buf.append("<OR>");  REMOVED OR's.  OR groups static in FilterSet class
        Map m = (Map) filts.get(key); // or groups' values in the map are other maps
        addXmlToBuffer(m, buf); // recursively put these filters inside the OR tags
//        buf.append("</OR>");
      }
      else {
        Filter f = (Filter) filts.get(key); // other than or groups, Map has Filter objs.
        f.toXml(buf);
      }
    }
  }

  
  //============================  TEST  ============================

  public static void main(String[] args) {

    ProductFilters pf = new SampleFilters();
    pf.addFilter(new FilterSampleId(new String[] { "FR00000001", "PA00000002" }));
    pf.addFilter(new FilterDonorId(new String[] { "AU0000000001" }));
    pf.addFilter(new FilterCaseDiagnosisLikeBest("*breast*"));

    QueryProfile qProf = new QueryProfile();
    pf.copyInto(qProf);
    
    String xml = qProf.toXml("id, donor and breast diagnosis");
    System.out.println("XML: \n" + xml);
  }


}
