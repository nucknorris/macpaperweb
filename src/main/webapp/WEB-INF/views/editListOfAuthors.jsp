<%@ include file="/WEB-INF/views/includes/taglibs.jsp"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ include file="/WEB-INF/views/includes/taglibs.jsp"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>


<!DOCTYPE HTML>
<HTML>
<HEAD>
</HEAD>
<BODY>
	<div id="content" class="span-24">
		<form name="listofauthors">
			<c:forEach var="paper" items="${paper.authorIds}" varStatus="status">
				${ author.getAuthorId()} - ${ author.name}
			</c:forEach>

			<!-- 			<input type="button" value="Auswählen" onClick="updateParent();"> -->
		</form>

	</div>
</BODY>
</HTML>