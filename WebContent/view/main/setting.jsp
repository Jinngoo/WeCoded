<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ include file="../../base.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Setting</title>
    <link rel="stylesheet" type="text/css" href="${BOOTSTRAP_CSS}" />
    <script type="text/javascript" src="${JQUERY}"></script>
    <script type="text/javascript" src="${JQUERY_MD5}"></script>  
    <script type="text/javascript" src="${JQUERY_FORM}"></script>  
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/util/jinva.js"></script>
    <script type="text/javascript" src="${BOOTSTRAP_JS}"></script>
    <script type="text/javascript">
    	$(document).ready(function(){
    		$("#mainContent").slideDown("fast");
    		$("#saveBtn").click(function(){
    			updateBasicInfo();
    		});
    		$("#nickname").blur(function(){
    			validateNickname();
    		});
    		$("#password").blur(function(){
   				validatePassword();
    		});
    		$("#repassword").blur(function(){
   				validatePassword();
    		});
    	});
    	
    	function updateBasicInfo(){
    		$("#save-tip").html("");
    		if(validatePassword()){
    			if(validateNickname()){
    				var url = "ajax_updateBasicInfo";
    				var params = {"nickname":$("#nickname").val()};
    				if(J.isNotEmpty($("#password").val())){
    					$.extend(params, {"password":$.md5($("#password").val())});
    				}
    				$("#saveBtn").button("loading");
    				$.post(url, params, function(data, textStatus, jqXHR){
    					$("#saveBtn").button("reset");
    					var code = data.result["code"];
    					if(code == "success"){
    						$("#save-tip").html("保存成功");
    						//TODO
    						if(J.isNotEmpty($("#nickname").val())){
	    						top.$("#user_name").html($("#nickname").val());
    						}
    					}else if(code == "error"){
    						alert(data.result["message"]);
    					}else{
    						alert("Unknown error occured");
    					}
    				});
    			}
			}
    	}
    	
    	function validateNickname(){
    		if(J.isEmpty($("#nickname").val())){
				$("#nickname-group").addClass("error");
				return false;
			}else{
				$("#nickname-group").removeClass("error");
				return true;
			}
    	}
    	
    	function validatePassword(){
    		var password = $("#password").val();
			var repassword = $("#repassword").val();
			if(J.isNotEmpty(password) || J.isNotEmpty(repassword)){
    			if(password != repassword){
    				$("#password-group").addClass("error");
    				$("#repassword-group").addClass("error");
    				return false;
    			}
			}
			$("#password-group").removeClass("error");
			$("#repassword-group").removeClass("error");
			return true;
    	}
    	
    	function uploadPhoto(){
    		var form = $('#photoForm').ajaxSubmit({  });
    		var xhr = form.data('jqxhr');
    		xhr.done(function() {
    			$("#photo").attr("src", $("#photo").attr("src"));
    			//TODO
    			top.$("#photo").attr("src", $("#photo").attr("src"));
    		});
    	}
    	
    </script>
    <style type="text/css">
    	.photo{
    		width: 180px;
    		height: 180px;
    	}
    	.photo_input{
    	}
    </style>
</head>
<body>

	<div id="mainContent" style="display:none;margin-left:20px;">
		<ul class="nav nav-tabs">
			<li class="active"><a href="#basic" data-toggle="tab">基本</a></li>
			<li><a href="#avatar" data-toggle="tab">头像</a></li>
			<li><a href="#other" data-toggle="tab">其他</a></li>
		</ul>
		
		<div class="tab-content">
			<div class="tab-pane active" id="basic">
				<div class="well">
					<form class="form-horizontal" action="aa_editUser">
						<div class="control-group" id="password-group">
							<label class="control-label" for="password">新密码</label>
							<div class="controls">
								<input type="password" class="input-large" id="password">
								<span class="help-inline" id="password-tip"></span>
							</div>
						</div>
						<div class="control-group" id="repassword-group">
							<label class="control-label" for="repassword">确认密码</label>
							<div class="controls">
								<input type="password" class="input-large" id="repassword">
								<span class="help-inline" id="repassword-tip"></span>
							</div>
						</div>
						<div class="control-group" id="nickname-group">
							<label class="control-label" for="nickname">昵称</label>
							<div class="controls">
								<input type="text" class="input-large" id="nickname" value="${user.nickname}">
								<span class="help-inline" id="nickname-tip"></span>
								<p class="help-block">字母，数字，汉字皆可</p>
							</div>
						</div>
						<div class="control-group">
							<div class="controls">
							 	<a class="btn btn-primary" id="saveBtn">保存</a>
							 	<span class="help-inline" id="save-tip"></span>
							 </div>
						</div>
					</form>
				</div>
			</div>
			<div class="tab-pane" id="avatar">
				<div class="well">
					<form action="ajaxUpload" id="photoForm" method="post" enctype="multipart/form-data" encoding="multipart/form-data">
						<img id="photo" class="photo" alt="" src="img?type=1">
						<input class="photo_input" type="file" name="file" onchange="uploadPhoto()">
						<input name="type" value="1" style="display:none">
					</form>
					<div id="myResultsDiv"></div>
				</div>
			</div>
			<div class="tab-pane" id="other">其他啊其他</div>
		</div>
		
		
		
		
		
		
		
	</div>




</body>
</html>