<%@ page language="java" errorPage="/hiddenJsps/reportError/errorReport.jsp"%>
<%
	com.ardais.bigr.orm.helpers.FormLogic.commonPageActions(request, response, this.getServletContext(),"P");
%>
<%
  response.setHeader("Cache-Control", "no-cache");
  response.setHeader("Pragma", "no-cache");
  response.setDateHeader("Expires", 0);
%>
<%@ taglib uri='/tld/struts-template' prefix='template' %>
<%@ taglib uri='/tld/struts-logic' prefix='logic' %>

<template:insert template='/hiddenJsps/lims/pathology_lims/LimsManageEvaluationsTemplate.jsp'>
    <template:put name='title' content='Sample Pathology Evaluations' direct="true"/>
    <template:put name='header' content='/hiddenJsps/lims/pathology_lims/headers/ManageInformation.jsp'/>
    <template:put name='detail' content='/hiddenJsps/lims/pathology_lims/details/EvaluationsList.jsp'/>
</template:insert>
