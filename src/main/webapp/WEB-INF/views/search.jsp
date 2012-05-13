<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Result Page</title>
</head>
<body>
<h1>Search Term: ${searchTerm}</h1>
<p>Document Score: ${documentScore}</p>
<p>Document ID: ${documentId}</p>
<p>Total Hits: ${totalHits}</p>
<p>Max Score: ${maxScore}</p>

<p>Document Key: ${documentKey}</p>
</body>
</html>