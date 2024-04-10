<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="icon" type="image/png" href="resources/img/board.png">
<script src="https://kit.fontawesome.com/742b89101d.js" crossorigin="anonymous"></script>
<link href="<c:url value="/resources/css/management.css" />" rel="stylesheet">
<title>박스 재고 관리</title>
</head>
<body>

<i id="navBar" class="fas fa-bars"></i>

<div class="noneBox">
<div id="navBox">
	<i id="axBox" class="fas fa-times"></i>
	<ul>
		<li><a href="main">거래처 관리</a></li>
		<li><a href="boxManagement?num=one">박스 재고 관리</a></li>
		<li><a href="boardList">자유게시판</a></li>
	</ul>
</div>
</div>

<h5>${company_name}님 로그인 <span><a href="logout">[로그아웃]</a></span></h5>
<h2><a href="boxManagement?num=one">박스 재고 관리</a></h2>

<div class="tab_wrap">
  <div class="tab_menu_container">
    <button class="tab_menu_btn" onclick="location.href='boxManagement?num=one'">사용자 박스 요청 조회</button>
    <button class="tab_menu_btn" onclick="location.href='boxManagement?num=two'">재고 조회 화면</button>
    <button class="tab_menu_btn" onclick="location.href='boxManagement?num=three'">박스구매(out)</button>
    <button class="tab_menu_btn" onclick="location.href='boxManagement?num=four'">신규박스입력(in)</button>
    <button class="tab_menu_btn" onclick="location.href='boxManagement?num=five'">박스입고(in)</button>
  </div> 

  <div class="tab_box_container">
    <div class="tab_box">
    	<table id="maneger" border="1">
    		<tr>
    			<th>번호</th>
    			<th>거래처명</th>
    			<th>박스 타입</th>
    			<th>수량</th>
    			<th>구입일</th>
    			<th>발송요청일</th>
    			<th>발송</th>
    		</tr>
    		<c:set var="i" value="${(currentbtn-1)*20+1}" />
    		<c:forEach var="boxList" items="${boxList}">
	    		<tr>
	    			<td>${i}</td>
	    			<td>${boxList.company_name}</td>
	    			<td>${boxList.box_type}</td>
	    			<td>${boxList.order_cnt}</td>
	    			<td>${boxList.order_date}</td>
	    			<td>${boxList.require_date}</td>
	    			<td>
	    				<c:choose>
		    				<c:when test="${now<boxList.require_date}">
								<button class="blankGo" onclick="blankGo()">--</button>
							</c:when>
		    				<c:otherwise>
		    					<button class="go" onclick="location.href='updateBox?order_code=${boxList.order_code}&num=one'">발송</button>
		    				</c:otherwise>
		    			</c:choose>
	    			</td>
	    		</tr>
	    		<c:set var="i" value="${i+1}"></c:set>
    		</c:forEach>
    		<!-- 맨뒤에 빈칸 공백 채우기 -->
			<c:set var="z" value="${(i-1)%20}" />
			<c:if test="${z>0}">
				<c:forEach var="blankTr" begin="1" end="${20-z}">
					<tr class="noneTr"><td colspan="7"></td></tr>
				</c:forEach>
			</c:if>
    	</table>
    	<!-- 페이징 -->
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
			<button class="btns" id="prebtn" onclick="location.href='boxManagement?num=one&btn=${currentbtn-1}'">&lt;</button>
			<c:forEach var="btn" begin="1" end="${lastPage}">
				<button id="btn${inPage}" class="btns" onclick="location.href='boxManagement?num=one&btn=${inPage}'">${inPage}</button>
				<c:set var="inPage" value="${inPage+1}"></c:set>
			</c:forEach>
			<c:if test="${currentbtn!=totalbtn}"><button class="btns" id="pastbtn" onclick="location.href='boxManagement?num=one&btn=${currentbtn+1}'">&gt;</button></c:if>
		</div>
    </div>
    <!-- =========================================재고조회화면============================================= -->
    <div class="tab_box recentBox">
    	<div>
    	<h3>재고현황</h3>
  		<table id="recent" border="1">
        	<tr>
        		<th>번호</th>
        		<th>박스타입</th>
        		<th>재고량</th>
        	</tr>
        	<c:set var="i" value="1"></c:set>
        	<c:forEach var="boxList" items="${boxList}">
        	<tr class="clickTr" onclick="javascript:clickTr('${boxList.box_code}')">
        		<td>${i}</td>
        		<td>${boxList.box_type}</td>
        		<td>${boxList.remain_cnt}개</td>
        	</tr>
        	<c:set var="i" value="${i+1}"></c:set>
        	</c:forEach>
        	<!-- 맨뒤에 빈칸 공백 채우기 -->
			<c:set var="z" value="${(i-1)%20}" />
			<c:if test="${z>0}">
				<c:forEach var="blankTr" begin="1" end="${20-z}">
					<tr class="noneTr"><td colspan=3></td></tr>
				</c:forEach>
			</c:if>
        </table>
        </div>
        <div><i class="fas fa-arrow-right"></i></div>
        <!-- =========================================재고조회화면2============================================= -->
        <div>
        <form action="dateSee?num=twotwo&box_code=${boxtwoList[0].box_code}" method="post" onsubmit="return reserve_date()">
        <h3>
        	<c:if test="${num=='twotwo'}">${boxtwoList[0].box_type}박스 </c:if>
        	상세재고현황
        	<c:if test="${num=='twotwo'}">
        		<c:choose>
					<c:when test="${dateChk==1}">
					<button class="inbtn" id="dateSee">조회</button>
					<input type="date" class="inbtn" id="endDate" name="endDate" max="${now}" value="${endDate}">
						<input type="date" class="inbtn" id="startDate" name="startDate" value="${startDate}">
					</c:when>
					<c:otherwise>
						<button class="inbtn" id="dateSee">조회</button>
						<input type="date" class="inbtn" id="endDate" name="endDate" max="${now}" value="${now}">
						<input type="date" class="inbtn" id="startDate" name="startDate">
					</c:otherwise>
				</c:choose>
        	</c:if>
        </h3>
        </form>
        <c:choose>
        	<c:when test="${num!='twotwo'}">
        		<p>재고현황을 클릭해주세요!</p>
        	</c:when>
        	<c:otherwise>
	        	<table id="recentIn" border="1">
	        	<tr>
	        		<th>날짜</th>
	        		<th>업체</th>
	        		<th>출고(out)</th>
	        		<th>입고(in)</th>
	        	</tr>
		 		<c:set var="i" value="1"></c:set>
		 		<c:set var="total" value="0"></c:set>
		 			<c:forEach var="boxList" items="${boxtwoList}">
		        	<tr>
	        			<c:choose>
	        				<c:when test="${boxList.company_name=='관리자'}">
	        					<td>${boxList.order_date}</td>
	        					<td></td>
	        					<td></td>
	        					<td>${boxList.order_cnt}</td>
	        				</c:when>
	        				<c:otherwise>
	        				<td>${boxList.order_date}</td>
	        					<td>${boxList.company_name}</td>
	        					<td>${boxList.order_cnt}</td>
	        					<td></td> 
	        				</c:otherwise>
	        			</c:choose>
		        	</tr>
		        	<c:set var="i" value="${i+1}"></c:set>
		       		</c:forEach>
		       	<!-- 맨뒤에 빈칸 공백 채우기 -->
				<c:set var="z" value="${(i-1)%20}" />
				<c:if test="${z>0}">
					<c:forEach var="blankTr" begin="1" end="${20-z}">
						<tr class="noneTr"><td colspan="7"></td></tr>
					</c:forEach>
				</c:if>
		        </table>
		        <!-- 페이징 -->
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
					<c:choose>
						<c:when test="${dateChk==1}">
							<button class="btns" id="prebtn" onclick="location.href='dateSee?num=twotwo&box_code=${box_code}&btn=${currentbtn-1}&startDate=${startDate}&endDate=${endDate}'">&lt;</button>
							<c:forEach var="btn" begin="1" end="${lastPage}">
								<button id="btn${inPage}" class="btns" onclick="location.href='dateSee?num=twotwo&box_code=${box_code}&btn=${inPage}&startDate=${startDate}&endDate=${endDate}'">${inPage}</button>
								<c:set var="inPage" value="${inPage+1}"></c:set>
							</c:forEach>
							<c:if test="${currentbtn!=totalbtn}"><button class="btns" id="pastbtn" onclick="location.href='dateSee?num=twotwo&box_code=${box_code}&btn=${currentbtn+1}&startDate=${startDate}&endDate=${endDate}'">&gt;</button></c:if>
						</c:when>
						<c:otherwise>
							<button class="btns" id="prebtn" onclick="location.href='boxManagement?num=twotwo&box_code=${box_code}&btn=${currentbtn-1}'">&lt;</button>
							<c:forEach var="btn" begin="1" end="${lastPage}">
								<button id="btn${inPage}" class="btns" onclick="location.href='boxManagement?num=twotwo&box_code=${box_code}&btn=${inPage}'">${inPage}</button>
								<c:set var="inPage" value="${inPage+1}"></c:set>
							</c:forEach>
							<c:if test="${currentbtn!=totalbtn}"><button class="btns" id="pastbtn" onclick="location.href='boxManagement?num=twotwo&box_code=${box_code}&btn=${currentbtn+1}'">&gt;</button></c:if>
						</c:otherwise>
					</c:choose>
				</div>
        	</c:otherwise>
        </c:choose>
		</div>
    </div>
    <!-- =========================================박스구매(out)============================================= -->
    <form action="boxOut" method="post" onsubmit="return reserve()">
    <div class="tab_box boxOut">
    	<select id="company_code" name="company_code"> 
    		<option value="">------업체명 입력------</option>
			<c:forEach var="comList" items="${comList}">
				<option value="${comList.company_code}">${comList.company_name}</option>
	        </c:forEach>
		</select><br>
       	<select id="box_code" name="box_code"> 
       		<option value="" selected>------박스타입 입력------</option>
			<c:forEach var="boxList" items="${boxList}">
			<c:choose>
				<c:when test="${boxList.remain_cnt=='0'}">
					<option value="${boxList.box_code}" disabled>${boxList.box_type}(${boxList.box_standard}) (재고: ${boxList.remain_cnt})</option>
				</c:when>
				<c:otherwise>
					<option value="${boxList.box_code}" >${boxList.box_type}(${boxList.box_standard}) (재고: ${boxList.remain_cnt})</option>
				</c:otherwise>
			</c:choose>
	        </c:forEach>
		</select><br>
		<input type="text" id="order_cnt" name="order_cnt" placeholder="수량 입력"><br>
		<input type="date" id="require_date" name="require_date" min="${now}" value="${tomorrow}" placeholder="받을 날짜 입력"><br>
		<button>입력</button>
    </div>
    </form>
    <!-- =========================================신규박스입력(out)============================================= -->
    <form action="boxIn" method="post" onsubmit="return reserve_out()">
    <div class="tab_box boxIn">
        <input type="text" id="box_type" name="box_type" placeholder="박스명 입력 ex)2호"><br>
        <input type="text" id="box_standard" name="box_standard" placeholder="박스규격"><br>
        <input type="text" id="box_cnt" name="box_cnt" placeholder="재고량"><br>
        <button>입력</button>
    </div>
    </form>
    <!-- =========================================박스입고(out)============================================= -->
    <form action="oldBoxIn" method="post" onsubmit="return reserve_oldIn()">
    <div class="tab_box oldBoxIn">
        <select id="box_coder" name="box_code"> 
       		<option value="" selected>------박스타입 선택------</option>
			<c:forEach var="boxList" items="${boxList}">
				<option value="${boxList.box_code}" >${boxList.box_type}(${boxList.box_standard}) (재고: ${boxList.remain_cnt})</option>
	        </c:forEach>
		</select><br>
        <input type="text" id="box_cntr" name="box_cnt" placeholder="입고수량 입력"><br>
        <button>입력</button>
    </div>
    </form>
  </div> 
