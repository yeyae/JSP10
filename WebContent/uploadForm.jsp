<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>파일 업로드</title>
</head>
<body>
	<!-- 파일 업로드시 form 에 multipart 타입 데이터를 사용한다고 명시 -->
	<form action="upload" method="post" enctype="multipart/form-data">
	file : <input type="file" name="uploadFile"><br>
	<input type="submit" value="전송">
	</form>
</body>
</html>