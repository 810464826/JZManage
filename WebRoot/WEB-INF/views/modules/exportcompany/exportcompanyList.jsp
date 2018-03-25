<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>终端门店管理</title>
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
		<li class="active"><a href="${ctx}/exportcompany/exportcompany/">终端门店列表</a></li>
		<shiro:hasPermission name="exportcompany:exportcompany:edit"><li><a href="${ctx}/exportcompany/exportcompany/form">终端门店添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="exportcompany" action="${ctx}/exportcompany/exportcompany/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>联系人名称：</label>
				<form:input path="name" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>联系人名称</th>
				<shiro:hasPermission name="exportcompany:exportcompany:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="exportcompany">
			<tr>
				<td><a href="${ctx}/exportcompany/exportcompany/form?id=${exportcompany.id}">
					${exportcompany.name}
				</a></td>
				<shiro:hasPermission name="exportcompany:exportcompany:edit"><td>
    				<a href="${ctx}/exportcompany/exportcompany/form?id=${exportcompany.id}">修改</a>
					<a href="${ctx}/exportcompany/exportcompany/delete?id=${exportcompany.id}" onclick="return confirmx('确认要删除该终端门店吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>