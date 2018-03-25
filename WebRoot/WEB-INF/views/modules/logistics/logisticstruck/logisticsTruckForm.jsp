<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>物流车辆管理</title>
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
		<li><a href="${ctx}/logisticstruck/logisticsTruck/">物流车辆列表</a></li>
		<li class="active"><a href="${ctx}/logisticstruck/logisticsTruck/form?id=${logisticsTruck.id}">物流车辆<shiro:hasPermission name="logisticstruck:logisticsTruck:edit">${not empty logisticsTruck.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="logisticstruck:logisticsTruck:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="logisticsTruck" action="${ctx}/logisticstruck/logisticsTruck/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">车牌号：</label>
			<div class="controls">
				<form:input path="carNumber" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">载重量：</label>
			<div class="controls">
				<form:input path="load" htmlEscape="false" maxlength="18" class="input-xlarge  digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">汽车名称：</label>
			<div class="controls">
				<form:input path="carName" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<form:input path="firmId" type="hidden" value="${firmid}" htmlEscape="false" maxlength="64" class="input-medium"/>
		<div class="form-actions">
			<shiro:hasPermission name="logisticstruck:logisticsTruck:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>