<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ include file="../base.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Setting</title>
    <%@ include file="../head.jsp"%>  
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta http-equiv="keywords" content="jinnan,jinn">
	<meta http-equiv="description" content="jinn">
	
	<!-- Css -->
	<link href="${BOOTSTRAP_CSS}" rel="stylesheet" media="screen">
	<link href="${BOOTSTRAP_THEME_CSS}" rel="stylesheet" media="screen">
	
	<!-- Js -->
	<script type="text/javascript" src="${JQUERY}"></script>
	<script type="text/javascript" src="${BOOTSTRAP_JS}"></script>
	<script type="text/javascript" src="${AJAXANYWHERE}"></script>
	<script type="text/javascript" src="${JQUERY_MD5}"></script>
	<script type="text/javascript" src="${JQUERY_FORM}"></script>
	<script type="text/javascript" src="${RESOURCE}/js/util/jinva.js"></script>
	
	<script type="text/javascript" src="${RESOURCE}/js/setting/index.js"></script>
	
	<style type="text/css">
    	.photo{
    		width: 180px;
    		height: 180px;
    	}
    	.photo_input{
    	}
    	.input-label{
    		width:100px;
    	}
    </style>
</head>
<body>

	<%@ include file="../nav_top.jsp" %>


	<h1>Setting</h1>
	
	<div id="mainContent" style="display:none;margin-left:20px;">
		<ul class="nav nav-tabs">
			<li class="active"><a href="#basic" data-toggle="tab">基本</a></li>
			<li><a href="#avatar" data-toggle="tab">头像</a></li>
			<li><a href="#other" data-toggle="tab">其他</a></li>
		</ul>
		
		<div class="tab-content">
			<div class="tab-pane active" id="basic">
				<div class="well">
					<form class="form-horizontal" action="aa_editUser" role="form" style="max-width: 800px; margin-left: 100px;">
						<div class="form-group" id="password-group">
							<div class="input-group">
								<span class="input-group-addon"><div class="input-label">新密码</div></span>
								<input id="password" name="password" type="password" class="form-control"> 
							</div>
							<span class="help-inline" id="password-tip"></span>
						</div>
						<div class="form-group" id="repassword-group">
							<div class="input-group">
								<span class="input-group-addon"><div class="input-label">确认密码</div></span>
								<input id="repassword" name="repassword" type="password" class="form-control"> 
							</div>
							<span class="help-inline" id="repassword-tip"></span>
						</div>
						<div class="form-group" id="nickname-group">
							<div class="input-group">
								<span class="input-group-addon"><div class="input-label">昵称</div></span>
								<input id="nickname" name="nickname" type="text" class="form-control" required="required" value="${user.nickname}"> 
							</div>
							<span class="help-inline" id="nickname-tip"></span>
							<p class="help-block">字母，数字，汉字皆可</p>
						</div>
						<div class="form-group">
							<div style="text-align:center">
							 	<a class="btn btn-primary" id="saveBtn">保存</a>
							 	<button id="invalidSubmit" type="submit" style="display:none"></button>
							 	<span class="help-inline" id="save-tip"></span>
							 </div>
						</div>
					</form>
				</div>
			</div>
			<div class="tab-pane" id="avatar">
				<div class="well">
					<form action="${CONTEXT_PATH}/setting/uploadSingle" id="photoForm" method="post" enctype="multipart/form-data" >
						<img id="photo" class="photo" alt="" src="${CONTEXT_PATH}/getImage/1">
						<input class="photo_input" type="file" name="file" onchange="uploadPhoto()">
						<input name="type" value="1" style="display:none">
					</form>
					<div id="myResultsDiv"></div>
				</div>
			</div>
			<div class="tab-pane" id="other">其他啊其他</div>
		</div>
	</div>


	<%@ include file="../nav_bottom.jsp" %>
</body>
</html>