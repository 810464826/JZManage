<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>产品积分关系管理</title>
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
			
			var kindName=$("#s2id_kindNameSel .select2-chosen").html();
			var degree=$("#s2id_degreeSel .select2-chosen").html();
			var volume=$("#s2id_volumeSel .select2-chosen").html();
			var spec=$("#s2id_specSel .select2-chosen").html();
			var integral;
			$("#kindNameSel").change(function() {
				kindName = $("#kindNameSel option:selected").text();
			});
			$("#degreeSel").change(function() {
				degree = $("#degreeSel option:selected").text();
			});
			$("#volumeSel").change(function() {
				volume = $("#volumeSel option:selected").text();
			});
			$("#specSel").change(function() {
				spec = $("#specSel option:selected").text();
			});
			$("#btnSubmit").on("click",function(){
				integral=$("#integralNum").val();
				/* alert("kindName"+kindName+"degree"+degree+"volume"+volume+"spec"+spec+"integral"+integral); */
				$("#inputForm").attr("action","${ctx}/productintegral/productintegral/saveIntegral?kindName="+kindName+"&degree="+degree+"&volume="+volume+"&spec="+spec+"&integral="+integral);
			});
		});
		
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/productintegral/productintegral/">产品积分关系列表</a></li>
		<li class="active"><a href="${ctx}/productintegral/productintegral/form?id=${productintegral.id}">产品积分关系<shiro:hasPermission name="productintegral:productintegral:edit">${not empty productintegral.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="productintegral:productintegral:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="productintegral" action="${ctx}/productintegral/productintegral/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">产品名称：</label>
			<%-- <div class="controls">
				<form:input path="name" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div> --%>
			<select id="kindNameSel">
				<c:forEach items="${allKindName}" var="kindName">
				<option>${kindName.kindName}</option>
				</c:forEach>
			</select>
		</div>
		<div class="control-group">
			<label class="control-label">度数：</label>
			<%-- <div class="controls">
				<form:input path="degree" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div> --%>
			<select id="degreeSel">
				<c:forEach items="${allDegree}" var="degree">
				<option>${degree.degree}</option>
				</c:forEach>
			</select>
		</div>
		<div class="control-group">
			<label class="control-label">容量：</label>
			<%-- <div class="controls">
				<form:input path="volume" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div> --%>
			<select id="volumeSel">
				<c:forEach items="${allVolume}" var="volume">
				<option>${volume.volume}</option>
				</c:forEach>
			</select>
		</div>
		<div class="control-group">
			<label class="control-label">规格：</label>
			<%-- <div class="controls">
				<form:input path="spec" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div> --%>
			<select id="specSel">
				<c:forEach items="${allSpec}" var="spec">
				<option>${spec.spec}</option>
				</c:forEach>
			</select>
		</div>
		<div class="control-group">
			<label class="control-label">积分：</label>
			<input type="text" id="integralNum" value="" maxlength="10"/>
			<%-- <div class="controls">
				<form:input path="integreal" htmlEscape="false" maxlength="64" class="input-xlarge "/>
				<input type="text" id="integralNum" value="" />
			</div> --%>
		</div>
	<%-- 	<div class="control-group">
			<label class="control-label">备用字段一：</label>
			<div class="controls">
				<form:input path="spareone" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备用字段二：</label>
			<div class="controls">
				<form:input path="sparetwo" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div> --%>
		<div class="form-actions">
			<shiro:hasPermission name="productintegral:productintegral:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>