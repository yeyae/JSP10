<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<script src="https://code.jquery.com/jquery-3.6.0.js" integrity="sha256-H+K7U5CnXl1h5ywQfKtSj8PCmoN9aaq30gDh27Xc0jk=" crossorigin="anonymous"></script>
<script>
	$(document).ready(function() {
		$.ajax({
			url : "document.txt",
			type : "get",
			dataType : "text",
			success : function(data) {
				alert(data);
				$("#d1").html(data);
			}
		})
	})
</script>
<body>
	<div id="d1">
	</div>
</body>
</html>