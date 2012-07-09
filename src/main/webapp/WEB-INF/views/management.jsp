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
	</div>
	<div class="container">

		<div id="header"></div>
		<div id="authors">

			<table>

				<c:forEach var="author" items="${authors}" varStatus="status">
					<c:if test="${not empty author.getAuthorId()}">
						<tr>
							<td>${ author.getAuthorId()}</td>
							<td>${ author.getName()}</td>
							<td><a
								href="${pageContext.request.contextPath}/author/${ author.getAuthorId()}">+</a></td>
						</tr>
					</c:if>
				</c:forEach>

			</table>


			<button id='new-author' type='button'
				onClick="location.href='${pageContext.request.contextPath}/author/createAuthor'">
				<span>New Author</span>
			</button>
		</div>

		<div id="universities">

			<table>

				<c:forEach var="university" items="${universities}"
					varStatus="status">
					<c:if test="${not empty university.getUniversityId()}">
						<tr>
							<td>${university.getUniversityId()}</td>
							<td>${university.getName()}</td>
							<td><a
								href="${pageContext.request.contextPath}/university/${university.getUniversityId()}">+</a></td>
						</tr>
					</c:if>
				</c:forEach>

			</table>

			<button id='new-university' type='button'
				onClick="location.href='${pageContext.request.contextPath}/university/createUniversity'">
				<span>New University</span>
			</button>
		</div>

		<div id="papers">

			<table>

				<c:forEach var="paper" items="${papers}" varStatus="status">
					<c:if test="${not empty paper.getPaperId()}">
						<tr>
							<td>${paper.getPaperId()}</td>
							<td>${paper.getTitle()}</td>
							<td><a
								href="${pageContext.request.contextPath}/paper/${paper.getPaperId()}">+</a></td>
						</tr>
					</c:if>
				</c:forEach>

			</table>

			<button id='new-paper' type='button'
				onClick="location.href='${pageContext.request.contextPath}/paper/upload'">
				<span>New Paper</span>
			</button>

		</div>


		<!-- 		<div id="header" class="prepend-1 span-22 append-1 last"> -->
		<div id="content" class="span-24 last"></div>
		<div id="footer" class="span-24 ">
			<p></p>
		</div>
	</div>
</body>
</html>
