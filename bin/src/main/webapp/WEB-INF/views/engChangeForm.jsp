<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="icon" type="image/png" href="resources/img/PIcon.png">
<title>영문 변환</title>
<style>
	#addressEng{ width: 350px; }
</style>
</head>
<body>
<h1>영문 주소 번역기</h1>
<input type="text" id="addressEng" value="${english }" maxlength="100">
<input type="button" value="전달하기" onclick="setParentText()">
<input type="button" value="창닫기" onclick="window.close()">

<script>
var addressEng = document.getElementById("addressEng");

var engNum = /^[a-zA-Z0-9| |,|''-|.]*$/;

function setParentText(){
	if(addressEng.value==""){
		alert("올바른 영문 주소를 입력해주세요.");
		addressEng.focus();
	}else if(!engNum.test(addressEng.value)){
		alert("영어와 숫자만 입력가능합니다.");
	}else{
		// opener.document.getElementById("부모 인풋 ID 이름").value = document.getElementById("자식 인풋 ID 이름").value 
		opener.document.getElementById("address_eng").value = addressEng.value;
	}
}
</script>
</body>
</html>