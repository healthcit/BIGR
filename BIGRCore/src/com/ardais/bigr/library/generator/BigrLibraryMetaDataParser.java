package com.ardais.bigr.library.generator;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.apache.commons.digester.Digester;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.api.ApiLogger;
import com.ardais.bigr.util.BigrXMLParserBase;

/**
 * This class parses an XML file containing BigrLibraryMetaData
 * into a <code>com.ardais.bigr.library.generator.BigrLibraryMetaData</code> 
 * object.
 */
public class BigrLibraryMetaDataParser extends BigrXMLParserBase {
  private BigrLibraryMetaData _metaData = null;

  /**
   * Constructor for BigrLibraryMetaDataParser.
   */
  public BigrLibraryMetaDataParser() {
    super();
  }

  /**
   * Parse the XML file representing the data element taxonomy into a DataElementTaxonomy
   * object.
   * 
   * @return the data element taxonomy
   */
  public BigrLibraryMetaData performParse() {
    System.out.println("\nParsing BigrLibraryMetaData...");

    //create a Digester
    // Create a default digester.
    Digester digester = makeDigester();

    // Register the location of the category configuration DTD.
    URL dtdURL =
      getClass().getResource(ApiFunctions.CLASSPATH_RESOURCES_PREFIX + "BigrLibraryMetaData.dtd");
    digester.register(
      "-//Ardais Corporation//DTD Bigr Library Meta Data 1.0//EN",
      dtdURL.toString());

    digester.push(this);

    //define the patterns we want to look for
    //any time we see a sampleSelectionMetaData element, create a BigrLibraryMetaData object
    digester.addObjectCreate("bigrLibraryMetaData", BigrLibraryMetaData.class);
    //set the properties on the BigrLibraryMetaData object.  XML attribute names matching
    //BigrLibraryMetaData property names are automatically set.  Although it is possible to
    //set properties that don't match attribute names, this is the preferred mechanism
    //so that this code doesn't need to be modified every time we add an attribute/property.
    digester.addSetProperties("bigrLibraryMetaData");
    //add the BigrLibraryMetaData to this digester, so we can use it to update
    //the child java objects with version information and any other
    //information from the BigrLibraryMetaData object deemed necessary.
    digester.addSetRoot(
      "bigrLibraryMetaData",
      "setBigrLibraryMetaData",
      "com.ardais.bigr.library.generator.BigrLibraryMetaData");
    //any time we see a dbTable element, create a DbTable object
    digester.addObjectCreate("*/dbTable", DbTable.class);
    //set the properties on the DbTable object.  XML attribute names matching
    //DbTable property names are automatically set.  Although it is possible to
    //set properties that don't match attribute names, this is the preferred mechanism
    //so that this code doesn't need to be modified every time we add an attribute/property.
    digester.addSetProperties("*/dbTable");
    //any time we see a dbColumn element, create a DbColumn object
    digester.addObjectCreate("*/dbTable/dbColumn", DbColumn.class);
    //set the properties on the DbColumn object.  XML attribute names matching
    //DataElement property names are automatically set.  Although it is possible to
    //set properties that don't match attribute names, this is the preferred mechanism
    //so that this code doesn't need to be modified every time we add an attribute/property.
    digester.addSetProperties("*/dbTable/dbColumn");
    //any time we see a dbAlias element, create a DbAlias object
    digester.addObjectCreate("*/dbTable/dbColumn/dbAlias", DbAlias.class);
    //set the properties on the DbAlias object.  XML attribute names matching
    //DataElement property names are automatically set.  Although it is possible to
    //set properties that don't match attribute names, this is the preferred mechanism
    //so that this code doesn't need to be modified every time we add an attribute/property.
    digester.addSetProperties("*/dbTable/dbColumn/dbAlias");
    //add the completed DbAlias object to its parent DbColumn object
    digester.addSetNext(
      "*/dbTable/dbColumn/dbAlias",
      "addDbAlias",
      "com.ardais.bigr.library.generator.DbAlias");

    //add the completed DbColumn object to its parent DbTable object
    digester.addSetNext(
      "*/dbTable/dbColumn",
      "addDbColumn",
      "com.ardais.bigr.library.generator.DbColumn");
    //any time we see a dbAlias element, create a DbAlias object
    digester.addObjectCreate("*/dbTable/dbAlias", DbAlias.class);
    //set the properties on the DbAlias object.  XML attribute names matching
    //DataElement property names are automatically set.  Although it is possible to
    //set properties that don't match attribute names, this is the preferred mechanism
    //so that this code doesn't need to be modified every time we add an attribute/property.
    digester.addSetProperties("*/dbTable/dbAlias");
    //add the completed DbAlias object to its parent DbTable object
    digester.addSetNext(
      "*/dbTable/dbAlias",
      "addDbAlias",
      "com.ardais.bigr.library.generator.DbAlias");
    //add the completed DbTable object to its parent object (BigrLibraryMetaData).
    digester.addSetNext("*/dbTable", "addDbTable", "com.ardais.bigr.library.generator.DbTable");
    //any time we see a table element, create a Table object
    digester.addObjectCreate("*/table", Table.class);
    //set the properties on the Table object.  XML attribute names matching
    //Table property names are automatically set.  Although it is possible to
    //set properties that don't match attribute names, this is the preferred mechanism
    //so that this code doesn't need to be modified every time we add an attribute/property.
    digester.addSetProperties("*/table");
    //add the completed Table object to its parent object (BigrLibraryMetaData).
    digester.addSetNext("*/table", "addTable", "com.ardais.bigr.library.generator.Table");
    //any time we see a column element, create a Column object
    digester.addObjectCreate("*/column", Column.class);
    //set the properties on the Column object.  XML attribute names matching
    //Table property names are automatically set.  Although it is possible to
    //set properties that don't match attribute names, this is the preferred mechanism
    //so that this code doesn't need to be modified every time we add an attribute/property.
    digester.addSetProperties("*/column");
    //add the completed Column object to its parent object (BigrLibraryMetaData).
    digester.addSetNext("*/column", "addColumn", "com.ardais.bigr.library.generator.Column");
    //any time we see a columnCount element, create a ColumnCount object
    digester.addObjectCreate("*/columnCount", ColumnCount.class);
    //set the properties on the ColumnCount object.  XML attribute names matching
    //Table property names are automatically set.  Although it is possible to
    //set properties that don't match attribute names, this is the preferred mechanism
    //so that this code doesn't need to be modified every time we add an attribute/property.
    digester.addSetProperties("*/columnCount");
    //add the completed ColumnCount object to its parent object (BigrLibraryMetaData).
    digester.addSetNext(
      "*/columnCount",
      "addColumnCount",
      "com.ardais.bigr.library.generator.ColumnCount");

    parseFilter(digester);

    //now that everything is set up, parse the file
    try {
      URL xmlURL =
        getClass().getResource(ApiFunctions.CLASSPATH_RESOURCES_PREFIX + "BigrLibraryMetaData.xml");
      digester.parse(xmlURL.toString());
    }
    catch (SAXException se) {
      throw new ApiException(se);
    }
    catch (IOException ie) {
      throw new ApiException(ie);
    }
    System.out.println("Completed parsing BigrLibraryMetaData.");

    //before generating any files, make sure all data is correct
    System.out.println("\nValidating BigrLibraryMetaData...");
    if (!_metaData.checkValidity()) {
      throw new ApiException("Please check the warnings before proceeding.");
    }
    System.out.println("Finished validating BigrLibraryMetaData.");

    //return the resulting BigrLibraryMetaData
    return _metaData;
  }

