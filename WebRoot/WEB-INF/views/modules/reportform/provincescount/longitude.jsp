<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
	<style type="text/css">
		body, html {width: 100%;height: 100%;margin:0;font-family:"微软雅黑";}
		#allmap{width:100%;height:100%;}
		p{margin-left:5px; font-size:14px;}
	</style>
	<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=ZUtBCLA8dFeXmCXYbUUaeQzjswpw0De5"></script>
	<script type="text/javascript" src="http://api.map.baidu.com/library/TextIconOverlay/1.2/src/TextIconOverlay_min.js"></script>
	<script type="text/javascript" src="http://api.map.baidu.com/library/MarkerClusterer/1.2/src/MarkerClusterer_min.js"></script>
	<title>点聚合</title>
</head>
<body>
	<div id="allmap"></div>
	<p>缩放地图，查看点聚合效果</p>
	<%-- <c:forEach items="${lngList}" var="lng">
		<p>经度：${lng.longitude}</p>
		<p>纬度：${lng.latitude}</p>
	</c:forEach> --%>
	<label>经销商名称：</label><sys:treeselect id="office" name="office.id" value="${office.id}" labelName="office.name" labelValue="" 
				title="公司" url="/sys/office/treeData?type=1" cssClass="input-small" allowClear="true"/>
</body>
</html>
<script type="text/javascript">
	var lngLists=[];
	<c:forEach items='${lngList}' var="lng">
	var lngList = new Object();
	lngList.longitude = '${lng.longitude}';
	lngList.latitude = '${lng.latitude}';
	lngLists.push(lngList);
	</c:forEach>
	console.log(lngLists)


	// 百度地图API功能
	var map = new BMap.Map("allmap");
	map.centerAndZoom(new BMap.Point(116.404, 39.915), 4);
	map.enableScrollWheelZoom();

	var MAX = 10;
	var markers = [];
	var pt = null;
	/*var i = 0;
	for (; i < MAX; i++) {
	   pt = new BMap.Point(Math.random() * 40 + 85, Math.random() * 30 + 21);
	   markers.push(new BMap.Marker(pt));
	}
	//最简单的用法，生成一个marker数组，然后调用markerClusterer类即可。
	var markerClusterer = new BMapLib.MarkerClusterer(map, {markers:markers});*/
	
	
	var mapType1 = new BMap.MapTypeControl({mapTypes: [BMAP_NORMAL_MAP,BMAP_HYBRID_MAP]});
	var top_left_navigation = new BMap.NavigationControl();  //左上角，添加默认缩放平移控件
	function add_control(){
		map.addControl(mapType1);          //2D图，卫星图
		map.addControl(top_left_navigation);
	}
	add_control();
	
	//var lng_lat=[[104.0658, 30.5585],[104.066, 30.5557],[126.404, 29.915],[136.404, 39.915],[118.404, 35.915],[126.404, 39.915],[119.404, 40.915]]
	for(var i in lngLists){
		pt = new BMap.Point(lngLists[i].longitude,lngLists[i].latitude);
		markers.push(new BMap.Marker(pt));
	}
	var markerClusterer = new BMapLib.MarkerClusterer(map, {markers:markers});

</script>
<!--<script>
	var map = new BMap.Map("allmap");
    var point = new BMap.Point(37.5773850000,126.9863630000);
    var gc = new BMap.Geocoder();
    gc.getLocation(point, function(rs){
   var addComp = rs.addressComponents;
   alert(addComp.province + ", " + addComp.city + ", " + addComp.district + ", " + addComp.street + ", " + addComp.streetNumber);
   });
</script> -->