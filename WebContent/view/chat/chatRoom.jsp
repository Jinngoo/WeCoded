<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ include file="../base.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<title>test</title>
    <%@ include file="../head.jsp"%>  
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    
    <!-- Css -->
	<link href="${BOOTSTRAP_CSS}" rel="stylesheet" media="screen">
	<link href="${BOOTSTRAP_THEME_CSS}" rel="stylesheet" media="screen">
	
	<link href="${RESOURCE}/css/common.css" rel="stylesheet" media="screen">
	
	<!-- Js -->
	<script type="text/javascript" src="${JQUERY}"></script>
	<script type="text/javascript" src="${BOOTSTRAP_JS}"></script>
	<script type="text/javascript" src="${JQUERY_MD5}"></script>
	<script type="text/javascript" src="${JQUERY_COOKIE}"></script>
	
	<script type="text/javascript" src="${JN_UTIL}"></script>
	<script type="text/javascript" src="${RESOURCE}/js/chat/chatRoom.js"></script>
	
	<style type="text/css">
		html, body{
			height: 100%;
		}
		
		.output{
			height: 400px; 
			margin-bottom:10px;
			overflow-y: auto;
			padding: 15px;
		}
		div.chatbox{
			display:none;
			position: relative;
			margin-bottom: 15px;
		}
		div.chatbox img.avatar{
			width:50px;
			height:50px;
			float: left;
			cursor: pointer;
		}
		div.chatbox div.text{
			margin-left: 60px;
			padding-left: 5px;
			padding-right: 10px;
			font-family: "微软雅黑";
		}
	</style>
</head>
<body>
	<%@ include file="../nav_top.jsp" %>
	<input id="userid" type="hidden" value="${sessionScope.user.id}"/>
	
	<div id="template" class="chatbox shadow">
		<img class="avatar shadow" />
		<div class="text"></div>
		<div style="clear:both"></div>
	</div>
	
	<div class="container output well">
		<div id="output"></div>
	</div>


	<div class="container">
		<div class="form-group">
			<div class="input-group">
				<span class="input-group-addon"><b>I say :</b></span>
				<textarea id="input" rows="5" class="form-control"></textarea>
				<span class="input-group-addon">
					<button id="send" class="btn btn-default" style="width:100%;height:100%">发送<br/>Ctrl+Enter</button>
				</span>
			</div>
		</div>
	</div>

</body>
</html>