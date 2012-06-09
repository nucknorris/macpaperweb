<%@ include file="/WEB-INF/views/includes/taglibs.jsp"%>

<!DOCTYPE HTML>
<html>
<head>
<title>Extended Search</title>
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
	</div>
	<div class="container">
		<div id="header">
			<div id='search-box'>
				<form action='elasticsearch' id='search-form' method='get'
					target='_top'>
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
			<form>
				<fieldset>
					<legend>Filter: </legend>

					<input type="checkbox" name="e1" value="checkAuthor" />Author<br />
					<input type="text" name="author" placeholder='Please insert author' />
					<br /> <input type="checkbox" name="e2" value="checkUniversity" />University<br />
					<input type="text" name="uni"
						placeholder='Please insert university' /> <br /> <input
						type="checkbox" name="e3" value="checkCategory" />Category<br />
					<input type="text" name="category"
						placeholder='Please insert category' /> <br /> <input
						type="checkbox" name="e3" value="checkTag" />Tag<br /> <input
						type="text" name="tags" placeholder='Please insert tags' /> <br />
				</fieldset>
				<fieldset>
					<legend>Searchphrase:</legend>
					<input type="text" name="keywords"
						placeholder='Please insert searchphrase!' /><br /> Search for <input
						type="radio" name="andor" value=and />each words<br /> <input
						type="radio" name="andor" value="andor" />all words.<br />

				</fieldset>
			</form>
		</div>
	</div>
</body>
</html>