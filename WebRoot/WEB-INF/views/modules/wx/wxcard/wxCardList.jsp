<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>微信卡券管理</title>
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
		<li class="active"><a href="${ctx}/wxcard/wxCard/">微信卡券列表</a></li>
		<shiro:hasPermission name="wxcard:wxCard:edit"><li><a href="${ctx}/wxcard/wxCard/form">微信卡券添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="wxCard" action="${ctx}/wxcard/wxCard/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>卡券名称：</label>
				<form:input path="name" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>卡券ID：</label>
				<form:input path="cardId" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>卡券名称</th>
				<th>卡券ID</th>
				<th>卡券类型</th>
				<shiro:hasPermission name="wxcard:wxCard:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="wxCard">
			<tr>
				<td><a href="${ctx}/wxcard/wxCard/form?id=${wxCard.id}">
					${wxCard.name}
				</a></td>
				<td>
					${wxCard.cardId}
				</td>
				<td>
					${wxCard.cardType}
				</td>
				<shiro:hasPermission name="wxcard:wxCard:edit"><td>
    				<a href="${ctx}/wxcard/wxCard/form?id=${wxCard.id}">修改</a>
					<a href="${ctx}/wxcard/wxCard/delete?id=${wxCard.id}" onclick="return confirmx('确认要删除该微信卡券吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>