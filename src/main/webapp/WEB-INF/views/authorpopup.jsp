<%@ include file="/WEB-INF/views/includes/taglibs.jsp"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ include file="/WEB-INF/views/includes/taglibs.jsp"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>


<!DOCTYPE HTML>
<HTML>
<HEAD>
<SCRIPT LANGUAGE="JavaScript">
	function updateParent() {

// 		if (opener.document.parentForm.feldauthorID.value == "") {
// 			opener.document.parentForm.feldauthorID.value = radioWert(document.listofauthors.myRadio);
// 		} else {
// 			opener.document.parentForm.feldauthorID.value = opener.document.parentForm.feldauthorID.value
// 					+ ", " + radioWert(document.listofauthors.myRadio);
// 		}
		
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
</SCRIPT>
</HEAD>
<BODY>
	<div id="content" class="span-24 last">
		<form name="listofauthors">

			<c:forEach var="author" items="${authors}" varStatus="status">
				<c:if test="${not empty author.getAuthorId()}">

					<input type="radio" name="myRadio" value=${ author.getAuthorId()}>${ author.getName()}<BR>
				</c:if>
			</c:forEach>

			<input type="button" value="Auswählen" onClick="updateParent();">
		</form>

	</div>
</BODY>
</HTML>