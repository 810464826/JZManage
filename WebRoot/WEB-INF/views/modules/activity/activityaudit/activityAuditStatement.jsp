<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>活动审核管理</title>
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
.remark{
	margin-top:50px;
}
</style>
<script type="text/javascript">
$(document).ready( function() {
	$("#btnSubmit").click(function(){
		var Remarks = $("#Remarks").val();
		var activityAuditId =$("#activityAuditId").val();
		var state = "2";
		$.ajax({
			   url : "${ctx}/activityaudit/activityAudit/saveActivityAudit",
			   type : "post",
		       dataType : "json",
				　data: {
					Remarks:Remarks,                 
				　　activityAuditId:activityAuditId,         
				　　state: state   
				　　},
				　　success : function(msg) {
							location.href="${ctx}/activityaudit/activityAudit/list";
				　　},
				   error:function(){
					 setTimeout(function(){
						 closeLoading();
						 that.removeAttr("disabled");  //将当前按钮解除禁用
						 showTip("网络异常，请重新提交","","3000");
					 } ,10000)
					  
				  },
				});

	})
	$("#reject").click(function(){
		var Remarks = $("#Remarks").val();
		var activityAuditId =$("#activityAuditId").val();
		var state = "3";
		$.ajax({
			   url : "${ctx}/activityaudit/activityAudit/saveActivityAudit",
			   type : "post",
			   dataType : "json",
				　data: {
					Remarks:Remarks,                 
				　　activityAuditId:activityAuditId,         
				　　state: state   
				　　},
				　　success : function(msg) {
							location.href="${ctx}/activityaudit/activityAudit/list";
				　　},
				   error:function(){
					 setTimeout(function(){
						 closeLoading();
                         that.removeAttr("disabled");  //将当前按钮解除禁用
						 showTip("网络异常，请重新提交","","3000");
					 } ,10000)
					  
				  },
				});

	})
})


</script>
</head>
<body>
	<form:form id="inputForm" modelAttribute="activityAudit"
		action="${ctx}/activityreport/activityReport/save" method="post"
		class="form-horizontal">
		<sys:message content="${message}" />
		<input type="hidden" id="activityAuditId" value="${activityAudit.id}"/>
		<div class="control-group" style="margin-top:20px">
			<div class="active-name">
				<label class="control-label">活动名称：</label>
				<div class="controls">
					<form:input path="activityName" name="activityName" htmlEscape="false" maxlength="255"
						class="input-xlarge required" readonly="true" />
				</div>
			</div>
			<div class="create-time">
				<label class="control-label">创建时间：</label>
				<div class="controls">
					<input name="createTime" type="text" readonly="readonly"
						maxlength="20" class="input-medium Wdate required"
						value="<fmt:formatDate value="${activityAudit.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
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
				</div>
			</div>
			<div class="modification-time">
				<label class="control-label">结束时间：</label>
				<div class="controls">
					<input name="updateTime" type="text" readonly="readonly"
						maxlength="20" class="input-medium Wdate required"
						value="<fmt:formatDate value="${activityAudit.updateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
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
							<td>${signGoods.createTime}</td>
							<%-- <td><fmt:formatDate value="${signGoods.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td> --%>
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
							<td>${turnlist.surplusNumber}</td>     <!-- 剩余数量 -->
							<td>${turnlist.prizeType}</td>     <!-- 奖品类型 -->
							<td>${turnlist.winningNumber}</td>     <!-- 单次中奖数量 -->
							<td>${turnlist.wxCardId}</td>     <!-- 微信卡卷ID -->
							<td>${turnlist.winningProbability}</td>     <!-- 原始中奖概率 -->
							<td>${turnlist.newProbability}</td>     <!-- 当前中奖概率 -->
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		
		<div class="remark">
		<strong>备注：</strong><textarea id="Remarks"  rows="5"></textarea>
		</div>
		<!-- 确定、返回按钮 -->
		<div class="form-actions">
		    <input id="btnSubmit" class="btn btn-primary" type="button" value="通过" name="btnSubmit" />&nbsp;
		    <input id="reject" class="btn btn-primary" type="button" value="驳回" name="btnSubmit" />&nbsp;
		    <input id="btnCancel" class="btn" type="button" value="返 回" name="btnCancel" onclick="history.go(-1)" />&nbsp;
		</div>
	</form:form>
</body>

</html>