</div>

<script src="https://code.jquery.com/jquery-2.2.1.js"></script>
<script>
	
	function clickTr(box_code){
		location.href = "boxManagement?num=twotwo&box_code=" + box_code;
	}
	
	// 발송ㄴㄴ
	function blankGo(){
		alert("발송 요청일부터 발송 가능!");
	}
	
	// 작성 완료!
	if(parseInt('${see}')==1){
		alert('${msg}');
	}

	// div
	var tab_menu_btn = document.querySelectorAll(".tab_menu_btn");
	var tab_box = document.querySelectorAll(".tab_box");
	var tab_menu_btn = document.querySelectorAll(".tab_menu_btn");
	    
	var num = '${num}';
	
	if(num=="one"){
		tab_box[0].style.display="block";
	    tab_box[1].style.display="none";
	    tab_box[2].style.display="none";
	    tab_box[3].style.display="none";
	    tab_box[4].style.display="none";
	    tab_menu_btn[0].style.fontWeight="bold";
	    tab_menu_btn[1].style.fontWeight="normal";
	    tab_menu_btn[2].style.fontWeight="normal";
	    tab_menu_btn[3].style.fontWeight="normal";
	    tab_menu_btn[4].style.fontWeight="normal";
	    tab_menu_btn[0].style.background="#CFA987";
	    tab_menu_btn[1].style.background="#3E3E3E";
	    tab_menu_btn[2].style.background="#3E3E3E";
	    tab_menu_btn[3].style.background="#3E3E3E";
	    tab_menu_btn[4].style.background="#3E3E3E";
	}else if(num=="two" || num=="twotwo"){
		tab_box[0].style.display="none";
	    tab_box[1].style.display="block";
	    tab_box[2].style.display="none";
	    tab_box[3].style.display="none";
	    tab_box[4].style.display="none";
	    tab_menu_btn[0].style.fontWeight="normal";
	    tab_menu_btn[1].style.fontWeight="bold";
	    tab_menu_btn[2].style.fontWeight="normal";
	    tab_menu_btn[3].style.fontWeight="normal";
	    tab_menu_btn[4].style.fontWeight="normal";
	    tab_menu_btn[0].style.background="#3E3E3E";
	    tab_menu_btn[1].style.background="#CFA987";
	    tab_menu_btn[2].style.background="#3E3E3E";
	    tab_menu_btn[3].style.background="#3E3E3E";
	    tab_menu_btn[4].style.background="#3E3E3E";
	}else if(num=="three"){
		tab_box[0].style.display="none";
	    tab_box[1].style.display="none";
	    tab_box[2].style.display="block";
	    tab_box[3].style.display="none";
	    tab_box[4].style.display="none";
	    tab_menu_btn[0].style.fontWeight="normal";
	    tab_menu_btn[1].style.fontWeight="normal";
	    tab_menu_btn[1].style.fontWeight="bold";
	    tab_menu_btn[3].style.fontWeight="normal";
	    tab_menu_btn[4].style.fontWeight="normal";
	    tab_menu_btn[0].style.background="#3E3E3E";
	    tab_menu_btn[1].style.background="#3E3E3E";
	    tab_menu_btn[2].style.background="#CFA987";
	    tab_menu_btn[3].style.background="#3E3E3E";
	    tab_menu_btn[4].style.background="#3E3E3E";
	}else if(num=="four"){
		tab_box[0].style.display="none";
	    tab_box[1].style.display="none";
	    tab_box[2].style.display="none";
	    tab_box[3].style.display="block";
	    tab_box[4].style.display="none";
	    tab_menu_btn[0].style.fontWeight="normal";
	    tab_menu_btn[1].style.fontWeight="normal";
	    tab_menu_btn[1].style.fontWeight="normal";
	    tab_menu_btn[3].style.fontWeight="bold";
	    tab_menu_btn[4].style.fontWeight="normal";
	    tab_menu_btn[0].style.background="#3E3E3E";
	    tab_menu_btn[1].style.background="#3E3E3E";
	    tab_menu_btn[2].style.background="#3E3E3E";
	    tab_menu_btn[3].style.background="#CFA987";
	    tab_menu_btn[4].style.background="#3E3E3E";
	}else if(num=="five"){
		tab_box[0].style.display="none";
	    tab_box[1].style.display="none";
	    tab_box[2].style.display="none";
	    tab_box[3].style.display="none";
	    tab_box[4].style.display="block";
	    tab_menu_btn[0].style.fontWeight="normal";
	    tab_menu_btn[1].style.fontWeight="normal";
	    tab_menu_btn[1].style.fontWeight="normal";
	    tab_menu_btn[3].style.fontWeight="normal";
	    tab_menu_btn[4].style.fontWeight="bold";
	    tab_menu_btn[0].style.background="#3E3E3E";
	    tab_menu_btn[1].style.background="#3E3E3E";
	    tab_menu_btn[2].style.background="#3E3E3E";
	    tab_menu_btn[3].style.background="#3E3E3E";
	    tab_menu_btn[4].style.background="#CFA987";
	}
	
	// 버튼 색
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
	
	var company_code = document.getElementById("company_code");
	var boxs_code = document.getElementById("box_code");
	var order_cnt = document.getElementById("order_cnt");
	var require_date = document.getElementById("require_date");
	
	var num = /^[0-9]*$/;
	var date_pattern = /^(19|20)\d{2}-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[0-1])$/; 
	
	//예외처리
	function reserve(){
		console.log(boxs_code.value);
		if(boxs_code.value==""){
			alert("박스타입을 선택해주세요!");
			return false;
		}else{
			var result = boxs_code.options[boxs_code.selectedIndex].text;
			var sp = result.split(" ");
			var as = sp[2];
			var remain = as.slice(0, -1);
			
			if(!num.test(order_cnt.value)){
				alert("숫자만 입력할 수 있습니다.");
				return false;
			}else if(company_code.value==''){
				alert("거래처를 선택해주세요!");
				return false;
			}else if(order_cnt.value>parseInt(remain)){
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
	}
	
	var box_type = document.getElementById("box_type");
	var box_standard = document.getElementById("box_standard");
	var box_cnt = document.getElementById("box_cnt");
	
	function reserve_out(){
		if(box_type.value=='' || box_standard.value=='' || box_cnt.value==''){
			alert("빈칸은 입력할 수 없습니다!");
			return false;
		}else if(!num.test(box_cnt.value)){
			alert("숫자만 입력할 수 있습니다.");
			return false;
		}else{
			return true;
		}
	}
	
	var box_coder = document.getElementById("box_coder");
	var box_cntr = document.getElementById("box_cntr");
	
	function reserve_oldIn(){
		if(box_coder.value=='' || box_cntr.value==''){
			alert("빈칸은 입력할 수 없습니다!");
			return false;
		}else if(!num.test(box_cntr.value)){
			alert("숫자만 입력할 수 있습니다.");
			return false;
		}else{
			return true;
		}
	}
	
	var startDate = document.getElementById("startDate");
	var endDate = document.getElementById("endDate");
	
	function reserve_date(){
		if(startDate.value=="" || endDate.value==""){
			alert("빈칸 입력ㄴㄴ");
			return false;
		}else if(!date_pattern.test(startDate.value) || !date_pattern.test(endDate.value)){
			alert("날짜 형식에 맞게 입력해주세요!");
			return false;
		}else{
			return true;
		}
	}

</script>

</body>
</html>







































