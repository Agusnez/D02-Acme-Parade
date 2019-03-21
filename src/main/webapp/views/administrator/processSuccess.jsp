<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<jstl:if test="${numOfDeactivations > 0}">
<p><spring:message code="sponsorship.success.begin" /> <jstl:out value="${numOfDeactivations}" /> <spring:message code="sponsorship.success.end" /></p>

<iframe src="https://giphy.com/embed/4xpB3eE00FfBm" width="480" height="458" frameBorder="0" class="giphy-embed" allowFullScreen></iframe>
</jstl:if>

<jstl:if test="${numOfDeactivations == 0}">
<p><spring:message code="sponsorship.success.empty" /></p>
<iframe src="https://giphy.com/embed/6uGhT1O4sxpi8" width="480" height="240" frameBorder="0" class="giphy-embed" allowFullScreen></iframe>
</jstl:if>