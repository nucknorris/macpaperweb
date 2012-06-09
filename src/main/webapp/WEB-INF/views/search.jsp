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
	<div class="container">
		<div id="header" class="prepend-1 span-22 append-1 last">
			<h1>SearchResults</h1>
		</div>
		<a href="javascript:history.back()">back</a>
		<p>
			Search Term: ${searchTerm} <br /> Document Score: ${documentScore}<br />
			Document ID: ${documentId} <br />Total Hits: ${totalHits} (only paper with ID are shown) <br />Max
			Score: ${maxScore}
		</p>

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