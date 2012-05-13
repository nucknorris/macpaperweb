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
		<p>Search Term: ${searchTerm}</p>
		<p>Document Score: ${documentScore}</p>
		<p>Document ID: ${documentId}</p>
		<p>Total Hits: ${totalHits}</p>
		<p>Max Score: ${maxScore}</p>

		<p>Document Key: ${documentKey}</p>
	</div>
</body>
</html>