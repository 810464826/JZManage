<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>奖品派送管理</title>
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
		
		$(document).ready(function() {
			$("#btnExport").click(function(){
				top.$.jBox.confirm("确认要导出奖品派送数据吗？","系统提示",function(v,h,f){
					if(v=="ok"){
						location.href="${ctx}/winningdelivepy/winningDelivery/export";
					}
				},{buttonsFocus:1});
				top.$('.jbox-body .jbox-icon').css('top','55px');
			});
		});
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/winningdelivepy/winningDelivery/">奖品派送列表</a></li>
		<%-- <shiro:hasPermission name="winningdelivepy:winningDelivery:edit"><li><a href="${ctx}/winningdelivepy/winningDelivery/form">奖品派送添加</a></li></shiro:hasPermission> --%>
	</ul>
	<form:form id="searchForm" modelAttribute="winningDelivery" action="${ctx}/winningdelivepy/winningDelivery/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>呢称：</label>
				<form:input path="name" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>中奖方式：</label>
				<form:select path="winningWay" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('winning_way')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>配送状态：</label>
				<form:select path="distributionStatus" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('distribution_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>配送时间：</label>
				<input name="startTime" id="startTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${winningDelivery.deliveryTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>至
				<input name="endTime" id="endTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${winningDelivery.deliveryTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="btns"><input id="btnExport" class="btn btn-primary" type="button" value="数据导出"/></li>
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
				<th>快递单号</th>
				<th>快递公司</th>
				<th>收获地址</th>
				<th>收货人</th>
				<th>收货电话</th>
				<th>发货时间</th>
				<th>中奖时间</th>
				<th>配送状态</th>
				<shiro:hasPermission name="winningdelivepy:winningDelivery:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="winningDelivery">
			<tr>
				<td><a href="${ctx}/winningdelivepy/winningDelivery/form?id=${winningDelivery.id}">
					${winningDelivery.name}
				</a></td>
				<td>
					${fns:getDictLabel(winningDelivery.winningWay, 'winning_way', '')}
				</td>
				<td>
					${winningDelivery.prizeName}
				</td>
				<td>
					${winningDelivery.expressNumber}
				</td>
				<td>
					${winningDelivery.express}
				</td>
				<td>
					${winningDelivery.collectAddress}
				</td>
				<td>
					${winningDelivery.collectUser}
				</td>
				<td>
					${winningDelivery.collectPhone}
				</td>
				<td>
					<fmt:formatDate value="${winningDelivery.deliveryTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<fmt:formatDate value="${winningDelivery.winningTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${fns:getDictLabel(winningDelivery.distributionStatus, 'distribution_status', '')}
				</td>
				<shiro:hasPermission name="winningdelivepy:winningDelivery:edit"><td>
    				<a href="${ctx}/winningdelivepy/winningDelivery/form?id=${winningDelivery.id}">物流发货</a>
					<%-- <a href="${ctx}/winningdelivepy/winningDelivery/delete?id=${winningDelivery.id}" onclick="return confirmx('确认要删除该奖品派送吗？', this.href)">删除</a> --%>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>