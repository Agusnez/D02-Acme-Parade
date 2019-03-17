<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

	modelAttribute="educationRecord" method="post">

<form:form modelAttribute="legalRecord" action="legalRecord/brotherhood/edit.do">

	<form:hidden path="id" />
	<form:hidden path="version" />
	
	<acme:textbox code="legalRecord.title" path="title" obligatory="true"/>
	
	<acme:textbox code="legalRecord.description" path="description" obligatory="true"/>
	
	<acme:textbox code="legalRecord.legalName" path="legalName" obligatory="true"/>
	
	<acme:textbox code="legalRecord.VATNumber" path="VATNumber" obligatory="true"/>
	
	<acme:textbox code="legalRecord.laws" size="100" path="laws" obligatory="false"/>

	<acme:submit name="save" code="legalRecord.save" />
	
	
	<acme:cancel code="legalRecord.cancel" url="history/display.do"/>


</form:form>