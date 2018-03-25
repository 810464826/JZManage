<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>积分兑换管理</title>
	<meta name="decorator" content="default"/>
	<style type="text/css">
	#tableOuter{
		height:450px;
		overflow-y:auto;
	}
	</style>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
		$(document).ready(function() {
			$("#btnExport").click(function(){
				top.$.jBox.confirm("确认要导出跨区域核销统计数据吗？","系统提示",function(v,h,f){
					if(v=="ok"){
						location.href="${ctx}/integralprize/integralPrize/export";
						
					}
				},{buttonsFocus:1});
				top.$('.jbox-body .jbox-icon').css('top','55px');
			});
		});
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/integralprize/integralPrize/">积分兑换列表</a></li>
		<%-- <shiro:hasPermission name="integralprize:integralPrize:edit"><li><a href="${ctx}/integralprize/integralPrize/form">积分兑换添加</a></li></shiro:hasPermission> --%>
	</ul>
	<form:form id="searchForm" modelAttribute="integralPrize" action="${ctx}/integralprize/integralPrize/" method="post" class="breadcrumb form-search">
		<%-- <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/> --%>
		<ul class="ul-form">
			<li><label>奖品名称：</label>
				<form:input path="prizeName" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>奖品类型</label>
				<form:select path="prizeType" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('turntable_Prize')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="btns"><input id="btnExport" class="btn btn-primary" type="button" value="数据导出"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<div id="tableOuter">
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>奖品名称</th>
				<th>奖品类型</th>
				<th>数量</th>
				<%-- <shiro:hasPermission name="integralprize:integralPrize:edit"><th>操作</th></shiro:hasPermission> --%>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page}" var="integralPrize">
			<tr>
				<td>
					${integralPrize.name}
				</td>
				<td>
					${fns:getDictLabel(integralPrize.prizeType, 'turntable_Prize', '')}
				</td>
				<td>
					${integralPrize.number}
				</td>
				<%-- <shiro:hasPermission name="integralprize:integralPrize:edit"><td>
    				<a href="${ctx}/integralprize/integralPrize/form?id=${integralPrize.id}">修改</a>
					<a href="${ctx}/integralprize/integralPrize/delete?id=${integralPrize.id}" onclick="return confirmx('确认要删除该积分兑换吗？', this.href)">删除</a>
				</td></shiro:hasPermission> --%>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	</div>
	<%-- <div class="pagination">${page}</div> --%>
</body>
</html>