<%@ page language="java" contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="EUC-KR">
		<link id="contextPath" data-path="<c:url value='/'/>" />
		<link rel="stylesheet" href="<c:url value='/libs/bootstrap-4.4.1-dist/css/bootstrap.min.css'/>">
		
		<script src="<c:url value='/libs/jquery-3.4.1.min.js'/>"></script>
		<script src="<c:url value='/libs/bootstrap-4.4.1-dist/js/bootstrap.min.js'/>"></script>
		<script type="text/javascript" src="<c:url value='/resources/smartEditor/js/service/HuskyEZCreator.js'/>" charset="utf-8"></script>
		
		<title>Insert title here</title>
	</head>
	<body>
		<textarea name="ir1" id="ir1" rows="10" cols="100">에디터에 기본으로 삽입할 글(수정 모드)이 없다면 이 value 값을 지정하지 않으시면 됩니다.</textarea>
	</body>
	
	<script>
		var oEditors = [];
		nhn.husky.EZCreator.createInIFrame({
			oAppRef: oEditors,
			elPlaceHolder: "ir1",
			sSkinURI: $('#contextPath').data('path') + "resources/smartEditor/SmartEditor2Skin.html",
			fCreator: "createSEditor2"
		});
	</script>
</html>