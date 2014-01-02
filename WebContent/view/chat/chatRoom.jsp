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
	<link href="${RESOURCE}/css/chat/chatRoom.css" rel="stylesheet" media="screen">
	
	
	<style type="text/css">
		
	</style>
	<!-- Js -->
	<script type="text/javascript" src="${JQUERY}"></script>
	<script type="text/javascript" src="${BOOTSTRAP_JS}"></script>
	<script type="text/javascript" src="${JQUERY_MD5}"></script>
	<script type="text/javascript" src="${JQUERY_COOKIE}"></script>
	<script type="text/javascript" src="${JQUERY_JSON}"></script>
	
	<script type="text/javascript" src="${MATH_UUID}"></script>
	<script type="text/javascript" src="${JN_UTIL}"></script>
	<script type="text/javascript" src="${JQUERY_EASY_DRAG}"></script>
	<script type="text/javascript" src="${RESOURCE}/js/chat/chatRoom.js"></script>
</head>
<body>
	<%@ include file="../nav_top.jsp" %>
	<input id="userid" type="hidden" value="${sessionScope.user.id}"/>
	<input id="username" type="hidden" value="${sessionScope.user.name}"/>
	<input id="usernickname" type="hidden" value="${sessionScope.user.nickname}"/>
	
	<div id="template" class="chatbox">
		<img class="avatar shadow" src="" />
		<div class="shadow body">
			<div class="name">name</div>
			<div class="date">date</div>
			<div class="content"></div>
		</div>
	</div>
	
	<div id="userbox" class="userbox well">
		<div><a href="${RESOURCE}/static/jn_chrome.crx" title="开发中，下载另存为，在chrome设置-扩展程序-开发者模式勾选，把插件拖到设置界面里即可安装">chrome提示插件下载</a></div><br/>
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
					<table class="toolTable">
						<tr>
							<td><i id="alertToneSwitch" class="fa fa-volume-up fa-2x tool" title="提示音开关"></i>&nbsp;&nbsp;&nbsp;</td>
							<td><i id="sendPic" class="fa fa-picture-o fa-2x tool" title="发送图片"></i></td>
						</tr>
						<tr>
							<td><i id="clearMsg" class="fa fa-trash-o fa-2x tool" title="清除记录"></i>&nbsp;&nbsp;&nbsp;</td>
							<td><i id="speakMsgSwitch" class="fa fa-microphone fa-2x tool" title="朗读消息"></i></td>
						</tr>
					</table>
				</span>
				<textarea id="input" rows="5" class="form-control"></textarea>
				<span class="input-group-addon dropup" style="width: 140px;">
					<div class="btn-group" style="height:100%">
						<button type="button" class="btn btn-default" id="send" style="width:80px;height:100%">发送</button>
						<button type="button" class="btn btn-info dropdown-toggle" data-toggle="dropdown" style="width:30px;height:100%">
							<span class="caret"></span> 
						</button>
						<ul id="sendShortcutsMenu" class="dropdown-menu pull-right" role="menu">
							<li><a href="javascript:changeSendShortcutKeys(1)">Enter</a></li>
							<li><a href="javascript:changeSendShortcutKeys(2)">Ctrl + Enter</a></li>
						</ul>
					</div>
				</span>
			</div>
		</div>
	</div>
	
	<div id="notificationSupport" type="WeCoded_ChatRoom_Notufication" message="" style="display:none"></div>
	<input type="file" id="fileChooser" style="display:none" accept="image/jpeg,image/gif,image/bmp,image/x-png">
	<audio id="alertAudio" controls="controls" preload="preload" style="display:none">
		<source src="${RESOURCE}/audio/alert1.mp3" type="audio/mpeg">
	</audio>
	
	<div id="videoBox" class="videoBox">
		<img class="videoThumbnail" />
		<img class="videoPlay" src="${RESOURCE}/image/common/play.jpg" title="播放" />
	</div>
	
	<div class="videoWindow">
		<div class="videoTool well">
			<i id="closeVideo" class="fa fa-power-off fa-lg" onclick="closeVideo()"></i>
			<i id="moveVideo" class="fa fa-arrows fa-lg"></i>
			<i id="toggleVideo" class="fa fa-exchange fa-lg" onclick="toggleVideo()"></i>
		</div>
		<div class="videoContainer"></div>
	</div>
	<object id="youku" width="480" height="400" style="display:none" classid="clsid:d27cdb6e-ae6d-11cf-96b8-444553540000" codebase="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=6,0,40,0">
		<param name="src" value="about:blank" />
		<param name="allowFullScreen" value="true"></param>
		<embed src="about:blank" width="480" height="400" type="application/x-shockwave-flash" allowFullScreen="true" quality="high" align="middle" wmode="opaque" allowscriptaccess="always"></embed>
	</object>
	<object id="tudou" width="480" height="400" style="display:none" classid="clsid:d27cdb6e-ae6d-11cf-96b8-444553540000" codebase="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=6,0,40,0">
		<param name="src" value="about:blank" />
		<param name="allowFullScreen" value="true"></param>
		<embed src="about:blank" width="480" height="400" type="application/x-shockwave-flash" allowFullScreen="true" quality="high" align="middle" wmode="opaque" allowscriptaccess="always"></embed>
	</object>
	<object id="youtube" width="480" height="400" style="display:none" classid="clsid:d27cdb6e-ae6d-11cf-96b8-444553540000" codebase="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=6,0,40,0">
		<param name="movie" value="about:blank"></param>
		<param name="allowFullScreen" value="true"></param>
		<embed src="about:blank" type="application/x-shockwave-flash" allowfullscreen="true" width="480" height="400"></embed>
	</object>
	<iframe id="speakFrame" src="about:blank" style="display:none"></iframe>
</body>
</html>