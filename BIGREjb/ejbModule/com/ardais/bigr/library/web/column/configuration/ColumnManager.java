package com.ardais.bigr.library.web.column.configuration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.ardais.bigr.library.web.column.KcDataColumn;
import com.ardais.bigr.library.web.column.SampleColumn;
import com.ardais.bigr.query.ColumnConstants;
import com.ardais.bigr.query.ColumnDescriptor;
import com.ardais.bigr.query.ViewParams;
import com.ardais.bigr.util.ClassLookupManager;

/**
 * @author dfeldman
 *
 * Manages the global list of available columns and associated classes.  Front-end or other
 * external components can update this list, since we don't know here what columns are
 * available.
 * 
 * responsibilities:  Registers instances, builds unique keys, enforces uniqueness, creates 
 * instances via introspection.
 * 
 */
public class ColumnManager extends ClassLookupManager {

  private static ColumnManager instance;

  public static synchronized ColumnManager instance() {
    if (instance == null)
      instance = new ColumnManager();
    return instance;
  }

  /**
   * Override super implementation to return null.  This is possible if, for example, the key
   * stored in a column profile is the key for a column that was removed from the system. 
   * @param key  - the unique identifier to get the class for
   * @return Class corresponding to the key
   */
  protected Class get(String key) throws ClassNotFoundException {
    try {
      return super.get(key);
    }
    catch (ClassNotFoundException e) {
      return null;
    }
  }

  // if a null argument is passed in, the config screens need a dummied-down column for 
  // displaying prompts and tooltips and such.  Override classname so that null still
  // returns the right signature to find the columns' constructor method.
  protected Class[] getTypes(Object[] args) {
    if (args.length == 1 && args[0] == null)
      return new Class[] { ViewParams.class };
    else
      return super.getTypes(args);
  }

  /**
   * @return A new instance of a SampleColumn for the column with the specified key, for the
   *    specified view.  If the column isn't available to be shown in the specified view, return
   *    null.
   */
  public SampleColumn newInstance(ColumnDescriptor columnDescriptor, ViewParams vp) {
    SampleColumn column = null;
    if (columnDescriptor.isBigrDataColumn()) {
      column = (SampleColumn) super.getInstance(columnDescriptor.getBigrColumnConstant(), new Object[] { vp }, (Class[]) null);
    }
    else {
      column = new KcDataColumn(columnDescriptor, vp);
    }

    // Don't return columns that shouldn't be available in the specified view.
    if ((column != null) && (!column.isShown())) {
      return null;
    }

    return column;
  }

  /**
   * @return A list of {@link SampleColumn} instances derived from a list of ColumnDescriptors, in
   *    the same order as the list of ColumnDescriptors, for the specified view.  This always 
   *    returns a non-null list.  Any columns that aren't available to be shown in the specified 
   *    view are omitted from the result list.
   */
  public List newInstances(List columnDescriptors, ViewParams vp) {
    if (columnDescriptors == null) {
      return new ArrayList(0);
    }
    List columns = new ArrayList(columnDescriptors.size());
    Iterator iter = columnDescriptors.iterator();
    while (iter.hasNext()) {
      ColumnDescriptor columnDescriptor = (ColumnDescriptor) iter.next();
      SampleColumn column = newInstance(columnDescriptor, vp);
      if (column != null) {
        columns.add(column);
      } 
    }
    ((ArrayList) columns).trimToSize();

    return columns;
  }

}
