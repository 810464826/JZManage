<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>活动报备管理</title>
<meta name="decorator" content="default" />
<script type="text/javascript" src="/JZManage/static/modules/js/jquery.reveal.js"></script>
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
	height:40px;
	line-height:40px;
	background:#f5f5f5;
}
#waybill-info table tr, #award-info table tr{
	height:30px;
	line-height:30px;
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
.deleteTd{
	color:red;
}
.deleteTd:hover {
	cursor: pointer;
}
.infoName{
	font-size:16px;
	font-weight:600;
	margin-top:30px;
}
.currentRate{
	width:90%;
	/* border:none !important; */
	text-align:center;
}
.remainingNum{
	width:90%;
	text-align:center;
}
</style>
<script type="text/javascript">
//运单信息数组
var totalBottleArr=[];
//奖品信息数组
var awardArr=[];
//失去焦点的对象，包含索引值和最大允许值
var blurNewObj={};
//总瓶数
var total;

//将对象转化成字符串
function objTransform(jsonobj){
	return JSON.stringify(jsonobj);
}
/*获取总瓶数=箱数*每箱多少瓶，再累加*/
	function getTotalBox(arr){
		var totalbottles=0;
		var len=arr.length;
		for(var i=0;i<len;i++){
			//每箱乘以每箱的规格，再累加
			totalbottles+=parseInt(arr[i].totalBox)*parseInt(arr[i].poductsize);
		}
		return totalbottles;
	}
	//
	
	/*方法一加到行内onblur 给每个输入框添加失去焦点的方法
      	1、判断当输入值大于允许的最大值时，直接在文本中显示最大值
      	2、在失去焦点的时候让当前中奖概率随着改变 */
	/* function blurJuge(obj){
		//找到索引值
		for(var i in awardArr){
			if(awardArr[i].id==obj.name){
				blurNewObj.index=i;
				blurNewObj.allowMax=awardArr[i].surplusNumber;
			}
		}
		//console.log(blurNewObj);
		//当输入的值大于允许的最大值则直接默认最大值为当前的值
		if(parseInt(blurNewObj.allowMax)<parseInt($(".remainingNum").eq(blurNewObj.index).val())){
			$(".remainingNum").eq(blurNewObj.index).val(blurNewObj.allowMax);
		}
		var currentRate=($(".remainingNum").eq(blurNewObj.index).val()/total*100).toFixed(2);
		$(".currentRte").eq(blurNewObj.index).html(currentRate);
	}  */
	
	$(document).ready(function(){

		//点击确定的时候对象重新赋值
		$("#btnSubmit").on("click",function(){
			for(var i in awardArr){
				awardArr[i].surplusNumber=$(".remainingNum").eq(i).val();
				awardArr[i].newProbability=$(".currentRte").eq(i).html();
			}
			var activityId=$("#activityId").val();
			var awardArrString=objTransform(awardArr);
			//console.log("activityId"+activityId+"----"+"awardArrString"+awardArrString);
			//location.href="${ctx}/activityreport/activityReport/editSave?activityId=,"+activityId+",&awardArr="+awardArrString;
			//console.log(awardArr);
			/* 交互数据 */
			$.ajax({
				   url : "${ctx}/activityreport/activityReport/editSave",
				   type : "POST",
				   dataType : "json",
				   data: {
					   activityId:activityId,
					   awardArr:objTransform(awardArr)      //奖品信息表格数据
				　　},
				　　 success : function(data) {
				　　　　//要执行的代码，交互成功则关掉'正在加载'弹窗,并重新加载页面
						closeLoading();
						location.href="${ctx}/activityreport/activityReport/list";
						//location.reload();
				　　},
				   error:function(){
					/*  setTimeout(function(){
						 closeLoading();
						 showTip("网络异常，请重新提交","","3000");
					 } ,10000) */
					  
				  } 
				});
		})
		//让剩余数量的输入值只能是数字
		$("input[type='text']").on("input propertychange",function(){
			$(this).val($(this).val().replace(/\D/g,''));
		});
		
		
		
		/*方法二 给每个输入框添加失去焦点的方法
      	1、判断当输入值大于允许的最大值时，直接在文本中显示最大值
      	2、在失去焦点的时候让当前中奖概率随着改变 */
		$(".remainingNum").each(function(index,value){
			$(this).blur(function(){
				if(parseInt(awardArr[index].surplusNumber)<parseInt($(this).val())){
					$(this).val(awardArr[index].surplusNumber);
				}
				var currentRate=(($(this).val()/total)*100).toFixed(2)+"%";
				console.log(currentRate);
				$(".currentRte").eq(index).html(currentRate);
				
			})
		})
		
		//获取运单信息中的总箱数和规格
		<c:forEach items="${sign}" var="signGoods">
		var waybill = new Object();
		waybill.poductsize = '${signGoods.poductsize}';
		waybill.totalBox = '${signGoods.totalBox}';
		totalBottleArr.push(waybill);
		</c:forEach>
		//获取奖品信息中的数据
		<c:forEach items="${turn}" var="turnlist">
		var award = new Object();
		award.id = '${turnlist.id}';
		award.prizeName = '${turnlist.prizeName}';
		award.prizeNumber = '${turnlist.prizeNumber}';
		award.surplusNumber = '${turnlist.surplusNumber}';
		award.prizeType = '${turnlist.prizeType}';
		award.winningNumber = '${turnlist.winningNumber}';
		award.wxCardId = '${turnlist.wxCardId}';
		award.winningProbability = '${turnlist.winningProbability}';
		award.newProbability = '${turnlist.newProbability}';
		awardArr.push(award);
		</c:forEach>
		//console.log(awardArr);
		
		//页面一加载完毕，就计算总瓶数totalbottles
		total=getTotalBox(totalBottleArr);
		//alert(total);
		
		
		
	
	})
