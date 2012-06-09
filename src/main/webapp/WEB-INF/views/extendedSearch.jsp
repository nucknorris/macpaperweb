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
	<div class="container">
		<div id="header" class="prepend-1 span-22 append-1 last">
			<h1>erweiterte Suche</h1>
		</div>
		<a href="javascript:history.back()">Zurück</a>
		<h2>Angepasste Suche:</h2>
		<form>
		 <fieldset>
			<legend>Filter: </legend>
			
			<input type="checkbox" name="e1" value="checkAuthor" />Author<br />
			<input type="text" name="author" /> <br /> 
			
			<input type="checkbox" name="e2" value="checkUniversity" />Universität<br />
			<input type="text" name="uni" /> <br />
			
			<input type="checkbox" name="e3" value="checkCategory" />Kategorie<br />
			<input type="text" name="category" /> <br />
			
			<input type="checkbox" name="e3" value="checkTag" />Tag<br />
			<input type="text" name="tags" /> <br />
		  </fieldset>
		  <fieldset>
			 <legend>Suche nach:</legend>
			
			<input type="text" name="tags" /><br />
			Suche
			<input type="radio" name="andor" value=and />enthät eines der Wörter<br />
			<input type="radio" name="andor" value="andor" />enthält beide Wörter.<br />

			<input type="submit" value="Suche starten" />
		  </fieldset>
		</form>
		<a href="javascript:history.back()">Zurück</a>
	</div>
</body>
</html>