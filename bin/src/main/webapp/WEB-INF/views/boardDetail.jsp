<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="https://kit.fontawesome.com/742b89101d.js" crossorigin="anonymous"></script>
<script type="text/javascript" src="../resources/js/service/HuskyEZCreator.js" charset="utf-8"></script>
<link rel="icon" type="image/png" href="resources/img/board.png">
<link href="<c:url value="/resources/css/boardDetail.css" />" rel="stylesheet">
<title>상세보기</title>
</head>
<body>

<h2><button id="back" onclick="location.href='boardList'">뒤로 가기</button><label>상세글보기</label></h2>

<div id="free_box">
	<div id="title">
		<p>${boardDetail.title}</p>
		<p><span>${boardDetail.id}</span> | <span>${boardDetail.write_date}</span> <span id="span"><span>조회 ${boardDetail.cnt}</span> | <span>댓글 ${commentCnt}</span></span></p>
	</div>
	<div id="content">
		<p>${boardDetail.content}</p>
	</div>
	
	<button id="del" onclick="del()">삭제하기</button>
	<button id="fix" onclick="fix()">수정하기</button>
	
	<!-- 댓글쓰기 -->
	<form action="writeComment?board_code=${board_code}" method="post" onsubmit="return reserve()">
	<div class="write">
		<div id="left">
			<input type="text" id="comment_id" name="comment_id" placeholder="닉네임">
			<input type="password" id="comment_password" name="comment_password" placeholder="비밀번호">
		</div>
		<div id="right">
			<input type="text" id="comment_content" name="comment_content">
		</div>
		<button id="commitSubmit">등록</button>
	</div>
	</form>
	
	<!-- 전체 댓글 -->
	<div id="comment">
		<p>전체 댓글 ${commentCnt}개</p>
		<c:choose>
	    	<c:when test="${commentCnt==0}">
				<p>작성된 댓글이 존재하지 않습니다.</p>
			</c:when>
	    	<c:otherwise>
				<div id="re_comment">
					<c:set var="i" value="1"></c:set>
					<c:forEach var="commentDetail" items="${commentDetail}">
					<c:choose>
						<c:when test="${commentDetail.re_level>1}"><div class="inComment inNopeComment" id="see${i}"></c:when>
						<c:otherwise><div class="inComment" id="see${i}"></c:otherwise>
					</c:choose>
							<c:choose>
						    	<c:when test="${commentDetail.chk==2}">
									<label>
										<c:if test="${commentDetail.re_step>1}">
											<c:forEach var="j" begin="1" end="${commentDetail.re_step}">
												 &nbsp;
											</c:forEach>
											ㄴ&nbsp;
										</c:if>
										삭제된 댓글입니다.
									</label><br>
								</c:when>
						    	<c:otherwise>
									<input type="hidden" id="code${i}" value="${commentDetail.comment_code}">
									<input type="hidden" id="pass${i}" value="${commentDetail.comment_password}">
									<input type="hidden" id="group${i}" value="${commentDetail.re_group}">
									<c:if test="${commentDetail.re_step>1}">
										<c:forEach var="j" begin="1" end="${commentDetail.re_step}">
											&nbsp;
										</c:forEach>
										<label>ㄴ</label>&nbsp;
									</c:if>
									<span>${commentDetail.comment_id}</span>
									<span id="con" onclick="seeComment(${i})">${commentDetail.comment_content}</span>
									<span><i class="fas fa-times-circle" onclick="axbtn(${i})"></i></span>
									<span><i class="fas fa-pen-square" onclick="rebtn(${i})"></i></span>
									<span>${commentDetail.comment_write_date}</span>
								</c:otherwise>
						    </c:choose>
						</div>
					<!-- 수정하기 -->
					<form action="rewriteComment?comment_code=${commentDetail.comment_code}&board_code=${board_code}" method="post" onsubmit="return re_reserve(${i})">
						<div class="write re" id="re${i}">
							<div id="releft">
								<input type="text" id="recomment_id${i}" name="comment_id" value="${commentDetail.comment_id}" readonly>
								<input type="password" id="recomment_password${i}" name="comment_password" placeholder="비밀번호">
							</div>
							<div id="reright">
								<input type="text" id="recomment_content" id="recomment_content${i}" name="comment_content" value="${commentDetail.comment_content}">
							</div>
							<button id="recommitSubmit">등록</button>
						</div>
					</form>
					<!-- 대댓글 달기 -->
					<form action="rereComment?comment_code=${commentDetail.comment_code}&board_code=${board_code}&re_group=${commentDetail.re_group}&re_level=${commentDetail.re_level}&re_step=${commentDetail.re_step}" method="post" onsubmit="return rere_reserve(${i})">
						<div class="write rere" id="rere${i}">
							<div id="rereleft">
								<input type="text" id="rerecomment_id${i}" name="comment_id" placeholder="닉네임">
								<input type="password" id="rerecomment_password${i}" name="comment_password" placeholder="비밀번호">
							</div>
							<div id="reright">
								<input type="text" id="rerecomment_content" id="rerecomment_content${i}" name="comment_content">
							</div>
							<button id="rerecommitSubmit">등록</button>
						</div>
					</form>
					<c:set var="i" value="${i+1}"></c:set>
					</c:forEach>
				</div>
			</c:otherwise>
	    </c:choose>
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
			<button class="btns" id="prebtn" onclick="location.href='boardDetail?btn=${currentbtn-1}&board_code=${board_code}'">&lt;</button>
			<c:forEach var="btn" begin="1" end="${lastPage}">
				<button id="btn${inPage}" class="btns" onclick="location.href='boardDetail?btn=${inPage}&board_code=${board_code}'">${inPage}</button>
				<c:set var="inPage" value="${inPage+1}"></c:set>
			</c:forEach>
			<c:if test="${currentbtn!=totalbtn && commentCnt!=0}"><button class="btns" id="pastbtn" onclick="location.href='boardDetail?btn=${currentbtn+1}&board_code=${board_code}'">&gt;</button></c:if>
		</div>
	</div>