</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/activityreport/activityReport/">活动报备列表</a></li>
		<li class="active"><a
			href="${ctx}/activityreport/activityReport/form?id=${activityReport.id}">活动报备<shiro:hasPermission
					name="activityreport:activityReport:edit">查看</shiro:hasPermission>
				<shiro:lacksPermission name="activityreport:activityReport:edit">查看</shiro:lacksPermission></a></li>
	<input type="hidden" value="${activityReport.id}" id="activityId"/>
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
					<form:input path="activityName" name="activityName" htmlEscape="false" maxlength="255"
						class="input-xlarge required" readonly="true" />
					<span class="help-inline"><font color="red">*</font> </span>
				</div>
			</div>
			<div class="create-time">
				<label class="control-label">创建时间：</label>
				<div class="controls">
					<input name="createTime" type="text" readonly="readonly"
						maxlength="20" class="input-medium Wdate required"
						value="<fmt:formatDate value="${activityReport.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
					<span class="help-inline"><font color="red">*</font> </span>
				</div>
			</div>
		</div>

		<div class="control-group">
			<div class="active-address">
				<label class="control-label">活动地址：</label>
				<div class="controls">
					<form:input path="activityAddress" name="activityAddress" htmlEscape="false"
						maxlength="255" class="input-xlarge required" readonly="true" />
					<span class="help-inline"><font color="red">*</font> </span>
				</div>
			</div>
			<div class="modification-time">
				<label class="control-label">修改时间：</label>
				<div class="controls">
					<input name="updateTime" type="text" readonly="readonly"
						maxlength="20" class="input-medium Wdate required"
						value="<fmt:formatDate value="${activityReport.updateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
					<span class="help-inline"><font color="red">*</font> </span>
				</div>
			</div>
		</div>



		<!-- 运单信息表格 -->
		<p class="infoName">运单信息</p>
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
				<tbody>
					<c:forEach items="${sign}" var="signGoods"> 
						<tr>
							<td>${signGoods.goodsNumber}</td>
							<td>${signGoods.dealerName}</td>
							<td>${signGoods.totalBox}</td>
							<td>${signGoods.poductsize}</td>
							<td>${signGoods.outTime}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		<!-- 奖品信息表格 -->
		<p class="infoName">奖品信息</p>
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
					</tr>
				</thead>
				<tbody>
					 <c:forEach items="${turn}" var="turnlist">
						<tr>
							<td><input id="prizeId" type="hidden" value="${turnlist.id}"/>${turnlist.name}</td>  <!-- 奖品名称 -->
							<td>${turnlist.prizeNumber}</td>    <!-- 奖品总数 -->
							<td><input type="text" class="remainingNum" value="${turnlist.surplusNumber}" name="${turnlist.id}" /></td>     <!-- 剩余数量 -->
							<td>${turnlist.prizeType}</td>     <!-- 奖品类型 -->
							<td>${turnlist.winningNumber}</td>     <!-- 单次中奖数量 -->
							<td>${turnlist.wxCardId}</td>     <!-- 微信卡卷ID -->
							<td>${turnlist.winningProbability}%</td>     <!-- 原始中奖概率 -->
							<td class="currentRte">${turnlist.newProbability}%</td>     <!-- 当前中奖概率 -->
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		<!-- 确定、返回按钮 -->
		<div class="form-actions">
			<input id="btnCancel" class="btn" type="button" value="返 回" name="btnCancel" onclick="history.go(-1)" />&nbsp;
		    <input id="btnSubmit" class="btn btn-primary" type="button" value="确定" name="btnSubmit" />&nbsp;
		</div>

	</form:form>
</body>

</html>