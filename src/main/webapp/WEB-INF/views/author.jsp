<%@ include file="/WEB-INF/views/includes/taglibs.jsp"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>


<!DOCTYPE html>
<html>
<head>
<title>MacPaper Author</title>
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
		<h3>Detailed Information about ${recievedAuthor}</h3>
	</div>
	<div class="container">
		<div id="header"></div>
		<div id="content" class="span-24 last">
			<sf:form modelAttribute="author" method="POST"
				action="${pageContext.request.contextPath}/author/update"
				enctype="multipart/form-datahast ">
				<table>
					<tr>
						<td width="3%"><img
							src="<s:url value="/icons/glyphicons_341_github.png" />" /></td>
						<td><sf:label path="authorId">AuthorId:</sf:label></td>
						<td><sf:input path="authorId" readonly="true" size="50" /></td>
					</tr>
					<tr>
						<td width="3%"><img
							src="<s:url value="/icons/glyphicons_341_github.png" />" /></td>
						<td><sf:label path="title">Title:</sf:label></td>
						<td><sf:input path="title" readonly="false" size="50" /></td>
					</tr>
					<tr>
						<td width="3%"><img
							src="<s:url value="/icons/glyphicons_341_github.png" />" /></td>
						<td><sf:label path="name">Name:</sf:label></td>
						<td><sf:input path="name" readonly="true" size="50" /></td>
					</tr>
					<tr>
						<td width="3%"><img
							src="<s:url value="/icons/glyphicons_341_github.png" />" /></td>
						<td><sf:label path="lastname">Lastname:</sf:label></td>
						<td><sf:input path="lastname" readonly="false" size="50" /></td>
					</tr>
					<tr>
						<td width="3%"><img
							src="<s:url value="/icons/glyphicons_341_github.png" />" /></td>
						<td><sf:label path="email">Email:</sf:label></td>
						<td><sf:input path="email" readonly="false" size="50" /></td>
					</tr>
					<tr>
						<td width="3%"><img
							src="<s:url value="/icons/glyphicons_341_github.png" />" /></td>
						<td><sf:label path="universityId">UniversityId:</sf:label></td>
						<td><sf:input path="universityId" readonly="true" size="50" /></td>
					</tr>
					<tr>
						<td width="3%"><img
							src="<s:url value="/icons/glyphicons_341_github.png" />" /></td>
						<td><sf:label path="paperIds">PaperIds:</sf:label></td>
						<td><sf:input path="paperIds" readonly="true" size="50" /></td>
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