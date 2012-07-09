<%@ include file="/WEB-INF/views/includes/taglibs.jsp"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>


<!DOCTYPE HTML>
<HTML>
<HEAD></HEAD>

<SCRIPT LANGUAGE="JavaScript">
	function openChild(file, window) {
		childWindow = open(file, window, 'resizable=no,width=200,height=400');
		if (childWindow.opener == null)
			childWindow.opener = self;
	}
</SCRIPT>

<BODY>
	<FORM NAME="parentForm">
		<INPUT TYPE="button" VALUE="Open child"
			onClick="openChild('authorpopup','win2')"> <BR> <INPUT
			NAME="pf1" TYPE="TEXT" VALUE=""> <BR> <INPUT NAME="pf2"
			TYPE="TEXT" VALUE="">
	</FORM>
</BODY>
</HTML>