</div>
  
<script src="https://code.jquery.com/jquery-2.2.1.js"></script>
<script>

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
	
	/* 게시글 수정 */
	function fix(){
		location.href="boardFix?board_code=${board_code}";
	}
	
	/* 게시글 삭제 */
	function del(){
		var input = prompt("비밀번호를 입력해주세요!", "");
		if(input == '${boardDetail.password}'){
			var con = confirm("정말로 삭제하시나요?");
			if(con == true){
				location.href = "deleteWrite?board_code=${board_code}";
			}
		}else if(input != '${boardDetail.password}' && input != null && input != '' ){
			alert("비밀번호가 일치하지 않습니다!");
		}
	}
	
	/* 댓글 삭제하기 */
	function axbtn(a){
		var input = prompt("비밀번호를 입력해주세요!", "");
		if(input == document.getElementById("pass" + a).value){
			var con = confirm("정말로 삭제하시나요?");
			if(con == true){
				location.href = "deleteComment?comment_code=" + document.getElementById("code" + a).value + "&board_code=${board_code}&re_group=" + document.getElementById("group" + a).value;
			}
		}else if(input != document.getElementById("pass" + a).value && input != null && input != '' ){
			alert("비밀번호가 일치하지 않습니다!");
		}
	}
	
	/* 수정하기 */
	function rebtn(a){
		var ii = parseInt('${i}')-1;
		for(var i=0; i<ii; i++){
			if(i!=parseInt(a-1)){
				document.getElementsByClassName("re")[i].style.display = "none";
			}
			document.getElementsByClassName("rere")[i].style.display = "none";
		}
		if(document.getElementById("re" + a).style.display == "block"){
			document.getElementById("re" + a).style.display = "none";
		}else{
			document.getElementById("re" + a).style.display = "block";
		}
	}
	
	/* 대댓글 달기 */
	function seeComment(a){
		var ii = parseInt('${i}')-1;
		for(var i=0; i<ii; i++){
			if(i!=parseInt(a-1)){
				document.getElementsByClassName("rere")[i].style.display = "none";
			}
			document.getElementsByClassName("re")[i].style.display = "none";
		}
		if(document.getElementById("rere" + a).style.display == "none"){
			document.getElementById("rere" + a).style.display = "block";
		}else{
			document.getElementById("rere" + a).style.display = "none";
		}
	}
	
	var comment_id = document.getElementById("comment_id");
	var comment_password = document.getElementById("comment_password");
	var comment_content = document.getElementById("comment_content");
	
	// 예외처리
	function reserve(){
		if(comment_id.value=='' || comment_password.value=='' || comment_content.value==''){
			alert("빈칸을 입력할 수 없습니다.");
			return false;
		}else{
			return true;
		}
	}
	
	// 수정 예외처리
	function re_reserve(a){
		var recomment_id = document.getElementById("recomment_id" + a);
		var recomment_password = document.getElementById("recomment_password" + a);
		var recomment_content = document.getElementById("recomment_content" + a);
		
		if(recomment_password.value=='' || recomment_content.value==''){
			alert("빈칸X");
			return false;
		}else if(recomment_password.value!=document.getElementById("pass" + a).value){
			alert("비밀번호가 일치하지 않습니다.");
			return false;
		}else if(recomment_content.value == document.getElementById("con").innerHTML){
			alert("수정할 내용이 존재하지 않습니다.");
			return false;
		}else{
			return true;
		}
	}
	
	// 대댓글 예외처리
	function rere_reserve(a){
		var recomment_id = document.getElementById("rerecomment_id" + a);
		var recomment_password = document.getElementById("rerecomment_password" + a);
		var recomment_content = document.getElementById("rerecomment_content" + a);
		
		if(recomment_id.value=='' || recomment_password.value=='' || recomment_content.value==''){
			alert("빈칸을 입력할 수 없습니다.");
			return false;
		}else{
			return true;
		}
	}
	
	

</script>
</body>
</html>






































