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

<SCRIPT LANGUAGE="JavaScript">
	function openChild(file, window) {
		childWindow = open(file, window, 'resizable=no,width=200,height=400');
		if (childWindow.opener == null)
			childWindow.opener = self;
	}
</SCRIPT>

<body>
	<div id="head">
		<div id="logo">
			<a href="${pageContext.request.contextPath}" class="quiet">MacPaper</a>
		</div>
	</div>
	<div id="subhead">
		<h3>Detailed Information about the paper ${recievedPaperName}</h3>
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
						<span>Extended Search</span>
					</button>
				</form>
			</div>

		</div>

		<!-- 		<div id="header" class="prepend-1 span-22 append-1 last"> -->
		<div id="content" class="span-24 last">


			<sf:form modelAttribute="paper" method="POST " name="parentForm"
				action="${pageContext.request.contextPath}/paper/update"
				enctype="multipart/form-datahast ">
				<table>
					<tr>
						<td width="3%"><img
							src="<s:url value="/icons/glyphicons_065_tag.png" />" /></td>
						<td><sf:label path="paperId">PaperId:</sf:label></td>
						<td><sf:input path="paperId" readonly="true" size="50" />
					</tr>

					<tr>
						<td><img
							src="<s:url value="/icons/glyphicons_024_parents.png" />" /></td>

						<td><sf:label path="authorIds">Authors:</sf:label></td>
						<td><sf:select path="authorIds" items="${paper.authorIds}"
								name="feldauthorID">
							</sf:select> <INPUT TYPE="button" VALUE="edit" onClick="authorPopup('${paper.paperId}')"></td>

						<!-- 						<INPUT NAME="feldauthorID" TYPE="TEXT" VALUE=""> -->
						<!-- 						<td><label>Authors:</label></td> -->

						<%-- 						<td><c:forEach items="${paper.getAuthors()}" var="author"> --%>
						<%-- 								<c:if test="${author}"> --%>
						<%-- 									<a href="/author/${author}">${author}</a> --%>
						<%-- 								</c:if> --%>
						<%-- 								<c:choose> --%>
						<%-- 									<c:when test="test=${author}"> --%>




						<%-- 										<a href="/author/${author}">${author}</a> --%>
						<%-- 									</c:when> --%>

						<%-- 									<c:otherwise> --%>
						<!-- 																				<INPUT NAME="feldauthorID" TYPE="TEXT" VALUE=""> -->
						<!-- 																				<INPUT TYPE="button" VALUE="+" -->
						<!-- 																					onClick="openChild('authorpopup','win2')"> -->

						<%-- 									</c:otherwise> --%>
						<%-- 								</c:choose> --%>
						<%-- 							</c:forEach></td> --%>
					</tr>

					<tr>
						<td><img
							src="<s:url value="/icons/glyphicons_054_clock.png" />" /></td>
						<td><sf:label path="uploadDate">Upload Date:</sf:label></td>
						<td><sf:input path="uploadDate" readonly="true" size="50" /></td>
					</tr>

					<tr>
						<td><img
							src="<s:url value="/icons/glyphicons_030_pencil.png" />" /></td>
						<td><sf:label path="title">Title:</sf:label></td>
						<td><sf:input path="title" size="50" /></td>
					</tr>

					<tr>
						<td><img
							src="<s:url value="/icons/glyphicons_045_calendar.png" />" /></td>
						<td><sf:label path="createDate">Creation Date:</sf:label></td>
						<td><sf:input path="createDate" size="50" /></td>
					</tr>

					<tr>
						<td><img
							src="<s:url value="/icons/glyphicons_319_sort.png" />" /></td>
						<td><sf:label path="kindOf">Categories:</sf:label></td>
						<td><sf:input path="kindOf" size="50" /></td>
					</tr>

					<tr>
						<td><img
							src="<s:url value="/icons/glyphicons_036_file.png" />" /></td>
						<td><sf:label path="fileName">Filename:</sf:label></td>
						<td><sf:input path="fileName" size="50" /></td>
					</tr>

					<tr>
						<td><img
							src="<s:url value="/icons/glyphicons_110_align_left.png" />" /></td>
						<td><sf:label path="paperAbstract">Abstract:</sf:label></td>
						<td><sf:textarea path="paperAbstract" /></td>
					</tr>

					<tr>
						<td><img
							src="<s:url value="/icons/glyphicons_200_download.png" />" /></td>
						<td><sf:label path="paperAbstract">Download</sf:label></td>
						<td><a
							href="${pageContext.request.contextPath}/paper/download?paperId=${ paper.getPaperId()}">Klick
								here to start download</a></td>
					</tr>
					<tr>
						<td><img
							src="<s:url value="/icons/glyphicons_016_bin.png" />" /></td>
						<td><sf:label path="paperAbstract">Delete</sf:label></td>
						<td><a
							href="${pageContext.request.contextPath}/paper/delete?paperId=${ paper.getPaperId()}"
							onclick="return deletechecked();">Klick here to delete paper</a></td>
					</tr>



				</table>

				<input id='save-button' type="submit" value="Save" />
			</sf:form>

		</div>
		<div id="footer" class="span-24 ">
			<p></p>
		</div>
	</div>
</body>
<script type="text/javascript">
	function deletechecked() {
		var answer = confirm("Delete paper ?");
		if (answer) {
			document.messages.submit();
		}

		return false;
	}

	function authorPopup(id) {
		window.open("/macpaperweb/paper/editListOfAuthors?id=" + id,
				"myWindow",
				"status = 1, height = 300, width = 300, resizable = 0");
	}
</script>
</html>