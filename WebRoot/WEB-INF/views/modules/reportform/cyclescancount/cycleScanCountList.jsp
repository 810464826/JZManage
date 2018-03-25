<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>周期扫码次数统计管理</title>
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
				top.$.jBox.confirm("确认要导出周期扫码次数统计数据吗？","系统提示",function(v,h,f){
					if(v=="ok"){
						location.href="${ctx}/cyclescancount/cycleScanCount/export";
					}
				},{buttonsFocus:1});
				top.$('.jbox-body .jbox-icon').css('top','55px');
			});
		});
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/cyclescancount/cycleScanCount/">周期扫码次数统计列表</a></li>
		<c:if test="${userType == '1'}">
			<li><a href="${ctx}/provincescount/provincesCount/provincesMapIndex">周期扫码地图</a></li>
    	</c:if>
	</ul>
	<form:form id="searchForm" modelAttribute="cycleScanCount" action="${ctx}/cyclescancount/cycleScanCount/" method="post" class="breadcrumb form-search">
		<%-- <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/> --%>
		<ul class="ul-form">
			<li><label>扫码次数：</label>
				<form:input path="scanNumber" htmlEscape="false" maxlength="255" class="input-medium"/>
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
				<!-- <th>电话号码</th> -->
				<th>省</th>
				<th>城市</th>
				<th>扫码次数</th>
				<th>扫码时间</th>
				<%-- <shiro:hasPermission name="cyclescancount:cycleScanCount:edit"><th>操作</th></shiro:hasPermission> --%>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page}" var="cycleScanCount">
			<tr>
				<td>
					${cycleScanCount.userName}
				</td>
				<%-- <td>
					${cycleScanCount.phone}
				</td> --%>
				<td>
					${cycleScanCount.province}
				</td>
				<td>
					${cycleScanCount.city}
				</td>
				<td>
					${cycleScanCount.scanNumber}
				</td>
				<td>
					${cycleScanCount.scanTime}
				</td>
				
				<%-- <shiro:hasPermission name="cyclescancount:cycleScanCount:edit"><td>
    				<a href="${ctx}/cyclescancount/cycleScanCount/form?id=${cycleScanCount.id}">修改</a>
					<a href="${ctx}/cyclescancount/cycleScanCount/delete?id=${cycleScanCount.id}" onclick="return confirmx('确认要删除该周期扫码次数统计吗？', this.href)">删除</a>
				</td></shiro:hasPermission> --%>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<%-- <div class="pagination">${page}</div> --%>
</body>
</html>