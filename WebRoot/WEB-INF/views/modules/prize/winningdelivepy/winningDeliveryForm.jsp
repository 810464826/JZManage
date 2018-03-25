<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>奖品派送管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
		});
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/winningdelivepy/winningDelivery/">奖品派送列表</a></li>
		<li class="active"><a href="${ctx}/winningdelivepy/winningDelivery/form?id=${winningDelivery.id}">奖品派送<shiro:hasPermission name="winningdelivepy:winningDelivery:edit">${not empty winningDelivery.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="winningdelivepy:winningDelivery:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="winningDelivery" action="${ctx}/winningdelivepy/winningDelivery/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group" style="display: none;">
			<label class="control-label">呢称：</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" maxlength="255" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group" style="display: none;">
			<label class="control-label">中奖方式：</label>
			<div class="controls">
				<form:select path="winningWay" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('winning_way')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group" style="display: none;">
			<label class="control-label">奖品名称：</label>
			<div class="controls">
				<form:input path="prizeName" htmlEscape="false" maxlength="255" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group" >
			<label class="control-label">快递单号：</label>
			<div class="controls">
				<form:input path="expressNumber" htmlEscape="false" maxlength="64" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">快递公司：</label>
			<div class="controls">
				<form:input path="express" id="express_id" type="hidden" htmlEscape="false" maxlength="64" class="input-xlarge required"/>
				<select style="width: 280px;" id="select_express" onchange="expressID()">
					<option value="${logistics.id}">${logistics.logisticsName}</option>
					<c:forEach items="${findList}" var="logi">
						<option value="${logi.id}">${logi.logisticsName}</option>
					</c:forEach>
				</select>
			</div>
		</div>
		<div class="control-group" style="display: none;">
			<label class="control-label">收获地址：</label>
			<div class="controls">
				<form:input path="collectAddress" htmlEscape="false" maxlength="255" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group" style="display: none;">
			<label class="control-label">收货人：</label>
			<div class="controls">
				<form:input path="collectUser" htmlEscape="false" maxlength="255" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group" style="display: none;">
			<label class="control-label">收货电话：</label>
			<div class="controls">
				<form:input path="collectPhone" htmlEscape="false" maxlength="18" class="input-xlarge required digits"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">发货时间：</label>
			<div class="controls">
				<input name="deliveryTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					value="<fmt:formatDate value="${winningDelivery.deliveryTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group" style="display: none;">
			<label class="control-label">中奖时间：</label>
			<div class="controls">
				<input name="winningTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					value="<fmt:formatDate value="${winningDelivery.winningTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group" >
			<label class="control-label">配送状态：</label>
			<div class="controls">
				<form:select path="distributionStatus" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('distribution_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group" style="display: none;">
			<label class="control-label">openid：</label>
			<div class="controls">
				<form:input path="openid" htmlEscape="false" maxlength="64" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="winningdelivepy:winningDelivery:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
	<script type="text/javascript">
		function expressID(){
			var express =$('#select_express').val();
			$('#express_id').val(express);
		}
	</script>
</body>
</html>