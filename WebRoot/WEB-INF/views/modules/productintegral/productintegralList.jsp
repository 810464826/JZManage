<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>产品积分关系管理</title>
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
		<li class="active"><a href="${ctx}/productintegral/productintegral/">产品积分关系列表</a></li>
		<shiro:hasPermission name="productintegral:productintegral:edit"><li><a href="${ctx}/productintegral/productintegral/goToAddForm">产品积分关系添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="productintegral" action="${ctx}/productintegral/productintegral/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>产品名称：</label>
				<form:input path="name" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>度数：</label>
				<form:input path="degree" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>容量：</label>
				<form:input path="volume" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>规格：</label>
				<form:input path="spec" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>积分：</label>
				<form:input path="integreal" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>产品名称</th>
				<th>度数(度)</th>
				<th>容量(ml)</th>
				<th>规格</th>
				<th>积分</th>
				<shiro:hasPermission name="productintegral:productintegral:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="productintegral">
			<tr>
				<td>
				<%-- <a href="${ctx}/productintegral/productintegral/form?id=${productintegral.id}"> --%>
					${productintegral.name}
				<!-- </a> -->
				</td>
				<td>
					${productintegral.degree}
				</td>
				<td>
					${productintegral.volume}
				</td>
				<td>
					${productintegral.spec}
				</td>
				<td>
					${productintegral.integreal}
				</td>
				<shiro:hasPermission name="productintegral:productintegral:edit"><td>
    				<a href="${ctx}/productintegral/productintegral/form?id=${productintegral.id}">修改</a>
					<a href="${ctx}/productintegral/productintegral/delete?id=${productintegral.id}" onclick="return confirmx('确认要删除该产品积分关系吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>