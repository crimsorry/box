<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="https://kit.fontawesome.com/742b89101d.js" crossorigin="anonymous"></script>
<link rel="icon" type="image/png" href="resources/img/board.png">
<link href="<c:url value="/resources/css/board.css" />" rel="stylesheet">
<title>자유게시판</title>
</head>
<body>

<i id="navBar" class="fas fa-bars"></i>

<div class="noneBox">
<div id="navBox">
	<i id="axBox" class="fas fa-times"></i>
	<ul>
		<c:choose>
			<c:when test="${company_name!='관리자'}">
				<li><a href="goUserBox">사용자 박스 요청</a></li>
				<li><a href="boardList">자유게시판</a></li>
			</c:when>
			<c:otherwise>
				<li><a href="main">거래처 관리</a></li>
				<li><a href="boxManagement?num=one">박스 재고 관리</a></li>
				<li><a href="boardList">자유게시판</a></li>
			</c:otherwise>
		</c:choose>
		
		
	</ul>
</div>
</div>

<h5>${company_name}님 로그인 <span><a href="logout">[로그아웃]</a></span></h5>
<h2 id="freeBoard"><a href="boardList">자유게시판</a></h2>


<button id="writeBtn">글쓰기</button>

<div id="free_box">
	<table id="free" border="1">
		<tr>
			<th>게시글번호</th>
			<th>제목</th>
			<th>글쓴이</th>
			<th>작성일</th>
			<th>조회</th>
		</tr>
		<c:set var="i" value="${(currentbtn-1)*20+1}" />
		<c:forEach var="boardList" items="${boardList}">
		<tr onclick="javascript:clickTr('${boardList.board_code}')">
			<td>${i}</td>
			<td>${boardList.title} [${boardList.comment_code}]</td>
			<td>${boardList.id}</td>
			<td>${boardList.dates}</td>
			<td>${boardList.cnt}</td>
		</tr>
		<c:set var="i" value="${i+1}" />
		</c:forEach>
		<c:set var="z" value="${(i-1)%20}" />
		<c:if test="${z>0}">
			<c:forEach var="blankTr" begin="1" end="${20-z}">
				<tr class="noneTr"><td colspan="28"></td></tr>
			</c:forEach>
		</c:if>
	</table>
	<div id="btn_box">
		<c:if test="${currentbtn%3==1}">
			<c:set var="inPage" value="${currentbtn}"></c:set>
		</c:if>
		<c:if test="${currentbtn%3==0}">
			<c:set var="inPage" value="${currentbtn-2}"></c:set>
		</c:if>
		<c:if test="${currentbtn%3==2}">
			<c:set var="inPage" value="${currentbtn-1}"></c:set>
		</c:if>
		<c:set var="lastPage" value="${inPage+2}"></c:set>
		<c:if test="${totalbtn-inPage<3}">
			<c:set var="lastPage" value="${totalbtn-inPage+1}"></c:set>
		</c:if>
		<button class="btns" id="prebtn" onclick="location.href='boardList?btn=${currentbtn-1}'">&lt;</button>
		<c:forEach var="btn" begin="1" end="${lastPage}">
			<button id="btn${inPage}" class="btns" onclick="location.href='boardList?btn=${inPage}'">${inPage}</button>
			<c:set var="inPage" value="${inPage+1}"></c:set>
		</c:forEach>
		<c:if test="${currentbtn!=totalbtn}"><button class="btns" id="pastbtn" onclick="location.href='boardList?btn=${currentbtn+1}'">&gt;</button></c:if>
	</div>
</div>

<!-- 글쓰기 버튼! -->
<form id="writeForm" action="boardWrite" method="post" onsubmit="return reserve()">
	<div id="write_back">
		<div id="write_white">
			<div id="write">
				<h2 class="tion">글쓰기<span><a href="boardList?btn=${currentbtn}">X</a></span></h2>
				<div id="writetitle">
					<p><input type="text" id="title" name="title" maxlength="50"placeholder="제목을 입력해 주세요."></p>
					<p>
						<input type="text" id="id" name="id" maxlength="20" placeholder="닉네임을 입력해주세요.">
						<input type="password" id="password" name="password" maxlength="20" placeholder="비밀번호를 입력해주세요.">
					</p>
				</div>
				<div id="content">
					<p>
						<textarea name="content" id="minecontent" rows="10" cols="100" placeholder="내용을 입력해주세요."></textarea>
						<!-- <input type="text" id="content" name="content" maxlength="500" placeholder="내용을 입력해주세요."> -->
					</p>
				</div>
				<input type="submit" value="입력">
			</div>
		</div>
	</div>
</form>
  
<script src="https://code.jquery.com/jquery-2.2.1.js"></script>

<script>
	

	//상세보기
	function clickTr(board_code){
		location.href = "boardDetail?board_code=" + board_code + "&btn=1"
	}

	//버튼 색
	$(function(){
		if(parseInt('${currentbtn}')==1){
			$("[id=prebtn]").css("visibility", "hidden");
		}
	});
	
	$(function(){
		$("[id=btn${currentbtn}]").css("background-color", "#c32148");
		$("[id=btn${currentbtn}]").css("color", "white");
	});
	
	// 네비게이션 바
	$(function(){
		$("#navBar").click(function(){
			$(".noneBox").css("display", "block");
			$("#navBox").animate({left: "0px", opacity: "1", transition: "all 0.5s ease-in"});
		});
	});
	
	// 네비게이션 x
	$(function(){
		$("#axBox").click(function(){
			$("#navBox").animate({left: "-250px", opacity: "0", transition: "all 0.5s ease-in"});
			$(".noneBox").css("display", "none");
		});
	});
	
	// 글쓰기 버튼 클릭
	$(function(){
		$("#writeBtn").click(function(){
			if($("#write_back").css("display")=="none"){
				$("#write_back").show();
			}else{
				$("#write_back").hide();
			}
		});
	});
	
	var id = document.getElementById("id");
	var password = document.getElementById("password");
	var title = document.getElementById("title");
	var content = document.getElementById("content");
	
	// 예외처리
	function reserve(){
		if(id.value=='' || password.value=='' || title.value=='' || content.value==''){
			alert("빈칸을 입력할 수 없습니다.");
			return false;
		}else{
			return true;
		}
	}

</script>
</body>
</html>






































