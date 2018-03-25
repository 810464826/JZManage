<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>核销统计管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#btnSubmit").on("click",function(){
				var companyName=$("#companyName").val();
				//alert(companyName);
				$("#searchForm").attr("action","${ctx}/localcancel/localCancel/?companyName="+companyName)
			})
			
			
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
		$(document).ready(function() {
			$("#btnExport").click(function(){
				top.$.jBox.confirm("确认要导出核销统计数据吗？","系统提示",function(v,h,f){
					if(v=="ok"){
						location.href="${ctx}/localcancel/localCancel/export";
						
					}
				},{buttonsFocus:1});
				top.$('.jbox-body .jbox-icon').css('top','55px');
			});
		});
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/localcancel/localCancel/">核销统计列表</a></li>
		<%-- <shiro:hasPermission name="localcancel:localCancel:edit"><li><a href="${ctx}/localcancel/localCancel/form">核销统计添加</a></li></shiro:hasPermission> --%>
	</ul>
	<form:form id="searchForm" modelAttribute="localCancel" action="${ctx}/localcancel/localCancel/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>核销经销商：</label><sys:treeselect id="company" name="company.id" value="${user.company.id}" labelName="company.name" labelValue="${user.company.name}" 
				title="公司" url="/sys/office/treeData?type=1" cssClass="input-small" allowClear="true"/></li>
			<%-- <li><label>核销经销商：</label>
				<form:input path="cancelOffice" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li> --%>
			<li><label>活动名称：</label>
				<form:input path="activityName" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>活动区域：</label>
				<form:input path="activityPrizesAddress" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>活动经销商：</label>
				<form:input path="activityPrizesId" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>核销数量：</label>
				<form:input path="cancelNumber" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>核销时间：</label>
				<input name="startTime" id="startTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${localCancel.createDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>至
				<input name="endTime" id="endTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${localCancel.createDate}" pattern="yyyy-MM-dd"/>"
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
				<th>活动经销商</th>
				<th>活动区域</th>
				<th>核销经销商</th>
				<th>核销人区域</th>
				<th>核销人姓名</th>
				<th>核销人手机</th>
				<th>奖品名称</th>
				<th>核销数量</th>
				<th>核销时间</th>
				<%-- <shiro:hasPermission name="localcancel:localCancel:edit"><th>操作</th></shiro:hasPermission> --%>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="localCancel">
			<tr>
				<td>
					${localCancel.activityName}
				</td>
				
				<td>
					${localCancel.activityPrizesOffice}
				</td>
				<td>
					${localCancel.activityPrizesAddress}
				</td>
				
				<td>
					${localCancel.cancelOffice}
				</td>
				<td>
					${localCancel.cancelAddress}
				</td>
				<td>
					${localCancel.cancelUserName}
				</td>
				<td>
					${localCancel.cancelUserPhone}
				</td>
				
				<td>
					${localCancel.prizeName}
				</td>
				<td>
					${localCancel.cancelNumber}
				</td>
				<td>
					${localCancel.createTime}
				</td>
				<%-- <shiro:hasPermission name="localcancel:localCancel:edit"><td>
    				<a href="${ctx}/localcancel/localCancel/form?id=${localCancel.id}">修改</a>
					<a href="${ctx}/localcancel/localCancel/delete?id=${localCancel.id}" onclick="return confirmx('确认要删除该核销统计吗？', this.href)">删除</a>
				</td></shiro:hasPermission> --%>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>