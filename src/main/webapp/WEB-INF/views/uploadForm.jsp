<%@ include file="/WEB-INF/views/includes/taglibs.jsp"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

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
	<div id="head">
		<div id="logo">
			<a href="${pageContext.request.contextPath}" class="quiet">MacPaper</a>
		</div>
	</div>
	<div id="subhead">
		<h3>Uploading a new Paper</h3>
	</div>
	<div class="container">
		<div id="header">To upload a new paper, choose the file and
			title and click on submit:</div>
		<div id="content" class="span-24 last">

			<form:form modelAttribute="uploadItem" method="post"
				enctype="multipart/form-data">
				<fieldset>
					<legend>Upload Fields</legend>

					<p>
						<form:label for="title" path="title">Title</form:label>
						<br />
						<form:input path="title" />
					</p>

					<p>
						<form:label for="fileData" path="fileData">File</form:label>
						<br />
						<form:input path="fileData" type="file" />
					</p>

					<p>
						<input id="save-button" type="submit" />
					</p>

				</fieldset>
			</form:form>
		</div>
	</div>
</body>
</html>