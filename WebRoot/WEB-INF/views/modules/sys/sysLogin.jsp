<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="org.apache.shiro.web.filter.authc.FormAuthenticationFilter"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>${fns:getConfig('productName')} 登录</title>
	<meta name="decorator" content="blank"/>
	<style type="text/css">
      html,body,table{width:100%;text-align:center;overflow:hidden!important;}
      body{background-image:url("${ctxStatic}/modules/sys/img/bg_login.png");background-size: cover;background-repeat: no-repeat;}
      #middleYZ{margin-top: 15%;}
        .form-signin-heading{font-family:Microsoft YaHei, Helvetica, Georgia, Arial, sans-serif, 黑体;letter-spacing:10px;font-size:50px;color:white;float: left;margin:100px 0px 0px 15%;}
        .form-signin{position:relative;text-align:left;width:300px;height:250px;float: right;margin: 40px 20% 0px 0px;}
        .form-signin .checkbox{margin-bottom:10px;color:#0663a2;}
        .form-signin .input-label{font-size:16px;line-height:23px;color:#999;}
        #username{margin: 40px 0px 20px 0px}
        .form-signin .input-block-level{font-size:16px;height:40px;background: rgba(225,225,225,0.5);border-style: none;color: white;margin-bottom: 20px;}
		input::-webkit-input-placeholder{ color:white; }
		#comein{width: 100px;height: 30px;font-size: 12px;line-height: 8px;border-radius: 5px;}
        .form-signin .btn.btn-large{font-size:16px;}
	    .form-signin #themeSwitch{position:absolute;right:15px;bottom:10px;}
        .form-signin div.validateCode {padding-bottom:15px;}
	    .mid{vertical-align:middle;}
        .header{height:80px;padding-top:20px;} .alert{position:relative;width:300px;margin:0 auto;*padding-bottom:0px;}
        label.error{background:none;width:270px;font-weight:normal;color:inherit;margin:0;}
        .dropdown-toggle{position: absolute;top:-75px;right:5px;width:65px;}
        .footer{clear: both;color: white;margin-top: 40%;}
    </style>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#loginForm").validate({
				rules: {
					validateCode: {remote: "${pageContext.request.contextPath}/servlet/validateCodeServlet"}
				},
				messages: {
					username: {required: "请填写用户名."},password: {required: "请填写密码."},
					validateCode: {remote: "验证码不正确.", required: "请填写验证码."}
				},
				errorLabelContainer: "#messageBox",
				errorPlacement: function(error, element) {
					error.appendTo($("#loginError").parent());
				} 
			});
		});
		// 如果在框架或在对话框中，则弹出提示并跳转到首页
		if(self.frameElement && self.frameElement.tagName == "IFRAME" || $('#left').length > 0 || $('.jbox').length > 0){
			alert('未登录或登录超时。请重新登录，谢谢！');
			top.location = "${ctx}";
		}
	</script>
</head>
<body>
	<!--[if lte IE 6]><br/><div class='alert alert-block' style="text-align:left;padding-bottom:10px;"><a class="close" data-dismiss="alert">x</a><h4>温馨提示：</h4><p>你使用的浏览器版本过低。为了获得更好的浏览体验，我们强烈建议您 <a href="http://browsehappy.com" target="_blank">升级</a> 到最新版本的IE浏览器，或者使用较新版本的 Chrome、Firefox、Safari 等。</p></div><![endif]-->
	<div class="header">
		<div id="messageBox" class="alert alert-error ${empty message ? 'hide' : ''}"><button data-dismiss="alert" class="close">×</button>
			<label id="loginError" class="error">${message}</label>
		</div>
	</div>
	<div id="middleYZ">
		<h1 class="form-signin-heading">${fns:getConfig('productName')}</h1>
		<form id="loginForm" class="form-signin" action="${ctx}/login" method="post">
			<input type="text" id="username" name="username" class="input-block-level required" value="${username}" placeholder="请输入账号">
			<input type="password" id="password" name="password" class="input-block-level required" placeholder="请输入密码">
			<c:if test="${isValidateCodeLogin}"><div class="validateCode">
				<label class="input-label mid" for="validateCode">验证码</label>
				<sys:validateCode name="validateCode" inputCssStyle="margin-bottom:0;"/>
			</div></c:if><%--
			<label for="mobile" title="手机登录"><input type="checkbox" id="mobileLogin" name="mobileLogin" ${mobileLogin ? 'checked' : ''}/></label> --%>
			<input id="comein" class="btn btn-large btn-primary" type="submit" value="登 录"/>&nbsp;&nbsp;
			<label for="rememberMe" title="下次不需要再登录" style="color: white;"><input type="checkbox" id="rememberMe" name="rememberMe" ${rememberMe ? 'checked' : ''}/> 记住我</label>
			<div id="themeSwitch" class="dropdown">
				<a class="dropdown-toggle" data-toggle="dropdown" href="#">${fns:getDictLabel(cookie.theme.value,'theme','默认主题')}<b class="caret"></b></a>
				<ul class="dropdown-menu">
				  <c:forEach items="${fns:getDictList('theme')}" var="dict"><li><a href="#" onclick="location='${pageContext.request.contextPath}/theme/${dict.value}?url='+location.href">${dict.label}</a></li></c:forEach>
				</ul>
				<!--[if lte IE 6]><script type="text/javascript">$('#themeSwitch').hide();</script><![endif]-->
			</div>
		</form>
	</div>
	<div class="footer">
		Copyright &copy; ${fns:getConfig('copyrightYear')} <a href="${pageContext.request.contextPath}${fns:getFrontPath()}/a">${fns:getConfig('productName')}</a> - Powered By <a href="${fns:getConfig('productAddress')}" target="_blank">${fns:getConfig('autor')}</a> ${fns:getConfig('version')} 
	</div>
	<script src="${ctxStatic}/flash/zoom.min.js" type="text/javascript"></script>
</body>
</html>