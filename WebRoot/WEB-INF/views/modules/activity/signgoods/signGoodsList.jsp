<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>扫描发货管理</title>
	<meta name="decorator" content="default"/>
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
				 var startTime11=$("#startTime11").val();
				$("#startTime").val(startTime11); 
				var endTime11=$("#endTime11").val();
				$("#endTime").val(endTime11); 
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
		
		$(document).ready(function() {
			$("#btnExport").click(function(){
				top.$.jBox.confirm("确认要导出发货单数据吗？","系统提示",function(v,h,f){
					if(v=="ok"){
						location.href="${ctx}/signgoods/signGoods/export";
					}
				},{buttonsFocus:1});
				top.$('.jbox-body .jbox-icon').css('top','55px');
			});
			//分页加载
			$(function() {
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
					var outNo=$("#outNo").val();
					var sellerName=$("#sellerName").val();
					var startTime=$("#startTime").val();
					var endTime=$("#endTime").val();
					var pageNumber="1";
					$.ajax({
						data: {
							outNo: outNo,
							sellerName: sellerName,
							startTime: startTime,
							endTime: endTime,
							pageNumber: pageNumber
						},

						type: "GET",
						dataType: "JSON",
						url: "${ctx}/signgoods/signGoods/loadSellerInfo",
						success: function(data) {
							if(data != false) {
								checkLoad(data);
							} else {
								$("#contentTable tbody").html("");
								$("#admin-curent").val("1");
								$("#admin-total").text('0');
							}
						},
						error: function() {
							alert("网络连接异常，请稍后再试");
						}
					});
				});
			})
		});
		/* 分页查询 */
		function checkLoad(data) {
			$("#contentTable tbody").html("");
			$("#admin-curent").val(data.pageNumber);
			$("#admin-total").text(data.sellInfoCount);
			$("#admin-page").text(data.pageNumber); 
			for(var i = 0; i < data.outList.length; i++) {
				var adminObj = $(".admin-obj:eq(0)").clone(true);
				var adminArray = data.outList[i];
				adminObj.find(".recordId").text(adminArray.recordId);
				adminObj.find(".outNo").text(adminArray.outNo);
				adminObj.find(".brandsName").text(adminArray.productName);
				adminObj.find(".recsellerName").text(adminArray.recsellerName);
				adminObj.find(".outDate").text(adminArray.outTime);
				$("#contentTable tbody").append(adminObj);
			}
		}
		function adminAjax(page) {
			var outNo=$("#outNo").val();
			var sellerName=$("#sellerName").val();
			var startTime=$("#startTime").val();
			var endTime=$("#endTime").val();
			$.ajax({
				data: {
					outNo: outNo,
					sellerName: sellerName,
					startTime: startTime,
					endTime: endTime,
					pageNumber: page
				},
				type: "GET",
				dataType: "JSON",
				url: "${ctx}/signgoods/signGoods/loadStoreInfo",
				success: function(data) {
					console.log(data);
					checkLoad(data);
				},
				error: function() {
					alert("网络连接异常，请稍后再试");
				}
			});
		}
		
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/signgoods/signGoods/">扫描发货列表</a></li>
		<%-- <shiro:hasPermission name="signgoods:signGoods:edit"><li><a href="${ctx}/signgoods/signGoods/form">扫描发货添加</a></li></shiro:hasPermission> --%>
	</ul>
	<form:form id="searchForm" modelAttribute="signGoods" action="" method="post" class="breadcrumb form-search"> 
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>发货单号：</label>
				<input id="outNo" name="outNo" maxlength="255" class="input-medium"/>
			</li>
			<li><label>渠道商名称：</label>
				<input id="sellerName" name="sellerName" maxlength="255" class="input-medium"/>
			</li>
			<li><label>发货时间：</label>
				<input name="startTime" id="startTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>至
				<input name="endTime" id="endTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</li> 
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="button" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form> 
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>发货记录ID</th>
				<th>发货单号</th>
				<th>品种名称</th>
				<th>渠道商名称</th>
				<th>发货时间</th>
			</tr>
		</thead>
		<tbody>
		<%-- <c:forEach items="${outList}" var="sellerOut">
			<tr>
				<td>
					${sellerOut.recordId}
				</td>
				<td>
					${sellerOut.outNo}
				</td>
				<td>
					${sellerOut.productName}
				</td>
				<td>
					${sellerOut.recsellerName}
				</td>
				<td>
					<fmt:formatDate value="${sellerOut.outDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${signGoods.createTime}
				</td>
			</tr>
		</c:forEach> --%>
		
		
		</tbody>
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
					<td class="recordId"></td>
					<td class="outNo"></td>
					<td class="brandsName"></td>
					<td class="recsellerName"></td>
					<td class="outDate"></td>
				</tr>
			</table>
		</div>
	
	<div class="pagination">${page}</div>
</body>
</html>