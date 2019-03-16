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

<form:form action="educationRecord/handyWorker/edit.do"
	modelAttribute="educationRecord" method="post">

	<form:hidden path="id" />
	<form:hidden path="version" />
	

	<form:label path="title">
		<spring:message code="educationRecord.title"></spring:message>
	</form:label>
	<form:input path="title" />
	<form:errors cssClass="error" path="title"></form:errors>
	<br />

	<form:label path="institution">
		<spring:message code="educationRecord.institution"></spring:message>
	</form:label>
	<form:input path="institution" />
	<form:errors cssClass="error" path="institution"></form:errors>
	<br />

	<form:label path="attachment">
		<spring:message code="educationRecord.attachment"></spring:message>
	</form:label>
	<form:input path="attachment" />
	<form:errors cssClass="error" path="attachment"></form:errors>
	<br />

	<form:label path="comments">
		<spring:message code="educationRecord.comments"></spring:message>
	</form:label>
	<form:textarea path="comments" />
	<form:errors cssClass="error" path="comments"></form:errors>
	<br />

	<form:label path="period">
		<spring:message code="educationRecord.period" />
	</form:label>
	<form:input path="period" />
	<form:errors cssClass="error" path="period" />
	<br />


	<input type="submit" name="save"
		value="<spring:message code="educationRecord.save"/>" />


	<input type="button" name="cancel"
		value="<spring:message code="educationRecord.cancel" />"
		onclick="javascript: relativeRedir('curriculum/handyWorker/display.do');" />

</form:form>