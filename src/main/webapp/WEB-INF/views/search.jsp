<%@ include file="/WEB-INF/views/includes/taglibs.jsp"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html>
<head>
<title>MacPaper ResultPage</title>
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
		<h3>Search Results for: ${searchTerm}</h3>
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
		<div id="header">
			<div id='search-box'>
				<form action='elasticsearch' id='search-form' method='get'
					target='_top'>
					<input id='search-text' name='searchPhrase'
						placeholder='${searchTerm}' type='text' />
					<button id='search-button' type='submit'>
						<span>Search</span>
					</button>
					<button id='extendedsearch-button' type='button'
						onClick="location.href='${pageContext.request.contextPath}/extendedSearch'">
						<span>Extended Search</span>
					</button>
				</form>
			</div>
		</div>
		<table border="1">
			<tr>
				<th width="3%"></th>
				<th width="30%">Paper ID</th>
				<th width="64%">Title</th>
				<th width="3%"></th>
				<!-- 				<th>Key Class</th> -->
			</tr>
			<c:forEach var="paper" items="${paper}" varStatus="status">
				<c:if test="${not empty paper.getPaperId()}">
					<tr>
						<td><img
							src="<s:url value="/icons/glyphicons_039_notes.png" />" /></td>
						<td>${ paper.getPaperId()}</td>
						<td><b>${ paper.getTitle()}</b></td>
						<td><a
							href="${pageContext.request.contextPath}/paper/${ paper.getPaperId()}"><img
								src="<s:url value="/icons/glyphicons_195_circle_info.png" />" />
						</a></td>
					</tr>
				</c:if>
			</c:forEach>
		</table>

	</div>
</body>
</html>