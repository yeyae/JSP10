<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<script src="https://code.jquery.com/jquery-3.6.0.js"
	integrity="sha256-H+K7U5CnXl1h5ywQfKtSj8PCmoN9aaq30gDh27Xc0jk="
	crossorigin="anonymous"></script>
<script>
	function doAjax() {
		let d = "Hello Server!"; // 서버에 파라미터로 전달할 메세지
		$.ajax({
			url : "hello",
			type : "post",
			data : {"d" : d},
			dataType: "json",
			success: function(data) {
				alert(data.msg);
			}
		})
	}
</script>
<body>
	<button onclick="doAjax()">요청</button>
</body>
</html>