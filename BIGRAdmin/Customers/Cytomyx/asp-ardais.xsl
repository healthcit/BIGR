<?xml version="1.0" encoding="ISO-8859-1"?>

<xsl:stylesheet
  xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
  version="1.0">

  <xsl:output method="xml" encoding="UTF-8" indent="yes"/>

  <xsl:variable name="resultsviewall">
    <listItem item="COMPOSITION_ALL"/>
    <listItem item="DIAGNOSIS_LIMS"/>
    <listItem item="IMAGES"/>
    <listItem item="PV_NOTES"/>
    <listItem item="PV_NOTES_INTERNAL"/>
    <listItem item="ASM_POS_WITH_PV"/>
    <listItem item="ASM_OR_PREP"/>
    <listItem item="ASM_TISSUE"/>
    <listItem item="NUM_SECTIONS"/>
    <listItem item="QC_STATUS"/>
    <listItem item="SALES_STATUS"/>
    <listItem item="QC_POSTED"/>
  </xsl:variable>

  <xsl:variable name="resultsviewso">
    <listItem item="PV_NOTES_INTERNAL"/>
    <listItem item="ASM_TISSUE"/>
    <listItem item="NUM_SECTIONS"/>
    <listItem item="QC_STATUS"/>
    <listItem item="SALES_STATUS"/>
    <listItem item="QC_POSTED"/>
  </xsl:variable>

  <xsl:template match="@*|node()">
    <xsl:copy>
      <xsl:apply-templates select="@*|node()"/>
    </xsl:copy>
  </xsl:template>

  <xsl:template match="simpleList[@name='results_view_columns']">
    <xsl:copy>
      <xsl:apply-templates select="@*|node()"/>
      <xsl:copy-of select="$resultsviewall"/>
    </xsl:copy>
  </xsl:template>

  <xsl:template match="simpleList[@name='results_view_columns_so_only']">
    <xsl:copy>
      <xsl:apply-templates select="@*|node()"/>
      <xsl:copy-of select="$resultsviewso"/>
    </xsl:copy>
  </xsl:template>

</xsl:stylesheet>
