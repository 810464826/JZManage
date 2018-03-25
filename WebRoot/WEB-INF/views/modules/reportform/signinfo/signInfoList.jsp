<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>扫码中奖统计管理</title>
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
				top.$.jBox.confirm("确认要导出扫码中奖数据吗？","系统提示",function(v,h,f){
					if(v=="ok"){
						location.href="${ctx}/signinfo/signInfo/export";
					}
				},{buttonsFocus:1});
				top.$('.jbox-body .jbox-icon').css('top','55px');
			});
		});
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/signinfo/signInfo/">扫码中奖统计列表</a></li>
		<%-- <shiro:hasPermission name="signinfo:signInfo:edit"><li><a href="${ctx}/signinfo/signInfo/form">扫码中奖统计添加</a></li></shiro:hasPermission> --%>
	</ul>
	<form:form id="searchForm" modelAttribute="signInfo" action="${ctx}/signinfo/signInfo/" method="post" class="breadcrumb form-search">
		<%-- <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/> --%>
		<ul class="ul-form">
			<li>
				<label>活动名称</label>
				<form:input path="activityId" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>奖品名称</label>
				<form:input path="prizeName" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>经销商名称</label>
				<input type="text" maxlength="255" class="input-medium"/>
			</li>
			<li><label>奖品类型</label>
				<form:select path="prizeType" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('prize_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<%-- <li><label>中奖时间</label>
				<input name="winningTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${signInfo.winningTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li> --%>
			<li><label>中奖时间：</label>
				<input name="startTime" id="startTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${signInfo.winningTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>至
				<input name="endTime" id="endTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${signInfo.winningTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
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
					<th>活动名称</th>
					<th>经销商名称</th>
					<th>区域</th>
					<th>奖品名称</th>
					<th>数量</th>
					<th>中奖时间</th>
					<%-- <shiro:hasPermission name="signinfo:signInfo:edit"><th>操作</th></shiro:hasPermission> --%>
				</tr>
			</thead>
			<tbody>
			<c:forEach items="${page}" var="signInfo">
				<tr>
					<td>
						${signInfo.activityName}
					</td>
					<td>
						${signInfo.userName}
					</td>
					<td>
						${signInfo.activityAddress}
					</td>
					<td>
						${signInfo.prizeName}
					</td>
					<td>
						${signInfo.number}
					</td>
					<td>
						<fmt:formatDate value="${signInfo.winningTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
					</td>
					<%-- <shiro:hasPermission name="signinfo:signInfo:edit"><td>
		   				<a href="${ctx}/signinfo/signInfo/form?id=${signInfo.id}">修改</a>
						<a href="${ctx}/signinfo/signInfo/delete?id=${signInfo.id}" onclick="return confirmx('确认要删除该扫码中奖统计吗？', this.href)">删除</a>
					</td></shiro:hasPermission> --%>
				</tr>
			</c:forEach>
			</tbody>
		</table>
	</div>
	<%-- <div class="pagination">${page}</div> --%>
</body>
</html>