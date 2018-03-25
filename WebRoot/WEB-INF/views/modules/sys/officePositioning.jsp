<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
<script type="text/javascript" src="/JZManage/static/jquery/jquery-1.8.3.min.js"></script>
<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=ZUtBCLA8dFeXmCXYbUUaeQzjswpw0De5"></script>
<title>机构管理</title>
<script>
</script>
<style type="text/css">
	*{
	padding:0px;
	margin:0px;
	}
	body,
	html,
	#allmap {
		width: 100%;
		height: 100%;
		overflow: hidden;
		font-family: "微软雅黑";
		border-top:1px solid #ddd;
	}
	.headInfo{
		width:90%;
		margin:0px auto;
		margin-bottom:30px;
		position:relative;
	}
	#saveAddr{
		position:absolute;
		right:0px;
		top:0px;
		color: #fff;
		text-shadow: 0 -1px 0 rgba(0, 0, 0, 0.25);
		background-color: #006dcc;
		background-image: linear-gradient(to bottom, #08c, #04c);
		background-repeat: repeat-x;
		border-color: rgba(0, 0, 0, 0.1) rgba(0, 0, 0, 0.1) rgba(0, 0, 0, 0.25);
		padding: 4px 12px;
		font-size: 14px;
		line-height: 20px;
		border: 1px solid #ccc;
		border-radius: 4px;
		text-align: center;
	}
	.headInfo>p{
		position:absolute;
		top:0px;
		left:0px;
	}
</style>
</head>
<body>
<input type="hidden" value="${office.id}" id="officeId" />
	<div class="headInfo">
		<p>经销商地址：<span id="officeAddressName">${office.addressname}</span></p>
		<div style="margin-left: 450px">
			<p>选择的地址：<span id="addr"></span></p>
			<input type="button" id="saveAddr" value="保存地址"/>
		</div>
	</div>
	<input type="hidden" value="${office.longitude}" id="lng" />
	<input type="hidden" value="${office.latitude}" id="lat" />
	<div id="allmap"></div>
</body>
<script type="text/javascript">
	//将对象转化成字符串
	function objTransform(jsonobj){
		return JSON.stringify(jsonobj);
	}

	var addrInfos={};
	
	
	var lng = $("#lng").val();//经度
	var lat = $("#lat").val();//纬度

	// 百度地图API功能
	var map = new BMap.Map("allmap");
	map.centerAndZoom(new BMap.Point(lng, lat), 12);
	map.enableScrollWheelZoom();
	var MAX = 10;
	var markers = [];
	var pt = null;
	var mapType1 = new BMap.MapTypeControl({mapTypes: [BMAP_NORMAL_MAP,BMAP_HYBRID_MAP]});
	var top_left_navigation = new BMap.NavigationControl();  //左上角，添加默认缩放平移控件
	function add_control(){
		map.addControl(mapType1);          //2D图，卫星图
		map.addControl(top_left_navigation);
	}
	add_control();
	var marker = new BMap.Marker(new BMap.Point(lng,lat)); // 创建标注
	map.addOverlay(marker); // 将标注添加到地图中
	var geoc = new BMap.Geocoder();
	//点击让当前的位置标注，获取当前的地址
	map.addEventListener("click", function(e) {
		//清除所有的标注
		map.clearOverlays();
		//根据当前点击的经纬度标注当前的位置
		map.addOverlay(new BMap.Marker(new BMap.Point(e.point.lng, e.point.lat)));
		addrInfos.lng=e.point.lng;
		addrInfos.lat=e.point.lat;
		var pt = e.point;
		geoc.getLocation(pt, function(rs) {
		var addComp = rs.addressComponents;
		//可以获得省市区县街道号
		/* if(addComp.province==addComp.city){
			var addressAll=addComp.city + addComp.district + addComp.street + addComp.streetNumber;
		}else{ */
			var addressAll=addComp.province +addComp.city + addComp.district + addComp.street + addComp.streetNumber;
		/* }  */
		$("#addr").html(addressAll);
		addrInfos.addr=$("#addr").html();
		addrInfos.province=addComp.province;
		addrInfos.address=addComp.city + addComp.district + addComp.street + addComp.streetNumber;
		});
	})
	$("#saveAddr").on("click",function(){
		var officeId=$("#officeId").val();
		console.log(addrInfos);
		$.ajax ({
			url : "${ctx}/sys/office/savePosition",
			type : "POST",
			dataType : "JSON",
			data: {"lng": addrInfos.lng,
				"lat":addrInfos.lat,
				"province":addrInfos.province,
			    "address":addrInfos.address,
				"officeId":officeId
			},
			success : function(data) {
				alert("提交成功");
			}
		});
	})
</script>
</html>