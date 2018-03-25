<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<link rel="stylesheet" href="/JZManage/static/modules/css/reveal.css">
<script type="text/javascript" src="/JZManage/static/modules/js/jquery.reveal.js"></script>
	<title>积分商城管理</title>
	<meta name="decorator" content="default"/>
	<style type="text/css">
		#tableOuter{
			 height:400px;
			 overflow-y:auto;
		}
	</style>
	<script type="text/javascript">
	var productInfos=[]; //存放后台传的所有数据
	var option="";       //下拉框选择的类型
	var newdata="";      //获取新的数组
		$(document).ready(function() {
			$("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
			
			/* 获取后台传的全部数据，并且将其存入productInfos中 */
			<c:forEach items="${product.list}" var="productInfo">
			var productInfo = new Object();
			productInfo.id = '${productInfo.id}';
			productInfo.name = '${productInfo.name}';
			productInfo.number = '${productInfo.number}';
			productInfo.type = '${productInfo.type}';
			productInfo.price = '${productInfo.price}';
			productInfos.push(productInfo);
			</c:forEach>
			//console.log(productInfos); 
			/* 当页面刚一加载完 */
			option=$("#option2").val(); //选中的类型1或者3或者5
			if(option==1 ||option==3){
				newdata=findSameTypeData(option,productInfos);
			}else if(option==5){
				newdata=productInfos;
			}else{
				alert("无数据！")
			}
			var tbody=$("tbody");
			createTable(newdata,tbody);
			clickBtn();
			/* 当改变下拉框时，动态创建表格 */
			$("#option2").on("change",function(){
				option=$(this).val();
				if(option==1 ||option==3){
					newdata=findSameTypeData(option,productInfos);
				}else if(option==5){
					newdata=productInfos;
					console.log(newdata);
				}else{
					alert("无数据！")
				}
				var tbody=$("tbody");
				createTable(newdata,tbody);
				clickBtn();
			});
			
		});
		// 点击确定按钮时将选择的名称放到商品名称输入框中
		function clickBtn(){
			$(".confirm").on("click",function(){
				var index=$(this).parent().parent().index();
				$('#xuanze').val(newdata[index].name);
				$('#myModal').hide();
				$('.reveal-modal-bg').hide();
				//console.log(newdata);
				$("#name").val(newdata[index].id);
			})
		}
		
		//找到对应的option相同类型的数据，并放入到新的数组中
		function findSameTypeData(option,arr){
			var tableData=[];
			 for(var i=0;i<arr.length;i++){
				 if(option==arr[i].type){
					 tableData.push(arr[i]);
				 }
			 }
			 return tableData;
		}
		//动态创建表格
		function createTable(arr,parent){
			//创建表格之前，清除表格
			$("tbody").html("");
			for(var i=0;i<arr.length;i++){
				var tr = $("<tr></tr>");
				tr.appendTo(parent);
				var td1 = $("<td><a href='${ctx}/productinfo/productInfo/form?id=${"+arr[i].id+"}'>" + arr[i].name
						+ "</td>");
				var td2 = $('<td>' + arr[i].number
						+ '</td>');
				var td3 = $('<td>' + arr[i].type
						+ '</td>');
				var td4 =$('<td>' + arr[i].price
						+ '</td>');
				var td5 = $("<td><input type='button' value='确定' class='confirm' />"
						+ '</td>');
				td1.appendTo(tr);
				td2.appendTo(tr);
				td3.appendTo(tr);
				td4.appendTo(tr);
				td5.appendTo(tr);
			}
		}
		
		
		function option(){
			var option=$('#option2').val();//选中的文本
			$('#prizeType').val(option);
			/* location.href="${ctx}/productinfo/productInfo/list"; */
			$.ajax({
				type: "GET",
				url: "${ctx}/productinfo/productInfo/getAll",
				async: true,
				dataType: "json",
				data: {"selsect":option},
				success: function(data){
					console.log(data);
				},
				error: function(){
					alert("出错");
				}
			});
		}
		
		function zhang(id,name){	
			$('#xuanze').val(name);
			$('#name').val(id);
			$('.idText').val(id);
			$('#myModal').hide();
			$('.reveal-modal-bg').hide();
		};
		$(function(){
			$('#abiaoqian').click(function(){
				$('#myModal').show();
				$('.reveal-modal-bg').show();
			}) 
		})
		
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/integralmall/integralMall/">积分商城列表</a></li>
		<li class="active"><a href="${ctx}/integralmall/integralMall/form?id=${integralMall.id}">积分商城<shiro:hasPermission name="integralmall:integralMall:edit">${not empty integralMall.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="integralmall:integralMall:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="integralMall" action="${ctx}/integralmall/integralMall/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		
		<div class="control-group">
			<label class="control-label">商品名称：</label>
			<div class="controls">
				<!-- 产品名称 -->
				<input type="text" id="xuanze" style="width: 240px;" readonly="readonly" value="" />
				<!-- 产品ID -->
				<form:input path="name" name="name" type="hidden" htmlEscape="false" maxlength="64" id="name" class="input-xlarge required"/>
				<a id="abiaoqian" href="${ctx}/productinfo/productInfo/list" class="big-link" data-reveal-id="myModal" data-animation="none">选择</a>
				<input  class="idText" type="text" style="display:none"/>
			</div>
		</div>
		<!-- 弹出内容 -->
		<div id="myModal" class="reveal-modal">
			<h1>产品中心</h1>
				<div class="control-group">
					<label class="control-label">奖品类型：</label>
					<select id="option2">
					    <option value="5">所有商品</option>
						<option value="1">卡卷商品</option>
						<option value="3">物流商品</option>
					</select>
					<!-- 产品类型 -->
					<form:input path="prizeType" id="prizeType" type="hidden" htmlEscape="false" maxlength="64"  class="input-xlarge required"/>
				</div>
				<div id="tableOuter">
					<table id="contentTable" class="table table-striped table-bordered table-condensed">
						<thead>
							<tr>
								<th>产品名称</th>
								<th>产品编号</th>
								<th>产品类型</th>
								<th>产品价格</th>
								<shiro:hasPermission name="productinfo:productInfo:edit"><th>操作</th></shiro:hasPermission>
							</tr>
						</thead>
						<tbody>
					    </tbody>
				  </table>
			 </div>
			
		</div>
		<!-- 弹出内容 -->
		<div class="control-group">
			<label class="control-label">所需积分：</label>
			<div class="controls">
				<form:input path="exchange" name="exchange" htmlEscape="false" maxlength="255" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">是否推荐：</label>
			<div class="controls">
				<form:select path="recommend" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('mall_recommend')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">总数：</label>
			<div class="controls">
				<form:input path="total" htmlEscape="false" maxlength="255" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">剩余数量：</label>
			<div class="controls">
				<form:input path="remainingQuantity" htmlEscape="false" maxlength="255" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="integralmall:integralMall:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
	
</body>
</html>