<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>表单提交</title>
</head>
<body>
	<form method="post" action="<%=request.getContextPath() %>/multi/saveForm" id="formSubmit" name="formSubmit">
		<input type="hidden" name="token" value="${token}"/>  
		<div style="margin-left: 100px;">
			<img src="img/logo.png" />
		</div>
		<div>
			<p class="l_m_linput">
				<span><font color="#fff">用户名：</font></span><input type="text"
					id="login_name" name="login_name" value="zijuntang"><em class="l_peoplecon"></em>
			</p>
			<p class="l_m_linput">
				<span><font color="#fff">密码：</font></span><input type="password" name="login_psw"
					id="login_psw" value="tangzijun"><em class="l_mimacon"></em>
			</p>
			<div class="l_m_linput2"></div>
			<div class="l_m_lload">
				<a href="javascript:document.getElementById('formSubmit').submit();">登录</a>
			</div>
		</div>
	</form>
</body>
</html>