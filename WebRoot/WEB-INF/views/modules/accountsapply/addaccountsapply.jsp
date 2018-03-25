<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>对账管理管理</title>
	<meta name="decorator" content="default"/>
	<style>
	#reconciliation{
		height: 410px;
		overflow-y: auto;
		overflow-x: hidden;
		margin-top: 10px;
		margin-bottom:50px;
	}
	#reconciliation,#reconciliation table{
		width:800px;
		cellpadding: 0;
		text-align: center;
		cellspacing: 0;
	}
	#reconciliation table thead{
		height:30px;
		line-height:30px;
		background:#f5f5f5;
	}
	</style>
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
			
			$("#btnSubmit").on("click",function(){
				var loaclid = $("#localid").val();
				var textarea=$(".input-xxlarge").val();
				location.href="${ctx}/accountinfo/accounts/saveAcc?loaclid="+loaclid+"&textarea="+textarea;
			})
			
			
		});
		function aaa(id){
			$(".confirm").attr("disabled","disabled");
			$("#localid").val(id);
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/accountinfo/accounts/">对账管理列表</a></li>
		<li class="active"><a href="${ctx}/accountinfo/accounts/form?id=${accounts.id}">对账管理<shiro:hasPermission name="accountinfo:accounts:edit">${not empty accounts.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="accountinfo:accounts:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="accounts" action="${ctx}/accountinfo/accounts/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<input type="hidden" id="localid" value=""/>
		<div id="reconciliation">
			<table border="1" bordercolor="#ccc">
				<thead>
					<tr>
						<th>经销商</th>
						<th>总计</th>
						<th>经销商是否在线</th>
						<th>操作</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${localList}" var="local" >
						<tr>
							<td>${local.cancelOffice}<input type="hidden" value="${local.id}" /></td>
							<td>${local.cancelNumber}</td>
							<c:if test="${local.existence == '1'}">
								<td>在线</td>
							</c:if>
							<c:if test="${local.existence == '0'}">
								<td>离线</td>
							</c:if>
							<td><input type="button" value="确定" class="confirm" onclick="aaa('${local.id}')"/></td>
						</tr>					
					</c:forEach>
				</tbody>
			</table>
		</div>
		
		
		<div class="control-group">
			<label class="control-label">对账备注：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
			</div>
		</div>
		<%-- <div class="remark">
		<strong>备注：</strong><form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
		</div> --%>
		<div class="form-actions">
			<shiro:hasPermission name="accountinfo:accounts:edit"><input id="btnSubmit" class="btn btn-primary" type="button" value="添加对账"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>