<%@ include file="/WEB-INF/views/includes/taglibs.jsp"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>


<!DOCTYPE html>
<html>
<head>
<title>MacPaper University</title>
<link
	href='http://fonts.googleapis.com/css?family=Open+Sans:400,600,700'
	rel='stylesheet' type='text/css'>
<link rel="stylesheet" href="<c:url value='/css/blueprint/screen.css'/>"
	type="text/css" media="screen, projection">
<link rel="stylesheet" href="<c:url value='/css/blueprint/print.css'/>"
	type="text/css" media="print">
<link rel="stylesheet" href="<c:url value='/css/main.css'/>"
	type="text/css">
<script src="<c:url value='/js/jquery-1.6.1.min.js'/>"></script>
</head>
<body>
	<div id="head">
		<div id="logo">
			<a href="${pageContext.request.contextPath}" class="quiet">MacPaper</a>
		</div>
	</div>
	<div id="subhead">
		<h3>Adding or editing an university</h3>
		<div id="managementPic">
			<a href="management"> <img
				src="<s:url value="/icons/glyphicons_280_settings.png" />" />
			</a>
		</div>
		<div id="managementButton">
			<a href="management">Management</a>
		</div>
		<div id="uploadPic">
			<a href="upload"> <img
				src="<s:url value="/icons/glyphicons_201_upload.png" />" />
			</a>
		</div>
		<div id="uploadButton">
			<a href="upload">Upload a new Paper </a>
		</div>
	</div>
	<div class="container">
		<div id="content" class="span-24 last">
			<h2>To add a new University enter fill the fields and click on save.</h2>

			<sf:form modelAttribute="university" method="POST"
				action="${pageContext.request.contextPath}/university/update"
				enctype="multipart/form-datahast ">
				<table>
					<tr>
						<td width="3%"><img
							src="<s:url value="/icons/glyphicons_065_tag.png" />" /></td>
						<td><sf:label path="universityId">UniversityId:</sf:label></td>
						<td><sf:input path="universityId" readonly="true" size="50" /></td>
					</tr>
					<tr>
						<td width="3%"><img
							src="<s:url value="/icons/glyphicons_264_vcard.png" />" /></td>
						<td><sf:label path="name">Name:</sf:label></td>
						<td><sf:input path="name" readonly="false" size="50" /></td>
					</tr>
					<tr>
						<td width="3%"><img
							src="<s:url value="/icons/glyphicons_242_google_maps.png" />" /></td>
						<td><sf:label path="city">City:</sf:label></td>
						<td><sf:input path="city" readonly="false" size="50" /></td>
					</tr>
					<tr>
						<td width="3%"><img
							src="<s:url value="/icons/glyphicons_242_google_maps.png" />" /></td>
						<td><sf:label path="postcode">Postcode:</sf:label></td>
						<td><sf:input path="postcode" readonly="false" size="50" /></td>
					</tr>
					<tr>
						<td width="3%"><img
							src="<s:url value="/icons/glyphicons_242_google_maps.png" />" /></td>
						<td><sf:label path="street">Street:</sf:label></td>
						<td><sf:input path="street" readonly="false" size="50" /></td>
					</tr>
					<tr>
						<td width="3%"><img
							src="<s:url value="/icons/glyphicons_242_google_maps.png" />" /></td>
						<td><sf:label path="street2">Street2:</sf:label></td>
						<td><sf:input path="street2" readonly="false" size="50" /></td>
					</tr>
					<tr>
						<td width="3%"><img
							src="<s:url value="/icons/glyphicons_242_google_maps.png" />" /></td>
						<td><sf:label path="housenumber">Housenumber:</sf:label></td>
						<td><sf:input path="housenumber" readonly="false" size="50" /></td>
					</tr>
					<tr>
						<td width="3%"><img
							src="<s:url value="/icons/glyphicons_242_google_maps.png" />" /></td>
						<td><sf:label path="country">Country:</sf:label></td>
						<td><sf:input path="country" readonly="false" size="50" /></td>
					</tr>
					<tr>
						<td width="3%"><img
							src="<s:url value="/icons/glyphicons_024_parents.png" />" /></td>
						<td><sf:label path="authorIds">Authoren:</sf:label></td>
						<td><sf:input path="authorIds" readonly="false" size="50" /></td>
					</tr>
				</table>
				<input id='save-button' type="submit" value="Save" />
			</sf:form>
		</div>
		<div id="footer" class="span-24 ">
			<p></p>
		</div>
	</div>
</body>
</html>