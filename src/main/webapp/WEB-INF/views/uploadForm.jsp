<%@ include file="/WEB-INF/views/includes/taglibs.jsp"%>

<!DOCTYPE HTML>

<form:form modelAttribute="uploadItem" method="post"
	enctype="multipart/form-data">
	<fieldset>
		<legend>Upload Fields</legend>

		<p>
			<form:label for="name" path="name">Name</form:label>
			<br />
			<form:input path="name" />
		</p>

		<p>
			<form:label for="fileData" path="fileData">File</form:label>
			<br />
			<form:input path="fileData" type="file" />
		</p>

		<p>
			<input type="submit" />
		</p>

	</fieldset>
</form:form>