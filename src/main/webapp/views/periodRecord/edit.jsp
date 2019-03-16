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

<form:form action="professionalRecord/handyWorker/edit.do"
	modelAttribute="professionalRecord" method="post">

	<form:hidden path="id" />
	<form:hidden path="version" />
	

	<form:label path="companyName">
		<spring:message code="professionalRecord.companyName"></spring:message>
	</form:label>
	<form:input path="companyName" />
	<form:errors cssClass="error" path="companyName"></form:errors>
	<br />

	<form:label path="role">
		<spring:message code="professionalRecord.role"></spring:message>
	</form:label>
	<form:input path="role" />
	<form:errors cssClass="error" path="role"></form:errors>
	<br />

	<form:label path="attachment">
		<spring:message code="professionalRecord.attachment"></spring:message>
	</form:label>
	<form:input path="attachment" />
	<form:errors cssClass="error" path="attachment"></form:errors>
	<br />

	<form:label path="comments">
		<spring:message code="professionalRecord.comments"></spring:message>
	</form:label>
	<form:textarea path="comments" />
	<form:errors cssClass="error" path="comments"></form:errors>
	<br />

	<form:label path="period">
		<spring:message code="professionalRecord.period" />
	</form:label>
	<form:input path="period" />
	<form:errors cssClass="error" path="period" />
	<br />


	<input type="submit" name="save"
		value="<spring:message code="professionalRecord.save"/>" />


	<input type="button" name="cancel"
		value="<spring:message code="professionalRecord.cancel" />"
		onclick="javascript: relativeRedir('curriculum/handyWorker/display.do');" />

</form:form>