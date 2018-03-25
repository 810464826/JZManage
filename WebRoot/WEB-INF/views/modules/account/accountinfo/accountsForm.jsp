<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>对账管理管理</title>
	<meta name="decorator" content="default"/>
	<style>
	#reconciliation{
		height: 410px;
		overflow-y: auto;
		overflow-x: hidden;
		margin-top: 10px;
		margin-bottom:50px;
	}
	#reconciliation,#reconciliation table{
		width:800px;
		cellpadding: 0;
		text-align: center;
		cellspacing: 0;
	}
	#reconciliation table thead{
		height:30px;
		line-height:30px;
		background:#f5f5f5;
	}
	.control-group{
	
	}
	</style>
	<script type="text/javascript">
	
		$(document).ready(function() {
			
			//$("#name").focus();
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
			
			 //var aa=$("#reconciliation tbody tr").length;
			//alert(aa); 
			if($("#reconciliation tbody tr").length==0){
				$("#btnSubmit").attr("disabled","disabled");
			}else{
				$("#btnSubmit").removeAttr("disabled");
			} 	
			$("#btnSubmit").on("click",function(){
				var selectTime=$("#selectTime").val();
				//alert(selectTime)
				var selectedName=$("#manageSelect select").find("option:selected").text();;
				//alert(selectedName);
				var selectedIndex=$("#manageSelect select").get(0).selectedIndex;
				var valueId=$("#manageSelect select").get(0).options[selectedIndex].value;
				//alert(valueId);
				var loaclid = $("#localid").val();
				var textarea=$(".input-xxlarge").val();
				location.href="${ctx}/accountinfo/accounts/saveAcc?loaclid="+loaclid+"&textarea="+textarea+"&selectTime="+selectTime+"&valueId="+valueId+"&selectedName="+selectedName;
			});
			$(".search").click(function(){
				var name=$("#officeName").val();
				alert(name);
				var startTime=$("#startTime").val();
				var endTime=$("#endTime").val();
				location.href="${ctx}/accountinfo/accounts/search?name="+name+"&startTime="+startTime+"&endTime="+endTime;
			});
			$(".dealerName").click(function(){
				alert(44);
				var localId=$(".localId").val();
				alert(localId);
				$.ajax({
					type: "GET",
					url: "${ctx}/accountinfo/accounts/lookInfo",
					async: false,
					dataType: "json",
					data: {
						localId : localId
					},
					success: function(data){
						console.log(data);
						alert("hahah"+data.activityName);
						
						
						
						
					}
				});

				//location.href="${ctx}/accountinfo/accounts/lookInfo?localId="+localId;
				
				
			});
			
			
		});
		function aaa(id){
			$(".confirm").attr("disabled","disabled");
			$("#localid").val(id);
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/accountinfo/accounts/">对账管理列表</a></li>
		<li class="active"><a href="${ctx}/accountinfo/accounts/form?id=${accounts.id}">对账管理<shiro:hasPermission name="accountinfo:accounts:edit">${not empty accounts.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="accountinfo:accounts:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<div class="control-group">
			<!-- <label class="control-label">经销商名称：</label><input type="text" class="name"/> -->
			<label>经销商名称：</label><sys:treeselect id="office" name="office.id" value="${office.id}" labelName="office.name" labelValue="${office.name}" 
				title="公司" url="/sys/office/treeData?type=1" cssClass="input-small" allowClear="true"/>
			<label class="control-label" style="margin-left: 30px">核销时间：</label><input name="startTime" id="startTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${accounts.accountsTime}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>至
			<input name="endTime" id="endTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${accounts.accountsTime}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			<button class="search">查询</button>
	</div>
	<form:form id="inputForm" modelAttribute="accounts" action="${ctx}/accountinfo/accounts/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<input type="hidden" id="localid" value=""/>
		
		<div id="reconciliation">
			<table border="1" bordercolor="#ccc">
				<thead>
					<tr>
						<th>经销商</th>
						<th>总计</th>
						<!-- <th>经销商是否在线</th> -->
						<th>操作</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${localList}" var="local" >
						<tr>
							<td class="dealerName">${local.cancelOffice}<input type="hidden" value="${local.id}" class="localId"/></td>
							<td>${local.cancelNumber}</td>
							<%-- <c:if test="${local.existence == '1'}">
								<td>在线</td>
							</c:if>
							<c:if test="${local.existence == '0'}">
								<td>离线</td>
							</c:if> --%>
							<td><input type="button" value="确定" class="confirm" onclick="aaa('${local.id}')"/></td>
						</tr>					
					</c:forEach>
				</tbody>
			</table>
		</div>
		<div class="control-group">
			<label class="control-label">对账时间：</label>
			<div class="controls">
				<input name="createTime" id="selectTime" type="text" readonly="readonly"
					maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${activityReport.createTime}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});" />
				<!-- <span class="help-inline"><font color="red">*</font> </span> -->
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">对账业务员：</label>
			<div class="controls" id="manageSelect">
				<select>
				<c:forEach items="${salesList}" var="sales">
					<option value="${sales.id}">${sales.name}</option>
				</c:forEach>
				</select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">对账详情：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">推送模板：</label>
			<div class="controls">
				yyyy-MM-dd(对账时间)×××(业务员)……(对账详情)
			</div>
		</div>
		<%-- <div class="remark">
		<strong>备注：</strong><form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
		</div> --%>
		<div class="form-actions">
			<shiro:hasPermission name="accountinfo:accounts:edit"><input id="btnSubmit" class="btn btn-primary" type="button" value="添加对账"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
	<script>
		
	</script>
</body>
</html>