  private void parseGeneralFilter(Digester digester, String pattern, String className, Class clazz) {
    digester.addObjectCreate(pattern, clazz);
    digester.addSetProperties(pattern);
    digester.addSetProperties(pattern + "/hint");
    digester.addSetNext(
    pattern,
      "addFilter",
      "com.ardais.bigr.library.generator." + className);
  }

  private void parseFilterMethod(Digester digester, String method, String createPattern, String propertiesPattern, String className, Class clazz) {
    digester.addObjectCreate(createPattern, clazz);
    digester.addSetProperties(propertiesPattern);
    digester.addSetProperties(createPattern + "/hint");
    digester.addSetNext(
    createPattern,
      method,
      "com.ardais.bigr.library.generator." + className);
  }

  private void parseFilter(Digester digester) {
    parseGeneralFilter(digester, "*/filterEqual", "FilterEqual", FilterEqual.class);
    parseGeneralFilter(digester, "*/filterNotEqual", "FilterNotEqual", FilterNotEqual.class);
    parseGeneralFilter(digester, "*/filterGreaterThan", "FilterGreaterThan", FilterGreaterThan.class);
    parseGeneralFilter(digester, "*/filterLike", "FilterLike", FilterLike.class);
    parseGeneralFilter(digester, "*/filterNotLike", "FilterNotLike", FilterNotLike.class);
    parseGeneralFilter(digester, "*/filterIsNull", "FilterIsNull", FilterIsNull.class);
    parseGeneralFilter(digester, "*/filterNotIsNull", "FilterNotIsNull", FilterNotIsNull.class);
    parseGeneralFilter(digester, "*/filterContains", "FilterContains", FilterContains.class);
    parseGeneralFilter(digester, "*/filterNumericRange", "FilterNumericRange", FilterNumericRange.class);
    parseGeneralFilter(digester, "*/filterDateRange", "FilterDateRange", FilterDateRange.class);

    parseGeneralFilter(digester, "*/filterNvlEqual", "FilterNvlEqual", FilterNvlEqual.class);
    parseFilterMethod(digester, "setLeftNvlValue", "*/filterNvlEqual/leftNvlValue", "*/filterNvlEqual/leftNvlValue/tableColumn", "ColumnOrValue", ColumnOrValue.class);
    parseFilterMethod(digester, "setRightNvlValue", "*/filterNvlEqual/rightNvlValue", "*/filterNvlEqual/rightNvlValue/tableColumn", "ColumnOrValue", ColumnOrValue.class);

    parseGeneralFilter(digester, "*/filterNvlNotEqual", "FilterNvlNotEqual", FilterNvlNotEqual.class);
    parseFilterMethod(digester, "setLeftNvlValue", "*/filterNvlNotEqual/leftNvlValue", "*/filterNvlNotEqual/leftNvlValue/tableColumn", "ColumnOrValue", ColumnOrValue.class);
    parseFilterMethod(digester, "setRightNvlValue", "*/filterNvlNotEqual/rightNvlValue", "*/filterNvlNotEqual/rightNvlValue/tableColumn", "ColumnOrValue", ColumnOrValue.class);
    
    parseGeneralFilter(digester, "*/filterJoin", "FilterJoin", FilterJoin.class);
    parseFilterMethod(digester, "setLeftColumn", "*/filterJoin/leftColumn", "*/filterJoin/leftColumn/tableColumn", "TableColumn", TableColumn.class);
    parseFilterMethod(digester, "setRightColumn", "*/filterJoin/rightColumn", "*/filterJoin/rightColumn/tableColumn", "TableColumn", TableColumn.class);

    parseGeneralFilter(digester, "*/filterExists", "FilterExists", FilterExists.class);
    parseSelectClause(digester, "*/filterExists/selectClause");

    parseGeneralFilter(digester, "*/filterNotExists", "FilterNotExists", FilterNotExists.class);
    parseSelectClause(digester, "*/filterNotExists/selectClause");

    parseGeneralFilter(digester, "*/filterNotIn", "FilterNotIn", FilterNotIn.class);
    parseFilterMethod(digester, "setColumn", "*/filterNotIn/tableColumn", 
    "*/filterNotIn/tableColumn", "TableColumn", TableColumn.class);
    parseSelectClause(digester, "*/filterNotIn/selectClause");

    parseGeneralFilter(digester, "*/filterOther", "FilterOther", FilterOther.class);

    parseGeneralFilter(digester, "*/compoundFilter", "CompoundFilter", CompoundFilter.class);
    parseFilterMethod(digester, "addFilterKey", "*/compoundFilter/filterKey", "*/compoundFilter/filterKey", "FilterKey", FilterKey.class);
  }

