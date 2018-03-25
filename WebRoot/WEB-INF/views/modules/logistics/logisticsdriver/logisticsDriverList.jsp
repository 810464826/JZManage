<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>物流司机管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/logisticsdriver/logisticsDriver/">物流司机列表</a></li>
		<shiro:hasPermission name="logisticsdriver:logisticsDriver:edit"><li><a href="${ctx}/logisticsdriver/logisticsDriver/form?truckId=${truck}">物流司机添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="logisticsDriver" action="${ctx}/logisticsdriver/logisticsDriver/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>司机名称：</label>
				<form:input path="driverName" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>电话：</label>
				<form:input path="phone" htmlEscape="false" maxlength="18" class="input-medium"/>
			</li>
			<form:input path="truckId" type="hidden" value="${truck}" htmlEscape="false" maxlength="64" class="input-medium"/>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li style="margin-left: 150px;"><input id="btnSubmit" class="btn btn-primary" type="button" onclick="backGS()" value="返回物流公司"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>司机名称</th>
				<th>电话</th>
				<th>创建时间</th>
				<shiro:hasPermission name="logisticsdriver:logisticsDriver:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="logisticsDriver">
			<tr>
				<td><a href="${ctx}/logisticsdriver/logisticsDriver/form?id=${logisticsDriver.id}">
					${logisticsDriver.driverName}
				</a></td>
				<td>
					${logisticsDriver.phone}
				</td>
				<td>
					<fmt:formatDate value="${logisticsDriver.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="logisticsdriver:logisticsDriver:edit"><td>
    				<a href="${ctx}/logisticsdriver/logisticsDriver/form?id=${logisticsDriver.id}">修改</a>
					<a href="${ctx}/logisticsdriver/logisticsDriver/delete?id=${logisticsDriver.id}" onclick="return confirmx('确认要删除该物流司机吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
	<script type="text/javascript">
		function backGS(){
			location.href="${ctx}/logisticsfirm/logisticsFirm/list?id=";
		}
	</script>
</body>
</html>