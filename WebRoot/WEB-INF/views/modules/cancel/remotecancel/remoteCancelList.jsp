<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>异地核销管理</title>
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
				top.$.jBox.confirm("确认要导出跨区域核销统计数据吗？","系统提示",function(v,h,f){
					if(v=="ok"){
						location.href="${ctx}/remotecancel/remoteCancel/export";
						
					}
				},{buttonsFocus:1});
				top.$('.jbox-body .jbox-icon').css('top','55px');
			});
		});
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/remotecancel/remoteCancel/">异地核销列表</a></li>
		<%-- <shiro:hasPermission name="remotecancel:remoteCancel:edit"><li><a href="${ctx}/remotecancel/remoteCancel/form">异地核销添加</a></li></shiro:hasPermission> --%>
	</ul>
	<form:form id="searchForm" modelAttribute="remoteCancel" action="${ctx}/remotecancel/remoteCancel/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>活动名称：</label>
				<form:input path="activityName" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>运单编号：</label>
				<form:input path="goodsNumber" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>核销经销商：</label>
				<form:input path="cancelOffice" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>核销时间：</label>
				<input name="startTime" id="startTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${remoteCancel.createTime}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>至
				<input name="endTime" id="endTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${remoteCancel.createTime}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
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
				<th>活动名称</th>
				<th>运单编号</th>
				<th>活动所属区域</th>
				<th>活动所属经销商</th>
				<th>核销经销商区域</th>
				<th>核销经销商</th>
				<th>核销人姓名</th>
				<th>核销人手机</th>
				<th>创建时间</th>
				<th>数量</th>
				<%-- <shiro:hasPermission name="remotecancel:remoteCancel:edit"><th>操作</th></shiro:hasPermission> --%>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="remoteCancel">
			<tr>
				<td>
					${remoteCancel.activityName}
				</td>
				<td>
					${remoteCancel.goodsNumber}
				</td>
				<td>
					${remoteCancel.activityPrizesAddress}
				</td>
				<td>
					${remoteCancel.activityPrizesOffice}
				</td>
				<td>
					${remoteCancel.cancelAddress}
				</td>
				<td>
					${remoteCancel.cancelOffice}
				</td>
				
				<td>
					${remoteCancel.cancelUserName}
				</td><td>
					${remoteCancel.cancelUserPhone}
				</td>
				
				<td>
					${remoteCancel.createTime}
				</td>
				
				<td>
					${remoteCancel.cancelNumber}
				</td>
				<%-- <shiro:hasPermission name="remotecancel:remoteCancel:edit"><td>
    				<a href="${ctx}/remotecancel/remoteCancel/form?id=${remoteCancel.id}">修改</a>
					<a href="${ctx}/remotecancel/remoteCancel/delete?id=${remoteCancel.id}" onclick="return confirmx('确认要删除该异地核销吗？', this.href)">删除</a>
				</td></shiro:hasPermission> --%>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>