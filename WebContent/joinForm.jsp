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
	$(function() {
		/* ajax 로 form data 전송 */
		// memberForm 요소에 submit 이벤트가 발생했을때 ajax 요청 만들기
		$("#memberForm").on("submit" , function() {
			// 폼 요소에 있는 데이터를 서버로 전송
			// 폼 요소의 input을 전송가능한 상태로 변경
			// this ==> 이벤트 발생 객체
			// serialize() => jquery에서 제공하는 함수
			let data = $(this).serialize();
			alert("data : " + data);
			$.ajax({
				url: "member",
				type: "get",
				data : data,
				dataType : "json",
				success : function(data) {
					alert(data.result);
				}
			})
			return false; // 기본적으로 form 에 달려있던 submit이벤트 진행을 멈춤
		})
		// blur 이벤트는 요소에 포커싱이 해제 되었을때 발생하는 이벤트
		$("#userid").on("blur" , function() {
			alert("이벤트 발생!");
			$.ajax({
				url : "member?action=checkId",
				type : "post",
				data: {"userid" : $(this).val()},
				dataType: "json",
				success : function(data) {
					let result = "";
					if(data.result) {
						// 생성가능 아이디
						result = "사용 가능 아이디";
					} else {
						// 중복 아이디
						result = "이미 사용중인 아이디";
					}
					$("#idResult").text(result);
				}
			})
		});
		
		$("#email").on("blur" , function() {
			$.ajax({
				url : "member?action=checkEmail",
				type : "post",
				data : {"email" : $(this).val() },
				dataType: "json",
				success: function(data) {
					let result = "";
					if(data.result) {
						result = "사용 가능한 이메일";
					} else {
						result = "이미 사용중인 이메일";
					}
					$("#emailResult").text(result);
				}
			})
		})
	});
	
	function loadMembers() {
		let listDiv = $("#listTable");
		
		$.ajax({
			url : "member?action=memberList",
			type : "get",
			dataType : "json",
			success: function(data) {
				for(let i in data){
					let member = $("<div>"); // 새로운 문서 객체 생성
					console.log(data[i].name);
					member.text(data[i].name);
					listDiv.append(member);
				}
			}
		})
	}
	
</script>
<body>
	<form id="memberForm" >
	<fieldset>
		<legend>회원가입</legend>
		<p>아이디 : <input type="text" name="userid" id="userid">
		<small><span id="idResult"></span></small></p>
		<p>비밀번호 : <input type="password" name="password"></p>
		<p>이름 : <input type="text" name="name"></p>
		<p>이메일 : <input type="text" name="email" id="email">
		<small><span id="emailResult"></span></small></p>
		<input type="hidden" value="join" name="action">
	</fieldset>
	<input type="submit">
	</form>
	
	<h2>회원 목록</h2>
	<div id="listTable" border="1">
		
	
	</div>
	<button onclick="loadMembers()">회원 정보 불러오기</button>
</body>
</html>