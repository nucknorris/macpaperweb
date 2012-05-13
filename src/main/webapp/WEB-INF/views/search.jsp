<%@ include file="/WEB-INF/views/includes/taglibs.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
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
			Document ID: ${documentId} <br />Total Hits: ${totalHits} <br />Max
			Score: ${maxScore}
		</p>

		<table border="1">
			<tr>
				<th>Key</th>
				<th>value</th>
				<th>Key Class</th>
			</tr>
			<c:forEach var="entry" items="${myMap}" varStatus="status">
				<tr>
					<td>${entry.key}</td>
					<td>${entry.value}</td>
					<td>${entry.key.class}</td>
				</tr>
			</c:forEach>
		</table>

	</div>
</body>
</html>