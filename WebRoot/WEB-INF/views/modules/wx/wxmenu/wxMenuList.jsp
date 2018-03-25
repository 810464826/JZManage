<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>微信菜单管理</title>
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
		<li class="active"><a href="${ctx}/wxmenu/wxMenu/">微信菜单列表</a></li>
		<shiro:hasPermission name="wxmenu:wxMenu:edit"><li><a href="${ctx}/wxmenu/wxMenu/form">微信菜单添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="wxMenu" action="${ctx}/wxmenu/wxMenu/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>菜单名称：</label>
				<form:input path="menuName" htmlEscape="false" maxlength="8" class="input-medium"/>
			</li>
			<li><label>菜单类型：</label>
				<form:input path="menuType" htmlEscape="false" maxlength="10" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>菜单名称</th>
				<th>菜单类型</th>
				<th>事件或链接</th>
				<th>是/否</th>
				<th>顺序</th>
				<shiro:hasPermission name="wxmenu:wxMenu:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="wxMenu">
			<tr>
				<td><a href="${ctx}/wxmenu/wxMenu/form?id=${wxMenu.id}">
					${wxMenu.menuName}
				</a></td>
				<td>
					${wxMenu.menuType}
				</td>
				<td>
					${wxMenu.eventLink}
				</td>
				<td>
					${wxMenu.generate}
				</td>
				<td>
					${wxMenu.order}
				</td>
				<shiro:hasPermission name="wxmenu:wxMenu:edit"><td>
    				<a href="${ctx}/wxmenu/wxMenu/form?id=${wxMenu.id}">修改</a>
					<a href="${ctx}/wxmenu/wxMenu/delete?id=${wxMenu.id}" onclick="return confirmx('确认要删除该微信菜单吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>