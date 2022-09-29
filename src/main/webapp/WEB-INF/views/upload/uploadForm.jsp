<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>UploadForm</title>
</head>
<body>

	<h2> File Upload </h2>
	<form action="/upload/uploadPro" method="post" enctype="multipart/form-data">
		message : <input type="text" name="msg" /> <br />
		file	: <input type="file" name="img" /> <br />
				  <input type="submit" value="전송" /> <br />
	</form>



</body>
</html>