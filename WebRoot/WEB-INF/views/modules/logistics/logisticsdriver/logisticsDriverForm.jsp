<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>物流司机管理</title>
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
		<li><a href="${ctx}/logisticsdriver/logisticsDriver/">物流司机列表</a></li>
		<li class="active"><a href="${ctx}/logisticsdriver/logisticsDriver/form?id=${logisticsDriver.id}">物流司机<shiro:hasPermission name="logisticsdriver:logisticsDriver:edit">${not empty logisticsDriver.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="logisticsdriver:logisticsDriver:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="logisticsDriver" action="${ctx}/logisticsdriver/logisticsDriver/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">司机名称：</label>
			<div class="controls">
				<form:input path="driverName" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">电话：</label>
			<div class="controls">
				<form:input path="phone" htmlEscape="false" maxlength="18" class="input-xlarge  digits"/>
			</div>
		</div>
		<form:input path="truckId" type="hidden" value="${truckid}" htmlEscape="false" maxlength="64" class="input-medium"/>
		<div class="form-actions">
			<shiro:hasPermission name="logisticsdriver:logisticsDriver:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>