  private void parseSelectClause(Digester digester, String pattern) {

    parseFilterMethod(digester, "setSelectClause", pattern, 
      pattern, "SelectClause", SelectClause.class);

    parseFilterMethod(digester, "addColumn", pattern + "/columnOrValueList/tableColumn", 
      pattern + "/columnOrValueList/tableColumn", "ColumnOrValue", ColumnOrValue.class);
    parseFilterMethod(digester, "addColumn", pattern + "/columnOrValueList/fixedValue", 
      pattern + "/columnOrValueList/fixedValue", "ColumnOrValue", ColumnOrValue.class);
    parseFilterMethod(digester, "addTable", pattern + "/fromTable/dbAlias", 
      pattern + "/fromTable/dbAlias", "DbAlias", DbAlias.class);

    parseFilterMethod(digester, "setWhereClause", pattern + "/whereClause", 
      pattern + "/whereClause", "WhereClause", WhereClause.class);
    parseFilterMethod(digester, "addFilterKey", pattern + "/whereClause/filterKey", 
      pattern + "/whereClause/filterKey", "FilterKey", FilterKey.class);

  }

  /* method to add a BigrLibraryMetaData object to this class
   */
  public void setBigrLibraryMetaData(BigrLibraryMetaData metaData) {
    _metaData = metaData;
  }

  //  /* method to handle parse errors returned from the digester - required because this
  //   * class is the errorHandler for the digester and must implement the ErrorHandler
  //   * interface.
  //   */
  //  public void error(SAXParseException exception) {
  //    ApiFunctions.throwAsRuntimeException(exception);
  //  }
  //
  //  /* method to handle parse fatal errors returned from the digester - required because this
  //   * class is the errorHandler for the digester and must implement the ErrorHandler
  //   * interface.
  //   */
  //  public void fatalError(SAXParseException exception) {
  //    ApiFunctions.throwAsRuntimeException(exception);
  //  }
  //
  //  /* method to handle warnings returned from the digester - required because this
  //   * class is the errorHandler for the digester and must implement the ErrorHandler
  //   * interface.
  //   */
  //  public void warning(SAXParseException exception) {
  //    ApiLogger.warn("BigrLibraryMetaData parser warning", exception);
  //  }

  public static void main(String[] args) {
    BigrLibraryMetaDataParser digester = new BigrLibraryMetaDataParser();
    digester.performParse();
  }

}
