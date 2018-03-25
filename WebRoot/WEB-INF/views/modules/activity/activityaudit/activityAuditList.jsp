<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
	<title>活动审核管理</title>
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
		<li class="active"><a href="${ctx}/activityaudit/activityAudit/">活动审核列表</a></li>
		<%-- <shiro:hasPermission name="activityaudit:activityAudit:edit"><li><a href="${ctx}/activityaudit/activityAudit/form">活动审核添加</a></li></shiro:hasPermission> --%>
	</ul>
	<form:form id="searchForm" modelAttribute="activityAudit" action="${ctx}/activityaudit/activityAudit/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>活动名称：</label>
				<form:input path="activityName" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<%-- <li><label>活动地址：</label>
				<form:input path="activityAddress" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>申请人：</label>
				<form:input path="applyUser" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li> --%>
			<li><label>状态</label>
				<form:select path="state" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('activity_report_state')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
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
				<th>活动名称</th>
				<th>活动地址</th>
				<th>创建时间</th>
				<th>结束时间</th>
				<th>申请人</th>
				<th>最后审核人</th>
				<th>状态</th>
				<shiro:hasPermission name="activityaudit:activityAudit:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="activityAudit">
			<tr>
				<td>
					${activityAudit.activityName}
				</td>
				<td>
					${activityAudit.activityAddress}
				</td>
				<td>
					<fmt:formatDate value="${activityAudit.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<fmt:formatDate value="${activityAudit.updateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${activityAudit.applyUser}
				</td>
				<td>
					${activityAudit.examineUser}
				</td>
				<td>
					${fns:getDictLabel(activityAudit.state, 'activity_report_state', '')}
				</td>
				<shiro:hasPermission name="activityaudit:activityAudit:edit"><td>
					<a href="${ctx}/activityreport/activityReport/activityReportLook?id=,${activityAudit.id},">查看</a>
					<!--状态  1待审批   2已通过  3未通过  4已撤销  -->
					<c:if test="${activityAudit.state != '2' || activityAudit.state != '4' }" >
						<a href="${ctx}/activityaudit/activityAudit/activityAuditStatement?id=,${activityAudit.id},">审核</a>
					</c:if>
					
    				<%-- <a href="${ctx}/activityaudit/activityAudit/form?id=${activityAudit.id}">修改</a>
					<a href="${ctx}/activityaudit/activityAudit/delete?id=${activityAudit.id}" onclick="return confirmx('确认要删除该活动审核吗？', this.href)">删除</a> --%>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>