<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<form:form action="sponsorship/sponsor/edit.do" modelAttribute="sponsor">
	
	<form:hidden path="id" />
	<form:hidden path="version" />
	
	
	<acme:textbox code="sponsorship.banner" path="banner" obligatory="true"/>
	
	<acme:textbox code="sponsorship.targetURL" path="targetURL" obligatory="true"/>
	
	<acme:textbox code="sponsorship.creditCard.holderName" path="creditCard.holderName" obligatory="true"/>
	
	<acme:textbox code="sponsorship.creditCard.make" path="creditCard.make" obligatory="true"/>
	
	<acme:textbox code="sponsorship.creditCard.number" path="creditCard.number" obligatory="true"/>
	
	<acme:textbox code="sponsorship.creditCard.expMonth" path="creditCard.expMonth" obligatory="true"/>
	
	<acme:textbox code="sponsorship.creditCard.expYear" path="creditCard.expYear" obligatory="true"/>
	
	<acme:textbox code="sponsorship.creditCard.CVV" path="creditCard.CVV" obligatory="true"/>
	
	<acme:submit name="save" code="sponsorship.save" />
	
	<acme:cancel code="sponsorship.cancel" url="welcome/index.do" />


</form:form>    