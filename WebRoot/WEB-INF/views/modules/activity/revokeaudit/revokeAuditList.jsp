<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>撤销审核管理</title>
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
		<li class="active"><a href="${ctx}/revokeaudit/revokeAudit/">撤销审核列表</a></li>
		<%-- <shiro:hasPermission name="revokeaudit:revokeAudit:edit"><li><a href="${ctx}/revokeaudit/revokeAudit/form">撤销审核添加</a></li></shiro:hasPermission> --%>
	</ul>
	<form:form id="searchForm" modelAttribute="revokeAudit" action="${ctx}/revokeaudit/revokeAudit/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>活动名称：</label>
				<form:input path="activityName" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>活动地址：</label>
				<form:input path="activityAddress" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
<%-- 			<li><label>申请人：</label>
				<form:input path="applyUser" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li> --%>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>活动名称</th>
				<th>活动地址</th>
				<th>创建时间</th>
				<th>修改时间</th>
				<th>申请人</th>
				<th>最后审核人</th>
				<th>状态</th>
				<shiro:hasPermission name="revokeaudit:revokeAudit:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="revokeAudit">
			<tr>
				<td>
					${revokeAudit.activityName}
				</td>
				<td>
					${revokeAudit.activityAddress}
				</td>
				<td>
					<fmt:formatDate value="${revokeAudit.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<fmt:formatDate value="${revokeAudit.updateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${revokeAudit.applyUser}
				</td>
				<td>
					${revokeAudit.examineUser}
				</td>
				<td>
					${fns:getDictLabel(revokeAudit.state, 'activityReport_state', '')}
				</td>
				<shiro:hasPermission name="revokeaudit:revokeAudit:edit"><td>
					<a href="${ctx}/activityreport/activityReport/activityReportLook?id=,${revokeAudit.id},">查看</a>
					<a href="${ctx}/revokeaudit/revokeAudit/delete?id=,${revokeAudit.id}," onclick="return confirmx('确认要撤销审核吗？', this.href)">撤销</a>
    				<%-- <a href="${ctx}/revokeaudit/revokeAudit/form?id=${revokeAudit.id}">修改</a>
					<a href="${ctx}/revokeaudit/revokeAudit/delete?id=${revokeAudit.id}" onclick="return confirmx('确认要删除该撤销审核吗？', this.href)">删除</a> --%>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>