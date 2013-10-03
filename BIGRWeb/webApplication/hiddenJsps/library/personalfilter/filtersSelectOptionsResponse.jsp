<%@ page isELIgnored ="false" %>

<%@ page import="com.ardais.bigr.security.SecurityInfo" %>
<%@ page import="com.ardais.bigr.util.WebUtils" %>
<%@ page import="java.util.List" %>
<%@ page import="com.ardais.bigr.filter.domain.PersonalFilter" %>
<%@ page import="com.ardais.bigr.filter.PersonalFilterService" %>
<%@ page import="com.ardais.bigr.filter.domain.SamplePersonalFilter" %>
<%@ page import="com.ardais.bigr.filter.domain.EmptyPersonalFilter" %>
<%@ page import="java.util.ArrayList" %>

<%@ taglib uri="/tld/struts-bean" prefix="bean" %>
<%@ taglib uri="/tld/struts-html" prefix="html" %>
<%@ taglib uri="/tld/json" prefix="json" %>

<%
	final SecurityInfo securityInfo = WebUtils.getSecurityInfo(request);

	final List<PersonalFilter> userFilters = new ArrayList<PersonalFilter>();
	userFilters.add(new EmptyPersonalFilter());
	userFilters.addAll(
		PersonalFilterService.SINGLETON.getUserFilters(securityInfo.getAccount(),
													   securityInfo.getUsername(),
													   SamplePersonalFilter.class));
	request.setAttribute("userFilters", userFilters);
%>

<json:object>

	<json:array name="options" items="${userFilters}" var="filter">

		<json:object>
			<json:property name="value" value="${filter.id}" />
			<json:property name="label" value="${filter.name}" />
			<json:property name="selected" value="${not empty personalFilterId and filter.id eq personalFilterId}" />
		</json:object>

	</json:array>

</json:object>