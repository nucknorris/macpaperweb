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
		<div id="managementPic">
			<a href="/macpaperweb/management"> <img
				src="<s:url value="/icons/glyphicons_280_settings.png" />" />
			</a>
		</div>
		<div id="managementButton">
			<a href="/macpaperweb/management">Management</a>
		</div>
		<div id="uploadPic">
			<a href="/macpaperweb/upload"> <img
				src="<s:url value="/icons/glyphicons_201_upload.png" />" />
			</a>
		</div>
		<div id="uploadButton">
			<a href="/macpaperweb/upload">Upload a new Paper </a>
		</div>
	</div>
	<div class="container">

		<div id="content" class="span-24 last">
			<form action='evaluateExtendedSearch' id='search-form' method='get'>
				<div class="box">
					<fieldset>
						<legend>Filter: </legend>
						<table>
							<tr>
								<td width="25%">Author:</td>
								<td><input size="70" type="text" name="author"
									placeholder='Please insert author' /></td>
							</tr>
							<tr>
								<td>University:</td>
								<td><input size="70" type="text" name="uni"
									placeholder='Please insert university' /></td>
							</tr>
							<tr>
								<td>Category:</td>
								<td><input size="70" type="text" name="category"
									placeholder='Please insert category' /></td>
							</tr>
							<tr>
								<td>Tags:</td>
								<td><input size="70" type="text" name="tags"
									placeholder='Please insert tags' /></td>
							</tr>
						</table>
					</fieldset>
				</div>
				<div class="box">

					<fieldset>
						<legend>Search papers that ... </legend>
						<table>
							<tr>
								<td width="25%">contain all these words:</td>
								<td><input size="70" type="text" name="and"
									placeholder='Foo Bar Bull Shit' /></td>
							</tr>
							<tr>
								<td>contain exactly the word or phrase:</td>
								<td><input size="70" type="text" name="secialand"
									placeholder='"Foo Bar"' /></td>
							</tr>
							<tr>
								<td>one of these words include:</td>
								<td><input size="70" type="text" name="or"
									placeholder='Foo OR Bar' /></td>
							</tr>
						</table>
					</fieldset>
				</div>

				<div class="box">
					<fieldset>
						<legend>Categories: </legend>
						<c:forEach var="cat" items="${categories}">
							<input type="checkbox" value="${cat}" name="category" />${cat} <br />
						</c:forEach>
					</fieldset>
				</div>
				<div class="extendedsearchbutton">
					<input id="save-button" type="submit" value="Search" />
				</div>

			</form>
		</div>
	</div>
</body>
</html>