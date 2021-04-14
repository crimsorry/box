<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="icon" type="image/png" href="resources/img/board.png">
<title>지도 보기</title>

<style>
	/* div라고 쓰면 인식을 못함... */
	#map {width: 500px; height: 500px;}
	#address { width: 350px; }
</style>
</head>

<body>
<h2>지도를 클릭해서 주소 수정</h2>
<div id="map"></div>
<input type="text" id="address">
<input type="hidden" id="lat">
<input type="hidden" id="lng">
<input type="button" value="전달하기" onclick="setText()"> 
<input type="button" value="창닫기" onclick="window.close()"> 

<script src="http://maps.googleapis.com/maps/api/js?key=AIzaSyD8DS8TdoWivk2Lu0pMibDfp4Dyrj-hwK8"></script>
<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.6.4/jquery.min.js"></script>
<script type="text/javascript">

	var map;
	var address = new google.maps.LatLng(parseFloat('${lat}'), parseFloat('${lng}'));
	
	// 바뀐 주소 전달
	function setText(){
		if(document.getElementById("address").value==""){
			alert("전달한 주소가 존재하지 않습니다!");
		}else{
			opener.document.getElementById("address").value = document.getElementById("address").value;
			opener.document.getElementById("lat").value = document.getElementById("lat").value;
			opener.document.getElementById("lng").value = document.getElementById("lng").value;
		}
	}
	
	// 구글 맵
	function initMap() {
	  var myOptions = {
	      zoom: 8,
	      center: address,
	      /* scrollwheel : true, //마우스 휠로 확대 축소 사용 여부
	      mapTypeControl : true, //맵 타입 컨트롤 사용 여부
	      disableDefaultUI : true, //기본 UI 사용 여부
	      disableDoubleClickZoom : true, //더블클릭 중심으로 확대 사용 여부
	      draggable : true, //지도 드래그 이동 사용 여부
	      keyboardShortcuts : true, //키보드 단축키 사용 여부
	      maxZoom : 18, //최대 줌
	      minZoom : 1, //최소 줌 */
	      mapTypeId: google.maps.MapTypeId.ROADMAP
	    };
	
	    map = new google.maps.Map(document.getElementById("map"), myOptions);
	    
	    var marker = new google.maps.Marker({
			position: address,
			label: 'Click to zoom'
		});
	    
	    marker.setMap(map);
	    
	    // 마커 클릭하면 줌됨!
	    google.maps.event.addListener(marker, "click", function() { 
	    	map.setZoom(17);
	    	map.setCenter(marker.getPosition());
        });
	    
	    var geocoder = new google.maps.Geocoder();
	    
	    // 맵 클릭하면 마커 이동!
	    google.maps.event.addListener(map, 'click', function(event){
			var location = event.latLng;
			geocoder.geocode({
				'latLng' : location
			},
			function(results, status){
				if(status==google.maps.GeocoderStatus.OK){
					$('#address').val(results[0].formatted_address);
					$('#lat').val(results[0].geometry.location.lat());
					$('#lng').val(results[0].geometry.location.lng());
				}else{
					alert("Geocoder failed due to: " + status);
				}
			});
			if(!marker){
				marker = new google.maps.Marker({
					position: location,
					map: map
				});
			}else{
				marker.setMap(null);
				marker = new google.maps.Marker({
					position: location,
					map: map
				});
			}
			map.setCenter(location);
	   	 });
	} 
	google.maps.event.addDomListener(window, 'load', initMap);

</script> 
</body>
</html>

















