<%@ include file="/WEB-INF/views/includes/taglibs.jsp"%>

<!DOCTYPE HTML>
<html>
<head>
<title>Normale Suche BÄM!</title>
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
			<h1>einfache Suche</h1>
		</div>
		<a href="javascript:history.back()">Zurück</a>
		<form>
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