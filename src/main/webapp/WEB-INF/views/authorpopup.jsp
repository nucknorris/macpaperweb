<%@ include file="/WEB-INF/views/includes/taglibs.jsp"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ include file="/WEB-INF/views/includes/taglibs.jsp"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>


<!DOCTYPE HTML>
<HTML>
<HEAD>
<link
	href='http://fonts.googleapis.com/css?family=Open+Sans:400,600,700'
	rel='stylesheet' type='text/css'>
<link rel="stylesheet" href="<c:url value='/css/blueprint/screen.css'/>"
	type="text/css" media="screen, projection">
<link rel="stylesheet" href="<c:url value='/css/blueprint/print.css'/>"
	type="text/css" media="print">
<link rel="stylesheet" href="<c:url value='/css/main.css'/>"
	type="text/css">
<script>
	function updateParent() {

		var oSelField = opener.document.getElementById("selectField");
		var oOption = opener.document.createElement("OPTION");
		oSelField.options.add(oOption);
		oOption.text = radioWert(document.listofauthors.myRadio);
		oOption.value = radioWert(document.listofauthors.myRadio);

		self.close();
		return false;
	}
	function radioWert(rObj) {
		for ( var i = 0; i < rObj.length; i++)
			if (rObj[i].checked)
				return rObj[i].value;
		return false;
	}

	function createNewAuthor() {
		opener.window.open('/macpaperweb/author/createAuthor', '_blank');
		// 		opener.location.href = '/macpaperweb/author/createAuthor';

		self.close();
		return false;
	}
</script>
</HEAD>
<BODY>
	<!-- 	<div id="content" class="span-24 last"> -->
	<div id="head">
		<h3>Choose the author you want to add</h3>
	</div>
	<form name="listofauthors">
		<table>
			<tr>
				<td></td>
				<td>Name</td>
				<td>Lastname</td>
				<td>ID</td>
			</tr>

			<c:forEach var="author" items="${authors}" varStatus="status">

				<tr>
					<td><input type="radio" name="myRadio"
						value=${ author.getAuthorId()}></td>
					<td>${ author.getName()}</td>
					<td>${ author.getLastname()}</td>
					<td>${ author.getAuthorId()}</td>
				</tr>

				<%-- 				<c:if test="${not empty author.getAuthorId()}"> --%>

				<%-- 					<input type="radio" name="myRadio" value=${ author.getAuthorId()}>Name: ${ author.getName()}, ID: ${ author.getAuthorId()}<BR> --%>
				<%-- 				</c:if> --%>
			</c:forEach>

			<tr>
				<td></td>
				<td><input type="button" value="Add" onClick="updateParent();"></td>
				<td></td>
			</tr>
		</table>
	</form>
	<form name="newauthor">
		<input type="button" value="Create new author"
			onClick="createNewAuthor();">
	</form>


	<!-- 	</div> -->
</BODY>
</HTML>