<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="https://kit.fontawesome.com/742b89101d.js" crossorigin="anonymous"></script>
<script type="text/javascript" src="/resources/smartEditor/js/HuskyEZCreator.js" charset="utf-8"></script>
<link rel="icon" type="image/png" href="resources/img/board.png">
<link href="<c:url value="resources/css/boardFix.css" />" rel="stylesheet">
<title>상세보기</title>
</head>
<body>

<h2>수정하기</h2>

<form action="rewrite?board_code=${board_code}" method="post" onsubmit="return reserve()">
<div id="free_box">
	<div id="title">
		<p><input type="text" id="mytitle" name="title" value="${boardDetail.title}" placeholder="제목을 입력해주세요."></p>
		<p><span>${boardDetail.id}</span> | <span>${boardDetail.write_date}</span> <span id="span"><span>조회 ${boardDetail.cnt}</span></span></p>
		<p><input type="password" id="password" name="password" placeholder="비밀번호를 입력해주세요."></p>
	</div>
	<div id="content">
		<p>
			<textarea name="content" id="mycontent" rows="10" cols="100" placeholder="내용을 입력해주세요.">${boardDetail.content}</textarea>
			<%-- <input type="text" id="mycontent" name="content" value="${boardDetail.content}" placeholder="내용을 입력해주세요."> --%>
		</p>
	</div>
	<button id="fix">수정하기</button>
</div>
</form>
  
<script src="https://code.jquery.com/jquery-2.2.1.js"></script>
<script>

	var title = document.getElementById("mytitle");
	var password = document.getElementById("password");
	var content = document.getElementById("mycontent");
	
	var remain_title = '${boardDetail.title}';
	var remain_password = '${boardDetail.password}';
	console.log(remain_title);
	console.log(remain_password);
	var remain_content = '${boardDetail.content}';
	
	//전역변수
    var obj = [];
    //스마트에디터 프레임생성
    nhn.husky.EZCreator.createInIFrame({
        oAppRef: obj,
        elPlaceHolder: "mycontent",
        sSkinURI: "resources/smartEditer/SmartEditor2Skin.html",
        htParams : {
            // 툴바 사용 여부
            bUseToolbar : true,
            // 입력창 크기 조절바 사용 여부
            bUseVerticalResizer : true,
            // 모드 탭(Editor | HTML | TEXT) 사용 여부
            bUseModeChanger : true,
        },
    	fCreator: "createSEditor2"
    });

	//예외처리
	function reserve(){
		if(title.value=='' || password.value=='' || content.value==''){
			alert("빈칸을 입력할 수 없습니다.");
			return false;
		}else if(remain_password!=password.value){
			alert("비밀번호가 일치하지 않습니다.");
			return false;
		}else if(remain_title==title.value && remain_content==content.value){
			alert("수정할 내용이 존재하지 않습니다.");
			return false;
		}else{
			return true;
		}
	}
	
	

</script>
</body>
</html>






































