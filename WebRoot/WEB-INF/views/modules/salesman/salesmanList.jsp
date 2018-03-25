<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>业务员管理</title>
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
		<li class="active"><a href="${ctx}/salesman/salesman/">业务员列表</a></li>
		<shiro:hasPermission name="salesman:salesman:edit"><li><a href="${ctx}/salesman/salesman/form">业务员添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="salesman" action="${ctx}/salesman/salesman/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>业务员名称：</label>
				<form:input path="name" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>管理店名称：</label>
				<form:input path="manangeofficename" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>业务员名称</th>
				<th>管理门店</th>
				<th>业务员电话</th>
				<th>所属经销商</th>
				<shiro:hasPermission name="salesman:salesman:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="salesman">
			<tr>
				<td><a href="${ctx}/salesman/salesman/form?id=${salesman.id}">
					${salesman.name}
				</a></td>
				<td>${salesman.manangeofficename}</td>
				<td>${salesman.phone}</td>
				<td>${salesman.officename}</td>
				<shiro:hasPermission name="salesman:salesman:edit"><td>
    				<a href="${ctx}/salesman/salesman/form?id=${salesman.id}">修改</a>
					<a href="${ctx}/salesman/salesman/delete?id=${salesman.id}" onclick="return confirmx('确认要删除该业务员吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>