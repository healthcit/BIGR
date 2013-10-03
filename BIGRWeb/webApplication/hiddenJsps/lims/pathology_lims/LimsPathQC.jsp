<%@ page language="java" errorPage="/hiddenJsps/reportError/errorReport.jsp"%>
<%
	com.ardais.bigr.orm.helpers.FormLogic.commonPageActions(request, response, this.getServletContext(),"P");
%>
<%@ taglib uri='/tld/struts-template' prefix='template' %>
<%@ taglib uri='/tld/struts-logic' prefix='logic' %>
<%@ taglib uri='/tld/struts-html' prefix='html' %>

<template:insert template='/hiddenJsps/lims/pathology_lims/LimsPathQCTemplate.jsp'>
    <template:put name='title' content='Path QC Work Queue' direct="true"/>
    <template:put name='header' content='/hiddenJsps/lims/pathology_lims/headers/PathQC.jsp'/>
    <template:put name='detail' content='/hiddenJsps/lims/pathology_lims/details/PathQC.jsp'/>
    
</template:insert>
