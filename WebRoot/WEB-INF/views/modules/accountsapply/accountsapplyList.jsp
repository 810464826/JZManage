<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>对账申请管理</title>
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
		<li class="active"><a href="${ctx}/accountsapply/accountsapply/">对账申请列表</a></li>
		<shiro:hasPermission name="accountsapply:accountsapply:edit">
		<c:if test="${result}">
		   <li><a href="${ctx}/accountsapply/accountsapply/save">对账申请添加</a></li>
		</c:if>
		</shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="accountsapply" action="${ctx}/accountsapply/accountsapply/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>申请经销商：</label>
				<form:input path="applyoffice" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>申请人：</label>
				<form:input path="applyusername" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>处理人：</label>
				<form:input path="handleusername" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>处理经销商：</label>
				<form:input path="handleoffice" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>状态：</label>
				<form:select path="state" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('accounts_apply_state')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>申请时间：</label>
				<input name="applytime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${accountsapply.createDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</li>
			<li><label>处理时间：</label>
				<input name="handletime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${accountsapply.createDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>申请经销商</th>
				<th>申请人</th>
				<th>处理经销商</th>
				<th>处理人</th>
				<th>状态</th>
				<th>申请时间</th>
				<th>处理时间</th>
				<th>备注</th>
				<shiro:hasPermission name="accountsapply:accountsapply:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="accountsapply">
			<tr>
				<td><a href="${ctx}/accountsapply/accountsapply/form?id=${accountsapply.id}">
					${accountsapply.applyoffice}
				</a></td>
				<td>
					${accountsapply.applyusername}
				</td>
				<td>
					${accountsapply.handleoffice}
				</td>
				<td>
					${accountsapply.handleusername}
				</td>
				<td>
					${fns:getDictLabel(accountsapply.state, 'accounts_apply_state', '')}
				</td>
				<td>
					${accountsapply.applytime}
				</td>
				<td>
					${accountsapply.handletime}
				</td>
				<td>
					${accountsapply.remarks}
				</td>
				<shiro:hasPermission name="accountsapply:accountsapply:edit"><td>
    				<a href="${ctx}/accountsapply/accountsapply/form?id=${accountsapply.id}">修改</a>
					<a href="${ctx}/accountsapply/accountsapply/delete?id=${accountsapply.id}" onclick="return confirmx('确认要删除该对账申请吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>