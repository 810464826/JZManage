<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>转盘抽奖管理</title>
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
		<li class="active"><a href="${ctx}/turntableraffle/turntableRaffle/">转盘抽奖列表</a></li>
		<shiro:hasPermission name="turntableraffle:turntableRaffle:edit"><li><a href="${ctx}/turntableraffle/turntableRaffle/form">转盘抽奖添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="turntableRaffle" action="${ctx}/turntableraffle/turntableRaffle/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>奖品名称：</label>
				<form:input path="prizeName" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>奖品名称</th>
				<th>奖品总数</th>
				<th>剩余数量</th>
				<th>奖品类型</th>
				<th>获奖数量</th>
				<th>获奖概率</th>
				<th>微信卡券ID</th>
				<shiro:hasPermission name="turntableraffle:turntableRaffle:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="turntableRaffle">
			<tr>
				<td><a href="${ctx}/turntableraffle/turntableRaffle/form?id=${turntableRaffle.id}">
					${turntableRaffle.name}
				</a></td>
				<td>
					${turntableRaffle.prizeNumber}
				</td>
				<td>
					${turntableRaffle.surplusNumber}
				</td>
				<td>
					${fns:getDictLabel(turntableRaffle.prizeType, 'turntable_prize_type', '')}
				</td>
				<td>
					${turntableRaffle.winningNumber}
				</td>
				<td>
					${turntableRaffle.winningProbability}
				</td>
				<td>
					${turntableRaffle.wxCardId}
				</td>
				<shiro:hasPermission name="turntableraffle:turntableRaffle:edit"><td>
    				<a href="${ctx}/turntableraffle/turntableRaffle/form?id=${turntableRaffle.id}">修改</a>
					<a href="${ctx}/turntableraffle/turntableRaffle/delete?id=${turntableRaffle.id}" onclick="return confirmx('确认要删除该转盘抽奖吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>