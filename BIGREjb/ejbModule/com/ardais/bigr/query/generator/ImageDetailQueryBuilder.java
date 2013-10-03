package com.ardais.bigr.query.generator;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.api.ApiLogger;
import com.ardais.bigr.api.ApiProperties;
import com.ardais.bigr.api.StringCanonicalizer;
import com.ardais.bigr.javabeans.SampleData;
import com.ardais.bigr.lims.javabeans.ImageData;
import com.ardais.bigr.query.generator.gen.ColumnMetaData;
import com.ardais.bigr.query.generator.gen.FilterMetaData;
import com.ardais.bigr.query.generator.gen.TableMetaData;
import com.ardais.bigr.util.BigrPreparedStatement;
import com.ardais.bigr.util.BigrResultSet;
import com.ardais.bigr.util.DbUtils;
import com.ardais.bigr.util.gen.DbAliases;

/**
 */
public class ImageDetailQueryBuilder {

  /**
   * The query builder used to build this query.
   */
  private ProductQueryBuilder _queryBuilder = new ProductQueryBuilder();

  /**
   * Logger for logging info about database queries (e.g. a query's SQL).
   */
  private static Log _queryLog = LogFactory.getLog(ApiLogger.BIGR_LOGNAME_LIBRARY_QUERY);

  /**
   * Creates a new <code>ImageDetailQueryBuilder</code>.
   */
  public ImageDetailQueryBuilder() {
    super();
  }

  /** 
   * Specifies that the image id for any images associated with
   * the sample be returned in this detail query 
   */
  public void addColumnImageId() {
    addColumnInImages(ColumnMetaData.KEY_IMAGES_IMAGE_ID);
  }

  /** 
   * Specifies that the image filename for any images associated with
   * the sample be returned in this detail query 
   */
  public void addColumnImageFilename() {
    addColumnInImages(ColumnMetaData.KEY_IMAGES_IMAGE_FILE_NAME);
  }

  /** 
   * Specifies that the image name for any images associated with
   * the sample be returned in this detail query 
   */
  public void addColumnImageMagnification() {
    addColumnInImages(ColumnMetaData.KEY_IMAGES_MAGNIFICATION);
  }

  /** 
   * Specifies that the image capture date for any images associated with
   * the sample be returned in this detail query 
   */
  public void addColumnImageCaptureDate() {
    addColumnInImages(ColumnMetaData.KEY_IMAGES_CAPTURE_DATE);
  }

  /** 
   * Specifies that the image slide id for any images associated with
   * the sample be returned in this detail query 
   */
  public void addColumnSlideId() {
    addColumnInSlides(ColumnMetaData.KEY_IMAGES_SLIDE_ID);
  }

  /** 
   * Specifies that the image sample id for any images associated with
   * the sample be returned in this detail query 
   */
  public void addColumnSampleId() {
    addColumnInSlides(ColumnMetaData.KEY_SLIDE_SAMPLE_ID);
  }

  /**
   * Add a column from the images table to this detail query.
   */
  private void addColumnInImages(String key) {
    _queryBuilder.addColumn(key);
    _queryBuilder.addTable(TableMetaData.KEY_TABLE_IMAGES);
    _queryBuilder.addTable(TableMetaData.KEY_TABLE_SLIDE);
    _queryBuilder.addJoin(FilterMetaData.KEY_JOIN_IMAGES_SLIDE);
  }

  /**
   * Add a column from the slide table to this detail query.
   */
  private void addColumnInSlides(String key) {
    _queryBuilder.addColumn(key);
    _queryBuilder.addTable(TableMetaData.KEY_TABLE_SLIDE);
  }

  /**
   * Creates and populates {@link com.ardais.bigr.lims.javabeans.ImageData}
   * data beans for the samples in the specified map, with the columns specified 
   * by the addColumn methods in this class.  The ImageData beans
   * are set onto their associated samples.
   * 
   * @param  a <code>Map</code> of {@link com.ardais.bigr.javabeans.SampleData}
   * 				  beans, keyed by their sample id.
   */
  public void getDetailsForSamples(Map sampleDataBeans) {

    addColumnSampleId();

    Connection con = null;
    BigrPreparedStatement pstmt = null;
    BigrResultSet brs = new BigrResultSet(new StringCanonicalizer());
    Map columns = null;

    try {
      con = ApiFunctions.getDbConnection();

      int idBatchSize = getIdBatchSize();

      List idChunks =
        ApiFunctions.chunkStrings(
          (String[]) sampleDataBeans.keySet().toArray(new String[0]),
          idBatchSize);
      Iterator chunks = idChunks.iterator();
      boolean first = true;
      while (chunks.hasNext()) {
        String[] chunk = (String[]) chunks.next();
        _queryBuilder.addFilter(FilterMetaData.KEY_SLIDE_SAMPLE_ID, chunk);
        if (first || (chunk.length != idBatchSize)) {
          StringBuffer sqlBuf = new StringBuffer(1024);
          _queryBuilder.setOptimizerHint(getQueryHint());
          // don't let this hint be superceded by a filter hint
          _queryBuilder.setHintPriority(ProductQueryBuilder.MAX_HINT_PRIORITY); 
          _queryBuilder.getQuery(sqlBuf);
          if (pstmt != null) {
            pstmt.close();
            pstmt = null;
          }
          pstmt = new BigrPreparedStatement(con, sqlBuf.toString());
        }

        _queryBuilder.bindAllParameters(pstmt);
        if (first && _queryLog.isDebugEnabled()) {
          _queryLog.debug("Detail query for " + ApiFunctions.shortClassName(getClass().getName()));
          _queryLog.debug(pstmt.toString());
        }
        brs.setResultSet(pstmt.executeQuery());

        if (columns == null) {
          columns = DbUtils.getColumnNames(brs);
        }
        while (brs.next()) {
          SampleData sample =
            (SampleData) sampleDataBeans.get(brs.getString(DbAliases.SLIDE_SAMPLE_ID));
          ArrayList images = sample.getImages();
          if (images == null) {
            images = new ArrayList();
            sample.setImages(images);
          }

          ImageData imageData = new ImageData(columns, brs);
          images.add(imageData);
        }
        brs.close();
        brs.setResultSet(null);

        first = false;
      }
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(con, pstmt, brs);
    }
  }

  /**
   * Return the number of ids to query against in a single query when
   * fetching detail rows.
   * 
   * @return the number of ids
   */
  private int getIdBatchSize() {
    return ApiProperties.getPropertyAsInt(
      "api.bigr.library." + ApiFunctions.shortClassName(getClass().getName()) + ".batch.size",
      500);
  }

  /**
   * Return the Oracle query optimizer hint to use when fetching detail rows,
   * or null if there is no hint defined.
   *
   * @return the Oracle query optimizer hint
   */
  private String getQueryHint() {
    return ApiProperties.getProperty(
      "api.bigr.library." + ApiFunctions.shortClassName(getClass().getName()) + ".hint");
  }
}
