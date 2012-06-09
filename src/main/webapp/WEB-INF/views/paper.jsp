<%@ include file="/WEB-INF/views/includes/taglibs.jsp"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html>
<html>
<head>
<title>MacPaper ResultPage</title>
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
			<h1>Detailed Information</h1>
			<h2>about the paper ${recievedPaperName}</h2>
		</div>
		<a href="javascript:history.back()">back</a>
		<sf:form modelAttribute="paper" method="POST"
			action="${pageContext.request.contextPath}/paper/update"
			enctype="multipart/form-datahast ">
			<table>
				<tr>
					<td><sf:label path="paperId">PaperId:</sf:label></td>
					<td><sf:input path="paperId" readonly="true" size="50" /></td>
				</tr>

				<tr>
					<td><sf:label path="uploadDate">Upload Date:</sf:label></td>
					<td><sf:input path="uploadDate" readonly="true" size="50" /></td>
				</tr>

				<tr>
					<td><sf:label path="title">Title:</sf:label></td>
					<td><sf:input path="title" size="50" /></td>
				</tr>

				<tr>
					<td><sf:label path="createDate">Creation Date:</sf:label></td>
					<td><sf:input path="createDate" size="50" /></td>
				</tr>

				<tr>
					<td><sf:label path="kindOf">Categories:</sf:label></td>
					<td><sf:input path="kindOf" size="50" /></td>
				</tr>

				<tr>
					<td><sf:label path="fileName">Filename:</sf:label></td>
					<td><sf:input path="fileName" size="50" /></td>
				</tr>

				<tr>
					<td><sf:label path="paperAbstract">Abstract:</sf:label></td>
					<td><sf:textarea path="paperAbstract" /></td>
				</tr>



			</table>

			<input type="submit" value="Save" />
		</sf:form>

		<%-- 		<sf:form modelAttribute="paper" method="POST" action="update"> --%>
		<!-- 			<table border="1"> -->
		<!-- 				<tr> -->
		<!-- 					<th>Key</th> -->
		<!-- 					<th>value</th> -->
		<!-- 				</tr> -->
		<!-- 				<tr> -->
		<!-- 					<td>Paper Filename</td> -->
		<%-- 					<td>${paper.getFileName()}</td> --%>
		<!-- 				</tr> -->
		<!-- 				<tr> -->
		<!-- 					<td>Paper ID</td> -->
		<%-- 					<td>${paper.getPaperId()}</td> --%>
		<!-- 				</tr> -->
		<!-- 				<tr> -->
		<!-- 					<td>Paper Title</td> -->
		<%-- 					<td><sf:input path="title" size="20" /></td> --%>
		<!-- 											<td><input type="text" name="title" size="20" -->
		<%-- 												value="${paper.getTitle()}"></td> --%>
		<!-- 				</tr> -->
		<!-- 				<tr> -->
		<!-- 					<td>Paper Creation Date</td> -->
		<%-- 					<td>${paper.getCreateDate()}</td> --%>
		<!-- 				</tr> -->
		<!-- 				<tr> -->
		<!-- 					<td>Paper Abstract</td> -->
		<%-- 					<td>${paper.getPaperAbstract()}</td> --%>
		<!-- 				</tr> -->
		<!-- 			</table> -->
		<!-- 			<input type="submit" value="save"> -->
		<%-- 		</sf:form> --%>
	</div>
</body>
</html>