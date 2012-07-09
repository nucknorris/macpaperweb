<%@ include file="/WEB-INF/views/includes/taglibs.jsp"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
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
		<h3>MacPaper - Management</h3>
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
				<form action='search' id='search-form' method='get' target='_top'>
					<div id="backbutton">
						<a href="javascript:history.back()"> <img
							src="<s:url value="/icons/glyphicons_216_circle_arrow_left.png" />" /></a>
					</div>
					<input id='search-text' name='searchPhrase' placeholder='type here'
						type='text' />
					<button id='search-button' type='submit'>
						<span>Search</span>
					</button>
					<button id='extendedsearch-button' type='button'
						onClick="location.href='${pageContext.request.contextPath}/extendedSearch'">
						<span>MacPaper - Management</span>
					</button>
				</form>
			</div>

		</div>

		<!-- 		<div id="header" class="prepend-1 span-22 append-1 last"> -->
		<div id="content" class="span-24 last"></div>
		<div id="footer" class="span-24 ">
			<p></p>
		</div>
	</div>
</body>
</html>