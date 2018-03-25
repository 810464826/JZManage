<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>关注用户管理</title>
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
		<li class="active"><a href="${ctx}/followuser/wxUser/">关注用户列表</a></li>
		<%-- <shiro:hasPermission name="followuser:wxUser:edit"><li><a href="${ctx}/followuser/wxUser/form">关注用户添加</a></li></shiro:hasPermission>  --%>
	</ul>
	<form:form id="searchForm" modelAttribute="wxUser" action="${ctx}/followuser/wxUser/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>微信名称：</label>
				<form:input path="nickname" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>头像</th>
				<th>微信名称</th>
				<th>性别</th>
				<th>手机号码</th>
				<th>国籍</th>
				<th>省</th>
				<th>市</th>
				<th>关注时间</th>
				<%-- <shiro:hasPermission name="followuser:wxUser:edit"><th>操作</th></shiro:hasPermission> --%>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="wxUser">
			<tr>
				<td style="width: 130px;height: 60px;">
					<div class="imgtest">
						<figure>
							<div>
							<img src="${wxUser.headimgurl}"/>
							</div>  
						</figure>
					</div>
				</td>
				<td>
					${wxUser.nickname}
				</td>
				<td>
					${fns:getDictLabel(wxUser.sex, 'sex', '')}
				</td>
				<td>
					${wxUser.phoneNumber}
				</td>
				<td>
					${wxUser.country}
				</td>
				<td>
					${wxUser.province}
				</td>
				<td>
					${wxUser.city}
				</td>
				<td>
					<fmt:formatDate value="${wxUser.follotime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<%-- <shiro:hasPermission name="followuser:wxUser:edit"><td>
    				<a href="${ctx}/followuser/wxUser/form?id=${wxUser.id}">修改</a>
					<a href="${ctx}/followuser/wxUser/delete?id=${wxUser.id}" onclick="return confirmx('确认要删除该关注用户吗？', this.href)">删除</a>
				</td></shiro:hasPermission> --%>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>

</body>
</html>