<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>门店管理</title>
	<meta name="decorator" content="default"/>
	<%@include file="/WEB-INF/views/include/treetable.jsp" %>
	<style>
	 /* 分页开始 */
	.page-break{
		margin-top:1vw;
		margin-bottom:1vw;
		height:2vw;
		line-height:2vw;
		color: #999;
	}
	.page-btn{
		float:left;
		font-size:1vw;
		border:1px solid #D4D4D4;
		border-radius:4px;
	}
	.page-btn span:nth-child(2){
		display:inline-block;
		text-align:center;
		width:3vw;
	}
	.page-btn span:nth-child(1){
		border-right:1px solid #D4D4D4;
	}
	.page-btn span:nth-child(3){
		border-left:1px solid #D4D4D4;
	}
	.page-btn span:nth-child(1),.page-btn span:nth-child(3){
		display:inline-block;
		width:5vw;
		text-align:center;
		font-size:0.8vw;
	}
	
	.page-btn span:hover{
		cursor:default;
	}
	.page-btn span:nth-child(1):hover,.page-btn span:nth-child(3):hover{
		cursor:pointer;
	}
	.page-show{
		font-size:1vw;
		float:left;
		margin-left:2vw;
	}
	.page-show input{
		color: #999;
	    width: 30px;
	    height: 20px;
	    font-size: 14px; 
    	line-height: 20px;
		padding: 0;
		vertical-align: middle;
	    margin: -3px 0 0 0;
	    text-align: center;
	    box-shadow: inset 0 1px 1px rgba(0,0,0,0.075);
	    background-color: #fff;
	    transition: border linear .2s,box-shadow linear .2s;
	}
	/* 分页结束 */
	</style>
	<script type="text/javascript">
		$(document).ready(function() {
			var tpl = $("#treeTableTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
			var data = ${fns:toJson(list)}, rootId = "${not empty office.id ? office.id : '0'}";
			addRow("#treeTableList", tpl, data, rootId, true);
			$("#treeTable").treeTable({expandLevel : 5});  //树表的展开层次
			
			//点击定位弹出弹框,并让弹窗出现在指定的位置
			var windowHeight=$(window).height();
			var windowWidth=$(window).width();
			var positioningTop=windowHeight/2-250;
			var positioningLeft=windowWidth/2-400;
			$(".positioning").click(function(){
				var officeId=$(this).parent().parent().attr("id");
				//alert(id);
				window.open('${ctx}/sys/office/officePositioning?officeId='+officeId,'','width=800,height=500,top='+positioningTop+',left='+positioningLeft);
			}); 
			var len=$(".typeNum").length;
			//alert(len);
			for(var i=0;i<len;i++){
				//alert($(".typeNum")[i].value);
				if($(".typeNum")[i].value=="经销商"){
					$(".typeNum").eq(i).parent().find(".modify").hide();
				}
			};
			var type=$("#type option").eq(0).val();
			$("#type").change(function() {
				type = $("#type option:selected").val();
			});
			var coretype=$("#coretype option").eq(0).val();
			$("#coretype").change(function() {
				coretype = $("#coretype option:selected").val();
			});
			/* $("#btnSubmit").on("click",function(){
				//alert(11);
				var officename=$("#officeName").val();
				//alert(office);
				var actionUrl="${ctx}/sys/office/searchStore?type="+type+"&officename="+officename+"&coretype="+coretype;
				$("#searchForm").attr("action",actionUrl);
			}); */
			
			//**************
			$("#btnExport1").click(function(){
				top.$.jBox.confirm("确认要导出终端数据吗？","系统提示",function(v,h,f){
					if(v=="ok"){
						$("#searchForm").attr("action","${ctx}/sys/office/export");
						$("#searchForm").submit();
					}
				},{buttonsFocus:1});
				top.$('.jbox-body .jbox-icon').css('top','55px');
			});
			$("#btnImport1").click(function(){
				$.jBox($("#importBox1").html(), {title:"导入数据", buttons:{"关闭":true}, 
					bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！"});
			});
		});
		function addRow(list, tpl, data, pid, root){
			for (var i=0; i<data.length; i++){
				var row = data[i];
				if ((${fns:jsGetVal('row.parentId')}) == pid){
					$(list).append(Mustache.render(tpl, {
						dict: {
							type: getDictLabel(${fns:toJson(fns:getDictList('sys_office_type'))}, row.type)
						}, pid: (root?0:pid), row: row
					}));
					addRow(list, tpl, data, row.id);
				}
			}
		}
		//分页加载
		$(function() {
			$.ajax({
				data: {
				},
				type: "GET",
				dataType: "JSON",
				url: "${ctx}/sys/office/loadParentStoreInfo",
				success: function(data) {
					checkLoad(data);
				},
				error: function() {
					//alert("网络连接异常，请稍后再试");
				}
			});
			
			
			adminAjax(1);
			$(".manager-second-but").click(function() { //点击下一页
				var sellInfoCount = Number($("#admin-total").text());
				var page = Number($("#admin-page").text());
				var total=Math.ceil(sellInfoCount/30);
				if(total > page) {
					adminAjax(page + 1);
				}
			});

			$(".manager-first-but").click(function() { //点击上一页
				var page = Number($("#admin-page").text());
				if(page > 1) {
					adminAjax(page - 1);
				}
			});

			$("#btnSubmit").click(function() {
				var officeName=$("#officeName").val();
				var type=$("#s2id_type .select2-chosen").html();
				var coretype=$("#s2id_coretype .select2-chosen").html();
				var pageNumber="1";
				var officeid=$("#officeid").val();
				var parentIds=$("#parentIds").val();
				var storeofficeid=$("#storeofficeid").val();
				$.ajax({
					data: {
						officeid:officeid,
						parentIds:parentIds,
						storeofficeid:storeofficeid,	
						officeName: officeName,
						type: type,
						coretype: coretype,
						pageNumber: pageNumber
					},

					type: "GET",
					dataType: "JSON",
					url: "${ctx}/sys/office/loadParentStoreInfo",
					success: function(data) {
						console.log(data);
						if(data != false) {
							checkLoad(data);
						} else {
							$("#contentTable tbody").html("");
							$("#admin-curent").val("1");
							$("#admin-total").text('0');
						}
					},
					error: function() {
						//alert("网络连接异常，请稍后再试");
					}
				});
			});
		
	});
	/* 分页查询 */
	function checkLoad(data) {
		$("#treeTableList").html("");
		$("#admin-curent").val(data.pageNumber);
		$("#admin-total").text(data.storeByTypeCount);
		$("#admin-page").text(data.pageNumber); 
		for(var i = 0; i < data.list.length; i++) {
			var adminObj = $(".admin-obj:eq(0)").clone(true);
			var adminArray = data.list[i];
			adminObj.find(".name").text(adminArray.name);
			adminObj.find(".addressname").text(adminArray.addressname);
			if(adminArray.type=="0"){
				adminObj.find(".type").text("经销商");
			}else if(adminArray.type=="1"){
				adminObj.find(".type").text("餐饮店");
			}else if(adminArray.type=="2"){
				adminObj.find(".type").text("烟酒店");
			}else if(adminArray.type=="3"){
				adminObj.find(".type").text("商超店");
			}else if(adminArray.type=="4"){
				adminObj.find(".type").text("KA专卖店");
			}else if(adminArray.type=="5"){
				adminObj.find(".type").text("夜店");
			}
			
			if(adminArray.coretype=="1"){
				adminObj.find(".coretype").text("核心店");
			}else if(adminArray.coretype=="2"){
				adminObj.find(".coretype").text("非核心店");
			}
			adminObj.find(".controll .position").attr("href","${ctx}/sys/office/officePositioning?officeId="+adminArray.id);
			adminObj.find(".controll .del").attr("href","${ctx}/sys/office/deleteStore?id="+adminArray.id);
			//当门店类型不等于0（门店），有修改，无添加下级机构
			if(adminArray.type!="0"){
				adminObj.find(".controll .modify").attr("href","${ctx}/sys/office/storeUpdate?id="+adminArray.id);
			}else{
				adminObj.find(".controll .modify").text("");
			}
			
			$("#treeTableList").append(adminObj);
		}
	}
	
	function adminAjax(page) {
		var officeName=$("#officeName").val();
		var type=$("#s2id_type .select2-chosen").html();
		var coretype=$("#s2id_coretype .select2-chosen").html();
		var officeid=$("#officeid").val();
		var parentIds=$("#parentIds").val();
		var storeofficeid=$("#storeofficeid").val();
		$.ajax({
			data: {
				officeid:officeid,
				parentIds:parentIds,
				storeofficeid:storeofficeid,	
				officeName: officeName,
				type: type,
				coretype: coretype,
				pageNumber: page
			},
			type: "GET",
			dataType: "JSON",
			url: "${ctx}/sys/office/loadParentStoreInfo",
			success: function(data) {
				checkLoad(data);
			},
			error: function() {
				//alert("网络连接异常，请稍后再试");
			}
		});
	}
	</script>
</head>
<body>
	<div id="importBox1" class="hide">
		<form id="importForm" action="${ctx}/sys/office/import" method="post" enctype="multipart/form-data"
			class="form-search" style="padding-left:20px;text-align:center;" onsubmit="loading('正在导入，请稍等...');"><br/>
			<input id="uploadFile" name="file" type="file" style="width:330px"/><br/><br/>　　
			<input id="btnImportSubmit" class="btn btn-primary" type="submit" value=" 导    入   "/>
			<a href="${ctx}/sys/office/import/template">下载模板</a>
		</form>
	</div>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/sys/office/storelist?officeid=${office.id}&parentIds=${office.parentIds}">门店列表</a></li>
		<shiro:hasPermission name="sys:office:edit"><li><a href="${ctx}/sys/office/storeAdd">门店添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="office" action="${ctx}/sys/office/storelist" method="post" class="breadcrumb form-search ">
		<ul class="ul-form">
			<li><label>门店名称：</label><sys:treeselect id="office" name="office.id" value="${office.id}" labelName="office.name" labelValue="${office.name}" 
				title="公司" url="/sys/office/treeData?type=1" cssClass="input-small" allowClear="true"/></li>
			<li><label>门店类型：</label>
			<select id="type">
					<option>请选择</option>
					<option>餐饮店</option>
					<option>烟酒店</option>
					<option>商超店</option>
					<option>KA专卖店</option>
					<option>夜店</option>
			</select>
			</li>
			<li><label>核心类型：</label>
			<select id="coretype">
					<option>请选择</option>
					<option>核心店</option>
					<option>非核心店</option>
			</select>
			</li>
		</ul>
		<input type="hidden" value="${officeid}" id="officeid" />
		<input type="hidden" value="${parentIds}" id="parentIds" />
		<input type="hidden" value="${storeofficeid}" id="storeofficeid" />
		
		<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="button" value="查询"/>
				<input id="btnImport1" class="btn btn-primary" type="button" value="终端导入"/>
				<input id="btnExport1" class="btn btn-primary" type="button" value="终端导出"/>
		</li>
	</form:form>
	<sys:message content="${message}"/>
	<table id="treeTable" class="table table-striped table-bordered table-condensed">
		<thead><tr><th>门店名称</th><!-- <th>归属区域</th> --><th>地址</th><th>门店类型</th><th>核心类型</th><shiro:hasPermission name="sys:office:edit"><th>操作</th></shiro:hasPermission></tr></thead>
		<tbody id="treeTableList"></tbody>
	</table>
	<!-- 分页 -->
		<div class="page-break">
			<div class="page-btn">
				<span class="manager-first-but">« 上一页</span>
				<span id="admin-page">1</span>
				<span class="manager-second-but">下一页 »</span>
			</div>
			<div class="page-show">当前&nbsp;<input type="text" id="admin-curent" />&nbsp;/&nbsp;<input type="text" value="30" />&nbsp;条，共&nbsp;<span id="admin-total"></span>&nbsp;条</div>
		</div>
		<div style="display: none;">
			<table>
				<tr class="admin-obj">
					<td class="name"></td>
					<td class="addressname"></td>
					<td class="type"></td>
					<td class="coretype"></td>
					<td class="controll">
						<a class="position" href="">位置&nbsp;&nbsp;</a>
						<a class="modify" href="">修改&nbsp;&nbsp;</a>
						<a class="del" href="" onclick="return confirmx('要删除该机构及所有子机构项吗？', this.href)">删除&nbsp;&nbsp;</a>
					</td>
				</tr>
			</table>
		</div>
		<!-- 分页结束 -->
	
	<!--<td>{{row.area.name}}</td>  -->
	<%-- <td><a href="${ctx}/sys/office/storeUpdate?id={{row.id}}">{{row.name}}</a></td> --%>
	<script type="text/template" id="treeTableTpl">
		<tr id="{{row.id}}" pId="{{pid}}">
			<td><a href="#">{{row.name}}</a></td>
			
			<td>{{row.addressname}}</td>
			<td>
				{{row.type}}
			</td>
			<td>
				{{row.coretype}}
			</td>
			<shiro:hasPermission name="sys:office:edit"><td>
				<a href="#" class="positioning">位置&nbsp;&nbsp;</a>
                <input type="hidden" class="typeNum" value="{{row.type}}" />
				<a href="${ctx}/sys/office/storeUpdate?id={{row.id}}" class="modify">修改&nbsp;&nbsp;</a>
				<a href="${ctx}/sys/office/deleteStore?id={{row.id}}" onclick="return confirmx('要删除该机构及所有子机构项吗？', this.href)">删除&nbsp;&nbsp;</a>
				<a href="${ctx}/sys/office/form?parent.id={{row.id}}">添加下级机构&nbsp;&nbsp;</a> 
			</td></shiro:hasPermission>
		</tr>
	</script>
</body>
</html>