<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>物流信息管理</title>
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
		<li class="active"><a href="${ctx}/logisticsfirm/logisticsFirm/">物流信息列表</a></li>
		<shiro:hasPermission name="logisticsfirm:logisticsFirm:edit"><li><a href="${ctx}/logisticsfirm/logisticsFirm/form">物流信息添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="logisticsFirm" action="${ctx}/logisticsfirm/logisticsFirm/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>公司名称：</label>
				<form:input path="logisticsName" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>公司名称</th>
				<th>公司代码</th>
				<th>创建时间</th>
				<shiro:hasPermission name="logisticsfirm:logisticsFirm:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="logisticsFirm">
			<tr>
				<td><a href="${ctx}/logisticsfirm/logisticsFirm/form?id=${logisticsFirm.id}">
					${logisticsFirm.logisticsName}
				</a></td>
				<td>
					${logisticsFirm.logisticsCode}
				</td>
				<td>
					<fmt:formatDate value="${logisticsFirm.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="logisticsfirm:logisticsFirm:edit"><td>
					<%-- <a href="${ctx}/logisticstruck/logisticsTruck/list?firmId=${logisticsFirm.id}">货车</a> --%>
    				<a href="${ctx}/logisticsfirm/logisticsFirm/form?id=${logisticsFirm.id}">修改</a>
					<a href="${ctx}/logisticsfirm/logisticsFirm/delete?id=${logisticsFirm.id}" onclick="return confirmx('确认要删除该物流信息吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>