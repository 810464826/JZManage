<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>经销商管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#name").focus();
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
			var core=$("#core option").eq(0).val();
			$("#core").change(function() {
				core = $("#core option:selected").val();
			});
			var shopType=$("#shopType option").eq(0).val();
			$("#shopType").change(function() {
				shopType = $("#shopType option:selected").val();
			});
			$("#btnSubmit").on("click",function(){
				var telphone=$("#telphone").val();
				var name=$("#name").val();
				var shopName=$("#shopName").val();
				var saveUrl="${ctx}/sys/office/storeSave?core="+core+"&telphone="+telphone+"&name="+name+"&shopName="+shopName+"&shopType="+shopType;
				$("#inputForm").attr("action",saveUrl);
			})
		});
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/sys/office/storelist">门店列表</a></li>
		<li class="active"><a href="${ctx}/sys/office/storeAdd" >门店添加</a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="office" action="" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">上级经销商:</label>
			<div class="controls">
                <sys:treeselect id="office" name="parent.id" value="${office.parent.id}" labelName="parent.name" labelValue="${office.parent.name}"
					title="机构" url="/sys/office/treeData" extId="${office.id}" cssClass="" allowClear="${office.currentUser.admin}"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">归属区域:</label>
			<div class="controls">
                <sys:treeselect id="area" name="area.id" value="${office.area.id}" labelName="area.name" labelValue="${office.area.name}"
					title="区域" url="/sys/area/treeData" cssClass="required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">终端店名称:</label>
			<div class="controls">
				<input type="text" maxlength="50" class="required" id="shopName" />
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>

		<div class="control-group">
			<label class="control-label">终端店类型:</label>
			<div class="controls">
				<%-- <form:select path="type" class="input-medium">
					<form:options items="${fns:getDictList('sys_office_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select> --%>
				<select id="shopType">
					<option>餐饮店</option>
					<option>烟酒店</option>
					<option>商超店</option>
					<option>KA专卖店</option>
					<option>夜店</option>
				</select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">核心店/非核心店:</label>
			<div class="controls">
				<%-- <form:select path="type" class="input-medium">
					<form:options items="${fns:getDictList('sys_office_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select> --%>
				<select id="core">
					<option>核心店</option>
					<option>非核心店</option>
				</select>
			</div>
		</div>
		<div class="control-group" style="display:none;">
			<label class="control-label">是否可用:</label>
			<div class="controls" >
				<form:select path="useable">
					<form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline">“是”代表此账号允许登陆，“否”则表示此账号不允许登陆</span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">联系人姓名:</label>
			<div class="controls">
				<input  type="text" maxlength="50" id="name" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">联系人手机号:</label>
			<div class="controls">
				<input type="text" maxlength="50" id="telphone" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">联系地址:</label>
			<div class="controls">
				<form:input path="address" htmlEscape="false" maxlength="50"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注:</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="3" maxlength="200" class="input-xlarge"/>
			</div>
		</div>

		<div class="form-actions">
			<shiro:hasPermission name="sys:office:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>