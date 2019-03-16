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

<form:form action="endorserRecord/handyWorker/edit.do"
	modelAttribute="endorserRecord" method="post">

	<form:hidden path="id" />
	<form:hidden path="version" />
	

	<form:label path="fullName">
		<spring:message code="endorserRecord.fullName"></spring:message>
	</form:label>
	<form:input path="fullName" />
	<form:errors cssClass="error" path="fullName"></form:errors>
	<br />

	<form:label path="email">
		<spring:message code="endorserRecord.email"></spring:message>
	</form:label>
	<form:input path="email" />
	<form:errors cssClass="error" path="email"></form:errors>
	<br />

	<form:label path="phone">
		<spring:message code="customer.phone" />
	</form:label>
	<form:input path="phone" id="phone" />
	<%-- onblur="javascript: checkPhone();"--%>	
	<form:errors class="error" path="phone" />
	<br />	

	<form:label path="comments">
		<spring:message code="endorserRecord.comments"></spring:message>
	</form:label>
	<form:textarea path="comments" />
	<form:errors cssClass="error" path="comments"></form:errors>
	<br />

	<form:label path="linkedInProfile">
		<spring:message code="endorserRecord.linkedInProfile" />
	</form:label>
	<form:input path="linkedInProfile" />
	<form:errors cssClass="error" path="linkedInProfile" />
	<br />


	<input type="submit" name="save"
		value="<spring:message code="endorserRecord.save"/>" />


	<input type="button" name="cancel"
		value="<spring:message code="endorserRecord.cancel" />"
		onclick="javascript: relativeRedir('curriculum/handyWorker/display.do');" />

</form:form>

<%-- 
<script type="text/javascript">
	function checkPhone() {
		var target = document.getElementById("phone");
		var input = target.value;
		var regExp1 = new RegExp("(^[+]([1-9]{1,3})) ([(][1-9]{1,3}[)]) (\\d{4,}$)");
		var regExp2 = new RegExp("(^[+]([1-9]{1,3})) (\\d{4,}$)");
		var regExp3 = new RegExp("(^\\d{4,}$)");

		if ('${phone}' != input && regExp1.test(input) == false && regExp2.test(input) == false && regExp3.test(input) == false) {
			if (confirm('<spring:message code="actor.phone.wrong" />') == false) {
				return true;
			
			}
		} else if ('${phone}' != input && regExp1.test(input) == false && regExp2.test(input) == false && regExp3.test(input) == true) {
			target.value = '${defaultCountry}' + " " + input;
		}
	}
</script>
--%>