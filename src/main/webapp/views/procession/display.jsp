<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>


<acme:display property="${procession.title }" code="procession.title" />

<acme:display property="${procession.description }" code="procession.description" />

<acme:display property="${procession.maxRow }" code="procession.maxRow" />

<acme:display property="${procession.maxColumn }" code="procession.maxColumn" />

<acme:display property="${procession.brotherhood.title }" code="procession.brotherhood" />

<acme:display property="${procession.ticker }" code="procession.ticker" />

<spring:message code="procession.organisationMoment"/>: 
<fmt:formatDate value="${procession.organisationMoment}" pattern="yyyy/MM/dd HH:mm"/>
<br> 

<acme:display property="${procession.finalMode }" code="procession.finalMode" />

<acme:button name="back" code="procession.back" onclick="javascript: relativeRedir('procession/brotherhood/list.do');" />


