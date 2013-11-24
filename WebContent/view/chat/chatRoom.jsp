<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ include file="../base.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<title>聊天室</title>
    <%@ include file="../head.jsp"%>  
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    
    <!-- Css -->
	<link href="${BOOTSTRAP_CSS}" rel="stylesheet" media="screen">
	<link href="${BOOTSTRAP_THEME_CSS}" rel="stylesheet" media="screen">
	<link href="${FONT_AWESOME_CSS}" rel="stylesheet" media="screen">
	
	<link href="${RESOURCE}/css/common.css" rel="stylesheet" media="screen">
	
	<!-- Js -->
	<script type="text/javascript" src="${JQUERY}"></script>
	<script type="text/javascript" src="${BOOTSTRAP_JS}"></script>
	<script type="text/javascript" src="${JQUERY_MD5}"></script>
	<script type="text/javascript" src="${JQUERY_COOKIE}"></script>
	<script type="text/javascript" src="${JQUERY_JSON}"></script>
	
	<script type="text/javascript" src="${MATH_UUID}"></script>
	<script type="text/javascript" src="${JN_UTIL}"></script>
	<script type="text/javascript" src="${RESOURCE}/js/chat/chatRoom.js"></script>
	
	<style type="text/css">
		body{
			
		}
		.output{
			height: 480px; 
			margin-bottom:10px;
			overflow-y: auto;
			padding: 15px;
			box-shadow: 0px 0px 2px #00CCFF;
		}
		div.fixedbox{
			width:185px;
			height:100px;
			overflow-y: auto;
			position: fixed;
			border:1px solid #00CCFF;
			box-shadow: 0px 0px 4px #0066FF;
			font-family: "微软雅黑";
		}
		div.chatbox{
			display:none;
			position: relative;
			margin-bottom: 15px;
			margin-left: 200px;
		}
		div.chatbox img.avatar{
			width:60px;
			height:60px;
			float: left;
			cursor: pointer;
		}
		div.chatbox div.body{
			font-family: "微软雅黑";
			margin-left: 60px;
			padding-right: 10px;
			width:auto;
			min-height: 60px;
			padding-bottom: 10px;
			clear: right;
		}
		div.chatbox div.name{
			padding-left: 10px;
			float: left;
			font-weight: bold;
		}
		div.chatbox div.date{
			float: right;
		}
		div.chatbox div.content{
			padding-top: 5px;
			padding-left: 25px;
			clear: right;
			word-wrap: break-word; 
			word-break: normal;
		}
		div.chatbox div.content img.image{
			max-width: 100%;
			cursor: pointer;
		}
		i.tool{
			cursor: pointer;
			margin-top: 5px;
		}
		
	</style>
</head>
<body>
	<%@ include file="../nav_top.jsp" %>
	<input id="userid" type="hidden" value="${sessionScope.user.id}"/>
	<input id="username" type="hidden" value="${sessionScope.user.nickname}"/>
	
	<div id="template" class="chatbox">
		<img class="avatar shadow" src="" />
		<div class="shadow body">
			<div class="name">name</div>
			<div class="date">date</div>
			<div class="content"></div>
		</div>
	</div>
	
	<div id="fixed" class="fixedbox well">
		<div>在线人数：<span id="usercount"></span></div>
		<div id="userlist"></div>
	</div>
	
	<div class="container output well">
		<div id="output"></div>
	</div>
	
	<div id="inputArea" class="container">
		<div class="form-group">
			<div class="input-group ">
				<span class="input-group-addon">
					<i id="sendPic" class="fa fa-picture-o fa-2x tool" title="发送图片"></i> <br/>
					<i id="clearMsg" class="fa fa-times fa-2x tool" title="清除记录"></i> 
				</span>
				<textarea id="input" rows="5" class="form-control"></textarea>
				<span class="input-group-addon">
					<button id="send" class="btn btn-default" style="width:100%;height:100%;">发送<br/>Ctrl+Enter</button><br/>
				</span>
			</div>
		</div>
	</div>
	
	
	<input type="file" id="fileChooser" style="display:none" accept="image/jpeg,image/gif,image/bmp,image/x-png">
	<audio id="alertAudio" controls="controls" preload="preload" style="display:none">
		<source src="${RESOURCE}/audio/alert1.mp3" type="audio/mpeg">
	</audio>
<!-- 	<embed id="youku" style="display:none" src="" allowFullScreen="true" quality="high" width="480" height="400" align="middle" allowScriptAccess="always" type="application/x-shockwave-flash"></embed> -->
<!-- 	<embed id="tudou" style="display:none" src="" type="application/x-shockwave-flash" allowscriptaccess="always" allowfullscreen="true" wmode="opaque" width="480" height="400"></embed> -->
</body>
</html>