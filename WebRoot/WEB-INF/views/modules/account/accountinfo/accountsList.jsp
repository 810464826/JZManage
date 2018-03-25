<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>对账管理管理</title>
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
		<li class="active"><a href="${ctx}/accountinfo/accounts/">对账管理列表</a></li>
		<shiro:hasPermission name="accountinfo:accounts:edit"><li><a href="${ctx}/accountinfo/accounts/form">对账管理添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="accounts" action="${ctx}/accountinfo/accounts/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>对账时间：</label>
				<input name="startTime" id="startTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${accounts.accountsTime}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>至
				<input name="endTime" id="endTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${accounts.accountsTime}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</li>
			<li><label>状态：</label>
				<form:select path="state" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('accounts_state')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>对账发起人</th>
				<th>对账发起经销商</th>
				<th>确认对账人</th>
				<th>确认对账经销商</th>
				<th>对账时间</th>
				<th>状态</th>
				<th>对账详情</th>
				<%-- <shiro:hasPermission name="accountinfo:accounts:edit"><th>操作</th></shiro:hasPermission> --%>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="accounts">
			<tr>
				<td>
				<%-- <a href="${ctx}/accountinfo/accounts/form?id=${accounts.id}">
					${accounts.accountsUserId}
				</a> --%>
				${accounts.accountsUserId}
				</td>
				<td>
					${accounts.accountsDistributorId}
				</td>
				<td>
					${accounts.accountsConfirmId}
				</td>
				<td>
					${accounts.accountsConfirmDistributorI}
				</td>
				<td>
					<fmt:formatDate value="${accounts.accountsTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${fns:getDictLabel(accounts.state, 'accounts_state', '')}
				</td>
				<td>
					${accounts.remarks}
				</td>
				<shiro:hasPermission name="accountinfo:accounts:edit"><td>
					<%-- <a href="${ctx}/accountinfo/accounts/look?id=${accounts.id}">查看</a>	 --%>
    				<%-- <a href="${ctx}/accountinfo/accounts/form?id=${accounts.id}">修改</a>
					<a href="${ctx}/accountinfo/accounts/delete?id=${accounts.id}" onclick="return confirmx('确认要删除该对账管理吗？', this.href)">删除</a> --%>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>