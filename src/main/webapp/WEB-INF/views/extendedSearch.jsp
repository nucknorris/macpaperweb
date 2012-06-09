<%@ include file="/WEB-INF/views/includes/taglibs.jsp"%>

<!DOCTYPE HTML>
<html>
<head>
<title>Erweiterte Suche BÄM!</title>
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
		<div><h1>Suche</h1></div>
		
	</div>
	<div id="subhead">
		<h3>Angepasste Suche:</h3>
	</div>
	<div class="container">
		<div id="header">
			<div id='search-box'>
				<a href="javascript:history.back()">Zurück</a>
				<form>
					<fieldset>
						<legend>Filter: </legend>

						<input type="checkbox" name="e1" value="checkAuthor" />Author<br />
						<input type="text" name="author"
							placeholder='Bitte Author eingeben!' /> <br /> <input
							type="checkbox" name="e2" value="checkUniversity" />Universität<br />
						<input type="text" name="uni"
							placeholder='Bitte Universität eingeben!' /> <br /> <input
							type="checkbox" name="e3" value="checkCategory" />Kategorie<br />
						<input type="text" name="category"
							placeholder='Bitte Kategorie eingeben!' /> <br /> <input
							type="checkbox" name="e3" value="checkTag" />Tag<br /> <input
							type="text" name="tags" placeholder='Bitte Tags eingeben!' /> <br />
					</fieldset>
					<fieldset>
						<legend>Suche nach:</legend>

						<input type="text" name="keywords"
							placeholder='Bitte Suchort eingeben!' /><br /> Suche <input
							type="radio" name="andor" value=and />enthät eines der Wörter<br />
						<input type="radio" name="andor" value="andor" />enthält beide
						Wörter.<br />

						<button id='search-button' type='submit'>
							<span>Search</span>
						</button>
					</fieldset>
				</form>
				<a href="javascript:history.back()">Zurück</a>
			</div>
		</div>
	</div>
</body>
</html>