<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<jstl:if test="${AreInFinder }">
<security:authorize access="hasRole('MEMBER')">
<form:form action="${requestAction }" modelAttribute="finder"> 

	<form:hidden path="id"/>
	<form:hidden path="version"/>

	<acme:textbox path="keyWord" code="parade.keyWord" />
	
	<acme:textbox path="area" code="parade.area" />
	
	<acme:textbox path="minDate" code="parade.minDate" />
	
	<acme:textbox path="maxDate" code="parade.maxDate" />
	
	<input type="submit" name="find" value="<spring:message code="parade.find"/>"/>
	
</form:form> 
</security:authorize>
</jstl:if>

<br/>

<display:table name="parades" id="row" requestURI="${requestURI }" pagesize="${pagesize }">
	
	
	<acme:column property="title" titleKey="parade.title" value= "${row.title}: "/>
	
	<acme:column property="brotherhood.title" titleKey="parade.brotherhood" value= "${row.brotherhood.title} "/>
	
	<acme:column property="brotherhood.area.name" titleKey="parade.area" value="${row.brotherhood.area.name }" />
	
	<acme:column property="description" titleKey="parade.description" value= "${row.description}: "/>
	
	<acme:dateFormat titleKey="parade.organisationMoment" value="${row.organisationMoment }" pattern="yyyy/MM/dd HH:mm" />

	<acme:url href="float/parade/list.do?paradeId=${row.id }" code="parade.float" />

</display:table>
	
	<br/>
	
	<acme:button name="back" code="parade.back" onclick="javascript: relativeRedir('welcome/index.do');" />