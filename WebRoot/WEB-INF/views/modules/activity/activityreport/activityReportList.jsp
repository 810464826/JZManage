<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>活动报备管理</title>
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
		<li class="active"><a href="${ctx}/activityreport/activityReport/">活动报备列表</a></li>
		<shiro:hasPermission name="activityreport:activityReport:edit"><li><a href="${ctx}/activityreport/activityReport/form">活动报备添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="activityReport" action="${ctx}/activityreport/activityReport/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>活动名称：</label>
				<form:input path="activityName" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>活动地址：</label>
				<form:input path="activityAddress" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<%-- <li><label>申请人：</label>
				<sys:treeselect id="applyUser" name="applyUser" value="${activityReport.applyUser}" labelName="" labelValue="${activityReport.applyUser}"
					title="用户" url="/sys/office/treeData?type=3" cssClass="input-small" allowClear="true" notAllowSelectParent="true"/>
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
				<th>开始时间</th>
				<th>结束时间</th>
				<th>申请人</th>
				<th>最后审核人</th>
				<th>状态</th>
				<shiro:hasPermission name="activityreport:activityReport:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="activityReport">
			<tr>
				<td>
					${activityReport.activityName}
				</td>
				<td>
					${activityReport.activityAddress}
				</td>
				<td>
					<fmt:formatDate value="${activityReport.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<fmt:formatDate value="${activityReport.updateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${activityReport.applyUser}
				</td>
				<td>
					${activityReport.examineUser}
				</td>
				<td>
					${fns:getDictLabel(activityReport.state, 'activityReport_state', '')}
				</td>
				<!--状态  1待审批   2已通过  3未通过  4已撤销  -->
				<shiro:hasPermission name="activityreport:activityReport:edit"><td>
					<c:if test="${activityReport.state != '4'}">
						<a href="${ctx}/activityreport/activityReport/activityReportLook?id=,${activityReport.id},">查看</a>
	    				<a href="${ctx}/activityreport/activityReport/activityReportModify?id=,${activityReport.id},">修改</a>
    				</c:if>
					<a href="${ctx}/activityreport/activityReport/delete?id=,${activityReport.id}," onclick="return confirmx('确认要删除该活动报备吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>