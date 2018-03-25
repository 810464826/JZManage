<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>活动报备管理</title>
<meta name="decorator" content="default" />
<script type="text/javascript"
	src="/JZManage/static/modules/js/jquery.reveal.js"></script>
<link rel="stylesheet" href="/JZManage/static/modules/css/reveal.css">
<style>
.active-name, .create-time, .active-address, .modification-time {
	float: left;
}

.control-group:after {
	clear: both;
	content: "";
	visibility: hidden;
	width: 0;
	height: 0;
	display: block;
}

#waybill-info, #award-info {
	height: 210px;
	overflow-y: auto;
	overflow-x: hidden;
	margin-top: 10px;
}

#waybill-info, #waybill-info table {
	width: 800px;
}

#award-info, #award-info table {
	width: 1200px;
}

#waybill-info table, #award-info table {
	cellpadding: 0;
	text-align: center;
	cellspacing: 0;
}
#waybill-info table thead, #award-info table thead{
	height:30px;
	line-height:30px;
	background:#f5f5f5;
}
.waybill-button, .award-button {
	display: block;
	margin: 50px 0;
}

.save, .back, .refer {
	margin-top: 50px;
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

#awardTableInfo {
	width: 100%;
	margin: 10px auto;
}

.deleteTd {
	color: red;
}

