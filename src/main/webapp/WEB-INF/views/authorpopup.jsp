<%@ include file="/WEB-INF/views/includes/taglibs.jsp"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>


<!DOCTYPE HTML>
<HTML>
<HEAD>
<SCRIPT LANGUAGE="JavaScript">
	function updateParent() {
		opener.document.parentForm.pf1.value = document.childForm.cf1.value;
		opener.document.parentForm.pf2.value = document.childForm.cf2.value;
		self.close();
		return false;
	}
</SCRIPT>
</HEAD>
<BODY>
	<FORM NAME="childForm" onSubmit="return updateParent();">
		<BR> <INPUT NAME="cf1" TYPE="TEXT" VALUE=""> <BR> <INPUT
			NAME="cf2" TYPE="TEXT" VALUE=""> <BR> <INPUT
			TYPE="SUBMIT" VALUE="Update parent">
	</FORM>
</BODY>
</HTML>