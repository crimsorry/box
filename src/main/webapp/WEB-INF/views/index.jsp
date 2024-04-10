<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="https://kit.fontawesome.com/742b89101d.js" crossorigin="anonymous"></script>
<link rel="icon" type="image/png" href="resources/img/board.png">
<link href="<c:url value="/resources/css/index.css" />" rel="stylesheet">
<title>CRUD 게시판</title>
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
<h2><a href="main">거래처 관리</a></h2>

<button id="boardPlus">새 추가(+)</button>
<button id="boardDelete" onclick="deleteCom()">삭제</button>
<form id="form1" name="form1" method="post" enctype="multipart/form-data">
	<input type="file" id="fileInput" name="fileInput">
	<button id="excelUpload" onclick="doExcelUploadProcess()">엑셀 업로드</button>
</form>
<form id="form2" name="form2" method="post" enctype="multipart/form-data">
	<button id="excelDownload" onclick="doExcelDownloadProcess()">엑셀 다운로드</button>
</form>
<form id="form3" name="form3" method="post" enctype="multipart/form-data">
	<button id="excelDownloadSample" onclick="doExcelSampleDownloadProcess()">엑셀 양식 다운로드</button>
</form>

<div id="board_box">
<p id="totalP">Total: ${remaintotalbtn}</p>
<table id="board" border="1">
	<tr>
		<th></th>
		<th>번호</th>
		<th>사용여부</th>
		<th>거래처</th>
		<th>거래처명</th>
		<th>거래처명(영문)</th>
		<th>주소</th>
		<th>주소(영문)</th>
		<th>주소 A(영문)</th>
		<th>주소 B(영문)</th>
		<th>전화번호</th>
		<th>팩스번호</th>
		<th>담당자명(영문)</th>
		<th>이메일</th>
		<th>Local담당자</th>
		<th>Local연락처</th>
		<th>Local Email</th>
		<th>비고사항</th>
		<th>정산처</th>
		<th>통화단위</th>
		<th>사용자번호</th>
		<th>대표자 이름</th>
		<th>거래시작일</th>
		<th>거래종료일</th>
		<th>입력일자</th>
		<th>입력자</th>
		<th>수정일자</th>
		<th>수정자</th>
		<th>비밀번호</th>
	</tr>
	<c:set var="i" value="${(currentbtn-1)*20+1}" />
	<c:forEach var="companyList" items="${companyList}">
		<tr class="clickTr">
			<td><input type="checkbox" name="check_board" value="${companyList.company_code}"></td>
			<td>${i}</td>
			<td>${companyList.use_check}</td>
			<td>${companyList.company_code}</td>
			<td>${companyList.company_name}</td>
			<td>${companyList.company_name_eng}</td>
			<td>${companyList.address}</td>
			<td>${companyList.address_eng}</td>
			<td>${companyList.addressA_eng}</td>
			<td>${companyList.addressB_eng}</td>
			<td>${companyList.tel}</td>
			<td>${companyList.fex}</td>
			<td>${companyList.manager_eng}</td>
			<td>${companyList.email}</td>
			<td>${companyList.local_manager}</td>
			<td>${companyList.local_tel}</td>
			<td>${companyList.local_email}</td>
			<td>${companyList.remark}</td>
			<td>${companyList.settlement}</td>
			<td>${companyList.cur}</td>
			<td><c:choose><c:when test="${companyList.businessNum=='0'}"></c:when><c:otherwise>${companyList.businessNum}</c:otherwise></c:choose></td>
			<td>${companyList.president}</td>
			<td>${companyList.start_date}</td>
			<td>${companyList.end_date}</td>
			<td>${companyList.input_date}</td>
			<td>${companyList.inpuner}</td>
			<td>${companyList.modify_date}</td>
			<td>${companyList.modifier}</td>
			<td>${companyList.com_password}</td>
		</tr>
	<!-- 맨뒤에 빈칸 공백 채우기 -->
	<c:set var="i" value="${i+1}" />
	</c:forEach>
	<c:set var="z" value="${(i-1)%20}" />
	<c:if test="${z>0}">
		<c:forEach var="blankTr" begin="1" end="${20-z}">
			<tr class="noneTr"><td colspan="28"></td></tr>
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
		<button class="btns" id="prebtn" onclick="location.href='main?btn=${currentbtn-1}'">&lt;</button>
		<c:forEach var="btn" begin="1" end="${lastPage}">
			<button id="btn${inPage}" class="btns" onclick="location.href='main?btn=${inPage}'">${inPage}</button>
			<c:set var="inPage" value="${inPage+1}"></c:set>
		</c:forEach>
		<c:if test="${currentbtn!=totalbtn}"><button class="btns" id="pastbtn" onclick="location.href='main?btn=${currentbtn+1}'">&gt;</button></c:if>
	</div>