.deleteTd:hover {
	cursor: pointer;
}
</style>
<script type="text/javascript">
	/*头部输入框的信息*/
	var headInfo = new Object();
	/*运单弹窗所有的信息*/
	var waybillList = new Array();
	/*运单里面选中的所有信息*/
	var selectedLists = new Array();
	/*奖品信息需要给后台的数据*/
	var awardInfoLists = new Array();
	//将对象转化成字符串
	function objTransform(jsonobj){
		return JSON.stringify(jsonobj);
	}
	$(document).ready( function() {
		//以下两个输入框只能输入数字，监听文本框的输入值
		$("#onceAwardNumber,#prizeTotal").on("input propertychange",function(){
			$(this).val($(this).val().replace(/\D/g,''));
		})
		//给这两个添加name，以防出现bug
		$("#s2id_autogen1").attr("name","s2id_autogen1");
		$("#s2id_autogen2").attr("name","s2id_autogen2");
		//点击关闭标签让复选框反选，即让表格的数据在复选框中是默认选中
		$(".close-reveal-modal").click(function(){
			$("input[type='checkbox']:checked").each(function(){
				$(this).removeAttr("checked");
			})
			for(var i in selectedLists){
				for(var k in waybillList){
					if(selectedLists[i].id==$("input[type='checkbox']").eq(k).val()){
						$("input[type='checkbox']").eq(k).attr("checked","checked");
					}
				}
			}
			var len=$("input[type='checkbox']:checked").eq(0).val();
		})
		//点击提交和保存按钮分别进行验证
			$("#btnSave,#btnSubmit").click(function(){
				/*获取运单号*/
				var billNumbers="";
				for(var i in selectedLists){
					if(i==0){
						billNumbers=selectedLists[i].goodsNumber;
					}else{
						billNumbers=billNumbers+","+selectedLists[i].goodsNumber;
					}
					
				}
				console.log(billNumbers);
				
				/*获取文本输入框的值*/
				var activityName = $("#activityName").val();
				var createTime = $("input[name='createTime']").val();
				var activityAddress = $("#activityAddress").val();
				var updateTime = $("input[name='updateTime']").val();
					headInfo.activityName = activityName;
					headInfo.createTime = createTime;
					headInfo.activityAddress = activityAddress;
					headInfo.updateTime = updateTime;
				var currentBtn=$(this).attr("id");
				var currentBtnVal=$(this).val();
				var that=$(this);
				if($("#inputForm").valid()){
					//头部四个输入框的值不为空验证之后，判断运单信息和奖品信息的表格是否有空，当其都不为空的时候再提交表单
					if($("#waybill-info tbody").html()!="" && $("#award-info tbody").html()!=""){
						loading('正在'+currentBtnVal+'，请稍等...');
						$(this).attr("disabled","disabled");  //当前按钮设置成禁用
						//进行ajax传值
						$.ajax({
						   url : "${ctx}/activityreport/activityReport/saveActivity",
						   type : "post",
						   dataType : "json",
						　data: {
							 currentClickObj:currentBtn,                  //是提交还是保存
						　　　  waybillInfos: objTransform(selectedLists),   //复选框选中的数据
							  activityName:activityName,
							  createTime:createTime,
							  activityAddress:activityAddress,
							  updateTime:updateTime,
						     awardInfos:objTransform(awardInfoLists),      //奖品信息表格数据
						     billNumbers:billNumbers
						　　},
						　　success : function(msg) {
						　　　　//要执行的代码，交互成功则关掉'正在加载'弹窗,并重新加载页面
						    alert(msg);
							closeLoading();
							location.href="${ctx}/activityreport/activityReport/list";
							//location.reload();
						　　},
						   error:function(){
							 setTimeout(function(){
								 closeLoading();
								 that.removeAttr("disabled");  //将当前按钮解除禁用
								 showTip("网络异常，请重新提交","","3000");
							 } ,10000)
							  
						  },
						});
					}
				};
			});
						
							/*点击运单信息按钮后的相关样式*/
							$('.waybill-button').click(function() {
//								清空奖品信息表格中的数据
								$("#award-info tbody").html("");
								//存放奖品信息表格的数组清空
								awardInfoLists.length=0;
								//让弹窗的提示框里的最大值改变
								$("#residue").html(getTotalBox());
								// 弹出复选框弹窗
								$('#myModal').show();
								$('.reveal-modal-bg').show();
	
							});
							/* 点击奖品信息按钮后的相关样式*/
							$('.award-button').click(function() {
									/*判断运单信息表格是否有内容*/
									if($("#waybill-info tbody").html()!=""){
										//计算剩余瓶数
										var remainingBottle=remainingAward(awardInfoLists,getTotalBox());
										if(remainingBottle>0){
											$('.reveal-modal-bg').css("background","rgba(0,0,0,.8)");
											$('#awardInfo').show();
											//将弹窗信息清空
											$("#prizeTotal,#onceAwardNumber").val("");
											$("#prizeRate").html("");
											
											$("#residue").html(remainingBottle);
											
											$("#reminder-residue,#reminder").show();
											//当弹窗一出来就进行交互
											getData();
											/*当一级下拉框里面的值改变时，重新获取value值*/
											$("#onelevel").change(function() {
												onelevelVal = $("#onelevel option:selected").val();
												getData();
											});
										}else{
											alert("运单货物数已全部中奖");
											$('#awardInfo').hide();
											$('.reveal-modal-bg').css("background","transparent");
										}
										
									}
		
								});
							
							/*点击确定按钮之后获取相关数据，并放到表格中*/
							$("#confirmSelecte-award").on("click", function() {
								
								/*奖品信息数据信息*/
								var awardPopInfo = new Object();
								awardPopInfo.prizeType=$("#onelevel option:selected").val();
								awardPopInfo.prizeTypeName=$("#onelevel option:selected").text();
								awardPopInfo.activePrizesText=$("#twolevel option:selected").text();
								awardPopInfo.prizeName=$("#twolevel option:selected").val();
								awardPopInfo.wxCardId=$("#twolevel option:selected").attr("cardid");
								awardPopInfo.prizeNumber=$("#prizeTotal").val();
								awardPopInfo.surplusNumber=$("#prizeTotal").val();
								awardPopInfo.winningNumber=$("#onceAwardNumber").val();
								awardPopInfo.winningProbability=$("#prizeRate").html().substring(0,$("#prizeRate").html().indexOf("%"));
								awardPopInfo.newProbability=$("#prizeRate").html().substring(0,$("#prizeRate").html().indexOf("%"));
								//判断弹窗里面的数据是否验证成功，成功则提交，否则弹窗不消失
								var ishasempty=isAwardPopInfoHasEmpty(awardPopInfo);
								if(ishasempty){
									awardInfoLists.push(awardPopInfo);
									console.log(awardInfoLists);
									$("#awardInfo").hide();
									$('.reveal-modal-bg').hide();
									CreateTableAwardInfo(1, 8, awardPopInfo);
								}
							});
							/* 获取当前运单弹框当前页中的全部数据，并且将其存入waybillList中 */
							<c:forEach items='${page}' var="signGoods">
							var waybill = new Object();
							waybill.goodsNumber = '${signGoods.goodsNumber}';
							waybill.dealerName = '${signGoods.dealerName}';
							waybill.varieties = '${signGoods.varieties}';
							waybill.totalBox = '${signGoods.totalBox}';
							waybill.createTime = '${signGoods.createTime}';
							waybill.poductsize='${signGoods.poductsize}';
							waybillList.push(waybill);
							</c:forEach>
							
							/*点击确定选择，获取复选框选中的数据*/
							$('#confirmSelecte').on('click',function() {
									/*每次点击确定按钮时要把之前的数组清空，避免数据在表格中重复出现*/
									selectedLists.length=0;
									$('#myModal').hide();
									$('.reveal-modal-bg').hide();
									$('input[type="checkbox"]:checked').each(function() {
										var id = $(this).val();
										var selectedObj = getSelectedInfo(id);
										//将勾选的复选框信息放入数组selectedLists中
										selectedLists.push(selectedObj);
									});
									var rowCount = selectedLists.length;
									if(rowCount>0) {
										$(".award-button").removeAttr("disabled");
									}else{
										$(".award-button").attr("disabled","disabled");
									}
									CreateTableWayBill(rowCount, 4,selectedLists)
	
						    });
				});
	/*交互传输数据封装的方法*/
	function getData() {
		var onelevelVal = $("#onelevel").val();
		$.ajax({
					type : "GET",
					url : "${ctx}/productinfo/productInfo/getAll",
					async : true,
					dataType : "json",
					data : {
						"selsect" : onelevelVal
					},
					success : function(data) {
						console.log(data);
						//根据拿到的数据动态创建下拉选项
						$("#twolevel").find("option").remove();
						for ( var i in data) {
							$("#twolevel").append("<option value="+data[i].id+" cardid="+data[i].cardId+">"
							+ data[i].name+ "</option>")
						}
						//获取被选中的option，并将selete框中的值改成当前选中的文本
						var checkedText=$("#twolevel option:checked").text();
						$("#s2id_twolevel>a>span:first-child").html(checkedText);
					},
					error : function() {
						alert("出错");
					}
				});
	}
	/*根据选中的复选框的id获取这一行的信息，*/
	function getSelectedInfo(id) {
		for ( var i in waybillList) {
			if (id == waybillList[i].goodsNumber) {
				return waybillList[i];
			}
		}

	}
	//判断对象里面是否有空
	function isAwardPopInfoHasEmpty(obj){
		for(var i in obj){
			if(obj[i]==""){
				return false;
			}
		}
		return true;
	}
	/*动态创建运单信息表格*/
	function CreateTableWayBill(rowCount, cellCount, content) {
		/*动态创建表格之前先将除表头外的信息清空*/
		$('#waybill-info table>tbody').html("");
		for (var i = 0; i < rowCount; i++) {
			var tr = $("<tr></tr>");
			tr.appendTo($('#waybill-info table>tbody'));
			var td1 = $('<td>' + content[i].goodsNumber
					+ '</td>');
			var td2 = $('<td>' + content[i].varieties
					+ '</td>');
			var td3 = $('<td>' + content[i].totalBox
					+ '</td>');
			var td4 =$('<td>' + content[i].poductsize
					+ '</td>');
			var td5 = $('<td>'
					+ getTaskTime(content[i].createTime)
					+ '</td>');
			td1.appendTo(tr);
			td2.appendTo(tr);
			td3.appendTo(tr);
			td4.appendTo(tr);
			td5.appendTo(tr);
		}
	};
	/* 动态创建奖品信息表格 */
	function CreateTableAwardInfo(rowCount, cellCount, content) {
	/*动态创建表格之前先将除表头外的信息清空*/
		var tr = $("<tr></tr>");
		tr.appendTo($('#award-info table>tbody'));
		var td1 = $('<td>' + content.activePrizesText
				+ '</td>');
		var td2 = $('<td>' + content.prizeNumber
				+ '</td>');
		var td3 = $('<td>' + content.prizeNumber
				+ '</td>');
		var td4 = $('<td>'
				+ content.prizeTypeName
				+ '</td>');
		var td5 = $('<td>'
				+ content.winningNumber
				+ '</td>');
		var td6 = $('<td>'
				+content.wxCardId
				+ '</td>');
		var td7 = $('<td>'
				+content.winningProbability+"%"
				+ '</td>');
		var td8 = $('<td>'
				+content.newProbability+"%"
				+ '</td>');
		var td9 = $("<td class='deleteTd' onclick='deleteTr(this)'>删除</td>");
		td1.appendTo(tr);
		td2.appendTo(tr);
		td3.appendTo(tr);
		td4.appendTo(tr);
		td5.appendTo(tr);
		td6.appendTo(tr);
		td7.appendTo(tr);
		td8.appendTo(tr);
		td9.appendTo(tr);
	};
	//当单次中奖数量失去焦点时，验证里面是否为空
	function onceAwardNumberVerification(obj){
		if($(obj).val()==""){
			$("#reminder").show();
		}else{
			$("#reminder").hide();
		}
	}
	/*当奖品总数文本框失去焦点时获取中奖率*/
	/*計算中獎率*/
	function blurJudge(){
		var tableRateTotal = parseFloat(probability(awardInfoLists).toFixed(2));
		var allowMaxRate = numSub(100, tableRateTotal);//当前允许的最大概率
		//计算剩余瓶数
		var remainingBottle=remainingAward(awardInfoLists,getTotalBox());
		//当输入的值大于0时
		if(parseInt($("#prizeTotal").val())>0){
			$("#reminder-residue").hide();
			//当输入值<=剩余总瓶数时,取其文本框中的值，否则直接将剩余总瓶数填在文本框中
			if(remainingBottle<parseInt($("#prizeTotal").val())){
				$("#prizeTotal").val(remainingBottle);
				//当直接给奖品总数赋予剩余值时，做判断，当值等于0时，直接将弹窗隐藏掉
				if(parseInt($("#prizeTotal").val())==0){
					$("#awardInfo").hide();
					$('.reveal-modal-bg').hide();
				}
			}
			var rate=parseFloat((parseInt($("#prizeTotal").val())/getTotalBox()*100).toFixed(2));
			//当概率大于允许的最大概率,否则直接将允许的最大概率填在文本框中
			if(rate > allowMaxRate) {
				rate = allowMaxRate;
			}
			$("#prizeRate").html(rate+"%");
	//当输入的值小于等于0时
		}else{
			$("#residue").html(remainingBottle);
			$("#reminder-residue").show();
		}
		
		
	}
	
	/*获取总瓶数=总箱数*每箱多少瓶，每箱多少瓶用n表示*/
	 function getTotalBox(){
		var totalBox=0;
		var totalbottles;
		var len=selectedLists.length;
		for(var i=0;i<len;i++){
			totalBox+=parseInt(selectedLists[i].totalBox)*parseInt(selectedLists[i].poductsize);
		}
		totalbottles=totalBox;
		return totalbottles;
	}
	/*记录剩余活动奖品数量,total表示总瓶数，remainingBottle表示剩余瓶数*/
	function remainingAward(arr,total){
		var  remainingBottle;
		var activeTotal=0;
		for(var i=0;i<arr.length;i++){
			activeTotal+=parseInt(arr[i].prizeNumber);
		}
		remainingBottle=total-activeTotal;
		return remainingBottle;
	};
	/*计算在表格中的概率以及弹窗概率之和*/
	function  probability(arr){
		var popProbability=0;
		for(var i in arr){
			popProbability = numAdd(popProbability,parseFloat(arr[i].winningProbability));
		}
		return popProbability;
	}

	/*点击每一行的delete就会删除该行的数据*/
	function deleteTr(obj){
		var index=$(obj).parent('tr').index();
		/*刪除对应的数组项*/
		awardInfoLists.splice(index,1);
		$(obj).parent('tr').remove();
	}
	
	/*转化时间格式*/
	function getTaskTime(strDate) {
		if (null == strDate || "" == strDate) {
			return "";
		}
		var dateStr = strDate.trim().split(" ");
		var strGMT = dateStr[0] + " " + dateStr[1] + " " + dateStr[2] + " "
				+ dateStr[5] + " " + dateStr[3] + " GMT+0800";
		var date = new Date(Date.parse(strGMT));
		var y = date.getFullYear();
		var m = date.getMonth() + 1;
		m = m < 10 ? ('0' + m) : m;
		var d = date.getDate();
		d = d < 10 ? ('0' + d) : d;
		var h = date.getHours();
		h = h < 10 ? ('0' + h) : h;
		var minute = date.getMinutes();
		minute = minute < 10 ? ('0' + minute) : minute;
		var second = date.getSeconds();
		second = second < 10 ? ('0' + second) : second;
		return y + "-" + m + "-" + d + " " + h + ":" + minute + ":" + second;
	}
	//numAdd为加法运算精度损失
	function numAdd(num1, num2) {
		var baseNum, baseNum1, baseNum2;
		try {
		baseNum1 = num1.toString().split(".")[1].length;
		} catch (e) {
		baseNum1 = 0;
		}
		try {
		baseNum2 = num2.toString().split(".")[1].length;
		} catch (e) {
		baseNum2 = 0;
		}
		baseNum = Math.pow(10, Math.max(baseNum1, baseNum2));
		return (num1 * baseNum + num2 * baseNum) / baseNum;
		}; 
		//numSub为减法运算损失精度损失
	function numSub(num1, num2) {
		var baseNum, baseNum1, baseNum2;
		var precision;// 精度
		try {
			baseNum1 = num1.toString().split(".")[1].length;
		} catch (e) {
			baseNum1 = 0;
		}
		try {
			baseNum2 = num2.toString().split(".")[1].length;
		} catch (e) {
			baseNum2 = 0;
		}
		baseNum = Math.pow(10, Math.max(baseNum1, baseNum2));
		precision = (baseNum1 >= baseNum2) ? baseNum1 : baseNum2;
		return ((num1 * baseNum - num2 * baseNum) / baseNum).toFixed(precision);
	};
