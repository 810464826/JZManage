<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>积分商城管理</title>
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
		<li class="active"><a href="${ctx}/integralmall/integralMall/">积分商城列表</a></li>
		<shiro:hasPermission name="integralmall:integralMall:edit"><li><a href="${ctx}/integralmall/integralMall/form">积分商城添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="integralMall" action="${ctx}/integralmall/integralMall/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>商品名称：</label>
				<form:input path="name" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>积分兑换：</label>
				<form:input path="exchange" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>商品名称</th>
				<th>积分兑换</th>
				<th>是否推荐</th>
				<th>总数</th>
				<th>剩余数量</th>
				<th>奖品类型</th>
				<th>卡券ID</th>
				<shiro:hasPermission name="integralmall:integralMall:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="integralMall">
			<tr>
				<td><a href="${ctx}/integralmall/integralMall/form?id=${integralMall.id}">
					${integralMall.name}
				</a></td>
				<td>
					${integralMall.exchange}
				</td>
				<td>
					${fns:getDictLabel(integralMall.recommend, 'mall_recommend', '')}
				</td>
				<td>
					${integralMall.total}
				</td>
				<td>
					${integralMall.remainingQuantity}
				</td>
				<td>
					${fns:getDictLabel(integralMall.prizeType, 'integra_prize_type', '')}
				</td>
				<td>
					${integralMall.cardId}
				</td>
				<shiro:hasPermission name="integralmall:integralMall:edit"><td>
    				<a href="${ctx}/integralmall/integralMall/form?id=${integralMall.id}">修改</a>
					<a href="${ctx}/integralmall/integralMall/delete?id=${integralMall.id}" onclick="return confirmx('确认要删除该积分商城吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>