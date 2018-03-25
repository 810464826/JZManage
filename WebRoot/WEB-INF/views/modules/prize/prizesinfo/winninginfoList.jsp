<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>中奖记录管理</title>
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
		<li class="active"><a href="${ctx}/prizesinfo/winninginfo/">中奖记录列表</a></li>
		<%-- <shiro:hasPermission name="prizesinfo:winninginfo:edit"><li><a href="${ctx}/prizesinfo/winninginfo/form">中奖记录添加</a></li></shiro:hasPermission> --%>
	</ul>
	<form:form id="searchForm" modelAttribute="winninginfo" action="${ctx}/prizesinfo/winninginfo/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>呢称：</label>
				<form:input path="name" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>中奖方式</label>
				<form:select path="winningWay" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('winning_way')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>奖品状态</label>
				<form:select path="distributionStatus" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('winninginfo_distributionStatus')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>奖品名称：</label>
				<form:input path="prizeName" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>中奖时间：</label>
				<input name="startTime" id="startTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${winninginfo.winningTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>至
				<input name="endTime" id="endTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${winninginfo.winningTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li> 
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>呢称</th>
				<th>中奖方式</th>
				<th>奖品名称</th>
				<th>活动经销商</th>
				<th>活动区域</th>
				<th>中奖时间</th>
				<th>奖品状态</th>
				<%-- <shiro:hasPermission name="prizesinfo:winninginfo:edit"><th>操作</th></shiro:hasPermission> --%>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="winninginfo">
			<tr>
				<td>
					<span class="encode">${winninginfo.name}</span>
				</td>
				<td>
					${fns:getDictLabel(winninginfo.winningWay, 'winning_way', '')}
				</td>
				<td>
					${winninginfo.prizeName}
				</td>
				<td>
					${winninginfo.activityUserName}
				</td>
				<td>
					${winninginfo.activityArea}
				</td>
				<td>
					<fmt:formatDate value="${winninginfo.winningTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${fns:getDictLabel(winninginfo.distributionStatus, 'winninginfo_distributionStatus', '')}
				</td>
				<%-- <shiro:hasPermission name="prizesinfo:winninginfo:edit"><td>
    				<a href="${ctx}/prizesinfo/winninginfo/form?id=${winninginfo.id}">修改</a>
					<a href="${ctx}/prizesinfo/winninginfo/delete?id=${winninginfo.id}" onclick="return confirmx('确认要删除该中奖记录吗？', this.href)">删除</a>
				</td></shiro:hasPermission> --%>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>