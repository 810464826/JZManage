<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>积分规则管理</title>
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
		<li class="active"><a href="${ctx}/integralrule/integralRule/">积分规则列表</a></li>
		<shiro:hasPermission name="integralrule:integralRule:edit"><li><a href="${ctx}/integralrule/integralRule/form">积分规则添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="integralRule" action="${ctx}/integralrule/integralRule/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>编号：</label>
				<form:input path="number" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>名称：</label>
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
				<th>编号</th>
				<th>名称</th>
				<th>值</th>
				<th>创建时间</th>
				<th>修改时间</th>
				<th>备注</th>
				<shiro:hasPermission name="integralrule:integralRule:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="integralRule">
			<tr>
				<td><a href="${ctx}/integralrule/integralRule/form?id=${integralRule.id}">
					${integralRule.number}
				</a></td>
				<td>
					${integralRule.name}
				</td>
				<td>
					${integralRule.value}
				</td>
				<td>
					<fmt:formatDate value="${integralRule.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<fmt:formatDate value="${integralRule.updateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${integralRule.remarks}
				</td>
				<shiro:hasPermission name="integralrule:integralRule:edit"><td>
    				<a href="${ctx}/integralrule/integralRule/form?id=${integralRule.id}">修改</a>
					<a href="${ctx}/integralrule/integralRule/delete?id=${integralRule.id}" onclick="return confirmx('确认要删除该积分规则吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>