<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<acme:display code="history.inceptionRecord" property=""/>
<fieldset>
<div>
<acme:display code="history.inceptionRecord.title" property="${history.inceptionRecord.title }" />

<acme:display code="history.inceptionRecord.description" property="${history.inceptionRecord.description }" />

<acme:display code="history.inceptionRecord.photos" property="${history.inceptionRecord.photos}" />

<security:authorize access="hasRole('BROTHERHOOD')">
<jstl:if test="${history.id==0}">
	<acme:button name="create" code="inceptionRecord.create" onclick="javascript: relativeRedir('inceptionRecord/brotherhood/create.do');"/>
	</jstl:if>
</security:authorize>

<security:authorize access="hasRole('BROTHERHOOD')">
<jstl:if test="${history.id!=0}">
	<acme:button name="edit" code="inceptionRecord.edit" onclick="javascript: relativeRedir('inceptionRecord/brotherhood/edit.do?inceptionRecordId=${history.inceptionRecord.id}');"/>
	</jstl:if>
</security:authorize>
</div>
</fieldset>


<acme:display code="history.legalRecords" property=""/>
<fieldset>
<div>
<display:table pagesize="5" name="history.legalRecords" id="row" 
requestURI="${requestURI }" class="displaytag">

	<acme:column property="title" titleKey="legalRecord.title" value= "${row.title}: "/>
	
	<acme:column property="description" titleKey="legalRecord.description" value= "${row.description}: "/>
	
	<acme:column property="legalName" titleKey="legalRecord.legalName" value= "${row.legalName}: "/>
	
	<acme:column property="VATNumber" titleKey="legalRecord.VATNumber" value= "${row.VATNumber}: "/>
	
	<acme:column property="laws" titleKey="legalRecord.laws" value= "${row.laws}: "/>
	
	<security:authorize access="hasRole('BROTHERHOOD')">
	<acme:url href="legalRecord/brotherhood/edit.do?legalRecordId=${row.id}" code="legalRecord.edit"/>
	</security:authorize>	
</display:table>
	<security:authorize access="hasRole('BROTHERHOOD')">
	<acme:button name="create" code="legalRecord.create" onclick="javascript: relativeRedir('legalRecord/brotherhood/create.do');"/>
	</security:authorize>
</div>
</fieldset>


<acme:display code="history.periodRecords" property=""/>
<fieldset>
<div>
<display:table pagesize="5" name="history.periodRecords" id="row" 
requestURI="${requestURI }" class="displaytag">

	<acme:column property="title" titleKey="periodRecord.title" value= "${row.title}: "/>
	
	<acme:column property="description" titleKey="periodRecord.description" value= "${row.description}: "/>
	
	<acme:column property="startYear" titleKey="periodRecord.startYear" value= "${row.startYear}: "/>
	
	<acme:column property="endYear" titleKey="periodRecord.endYear" value= "${row.endYear}: "/>
	
	<acme:column property="photos" titleKey="periodRecord.photos" value= "${row.photos}: "/>
	
	<security:authorize access="hasRole('BROTHERHOOD')">
	<acme:url href="periodRecord/brotherhood/edit.do?periodRecordId=${row.id}" code="periodRecord.edit"/>
	</security:authorize>	
</display:table>
	<security:authorize access="hasRole('BROTHERHOOD')">
	<acme:button name="create" code="periodRecord.create" onclick="javascript: relativeRedir('periodRecord/brotherhood/create.do');"/>
	</security:authorize>
</div>
</fieldset>

<acme:display code="history.linkRecords" property=""/>
<fieldset>
<div>
<display:table pagesize="5" name="history.linkRecords" id="row" 
requestURI="${requestURI }" class="displaytag">

	<acme:column property="title" titleKey="linkRecord.title" value= "${row.title}: "/>
	
	<acme:column property="description" titleKey="linkRecord.description" value= "${row.description}: "/>
	
	<acme:column property="link" titleKey="linkRecord.link" value= "${row.link}: "/>
	<security:authorize access="hasRole('BROTHERHOOD')">	
	<acme:url href="linkRecord/brotherhood/edit.do?linkRecordId=${row.id}" code="linkRecord.edit"/>
	</security:authorize>	
</display:table>
	<security:authorize access="hasRole('BROTHERHOOD')">
	<acme:button name="create" code="linkRecord.create" onclick="javascript: relativeRedir('linkRecord/brotherhood/create.do');"/>
	</security:authorize>
</div>
</fieldset>


<acme:display code="history.miscellaneousRecords" property=""/>
<fieldset>
<div>
<display:table pagesize="5" name="history.miscellaneousRecords" id="row" 
requestURI="${requestURI }" class="displaytag">

	<acme:column property="title" titleKey="miscellaneousRecord.title" value= "${row.title}: "/>
	
	<acme:column property="description" titleKey="miscellaneousRecord.description" value= "${row.description}: "/>
	<security:authorize access="hasRole('BROTHERHOOD')">
	<acme:url href="miscellaneousRecord/brotherhood/edit.do?miscellaneousRecordId=${row.id}" code="miscellaneousRecord.edit"/>
	</security:authorize>
</display:table>
	<security:authorize access="hasRole('BROTHERHOOD')">
	<acme:button name="create" code="miscellaneousRecord.create" onclick="javascript: relativeRedir('miscellaneousRecord/brotherhood/create.do');"/>
	</security:authorize>
</div>
</fieldset>