<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>微信配置管理</title>
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
		<li class="active"><a href="${ctx}/wx_config/wxConfig/">微信配置列表</a></li>
		<shiro:hasPermission name="wx_config:wxConfig:edit"><li><a href="${ctx}/wx_config/wxConfig/form">微信配置添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="wxConfig" action="${ctx}/wx_config/wxConfig/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>名称：</label>
				<form:input path="name" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>值：</label>
				<form:input path="value" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>名称</th>
				<th>值</th>
				<th>备注</th>
				<shiro:hasPermission name="wx_config:wxConfig:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="wxConfig">
			<tr>
				<td><a href="${ctx}/wx_config/wxConfig/form?id=${wxConfig.id}">
					${wxConfig.name}
				</a></td>
				<td>
					${wxConfig.value}
				</td>
				<td>
					${wxConfig.remarks}
				</td>
				<shiro:hasPermission name="wx_config:wxConfig:edit"><td>
    				<a href="${ctx}/wx_config/wxConfig/form?id=${wxConfig.id}">修改</a>
					<a href="${ctx}/wx_config/wxConfig/delete?id=${wxConfig.id}" onclick="return confirmx('确认要删除该微信配置吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>