</div>

<!-- 상세보기 버튼 눌렀을때  -->
<c:if test="${see==1}">
<form class="detailForm" action="update?btn=${currentbtn}" method="post" onsubmit="return reserve()">
	<div class="detailContainer">
		<div class="detail_con">
		<table class="detail" id="detailTable" border=1>
			<caption class="tion">상세보기 화면<span><a href="main?btn=${currentbtn}">X</a></span></caption>
			<tr>
				<th>사용여부<span>*</span></th>
				<td>
					<input type="text" class="noClick" id="use_check" name="use_check" value="${detailList.use_check}" readonly>
					<input type="hidden" id="com_password" name="com_password" value="${detailList.com_password}">
				</td>
				<th>비밀번호<span>*</span></th>
				<td>
					<input type="text" class="noClick" id="com_password" name="com_password" value="${detailList.com_password}" readonly>
				</td>
			</tr>
			<tr>
				<th>거래처<span>*</span></th>
				<td class="readTd threeTd" colspan="3"><input type="text" id="company_code" name="company_code" maxlength="20" value="${detailList.company_code}" readonly></td>
			</tr>
			<tr>
				<th>거래처명<span>*</span></th>
				<td><input type="text" id="company_name" name="company_name" value="${detailList.company_name}" maxlength="25"></td>
				<th>거래처명(영문)<span>*</span></th>
				<td><input type="text" id="company_name_eng" name="company_name_eng" value="${detailList.company_name_eng}" maxlength="25"></td>
			</tr>
			<tr>
				<th>주소</th>
				<td>
					<input type="text" class="readTd" id="address" name="address" value="${detailList.address}" maxlength="100">
					<i class="fas fa-map-marker-alt" onclick="gogleMap()"></i>
					<input type="hidden" id="lat" name="lat" value="${detailList.lat}" maxlength="50">
					<input type="hidden" id="lng" name="lng" value="${detailList.lng}" maxlength="50">
				</td>
				<th>주소(영문)</th>
				<td><input type="text" id="address_eng" name="address_eng" value="${detailList.address_eng}" maxlength="100" placeholder="클릭해주세요!" onclick="openChgBtn()"></td>
			</tr>
			<tr>
				<th>주소 A(영문)</th>
				<td><input type="text" id="addressA_eng" name="addressA_eng" value="${detailList.addressA_eng}" maxlength="50"></td>
				<th>주소 B(영문)</th>
				<td><input type="text" id="addressB_eng" name="addressB_eng" value="${detailList.addressB_eng}" maxlength="50"></td>
			</tr>
			<tr>
				<th>전화번호</th>
				<td class="threeTd" colspan="3"><input type="text" id="tel" name="tel" value="${detailList.tel}" maxlength="15"></td>
			</tr>
			<tr>
				<th>팩스번호</th>
				<td class="threeTd" colspan="3"><input type="text" id="fex" name="fex" value="${detailList.fex}" maxlength="15"></td>
			</tr>
			<tr>
				<th>담당자명(영문)</th>
				<td><input type="text" id="manager_eng" name="manager_eng" value="${detailList.manager_eng}" maxlength="20"></td>
				<th>이메일</th>
				<td><input type="text" id="email" name="email" value="${detailList.email}" maxlength="20"></td>
			</tr>
			<tr>
				<th>Local 담당자</th>
				<td><input type="text" id="local_manager" name="local_manager" value="${detailList.local_manager}" maxlength="10"></td>
				<th>Local 연락처</th>
				<td><input type="text" id="local_tel" name="local_tel" value="${detailList.local_tel}" maxlength="20"></td>
			</tr>
			<tr>
				<th>Local Email</th>
				<td><input type="text" id="local_email" name="local_email" value="${detailList.local_email}" maxlength="20"></td>
				<th>비고사항</th>
				<td><input type="text" id="remark" name="remark" value="${detailList.remark}" maxlength="10"></td>
			</tr>
			<tr>
				<th>정산처</th>
				<td><input type="text" id="settlement" name="settlement" value="${detailList.settlement}" maxlength="10"></td>
				<th>통화단위</th>
				<td><input type="text" id="cur" name="cur" value="${detailList.cur}" maxlength="3"></td>
			</tr>
			<tr>
				<th>사용자번호</th>
				<td><c:choose><c:when test="${detailList.businessNum=='0'}"><input type="text" id="businessNum" name="businessNum" value="${detailList.businessNum}" maxlength="15"></c:when><c:otherwise><input type="text" id="businessNum" name="businessNum" value="${detailList.businessNum}" maxlength="15"></c:otherwise></c:choose></td>
				<th>대표자 이름</th>
				<td><input type="text" id="president" name="president" value="${detailList.president}" maxlength="10"></td>
			</tr>
			<tr>
				<th>거래시작일</th>
				<td><input type="date" id="start_date" name="start_date" value="${detailList.start_date}"></td>
				<th>거래종료일</th>
				<td><input type="date" id="end_date" name="end_date" value="${detailList.end_date}"></td>
			</tr>
			<tr>
				<th>입력일자<span>*</span></th>
				<td><input type="text" class="noClick" id="input_date" name="input_date" value="${detailList.input_date}" readonly></td>
				<th>입력자<span>*</span></th>
				<td><input type="text" class="noClick" id="inpuner" name="inpuner" value="${detailList.inpuner}" readonly></td>
			</tr>
			<tr>
				<th>수정일자</th>
				<td><input type="text" class="noClick" id="modify_date" name="modify_date" value="${detailList.modify_date}" readonly></td>
				<th>수정자</th>
				<td><input type="text" class="noClick" id="modifier" name="modifier" value="${detailList.modifier}" readonly></td>
			</tr>
			<tr>
				<td colspan="4"><input type="submit" value="수정"></td>
			</tr>
		</table>
		</div>
	</div>
