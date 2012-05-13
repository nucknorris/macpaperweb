<%@ include file="/WEB-INF/views/includes/taglibs.jsp"%>

<!DOCTYPE HTML>

<html>
<head>
<title>MacPaper Start</title>
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
			<h1>Welcome to MacPaper</h1>
		</div>
		<div>
			<form action="elasticsearch" method="get">
				search: <input type="text" name="searchPhrase"> <input
					type="submit">
			</form>
			<a href="upload">upload new file</a>
		</div>
	</div>
</body>
</html>