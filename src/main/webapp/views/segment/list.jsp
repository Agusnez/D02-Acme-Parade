<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<%-- Path (List Segments) --%>

<display:table name="segments" id="row" requestURI="${requestURI}" pagesize="5">
	
	
	<acme:column property="origin" titleKey="segment.origin" value= "${row.origin}"/>
	
	<acme:column property="destination" titleKey="segment.destination" value= "${row.destination} "/>
	
	<acme:column property="timeOrigin" titleKey="segment.timeOrigin" value="${row.timeOrigin}" />
	
	<acme:column property="timeDestination" titleKey="segment.timeDestination" value= "${row.timeDestination}"/>
	
	<acme:url href="segment/brotherhood/display.do?paradeId=${row.id}" code="segment.display" />
	

</display:table>

	
	<acme:button name="back" code="back" onclick="javascript: relativeRedir('welcome/index.do');" />
	