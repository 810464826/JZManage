<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>经销商管理</title>
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
		<li class="active"><a href="${ctx}/exportuser/exportuser/">经销商列表</a></li>
		<shiro:hasPermission name="exportuser:exportuser:edit"><li><a href="${ctx}/exportuser/exportuser/form">经销商添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="exportuser" action="${ctx}/exportuser/exportuser/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>联系人姓名：</label>
				<form:input path="name" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>联系人姓名</th>
				<shiro:hasPermission name="exportuser:exportuser:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="exportuser">
			<tr>
				<td><a href="${ctx}/exportuser/exportuser/form?id=${exportuser.id}">
					${exportuser.name}
				</a></td>
				<shiro:hasPermission name="exportuser:exportuser:edit"><td>
    				<a href="${ctx}/exportuser/exportuser/form?id=${exportuser.id}">修改</a>
					<a href="${ctx}/exportuser/exportuser/delete?id=${exportuser.id}" onclick="return confirmx('确认要删除该经销商吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>