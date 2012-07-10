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
<!-- Plugin CSS -->
<link rel="stylesheet" href="/blueprint-plugins/tabs/screen.css"
	type="text/css" media="screen">
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

		<!-- 		<div id="header"></div> -->
		<div id="authors">
			<h2>List of Authors</h2>
			<table>

				<tr>
					<th width="50%">Name</th>
					<th width="40%">Id</th>
					<th width="5%"></th>
					<th width="5%"></th>
				</tr>
				<c:forEach var="author" items="${authors}" varStatus="status">
					<c:if test="${not empty author.getAuthorId()}">
						<tr>
							<td>${ author.getName()}</td>
							<td>${ author.getAuthorId()}</td>
							<td><a
								href="${pageContext.request.contextPath}/author/${ author.getAuthorId()}"><img
									src="<s:url value="/icons/glyphicons_195_circle_info.png" />" />
							</a></td>
							<td><a
								href="${pageContext.request.contextPath}/author/delete?authorId=${author.getAuthorId()}"><img
									src="<s:url value="/icons/glyphicons_016_bin.png" />" /> </a></td>
						</tr>
					</c:if>
				</c:forEach>
				<tr>
					<td>
						<button id='new-author' type='button'
							onClick="location.href='${pageContext.request.contextPath}/author/createAuthor'">
							<span>New Author</span>
						</button>
					</td>
					<td></td>
					<td></td>
					<td></td>
				</tr>


			</table>



		</div>

		<div id="universities">
			<h2>List of Universities</h2>


			<table>
				<tr>
					<th width="50%">Name</th>
					<th width="40%">Id</th>
					<th width="5%"></th>
					<th width="5%"></th>
				</tr>
				<c:forEach var="university" items="${universities}"
					varStatus="status">
					<c:if test="${not empty university.getUniversityId()}">
						<tr>
							<td>${university.getName()}</td>
							<td>${university.getUniversityId()}</td>
							<td><a
								href="${pageContext.request.contextPath}/university/${ university.getUniversityId()}"><img
									src="<s:url value="/icons/glyphicons_195_circle_info.png" />" />
							</a></td>
							<td><a
								href="${pageContext.request.contextPath}/university/delete?universityId=${university.getUniversityId()}"><img
									src="<s:url value="/icons/glyphicons_016_bin.png" />" /> </a></td>
						</tr>
					</c:if>
				</c:forEach>
				<tr>
					<td>
						<button id='new-university' type='button'
							onClick="location.href='${pageContext.request.contextPath}/university/createUniversity'">
							<span>New University</span>
						</button>
					</td>
					<td></td>
					<td></td>
					<td></td>
				</tr>

			</table>


		</div>

		<div id="papers">
			<h2>List of Papers</h2>

			<table>
				<tr>
					<th width="50%">Name</th>
					<th width="40%">Id</th>
					<th width="5%"></th>
					<th width="5%"></th>
				</tr>
				<c:forEach var="paper" items="${papers}" varStatus="status">
					<c:if test="${not empty paper.getPaperId()}">
						<tr>
							<td>${paper.getTitle()}</td>
							<td>${paper.getPaperId()}</td>
							<td><a
								href="${pageContext.request.contextPath}/paper/${  paper.getPaperId()}"><img
									src="<s:url value="/icons/glyphicons_195_circle_info.png" />" />
							</a></td>
							<td><a
								href="${pageContext.request.contextPath}/paper/delete?paperId=${ paper.getPaperId()}"><img
									src="<s:url value="/icons/glyphicons_016_bin.png" />" /> </a></td>
						</tr>
					</c:if>
				</c:forEach>
				<tr>
					<td>

						<button id='new-paper' type='button'
							onClick="location.href='${pageContext.request.contextPath}/upload'">
							<span>New Paper</span>
						</button>
					</td>
					<td></td>
					<td></td>
					<td></td>
				</tr>
			</table>


		</div>


		<!-- 		<div id="header" class="prepend-1 span-22 append-1 last"> -->
		<div id="content" class="span-24 last"></div>
		<div id="footer" class="span-24 ">
			<p></p>
		</div>
	</div>
</body>
</html>
