<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no, maximum-scale=1.0, minimum-scale=1.0">
<link rel="icon" type="image/png" href="resources/img/PIcon.png">
<script src="https://kit.fontawesome.com/742b89101d.js" crossorigin="anonymous"></script>
<link rel="icon" type="image/png" href="resources/img/board.png">
<link href="<c:url value="/resources/css/userBox.css" />" rel="stylesheet">
<title>사용자 박스 요청</title>
</head>
<body>

<i id="navBar" class="fas fa-bars"></i>
<i id="navMidiBar" class="fas fa-bars"></i>

<div class="noneBox">
<div id="navBox">
	<i id="axBox" class="fas fa-times"></i>
	<ul>
		<li><a href="goUserBox">사용자 박스 요청</a></li>
		<li><a href="boardList">자유게시판</a></li>
	</ul>
</div>
<div id="navMidiBox">
	<i id="axMidiBox" class="fas fa-times"></i>
	<ul>
		<li><a href="goUserBox">사용자 박스 요청</a></li>
		<li><a href="boardList">자유게시판</a></li>
	</ul>
</div>
</div>

<form action="ordered" method="post" onsubmit="return reserve()">
<h5>${company_name}님 로그인 <span><a href="logout">[로그아웃]</a></span></h5>
<div id="box">
	<h2>사용자 박스 요청</h2>
	<select id="box_code" name="box_code"> 
		<c:forEach var="boxList" items="${boxList}">
		<c:choose>
			<c:when test="${boxList.remain_cnt=='0'}">
				<option value="${boxList.box_code}" disabled>${boxList.box_type}(${boxList.box_standard}) (재고: ${boxList.remain_cnt})</option>
			</c:when>
			<c:when test="${boxList.box_code=='B1'}">
				<option value="${boxList.box_code}" selected>${boxList.box_type}(${boxList.box_standard}) (재고: ${boxList.remain_cnt})</option>
			</c:when>
			<c:when test="${boxList.box_code!='B1'}">
				<option value="${boxList.box_code}" >${boxList.box_type}(${boxList.box_standard}) (재고: ${boxList.remain_cnt})</option>
			</c:when>
		</c:choose>
        </c:forEach>
	</select>
	<input type="text" id="order_cnt" name="order_cnt" placeholder="수량 입력">
	<input type="date" id="require_date" name="require_date" min="${tomorrow}" value="${tomorrow}" placeholder="받을 날짜 입력">
	<button>입력</button>
</div>

</form>

<script src="https://code.jquery.com/jquery-2.2.1.js"></script>
<script>

	var mql = window.matchMedia("screen and (max-width: 360px)");
	var navBar = document.getElementById("navBar");
	var navBox = document.getElementById("navBox");
	var axBox = document.getElementById("axBox");
	var noneBox = document.querySelector(".noneBox");
	
	$(function(){
		$("#navBar").click(function(){
			console.log(1);
			$(".noneBox").css("display", "block");
			$("#navBox").css("display", "block");
			$("#navMidiBar").css("display", "none");
			$("#navMidiBox").css("display", "none");
			$("#navBox").animate({left: "0px", top: "0px", opacity: "1", transition: "all 0.5s ease-in"});
		});
	});
	
	// 네비게이션 x
 	$(function(){
		$("#axBox").click(function(){
			console.log(2);
			$(".noneBox").css("display", "none");
			$("#navBox").css("display", "none");
			$("#navBox").animate({left: "-250px", top: "0px", opacity: "0", transition: "all 0.5s ease-in"});
			
		});
	}); 
	
	// 모바일 네비게이션
 	mql.addListener(function(e) {
		if(e.matches) {
			$("#navMidiBar").css("display", "block");
			$(".noneBox").css("display", "none");
			$("#navBox").css("display", "none");
	 		$(function(){
	 			$("#navMidiBar").click(function(){
	 				console.log(3);
	 				$(".noneBox").css("display", "block");
	 				$("#navBox").css("display", "none");
	 				
	 				$("#navMidiBox").css("display", "block");
	 				$("#navMidiBox").animate({top: "0px", left: "0px", opacity: "1", transition: "all 0.5s ease-in"});
	 			});
	 		});
	 		
	 		// 네비게이션 x
	 	 	$(function(){
	 			$("#axMidiBox").click(function(){
	 				console.log(4);
	 				$(".noneBox").css("display", "none");
	 				$("#navBox").css("display", "none");
	 				$("#navMidiBox").css("display", "none");
	 				$("#navMidiBox").animate({top: "-250px", left: "0px", opacity: "0", transition: "all 0.5s ease-in"});
	 				
	 			});
	 		});
 		}else{
 			$("#navMidiBar").css("display", "none");
 			$(".noneBox").css("display", "none");
			$("#navBox").css("display", "none");
 		}
 	});

	// 메세지 보여주기
	if(parseInt('${see}')==1){
		alert('${msg}');
	}

	var box_code = document.getElementById("box_code");
	var order_cnt = document.getElementById("order_cnt");
	var require_date = document.getElementById("require_date");
	
	var num = /^[0-9]*$/;
	var date_pattern = /^(19|20)\d{2}-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[0-1])$/; 

	//예외처리
	function reserve(){
		var result = box_code.options[box_code.selectedIndex].text;
		var sp = result.split(" ");
		var remain = parseInt(sp[2].slice(0, -1));
		
		if(!num.test(order_cnt.value)){
			alert("숫자만 입력할 수 있습니다.");
			return false;
		}else if(order_cnt.value>remain){
			alert("재고량 이상의 값은 입력 하실 수 없습니다.");
			return false;
		}else if(!date_pattern.test(require_date.value)){
			alert("날짜 형식에 맞게 입력해주세요!");
			return false;
		}else if(require_date.value<='${now}'){
			alert("오늘 날짜 이후만 요청 가능합니다!");
			return false;
		}else if(order_cnt.value=='' || require_date.value==''){
			alert("빈칸을 입력할 수 없습니다.");
			return false;
		}else{
			return true;
		}
	}

</script>
</body>
</html>

























