<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>产品管理管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#btnExport").click(function(){
				top.$.jBox.confirm("确认要导出产品数据吗？","系统提示",function(v,h,f){
					if(v=="ok"){
						$("#searchForm").attr("action","${ctx}/productinfo/productInfo/export");
						$("#searchForm").submit();
					}
				},{buttonsFocus:1});
				top.$('.jbox-body .jbox-icon').css('top','55px');
			});
			$("#btnImport").click(function(){
				$.jBox($("#importBox").html(), {title:"导入数据", buttons:{"关闭":true}, 
					bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！"});
			});
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").attr("action","${ctx}/productinfo/productInfo/list");
			$("#searchForm").submit();
        	return false;
        }
	</script>
</head>
<body>
	<div id="importBox" class="hide">
		<form id="importForm" action="${ctx}/productinfo/productInfo/import" method="post" enctype="multipart/form-data"
			class="form-search" style="padding-left:20px;text-align:center;" onsubmit="loading('正在导入，请稍等...');"><br/>
			<input id="uploadFile" name="file" type="file" style="width:330px"/><br/><br/>　　
			<input id="btnImportSubmit" class="btn btn-primary" type="submit" value=" 导    入   "/>
			<a href="${ctx}/productinfo/productInfo/import/template">下载模板</a>
		</form>
	</div>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/productinfo/productInfo/list">产品管理列表</a></li>
		<shiro:hasPermission name="productinfo:productInfo:edit"><li><a href="${ctx}/productinfo/productInfo/form">产品管理添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="productInfo" action="${ctx}/productinfo/productInfo/list" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>产品名称：</label>
				<form:input path="name" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>产品编号：</label>
				<form:input path="number" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<%-- <li><label>产品价格：</label>
				<form:input path="price" htmlEscape="false" class="input-medium"/>
			</li> --%>
			<li><label>商品类型</label>
				<form:select path="type" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('productInfo_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<%-- <li><label>产品单位：</label>
				<form:input path="unit" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li> --%>
			<li class="btns">
				<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
				<input id="btnExport" class="btn btn-primary" type="button" value="产品导出"/>
				<input id="btnImport" class="btn btn-primary" type="button" value="产品导入"/>
			</li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>产品名称</th>
				<th>产品编号</th>
				<th>图片</th>
				<th>产品类型</th>
				<!-- <th>创建时间</th>
				<th>修改时间</th> -->
				<th>产品价格</th>
				<th>产品单位</th>
				<th>酒精度数</th>
				<th>规格</th>
				<th>净含量</th>
				<th>产品描述</th>
				<shiro:hasPermission name="productinfo:productInfo:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="productInfo">
			<tr>
				<td><a href="${ctx}/productinfo/productInfo/form?id=${productInfo.id}">
					${productInfo.name}
				</a></td>
				<td>
					${productInfo.number}
				</td>
				<td style="text-align: center;">
					<img alt="缺图" style="width: 40px;height: 40px;" src="${productInfo.imgid}">  
				</td>
				<td>
					${fns:getDictLabel(productInfo.type, 'productInfo_type', '')}
				</td>
				<%-- <td>
					<fmt:formatDate value="${productInfo.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<fmt:formatDate value="${productInfo.updateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td> --%>
				<td>
					${productInfo.price}
				</td>
				<td>
					${productInfo.unit}
				</td>
				<td>
					${productInfo.alcoholic}
				</td>
				<td>
					${productInfo.productsize}
				</td>
				<td>
					${productInfo.capacity}
				</td>
				<td>
					${productInfo.description}
				</td>
				<shiro:hasPermission name="productinfo:productInfo:edit"><td>
    				<a href="${ctx}/productinfo/productInfo/form?id=${productInfo.id}">修改</a>
					<a href="${ctx}/productinfo/productInfo/delete?id=${productInfo.id}" onclick="return confirmx('确认要删除该产品管理吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>