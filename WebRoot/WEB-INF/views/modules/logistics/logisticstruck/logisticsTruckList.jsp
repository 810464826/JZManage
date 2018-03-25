<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>物流车辆管理</title>
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
		<li class="active"><a href="${ctx}/logisticstruck/logisticsTruck/">物流车辆列表</a></li>
		<shiro:hasPermission name="logisticstruck:logisticsTruck:edit"><li><a href="${ctx}/logisticstruck/logisticsTruck/form?firmId=${firmid}">物流车辆添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="logisticsTruck" action="${ctx}/logisticstruck/logisticsTruck/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>车牌号：</label>
				<form:input path="carNumber" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>汽车名称：</label>
				<form:input path="carName" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<form:input path="firmId" type="hidden" value="${firmid}" htmlEscape="false" maxlength="64" class="input-medium"/>
			
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li style="margin-left: 200px;"><input id="btnSubmit" class="btn btn-primary" type="button" onclick="back()" value="返回物流公司"/></li>
			<li class="clearfix"></li>
		</ul>
		
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>车牌号</th>
				<th>载重量</th>
				<th>创建时间</th>
				<th>汽车名称</th>
				<shiro:hasPermission name="logisticstruck:logisticsTruck:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="logisticsTruck">
			<tr>
				<td><a href="${ctx}/logisticstruck/logisticsTruck/form?id=${logisticsTruck.id}">
					${logisticsTruck.carNumber}
				</a></td>
				<td>
					${logisticsTruck.load}
				</td>
				<td>
					<fmt:formatDate value="${logisticsTruck.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${logisticsTruck.carName}
				</td>
				<shiro:hasPermission name="logisticstruck:logisticsTruck:edit"><td>
					<a href="${ctx}/logisticsdriver/logisticsDriver/list?truckId=${logisticsTruck.id}">司机</a>
    				<a href="${ctx}/logisticstruck/logisticsTruck/form?id=${logisticsTruck.id}">修改</a>
					<a href="${ctx}/logisticstruck/logisticsTruck/delete?id=${logisticsTruck.id}" onclick="return confirmx('确认要删除该物流车辆吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
	<script type="text/javascript">
	  function back(){
		  location.href="${ctx}/logisticsfirm/logisticsFirm/list?id=";
	  }
	</script>
</body>
</html>