</form>
</c:if>

<!-- 새 컬럼 추가 -->
<form id="plusForm" action="insert" method="post" onsubmit="return reserve()">
	<div class="detailContainer" id="plusContainer">
		<div class="detail_con">
		<table class="detail" id="plusTable" border=1>
			<caption class="tion">새 추가<span><a href="main?btn=${currentbtn}">X</a></span></caption>
			<tr>
				<th>사용여부<span>*</span></th>
				<td ><input type="text" id="use_check" name="use_check" value="Y" maxlength="1"></td>
				<th>비밀번호<span>*</span></th>
				<td><input type="password" id="com_password" name="com_password" maxlength="20"></td>
			</tr>
			<tr>
				<th>거래처<span>*</span></th>
				<td class="threeTd" colspan="3"><input type="text" id="company_code" name="company_code" maxlength="20"></td>
			</tr>
			<tr>
				<th>거래처명<span>*</span></th>
				<td><input type="text" id="company_name" name="company_name" maxlength="25"></td>
				<th>거래처명(영문)<span>*</span></th>
				<td><input type="text" id="company_name_eng" name="company_name_eng" maxlength="50"></td>
			</tr>
			<tr>
				<th>주소</th>
				<td>
					<input type="text" id="address" name="address" maxlength="100">
					<i class="fas fa-map-marker-alt" onclick="gogleMap()"></i>
					<input type="hidden" id="lat" name="lat" value="0" maxlength="50">
					<input type="hidden" id="lng" name="lng" value="0" maxlength="50">
				</td>
				<th>주소(영문)</th>
				<td><input type="text" id="address_eng" name="address_eng" maxlength="100" placeholder="클릭해주세요!" onclick="openChgBtn()" readonly></td>
			</tr>
			<tr>
				<th>주소 A(영문)</th>
				<td><input type="text" id="addressA_eng" name="addressA_eng" maxlength="50"></td>
				<th>주소 B(영문)</th>
				<td><input type="text" id="addressB_eng" name="addressB_eng" maxlength="50"></td>
			</tr>
			<tr>
				<th>전화번호</th>
				<td class="threeTd" colspan="3"><input type="text" id="tel" name="tel" maxlength="15"></td>
			</tr>
			<tr>
				<th>팩스번호</th>
				<td class="threeTd" colspan="3"><input type="text" id="fex" name="fex" maxlength="15"></td>
			</tr>
			<tr>
				<th>담당자명(영문)</th>
				<td><input type="text" id="manager_eng" name="manager_eng" maxlength="20"></td>
				<th>이메일</th>
				<td><input type="text" id="email" name="email" maxlength="20"></td>
			</tr>
			<tr>
				<th>Local 담당자</th>
				<td><input type="text" id="local_manager" name="local_manager" maxlength="10"></td>
				<th>Local 연락처</th>
				<td><input type="text" id="local_tel" name="local_tel" maxlength="20"></td>
			</tr>
			<tr>
				<th>Local Email</th>
				<td><input type="text" id="local_email" name="local_email" maxlength="20"></td>
				<th>비고사항</th>
				<td><input type="text" id="remark" name="remark" maxlength="10"></td>
			</tr>
			<tr>
				<th>정산처</th>
				<td><input type="text" id="settlement" name="settlement" maxlength="10"></td>
				<th>통화단위</th>
				<td><input type="text" id="cur" name="cur" maxlength="3"></td>
			</tr>
			<tr>
				<th>사용자번호</th>
				<td><input type="text" id="businessNum" name="businessNum" maxlength="15"></td>
				<th>대표자 이름</th>
				<td><input type="text" id="president" name="president" maxlength="10"></td>
			</tr>
			<tr>
				<th>거래시작일</th>
				<td><input type="date" id="start_date" name="start_date"></td>
				<th>거래종료일</th>
				<td><input type="date" id="end_date" name="end_date"></td>
			</tr>
			<tr>
				<td colspan="4"><input type="submit" value="추가"></td>
			</tr>
		</table>
		</div>
	</div>
