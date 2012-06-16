<%@ include file="/WEB-INF/views/includes/taglibs.jsp"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>


<!DOCTYPE HTML>
<html>
<head>
<title>Extended Search</title>
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
		<h3>Extended Search:</h3>
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
					<input id='search-text' name='searchPhrase' placeholder='type here'
						type='text' />
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

		<div id="content" class="span-24 last">
			<form action='evaluateExtendedSearch' id='search-form' method='get'>
				<fieldset>
					<legend>Filter: </legend>
					<table border="0" width="100%" cellpadding="0" cellspacing="0">
						<tr>
							<td>Author:</td>
							<td><input type="text" name="author"
								placeholder='Please insert author' /></td>
						</tr>
						<tr>
							<td>University:</td>
							<td><input type="text" name="uni"
								placeholder='Please insert university' /></td>
						</tr>
						<tr>
							<td>Category:</td>
							<td><input type="text" name="category"
								placeholder='Please insert category' /></td>
						</tr>
						<tr>
							<td>Tags:</td>
							<td><input type="text" name="tags"
								placeholder='Please insert tags' /></td>
						</tr>
					</table>
				</fieldset>
				<fieldset>
					<legend>Search papers, ... </legend>
					<table border="0" width="100%" cellpadding="0" cellspacing="0">
						<tr>
							<td>contain all these words:</td>
							<td><input type="text" name="and"
								placeholder='Foo Bar Bull Shit' /></td>
						</tr>
						<tr>
							<td>contain exactly the word or the phrase:</td>
							<td><input type="text" name="secialand"
								placeholder='"Foo Bar"' /></td>
						</tr>
						<tr>
							<td>one of these words include:</td>
							<td><input type="text" name="or" placeholder='Foo OR Bar' /></td>
						</tr>
					</table>
				</fieldset>
				<input id="save-button" type="submit" value="Search" />
			</form>
		</div>
	</div>
</body>
</html>