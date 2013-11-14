<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ include file="base.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<title><spring:message code="main.title" /></title>
    <%@ include file="./head.jsp"%>  
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    
    <!-- Css -->
	<link href="${BOOTSTRAP_CSS}" rel="stylesheet" media="screen">
	<link href="${BOOTSTRAP_THEME_CSS}" rel="stylesheet" media="screen">
	
	<link href="${RESOURCE}/css/login.css" rel="stylesheet" media="screen">
	
	<!-- Js -->
	<script type="text/javascript" src="${JQUERY}"></script>
	<script type="text/javascript" src="${BOOTSTRAP_JS}"></script>
	<script type="text/javascript" src="${JQUERY_MD5}"></script>
	
	<script type="text/javascript" src="${RESOURCE}/js/login.js"></script>
	
	<%--
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title><s:text name="main.title"></s:text></title>
    <link rel="stylesheet" type="text/css" href="css/login.css" />
    <link rel="stylesheet" type="text/css" href="${BOOTSTRAP_CSS}" />
    <script type="text/javascript" src="${JQUERY}"></script>
    <script type="text/javascript" src="${JQUERY_COOKIE}"></script>  
    <script type="text/javascript" src="${JQUERY_MD5}"></script>  
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/util/jinva.js"></script>
    <script type="text/javascript" src="${BOOTSTRAP_JS}"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/login.js"></script>
     --%>
</head>
<body>


	<div class="frame">
		<div class="left">
		</div>
		<div class="right">
		<div class="good"></div>
			<div class="login_panel">
				<div class="box">
					<input class="input" type="text" id="username-login" name="username" placeholder="用户名" value="jn"></input>
					<input class="input" type="password" id="password-login" name="password" placeholder="密码" value="1"></input>
				</div>
				<div class="box">
					<input class="btn btn-success" type="button" id="submit" value="登录"></input>
					<input class="btn btn-success" type="button" value="注册" data-toggle="modal" data-target="#signupModal"></input>
				</div>
			</div>
		</div>
		<div style="clear:both"></div>
	</div>

	<div class="modal fade" id="signupModal" style="display:none">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
					<h3>新用户注册</h3>
				</div>
				<div class="modal-body">
					<form class="form-horizontal" id="signup-form" role="form">
						<div class="form-group" id="name-group">
							<label class="control-label" for="name">登陆名</label>
							<div class="col-sm-10">
								<input type="text" class="form-control" id="name" onkeyup="validateInput(this)" onafterpaste="validateInput(this)">
								<span class="help-inline" id="name-tip"></span>
								<p class="help-block">字母，数字皆可</p>
							</div>
						</div>
						<div class="form-group" id="password-group">
							<label class="control-label" for="password">密码</label>
							<div class="col-sm-10">
								<input type="password" class="form-control" id="password">
								<span class="help-inline" id="password-tip"></span>
							</div>
						</div>
						<div class="form-group" id="repassword-group">
							<label class="control-label" for="repassword">确认密码</label>
							<div class="col-sm-10">
								<input type="password" class="form-control" id="repassword">
								<span class="help-inline" id="repassword-tip"></span>
							</div>
						</div>
						<div class="form-group" id="nickname-group">
							<label class="control-label" for="nickname">昵称</label>
							<div class="col-sm-10">
								<input type="text" class="form-control" id="nickname">
								<span class="help-inline" id="nickname-tip"></span>
								<p class="help-block">字母，数字，汉字皆可</p>
							</div>
						</div>
					</form>
				</div>
				<div class="modal-footer">
					<a href="#" class="btn btn-small" data-dismiss="modal">关闭</a>
					<a href="#" class="btn btn-primary" id="btn-signup" data-loading-text="Loading...">填好啦!</a>
				</div>
			</div><!-- /.modal-content -->
		</div><!-- /.modal-dialog -->
	</div><!-- /.modal -->


	<div class="modal fade" id="loginModal">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-body">
					<h4 id="login-tip"></h4>
				</div>
			</div> <!-- /.modal-content -->
		</div> <!-- /.modal-dialog -->
	</div> <!-- /.modal -->
</body>
</html>