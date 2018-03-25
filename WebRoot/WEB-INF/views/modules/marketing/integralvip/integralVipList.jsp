<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>会员积分管理</title>
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
		<li class="active"><a href="${ctx}/integralvip/integralVip/">会员积分列表</a></li>
		<%-- <shiro:hasPermission name="integralvip:integralVip:edit"><li><a href="${ctx}/integralvip/integralVip/form">会员积分添加</a></li></shiro:hasPermission> --%>
	</ul>
	<form:form id="searchForm" modelAttribute="integralVip" action="${ctx}/integralvip/integralVip/list" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>抽奖人：</label>
				<form:input path="luckyDraw" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>电话号码：</label>
				<form:input path="phone" htmlEscape="false" maxlength="11" class="input-medium"/>
			</li>
			<%-- <li><label>抽奖次数：</label>
				<form:input path="luckyNumber" htmlEscape="false" maxlength="11" class="input-medium"/>
			</li> --%>
			<li><label>总积分：</label>
			<!-- <input name="smallFen" id="smallFen" type="text" maxlength="11" class="input-medium" />至
			<input name="bigFen" id="bigFen" type="text" maxlength="11" class="input-medium"  /> -->
			
			
			<%-- <form:input path="smallFen" name="smallFen" id="smallFen" type="number" htmlEscape="false" maxlength="64" class="input-medium"/>至
			<form:input path="bigFen" name="bigFen" id="bigFen" type="number" htmlEscape="false" maxlength="255" class="input-medium"/> --%>
			
				<form:input path="smallFen" htmlEscape="false" maxlength="11" class="input-medium"/>至
				<form:input path="bigFen" htmlEscape="false" maxlength="11" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>抽奖人</th>
				<th>电话号码</th>
				<th>小麦积分</th>
				<th>大米积分</th>
				<th>玉米积分</th>
				<th>高粱积分</th>
				<th>糯米积分</th>
				<th>全部积分</th>
				<th>抽奖次数</th>
				<th>创建时间</th>
				<th>修改时间</th>
				<%-- <shiro:hasPermission name="integralvip:integralVip:edit"><th>操作</th></shiro:hasPermission> --%>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="integralVip">
			<tr>
				<td>
					${integralVip.luckyDraw}
				</td>
				<td>
					${integralVip.phone}
				</td>
				<td>
					${integralVip.wheat}
				</td>
				<td>
					${integralVip.rice}
				</td>
				<td>
					${integralVip.corn}
				</td>
				<td>
					${integralVip.sorghum}
				</td>
				<td>
					${integralVip.glutinousrice}
				</td>
				<td>
					${integralVip.allIntegral}
				</td>
				<td>
					${integralVip.luckyNumber}
				</td>
				<td>
					<fmt:formatDate value="${integralVip.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<fmt:formatDate value="${integralVip.updateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<%-- <shiro:hasPermission name="integralvip:integralVip:edit"><td>
    				<a href="${ctx}/integralvip/integralVip/form?id=${integralVip.id}">修改</a>
					<a href="${ctx}/integralvip/integralVip/delete?id=${integralVip.id}" onclick="return confirmx('确认要删除该会员积分吗？', this.href)">删除</a>
				</td></shiro:hasPermission> --%>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>