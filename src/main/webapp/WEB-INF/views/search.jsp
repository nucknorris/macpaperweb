<%@ include file="/WEB-INF/views/includes/taglibs.jsp"%>

<!DOCTYPE html>
<html>
<head>
<title>MacPaper ResultPage</title>
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
				</form>
			</div>
		</div>
		<table border="1">
			<tr>
				<th>Paper ID</th>
				<th>Title</th>
				<th></th>
				<!-- 				<th>Key Class</th> -->
			</tr>
			<c:forEach var="paper" items="${paper}" varStatus="status">
				<c:if test="${not empty paper.getPaperId()}">
					<tr>
						<td>${ paper.getPaperId()}</td>
						<td>${ paper.getTitle()}</td>
						<td><a
							href="${pageContext.request.contextPath}/paper/${ paper.getPaperId()}">edit</a></td>
					</tr>
				</c:if>
			</c:forEach>
		</table>

	</div>
</body>
</html>