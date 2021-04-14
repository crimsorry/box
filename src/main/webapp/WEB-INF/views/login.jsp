<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no, maximum-scale=1.0, minimum-scale=1.0">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="icon" type="image/png" href="resources/img/board.png">
<link href="<c:url value="/resources/css/login.css" />" rel="stylesheet">
<title>로그인</title>
</head>
<body>


<form action="loginChk" method="post" onsubmit="return reserve()">
<div id="login">
	<h2>Login</h2>
	<input type="text" id="id" name="id" placeholder="아이디">
	<input type="password" id="password" name="password" placeholder="비밀번호">
	<button>로그인</button>
</div>
</form>

<script src="https://code.jquery.com/jquery-2.2.1.js"></script>
<script>

	var id = document.getElementById("id");
	var password = document.getElementById("password");

	//예외처리
	function reserve(){
		if(id.value=='' || password.value==''){
			alert("빈칸을 입력할 수 없습니다.");
			return false;
		}else{
			return true;
		}
	}

</script>
</body>
</html>







