</form>
<input type="hidden" id="blank01" value="one">
<script src="https://code.jquery.com/jquery-2.2.1.js"></script>
<script src="https://ssl.daumcdn.net/dmaps/map_js_init/postcode.v2.js"></script>
<script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyD8DS8TdoWivk2Lu0pMibDfp4Dyrj-hwK8&libraries=places&callback=initAutocomplete" async defer></script>
<script>
	
	var use_check = document.getElementById("use_check");
	var company_code = document.getElementById("company_code");
	var company_name = document.getElementById("company_name");
	var company_name_eng = document.getElementById("company_name_eng");
	var address = document.getElementById("address");
	var address_eng = document.getElementById("address_eng");
	var addressA_eng = document.getElementById("addressA_eng");
	var addressB_eng = document.getElementById("addressB_eng");
	var tel = document.getElementById("tel");
	var fex = document.getElementById("fex");
	var manager_eng = document.getElementById("manager_eng");
	var email = document.getElementById("email");
	var local_manager = document.getElementById("local_manager");
	var local_tel = document.getElementById("local_tel");
	var local_email = document.getElementById("local_email");
	var remark = document.getElementById("remark");
	var settlement = document.getElementById("settlement");
	var cur = document.getElementById("cur");
	var businessNum = document.getElementById("businessNum");
	var president = document.getElementById("president");
	var start_date = document.getElementById("start_date");
	var end_date = document.getElementById("end_date");
	var input_date = document.getElementById("input_date");
	var inpuner = document.getElementById("inpuner");
	var modify_date = document.getElementById("modify_date");
	var modifier = document.getElementById("modifier");
	var com_password = document.getElementById("com_password");
	
	var fileInput = document.getElementById("fileInput");
	
	console.log("seemsg: " + '${seemsg}');
	console.log("msg: " + '${msg}');
	if(parseInt('${seemsg}')==1){
		alert('${msg}');
	}
	
	var s = new FormData(document.getElementById('form1'));
	console.log(s);
	
	// 엑셀 업로드
	function doExcelUploadProcess(){
		if(fileInput.value!=''){
			var f = new FormData(document.getElementById('form1'));
			console.log(f);
			
			// async: false: return 값 받기
			// 받을떄 한글이 꺠져서 controller에서 encoding 후 ajax에서 decoding해 한글 꺠짐 방지
	        $.ajax({
	            url: "uploadExcelFile",
	            data: f,
	            processData: false, // 데이터 객체를 문자열로 바꿀지에 대한 값이다. true면 일반문자
	            contentType: false, // 해당 타입을 true로 하면 일반 text로 구분되어 진다.
	            async: false,
	            type: "POST",
	            success: function(data){
	            	// +로 이어진 문자열을 띄어쓰기로 바꾸기!
	            	var Ca = /\+/g;
	                console.log("date: "+data);
	                alert(decodeURIComponent(data.replace(Ca, " ")));
	            }
	        });
		}else{
			alert("파일을 먼저 올려주세요!");
			/*  */
		}
    }
    
	// 다운로드
    function doExcelDownloadProcess(){
        var f = document.form2;
        f.action = "downloadExcelFile";
        f.submit();
    }
	
	// 샘플 다운로드
	function doExcelSampleDownloadProcess(){
		var f = document.form3;
        f.action = "downloadExcelSampleFile";
        f.submit();
	}
	
	// 상세보기
	$(document).ready(function() { 
		$('#board').on('click', 'td:not(:first-of-type)', function () { 
			// 현재 클릭된 Row(<td>)
			var td = $(this);
			// 셀렉터("tr")로 잡히는 상위 요소중 가장 근접한 하나를 반환
			var tr = td.closest("tr");
			
			// tr에 있는 모든 값들을 \n으로 나눔!
			var tdSplit = tr.text().split("\n");
			var company_code = tdSplit[4];
			
			location.href = "detail?company_code=" + company_code + "&btn=${currentbtn}";
		}); 
	});
	
	var placeSearch, autocomplete;
	
	function initAutocomplete() {
	  // 주소 자동으로 보여줌!
		autocomplete = new google.maps.places.Autocomplete(document.getElementById("address"),{types: ['geocode']});
	  	autocomplete.addListener('place_changed', fillInAddress);
	}
	
	var lat, lng;
	
	// 누른 위도, 경도 구하기!
	function fillInAddress() { 
		var place = autocomplete.getPlace();
		lat = place.geometry.location.lat();
		lng = place.geometry.location.lng();
		document.getElementById('lat').value = lat;
		document.getElementById('lng').value = lng;
	}
	
	// 지도 보여주기
	function gogleMap(){
		if(address.value == ''){
			alert("주소를 먼저 입력해주세요!");
		}else{
			latID = document.getElementById('lat');
			lngID = document.getElementById('lng');
			console.log(document.getElementById('lat').value);
			console.log(document.getElementById('lng').value);
			if(document.getElementById('lat').value == '' && document.getElementById('lng').value == ''){
				alert("유효한 주소가 맞는지 확인해주세요!");
			}else{
				lat = document.getElementById('lat').value;
				lng = document.getElementById('lng').value; 
				console.log("lat: "+lat);
				console.log("lng: " + lat);
				openWin = window.open("gogleMap?lat=" + lat + "&lng=" + lng, "gogelMapForm","width=570, height=600, scrollbars = no");
			}
		} 
	}
	
	var myaddress = document.getElementById("address").value;
	
	// 주소 영문 변환 API
	function openChgBtn(){
		if(address_eng.value != '' && myaddress == address.value){
			openWin = window.open("engChange?addressEng=" + address_eng.value, "engChangeForm","width=570, height=350, resizable = no, scrollbars = no");
		}else if(address.value != '' && myaddress != address.value){
			myaddress = address.value;
			openWin = window.open("engChange?addressEng=" + address.value, "engChangeForm","width=570, height=350, resizable = no, scrollbars = no");
		}else if(address.value != ''){
			openWin = window.open("engChange?addressEng=" + address.value, "engChangeForm","width=570, height=350, resizable = no, scrollbars = no");
		}else if(address.value == ''){
			alert("주소를 먼저 입력해주세요!");
		}
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
	
	var param = "";
	var check_board = document.getElementsByName("check_board");
	var count = 0;
	
	// 삭제
	function deleteCom(){
		for(var i=0; i<check_board.length; i++){
			if(check_board[i].checked){
				count++;
			}
		}
		if(count==0){
			alert("삭제할 항목이 존재하지 않습니다.");
		}else{
			var con = confirm("정말로 삭제하시나요?");
			if(con == true){
				for(var i=0; i<check_board.length; i++){
					if(check_board[i].checked){
						param = (param + check_board[i].value + " ");
					}
				}
				location.href="deleteCom?param=" + param;
			}
		}
	}
	
	// 새 추가 항목 보이기
	$(function(){
		$("[id=boardPlus]").click(function(){
			if($("[id=plusContainer]").css("display")=="none"){
				$("[id=plusContainer]").show();
				$("#blank01").val("one");
			}else{
				$("[id=plusContainer]").hide();
				$("#blank01").val("one");
			}
		});
	});
	
	// 새 추가시 중복체크
	if(parseInt("${see}")==2){
		alert("${msg}");
	}
	if(parseInt("${seeMSG}")==1){
		alert("${msg}");
	}
	
	var eng = /^[a-zA-Z]*$/; 
	var engNum = /^[a-zA-Z0-9| |,|''-|.]*$/;
	var num = /^[0-9]*$/;
	var date_pattern = /^(19|20)\d{2}-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[0-1])$/; 
	var yn = /^[Y|N]*$/;
	
	var is_focus = true;
	
	// 예외처리(빈칸, 정규식)
	$(function(){
		$('.tion').on('mouseenter', function() { 
			is_focus = false;
		});
		$('.tion').on('mouseleave', function() { 
			is_focus = true;
		});
		// 사용여부밖에 커서 이동시
		$('#use_check').on("blur", function(event){
			if($('#use_check').val()=='' && is_focus==true){
				alert("빈칸X");
				$('#use_check').focus();
			}
		});
		$('#com_password').on("blur", function(event){
			if($('#com_password').val()=='' && is_focus==true){
				alert("빈칸X");
				$('#com_password').focus();
			}
		});
		$('#company_code').on("blur", function(event){
			if($('#company_code').val()=='' && is_focus==true){
				alert("빈칸X");
				$('#company_code').focus();
			}
		});
		$('#company_name').on("blur", function(event){
			if($('#company_name').val()=='' && is_focus==true){
				alert("빈칸X");
				$('#company_name').focus();
			}
		});
		$('#company_name_eng').on("blur", function(event){
			if($('#company_name_eng').val()=='' && is_focus==true){
				alert("빈칸X");
				$('#company_name_eng').focus();
			}
		});
		$('#use_check').on("blur", function(event){
			if(!yn.test($('#use_check').val()) && is_focus==true){
				alert("대문자 Y와 N만 입력 가능");
				$('#use_check').focus();
			}
		});
		$('#company_name_eng').on("blur", function(event){
			if(!engNum.test($('#company_name_eng').val()) && is_focus==true){
				alert("영어와 숫자만 입력가능");
				$('#company_name_eng').focus();
			}
		});
		$('#address_eng').on("blur", function(event){
			if(!engNum.test($('#address_eng').val()) && is_focus==true){
				alert("영어와 숫자만 입력가능");
				$('#address_eng').focus();
			}
		});
		$('#addressA_eng').on("blur", function(event){
			if(!engNum.test($('#addressA_eng').val()) && is_focus==true){
				alert("영어와 숫자만 입력가능");
				$('#addressA_eng').focus();
			}
		});
		$('#addressB_eng').on("blur", function(event){
			if(!engNum.test($('#addressB_eng').val()) && is_focus==true){
				alert("영어와 숫자만 입력가능");
				$('#addressB_eng').focus();
			}
		});
		$('#manager_eng').on("blur", function(event){
			if(!engNum.test($('#manager_eng').val()) && is_focus==true){
				alert("영어와 숫자만 입력가능");
				$('#manager_eng').focus();
			}
		});
		$('#cur').on("blur", function(event){
			if(!eng.test($('#cur').val()) && is_focus==true){
				alert("영어만 입력가능");
				$('#cur').focus();
			}
		});
		$('#start_date').on("blur", function(event){
			if(!date_pattern.test($('#start_date').val()) && is_focus==true){
				alert("날자 형식에 맞게 입력 부탁!");
				$('#start_date').focus();
			}
		});
		$('#end_date').on("blur", function(event){
			if(!date_pattern.test($('#end_date').val()) && is_focus==true){
				alert("날자 형식에 맞게 입력 부탁!");
				$('#end_date').focus();
			}
		});
		$('#businessNum').on("blur", function(event){
			if(!num.test($('#businessNum').val()) && is_focus==true){
				alert("숫자만 입력 가능!");
				$('#businessNum').focus();
			}
		});
	});
	
	// 버튼 클릭 시 예외처리
	function reserve(){
		var company_password = '${detailList.com_password}';
		
		if(use_check.value=='' || company_code.value=='' || company_name.value=='' || company_name_eng.value=='' || com_password.value==''){
			alert("빈칸을 입력할 수 없습니다.");
			return false;
		}else if(!yn.test(use_check.value) && use_check.value!=''){
			alert("대문자 Y와 N만 입력가능합니다.");
			return false;
		}else if(!engNum.test(company_name_eng.value) && company_name_eng.value!='' || !engNum.test(address_eng.value) && address_eng.value!='' || !engNum.test(addressA_eng.value) && addressA_eng.value!='' || !engNum.test(addressB_eng.value) && addressB_eng.value!='' || !engNum.test(manager_eng.value) && manager_eng.value!='' ){
			alert("영어와 숫자만 입력할 수 있습니다.");
			return false;
		}else if(!eng.test(cur.value) && cur.value!=''){
			alert("영어만 입력할 수 있습니다.");
			return false;
		}else if(!num.test(businessNum.value) && businessNum.value!=''){
			alert("숫자만 입력할 수 있습니다.");
			return false;
		}else if(!date_pattern.test(start_date.value) && start_date.value!='' || !date_pattern.test(end_date.value) && end_date.value!=''){
			alert("날자 형식에 맞게 입력해주세요.");
			return false;
		}else if(parseInt('${see}')==1){
			if(company_code.value=='${detailList.company_code}' && use_check.value=='${detailList.use_check}' && company_name.value=='${detailList.company_name}' && company_name_eng.value=='${detailList.company_name_eng}' && address.value=='${detailList.address}' && address_eng.value=='${detailList.address_eng}' && addressA_eng.value=='${detailList.addressA_eng}' && addressB_eng.value=='${detailList.addressB_eng}' && tel.value=='${detailList.tel}' && fex.value=='${detailList.fex}' && manager_eng.value=='${detailList.manager_eng}' && email.value=='${detailList.email}' && local_manager.value=='${detailList.local_manager}' && local_tel.value=='${detailList.local_tel}' && local_email.value=='${detailList.local_email}' && remark.value=='${detailList.remark}' && settlement.value=='${detailList.settlement}' && cur.value=='${detailList.cur}' && businessNum.value=='${detailList.businessNum}' && president.value=='${detailList.president}' && start_date.value=='${detailList.start_date}' && end_date.value=='${detailList.end_date}'){
				alert("수정할 항목이 존재하지 않습니다.");
				return false;
			}else{
				return true;
			}
		}else{
			return true;
		}
	}
	
</script>
</body>
</html>























