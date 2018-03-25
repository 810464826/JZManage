<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>跨省份扫码统计管理</title>
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
				top.$.jBox.confirm("确认要导出跨省份扫码数据吗？","系统提示",function(v,h,f){
					if(v=="ok"){
						location.href="${ctx}/provincescount/provincesCount/export";
						
					}
				},{buttonsFocus:1});
				top.$('.jbox-body .jbox-icon').css('top','55px');
			});
		});
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/provincescount/provincesCount/">跨省份扫码统计列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="provincesCount" action="${ctx}/provincescount/provincesCount/" method="post" class="breadcrumb form-search">
		<%-- <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/> --%>
		<ul class="ul-form">
			<li><label>经销商名称：</label>
				<form:input path="activityPrizesName" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>活动省份</label>
				<form:input path="activityPrizesAddress" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>扫码时间</label>
				<input name="scanTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${provincesCount.createDate}" pattern="yyyy-MM"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM',isShowClear:false});"/>
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
				
				<th>活动经销商名称</th>
				<th>活动经销商所属省份</th>
				<th>扫码省份</th>
				<th>扫码次数</th>
				<th>扫码时间</th>
				<%-- <shiro:hasPermission name="provincescount:provincesCount:edit"><th>操作</th></shiro:hasPermission> --%>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page}" var="provincesCount">
			<tr>
				<td>
					${provincesCount.activityPrizesName}
				</td>
				<td>
					${provincesCount.activityPrizesAddress}
				</td>
				<td>
					${provincesCount.province}
				</td>
				<td>
					${provincesCount.scanNumber}
				</td>
				<td>
					${provincesCount.scanTime}
				</td>
				<%-- <shiro:hasPermission name="provincescount:provincesCount:edit"><td>
    				<a href="${ctx}/provincescount/provincesCount/form?id=${provincesCount.id}">修改</a>
					<a href="${ctx}/provincescount/provincesCount/delete?id=${provincesCount.id}" onclick="return confirmx('确认要删除该跨省份扫码统计吗？', this.href)">删除</a>
				</td></shiro:hasPermission> --%>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<%-- <div class="pagination">${page}</div> --%>
</body>
</html>