</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/activityreport/activityReport/">活动报备列表</a></li>
		<li class="active"><a
			href="${ctx}/activityreport/activityReport/form?id=${activityReport.id}">活动报备<shiro:hasPermission
					name="activityreport:activityReport:edit">${not empty activityReport.id?'修改':'添加'}</shiro:hasPermission>
				<shiro:lacksPermission name="activityreport:activityReport:edit">查看</shiro:lacksPermission></a></li>
	</ul>
	<br />
	<form:form id="inputForm" modelAttribute="activityReport"
		action="${ctx}/activityreport/activityReport/save" method="post"
		class="form-horizontal">
		<form:hidden path="id" />
		<sys:message content="${message}" />
		<div class="control-group">
			<div class="active-name">
				<label class="control-label">活动名称：</label>
				<div class="controls">
					<form:input path="activityName" name="activityName"
						htmlEscape="false" maxlength="255" class="input-xlarge required" />
					<span class="help-inline"><font color="red">*</font> </span>
				</div>
			</div>
			<div class="create-time">
				<label class="control-label">开始时间：</label>
				<div class="controls">
					<input name="createTime" type="text" readonly="readonly"
						maxlength="20" class="input-medium Wdate required"
						value="<fmt:formatDate value="${activityReport.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
						onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});" />
					<span class="help-inline"><font color="red">*</font> </span>
				</div>
			</div>
		</div>

		<div class="control-group">
			<div class="active-address">
				<label class="control-label">活动地址：</label>
				<div class="controls">
					<form:input path="activityAddress" name="activityAddress"
						htmlEscape="false" maxlength="255" class="input-xlarge required" />
					<span class="help-inline"><font color="red">*</font> </span>
				</div>
			</div>
			<div class="modification-time">
				<label class="control-label">结束时间：</label>
				<div class="controls">
					<input name="updateTime" type="text" readonly="readonly"
						maxlength="20" class="input-medium Wdate required"
						value="<fmt:formatDate value="${activityReport.updateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
						onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});" />
					<span class="help-inline"><font color="red">*</font> </span>
				</div>
			</div>
		</div>



		<a id="abiaoqian" href="${ctx}/productinfo/productInfo/list"
			class="big-link" data-reveal-id="myModal" data-animation="none">
			<input type="button" class="waybill-button" value="运单信息"
			name="waybill-button">
		</a>
		<div id="waybill-info">
			<table border="1">
				<thead>
					<tr>
						<th>发货单号</th>
						<th>品种</th>
						<th>总箱数</th>
						<th>规格</th>
						<th>发货时间</th>
					</tr>
				</thead>
				<tbody></tbody>
			</table>
		</div>
		<a id="abiaoqian-award" href="${ctx}/productinfo/productInfo/list"
			class="big-link" data-reveal-id="awardInfo" data-animation="none">
			<input type="button" class="award-button" value="奖品信息"
			name="award-button" disabled="disabled">
		</a>
		<div id="award-info">
			<table border="1">
				<thead>
					<tr>
						<th>奖品名称</th>
						<th>奖品总数</th>
						<th>剩余数量</th>
						<th>奖品类型</th>
						<th>单次中奖数量</th>
						<th>微信卡卷ID</th>
						<th>原始中奖概率</th>
						<th>当前中奖概率</th>
						<th>操作</th>
					</tr>
				</thead>
				<tbody>
				</tbody>
			</table>
		</div>
		<!-- 提交、保存、返回按钮 -->
		<div class="form-actions">
			<!-- <input id="btnSave" class="btn btn-primary" type="button" value="保 存"
				name="btnSave" />&nbsp;  -->
			<input id="btnCancel" class="btn"
				type="button" value="返 回" name="btnCancel" onclick="history.go(-1)" />&nbsp;
			<input id="btnSubmit" class="btn btn-primary" type="button"
				value="提交审核" name="btnSubmit" />&nbsp;
		</div>

		<!-- 运单信息弹出内容 -->
		<div id="myModal" class="reveal-modal">
			<div>
				<h1 style="float: left;">运单中心</h1>
				<input id="confirmSelecte" name="confirmSelecte"
					style="margin-left: 60%" type="button" class="btn btn-primary"
					value="确定选择" />
			</div>
			<div style="height: 460px; overflow-y: auto; margin: 16px auto">
				<table id="contentTable"
					class="table table-striped table-bordered table-condensed">
					<thead>
						<tr>
							<th>发货单号</th>
							<th>渠道商名称</th>
							<th>品种名称</th>
							<th>总箱数</th>
							<th>发货时间</th>
							<th>规格</th>
							<th>操作</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${page}" var="signGoods">
							<tr>
								<td>${signGoods.goodsNumber}</td>
								<td>${signGoods.dealerName}</td>
								<td>${signGoods.varieties}</td>
								<td>${signGoods.totalBox}</td>
								<td>${signGoods.createTime}</td>
								<%-- <td><fmt:formatDate value="${signGoods.createTime}"
										pattern="yyyy-MM-dd HH:mm:ss" /></td> --%>
								<td>${signGoods.poductsize}</td>
								<td><input type="checkbox" value="${signGoods.goodsNumber}"
									name="checkbox" /></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
			<a class="close-reveal-modal">&#215;</a>
		</div>
		<!-- 弹出内容 -->

		<!-- 奖品信息弹出内容 -->
		<div id="awardInfo" class="reveal-modal">
			<div>
				<h1>奖品中心</h1>
				<div id="awardTableInfo">
					<div>
						<label>奖品类型：</label> <select style="width: 200px" id="onelevel"
							name="onelevel">
							<option value="1">卡券商品</option>
							<option value="2">积分奖品</option>
							<option value="3">物流商品</option>
						</select>
					</div>
					<br> <br>
					<div>
						<label>奖品名称：</label> <select style="width: 200px" id="twolevel"
							name="twolevel">
						</select>
					</div>
					<br> <br>
					<div>
						奖品总数：<input type="text" id="prizeTotal" name="prizeTotal" onblur="blurJudge()" /> 
							<span id="reminder-residue" style="color: red">*请输入大于0小于<span
							id="residue"></span>的值
						</span>
					</div>
					<br> <br>
					<div>
						中奖率：<span id="prizeRate"></span>
					</div>
					<br> <br>
					<div>
						单次中奖数量：<input type="text" id="onceAwardNumber"
							name="onceAwardNumber" onblur="onceAwardNumberVerification(this)"/> 
							<span id="reminder" style="color: red">*请输入值</span>
					</div>
				</div>

				<input id="confirmSelecte-award" name="confirmSelecte-award"
					style="margin-left: 40%" type="button" class="btn btn-primary"
					value="确定选择" />
			</div>

			<a class="close-reveal-modal">&#215;</a>
		</div>
		<!-- 弹出内容 -->
	</form:form>
</body>
<script type="text/javascript">
	
</script>
</html>

